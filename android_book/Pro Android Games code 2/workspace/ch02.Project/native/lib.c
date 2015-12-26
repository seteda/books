#include <stdio.h>
#include <stdlib.h>

/* JNI Includes */
#include <jni.h>

#include "include/jni_Natives.h"

#define CB_CLASS "jni/Natives"

/**
 * OnMessage callback
 */
#define CB_CLASS_MSG_CB  "OnMessage"
#define CB_CLASS_MSG_SIG  "(Ljava/lang/String;I)V"

// prototypes

// Lib main Sub
int lib_main(int argc, char **argv) ;

// Used to get the len of a Java Array
const int getArrayLen(JNIEnv * env, jobjectArray jarray);

// printf str messages back to java
void jni_printf(char *format, ...);


// Global env ref (for callbacks)
static JavaVM *g_VM;

// Global Reference to the native Java class jni.Natives.java
static jclass jNativesCls;


/*
 * Class:     jni_Natives
 * Method:    LibMain
 * Signature: ([Ljava/lang/String;)V
 */
JNIEXPORT jint JNICALL Java_jni_Natives_LibMain
  (JNIEnv * env, jclass class, jobjectArray jargv)
{
	// obtain a global ref to the caller jclass
	(*env)->GetJavaVM(env, &g_VM);

	// Extract char ** args from Java array
	jsize clen =  getArrayLen(env, jargv);

	char * args[(int)clen];

	int i;
	jstring jrow;
	for (i = 0; i < clen; i++)
	{
		// Get C string from Java Strin[i]
	    jrow = (jstring)(*env)->GetObjectArrayElement(env, jargv, i);
	    const char *row  = (*env)->GetStringUTFChars(env, jrow, 0);

	    args[i] = malloc( strlen(row) + 1);
	    strcpy (args[i], row);

	    // print args
	    jni_printf("Main argv[%d]=%s", i, args[i]);

	    // free java string jrow
	    (*env)->ReleaseStringUTFChars(env, jrow, row);
	}

	/*
	 * Load the jni.Natives class
	 */
	jNativesCls = (*env)->FindClass(env, CB_CLASS);

	if ( jNativesCls == 0 ) {
		jni_printf("Unable to find class: %s", CB_CLASS);
	    return -1;
	}

	// Invoke the Lib main sub. This will loop forever
	// Program args come from Java
	lib_main (clen, args);

	return 0;
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

	// Load jni.Natives if missing
	if ( !jNativesCls ) {
		jNativesCls = (*env)->FindClass(env, CB_CLASS);

		if ( jNativesCls == 0 ) {
	    		printf("Unable to find class: %s", CB_CLASS);
	    		return;
		}
	}

	// Call jni.Natives.OnMessage(String, int)
	if (! mSendStr ) {
		// Get  aref to the static method: jni.Natives.OnMessage
		mSendStr = (*env)->GetStaticMethodID(env, jNativesCls
			, CB_CLASS_MSG_CB
			, CB_CLASS_MSG_SIG);
	}
	if (mSendStr) {
	    // Call method
	    (*env)->CallStaticVoidMethod(env, jNativesCls
	    		, mSendStr
	    		, (*env)->NewStringUTF(env, text)
			, (jint) level );
	}
	else {
	    printf("Unable to find method: %s, signature: %s\n"
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

/**
 * Get java array length
 */
const int getArrayLen(JNIEnv * env, jobjectArray jarray)
{
	return (*env)->GetArrayLength(env, jarray);
}

/**
 * Library main sub
 */
int lib_main(int argc, char **argv)  
{
	int i;

	jni_printf("Entering LIB MAIN");

	for ( i = 0 ; i < argc ; i++ ) {
		jni_printf("Lib Main argv[%d]=%s", i, argv[i]);
	}

	return 0;
}

