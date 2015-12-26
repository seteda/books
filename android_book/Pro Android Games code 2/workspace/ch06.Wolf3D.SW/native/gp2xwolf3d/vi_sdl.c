#include "wl_def.h"

#include "SDL.h"
#include "unistd.h"

static SDL_Surface *surface;
static unsigned int sdl_palettemode;

byte *gfxbuf = NULL;

extern void keyboard_handler(int code, int press);
extern boolean InternalKeyboard[NumCodes];

int main (int argc, char *argv[])
{
	return WolfMain(argc, argv);
}

void DisplayTextSplash(byte *text);

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
		//DisplayTextSplash(screen);
	}
	
	if (error && *error) {
		fprintf(stderr, "Quit: %s\n", error);
		exit(EXIT_FAILURE);
 	}
	exit(EXIT_SUCCESS);
}

void VL_WaitVBL(int vbls)
{
	unsigned long last = get_TimeCount() + vbls;
	while (last > get_TimeCount()) ;
}

void VW_UpdateScreen()
{
	SDL_Flip(surface);

	gfxbuf = surface->pixels;
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
	const SDL_VideoInfo *vinfo;
	int flags;
	
	vwidth = 320;
	vheight = 240;

	SDL_Init(SDL_INIT_JOYSTICK | SDL_INIT_VIDEO);
	SDL_JoystickOpen(0);

	vinfo = SDL_GetVideoInfo();
	sdl_palettemode = (vinfo->vfmt->BitsPerPixel == 8) ? (SDL_PHYSPAL|SDL_LOGPAL) : SDL_LOGPAL;
	
	flags = SDL_FULLSCREEN;
		
	surface = SDL_SetVideoMode(vwidth, vheight, 8, flags);
		
	if (surface == NULL) {
		SDL_Quit();
		Quit("(SDL) Couldn't set video mode");
	}
	gfxbuf = surface->pixels;
	
	if (surface->flags & SDL_FULLSCREEN)
		SDL_ShowCursor(0);
	
	SDL_WM_SetCaption(GAMENAME, GAMENAME);	
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
	SDL_Quit();
	chdir("/usr/gp2x");
	execl("gp2xmenu","gp2xmenu",NULL);
}

/* ======================================================================== */

/*
=================
=
= VL_SetPalette
=
=================
*/

void VL_SetPalette(const byte *palette)
{
	SDL_Color colors[256];
	int i;
	
	VL_WaitVBL(1);
	
	for (i = 0; i < 256; i++)
	{
		colors[i].r = palette[i*3+0] << 2;
		colors[i].g = palette[i*3+1] << 2;
		colors[i].b = palette[i*3+2] << 2;
	}
	SDL_SetPalette(surface, sdl_palettemode, colors, 0, 256);
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
	int i;
	for (i=0;i<256;i++)
	{
		palette[i*3+0] = surface->format->palette->colors[i].r >> 2;
		palette[i*3+1] = surface->format->palette->colors[i].g >> 2;
		palette[i*3+2] = surface->format->palette->colors[i].b >> 2;
	}
}

int up, down, left, right, upleft, upright, downleft, downright;

void INL_Update()
{
	SDL_Event event;
	if (SDL_PollEvent(&event)) {
		do {
			switch(event.type) {
				case SDL_JOYBUTTONDOWN:
					switch(event.jbutton.button)
            		{
	            		case GP2X_BUTTON_CLICK:
	            			keyboard_handler(sc_Enter, 1);
	            			break;
	            		case GP2X_BUTTON_START:
							keyboard_handler(sc_Escape, 1);
							break;
						case GP2X_BUTTON_A:
							keyboard_handler(sc_Space, 1);
							break;
						case GP2X_BUTTON_X:
							keyboard_handler(sc_Control, 1);
							break;
						case GP2X_BUTTON_Y:
							keyboard_handler(sc_Y, 1);
							break;
						case GP2X_BUTTON_B:
							keyboard_handler(sc_RShift, 1);
							break;
						case GP2X_BUTTON_L:
							keyboard_handler(sc_5, 1);
							break;
						case GP2X_BUTTON_R:
							keyboard_handler(sc_6, 1);
							break;
						case GP2X_BUTTON_SELECT:
							keyboard_handler(sc_7, 1);
							break;

		//volume adjust hack by Flavor

						case GP2X_BUTTON_VOLUP:
							SD_AdjustVolume(1);
							break;
						case GP2X_BUTTON_VOLDOWN:
							SD_AdjustVolume(-1);
							break;

						case GP2X_BUTTON_UP:
							up = true;
							break;
						case GP2X_BUTTON_DOWN:
							down = true;
							break;
						case GP2X_BUTTON_LEFT:
							left = true;
							break;
						case GP2X_BUTTON_RIGHT:
							right = true;
							break;
						case GP2X_BUTTON_UPLEFT:
							upleft = true;
							break;
						case GP2X_BUTTON_UPRIGHT:
							upright = true;
							break;
						case GP2X_BUTTON_DOWNLEFT:
							downleft = true;
							break;
						case GP2X_BUTTON_DOWNRIGHT:
							downright = true;
							break;
						}
					break;
				case SDL_JOYBUTTONUP:
					switch(event.jbutton.button)
            		{

	            		case GP2X_BUTTON_CLICK:
	            			keyboard_handler(sc_Enter, 0);
	            			break;
	            		case GP2X_BUTTON_START:
							keyboard_handler(sc_Escape, 0);
							break;
						case GP2X_BUTTON_A:
							keyboard_handler(sc_Space, 0);
							break;
						case GP2X_BUTTON_X:
							keyboard_handler(sc_Control, 0);
							break;
						case GP2X_BUTTON_Y:
							keyboard_handler(sc_Y, 0);
							break;
						case GP2X_BUTTON_B:
							keyboard_handler(sc_RShift, 0);
							break;
						case GP2X_BUTTON_L:
							keyboard_handler(sc_5, 0);
							break;
						case GP2X_BUTTON_R:
							keyboard_handler(sc_6, 0);
							break;
						case GP2X_BUTTON_SELECT:
							keyboard_handler(sc_7, 0);
							break;
						case GP2X_BUTTON_UP:
							up = false;
							break;
						case GP2X_BUTTON_DOWN:
							down = false;
							break;
						case GP2X_BUTTON_LEFT:
							left = false;
							break;
						case GP2X_BUTTON_RIGHT:
							right = false;
							break;
						case GP2X_BUTTON_UPLEFT:
							upleft = false;
							break;
						case GP2X_BUTTON_UPRIGHT:
							upright = false;
							break;
						case GP2X_BUTTON_DOWNLEFT:
							downleft = false;
							break;
						case GP2X_BUTTON_DOWNRIGHT:
							downright = false;
							break;
						}
					break;
				case SDL_QUIT:
					/* TODO do something here */
					break;
				default:
					break;
			}
		} while (SDL_PollEvent(&event));
	}
							if (up || (up && upright) || (up && upleft))
							{
								keyboard_handler(sc_UpArrow, 1);
							}
							if (down || (down && downright) || (down && downleft))
							{
								keyboard_handler(sc_DownArrow, 1);
							}
							if (left || (left && downleft) || (left && upleft))
							{
								keyboard_handler(sc_LeftArrow, 1);
							}
							if (right || (right && upright) || (right && downright))
							{
								keyboard_handler(sc_RightArrow, 1);
							}
							if (upleft && !left && !up)
							{
								keyboard_handler(sc_UpArrow, 1);
								keyboard_handler(sc_LeftArrow, 1);
							}
							if (upright && !right && !up)
							{
								keyboard_handler(sc_UpArrow, 1);
								keyboard_handler(sc_RightArrow, 1);
							}
							if (downleft && !left && !down)
							{
								keyboard_handler(sc_DownArrow, 1);
								keyboard_handler(sc_LeftArrow, 1);
							}
							if (downright && !right && !down)
							{
								keyboard_handler(sc_DownArrow, 1);
								keyboard_handler(sc_RightArrow, 1);
							}
							if (!up && !upright && !upleft)
							{
								keyboard_handler(sc_UpArrow, 0);
							}
							if (!down && !downright && !downleft)
							{
								keyboard_handler(sc_DownArrow, 0);
							}
							if (!left && !upleft && !downleft)
							{
								keyboard_handler(sc_LeftArrow, 0);
							}
							if (!right && !upright && !downright)
							{
								keyboard_handler(sc_RightArrow, 0);
							}
}

void IN_GetMouseDelta(int *dx, int *dy)
{
	int x, y;
	
	SDL_GetRelativeMouseState(&x, &y);
	
	if (dx)
		*dx = x;
	if (dy)
		*dy = y;
}

byte IN_MouseButtons()
{
	Uint8 state;
	byte retr;
	
	state = SDL_GetMouseState(NULL, NULL);
	
	retr = 0;
	
	if (state & SDL_BUTTON(SDL_BUTTON_LEFT)) {
		retr |= 1;
	}
	
	if (state & SDL_BUTTON(SDL_BUTTON_RIGHT)) {
		retr |= 2;
	}
	
	if (state & SDL_BUTTON(SDL_BUTTON_MIDDLE)) {
		retr |= 4;
	}
	
	return retr;
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
