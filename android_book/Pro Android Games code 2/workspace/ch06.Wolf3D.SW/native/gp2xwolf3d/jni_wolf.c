#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "include/wolf_jni_Natives.h"
#include "include/jni_wolf.h"
#include "wl_def.h"

// Global env ref (for callbacks)
static JavaVM *g_VM;

jclass jNativesCls;
jmethodID jSendImageMethod;
jmethodID jStartSoundMethod;

// Java image pixels: int ARGB
jintArray jImage;
int iSize;

extern int wolf_main(int argc, char **argv);


/*
 * Class:     quake_jni_Natives
 * Method:    QuakeMain
 * Signature: ([Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_wolf_jni_Natives_WolfMain
  (JNIEnv * env, jclass cls, jobjectArray jargv)
{
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

	    // free java string jrow
	    (*env)->ReleaseStringUTFChars(env, jrow, row);
	}

	/*
	 * Load the Image update class (called many times)
	 */
	jNativesCls = (*env)->FindClass(env, "wolf/jni/Natives");

	if ( jNativesCls == 0 ) {
		printf("Unable to find class: wolf/jni/Natives");
	    	return -1;
	}

	// Load doom.util.Natives.OnImageUpdate(char[])
	jSendImageMethod = (*env)->GetStaticMethodID(env, jNativesCls
			, "OnImageUpdate"
			, "([IIIII)V");

	if ( jSendImageMethod == 0 ) {
		jni_printf("Unable to find method wolf.jni.OnImageUpdate(byte[])");
	   	return -1;
	}

	// Load OnStartSound(byte[] name, int vol)
/*
	jStartSoundMethod = (*env)->GetStaticMethodID(env, jNativesCls
			, "OnStartSound"
			, "([BI)V");
*/
	jStartSoundMethod = (*env)->GetStaticMethodID(env, jNativesCls
			, "OnStartSound"
			, "(I)V");


	if ( jStartSoundMethod == 0 ) {
		jni_printf("Unable to find method wolf.jni.OnStartSound sig: ([BI)V");
		return -1;
	}

	for (i = 0; i < clen; i++) {
		jni_printf("WolfMain args[%d]=%s", clen, args[i]);
	}

	// Invoke Quake's main sub. This will loop forever
	// Program args come from Java
	wolf_main (clen, args);

	return 0;

}

extern void keyboard_handler(int code, int press);

/*
 * Class:     wolf_jni_Natives
 * Method:    keyPress
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_wolf_jni_Natives_keyPress
  (JNIEnv * env, jclass cls, jint scanCode)
{
	keyboard_handler((int)scanCode, 1);
	return scanCode;
}

/*
 * Class:     wolf_jni_Natives
 * Method:    keyRelease
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_wolf_jni_Natives_keyRelease
  (JNIEnv * env, jclass cls, jint scanCode)
{
	keyboard_handler((int)scanCode, 0);
	return scanCode;
}


/*
 * Class:     wolf_jni_Natives
 * Method:    motionEvent
 * Signature: (III)I
 */

// Defined in vi_null.c
extern int dmousex;
extern int dmousey;

JNIEXPORT jint JNICALL Java_wolf_jni_Natives_motionEvent
  (JNIEnv * env, jclass cls, jint jbutton, jint jx, jint jy)
{
	dmousex = (int)jx;
	dmousey = (int)jy;
	return 0;
}

/*
 * Class:     wolf_jni_Natives
 * Method:    strafeLeft
 * Signature: ()I
 */
/*
JNIEXPORT jint JNICALL Java_wolf_jni_Natives_strafeLeft
  (JNIEnv * env, jclass cls)
{
	buttonstate[bt_strafeLeft] = true;
	return 0;
}
*/
/*
 * Class:     wolf_jni_Natives
 * Method:    strafeRight
 * Signature: ()I
 */
/*
JNIEXPORT jint JNICALL Java_wolf_jni_Natives_strafeRight
  (JNIEnv * env, jclass cls)
{
	buttonstate[bt_strafeRight] = true;
	return 0;
}
*/


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

	// Create a new int[] used by jni_send_pixels
	jImage = (*env)-> NewIntArray(env, iSize);

	// call doom.util.Natives.OnInitGraphics(w, h);
	jmethodID mid = (*env)->GetStaticMethodID(env, jNativesCls
			, "OnInitGraphics"
			, "(II)V");

	if (mid) {
	    (*env)->CallStaticVoidMethod(env, jNativesCls
	    		, mid
	    		, width, height);
	}
}

/**
 * Image update Java callback. Gets called many times per sec.
 * It must not lookup JNI classes/methods or create any objects, otherwise
 * the local JNI ref table Will overflow & the app will crash
 */
void jni_send_pixels(int * data, int x, int y, int w, int h)
{
	JNIEnv *env;

	if ( !g_VM) {
		return;
	}

	(*g_VM)->AttachCurrentThread (g_VM, (void **) &env, NULL);

	// Send img back to java.
	//int iSize = w * h;
	//jintArray jImage = (*env)-> NewIntArray(env, iSize);

	if (jSendImageMethod) {
		(*env)->SetIntArrayRegion(env, jImage, 0, iSize, (jint *) data);

		// Call Java method
	    	(*env)->CallStaticVoidMethod(env, jNativesCls
	    		, jSendImageMethod
	    		, jImage
			, (jint)x, (jint)y, (jint)w, (jint)h);
	}

	//(*env)->DeleteLocalRef(env,jImage);
}


/**
 * Send a string back to Java
 */
jmethodID mSendStr;

void jni_send_str( const char * text) {
	JNIEnv *env;

	if ( !g_VM) {
		printf("JNI-NOVM:%s\n", text);
		return;
	}

	(*g_VM)->AttachCurrentThread (g_VM, (void **) &env, NULL);


	if ( jNativesCls == 0 ) {
    		return;
	}

	// Call wolf.jni.Natives.OnMessage(String)
	if (! mSendStr ) {
		mSendStr = (*env)->GetStaticMethodID(env, jNativesCls
			, "OnMessage"
			, "(Ljava/lang/String;)V");
	}
	if (mSendStr) {
	    jstring jstr = (*env)->NewStringUTF(env, text);

	    (*env)->CallStaticVoidMethod(env, jNativesCls
	    		, mSendStr
	    		,  jstr );

 	    (*env)->DeleteLocalRef(env,jstr);
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

	jni_send_str (string);
}

/**
 * Called when a fatal error has occurred.
 * The receiver should terminate
 */
void jni_sys_error(const char * text) {
	JNIEnv *env;

	if ( !g_VM) {
		printf("%s\n", text);
		exit (1);
		return;
	}

	(*g_VM)->AttachCurrentThread (g_VM, (void **) &env, NULL);

	if ( !env) {
		ERROR0("I_JNI: jni_fataL-error No JNI Environment available.\n");
		printf("%s\n", text);
		exit (1);
		return;
	}

	if ( !jNativesCls ) {
		printf("%s\n", text);
		exit (1);
    		return;
	}

	jmethodID mid = (*env)->GetStaticMethodID(env, jNativesCls
		, "OnSysError"
		, "(Ljava/lang/String;)V");

	if (mid) {
	    (*env)->CallStaticVoidMethod(env, jNativesCls
	    		, mid
	    		, (*env)->NewStringUTF(env, text) );
	}
}

/**
 * Fires multiple times when a sound is played
 * @param name Sound name
 * @param volume
 */
//void jni_start_sound (const char * name, int vol)
void jni_start_sound (int idx)
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

	if ( jStartSoundMethod == 0 ) {
		jni_printf("BUG: Invalid Doom JNI method method OnStartSound (I)V");
	    	return ;
	}
/*
	// Sund name is a byte[]
	int iSize = strlen(name);
	jbyteArray jSound = (*env)-> NewByteArray(env, iSize);

	(*env)->SetByteArrayRegion(env, jSound, 0, iSize, (jbyte *) name);
*/
	// Call Java method wolf.jni.OnStartSound(byte[] name, int vol)
	(*env)->CallStaticVoidMethod(env, jNativesCls
			, jStartSoundMethod
			//, jSound 
			, (jint) idx);

//	(*env)->DeleteLocalRef(env,jSound);
}

void jni_start_music (int idx)
{
	/*
	 * Attach to the curr thread otherwise we get JNI WARNING:
	 * threadid=3 using env from threadid=15 which aborts the VM
	 */
	JNIEnv *env;

	if ( !g_VM) {
		return;
	}

	if ( !jNativesCls ) {
		printf("JNIStartMusic: No JNI interface\n");
		return;
	}

	(*g_VM)->AttachCurrentThread (g_VM, (void **) &env, NULL);

	jmethodID mid = (*env)->GetStaticMethodID(env, jNativesCls
		, "OnStartMusic"
		, "(I)V");

	if (mid) {
	    (*env)->CallStaticVoidMethod(env, jNativesCls
	    		, mid
	    		, (jint) idx);
	}

}

