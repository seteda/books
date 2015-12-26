typedef enum {
		// Lump Start
		C_BACKDROPPICSDM=3,
		C_MOUSELBACKPICSDM,                     // 4
		C_CURSOR1PICSDM,                        // 5
		C_CURSOR2PICSDM,                        // 6
		C_NOTSELECTEDPICSDM,                    // 7
		C_SELECTEDPICSDM,                       // 8
		// Lump Start
		C_CUSTOMIZEPICSDM,                      // 9
		C_JOY1PICSDM,                           // 10
		C_JOY2PICSDM,                           // 11
		C_MOUSEPICSDM,                          // 12
		C_JOYSTICKPICSDM,                       // 13
		C_KEYBOARDPICSDM,                       // 14
		C_CONTROLPICSDM,                        // 15
		// Lump Start
		C_OPTIONSPICSDM,                        // 16
		// Lump Start
		C_FXTITLEPICSDM,                        // 17
		C_DIGITITLEPICSDM,                      // 18
		C_MUSICTITLEPICSDM,                     // 19
		// Lump Start
		C_HOWTOUGHPICSDM,                       // 20
		C_BABYMODEPICSDM,                       // 21
		C_EASYPICSDM,                           // 22
		C_NORMALPICSDM,                         // 23
		C_HARDPICSDM,                           // 24
		// Lump Start
		C_DISKLOADING1PICSDM,                   // 25
		C_DISKLOADING2PICSDM,                   // 26
		C_LOADGAMEPICSDM,                       // 27
		C_SAVEGAMEPICSDM,                       // 28
		// Lump Start
		HIGHSCORESPICSDM,                       // 29
		C_WONSPEARPICSDM,                       // 30
		// Lump Start
		L_GUYPICSDM,                            // 31
		L_COLONPICSDM,                          // 32
		L_NUM0PICSDM,                           // 33
		L_NUM1PICSDM,                           // 34
		L_NUM2PICSDM,                           // 35
		L_NUM3PICSDM,                           // 36
		L_NUM4PICSDM,                           // 37
		L_NUM5PICSDM,                           // 38
		L_NUM6PICSDM,                           // 39
		L_NUM7PICSDM,                           // 40
		L_NUM8PICSDM,                           // 41
		L_NUM9PICSDM,                           // 42
		L_PERCENTPICSDM,                        // 43
		L_APICSDM,                              // 44
		L_BPICSDM,                              // 45
		L_CPICSDM,                              // 46
		L_DPICSDM,                              // 47
		L_EPICSDM,                              // 48
		L_FPICSDM,                              // 49
		L_GPICSDM,                              // 50
		L_HPICSDM,                              // 51
		L_IPICSDM,                              // 52
		L_JPICSDM,                              // 53
		L_KPICSDM,                              // 54
		L_LPICSDM,                              // 55
		L_MPICSDM,                              // 56
		L_NPICSDM,                              // 57
		L_OPICSDM,                              // 58
		L_PPICSDM,                              // 59
		L_QPICSDM,                              // 60
		L_RPICSDM,                              // 61
		L_SPICSDM,                              // 62
		L_TPICSDM,                              // 63
		L_UPICSDM,                              // 64
		L_VPICSDM,                              // 65
		L_WPICSDM,                              // 66
		L_XPICSDM,                              // 67
		L_YPICSDM,                              // 68
		L_ZPICSDM,                              // 69
		L_EXPOINTPICSDM,                        // 70
		L_APOSTROPHEPICSDM,                     // 71
		L_GUY2PICSDM,                           // 72
		L_BJWINSPICSDM,                         // 73
		// Lump Start
		TITLE1PICSDM,                           // 74
		TITLE2PICSDM,                           // 75
		STATUSBARPICSDM,                        // 76
		PG13PICSDM,                             // 77
		CREDITSPICSDM,                          // 78
		// Lump Start
		KNIFEPICSDM,                            // 79
		GUNPICSDM,                              // 80
		MACHINEGUNPICSDM,                       // 81
		GATLINGGUNPICSDM,                       // 82
		NOKEYPICSDM,                            // 83
		GOLDKEYPICSDM,                          // 84
		SILVERKEYPICSDM,                        // 85
		N_BLANKPICSDM,                          // 86
		N_0PICSDM,                              // 87
		N_1PICSDM,                              // 88
		N_2PICSDM,                              // 89
		N_3PICSDM,                              // 90
		N_4PICSDM,                              // 91
		N_5PICSDM,                              // 92
		N_6PICSDM,                              // 93
		N_7PICSDM,                              // 94
		N_8PICSDM,                              // 95
		N_9PICSDM,                              // 96
		FACE1APICSDM,                           // 97
		FACE1BPICSDM,                           // 98
		FACE1CPICSDM,                           // 99
		FACE2APICSDM,                           // 100
		FACE2BPICSDM,                           // 101
		FACE2CPICSDM,                           // 102
		FACE3APICSDM,                           // 103
		FACE3BPICSDM,                           // 104
		FACE3CPICSDM,                           // 105
		FACE4APICSDM,                           // 106
		FACE4BPICSDM,                           // 107
		FACE4CPICSDM,                           // 108
		FACE5APICSDM,                           // 109
		FACE5BPICSDM,                           // 110
		FACE5CPICSDM,                           // 111
		FACE6APICSDM,                           // 112
		FACE6BPICSDM,                           // 113
		FACE6CPICSDM,                           // 114
		FACE7APICSDM,                           // 115
		FACE7BPICSDM,                           // 116
		FACE7CPICSDM,                           // 117
		FACE8APICSDM,                           // 118
		GOTGATLINGPICSDM,                       // 119
		GODMODEFACE1PICSDM,                     // 120
		GODMODEFACE2PICSDM,                     // 121
		GODMODEFACE3PICSDM,                     // 122
		BJWAITING1PICSDM,                       // 123
		BJWAITING2PICSDM,                       // 124
		BJOUCHPICSDM,                           // 125
		PAUSEDPICSDM,                           // 126
		GETPSYCHEDPICSDM,                       // 127



		ORDERSCREENSDM=129,
		ERRORSCREENSDM,                         // 130
		TITLEPALETTESDM,                        // 131
		T_DEMO0SDM,                             // 132
		ENUMENDSDM
	     } graphicnumsSDM;

//
// Data LUMPs
//

#define LEVELEND_LUMP_STARTSDM		31
#define LEVELEND_LUMP_ENDSDM		73

#define TITLESCREEN_LUMP_STARTSDM		74
#define TITLESCREEN_LUMP_ENDSDM		75

#define LATCHPICS_LUMP_STARTSDM		79
#define LATCHPICS_LUMP_ENDSDM		127


//
// Amount of each data item
//
#define NUMCHUNKSSDM    133
#define NUMPICSSDM      125
#define NUMEXTERNSSDM   4
//
// File offsets for data items
//
#define STARTTILE8SDM   128
#define STARTEXTERNSSDM 129
