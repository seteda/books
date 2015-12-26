#include "wl_def.h"

void A_StartDeathCam(objtype *ob);

void T_Path(objtype *ob);
void T_Shoot(objtype *ob);
void T_Bite(objtype *ob);
void T_DogChase(objtype *ob);
void T_Chase(objtype *ob);
void T_Projectile(objtype *ob);
void T_Stand(objtype *ob);

void A_Smoke(objtype *ob);

void T_Schabb(objtype *ob);
void T_SchabbThrow(objtype *ob);
void T_Fake(objtype *ob);
void T_FakeFire(objtype *ob);
void T_Ghosts(objtype *ob);

void A_SlurpieWL6(objtype *ob);
void A_SlurpieSOD(objtype *ob);
void A_HitlerMorph(objtype *ob);
void A_MechaSound(objtype *ob);

void T_UShoot(objtype *ob);

void T_Launch(objtype *ob);
void T_Will(objtype *ob);

void A_Relaunch(objtype *ob);
void A_Victory(objtype *ob);
void A_StartAttack(objtype *ob);
void A_Breathing(objtype *ob);

void T_SpectreWait(objtype *ob);
void A_Dormant(objtype *ob);

void T_Gift(objtype *ob);
void T_GiftThrow (objtype *ob);

void T_Fat(objtype *ob);
void T_FatThrow(objtype *ob);

void T_BJRun(objtype *ob);
void T_BJJump(objtype *ob);
void T_BJDone(objtype *ob);
void T_BJYell(objtype *ob);

void T_DeathCam(objtype *ob);

void T_Player(objtype *ob);
void T_Attack(objtype *ob);

statetype gamestatesWL6[MAXSTATESWL6] = {
/* s_none */	{false,0,0,NULL,NULL,s_noneWL6},
/* s_boom1 */	{false,SPR_BOOM_1WL6,6,NULL,NULL,s_boom2WL6},
/* s_boom2 */	{false,SPR_BOOM_2WL6,6,NULL,NULL,s_boom3WL6},
/* s_boom3 */	{false,SPR_BOOM_3WL6,6,NULL,NULL, s_noneWL6},

/* s_rocket */	{true,SPR_ROCKET_1WL6,3,T_Projectile,A_Smoke,s_rocketWL6},

/* s_smoke1 */	{false,SPR_SMOKE_1WL6,3,NULL,NULL,s_smoke2WL6},
/* s_smoke2 */	{false,SPR_SMOKE_2WL6,3,NULL,NULL,s_smoke3WL6},
/* s_smoke3 */	{false,SPR_SMOKE_3WL6,3,NULL,NULL,s_smoke4WL6},
/* s_smoke4 */	{false,SPR_SMOKE_4WL6,3,NULL,NULL, s_noneWL6},


/* guards */
/* s_grdstand */	{true,SPR_GRD_S_1WL6,0,T_Stand,NULL,s_grdstandWL6},

/* s_grdpath1  */	{true,SPR_GRD_W1_1WL6,20,T_Path,NULL,s_grdpath1sWL6},
/* s_grdpath1s  */	{true,SPR_GRD_W1_1WL6,5,NULL,NULL,s_grdpath2WL6},
/* s_grdpath2  */	{true,SPR_GRD_W2_1WL6,15,T_Path,NULL,s_grdpath3WL6},
/* s_grdpath3  */	{true,SPR_GRD_W3_1WL6,20,T_Path,NULL,s_grdpath3sWL6},
/* s_grdpath3s  */	{true,SPR_GRD_W3_1WL6,5,NULL,NULL,s_grdpath4WL6},
/* s_grdpath4  */	{true,SPR_GRD_W4_1WL6,15,T_Path,NULL,s_grdpath1WL6},

/* s_grdpain  */	{2,SPR_GRD_PAIN_1WL6,10,NULL,NULL,s_grdchase1WL6},
/* s_grdpain1  */	{2,SPR_GRD_PAIN_2WL6,10,NULL,NULL,s_grdchase1WL6},

/* s_grdshoot1  */	{false,SPR_GRD_SHOOT1WL6,20,NULL,NULL,s_grdshoot2WL6},
/* s_grdshoot2  */	{false,SPR_GRD_SHOOT2WL6,20,NULL,T_Shoot,s_grdshoot3WL6},
/* s_grdshoot3  */	{false,SPR_GRD_SHOOT3WL6,20,NULL,NULL,s_grdchase1WL6},

/* s_grdchase1  */	{true,SPR_GRD_W1_1WL6,10,T_Chase,NULL,s_grdchase1sWL6},
/* s_grdchase1s  */	{true,SPR_GRD_W1_1WL6,3,NULL,NULL,s_grdchase2WL6},
/* s_grdchase2  */	{true,SPR_GRD_W2_1WL6,8,T_Chase,NULL,s_grdchase3WL6},
/* s_grdchase3  */	{true,SPR_GRD_W3_1WL6,10,T_Chase,NULL,s_grdchase3sWL6},
/* s_grdchase3s  */	{true,SPR_GRD_W3_1WL6,3,NULL,NULL,s_grdchase4WL6},
/* s_grdchase4  */	{true,SPR_GRD_W4_1WL6,8,T_Chase,NULL,s_grdchase1WL6},

/* s_grddie1	 */	{false,SPR_GRD_DIE_1WL6,15,NULL,A_DeathScream,s_grddie2WL6},
/* s_grddie2	 */	{false,SPR_GRD_DIE_2WL6,15,NULL,NULL,s_grddie3WL6},
/* s_grddie3	 */	{false,SPR_GRD_DIE_3WL6,15,NULL,NULL,s_grddie4WL6},
/* s_grddie4	 */	{false,SPR_GRD_DEADWL6,0,NULL,NULL,s_grddie4WL6},



/* s_dogpath1  */	{true,SPR_DOG_W1_1WL6,20,T_Path,NULL,s_dogpath1sWL6},
/* s_dogpath1s  */	{true,SPR_DOG_W1_1WL6,5,NULL,NULL,s_dogpath2WL6},
/* s_dogpath2  */	{true,SPR_DOG_W2_1WL6,15,T_Path,NULL,s_dogpath3WL6},
/* s_dogpath3  */	{true,SPR_DOG_W3_1WL6,20,T_Path,NULL,s_dogpath3sWL6},
/* s_dogpath3s  */	{true,SPR_DOG_W3_1WL6,5,NULL,NULL,s_dogpath4WL6},
/* s_dogpath4  */	{true,SPR_DOG_W4_1WL6,15,T_Path,NULL,s_dogpath1WL6},

/* s_dogjump1  */	{false,SPR_DOG_JUMP1WL6,10,NULL,NULL,s_dogjump2WL6},
/* s_dogjump2  */	{false,SPR_DOG_JUMP2WL6,10,NULL,T_Bite,s_dogjump3WL6},
/* s_dogjump3  */	{false,SPR_DOG_JUMP3WL6,10,NULL,NULL,s_dogjump4WL6},
/* s_dogjump4  */	{false,SPR_DOG_JUMP1WL6,10,NULL,NULL,s_dogjump5WL6},
/* s_dogjump5  */	{false,SPR_DOG_W1_1WL6,10,NULL,NULL,s_dogchase1WL6},

/* s_dogchase1  */	{true,SPR_DOG_W1_1WL6,10,T_DogChase,NULL,s_dogchase1sWL6},
/* s_dogchase1s  */	{true,SPR_DOG_W1_1WL6,3,NULL,NULL,s_dogchase2WL6},
/* s_dogchase2  */	{true,SPR_DOG_W2_1WL6,8,T_DogChase,NULL,s_dogchase3WL6},
/* s_dogchase3  */	{true,SPR_DOG_W3_1WL6,10,T_DogChase,NULL,s_dogchase3sWL6},
/* s_dogchase3s  */	{true,SPR_DOG_W3_1WL6,3,NULL,NULL,s_dogchase4WL6},
/* s_dogchase4  */	{true,SPR_DOG_W4_1WL6,8,T_DogChase,NULL,s_dogchase1WL6},

/* s_dogdie1	 */	{false,SPR_DOG_DIE_1WL6,15,NULL,A_DeathScream,s_dogdie2WL6},
/* s_dogdie2	 */	{false,SPR_DOG_DIE_2WL6,15,NULL,NULL,s_dogdie3WL6},
/* s_dogdie3	 */	{false,SPR_DOG_DIE_3WL6,15,NULL,NULL,s_dogdeadWL6},
/* s_dogdead	 */	{false,SPR_DOG_DEADWL6,15,NULL,NULL,s_dogdeadWL6},

/* s_ofcstand */	{true,SPR_OFC_S_1WL6,0,T_Stand,NULL,s_ofcstandWL6},

/* s_ofcpath1  */	{true,SPR_OFC_W1_1WL6,20,T_Path,NULL,s_ofcpath1sWL6},
/* s_ofcpath1s  */	{true,SPR_OFC_W1_1WL6,5,NULL,NULL,s_ofcpath2WL6},
/* s_ofcpath2  */	{true,SPR_OFC_W2_1WL6,15,T_Path,NULL,s_ofcpath3WL6},
/* s_ofcpath3  */	{true,SPR_OFC_W3_1WL6,20,T_Path,NULL,s_ofcpath3sWL6},
/* s_ofcpath3s  */	{true,SPR_OFC_W3_1WL6,5,NULL,NULL,s_ofcpath4WL6},
/* s_ofcpath4  */	{true,SPR_OFC_W4_1WL6,15,T_Path,NULL,s_ofcpath1WL6},

/* s_ofcpain  */	{2,SPR_OFC_PAIN_1WL6,10,NULL,NULL,s_ofcchase1WL6},
/* s_ofcpain1  */	{2,SPR_OFC_PAIN_2WL6,10,NULL,NULL,s_ofcchase1WL6},

/* s_ofcshoot1  */	{false,SPR_OFC_SHOOT1WL6,6,NULL,NULL,s_ofcshoot2WL6},
/* s_ofcshoot2  */	{false,SPR_OFC_SHOOT2WL6,20,NULL,T_Shoot,s_ofcshoot3WL6},
/* s_ofcshoot3  */	{false,SPR_OFC_SHOOT3WL6,10,NULL,NULL,s_ofcchase1WL6},

/* s_ofcchase1  */	{true,SPR_OFC_W1_1WL6,10,T_Chase,NULL,s_ofcchase1sWL6},
/* s_ofcchase1s  */	{true,SPR_OFC_W1_1WL6,3,NULL,NULL,s_ofcchase2WL6},
/* s_ofcchase2  */	{true,SPR_OFC_W2_1WL6,8,T_Chase,NULL,s_ofcchase3WL6},
/* s_ofcchase3  */	{true,SPR_OFC_W3_1WL6,10,T_Chase,NULL,s_ofcchase3sWL6},
/* s_ofcchase3s  */	{true,SPR_OFC_W3_1WL6,3,NULL,NULL,s_ofcchase4WL6},
/* s_ofcchase4  */	{true,SPR_OFC_W4_1WL6,8,T_Chase,NULL,s_ofcchase1WL6},

/* s_ofcdie1	 */	{false,SPR_OFC_DIE_1WL6,11,NULL,A_DeathScream,s_ofcdie2WL6},
/* s_ofcdie2	 */	{false,SPR_OFC_DIE_2WL6,11,NULL,NULL,s_ofcdie3WL6},
/* s_ofcdie3	 */	{false,SPR_OFC_DIE_3WL6,11,NULL,NULL,s_ofcdie4WL6},
/* s_ofcdie4	 */	{false,SPR_OFC_DIE_4WL6,11,NULL,NULL,s_ofcdie5WL6},
/* s_ofcdie5	 */	{false,SPR_OFC_DEADWL6,0,NULL,NULL,s_ofcdie5WL6},


/* s_mutstand */	{true,SPR_MUT_S_1WL6,0,T_Stand,NULL,s_mutstandWL6},

/* s_mutpath1  */	{true,SPR_MUT_W1_1WL6,20,T_Path,NULL,s_mutpath1sWL6},
/* s_mutpath1s  */	{true,SPR_MUT_W1_1WL6,5,NULL,NULL,s_mutpath2WL6},
/* s_mutpath2  */	{true,SPR_MUT_W2_1WL6,15,T_Path,NULL,s_mutpath3WL6},
/* s_mutpath3  */	{true,SPR_MUT_W3_1WL6,20,T_Path,NULL,s_mutpath3sWL6},
/* s_mutpath3s  */	{true,SPR_MUT_W3_1WL6,5,NULL,NULL,s_mutpath4WL6},
/* s_mutpath4  */	{true,SPR_MUT_W4_1WL6,15,T_Path,NULL,s_mutpath1WL6},

/* s_mutpain  */	{2,SPR_MUT_PAIN_1WL6,10,NULL,NULL,s_mutchase1WL6},
/* s_mutpain1  */	{2,SPR_MUT_PAIN_2WL6,10,NULL,NULL,s_mutchase1WL6},

/* s_mutshoot1  */	{false,SPR_MUT_SHOOT1WL6,6,NULL,T_Shoot,s_mutshoot2WL6},
/* s_mutshoot2  */	{false,SPR_MUT_SHOOT2WL6,20,NULL,NULL,s_mutshoot3WL6},
/* s_mutshoot3  */	{false,SPR_MUT_SHOOT3WL6,10,NULL,T_Shoot,s_mutshoot4WL6},
/* s_mutshoot4  */	{false,SPR_MUT_SHOOT4WL6,20,NULL,NULL,s_mutchase1WL6},

/* s_mutchase1  */	{true,SPR_MUT_W1_1WL6,10,T_Chase,NULL,s_mutchase1sWL6},
/* s_mutchase1s  */	{true,SPR_MUT_W1_1WL6,3,NULL,NULL,s_mutchase2WL6},
/* s_mutchase2  */	{true,SPR_MUT_W2_1WL6,8,T_Chase,NULL,s_mutchase3WL6},
/* s_mutchase3  */	{true,SPR_MUT_W3_1WL6,10,T_Chase,NULL,s_mutchase3sWL6},
/* s_mutchase3s  */	{true,SPR_MUT_W3_1WL6,3,NULL,NULL,s_mutchase4WL6},
/* s_mutchase4  */	{true,SPR_MUT_W4_1WL6,8,T_Chase,NULL,s_mutchase1WL6},

/* s_mutdie1	 */	{false,SPR_MUT_DIE_1WL6,7,NULL,A_DeathScream,s_mutdie2WL6},
/* s_mutdie2	 */	{false,SPR_MUT_DIE_2WL6,7,NULL,NULL,s_mutdie3WL6},
/* s_mutdie3	 */	{false,SPR_MUT_DIE_3WL6,7,NULL,NULL,s_mutdie4WL6},
/* s_mutdie4	 */	{false,SPR_MUT_DIE_4WL6,7,NULL,NULL,s_mutdie5WL6},
/* s_mutdie5	 */	{false,SPR_MUT_DEADWL6,0,NULL,NULL,s_mutdie5WL6},


/* s_ssstand */	{true,SPR_SS_S_1WL6,0,T_Stand,NULL,s_ssstandWL6},

/* s_sspath1  */	{true,SPR_SS_W1_1WL6,20,T_Path,NULL,s_sspath1sWL6},
/* s_sspath1s  */	{true,SPR_SS_W1_1WL6,5,NULL,NULL,s_sspath2WL6},
/* s_sspath2  */	{true,SPR_SS_W2_1WL6,15,T_Path,NULL,s_sspath3WL6},
/* s_sspath3  */	{true,SPR_SS_W3_1WL6,20,T_Path,NULL,s_sspath3sWL6},
/* s_sspath3s  */	{true,SPR_SS_W3_1WL6,5,NULL,NULL,s_sspath4WL6},
/* s_sspath4  */	{true,SPR_SS_W4_1WL6,15,T_Path,NULL,s_sspath1WL6},

/* s_sspain 	 */	{2,SPR_SS_PAIN_1WL6,10,NULL,NULL,s_sschase1WL6},
/* s_sspain1  */	{2,SPR_SS_PAIN_2WL6,10,NULL,NULL,s_sschase1WL6},

/* s_ssshoot1  */	{false,SPR_SS_SHOOT1WL6,20,NULL,NULL,s_ssshoot2WL6},
/* s_ssshoot2  */	{false,SPR_SS_SHOOT2WL6,20,NULL,T_Shoot,s_ssshoot3WL6},
/* s_ssshoot3  */	{false,SPR_SS_SHOOT3WL6,10,NULL,NULL,s_ssshoot4WL6},
/* s_ssshoot4  */	{false,SPR_SS_SHOOT2WL6,10,NULL,T_Shoot,s_ssshoot5WL6},
/* s_ssshoot5  */	{false,SPR_SS_SHOOT3WL6,10,NULL,NULL,s_ssshoot6WL6},
/* s_ssshoot6  */	{false,SPR_SS_SHOOT2WL6,10,NULL,T_Shoot,s_ssshoot7WL6},
/* s_ssshoot7   */	{false,SPR_SS_SHOOT3WL6,10,NULL,NULL,s_ssshoot8WL6},
/* s_ssshoot8   */	{false,SPR_SS_SHOOT2WL6,10,NULL,T_Shoot,s_ssshoot9WL6},
/* s_ssshoot9   */	{false,SPR_SS_SHOOT3WL6,10,NULL,NULL,s_sschase1WL6},

/* s_sschase1  */	{true,SPR_SS_W1_1WL6,10,T_Chase,NULL,s_sschase1sWL6},
/* s_sschase1s  */	{true,SPR_SS_W1_1WL6,3,NULL,NULL,s_sschase2WL6},
/* s_sschase2  */	{true,SPR_SS_W2_1WL6,8,T_Chase,NULL,s_sschase3WL6},
/* s_sschase3  */	{true,SPR_SS_W3_1WL6,10,T_Chase,NULL,s_sschase3sWL6},
/* s_sschase3s  */	{true,SPR_SS_W3_1WL6,3,NULL,NULL,s_sschase4WL6},
/* s_sschase4  */	{true,SPR_SS_W4_1WL6,8,T_Chase,NULL,s_sschase1WL6},

/* s_ssdie1	 */	{false,SPR_SS_DIE_1WL6,15,NULL,A_DeathScream,s_ssdie2WL6},
/* s_ssdie2	 */	{false,SPR_SS_DIE_2WL6,15,NULL,NULL,s_ssdie3WL6},
/* s_ssdie3	 */	{false,SPR_SS_DIE_3WL6,15,NULL,NULL,s_ssdie4WL6},
/* s_ssdie4	 */	{false,SPR_SS_DEADWL6,0,NULL,NULL,s_ssdie4WL6},

/* s_blinkychase1  */	{false,SPR_BLINKY_W1WL6,10,T_Ghosts,NULL,s_blinkychase2},
/* s_blinkychase2  */	{false,SPR_BLINKY_W2WL6,10,T_Ghosts,NULL,s_blinkychase1},

/* s_inkychase1 	 */	{false,SPR_INKY_W1WL6,10,T_Ghosts,NULL,s_inkychase2},
/* s_inkychase2 	 */	{false,SPR_INKY_W2WL6,10,T_Ghosts,NULL,s_inkychase1},

/* s_pinkychase1  */	{false,SPR_PINKY_W1WL6,10,T_Ghosts,NULL,s_pinkychase2},
/* s_pinkychase2  */	{false,SPR_PINKY_W2WL6,10,T_Ghosts,NULL,s_pinkychase1},

/* s_clydechase1  */	{false,SPR_CLYDE_W1WL6,10,T_Ghosts,NULL,s_clydechase2},
/* s_clydechase2  */	{false,SPR_CLYDE_W2WL6,10,T_Ghosts,NULL,s_clydechase1},

/* s_bossstand */	{false,SPR_BOSS_W1WL6,0,T_Stand,NULL,s_bossstand},

/* s_bosschase1  */	{false,SPR_BOSS_W1WL6,10,T_Chase,NULL,s_bosschase1s},
/* s_bosschase1s */	{false,SPR_BOSS_W1WL6,3,NULL,NULL,s_bosschase2},
/* s_bosschase2  */	{false,SPR_BOSS_W2WL6,8,T_Chase,NULL,s_bosschase3},
/* s_bosschase3  */	{false,SPR_BOSS_W3WL6,10,T_Chase,NULL,s_bosschase3s},
/* s_bosschase3s */	{false,SPR_BOSS_W3WL6,3,NULL,NULL,s_bosschase4},
/* s_bosschase4  */	{false,SPR_BOSS_W4WL6,8,T_Chase,NULL,s_bosschase1},

/* s_bossdie1 */	{false,SPR_BOSS_DIE1WL6,15,NULL,A_DeathScream,s_bossdie2},
/* s_bossdie2 */	{false,SPR_BOSS_DIE2WL6,15,NULL,NULL,s_bossdie3},
/* s_bossdie3 */	{false,SPR_BOSS_DIE3WL6,15,NULL,NULL,s_bossdie4},
/* s_bossdie4 */	{false,SPR_BOSS_DEADWL6,0,NULL,NULL,s_bossdie4},

/* s_bossshoot1  */	{false,SPR_BOSS_SHOOT1WL6,30,NULL,NULL,s_bossshoot2},
/* s_bossshoot2  */	{false,SPR_BOSS_SHOOT2WL6,10,NULL,T_Shoot,s_bossshoot3},
/* s_bossshoot3  */	{false,SPR_BOSS_SHOOT3WL6,10,NULL,T_Shoot,s_bossshoot4},
/* s_bossshoot4  */	{false,SPR_BOSS_SHOOT2WL6,10,NULL,T_Shoot,s_bossshoot5},
/* s_bossshoot5  */	{false,SPR_BOSS_SHOOT3WL6,10,NULL,T_Shoot,s_bossshoot6},
/* s_bossshoot6  */	{false,SPR_BOSS_SHOOT2WL6,10,NULL,T_Shoot,s_bossshoot7},
/* s_bossshoot7  */	{false,SPR_BOSS_SHOOT3WL6,10,NULL,T_Shoot,s_bossshoot8},
/* s_bossshoot8  */	{false,SPR_BOSS_SHOOT1WL6,10,NULL,NULL,s_bosschase1},



/* s_gretelstand */	{false,SPR_GRETEL_W1WL6,0,T_Stand,NULL,s_gretelstand},

/* s_gretelchase1  */	{false,SPR_GRETEL_W1WL6,10,T_Chase,NULL,s_gretelchase1s},
/* s_gretelchase1s */	{false,SPR_GRETEL_W1WL6,3,NULL,NULL,s_gretelchase2},
/* s_gretelchase2  */	{false,SPR_GRETEL_W2WL6,8,T_Chase,NULL,s_gretelchase3},
/* s_gretelchase3  */	{false,SPR_GRETEL_W3WL6,10,T_Chase,NULL,s_gretelchase3s},
/* s_gretelchase3s */	{false,SPR_GRETEL_W3WL6,3,NULL,NULL,s_gretelchase4},
/* s_gretelchase4  */	{false,SPR_GRETEL_W4WL6,8,T_Chase,NULL,s_gretelchase1},

/* s_greteldie1 */	{false,SPR_GRETEL_DIE1WL6,15,NULL,A_DeathScream,s_greteldie2},
/* s_greteldie2 */	{false,SPR_GRETEL_DIE2WL6,15,NULL,NULL,s_greteldie3},
/* s_greteldie3 */	{false,SPR_GRETEL_DIE3WL6,15,NULL,NULL,s_greteldie4},
/* s_greteldie4 */	{false,SPR_GRETEL_DEADWL6,0,NULL,NULL,s_greteldie4},

/* s_gretelshoot1  */	{false,SPR_GRETEL_SHOOT1WL6,30,NULL,NULL,s_gretelshoot2},
/* s_gretelshoot2  */	{false,SPR_GRETEL_SHOOT2WL6,10,NULL,T_Shoot,s_gretelshoot3},
/* s_gretelshoot3  */	{false,SPR_GRETEL_SHOOT3WL6,10,NULL,T_Shoot,s_gretelshoot4},
/* s_gretelshoot4  */	{false,SPR_GRETEL_SHOOT2WL6,10,NULL,T_Shoot,s_gretelshoot5},
/* s_gretelshoot5  */	{false,SPR_GRETEL_SHOOT3WL6,10,NULL,T_Shoot,s_gretelshoot6},
/* s_gretelshoot6  */	{false,SPR_GRETEL_SHOOT2WL6,10,NULL,T_Shoot,s_gretelshoot7},
/* s_gretelshoot7  */	{false,SPR_GRETEL_SHOOT3WL6,10,NULL,T_Shoot,s_gretelshoot8},
/* s_gretelshoot8  */	{false,SPR_GRETEL_SHOOT1WL6,10,NULL,NULL,s_gretelchase1},

/* s_schabbstand */	{false,SPR_SCHABB_W1WL6,0,T_Stand,NULL,s_schabbstand},

/* s_schabbchase1  */	{false,SPR_SCHABB_W1WL6,10,T_Schabb,NULL,s_schabbchase1s},
/* s_schabbchase1s */	{false,SPR_SCHABB_W1WL6,3,NULL,NULL,s_schabbchase2},
/* s_schabbchase2  */	{false,SPR_SCHABB_W2WL6,8,T_Schabb,NULL,s_schabbchase3},
/* s_schabbchase3  */	{false,SPR_SCHABB_W3WL6,10,T_Schabb,NULL,s_schabbchase3s},
/* s_schabbchase3s */	{false,SPR_SCHABB_W3WL6,3,NULL,NULL,s_schabbchase4},
/* s_schabbchase4  */	{false,SPR_SCHABB_W4WL6,8,T_Schabb,NULL,s_schabbchase1},

/* s_schabbdeathcam */	{false,SPR_SCHABB_W1WL6,1,NULL,NULL,s_schabbdie1},

/* s_schabbdie1 */	{false,SPR_SCHABB_W1WL6,10,NULL,A_DeathScream,s_schabbdie2},
/* s_schabbdie2 */	{false,SPR_SCHABB_W1WL6,10,NULL,NULL,s_schabbdie3},
/* s_schabbdie3 */	{false,SPR_SCHABB_DIE1WL6,10,NULL,NULL,s_schabbdie4},
/* s_schabbdie4 */	{false,SPR_SCHABB_DIE2WL6,10,NULL,NULL,s_schabbdie5},
/* s_schabbdie5 */	{false,SPR_SCHABB_DIE3WL6,10,NULL,NULL,s_schabbdie6},
/* s_schabbdie6 */	{false,SPR_SCHABB_DEADWL6,20,NULL,A_StartDeathCam,s_schabbdie6},

/* s_schabbshoot1  */	{false,SPR_SCHABB_SHOOT1WL6,30,NULL,NULL,s_schabbshoot2},
/* s_schabbshoot2  */	{false,SPR_SCHABB_SHOOT2WL6,10,NULL,T_SchabbThrow,s_schabbchase1},

/* s_needle1  */	{false,SPR_HYPO1WL6,6,T_Projectile,NULL,s_needle2},
/* s_needle2  */	{false,SPR_HYPO2WL6,6,T_Projectile,NULL,s_needle3},
/* s_needle3  */	{false,SPR_HYPO3WL6,6,T_Projectile,NULL,s_needle4},
/* s_needle4  */	{false,SPR_HYPO4WL6,6,T_Projectile,NULL,s_needle1},



/* s_giftstand */	{false,SPR_GIFT_W1WL6,0,T_Stand,NULL,s_giftstand},

/* s_giftchase1  */	{false,SPR_GIFT_W1WL6,10,T_Gift,NULL,s_giftchase1s},
/* s_giftchase1s */	{false,SPR_GIFT_W1WL6,3,NULL,NULL,s_giftchase2},
/* s_giftchase2  */	{false,SPR_GIFT_W2WL6,8,T_Gift,NULL,s_giftchase3},
/* s_giftchase3  */	{false,SPR_GIFT_W3WL6,10,T_Gift,NULL,s_giftchase3s},
/* s_giftchase3s */	{false,SPR_GIFT_W3WL6,3,NULL,NULL,s_giftchase4},
/* s_giftchase4  */	{false,SPR_GIFT_W4WL6,8,T_Gift,NULL,s_giftchase1},

/* s_giftdeathcam */	{false,SPR_GIFT_W1WL6,1,NULL,NULL,s_giftdie1},

/* s_giftdie1 */	{false,SPR_GIFT_W1WL6,1,NULL,A_DeathScream,s_giftdie2},
/* s_giftdie2 */	{false,SPR_GIFT_W1WL6,10,NULL,NULL,s_giftdie3},
/* s_giftdie3 */	{false,SPR_GIFT_DIE1WL6,10,NULL,NULL,s_giftdie4},
/* s_giftdie4 */	{false,SPR_GIFT_DIE2WL6,10,NULL,NULL,s_giftdie5},
/* s_giftdie5 */	{false,SPR_GIFT_DIE3WL6,10,NULL,NULL,s_giftdie6},
/* s_giftdie6 */	{false,SPR_GIFT_DEADWL6,20,NULL,A_StartDeathCam,s_giftdie6},

/* s_giftshoot1  */	{false,SPR_GIFT_SHOOT1WL6,30,NULL,NULL,s_giftshoot2},
/* s_giftshoot2  */	{false,SPR_GIFT_SHOOT2WL6,10,NULL,T_GiftThrow,s_giftchase1},



/* s_fatstand */	{false,SPR_FAT_W1WL6,0,T_Stand,NULL,s_fatstand},

/* s_fatchase1  */	{false,SPR_FAT_W1WL6,10,T_Fat,NULL,s_fatchase1s},
/* s_fatchase1s */	{false,SPR_FAT_W1WL6,3,NULL,NULL,s_fatchase2},
/* s_fatchase2  */	{false,SPR_FAT_W2WL6,8,T_Fat,NULL,s_fatchase3},
/* s_fatchase3  */	{false,SPR_FAT_W3WL6,10,T_Fat,NULL,s_fatchase3s},
/* s_fatchase3s */	{false,SPR_FAT_W3WL6,3,NULL,NULL,s_fatchase4},
/* s_fatchase4  */	{false,SPR_FAT_W4WL6,8,T_Fat,NULL,s_fatchase1},

/* s_fatdeathcam */	{false,SPR_FAT_W1WL6,1,NULL,NULL,s_fatdie1},

/* s_fatdie1 */	{false,SPR_FAT_W1WL6,1,NULL,A_DeathScream,s_fatdie2},
/* s_fatdie2 */	{false,SPR_FAT_W1WL6,10,NULL,NULL,s_fatdie3},
/* s_fatdie3 */	{false,SPR_FAT_DIE1WL6,10,NULL,NULL,s_fatdie4},
/* s_fatdie4 */	{false,SPR_FAT_DIE2WL6,10,NULL,NULL,s_fatdie5},
/* s_fatdie5 */	{false,SPR_FAT_DIE3WL6,10,NULL,NULL,s_fatdie6},
/* s_fatdie6 */	{false,SPR_FAT_DEADWL6,20,NULL,A_StartDeathCam,s_fatdie6},

/* s_fatshoot1  */	{false,SPR_FAT_SHOOT1WL6,30,NULL,NULL,s_fatshoot2},
/* s_fatshoot2  */	{false,SPR_FAT_SHOOT2WL6,10,NULL,T_GiftThrow,s_fatshoot3},
/* s_fatshoot3  */	{false,SPR_FAT_SHOOT3WL6,10,NULL,T_Shoot,s_fatshoot4},
/* s_fatshoot4  */	{false,SPR_FAT_SHOOT4WL6,10,NULL,T_Shoot,s_fatshoot5},
/* s_fatshoot5  */	{false,SPR_FAT_SHOOT3WL6,10,NULL,T_Shoot,s_fatshoot6},
/* s_fatshoot6  */	{false,SPR_FAT_SHOOT4WL6,10,NULL,T_Shoot,s_fatchase1},


/* s_fakestand */	{false,SPR_FAKE_W1WL6,0,T_Stand,NULL,s_fakestand},

/* s_fakechase1  */	{false,SPR_FAKE_W1WL6,10,T_Fake,NULL,s_fakechase1s},
/* s_fakechase1s */	{false,SPR_FAKE_W1WL6,3,NULL,NULL,s_fakechase2},
/* s_fakechase2  */	{false,SPR_FAKE_W2WL6,8,T_Fake,NULL,s_fakechase3},
/* s_fakechase3  */	{false,SPR_FAKE_W3WL6,10,T_Fake,NULL,s_fakechase3s},
/* s_fakechase3s */	{false,SPR_FAKE_W3WL6,3,NULL,NULL,s_fakechase4},
/* s_fakechase4  */	{false,SPR_FAKE_W4WL6,8,T_Fake,NULL,s_fakechase1},

/* s_fakedie1 */	{false,SPR_FAKE_DIE1WL6,10,NULL,A_DeathScream,s_fakedie2},
/* s_fakedie2 */	{false,SPR_FAKE_DIE2WL6,10,NULL,NULL,s_fakedie3},
/* s_fakedie3 */	{false,SPR_FAKE_DIE3WL6,10,NULL,NULL,s_fakedie4},
/* s_fakedie4 */	{false,SPR_FAKE_DIE4WL6,10,NULL,NULL,s_fakedie5},
/* s_fakedie5 */	{false,SPR_FAKE_DIE5WL6,10,NULL,NULL,s_fakedie6},
/* s_fakedie6 */	{false,SPR_FAKE_DEADWL6,0,NULL,NULL,s_fakedie6},

/* s_fakeshoot1  */	{false,SPR_FAKE_SHOOTWL6,8,NULL,T_FakeFire,s_fakeshoot2},
/* s_fakeshoot2  */	{false,SPR_FAKE_SHOOTWL6,8,NULL,T_FakeFire,s_fakeshoot3},
/* s_fakeshoot3  */	{false,SPR_FAKE_SHOOTWL6,8,NULL,T_FakeFire,s_fakeshoot4},
/* s_fakeshoot4  */	{false,SPR_FAKE_SHOOTWL6,8,NULL,T_FakeFire,s_fakeshoot5},
/* s_fakeshoot5  */	{false,SPR_FAKE_SHOOTWL6,8,NULL,T_FakeFire,s_fakeshoot6},
/* s_fakeshoot6  */	{false,SPR_FAKE_SHOOTWL6,8,NULL,T_FakeFire,s_fakeshoot7},
/* s_fakeshoot7  */	{false,SPR_FAKE_SHOOTWL6,8,NULL,T_FakeFire,s_fakeshoot8},
/* s_fakeshoot8  */	{false,SPR_FAKE_SHOOTWL6,8,NULL,T_FakeFire,s_fakeshoot9},
/* s_fakeshoot9  */	{false,SPR_FAKE_SHOOTWL6,8,NULL,NULL,s_fakechase1},

/* s_fire1  */	{false,SPR_FIRE1WL6,6,NULL,T_Projectile,s_fire2},
/* s_fire2  */	{false,SPR_FIRE2WL6,6,NULL,T_Projectile,s_fire1},


/* s_mechastand */	{false,SPR_MECHA_W1WL6,0,T_Stand,NULL,s_mechastand},

/* s_mechachase1  */	{false,SPR_MECHA_W1WL6,10,T_Chase,A_MechaSound,s_mechachase1s},
/* s_mechachase1s */	{false,SPR_MECHA_W1WL6,6,NULL,NULL,s_mechachase2},
/* s_mechachase2  */	{false,SPR_MECHA_W2WL6,8,T_Chase,NULL,s_mechachase3},
/* s_mechachase3  */	{false,SPR_MECHA_W3WL6,10,T_Chase,A_MechaSound,s_mechachase3s},
/* s_mechachase3s */	{false,SPR_MECHA_W3WL6,6,NULL,NULL,s_mechachase4},
/* s_mechachase4  */	{false,SPR_MECHA_W4WL6,8,T_Chase,NULL,s_mechachase1},

/* s_mechadie1 */	{false,SPR_MECHA_DIE1WL6,10,NULL,A_DeathScream,s_mechadie2},
/* s_mechadie2 */	{false,SPR_MECHA_DIE2WL6,10,NULL,NULL,s_mechadie3},
/* s_mechadie3 */	{false,SPR_MECHA_DIE3WL6,10,NULL,A_HitlerMorph,s_mechadie4},
/* s_mechadie4 */	{false,SPR_MECHA_DEADWL6,0,NULL,NULL,s_mechadie4},

/* s_mechashoot1  */	{false,SPR_MECHA_SHOOT1WL6,30,NULL,NULL,s_mechashoot2},
/* s_mechashoot2  */	{false,SPR_MECHA_SHOOT2WL6,10,NULL,T_Shoot,s_mechashoot3},
/* s_mechashoot3  */	{false,SPR_MECHA_SHOOT3WL6,10,NULL,T_Shoot,s_mechashoot4},
/* s_mechashoot4  */	{false,SPR_MECHA_SHOOT2WL6,10,NULL,T_Shoot,s_mechashoot5},
/* s_mechashoot5  */	{false,SPR_MECHA_SHOOT3WL6,10,NULL,T_Shoot,s_mechashoot6},
/* s_mechashoot6  */	{false,SPR_MECHA_SHOOT2WL6,10,NULL,T_Shoot,s_mechachase1},


/* s_hitlerchase1  */	{false,SPR_HITLER_W1WL6,6,T_Chase,NULL,s_hitlerchase1s},
/* s_hitlerchase1s */	{false,SPR_HITLER_W1WL6,4,NULL,NULL,s_hitlerchase2},
/* s_hitlerchase2  */	{false,SPR_HITLER_W2WL6,2,T_Chase,NULL,s_hitlerchase3},
/* s_hitlerchase3  */	{false,SPR_HITLER_W3WL6,6,T_Chase,NULL,s_hitlerchase3s},
/* s_hitlerchase3s */	{false,SPR_HITLER_W3WL6,4,NULL,NULL,s_hitlerchase4},
/* s_hitlerchase4  */	{false,SPR_HITLER_W4WL6,2,T_Chase,NULL,s_hitlerchase1},

/* s_hitlerdeathcam */	{false,SPR_HITLER_W1WL6,10,NULL,NULL,s_hitlerdie1},

/* s_hitlerdie1 */	{false,SPR_HITLER_W1WL6,1,NULL,A_DeathScream,s_hitlerdie2},
/* s_hitlerdie2 */	{false,SPR_HITLER_W1WL6,10,NULL,NULL,s_hitlerdie3},
/* s_hitlerdie3 */	{false,SPR_HITLER_DIE1WL6,10,NULL,A_SlurpieWL6,s_hitlerdie4},
/* s_hitlerdie4 */	{false,SPR_HITLER_DIE2WL6,10,NULL,NULL,s_hitlerdie5},
/* s_hitlerdie5 */	{false,SPR_HITLER_DIE3WL6,10,NULL,NULL,s_hitlerdie6},
/* s_hitlerdie6 */	{false,SPR_HITLER_DIE4WL6,10,NULL,NULL,s_hitlerdie7},
/* s_hitlerdie7 */	{false,SPR_HITLER_DIE5WL6,10,NULL,NULL,s_hitlerdie8},
/* s_hitlerdie8 */	{false,SPR_HITLER_DIE6WL6,10,NULL,NULL,s_hitlerdie9},
/* s_hitlerdie9 */	{false,SPR_HITLER_DIE7WL6,10,NULL,NULL,s_hitlerdie10},
/* s_hitlerdie10 */	{false,SPR_HITLER_DEADWL6,20,NULL,A_StartDeathCam,s_hitlerdie10},

/* s_hitlershoot1  */	{false,SPR_HITLER_SHOOT1WL6,30,NULL,NULL,s_hitlershoot2},
/* s_hitlershoot2  */	{false,SPR_HITLER_SHOOT2WL6,10,NULL,T_Shoot,s_hitlershoot3},
/* s_hitlershoot3  */	{false,SPR_HITLER_SHOOT3WL6,10,NULL,T_Shoot,s_hitlershoot4},
/* s_hitlershoot4  */	{false,SPR_HITLER_SHOOT2WL6,10,NULL,T_Shoot,s_hitlershoot5},
/* s_hitlershoot5  */	{false,SPR_HITLER_SHOOT3WL6,10,NULL,T_Shoot,s_hitlershoot6},
/* s_hitlershoot6  */	{false,SPR_HITLER_SHOOT2WL6,10,NULL,T_Shoot,s_hitlerchase1},



/* s_bjrun1  */	{false,SPR_BJ_W1WL6,12,T_BJRun,NULL,s_bjrun1s},
/* s_bjrun1s */	{false,SPR_BJ_W1WL6,3, NULL,NULL,s_bjrun2},
/* s_bjrun2  */	{false,SPR_BJ_W2WL6,8,T_BJRun,NULL,s_bjrun3},
/* s_bjrun3  */	{false,SPR_BJ_W3WL6,12,T_BJRun,NULL,s_bjrun3s},
/* s_bjrun3s */	{false,SPR_BJ_W3WL6,3, NULL,NULL,s_bjrun4},
/* s_bjrun4  */	{false,SPR_BJ_W4WL6,8,T_BJRun,NULL,s_bjrun1},


/* s_bjjump1 */	{false,SPR_BJ_JUMP1WL6,14,T_BJJump,NULL,s_bjjump2},
/* s_bjjump2 */	{false,SPR_BJ_JUMP2WL6,14,T_BJJump,T_BJYell,s_bjjump3},
/* s_bjjump3 */	{false,SPR_BJ_JUMP3WL6,14,T_BJJump,NULL,s_bjjump4},
/* s_bjjump4 */	{false,SPR_BJ_JUMP4WL6,300,NULL,T_BJDone,s_bjjump4},


/* s_deathcam */	{false,0,0,NULL,NULL, s_noneWL6},


/* s_player */	{false,0,0,T_Player,NULL, s_noneWL6},
/* s_attack */	{false,0,0,T_Attack,NULL, s_noneWL6}

};

statetype gamestatesSOD[MAXSTATESSOD] = {
/* s_none */	{false,0,0,NULL,NULL,s_noneSOD},
/* s_boom1 */	{false,SPR_BOOM_1SOD,6,NULL,NULL,s_boom2SOD},
/* s_boom2 */	{false,SPR_BOOM_2SOD,6,NULL,NULL,s_boom3SOD},
/* s_boom3 */	{false,SPR_BOOM_3SOD,6,NULL,NULL, s_noneSOD},

/* s_rocket */	{true,SPR_ROCKET_1SOD,3,T_Projectile,A_Smoke,s_rocketSOD},

/* s_smoke1 */	{false,SPR_SMOKE_1SOD,3,NULL,NULL,s_smoke2SOD},
/* s_smoke2 */	{false,SPR_SMOKE_2SOD,3,NULL,NULL,s_smoke3SOD},
/* s_smoke3 */	{false,SPR_SMOKE_3SOD,3,NULL,NULL,s_smoke4SOD},
/* s_smoke4 */	{false,SPR_SMOKE_4SOD,3,NULL,NULL, s_noneSOD},


/* guards */
/* s_grdstand */	{true,SPR_GRD_S_1SOD,0,T_Stand,NULL,s_grdstandSOD},

/* s_grdpath1  */	{true,SPR_GRD_W1_1SOD,20,T_Path,NULL,s_grdpath1sSOD},
/* s_grdpath1s  */	{true,SPR_GRD_W1_1SOD,5,NULL,NULL,s_grdpath2SOD},
/* s_grdpath2  */	{true,SPR_GRD_W2_1SOD,15,T_Path,NULL,s_grdpath3SOD},
/* s_grdpath3  */	{true,SPR_GRD_W3_1SOD,20,T_Path,NULL,s_grdpath3sSOD},
/* s_grdpath3s  */	{true,SPR_GRD_W3_1SOD,5,NULL,NULL,s_grdpath4SOD},
/* s_grdpath4  */	{true,SPR_GRD_W4_1SOD,15,T_Path,NULL,s_grdpath1SOD},

/* s_grdpain  */	{2,SPR_GRD_PAIN_1SOD,10,NULL,NULL,s_grdchase1SOD},
/* s_grdpain1  */	{2,SPR_GRD_PAIN_2SOD,10,NULL,NULL,s_grdchase1SOD},

/* s_grdshoot1  */	{false,SPR_GRD_SHOOT1SOD,20,NULL,NULL,s_grdshoot2SOD},
/* s_grdshoot2  */	{false,SPR_GRD_SHOOT2SOD,20,NULL,T_Shoot,s_grdshoot3SOD},
/* s_grdshoot3  */	{false,SPR_GRD_SHOOT3SOD,20,NULL,NULL,s_grdchase1SOD},

/* s_grdchase1  */	{true,SPR_GRD_W1_1SOD,10,T_Chase,NULL,s_grdchase1sSOD},
/* s_grdchase1s  */	{true,SPR_GRD_W1_1SOD,3,NULL,NULL,s_grdchase2SOD},
/* s_grdchase2  */	{true,SPR_GRD_W2_1SOD,8,T_Chase,NULL,s_grdchase3SOD},
/* s_grdchase3  */	{true,SPR_GRD_W3_1SOD,10,T_Chase,NULL,s_grdchase3sSOD},
/* s_grdchase3s  */	{true,SPR_GRD_W3_1SOD,3,NULL,NULL,s_grdchase4SOD},
/* s_grdchase4  */	{true,SPR_GRD_W4_1SOD,8,T_Chase,NULL,s_grdchase1SOD},

/* s_grddie1	 */	{false,SPR_GRD_DIE_1SOD,15,NULL,A_DeathScream,s_grddie2SOD},
/* s_grddie2	 */	{false,SPR_GRD_DIE_2SOD,15,NULL,NULL,s_grddie3SOD},
/* s_grddie3	 */	{false,SPR_GRD_DIE_3SOD,15,NULL,NULL,s_grddie4SOD},
/* s_grddie4	 */	{false,SPR_GRD_DEADSOD,0,NULL,NULL,s_grddie4SOD},



/* s_dogpath1  */	{true,SPR_DOG_W1_1SOD,20,T_Path,NULL,s_dogpath1sSOD},
/* s_dogpath1s  */	{true,SPR_DOG_W1_1SOD,5,NULL,NULL,s_dogpath2SOD},
/* s_dogpath2  */	{true,SPR_DOG_W2_1SOD,15,T_Path,NULL,s_dogpath3SOD},
/* s_dogpath3  */	{true,SPR_DOG_W3_1SOD,20,T_Path,NULL,s_dogpath3sSOD},
/* s_dogpath3s  */	{true,SPR_DOG_W3_1SOD,5,NULL,NULL,s_dogpath4SOD},
/* s_dogpath4  */	{true,SPR_DOG_W4_1SOD,15,T_Path,NULL,s_dogpath1SOD},

/* s_dogjump1  */	{false,SPR_DOG_JUMP1SOD,10,NULL,NULL,s_dogjump2SOD},
/* s_dogjump2  */	{false,SPR_DOG_JUMP2SOD,10,NULL,T_Bite,s_dogjump3SOD},
/* s_dogjump3  */	{false,SPR_DOG_JUMP3SOD,10,NULL,NULL,s_dogjump4SOD},
/* s_dogjump4  */	{false,SPR_DOG_JUMP1SOD,10,NULL,NULL,s_dogjump5SOD},
/* s_dogjump5  */	{false,SPR_DOG_W1_1SOD,10,NULL,NULL,s_dogchase1SOD},

/* s_dogchase1  */	{true,SPR_DOG_W1_1SOD,10,T_DogChase,NULL,s_dogchase1sSOD},
/* s_dogchase1s  */	{true,SPR_DOG_W1_1SOD,3,NULL,NULL,s_dogchase2SOD},
/* s_dogchase2  */	{true,SPR_DOG_W2_1SOD,8,T_DogChase,NULL,s_dogchase3SOD},
/* s_dogchase3  */	{true,SPR_DOG_W3_1SOD,10,T_DogChase,NULL,s_dogchase3sSOD},
/* s_dogchase3s  */	{true,SPR_DOG_W3_1SOD,3,NULL,NULL,s_dogchase4SOD},
/* s_dogchase4  */	{true,SPR_DOG_W4_1SOD,8,T_DogChase,NULL,s_dogchase1SOD},

/* s_dogdie1	 */	{false,SPR_DOG_DIE_1SOD,15,NULL,A_DeathScream,s_dogdie2SOD},
/* s_dogdie2	 */	{false,SPR_DOG_DIE_2SOD,15,NULL,NULL,s_dogdie3SOD},
/* s_dogdie3	 */	{false,SPR_DOG_DIE_3SOD,15,NULL,NULL,s_dogdeadSOD},
/* s_dogdead	 */	{false,SPR_DOG_DEADSOD,15,NULL,NULL,s_dogdeadSOD},

/* s_ofcstand */	{true,SPR_OFC_S_1SOD,0,T_Stand,NULL,s_ofcstandSOD},

/* s_ofcpath1  */	{true,SPR_OFC_W1_1SOD,20,T_Path,NULL,s_ofcpath1sSOD},
/* s_ofcpath1s  */	{true,SPR_OFC_W1_1SOD,5,NULL,NULL,s_ofcpath2SOD},
/* s_ofcpath2  */	{true,SPR_OFC_W2_1SOD,15,T_Path,NULL,s_ofcpath3SOD},
/* s_ofcpath3  */	{true,SPR_OFC_W3_1SOD,20,T_Path,NULL,s_ofcpath3sSOD},
/* s_ofcpath3s  */	{true,SPR_OFC_W3_1SOD,5,NULL,NULL,s_ofcpath4SOD},
/* s_ofcpath4  */	{true,SPR_OFC_W4_1SOD,15,T_Path,NULL,s_ofcpath1SOD},

/* s_ofcpain  */	{2,SPR_OFC_PAIN_1SOD,10,NULL,NULL,s_ofcchase1SOD},
/* s_ofcpain1  */	{2,SPR_OFC_PAIN_2SOD,10,NULL,NULL,s_ofcchase1SOD},

/* s_ofcshoot1  */	{false,SPR_OFC_SHOOT1SOD,6,NULL,NULL,s_ofcshoot2SOD},
/* s_ofcshoot2  */	{false,SPR_OFC_SHOOT2SOD,20,NULL,T_Shoot,s_ofcshoot3SOD},
/* s_ofcshoot3  */	{false,SPR_OFC_SHOOT3SOD,10,NULL,NULL,s_ofcchase1SOD},

/* s_ofcchase1  */	{true,SPR_OFC_W1_1SOD,10,T_Chase,NULL,s_ofcchase1sSOD},
/* s_ofcchase1s  */	{true,SPR_OFC_W1_1SOD,3,NULL,NULL,s_ofcchase2SOD},
/* s_ofcchase2  */	{true,SPR_OFC_W2_1SOD,8,T_Chase,NULL,s_ofcchase3SOD},
/* s_ofcchase3  */	{true,SPR_OFC_W3_1SOD,10,T_Chase,NULL,s_ofcchase3sSOD},
/* s_ofcchase3s  */	{true,SPR_OFC_W3_1SOD,3,NULL,NULL,s_ofcchase4SOD},
/* s_ofcchase4  */	{true,SPR_OFC_W4_1SOD,8,T_Chase,NULL,s_ofcchase1SOD},

/* s_ofcdie1	 */	{false,SPR_OFC_DIE_1SOD,11,NULL,A_DeathScream,s_ofcdie2SOD},
/* s_ofcdie2	 */	{false,SPR_OFC_DIE_2SOD,11,NULL,NULL,s_ofcdie3SOD},
/* s_ofcdie3	 */	{false,SPR_OFC_DIE_3SOD,11,NULL,NULL,s_ofcdie4SOD},
/* s_ofcdie4	 */	{false,SPR_OFC_DIE_4SOD,11,NULL,NULL,s_ofcdie5SOD},
/* s_ofcdie5	 */	{false,SPR_OFC_DEADSOD,0,NULL,NULL,s_ofcdie5SOD},


/* s_mutstand */	{true,SPR_MUT_S_1SOD,0,T_Stand,NULL,s_mutstandSOD},

/* s_mutpath1  */	{true,SPR_MUT_W1_1SOD,20,T_Path,NULL,s_mutpath1sSOD},
/* s_mutpath1s  */	{true,SPR_MUT_W1_1SOD,5,NULL,NULL,s_mutpath2SOD},
/* s_mutpath2  */	{true,SPR_MUT_W2_1SOD,15,T_Path,NULL,s_mutpath3SOD},
/* s_mutpath3  */	{true,SPR_MUT_W3_1SOD,20,T_Path,NULL,s_mutpath3sSOD},
/* s_mutpath3s  */	{true,SPR_MUT_W3_1SOD,5,NULL,NULL,s_mutpath4SOD},
/* s_mutpath4  */	{true,SPR_MUT_W4_1SOD,15,T_Path,NULL,s_mutpath1SOD},

/* s_mutpain  */	{2,SPR_MUT_PAIN_1SOD,10,NULL,NULL,s_mutchase1SOD},
/* s_mutpain1  */	{2,SPR_MUT_PAIN_2SOD,10,NULL,NULL,s_mutchase1SOD},

/* s_mutshoot1  */	{false,SPR_MUT_SHOOT1SOD,6,NULL,T_Shoot,s_mutshoot2SOD},
/* s_mutshoot2  */	{false,SPR_MUT_SHOOT2SOD,20,NULL,NULL,s_mutshoot3SOD},
/* s_mutshoot3  */	{false,SPR_MUT_SHOOT3SOD,10,NULL,T_Shoot,s_mutshoot4SOD},
/* s_mutshoot4  */	{false,SPR_MUT_SHOOT4SOD,20,NULL,NULL,s_mutchase1SOD},

/* s_mutchase1  */	{true,SPR_MUT_W1_1SOD,10,T_Chase,NULL,s_mutchase1sSOD},
/* s_mutchase1s  */	{true,SPR_MUT_W1_1SOD,3,NULL,NULL,s_mutchase2SOD},
/* s_mutchase2  */	{true,SPR_MUT_W2_1SOD,8,T_Chase,NULL,s_mutchase3SOD},
/* s_mutchase3  */	{true,SPR_MUT_W3_1SOD,10,T_Chase,NULL,s_mutchase3sSOD},
/* s_mutchase3s  */	{true,SPR_MUT_W3_1SOD,3,NULL,NULL,s_mutchase4SOD},
/* s_mutchase4  */	{true,SPR_MUT_W4_1SOD,8,T_Chase,NULL,s_mutchase1SOD},

/* s_mutdie1	 */	{false,SPR_MUT_DIE_1SOD,7,NULL,A_DeathScream,s_mutdie2SOD},
/* s_mutdie2	 */	{false,SPR_MUT_DIE_2SOD,7,NULL,NULL,s_mutdie3SOD},
/* s_mutdie3	 */	{false,SPR_MUT_DIE_3SOD,7,NULL,NULL,s_mutdie4SOD},
/* s_mutdie4	 */	{false,SPR_MUT_DIE_4SOD,7,NULL,NULL,s_mutdie5SOD},
/* s_mutdie5	 */	{false,SPR_MUT_DEADSOD,0,NULL,NULL,s_mutdie5SOD},


/* s_ssstand */	{true,SPR_SS_S_1SOD,0,T_Stand,NULL,s_ssstandSOD},

/* s_sspath1  */	{true,SPR_SS_W1_1SOD,20,T_Path,NULL,s_sspath1sSOD},
/* s_sspath1s  */	{true,SPR_SS_W1_1SOD,5,NULL,NULL,s_sspath2SOD},
/* s_sspath2  */	{true,SPR_SS_W2_1SOD,15,T_Path,NULL,s_sspath3SOD},
/* s_sspath3  */	{true,SPR_SS_W3_1SOD,20,T_Path,NULL,s_sspath3sSOD},
/* s_sspath3s  */	{true,SPR_SS_W3_1SOD,5,NULL,NULL,s_sspath4SOD},
/* s_sspath4  */	{true,SPR_SS_W4_1SOD,15,T_Path,NULL,s_sspath1SOD},

/* s_sspain 	 */	{2,SPR_SS_PAIN_1SOD,10,NULL,NULL,s_sschase1SOD},
/* s_sspain1  */	{2,SPR_SS_PAIN_2SOD,10,NULL,NULL,s_sschase1SOD},

/* s_ssshoot1  */	{false,SPR_SS_SHOOT1SOD,20,NULL,NULL,s_ssshoot2SOD},
/* s_ssshoot2  */	{false,SPR_SS_SHOOT2SOD,20,NULL,T_Shoot,s_ssshoot3SOD},
/* s_ssshoot3  */	{false,SPR_SS_SHOOT3SOD,10,NULL,NULL,s_ssshoot4SOD},
/* s_ssshoot4  */	{false,SPR_SS_SHOOT2SOD,10,NULL,T_Shoot,s_ssshoot5SOD},
/* s_ssshoot5  */	{false,SPR_SS_SHOOT3SOD,10,NULL,NULL,s_ssshoot6SOD},
/* s_ssshoot6  */	{false,SPR_SS_SHOOT2SOD,10,NULL,T_Shoot,s_ssshoot7SOD},
/* s_ssshoot7   */	{false,SPR_SS_SHOOT3SOD,10,NULL,NULL,s_ssshoot8SOD},
/* s_ssshoot8   */	{false,SPR_SS_SHOOT2SOD,10,NULL,T_Shoot,s_ssshoot9SOD},
/* s_ssshoot9   */	{false,SPR_SS_SHOOT3SOD,10,NULL,NULL,s_sschase1SOD},

/* s_sschase1  */	{true,SPR_SS_W1_1SOD,10,T_Chase,NULL,s_sschase1sSOD},
/* s_sschase1s  */	{true,SPR_SS_W1_1SOD,3,NULL,NULL,s_sschase2SOD},
/* s_sschase2  */	{true,SPR_SS_W2_1SOD,8,T_Chase,NULL,s_sschase3SOD},
/* s_sschase3  */	{true,SPR_SS_W3_1SOD,10,T_Chase,NULL,s_sschase3sSOD},
/* s_sschase3s  */	{true,SPR_SS_W3_1SOD,3,NULL,NULL,s_sschase4SOD},
/* s_sschase4  */	{true,SPR_SS_W4_1SOD,8,T_Chase,NULL,s_sschase1SOD},

/* s_ssdie1	 */	{false,SPR_SS_DIE_1SOD,15,NULL,A_DeathScream,s_ssdie2SOD},
/* s_ssdie2	 */	{false,SPR_SS_DIE_2SOD,15,NULL,NULL,s_ssdie3SOD},
/* s_ssdie3	 */	{false,SPR_SS_DIE_3SOD,15,NULL,NULL,s_ssdie4SOD},
/* s_ssdie4	 */	{false,SPR_SS_DEADSOD,0,NULL,NULL,s_ssdie4SOD},


/* s_hrocket	  */	{true,SPR_HROCKET_1SOD,3,T_Projectile,A_Smoke,s_hrocket},
/* s_hsmoke1	  */	{false,SPR_HSMOKE_1SOD,3,NULL,NULL,s_hsmoke2},
/* s_hsmoke2	  */	{false,SPR_HSMOKE_2SOD,3,NULL,NULL,s_hsmoke3},
/* s_hsmoke3	  */	{false,SPR_HSMOKE_3SOD,3,NULL,NULL,s_hsmoke4},
/* s_hsmoke4	  */	{false,SPR_HSMOKE_4SOD,3,NULL,NULL, s_noneSOD},

/* s_hboom1	  */	{false,SPR_HBOOM_1SOD,6,NULL,NULL,s_hboom2},
/* s_hboom2	  */	{false,SPR_HBOOM_2SOD,6,NULL,NULL,s_hboom3},
/* s_hboom3	  */	{false,SPR_HBOOM_3SOD,6,NULL,NULL,s_noneSOD},

/* s_transstand */	{false,SPR_TRANS_W1SOD,0,T_Stand,NULL,s_transstand},

/* s_transchase1  */	{false,SPR_TRANS_W1SOD,10,T_Chase,NULL,s_transchase1s},
/* s_transchase1s */	{false,SPR_TRANS_W1SOD,3,NULL,NULL,s_transchase2},
/* s_transchase2  */	{false,SPR_TRANS_W2SOD,8,T_Chase,NULL,s_transchase3},
/* s_transchase3  */	{false,SPR_TRANS_W3SOD,10,T_Chase,NULL,s_transchase3s},
/* s_transchase3s */	{false,SPR_TRANS_W3SOD,3,NULL,NULL,s_transchase4},
/* s_transchase4  */	{false,SPR_TRANS_W4SOD,8,T_Chase,NULL,s_transchase1},

/* s_transdie0 */	{false,SPR_TRANS_W1SOD,1,NULL,A_DeathScream,s_transdie01},
/* s_transdie01 */	{false,SPR_TRANS_W1SOD,1,NULL,NULL,s_transdie1},
/* s_transdie1 */	{false,SPR_TRANS_DIE1SOD,15,NULL,NULL,s_transdie2},
/* s_transdie2 */	{false,SPR_TRANS_DIE2SOD,15,NULL,NULL,s_transdie3},
/* s_transdie3 */	{false,SPR_TRANS_DIE3SOD,15,NULL,NULL,s_transdie4},
/* s_transdie4 */	{false,SPR_TRANS_DEADSOD,0,NULL,NULL,s_transdie4},

/* s_transshoot1  */	{false,SPR_TRANS_SHOOT1SOD,30,NULL,NULL,s_transshoot2},
/* s_transshoot2  */	{false,SPR_TRANS_SHOOT2SOD,10,NULL,T_Shoot,s_transshoot3},
/* s_transshoot3  */	{false,SPR_TRANS_SHOOT3SOD,10,NULL,T_Shoot,s_transshoot4},
/* s_transshoot4  */	{false,SPR_TRANS_SHOOT2SOD,10,NULL,T_Shoot,s_transshoot5},
/* s_transshoot5  */	{false,SPR_TRANS_SHOOT3SOD,10,NULL,T_Shoot,s_transshoot6},
/* s_transshoot6  */	{false,SPR_TRANS_SHOOT2SOD,10,NULL,T_Shoot,s_transshoot7},
/* s_transshoot7  */	{false,SPR_TRANS_SHOOT3SOD,10,NULL,T_Shoot,s_transshoot8},
/* s_transshoot8  */	{false,SPR_TRANS_SHOOT1SOD,10,NULL,NULL,s_transchase1},


/* s_uberstand */	{false,SPR_UBER_W1SOD,0,T_Stand,NULL,s_uberstand},

/* s_uberchase1  */	{false,SPR_UBER_W1SOD,10,T_Chase,NULL,s_uberchase1s},
/* s_uberchase1s */	{false,SPR_UBER_W1SOD,3,NULL,NULL,s_uberchase2},
/* s_uberchase2  */	{false,SPR_UBER_W2SOD,8,T_Chase,NULL,s_uberchase3},
/* s_uberchase3  */	{false,SPR_UBER_W3SOD,10,T_Chase,NULL,s_uberchase3s},
/* s_uberchase3s */	{false,SPR_UBER_W3SOD,3,NULL,NULL,s_uberchase4},
/* s_uberchase4  */	{false,SPR_UBER_W4SOD,8,T_Chase,NULL,s_uberchase1},

/* s_uberdie0 */	{false,SPR_UBER_W1SOD,1,NULL,A_DeathScream,s_uberdie01},
/* s_uberdie01 */	{false,SPR_UBER_W1SOD,1,NULL,NULL,s_uberdie1},
/* s_uberdie1 */	{false,SPR_UBER_DIE1SOD,15,NULL,NULL,s_uberdie2},
/* s_uberdie2 */	{false,SPR_UBER_DIE2SOD,15,NULL,NULL,s_uberdie3},
/* s_uberdie3 */	{false,SPR_UBER_DIE3SOD,15,NULL,NULL,s_uberdie4},
/* s_uberdie4 */	{false,SPR_UBER_DIE4SOD,15,NULL,NULL,s_uberdie5},
/* s_uberdie5 */	{false,SPR_UBER_DEADSOD,0,NULL,NULL,s_uberdie5},

/* s_ubershoot1  */	{false,SPR_UBER_SHOOT1SOD,30,NULL,NULL,s_ubershoot2},
/* s_ubershoot2  */	{false,SPR_UBER_SHOOT2SOD,12,NULL,T_UShoot,s_ubershoot3},
/* s_ubershoot3  */	{false,SPR_UBER_SHOOT3SOD,12,NULL,T_UShoot,s_ubershoot4},
/* s_ubershoot4  */	{false,SPR_UBER_SHOOT4SOD,12,NULL,T_UShoot,s_ubershoot5},
/* s_ubershoot5  */	{false,SPR_UBER_SHOOT3SOD,12,NULL,T_UShoot,s_ubershoot6},
/* s_ubershoot6  */	{false,SPR_UBER_SHOOT2SOD,12,NULL,T_UShoot,s_ubershoot7},
/* s_ubershoot7  */	{false,SPR_UBER_SHOOT1SOD,12,NULL,NULL,s_uberchase1},


/* s_willstand */	{false,SPR_WILL_W1SOD,0,T_Stand,NULL,s_willstand},

/* s_willchase1  */	{false,SPR_WILL_W1SOD,10,T_Will,NULL,s_willchase1s},
/* s_willchase1s */	{false,SPR_WILL_W1SOD,3,NULL,NULL,s_willchase2},
/* s_willchase2  */	{false,SPR_WILL_W2SOD,8,T_Will,NULL,s_willchase3},
/* s_willchase3  */	{false,SPR_WILL_W3SOD,10,T_Will,NULL,s_willchase3s},
/* s_willchase3s */	{false,SPR_WILL_W3SOD,3,NULL,NULL,s_willchase4},
/* s_willchase4  */	{false,SPR_WILL_W4SOD,8,T_Will,NULL,s_willchase1},

/* s_willdie1 */	{false,SPR_WILL_W1SOD,1,NULL,A_DeathScream,s_willdie2},
/* s_willdie2 */	{false,SPR_WILL_W1SOD,10,NULL,NULL,s_willdie3},
/* s_willdie3 */	{false,SPR_WILL_DIE1SOD,10,NULL,NULL,s_willdie4},
/* s_willdie4 */	{false,SPR_WILL_DIE2SOD,10,NULL,NULL,s_willdie5},
/* s_willdie5 */	{false,SPR_WILL_DIE3SOD,10,NULL,NULL,s_willdie6},
/* s_willdie6 */	{false,SPR_WILL_DEADSOD,20,NULL,NULL,s_willdie6},

/* s_willshoot1  */	{false,SPR_WILL_SHOOT1SOD,30,NULL,NULL,s_willshoot2},
/* s_willshoot2  */	{false,SPR_WILL_SHOOT2SOD,10,NULL,T_Launch,s_willshoot3},
/* s_willshoot3  */	{false,SPR_WILL_SHOOT3SOD,10,NULL,T_Shoot,s_willshoot4},
/* s_willshoot4  */	{false,SPR_WILL_SHOOT4SOD,10,NULL,T_Shoot,s_willshoot5},
/* s_willshoot5  */	{false,SPR_WILL_SHOOT3SOD,10,NULL,T_Shoot,s_willshoot6},
/* s_willshoot6  */	{false,SPR_WILL_SHOOT4SOD,10,NULL,T_Shoot,s_willchase1},


/* s_deathstand */	{false,SPR_DEATH_W1SOD,0,T_Stand,NULL,s_deathstand},

/* s_deathchase1  */	{false,SPR_DEATH_W1SOD,10,T_Will,NULL,s_deathchase1s},
/* s_deathchase1s */	{false,SPR_DEATH_W1SOD,3,NULL,NULL,s_deathchase2},
/* s_deathchase2  */	{false,SPR_DEATH_W2SOD,8,T_Will,NULL,s_deathchase3},
/* s_deathchase3  */	{false,SPR_DEATH_W3SOD,10,T_Will,NULL,s_deathchase3s},
/* s_deathchase3s */	{false,SPR_DEATH_W3SOD,3,NULL,NULL,s_deathchase4},
/* s_deathchase4  */	{false,SPR_DEATH_W4SOD,8,T_Will,NULL,s_deathchase1},

/* s_deathdie1 */	{false,SPR_DEATH_W1SOD,1,NULL,A_DeathScream,s_deathdie2},
/* s_deathdie2 */	{false,SPR_DEATH_W1SOD,10,NULL,NULL,s_deathdie3},
/* s_deathdie3 */	{false,SPR_DEATH_DIE1SOD,10,NULL,NULL,s_deathdie4},
/* s_deathdie4 */	{false,SPR_DEATH_DIE2SOD,10,NULL,NULL,s_deathdie5},
/* s_deathdie5 */	{false,SPR_DEATH_DIE3SOD,10,NULL,NULL,s_deathdie6},
/* s_deathdie6 */	{false,SPR_DEATH_DIE4SOD,10,NULL,NULL,s_deathdie7},
/* s_deathdie7 */	{false,SPR_DEATH_DIE5SOD,10,NULL,NULL,s_deathdie8},
/* s_deathdie8 */	{false,SPR_DEATH_DIE6SOD,10,NULL,NULL,s_deathdie9},
/* s_deathdie9 */	{false,SPR_DEATH_DEADSOD,0,NULL,NULL,s_deathdie9},

/* s_deathshoot1  */	{false,SPR_DEATH_SHOOT1SOD,30,NULL,NULL,s_deathshoot2},
/* s_deathshoot2  */	{false,SPR_DEATH_SHOOT2SOD,10,NULL,T_Launch,s_deathshoot3},
/* s_deathshoot3  */	{false,SPR_DEATH_SHOOT4SOD,10,NULL,T_Shoot,s_deathshoot4},
/* s_deathshoot4  */	{false,SPR_DEATH_SHOOT3SOD,10,NULL,T_Launch,s_deathshoot5},
/* s_deathshoot5  */	{false,SPR_DEATH_SHOOT4SOD,10,NULL,T_Shoot,s_deathchase1},


/* s_angelstand */	{false,SPR_ANGEL_W1SOD,0,T_Stand,NULL,s_angelstand},

/* s_angelchase1  */	{false,SPR_ANGEL_W1SOD,10,T_Will,NULL,s_angelchase1s},
/* s_angelchase1s */	{false,SPR_ANGEL_W1SOD,3,NULL,NULL,s_angelchase2},
/* s_angelchase2  */	{false,SPR_ANGEL_W2SOD,8,T_Will,NULL,s_angelchase3},
/* s_angelchase3  */	{false,SPR_ANGEL_W3SOD,10,T_Will,NULL,s_angelchase3s},
/* s_angelchase3s */	{false,SPR_ANGEL_W3SOD,3,NULL,NULL,s_angelchase4},
/* s_angelchase4  */	{false,SPR_ANGEL_W4SOD,8,T_Will,NULL,s_angelchase1},

/* s_angeldie1 */	{false,SPR_ANGEL_W1SOD,1,NULL,A_DeathScream,s_angeldie11},
/* s_angeldie11 */	{false,SPR_ANGEL_W1SOD,1,NULL,NULL,s_angeldie2},
/* s_angeldie2 */	{false,SPR_ANGEL_DIE1SOD,10,NULL,A_SlurpieSOD,s_angeldie3},
/* s_angeldie3 */	{false,SPR_ANGEL_DIE2SOD,10,NULL,NULL,s_angeldie4},
/* s_angeldie4 */	{false,SPR_ANGEL_DIE3SOD,10,NULL,NULL,s_angeldie5},
/* s_angeldie5 */	{false,SPR_ANGEL_DIE4SOD,10,NULL,NULL,s_angeldie6},
/* s_angeldie6 */	{false,SPR_ANGEL_DIE5SOD,10,NULL,NULL,s_angeldie7},
/* s_angeldie7 */	{false,SPR_ANGEL_DIE6SOD,10,NULL,NULL,s_angeldie8},
/* s_angeldie8 */	{false,SPR_ANGEL_DIE7SOD,10,NULL,NULL,s_angeldie9},
/* s_angeldie9 */	{false,SPR_ANGEL_DEADSOD,130,NULL,A_Victory,s_angeldie9},

/* s_angelshoot1  */	{false,SPR_ANGEL_SHOOT1SOD,10,NULL,A_StartAttack,s_angelshoot2},
/* s_angelshoot2  */	{false,SPR_ANGEL_SHOOT2SOD,20,NULL,T_Launch,s_angelshoot3},
/* s_angelshoot3  */	{false,SPR_ANGEL_SHOOT1SOD,10,NULL,A_Relaunch,s_angelshoot2},

/* s_angeltired  */	{false,SPR_ANGEL_TIRED1SOD,40,NULL,A_Breathing,s_angeltired2},
/* s_angeltired2 */	{false,SPR_ANGEL_TIRED2SOD,40,NULL,NULL,s_angeltired3},
/* s_angeltired3 */	{false,SPR_ANGEL_TIRED1SOD,40,NULL,A_Breathing,s_angeltired4},
/* s_angeltired4 */	{false,SPR_ANGEL_TIRED2SOD,40,NULL,NULL,s_angeltired5},
/* s_angeltired5 */	{false,SPR_ANGEL_TIRED1SOD,40,NULL,A_Breathing,s_angeltired6},
/* s_angeltired6 */	{false,SPR_ANGEL_TIRED2SOD,40,NULL,NULL,s_angeltired7},
/* s_angeltired7 */	{false,SPR_ANGEL_TIRED1SOD,40,NULL,A_Breathing,s_angelchase1},

/* s_spark1  */	{false,SPR_SPARK1SOD,6,T_Projectile,NULL,s_spark2},
/* s_spark2  */	{false,SPR_SPARK2SOD,6,T_Projectile,NULL,s_spark3},
/* s_spark3  */	{false,SPR_SPARK3SOD,6,T_Projectile,NULL,s_spark4},
/* s_spark4  */	{false,SPR_SPARK4SOD,6,T_Projectile,NULL,s_spark1},


//
// spectre
//


/* s_spectrewait1 */	{false,SPR_SPECTRE_W1SOD,10,T_Stand,NULL,s_spectrewait2},
/* s_spectrewait2 */	{false,SPR_SPECTRE_W2SOD,10,T_Stand,NULL,s_spectrewait3},
/* s_spectrewait3 */	{false,SPR_SPECTRE_W3SOD,10,T_Stand,NULL,s_spectrewait4},
/* s_spectrewait4 */	{false,SPR_SPECTRE_W4SOD,10,T_Stand,NULL,s_spectrewait1},

/* s_spectrechase1 */	{false,SPR_SPECTRE_W1SOD,10,T_Ghosts,NULL,s_spectrechase2},
/* s_spectrechase2 */	{false,SPR_SPECTRE_W2SOD,10,T_Ghosts,NULL,s_spectrechase3},
/* s_spectrechase3 */	{false,SPR_SPECTRE_W3SOD,10,T_Ghosts,NULL,s_spectrechase4},
/* s_spectrechase4 */	{false,SPR_SPECTRE_W4SOD,10,T_Ghosts,NULL,s_spectrechase1},

/* s_spectredie1 */	{false,SPR_SPECTRE_F1SOD,10,NULL,NULL,s_spectredie2},
/* s_spectredie2 */	{false,SPR_SPECTRE_F2SOD,10,NULL,NULL,s_spectredie3},
/* s_spectredie3 */	{false,SPR_SPECTRE_F3SOD,10,NULL,NULL,s_spectredie4},
/* s_spectredie4 */	{false,SPR_SPECTRE_F4SOD,300,NULL,NULL,s_spectrewake},
/* s_spectrewake */	{false,SPR_SPECTRE_F4SOD,10,NULL,A_Dormant,s_spectrewake},


/* s_player */	{false,0,0,T_Player,NULL, s_noneSOD},
/* s_attack */	{false,0,0,T_Attack,NULL, s_noneSOD}

};
