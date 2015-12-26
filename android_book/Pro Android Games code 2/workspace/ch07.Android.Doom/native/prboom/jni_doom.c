#include <stdio.h>
#include "include/doom_jni_Natives.h"
#include "include/jni_doom.h"
#include "doomdef.h"
#include "d_event.h"


// Global env ref (for callbacks)

//static JNIEnv *g_jniEnv = 0;
static JavaVM *g_VM;

/**
 * Used for image update
 */
jclass jNativesCls;
jmethodID jSendImageMethod;
jmethodID jStartSoundMethod;

// Java image pixels: int ARGB
jintArray jImage;
int iSize;

extern int doom_main(int argc, char **argv);

/*
 * Class:     doom_util_Natives
 * Method:    DoomMain
 * Signature: ([Ljava/lang/String;)V
 */
JNIEXPORT jint JNICALL Java_doom_jni_Natives_DoomMain
  (JNIEnv * env, jclass class, jobjectArray jargv)
{
	// obtain a global ref to the caller jclass
	//g_jniEnv = env;

	(*env)->GetJavaVM(env, &g_VM);

	// Extract char ** args from Java array
	jsize clen =  getArrayLen(env, jargv);

	char * args[(int)clen];

	int i;
	jstring jrow;
	for (i = 0; i < clen; i++)
	{
	    jrow = (jstring)(*env)->GetObjectArrayElement(env, jargv, i);
	    const char *row  = (*env)->GetStringUTFChars(env, jrow, 0);

	    args[i] = malloc( strlen(row) + 1);
	    strcpy (args[i], row);

	    jni_printf("Main argv[%d]=%s", i, args[i]);

	    // free java string jrow
	    (*env)->ReleaseStringUTFChars(env, jrow, row);
	}

	/*
	 * Load the Image update class (called many times)
	 */
	jNativesCls = (*env)->FindClass(env, CB_CLASS);

	if ( jNativesCls == 0 ) {
		jni_printf("Unable to find class: %s", CB_CLASS);
	    return -1;
	}

	// Load doom.util.Natives.OnImageUpdate(char[])
	jSendImageMethod = (*env)->GetStaticMethodID(env, jNativesCls
			, CB_CLASS_IU_CB
			, CB_CLASS_IU_SIG);

	if ( jSendImageMethod == 0 ) {
		jni_printf("Unable to find method OnImageUpdate(char[]): %s", CB_CLASS);
	    return -1;
	}

	// Load OnStartSound(String name, int vol)
	jStartSoundMethod = (*env)->GetStaticMethodID(env, jNativesCls
			, CB_CLASS_SS_CB
			, CB_CLASS_SS_SIG);

	if ( jStartSoundMethod == 0 ) {
		jni_printf("Unable to find method OnStartSound sig: %s ", CB_CLASS_SS_SIG);
	    return -1;
	}

	// Invoke Doom's main sub. This will loop forever
	// Program args come from Java
	doom_main (clen, args);

	return 0;
}

/*
 * Class:     doom_util_Natives
 * Method:    keyEvent
 * Signature: (II)V
 */
extern void D_PostEvent (event_t* ev);

JNIEXPORT jint JNICALL Java_doom_jni_Natives_keyEvent
  (JNIEnv * env, jclass cls, jint type, jint key)
{
	event_t event;
	event.type = (int)type;
	event.data1 = (int)key;
	D_PostEvent(&event);

	return type + key;
}

/*
 * Class:     doom_util_Natives
 * Method:    motionEvent
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_doom_jni_Natives_motionEvent
  (JNIEnv * env, jclass cls, jint x, jint y, jint z)
{
	event_t event;
	event.type = ev_mouse;
	event.data1 = x;
	event.data2 = y;
	event.data3 = z;
	D_PostEvent(&event);
	return 0;
}

/*
 * Class:     doom_util_Natives
 * Method:    setVideoMode
 * Signature: (II)I
 */
extern void I_UpdateVideoMode(void);

JNIEXPORT jint JNICALL Java_doom_jni_Natives_setVideoMode
  (JNIEnv * env, jclass cls, jint w, jint h)
{
	extern int SCREENWIDTH, SCREENHEIGHT, desired_fullscreen;

	SCREENWIDTH = (int)w;
	SCREENHEIGHT = (int)h;
	desired_fullscreen = 0;

	I_UpdateVideoMode();
}

/**
 * Get java array length
 */
const int getArrayLen(JNIEnv * env, jobjectArray jarray)
{
	return (*env)->GetArrayLength(env, jarray);
}



/***********************************************************
 * Doom Callbacks - Send stuff back to Java
 ***********************************************************/


/**
 * Fires when Doom graphics are initialized.
 * params: img width, height
 */
void jni_init_graphics(int width, int height)
{
	JNIEnv *env;

	if ( !g_VM) {
		ERROR0("I_JNI: jni_init_graphics No JNI VM available.\n");
		return;
	}

	(*g_VM)->AttachCurrentThread (g_VM, (void **) &env, NULL);


	iSize = width * height;
/*
	if ( jImage ) {
		jni_printf("Resolution changed %dx%d (Ignoring Java OnInitGraphics)", width, height);
		// TODO (*env)->DeleteLocalRef(env, jImage);
		return;
	}
*/

	// Create a new int[] used by jni_send_pixels
	jImage = (*env)-> NewIntArray(env, iSize);

	// call doom.util.Natives.OnInitGraphics(w, h);
	jmethodID mid = (*env)->GetStaticMethodID(env, jNativesCls
			, CB_CLASS_IG_CB
			, CB_CLASS_IG_SIG);

	if (mid) {
	    (*env)->CallStaticVoidMethod(env, jNativesCls
	    		, mid
	    		, width, height);
	}

	//(*g_VM)->DetachCurrentThread (g_VM);
}

/**
 * Image update Java callback. Gets called many times per sec.
 * It must not lookup JNI classes/methods or create any objects, otherwise
 * the local JNI ref table Will overflow & the app will crash
 */
void jni_send_pixels(int * data)
{
	JNIEnv *env;

	if ( !g_VM) {
		return;
	}

	(*g_VM)->AttachCurrentThread (g_VM, (void **) &env, NULL);

	// Send img back to java.
	if (jSendImageMethod) {
		(*env)->SetIntArrayRegion(env, jImage, 0, iSize, (jint *) data);

		// Call Java method
	    (*env)->CallStaticVoidMethod(env, jNativesCls
	    		, jSendImageMethod
	    		, jImage);
	}

}


/**
 * Send a string back to Java
 */
jmethodID mSendStr;

static void jni_send_str( const char * text, int level) {
	JNIEnv *env;

	if ( !g_VM) {
		printf("I_JNI-NOVM: %s\n", text);
		return;
	}

	(*g_VM)->AttachCurrentThread (g_VM, (void **) &env, NULL);


	if ( !jNativesCls ) {
		jNativesCls = (*env)->FindClass(env, CB_CLASS);

		if ( jNativesCls == 0 ) {
	    		ERROR1("Unable to find class: %s", CB_CLASS);
	    		return;
		}
	}

	// Call doom.jni.Natives.OnMessage(String)
	if (! mSendStr ) {
		mSendStr = (*env)->GetStaticMethodID(env, jNativesCls
			, CB_CLASS_MSG_CB
			, CB_CLASS_MSG_SIG);
	}
	if (mSendStr) {
	    (*env)->CallStaticVoidMethod(env, jNativesCls
	    		, mSendStr
	    		, (*env)->NewStringUTF(env, text)
			, (jint) level );
	}
	else {
	    ERROR2("Unable to find method: %s, signature: %s\n"
	    		, CB_CLASS_MSG_CB, CB_CLASS_MSG_SIG );
	}
}

/**
 * Printf into the java layer
 * does a varargs printf into a temp buffer
 * and calls jni_sebd_str
 */
void jni_printf(char *format, ...)
{
	va_list         argptr;
	static char             string[1024];

	va_start (argptr, format);
	vsprintf (string, format,argptr);
	va_end (argptr);

	jni_send_str (string, 0);
}

void jni_message(int level, char *format, ...)
{
	va_list         argptr;
	static char             string[1024];

	va_start (argptr, format);
	vsprintf (string, format,argptr);
	va_end (argptr);

	jni_send_str (string, level);
}


/**
 * Called when a fatal error has occurred.
 * The receiver should terminate
 */
void jni_fatal_error(const char * text) {
	JNIEnv *env;

	if ( !g_VM) {
		printf("JNI FATAL: No JNI Environment available. %s\n", text);
		exit(-1);
	}

	(*g_VM)->AttachCurrentThread (g_VM, (void **) &env, NULL);

	if ( !env) {
		printf("JNI FATAL: Unable to attach to cuur thread: %s.\n", text);
		exit(-1);
	}

	if ( !jNativesCls ) {
		jNativesCls = (*env)->FindClass(env, CB_CLASS);

		if ( jNativesCls == 0 ) {
	    		ERROR1("JNI FATAL: Unable to find class: %s", CB_CLASS);
	    		exit(-1);
		}
	}
	jmethodID mid = (*env)->GetStaticMethodID(env, jNativesCls
		, CB_CLASS_FATAL_CB
		, CB_CLASS_FATAL_SIG);

	if (mid) {
	    (*env)->CallStaticVoidMethod(env, jNativesCls
	    		, mid
	    		, (*env)->NewStringUTF(env, text) );
	}
	else {
	    printf("JNI FATAL: Unable to find method: %s, signature: %s\n"
	    		, CB_CLASS_MSG_CB, CB_CLASS_MSG_SIG );
	    exit (-1);
	}

}


/**
 * Fires multiple times when a sound is played
 * @param name Sound name
 * @param volume
 */
void jni_start_sound (const char * name, int vol)
{
	/*
	 * Attach to the curr thread otherwise we get JNI WARNING:
	 * threadid=3 using env from threadid=15 which aborts the VM
	 */
	JNIEnv *env;

	if ( !g_VM) {
		//ERROR0("I_JNI: jni_start_sound No JNI Environment available.\n");
		return;
	}

	(*g_VM)->AttachCurrentThread (g_VM, (void **) &env, NULL);

	if ( jStartSoundMethod == 0 ) {
		jni_printf("BUG: Invalid Doom JNI method method OnStartSound %s", CB_CLASS_SS_SIG);
	    return ;
	}

	// Create a new char[] used by jni_send_pixels
	// Used to prevent JNI ref table overflows
	int iSize = strlen(name);
	jbyteArray jSound = (*env)-> NewByteArray(env, iSize);

	(*env)->SetByteArrayRegion(env, jSound, 0, iSize, (jbyte *) name);

	// Call Java method
	(*env)->CallStaticVoidMethod(env, jNativesCls
			, jStartSoundMethod
			, jSound //(*env)->NewStringUTF(env, name)
			, (jint) vol);

	(*env)->DeleteLocalRef(env,jSound);
}

/**
 * Fires when a background song is requested
 */
void jni_start_music (const char * name, int loop)
{
	/*
	 * Attach to the curr thread otherwise we get JNI WARNING:
	 * threadid=3 using env from threadid=15 which aborts the VM
	 */
	JNIEnv *env;

	if ( !g_VM) {
		return;
	}

	(*g_VM)->AttachCurrentThread (g_VM, (void **) &env, NULL);

	jmethodID mid = (*env)->GetStaticMethodID(env, jNativesCls
		, CB_CLASS_SM_CB
		, CB_CLASS_SM_SIG);

	if (mid) {
		(*env)->CallStaticVoidMethod(env, jNativesCls
				, mid
				, (*env)->NewStringUTF(env, name)
				, (jint) loop );

	}
}

/**
 * Fires when a background song is stopped
 */
void jni_stop_music (const char * name)
{
	/*
	 * Attach to the curr thread otherwise we get JNI WARNING:
	 * threadid=3 using env from threadid=15 which aborts the VM
	 */
	JNIEnv *env;

	if ( !g_VM) {
		return;
	}

	(*g_VM)->AttachCurrentThread (g_VM, (void **) &env, NULL);

	jmethodID mid = (*env)->GetStaticMethodID(env, jNativesCls
		, CB_CLASS_STOPM_CB
		, CB_CLASS_STOPM_SIG);

	if (mid) {
		(*env)->CallStaticVoidMethod(env, jNativesCls
				, mid
				, (*env)->NewStringUTF(env, name)
				);

	}
}

/**
 * Set bg msic vol callback
 */
void jni_set_music_volume (int vol) {
	JNIEnv *env;

	if ( !g_VM) {
		return;
	}

	(*g_VM)->AttachCurrentThread (g_VM, (void **) &env, NULL);

	jmethodID mid = (*env)->GetStaticMethodID(env, jNativesCls
		, CB_CLASS_SETMV_CB
		, CB_CLASS_SETMV_SIG);

	if (mid) {
		(*env)->CallStaticVoidMethod(env, jNativesCls
				, mid
				, (jint) vol
				);

	}

}


