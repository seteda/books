#include "wl_def.h"

/*
=====================
=
= InitDigiMap
=
=====================
*/

int DigiMapWL6[LASTSOUNDWL6];
int DigiMapSOD[LASTSOUNDSOD];

static int wolfdigimapWL6[] =
{
	HALTSNDWL6,                0,
	DOGBARKSNDWL6,             1,
	CLOSEDOORSNDWL6,           2,
	OPENDOORSNDWL6,            3,
	ATKMACHINEGUNSNDWL6,       4,
	ATKPISTOLSNDWL6,           5,
	ATKGATLINGSNDWL6,          6,
	SCHUTZADSNDWL6,            7,
	GUTENTAGSND,            8,
	MUTTISND,               9,
	BOSSFIRESNDWL6,            10,
	SSFIRESNDWL6,              11,
	DEATHSCREAM1SNDWL6,        12,
	DEATHSCREAM2SNDWL6,        13,
	DEATHSCREAM3SNDWL6,        13,
	TAKEDAMAGESNDWL6,          14,
	PUSHWALLSNDWL6,            15,
	LEBENSNDWL6,               20,
	NAZIFIRESNDWL6,            21,
	SLURPIESNDWL6,             22,
	YEAHSND,				   32,
	DOGDEATHSNDWL6,            16,
	AHHHGSNDWL6,               17,
	DIESND,                 18,
	EVASND,                 19,
	TOT_HUNDSND,            23,
	MEINGOTTSND,            24,
	SCHABBSHASND,           25,
	HITLERHASND,            26,
	SPIONSNDWL6,               27,
	NEINSOVASSNDWL6,           28,
	DOGATTACKSNDWL6,           29,
	LEVELDONESNDWL6,           30,
	MECHSTEPSNDWL6,			   31,
	SCHEISTSND,			   33,
	DEATHSCREAM4SNDWL6,		   34,		/* AIIEEE */
	DEATHSCREAM5SNDWL6,		   35,		/* DEE-DEE */
	DONNERSND,			   36,		/* EPISODE 4 BOSS DIE */
	EINESND,				   37,		/* EPISODE 4 BOSS SIGHTING */
	ERLAUBENSND,			   38,		/* EPISODE 6 BOSS SIGHTING */
	DEATHSCREAM6SNDWL6,		   39,		/* FART */
	DEATHSCREAM7SNDWL6,		   40,		/* GASP */
	DEATHSCREAM8SNDWL6,		   41,		/* GUH-BOY! */
	DEATHSCREAM9SNDWL6,		   42,		/* AH GEEZ! */
	KEINSND,				   43,		/* EPISODE 5 BOSS SIGHTING */
	MEINSND,				   44,		/* EPISODE 6 BOSS DIE */
	ROSESND,				   45,		/* EPISODE 5 BOSS DIE */
	LASTSOUNDWL6
};

static int wolfdigimapWL1[] =
{
	HALTSNDWL6,                0,
	DOGBARKSNDWL6,             1,
	CLOSEDOORSNDWL6,           2,
	OPENDOORSNDWL6,            3,
	ATKMACHINEGUNSNDWL6,       4,
	ATKPISTOLSNDWL6,           5,
	ATKGATLINGSNDWL6,          6,
	SCHUTZADSNDWL6,            7,
	GUTENTAGSND,            8,
	MUTTISND,               9,
	BOSSFIRESNDWL6,            10,
	SSFIRESNDWL6,              11,
	DEATHSCREAM1SNDWL6,        12,
	DEATHSCREAM2SNDWL6,        13,
	DEATHSCREAM3SNDWL6,        13,
	TAKEDAMAGESNDWL6,          14,
	PUSHWALLSNDWL6,            15,
	LEBENSNDWL6,               20,
	NAZIFIRESNDWL6,            21,
	SLURPIESNDWL6,             22,
	YEAHSND,				   32,
	LASTSOUNDWL6
};

static int wolfdigimapSOD[] =
{
	HALTSNDSOD,                0,
	CLOSEDOORSNDSOD,           2,
	OPENDOORSNDSOD,            3,
	ATKMACHINEGUNSNDSOD,       4,
	ATKPISTOLSNDSOD,           5,
	ATKGATLINGSNDSOD,          6,
	SCHUTZADSNDSOD,            7,
	BOSSFIRESNDSOD,            8,
	SSFIRESNDSOD,              9,
	DEATHSCREAM1SNDSOD,        10,
	DEATHSCREAM2SNDSOD,        11,
	TAKEDAMAGESNDSOD,          12,
	PUSHWALLSNDSOD,            13,
	AHHHGSNDSOD,               15,
	LEBENSNDSOD,               16,
	NAZIFIRESNDSOD,            17,
	SLURPIESNDSOD,             18,
	LEVELDONESNDSOD,           22,
	DEATHSCREAM4SNDSOD,		   23,		// AIIEEE
	DEATHSCREAM3SNDSOD,        23,		// DOUBLY-MAPPED!!!
	DEATHSCREAM5SNDSOD,		   24,		// DEE-DEE
	DEATHSCREAM6SNDSOD,		   25,		// FART
	DEATHSCREAM7SNDSOD,		   26,		// GASP
	DEATHSCREAM8SNDSOD,		   27,		// GUH-BOY!
	DEATHSCREAM9SNDSOD,		   28,		// AH GEEZ!
	GETGATLINGSNDSOD,		   38,		// Got Gat replacement
	DOGBARKSNDSOD,             1,
	DOGDEATHSNDSOD,            14,
	SPIONSNDSOD,               19,
	NEINSOVASSNDSOD,           20,
	DOGATTACKSNDSOD,           21,
	TRANSSIGHTSND,		   29,		// Trans Sight
	TRANSDEATHSND,		   30,		// Trans Death
	WILHELMSIGHTSND,		   31,		// Wilhelm Sight
	WILHELMDEATHSND,		   32,		// Wilhelm Death
	UBERDEATHSND,		   33,		// Uber Death
	KNIGHTSIGHTSND,		   34,		// Death Knight Sight
	KNIGHTDEATHSND,		   35,		// Death Knight Death
	ANGELSIGHTSND,		   36,		// Angel Sight
	ANGELDEATHSND,		   37,		// Angel Death
	GETSPEARSND,			   39,		// Got Spear replacement
	LASTSOUNDSOD
};

static int wolfdigimapSDM[] =
{
	HALTSNDSOD,                0,
	CLOSEDOORSNDSOD,           2,
	OPENDOORSNDSOD,            3,
	ATKMACHINEGUNSNDSOD,       4,
	ATKPISTOLSNDSOD,           5,
	ATKGATLINGSNDSOD,          6,
	SCHUTZADSNDSOD,            7,
	BOSSFIRESNDSOD,            8,
	SSFIRESNDSOD,              9,
	DEATHSCREAM1SNDSOD,        10,
	DEATHSCREAM2SNDSOD,        11,
	TAKEDAMAGESNDSOD,          12,
	PUSHWALLSNDSOD,            13,
	AHHHGSNDSOD,               15,
	LEBENSNDSOD,               16,
	NAZIFIRESNDSOD,            17,
	SLURPIESNDSOD,             18,
	LEVELDONESNDSOD,           22,
	DEATHSCREAM4SNDSOD,		   23,		// AIIEEE
	DEATHSCREAM3SNDSOD,        23,		// DOUBLY-MAPPED!!!
	DEATHSCREAM5SNDSOD,		   24,		// DEE-DEE
	DEATHSCREAM6SNDSOD,		   25,		// FART
	DEATHSCREAM7SNDSOD,		   26,		// GASP
	DEATHSCREAM8SNDSOD,		   27,		// GUH-BOY!
	DEATHSCREAM9SNDSOD,		   28,		// AH GEEZ!
	GETGATLINGSNDSOD,		   38,		// Got Gat replacement
	LASTSOUNDSOD
};

void InitDigiMap()
{
	int *map, i;

	if (w0 == true){
		for (i = 0; i < LASTSOUNDWL6; i++)
			DigiMapWL6[i] = -1;
	
		for (map = wolfdigimapWL1; *map != LASTSOUNDWL6; map += 2)
			DigiMapWL6[map[0]] = map[1];
	} else if (w1 == true){
		for (i = 0; i < LASTSOUNDWL6; i++)
			DigiMapWL6[i] = -1;
	
		for (map = wolfdigimapWL6; *map != LASTSOUNDWL6; map += 2)
			DigiMapWL6[map[0]] = map[1];
	} else if (s0 == true){
		for (i = 0; i < LASTSOUNDSOD; i++)
			DigiMapSOD[i] = -1;
	
		for (map = wolfdigimapSDM; *map != LASTSOUNDSOD; map += 2)
			DigiMapSOD[map[0]] = map[1];
	} else if (s1 == true || s2 == true || s3 == true){
		for (i = 0; i < LASTSOUNDSOD; i++)
			DigiMapSOD[i] = -1;
	
		for (map = wolfdigimapSOD; *map != LASTSOUNDSOD; map += 2)
			DigiMapSOD[map[0]] = map[1];
	}
}
