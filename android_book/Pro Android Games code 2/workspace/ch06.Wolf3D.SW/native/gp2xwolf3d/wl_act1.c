#include "wl_def.h"

/*
=============================================================================

							STATICS

=============================================================================
*/


statobj_t statobjlist[MAXSTATS], *laststatobj;

struct
{
	int	picnum;
	stat_t	type;
} static const statinfoWL6[] =
{
{SPR_STAT_0WL6},					// puddle          spr1v
{SPR_STAT_1WL6,block},				// Green Barrel    "
{SPR_STAT_2WL6,block},				// Table/chairs    "
{SPR_STAT_3WL6,block},				// Floor lamp      "
{SPR_STAT_4WL6},					// Chandelier      "
{SPR_STAT_5WL6,block},				// Hanged man      "
{SPR_STAT_6WL6,bo_alpo},			// Bad food        "
{SPR_STAT_7WL6,block},				// Red pillar      "
{SPR_STAT_8WL6,block},				// Tree            spr2v
{SPR_STAT_9WL6},					// Skeleton flat   "
{SPR_STAT_10WL6,block},			// Sink            " (SOD:gibs)
{SPR_STAT_11WL6,block},			// Potted plant    "
{SPR_STAT_12WL6,block},			// Urn             "
{SPR_STAT_13WL6,block},			// Bare table      "
{SPR_STAT_14WL6},					// Ceiling light   "
{SPR_STAT_15WL6},					// Kitchen stuff   "
{SPR_STAT_16WL6,block},			// suit of armor   spr3v
{SPR_STAT_17WL6,block},			// Hanging cage    "
{SPR_STAT_18WL6,block},			// SkeletoninCage  "
{SPR_STAT_19WL6},					// Skeleton relax  "
{SPR_STAT_20WL6,bo_key1},			// Key 1           "
{SPR_STAT_21WL6,bo_key2},			// Key 2           "
{SPR_STAT_22WL6,block},			// stuff				(SOD:gibs)
{SPR_STAT_23WL6},					// stuff
{SPR_STAT_24WL6,bo_food}, 			// Good food       spr4v
{SPR_STAT_25WL6,bo_firstaid},		// First aid       "
{SPR_STAT_26WL6,bo_clip},			// Clip            "
{SPR_STAT_27WL6,bo_machinegun},	// Machine gun     "
{SPR_STAT_28WL6,bo_chaingun},		// Gatling gun     "
{SPR_STAT_29WL6,bo_cross},			// Cross           "
{SPR_STAT_30WL6,bo_chalice},		// Chalice         "
{SPR_STAT_31WL6,bo_bible},			// Bible           "
{SPR_STAT_32WL6,bo_crown},			// crown           spr5v
{SPR_STAT_33WL6,bo_fullheal},		// one up          "
{SPR_STAT_34WL6,bo_gibs},			// gibs            "
{SPR_STAT_35WL6,block},			// barrel          "
{SPR_STAT_36WL6,block},			// well            "
{SPR_STAT_37WL6,block},			// Empty well      "
{SPR_STAT_38WL6,bo_gibs},			// Gibs 2          "
{SPR_STAT_39WL6,block},			// flag				"
{SPR_STAT_40WL6,block},			// Call Apogee		spr7v
{SPR_STAT_41WL6},					// junk            "
{SPR_STAT_42WL6},					// junk 		   "
{SPR_STAT_43WL6},					// junk            "
{SPR_STAT_44WL6},					// pots            "
{SPR_STAT_45WL6,block},			// stove           " (SOD:gibs)
{SPR_STAT_46WL6,block},			// spears          " (SOD:gibs)
{SPR_STAT_47WL6},					// vines			"
{SPR_STAT_26WL6,bo_clip2},			// Clip            "
{-1}							// terminator
};

struct
{
	int	picnum;
	stat_t	type;
} static const statinfoSOD[] =
{
{SPR_STAT_0SOD},					// puddle          spr1v
{SPR_STAT_1SOD,block},				// Green Barrel    "
{SPR_STAT_2SOD,block},				// Table/chairs    "
{SPR_STAT_3SOD,block},				// Floor lamp      "
{SPR_STAT_4SOD},					// Chandelier      "
{SPR_STAT_5SOD,block},				// Hanged man      "
{SPR_STAT_6SOD,bo_alpo},			// Bad food        "
{SPR_STAT_7SOD,block},				// Red pillar      "
{SPR_STAT_8SOD,block},				// Tree            spr2v
{SPR_STAT_9SOD},					// Skeleton flat   "
{SPR_STAT_10SOD,block},			// Sink            " (SOD:gibs)
{SPR_STAT_11SOD,block},			// Potted plant    "
{SPR_STAT_12SOD,block},			// Urn             "
{SPR_STAT_13SOD,block},			// Bare table      "
{SPR_STAT_14SOD},					// Ceiling light   "
{SPR_STAT_15SOD,block},			// Gibs!
{SPR_STAT_16SOD,block},			// suit of armor   spr3v
{SPR_STAT_17SOD,block},			// Hanging cage    "
{SPR_STAT_18SOD,block},			// SkeletoninCage  "
{SPR_STAT_19SOD},					// Skeleton relax  "
{SPR_STAT_20SOD,bo_key1},			// Key 1           "
{SPR_STAT_21SOD,bo_key2},			// Key 2           "
{SPR_STAT_22SOD,block},			// stuff				(SOD:gibs)
{SPR_STAT_23SOD},					// stuff
{SPR_STAT_24SOD,bo_food}, 			// Good food       spr4v
{SPR_STAT_25SOD,bo_firstaid},		// First aid       "
{SPR_STAT_26SOD,bo_clip},			// Clip            "
{SPR_STAT_27SOD,bo_machinegun},	// Machine gun     "
{SPR_STAT_28SOD,bo_chaingun},		// Gatling gun     "
{SPR_STAT_29SOD,bo_cross},			// Cross           "
{SPR_STAT_30SOD,bo_chalice},		// Chalice         "
{SPR_STAT_31SOD,bo_bible},			// Bible           "
{SPR_STAT_32SOD,bo_crown},			// crown           spr5v
{SPR_STAT_33SOD,bo_fullheal},		// one up          "
{SPR_STAT_34SOD,bo_gibs},			// gibs            "
{SPR_STAT_35SOD,block},			// barrel          "
{SPR_STAT_36SOD,block},			// well            "
{SPR_STAT_37SOD,block},			// Empty well      "
{SPR_STAT_38SOD,bo_gibs},			// Gibs 2          "
{SPR_STAT_39SOD,block},			// flag				"
{SPR_STAT_40SOD},					// Red light
{SPR_STAT_41SOD},					// junk            "
{SPR_STAT_42SOD},					// junk 		   "
{SPR_STAT_43SOD},					// junk            "
{SPR_STAT_44SOD,block},			// Gibs!
{SPR_STAT_45SOD,block},			// stove           " (SOD:gibs)
{SPR_STAT_46SOD,block},			// spears          " (SOD:gibs)
{SPR_STAT_47SOD},					// vines			"
{SPR_STAT_48SOD,block},			// marble pillar
{SPR_STAT_49SOD,bo_25clip},		// bonus 25 clip
{SPR_STAT_50SOD,block},			// truck
{SPR_STAT_51SOD,bo_spear},			// SPEAR OF DESTINY!
{SPR_STAT_26SOD,bo_clip2},			// Clip            "
{-1}							// terminator
};

/*
===============
=
= InitStaticList
=
===============
*/

void InitStaticList()
{
	laststatobj = &statobjlist[0];
}

/*
===============
=
= SpawnStatic
=
===============
*/

void SpawnStatic(int tilex, int tiley, int type)
{
	if (w0 == true || w1 == true){
		laststatobj->shapenum = statinfoWL6[type].picnum;
	} else {
		laststatobj->shapenum = statinfoSOD[type].picnum;
	}
	laststatobj->tilex = tilex;
	laststatobj->tiley = tiley;
	laststatobj->visspot = &spotvis[tilex][tiley];

	if (w0 == true || w1 == true){
		switch (statinfoWL6[type].type)
		{
		case block:
			actorat[tilex][tiley] = 1;	// consider it a blocking tile
		case dressing:
			laststatobj->flags = 0;
			break;
	
		case	bo_cross:
		case	bo_chalice:
		case	bo_bible:
		case	bo_crown:
		case	bo_fullheal:
			if (!loadedgame)
				gamestate.treasuretotal++;
	
		case	bo_firstaid:
		case	bo_key1:
		case	bo_key2:
		case	bo_key3:
		case	bo_key4:
		case	bo_clip:
		case	bo_25clip:
		case	bo_machinegun:
		case	bo_chaingun:
		case	bo_food:
		case	bo_alpo:
		case	bo_gibs:
		case	bo_spear:
			laststatobj->flags = FL_BONUS;
			laststatobj->itemnumber = statinfoWL6[type].type;
			break;
		default: 
			break;
		}
	} else {
		switch (statinfoSOD[type].type)
		{
		case block:
			actorat[tilex][tiley] = 1;	// consider it a blocking tile
		case dressing:
			laststatobj->flags = 0;
			break;
	
		case	bo_cross:
		case	bo_chalice:
		case	bo_bible:
		case	bo_crown:
		case	bo_fullheal:
			if (!loadedgame)
				gamestate.treasuretotal++;
	
		case	bo_firstaid:
		case	bo_key1:
		case	bo_key2:
		case	bo_key3:
		case	bo_key4:
		case	bo_clip:
		case	bo_25clip:
		case	bo_machinegun:
		case	bo_chaingun:
		case	bo_food:
		case	bo_alpo:
		case	bo_gibs:
		case	bo_spear:
			laststatobj->flags = FL_BONUS;
			laststatobj->itemnumber = statinfoSOD[type].type;
			break;
		default: 
			break;
		}
	}

	laststatobj++;

	if (laststatobj == &statobjlist[MAXSTATS])
		Quit ("Too many static objects!\n");
}

/*
===============
=
= PlaceItemType
=
= Called during game play to drop actors' items.  It finds the proper
= item number based on the item type (bo_???).  If there are no free item
= spots, nothing is done.
=
===============
*/

void PlaceItemType(int itemtype, int tilex, int tiley)
{
	int type;
	statobj_t *spot;

//
// find the item number
//
	for (type = 0; ; type++)
	{
		if (w0 == true || w1 == true){
			if (statinfoWL6[type].picnum == -1) /* end of list */
				Quit("PlaceItemType: couldn't find type!");
			if (statinfoWL6[type].type == itemtype)
				break;
		} else {
			if (statinfoSOD[type].picnum == -1) /* end of list */
				Quit("PlaceItemType: couldn't find type!");
			if (statinfoSOD[type].type == itemtype)
				break;
		}
	}

//
// find a spot in statobjlist to put it in
//
	for (spot=&statobjlist[0] ; ; spot++)
	{
		if (spot==laststatobj)
		{
			if (spot == &statobjlist[MAXSTATS])
				return;	/* no free spots */
			laststatobj++;	/* space at end */
			break;
		}

		if (spot->shapenum == -1) /* -1 is a free spot */
			break;
	}
//
// place it
//
	if (w0 == true || w1 == true){
		spot->shapenum = statinfoWL6[type].picnum;
	} else {
		spot->shapenum = statinfoSOD[type].picnum;
	}
	spot->tilex = tilex;
	spot->tiley = tiley;
	spot->visspot = &spotvis[tilex][tiley];
	spot->flags = FL_BONUS;
	if (w0 == true || w1 == true){
		spot->itemnumber = statinfoWL6[type].type;
	} else {
		spot->itemnumber = statinfoSOD[type].type;
	}
}

/*
=============================================================================

							DOORS

doorobjlist[] holds most of the information for the doors

doorposition[] holds the amount the door is open, ranging from 0 to 0xffff
	this is directly accessed by AsmRefresh during rendering

The number of doors is limited to 64 because a spot in tilemap holds the
	door number in the low 6 bits, with the high bit meaning a door center
	and bit 6 meaning a door side tile

Open doors conect two areas, so sounds will travel between them and sight
	will be checked when the player is in a connected area.

Areaconnect is incremented/decremented by each door. If >0 they connect

Every time a door opens or closes the areabyplayer matrix gets recalculated.
	An area is true if it connects with the player's current spot.

=============================================================================
*/

#define OPENTICS	300

doorobj_t	doorobjlist[MAXDOORS],*lastdoorobj;
int			doornum;

unsigned	doorposition[MAXDOORS];	// leading edge of door 0=closed
					// 0xffff = fully open
byte		areaconnect[NUMAREAS][NUMAREAS];

boolean		areabyplayer[NUMAREAS];


/*
==============
=
= ConnectAreas
=
= Scans outward from playerarea, marking all connected areas
=
==============
*/

void RecursiveConnect(int areanumber)
{
	int i;

	for (i = 0; i < NUMAREAS; i++)
	{
		if (areaconnect[areanumber][i] && !areabyplayer[i])
		{
			areabyplayer[i] = true;
			RecursiveConnect(i);
		}
	}
}

void ConnectAreas()
{
	memset(areabyplayer, 0, sizeof(areabyplayer));
	areabyplayer[player->areanumber] = true;
	RecursiveConnect(player->areanumber);
}

void InitAreas()
{
	memset(areabyplayer, 0, sizeof(areabyplayer));
	areabyplayer[player->areanumber] = true;
}

/*
===============
=
= InitDoorList
=
===============
*/

void InitDoorList()
{
	memset(areabyplayer, 0, sizeof(areabyplayer));
	memset(areaconnect, 0, sizeof(areaconnect));

	lastdoorobj = &doorobjlist[0];
	doornum = 0;
}

/*
===============
=
= SpawnDoor
=
===============
*/

void SpawnDoor(int tilex, int tiley, boolean vertical, int lock)
{
	word *map;

	if (doornum == 64)
		Quit ("64+ doors on level!");

	doorposition[doornum] = 0;		// doors start out fully closed
	lastdoorobj->tilex = tilex;
	lastdoorobj->tiley = tiley;
	lastdoorobj->vertical = vertical;
	lastdoorobj->lock = lock;
	lastdoorobj->action = dr_closed;

	actorat[tilex][tiley] = doornum | 0x80;	// consider it a solid wall

//
// make the door tile a special tile, and mark the adjacent tiles
// for door sides
//
	tilemap[tilex][tiley] = doornum | 0x80;
	map = (word *)(mapsegs[0] + farmapylookup[tiley]+tilex);
	if (vertical)
	{
		*map = *(map-1);                        // set area number
		tilemap[tilex][tiley-1] |= 0x40;
		tilemap[tilex][tiley+1] |= 0x40;
	}
	else
	{
		*map = *(map-mapwidth);					// set area number
		tilemap[tilex-1][tiley] |= 0x40;
		tilemap[tilex+1][tiley] |= 0x40;
	}

	doornum++;
	lastdoorobj++;
}

/*
=====================
=
= OpenDoor
=
=====================
*/

void OpenDoor(int door)
{
	if (doorobjlist[door].action == dr_open)
		doorobjlist[door].ticcount = 0;			// reset open time
	else
		doorobjlist[door].action = dr_opening;	// start it opening
}

/*
=====================
=
= CloseDoor
=
=====================
*/

void CloseDoor(int door)
{
	int tilex, tiley, area;
	objtype *check;

//
// don't close on anything solid
//
	tilex = doorobjlist[door].tilex;
	tiley = doorobjlist[door].tiley;

	if (actorat[tilex][tiley])
		return;

	if (player->tilex == tilex && player->tiley == tiley)
		return;

	if (doorobjlist[door].vertical)
	{
		if (player->tiley == tiley)
		{
			if (((player->x+MINDIST) >>TILESHIFT) == tilex)
				return;
			if (((player->x-MINDIST) >>TILESHIFT) == tilex)
				return;
		}

		if (actorat[tilex-1][tiley] & 0x8000)
			check = &objlist[actorat[tilex-1][tiley] & ~0x8000];
		else
			check = NULL;

		if (check && ((check->x+MINDIST) >> TILESHIFT) == tilex)
			return;
		
		if (actorat[tilex+1][tiley] & 0x8000)
			check = &objlist[actorat[tilex+1][tiley] & ~0x8000];
		else
			check = NULL;

		if (check && ((check->x-MINDIST) >> TILESHIFT) == tilex)
			return;
	}
	else if (!doorobjlist[door].vertical)
	{
		if (player->tilex == tilex)
		{
			if ( ((player->y+MINDIST) >>TILESHIFT) == tiley )
				return;
			if ( ((player->y-MINDIST) >>TILESHIFT) == tiley )
				return;
		}
		
		if (actorat[tilex][tiley-1] & 0x8000)
			check = &objlist[actorat[tilex][tiley-1] & ~0x8000];
		else
			check = NULL;

		if (check && ((check->y+MINDIST) >> TILESHIFT) == tiley )
			return;
		
		if (actorat[tilex][tiley+1] & 0x8000)
			check = &objlist[actorat[tilex][tiley+1] & ~0x8000];
		else
			check = NULL;
		
		if (check && ((check->y-MINDIST) >> TILESHIFT) == tiley )
			return;
	}


//
// play door sound if in a connected area
//
	area = *(mapsegs[0] + farmapylookup[doorobjlist[door].tiley]
			+doorobjlist[door].tilex)-AREATILE;
	if (areabyplayer[area])
	{
		if (w0 == true || w1 == true){
			PlaySoundLocTile(CLOSEDOORSNDWL6,doorobjlist[door].tilex,doorobjlist[door].tiley);	// JAB
		} else {
			PlaySoundLocTile(CLOSEDOORSNDSOD,doorobjlist[door].tilex,doorobjlist[door].tiley);	// JAB
		}
	}

	doorobjlist[door].action = dr_closing;
//
// make the door space solid
//
	actorat[tilex][tiley] = door | 0x80;
}

/*
=====================
=
= OperateDoor
=
= The player wants to change the door's direction
=
=====================
*/

void OperateDoor(int door)
{
	int	lock;

	lock = doorobjlist[door].lock;
	if (lock >= dr_lock1 && lock <= dr_lock4)
	{
		if ( ! (gamestate.keys & (1 << (lock-dr_lock1) ) ) )
		{
			if (w0 == true || w1 == true){
				SD_PlaySoundWL6 (NOWAYSNDWL6);
			} else {
				SD_PlaySoundSOD (NOWAYSNDSOD);
			}
			return;
		}
	}

	switch (doorobjlist[door].action)
	{
	case dr_closed:
	case dr_closing:
		OpenDoor (door);
		break;
	case dr_open:
	case dr_opening:
		CloseDoor (door);
		break;
	}
}

/*
===============
=
= DoorOpen
=
= Close the door after three seconds
=
===============
*/

void DoorOpen (int door)
{
	if ( (doorobjlist[door].ticcount += tics) >= OPENTICS)
		CloseDoor (door);
}



/*
===============
=
= DoorOpening
=
===============
*/

void DoorOpening(int door)
{
	int		area1,area2;
	word *map;
	long	position;

	position = doorposition[door];
	if (!position)
	{
	//
	// door is just starting to open, so connect the areas
	//
		map = (word *)(mapsegs[0] + farmapylookup[doorobjlist[door].tiley]
			+doorobjlist[door].tilex);

		if (doorobjlist[door].vertical)
		{
			area1 =	*(map+1);
			area2 =	*(map-1);
		}
		else
		{
			area1 =	*(map-mapwidth);
			area2 =	*(map+mapwidth);
		}
		area1 -= AREATILE;
		area2 -= AREATILE;
		areaconnect[area1][area2]++;
		areaconnect[area2][area1]++;
		ConnectAreas ();
		if (areabyplayer[area1])
		{
			if (w0 == true || w1 == true){
				PlaySoundLocTile(OPENDOORSNDWL6,doorobjlist[door].tilex,doorobjlist[door].tiley);	// JAB
			} else {
				PlaySoundLocTile(OPENDOORSNDSOD,doorobjlist[door].tilex,doorobjlist[door].tiley);	// JAB
			}
		}
	}

//
// slide the door by an adaptive amount
//
	position += tics<<10;
	if (position >= 0xffff)
	{
	//
	// door is all the way open
	//
		position = 0xffff;
		doorobjlist[door].ticcount = 0;
		doorobjlist[door].action = dr_open;
		actorat[doorobjlist[door].tilex][doorobjlist[door].tiley] = 0;
	}

	doorposition[door] = position;
}


/*
===============
=
= DoorClosing
=
===============
*/

void DoorClosing(int door)
{
	int		area1,area2;
	word *map;
	long	position;
	int		tilex,tiley;

	tilex = doorobjlist[door].tilex;
	tiley = doorobjlist[door].tiley;

	if ((actorat[tilex][tiley] != (door | 0x80))
	|| (player->tilex == tilex && player->tiley == tiley) )
	{			// something got inside the door
		OpenDoor(door);
		return;
	};

	position = doorposition[door];

//
// slide the door by an adaptive amount
//
	position -= tics<<10;
	if (position <= 0)
	{
	//
	// door is closed all the way, so disconnect the areas
	//
		position = 0;

		doorobjlist[door].action = dr_closed;

		map = (word *)(mapsegs[0] + farmapylookup[doorobjlist[door].tiley]
			+doorobjlist[door].tilex);

		if (doorobjlist[door].vertical)
		{
			area1 =	*(map+1);
			area2 =	*(map-1);
		}
		else
		{
			area1 =	*(map-mapwidth);
			area2 =	*(map+mapwidth);
		}
		area1 -= AREATILE;
		area2 -= AREATILE;
		areaconnect[area1][area2]--;
		areaconnect[area2][area1]--;

		ConnectAreas ();
	}

	doorposition[door] = position;
}

/*
=====================
=
= MoveDoors
=
= Called from PlayLoop
=
=====================
*/

void MoveDoors()
{
	int door;

	if (gamestate.victoryflag)		// don't move door during victory sequence
		return;

	for (door = 0 ; door < doornum ; door++)
		switch(doorobjlist[door].action)
		{
		case dr_open:
			DoorOpen (door);
			break;

		case dr_opening:
			DoorOpening(door);
			break;

		case dr_closing:
			DoorClosing(door);
			break;
		default:
			break;
		}
}


/*
=============================================================================

						PUSHABLE WALLS

=============================================================================
*/

unsigned pwallstate;
unsigned pwallpos; // amount a pushable wall has been moved (0-63)
unsigned pwallx, pwally;
int pwalldir;

/*
===============
=
= PushWall
=
===============
*/

void PushWall(int checkx, int checky, int dir)
{
	int oldtile;

	if (pwallstate)
		return;

	oldtile = tilemap[checkx][checky];
	if (!oldtile)
		return;

	switch (dir)
	{
	case di_north:
		if (actorat[checkx][checky-1])
		{
			if (w0 == true || w1 == true){
				SD_PlaySoundWL6 (NOWAYSNDWL6);
			} else {
				SD_PlaySoundSOD (NOWAYSNDSOD);
			}
			return;
		}
		actorat[checkx][checky-1] = tilemap[checkx][checky-1] = oldtile;
		break;

	case di_east:
		if (actorat[checkx+1][checky])
		{
			if (w0 == true || w1 == true){
				SD_PlaySoundWL6 (NOWAYSNDWL6);
			} else {
				SD_PlaySoundSOD (NOWAYSNDSOD);
			}
			return;
		}
		actorat[checkx+1][checky] = tilemap[checkx+1][checky] = oldtile;
		break;

	case di_south:
		if (actorat[checkx][checky+1])
		{
			if (w0 == true || w1 == true){
				SD_PlaySoundWL6 (NOWAYSNDWL6);
			} else {
				SD_PlaySoundSOD (NOWAYSNDSOD);
			}
			return;
		}
		actorat[checkx][checky+1] = tilemap[checkx][checky+1] = oldtile;
		break;

	case di_west:
		if (actorat[checkx-1][checky])
		{
			if (w0 == true || w1 == true){
				SD_PlaySoundWL6 (NOWAYSNDWL6);
			} else {
				SD_PlaySoundSOD (NOWAYSNDSOD);
			}
			return;
		}
		actorat[checkx-1][checky] = tilemap[checkx-1][checky] = oldtile;
		break;
	}

	gamestate.secretcount++;
	pwallx = checkx;
	pwally = checky;
	pwalldir = dir;
	pwallstate = 1;
	pwallpos = 0;
	tilemap[pwallx][pwally] |= 0xc0;
	*(mapsegs[1]+farmapylookup[pwally]+pwallx) = 0;	// remove P tile info

	if (w0 == true || w1 == true){
		SD_PlaySoundWL6(PUSHWALLSNDWL6);
	} else {
		SD_PlaySoundSOD(PUSHWALLSNDSOD);
	}
}



/*
=================
=
= MovePWalls
=
=================
*/

void MovePWalls()
{
	int oldblock, oldtile;

	if (!pwallstate)
		return;

	oldblock = pwallstate/128;

	pwallstate += tics;

	if (pwallstate/128 != oldblock)
	{
	// block crossed into a new block
		oldtile = tilemap[pwallx][pwally] & 63;

		//
		// the tile can now be walked into
		//
		tilemap[pwallx][pwally] = 0;
		actorat[pwallx][pwally] = 0;
		*(mapsegs[0]+farmapylookup[pwally]+pwallx) = player->areanumber+AREATILE;

		//
		// see if it should be pushed farther
		//
		if (pwallstate>256)
		{
		//
		// the block has been pushed two tiles
		//
			pwallstate = 0;
			return;
		}
		else
		{
			switch (pwalldir)
			{
			case di_north:
				pwally--;
				if (actorat[pwallx][pwally-1])
				{
					pwallstate = 0;
					return;
				}
				actorat[pwallx][pwally-1] =
				tilemap[pwallx][pwally-1] = oldtile;
				break;

			case di_east:
				pwallx++;
				if (actorat[pwallx+1][pwally])
				{
					pwallstate = 0;
					return;
				}
				actorat[pwallx+1][pwally] =
				tilemap[pwallx+1][pwally] = oldtile;
				break;

			case di_south:
				pwally++;
				if (actorat[pwallx][pwally+1])
				{
					pwallstate = 0;
					return;
				}
				actorat[pwallx][pwally+1] =
				tilemap[pwallx][pwally+1] = oldtile;
				break;

			case di_west:
				pwallx--;
				if (actorat[pwallx-1][pwally])
				{
					pwallstate = 0;
					return;
				}
				actorat[pwallx-1][pwally] =
				tilemap[pwallx-1][pwally] = oldtile;
				break;
			}

			tilemap[pwallx][pwally] = oldtile | 0xc0;
		}
	}

	pwallpos = (pwallstate/2)&63;

}
