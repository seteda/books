////////////////////////////////////////////////////////////////////
//
// WL_MENU.C
// by John Romero (C) 1992 Id Software, Inc.
//
////////////////////////////////////////////////////////////////////
#include "wl_def.h"

//
// PRIVATE PROTOTYPES
//

enum {MOUSE,JOYSTICK,KEYBOARDBTNS,KEYBOARDMOVE};        // FOR INPUT TYPES

void CP_ReadThis();

#define STARTITEM	newgame

char endStringsWL6[9][80]=
{
	ENDSTR1WL6,
	ENDSTR2WL6,
	ENDSTR3WL6,
	ENDSTR4WL6,
	ENDSTR5WL6,
	ENDSTR6WL6,
	ENDSTR7WL6,
	ENDSTR8WL6,
	ENDSTR9WL6
};

char endStringsSOD[9][80]=
{
	ENDSTR1SOD,
	ENDSTR2SOD,
	ENDSTR3SOD,
	ENDSTR4SOD,
	ENDSTR5SOD,
	ENDSTR6SOD,
	ENDSTR7SOD,
	ENDSTR8SOD,
	ENDSTR9SOD
};

CP_iteminfo
	MainItemsWL1={MENU_X,MENU_Y,10,STARTITEM,24},
	MainItems={MENU_X,MENU_Y, 9,STARTITEM,24},
	SndItems={SM_X,SM_Y1,12,0,52},
	LSItems={LSM_X,LSM_Y,10,0,24},
	CtlItems={CTL_X,CTL_Y,6,-1,56},
	CusItems={8,CST_Y+13*2,9,-1,0},
	NewEitems={NE_X,NE_Y,11,0,88},
	NewItems={NM_X,NM_Y,4,2,24};

CP_itemtype 
MainMenu[]=
{
	{1,STR_NG,(MenuFunc)CP_NewGame},
	{1,STR_SD,(MenuFunc)CP_Sound},
	{1,STR_CL,(MenuFunc)CP_Control},
	{1,STR_LG,(MenuFunc)CP_LoadGame},
	{0,STR_SG,(MenuFunc)CP_SaveGame},
	{1,STR_CV,(MenuFunc)CP_ChangeView},
	{1,STR_VS,(MenuFunc)CP_ViewScores},
	{1,STR_BD,0},
	{1,STR_QT,0}
},

SndMenu[]=
{
	{1,STR_NONE,0},
	{1,STR_PC,0},
	{1,STR_ALSB,0},
	{0,"",0},
	{0,"",0},
	{1,STR_NONE,0},
	{1,STR_DISNEY,0},
	{1,STR_SB,0},
	{0,"",0},
	{0,"",0},
	{1,STR_NONE,0},
	{1,STR_ALSB,0}
},

CtlMenu[]=
{
	{0,STR_MOUSEEN,0},
	{0,STR_JOYEN,0},
	{0,STR_PORT2,0},
	{0,STR_GAMEPAD,0},
	{0,STR_SENS,(MenuFunc)MouseSensitivity},
	{1,STR_CUSTOM,(MenuFunc)CustomControls}
},

NewEmenu[]=
{
	{1,"Episode 1\n"
		"Escape from Wolfenstein",0},
	{0,"",0},
	{3,"Episode 2\n"
		"Operation: Eisenfaust",0},
	{0,"",0},
	{3,"Episode 3\n"
		"Die, Fuhrer, Die!",0},
	{0,"",0},
	{3,"Episode 4\n"
		"A Dark Secret",0},
	{0,"",0},
	{3,"Episode 5\n"
		"Trail of the Madman",0},
	{0,"",0},
	{3,"Episode 6\n"
		"Confrontation",0}
},


NewMenu[]=
{
	{1,STR_DADDY,0},
	{1,STR_HURTME,0},
	{1,STR_BRINGEM,0},
	{1,STR_DEATH,0}
},

LSMenu[]=
{
	{1,"",0},
	{1,"",0},
	{1,"",0},
	{1,"",0},
	{1,"",0},
	{1,"",0},
	{1,"",0},
	{1,"",0},
	{1,"",0},
	{1,"",0}
},

CusMenu[]=
{
	{1,"",0},
	{0,"",0},
	{0,"",0},
	{1,"",0},
	{0,"",0},
	{0,"",0},
	{1,"",0},
	{0,"",0},
	{1,"",0}
};


static const int color_hliteWL6[] = { DEACTIVEWL6, HIGHLIGHT, READHCOLOR, 0x67 };
static const int color_hliteSOD[] = { DEACTIVESOD, HIGHLIGHT, READHCOLOR, 0x67 };
static const int color_normlWL6[] = { DEACTIVEWL6, TEXTCOLOR, READCOLOR, 0x6b };
static const int color_normlSOD[] = { DEACTIVESOD, TEXTCOLOR, READCOLOR, 0x6b };

static int EpisodeSelect[6] = { 1 };

static int SaveGamesAvail[10],StartGame,SoundStatus=1,pickquick;
static char SaveGameNames[10][32],SaveName[13]="savegam?.";


////////////////////////////////////////////////////////////////////
//
// INPUT MANAGER SCANCODE TABLES and
//
//	IN_GetScanName() - Returns a string containing the name of the
//		specified scan code
//
////////////////////////////////////////////////////////////////////

static const char
					*ScanNames[] =		// Scan code names with single chars
					{
	"?","?","1","2","3","4","5","6","7","8","9","0","-","+","?","?",
	"Q","W","E","R","T","Y","U","I","O","P","[","]","|","?","A","S",
	"D","F","G","H","J","K","L",";","\"","?","?","?","Z","X","C","V",
	"B","N","M",",",".","/","?","?","?","?","?","?","?","?","?","?",
	"?","?","?","?","?","?","?","?","\xf","?","-","\x15","5","\x11","+","?",
	"\x13","?","?","?","?","?","?","?","?","?","?","?","?","?","?","?",
	"?","?","?","?","?","?","?","?","?","?","?","?","?","?","?","?",
	"?","?","?","?","?","?","?","?","?","?","?","?","?","?","?","?"
					},	// DEBUG - consolidate these
					*ExtScanNames[] =	// Names corresponding to ExtScanCodes
					{
	"Esc","BkSp","Tab","Ctrl","LShft","Space","CapsLk","F1","F2","F3","F4",
	"F5","F6","F7","F8","F9","F10","F11","F12","ScrlLk","Enter","RShft",
	"PrtSc","Alt","Home","PgUp","End","PgDn","Ins","Del","NumLk","Up",
	"Down","Left","Right",""
					};

static const ScanCode
					ExtScanCodes[] =	// Scan codes with >1 char names
					{
	1,0xe,0xf,0x1d,sc_LShift,0x39,0x3a,0x3b,0x3c,0x3d,0x3e,
	0x3f,0x40,0x41,0x42,0x43,0x44,0x57,0x59,0x46,0x1c,sc_RShift,
	0x37,0x38,sc_Home,sc_PgUp,sc_End,sc_PgDn,sc_Insert,sc_Delete,0x45,sc_UpArrow,
	sc_DownArrow,sc_LeftArrow,sc_RightArrow,0x00
					};

const char *IN_GetScanName(ScanCode scan)
{
	const char **p;
	const ScanCode *s;

	for (s = ExtScanCodes, p = ExtScanNames; *s; p++, s++)
		if (*s == scan)
			return *p;

	return ScanNames[scan];
}

////////////////////////////////////////////////////////////////////
//
// Wolfenstein Control Panel!  Ta Da!
//
////////////////////////////////////////////////////////////////////
void US_ControlPanel(byte scancode)
{
	int which;

	if (ingame)
		if (CP_CheckQuick(scancode))
			return;

	if (w0 == true || w1 == true){
		StartCPMusic(MENUSONGWL6);
	} else {
		StartCPMusic(MENUSONGSOD);
	}
	SetupControlPanel();

	//
	// F-KEYS FROM WITHIN GAME
	//
	switch(scancode)
	{
		case sc_F1:
			if (w0 == true){
				HelpScreens();
			} else {
				BossKey();
			}
			goto finishup;

		case sc_F2:
			CP_SaveGame(0);
			goto finishup;

		case sc_F3:
			CP_LoadGame(0);
			goto finishup;

		case sc_F4:
			CP_Sound();
			goto finishup;

		case sc_F5:
			CP_ChangeView();
			goto finishup;

		case sc_F6:
			CP_Control();
			goto finishup;

		finishup:
			CleanupControlPanel();
			if (s0 == true || s1 == true || s2 == true || s3 == true){
				UnCacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
			}
			return;
	}

	if (s0 == true || s1 == true || s2 == true || s3 == true){
		CacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
	}

	DrawMainMenu();
	if (w0 == true || w1 == true){
		MenuFadeInWL6();
	} else {
		MenuFadeInSOD();
	}
	StartGame=0;

	//
	// MAIN MENU LOOP
	//
	do
	{
		which=HandleMenu(&MainItems,&MainMenu[0],NULL);

		if (s1 == true || s2 == true || s3 == true){
			//
			// EASTER EGG FOR SPEAR OF DESTINY!
			//
			if (IN_KeyDown(sc_I) && IN_KeyDown(sc_D)) {
				VW_FadeOut();
				StartCPMusic (XJAZNAZI_MUS);
				UnCacheLump(OPTIONS_LUMP_START,OPTIONS_LUMP_END);
				UnCacheLump(BACKDROP_LUMP_START,BACKDROP_LUMP_END);
				MM_SortMem();
				ClearMemory();
	
				CA_CacheGrChunk (IDGUYS1PIC);
				VWB_DrawPic(0,0,IDGUYS1PIC);
				CA_UnCacheGrChunk(IDGUYS1PIC);
	
				CA_CacheGrChunk (IDGUYS2PIC);
				VWB_DrawPic(0,80,IDGUYS2PIC);
				CA_UnCacheGrChunk(IDGUYS2PIC);
	
				VW_UpdateScreen();
	
				CA_CacheGrChunk(IDGUYSPALETTE);
				VL_FadeIn(0, 255, grsegsSOD[IDGUYSPALETTE], 30);
				CA_UnCacheGrChunk(IDGUYSPALETTE);
	
				while (IN_KeyDown(sc_I) || IN_KeyDown(sc_D)) IN_CheckAck();
				
				IN_ClearKeysDown();
				IN_Ack();
	
				VW_FadeOut();
	
				CacheLump(BACKDROP_LUMP_START,BACKDROP_LUMP_END);
				CacheLump(OPTIONS_LUMP_START,OPTIONS_LUMP_END);
				DrawMainMenu();
				StartCPMusic(MENUSONGSOD);
				MenuFadeInSOD();
			}
		}

		switch(which)
		{
			case viewscores:
				if (MainMenu[viewscores].routine == NULL)
					if (CP_EndGame())
						StartGame=1;

				DrawMainMenu();
				if (w0 == true || w1 == true){
					MenuFadeInWL6();
				} else {
					MenuFadeInSOD();
				}
				break;

			case backtodemo:
				MM_SortMem();
				StartGame=1;
				if (!ingame){
					if (w0 == true || w1 == true){
						StartCPMusic(INTROSONGWL6);
					} else {
						StartCPMusic(INTROSONGSOD);
					}
				}
				VL_FadeOut(0,255,0,0,0,10);
				break;

			case -1:
			case quit:
				CP_Quit();
				break;

			default:
				if (!StartGame)
				{
					DrawMainMenu();
					if (w0 == true || w1 == true){
						MenuFadeInWL6();
					} else {
						MenuFadeInSOD();
					}
				}
		}

	//
	// "EXIT OPTIONS" OR "NEW GAME" EXITS
	//
	} while(!StartGame);

	//
	// DEALLOCATE EVERYTHING
	//
	CleanupControlPanel();

	//
	// CHANGE MAINMENU ITEM
	//
	if (startgame || loadedgame)
	{
		MainMenu[viewscores].routine = NULL;
		strcpy(MainMenu[viewscores].string,STR_EG);
	}

	// RETURN/START GAME EXECUTION
		
	if (s0 == true || s1 == true || s2 == true || s3 == true){
		UnCacheLump(OPTIONS_LUMP_START, OPTIONS_LUMP_END);
		MM_SortMem();
	}
}


////////////////////////
//
// DRAW MAIN MENU SCREEN
//
void DrawMainMenu()
{
	ClearMScreen();

	VWB_DrawPic(112,184,C_MOUSELBACKPIC);
	DrawStripes(10);
	VWB_DrawPic(84,0,C_OPTIONSPIC);

	if (w0 == true){
		DrawWindow(MENU_X-8,MENU_Y-3,MENU_W,MENU_HWL1,BKGDCOLORWL6);
	} else if (w1 == true){
		DrawWindow(MENU_X-8,MENU_Y-3,MENU_W,MENU_H,BKGDCOLORWL6);
	} else {
		DrawWindow(MENU_X-8,MENU_Y-3,MENU_W,MENU_H,BKGDCOLORSOD);
	}

	//
	// CHANGE "GAME" AND "DEMO"
	//
	if (ingame)
	{

		strcpy(&MainMenu[backtodemo].string[8],STR_GAME);

		MainMenu[backtodemo].active=2;
	}
	else
	{
		strcpy(&MainMenu[backtodemo].string[8],STR_DEMO);

		MainMenu[backtodemo].active=1;
	}

	DrawMenu(&MainItems,&MainMenu[0]);
	VW_UpdateScreen();
}

////////////////////////////////////////////////////////////////////
//
// READ THIS!
//
////////////////////////////////////////////////////////////////////
void CP_ReadThis()
{
	StartCPMusic(CORNER_MUS);
	HelpScreens();
	if (w0 == true || w1 == true){
		StartCPMusic(MENUSONGWL6);
	} else {
		StartCPMusic(MENUSONGSOD);
	}
}

////////////////////////////////////////////////////////////////////
//
// BOSS KEY
//
////////////////////////////////////////////////////////////////////
void BossKey()
{
}

////////////////////////////////////////////////////////////////////
//
// CHECK QUICK-KEYS & QUIT (WHILE IN A GAME)
//
////////////////////////////////////////////////////////////////////
int CP_CheckQuick(unsigned scancode)
{
	switch(scancode)
	{
		//
		// END GAME
		//
		case sc_F7:
			CA_CacheGrChunk(STARTFONT+1);

			WindowH=160;
			if (Confirm(ENDGAMESTR))
			{
				playstate = ex_died;
				pickquick = gamestate.lives = 0;
			}

			DrawPlayBorder();
			WindowH=200;
			fontnumber=0;
			MainMenu[savegame].active = 0;
			return 1;

		//
		// QUICKSAVE
		//
		case sc_F8:
			if (SaveGamesAvail[LSItems.curpos] && pickquick)
			{
				CA_CacheGrChunk(STARTFONT+1);
				fontnumber = 1;
				Message(STR_SAVING"...");
				CP_SaveGame(1);
				fontnumber=0;
			}
			else
			{
				if (w0 == true || w1 == true){
					CA_CacheGrChunk(STARTFONT+1);
					CA_CacheGrChunk(C_CURSOR1PIC);
					CA_CacheGrChunk(C_CURSOR2PIC);
					CA_CacheGrChunk(C_DISKLOADING1PIC);
					CA_CacheGrChunk(C_DISKLOADING2PIC);
					CA_CacheGrChunk(C_SAVEGAMEPIC);
					CA_CacheGrChunk(C_MOUSELBACKPIC);
				} else {
					CacheLump(BACKDROP_LUMP_START,BACKDROP_LUMP_END);
					CA_CacheGrChunk(C_CURSOR1PIC);
				}

				VW_FadeOut();

				if (w0 == true || w1 == true){
					StartCPMusic(MENUSONGWL6);
				} else {
					StartCPMusic(MENUSONGSOD);
				}
				pickquick=CP_SaveGame(0);

				SETFONTCOLOR(0,15);
				IN_ClearKeysDown();
				DrawPlayScreen ();

				if (!startgame && !loadedgame)
				{
					if (w0 == true || w1 == true){
						VW_FadeInWL6();
					} else {
						VW_FadeInSOD();
					}
					StartMusic ();
				}

				if (loadedgame)
					playstate = ex_abort;

				lasttimecount = get_TimeCount();

				IN_GetMouseDelta(NULL, NULL); // Clear accumulated mouse movement
				
				if (w0 == true || w1 == true){
					CA_UnCacheGrChunk(C_CURSOR1PIC);
					CA_UnCacheGrChunk(C_CURSOR2PIC);
					CA_UnCacheGrChunk(C_DISKLOADING1PIC);
					CA_UnCacheGrChunk(C_DISKLOADING2PIC);
					CA_UnCacheGrChunk(C_SAVEGAMEPIC);
					CA_UnCacheGrChunk(C_MOUSELBACKPIC);
				} else {
					UnCacheLump (BACKDROP_LUMP_START,BACKDROP_LUMP_END);
				}
			}
			return 1;

		/* QUICKLOAD */
		case sc_F9:
			if (SaveGamesAvail[LSItems.curpos] && pickquick)
			{
				char string[100]=STR_LGC;

				CA_CacheGrChunk(STARTFONT+1);
				fontnumber = 1;

				strcat(string,SaveGameNames[LSItems.curpos]);
				strcat(string,"\"?");

				if (Confirm(string))
					CP_LoadGame(1);

				DrawPlayBorder();
				fontnumber=0;
			}
			else
			{
				if (w0 == true || w1 == true){
					CA_CacheGrChunk(STARTFONT+1);
					CA_CacheGrChunk(C_CURSOR1PIC);
					CA_CacheGrChunk(C_CURSOR2PIC);
					CA_CacheGrChunk(C_DISKLOADING1PIC);
					CA_CacheGrChunk(C_DISKLOADING2PIC);
					CA_CacheGrChunk(C_LOADGAMEPIC);
					CA_CacheGrChunk(C_MOUSELBACKPIC);
				} else {
					CA_CacheGrChunk(C_CURSOR1PIC);
					CacheLump (BACKDROP_LUMP_START,BACKDROP_LUMP_END);
				}

				VW_FadeOut ();

				if (w0 == true || w1 == true){
					StartCPMusic(MENUSONGWL6);
				} else {
					StartCPMusic(MENUSONGSOD);
				}
				pickquick=CP_LoadGame(0);

				SETFONTCOLOR(0,15);
				IN_ClearKeysDown();
				DrawPlayScreen ();

				if (!startgame && !loadedgame)
				{
					if (w0 == true || w1 == true){
						VW_FadeInWL6();
					} else {
						VW_FadeInSOD();
					}
					StartMusic ();
				}

				if (loadedgame)
					playstate = ex_abort;

				lasttimecount = get_TimeCount();

				IN_GetMouseDelta(NULL, NULL); // Clear accumulated mouse movement
				
				if (w0 == true || w1 == true){
					CA_UnCacheGrChunk(C_CURSOR1PIC);
					CA_UnCacheGrChunk(C_CURSOR2PIC);
					CA_UnCacheGrChunk(C_DISKLOADING1PIC);
					CA_UnCacheGrChunk(C_DISKLOADING2PIC);
					CA_UnCacheGrChunk(C_LOADGAMEPIC);
					CA_UnCacheGrChunk(C_MOUSELBACKPIC);
				} else {
					UnCacheLump (BACKDROP_LUMP_START,BACKDROP_LUMP_END);
				}
			}
			return 1;

		//
		// QUIT
		//
		case sc_F10:
			CA_CacheGrChunk(STARTFONT+1);

			WindowX=WindowY=0;
			WindowW=320;
			WindowH=160;
			if (w0 == true || w1 == true){
				if (Confirm(endStringsWL6[(US_RndT()&0x7)+(US_RndT()&1)]))
				{
					VW_UpdateScreen();
					SD_MusicOff();
					SD_StopSound();
					MenuFadeOutWL6();

					Quit(NULL);
				}
			} else {
				if (Confirm(endStringsSOD[(US_RndT()&0x7)+(US_RndT()&1)]))
				{
					VW_UpdateScreen();
					SD_MusicOff();
					SD_StopSound();
					MenuFadeOutSOD();

					Quit(NULL);
				}
			}

			DrawPlayBorder();
			WindowH=200;
			fontnumber=0;
			return 1;
		}

	return 0;
}


////////////////////////////////////////////////////////////////////
//
// END THE CURRENT GAME
//
////////////////////////////////////////////////////////////////////
int CP_EndGame()
{
	if (!Confirm(ENDGAMESTR))
		return 0;

	pickquick = gamestate.lives = 0;
	playstate = ex_died;

	MainMenu[savegame].active = 0;
	MainMenu[viewscores].routine = (MenuFunc)CP_ViewScores;
	strcpy(MainMenu[viewscores].string,STR_VS);

	return 1;
}


////////////////////////////////////////////////////////////////////
//
// VIEW THE HIGH SCORES
//
////////////////////////////////////////////////////////////////////
void CP_ViewScores()
{
	fontnumber=0;

	if (s0 == true || s1 == true || s2 == true || s3 == true){
		UnCacheLump(OPTIONS_LUMP_START,OPTIONS_LUMP_END);
		StartCPMusic(XAWARD_MUS);
	} else {
		StartCPMusic(ROSTER_MUS);
	}

	DrawHighScores();
	VW_UpdateScreen();
	if (w0 == true || w1 == true){
		MenuFadeInWL6();
	} else {
		MenuFadeInSOD();
	}
	fontnumber=1;

	IN_Ack();

	if (w0 == true || w1 == true){
		StartCPMusic(MENUSONGWL6);
	} else {
		StartCPMusic(MENUSONGSOD);
	}
	if (w0 == true || w1 == true){
		MenuFadeOutWL6();
	} else {
		MenuFadeOutSOD();
	}

	if (s0 == true || s1 == true || s2 == true || s3 == true){
		CacheLump (BACKDROP_LUMP_START,BACKDROP_LUMP_END);
		CacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
	}
}


////////////////////////////////////////////////////////////////////
//
// START A NEW GAME
//
////////////////////////////////////////////////////////////////////
void CP_NewGame()
{
	int which,episode;

	if (s0 == true || s1 == true || s2 == true || s3 == true){
		UnCacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
	}

	episode = 0;

	if (w0 == true || w1 == true){
firstpart:

		DrawNewEpisode();
		do
		{
			which=HandleMenu(&NewEitems,&NewEmenu[0],NULL);
			switch(which)
			{
				case -1:
					MenuFadeOutWL6();
					return;
	
				default:
					if (!EpisodeSelect[which/2])
					{
						if (w0 == true || w1 == true){
							SD_PlaySoundWL6 (NOWAYSNDWL6);
						} else {
							SD_PlaySoundSOD (NOWAYSNDSOD);
						}
						Message("Please select \"Read This!\"\n"
								"from the Options menu to\n"
								"find out how to order this\n"
								"episode from Apogee.");
						IN_ClearKeysDown();
						IN_Ack();
						DrawNewEpisode();
						which = 0;
					}
					else
					{
						episode = which/2;
						which = 1;
					}
					break;
			}
	
		} while (!which);
	
		ShootSnd();
	
		//
		// ALREADY IN A GAME?
		//
		if (ingame)
			if (!Confirm(CURGAME))
			{
				MenuFadeOutWL6();
				return;
			}
	
		MenuFadeOutWL6();

	} else {
	//
	// ALREADY IN A GAME?
	//
	CacheLump (NEWGAME_LUMP_START,NEWGAME_LUMP_END);
	DrawNewGame();
	if (ingame)
		if (!Confirm(CURGAME))
		{
			MenuFadeOutSOD();
			UnCacheLump (NEWGAME_LUMP_START,NEWGAME_LUMP_END);
			CacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
			return;
		}
	}

	DrawNewGame();
	which=HandleMenu(&NewItems,&NewMenu[0],DrawNewGameDiff);
	if (which<0)
	{
		if (w0 == true || w1 == true){
			MenuFadeOutWL6();
		} else {
			MenuFadeOutSOD();
		}
		if (w0 == true || w1 == true){
			goto firstpart;
		} else {
			UnCacheLump (NEWGAME_LUMP_START,NEWGAME_LUMP_END);
			CacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
			return;
		}
	}

	ShootSnd();
	NewGame(which,episode);
	StartGame=1;
	if (w0 == true || w1 == true){
		MenuFadeOutWL6();
	} else {
		MenuFadeOutSOD();
	}

	pickquick = 0;

	if (s0 == true || s1 == true || s2 == true || s3 == true){
		UnCacheLump (NEWGAME_LUMP_START,NEWGAME_LUMP_END);
		CacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
	}
}

/////////////////////
//
// DRAW NEW EPISODE MENU
//
void DrawNewEpisode(void)
{
	int i;

	ClearMScreen();
	VWB_DrawPic(112,184,C_MOUSELBACKPIC);

	if (w0 == true || w1 == true){
		SETFONTCOLOR(READHCOLOR,BKGDCOLORWL6);
		DrawWindow(NE_X-4,NE_Y-4,NE_W+8,NE_H+8,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(READHCOLOR,BKGDCOLORSOD);
		DrawWindow(NE_X-4,NE_Y-4,NE_W+8,NE_H+8,BKGDCOLORSOD);
	}
	PrintY=2;
	WindowX=0;
	US_CPrint("Which episode to play?");

	if (w0 == true || w1 == true){
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORSOD);
	}
	DrawMenu(&NewEitems,&NewEmenu[0]);

	for (i=0;i<6;i++)
		VWB_DrawPic(NE_X+32,NE_Y+i*26,C_EPISODE1PIC+i);

	VW_UpdateScreen();
	if (w0 == true || w1 == true){
		MenuFadeInWL6();
	} else {
		MenuFadeInSOD();
	}
	WaitKeyUp();
}

/////////////////////
//
// DRAW NEW GAME MENU
//
void DrawNewGame(void)
{
	ClearMScreen();
	VWB_DrawPic(112,184,C_MOUSELBACKPIC);

	if (w0 == true || w1 == true){
		SETFONTCOLOR(READHCOLOR,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(READHCOLOR,BKGDCOLORSOD);
	}
	PrintX=NM_X+20;
	PrintY=NM_Y-32;

	if (w0 == true || w1 == true){
		US_Print("How tough are you?");
	} else {
		VWB_DrawPic (PrintX,PrintY,C_HOWTOUGHPIC);
	}

	if (w0 == true || w1 == true){
		DrawWindow(NM_X-5,NM_Y-10,NM_W,NM_H,BKGDCOLORWL6);
	} else {
		DrawWindow(NM_X-5,NM_Y-10,NM_W,NM_H,BKGDCOLORSOD);
	}

	DrawMenu(&NewItems,&NewMenu[0]);
	DrawNewGameDiff(NewItems.curpos);
	VW_UpdateScreen();
	if (w0 == true || w1 == true){
		MenuFadeInWL6();
	} else {
		MenuFadeInSOD();
	}
	WaitKeyUp();
}


////////////////////////
//
// DRAW NEW GAME GRAPHIC
//
void DrawNewGameDiff(int w)
{
	VWB_DrawPic(NM_X+185,NM_Y+7,w+C_BABYMODEPIC);
}


////////////////////////////////////////////////////////////////////
//
// HANDLE SOUND MENU
//
////////////////////////////////////////////////////////////////////
void CP_Sound()
{
	int which;

	if (s0 == true || s1 == true || s2 == true || s3 == true){
		UnCacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
		CacheLump (SOUND_LUMP_START,SOUND_LUMP_END);
	}

	DrawSoundMenu();
	if (w0 == true || w1 == true){
		MenuFadeInWL6();
	} else {
		MenuFadeInSOD();
	}
	WaitKeyUp();

	do
	{
		which=HandleMenu(&SndItems,&SndMenu[0],NULL);
		//
		// HANDLE MENU CHOICES
		//
		switch(which)
		{
			//
			// SOUND EFFECTS
			//
			case 0:
				if (SoundMode!=sdm_Off)
				{
					SD_WaitSoundDone();
					SD_SetSoundMode(sdm_Off);
					DrawSoundMenu();
				}
				break;
			case 1:
				if (SoundMode!=sdm_PC)
				{
					SD_WaitSoundDone();
					SD_SetSoundMode(sdm_PC);
					CA_LoadAllSounds();
					DrawSoundMenu();
					ShootSnd();
				}
				break;
			case 2:
				if (SoundMode!=sdm_AdLib)
				{
					SD_WaitSoundDone();
					SD_SetSoundMode(sdm_AdLib);
					CA_LoadAllSounds();
					DrawSoundMenu();
					ShootSnd();
				}
				break;

			//
			// DIGITIZED SOUND
			//
			case 5:
				if (DigiMode!=sds_Off)
				{
					SD_SetDigiDevice(sds_Off);
					DrawSoundMenu();
				}
				break;
			case 6:
				break;
			case 7:
				if (DigiMode!=sds_SoundBlaster)
				{
					SD_SetDigiDevice(sds_SoundBlaster);
					DrawSoundMenu();
					ShootSnd();
				}
				break;

			//
			// MUSIC
			//
			case 10:
				if (MusicMode!=smm_Off)
				{
					SD_SetMusicMode(smm_Off);
					DrawSoundMenu();
					ShootSnd();
				}
				break;
			case 11:
				if (MusicMode!=smm_AdLib)
				{
					SD_SetMusicMode(smm_AdLib);
					DrawSoundMenu();
					ShootSnd();
					if (w0 == true || w1 == true){
						StartCPMusic(MENUSONGWL6);
					} else {
						StartCPMusic(MENUSONGSOD);
					}
				}
				break;
		}
	} while(which>=0);

	if (w0 == true || w1 == true){
		MenuFadeOutWL6();
	} else {
		MenuFadeOutSOD();
	}

	if (s0 == true || s1 == true || s2 == true || s3 == true){
		UnCacheLump (SOUND_LUMP_START,SOUND_LUMP_END);
		CacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
	}
}


//////////////////////
//
// DRAW THE SOUND MENU
//
void DrawSoundMenu(void)
{
	int i,on;

	//
	// DRAW SOUND MENU
	//
	ClearMScreen();
	VWB_DrawPic(112,184,C_MOUSELBACKPIC);

	if (w0 == true || w1 == true){
		DrawWindow(SM_X-8,SM_Y1-3,SM_W,SM_H1,BKGDCOLORWL6);
		DrawWindow(SM_X-8,SM_Y2-3,SM_W,SM_H2,BKGDCOLORWL6);
		DrawWindow(SM_X-8,SM_Y3-3,SM_W,SM_H3,BKGDCOLORWL6);
	} else {
		DrawWindow(SM_X-8,SM_Y1-3,SM_W,SM_H1,BKGDCOLORSOD);
		DrawWindow(SM_X-8,SM_Y2-3,SM_W,SM_H2,BKGDCOLORSOD);
		DrawWindow(SM_X-8,SM_Y3-3,SM_W,SM_H3,BKGDCOLORSOD);
	}

	//
	// IF NO ADLIB, NON-CHOOSENESS!
	//
	if (!AdLibPresent && !SoundBlasterPresent)
	{
		SndMenu[2].active=SndMenu[10].active=SndMenu[11].active=0;
	}

	SndMenu[6].active = 0;

	if (!SoundBlasterPresent)
		SndMenu[7].active=0;

	if (!SoundBlasterPresent)
		SndMenu[5].active=0;

	DrawMenu(&SndItems,&SndMenu[0]);
	VWB_DrawPic(100,SM_Y1-20,C_FXTITLEPIC);
	VWB_DrawPic(100,SM_Y2-20,C_DIGITITLEPIC);
	VWB_DrawPic(100,SM_Y3-20,C_MUSICTITLEPIC);

	for (i=0;i<SndItems.amount;i++)
		if (SndMenu[i].string[0])
		{
			//
			// DRAW SELECTED/NOT SELECTED GRAPHIC BUTTONS
			//
			on=0;
			switch(i)
			{
				//
				// SOUND EFFECTS
				//
				case 0: if (SoundMode==sdm_Off) on=1; break;
				case 1: if (SoundMode==sdm_PC) on=1; break;
				case 2: if (SoundMode==sdm_AdLib) on=1; break;

				//
				// DIGITIZED SOUND
				//
				case 5: if (DigiMode==sds_Off) on=1; break;
				case 6: break;
				case 7: if (DigiMode==sds_SoundBlaster) on=1; break;

				//
				// MUSIC
				//
				case 10: if (MusicMode==smm_Off) on=1; break;
				case 11: if (MusicMode==smm_AdLib) on=1; break;
			}

			if (on)
				VWB_DrawPic(SM_X+24,SM_Y1+i*13+2,C_SELECTEDPIC);
			else
				VWB_DrawPic(SM_X+24,SM_Y1+i*13+2,C_NOTSELECTEDPIC);
		}

	DrawMenuGun(&SndItems);
	VW_UpdateScreen();
}


//
// DRAW LOAD/SAVE IN PROGRESS
//
void DrawLSAction(int which)
{
	#define LSA_X	96
	#define LSA_Y	80
	#define LSA_W	130
	#define LSA_H	42

	DrawWindow(LSA_X,LSA_Y,LSA_W,LSA_H,TEXTCOLOR);
	DrawOutline(LSA_X,LSA_Y,LSA_W,LSA_H,0,HIGHLIGHT);
	VWB_DrawPic(LSA_X+8,LSA_Y+5,C_DISKLOADING1PIC);

	fontnumber=1;
	SETFONTCOLOR(0,TEXTCOLOR);
	PrintX=LSA_X+46;
	PrintY=LSA_Y+13;

	if (!which)
		US_Print(STR_LOADING"...");
	else
		US_Print(STR_SAVING"...");

	VW_UpdateScreen();
}


////////////////////////////////////////////////////////////////////
//
// LOAD SAVED GAMES
//
////////////////////////////////////////////////////////////////////
int CP_LoadGame(int quick)
{
	int which, exit=0;
	char name[13];

	strcpy(name, SaveName);

	//
	// QUICKLOAD?
	//
	if (quick)
	{
		which=LSItems.curpos;

		if (SaveGamesAvail[which])
		{
			name[7]=which+'0';
			loadedgame=true;
			LoadTheGame(name, 0, 0);
			loadedgame=false;

			DrawStatusBar();
			
			return 1;
		}
	}


	if (s0 == true || s1 == true || s2 == true || s3 == true){
		UnCacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
		CacheLump (LOADSAVE_LUMP_START,LOADSAVE_LUMP_END);
	}

	DrawLoadSaveScreen(0);

	do
	{
		which=HandleMenu(&LSItems,&LSMenu[0],TrackWhichGame);
		if (which>=0 && SaveGamesAvail[which])
		{
			ShootSnd();
			name[7]=which+'0';

			DrawLSAction(0);
			loadedgame=true;

			LoadTheGame(name, LSA_X+8, LSA_Y+5);

			StartGame=1;
			ShootSnd();
			//
			// CHANGE "READ THIS!" TO NORMAL COLOR
			//

			//if (w0 == true){
			//	MainMenu[readthis].active=1; Hooka: Maybe put readthis back in when you get a chance
			//}

			exit=1;
			break;
		}

	} while(which>=0);

	if (w0 == true || w1 == true){
		MenuFadeOutWL6();
	} else {
		MenuFadeOutSOD();
	}

	if (s0 == true || s1 == true || s2 == true || s3 == true){
		UnCacheLump (LOADSAVE_LUMP_START,LOADSAVE_LUMP_END);
		CacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
	}

	return exit;
}


///////////////////////////////////
//
// HIGHLIGHT CURRENT SELECTED ENTRY
//
void TrackWhichGame(int w)
{
	static int lastgameon=0;

	PrintLSEntry(lastgameon,TEXTCOLOR);
	PrintLSEntry(w,HIGHLIGHT);

	lastgameon=w;
}


////////////////////////////
//
// DRAW THE LOAD/SAVE SCREEN
//
void DrawLoadSaveScreen(int loadsave)
{
	#define DISKX	100
	#define DISKY	0

	int i;


	ClearMScreen();
	fontnumber=1;
	VWB_DrawPic(112,184,C_MOUSELBACKPIC);
	if (w0 == true || w1 == true){
		DrawWindow(LSM_X-10,LSM_Y-5,LSM_W,LSM_H,BKGDCOLORWL6);
	} else {
		DrawWindow(LSM_X-10,LSM_Y-5,LSM_W,LSM_H,BKGDCOLORSOD);
	}
	DrawStripes(10);

	if (!loadsave)
		VWB_DrawPic(60,0,C_LOADGAMEPIC);
	else
		VWB_DrawPic(60,0,C_SAVEGAMEPIC);

	for (i=0;i<10;i++)
		PrintLSEntry(i,TEXTCOLOR);

	DrawMenu(&LSItems,&LSMenu[0]);
	VW_UpdateScreen();
	if (w0 == true || w1 == true){
		MenuFadeInWL6();
	} else {
		MenuFadeInSOD();
	}
	WaitKeyUp();
}


///////////////////////////////////////////
//
// PRINT LOAD/SAVE GAME ENTRY W/BOX OUTLINE
//
void PrintLSEntry(int w,int color)
{
	if (w0 == true || w1 == true){
		SETFONTCOLOR(color,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(color,BKGDCOLORSOD);
	}
	DrawOutline(LSM_X+LSItems.indent,LSM_Y+w*13,LSM_W-LSItems.indent-15,11,color,color);
	PrintX=LSM_X+LSItems.indent+2;
	PrintY=LSM_Y+w*13+1;
	fontnumber=0;

	if (SaveGamesAvail[w])
		US_Print(SaveGameNames[w]);
	else
		US_Print("      - "STR_EMPTY" -");

	fontnumber=1;
}


////////////////////////////////////////////////////////////////////
//
// SAVE CURRENT GAME
//
////////////////////////////////////////////////////////////////////
int CP_SaveGame(int quick)
{
	int which, exit=0;
	char name[13], input[32];

	strcpy(name,SaveName);

	//
	// QUICKSAVE?
	//
	if (quick)
	{
		which=LSItems.curpos;

		if (SaveGamesAvail[which])
		{
			name[7] = which+'0';
			SaveTheGame(name, &SaveGameNames[which][0], 0, 0);
			
			return 1;
		}
	}


	if (s0 == true || s1 == true || s2 == true || s3 == true){
		UnCacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
		CacheLump (LOADSAVE_LUMP_START,LOADSAVE_LUMP_END);
	}

	DrawLoadSaveScreen(1);

	do
	{
		which=HandleMenu(&LSItems,&LSMenu[0],TrackWhichGame);
		if (which>=0)
		{
			//
			// OVERWRITE EXISTING SAVEGAME?
			//
			if (SaveGamesAvail[which]) {
				if (!Confirm(GAMESVD))
				{
					DrawLoadSaveScreen(1);
					continue;
				}
				else
				{
					DrawLoadSaveScreen(1);
					PrintLSEntry(which,HIGHLIGHT);
					VW_UpdateScreen();
				}
			}
			ShootSnd();

			strcpy(input,&SaveGameNames[which][0]);
			name[7]=which+'0';

			fontnumber=0;
			if (!SaveGamesAvail[which]){
				if (w0 == true || w1 == true){
					VW_Bar(LSM_X+LSItems.indent+1,LSM_Y+which*13+1,LSM_W-LSItems.indent-16,10,BKGDCOLORWL6);
				} else {
					VW_Bar(LSM_X+LSItems.indent+1,LSM_Y+which*13+1,LSM_W-LSItems.indent-16,10,BKGDCOLORSOD);
				}
			}
			VW_UpdateScreen();

			if (US_LineInput(LSM_X+LSItems.indent+2,LSM_Y+which*13+1,input,input,true,31,LSM_W-LSItems.indent-30))
			{
				SaveGamesAvail[which] = 1;
				DrawLSAction(1);
				strcpy(&SaveGameNames[which][0],input);
				SaveTheGame(name, input, LSA_X+8, LSA_Y+5);
				ShootSnd();
				exit=1;
			}
			else
			{
				if (w0 == true || w1 == true){
					VW_Bar(LSM_X+LSItems.indent+1,LSM_Y+which*13+1,LSM_W-LSItems.indent-16,10,BKGDCOLORWL6);
				} else {
					VW_Bar(LSM_X+LSItems.indent+1,LSM_Y+which*13+1,LSM_W-LSItems.indent-16,10,BKGDCOLORSOD);
				}
				PrintLSEntry(which,HIGHLIGHT);
				VW_UpdateScreen();
				 if (w0 == true || w1 == true){
					 SD_PlaySoundWL6(ESCPRESSEDSNDWL6);
				 } else {
					 SD_PlaySoundSOD(ESCPRESSEDSNDSOD);
				 }
				continue;
			}

			fontnumber=1;
			break;
		}

	} while(which>=0);

	if (w0 == true || w1 == true){
		MenuFadeOutWL6();
	} else {
		MenuFadeOutSOD();
	}

	if (s0 == true || s1 == true || s2 == true || s3 == true){
		UnCacheLump (LOADSAVE_LUMP_START,LOADSAVE_LUMP_END);
		CacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
	}

	return exit;
}


////////////////////////////////////////////////////////////////////
//
// CALIBRATE JOYSTICK
//
////////////////////////////////////////////////////////////////////
int CalibrateJoystick()
{
	#define CALX	85
	#define CALY	40
	#define CALW	158
	#define CALH	140

	word xmin,ymin,xmax,ymax,jb;

	DrawWindow(CALX-5,CALY-5,CALW,CALH,TEXTCOLOR);
	DrawOutline(CALX-5,CALY-5,CALW,CALH,0,HIGHLIGHT);
	SETFONTCOLOR(0,TEXTCOLOR);

	WindowX = PrintX = CALX;
	WindowW = CALW;
	WindowH = CALH;
	WindowY = PrintY = CALY;
	US_Print("    "STR_CALIB"\n    "STR_JOYST"\n");
	VWB_DrawPic(CALX+40,CALY+30,C_JOY1PIC);
	PrintY = CALY+80;
	US_Print(STR_MOVEJOY);
	if (w0 == true || w1 == true){
		SETFONTCOLOR(BKGDCOLORWL6,TEXTCOLOR);
	} else {
		SETFONTCOLOR(BKGDCOLORSOD,TEXTCOLOR);
	}
	US_Print("   "STR_ESCEXIT);

	VW_UpdateScreen();

	do {
		jb=IN_JoyButtons();
		IN_CheckAck(); /* force update */
		if (IN_KeyDown(sc_Escape))
			return 0;
		
		if (IN_KeyDown(sc_Tab) && IN_KeyDown(sc_P) && MS_CheckParm("debugmode"))
			PicturePause();

	} while(!(jb&1));

	if (w0 == true || w1 == true){
		SD_PlaySoundWL6(SHOOTSNDWL6);
	} else {
		SD_PlaySoundSOD(SHOOTSNDSOD);
	}
	IN_GetJoyAbs(joystickport, &xmin, &ymin);


	DrawWindow(CALX-5,CALY-5,CALW,CALH,TEXTCOLOR);
	DrawOutline(CALX-5,CALY-5,CALW,CALH,0,HIGHLIGHT);
	SETFONTCOLOR(0,TEXTCOLOR);

	PrintX = CALX;
	PrintY = CALY;
	US_Print("    "STR_CALIB"\n    "STR_JOYST"\n");
	VWB_DrawPic(CALX+40,CALY+30,C_JOY2PIC);
	PrintY = CALY+80;
	US_Print(STR_MOVEJOY2);
	if (w0 == true || w1 == true){
		SETFONTCOLOR(BKGDCOLORWL6,TEXTCOLOR);
	} else {
		SETFONTCOLOR(BKGDCOLORSOD,TEXTCOLOR);
	}
	US_Print("   "STR_ESCEXIT);

	VW_UpdateScreen();

	do {
		jb = IN_JoyButtons();
		IN_CheckAck(); /* force update */
		if (IN_KeyDown(sc_Escape))
			return 0;
		if (IN_KeyDown(sc_Tab) && IN_KeyDown(sc_P) && MS_CheckParm("debugmode"))
			PicturePause();
	} while(!(jb&2));

	IN_GetJoyAbs(joystickport,&xmax,&ymax);
	if (w0 == true || w1 == true){
		SD_PlaySoundWL6(SHOOTSNDWL6);
	} else {
		SD_PlaySoundSOD(SHOOTSNDSOD);
	}

	while (IN_JoyButtons());

	//
	// ASSIGN ACTUAL VALUES HERE
	//
	if ((xmin != xmax) && (ymin != ymax))
		IN_SetupJoy(joystickport,xmin,xmax,ymin,ymax);
	else
		return 0;

	return 1;
}


////////////////////////////////////////////////////////////////////
//
// DEFINE CONTROLS
//
////////////////////////////////////////////////////////////////////
void CP_Control()
{
	enum {MOUSEENABLE,JOYENABLE,USEPORT2,PADENABLE,MOUSESENS,CUSTOMIZE};
	int which;

	if (s0 == true || s1 == true || s2 == true || s3 == true){
		UnCacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
		CacheLump (CONTROL_LUMP_START,CONTROL_LUMP_END);
	}

	DrawCtlScreen();
	if (w0 == true || w1 == true){
		MenuFadeInWL6();
	} else {
		MenuFadeInSOD();
	}
	WaitKeyUp();

	do
	{
		which=HandleMenu(&CtlItems,&CtlMenu[0],NULL);
		switch(which)
		{
			case MOUSEENABLE:
				mouseenabled^=1;
				DrawCtlScreen();
				CusItems.curpos=-1;
				ShootSnd();
				break;

			case JOYENABLE:
				joystickenabled^=1;
				DrawCtlScreen();
				CusItems.curpos=-1;
				ShootSnd();
				break;
			case USEPORT2:
				joystickport^=1;
				DrawCtlScreen();
				ShootSnd();
				break;

			case PADENABLE:
				joypadenabled^=1;
				DrawCtlScreen();
				ShootSnd();
				break;

			case MOUSESENS:
			case CUSTOMIZE:
				DrawCtlScreen();
				if (w0 == true || w1 == true){
					MenuFadeInWL6();
				} else {
					MenuFadeInSOD();
				}
				WaitKeyUp();
				break;
		}
	} while(which>=0);

	if (w0 == true || w1 == true){
		MenuFadeOutWL6();
	} else {
		MenuFadeOutSOD();
	}

	if (s0 == true || s1 == true || s2 == true || s3 == true){
		UnCacheLump (CONTROL_LUMP_START,CONTROL_LUMP_END);
		CacheLump (OPTIONS_LUMP_START,OPTIONS_LUMP_END);
	}
}


////////////////////////////////
//
// DRAW MOUSE SENSITIVITY SCREEN
//
void DrawMouseSens()
{
	ClearMScreen();
	VWB_DrawPic(112,184,C_MOUSELBACKPIC);
	if (w0 == true || w1 == true){
		DrawWindow(10,80,300,30,BKGDCOLORWL6);
	} else {
		DrawWindow(10,80,300,30,BKGDCOLORSOD);
	}

	WindowX=0;
	WindowW=320;
	PrintY=82;
	if (w0 == true || w1 == true){
		SETFONTCOLOR(READCOLOR,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(READCOLOR,BKGDCOLORSOD);
	}
	US_CPrint(STR_MOUSEADJ);

	if (w0 == true || w1 == true){
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORSOD);
	}
	PrintX=14;
	PrintY=95;
	US_Print(STR_SLOW);
	PrintX=269;
	US_Print(STR_FAST);

	VW_Bar(60,97,200,10,TEXTCOLOR);
	DrawOutline(60,97,200,10,0,HIGHLIGHT);
	DrawOutline(60+20*mouseadjustment,97,20,10,0,READCOLOR);
	VW_Bar(61+20*mouseadjustment,98,19,9,READHCOLOR);

	VW_UpdateScreen();
	if (w0 == true || w1 == true){
		MenuFadeInWL6();
	} else {
		MenuFadeInSOD();
	}
}


///////////////////////////
//
// ADJUST MOUSE SENSITIVITY
//
void MouseSensitivity()
{
	ControlInfo ci;
	int exit=0,oldMA;


	oldMA=mouseadjustment;
	DrawMouseSens();
	do
	{
		ReadAnyControl(&ci);
		switch(ci.dir)
		{
			case dir_North:
			case dir_West:
				if (mouseadjustment)
				{
					mouseadjustment--;
					VW_Bar(60,97,200,10,TEXTCOLOR);
					DrawOutline(60,97,200,10,0,HIGHLIGHT);
					DrawOutline(60+20*mouseadjustment,97,20,10,0,READCOLOR);
					VW_Bar(61+20*mouseadjustment,98,19,9,READHCOLOR);
					VW_UpdateScreen();
					 if (w0 == true || w1 == true){
						 SD_PlaySoundWL6(MOVEGUN1SNDWL6);
					 } else {
						 SD_PlaySoundSOD(MOVEGUN1SNDSOD);
					 }
					
					while(IN_KeyDown(sc_LeftArrow)) IN_CheckAck();
					WaitKeyUp();
				}
				break;

			case dir_South:
			case dir_East:
				if (mouseadjustment<9)
				{
					mouseadjustment++;
					VW_Bar(60,97,200,10,TEXTCOLOR);
					DrawOutline(60,97,200,10,0,HIGHLIGHT);
					DrawOutline(60+20*mouseadjustment,97,20,10,0,READCOLOR);
					VW_Bar(61+20*mouseadjustment,98,19,9,READHCOLOR);
					VW_UpdateScreen();
					 if (w0 == true || w1 == true){
						 SD_PlaySoundWL6(MOVEGUN1SNDWL6);
					 } else {
						 SD_PlaySoundSOD(MOVEGUN1SNDSOD);
					 }
					
					while(IN_KeyDown(sc_RightArrow)) IN_CheckAck();
					WaitKeyUp();
				}
				break;
			default: /* ignore */
				break;
		}

		if (IN_KeyDown(sc_Tab) && IN_KeyDown(sc_P) && MS_CheckParm("debugmode"))
			PicturePause();

		if (ci.button0 || IN_KeyDown(sc_Space) || IN_KeyDown(sc_Enter))
			exit = 1;
		else if (ci.button1 || IN_KeyDown(sc_Escape))
			exit = 2;

	} while(!exit);

	if (exit == 2) {
		mouseadjustment = oldMA;
		 if (w0 == true || w1 == true){
			 SD_PlaySoundWL6(ESCPRESSEDSNDWL6);
		 } else {
			 SD_PlaySoundSOD(ESCPRESSEDSNDSOD);
		 }
	} else
		if (w0 == true || w1 == true){
			SD_PlaySoundWL6(SHOOTSNDWL6);
		} else {
			SD_PlaySoundSOD(SHOOTSNDSOD);
		}

	WaitKeyUp();
	if (w0 == true || w1 == true){
		MenuFadeOutWL6();
	} else {
		MenuFadeOutSOD();
	}
}


///////////////////////////
//
// DRAW CONTROL MENU SCREEN
//
void DrawCtlScreen()
{
	int i, x, y;

	ClearMScreen();
	DrawStripes(10);
	VWB_DrawPic(80,0,C_CONTROLPIC);
	VWB_DrawPic(112,184,C_MOUSELBACKPIC);
	if (w0 == true || w1 == true){
		DrawWindow(CTL_X-8,CTL_Y-5,CTL_W,CTL_H,BKGDCOLORWL6);
	} else {
		DrawWindow(CTL_X-8,CTL_Y-5,CTL_W,CTL_H,BKGDCOLORSOD);
	}

 WindowX=0;
 WindowW=320;
 if (w0 == true || w1 == true){
 	SETFONTCOLOR(TEXTCOLOR,BKGDCOLORWL6);
 } else {
	SETFONTCOLOR(TEXTCOLOR,BKGDCOLORSOD);
 }

 if (JoysPresent[0])
   CtlMenu[1].active=
   CtlMenu[2].active=
   CtlMenu[3].active=1;

 CtlMenu[2].active=CtlMenu[3].active=joystickenabled;

 if (MousePresent)
 {
  CtlMenu[4].active=
  CtlMenu[0].active=1;
 }

 CtlMenu[4].active=mouseenabled;


 DrawMenu(&CtlItems,&CtlMenu[0]);


 x=CTL_X+CtlItems.indent-24;
 y=CTL_Y+3;
 if (mouseenabled)
   VWB_DrawPic(x,y,C_SELECTEDPIC);
 else
   VWB_DrawPic(x,y,C_NOTSELECTEDPIC);

 y=CTL_Y+16;
 if (joystickenabled)
   VWB_DrawPic(x,y,C_SELECTEDPIC);
 else
   VWB_DrawPic(x,y,C_NOTSELECTEDPIC);

 y=CTL_Y+29;
 if (joystickport)
   VWB_DrawPic(x,y,C_SELECTEDPIC);
 else
   VWB_DrawPic(x,y,C_NOTSELECTEDPIC);

 y=CTL_Y+42;
 if (joypadenabled)
   VWB_DrawPic(x,y,C_SELECTEDPIC);
 else
   VWB_DrawPic(x,y,C_NOTSELECTEDPIC);

 //
 // PICK FIRST AVAILABLE SPOT
 //
 if (CtlItems.curpos<0 || !CtlMenu[CtlItems.curpos].active)
   for (i=0;i<6;i++)
	 if (CtlMenu[i].active)
	 {
	  CtlItems.curpos=i;
	  break;
	 }

 DrawMenuGun(&CtlItems);
 VW_UpdateScreen();
}


////////////////////////////////////////////////////////////////////
//
// CUSTOMIZE CONTROLS
//
////////////////////////////////////////////////////////////////////
enum {FIRE,STRAFE,RUN,OPEN};
char mbarray[4][3]={"b0","b1","b2","b3"};
int order[4]={RUN,OPEN,FIRE,STRAFE};

void CustomControls(void)
{
 int which;


 DrawCustomScreen();
 do
 {
  which=HandleMenu(&CusItems,&CusMenu[0],FixupCustom);
  switch(which)
  {
   case 0:
	 DefineMouseBtns();
	 DrawCustMouse(1);
	 break;
   case 3:
	 DefineJoyBtns();
	 DrawCustJoy(0);
	 break;
   case 6:
	 DefineKeyBtns();
	 DrawCustKeybd(0);
	 break;
   case 8:
	 DefineKeyMove();
	 DrawCustKeys(0);
  }
 } while(which>=0);



 if (w0 == true || w1 == true){
	MenuFadeOutWL6();
 } else {
	MenuFadeOutSOD();
 }
}


////////////////////////
//
// DEFINE THE MOUSE BUTTONS
//
void DefineMouseBtns(void)
{
 CustomCtrls mouseallowed={ {0,1,1,1} };
 EnterCtrlData(2,&mouseallowed,DrawCustMouse,PrintCustMouse,MOUSE);
}


////////////////////////
//
// DEFINE THE JOYSTICK BUTTONS
//
void DefineJoyBtns(void)
{
 CustomCtrls joyallowed={ {1,1,1,1} };
 EnterCtrlData(5,&joyallowed,DrawCustJoy,PrintCustJoy,JOYSTICK);
}


////////////////////////
//
// DEFINE THE KEYBOARD BUTTONS
//
void DefineKeyBtns(void)
{
 CustomCtrls keyallowed={ {1,1,1,1} };
 EnterCtrlData(8,&keyallowed,DrawCustKeybd,PrintCustKeybd,KEYBOARDBTNS);
}


////////////////////////
//
// DEFINE THE KEYBOARD BUTTONS
//
void DefineKeyMove(void)
{
	CustomCtrls keyallowed={ {1,1,1,1} };
	EnterCtrlData(10,&keyallowed,DrawCustKeys,PrintCustKeys,KEYBOARDMOVE);
}


////////////////////////
//
// ENTER CONTROL DATA FOR ANY TYPE OF CONTROL
//
enum {FWRD,RIGHT,BKWD,LEFT};
int moveorder[4]={LEFT,RIGHT,FWRD,BKWD};

void EnterCtrlData(int index,CustomCtrls *cust,void (*DrawRtn)(int),void (*PrintRtn)(int),int type)
{
 int j,exit,tick,redraw,which = 0,x = 0,picked;
 ControlInfo ci;

 ShootSnd();
 PrintY=CST_Y+13*index;
 IN_ClearKeysDown();
 exit=0;
 redraw=1;
 //
 // FIND FIRST SPOT IN ALLOWED ARRAY
 //
 for (j=0;j<4;j++)
   if (cust->allowed[j])
   {
	which=j;
	break;
   }

 do
 {
  if (redraw)
  {
   x=CST_START+CST_SPC*which;
   if (w0 == true || w1 == true){
    DrawWindow(5,PrintY-1,310,13,BKGDCOLORWL6);
   } else {
    DrawWindow(5,PrintY-1,310,13,BKGDCOLORSOD);
   }

   DrawRtn(1);
   DrawWindow(x-2,PrintY,CST_SPC,11,TEXTCOLOR);
   DrawOutline(x-2,PrintY,CST_SPC,11,0,HIGHLIGHT);
   SETFONTCOLOR(0,TEXTCOLOR);
   PrintRtn(which);
   PrintX=x;
   if (w0 == true || w1 == true){
 	SETFONTCOLOR(TEXTCOLOR,BKGDCOLORWL6);
   } else {
	SETFONTCOLOR(TEXTCOLOR,BKGDCOLORSOD);
   }
   VW_UpdateScreen();
   WaitKeyUp();
   redraw=0;
  }

  ReadAnyControl(&ci);

	if (type==MOUSE || type==JOYSTICK)
		if (IN_KeyDown(sc_Enter) || IN_KeyDown(sc_Control) || IN_KeyDown(sc_Alt))
		{
			IN_ClearKeysDown();
			ci.button0=ci.button1=false;
		}

  //
  // CHANGE BUTTON VALUE?
  //
  if ((ci.button0|ci.button1|ci.button2|ci.button3)||
	  ((type==KEYBOARDBTNS||type==KEYBOARDMOVE) && LastScan==sc_Enter))
  {
   tick = picked = 0;
   set_TimeCount(0);
   
   SETFONTCOLOR(0,TEXTCOLOR);

   do
   {
	int button,result=0;

	if (type==KEYBOARDBTNS||type==KEYBOARDMOVE)
	  IN_ClearKeysDown();

	IN_CheckAck(); /* force update */
	
	//
	// FLASH CURSOR
	//
	if (get_TimeCount() >10)
	{
	 switch(tick)
	 {
	  case 0:
	VW_Bar(x,PrintY+1,CST_SPC-2,10,TEXTCOLOR);
	break;
	  case 1:
	PrintX=x;
	US_Print("?");
	if (w0 == true || w1 == true){
		SD_PlaySoundWL6(HITWALLSNDWL6);
	} else {
		SD_PlaySoundSOD(HITWALLSNDSOD);
	}
	 }
	 tick^=1;
	 set_TimeCount(0);
	 VW_UpdateScreen();
	}

	//
	// WHICH TYPE OF INPUT DO WE PROCESS?
	//
	switch(type)
	{
	 case MOUSE:
	 	button = IN_MouseButtons();
	   switch(button)
	   {
	case 1: result=1; break;
	case 2: result=2; break;
	case 4: result=3; break;
	   }

	   if (result)
	   {
	int z;


	for (z=0;z<4;z++)
	  if (order[which]==buttonmouse[z])
	  {
	   buttonmouse[z]=bt_nobutton;
	   break;
	  }

	buttonmouse[result-1]=order[which];
	picked=1;
	if (w0 == true || w1 == true){
		SD_PlaySoundWL6(SHOOTDOORSNDWL6);
	} else {
		SD_PlaySoundSOD(SHOOTDOORSNDSOD);
	}
	   }
	   break;

	 case JOYSTICK:
	   if (ci.button0) result=1;
	   else
	   if (ci.button1) result=2;
	   else
	   if (ci.button2) result=3;
	   else
	   if (ci.button3) result=4;

	   if (result)
	   {
	int z;


	for (z=0;z<4;z++)
	  if (order[which]==buttonjoy[z])
	  {
	   buttonjoy[z]=bt_nobutton;
	   break;
	  }

	buttonjoy[result-1]=order[which];
	picked=1;
	if (w0 == true || w1 == true){
		SD_PlaySoundWL6(SHOOTDOORSNDWL6);
	} else {
		SD_PlaySoundSOD(SHOOTDOORSNDSOD);
	}
	   }
	   break;

	 case KEYBOARDBTNS:
	   if (LastScan)
	   {
	buttonscan[order[which]]=LastScan;
	picked=1;
	ShootSnd();
	IN_ClearKeysDown();
	   }
	   break;

	 case KEYBOARDMOVE:
	   if (LastScan)
	   {
	dirscan[moveorder[which]]=LastScan;
	picked=1;
	ShootSnd();
	IN_ClearKeysDown();
	   }
	   break;
	}

	//
	// EXIT INPUT?
	//
	if (IN_KeyDown(sc_Escape))
	{
	 picked=1;
	 continue;
	}

   } while(!picked);

   if (w0 == true || w1 == true){
 	SETFONTCOLOR(TEXTCOLOR,BKGDCOLORWL6);
   } else {
	SETFONTCOLOR(TEXTCOLOR,BKGDCOLORSOD);
   }
   redraw=1;
   WaitKeyUp();
   continue;
  }

  if (ci.button1 || IN_KeyDown(sc_Escape))
	exit=1;

  //
  // MOVE TO ANOTHER SPOT?
  //
  switch(ci.dir)
  {
   case dir_West:
	 do
	 {
	  which--;
	  if (which<0)
	which=3;
	 } while(!cust->allowed[which]);
	 redraw=1;
	 if (w0 == true || w1 == true){
		 SD_PlaySoundWL6(MOVEGUN1SNDWL6);
	 } else {
		 SD_PlaySoundSOD(MOVEGUN1SNDSOD);
	 }
	 while(ReadAnyControl(&ci),ci.dir!=dir_None);
	 IN_ClearKeysDown();
	 break;

   case dir_East:
	 do
	 {
	  which++;
	  if (which>3)
	which=0;
	 } while(!cust->allowed[which]);
	 redraw=1;
	 if (w0 == true || w1 == true){
		 SD_PlaySoundWL6(MOVEGUN1SNDWL6);
	 } else {
		 SD_PlaySoundSOD(MOVEGUN1SNDSOD);
	 }
	 while(ReadAnyControl(&ci),ci.dir!=dir_None);
	 IN_ClearKeysDown();
	 break;
   case dir_North:
   case dir_South:
	 exit=1;
   default:
   	break;
  }
 } while(!exit);

 if (w0 == true || w1 == true){
	 SD_PlaySoundWL6(ESCPRESSEDSNDWL6);
 } else {
	 SD_PlaySoundSOD(ESCPRESSEDSNDSOD);
 }
 WaitKeyUp();

 if (w0 == true || w1 == true){
 	 DrawWindow(5,PrintY-1,310,13,BKGDCOLORWL6);
 } else {
 	 DrawWindow(5,PrintY-1,310,13,BKGDCOLORSOD);
 }
}


////////////////////////
//
// FIXUP GUN CURSOR OVERDRAW SHIT
//
void FixupCustom(int w)
{
	static int lastwhich=-1;
	int y=CST_Y+26+w*13;


	if (w0 == true || w1 == true){
		VW_Hlin(7,32,y-1,DEACTIVEWL6);
		VW_Hlin(7,32,y+12,BORD2COLORWL6);
		VW_Hlin(7,32,y-2,BORDCOLORWL6);
		VW_Hlin(7,32,y+13,BORDCOLORWL6);
	} else {
		VW_Hlin(7,32,y-1,DEACTIVESOD);
		VW_Hlin(7,32,y+12,BORD2COLORSOD);
		VW_Hlin(7,32,y-2,BORD2COLORSOD);
		VW_Hlin(7,32,y+13,BORD2COLORSOD);
	}

	switch(w)
	{
		case 0: DrawCustMouse(1); break;
		case 3: DrawCustJoy(1); break;
		case 6: DrawCustKeybd(1); break;
		case 8: DrawCustKeys(1);
	}


	if (lastwhich>=0)
	{
		y=CST_Y+26+lastwhich*13;
		if (w0 == true || w1 == true){
			VW_Hlin(7,32,y-1,DEACTIVEWL6);
			VW_Hlin(7,32,y+12,BORD2COLORWL6);
			VW_Hlin(7,32,y-2,BORDCOLORWL6);
			VW_Hlin(7,32,y+13,BORDCOLORWL6);
		} else {
			VW_Hlin(7,32,y-1,DEACTIVESOD);
			VW_Hlin(7,32,y+12,BORD2COLORSOD);
			VW_Hlin(7,32,y-2,BORD2COLORSOD);
			VW_Hlin(7,32,y+13,BORD2COLORSOD);
		}

		if (lastwhich!=w)
			switch(lastwhich)
			{
				case 0: DrawCustMouse(0); break;
				case 3: DrawCustJoy(0); break;
				case 6: DrawCustKeybd(0); break;
				case 8: DrawCustKeys(0);
			}
	}

	lastwhich=w;
}


////////////////////////
//
// DRAW CUSTOMIZE SCREEN
//
void DrawCustomScreen(void)
{
	int i;

	ClearMScreen();
	WindowX=0;
	WindowW=320;
	VWB_DrawPic(112,184,C_MOUSELBACKPIC);
	DrawStripes(10);
	VWB_DrawPic(80,0,C_CUSTOMIZEPIC);

	//
	// MOUSE
	//
	if (w0 == true || w1 == true){
		SETFONTCOLOR(READCOLOR,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(READCOLOR,BKGDCOLORSOD);
	}
	WindowX=0;
	WindowW=320;

	if (w0 == true || w1 == true){
		PrintY=CST_Y;
		US_CPrint("Mouse\n");
	} else {
		PrintY = CST_Y+13;
		VWB_DrawPic (128,48,C_MOUSEPIC);
	}

	if (w0 == true || w1 == true){
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORSOD);
	}
	PrintX=CST_START;
	US_Print(STR_CRUN);
	PrintX=CST_START+CST_SPC*1;
	US_Print(STR_COPEN);
	PrintX=CST_START+CST_SPC*2;
	US_Print(STR_CFIRE);
	PrintX=CST_START+CST_SPC*3;
	US_Print(STR_CSTRAFE"\n");

	if (w0 == true || w1 == true){
		DrawWindow(5,PrintY-1,310,13,BKGDCOLORWL6);
	} else {
		DrawWindow(5,PrintY-1,310,13,BKGDCOLORSOD);
	}
	DrawCustMouse(0);
	US_Print("\n");


	//
	// JOYSTICK/PAD
	//
	if (w0 == true || w1 == true){
		SETFONTCOLOR(READCOLOR,BKGDCOLORWL6);
		US_CPrint("Joystick/Gravis GamePad\n");
	} else {
		PrintY += 13;
		VWB_DrawPic (40,88,C_JOYSTICKPIC);
	}

	if (s0 == true || s1 == true || s2 == true || s3 == true){
		VWB_DrawPic (112,120,C_KEYBOARDPIC);
	}

	if (w0 == true || w1 == true){
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORSOD);
	}
	PrintX=CST_START;
	US_Print(STR_CRUN);
	PrintX=CST_START+CST_SPC*1;
	US_Print(STR_COPEN);
	PrintX=CST_START+CST_SPC*2;
	US_Print(STR_CFIRE);
	PrintX=CST_START+CST_SPC*3;
	US_Print(STR_CSTRAFE"\n");

	if (w0 == true || w1 == true){
		DrawWindow(5,PrintY-1,310,13,BKGDCOLORWL6);
	} else {
		DrawWindow(5,PrintY-1,310,13,BKGDCOLORSOD);
	}
	DrawCustJoy(0);
	US_Print("\n");


	//
	// KEYBOARD
	//
	if (w0 == true || w1 == true){
		SETFONTCOLOR(READCOLOR,BKGDCOLORWL6);
		US_CPrint("Keyboard\n");
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORWL6);
	} else {
		PrintY += 13;
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORSOD);
	}
	PrintX=CST_START;
	US_Print(STR_CRUN);
	PrintX=CST_START+CST_SPC*1;
	US_Print(STR_COPEN);
	PrintX=CST_START+CST_SPC*2;
	US_Print(STR_CFIRE);
	PrintX=CST_START+CST_SPC*3;
	US_Print(STR_CSTRAFE"\n");

	if (w0 == true || w1 == true){
		DrawWindow(5,PrintY-1,310,13,BKGDCOLORWL6);
	} else {
		DrawWindow(5,PrintY-1,310,13,BKGDCOLORSOD);
	}
	DrawCustKeybd(0);
	US_Print("\n");


	//
	// KEYBOARD MOVE KEYS
	//
	if (w0 == true || w1 == true){
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORSOD);
	}
	PrintX=CST_START;
	US_Print(STR_LEFT);
	PrintX=CST_START+CST_SPC*1;
	US_Print(STR_RIGHT);
	PrintX=CST_START+CST_SPC*2;
	US_Print(STR_FRWD);
	PrintX=CST_START+CST_SPC*3;
	US_Print(STR_BKWD"\n");

	if (w0 == true || w1 == true){
		DrawWindow(5,PrintY-1,310,13,BKGDCOLORWL6);
	} else {
		DrawWindow(5,PrintY-1,310,13,BKGDCOLORSOD);
	}
	DrawCustKeys(0);

	//
	// PICK STARTING POINT IN MENU
	//
	if (CusItems.curpos<0)
		for (i=0;i<CusItems.amount;i++)
			if (CusMenu[i].active)
			{
				CusItems.curpos=i;
				break;
			}


	VW_UpdateScreen();
	if (w0 == true || w1 == true){
		MenuFadeInWL6();
	} else {
		MenuFadeInSOD();
	}
}


void PrintCustMouse(int i)
{
	int j;

	for (j=0;j<4;j++)
		if (order[i]==buttonmouse[j])
		{
			PrintX=CST_START+CST_SPC*i;
			US_Print(mbarray[j]);
			break;
		}
}

void DrawCustMouse(int hilight)
{
	int i,color;


	color=TEXTCOLOR;
	if (hilight)
		color=HIGHLIGHT;

	if (w0 == true || w1 == true){
		SETFONTCOLOR(color,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(color,BKGDCOLORSOD);
	}

	if (!mouseenabled)
	{
		if (w0 == true || w1 == true){
			SETFONTCOLOR(DEACTIVEWL6,BKGDCOLORWL6);
		} else {
			SETFONTCOLOR(DEACTIVESOD,BKGDCOLORSOD);
		}
		CusMenu[0].active=0;
	}
	else
		CusMenu[0].active=1;

	PrintY=CST_Y+13*2;
	for (i=0;i<4;i++)
		PrintCustMouse(i);
}

void PrintCustJoy(int i)
{
	int j;

	for (j=0;j<4;j++)
		if (order[i]==buttonjoy[j])
		{
			PrintX=CST_START+CST_SPC*i;
			US_Print(mbarray[j]);
			break;
		}
}

void DrawCustJoy(int hilight)
{
	int i,color;


	color=TEXTCOLOR;
	if (hilight)
		color=HIGHLIGHT;
		if (w0 == true || w1 == true){
			SETFONTCOLOR(DEACTIVEWL6,BKGDCOLORWL6);
		} else {
			SETFONTCOLOR(DEACTIVESOD,BKGDCOLORSOD);
		}

	if (!joystickenabled)
	{
		if (w0 == true || w1 == true){
			SETFONTCOLOR(DEACTIVEWL6,BKGDCOLORWL6);
		} else {
			SETFONTCOLOR(DEACTIVESOD,BKGDCOLORSOD);
		}
		CusMenu[3].active=0;
	}
	else
		CusMenu[3].active=1;

	PrintY=CST_Y+13*5;
	for (i=0;i<4;i++)
		PrintCustJoy(i);
}


void PrintCustKeybd(int i)
{
	PrintX=CST_START+CST_SPC*i;
	US_Print(IN_GetScanName(buttonscan[order[i]]));
}

void DrawCustKeybd(int hilight)
{
	int i,color;


	color=TEXTCOLOR;
	if (hilight)
		color=HIGHLIGHT;
	if (w0 == true || w1 == true){
		SETFONTCOLOR(color,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(color,BKGDCOLORSOD);
	}

	PrintY=CST_Y+13*8;
	for (i=0;i<4;i++)
		PrintCustKeybd(i);
}

void PrintCustKeys(int i)
{
	PrintX=CST_START+CST_SPC*i;
	US_Print(IN_GetScanName(dirscan[moveorder[i]]));
}

void DrawCustKeys(int hilight)
{
	int i,color;


	color=TEXTCOLOR;
	if (hilight)
		color=HIGHLIGHT;
	if (w0 == true || w1 == true){
		SETFONTCOLOR(color,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(color,BKGDCOLORSOD);
	}

	PrintY=CST_Y+13*10;
	for (i=0;i<4;i++)
		PrintCustKeys(i);
}


////////////////////////////////////////////////////////////////////
//
// CHANGE SCREEN VIEWING SIZE
//
////////////////////////////////////////////////////////////////////
void CP_ChangeView()
{
	int exit = 0, oldview, newview;
	ControlInfo ci;

	WindowX=WindowY=0;
	WindowW=320;
	WindowH=200;
	newview=oldview=viewsize;
	DrawChangeView(oldview);

	do {
		CheckPause();
		ReadAnyControl(&ci);
		switch(ci.dir) {
		case dir_South:
		case dir_West:
			newview--;
			if (newview<4)
				newview=4;
			ShowViewSize(newview);
			VW_UpdateScreen();
			if (w0 == true || w1 == true){
				SD_PlaySoundWL6(HITWALLSNDWL6);
			} else {
				SD_PlaySoundSOD(HITWALLSNDSOD);
			}
			TicDelay(10);
			break;

		case dir_North:
		case dir_East:
			newview++;
			if (newview>20)
				newview=20;
			ShowViewSize(newview);
			VW_UpdateScreen();
			if (w0 == true || w1 == true){
				SD_PlaySoundWL6(HITWALLSNDWL6);
			} else {
				SD_PlaySoundSOD(HITWALLSNDSOD);
			}
			TicDelay(10);
			break;
		default:
			break;
		}

		if (IN_KeyDown(sc_Tab) && IN_KeyDown(sc_P) && MS_CheckParm("debugmode"))
			PicturePause();

		if (ci.button0 || IN_KeyDown(sc_Enter))
			exit = 1;
		else if (ci.button1 || IN_KeyDown(sc_Escape)) {
			if (w0 == true || w1 == true){
				SD_PlaySoundWL6(ESCPRESSEDSNDWL6);
				MenuFadeOutWL6();
			} else {
				SD_PlaySoundSOD(ESCPRESSEDSNDSOD);
				MenuFadeOutSOD();
			}
			return;
		}
	} while(!exit);

	if (oldview != newview) {
		if (w0 == true || w1 == true){
			SD_PlaySoundWL6(SHOOTSNDWL6);
		} else {
			SD_PlaySoundSOD(SHOOTSNDSOD);
		}
		Message(STR_THINK"...");
		NewViewSize(newview);
	}

	ShootSnd();
	if (w0 == true || w1 == true){
		MenuFadeOutWL6();
	} else {
		MenuFadeOutSOD();
	}
}


/////////////////////////////
//
// DRAW THE CHANGEVIEW SCREEN
//
void DrawChangeView(int view)
{
	VW_Bar(0,160,320,40,VIEWCOLOR);
	ShowViewSize(view);

	PrintY=161;
	WindowX=0;
	WindowY=320;
	if (w0 == true || w1 == true){
		SETFONTCOLOR(HIGHLIGHT,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(HIGHLIGHT,BKGDCOLORSOD);
	}

	US_CPrint(STR_SIZE1"\n");
	US_CPrint(STR_SIZE2"\n");
	US_CPrint(STR_SIZE3);

	VW_UpdateScreen();

	if (w0 == true || w1 == true){
		MenuFadeInWL6();
	} else {
		MenuFadeInSOD();
	}
}


////////////////////////////////////////////////////////////////////
//
// QUIT THIS INFERNAL GAME!
//
////////////////////////////////////////////////////////////////////
void CP_Quit()
{
	if (w0 == true || w1 == true){
		if (Confirm(endStringsWL6[(US_RndT()&0x7)+(US_RndT()&1)]))
		{
			VW_UpdateScreen();
			SD_MusicOff();
			SD_StopSound();
			MenuFadeOutWL6();

			Quit(NULL);
		}
	} else {
		if (Confirm(endStringsSOD[(US_RndT()&0x7)+(US_RndT()&1)]))
		{
			VW_UpdateScreen();
			SD_MusicOff();
			SD_StopSound();
			MenuFadeOutSOD();

			Quit(NULL);
		}
	}

	DrawMainMenu();
}

////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////
//
// SUPPORT ROUTINES
//
////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////
//
// Clear Menu screens to dark red
//
////////////////////////////////////////////////////////////////////
void ClearMScreen(void)
{
	if (w0 == true || w1 == true){
		VW_Bar(0,0,320,200,BORDCOLORWL6);
	} else {
		VWB_DrawPic(0,0,C_BACKDROPPIC);
	}
}


////////////////////////////////////////////////////////////////////
//
// Un/Cache a LUMP of graphics
//
////////////////////////////////////////////////////////////////////
void CacheLump(int lumpstart, int lumpend)
{
	int i;

	for (i=lumpstart;i<=lumpend;i++)
		CA_CacheGrChunk(i);
}


void UnCacheLump(int lumpstart, int lumpend)
{
	int i;

	for (i=lumpstart;i<=lumpend;i++)
		CA_UnCacheGrChunk(i);
}


////////////////////////////////////////////////////////////////////
//
// Draw a window for a menu
//
////////////////////////////////////////////////////////////////////
void DrawWindow(int x,int y,int w,int h,int wcolor)
{
	VW_Bar(x,y,w,h,wcolor);
	if (w0 == true || w1 == true){
		DrawOutline(x,y,w,h,BORD2COLORWL6,DEACTIVEWL6);
	} else {
		DrawOutline(x,y,w,h,BORD2COLORSOD,DEACTIVESOD);
	}
}

void DrawOutline(int x,int y,int w,int h,int color1,int color2)
{
	VW_Hlin(x,x+w,y,color2);
	VW_Vlin(y,y+h,x,color2);
	VW_Hlin(x,x+w,y+h,color1);
	VW_Vlin(y,y+h,x+w,color1);
}


////////////////////////////////////////////////////////////////////
//
// Setup Control Panel stuff - graphics, etc.
//
////////////////////////////////////////////////////////////////////
void SetupControlPanel()
{
	int which;

	//
	// CACHE GRAPHICS & SOUNDS
	//
	CA_CacheGrChunk(STARTFONT+1);
	if (w0 == true){
		CacheLump(CONTROLS_LUMP_STARTWL1, CONTROLS_LUMP_ENDWL1);
	} else if (w1 == true){
		CacheLump(CONTROLS_LUMP_STARTWL6, CONTROLS_LUMP_ENDWL6);
	} else {
		CacheLump(BACKDROP_LUMP_START, BACKDROP_LUMP_END);
	}

	if (w0 == true || w1 == true){
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORSOD);
	}
	fontnumber=1;
	WindowH=200;

	if (!ingame)
		CA_LoadAllSounds();
	else
		MainMenu[savegame].active=1;

	//
	// SEE WHICH SAVE GAME FILES ARE AVAILABLE & READ STRING IN
	//
{
#if defined(HAVE_FFBLK)
	struct ffblk f;
	
	if (!findfirst(SaveNaame,&f,0))
		do
		{
			which=f.ff_name[7]-'0';
			if (which<10)
			{
				char temp[32];

				if (ReadSaveTag(f.ff_name, temp) != -1) {
					SaveGamesAvail[which]=1;
					strcpy(&SaveGameNames[which][0],temp);
				}
			}
		} while(!findnext(&f));
#elif defined(HAVE_FINDDATA)
	struct _finddata_t f;
	long hand;

	if ((hand = _findfirst(SaveName, &f)) != -1)
		do
		{
			which=f.name[7]-'0';
			if (which<10)
			{
				char temp[32];
				
				if (ReadSaveTag(f.name, temp) != -1) {
					SaveGamesAvail[which]=1;
					strcpy(&SaveGameNames[which][0],temp);
				}
			}
		} while(_findnext(hand, &f) != -1);
#else

/*
	glob_t globbuf;
	int x;
	
	if (glob(SaveName, 0, NULL, &globbuf))
		return;
	
	for (x = 0; x < globbuf.gl_pathc; x++) {
		which = globbuf.gl_pathv[x][7] - '0';
		if (which < 10)	{
			char temp[32];
			
			if (ReadSaveTag(globbuf.gl_pathv[x], temp) != -1) {
				SaveGamesAvail[which]=1;
				strcpy(&SaveGameNames[which][0],temp);
			}
		}
	}

	globfree(&globbuf);
*/
#endif
}

}


////////////////////////////////////////////////////////////////////
//
// Clean up all the Control Panel stuff
//
////////////////////////////////////////////////////////////////////
void CleanupControlPanel()
{
	if (w0 == true){
		UnCacheLump(CONTROLS_LUMP_STARTWL1, CONTROLS_LUMP_ENDWL1);
	} else if (w1 == true){
		UnCacheLump(CONTROLS_LUMP_STARTWL6, CONTROLS_LUMP_ENDWL6);
	} else {
		UnCacheLump(BACKDROP_LUMP_START, BACKDROP_LUMP_END);
	}

	fontnumber = 0;
}


////////////////////////////////////////////////////////////////////
//
// Handle moving gun around a menu
//
////////////////////////////////////////////////////////////////////
int HandleMenu(CP_iteminfo *item_i,CP_itemtype *items,void (*routine)(int w))
{
	char key;
	static int redrawitem=1,lastitem=-1;
	int i,x,y,basey,exit,which,shape,timer;
	ControlInfo ci;


	which=item_i->curpos;
	x=item_i->x&-8;
	basey=item_i->y-2;
	y=basey+which*13;

	VWB_DrawPic(x,y,C_CURSOR1PIC);
	SetMenuTextColor(items+which,1);
	if (redrawitem)
	{
		PrintX=item_i->x+item_i->indent;
		PrintY=item_i->y+which*13;
		US_Print((items+which)->string);
	}
	//
	// CALL CUSTOM ROUTINE IF IT IS NEEDED
	//
	if (routine)
		routine(which);
	VW_UpdateScreen();

	shape=C_CURSOR1PIC;
	timer=8;
	exit=0;
	set_TimeCount(0);
	IN_ClearKeysDown();


	do
	{
		//
		// CHANGE GUN SHAPE
		//
		if (get_TimeCount() > timer)
		{
			set_TimeCount(0);
			if (shape==C_CURSOR1PIC)
			{
				shape=C_CURSOR2PIC;
				timer=8;
			}
			else
			{
				shape=C_CURSOR1PIC;
				timer=70;
			}
			VWB_DrawPic(x,y,shape);
			if (routine)
				routine(which);
			VW_UpdateScreen();
		}

		CheckPause();

		//
		// SEE IF ANY KEYS ARE PRESSED FOR INITIAL CHAR FINDING
		//
		key=LastASCII;
		if (key)
		{
			int ok=0;

			//
			// CHECK FOR SCREEN CAPTURE
			//
			if (IN_KeyDown(sc_Tab) && IN_KeyDown(sc_P) && MS_CheckParm("debugmode"))
				PicturePause();


			if (key>='a')
				key-='a'-'A';

			for (i=which+1;i<item_i->amount;i++)
				if ((items+i)->active && (items+i)->string[0]==key)
				{
					EraseGun(item_i,items,x,y,which);
					which=i;
					DrawGun(item_i,items,x,&y,which,basey,routine);
					ok=1;
					IN_ClearKeysDown();
					break;
				}

			//
			// DIDN'T FIND A MATCH FIRST TIME THRU. CHECK AGAIN.
			//
			if (!ok)
			{
				for (i=0;i<which;i++)
					if ((items+i)->active && (items+i)->string[0]==key)
					{
						EraseGun(item_i,items,x,y,which);
						which=i;
						DrawGun(item_i,items,x,&y,which,basey,routine);
						IN_ClearKeysDown();
						break;
					}
			}
		}

		//
		// GET INPUT
		//
		ReadAnyControl(&ci);
		switch(ci.dir)
		{
			////////////////////////////////////////////////
			//
			// MOVE UP
			//
			case dir_North:

			EraseGun(item_i,items,x,y,which);

			//
			// ANIMATE HALF-STEP
			//
			if (which && (items+which-1)->active)
			{
				y-=6;
				DrawHalfStep(x,y);
			}

			//
			// MOVE TO NEXT AVAILABLE SPOT
			//
			do
			{
				if (!which)
					which=item_i->amount-1;
				else
					which--;
			} while(!(items+which)->active);

			DrawGun(item_i,items,x,&y,which,basey,routine);
			//
			// WAIT FOR BUTTON-UP OR DELAY NEXT MOVE
			//
			TicDelay(20);
			break;

			////////////////////////////////////////////////
			//
			// MOVE DOWN
			//
			case dir_South:

			EraseGun(item_i,items,x,y,which);
			//
			// ANIMATE HALF-STEP
			//
			if (which!=item_i->amount-1 && (items+which+1)->active)
			{
				y+=6;
				DrawHalfStep(x,y);
			}

			do
			{
				if (which==item_i->amount-1)
					which=0;
				else
					which++;
			} while(!(items+which)->active);

			DrawGun(item_i,items,x,&y,which,basey,routine);

			//
			// WAIT FOR BUTTON-UP OR DELAY NEXT MOVE
			//
			TicDelay(20);
			break;
			
			default:
				break;
		}

		if (ci.button0 || IN_KeyDown(sc_Space) || IN_KeyDown(sc_Enter))
			exit = 1;

		if (ci.button1 || IN_KeyDown(sc_Escape))
			exit = 2;

	} while(!exit);


	IN_ClearKeysDown();

	//
	// ERASE EVERYTHING
	//
	if (lastitem!=which)
	{
		if (w0 == true || w1 == true){
			VW_Bar(x-1,y,25,16,BKGDCOLORWL6);
		} else {
			VW_Bar(x-1,y,25,16,BKGDCOLORSOD);
		}
		PrintX=item_i->x+item_i->indent;
		PrintY=item_i->y+which*13;
		US_Print((items+which)->string);
		redrawitem=1;
	}
	else
		redrawitem=0;

	if (routine)
		routine(which);
	VW_UpdateScreen();

	item_i->curpos=which;

	lastitem=which;
	switch(exit)
	{
		case 1:
			//
			// CALL THE ROUTINE
			//
			if ((items+which)->routine!=NULL)
			{
				ShootSnd();
				if (w0 == true || w1 == true){
					MenuFadeOutWL6();
				} else {
					MenuFadeOutSOD();
				}
				(items+which)->routine(0);
			}
			return which;

		case 2:
			if (w0 == true || w1 == true){
				SD_PlaySoundWL6(ESCPRESSEDSNDWL6);
			} else {
				SD_PlaySoundSOD(ESCPRESSEDSNDSOD);
			}
			return -1;
	}

	return 0; // JUST TO SHUT UP THE ERROR MESSAGES!
}


//
// ERASE GUN & DE-HIGHLIGHT STRING
//
void EraseGun(CP_iteminfo *item_i,CP_itemtype *items,int x,int y,int which)
{
	if (w0 == true || w1 == true){
		VW_Bar(x-1,y,25,16,BKGDCOLORWL6);
	} else {
		VW_Bar(x-1,y,25,16,BKGDCOLORSOD);
	}
	SetMenuTextColor(items+which,0);

	PrintX=item_i->x+item_i->indent;
	PrintY=item_i->y+which*13;
	US_Print((items+which)->string);
	VW_UpdateScreen();
}


//
// DRAW HALF STEP OF GUN TO NEXT POSITION
//
void DrawHalfStep(int x,int y)
{
	VWB_DrawPic(x,y,C_CURSOR1PIC);
	VW_UpdateScreen();
	if (w0 == true || w1 == true){
		SD_PlaySoundWL6(MOVEGUN1SNDWL6);
	} else {
		SD_PlaySoundSOD(MOVEGUN1SNDSOD);
	}
	set_TimeCount(0);
	while(get_TimeCount() < 8);
}


//
// DRAW GUN AT NEW POSITION
//
void DrawGun(CP_iteminfo *item_i,CP_itemtype *items,int x,int *y,int which,int basey,void (*routine)(int w))
{
	if (w0 == true || w1 == true){
		VW_Bar(x-1,*y,25,16,BKGDCOLORWL6);
	} else {
		VW_Bar(x-1,*y,25,16,BKGDCOLORSOD);
	}
	*y=basey+which*13;
	VWB_DrawPic(x,*y,C_CURSOR1PIC);
	SetMenuTextColor(items+which,1);

	PrintX=item_i->x+item_i->indent;
	PrintY=item_i->y+which*13;
	US_Print((items+which)->string);

	//
	// CALL CUSTOM ROUTINE IF IT IS NEEDED
	//
	if (routine)
		routine(which);
	VW_UpdateScreen();
	if (w0 == true || w1 == true){
		SD_PlaySoundWL6(MOVEGUN2SNDWL6);
	} else {
		SD_PlaySoundSOD(MOVEGUN2SNDSOD);
	}
}

////////////////////////////////////////////////////////////////////
//
// DELAY FOR AN AMOUNT OF TICS OR UNTIL CONTROLS ARE INACTIVE
//
////////////////////////////////////////////////////////////////////
void TicDelay(int count)
{
	ControlInfo ci;

	set_TimeCount(0);
	do {
		ReadAnyControl(&ci);
	} while( (get_TimeCount() < count) && (ci.dir!=dir_None) );
}


////////////////////////////////////////////////////////////////////
//
// Draw a menu
//
////////////////////////////////////////////////////////////////////
void DrawMenu(CP_iteminfo *item_i,CP_itemtype *items)
{
	int i,which=item_i->curpos;


	WindowX=PrintX=item_i->x+item_i->indent;
	WindowY=PrintY=item_i->y;
	WindowW=320;
	WindowH=200;

	for (i=0;i<item_i->amount;i++)
	{
		SetMenuTextColor(items+i,which==i);

		PrintY=item_i->y+i*13;
		if ((items+i)->active)
			US_Print((items+i)->string);
		else
		{
			if (w0 == true || w1 == true){
				SETFONTCOLOR(DEACTIVEWL6,BKGDCOLORWL6);
			} else {
				SETFONTCOLOR(DEACTIVESOD,BKGDCOLORSOD);
			}
			US_Print((items+i)->string);
			if (w0 == true || w1 == true){
				SETFONTCOLOR(TEXTCOLOR,BKGDCOLORWL6);
			} else {
				SETFONTCOLOR(TEXTCOLOR,BKGDCOLORSOD);
			}
		}

		US_Print("\n");
	}
}


////////////////////////////////////////////////////////////////////
//
// SET TEXT COLOR (HIGHLIGHT OR NO)
//
////////////////////////////////////////////////////////////////////
void SetMenuTextColor(CP_itemtype *items,int hlight)
{
	if (hlight){
		if (w0 == true || w1 == true){
			SETFONTCOLOR(color_hliteWL6[items->active],BKGDCOLORWL6);
		} else {
			SETFONTCOLOR(color_hliteSOD[items->active],BKGDCOLORSOD);
		}
	} else {
		if (w0 == true || w1 == true){
			SETFONTCOLOR(color_normlWL6[items->active],BKGDCOLORWL6);
		} else {
			SETFONTCOLOR(color_normlSOD[items->active],BKGDCOLORSOD);
		}
	}
}


////////////////////////////////////////////////////////////////////
//
// WAIT FOR CTRLKEY-UP OR BUTTON-UP
//
////////////////////////////////////////////////////////////////////
void WaitKeyUp(void)
{
	ControlInfo ci;
	while(ReadAnyControl(&ci), 
		ci.button0|ci.button1|ci.button2|ci.button3|
		IN_KeyDown(sc_Space)|IN_KeyDown(sc_Enter)|IN_KeyDown(sc_Escape));
}


////////////////////////////////////////////////////////////////////
//
// READ KEYBOARD, JOYSTICK AND MOUSE FOR INPUT
//
////////////////////////////////////////////////////////////////////
void ReadAnyControl(ControlInfo *ci)
{
	int mouseactive=0;

	IN_ReadControl(0,ci);

	if (mouseenabled)
	{
	}

	if (joystickenabled && !mouseactive)
	{
	}
}


////////////////////////////////////////////////////////////////////
//
// DRAW DIALOG AND CONFIRM YES OR NO TO QUESTION
//
////////////////////////////////////////////////////////////////////
int Confirm(const char *string)
{
	int xit=0,x,y,tick=0,whichsndWL6[2]={ESCPRESSEDSNDWL6,SHOOTSNDWL6},whichsndSOD[2]={ESCPRESSEDSNDSOD,SHOOTSNDSOD};

	Message(string);
	IN_ClearKeysDown();

	//
	// BLINK CURSOR
	//
	x=PrintX;
	y=PrintY;
	set_TimeCount(0);

	do
	{
		IN_CheckAck(); /* force update */
		if (get_TimeCount() >= 10)
		{
			switch(tick)
			{
				case 0:
					VW_Bar(x,y,8,13,TEXTCOLOR);
					break;
				case 1:
					PrintX=x;
					PrintY=y;
					US_Print("_");
			}
			VW_UpdateScreen();
			tick^=1;
			set_TimeCount(0);
		}

		if (IN_KeyDown(sc_Tab) && IN_KeyDown(sc_P) && MS_CheckParm("debugmode"))
			PicturePause();

	} while(!IN_KeyDown(sc_Y) && !IN_KeyDown(sc_N) && !IN_KeyDown(sc_Escape));

	if (IN_KeyDown(sc_Y)) {
		xit=1;
		ShootSnd();
	}

	while(IN_KeyDown(sc_Y) || IN_KeyDown(sc_N) || IN_KeyDown(sc_Escape)) IN_CheckAck();

	IN_ClearKeysDown();
	if (w0 == true || w1 == true){
		SD_PlaySoundWL6(whichsndWL6[xit]);
	} else {
		SD_PlaySoundSOD(whichsndSOD[xit]);
	}
	return xit;
}

////////////////////////////////////////////////////////////////////
//
// PRINT A MESSAGE IN A WINDOW
//
////////////////////////////////////////////////////////////////////
void Message(const char *string)
{
	word h=0, mw=0;

	CA_CacheGrChunk(STARTFONT+1);
	fontnumber=1;

	VW_MeasurePropString(string, &mw, &h);
	mw += 4;
	
	PrintY=(WindowH/2)-h/2;
	PrintX=WindowX=160-mw/2;

	DrawWindow(WindowX-5,PrintY-5,mw+10,h+10,TEXTCOLOR);
	DrawOutline(WindowX-5,PrintY-5,mw+10,h+10,0,HIGHLIGHT);
	SETFONTCOLOR(0,TEXTCOLOR);
	US_Print(string);
	VW_UpdateScreen();
}

static int lastmusic = -1;

void StartCPMusic(int song)
{
	FreeMusic();
	
	lastmusic = song;

	SD_StartMusic(song);
}

void FreeMusic()
{
	SD_MusicOff();
}

///////////////////////////////////////////////////////////////////////////
//
// CHECK FOR PAUSE KEY (FOR MUSIC ONLY)
//
///////////////////////////////////////////////////////////////////////////
void CheckPause()
{
	if (Paused)
	{
		switch(SoundStatus)
		{
			case 0: SD_MusicOn(); break;
			case 1: SD_MusicOff(); break;
		}

		SoundStatus^=1;
		VW_WaitVBL(3);
		IN_ClearKeysDown();
		Paused=false;
 }
}


///////////////////////////////////////////////////////////////////////////
//
// DRAW GUN CURSOR AT CORRECT POSITION IN MENU
//
///////////////////////////////////////////////////////////////////////////
void DrawMenuGun(CP_iteminfo *iteminfo)
{
	int x, y;

	x = iteminfo->x;
	y = iteminfo->y+iteminfo->curpos*13-2;
	
	VWB_DrawPic(x,y,C_CURSOR1PIC);
}


///////////////////////////////////////////////////////////////////////////
//
// DRAW SCREEN TITLE STRIPES
//
///////////////////////////////////////////////////////////////////////////
void DrawStripes(int y)
{
	if (w0 == true || w1 == true){
		VW_Bar(0,y,320,24,0);
		VW_Hlin(0,319,y+22,STRIPE);
	} else {
		VW_Bar(0,y,320,22,0);
		VW_Hlin(0,319,y+23,0);
	}
}

void ShootSnd()
{
	if (w0 == true || w1 == true){
		SD_PlaySoundWL6(SHOOTSNDWL6);
	} else {
		SD_PlaySoundSOD(SHOOTSNDSOD);
	}
}

void CheckForEpisodes()
{
	if (w1 == true){
		strcpy(extension, "wl6");
		NewEmenu[2].active =
		NewEmenu[4].active =
		NewEmenu[6].active =
		NewEmenu[8].active =
		NewEmenu[10].active =
		EpisodeSelect[1] =
		EpisodeSelect[2] =
		EpisodeSelect[3] =
		EpisodeSelect[4] =
		EpisodeSelect[5] = 1;
	} else if (w0 == true){
		strcpy(extension, "wl1");
	} else if (s0 == true){
		strcpy(extension, "sdm");
	} else if (s1 == true){
		strcpy(extension, "sod");
	} else if (s2 == true){
		strcpy(extension, "sod");
		strcpy(extension2, "sd2");
	} else if (s3 == true){
		strcpy(extension, "sod");
		strcpy(extension2, "sd3");
	}

	if (w0 == true || w1 == true || s0 == true || s1 == true){
		strcat(configname, extension);
		strcat(SaveName, extension);
	} else {
		strcat(configname, extension2);
		strcat(SaveName, extension2);
	}

}
