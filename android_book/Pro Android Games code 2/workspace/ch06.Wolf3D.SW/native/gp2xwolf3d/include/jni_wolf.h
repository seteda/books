
#ifndef JNI_WOLF_H
#define JNI_WOLF_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

/* JNI Includes */
#include <jni.h>


/*
 *----------------------------------------------------------------------------
 * globals
 *----------------------------------------------------------------------------
 */



/* debug macros */
#define DEBUG_PREFIX printf("JNI__DEBUG ");
#define DEBUG0(X) if (debug_jni) {DEBUG_PREFIX printf(X);}
#define DEBUG1(X,Y) if (debug_jni) {DEBUG_PREFIX printf(X,Y);}
#define DEBUG2(X,Y,Z) if (debug_jni) {DEBUG_PREFIX printf(X,Y,Z);}
#define DEBUG3(X,Y,Z,P) if (debug_jni) {DEBUG_PREFIX printf(X,Y,Z,P);}

#define ERROR0(X) printf(X);
#define ERROR1(X,Y) printf(X,Y);
#define ERROR2(X,Y,Z) printf(X,Y,Z);
#define ERROR3(X,Y,Z,P) printf(X,Y,Z,P);

// Send a fatal error msg, before exi
void jni_sys_error(const char * text);

// printf str messages back to java
void jni_printf(char *format, ...);


// For sending img updates back to Java
void jni_init_graphics(int width, int height);
void jni_send_pixels(int * data, int x, int y, int w, int h);
//void jni_start_sound (const char * name, int vol);
void jni_start_sound (int idx);
void jni_start_music (int idx);

/*
void jni_stop_music (const char * name);
void jni_set_music_volume (int vol);
*/
const int getArrayLen(JNIEnv * env, jobjectArray jarray);


#endif
