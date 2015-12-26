#ifndef __WL_DEF_H__
#define __WL_DEF_H__

#include "id_heads.h"

#include "wl_menu.h"
#include "foreign.h"
// Vladimir #include "GP2XCONT.h"

extern int vwidth, vheight; /* size of screen */

#define NOASM

/*
=============================================================================

						 GLOBAL CONSTANTS

=============================================================================
*/

#define MAXACTORS		150		// max number of nazis, etc / map
#define MAXSTATS		400		// max number of lamps, bonus, etc
#define MAXDOORS		64		// max number of sliding doors
#define MAXWALLTILES		64		// max number of wall tiles

//
// tile constants
//

#define	ICONARROWS		90
#define PUSHABLETILE		98
#define EXITTILE		99		// at end of castle
#define AREATILE		107		// first of NUMAREAS floor tiles
#define NUMAREAS		37
#define ELEVATORTILE		21
#define AMBUSHTILE		106
#define	ALTELEVATORTILE		107

#define HEIGHTRATIO		0.50

//----------------

#define EXTRAPOINTS	40000

#define RUNSPEED   	6000

#define PLAYERSIZE	MINDIST		/* player radius */
#define MINACTORDIST	0x10000		/* minimum dist from player center */
					/* to any actor center */
#define TILESHIFT	16
#define UNSIGNEDSHIFT	(TILESHIFT-8)
#define GLOBAL1		(1<<TILESHIFT)
#define TILEGLOBAL  	GLOBAL1
#define VIEWGLOBAL	GLOBAL1

#define ANGLES		360		/* must be divisible by 4 */
#define ANGLEQUAD	(ANGLES/4)
#define FINEANGLES	3600

#define MINDIST		0x5800

#define MAXVIEWWIDTH	1280

#define MAPSIZE		64		/* maps are 64*64 */

#define STATUSLINES	40

#define STARTAMMO	8

// object flag values

#define FL_SHOOTABLE	1
#define FL_BONUS		2
#define FL_NEVERMARK	4
#define FL_VISABLE		8
#define FL_ATTACKMODE	16
#define FL_FIRSTATTACK	32
#define FL_AMBUSH		64
#define FL_NONMARK		128

//
// sprite constants
//

enum {
		SPR_DEMOWL6,
		SPR_DEATHCAMWL6,
//
// static sprites
//
		SPR_STAT_0WL6,SPR_STAT_1WL6,SPR_STAT_2WL6,SPR_STAT_3WL6,
		SPR_STAT_4WL6,SPR_STAT_5WL6,SPR_STAT_6WL6,SPR_STAT_7WL6,

		SPR_STAT_8WL6,SPR_STAT_9WL6,SPR_STAT_10WL6,SPR_STAT_11WL6,
		SPR_STAT_12WL6,SPR_STAT_13WL6,SPR_STAT_14WL6,SPR_STAT_15WL6,

		SPR_STAT_16WL6,SPR_STAT_17WL6,SPR_STAT_18WL6,SPR_STAT_19WL6,
		SPR_STAT_20WL6,SPR_STAT_21WL6,SPR_STAT_22WL6,SPR_STAT_23WL6,

		SPR_STAT_24WL6,SPR_STAT_25WL6,SPR_STAT_26WL6,SPR_STAT_27WL6,
		SPR_STAT_28WL6,SPR_STAT_29WL6,SPR_STAT_30WL6,SPR_STAT_31WL6,

		SPR_STAT_32WL6,SPR_STAT_33WL6,SPR_STAT_34WL6,SPR_STAT_35WL6,
		SPR_STAT_36WL6,SPR_STAT_37WL6,SPR_STAT_38WL6,SPR_STAT_39WL6,

		SPR_STAT_40WL6,SPR_STAT_41WL6,SPR_STAT_42WL6,SPR_STAT_43WL6,
		SPR_STAT_44WL6,SPR_STAT_45WL6,SPR_STAT_46WL6,SPR_STAT_47WL6,

//
// guard
//
		SPR_GRD_S_1WL6,SPR_GRD_S_2WL6,SPR_GRD_S_3WL6,SPR_GRD_S_4WL6,
		SPR_GRD_S_5WL6,SPR_GRD_S_6WL6,SPR_GRD_S_7WL6,SPR_GRD_S_8WL6,

		SPR_GRD_W1_1WL6,SPR_GRD_W1_2WL6,SPR_GRD_W1_3WL6,SPR_GRD_W1_4WL6,
		SPR_GRD_W1_5WL6,SPR_GRD_W1_6WL6,SPR_GRD_W1_7WL6,SPR_GRD_W1_8WL6,

		SPR_GRD_W2_1WL6,SPR_GRD_W2_2WL6,SPR_GRD_W2_3WL6,SPR_GRD_W2_4WL6,
		SPR_GRD_W2_5WL6,SPR_GRD_W2_6WL6,SPR_GRD_W2_7WL6,SPR_GRD_W2_8WL6,

		SPR_GRD_W3_1WL6,SPR_GRD_W3_2WL6,SPR_GRD_W3_3WL6,SPR_GRD_W3_4WL6,
		SPR_GRD_W3_5WL6,SPR_GRD_W3_6WL6,SPR_GRD_W3_7WL6,SPR_GRD_W3_8WL6,

		SPR_GRD_W4_1WL6,SPR_GRD_W4_2WL6,SPR_GRD_W4_3WL6,SPR_GRD_W4_4WL6,
		SPR_GRD_W4_5WL6,SPR_GRD_W4_6WL6,SPR_GRD_W4_7WL6,SPR_GRD_W4_8WL6,

		SPR_GRD_PAIN_1WL6,SPR_GRD_DIE_1WL6,SPR_GRD_DIE_2WL6,SPR_GRD_DIE_3WL6,
		SPR_GRD_PAIN_2WL6,SPR_GRD_DEADWL6,

		SPR_GRD_SHOOT1WL6,SPR_GRD_SHOOT2WL6,SPR_GRD_SHOOT3WL6,

//
// dogs
//
		SPR_DOG_W1_1WL6,SPR_DOG_W1_2WL6,SPR_DOG_W1_3WL6,SPR_DOG_W1_4WL6,
		SPR_DOG_W1_5WL6,SPR_DOG_W1_6WL6,SPR_DOG_W1_7WL6,SPR_DOG_W1_8WL6,

		SPR_DOG_W2_1WL6,SPR_DOG_W2_2WL6,SPR_DOG_W2_3WL6,SPR_DOG_W2_4WL6,
		SPR_DOG_W2_5WL6,SPR_DOG_W2_6WL6,SPR_DOG_W2_7WL6,SPR_DOG_W2_8WL6,

		SPR_DOG_W3_1WL6,SPR_DOG_W3_2WL6,SPR_DOG_W3_3WL6,SPR_DOG_W3_4WL6,
		SPR_DOG_W3_5WL6,SPR_DOG_W3_6WL6,SPR_DOG_W3_7WL6,SPR_DOG_W3_8WL6,

		SPR_DOG_W4_1WL6,SPR_DOG_W4_2WL6,SPR_DOG_W4_3WL6,SPR_DOG_W4_4WL6,
		SPR_DOG_W4_5WL6,SPR_DOG_W4_6WL6,SPR_DOG_W4_7WL6,SPR_DOG_W4_8WL6,

		SPR_DOG_DIE_1WL6,SPR_DOG_DIE_2WL6,SPR_DOG_DIE_3WL6,SPR_DOG_DEADWL6,
		SPR_DOG_JUMP1WL6,SPR_DOG_JUMP2WL6,SPR_DOG_JUMP3WL6,



//
// ss
//
		SPR_SS_S_1WL6,SPR_SS_S_2WL6,SPR_SS_S_3WL6,SPR_SS_S_4WL6,
		SPR_SS_S_5WL6,SPR_SS_S_6WL6,SPR_SS_S_7WL6,SPR_SS_S_8WL6,

		SPR_SS_W1_1WL6,SPR_SS_W1_2WL6,SPR_SS_W1_3WL6,SPR_SS_W1_4WL6,
		SPR_SS_W1_5WL6,SPR_SS_W1_6WL6,SPR_SS_W1_7WL6,SPR_SS_W1_8WL6,

		SPR_SS_W2_1WL6,SPR_SS_W2_2WL6,SPR_SS_W2_3WL6,SPR_SS_W2_4WL6,
		SPR_SS_W2_5WL6,SPR_SS_W2_6WL6,SPR_SS_W2_7WL6,SPR_SS_W2_8WL6,

		SPR_SS_W3_1WL6,SPR_SS_W3_2WL6,SPR_SS_W3_3WL6,SPR_SS_W3_4WL6,
		SPR_SS_W3_5WL6,SPR_SS_W3_6WL6,SPR_SS_W3_7WL6,SPR_SS_W3_8WL6,

		SPR_SS_W4_1WL6,SPR_SS_W4_2WL6,SPR_SS_W4_3WL6,SPR_SS_W4_4WL6,
		SPR_SS_W4_5WL6,SPR_SS_W4_6WL6,SPR_SS_W4_7WL6,SPR_SS_W4_8WL6,

		SPR_SS_PAIN_1WL6,SPR_SS_DIE_1WL6,SPR_SS_DIE_2WL6,SPR_SS_DIE_3WL6,
		SPR_SS_PAIN_2WL6,SPR_SS_DEADWL6,

		SPR_SS_SHOOT1WL6,SPR_SS_SHOOT2WL6,SPR_SS_SHOOT3WL6,

//
// mutant
//
		SPR_MUT_S_1WL6,SPR_MUT_S_2WL6,SPR_MUT_S_3WL6,SPR_MUT_S_4WL6,
		SPR_MUT_S_5WL6,SPR_MUT_S_6WL6,SPR_MUT_S_7WL6,SPR_MUT_S_8WL6,

		SPR_MUT_W1_1WL6,SPR_MUT_W1_2WL6,SPR_MUT_W1_3WL6,SPR_MUT_W1_4WL6,
		SPR_MUT_W1_5WL6,SPR_MUT_W1_6WL6,SPR_MUT_W1_7WL6,SPR_MUT_W1_8WL6,

		SPR_MUT_W2_1WL6,SPR_MUT_W2_2WL6,SPR_MUT_W2_3WL6,SPR_MUT_W2_4WL6,
		SPR_MUT_W2_5WL6,SPR_MUT_W2_6WL6,SPR_MUT_W2_7WL6,SPR_MUT_W2_8WL6,

		SPR_MUT_W3_1WL6,SPR_MUT_W3_2WL6,SPR_MUT_W3_3WL6,SPR_MUT_W3_4WL6,
		SPR_MUT_W3_5WL6,SPR_MUT_W3_6WL6,SPR_MUT_W3_7WL6,SPR_MUT_W3_8WL6,

		SPR_MUT_W4_1WL6,SPR_MUT_W4_2WL6,SPR_MUT_W4_3WL6,SPR_MUT_W4_4WL6,
		SPR_MUT_W4_5WL6,SPR_MUT_W4_6WL6,SPR_MUT_W4_7WL6,SPR_MUT_W4_8WL6,

		SPR_MUT_PAIN_1WL6,SPR_MUT_DIE_1WL6,SPR_MUT_DIE_2WL6,SPR_MUT_DIE_3WL6,
		SPR_MUT_PAIN_2WL6,SPR_MUT_DIE_4WL6,SPR_MUT_DEADWL6,

		SPR_MUT_SHOOT1WL6,SPR_MUT_SHOOT2WL6,SPR_MUT_SHOOT3WL6,SPR_MUT_SHOOT4WL6,

//
// officer
//
		SPR_OFC_S_1WL6,SPR_OFC_S_2WL6,SPR_OFC_S_3WL6,SPR_OFC_S_4WL6,
		SPR_OFC_S_5WL6,SPR_OFC_S_6WL6,SPR_OFC_S_7WL6,SPR_OFC_S_8WL6,

		SPR_OFC_W1_1WL6,SPR_OFC_W1_2WL6,SPR_OFC_W1_3WL6,SPR_OFC_W1_4WL6,
		SPR_OFC_W1_5WL6,SPR_OFC_W1_6WL6,SPR_OFC_W1_7WL6,SPR_OFC_W1_8WL6,

		SPR_OFC_W2_1WL6,SPR_OFC_W2_2WL6,SPR_OFC_W2_3WL6,SPR_OFC_W2_4WL6,
		SPR_OFC_W2_5WL6,SPR_OFC_W2_6WL6,SPR_OFC_W2_7WL6,SPR_OFC_W2_8WL6,

		SPR_OFC_W3_1WL6,SPR_OFC_W3_2WL6,SPR_OFC_W3_3WL6,SPR_OFC_W3_4WL6,
		SPR_OFC_W3_5WL6,SPR_OFC_W3_6WL6,SPR_OFC_W3_7WL6,SPR_OFC_W3_8WL6,

		SPR_OFC_W4_1WL6,SPR_OFC_W4_2WL6,SPR_OFC_W4_3WL6,SPR_OFC_W4_4WL6,
		SPR_OFC_W4_5WL6,SPR_OFC_W4_6WL6,SPR_OFC_W4_7WL6,SPR_OFC_W4_8WL6,

		SPR_OFC_PAIN_1WL6,SPR_OFC_DIE_1WL6,SPR_OFC_DIE_2WL6,SPR_OFC_DIE_3WL6,
		SPR_OFC_PAIN_2WL6,SPR_OFC_DIE_4WL6,SPR_OFC_DEADWL6,

		SPR_OFC_SHOOT1WL6,SPR_OFC_SHOOT2WL6,SPR_OFC_SHOOT3WL6,

//
// ghosts
//
		SPR_BLINKY_W1WL6,SPR_BLINKY_W2WL6,SPR_PINKY_W1WL6,SPR_PINKY_W2WL6,
		SPR_CLYDE_W1WL6,SPR_CLYDE_W2WL6,SPR_INKY_W1WL6,SPR_INKY_W2WL6,

//
// hans
//
		SPR_BOSS_W1WL6,SPR_BOSS_W2WL6,SPR_BOSS_W3WL6,SPR_BOSS_W4WL6,
		SPR_BOSS_SHOOT1WL6,SPR_BOSS_SHOOT2WL6,SPR_BOSS_SHOOT3WL6,SPR_BOSS_DEADWL6,

		SPR_BOSS_DIE1WL6,SPR_BOSS_DIE2WL6,SPR_BOSS_DIE3WL6,

//
// schabbs
//
		SPR_SCHABB_W1WL6,SPR_SCHABB_W2WL6,SPR_SCHABB_W3WL6,SPR_SCHABB_W4WL6,
		SPR_SCHABB_SHOOT1WL6,SPR_SCHABB_SHOOT2WL6,

		SPR_SCHABB_DIE1WL6,SPR_SCHABB_DIE2WL6,SPR_SCHABB_DIE3WL6,SPR_SCHABB_DEADWL6,
		SPR_HYPO1WL6,SPR_HYPO2WL6,SPR_HYPO3WL6,SPR_HYPO4WL6,

//
// fake
//
		SPR_FAKE_W1WL6,SPR_FAKE_W2WL6,SPR_FAKE_W3WL6,SPR_FAKE_W4WL6,
		SPR_FAKE_SHOOTWL6,SPR_FIRE1WL6,SPR_FIRE2WL6,

		SPR_FAKE_DIE1WL6,SPR_FAKE_DIE2WL6,SPR_FAKE_DIE3WL6,SPR_FAKE_DIE4WL6,
		SPR_FAKE_DIE5WL6,SPR_FAKE_DEADWL6,

//
// hitler
//
		SPR_MECHA_W1WL6,SPR_MECHA_W2WL6,SPR_MECHA_W3WL6,SPR_MECHA_W4WL6,
		SPR_MECHA_SHOOT1WL6,SPR_MECHA_SHOOT2WL6,SPR_MECHA_SHOOT3WL6,SPR_MECHA_DEADWL6,

		SPR_MECHA_DIE1WL6,SPR_MECHA_DIE2WL6,SPR_MECHA_DIE3WL6,

		SPR_HITLER_W1WL6,SPR_HITLER_W2WL6,SPR_HITLER_W3WL6,SPR_HITLER_W4WL6,
		SPR_HITLER_SHOOT1WL6,SPR_HITLER_SHOOT2WL6,SPR_HITLER_SHOOT3WL6,SPR_HITLER_DEADWL6,

		SPR_HITLER_DIE1WL6,SPR_HITLER_DIE2WL6,SPR_HITLER_DIE3WL6,SPR_HITLER_DIE4WL6,
		SPR_HITLER_DIE5WL6,SPR_HITLER_DIE6WL6,SPR_HITLER_DIE7WL6,

//
// giftmacher
//
		SPR_GIFT_W1WL6,SPR_GIFT_W2WL6,SPR_GIFT_W3WL6,SPR_GIFT_W4WL6,
		SPR_GIFT_SHOOT1WL6,SPR_GIFT_SHOOT2WL6,

		SPR_GIFT_DIE1WL6,SPR_GIFT_DIE2WL6,SPR_GIFT_DIE3WL6,SPR_GIFT_DEADWL6,

//
// Rocket, smoke and small explosion
//
		SPR_ROCKET_1WL6,SPR_ROCKET_2WL6,SPR_ROCKET_3WL6,SPR_ROCKET_4WL6,
		SPR_ROCKET_5WL6,SPR_ROCKET_6WL6,SPR_ROCKET_7WL6,SPR_ROCKET_8WL6,

		SPR_SMOKE_1WL6,SPR_SMOKE_2WL6,SPR_SMOKE_3WL6,SPR_SMOKE_4WL6,
		SPR_BOOM_1WL6,SPR_BOOM_2WL6,SPR_BOOM_3WL6,

//
// gretel
//
		SPR_GRETEL_W1WL6,SPR_GRETEL_W2WL6,SPR_GRETEL_W3WL6,SPR_GRETEL_W4WL6,
		SPR_GRETEL_SHOOT1WL6,SPR_GRETEL_SHOOT2WL6,SPR_GRETEL_SHOOT3WL6,SPR_GRETEL_DEADWL6,

		SPR_GRETEL_DIE1WL6,SPR_GRETEL_DIE2WL6,SPR_GRETEL_DIE3WL6,

//
// fat face
//
		SPR_FAT_W1WL6,SPR_FAT_W2WL6,SPR_FAT_W3WL6,SPR_FAT_W4WL6,
		SPR_FAT_SHOOT1WL6,SPR_FAT_SHOOT2WL6,SPR_FAT_SHOOT3WL6,SPR_FAT_SHOOT4WL6,

		SPR_FAT_DIE1WL6,SPR_FAT_DIE2WL6,SPR_FAT_DIE3WL6,SPR_FAT_DEADWL6,

//
// bj
//
		SPR_BJ_W1WL6,SPR_BJ_W2WL6,SPR_BJ_W3WL6,SPR_BJ_W4WL6,
		SPR_BJ_JUMP1WL6,SPR_BJ_JUMP2WL6,SPR_BJ_JUMP3WL6,SPR_BJ_JUMP4WL6,

//
// player attack frames
//
		SPR_KNIFEREADYWL6,SPR_KNIFEATK1WL6,SPR_KNIFEATK2WL6,SPR_KNIFEATK3WL6,
		SPR_KNIFEATK4WL6,

		SPR_PISTOLREADYWL6,SPR_PISTOLATK1WL6,SPR_PISTOLATK2WL6,SPR_PISTOLATK3WL6,
		SPR_PISTOLATK4WL6,

		SPR_MACHINEGUNREADYWL6,SPR_MACHINEGUNATK1WL6,SPR_MACHINEGUNATK2WL6,MACHINEGUNATK3WL6,
		SPR_MACHINEGUNATK4WL6,

		SPR_CHAINREADYWL6,SPR_CHAINATK1WL6,SPR_CHAINATK2WL6,SPR_CHAINATK3WL6,
		SPR_CHAINATK4WL6, SPR_NULLSPRITEWL6, SPR_TOTALWL6
};

enum {
		SPR_DEMOSOD,
		SPR_DEATHCAMSOD,
//
// static sprites
//
		SPR_STAT_0SOD,SPR_STAT_1SOD,SPR_STAT_2SOD,SPR_STAT_3SOD,
		SPR_STAT_4SOD,SPR_STAT_5SOD,SPR_STAT_6SOD,SPR_STAT_7SOD,

		SPR_STAT_8SOD,SPR_STAT_9SOD,SPR_STAT_10SOD,SPR_STAT_11SOD,
		SPR_STAT_12SOD,SPR_STAT_13SOD,SPR_STAT_14SOD,SPR_STAT_15SOD,

		SPR_STAT_16SOD,SPR_STAT_17SOD,SPR_STAT_18SOD,SPR_STAT_19SOD,
		SPR_STAT_20SOD,SPR_STAT_21SOD,SPR_STAT_22SOD,SPR_STAT_23SOD,

		SPR_STAT_24SOD,SPR_STAT_25SOD,SPR_STAT_26SOD,SPR_STAT_27SOD,
		SPR_STAT_28SOD,SPR_STAT_29SOD,SPR_STAT_30SOD,SPR_STAT_31SOD,

		SPR_STAT_32SOD,SPR_STAT_33SOD,SPR_STAT_34SOD,SPR_STAT_35SOD,
		SPR_STAT_36SOD,SPR_STAT_37SOD,SPR_STAT_38SOD,SPR_STAT_39SOD,

		SPR_STAT_40SOD,SPR_STAT_41SOD,SPR_STAT_42SOD,SPR_STAT_43SOD,
		SPR_STAT_44SOD,SPR_STAT_45SOD,SPR_STAT_46SOD,SPR_STAT_47SOD,

		SPR_STAT_48SOD,SPR_STAT_49SOD,SPR_STAT_50SOD,SPR_STAT_51SOD,

//
// guard
//
		SPR_GRD_S_1SOD,SPR_GRD_S_2SOD,SPR_GRD_S_3SOD,SPR_GRD_S_4SOD,
		SPR_GRD_S_5SOD,SPR_GRD_S_6SOD,SPR_GRD_S_7SOD,SPR_GRD_S_8SOD,

		SPR_GRD_W1_1SOD,SPR_GRD_W1_2SOD,SPR_GRD_W1_3SOD,SPR_GRD_W1_4SOD,
		SPR_GRD_W1_5SOD,SPR_GRD_W1_6SOD,SPR_GRD_W1_7SOD,SPR_GRD_W1_8SOD,

		SPR_GRD_W2_1SOD,SPR_GRD_W2_2SOD,SPR_GRD_W2_3SOD,SPR_GRD_W2_4SOD,
		SPR_GRD_W2_5SOD,SPR_GRD_W2_6SOD,SPR_GRD_W2_7SOD,SPR_GRD_W2_8SOD,

		SPR_GRD_W3_1SOD,SPR_GRD_W3_2SOD,SPR_GRD_W3_3SOD,SPR_GRD_W3_4SOD,
		SPR_GRD_W3_5SOD,SPR_GRD_W3_6SOD,SPR_GRD_W3_7SOD,SPR_GRD_W3_8SOD,

		SPR_GRD_W4_1SOD,SPR_GRD_W4_2SOD,SPR_GRD_W4_3SOD,SPR_GRD_W4_4SOD,
		SPR_GRD_W4_5SOD,SPR_GRD_W4_6SOD,SPR_GRD_W4_7SOD,SPR_GRD_W4_8SOD,

		SPR_GRD_PAIN_1SOD,SPR_GRD_DIE_1SOD,SPR_GRD_DIE_2SOD,SPR_GRD_DIE_3SOD,
		SPR_GRD_PAIN_2SOD,SPR_GRD_DEADSOD,

		SPR_GRD_SHOOT1SOD,SPR_GRD_SHOOT2SOD,SPR_GRD_SHOOT3SOD,

//
// dogs
//
		SPR_DOG_W1_1SOD,SPR_DOG_W1_2SOD,SPR_DOG_W1_3SOD,SPR_DOG_W1_4SOD,
		SPR_DOG_W1_5SOD,SPR_DOG_W1_6SOD,SPR_DOG_W1_7SOD,SPR_DOG_W1_8SOD,

		SPR_DOG_W2_1SOD,SPR_DOG_W2_2SOD,SPR_DOG_W2_3SOD,SPR_DOG_W2_4SOD,
		SPR_DOG_W2_5SOD,SPR_DOG_W2_6SOD,SPR_DOG_W2_7SOD,SPR_DOG_W2_8SOD,

		SPR_DOG_W3_1SOD,SPR_DOG_W3_2SOD,SPR_DOG_W3_3SOD,SPR_DOG_W3_4SOD,
		SPR_DOG_W3_5SOD,SPR_DOG_W3_6SOD,SPR_DOG_W3_7SOD,SPR_DOG_W3_8SOD,

		SPR_DOG_W4_1SOD,SPR_DOG_W4_2SOD,SPR_DOG_W4_3SOD,SPR_DOG_W4_4SOD,
		SPR_DOG_W4_5SOD,SPR_DOG_W4_6SOD,SPR_DOG_W4_7SOD,SPR_DOG_W4_8SOD,

		SPR_DOG_DIE_1SOD,SPR_DOG_DIE_2SOD,SPR_DOG_DIE_3SOD,SPR_DOG_DEADSOD,
		SPR_DOG_JUMP1SOD,SPR_DOG_JUMP2SOD,SPR_DOG_JUMP3SOD,



//
// ss
//
		SPR_SS_S_1SOD,SPR_SS_S_2SOD,SPR_SS_S_3SOD,SPR_SS_S_4SOD,
		SPR_SS_S_5SOD,SPR_SS_S_6SOD,SPR_SS_S_7SOD,SPR_SS_S_8SOD,

		SPR_SS_W1_1SOD,SPR_SS_W1_2SOD,SPR_SS_W1_3SOD,SPR_SS_W1_4SOD,
		SPR_SS_W1_5SOD,SPR_SS_W1_6SOD,SPR_SS_W1_7SOD,SPR_SS_W1_8SOD,

		SPR_SS_W2_1SOD,SPR_SS_W2_2SOD,SPR_SS_W2_3SOD,SPR_SS_W2_4SOD,
		SPR_SS_W2_5SOD,SPR_SS_W2_6SOD,SPR_SS_W2_7SOD,SPR_SS_W2_8SOD,

		SPR_SS_W3_1SOD,SPR_SS_W3_2SOD,SPR_SS_W3_3SOD,SPR_SS_W3_4SOD,
		SPR_SS_W3_5SOD,SPR_SS_W3_6SOD,SPR_SS_W3_7SOD,SPR_SS_W3_8SOD,

		SPR_SS_W4_1SOD,SPR_SS_W4_2SOD,SPR_SS_W4_3SOD,SPR_SS_W4_4SOD,
		SPR_SS_W4_5SOD,SPR_SS_W4_6SOD,SPR_SS_W4_7SOD,SPR_SS_W4_8SOD,

		SPR_SS_PAIN_1SOD,SPR_SS_DIE_1SOD,SPR_SS_DIE_2SOD,SPR_SS_DIE_3SOD,
		SPR_SS_PAIN_2SOD,SPR_SS_DEADSOD,

		SPR_SS_SHOOT1SOD,SPR_SS_SHOOT2SOD,SPR_SS_SHOOT3SOD,

//
// mutant
//
		SPR_MUT_S_1SOD,SPR_MUT_S_2SOD,SPR_MUT_S_3SOD,SPR_MUT_S_4SOD,
		SPR_MUT_S_5SOD,SPR_MUT_S_6SOD,SPR_MUT_S_7SOD,SPR_MUT_S_8SOD,

		SPR_MUT_W1_1SOD,SPR_MUT_W1_2SOD,SPR_MUT_W1_3SOD,SPR_MUT_W1_4SOD,
		SPR_MUT_W1_5SOD,SPR_MUT_W1_6SOD,SPR_MUT_W1_7SOD,SPR_MUT_W1_8SOD,

		SPR_MUT_W2_1SOD,SPR_MUT_W2_2SOD,SPR_MUT_W2_3SOD,SPR_MUT_W2_4SOD,
		SPR_MUT_W2_5SOD,SPR_MUT_W2_6SOD,SPR_MUT_W2_7SOD,SPR_MUT_W2_8SOD,

		SPR_MUT_W3_1SOD,SPR_MUT_W3_2SOD,SPR_MUT_W3_3SOD,SPR_MUT_W3_4SOD,
		SPR_MUT_W3_5SOD,SPR_MUT_W3_6SOD,SPR_MUT_W3_7SOD,SPR_MUT_W3_8SOD,

		SPR_MUT_W4_1SOD,SPR_MUT_W4_2SOD,SPR_MUT_W4_3SOD,SPR_MUT_W4_4SOD,
		SPR_MUT_W4_5SOD,SPR_MUT_W4_6SOD,SPR_MUT_W4_7SOD,SPR_MUT_W4_8SOD,

		SPR_MUT_PAIN_1SOD,SPR_MUT_DIE_1SOD,SPR_MUT_DIE_2SOD,SPR_MUT_DIE_3SOD,
		SPR_MUT_PAIN_2SOD,SPR_MUT_DIE_4SOD,SPR_MUT_DEADSOD,

		SPR_MUT_SHOOT1SOD,SPR_MUT_SHOOT2SOD,SPR_MUT_SHOOT3SOD,SPR_MUT_SHOOT4SOD,

//
// officer
//
		SPR_OFC_S_1SOD,SPR_OFC_S_2SOD,SPR_OFC_S_3SOD,SPR_OFC_S_4SOD,
		SPR_OFC_S_5SOD,SPR_OFC_S_6SOD,SPR_OFC_S_7SOD,SPR_OFC_S_8SOD,

		SPR_OFC_W1_1SOD,SPR_OFC_W1_2SOD,SPR_OFC_W1_3SOD,SPR_OFC_W1_4SOD,
		SPR_OFC_W1_5SOD,SPR_OFC_W1_6SOD,SPR_OFC_W1_7SOD,SPR_OFC_W1_8SOD,

		SPR_OFC_W2_1SOD,SPR_OFC_W2_2SOD,SPR_OFC_W2_3SOD,SPR_OFC_W2_4SOD,
		SPR_OFC_W2_5SOD,SPR_OFC_W2_6SOD,SPR_OFC_W2_7SOD,SPR_OFC_W2_8SOD,

		SPR_OFC_W3_1SOD,SPR_OFC_W3_2SOD,SPR_OFC_W3_3SOD,SPR_OFC_W3_4SOD,
		SPR_OFC_W3_5SOD,SPR_OFC_W3_6SOD,SPR_OFC_W3_7SOD,SPR_OFC_W3_8SOD,

		SPR_OFC_W4_1SOD,SPR_OFC_W4_2SOD,SPR_OFC_W4_3SOD,SPR_OFC_W4_4SOD,
		SPR_OFC_W4_5SOD,SPR_OFC_W4_6SOD,SPR_OFC_W4_7SOD,SPR_OFC_W4_8SOD,

		SPR_OFC_PAIN_1SOD,SPR_OFC_DIE_1SOD,SPR_OFC_DIE_2SOD,SPR_OFC_DIE_3SOD,
		SPR_OFC_PAIN_2SOD,SPR_OFC_DIE_4SOD,SPR_OFC_DEADSOD,

		SPR_OFC_SHOOT1SOD,SPR_OFC_SHOOT2SOD,SPR_OFC_SHOOT3SOD,

//
// Rocket, smoke and small explosion
//
		SPR_ROCKET_1SOD,SPR_ROCKET_2SOD,SPR_ROCKET_3SOD,SPR_ROCKET_4SOD,
		SPR_ROCKET_5SOD,SPR_ROCKET_6SOD,SPR_ROCKET_7SOD,SPR_ROCKET_8SOD,

		SPR_SMOKE_1SOD,SPR_SMOKE_2SOD,SPR_SMOKE_3SOD,SPR_SMOKE_4SOD,
		SPR_BOOM_1SOD,SPR_BOOM_2SOD,SPR_BOOM_3SOD,

//
// Angel of Death's DeathSparks(tm)
//
		SPR_HROCKET_1SOD,SPR_HROCKET_2SOD,SPR_HROCKET_3SOD,SPR_HROCKET_4SOD,
		SPR_HROCKET_5SOD,SPR_HROCKET_6SOD,SPR_HROCKET_7SOD,SPR_HROCKET_8SOD,

		SPR_HSMOKE_1SOD,SPR_HSMOKE_2SOD,SPR_HSMOKE_3SOD,SPR_HSMOKE_4SOD,
		SPR_HBOOM_1SOD,SPR_HBOOM_2SOD,SPR_HBOOM_3SOD,

		SPR_SPARK1SOD,SPR_SPARK2SOD,SPR_SPARK3SOD,SPR_SPARK4SOD,

//
// THESE ARE FOR 'SPEAR OF DESTINY'
//

//
// Trans Grosse
//
		SPR_TRANS_W1SOD,SPR_TRANS_W2SOD,SPR_TRANS_W3SOD,SPR_TRANS_W4SOD,
		SPR_TRANS_SHOOT1SOD,SPR_TRANS_SHOOT2SOD,SPR_TRANS_SHOOT3SOD,SPR_TRANS_DEADSOD,

		SPR_TRANS_DIE1SOD,SPR_TRANS_DIE2SOD,SPR_TRANS_DIE3SOD,

//
// Wilhelm
//
		SPR_WILL_W1SOD,SPR_WILL_W2SOD,SPR_WILL_W3SOD,SPR_WILL_W4SOD,
		SPR_WILL_SHOOT1SOD,SPR_WILL_SHOOT2SOD,SPR_WILL_SHOOT3SOD,SPR_WILL_SHOOT4SOD,

		SPR_WILL_DIE1SOD,SPR_WILL_DIE2SOD,SPR_WILL_DIE3SOD,SPR_WILL_DEADSOD,

//
// UberMutant
//
		SPR_UBER_W1SOD,SPR_UBER_W2SOD,SPR_UBER_W3SOD,SPR_UBER_W4SOD,
		SPR_UBER_SHOOT1SOD,SPR_UBER_SHOOT2SOD,SPR_UBER_SHOOT3SOD,SPR_UBER_SHOOT4SOD,

		SPR_UBER_DIE1SOD,SPR_UBER_DIE2SOD,SPR_UBER_DIE3SOD,SPR_UBER_DIE4SOD,
		SPR_UBER_DEADSOD,

//
// Death Knight
//
		SPR_DEATH_W1SOD,SPR_DEATH_W2SOD,SPR_DEATH_W3SOD,SPR_DEATH_W4SOD,
		SPR_DEATH_SHOOT1SOD,SPR_DEATH_SHOOT2SOD,SPR_DEATH_SHOOT3SOD,SPR_DEATH_SHOOT4SOD,

		SPR_DEATH_DIE1SOD,SPR_DEATH_DIE2SOD,SPR_DEATH_DIE3SOD,SPR_DEATH_DIE4SOD,
		SPR_DEATH_DIE5SOD,SPR_DEATH_DIE6SOD,SPR_DEATH_DEADSOD,

//
// Ghost
//
		SPR_SPECTRE_W1SOD,SPR_SPECTRE_W2SOD,SPR_SPECTRE_W3SOD,SPR_SPECTRE_W4SOD,
		SPR_SPECTRE_F1SOD,SPR_SPECTRE_F2SOD,SPR_SPECTRE_F3SOD,SPR_SPECTRE_F4SOD,

//
// Angel of Death
//
		SPR_ANGEL_W1SOD,SPR_ANGEL_W2SOD,SPR_ANGEL_W3SOD,SPR_ANGEL_W4SOD,
		SPR_ANGEL_SHOOT1SOD,SPR_ANGEL_SHOOT2SOD,SPR_ANGEL_TIRED1SOD,SPR_ANGEL_TIRED2SOD,

		SPR_ANGEL_DIE1SOD,SPR_ANGEL_DIE2SOD,SPR_ANGEL_DIE3SOD,SPR_ANGEL_DIE4SOD,
		SPR_ANGEL_DIE5SOD,SPR_ANGEL_DIE6SOD,SPR_ANGEL_DIE7SOD,SPR_ANGEL_DEADSOD,

//
// player attack frames
//
		SPR_KNIFEREADYSOD,SPR_KNIFEATK1SOD,SPR_KNIFEATK2SOD,SPR_KNIFEATK3SOD,
		SPR_KNIFEATK4SOD,

		SPR_PISTOLREADYSOD,SPR_PISTOLATK1SOD,SPR_PISTOLATK2SOD,SPR_PISTOLATK3SOD,
		SPR_PISTOLATK4SOD,

		SPR_MACHINEGUNREADYSOD,SPR_MACHINEGUNATK1SOD,SPR_MACHINEGUNATK2SOD,MACHINEGUNATK3SOD,
		SPR_MACHINEGUNATK4SOD,

		SPR_CHAINREADYSOD,SPR_CHAINATK1SOD,SPR_CHAINATK2SOD,SPR_CHAINATK3SOD,
		SPR_CHAINATK4SOD, SPR_NULLSPRITESOD, SPR_TOTALSOD
};


/*
=============================================================================

						   GLOBAL TYPES

=============================================================================
*/

typedef enum {
	di_north,
	di_east,
	di_south,
	di_west
} controldir_t;

typedef enum {
	dr_normal,
	dr_lock1,
	dr_lock2,
	dr_lock3,
	dr_lock4,
	dr_elevator
} door_t;

typedef enum {
	ac_badobject = -1,
	ac_no,
	ac_yes
} activetype;

typedef enum {
	nothing,
	playerobj,
	inertobj,
	guardobj,
	officerobj,
	ssobj,
	dogobj,
	bossobj,
	schabbobj,
	fakeobj,
	mechahitlerobj,
	mutantobj,
	needleobj,
	fireobj,
	bjobj,
	ghostobj,
	realhitlerobj,
	gretelobj,
	giftobj,
	fatobj,
	rocketobj,

	spectreobj,
	angelobj,
	transobj,
	uberobj,
	willobj,
	deathobj,
	hrocketobj,
	sparkobj
} classtype;

typedef enum {
	dressing,
	block,
	bo_gibs,
	bo_alpo,
	bo_firstaid,
	bo_key1,
	bo_key2,
	bo_key3,
	bo_key4,
	bo_cross,
	bo_chalice,
	bo_bible,
	bo_crown,
	bo_clip,
	bo_clip2,
	bo_machinegun,
	bo_chaingun,
	bo_food,
	bo_fullheal,
	bo_25clip,
	bo_spear
} stat_t;

typedef enum {
	east,
	northeast,
	north,
	northwest,
	west,
	southwest,
	south,
	southeast,
	nodir
} dirtype;

#define NUMENEMIES		22
typedef enum {
	en_guard,
	en_officer,
	en_ss,
	en_dog,
	en_boss,
	en_schabbs,
	en_fake,
	en_hitler,
	en_mutant,
	en_blinky,
	en_clyde,
	en_pinky,
	en_inky,
	en_gretel,
	en_gift,
	en_fat,
	en_spectre,
	en_angel,
	en_trans,
	en_uber,
	en_will,
	en_death
} enemy_t;

//---------------------
//
// trivial actor structure
//
//---------------------

typedef struct statstruct
{
	byte	tilex,tiley;
	byte	*visspot;
	int	shapenum;		/* if shapenum == -1 the obj has been removed */
	byte	flags;
	byte	itemnumber;
} statobj_t;

//---------------------
//
// door actor structure
//
//---------------------

typedef struct doorstruct
{
	byte	tilex,tiley;
	boolean	vertical;
	byte	lock;
	enum	{dr_open,dr_closed,dr_opening,dr_closing} action;
	int	ticcount;
} doorobj_t;

//--------------------
//
// thinking actor structure
//
//--------------------

typedef struct objstruct
{
	int		id;
	
	activetype	active;
	int		ticcount;
	classtype	obclass;
	int		state; /* stateenum */

	byte		flags;		/* FL_SHOOTABLE, etc */

	long		distance;	/* if negative, wait for that door to open */
	dirtype		dir;

	fixed 		x,y;
	unsigned	tilex,tiley;
	byte		areanumber;

	int	 	viewx;
	unsigned	viewheight;
	fixed		transx, transy;		/* in global coord */

	int 		angle;
	int		hitpoints;
	long		speed;

	int		temp1,temp2,temp3;
	struct		objstruct *next,*prev;
} objtype;

typedef struct statestruct
{
	boolean	rotate;
	int shapenum; /* a shapenum of -1 means get from ob->temp1 */
	int tictime;
	void (*think)(objtype *ob);
	void (*action)(objtype *ob);
	int next; /* stateenum */
} statetype;

#define NUMBUTTONS	11
enum {
	bt_nobutton=-1,
	bt_attack=0,
	bt_strafe,
	bt_run,
	bt_use,
	bt_readyknife,
	bt_readypistol,
	bt_readymachinegun,
	bt_readychaingun,
	bt_strafeLeft,
	bt_strafeRight,
	bt_cycleWeapon
};


#define NUMWEAPONS	4
typedef enum {
	wp_knife,
	wp_pistol,
	wp_machinegun,
	wp_chaingun
} weapontype;


enum {
	gd_baby,
	gd_easy,
	gd_medium,
	gd_hard
};

//---------------
//
// gamestate structure
//
//---------------

typedef	struct
{
	int		difficulty;
	int		mapon;
	long		oldscore,score,nextextra;
	int		lives;
	int		health;
	int		ammo;
	int		keys;
	weapontype	bestweapon,weapon,chosenweapon;

	int		faceframe;
	int		attackframe,attackcount,weaponframe;

	int		episode,secretcount,treasurecount,killcount,
			secrettotal,treasuretotal,killtotal;
	long		TimeCount;
	long		killx,killy;
	boolean		victoryflag;		// set during victory animations
} gametype;


typedef	enum {
	ex_stillplaying,
	ex_completed,
	ex_died,
	ex_warped,
	ex_resetgame,
	ex_loadedgame,
	ex_victorious,
	ex_abort,
	ex_demodone,
	ex_secretlevel
} exit_t;


/*
=============================================================================

						 WL_MAIN DEFINITIONS

=============================================================================
*/

extern	char str[80], str2[20];

extern int viewwidth, viewheight;
extern int viewwidthwin, viewheightwin;
extern int xoffset, yoffset;

extern	int			centerx;
extern	int			shootdelta;

extern	boolean         startgame,loadedgame;
extern	int		mouseadjustment;

/* math tables */
extern int pixelangle[MAXVIEWWIDTH];
extern long finetangent[FINEANGLES/4];
extern fixed sintable[], *costable;

extern char configname[13];

void CalcProjection(long focal);
void NewGame(int difficulty,int episode);
void NewViewSize(int width);
void ShowViewSize(int width);

int LoadTheGame(const char *fn, int x, int y);
int SaveTheGame(const char *fn, const char *tag, int x, int y);
int ReadSaveTag(const char *fn, const char *tag);

void ShutdownId();
int WriteConfig();

int WolfMain(int argc, char *argv[]);

/*
=============================================================================

						 WL_GAME DEFINITIONS

=============================================================================
*/


extern	boolean		ingame;
extern	gametype	gamestate;
extern	int		doornum;

extern	char		demoname[13];

extern	long		spearx,speary;
extern	unsigned	spearangle;
extern	boolean		spearflag;


void 	ScanInfoPlaneSOD (void);
void 	ScanInfoPlaneWL6 (void);
void	SetupGameLevel (void);
void 	DrawPlayScreen (void);
void 	GameLoop (void);
void ClearMemory (void);
void PlayDemo(int demonumber);
int PlayDemoFromFile(const char *demoname);
void RecordDemo();
void DrawHighScores();
void DrawPlayBorder();
void DrawPlayBorderSides();
void DrawStatusBar();

#define	PlaySoundLocTile(s,tx,ty)	PlaySoundLocGlobal(s,(int)((tx<<6)|(ty)), (tx << TILESHIFT) + (1 << (TILESHIFT - 1)), (ty << TILESHIFT) + (1L << (TILESHIFT - 1)))
#define	PlaySoundLocActor(s,ob)		PlaySoundLocGlobal(s,(int)ob,(ob)->x,(ob)->y)

/*
=============================================================================

						 WL_PLAY DEFINITIONS

=============================================================================
*/

extern	long		funnyticount;		// FOR FUNNY BJ FACE

extern	exit_t		playstate;

extern	boolean		madenoise;

extern	objtype 	objlist[MAXACTORS],*new,*obj,*player,*lastobj,
					*objfreelist,*killerobj;
extern	statobj_t	statobjlist[MAXSTATS],*laststatobj;
extern	doorobj_t	doorobjlist[MAXDOORS],*lastdoorobj;

extern	unsigned	farmapylookup[MAPSIZE];

extern	byte		tilemap[MAPSIZE][MAPSIZE];	// wall values only
extern	byte		spotvis[MAPSIZE][MAPSIZE];
extern	int		actorat[MAPSIZE][MAPSIZE];

extern	boolean		singlestep,godmode,noclip;

//
// control info
//
extern	boolean		mouseenabled,joystickenabled,joypadenabled;
extern	int			joystickport;
extern	int			dirscan[4];
extern	int			buttonscan[NUMBUTTONS];
extern	int			buttonmouse[4];
extern	int			buttonjoy[4];

extern	boolean		buttonheld[NUMBUTTONS];

extern	int viewsize;

//
// curent user input
//
extern	int			controlx,controly;		// range from -100 to 100
extern	boolean		buttonstate[NUMBUTTONS];

extern	boolean		demorecord,demoplayback;
extern	byte		*demoptr, *lastdemoptr;
extern	memptr		demobuffer;


void StatusDrawPic(unsigned x, unsigned y, unsigned picnum);

void	InitRedShifts (void);
void 	FinishPaletteShifts (void);

void	CenterWindow(word w,word h);
void 	InitActorList (void);
void 	GetNewActor (void);
void 	StopMusic(void);
void 	StartMusic(void);
void	PlayLoop (void);
void StartDamageFlash (int damage);
void StartBonusFlash (void);

/*
=============================================================================

							WL_INTER

=============================================================================
*/

void IntroScreen (void);
void PreloadGraphics(void);
void LevelCompleted (void);
void CheckHighScore (long score,word other);
void Victory (void);
void ClearSplitVWB (void);
void PG13();

/*
=============================================================================

							WL_DEBUG

=============================================================================
*/

int DebugKeys (void);
void PicturePause (void);

/*
=============================================================================

						 WL_DRAW DEFINITIONS

=============================================================================
*/

extern long lasttimecount;
extern long frameon;

/* refresh variables */
extern fixed viewx, viewy;			/* the focal point */
extern fixed viewsin, viewcos;

extern int horizwall[], vertwall[];


void BuildTables();
void CalcTics();
void ThreeDRefresh();

void FizzleFade(boolean abortable, int frames, int color);

/*
=============================================================================

						 WL_STATE DEFINITIONS

=============================================================================
*/

#define SPDPATROL	512
#define SPDDOG		1500

void	SpawnNewObj(unsigned tilex, unsigned tiley, int state); /* stateenum */ 
void	NewState(objtype *ob, int state); /* stateenum */

boolean TryWalk (objtype *ob);
void 	SelectChaseDir (objtype *ob);
void 	SelectDodgeDir (objtype *ob);
void	SelectRunDir (objtype *ob);
void	MoveObj (objtype *ob, long move);
boolean SightPlayer (objtype *ob);

void	KillActor (objtype *ob);
void	DamageActor (objtype *ob, unsigned damage);

boolean CheckLine (objtype *ob);
boolean	CheckSight (objtype *ob);

/*
=============================================================================

						 WL_AGENT DEFINITIONS

=============================================================================
*/

//
// player state info
//
extern	long		thrustspeed;
extern	unsigned	plux,pluy;		// player coordinates scaled to unsigned

extern	int			anglefrac;
extern	int			facecount;

void	SpawnPlayer (int tilex, int tiley, int dir);
void 	DrawFace (void);
void	DrawHealth (void);
void	TakeDamage (int points,objtype *attacker);
void	HealSelf (int points);
void	DrawLevel (void);
void	DrawLives (void);
void	GiveExtraMan (void);
void	DrawScore (void);
void	GivePoints (long points);
void	DrawWeapon (void);
void	DrawKeys (void);
void	GiveWeapon (int weapon);
void	DrawAmmo (void);
void	GiveAmmo (int ammo);
void	GiveKey (int key);
void	GetBonus (statobj_t *check);

void	Thrust (int angle, long speed);

/*
=============================================================================

						 WL_ACT1 DEFINITIONS

=============================================================================
*/

extern	doorobj_t	doorobjlist[MAXDOORS],*lastdoorobj;
extern	int			doornum;

extern	unsigned	doorposition[MAXDOORS],pwallstate;

extern	byte		areaconnect[NUMAREAS][NUMAREAS];

extern	boolean		areabyplayer[NUMAREAS];

extern unsigned	pwallstate;
extern unsigned	pwallpos;			// amount a pushable wall has been moved (0-63)
extern unsigned	pwallx,pwally;
extern int			pwalldir;


void InitDoorList (void);
void InitStaticList (void);
void SpawnStatic (int tilex, int tiley, int type);
void SpawnDoor (int tilex, int tiley, boolean vertical, int lock);
void MoveDoors (void);
void MovePWalls (void);
void OpenDoor (int door);
void PlaceItemType (int itemtype, int tilex, int tiley);
void PushWall (int checkx, int checky, int dir);
void OperateDoor (int door);
void InitAreas (void);

/*
=============================================================================

						 WL_ACT2 DEFINITIONS

=============================================================================
*/

void A_DeathScream(objtype *ob);

void SpawnBJVictory(void);

void SpawnStand (enemy_t which, int tilex, int tiley, int dir);
void SpawnPatrol (enemy_t which, int tilex, int tiley, int dir);

void US_ControlPanel(byte);

void SpawnDeadGuard (int tilex, int tiley);
void SpawnBoss (int tilex, int tiley);
void SpawnGretel (int tilex, int tiley);
void SpawnTrans (int tilex, int tiley);
void SpawnUber (int tilex, int tiley);
void SpawnWill (int tilex, int tiley);
void SpawnDeath (int tilex, int tiley);
void SpawnAngel (int tilex, int tiley);
void SpawnSpectre (int tilex, int tiley);
void SpawnGhosts (int which, int tilex, int tiley);
void SpawnSchabbs (int tilex, int tiley);
void SpawnGift (int tilex, int tiley);
void SpawnFat (int tilex, int tiley);
void SpawnFakeHitler (int tilex, int tiley);
void SpawnHitler (int tilex, int tiley);

/*
=============================================================================

						 WL_TEXT DEFINITIONS

=============================================================================
*/

extern void HelpScreens();
extern void EndText();

/*
=============================================================================

						 HOOKA MODIFICATIONS

=============================================================================
*/

extern boolean w0;
extern boolean w1;
extern boolean s0;
extern boolean s1;
extern boolean s2;
extern boolean s3;

extern unsigned int H_SPEARINFO;
extern unsigned int H_BJPIC;
extern unsigned int H_CASTLEPIC;
extern unsigned int H_BLAZEPIC;
extern unsigned int H_TOPWINDOWPIC;
extern unsigned int H_LEFTWINDOWPIC;
extern unsigned int H_RIGHTWINDOWPIC;
extern unsigned int H_BOTTOMINFOPIC;
extern unsigned int C_OPTIONSPIC;
extern unsigned int C_CURSOR1PIC;
extern unsigned int C_CURSOR2PIC;
extern unsigned int C_NOTSELECTEDPIC;
extern unsigned int C_SELECTEDPIC;
extern unsigned int C_FXTITLEPIC;
extern unsigned int C_DIGITITLEPIC;
extern unsigned int C_MUSICTITLEPIC;
extern unsigned int C_MOUSELBACKPIC;
extern unsigned int C_BABYMODEPIC;
extern unsigned int C_EASYPIC;
extern unsigned int C_NORMALPIC;
extern unsigned int C_HARDPIC;
extern unsigned int C_LOADSAVEDISKPIC;
extern unsigned int C_DISKLOADING1PIC;
extern unsigned int C_DISKLOADING2PIC;
extern unsigned int C_CONTROLPIC;
extern unsigned int C_CUSTOMIZEPIC;
extern unsigned int C_LOADGAMEPIC;
extern unsigned int C_SAVEGAMEPIC;
extern unsigned int C_EPISODE1PIC;
extern unsigned int C_EPISODE2PIC;
extern unsigned int C_EPISODE3PIC;
extern unsigned int C_EPISODE4PIC;
extern unsigned int C_EPISODE5PIC;
extern unsigned int C_EPISODE6PIC;
extern unsigned int C_CODEPIC;
extern unsigned int C_TIMECODEPIC;
extern unsigned int C_LEVELPIC;
extern unsigned int C_NAMEPIC;
extern unsigned int C_SCOREPIC;
extern unsigned int C_JOY1PIC;
extern unsigned int C_JOY2PIC;
extern unsigned int L_GUYPIC;
extern unsigned int L_COLONPIC;
extern unsigned int L_NUM0PIC;
extern unsigned int L_NUM1PIC;
extern unsigned int L_NUM2PIC;
extern unsigned int L_NUM3PIC;
extern unsigned int L_NUM4PIC;
extern unsigned int L_NUM5PIC;
extern unsigned int L_NUM6PIC;
extern unsigned int L_NUM7PIC;
extern unsigned int L_NUM8PIC;
extern unsigned int L_NUM9PIC;
extern unsigned int L_PERCENTPIC;
extern unsigned int L_APIC;
extern unsigned int L_BPIC;
extern unsigned int L_CPIC;
extern unsigned int L_DPIC;
extern unsigned int L_EPIC;
extern unsigned int L_FPIC;
extern unsigned int L_GPIC;
extern unsigned int L_HPIC;
extern unsigned int L_IPIC;
extern unsigned int L_JPIC;
extern unsigned int L_KPIC;
extern unsigned int L_LPIC;
extern unsigned int L_MPIC;
extern unsigned int L_NPIC;
extern unsigned int L_OPIC;
extern unsigned int L_PPIC;
extern unsigned int L_QPIC;
extern unsigned int L_RPIC;
extern unsigned int L_SPIC;
extern unsigned int L_TPIC;
extern unsigned int L_UPIC;
extern unsigned int L_VPIC;
extern unsigned int L_WPIC;
extern unsigned int L_XPIC;
extern unsigned int L_YPIC;
extern unsigned int L_ZPIC;
extern unsigned int L_EXPOINTPIC;
extern unsigned int L_APOSTROPHEPIC;
extern unsigned int L_GUY2PIC;
extern unsigned int L_BJWINSPIC;
extern unsigned int STATUSBARPIC;
extern unsigned int TITLEPIC;
extern unsigned int PG13PIC;
extern unsigned int CREDITSPIC;
extern unsigned int HIGHSCORESPIC;
extern unsigned int KNIFEPIC;
extern unsigned int GUNPIC;
extern unsigned int MACHINEGUNPIC;
extern unsigned int GATLINGGUNPIC;
extern unsigned int NOKEYPIC;
extern unsigned int GOLDKEYPIC;
extern unsigned int SILVERKEYPIC;
extern unsigned int N_BLANKPIC;
extern unsigned int N_0PIC;
extern unsigned int N_1PIC;
extern unsigned int N_2PIC;
extern unsigned int N_3PIC;
extern unsigned int N_4PIC;
extern unsigned int N_5PIC;
extern unsigned int N_6PIC;
extern unsigned int N_7PIC;
extern unsigned int N_8PIC;
extern unsigned int N_9PIC;
extern unsigned int FACE1APIC;
extern unsigned int FACE1BPIC;
extern unsigned int FACE1CPIC;
extern unsigned int FACE2APIC;
extern unsigned int FACE2BPIC;
extern unsigned int FACE2CPIC;
extern unsigned int FACE3APIC;
extern unsigned int FACE3BPIC;
extern unsigned int FACE3CPIC;
extern unsigned int FACE4APIC;
extern unsigned int FACE4BPIC;
extern unsigned int FACE4CPIC;
extern unsigned int FACE5APIC;
extern unsigned int FACE5BPIC;
extern unsigned int FACE5CPIC;
extern unsigned int FACE6APIC;
extern unsigned int FACE6BPIC;
extern unsigned int FACE6CPIC;
extern unsigned int FACE7APIC;
extern unsigned int FACE7BPIC;
extern unsigned int FACE7CPIC;
extern unsigned int FACE8APIC;
extern unsigned int GOTGATLINGPIC;
extern unsigned int MUTANTBJPIC;
extern unsigned int PAUSEDPIC;
extern unsigned int GETPSYCHEDPIC;
extern unsigned int ORDERSCREEN;
extern unsigned int ERRORSCREEN;
extern unsigned int T_HELPART;
extern unsigned int T_DEMO0;
extern unsigned int T_DEMO1;
extern unsigned int T_DEMO2;
extern unsigned int T_DEMO3;
extern unsigned int T_ENDART1;
extern unsigned int T_ENDART2;
extern unsigned int T_ENDART3;
extern unsigned int T_ENDART4;
extern unsigned int T_ENDART5;
extern unsigned int T_ENDART6;
extern unsigned int H_KEYBOARDPIC;
extern unsigned int H_JOYPIC;
extern unsigned int H_HEALPIC;
extern unsigned int H_TREASUREPIC;
extern unsigned int H_GUNPIC;
extern unsigned int H_KEYPIC;
extern unsigned int H_WEAPON1234PIC;
extern unsigned int H_WOLFLOGOPIC;
extern unsigned int H_VISAPIC;
extern unsigned int H_MCPIC;
extern unsigned int H_IDLOGOPIC;
extern unsigned int H_SPEARSHOT;
extern unsigned int C_BACKDROPPIC;
extern unsigned int C_MOUSEPIC;
extern unsigned int C_JOYSTICKPIC;
extern unsigned int C_KEYBOARDPIC;
extern unsigned int C_HOWTOUGHPIC;
extern unsigned int C_WONSPEARPIC;
extern unsigned int TITLE1PIC;
extern unsigned int TITLE2PIC;
extern unsigned int GODMODEFACE1PIC;
extern unsigned int GODMODEFACE2PIC;
extern unsigned int GODMODEFACE3PIC;
extern unsigned int BJWAITING1PIC;
extern unsigned int BJWAITING2PIC;
extern unsigned int BJOUCHPIC;
extern unsigned int TITLEPALETTE;
extern unsigned int BJCOLLAPSE1PIC;
extern unsigned int BJCOLLAPSE2PIC;
extern unsigned int BJCOLLAPSE3PIC;
extern unsigned int BJCOLLAPSE4PIC;
extern unsigned int ENDPICPIC;
extern unsigned int ENDSCREEN11PIC;
extern unsigned int ENDSCREEN12PIC;
extern unsigned int ENDSCREEN3PIC;
extern unsigned int ENDSCREEN4PIC;
extern unsigned int ENDSCREEN5PIC;
extern unsigned int ENDSCREEN6PIC;
extern unsigned int ENDSCREEN7PIC;
extern unsigned int ENDSCREEN8PIC;
extern unsigned int ENDSCREEN9PIC;
extern unsigned int IDGUYS1PIC;
extern unsigned int IDGUYS2PIC;
extern unsigned int COPYPROTTOPPIC;
extern unsigned int COPYPROTBOXPIC;
extern unsigned int BOSSPIC1PIC;
extern unsigned int BOSSPIC2PIC;
extern unsigned int BOSSPIC3PIC;
extern unsigned int BOSSPIC4PIC;
extern unsigned int END1PALETTE;
extern unsigned int END2PALETTE;
extern unsigned int END3PALETTE;
extern unsigned int END4PALETTE;
extern unsigned int END5PALETTE;
extern unsigned int END6PALETTE;
extern unsigned int END7PALETTE;
extern unsigned int END8PALETTE;
extern unsigned int END9PALETTE;
extern unsigned int IDGUYSPALETTE;
extern unsigned int ENUMEND;

#include "wl_act3.h"

/* FixedByFrac */
fixed FixedByFrac(fixed a, fixed b);

#ifndef NOASM
#define FixedByFrac(x, y) \
__extension__  \
({ unsigned long z; \
 asm("imull %2; shrdl $16, %%edx, %%eax" : "=a" (z) : "a" (x), "q" (y) : "%edx"); \
 z; \
})
#endif

#endif
