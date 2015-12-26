package wolf.audio;
/*******************************************************************************
 * Copyright (c) 2006 Vladimir Silva and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Vladimir Silva - initial API and implementation
 *******************************************************************************/

import game.wolfsw.R;

public class SoundNames {

	public static final int [] Sounds = new int[86];
	
	/* Native Sound
	typedef enum {
		HITWALLSNDWL6,              // 0
		SELECTWPNSNDWL6,            // 1
		SELECTITEMSNDWL6,           // 2
		HEARTBEATSNDWL6,            // 3
		MOVEGUN2SNDWL6,             // 4
		MOVEGUN1SNDWL6,             // 5
		NOWAYSNDWL6,                // 6
		NAZIHITPLAYERSNDWL6,        // 7
		SCHABBSTHROWSND,         // 8
		PLAYERDEATHSNDWL6,          // 9
		DOGDEATHSNDWL6,             // 10
		ATKGATLINGSNDWL6,           // 11
		GETKEYSNDWL6,               // 12
		NOITEMSNDWL6,               // 13
		WALK1SNDWL6,                // 14
		WALK2SNDWL6,                // 15
		TAKEDAMAGESNDWL6,           // 16
		GAMEOVERSNDWL6,             // 17
		OPENDOORSNDWL6,             // 18
		CLOSEDOORSNDWL6,            // 19
		DONOTHINGSNDWL6,            // 20
		HALTSNDWL6,                 // 21
		DEATHSCREAM2SNDWL6,         // 22
		ATKKNIFESNDWL6,             // 23
		ATKPISTOLSNDWL6,            // 24
		DEATHSCREAM3SNDWL6,         // 25
		ATKMACHINEGUNSNDWL6,        // 26
		HITENEMYSNDWL6,             // 27
		SHOOTDOORSNDWL6,            // 28
		DEATHSCREAM1SNDWL6,         // 29
		GETMACHINESNDWL6,           // 30
		GETAMMOSNDWL6,              // 31
		SHOOTSNDWL6,                // 32
		HEALTH1SNDWL6,              // 33
		HEALTH2SNDWL6,              // 34
		BONUS1SNDWL6,               // 35
		BONUS2SNDWL6,               // 36
		BONUS3SNDWL6,               // 37
		GETGATLINGSNDWL6,           // 38
		ESCPRESSEDSNDWL6,           // 39
		LEVELDONESNDWL6,            // 40
		DOGBARKSNDWL6,              // 41
		ENDBONUS1SNDWL6,            // 42
		ENDBONUS2SNDWL6,            // 43
		BONUS1UPSNDWL6,             // 44
		BONUS4SNDWL6,               // 45
		PUSHWALLSNDWL6,             // 46
		NOBONUSSNDWL6,              // 47
		PERCENT100SNDWL6,           // 48
		BOSSACTIVESNDWL6,           // 49
		MUTTISND,                // 50
		SCHUTZADSNDWL6,             // 51
		AHHHGSNDWL6,                // 52
		DIESND,                  // 53
		EVASND,                  // 54
		GUTENTAGSND,             // 55
		LEBENSNDWL6,                // 56
		SCHEISTSND,              // 57
		NAZIFIRESNDWL6,             // 58
		BOSSFIRESNDWL6,             // 59
		SSFIRESNDWL6,               // 60
		SLURPIESNDWL6,              // 61
		TOT_HUNDSND,             // 62
		MEINGOTTSND,             // 63
		SCHABBSHASND,            // 64
		HITLERHASND,             // 65
		SPIONSNDWL6,                // 66
		NEINSOVASSNDWL6,            // 67
		DOGATTACKSNDWL6,            // 68
		FLAMETHROWERSND,         // 69
		MECHSTEPSNDWL6,             // 70
		GOOBSSNDWL6,                // 71
		YEAHSND,                 // 72
		DEATHSCREAM4SNDWL6,         // 73
		DEATHSCREAM5SNDWL6,         // 74
		DEATHSCREAM6SNDWL6,         // 75
		DEATHSCREAM7SNDWL6,         // 76
		DEATHSCREAM8SNDWL6,         // 77
		DEATHSCREAM9SNDWL6,         // 78
		DONNERSND,               // 79
		EINESND,                 // 80
		ERLAUBENSND,             // 81
		KEINSND,                 // 82
		MEINSND,                 // 83
		ROSESND,                 // 84
		MISSILEFIRESNDWL6,          // 85
		MISSILEHITSNDWL6,           // 86
		LASTSOUNDWL6
	     } 
	*/
	
	static {
		//Sounds[0] = R.raw.noway;	// hit wall
		Sounds[1] = R.raw.wpnup;	// sel wpn
		Sounds[2] = R.raw.itemup;	// hit wall
		
		Sounds[4] = R.raw.wpnup;	// Move gun(weapon up)
		Sounds[5] = R.raw.wpnup;	// move gun 1	
		Sounds[6] = R.raw.noway;	// no way	
		
		Sounds[9] = R.raw.bgdth1;	// player death	

		// DOGS
		Sounds[10] = R.raw.dogdth;	// death
		Sounds[68] = R.raw.dogattk;	// attack
		Sounds[41] = R.raw.dogbark;	// bark

		Sounds[11] = R.raw.gatlingun;	// gatling gun shoot
		Sounds[12] = R.raw.itemup;		// get key
		
		Sounds[18] = R.raw.doropn;
		Sounds[19] = R.raw.dorcls;
		Sounds[24] = R.raw.pistol;
		Sounds[25] = R.raw.bgdth2;
		Sounds[26] = R.raw.gatlingun;	// shoot rifle
		
		Sounds[30] = R.raw.itemup;	// pick rifle
		Sounds[31] = R.raw.itemup;	// pick item
		
		// Bonus
		Sounds[35] = R.raw.itemup;	// pick item
		Sounds[36] = R.raw.itemup;	// pick item
		Sounds[37] = R.raw.itemup;	// pick item
		
		Sounds[46] = R.raw.pstart;	// push wall
		
		Sounds[51] = R.raw.sssit;	// ?
		Sounds[52] = R.raw.pldeth;	// AUGHHH
		Sounds[53] = R.raw.popain;	// DIESND
		
		Sounds[54] = R.raw.eva;		// eva
		Sounds[55] = R.raw.gutag;	// Gutentag
		Sounds[56] = R.raw.ssdth;	// LEIBEN
		Sounds[57] = R.raw.scheist;	// scheist
		
		
		Sounds[58] = R.raw.pistol;	// Nazi fires
		Sounds[59] = R.raw.pistol;	// Boss fires
		Sounds[60] = R.raw.pistol;	// SSfires

		Sounds[61] = R.raw.dstink;
		Sounds[62] = R.raw.kntsit;
		Sounds[63] = R.raw.bspdth;	// LEIBEN
		
		Sounds[64] = R.raw.evillaugh;
		Sounds[65] = R.raw.pesit;	//
		Sounds[66] = R.raw.spion;		// SPION
		Sounds[67] = R.raw.skesit;		// ?
		
		Sounds[69] = R.raw.flamesnd;	// Flame throw
		
		Sounds[72] = R.raw.yeah;		// YEAH 
		Sounds[73] = R.raw.bgdth1;
		Sounds[74] = R.raw.bgdth2;
		Sounds[75] = R.raw.podth1;
		Sounds[76] = R.raw.podth2;
		Sounds[77] = R.raw.podth3;
		Sounds[78] = R.raw.bgdth2;
		
	}
/* Music Indexes
	typedef enum {
		CORNER_MUS,              // 0
		DUNGEON_MUSWL6,             // 1
		WARMARCH_MUS,            // 2
		GETTHEM_MUSWL6,             // 3
		HEADACHE_MUS,            // 4
		HITLWLTZ_MUSWL6,            // 5
		INTROCW3_MUS,            // 6
		NAZI_NOR_MUSWL6,            // 7
		NAZI_OMI_MUSWL6,            // 8
		POW_MUSWL6,                 // 9
		SALUTE_MUSWL6,              // 10
		SEARCHN_MUSWL6,             // 11
		SUSPENSE_MUSWL6,            // 12
		VICTORS_MUSWL6,             // 13
		WONDERIN_MUSWL6,            // 14
		FUNKYOU_MUS,             // 15
		ENDLEVEL_MUSWL6,            // 16
		GOINGAFT_MUSWL6,            // 17
		PREGNANT_MUS,            // 18
		ULTIMATE_MUSWL6,            // 19
		NAZI_RAP_MUS,            // 20
		ZEROHOUR_MUSWL6,            // 21
		TWELFTH_MUSWL6,             // 22
		ROSTER_MUS,              // 23
		URAHERO_MUSWL6,             // 24
		VICMARCH_MUSWL6,            // 25
		PACMAN_MUS,              // 26
		LASTMUSICWL6
	     } musicnamesWL6;
	*/
	public static final int [] Music = new int[27]; 
	
	static {
		Music[0] = R.raw.mus_emnycrnr;
		Music[1] = R.raw.mus_emnycrnr;
		Music[2] = R.raw.mus_marchwar;
		Music[3] = R.raw.mus_getthem;
		Music[9] = R.raw.mus_pow;
		Music[10] = R.raw.mus_emnycrnr;
		Music[11] = R.raw.mus_srchenmy;
		Music[12] = R.raw.mus_ultchall;
		Music[13] = R.raw.mus_getthem;
		Music[14] = R.raw.mus_srchenmy;
		Music[15] = R.raw.mus_srchenmy;
		Music[16] = R.raw.mus_ultchall;
		Music[17] = R.raw.mus_getthem;
		Music[18] = R.raw.mus_marchwar;
		Music[19] = R.raw.mus_ultchall;
		Music[21] = R.raw.mus_zerohour;
		Music[26] = R.raw.pacman;	
	}
}
