#include "wl_def.h"

/*
=============================================================================

						 LOCAL CONSTANTS

=============================================================================
*/

#define PROJECTILESIZE	0xc000l

#define BJRUNSPEED	2048
#define BJJUMPSPEED	680

/*
=============================================================================

						 LOCAL VARIABLES

=============================================================================
*/

static const int starthitpoints[4][NUMENEMIES] =
	 //
	 // BABY MODE
	 //
	 {
	 {25,	// guards
	  50,	// officer
	  100,	// SS
	  1,	// dogs
	  850,	// Hans
	  850,	// Schabbs
	  200,	// fake hitler
	  800,	// mecha hitler
	  45,	// mutants
	  25,	// ghosts
	  25,	// ghosts
	  25,	// ghosts
	  25,	// ghosts

	  850,	// Gretel
	  850,	// Gift
	  850,	// Fat
	  5,	// en_spectre,
	  1450,	// en_angel,
	  850,	// en_trans,
	  1050,	// en_uber,
	  950,	// en_will,
	  1250	// en_death
	  },
	 //
	 // DON'T HURT ME MODE
	 //
	 {25,	// guards
	  50,	// officer
	  100,	// SS
	  1,	// dogs
	  950,	// Hans
	  950,	// Schabbs
	  300,	// fake hitler
	  950,	// mecha hitler
	  55,	// mutants
	  25,	// ghosts
	  25,	// ghosts
	  25,	// ghosts
	  25,	// ghosts

	  950,	// Gretel
	  950,	// Gift
	  950,	// Fat
	  10,	// en_spectre,
	  1550,	// en_angel,
	  950,	// en_trans,
	  1150,	// en_uber,
	  1050,	// en_will,
	  1350	// en_death
	  },
	 //
	 // BRING 'EM ON MODE
	 //
	 {25,	// guards
	  50,	// officer
	  100,	// SS
	  1,	// dogs

	  1050,	// Hans
	  1550,	// Schabbs
	  400,	// fake hitler
	  1050,	// mecha hitler

	  55,	// mutants
	  25,	// ghosts
	  25,	// ghosts
	  25,	// ghosts
	  25,	// ghosts

	  1050,	// Gretel
	  1050,	// Gift
	  1050,	// Fat
	  15,	// en_spectre,
	  1650,	// en_angel,
	  1050,	// en_trans,
	  1250,	// en_uber,
	  1150,	// en_will,
	  1450	// en_death
	  },
	 //
	 // DEATH INCARNATE MODE
	 //
	 {25,	// guards
	  50,	// officer
	  100,	// SS
	  1,	// dogs

	  1200,	// Hans
	  2400,	// Schabbs
	  500,	// fake hitler
	  1200,	// mecha hitler

	  65,	// mutants
	  25,	// ghosts
	  25,	// ghosts
	  25,	// ghosts
	  25,	// ghosts

	  1200,	// Gretel
	  1200,	// Gift
	  1200,	// Fat
	  25,	// en_spectre,
	  2000,	// en_angel,
	  1200,	// en_trans,
	  1400,	// en_uber,
	  1300,	// en_will,
	  1600	// en_death
}};

/*
=================
=
= A_Smoke
=
=================
*/

void A_Smoke(objtype *ob)
{
	GetNewActor();
	if (s0 == true || s1 == true || s2 == true || s3 == true){
		if (ob->obclass == hrocketobj)
			new->state = s_hsmoke1;
		else
			new->state = s_smoke1SOD;
	} else {
		new->state = s_smoke1WL6;
	}
		
	new->ticcount = 6;

	new->tilex = ob->tilex;
	new->tiley = ob->tiley;
	new->x = ob->x;
	new->y = ob->y;
	new->obclass = inertobj;
	new->active = ac_yes;

	new->flags = FL_NEVERMARK;
}


/*
===================
=
= ProjectileTryMove
=
= returns true if move ok
===================
*/

#define PROJSIZE	0x2000

boolean ProjectileTryMove(objtype *ob)
{
	int xl,yl,xh,yh,x,y;
 
	xl = (ob->x-PROJSIZE) >>TILESHIFT;
	yl = (ob->y-PROJSIZE) >>TILESHIFT;

	xh = (ob->x+PROJSIZE) >>TILESHIFT;
	yh = (ob->y+PROJSIZE) >>TILESHIFT;

/* check for solid walls */
	for (y=yl;y<=yh;y++) {
		for (x=xl;x<=xh;x++) {
			if (actorat[x][y] && !(actorat[x][y] & 0x8000))
				return false;
		}
	}

	return true;
}



/*
=================
=
= T_Projectile
=
=================
*/

void T_Projectile(objtype *ob)
{
	long	deltax,deltay;
	int		damage;
	long	speed;

	speed = (long)ob->speed*tics;

	deltax = FixedByFrac(speed,costable[ob->angle]);
	deltay = -FixedByFrac(speed,sintable[ob->angle]);

	if (deltax>0x10000l)
		deltax = 0x10000l;
	if (deltay>0x10000l)
		deltay = 0x10000l;

	ob->x += deltax;
	ob->y += deltay;

	deltax = labs(ob->x - player->x);
	deltay = labs(ob->y - player->y);

	if (!ProjectileTryMove(ob))
	{
		if (ob->obclass == rocketobj)
		{
			if (w0 == true || w1 == true){
				PlaySoundLocActor(MISSILEHITSNDWL6,ob);
				ob->state = s_boom1WL6;
			} else {
				PlaySoundLocActor(MISSILEHITSNDSOD,ob);
				ob->state = s_boom1SOD;
			}
		}
		else if (ob->obclass == hrocketobj)
		{
			if (s0 == true || s1 == true || s2 == true || s3 == true){
				PlaySoundLocActor(MISSILEHITSNDSOD,ob);
				ob->state = s_hboom1;
			}
		}
		else
			if (w0 == true || w1 == true){
				ob->state = s_noneWL6;		// mark for removal
			} else {
				ob->state = s_noneSOD;		// mark for removal
			}

		return;
	}

	if (deltax < PROJECTILESIZE && deltay < PROJECTILESIZE)
	{	// hit the player
		switch (ob->obclass)
		{
		case needleobj:
			damage = (US_RndT() >>3) + 20;
			break;
		case rocketobj:
		case hrocketobj:
		case sparkobj:
			damage = (US_RndT() >>3) + 30;
			break;
		case fireobj:
			damage = (US_RndT() >>3);
			break;
		default:
			return;
		}

		TakeDamage(damage, ob);
		if (w0 == true || w1 == true){
			ob->state = s_noneWL6;		// mark for removal
		} else {
			ob->state = s_noneSOD;		// mark for removal
		}
		return;
	}

	ob->tilex = ob->x >> TILESHIFT;
	ob->tiley = ob->y >> TILESHIFT;

}

/*
=============================================================================

							GUARD

=============================================================================
*/


/*
===============
=
= SpawnStand
=
===============
*/

void SpawnStand (enemy_t which, int tilex, int tiley, int dir)
{
	word *map, tile = 0;

	switch (which)
	{
	case en_guard:
		if (w0 == true || w1 == true){
			SpawnNewObj (tilex,tiley,s_grdstandWL6);
		} else {
			SpawnNewObj (tilex,tiley,s_grdstandSOD);
		}
		new->speed = SPDPATROL;
		if (!loadedgame)
		  gamestate.killtotal++;
		break;

	case en_officer:
		if (w0 == true || w1 == true){
			SpawnNewObj (tilex,tiley,s_ofcstandWL6);
		} else {
			SpawnNewObj (tilex,tiley,s_ofcstandSOD);
		}
		new->speed = SPDPATROL;
		if (!loadedgame)
		  gamestate.killtotal++;
		break;

	case en_mutant:
		if (w0 == true || w1 == true){
			SpawnNewObj (tilex,tiley,s_mutstandWL6);
		} else {
			SpawnNewObj (tilex,tiley,s_mutstandSOD);
		}
		new->speed = SPDPATROL;
		if (!loadedgame)
		  gamestate.killtotal++;
		break;

	case en_ss:
		if (w0 == true || w1 == true){
			SpawnNewObj (tilex,tiley,s_ssstandWL6);
		} else {
			SpawnNewObj (tilex,tiley,s_ssstandSOD);
		}
		new->speed = SPDPATROL;
		if (!loadedgame)
		  gamestate.killtotal++;
		break;
	default:
		break;
	}


	map = mapsegs[0]+farmapylookup[tiley]+tilex;
	if (*map == AMBUSHTILE)
	{
		tilemap[tilex][tiley] = 0;

		if (*(map+1) >= AREATILE)
			tile = *(map+1);
		if (*(map-mapwidth) >= AREATILE)
			tile = *(map-mapwidth);
		if (*(map+mapwidth) >= AREATILE)
			tile = *(map+mapwidth);
		if ( *(map-1) >= AREATILE)
			tile = *(map-1);

		*map = tile;
		new->areanumber = tile-AREATILE;

		new->flags |= FL_AMBUSH;
	}

	new->obclass = guardobj+which;
	new->hitpoints = starthitpoints[gamestate.difficulty][which];
	new->dir = dir*2;
	new->flags |= FL_SHOOTABLE;
}



/*
===============
=
= SpawnDeadGuard
=
===============
*/

void SpawnDeadGuard (int tilex, int tiley)
{
	if (w0 == true || w1 == true){
		SpawnNewObj (tilex,tiley,s_grddie4WL6);
	} else {
		SpawnNewObj (tilex,tiley,s_grddie4SOD);
	}
	new->obclass = inertobj;
}



/*
===============
=
= SpawnPatrol
=
===============
*/

void SpawnPatrol (enemy_t which, int tilex, int tiley, int dir)
{
	switch (which)
	{
	case en_guard:
		if (w0 == true || w1 == true){
			SpawnNewObj (tilex,tiley,s_grdpath1WL6);
		} else {
			SpawnNewObj (tilex,tiley,s_grdpath1SOD);
		}
		new->speed = SPDPATROL;
		if (!loadedgame)
		  gamestate.killtotal++;
		break;

	case en_officer:
		if (w0 == true || w1 == true){
			SpawnNewObj (tilex,tiley,s_ofcpath1WL6);
		} else {
			SpawnNewObj (tilex,tiley,s_ofcpath1SOD);
		}
		new->speed = SPDPATROL;
		if (!loadedgame)
		  gamestate.killtotal++;
		break;

	case en_ss:
		if (w0 == true || w1 == true){
			SpawnNewObj (tilex,tiley,s_sspath1WL6);
		} else {
			SpawnNewObj (tilex,tiley,s_sspath1SOD);
		}
		new->speed = SPDPATROL;
		if (!loadedgame)
		  gamestate.killtotal++;
		break;

	case en_mutant:
		if (w0 == true || w1 == true){
			SpawnNewObj (tilex,tiley,s_mutpath1WL6);
		} else {
			SpawnNewObj (tilex,tiley,s_mutpath1SOD);
		}
		new->speed = SPDPATROL;
		if (!loadedgame)
		  gamestate.killtotal++;
		break;

	case en_dog:
		if (w0 == true || w1 == true){
			SpawnNewObj (tilex,tiley,s_dogpath1WL6);
		} else {
			SpawnNewObj (tilex,tiley,s_dogpath1SOD);
		}
		new->speed = SPDDOG;
		if (!loadedgame)
		  gamestate.killtotal++;
		break;
	default:
		break;
	}

	new->obclass = guardobj+which;
	new->dir = dir*2;
	new->hitpoints = starthitpoints[gamestate.difficulty][which];
	new->distance = TILEGLOBAL;
	new->flags |= FL_SHOOTABLE;
	new->active = ac_yes;

	actorat[new->tilex][new->tiley] = 0;	// don't use original spot

	switch (dir)
	{
	case 0:
		new->tilex++;
		break;
	case 1:
		new->tiley--;
		break;
	case 2:
		new->tilex--;
		break;
	case 3:
		new->tiley++;
		break;
	}

	actorat[new->tilex][new->tiley] = new->id | 0x8000;
}



/*
==================
=
= A_DeathScream
=
==================
*/

void A_DeathScream (objtype *ob)
{
	if (w0 == true || w1 == true){
		if (mapon==9 && !US_RndT())
		{
		 switch(ob->obclass)
		 {
		  case mutantobj:
		  case guardobj:
		  case officerobj:
		  case ssobj:
		  case dogobj:
			PlaySoundLocActor(DEATHSCREAM6SNDWL6,ob);
			return;
		  default:
		  	break;
		 }
		}
	} else {
		if ((mapon==18 || mapon==19) && !US_RndT())
		{
		 switch(ob->obclass)
		 {
		  case mutantobj:
		  case guardobj:
		  case officerobj:
		  case ssobj:
		  case dogobj:
			PlaySoundLocActor(DEATHSCREAM6SNDSOD,ob);
			return;
		  default:
		  	break;
		 }
		}
	}

	switch (ob->obclass)
	{
	case mutantobj:
		if (w0 == true || w1 == true){
			PlaySoundLocActor(AHHHGSNDWL6, ob);
		} else {
			PlaySoundLocActor(AHHHGSNDSOD, ob);
		}
		break;

	case guardobj:
		{
			if (w0 == true || w1 == true){
				 int sounds[8]={ DEATHSCREAM1SNDWL6, DEATHSCREAM2SNDWL6,
						 DEATHSCREAM3SNDWL6, DEATHSCREAM4SNDWL6,
						 DEATHSCREAM5SNDWL6, DEATHSCREAM7SNDWL6,
						 DEATHSCREAM8SNDWL6, DEATHSCREAM9SNDWL6
				 };
				 PlaySoundLocActor(sounds[US_RndT()%8], ob);
			} else {
				 int sounds[3]={ DEATHSCREAM1SNDSOD, 
				   		 DEATHSCREAM2SNDSOD,
						 DEATHSCREAM3SNDSOD
				 };
				 PlaySoundLocActor(sounds[US_RndT()%3], ob);
			}		
		}
		break;
	case officerobj:
		if (w0 == true || w1 == true){
			PlaySoundLocActor(NEINSOVASSNDWL6,ob);
		} else {
			PlaySoundLocActor(NEINSOVASSNDSOD,ob);
		}
		break;
	case ssobj:
		if (w0 == true || w1 == true){
			PlaySoundLocActor(LEBENSNDWL6,ob);
		} else {
			PlaySoundLocActor(LEBENSNDSOD,ob);
		}
		break;
	case dogobj:
		if (w0 == true || w1 == true){
			PlaySoundLocActor(DOGDEATHSNDWL6,ob);
		} else {
			PlaySoundLocActor(DOGDEATHSNDSOD,ob);
		}
		break;
	case bossobj:
		SD_PlaySoundWL6(MUTTISND);
		break;
	case schabbobj:
		SD_PlaySoundWL6(MEINGOTTSND);
		break;
	case fakeobj:
		SD_PlaySoundWL6(HITLERHASND);
		break;
	case mechahitlerobj:
		SD_PlaySoundWL6(SCHEISTSND);
		break;
	case realhitlerobj:
		SD_PlaySoundWL6(EVASND);
		break;
	case gretelobj:
		SD_PlaySoundWL6(MEINSND);
		break;
	case giftobj:
		SD_PlaySoundWL6(DONNERSND);
		break;
	case fatobj:
		SD_PlaySoundWL6(ROSESND);
		break;
	case spectreobj:
		SD_PlaySoundSOD(GHOSTFADESND);
		break;
	case angelobj:
		SD_PlaySoundSOD(ANGELDEATHSND);
		break;
	case transobj:
		SD_PlaySoundSOD(TRANSDEATHSND);
		break;
	case uberobj:
		SD_PlaySoundSOD(UBERDEATHSND);
		break;
	case willobj:
		SD_PlaySoundSOD(WILHELMDEATHSND);
		break;
	case deathobj:
		SD_PlaySoundSOD(KNIGHTDEATHSND);
		break;
	default:
		break;
	}
}

/*
============================================================================

								PATH

============================================================================
*/


/*
===============
=
= SelectPathDir
=
===============
*/

void SelectPathDir(objtype *ob)
{
	unsigned spot;

	spot = *(mapsegs[1]+farmapylookup[ob->tiley]+ob->tilex)-ICONARROWS;

	if (spot<8)
	{
	// new direction
		ob->dir = spot;
	}

	ob->distance = TILEGLOBAL;

	if (!TryWalk (ob))
		ob->dir = nodir;
}


/*
===============
=
= T_Path
=
===============
*/

void T_Path (objtype *ob)
{
	long 	move;

	if (SightPlayer (ob))
		return;

	if (ob->dir == nodir)
	{
		SelectPathDir (ob);
		if (ob->dir == nodir)
			return;					// all movement is blocked
	}


	move = ob->speed*tics;

	while (move)
	{
		if (ob->distance < 0)
		{
		//
		// waiting for a door to open
		//
			OpenDoor (-ob->distance-1);
			if (doorobjlist[-ob->distance-1].action != dr_open)
				return;
			ob->distance = TILEGLOBAL;	// go ahead, the door is now opoen
		}

		if (move < ob->distance)
		{
			MoveObj (ob,move);
			break;
		}

		if (ob->tilex>MAPSIZE || ob->tiley>MAPSIZE)
		{
			sprintf(str,"T_Path hit a wall at %u,%u, dir %u"
			,ob->tilex,ob->tiley,ob->dir);
			Quit(str);
		}



		ob->x = ((long)ob->tilex<<TILESHIFT)+TILEGLOBAL/2;
		ob->y = ((long)ob->tiley<<TILESHIFT)+TILEGLOBAL/2;
		move -= ob->distance;

		SelectPathDir (ob);

		if (ob->dir == nodir)
			return;			/* all movement is blocked */
	}
}


/*
=============================================================================

								FIGHT

=============================================================================
*/


/*
===============
=
= T_Shoot
=
= Try to damage the player, based on skill level and player's speed
=
===============
*/

void T_Shoot (objtype *ob)
{
	int	dx,dy,dist;
	int	hitchance,damage;

	hitchance = 128;

	if (!areabyplayer[ob->areanumber])
		return;

	if (!CheckLine (ob))			// player is behind a wall
	  return;

	dx = abs(ob->tilex - player->tilex);
	dy = abs(ob->tiley - player->tiley);
	dist = dx>dy ? dx:dy;

	if (ob->obclass == ssobj || ob->obclass == bossobj)
		dist = dist*2/3;					// ss are better shots

	if (thrustspeed >= RUNSPEED)
	{
		if (ob->flags&FL_VISABLE)
			hitchance = 160-dist*16;		// player can see to dodge
		else
			hitchance = 160-dist*8;
	}
	else
	{
		if (ob->flags&FL_VISABLE)
			hitchance = 256-dist*16;		// player can see to dodge
		else
			hitchance = 256-dist*8;
	}

// see if the shot was a hit

	if (US_RndT()<hitchance)
	{
		if (dist<2)
			damage = US_RndT()>>2;
		else if (dist<4)
			damage = US_RndT()>>3;
		else
			damage = US_RndT()>>4;

		TakeDamage (damage,ob);
	}

	switch(ob->obclass)
	{
	 case ssobj:
	   if (w0 == true || w1 == true){
		   PlaySoundLocActor(SSFIRESNDWL6,ob);
	   } else {
		   PlaySoundLocActor(SSFIRESNDSOD,ob);
	   }
	   break;
	 case giftobj:
	 case fatobj:
	   PlaySoundLocActor(MISSILEFIRESNDWL6,ob);
	   break;
	 case mechahitlerobj:
	 case realhitlerobj:
	 case bossobj:
	   PlaySoundLocActor(BOSSFIRESNDWL6,ob);
	   break;
	 case schabbobj:
	   PlaySoundLocActor(SCHABBSTHROWSND,ob);
	   break;
	 case fakeobj:
	   PlaySoundLocActor(FLAMETHROWERSND,ob);
	   break;
	 default:
	   if (w0 == true || w1 == true){
		   PlaySoundLocActor(NAZIFIRESNDWL6,ob);
	   } else {
		   PlaySoundLocActor(NAZIFIRESNDSOD,ob);
	   }
	}

}


/*
===============
=
= T_Bite
=
===============
*/

void T_Bite (objtype *ob)
{
	long	dx,dy;

	if (w0 == true || w1 == true){
		PlaySoundLocActor(DOGATTACKSNDWL6,ob);	// JAB
	} else {
		PlaySoundLocActor(DOGATTACKSNDSOD,ob);	// JAB
	}

	dx = player->x - ob->x;
	if (dx<0)
		dx = -dx;
	dx -= TILEGLOBAL;
	if (dx <= MINACTORDIST)
	{
		dy = player->y - ob->y;
		if (dy<0)
			dy = -dy;
		dy -= TILEGLOBAL;
		if (dy <= MINACTORDIST)
		{
		   if (US_RndT()<180)
		   {
			   TakeDamage (US_RndT()>>4,ob);
			   return;
		   }
		}
	}

	return;
}

/*
=============================================================================

						 SPEAR ACTORS

=============================================================================
*/


/*
===============
=
= SpawnTrans
=
===============
*/

void SpawnTrans (int tilex, int tiley)
{
	if (SoundBlasterPresent && DigiMode != sds_Off)
		gamestatesSOD[s_transdie01].tictime = 105;

	SpawnNewObj(tilex,tiley,s_transstand);
	new->obclass = transobj;
	new->hitpoints = starthitpoints[gamestate.difficulty][en_trans];
	new->flags |= FL_SHOOTABLE|FL_AMBUSH;
	if (!loadedgame)
		gamestate.killtotal++;
}

//
// uber
//

/*
===============
=
= SpawnUber
=
===============
*/

void SpawnUber (int tilex, int tiley)
{
	if (SoundBlasterPresent && DigiMode != sds_Off)
		gamestatesSOD[s_uberdie01].tictime = 70;

	SpawnNewObj (tilex,tiley,s_uberstand);
	new->obclass = uberobj;
	new->hitpoints = starthitpoints[gamestate.difficulty][en_uber];
	new->flags |= FL_SHOOTABLE|FL_AMBUSH;
	if (!loadedgame)
		gamestate.killtotal++;
}

/*
===============
=
= T_UShoot
=
===============
*/

void T_UShoot(objtype *ob)
{
	int dx, dy, dist;

	T_Shoot(ob);

	dx = abs(ob->tilex - player->tilex);
	dy = abs(ob->tiley - player->tiley);
	dist = dx>dy ? dx : dy;
	if (dist <= 1)
		TakeDamage (10,ob);
}

/*
===============
=
= SpawnWill
=
===============
*/

void SpawnWill(int tilex, int tiley)
{
	if (SoundBlasterPresent && DigiMode != sds_Off)
		gamestatesSOD[s_willdie2].tictime = 70;

	SpawnNewObj (tilex,tiley,s_willstand);
	new->obclass = willobj;
	new->hitpoints = starthitpoints[gamestate.difficulty][en_will];
	new->flags |= FL_SHOOTABLE|FL_AMBUSH;
	if (!loadedgame)
		gamestate.killtotal++;
}

/*
================
=
= T_Will
=
================
*/

void T_Will(objtype *ob)
{
	long move;
	int	dx,dy,dist;
	boolean	dodge;

	dodge = false;
	dx = abs(ob->tilex - player->tilex);
	dy = abs(ob->tiley - player->tiley);
	dist = dx>dy ? dx : dy;

	if (CheckLine(ob))						// got a shot at player?
	{
		if ( US_RndT() < (tics<<3) )
		{
		//
		// go into attack frame
		//
			if (ob->obclass == willobj)
				NewState (ob,s_willshoot1);
			else if (ob->obclass == angelobj)
				NewState (ob,s_angelshoot1);
			else
				NewState (ob,s_deathshoot1);
			return;
		}
		dodge = true;
	}

	if (ob->dir == nodir)
	{
		if (dodge)
			SelectDodgeDir (ob);
		else
			SelectChaseDir (ob);
		if (ob->dir == nodir)
			return;							// object is blocked in
	}

	move = ob->speed*tics;

	while (move)
	{
		if (ob->distance < 0)
		{
		//
		// waiting for a door to open
		//
			OpenDoor (-ob->distance-1);
			if (doorobjlist[-ob->distance-1].action != dr_open)
				return;
			ob->distance = TILEGLOBAL;	// go ahead, the door is now opoen
		}

		if (move < ob->distance)
		{
			MoveObj (ob,move);
			break;
		}

		//
		// reached goal tile, so select another one
		//

		//
		// fix position to account for round off during moving
		//
		ob->x = ((long)ob->tilex<<TILESHIFT)+TILEGLOBAL/2;
		ob->y = ((long)ob->tiley<<TILESHIFT)+TILEGLOBAL/2;

		move -= ob->distance;

		if (dist <4)
			SelectRunDir (ob);
		else if (dodge)
			SelectDodgeDir (ob);
		else
			SelectChaseDir (ob);

		if (ob->dir == nodir)
			return;							// object is blocked in
	}

}



/*
===============
=
= SpawnDeath
=
===============
*/

void SpawnDeath(int tilex, int tiley)
{
	if (SoundBlasterPresent && DigiMode != sds_Off)
		gamestatesSOD[s_deathdie2].tictime = 105;

	SpawnNewObj (tilex,tiley,s_deathstand);
	new->obclass = deathobj;
	new->hitpoints = starthitpoints[gamestate.difficulty][en_death];
	new->flags |= FL_SHOOTABLE|FL_AMBUSH;
	if (!loadedgame)
	  gamestate.killtotal++;
}

/*
===============
=
= T_Launch
=
===============
*/

void T_Launch(objtype *ob)
{
	long deltax, deltay;
	float angle;
	int iangle;

	deltax = player->x - ob->x;
	deltay = ob->y - player->y;
	angle = atan2 (deltay,deltax);
	if (angle<0)
		angle = PI*2+angle;
	iangle = angle/(PI*2)*ANGLES;
	if (ob->obclass == deathobj)
	{
		T_Shoot (ob);
		if (ob->state == s_deathshoot2)
		{
			iangle-=4;
			if (iangle<0)
				iangle+=ANGLES;
		}
		else
		{
			iangle+=4;
			if (iangle>=ANGLES)
				iangle-=ANGLES;
		}
	}

	GetNewActor();
	new->state = s_rocketSOD;
	new->ticcount = 1;

	new->tilex = ob->tilex;
	new->tiley = ob->tiley;
	new->x = ob->x;
	new->y = ob->y;
	new->obclass = rocketobj;
	switch(ob->obclass)
	{
	case deathobj:
		new->state = s_hrocket;
		new->obclass = hrocketobj;
		PlaySoundLocActor(KNIGHTMISSILESNDSOD,new);
		break;
	case angelobj:
		new->state = s_spark1;
		new->obclass = sparkobj;
		PlaySoundLocActor(ANGELFIRESNDSOD,new);
		break;
	default:
		PlaySoundLocActor(MISSILEFIRESNDSOD,new);
	}

	new->dir = nodir;
	new->angle = iangle;
	new->speed = 0x2000l;
	new->flags = FL_NONMARK;
	new->active = ac_yes;
}

void A_SlurpieSOD(objtype *ob)
{
	SD_PlaySoundSOD(SLURPIESNDSOD);
}

void A_Breathing(objtype *ob)
{
	SD_PlaySoundSOD(ANGELTIREDSNDSOD);
}

/*
===============
=
= SpawnAngel
=
===============
*/

void SpawnAngel(int tilex, int tiley)
{
	if (SoundBlasterPresent && DigiMode != sds_Off)
		gamestatesSOD[s_angeldie11].tictime = 105;

	SpawnNewObj (tilex,tiley,s_angelstand);
	new->obclass = angelobj;
	new->hitpoints = starthitpoints[gamestate.difficulty][en_angel];
	new->flags |= FL_SHOOTABLE|FL_AMBUSH;
	if (!loadedgame)
		gamestate.killtotal++;
}

/*
=================
=
= A_Victory
=
=================
*/

void A_Victory(objtype *ob)
{
	playstate = ex_victorious;
}

/*
=================
=
= A_StartAttack
=
=================
*/

void A_StartAttack(objtype *ob)
{
	ob->temp1 = 0;
}

/*
=================
=
= A_Relaunch
=
=================
*/

void A_Relaunch(objtype *ob)
{
	if (++ob->temp1 == 3)
	{
		NewState(ob, s_angeltired);
		return;
	}

	if (US_RndT()&1)
	{
		NewState(ob, s_angelchase1);
		return;
	}
}

/*
===============
=
= SpawnSpectre
=
===============
*/

void SpawnSpectre(int tilex, int tiley)
{
	SpawnNewObj (tilex,tiley,s_spectrewait1);
	new->obclass = spectreobj;
	new->hitpoints = starthitpoints[gamestate.difficulty][en_spectre];
	new->flags |= FL_SHOOTABLE|FL_AMBUSH; // |FL_NEVERMARK|FL_NONMARK;
	if (!loadedgame)
		gamestate.killtotal++;
}

/*
===============
=
= A_Dormant
=
===============
*/

void A_Dormant(objtype *ob)
{
	long deltax, deltay;
	int xl,xh,yl,yh;
	int x,y;
	unsigned tile;

	deltax = ob->x - player->x;
	if (deltax < -MINACTORDIST || deltax > MINACTORDIST)
		goto moveok;
	deltay = ob->y - player->y;
	if (deltay < -MINACTORDIST || deltay > MINACTORDIST)
		goto moveok;

	return;
moveok:

	xl = (ob->x-MINDIST) >> TILESHIFT;
	xh = (ob->x+MINDIST) >> TILESHIFT;
	yl = (ob->y-MINDIST) >> TILESHIFT;
	yh = (ob->y+MINDIST) >> TILESHIFT;

	for (y = yl; y <= yh; y++)
		for (x = xl; x <= xh; x++)
		{
			tile = actorat[x][y];
			if (!tile)
				continue;
			if (tile < 256)
				return;
			if (objlist[tile & ~0x8000].flags & FL_SHOOTABLE)
				return;
		}

	ob->flags |= FL_AMBUSH | FL_SHOOTABLE;
	ob->flags &= ~FL_ATTACKMODE;
	ob->dir = nodir;
	NewState(ob, s_spectrewait1);
}

/*
=============================================================================

						 SCHABBS / GIFT / FAT

=============================================================================
*/


/*
===============
=
= SpawnBoss
=
===============
*/

void SpawnBoss (int tilex, int tiley)
{
	SpawnNewObj (tilex,tiley,s_bossstand);
	new->speed = SPDPATROL;

	new->obclass = bossobj;
	new->hitpoints = starthitpoints[gamestate.difficulty][en_boss];
	new->dir = south;
	new->flags |= FL_SHOOTABLE|FL_AMBUSH;
	if (!loadedgame)
	  gamestate.killtotal++;
}

/*
===============
=
= SpawnGretel
=
===============
*/

void SpawnGretel (int tilex, int tiley)
{
	SpawnNewObj (tilex,tiley,s_gretelstand);
	new->speed = SPDPATROL;

	new->obclass = gretelobj;
	new->hitpoints = starthitpoints[gamestate.difficulty][en_gretel];
	new->dir = north;
	new->flags |= FL_SHOOTABLE|FL_AMBUSH;
	if (!loadedgame)
	  gamestate.killtotal++;
}

/*
===============
=
= SpawnGhosts
=
===============
*/

void SpawnGhosts (int which, int tilex, int tiley)
{
	switch(which)
	{
	 case en_blinky:
	   SpawnNewObj (tilex,tiley,s_blinkychase1);
	   break;
	 case en_clyde:
	   SpawnNewObj (tilex,tiley,s_clydechase1);
	   break;
	 case en_pinky:
	   SpawnNewObj (tilex,tiley,s_pinkychase1);
	   break;
	 case en_inky:
	   SpawnNewObj (tilex,tiley,s_inkychase1);
	   break;
	}

	new->obclass = ghostobj;
	new->speed = SPDDOG;

	new->dir = east;
	new->flags |= FL_AMBUSH;
	if (!loadedgame)
	  gamestate.killtotal++;
}

/*
===============
=
= SpawnSchabbs
=
===============
*/

void SpawnSchabbs(int tilex, int tiley)
{ 
	if (DigiMode != sds_Off)
		gamestatesWL6[s_schabbdie2].tictime = 140;
	else
		gamestatesWL6[s_schabbdie2].tictime = 5;

	SpawnNewObj(tilex, tiley, s_schabbstand);
	new->speed = SPDPATROL;

	new->obclass = schabbobj;
	new->hitpoints = starthitpoints[gamestate.difficulty][en_schabbs];
	new->dir = south;
	new->flags |= FL_SHOOTABLE|FL_AMBUSH;
	if (!loadedgame)
	  gamestate.killtotal++;
}


/*
===============
=
= SpawnGift
=
===============
*/

void SpawnGift (int tilex, int tiley)
{
	if (DigiMode != sds_Off)
		gamestatesWL6[s_giftdie2].tictime = 140;
	else
		gamestatesWL6[s_giftdie2].tictime = 5;

	SpawnNewObj (tilex,tiley,s_giftstand);
	new->speed = SPDPATROL;

	new->obclass = giftobj;
	new->hitpoints = starthitpoints[gamestate.difficulty][en_gift];
	new->dir = north;
	new->flags |= FL_SHOOTABLE|FL_AMBUSH;
	if (!loadedgame)
	  gamestate.killtotal++;
}


/*
===============
=
= SpawnFat
=
===============
*/

void SpawnFat (int tilex, int tiley)
{
	if (DigiMode != sds_Off)
		gamestatesWL6[s_fatdie2].tictime = 140;
	else
		gamestatesWL6[s_fatdie2].tictime = 5;

	SpawnNewObj (tilex,tiley,s_fatstand);
	new->speed = SPDPATROL;

	new->obclass = fatobj;
	new->hitpoints = starthitpoints[gamestate.difficulty][en_fat];
	new->dir = south;
	new->flags |= FL_SHOOTABLE|FL_AMBUSH;
	if (!loadedgame)
	  gamestate.killtotal++;
}


/*
=================
=
= T_SchabbThrow
=
=================
*/

void T_SchabbThrow (objtype *ob)
{
	long	deltax,deltay;
	float	angle;
	int		iangle;

	deltax = player->x - ob->x;
	deltay = ob->y - player->y;
	angle = atan2 (deltay,deltax);
	if (angle<0)
		angle = PI*2+angle;
	iangle = angle/(PI*2)*ANGLES;

	GetNewActor ();
	new->state = s_needle1;
	new->ticcount = 1;

	new->tilex = ob->tilex;
	new->tiley = ob->tiley;
	new->x = ob->x;
	new->y = ob->y;
	new->obclass = needleobj;
	new->dir = nodir;
	new->angle = iangle;
	new->speed = 0x2000l;

	new->flags = FL_NONMARK;
	new->active = ac_yes;

	PlaySoundLocActor(SCHABBSTHROWSND, new);
}

/*
=================
=
= T_GiftThrow
=
=================
*/

void T_GiftThrow(objtype *ob)
{
	long deltax,deltay;
	float angle;
	int iangle;

	deltax = player->x - ob->x;
	deltay = ob->y - player->y;
	angle = atan2 (deltay,deltax);
	if (angle<0)
		angle = PI*2+angle;
	iangle = angle/(PI*2)*ANGLES;

	GetNewActor ();
	new->state = s_rocketWL6;
	new->ticcount = 1;

	new->tilex = ob->tilex;
	new->tiley = ob->tiley;
	new->x = ob->x;
	new->y = ob->y;
	new->obclass = rocketobj;
	new->dir = nodir;
	new->angle = iangle;
	new->speed = 0x2000l;
	new->flags = FL_NONMARK;
	new->active = ac_yes;

	PlaySoundLocActor(MISSILEFIRESNDWL6, new);
}



/*
=================
=
= T_Schabb
=
=================
*/

void T_Schabb (objtype *ob)
{
	long move;
	int	dx,dy,dist;
	boolean	dodge;

	dodge = false;
	dx = abs(ob->tilex - player->tilex);
	dy = abs(ob->tiley - player->tiley);
	dist = dx>dy ? dx : dy;

	if (CheckLine(ob))						// got a shot at player?
	{

		if ( US_RndT() < (tics<<3) )
		{
		//
		// go into attack frame
		//
			NewState (ob,s_schabbshoot1);
			return;
		}
		dodge = true;
	}

	if (ob->dir == nodir)
	{
		if (dodge)
			SelectDodgeDir (ob);
		else
			SelectChaseDir (ob);
		if (ob->dir == nodir)
			return;							// object is blocked in
	}

	move = ob->speed*tics;

	while (move)
	{
		if (ob->distance < 0)
		{
		//
		// waiting for a door to open
		//
			OpenDoor (-ob->distance-1);
			if (doorobjlist[-ob->distance-1].action != dr_open)
				return;
			ob->distance = TILEGLOBAL;	// go ahead, the door is now opoen
		}

		if (move < ob->distance)
		{
			MoveObj (ob,move);
			break;
		}

		//
		// reached goal tile, so select another one
		//

		//
		// fix position to account for round off during moving
		//
		ob->x = ((long)ob->tilex<<TILESHIFT)+TILEGLOBAL/2;
		ob->y = ((long)ob->tiley<<TILESHIFT)+TILEGLOBAL/2;

		move -= ob->distance;

		if (dist <4)
			SelectRunDir (ob);
		else if (dodge)
			SelectDodgeDir (ob);
		else
			SelectChaseDir (ob);

		if (ob->dir == nodir)
			return;							// object is blocked in
	}

}




/*
=================
=
= T_Gift
=
=================
*/

void T_Gift (objtype *ob)
{
	long move;
	int	dx,dy,dist;
	boolean	dodge;

	dodge = false;
	dx = abs(ob->tilex - player->tilex);
	dy = abs(ob->tiley - player->tiley);
	dist = dx>dy ? dx : dy;

	if (CheckLine(ob))						// got a shot at player?
	{

		if ( US_RndT() < (tics<<3) )
		{
		//
		// go into attack frame
		//
			NewState (ob,s_giftshoot1);
			return;
		}
		dodge = true;
	}

	if (ob->dir == nodir)
	{
		if (dodge)
			SelectDodgeDir (ob);
		else
			SelectChaseDir (ob);
		if (ob->dir == nodir)
			return;							// object is blocked in
	}

	move = ob->speed*tics;

	while (move)
	{
		if (ob->distance < 0)
		{
		//
		// waiting for a door to open
		//
			OpenDoor (-ob->distance-1);
			if (doorobjlist[-ob->distance-1].action != dr_open)
				return;
			ob->distance = TILEGLOBAL;	// go ahead, the door is now opoen
		}

		if (move < ob->distance)
		{
			MoveObj (ob,move);
			break;
		}

		//
		// reached goal tile, so select another one
		//

		//
		// fix position to account for round off during moving
		//
		ob->x = ((long)ob->tilex<<TILESHIFT)+TILEGLOBAL/2;
		ob->y = ((long)ob->tiley<<TILESHIFT)+TILEGLOBAL/2;

		move -= ob->distance;

		if (dist <4)
			SelectRunDir (ob);
		else if (dodge)
			SelectDodgeDir (ob);
		else
			SelectChaseDir (ob);

		if (ob->dir == nodir)
			return;							// object is blocked in
	}

}




/*
=================
=
= T_Fat
=
=================
*/

void T_Fat (objtype *ob)
{
	long move;
	int	dx,dy,dist;
	boolean	dodge;

	dodge = false;
	dx = abs(ob->tilex - player->tilex);
	dy = abs(ob->tiley - player->tiley);
	dist = dx>dy ? dx : dy;

	if (CheckLine(ob))						// got a shot at player?
	{

		if ( US_RndT() < (tics<<3) )
		{
		//
		// go into attack frame
		//
			NewState (ob,s_fatshoot1);
			return;
		}
		dodge = true;
	}

	if (ob->dir == nodir)
	{
		if (dodge)
			SelectDodgeDir (ob);
		else
			SelectChaseDir (ob);
		if (ob->dir == nodir)
			return;							// object is blocked in
	}

	move = ob->speed*tics;

	while (move)
	{
		if (ob->distance < 0)
		{
		//
		// waiting for a door to open
		//
			OpenDoor (-ob->distance-1);
			if (doorobjlist[-ob->distance-1].action != dr_open)
				return;
			ob->distance = TILEGLOBAL;	// go ahead, the door is now opoen
		}

		if (move < ob->distance)
		{
			MoveObj (ob,move);
			break;
		}

		//
		// reached goal tile, so select another one
		//

		//
		// fix position to account for round off during moving
		//
		ob->x = ((long)ob->tilex<<TILESHIFT)+TILEGLOBAL/2;
		ob->y = ((long)ob->tiley<<TILESHIFT)+TILEGLOBAL/2;

		move -= ob->distance;

		if (dist <4)
			SelectRunDir (ob);
		else if (dodge)
			SelectDodgeDir (ob);
		else
			SelectChaseDir (ob);

		if (ob->dir == nodir)
			return;							// object is blocked in
	}

}



/*
=============================================================================

							HITLERS

=============================================================================
*/


/*
===============
=
= SpawnFakeHitler
=
===============
*/

void SpawnFakeHitler(int tilex, int tiley)
{
	if (DigiMode != sds_Off)
		gamestatesWL6[s_hitlerdie2].tictime = 140;
	else
		gamestatesWL6[s_hitlerdie2].tictime = 5;

	SpawnNewObj(tilex, tiley, s_fakestand);
	new->speed = SPDPATROL;

	new->obclass = fakeobj;
	new->hitpoints = starthitpoints[gamestate.difficulty][en_fake];
	new->dir = north;
	new->flags |= FL_SHOOTABLE|FL_AMBUSH;
	if (!loadedgame)
	  gamestate.killtotal++;
}


/*
===============
=
= SpawnHitler
=
===============
*/

void SpawnHitler(int tilex, int tiley)
{
	if (DigiMode != sds_Off)
		gamestatesWL6[s_hitlerdie2].tictime = 140;
	else
		gamestatesWL6[s_hitlerdie2].tictime = 5;


	SpawnNewObj (tilex,tiley,s_mechastand);
	new->speed = SPDPATROL;

	new->obclass = mechahitlerobj;
	new->hitpoints = starthitpoints[gamestate.difficulty][en_hitler];
	new->dir = south;
	new->flags |= FL_SHOOTABLE|FL_AMBUSH;
	if (!loadedgame)
	  gamestate.killtotal++;
}


/*
===============
=
= A_HitlerMorph
=
===============
*/

void A_HitlerMorph (objtype *ob)
{
	word hitpoints[4]={500,700,800,900};

	SpawnNewObj (ob->tilex,ob->tiley,s_hitlerchase1);
	new->speed = SPDPATROL*5;

	new->x = ob->x;
	new->y = ob->y;

	new->distance = ob->distance;
	new->dir = ob->dir;
	new->flags = ob->flags | FL_SHOOTABLE;

	new->obclass = realhitlerobj;
	new->hitpoints = hitpoints[gamestate.difficulty];
}


////////////////////////////////////////////////////////
//
// A_MechaSound
// A_Slurpie
//
////////////////////////////////////////////////////////
void A_MechaSound(objtype *ob)
{
	if (areabyplayer[ob->areanumber])
		PlaySoundLocActor(MECHSTEPSNDWL6, ob);
}


void A_SlurpieWL6(objtype *ob)
{
	SD_PlaySoundWL6(SLURPIESNDWL6);
}

/*
=================
=
= T_FakeFire
=
=================
*/

void T_FakeFire (objtype *ob)
{
	long	deltax,deltay;
	float	angle;
	int		iangle;

	deltax = player->x - ob->x;
	deltay = ob->y - player->y;
	angle = atan2 (deltay,deltax);
	if (angle<0)
		angle = PI*2+angle;
	iangle = angle/(PI*2)*ANGLES;

	GetNewActor ();
	new->state = s_fire1;
	new->ticcount = 1;

	new->tilex = ob->tilex;
	new->tiley = ob->tiley;
	new->x = ob->x;
	new->y = ob->y;
	new->dir = nodir;
	new->angle = iangle;
	new->obclass = fireobj;
	new->speed = 0x1200l;
	new->flags = FL_NEVERMARK;
	new->active = ac_yes;

	PlaySoundLocActor(FLAMETHROWERSND, new);
}

/*
=================
=
= T_Fake
=
=================
*/

void T_Fake (objtype *ob)
{
	long move;

	if (CheckLine(ob))			// got a shot at player?
	{
		if ( US_RndT() < (tics<<1) )
		{
		//
		// go into attack frame
		//
			NewState (ob,s_fakeshoot1);
			return;
		}
	}

	if (ob->dir == nodir)
	{
		SelectDodgeDir (ob);
		if (ob->dir == nodir)
			return;							// object is blocked in
	}

	move = ob->speed*tics;

	while (move)
	{
		if (move < ob->distance)
		{
			MoveObj (ob,move);
			break;
		}

		//
		// reached goal tile, so select another one
		//

		//
		// fix position to account for round off during moving
		//
		ob->x = ((long)ob->tilex<<TILESHIFT)+TILEGLOBAL/2;
		ob->y = ((long)ob->tiley<<TILESHIFT)+TILEGLOBAL/2;

		move -= ob->distance;

		SelectDodgeDir (ob);

		if (ob->dir == nodir)
			return;							// object is blocked in
	}

}

/*
============================================================================

							STAND

============================================================================
*/


/*
===============
=
= T_Stand
=
===============
*/

void T_Stand(objtype *ob)
{
	SightPlayer (ob);
}


/*
============================================================================

								CHASE

============================================================================
*/

/*
=================
=
= T_Chase
=
=================
*/

void T_Chase (objtype *ob)
{
	long move;
	int	dx,dy,dist,chance;
	boolean	dodge;

	if (gamestate.victoryflag)
		return;

	dodge = false;
	if (CheckLine(ob))	// got a shot at player?
	{
		dx = abs(ob->tilex - player->tilex);
		dy = abs(ob->tiley - player->tiley);
		dist = dx>dy ? dx : dy;
		if (!dist || (dist==1 && ob->distance<0x4000) )
			chance = 300;
		else
			chance = (tics<<4)/dist;

		if ( US_RndT()<chance)
		{
		//
		// go into attack frame
		//
			switch (ob->obclass)
			{
			case guardobj:
				if (w0 == true || w1 == true){
					NewState (ob,s_grdshoot1WL6);
				} else {
					NewState (ob,s_grdshoot1SOD);
				}
				break;
			case officerobj:
				if (w0 == true || w1 == true){
					NewState (ob,s_ofcshoot1WL6);
				} else {
					NewState (ob,s_ofcshoot1SOD);
				}
				break;
			case mutantobj:
				if (w0 == true || w1 == true){
					NewState (ob,s_mutshoot1WL6);
				} else {
					NewState (ob,s_mutshoot1SOD);
				}
				break;
			case ssobj:
				if (w0 == true || w1 == true){
					NewState (ob,s_ssshoot1WL6);
				} else {
					NewState (ob,s_ssshoot1SOD);
				}
				break;
			case bossobj:
				NewState (ob,s_bossshoot1);
				break;
			case gretelobj:
				NewState (ob,s_gretelshoot1);
				break;
			case mechahitlerobj:
				NewState (ob,s_mechashoot1);
				break;
			case realhitlerobj:
				NewState (ob,s_hitlershoot1);
				break;
			case angelobj:
				NewState (ob,s_angelshoot1);
				break;
			case transobj:
				NewState (ob,s_transshoot1);
				break;
			case uberobj:
				NewState (ob,s_ubershoot1);
				break;
			case willobj:
				NewState (ob,s_willshoot1);
				break;
			case deathobj:
				NewState (ob,s_deathshoot1);
				break;
			default:
				break;
			}
			return;
		}
		dodge = true;
	}

	if (ob->dir == nodir)
	{
		if (dodge)
			SelectDodgeDir (ob);
		else
			SelectChaseDir (ob);
		if (ob->dir == nodir)
			return;							// object is blocked in
	}

	move = ob->speed*tics;

	while (move)
	{
		if (ob->distance < 0)
		{
		//
		// waiting for a door to open
		//
			OpenDoor (-ob->distance-1);
			if (doorobjlist[-ob->distance-1].action != dr_open)
				return;
			ob->distance = TILEGLOBAL;	// go ahead, the door is now opoen
		}

		if (move < ob->distance)
		{
			MoveObj (ob,move);
			break;
		}

		//
		// reached goal tile, so select another one
		//

		//
		// fix position to account for round off during moving
		//
		ob->x = ((long)ob->tilex<<TILESHIFT)+TILEGLOBAL/2;
		ob->y = ((long)ob->tiley<<TILESHIFT)+TILEGLOBAL/2;

		move -= ob->distance;

		if (dodge)
			SelectDodgeDir (ob);
		else
			SelectChaseDir (ob);

		if (ob->dir == nodir)
			return;							// object is blocked in
	}

}


/*
=================
=
= T_Ghosts
=
=================
*/

void T_Ghosts (objtype *ob)
{
	long move;


	if (ob->dir == nodir)
	{
		SelectChaseDir (ob);
		if (ob->dir == nodir)
			return;							// object is blocked in
	}

	move = ob->speed*tics;

	while (move)
	{
		if (move < ob->distance)
		{
			MoveObj (ob,move);
			break;
		}

		//
		// reached goal tile, so select another one
		//

		//
		// fix position to account for round off during moving
		//
		ob->x = ((long)ob->tilex<<TILESHIFT)+TILEGLOBAL/2;
		ob->y = ((long)ob->tiley<<TILESHIFT)+TILEGLOBAL/2;

		move -= ob->distance;

		SelectChaseDir (ob);

		if (ob->dir == nodir)
			return;							// object is blocked in
	}

}

/*
=================
=
= T_DogChase
=
=================
*/

void T_DogChase (objtype *ob)
{
	long 	move;
	long	dx,dy;


	if (ob->dir == nodir)
	{
		SelectDodgeDir (ob);
		if (ob->dir == nodir)
			return;							// object is blocked in
	}

	move = ob->speed*tics;

	while (move)
	{
	//
	// check for byte range
	//
		dx = player->x - ob->x;
		if (dx<0)
			dx = -dx;
		dx -= move;
		if (dx <= MINACTORDIST)
		{
			dy = player->y - ob->y;
			if (dy<0)
				dy = -dy;
			dy -= move;
			if (dy <= MINACTORDIST)
			{
				if (w0 == true || w1 == true){
					NewState (ob,s_dogjump1WL6);
				} else {
					NewState (ob,s_dogjump1SOD);
				}
				return;
			}
		}

		if (move < ob->distance)
		{
			MoveObj (ob,move);
			break;
		}

		//
		// reached goal tile, so select another one
		//

		//
		// fix position to account for round off during moving
		//
		ob->x = ((long)ob->tilex<<TILESHIFT)+TILEGLOBAL/2;
		ob->y = ((long)ob->tiley<<TILESHIFT)+TILEGLOBAL/2;

		move -= ob->distance;

		SelectDodgeDir (ob);

		if (ob->dir == nodir)
			return;							// object is blocked in
	}

}



/*
============================================================================

							BJ VICTORY

============================================================================
*/



/*
===============
=
= SpawnBJVictory
=
===============
*/

void SpawnBJVictory()
{
	SpawnNewObj(player->tilex,player->tiley+1, s_bjrun1);
	new->x = player->x;
	new->y = player->y;
	new->obclass = bjobj;
	new->dir = north;
	new->temp1 = 6;			// tiles to run forward
}



/*
===============
=
= T_BJRun
=
===============
*/

void T_BJRun(objtype *ob)
{
	long move;

	move = BJRUNSPEED*tics;

	while (move)
	{
		if (move < ob->distance)
		{
			MoveObj (ob,move);
			break;
		}

		ob->x = ((long)ob->tilex<<TILESHIFT)+TILEGLOBAL/2;
		ob->y = ((long)ob->tiley<<TILESHIFT)+TILEGLOBAL/2;
		move -= ob->distance;

		SelectPathDir (ob);

		if (!(--ob->temp1))
		{
			NewState(ob,s_bjjump1);
			return;
		}
	}
}


/*
===============
=
= T_BJJump
=
===============
*/

void T_BJJump(objtype *ob)
{
	long 	move;

	move = BJJUMPSPEED*tics;
	MoveObj(ob,move);
}


/*
===============
=
= T_BJYell
=
===============
*/

void T_BJYell(objtype *ob)
{
	PlaySoundLocActor(YEAHSND, ob);
}


/*
===============
=
= T_BJDone
=
===============
*/

void T_BJDone(objtype *ob)
{
	playstate = ex_victorious;				// exit castle tile
}



//===========================================================================


/*
===============
=
= CheckPosition
=
===============
*/

boolean	CheckPosition(objtype *ob)
{
	int x, y, xl, yl, xh, yh;

	xl = (ob->x-PLAYERSIZE) >>TILESHIFT;
	yl = (ob->y-PLAYERSIZE) >>TILESHIFT;

	xh = (ob->x+PLAYERSIZE) >>TILESHIFT;
	yh = (ob->y+PLAYERSIZE) >>TILESHIFT;

	//
	// check for solid walls
	//
	for (y=yl;y<=yh;y++)
		for (x=xl;x<=xh;x++)
		{
			if (actorat[x][y] && !(actorat[x][y] & 0x8000))
				return false;
		}

	return true;
}


/*
===============
=
= A_StartDeathCam
=
===============
*/

void A_StartDeathCam(objtype *ob)
{
	long	dx,dy;
	float	fangle;
	long    xmove,ymove;
	long	dist;

	FinishPaletteShifts();

	VW_WaitVBL(100);

	if (gamestate.victoryflag)
	{
		playstate = ex_victorious;				// exit castle tile
		return;
	}

	gamestate.victoryflag = true;
	
	FizzleFade(false, 70, 127);

	if (w0 == true){
		CacheLump(LEVELEND_LUMP_STARTWL1, LEVELEND_LUMP_ENDWL1);
	} else if (w1 == true){
		CacheLump(LEVELEND_LUMP_STARTWL6, LEVELEND_LUMP_ENDWL6);
	} else if (s0 == true){
		CacheLump(LEVELEND_LUMP_STARTSDM, LEVELEND_LUMP_ENDSDM);
	} else {
		CacheLump(LEVELEND_LUMP_STARTSOD, LEVELEND_LUMP_ENDSOD);
	}

	Write(0, 7, STR_SEEAGAIN);

	VW_UpdateScreen();

	IN_UserInput(300);

/* line angle up exactly */
	NewState (player,s_deathcam);

	player->x = gamestate.killx;
	player->y = gamestate.killy;

	dx = ob->x - player->x;
	dy = player->y - ob->y;

	fangle = atan2(dy, dx);			/* returns -pi to pi */
	if (fangle<0)
		fangle = PI*2+fangle;

	player->angle = fangle/(PI*2)*ANGLES;

/* try to position as close as possible without being in a wall */
	dist = 0x14000l;
	do
	{
		xmove = FixedByFrac(dist, costable[player->angle]);
		ymove = -FixedByFrac(dist, sintable[player->angle]);

		player->x = ob->x - xmove;
		player->y = ob->y - ymove;
		dist += 0x1000;

	} while (!CheckPosition(player));
	
	plux = player->x >> UNSIGNEDSHIFT;			// scale to fit in unsigned
	pluy = player->y >> UNSIGNEDSHIFT;
	player->tilex = player->x >> TILESHIFT;		// scale to tile values
	player->tiley = player->y >> TILESHIFT;

//
// go back to the game
//
	DrawPlayBorder();

	switch (ob->obclass)
	{
	case schabbobj:
		NewState(ob, s_schabbdeathcam);
		break;
	case realhitlerobj:
		NewState(ob, s_hitlerdeathcam);
		break;
	case giftobj:
		NewState(ob, s_giftdeathcam);
		break;
	case fatobj:
		NewState(ob, s_fatdeathcam);
		break;
	default:
		break;
	}

}
