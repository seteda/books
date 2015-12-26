/* input/video output "/dev/null" support */

/* this file does nothing but sit there and look pretty dumb */

#include "wl_def.h"
#include "include/jni_wolf.h"

byte *gfxbuf = NULL;

/****************************************************
 * Class XColor
 ***************************************************/
typedef struct Color XColor;

struct Color
{
	int red;
	int green;
	int blue;
	//int pixel;
};

// Pallete
XColor pal[256];

// Basedir
char basedir[64];

/****************************************************
 * Game start sub
 ***************************************************/

int wolf_main(int argc, char ** v)
{
	return WolfMain(argc, v);
}


/*
==========================
=
= Quit
=
==========================
*/

void Quit(const char *error)
{
	memptr screen = NULL;
/*
	if (!error || !*error) {
		CA_CacheGrChunk(ORDERSCREEN);
		screen = grsegs[ORDERSCREEN];
		WriteConfig();
	} else if (error) {
		CA_CacheGrChunk(ERRORSCREEN);
		screen = grsegs[ERRORSCREEN];
	}
*/
	ShutdownId();

	if (screen) {
		/* printf("spiffy ansi screen goes here..\n"); */
	}

	if (error && *error) {
		//printf("Quit: %s\n", error);
		//exit(EXIT_FAILURE);
		char buffer [50];
		sprintf (buffer, "%s", error);
		jni_sys_error(buffer);
 	}

	exit(EXIT_SUCCESS);
}

void VL_WaitVBL(int vbls)
{
	/* hack - but it works for me */
	long last = get_TimeCount() + vbls;
	while (last > get_TimeCount()) ;

}

void VW_UpdateScreen()
{
	//jni_printf("VW_UpdateScreen %dx%d gfxbuf %ld", vwidth, vheight, gfxbuf);

	// screen size
	int size = vwidth * vheight;

	// ARGB pixels
	int pixels[size], i;

	for ( i = 0 ; i < size ; i ++) {
		byte colIdx 	= gfxbuf[i];
		pixels[i] 	= (0xFF << 24)
			| (pal[colIdx].red << 16)
			| (pal[colIdx].green << 8)
			| (pal[colIdx].blue);

	}

	// send thru JNI here
	jni_send_pixels(pixels,0,0, vwidth, vheight);

}

/*
=======================
=
= VL_Startup
=
=======================
*/

void VL_Startup()
{
	vwidth = 320;
	vheight = 200;

	if (MS_CheckParm("x2")) {
		vwidth *= 2;
		vheight *= 2;
	} else if (MS_CheckParm("x3")) {
		vwidth *= 3;
		vheight *= 3;
	}

	if (gfxbuf == NULL)
		gfxbuf = malloc(vwidth * vheight * 1);

	jni_printf("VL_Startup %dx%d. Calling init graphics.",vwidth, vheight);

	jni_init_graphics(vwidth, vheight);
}

/*
=======================
=
= VL_Shutdown
=
=======================
*/

void VL_Shutdown()
{
	if (gfxbuf != NULL) {
		free(gfxbuf);
		gfxbuf = NULL;
	}
}

//===========================================================================

/*
=================
=
= VL_SetPalette
=
=================
*/

void VL_SetPalette(const byte *palette)
{
	int i;

	VL_WaitVBL(1);

	for (i = 0; i < 256; i++)
	{
		pal[i].red = palette[i*3+0] << 2;
		pal[i].green = palette[i*3+1] << 2;
		pal[i].blue = palette[i*3+2] << 2;
	}
/*
	for (i = 0; i < 10; i++)
		printf("pal [%d]= %d\n",i, palette[i]);
*/
	//jni_printf("VL_SetPalette");
}

/*
=================
=
= VL_GetPalette
=
=================
*/

void VL_GetPalette(byte *palette)
{
}

void INL_Update()
{
}


// mouse coords
int dmousex, dmousey;

void IN_GetMouseDelta(int *dx, int *dy)
{

	if (dx)
		*dx = dmousex;
	if (dy)
		*dy = dmousey;
	
}

byte IN_MouseButtons()
{
	return 0;
}

/*
===================
=
= IN_JoyButtons
=
===================
*/

byte IN_JoyButtons()
{
	return 0;
}

///////////////////////////////////////////////////////////////////////////
//
//	IN_GetJoyAbs() - Reads the absolute position of the specified joystick
//
///////////////////////////////////////////////////////////////////////////
void IN_GetJoyAbs(word joy,word *xp,word *yp)
{
	*xp = 0;
	*yp = 0;
}

///////////////////////////////////////////////////////////////////////////
//
//	INL_GetJoyDelta() - Returns the relative movement of the specified
//		joystick (from +/-127)
//
///////////////////////////////////////////////////////////////////////////
void INL_GetJoyDelta(word joy,int *dx,int *dy)
{
	*dx = 0;
	*dy = 0;
}

///////////////////////////////////////////////////////////////////////////
//
//	INL_GetJoyButtons() - Returns the button status of the specified
//		joystick
//
///////////////////////////////////////////////////////////////////////////
word INL_GetJoyButtons(word joy)
{
	return 0;
}

///////////////////////////////////////////////////////////////////////////
//
//      IN_SetupJoy() - Sets up thresholding values and calls INL_SetJoyScale()
//              to set up scaling values
//
///////////////////////////////////////////////////////////////////////////
void IN_SetupJoy(word joy,word minx,word maxx,word miny,word maxy)
{
}
