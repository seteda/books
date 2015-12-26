#include "wl_def.h"

/*
=============================================================================

						   WOLFENSTEIN 3-D

					  An Id Software production

						   by John Carmack

=============================================================================
*/

#define FOCALLENGTH     0x5800		/* in global coordinates */

char str[80], str2[20];

int viewwidth, viewheight;
int viewwidthwin, viewheightwin; /* for borders */
int xoffset, yoffset;
int vwidth, vheight; /* size of screen */
int viewsize;

int centerx;
int shootdelta;			/* pixels away from centerx a target can be */

boolean startgame,loadedgame;
int mouseadjustment;

long frameon;
long lasttimecount;
fixed viewsin, viewcos;
fixed viewx, viewy;		/* the focal point */
int pixelangle[MAXVIEWWIDTH];
long finetangent[FINEANGLES/4];
int horizwall[MAXWALLTILES], vertwall[MAXWALLTILES];

char configname[13] = "config.";

fixed sintable[ANGLES+ANGLES/4+1], *costable; // Vladimir = sintable+(ANGLES/4);

int _argc;
char **_argv;

boolean w0;
boolean w1;
boolean s0;
boolean s1;
boolean s2;
boolean s3;


//We'll give this a try...
unsigned int H_SPEARINFO = -1;
unsigned int H_BJPIC = -1;							// 3
unsigned int H_CASTLEPIC = -1;                         // 4
unsigned int H_BLAZEPIC = -1;                          // 5
unsigned int H_TOPWINDOWPIC = -1;                      // 6
unsigned int H_LEFTWINDOWPIC = -1;                     // 7
unsigned int H_RIGHTWINDOWPIC = -1;                    // 8
unsigned int H_BOTTOMINFOPIC = -1;                     // 9
// Lump Start
unsigned int C_OPTIONSPIC = -1;                        // 10
unsigned int C_CURSOR1PIC = -1;                        // 11
unsigned int C_CURSOR2PIC = -1;                        // 12
unsigned int C_NOTSELECTEDPIC = -1;                    // 13
unsigned int C_SELECTEDPIC = -1;                       // 14
unsigned int C_FXTITLEPIC = -1;                        // 15
unsigned int C_DIGITITLEPIC = -1;                      // 16
unsigned int C_MUSICTITLEPIC = -1;                     // 17
unsigned int C_MOUSELBACKPIC = -1;                     // 18
unsigned int C_BABYMODEPIC = -1;                       // 19
unsigned int C_EASYPIC = -1;                           // 20
unsigned int C_NORMALPIC = -1;                         // 21
unsigned int C_HARDPIC = -1;                           // 22
unsigned int C_LOADSAVEDISKPIC = -1;                   // 23
unsigned int C_DISKLOADING1PIC = -1;                   // 24
unsigned int C_DISKLOADING2PIC = -1;                   // 25
unsigned int C_CONTROLPIC = -1;                        // 26
unsigned int C_CUSTOMIZEPIC = -1;                      // 27
unsigned int C_LOADGAMEPIC = -1;                       // 28
unsigned int C_SAVEGAMEPIC = -1;                       // 29
unsigned int C_EPISODE1PIC = -1;                       // 30
unsigned int C_EPISODE2PIC = -1;                       // 31
unsigned int C_EPISODE3PIC = -1;                       // 32
unsigned int C_EPISODE4PIC = -1;                       // 33
unsigned int C_EPISODE5PIC = -1;                       // 34
unsigned int C_EPISODE6PIC = -1;                       // 35
unsigned int C_CODEPIC = -1;                           // 36
unsigned int C_TIMECODEPIC = -1;                       // 37
unsigned int C_LEVELPIC = -1;                          // 38
unsigned int C_NAMEPIC = -1;                           // 39
unsigned int C_SCOREPIC = -1;                          // 40
unsigned int C_JOY1PIC = -1;                           // 41
unsigned int C_JOY2PIC = -1;                           // 42
// Lump Start
unsigned int L_GUYPIC = -1;                            // 43
unsigned int L_COLONPIC = -1;                          // 44
unsigned int L_NUM0PIC = -1;                           // 45
unsigned int L_NUM1PIC = -1;                           // 46
unsigned int L_NUM2PIC = -1;                           // 47
unsigned int L_NUM3PIC = -1;                           // 48
unsigned int L_NUM4PIC = -1;                           // 49
unsigned int L_NUM5PIC = -1;                           // 50
unsigned int L_NUM6PIC = -1;                           // 51
unsigned int L_NUM7PIC = -1;                           // 52
unsigned int L_NUM8PIC = -1;                           // 53
unsigned int L_NUM9PIC = -1;                           // 54
unsigned int L_PERCENTPIC = -1;                        // 55
unsigned int L_APIC = -1;                              // 56
unsigned int L_BPIC = -1;                              // 57
unsigned int L_CPIC = -1;                              // 58
unsigned int L_DPIC = -1;                              // 59
unsigned int L_EPIC = -1;                              // 60
unsigned int L_FPIC = -1;                              // 61
unsigned int L_GPIC = -1;                              // 62
unsigned int L_HPIC = -1;                              // 63
unsigned int L_IPIC = -1;                              // 64
unsigned int L_JPIC = -1;                              // 65
unsigned int L_KPIC = -1;                              // 66
unsigned int L_LPIC = -1;                              // 67
unsigned int L_MPIC = -1;                              // 68
unsigned int L_NPIC = -1;                              // 69
unsigned int L_OPIC = -1;                              // 70
unsigned int L_PPIC = -1;                              // 71
unsigned int L_QPIC = -1;                              // 72
unsigned int L_RPIC = -1;                              // 73
unsigned int L_SPIC = -1;                              // 74
unsigned int L_TPIC = -1;                              // 75
unsigned int L_UPIC = -1;                              // 76
unsigned int L_VPIC = -1;                              // 77
unsigned int L_WPIC = -1;                              // 78
unsigned int L_XPIC = -1;                              // 79
unsigned int L_YPIC = -1;                              // 80
unsigned int L_ZPIC = -1;                              // 81
unsigned int L_EXPOINTPIC = -1;                        // 82
unsigned int L_APOSTROPHEPIC = -1;                     // 83
unsigned int L_GUY2PIC = -1;                           // 84
unsigned int L_BJWINSPIC = -1;                         // 85
unsigned int STATUSBARPIC = -1;                        // 86
unsigned int TITLEPIC = -1;                            // 87
unsigned int PG13PIC = -1;                             // 88
unsigned int CREDITSPIC = -1;                          // 89
unsigned int HIGHSCORESPIC = -1;                       // 90
// Lump Start
unsigned int KNIFEPIC = -1;                            // 91
unsigned int GUNPIC = -1;                              // 92
unsigned int MACHINEGUNPIC = -1;                       // 93
unsigned int GATLINGGUNPIC = -1;                       // 94
unsigned int NOKEYPIC = -1;                            // 95
unsigned int GOLDKEYPIC = -1;                          // 96
unsigned int SILVERKEYPIC = -1;                        // 97
unsigned int N_BLANKPIC = -1;                          // 98
unsigned int N_0PIC = -1;                              // 99
unsigned int N_1PIC = -1;                              // 100
unsigned int N_2PIC = -1;                              // 101
unsigned int N_3PIC = -1;                              // 102
unsigned int N_4PIC = -1;                              // 103
unsigned int N_5PIC = -1;                              // 104
unsigned int N_6PIC = -1;                              // 105
unsigned int N_7PIC = -1;                              // 106
unsigned int N_8PIC = -1;                              // 107
unsigned int N_9PIC = -1;                              // 108
unsigned int FACE1APIC = -1;                           // 109
unsigned int FACE1BPIC = -1;                           // 110
unsigned int FACE1CPIC = -1;                           // 111
unsigned int FACE2APIC = -1;                           // 112
unsigned int FACE2BPIC = -1;                           // 113
unsigned int FACE2CPIC = -1;                           // 114
unsigned int FACE3APIC = -1;                           // 115
unsigned int FACE3BPIC = -1;                           // 116
unsigned int FACE3CPIC = -1;                           // 117
unsigned int FACE4APIC = -1;                           // 118
unsigned int FACE4BPIC = -1;                           // 119
unsigned int FACE4CPIC = -1;                           // 120
unsigned int FACE5APIC = -1;                           // 121
unsigned int FACE5BPIC = -1;                           // 122
unsigned int FACE5CPIC = -1;                           // 123
unsigned int FACE6APIC = -1;                           // 124
unsigned int FACE6BPIC = -1;                           // 125
unsigned int FACE6CPIC = -1;                           // 126
unsigned int FACE7APIC = -1;                           // 127
unsigned int FACE7BPIC = -1;                           // 128
unsigned int FACE7CPIC = -1;                           // 129
unsigned int FACE8APIC = -1;                           // 130
unsigned int GOTGATLINGPIC = -1;                       // 131
unsigned int MUTANTBJPIC = -1;                         // 132
unsigned int PAUSEDPIC = -1;                           // 133
unsigned int GETPSYCHEDPIC = -1;                       // 134
unsigned int ORDERSCREEN = -1;						// 136
unsigned int ERRORSCREEN = -1;                         // 137
unsigned int T_HELPART = -1;                           // 138
unsigned int T_DEMO0 = -1;                             // 139
unsigned int T_DEMO1 = -1;                             // 140
unsigned int T_DEMO2 = -1;                             // 141
unsigned int T_DEMO3 = -1;                             // 142
unsigned int T_ENDART1 = -1;                           // 143
unsigned int T_ENDART2 = -1;                           // 144
unsigned int T_ENDART3 = -1;                           // 145
unsigned int T_ENDART4 = -1;                           // 146
unsigned int T_ENDART5 = -1;                           // 147
unsigned int T_ENDART6 = -1;                           // 148
// pics from wl1
unsigned int H_KEYBOARDPIC = -1;               // 5
unsigned int H_JOYPIC = -1;                    // 6
unsigned int H_HEALPIC = -1;                   // 7
unsigned int H_TREASUREPIC = -1;               // 8 // just white square
unsigned int H_GUNPIC = -1;                    // 9
unsigned int H_KEYPIC = -1;                    // 10
unsigned int H_WEAPON1234PIC = -1;             // 12
unsigned int H_WOLFLOGOPIC = -1;               // 13
unsigned int H_VISAPIC = -1;                   // 14
unsigned int H_MCPIC = -1;                     // 15
unsigned int H_IDLOGOPIC = -1;                 // 16
unsigned int H_SPEARSHOT = -1;				// 21
// pics from sdm
unsigned int C_BACKDROPPIC = -1;	// 3
unsigned int C_MOUSEPIC = -1;     // 12
unsigned int C_JOYSTICKPIC = -1;  // 13
unsigned int C_KEYBOARDPIC = -1;  // 14
unsigned int C_HOWTOUGHPIC = -1;  // 20
unsigned int C_WONSPEARPIC = -1;  // 30
unsigned int TITLE1PIC = -1;      // 74
unsigned int TITLE2PIC = -1;      // 75
unsigned int GODMODEFACE1PIC = -1;// 120
unsigned int GODMODEFACE2PIC = -1;// 121
unsigned int GODMODEFACE3PIC = -1;// 122
unsigned int BJWAITING1PIC = -1;  // 123
unsigned int BJWAITING2PIC = -1;  // 124
unsigned int BJOUCHPIC = -1;      // 125
unsigned int TITLEPALETTE = -1;   // 131
// pics from sod
unsigned int BJCOLLAPSE1PIC = -1; // 31
unsigned int BJCOLLAPSE2PIC = -1; // 32
unsigned int BJCOLLAPSE3PIC = -1; // 33
unsigned int BJCOLLAPSE4PIC = -1; // 34
unsigned int ENDPICPIC = -1;      // 35
unsigned int ENDSCREEN11PIC = -1; // 81
unsigned int ENDSCREEN12PIC = -1; // 82
unsigned int ENDSCREEN3PIC = -1;  // 83
unsigned int ENDSCREEN4PIC = -1;  // 84
unsigned int ENDSCREEN5PIC = -1;  // 85
unsigned int ENDSCREEN6PIC = -1;  // 86
unsigned int ENDSCREEN7PIC = -1;  // 87
unsigned int ENDSCREEN8PIC = -1;  // 88
unsigned int ENDSCREEN9PIC = -1;  // 89
unsigned int IDGUYS1PIC = -1;     // 93
unsigned int IDGUYS2PIC = -1;     // 94
unsigned int COPYPROTTOPPIC = -1; // 95
unsigned int COPYPROTBOXPIC = -1; // 96
unsigned int BOSSPIC1PIC = -1;    // 97
unsigned int BOSSPIC2PIC = -1;    // 98
unsigned int BOSSPIC3PIC = -1;    // 99
unsigned int BOSSPIC4PIC = -1;    // 100
unsigned int END1PALETTE = -1;    // 154
unsigned int END2PALETTE = -1;    // 155
unsigned int END3PALETTE = -1;    // 156
unsigned int END4PALETTE = -1;    // 157
unsigned int END5PALETTE = -1;    // 158
unsigned int END6PALETTE = -1;    // 159
unsigned int END7PALETTE = -1;    // 160
unsigned int END8PALETTE = -1;    // 161
unsigned int END9PALETTE = -1;    // 162
unsigned int IDGUYSPALETTE = -1;  // 163
unsigned int ENUMEND = -1;

void SetupGFXV(){
	if (w0 == true){
		H_BJPIC = H_BJPICWL1;
		H_CASTLEPIC = H_CASTLEPICWL1;
		H_KEYBOARDPIC = H_KEYBOARDPICWL1;
		H_JOYPIC = H_JOYPICWL1;
		H_HEALPIC = H_HEALPICWL1;
		H_TREASUREPIC = H_TREASUREPICWL1;
		H_GUNPIC = H_GUNPICWL1;
		H_KEYPIC = H_KEYPICWL1;
		H_BLAZEPIC = H_BLAZEPICWL1;
		H_WEAPON1234PIC = H_WEAPON1234PICWL1;
		H_WOLFLOGOPIC = H_WOLFLOGOPICWL1;
		H_VISAPIC = H_VISAPICWL1;
		H_MCPIC = H_MCPICWL1;
		H_IDLOGOPIC = H_IDLOGOPICWL1;
		H_TOPWINDOWPIC = H_TOPWINDOWPICWL1;
		H_LEFTWINDOWPIC = H_LEFTWINDOWPICWL1;
		H_RIGHTWINDOWPIC = H_RIGHTWINDOWPICWL1;
		H_BOTTOMINFOPIC = H_BOTTOMINFOPICWL1;
		H_SPEARINFO = H_SPEARINFOWL1;
		C_OPTIONSPIC = C_OPTIONSPICWL1;
		C_CURSOR1PIC = C_CURSOR1PICWL1;
		C_CURSOR2PIC = C_CURSOR2PICWL1;
		C_NOTSELECTEDPIC = C_NOTSELECTEDPICWL1;
		C_SELECTEDPIC = C_SELECTEDPICWL1;
		C_FXTITLEPIC = C_FXTITLEPICWL1;
		C_DIGITITLEPIC = C_DIGITITLEPICWL1;
		C_MUSICTITLEPIC = C_MUSICTITLEPICWL1;
		C_MOUSELBACKPIC = C_MOUSELBACKPICWL1;
		C_BABYMODEPIC = C_BABYMODEPICWL1;
		C_EASYPIC = C_EASYPICWL1;
		C_NORMALPIC = C_NORMALPICWL1;
		C_HARDPIC = C_HARDPICWL1;
		C_LOADSAVEDISKPIC = C_LOADSAVEDISKPICWL1;
		C_DISKLOADING1PIC = C_DISKLOADING1PICWL1;
		C_DISKLOADING2PIC = C_DISKLOADING2PICWL1;
		C_CONTROLPIC = C_CONTROLPICWL1;
		C_CUSTOMIZEPIC = C_CUSTOMIZEPICWL1;
		C_LOADGAMEPIC = C_LOADGAMEPICWL1;
		C_SAVEGAMEPIC = C_SAVEGAMEPICWL1;
		C_EPISODE1PIC = C_EPISODE1PICWL1;
		C_EPISODE2PIC = C_EPISODE2PICWL1;
		C_EPISODE3PIC = C_EPISODE3PICWL1;
		C_EPISODE4PIC = C_EPISODE4PICWL1;
		C_EPISODE5PIC = C_EPISODE5PICWL1;
		C_EPISODE6PIC = C_EPISODE6PICWL1;
		C_CODEPIC = C_CODEPICWL1;
		C_TIMECODEPIC = C_TIMECODEPICWL1;
		C_LEVELPIC = C_LEVELPICWL1;
		C_NAMEPIC = C_NAMEPICWL1;
		C_SCOREPIC = C_SCOREPICWL1;
		C_JOY1PIC = C_JOY1PICWL1;
		C_JOY2PIC = C_JOY2PICWL1;
		L_GUYPIC = L_GUYPICWL1;
		L_COLONPIC = L_COLONPICWL1;
		L_NUM0PIC = L_NUM0PICWL1;
		L_NUM1PIC = L_NUM1PICWL1;
		L_NUM2PIC = L_NUM2PICWL1;
		L_NUM3PIC = L_NUM3PICWL1;
		L_NUM4PIC = L_NUM4PICWL1;
		L_NUM5PIC = L_NUM5PICWL1;
		L_NUM6PIC = L_NUM6PICWL1;
		L_NUM7PIC = L_NUM7PICWL1;
		L_NUM8PIC = L_NUM8PICWL1;
		L_NUM9PIC = L_NUM9PICWL1;
		L_PERCENTPIC = L_PERCENTPICWL1;
		L_APIC = L_APICWL1;
		L_BPIC = L_BPICWL1;
		L_CPIC = L_CPICWL1;
		L_DPIC = L_DPICWL1;
		L_EPIC = L_EPICWL1;
		L_FPIC = L_FPICWL1;
		L_GPIC = L_GPICWL1;
		L_HPIC = L_HPICWL1;
		L_IPIC = L_IPICWL1;
		L_JPIC = L_JPICWL1;
		L_KPIC = L_KPICWL1;
		L_LPIC = L_LPICWL1;
		L_MPIC = L_MPICWL1;
		L_NPIC = L_NPICWL1;
		L_OPIC = L_OPICWL1;
		L_PPIC = L_PPICWL1;
		L_QPIC = L_QPICWL1;
		L_RPIC = L_RPICWL1;
		L_SPIC = L_SPICWL1;
		L_TPIC = L_TPICWL1;
		L_UPIC = L_UPICWL1;
		L_VPIC = L_VPICWL1;
		L_WPIC = L_WPICWL1;
		L_XPIC = L_XPICWL1;
		L_YPIC = L_YPICWL1;
		L_ZPIC = L_ZPICWL1;
		L_EXPOINTPIC = L_EXPOINTPICWL1;
		L_APOSTROPHEPIC = L_APOSTROPHEPICWL1;
		L_GUY2PIC = L_GUY2PICWL1;
		L_BJWINSPIC = L_BJWINSPICWL1;
		STATUSBARPIC = STATUSBARPICWL1;
		TITLEPIC = TITLEPICWL1;
		PG13PIC = PG13PICWL1;
		CREDITSPIC = CREDITSPICWL1;
		HIGHSCORESPIC = HIGHSCORESPICWL1;
		KNIFEPIC = KNIFEPICWL1;
		GUNPIC = GUNPICWL1;
		MACHINEGUNPIC = MACHINEGUNPICWL1;
		GATLINGGUNPIC = GATLINGGUNPICWL1;
		NOKEYPIC = NOKEYPICWL1;
		GOLDKEYPIC = GOLDKEYPICWL1;
		SILVERKEYPIC = SILVERKEYPICWL1;
		N_BLANKPIC = N_BLANKPICWL1;
		N_0PIC = N_0PICWL1;
		N_1PIC = N_1PICWL1;
		N_2PIC = N_2PICWL1;
		N_3PIC = N_3PICWL1;
		N_4PIC = N_4PICWL1;
		N_5PIC = N_5PICWL1;
		N_6PIC = N_6PICWL1;
		N_7PIC = N_7PICWL1;
		N_8PIC = N_8PICWL1;
		N_9PIC = N_9PICWL1;
		FACE1APIC = FACE1APICWL1;
		FACE1BPIC = FACE1BPICWL1;
		FACE1CPIC = FACE1CPICWL1;
		FACE2APIC = FACE2APICWL1;
		FACE2BPIC = FACE2BPICWL1;
		FACE2CPIC = FACE2CPICWL1;
		FACE3APIC = FACE3APICWL1;
		FACE3BPIC = FACE3BPICWL1;
		FACE3CPIC = FACE3CPICWL1;
		FACE4APIC = FACE4APICWL1;
		FACE4BPIC = FACE4BPICWL1;
		FACE4CPIC = FACE4CPICWL1;
		FACE5APIC = FACE5APICWL1;
		FACE5BPIC = FACE5BPICWL1;
		FACE5CPIC = FACE5CPICWL1;
		FACE6APIC = FACE6APICWL1;
		FACE6BPIC = FACE6BPICWL1;
		FACE6CPIC = FACE6CPICWL1;
		FACE7APIC = FACE7APICWL1;
		FACE7BPIC = FACE7BPICWL1;
		FACE7CPIC = FACE7CPICWL1;
		FACE8APIC = FACE8APICWL1;
		GOTGATLINGPIC = GOTGATLINGPICWL1;
		MUTANTBJPIC = MUTANTBJPICWL1;
		PAUSEDPIC = PAUSEDPICWL1;
		GETPSYCHEDPIC = GETPSYCHEDPICWL1;
		ORDERSCREEN = ORDERSCREENWL1;
		ERRORSCREEN = ERRORSCREENWL1;
		T_HELPART = T_HELPARTWL1;
		T_DEMO0 = T_DEMO0WL1;
		T_DEMO1 = T_DEMO1WL1;
		T_DEMO2 = T_DEMO2WL1;
		T_DEMO3 = T_DEMO3WL1;
		T_ENDART1 = T_ENDART1WL1;
		ENUMEND = ENUMENDWL1;
	} else if (w1 == true){
		H_BJPIC = H_BJPICWL6;
		H_CASTLEPIC = H_CASTLEPICWL6;
		H_BLAZEPIC = H_BLAZEPICWL6;
		H_TOPWINDOWPIC = H_TOPWINDOWPICWL6;
		H_LEFTWINDOWPIC = H_LEFTWINDOWPICWL6;
		H_RIGHTWINDOWPIC = H_RIGHTWINDOWPICWL6;
		H_BOTTOMINFOPIC = H_BOTTOMINFOPICWL6;
		C_OPTIONSPIC = C_OPTIONSPICWL6;
		C_CURSOR1PIC = C_CURSOR1PICWL6;
		C_CURSOR2PIC = C_CURSOR2PICWL6;
		C_NOTSELECTEDPIC = C_NOTSELECTEDPICWL6;
		C_SELECTEDPIC = C_SELECTEDPICWL6;
		C_FXTITLEPIC = C_FXTITLEPICWL6;
		C_DIGITITLEPIC = C_DIGITITLEPICWL6;
		C_MUSICTITLEPIC = C_MUSICTITLEPICWL6;
		C_MOUSELBACKPIC = C_MOUSELBACKPICWL6;
		C_BABYMODEPIC = C_BABYMODEPICWL6;
		C_EASYPIC = C_EASYPICWL6;
		C_NORMALPIC = C_NORMALPICWL6;
		C_HARDPIC = C_HARDPICWL6;
		C_LOADSAVEDISKPIC = C_LOADSAVEDISKPICWL6;
		C_DISKLOADING1PIC = C_DISKLOADING1PICWL6;
		C_DISKLOADING2PIC = C_DISKLOADING2PICWL6;
		C_CONTROLPIC = C_CONTROLPICWL6;
		C_CUSTOMIZEPIC = C_CUSTOMIZEPICWL6;
		C_LOADGAMEPIC = C_LOADGAMEPICWL6;
		C_SAVEGAMEPIC = C_SAVEGAMEPICWL6;
		C_EPISODE1PIC = C_EPISODE1PICWL6;
		C_EPISODE2PIC = C_EPISODE2PICWL6;
		C_EPISODE3PIC = C_EPISODE3PICWL6;
		C_EPISODE4PIC = C_EPISODE4PICWL6;
		C_EPISODE5PIC = C_EPISODE5PICWL6;
		C_EPISODE6PIC = C_EPISODE6PICWL6;
		C_CODEPIC = C_CODEPICWL6;
		C_TIMECODEPIC = C_TIMECODEPICWL6;
		C_LEVELPIC = C_LEVELPICWL6;
		C_NAMEPIC = C_NAMEPICWL6;
		C_SCOREPIC = C_SCOREPICWL6;
		C_JOY1PIC = C_JOY1PICWL6;
		C_JOY2PIC = C_JOY2PICWL6;
		L_GUYPIC = L_GUYPICWL6;
		L_COLONPIC = L_COLONPICWL6;
		L_NUM0PIC = L_NUM0PICWL6;
		L_NUM1PIC = L_NUM1PICWL6;
		L_NUM2PIC = L_NUM2PICWL6;
		L_NUM3PIC = L_NUM3PICWL6;
		L_NUM4PIC = L_NUM4PICWL6;
		L_NUM5PIC = L_NUM5PICWL6;
		L_NUM6PIC = L_NUM6PICWL6;
		L_NUM7PIC = L_NUM7PICWL6;
		L_NUM8PIC = L_NUM8PICWL6;
		L_NUM9PIC = L_NUM9PICWL6;
		L_PERCENTPIC = L_PERCENTPICWL6;
		L_APIC = L_APICWL6;
		L_BPIC = L_BPICWL6;
		L_CPIC = L_CPICWL6;
		L_DPIC = L_DPICWL6;
		L_EPIC = L_EPICWL6;
		L_FPIC = L_FPICWL6;
		L_GPIC = L_GPICWL6;
		L_HPIC = L_HPICWL6;
		L_IPIC = L_IPICWL6;
		L_JPIC = L_JPICWL6;
		L_KPIC = L_KPICWL6;
		L_LPIC = L_LPICWL6;
		L_MPIC = L_MPICWL6;
		L_NPIC = L_NPICWL6;
		L_OPIC = L_OPICWL6;
		L_PPIC = L_PPICWL6;
		L_QPIC = L_QPICWL6;
		L_RPIC = L_RPICWL6;
		L_SPIC = L_SPICWL6;
		L_TPIC = L_TPICWL6;
		L_UPIC = L_UPICWL6;
		L_VPIC = L_VPICWL6;
		L_WPIC = L_WPICWL6;
		L_XPIC = L_XPICWL6;
		L_YPIC = L_YPICWL6;
		L_ZPIC = L_ZPICWL6;
		L_EXPOINTPIC = L_EXPOINTPICWL6;
		L_APOSTROPHEPIC = L_APOSTROPHEPICWL6;
		L_GUY2PIC = L_GUY2PICWL6;
		L_BJWINSPIC = L_BJWINSPICWL6;
		STATUSBARPIC = STATUSBARPICWL6;
		TITLEPIC = TITLEPICWL6;
		PG13PIC = PG13PICWL6;
		CREDITSPIC = CREDITSPICWL6;
		HIGHSCORESPIC = HIGHSCORESPICWL6;
		KNIFEPIC = KNIFEPICWL6;
		GUNPIC = GUNPICWL6;
		MACHINEGUNPIC = MACHINEGUNPICWL6;
		GATLINGGUNPIC = GATLINGGUNPICWL6;
		NOKEYPIC = NOKEYPICWL6;
		GOLDKEYPIC = GOLDKEYPICWL6;
		SILVERKEYPIC = SILVERKEYPICWL6;
		N_BLANKPIC = N_BLANKPICWL6;
		N_0PIC = N_0PICWL6;
		N_1PIC = N_1PICWL6;
		N_2PIC = N_2PICWL6;
		N_3PIC = N_3PICWL6;
		N_4PIC = N_4PICWL6;
		N_5PIC = N_5PICWL6;
		N_6PIC = N_6PICWL6;
		N_7PIC = N_7PICWL6;
		N_8PIC = N_8PICWL6;
		N_9PIC = N_9PICWL6;
		FACE1APIC = FACE1APICWL6;
		FACE1BPIC = FACE1BPICWL6;
		FACE1CPIC = FACE1CPICWL6;
		FACE2APIC = FACE2APICWL6;
		FACE2BPIC = FACE2BPICWL6;
		FACE2CPIC = FACE2CPICWL6;
		FACE3APIC = FACE3APICWL6;
		FACE3BPIC = FACE3BPICWL6;
		FACE3CPIC = FACE3CPICWL6;
		FACE4APIC = FACE4APICWL6;
		FACE4BPIC = FACE4BPICWL6;
		FACE4CPIC = FACE4CPICWL6;
		FACE5APIC = FACE5APICWL6;
		FACE5BPIC = FACE5BPICWL6;
		FACE5CPIC = FACE5CPICWL6;
		FACE6APIC = FACE6APICWL6;
		FACE6BPIC = FACE6BPICWL6;
		FACE6CPIC = FACE6CPICWL6;
		FACE7APIC = FACE7APICWL6;
		FACE7BPIC = FACE7BPICWL6;
		FACE7CPIC = FACE7CPICWL6;
		FACE8APIC = FACE8APICWL6;
		GOTGATLINGPIC = GOTGATLINGPICWL6;
		MUTANTBJPIC = MUTANTBJPICWL6;
		PAUSEDPIC = PAUSEDPICWL6;
		GETPSYCHEDPIC = GETPSYCHEDPICWL6;
		ORDERSCREEN = ORDERSCREENWL6;
		ERRORSCREEN = ERRORSCREENWL6;
		T_HELPART = T_HELPARTWL6;
		T_DEMO0 = T_DEMO0WL6;
		T_DEMO1 = T_DEMO1WL6;
		T_DEMO2 = T_DEMO2WL6;
		T_DEMO3 = T_DEMO3WL6;
		T_ENDART1 = T_ENDART1WL6;
		T_ENDART2 = T_ENDART2WL6;
		T_ENDART3 = T_ENDART3WL6;
		T_ENDART4 = T_ENDART4WL6;
		T_ENDART5 = T_ENDART5WL6;
		T_ENDART6 = T_ENDART6WL6;
		ENUMEND = ENUMENDWL6;
	} else if (s0 == true){
		C_BACKDROPPIC = C_BACKDROPPICSDM;
		C_MOUSELBACKPIC = C_MOUSELBACKPICSDM;
		C_CURSOR1PIC = C_CURSOR1PICSDM;
		C_CURSOR2PIC = C_CURSOR2PICSDM;
		C_NOTSELECTEDPIC = C_NOTSELECTEDPICSDM;
		C_SELECTEDPIC = C_SELECTEDPICSDM;
		C_CUSTOMIZEPIC = C_CUSTOMIZEPICSDM;
		C_JOY1PIC = C_JOY1PICSDM;
		C_JOY2PIC = C_JOY2PICSDM;
		C_MOUSEPIC = C_MOUSEPICSDM;
		C_JOYSTICKPIC = C_JOYSTICKPICSDM;
		C_KEYBOARDPIC = C_KEYBOARDPICSDM;
		C_CONTROLPIC = C_CONTROLPICSDM;
		C_OPTIONSPIC = C_OPTIONSPICSDM;
		C_FXTITLEPIC = C_FXTITLEPICSDM;
		C_DIGITITLEPIC = C_DIGITITLEPICSDM;
		C_MUSICTITLEPIC = C_MUSICTITLEPICSDM;
		C_HOWTOUGHPIC = C_HOWTOUGHPICSDM;
		C_BABYMODEPIC = C_BABYMODEPICSDM;
		C_EASYPIC = C_EASYPICSDM;
		C_NORMALPIC = C_NORMALPICSDM;
		C_HARDPIC = C_HARDPICSDM;
		C_DISKLOADING1PIC = C_DISKLOADING1PICSDM;
		C_DISKLOADING2PIC = C_DISKLOADING2PICSDM;
		C_LOADGAMEPIC = C_LOADGAMEPICSDM;
		C_SAVEGAMEPIC = C_SAVEGAMEPICSDM;
		HIGHSCORESPIC = HIGHSCORESPICSDM;
		C_WONSPEARPIC = C_WONSPEARPICSDM;
		L_GUYPIC = L_GUYPICSDM;
		L_COLONPIC = L_COLONPICSDM;
		L_NUM0PIC = L_NUM0PICSDM;
		L_NUM1PIC = L_NUM1PICSDM;
		L_NUM2PIC = L_NUM2PICSDM;
		L_NUM3PIC = L_NUM3PICSDM;
		L_NUM4PIC = L_NUM4PICSDM;
		L_NUM5PIC = L_NUM5PICSDM;
		L_NUM6PIC = L_NUM6PICSDM;
		L_NUM7PIC = L_NUM7PICSDM;
		L_NUM8PIC = L_NUM8PICSDM;
		L_NUM9PIC = L_NUM9PICSDM;
		L_PERCENTPIC = L_PERCENTPICSDM;
		L_APIC = L_APICSDM;
		L_BPIC = L_BPICSDM;
		L_CPIC = L_CPICSDM;
		L_DPIC = L_DPICSDM;
		L_EPIC = L_EPICSDM;
		L_FPIC = L_FPICSDM;
		L_GPIC = L_GPICSDM;
		L_HPIC = L_HPICSDM;
		L_IPIC = L_IPICSDM;
		L_JPIC = L_JPICSDM;
		L_KPIC = L_KPICSDM;
		L_LPIC = L_LPICSDM;
		L_MPIC = L_MPICSDM;
		L_NPIC = L_NPICSDM;
		L_OPIC = L_OPICSDM;
		L_PPIC = L_PPICSDM;
		L_QPIC = L_QPICSDM;
		L_RPIC = L_RPICSDM;
		L_SPIC = L_SPICSDM;
		L_TPIC = L_TPICSDM;
		L_UPIC = L_UPICSDM;
		L_VPIC = L_VPICSDM;
		L_WPIC = L_WPICSDM;
		L_XPIC = L_XPICSDM;
		L_YPIC = L_YPICSDM;
		L_ZPIC = L_ZPICSDM;
		L_EXPOINTPIC = L_EXPOINTPICSDM;
		L_APOSTROPHEPIC = L_APOSTROPHEPICSDM;
		L_GUY2PIC = L_GUY2PICSDM;
		L_BJWINSPIC = L_BJWINSPICSDM;
		TITLE1PIC = TITLE1PICSDM;
		TITLE2PIC = TITLE2PICSDM;
		STATUSBARPIC = STATUSBARPICSDM;
		PG13PIC = PG13PICSDM;
		CREDITSPIC = CREDITSPICSDM;
		KNIFEPIC = KNIFEPICSDM;
		GUNPIC = GUNPICSDM;
		MACHINEGUNPIC = MACHINEGUNPICSDM;
		GATLINGGUNPIC = GATLINGGUNPICSDM;
		NOKEYPIC = NOKEYPICSDM;
		GOLDKEYPIC = GOLDKEYPICSDM;
		SILVERKEYPIC = SILVERKEYPICSDM;
		N_BLANKPIC = N_BLANKPICSDM;
		N_0PIC = N_0PICSDM;
		N_1PIC = N_1PICSDM;
		N_2PIC = N_2PICSDM;
		N_3PIC = N_3PICSDM;
		N_4PIC = N_4PICSDM;
		N_5PIC = N_5PICSDM;
		N_6PIC = N_6PICSDM;
		N_7PIC = N_7PICSDM;
		N_8PIC = N_8PICSDM;
		N_9PIC = N_9PICSDM;
		FACE1APIC = FACE1APICSDM;
		FACE1BPIC = FACE1BPICSDM;
		FACE1CPIC = FACE1CPICSDM;
		FACE2APIC = FACE2APICSDM;
		FACE2BPIC = FACE2BPICSDM;
		FACE2CPIC = FACE2CPICSDM;
		FACE3APIC = FACE3APICSDM;
		FACE3BPIC = FACE3BPICSDM;
		FACE3CPIC = FACE3CPICSDM;
		FACE4APIC = FACE4APICSDM;
		FACE4BPIC = FACE4BPICSDM;
		FACE4CPIC = FACE4CPICSDM;
		FACE5APIC = FACE5APICSDM;
		FACE5BPIC = FACE5BPICSDM;
		FACE5CPIC = FACE5CPICSDM;
		FACE6APIC = FACE6APICSDM;
		FACE6BPIC = FACE6BPICSDM;
		FACE6CPIC = FACE6CPICSDM;
		FACE7APIC = FACE7APICSDM;
		FACE7BPIC = FACE7BPICSDM;
		FACE7CPIC = FACE7CPICSDM;
		FACE8APIC = FACE8APICSDM;
		GOTGATLINGPIC = GOTGATLINGPICSDM;
		GODMODEFACE1PIC = GODMODEFACE1PICSDM;
		GODMODEFACE2PIC = GODMODEFACE2PICSDM;
		GODMODEFACE3PIC = GODMODEFACE3PICSDM;
		BJWAITING1PIC = BJWAITING1PICSDM;
		BJWAITING2PIC = BJWAITING2PICSDM;
		BJOUCHPIC = BJOUCHPICSDM;
		PAUSEDPIC = PAUSEDPICSDM;
		GETPSYCHEDPIC = GETPSYCHEDPICSDM;
		ORDERSCREEN = ORDERSCREENSDM;
		ERRORSCREEN = ERRORSCREENSDM;
		TITLEPALETTE = TITLEPALETTESDM;
		T_DEMO0 = T_DEMO0SDM;
		ENUMEND = ENUMENDSDM;
	} else {
		C_BACKDROPPIC = C_BACKDROPPICSOD;
		C_MOUSELBACKPIC = C_MOUSELBACKPICSOD;
		C_CURSOR1PIC = C_CURSOR1PICSOD;
		C_CURSOR2PIC = C_CURSOR2PICSOD;
		C_NOTSELECTEDPIC = C_NOTSELECTEDPICSOD;
		C_SELECTEDPIC = C_SELECTEDPICSOD;
		C_CUSTOMIZEPIC = C_CUSTOMIZEPICSOD;
		C_JOY1PIC = C_JOY1PICSOD;
		C_JOY2PIC = C_JOY2PICSOD;
		C_MOUSEPIC = C_MOUSEPICSOD;
		C_JOYSTICKPIC = C_JOYSTICKPICSOD;
		C_KEYBOARDPIC = C_KEYBOARDPICSOD;
		C_CONTROLPIC = C_CONTROLPICSOD;
		C_OPTIONSPIC = C_OPTIONSPICSOD;
		C_FXTITLEPIC = C_FXTITLEPICSOD;
		C_DIGITITLEPIC = C_DIGITITLEPICSOD;
		C_MUSICTITLEPIC = C_MUSICTITLEPICSOD;
		C_HOWTOUGHPIC = C_HOWTOUGHPICSOD;
		C_BABYMODEPIC = C_BABYMODEPICSOD;
		C_EASYPIC = C_EASYPICSOD;
		C_NORMALPIC = C_NORMALPICSOD;
		C_HARDPIC = C_HARDPICSOD;
		C_DISKLOADING1PIC = C_DISKLOADING1PICSOD;
		C_DISKLOADING2PIC = C_DISKLOADING2PICSOD;
		C_LOADGAMEPIC = C_LOADGAMEPICSOD;
		C_SAVEGAMEPIC = C_SAVEGAMEPICSOD;
		HIGHSCORESPIC = HIGHSCORESPICSOD;
		C_WONSPEARPIC = C_WONSPEARPICSOD;
		BJCOLLAPSE1PIC = BJCOLLAPSE1PICSOD;
		BJCOLLAPSE2PIC = BJCOLLAPSE2PICSOD;
		BJCOLLAPSE3PIC = BJCOLLAPSE3PICSOD;
		BJCOLLAPSE4PIC = BJCOLLAPSE4PICSOD;
		ENDPICPIC = ENDPICPICSOD;
		L_GUYPIC = L_GUYPICSOD;
		L_COLONPIC = L_COLONPICSOD;
		L_NUM0PIC = L_NUM0PICSOD;
		L_NUM1PIC = L_NUM1PICSOD;
		L_NUM2PIC = L_NUM2PICSOD;
		L_NUM3PIC = L_NUM3PICSOD;
		L_NUM4PIC = L_NUM4PICSOD;
		L_NUM5PIC = L_NUM5PICSOD;
		L_NUM6PIC = L_NUM6PICSOD;
		L_NUM7PIC = L_NUM7PICSOD;
		L_NUM8PIC = L_NUM8PICSOD;
		L_NUM9PIC = L_NUM9PICSOD;
		L_PERCENTPIC = L_PERCENTPICSOD;
		L_APIC = L_APICSOD;
		L_BPIC = L_BPICSOD;
		L_CPIC = L_CPICSOD;
		L_DPIC = L_DPICSOD;
		L_EPIC = L_EPICSOD;
		L_FPIC = L_FPICSOD;
		L_GPIC = L_GPICSOD;
		L_HPIC = L_HPICSOD;
		L_IPIC = L_IPICSOD;
		L_JPIC = L_JPICSOD;
		L_KPIC = L_KPICSOD;
		L_LPIC = L_LPICSOD;
		L_MPIC = L_MPICSOD;
		L_NPIC = L_NPICSOD;
		L_OPIC = L_OPICSOD;
		L_PPIC = L_PPICSOD;
		L_QPIC = L_QPICSOD;
		L_RPIC = L_RPICSOD;
		L_SPIC = L_SPICSOD;
		L_TPIC = L_TPICSOD;
		L_UPIC = L_UPICSOD;
		L_VPIC = L_VPICSOD;
		L_WPIC = L_WPICSOD;
		L_XPIC = L_XPICSOD;
		L_YPIC = L_YPICSOD;
		L_ZPIC = L_ZPICSOD;
		L_EXPOINTPIC = L_EXPOINTPICSOD;
		L_APOSTROPHEPIC = L_APOSTROPHEPICSOD;
		L_GUY2PIC = L_GUY2PICSOD;
		L_BJWINSPIC = L_BJWINSPICSOD;
		TITLE1PIC = TITLE1PICSOD;
		TITLE2PIC = TITLE2PICSOD;
		ENDSCREEN11PIC = ENDSCREEN11PICSOD;
		ENDSCREEN12PIC = ENDSCREEN12PICSOD;
		ENDSCREEN3PIC = ENDSCREEN3PICSOD;
		ENDSCREEN4PIC = ENDSCREEN4PICSOD;
		ENDSCREEN5PIC = ENDSCREEN5PICSOD;
		ENDSCREEN6PIC = ENDSCREEN6PICSOD;
		ENDSCREEN7PIC = ENDSCREEN7PICSOD;
		ENDSCREEN8PIC = ENDSCREEN8PICSOD;
		ENDSCREEN9PIC = ENDSCREEN9PICSOD;
		STATUSBARPIC = STATUSBARPICSOD;
		PG13PIC = PG13PICSOD;
		CREDITSPIC = CREDITSPICSOD;
		IDGUYS1PIC = IDGUYS1PICSOD;
		IDGUYS2PIC = IDGUYS2PICSOD;
		COPYPROTTOPPIC = COPYPROTTOPPICSOD;
		COPYPROTBOXPIC = COPYPROTBOXPICSOD;
		BOSSPIC1PIC = BOSSPIC1PICSOD;
		BOSSPIC2PIC = BOSSPIC2PICSOD;
		BOSSPIC3PIC = BOSSPIC3PICSOD;
		BOSSPIC4PIC = BOSSPIC4PICSOD;
		KNIFEPIC = KNIFEPICSOD;
		GUNPIC = GUNPICSOD;
		MACHINEGUNPIC = MACHINEGUNPICSOD;
		GATLINGGUNPIC = GATLINGGUNPICSOD;
		NOKEYPIC = NOKEYPICSOD;
		GOLDKEYPIC = GOLDKEYPICSOD;
		SILVERKEYPIC = SILVERKEYPICSOD;
		N_BLANKPIC = N_BLANKPICSOD;
		N_0PIC = N_0PICSOD;
		N_1PIC = N_1PICSOD;
		N_2PIC = N_2PICSOD;
		N_3PIC = N_3PICSOD;
		N_4PIC = N_4PICSOD;
		N_5PIC = N_5PICSOD;
		N_6PIC = N_6PICSOD;
		N_7PIC = N_7PICSOD;
		N_8PIC = N_8PICSOD;
		N_9PIC = N_9PICSOD;
		FACE1APIC = FACE1APICSOD;
		FACE1BPIC = FACE1BPICSOD;
		FACE1CPIC = FACE1CPICSOD;
		FACE2APIC = FACE2APICSOD;
		FACE2BPIC = FACE2BPICSOD;
		FACE2CPIC = FACE2CPICSOD;
		FACE3APIC = FACE3APICSOD;
		FACE3BPIC = FACE3BPICSOD;
		FACE3CPIC = FACE3CPICSOD;
		FACE4APIC = FACE4APICSOD;
		FACE4BPIC = FACE4BPICSOD;
		FACE4CPIC = FACE4CPICSOD;
		FACE5APIC = FACE5APICSOD;
		FACE5BPIC = FACE5BPICSOD;
		FACE5CPIC = FACE5CPICSOD;
		FACE6APIC = FACE6APICSOD;
		FACE6BPIC = FACE6BPICSOD;
		FACE6CPIC = FACE6CPICSOD;
		FACE7APIC = FACE7APICSOD;
		FACE7BPIC = FACE7BPICSOD;
		FACE7CPIC = FACE7CPICSOD;
		FACE8APIC = FACE8APICSOD;
		GOTGATLINGPIC = GOTGATLINGPICSOD;
		GODMODEFACE1PIC = GODMODEFACE1PICSOD;
		GODMODEFACE2PIC = GODMODEFACE2PICSOD;
		GODMODEFACE3PIC = GODMODEFACE3PICSOD;
		BJWAITING1PIC = BJWAITING1PICSOD;
		BJWAITING2PIC = BJWAITING2PICSOD;
		BJOUCHPIC = BJOUCHPICSOD;
		PAUSEDPIC = PAUSEDPICSOD;
		GETPSYCHEDPIC = GETPSYCHEDPICSOD;
		ORDERSCREEN = ORDERSCREENSOD;
		ERRORSCREEN = ERRORSCREENSOD;
		TITLEPALETTE = TITLEPALETTESOD;
		END1PALETTE = END1PALETTESOD;
		END2PALETTE = END2PALETTESOD;
		END3PALETTE = END3PALETTESOD;
		END4PALETTE = END4PALETTESOD;
		END5PALETTE = END5PALETTESOD;
		END6PALETTE = END6PALETTESOD;
		END7PALETTE = END7PALETTESOD;
		END8PALETTE = END8PALETTESOD;
		END9PALETTE = END9PALETTESOD;
		IDGUYSPALETTE = IDGUYSPALETTESOD;
		T_DEMO0 = T_DEMO0SOD;
		T_DEMO1 = T_DEMO1SOD;
		T_DEMO2 = T_DEMO2SOD;
		T_DEMO3 = T_DEMO3SOD;
		T_ENDART1 = T_ENDART1SOD;
		ENUMEND = ENUMENDSOD;
	}
}

/*
========================
=
= FixedByFrac (FixedMul)
=
= multiply two 16/16 bit, 2's complement fixed point numbers
=
========================
*/
#ifdef NOASM
#if defined(__INTEL_COMPILER)
typedef _int64 int64_t;
#endif
fixed FixedByFrac(fixed a, fixed b)
{
	int64_t ra = a;
	int64_t rb = b;
	int64_t r;
	
	r = ra * rb;
	r >>= TILESHIFT;
	return (fixed)r;
}
#endif

/*
=====================
=
= CalcTics
=
=====================
*/

void CalcTics()
{
	int newtime;
	int ticcount;
	
	if (demoplayback || demorecord)
		ticcount = DEMOTICS - 1; /* [70/4] 17.5 Hz */
	else
		ticcount = 0 + 1; /* 35 Hz */
	
	do {
		newtime = get_TimeCount();
		tics = newtime - lasttimecount;
	} while (tics <= ticcount);
	
	lasttimecount = newtime;
	
	if (demoplayback || demorecord)
		tics = DEMOTICS;
	else if (tics > MAXTICS)
		tics = MAXTICS;
}

/* ======================================================================== */

static void DiskFlopAnim(int x, int y)
{
	static char which = 0;
	
	if (!x && !y)
		return;
	
	VWB_DrawPic(x, y, C_DISKLOADING1PIC+which);
	VW_UpdateScreen();
	
	which ^= 1;
}

static int32_t CalcFileChecksum(int fd, int len)
{
	int8_t buf[4096];
	
	int32_t cs;
	int i, j;
	int8_t c1;
	
	cs = 0;
	c1 = ReadInt8(fd);
	
	len--;
	while (len > 0) {
		i = 4096;
		if (len < i) {
			i = len;
		}
		
		ReadBytes(fd, buf, i);
		
		for (j = 0; j < i; j++) {
			cs += c1 ^ buf[j];
			c1 = buf[j];
		}
		
		len -= 4096;
	}
	
	return cs;
}

int WriteConfig()
{
	int i;
	int fd;
	int32_t cs;

	char GAMETYPE[4];

	if (w0 == true){
		strcpy(GAMETYPE, "WL1");
	} else if (w1 == true){
		strcpy(GAMETYPE, "WL6");
	} else if (s0 == true){
		strcpy(GAMETYPE, "SDM");
	} else {
		strcpy(GAMETYPE, "SOD");
	}

	fd = OpenWrite(configname);
	
	if (fd != -1) {
		WriteBytes(fd, (byte *)GAMEHDR, 8);	/* WOLF3D, 0, 0 */
		WriteBytes(fd, (byte *)CFGTYPE, 4);	/* CFG, 0 */
		WriteInt32(fd, 0x00000000);		/* Version (integer) */
		WriteBytes(fd, (byte *)GAMETYPE, 4);	/* XXX, 0 */
		WriteInt32(fd, time(NULL));		/* Time */
		WriteInt32(fd, 0x00000000);		/* Padding */
		WriteInt32(fd, 0x00000000);		/* Checksum (placeholder) */
	
		for (i = 0; i < 7; i++) { /* MaxScores = 7 */
			WriteBytes(fd, (byte *)Scores[i].name, 58);
			WriteInt32(fd, Scores[i].score);
			WriteInt32(fd, Scores[i].completed);
			WriteInt32(fd, Scores[i].episode);
		}
		
		WriteInt32(fd, viewsize);

		/* sound config, etc. (to be done) */
		WriteInt32(fd, 0); /* padding */
		WriteInt32(fd, 0); /* padding */
		WriteInt32(fd, 0); /* padding */
		WriteInt32(fd, 0); /* padding */
		WriteInt32(fd, 0); /* padding */
		WriteInt32(fd, 0); /* padding */
		WriteInt32(fd, 0); /* padding */
		WriteInt32(fd, 0); /* padding */
			
		/* direction keys */	
		for (i = 0; i < 4; i++) {
			WriteInt32(fd, dirscan[i]);
		}
			
		/* other game keys */
		for (i = 0; i < 8; i++) { /* NUMBUTTONS = 8 */
			WriteInt32(fd, buttonscan[i]);
		}
			
		/* mouse enabled */
		WriteInt8(fd, mouseenabled);
			
		/* mouse buttons */
		for (i = 0; i < 4; i++) {
			WriteInt32(fd, buttonmouse[i]);
		}
			
		/* mouse adjustment */
		WriteInt32(fd, mouseadjustment);
			
		/* joystick (to be done) */
		WriteInt32(fd, -1);

		CloseWrite(fd);
		
		fd = OpenRead(configname);
		ReadSeek(fd, 32, SEEK_SET);
		cs = CalcFileChecksum(fd, ReadLength(fd) - 32);
		CloseRead(fd);
		
		fd = OpenWriteAppend(configname);
		WriteSeek(fd, 28, SEEK_SET);
		WriteInt32(fd, cs);
		
		CloseWrite(fd);
	}
	
	return 0;
}

static void SetDefaults()
{
	viewsize = 20;
	
	mouseenabled = false;

	joystickenabled = false;
	joypadenabled = false;
	joystickport = 0;

	mouseadjustment = 5;

	SD_SetMusicMode(smm_AdLib);
	SD_SetSoundMode(sdm_AdLib);
	SD_SetDigiDevice(sds_SoundBlaster);
}

int ReadConfig()
{
	int fd, configokay;
	char buf[8];
	int32_t version, v;
	int i;

	char GAMETYPE[4];

	if (w0 == true){
		strcpy(GAMETYPE, "WL1");
	} else if (w1 == true){
		strcpy(GAMETYPE, "WL6");
	} else if (s0 == true){
		strcpy(GAMETYPE, "SDM");
	} else {
		strcpy(GAMETYPE, "SOD");
	}

	configokay = 0;
	
	fd = OpenRead(configname);
	
	if (fd != -1) {
		SetDefaults();
		
		ReadBytes(fd, (byte *)buf, 8);
		if (strncmp(buf, GAMEHDR, 8))
			goto configend;
		
		ReadBytes(fd, (byte *)buf, 4);
		if (strncmp(buf, CFGTYPE, 4))
			goto configend;
		
		version = ReadInt32(fd);
		if (version != 0xFFFFFFFF && version != 0x00000000)
			goto configend;
		
		ReadBytes(fd, (byte *)buf, 4);
		if (strncmp(buf, GAMETYPE, 4))
			goto configend;
		
		ReadInt32(fd);	/* skip over time */
		ReadInt32(fd);	/* skip over padding */
		
		v = ReadInt32(fd);	/* get checksum */
		if (v != CalcFileChecksum(fd, ReadLength(fd) - 32))
			goto configend;
		
		ReadSeek(fd, 32, SEEK_SET);
		
		for (i = 0; i < 7; i++) { /* MaxScores = 7 */
			ReadBytes(fd, (byte *)Scores[i].name, 58);
			Scores[i].score = ReadInt32(fd);
			Scores[i].completed = ReadInt32(fd);
			Scores[i].episode = ReadInt32(fd);
		}
		
		viewsize = ReadInt32(fd);
		
		/* load the new data */
		if (version == 0x00000000) {
			/* sound config, etc. */
			ReadInt32(fd); /* padding */
			ReadInt32(fd); /* padding */
			ReadInt32(fd); /* padding */
			ReadInt32(fd); /* padding */
			ReadInt32(fd); /* padding */
			ReadInt32(fd); /* padding */
			ReadInt32(fd); /* padding */
			ReadInt32(fd); /* padding */
			
			/* direction keys */	
			for (i = 0; i < 4; i++) {
				dirscan[i] = ReadInt32(fd);
			}
			
			/* other game keys */
			for (i = 0; i < 8; i++) { /* NUMBUTTONS = 8 */
				buttonscan[i] = ReadInt32(fd);
			}
			
			/* mouse enabled */
			mouseenabled = ReadInt8(fd);
			
			/* mouse buttons */
			for (i = 0; i < 4; i++) {
				buttonmouse[i] = ReadInt32(fd);
			}
			
			/* mouse adjustment */
			mouseadjustment = ReadInt32(fd);
			
			/* unimplemented joystick */
			v = ReadInt32(fd);
			if (v != 0xFFFFFFFF) {
			}
		}

		configokay = 1;
	} 
	
configend:	
	
	if (fd != -1)
		CloseRead(fd);
	
	if (!configokay) {
		printf("Config: Setting defaults..\n");
		SetDefaults();
	}
	
	return 0;
}

int SaveTheGame(const char *fn, const char *tag, int dx, int dy)
{
	objtype *ob;
	int fd, i, x, y;
	int32_t cs;

	char GAMETYPE[4];

	if (w0 == true){
		strcpy(GAMETYPE, "WL1");
	} else if (w1 == true){
		strcpy(GAMETYPE, "WL6");
	} else if (s0 == true){
		strcpy(GAMETYPE, "SDM");
	} else {
		strcpy(GAMETYPE, "SOD");
	}

	fd = OpenWrite(fn);
	
	if (fd != -1) {
		WriteBytes(fd, (byte *)GAMEHDR, 8);
		WriteBytes(fd, (byte *)SAVTYPE, 4);
		WriteInt32(fd, 0x00000000); /* write version */
		WriteBytes(fd, (byte *)GAMETYPE, 4);
	
		WriteInt32(fd, time(NULL));
		WriteInt32(fd, 0x00000000);
	
		WriteInt32(fd, 0x00000000); /* write checksum (placeholder) */
	
		WriteBytes(fd, (byte *)tag, 32); /* write savegame name */
	
		DiskFlopAnim(dx, dy);
	
		WriteInt32(fd, gamestate.difficulty);
		WriteInt32(fd, gamestate.mapon);
		WriteInt32(fd, gamestate.oldscore);
		WriteInt32(fd, gamestate.score);
		WriteInt32(fd, gamestate.nextextra);
		WriteInt32(fd, gamestate.lives);
		WriteInt32(fd, gamestate.health);
		WriteInt32(fd, gamestate.ammo);
		WriteInt32(fd, gamestate.keys);
		WriteInt32(fd, gamestate.bestweapon);
		WriteInt32(fd, gamestate.weapon);
		WriteInt32(fd, gamestate.chosenweapon);
		WriteInt32(fd, gamestate.faceframe);
		WriteInt32(fd, gamestate.attackframe);
		WriteInt32(fd, gamestate.attackcount);
		WriteInt32(fd, gamestate.weaponframe);
		WriteInt32(fd, gamestate.episode);
		WriteInt32(fd, gamestate.secretcount);
		WriteInt32(fd, gamestate.treasurecount);
		WriteInt32(fd, gamestate.killcount);
		WriteInt32(fd, gamestate.secrettotal);
		WriteInt32(fd, gamestate.treasuretotal);
		WriteInt32(fd, gamestate.killtotal);
		WriteInt32(fd, gamestate.TimeCount);
		WriteInt32(fd, gamestate.killx);
		WriteInt32(fd, gamestate.killy);
		WriteInt8(fd, gamestate.victoryflag);
	
		DiskFlopAnim(dx, dy);
	
		if (s0 == true || s1 == true || s2 == true || s3 == true){
			for (i = 0; i < 20; i++) {
				WriteInt32(fd, LevelRatiosSOD[i].kill);
				WriteInt32(fd, LevelRatiosSOD[i].secret);
				WriteInt32(fd, LevelRatiosSOD[i].treasure);
				WriteInt32(fd, LevelRatiosSOD[i].time);
			}
		} else {
			for (i = 0; i < 8; i++) {
				WriteInt32(fd, LevelRatiosWL6[i].kill);
				WriteInt32(fd, LevelRatiosWL6[i].secret);
				WriteInt32(fd, LevelRatiosWL6[i].treasure);
				WriteInt32(fd, LevelRatiosWL6[i].time);
			}
		}
	
		DiskFlopAnim(dx, dy);
	
		WriteBytes(fd, (byte *)tilemap, 64*64); /* MAPSIZE * MAPSIZE */
	
		DiskFlopAnim(dx, dy);
	
		for (x = 0; x < 64; x++)
			for (y = 0; y < 64; y++)
				WriteInt32(fd, actorat[x][y]);
	
		DiskFlopAnim(dx, dy);
			
		WriteBytes(fd, (byte *)areaconnect, 37*37); /* NUMAREAS * NUMAREAS */
	
		DiskFlopAnim(dx, dy);
	
		for (i = 0; i < 37; i++)
			WriteInt8(fd, areabyplayer[i]);
	
		for (ob = player; ob; ob = ob->next) {
			DiskFlopAnim(dx, dy);
			
			WriteInt32(fd, ob->id);
			WriteInt32(fd, ob->active);
			WriteInt32(fd, ob->ticcount);
			WriteInt32(fd, ob->obclass);
			WriteInt32(fd, ob->state);
			WriteInt8(fd,  ob->flags);
			WriteInt32(fd, ob->distance);
			WriteInt32(fd, ob->dir);
			WriteInt32(fd, ob->x);
			WriteInt32(fd, ob->y);
			WriteInt32(fd, ob->tilex);
			WriteInt32(fd, ob->tiley);
			WriteInt8(fd,  ob->areanumber);
			WriteInt32(fd, ob->viewx);
			WriteInt32(fd, ob->viewheight);
			WriteInt32(fd, ob->transx);
			WriteInt32(fd, ob->transy);
			WriteInt32(fd, ob->angle);
			WriteInt32(fd, ob->hitpoints);
			WriteInt32(fd, ob->speed);
			WriteInt32(fd, ob->temp1);
			WriteInt32(fd, ob->temp2);
			WriteInt32(fd, ob->temp3);
		}	
		
		WriteInt32(fd, 0xFFFFFFFF); /* end of actor list */
		
		DiskFlopAnim(dx, dy);
		
		WriteInt32(fd, laststatobj - statobjlist); /* ptr offset */

		for (i = 0; i < 400; i++) { /* MAXSTATS */
			WriteInt8(fd,  statobjlist[i].tilex);
			WriteInt8(fd,  statobjlist[i].tiley);
			WriteInt32(fd, statobjlist[i].shapenum);
			WriteInt8(fd,  statobjlist[i].flags);
			WriteInt8(fd,  statobjlist[i].itemnumber);
		}
	
		DiskFlopAnim(dx, dy);
	
		for (i = 0; i < 64; i++) { /* MAXDOORS */
			WriteInt32(fd, doorposition[i]);
		}
	
		DiskFlopAnim(dx, dy);
	
		for (i = 0; i < 64; i++) { /* MAXDOORS */
			WriteInt8(fd,  doorobjlist[i].tilex);
			WriteInt8(fd,  doorobjlist[i].tiley);
			WriteInt8(fd,  doorobjlist[i].vertical);
			WriteInt8(fd,  doorobjlist[i].lock);
			WriteInt8(fd,  doorobjlist[i].action);
			WriteInt32(fd, doorobjlist[i].ticcount);
		}
	
		DiskFlopAnim(dx, dy);
	
		WriteInt32(fd, pwallstate);
		WriteInt32(fd, pwallx);
		WriteInt32(fd, pwally);
		WriteInt32(fd, pwalldir);
		WriteInt32(fd, pwallpos);

		DiskFlopAnim(dx, dy);

		CloseWrite(fd);

		fd = OpenRead(fn);
		ReadSeek(fd, 64, SEEK_SET);
		cs = CalcFileChecksum(fd, ReadLength(fd) - 64);
		CloseRead(fd);
		
		fd = OpenWriteAppend(fn);
		WriteSeek(fd, 28, SEEK_SET);
		WriteInt32(fd, cs);
		
		CloseWrite(fd);
	} else {
		Message(STR_NOSPACE1"\n"
			STR_NOSPACE2);
			
		IN_ClearKeysDown();
		IN_Ack();
		
		return -1;
	}
	
	return 0;
}

int ReadSaveTag(const char *fn, const char *tag)
{
	char buf[8];
	int fd;
	int32_t v;

	char GAMETYPE[4];

	if (w0 == true){
		strcpy(GAMETYPE, "WL1");
	} else if (w1 == true){
		strcpy(GAMETYPE, "WL6");
	} else if (s0 == true){
		strcpy(GAMETYPE, "SDM");
	} else {
		strcpy(GAMETYPE, "SOD");
	}

	fd = OpenRead(fn);
	if (fd == -1)
		goto rstfail;
	
	ReadBytes(fd, (byte *)buf, 8);
	if (strncmp(buf, GAMEHDR, 8))
		goto rstfail;
	
	ReadBytes(fd, (byte *)buf, 4);
	if (strncmp(buf, SAVTYPE, 4))
		goto rstfail;
	
	v = ReadInt32(fd);
	if (v != 0xFFFFFFFF && v != 0x00000000) /* -1 and 0 are the same */
		goto rstfail;
	
	ReadBytes(fd, (byte *)buf, 4);
	if (strncmp(buf, GAMETYPE, 4))
		goto rstfail;
	
	ReadInt32(fd);
	ReadInt32(fd);
	
	v = ReadInt32(fd); /* get checksum */
	
	ReadSeek(fd, 64, SEEK_SET);
	if (v != CalcFileChecksum(fd, ReadLength(fd) - 64))
		goto rstfail;
	
	ReadSeek(fd, 32, SEEK_SET);
	ReadBytes(fd, (byte *)tag, 32);
		
	CloseRead(fd);
	
	return 0;
rstfail:
	if (fd != -1)
		CloseRead(fd);
	
	return -1;
}

int LoadTheGame(const char *fn, int dx, int dy)
{
	char buf[8];
	char GAMETYPE[4];

	if (w0 == true){
		strcpy(GAMETYPE, "WL1");
	} else if (w1 == true){
		strcpy(GAMETYPE, "WL6");
	} else if (s0 == true){
		strcpy(GAMETYPE, "SDM");
	} else {
		strcpy(GAMETYPE, "SOD");
	}

	int fd, i, x, y, id;
	int32_t v;

	fd = OpenRead(fn);

	if (fd == -1)
		goto loadfail;
	
	ReadBytes(fd, (byte *)buf, 8);
	if (strncmp(buf, GAMEHDR, 8))
		goto loadfail;
	
	ReadBytes(fd, (byte *)buf, 4);
	if (strncmp(buf, SAVTYPE, 4))
		goto loadfail;
	
	v = ReadInt32(fd);
	if (v != 0xFFFFFFFF && v != 0x00000000) /* -1 and 0 are the same */
		goto loadfail;
	
	ReadBytes(fd, (byte *)buf, 4);
	if (strncmp(buf, GAMETYPE, 4))
		goto loadfail;
	
	ReadInt32(fd);
	ReadInt32(fd);
	
	v = ReadInt32(fd); /* get checksum */
	
	ReadSeek(fd, 64, SEEK_SET);
	if (v != CalcFileChecksum(fd, ReadLength(fd) - 64))
		goto loadfail;
	
	ReadSeek(fd, 64, SEEK_SET);
	
	DiskFlopAnim(dx, dy);
	
	gamestate.difficulty	= ReadInt32(fd);
	gamestate.mapon		= ReadInt32(fd);
	gamestate.oldscore	= ReadInt32(fd);
	gamestate.score		= ReadInt32(fd);
	gamestate.nextextra	= ReadInt32(fd);
	gamestate.lives		= ReadInt32(fd);
	gamestate.health	= ReadInt32(fd);
	gamestate.ammo		= ReadInt32(fd);
	gamestate.keys		= ReadInt32(fd);
	gamestate.bestweapon	= ReadInt32(fd);
	gamestate.weapon	= ReadInt32(fd);
	gamestate.chosenweapon	= ReadInt32(fd);
	gamestate.faceframe	= ReadInt32(fd);
	gamestate.attackframe	= ReadInt32(fd);
	gamestate.attackcount	= ReadInt32(fd);
	gamestate.weaponframe	= ReadInt32(fd);
	gamestate.episode	= ReadInt32(fd);
	gamestate.secretcount	= ReadInt32(fd);
	gamestate.treasurecount	= ReadInt32(fd);
	gamestate.killcount	= ReadInt32(fd);
	gamestate.secrettotal	= ReadInt32(fd);
	gamestate.treasuretotal = ReadInt32(fd);
	gamestate.killtotal	= ReadInt32(fd);
	gamestate.TimeCount	= ReadInt32(fd);
	gamestate.killx		= ReadInt32(fd);
	gamestate.killy		= ReadInt32(fd);
	gamestate.victoryflag	= ReadInt8(fd);
	
	DiskFlopAnim(dx, dy);
	
	if (s0 == true || s1 == true || s2 == true || s3 == true){
		for (i = 0; i < 20; i++) {
			LevelRatiosSOD[i].kill	= ReadInt32(fd);
			LevelRatiosSOD[i].secret	= ReadInt32(fd);
			LevelRatiosSOD[i].treasure	= ReadInt32(fd);
			LevelRatiosSOD[i].time	= ReadInt32(fd);
		}
	} else {
		for (i = 0; i < 8; i++) {
			LevelRatiosWL6[i].kill	= ReadInt32(fd);
			LevelRatiosWL6[i].secret	= ReadInt32(fd);
			LevelRatiosWL6[i].treasure	= ReadInt32(fd);
			LevelRatiosWL6[i].time	= ReadInt32(fd);
		}
	}
	
	DiskFlopAnim(dx, dy);
	
	SetupGameLevel();
	
	DiskFlopAnim(dx, dy);
	
	ReadBytes(fd, (byte *)tilemap, 64*64); /* MAPSIZE * MAPSIZE */
	
	DiskFlopAnim(dx, dy);
	
	for (x = 0; x < 64; x++)
		for (y = 0; y < 64; y++)
			actorat[x][y] = ReadInt32(fd);
	
	DiskFlopAnim(dx, dy);
			
	ReadBytes(fd, (byte *)areaconnect, 37*37); /* NUMAREAS * NUMAREAS */
	
	DiskFlopAnim(dx, dy);
	
	for (i = 0; i < 37; i++)
		areabyplayer[i] = ReadInt8(fd);
	
	DiskFlopAnim(dx, dy);
	
	InitActorList();
	
	DiskFlopAnim(dx, dy);
	
	/* player ptr already set up */
	id			= ReadInt32(fd); /* get id */
	player->active		= ReadInt32(fd);
	player->ticcount	= ReadInt32(fd);
	player->obclass		= ReadInt32(fd);
	player->state		= ReadInt32(fd);
	player->flags		= ReadInt8(fd);
	player->distance	= ReadInt32(fd);
	player->dir		= ReadInt32(fd);
	player->x		= ReadInt32(fd);
	player->y		= ReadInt32(fd);
	player->tilex		= ReadInt32(fd);
	player->tiley		= ReadInt32(fd);
	player->areanumber	= ReadInt8(fd);
	player->viewx		= ReadInt32(fd);
	player->viewheight	= ReadInt32(fd);
	player->transx		= ReadInt32(fd);
	player->transy		= ReadInt32(fd);
	player->angle		= ReadInt32(fd);
	player->hitpoints	= ReadInt32(fd);
	player->speed		= ReadInt32(fd);
	player->temp1		= ReadInt32(fd);
	player->temp2		= ReadInt32(fd);
	player->temp3		= ReadInt32(fd);
	
	/* update the id */
	for (x = 0; x < 64; x++)
		for (y = 0; y < 64; y++)
			if (actorat[x][y] == (id | 0x8000))
				actorat[x][y] = player->id | 0x8000;

	while (1) {
		DiskFlopAnim(dx, dy);
		
		id			= ReadInt32(fd);
		
		if (id == 0xFFFFFFFF)
			break;
		
		GetNewActor();
		
		new->active		= ReadInt32(fd);
		new->ticcount		= ReadInt32(fd);
		new->obclass		= ReadInt32(fd);
		new->state		= ReadInt32(fd);
		new->flags		= ReadInt8(fd);
		new->distance		= ReadInt32(fd);
		new->dir		= ReadInt32(fd);
		new->x			= ReadInt32(fd);
		new->y			= ReadInt32(fd);
		new->tilex		= ReadInt32(fd);
		new->tiley		= ReadInt32(fd);
		new->areanumber		= ReadInt8(fd);
		new->viewx		= ReadInt32(fd);
		new->viewheight		= ReadInt32(fd);
		new->transx		= ReadInt32(fd);
		new->transy		= ReadInt32(fd);
		new->angle		= ReadInt32(fd);
		new->hitpoints		= ReadInt32(fd);
		new->speed		= ReadInt32(fd);
		new->temp1		= ReadInt32(fd);
		new->temp2		= ReadInt32(fd);
		new->temp3		= ReadInt32(fd);
		
		for (x = 0; x < 64; x++)
			for (y = 0; y < 64; y++)
				if (actorat[x][y] == (id | 0x8000))
					actorat[x][y] = new->id | 0x8000;
	}
	
	DiskFlopAnim(dx, dy);
	
	laststatobj = statobjlist + ReadInt32(fd); /* ptr offset */
	for (i = 0; i < 400; i++) { /* MAXSTATS */
		statobjlist[i].tilex		= ReadInt8(fd);
		statobjlist[i].tiley		= ReadInt8(fd);
		statobjlist[i].shapenum		= ReadInt32(fd);
		statobjlist[i].flags		= ReadInt8(fd);
		statobjlist[i].itemnumber	= ReadInt8(fd);
		statobjlist[i].visspot 		= &spotvis[statobjlist[i].tilex][statobjlist[i].tiley];
	}
	
	DiskFlopAnim(dx, dy);
	
	for (i = 0; i < 64; i++) { /* MAXDOORS */
		doorposition[i] 		= ReadInt32(fd);
	}
	
	DiskFlopAnim(dx, dy);
	
	for (i = 0; i < 64; i++) { /* MAXDOORS */
		doorobjlist[i].tilex	= ReadInt8(fd);
		doorobjlist[i].tiley	= ReadInt8(fd);
		doorobjlist[i].vertical = ReadInt8(fd);
		doorobjlist[i].lock	= ReadInt8(fd);
		doorobjlist[i].action	= ReadInt8(fd);
		doorobjlist[i].ticcount	= ReadInt32(fd);
	}
	
	DiskFlopAnim(dx, dy);
	
	pwallstate 	= ReadInt32(fd);
	pwallx		= ReadInt32(fd);
	pwally		= ReadInt32(fd);
	pwalldir	= ReadInt32(fd);
	pwallpos	= ReadInt32(fd);

	DiskFlopAnim(dx, dy);
	
	CloseRead(fd);	
	
	return 0;
	
loadfail:
	if (fd != -1)
		CloseRead(fd);
		
	Message(STR_SAVECHT1"\n"
		STR_SAVECHT2"\n"
		STR_SAVECHT3"\n"
		STR_SAVECHT4);
			
	IN_ClearKeysDown();
	IN_Ack();
	
	NewGame(1, 0);
	return -1;
}

/* ======================================================================== */

/*
=================
=
= MS_CheckParm
=
=================
*/

int MS_CheckParm(const char *check)
{
	int i;
	char *parm;

	for (i = 1; i < _argc; i++) {
		parm = _argv[i];
		
		while (!isalpha(*parm))		// skip - / \ etc.. in front of parm
			if (!*parm++)
				break;		// hit end of string without an alphanum
		
		if (!stricmp(check, parm))
			return i;
	}
	return 0;
}

/* ======================================================================== */

/*
==================
=
= BuildTables
=
==================
*/

static const double radtoint = (double)FINEANGLES/2.0/PI;

void BuildTables()
{
	int i;
	double tang, angle, anglestep;
	fixed value;

/* calculate fine tangents */

	finetangent[0] = 0;
	for (i = 1; i < FINEANGLES/8; i++) {
		tang = tan((double)i/radtoint);
		finetangent[i] = tang*TILEGLOBAL;
		finetangent[FINEANGLES/4-1-i] = TILEGLOBAL/tang;
	}
	
	/* fight off asymptotic behaviour at 90 degrees */
	finetangent[FINEANGLES/4-1] = finetangent[FINEANGLES/4-2]+1;
	
//
// costable overlays sintable with a quarter phase shift
// ANGLES is assumed to be divisable by four
//

	angle = 0.0;
	anglestep = PI/2.0/ANGLEQUAD;
	for (i = 0; i <= ANGLEQUAD; i++) {
		value = GLOBAL1*sin(angle);
		
		sintable[i] = 
		sintable[i+ANGLES] =  
		sintable[ANGLES/2-i] = value;
		
		sintable[ANGLES-i] =
		sintable[ANGLES/2+i] = -value;
		
		angle += anglestep;
/*
jni_printf("angle=%.2f, sintable[%d,%d,%d]=%ld, sintable[%d, %d]=%ld\n"
	, angle, i, i+ANGLES, ANGLES/2-i, value, ANGLES-i, ANGLES/2+i, -value ); */

	}
	// Vladimir
	costable = sintable+(ANGLES/4);
/*
	for (i = 0; i <= 360; i++) {
		jni_printf("costable[%d]=%ld\n", i, costable[i]);
	} */
}

/*
===================
=
= SetupWalls
=
= Map tile values to scaled pics
=
===================
*/

void SetupWalls()
{
	int i;

	for (i=1;i<MAXWALLTILES;i++)
	{
		horizwall[i]=(i-1)*2;
		vertwall[i]=(i-1)*2+1;
	}
}

void ShowViewSize(int width)
{
	int oldwidth,oldheight;

	oldwidth = viewwidthwin;
	oldheight = viewheightwin;

	viewwidthwin = width*16;
	viewheightwin = width*16*HEIGHTRATIO;
	DrawPlayBorder();

	viewheightwin = oldheight;
	viewwidthwin = oldwidth;
}

void NewViewSize(int width)
{
	if (width > 20)
		width = 20;
	if (width < 4)
		width = 4;	
	
	width *= vwidth / 320;
	
	if ((width*16) > vwidth)
		width = vwidth / 16;
	
	if ((width*16*HEIGHTRATIO) > (vheight - 40*vheight/200))
		width = (vheight - 40*vheight/200)/8;
	
	viewwidthwin = width*16*320/vwidth;
	viewheightwin = width*16*HEIGHTRATIO*320/vwidth;
	viewsize = width*320/vwidth;
	
	viewwidth = width*16;
	viewheight = width*16*HEIGHTRATIO;
	
	centerx = viewwidth/2-1;
	shootdelta = viewwidth/10;
	
	yoffset = (vheight-STATUSLINES*vheight/200-viewheight)/2;
	xoffset = (vwidth-viewwidth)/2;
	
//
// calculate trace angles and projection constants
//
	CalcProjection(FOCALLENGTH);

}

//===========================================================================

CP_iteminfo MusicItemsWL6={CTL_X,CTL_Y,6,0,32};
CP_itemtype MusicMenuWL6[]=
{
	{1,"Get Them!",0},
	{1,"Searching",0},
	{1,"P.O.W.",0},
	{1,"Suspense",0},
	{1,"War March",0},
	{1,"Around The Corner!",0},

	{1,"Nazi Anthem",0},
	{1,"Lurking...",0},
	{1,"Going After Hitler",0},
	{1,"Pounding Headache",0},
	{1,"Into the Dungeons",0},
	{1,"Ultimate Conquest",0},

	{1,"Kill the S.O.B.",0},
	{1,"The Nazi Rap",0},
	{1,"Twelfth Hour",0},
	{1,"Zero Hour",0},
	{1,"Ultimate Conquest",0},
	{1,"Wolfpack",0}
};

CP_iteminfo MusicItemsSOD={CTL_X,CTL_Y-20,9,0,32};
CP_itemtype MusicMenuSOD[]=
{
	{1,"Funky Colonel Bill",0},
	{1,"Death To The Nazis",0},
	{1,"Tiptoeing Around",0},
	{1,"Is This THE END?",0},
	{1,"Evil Incarnate",0},
	{1,"Jazzin' Them Nazis",0},
	{1,"Puttin' It To The Enemy",0},
	{1,"The SS Gonna Get You",0},
	{1,"Towering Above",0}
};

static const int songsWL6[] =
{
	GETTHEM_MUSWL6,
	SEARCHN_MUSWL6,
	POW_MUSWL6,
	SUSPENSE_MUSWL6,
	WARMARCH_MUS,
	CORNER_MUS,

	NAZI_OMI_MUSWL6,
	PREGNANT_MUS,
	GOINGAFT_MUSWL6,
	HEADACHE_MUS,
	DUNGEON_MUSWL6,
	ULTIMATE_MUSWL6,

	INTROCW3_MUS,
	NAZI_RAP_MUS,
	TWELFTH_MUSWL6,
	ZEROHOUR_MUSWL6,
	ULTIMATE_MUSWL6,
	PACMAN_MUS
};

static const int songsSOD[] =
{
	XFUNKIE_MUS,             // 0
	XDEATH_MUS,              // 2
	XTIPTOE_MUS,             // 4
	XTHEEND_MUS,             // 7
	XEVIL_MUS,               // 17
	XJAZNAZI_MUS,            // 18
	XPUTIT_MUS,              // 21
	XGETYOU_MUS,             // 22
	XTOWER2_MUS              // 23
};
		
void DoJukebox()
{
	int which,lastsong=-1;
	unsigned start;

	IN_ClearKeysDown();
	if (!AdLibPresent && !SoundBlasterPresent)
		return;
	
	if (w0 == true || w1 == true){
		MenuFadeOutWL6();
	} else {
		MenuFadeOutSOD();
	}

	if (w1 == true){
		start = (US_RndT() % 3) * 6;
	} else {
		start = 0;
	}

	CA_CacheGrChunk(STARTFONT+1);

	if (w0 == true){
		CacheLump(CONTROLS_LUMP_STARTWL1, CONTROLS_LUMP_ENDWL1);
	} else if (w1 == true){
		CacheLump(CONTROLS_LUMP_STARTWL6, CONTROLS_LUMP_ENDWL6);
	} else {
		CacheLump(BACKDROP_LUMP_START, BACKDROP_LUMP_END);
	}

	CA_LoadAllSounds();

	fontnumber=1;
	ClearMScreen();
	VWB_DrawPic(112,184,C_MOUSELBACKPIC);
	DrawStripes(10);
	if (w0 == true || w1 == true){
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORWL6);
		DrawWindow (CTL_X-2,CTL_Y-6,280,13*7,BKGDCOLORWL6);
		DrawMenu (&MusicItemsWL6,&MusicMenuWL6[start]);
		SETFONTCOLOR(READHCOLOR,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORSOD);
		DrawWindow (CTL_X-2,CTL_Y-26,280,13*10,BKGDCOLORSOD);
		DrawMenu (&MusicItemsSOD,&MusicMenuSOD[start]);
		SETFONTCOLOR(READHCOLOR,BKGDCOLORSOD);
	}

	PrintY = 15;
	WindowX = 0;
	WindowY = 320;
	US_CPrint("Robert's Jukebox");

	if (w0 == true || w1 == true){
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORWL6);
	} else {
		SETFONTCOLOR(TEXTCOLOR,BKGDCOLORSOD);
	}
	VW_UpdateScreen();
	if (w0 == true || w1 == true){
		MenuFadeInWL6();
	} else {
		MenuFadeInSOD();
	}

	do
	{
		if (w0 == true || w1 == true){
			which = HandleMenu(&MusicItemsWL6,&MusicMenuWL6[start],NULL);
		} else {
			which = HandleMenu(&MusicItemsSOD,&MusicMenuSOD[start],NULL);
		}
		if (which>=0)
		{
			if (lastsong >= 0){
				if (w0 == true || w1 == true){
					MusicMenuWL6[start+lastsong].active = 1;
				} else {
					MusicMenuSOD[start+lastsong].active = 1;
				}
			}

			if (w0 == true || w1 == true){
				StartCPMusic(songsWL6[start + which]);
			} else {
				StartCPMusic(songsSOD[start + which]);
			}
			if (w0 == true || w1 == true){
				MusicMenuWL6[start+which].active = 2;
			} else {
				MusicMenuSOD[start+which].active = 2;
			}
			if (w0 == true || w1 == true){
				DrawMenu (&MusicItemsWL6,&MusicMenuWL6[start]);
			} else {
				DrawMenu (&MusicItemsSOD,&MusicMenuSOD[start]);
			}
			VW_UpdateScreen();
			lastsong = which;
		}
	} while(which>=0);

	if (w0 == true || w1 == true){
		MenuFadeOutWL6();
	} else {
		MenuFadeOutSOD();
	}
	IN_ClearKeysDown();
	if (w0 == true){
		UnCacheLump(CONTROLS_LUMP_STARTWL1, CONTROLS_LUMP_ENDWL1);
	} else if (w1 == true){
		UnCacheLump(CONTROLS_LUMP_STARTWL6, CONTROLS_LUMP_ENDWL6);
	} else {
		UnCacheLump(BACKDROP_LUMP_START, BACKDROP_LUMP_END);
	}
}

/* ======================================================================== */

/*
==========================
=
= ShutdownId
=
= Shuts down all ID_?? managers
=
==========================
*/

void ShutdownId()
{
	US_Shutdown();
	SD_Shutdown();
	IN_Shutdown();
	VW_Shutdown();
	CA_Shutdown();
	PM_Shutdown();
}

/*
=====================
=
= NewGame
=
= Set up new game to start from the beginning
=
=====================
*/

void NewGame(int difficulty, int episode)
{
	memset(&gamestate, 0, sizeof(gamestate));
	
	gamestate.difficulty = difficulty;
	gamestate.weapon = gamestate.bestweapon
		= gamestate.chosenweapon = wp_pistol;
	gamestate.health = 100;
	gamestate.ammo = STARTAMMO;
	gamestate.lives = 3;
	gamestate.nextextra = EXTRAPOINTS;
	gamestate.episode = episode;

	startgame = true;
}

/*
==========================
=
= InitGame
=
= Load a few things right away
=
==========================
*/

void InitGame()
{
	int i;

	PM_Startup();
	CA_Startup();
	VW_Startup();
	IN_Startup();
	SD_Startup();
	US_Startup();
//
// build some tables
//

	for (i = 0;i < MAPSIZE; i++)
	{
		farmapylookup[i] = i*64;
	}

	ReadConfig();

/* load in and lock down some basic chunks */

	CA_CacheGrChunk(STARTFONT);
	if (w0 == true){
		CA_CacheGrChunk(STARTTILE8WL1);
		for (i = LATCHPICS_LUMP_STARTWL1; i <= LATCHPICS_LUMP_ENDWL1; i++)
			CA_CacheGrChunk(i);
	} else if (w1 == true){
		CA_CacheGrChunk(STARTTILE8WL6);
		for (i = LATCHPICS_LUMP_STARTWL6; i <= LATCHPICS_LUMP_ENDWL6; i++)
			CA_CacheGrChunk(i);
	} else if (s0 == true){
		CA_CacheGrChunk(STARTTILE8SDM);
		for (i = LATCHPICS_LUMP_STARTSDM; i <= LATCHPICS_LUMP_ENDSDM; i++)
			CA_CacheGrChunk(i);
	} else if (s1 == true || s2 == true || s3 == true){
		CA_CacheGrChunk(STARTTILE8SOD);
		for (i = LATCHPICS_LUMP_STARTSOD; i <= LATCHPICS_LUMP_ENDSOD; i++)
			CA_CacheGrChunk(i);
	}

	BuildTables();
	SetupWalls();

	NewViewSize(viewsize);


//
// initialize variables
//
	InitRedShifts();

	IN_CheckAck();
//
// HOLDING DOWN 'M' KEY?
//
	if (s0 != true){
		if (IN_KeyDown(sc_M))
			DoJukebox();
	}
}

/*
=====================
=
= DemoLoop
=
=====================
*/

void DemoLoop()
{
	static int LastDemo;

	int i;
//
// main game cycle
//

	LastDemo = 0;

	if (w0 == true || w1 == true){
		StartCPMusic(INTROSONGWL6);
	} else {
		StartCPMusic(INTROSONGSOD);
	}

	if (!NoWait)
		PG13();
	
	i = MS_CheckParm("playdemo");
	if (i && ((i+1) < _argc)) {
		i++;
		for (; i < _argc; i++) {
			if (_argv[i][0] == '-')
				break;
			IN_ClearKeysDown();
			if (PlayDemoFromFile(_argv[i]))
				IN_UserInput(3 * 70);
		}
		VW_FadeOut();
	}
	
	if (MS_CheckParm("demotest")) {
		if (s0 == true){
			for (i = 0; i < 4; i++)
				PlayDemo(i);
		} else {
			PlayDemo(0);
		}
	}
	
	while (1)
	{
		while (!NoWait)
		{
//
// title page
//
			MM_SortMem ();
			if (s0 == true || s1 == true || s2 == true || s3 == true){
				CA_CacheGrChunk (TITLEPALETTE);
	
				CA_CacheGrChunk (TITLE1PIC);
				VWB_DrawPic (0,0,TITLE1PIC);
				CA_UnCacheGrChunk (TITLE1PIC);
	
				CA_CacheGrChunk (TITLE2PIC);
				VWB_DrawPic (0,80,TITLE2PIC);
				CA_UnCacheGrChunk(TITLE2PIC);
				VW_UpdateScreen();
				if (s0 == true){
					VL_FadeIn(0,255,grsegsSDM[TITLEPALETTE],30);
				} else {
					VL_FadeIn(0,255,grsegsSOD[TITLEPALETTE],30);
				}
	
				CA_UnCacheGrChunk (TITLEPALETTE);
			} else {
				VL_CacheScreen(TITLEPIC);
				VW_UpdateScreen ();
				if (w0 == true || w1 == true){
					VW_FadeInWL6();
				} else {
					VW_FadeInSOD();
				}
			}
			if (IN_UserInput(TickBase*15))
				break;
			VW_FadeOut();
//
// credits page
//
			VL_CacheScreen(CREDITSPIC);
			VW_UpdateScreen();
			if (w0 == true || w1 == true){
				VW_FadeInWL6();
			} else {
				VW_FadeInSOD();
			}
			if (IN_UserInput(TickBase*10))
				break;
			VW_FadeOut ();
//
// high scores
//
			DrawHighScores();
			VW_UpdateScreen();
			if (w0 == true || w1 == true){
				VW_FadeInWL6();
			} else {
				VW_FadeInSOD();
			}

			if (IN_UserInput(TickBase*10))
				break;
//
// demo
//
			if (s0 != true){
				PlayDemo(LastDemo++%4);
			} else {
				PlayDemo(0);
			}

			if (playstate == ex_abort)
				break;
			if (w0 == true || w1 == true){
				StartCPMusic(INTROSONGWL6);
			} else {
				StartCPMusic(INTROSONGSOD);
			}
		}

		VW_FadeOut();

		if (IN_KeyDown(sc_Tab) && MS_CheckParm("debugmode"))
			RecordDemo();
		else
			US_ControlPanel(0);

		if (startgame || loadedgame)
		{
			GameLoop();
			VW_FadeOut();
			if (w0 == true || w1 == true){
				StartCPMusic(INTROSONGWL6);
			} else {
				StartCPMusic(INTROSONGSOD);
			}
		}
	}
}


/* ======================================================================== */


/*
==========================
=
= WolfMain
=
==========================
*/
extern void jni_printf(char *format, ...);
extern char * basedir;

int WolfMain(int argc, char *argv[])
{
	_argc = argc;
	_argv = argv;

	// Base dir
	int p = MS_CheckParm("basedir");
	if ( p ) {
		basedir = argv[++p];
	}
	else {
		basedir = "./";
	}

	jni_printf("Now Loading %s basedir %s", GAMENAME, basedir);
	
	// default is SW wolf
	w1 = true;
/*
	glob_t globbuf;

	if (glob("*.wl6", 0, NULL, &globbuf) == 0) {w1=true;}
	if (glob("*.sod", 0, NULL, &globbuf) == 0) {s1=true;}
	if (glob("*.sd2", 0, NULL, &globbuf) == 0) {s2=true;}
	if (glob("*.sd3", 0, NULL, &globbuf) == 0) {s3=true;}
	if (glob("*.sdm", 0, NULL, &globbuf) == 0) {s0=true;}
	if (glob("*.wl1", 0, NULL, &globbuf) == 0) {w0=true;}

	globfree(&globbuf);
*/
	if (w1 == false && s1 == false && s2 == false && s3 == false && s0 == false && w0 == false)
	{
		Quit("NO WOLFENSTEIN 3-D DATA FILES TO BE FOUND!");
	}

	if (MS_CheckParm("wolf")) {
		jni_printf("You Chose Full Wolf3D");
		w0 = false;
		w1 = true;
		s0 = false;
		s1 = false;
		s2 = false;
		s3 = false;
	}

	if (MS_CheckParm("wolfsw")) {
		jni_printf("You Chose Shareware Wolf3D");
		w0 = true;
		w1 = false;
		s0 = false;
		s1 = false;
		s2 = false;
		s3 = false;
	}

	if (MS_CheckParm("sodemo")) {
		jni_printf("You Chose Shareware Spear of Destiny");
		w0 = false;
		w1 = false;
		s0 = true;
		s1 = false;
		s2 = false;
		s3 = false;
	}

	if (MS_CheckParm("sod")) {
		jni_printf("You Chose Full Spear of Destiny");
		w0 = false;
		s0 = false;
		w1 = false;
		s1 = true;
		s2 = false;
		s3 = false;
	}

	if (MS_CheckParm("sodm2")) {
		jni_printf("You Chose Full Spear of Destiny Mission 2");
		s2 = true;
		w0 = false;
		s0 = false;
		s1 = false;
		w1 = false;
		s3 = false;
	}
	
	if (MS_CheckParm("sodm3")) {
		jni_printf("You Chose Full Spear of Destiny Mission 3");
		w0 = false;
		s0 = false;
		s1 = false;
		s2 = false;
		w1 = false;
		s3 = true;
	}

	CheckForEpisodes();

	SetupGFXV();

	InitGame();

	DemoLoop();

	Quit("Demo loop exited???");

	return 0;
}
