// Emacs style mode select   -*- C++ -*-
//-----------------------------------------------------------------------------
//
// $Id:$
//
// Copyright (C) 1993-1996 by id Software, Inc.
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// $Log:$
//
// DESCRIPTION:
//
//-----------------------------------------------------------------------------

static const char
rcsid[] = "$Id: m_bbox.c,v 1.1 1997/02/03 22:45:10 b1 Exp $";


#include <stdio.h>

#include <string.h>
#include <stdlib.h>

#include <stdarg.h>
#include <sys/time.h>
#include <unistd.h>

#include "doomdef.h"
#include "m_misc.h"
#include "i_video.h"
#include "i_sound.h"

#include "d_net.h"
#include "g_game.h"

/*
#ifdef __GNUG__
#pragma implementation "i_system.h"
#endif
*/


#include "i_system.h"

#include "lprintf.h"
#include "r_fps.h"



int	mb_used = 6;


void
I_Tactile
( int	on,
  int	off,
  int	total )
{
  // UNUSED.
  on = off = total = 0;
}

ticcmd_t	emptycmd;
ticcmd_t*	I_BaseTiccmd(void)
{
    return &emptycmd;
}


int  I_GetHeapSize (void)
{
    return mb_used*1024*1024;
}

byte* I_ZoneBase (int*	size)
{
    *size = mb_used*1024*1024;
    return (byte *) malloc (*size);
}



//
// I_GetTime
// returns time in 1/70th second tics
//

int  I_GetTime (void)
{
    struct timeval	tp;
    struct timezone	tzp;

    int			newtics;
    static int		basetime=0;

    gettimeofday(&tp, &tzp);

    if (!basetime)
	basetime = tp.tv_sec;

    newtics = (tp.tv_sec-basetime)*TICRATE + tp.tv_usec*TICRATE/1000000;
    return newtics;
}



//
// I_Init
//
/*
void I_Init (void)
{
    I_InitSound();
    //  I_InitGraphics();
}
*/

//
// I_Quit
//
void I_Quit (void)
{
    // Vladimir D_QuitNetGame ();
    I_ShutdownSound();
    I_ShutdownMusic();
    M_SaveDefaults ();
    I_ShutdownGraphics();
    exit(0);
}

void I_WaitVBL(int count)
{
#ifdef SGI
    sginap(1);
#else
#ifdef SUN
    sleep(0);
#else
    usleep (count * (1000000/70) );
#endif
#endif
}

void I_BeginRead(void)
{
}

void I_EndRead(void)
{
}

byte*	I_AllocLow(int length)
{
    byte*	mem;

    mem = (byte *)malloc (length);
    memset (mem,0,length);
    return mem;
}


//
// I_Error
//
extern boolean demorecording;

// Send a string back to Java
extern void jni_fatal_error(const char * text);

void I_Error (char *error, ...)
{
    //printf("I_Error: %s\n", error);
    va_list	argptr;
    static char  string[1024];


    // Message first.
    va_start (argptr,error);
    vsprintf (string, error ,argptr);
/* Vladimir
    fprintf (stderr, "Error: ");
    vfprintf ( stderr,error,argptr);
    fprintf (stderr, "\n");

    printf ( "Error: ");
    vprintf (error,argptr);
    printf ("\n");
*/
    va_end (argptr);


    // Vladimir fflush( stderr );

    // Shutdown. Here might be other errors.
    if (demorecording)
	G_CheckDemoStatus();

    // Vladimir D_QuitNetGame ();
    I_ShutdownGraphics();

    // Send the error back to JNI layer
    jni_fatal_error(string);

    // Vladimir JNI Change: The Lib will not terminate to let Java know
    // Something wrong has happened
    //exit(-1);
}

/* cphipps - I_GetVersionString
 * Returns a version string in the given buffer
 */
const char* I_GetVersionString(char* buf, size_t sz)
{
  snprintf(buf,sz,"LxDoom v%s (http://lxdoom.linuxgames.com/)",VERSION);
  return buf;
}
/*
 * I_Filelength
 *
 * Return length of an open file.
 */

int I_Filelength(int handle)
{/*
  struct stat   fileinfo;
  if (fstat(handle,&fileinfo) == -1)
    I_Error("I_Filelength: %s",strerror(errno));
  return fileinfo.st_size;
*/
	int current = lseek(handle, 0, SEEK_CUR);
	int length  = lseek(handle, 0, SEEK_END);
	lseek(handle, current, SEEK_SET);

	return length;
}

// cph - V.Aguilar (5/30/99) suggested return ~/.lxdoom/, creating
//  if non-existant
static const char prboom_dir[] = {"/doom"}; // Mead rem extra slash 8/21/03

const char *I_DoomExeDir(void)
{
  static char *base;
  if (!base)        // cache multiple requests
    {
      char *home = "/sdcard"; //getenv("HOME");
      size_t len = strlen(home);

      base = malloc(len + strlen(prboom_dir) + 1);
      strcpy(base, home);
      // I've had trouble with trailing slashes before...
      if (base[len-1] == '/') base[len-1] = 0;
      strcat(base, prboom_dir);
      //mkdir(base, S_IRUSR | S_IWUSR | S_IXUSR); // Make sure it exists
	printf("I_DoomExeDir: Create dir %s\n", base);
    }
  return base;
}

/*
 * I_FindFile
 *
 * proff_fs 2002-07-04 - moved to i_system
 *
 * cphipps 19/1999 - writen to unify the logic in FindIWADFile and the WAD
 *      autoloading code.
 * Searches the standard dirs for a named WAD file
 * The dirs are listed at the start of the function
 */

char* I_FindFile(const char* wfname, const char* ext)
{
  // Vladimir
  if ( ! wfname ) {
	printf("I_FindFile BUG: File name cannot be NULL!\n");
	jni_fatal_error("I_FindFile BUG: File name cannot be NULL!\n");
	//exit (1);
  }
  if ( ! ext ) {
	printf("I_FindFile BUG: File extension cannot be NULL!\n");
	jni_fatal_error("I_FindFile BUG: File extension cannot be NULL!\n");
	//exit (1);
  }

  // lookup table of directories to search
  static const struct {
    const char *dir; // directory
    const char *sub; // subdirectory
    const char *env; // environment variable
    const char *(*func)(void); // for I_DoomExeDir
  } search[] = {
    {NULL}, // current working directory
    {NULL, NULL, "DOOMWADDIR"}, // run-time $DOOMWADDIR
    {DOOMWADDIR}, // build-time configured DOOMWADDIR
    {NULL, "doom", "HOME"}, // ~/doom
    {NULL, NULL, "HOME"}, // ~
    {NULL, NULL, NULL, I_DoomExeDir}, // config directory
    {"/data"},
    {"/data/data"},
    {"/sdcard/doom"},
    {"/sdcard"},
  };

  int   i;
  /* Precalculate a length we will need in the loop */
  size_t  pl = strlen(wfname) + strlen(ext) + 4;

  for (i = 0; i < sizeof(search)/sizeof(*search); i++) {
    char  * p;
    const char  * d = NULL;
    const char  * s = NULL;
    /* Each entry in the switch sets d to the directory to look in,
     * and optionally s to a subdirectory of d */
    // switch replaced with lookup table
    if (search[i].env) {
      if (!(d = getenv(search[i].env)))
        continue;
    } else if (search[i].func)
      d = search[i].func();
    else
      d = search[i].dir;
    s = search[i].sub;

    p = malloc((d ? strlen(d) : 0) + (s ? strlen(s) : 0) + pl);
    sprintf(p, "%s%s%s%s%s", d ? d : "", (d && !HasTrailingSlash(d)) ? "/" : "",
                             s ? s : "", (s && !HasTrailingSlash(s)) ? "/" : "",
                             wfname);

    if (access(p,F_OK))
      strcat(p, ext);

    if (!access(p,F_OK)) {
      lprintf(LO_INFO, " found %s\n", p);
      return p;
    }

    free(p);
  }
  return NULL;
}

/*
 * I_Read
 *
 * cph 2001/11/18 - wrapper for read(2) which handles partial reads and aborts
 * on error.
 */
void I_Read(int fd, void* vbuf, size_t sz)
{
  unsigned char* buf = vbuf;

  while (sz) {
    int rc = read(fd,buf,sz);
    if (rc <= 0) {
      I_Error("I_Read: read failed: %s", rc ? "IO Exception" : "EOF");
    }
    sz -= rc; buf += rc;
  }
}

/* cphipps - I_SigString
 * Returns a string describing a signal number
 */
const char* I_SigString(char* buf, size_t sz, int signum)
{
#ifdef SYS_SIGLIST_DECLARED
  if (strlen(sys_siglist[signum]) < sz)
    strcpy(buf,sys_siglist[signum]);
  else
#endif
#ifdef HAVE_SNPRINTF
    snprintf(buf,sz,"signal %d",signum);
#else
    sprintf(buf,"signal %d",signum);
#endif
  return buf;
}

void I_uSleep(unsigned long usecs)
{
    usleep(usecs/1000);
}

/*
 * HasTrailingSlash
 *
 * cphipps - simple test for trailing slash on dir names
 */

boolean HasTrailingSlash(const char* dn)
{
  return ( (dn[strlen(dn)-1] == '/')
          );
}

#define DEFAULT_AFFINITY_MASK 1

void I_SetAffinityMask(void)
{
  printf("I_SetAffinityMask\n");
}

fixed_t I_GetTimeFrac (void)
{
  return I_GetTime();
}

int ms_to_next_tick;

int I_GetTime_RealTime (void)
{
  return I_GetTime();
}

extern tic_vars_t tic_vars;

void I_GetTime_SaveMS(void)
{
  if (!movement_smooth)
    return;

  tic_vars.start = I_GetTime(); // SDL_GetTicks();
  tic_vars.next = (unsigned int) ((tic_vars.start * tic_vars.msec + 1.0f) / tic_vars.msec);
  tic_vars.step = tic_vars.next - tic_vars.start;
}

/*
 * I_GetRandomTimeSeed
 *
 * CPhipps - extracted from G_ReloadDefaults because it is O/S based
 */
unsigned long I_GetRandomTimeSeed(void)
{
/* This isnt very random */
  return I_GetTime();
  //return(SDL_GetTicks());
}

static unsigned int start_displaytime;
static unsigned int displaytime;
static boolean InDisplay = false;

boolean I_StartDisplay(void)
{
  if (InDisplay)
    return false;

  start_displaytime = I_GetTime(); //DL_GetTicks();
  InDisplay = true;
  return true;
}

void I_EndDisplay(void)
{
  //displaytime = SDL_GetTicks() - start_displaytime;
  displaytime = I_GetTime() - start_displaytime;
  InDisplay = false;
}


