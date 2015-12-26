#ifndef __WL_ACT3_H__
#define __WL_ACT3_H__

typedef enum {
	s_noneWL6,
	
	s_boom1WL6,
	s_boom2WL6,
	s_boom3WL6,

	s_rocketWL6,

	s_smoke1WL6,
	s_smoke2WL6,
	s_smoke3WL6,
	s_smoke4WL6,

	s_grdstandWL6,

	s_grdpath1WL6,
	s_grdpath1sWL6,
	s_grdpath2WL6,
	s_grdpath3WL6,
	s_grdpath3sWL6,
	s_grdpath4WL6,

	s_grdpainWL6,
	s_grdpain1WL6,

	s_grdshoot1WL6,
	s_grdshoot2WL6,
	s_grdshoot3WL6,

	s_grdchase1WL6,
	s_grdchase1sWL6,
	s_grdchase2WL6,
	s_grdchase3WL6,
	s_grdchase3sWL6,
	s_grdchase4WL6,

	s_grddie1WL6,
	s_grddie2WL6,
	s_grddie3WL6,
	s_grddie4WL6,


	s_dogpath1WL6,
	s_dogpath1sWL6,
	s_dogpath2WL6,
	s_dogpath3WL6,
	s_dogpath3sWL6,
	s_dogpath4WL6,

	s_dogjump1WL6,
	s_dogjump2WL6,
	s_dogjump3WL6,
	s_dogjump4WL6,
	s_dogjump5WL6,

	s_dogchase1WL6,
	s_dogchase1sWL6,
	s_dogchase2WL6,
	s_dogchase3WL6,
	s_dogchase3sWL6,
	s_dogchase4WL6,

	s_dogdie1WL6,
	s_dogdie2WL6,
	s_dogdie3WL6,
	s_dogdeadWL6,

//
// officers
//

	s_ofcstandWL6,

	s_ofcpath1WL6,
	s_ofcpath1sWL6,
	s_ofcpath2WL6,
	s_ofcpath3WL6,
	s_ofcpath3sWL6,
	s_ofcpath4WL6,

	s_ofcpainWL6,
	s_ofcpain1WL6,

	s_ofcshoot1WL6,
	s_ofcshoot2WL6,
	s_ofcshoot3WL6,

	s_ofcchase1WL6,
	s_ofcchase1sWL6,
	s_ofcchase2WL6,
	s_ofcchase3WL6,
	s_ofcchase3sWL6,
	s_ofcchase4WL6,

	s_ofcdie1WL6,
	s_ofcdie2WL6,
	s_ofcdie3WL6,
	s_ofcdie4WL6,
	s_ofcdie5WL6,



//
// mutant
//

	s_mutstandWL6,

	s_mutpath1WL6,
	s_mutpath1sWL6,
	s_mutpath2WL6,
	s_mutpath3WL6,
	s_mutpath3sWL6,
	s_mutpath4WL6,

	s_mutpainWL6,
	s_mutpain1WL6,

	s_mutshoot1WL6,
	s_mutshoot2WL6,
	s_mutshoot3WL6,
	s_mutshoot4WL6,

	s_mutchase1WL6,
	s_mutchase1sWL6,
	s_mutchase2WL6,
	s_mutchase3WL6,
	s_mutchase3sWL6,
	s_mutchase4WL6,

	s_mutdie1WL6,
	s_mutdie2WL6,
	s_mutdie3WL6,
	s_mutdie4WL6,
	s_mutdie5WL6,


//
// SS
//

	s_ssstandWL6,

	s_sspath1WL6,
	s_sspath1sWL6,
	s_sspath2WL6,
	s_sspath3WL6,
	s_sspath3sWL6,
	s_sspath4WL6,

	s_sspainWL6,
	s_sspain1WL6,

	s_ssshoot1WL6,
	s_ssshoot2WL6,
	s_ssshoot3WL6,
	s_ssshoot4WL6,
	s_ssshoot5WL6,
	s_ssshoot6WL6,
	s_ssshoot7WL6,
	s_ssshoot8WL6,
	s_ssshoot9WL6,

	s_sschase1WL6,
	s_sschase1sWL6,
	s_sschase2WL6,
	s_sschase3WL6,
	s_sschase3sWL6,
	s_sschase4WL6,

	s_ssdie1WL6,
	s_ssdie2WL6,
	s_ssdie3WL6,
	s_ssdie4WL6,

	s_blinkychase1,
	s_blinkychase2,
	s_inkychase1,
	s_inkychase2,
	s_pinkychase1,
	s_pinkychase2,
	s_clydechase1,
	s_clydechase2,

//
// hans
//
	s_bossstand,

	s_bosschase1,
	s_bosschase1s,
	s_bosschase2,
	s_bosschase3,
	s_bosschase3s,
	s_bosschase4,

	s_bossdie1,
	s_bossdie2,
	s_bossdie3,
	s_bossdie4,

	s_bossshoot1,
	s_bossshoot2,
	s_bossshoot3,
	s_bossshoot4,
	s_bossshoot5,
	s_bossshoot6,
	s_bossshoot7,
	s_bossshoot8,

//
// gretel
//
	s_gretelstand,

	s_gretelchase1,
	s_gretelchase1s,
	s_gretelchase2,
	s_gretelchase3,
	s_gretelchase3s,
	s_gretelchase4,

	s_greteldie1,
	s_greteldie2,
	s_greteldie3,
	s_greteldie4,

	s_gretelshoot1,
	s_gretelshoot2,
	s_gretelshoot3,
	s_gretelshoot4,
	s_gretelshoot5,
	s_gretelshoot6,
	s_gretelshoot7,
	s_gretelshoot8,

//
// schabb
//
	s_schabbstand,

	s_schabbchase1,
	s_schabbchase1s,
	s_schabbchase2,
	s_schabbchase3,
	s_schabbchase3s,
	s_schabbchase4,

	s_schabbdeathcam,

	s_schabbdie1,
	s_schabbdie2,
	s_schabbdie3,
	s_schabbdie4,
	s_schabbdie5,
	s_schabbdie6,

	s_schabbshoot1,
	s_schabbshoot2,

	s_needle1,
	s_needle2,
	s_needle3,
	s_needle4,


//
// gift
//
	s_giftstand,

	s_giftchase1,
	s_giftchase1s,
	s_giftchase2,
	s_giftchase3,
	s_giftchase3s,
	s_giftchase4,

	s_giftdeathcam,

	s_giftdie1,
	s_giftdie2,
	s_giftdie3,
	s_giftdie4,
	s_giftdie5,
	s_giftdie6,

	s_giftshoot1,
	s_giftshoot2,

//
// fat
//
	s_fatstand,

	s_fatchase1,
	s_fatchase1s,
	s_fatchase2,
	s_fatchase3,
	s_fatchase3s,
	s_fatchase4,

	s_fatdeathcam,

	s_fatdie1,
	s_fatdie2,
	s_fatdie3,
	s_fatdie4,
	s_fatdie5,
	s_fatdie6,

	s_fatshoot1,
	s_fatshoot2,
	s_fatshoot3,
	s_fatshoot4,
	s_fatshoot5,
	s_fatshoot6,


//
// fake
//
	s_fakestand,

	s_fakechase1,
	s_fakechase1s,
	s_fakechase2,
	s_fakechase3,
	s_fakechase3s,
	s_fakechase4,

	s_fakedie1,
	s_fakedie2,
	s_fakedie3,
	s_fakedie4,
	s_fakedie5,
	s_fakedie6,

	s_fakeshoot1,
	s_fakeshoot2,
	s_fakeshoot3,
	s_fakeshoot4,
	s_fakeshoot5,
	s_fakeshoot6,
	s_fakeshoot7,
	s_fakeshoot8,
	s_fakeshoot9,

	s_fire1,
	s_fire2,


//
// hitler
//

	s_mechastand,

	s_mechachase1,
	s_mechachase1s,
	s_mechachase2,
	s_mechachase3,
	s_mechachase3s,
	s_mechachase4,

	s_mechadie1,
	s_mechadie2,
	s_mechadie3,
	s_mechadie4,

	s_mechashoot1,
	s_mechashoot2,
	s_mechashoot3,
	s_mechashoot4,
	s_mechashoot5,
	s_mechashoot6,


	s_hitlerchase1,
	s_hitlerchase1s,
	s_hitlerchase2,
	s_hitlerchase3,
	s_hitlerchase3s,
	s_hitlerchase4,

	s_hitlerdeathcam,

	s_hitlerdie1,
	s_hitlerdie2,
	s_hitlerdie3,
	s_hitlerdie4,
	s_hitlerdie5,
	s_hitlerdie6,
	s_hitlerdie7,
	s_hitlerdie8,
	s_hitlerdie9,
	s_hitlerdie10,

	s_hitlershoot1,
	s_hitlershoot2,
	s_hitlershoot3,
	s_hitlershoot4,
	s_hitlershoot5,
	s_hitlershoot6,

//
// BJ victory
//

	s_bjrun1,
	s_bjrun1s,
	s_bjrun2,
	s_bjrun3,
	s_bjrun3s,
	s_bjrun4,

	s_bjjump1,
	s_bjjump2,
	s_bjjump3,
	s_bjjump4,

	s_deathcam,

	s_playerWL6,
	s_attackWL6,

	MAXSTATESWL6
} stateenumWL6;

typedef enum {
	s_noneSOD,
	
	s_boom1SOD,
	s_boom2SOD,
	s_boom3SOD,

	s_rocketSOD,

	s_smoke1SOD,
	s_smoke2SOD,
	s_smoke3SOD,
	s_smoke4SOD,

	s_grdstandSOD,

	s_grdpath1SOD,
	s_grdpath1sSOD,
	s_grdpath2SOD,
	s_grdpath3SOD,
	s_grdpath3sSOD,
	s_grdpath4SOD,

	s_grdpainSOD,
	s_grdpain1SOD,

	s_grdshoot1SOD,
	s_grdshoot2SOD,
	s_grdshoot3SOD,

	s_grdchase1SOD,
	s_grdchase1sSOD,
	s_grdchase2SOD,
	s_grdchase3SOD,
	s_grdchase3sSOD,
	s_grdchase4SOD,

	s_grddie1SOD,
	s_grddie2SOD,
	s_grddie3SOD,
	s_grddie4SOD,


	s_dogpath1SOD,
	s_dogpath1sSOD,
	s_dogpath2SOD,
	s_dogpath3SOD,
	s_dogpath3sSOD,
	s_dogpath4SOD,

	s_dogjump1SOD,
	s_dogjump2SOD,
	s_dogjump3SOD,
	s_dogjump4SOD,
	s_dogjump5SOD,

	s_dogchase1SOD,
	s_dogchase1sSOD,
	s_dogchase2SOD,
	s_dogchase3SOD,
	s_dogchase3sSOD,
	s_dogchase4SOD,

	s_dogdie1SOD,
	s_dogdie2SOD,
	s_dogdie3SOD,
	s_dogdeadSOD,

//
// officers
//

	s_ofcstandSOD,

	s_ofcpath1SOD,
	s_ofcpath1sSOD,
	s_ofcpath2SOD,
	s_ofcpath3SOD,
	s_ofcpath3sSOD,
	s_ofcpath4SOD,

	s_ofcpainSOD,
	s_ofcpain1SOD,

	s_ofcshoot1SOD,
	s_ofcshoot2SOD,
	s_ofcshoot3SOD,

	s_ofcchase1SOD,
	s_ofcchase1sSOD,
	s_ofcchase2SOD,
	s_ofcchase3SOD,
	s_ofcchase3sSOD,
	s_ofcchase4SOD,

	s_ofcdie1SOD,
	s_ofcdie2SOD,
	s_ofcdie3SOD,
	s_ofcdie4SOD,
	s_ofcdie5SOD,



//
// mutant
//

	s_mutstandSOD,

	s_mutpath1SOD,
	s_mutpath1sSOD,
	s_mutpath2SOD,
	s_mutpath3SOD,
	s_mutpath3sSOD,
	s_mutpath4SOD,

	s_mutpainSOD,
	s_mutpain1SOD,

	s_mutshoot1SOD,
	s_mutshoot2SOD,
	s_mutshoot3SOD,
	s_mutshoot4SOD,

	s_mutchase1SOD,
	s_mutchase1sSOD,
	s_mutchase2SOD,
	s_mutchase3SOD,
	s_mutchase3sSOD,
	s_mutchase4SOD,

	s_mutdie1SOD,
	s_mutdie2SOD,
	s_mutdie3SOD,
	s_mutdie4SOD,
	s_mutdie5SOD,


//
// SS
//

	s_ssstandSOD,

	s_sspath1SOD,
	s_sspath1sSOD,
	s_sspath2SOD,
	s_sspath3SOD,
	s_sspath3sSOD,
	s_sspath4SOD,

	s_sspainSOD,
	s_sspain1SOD,

	s_ssshoot1SOD,
	s_ssshoot2SOD,
	s_ssshoot3SOD,
	s_ssshoot4SOD,
	s_ssshoot5SOD,
	s_ssshoot6SOD,
	s_ssshoot7SOD,
	s_ssshoot8SOD,
	s_ssshoot9SOD,

	s_sschase1SOD,
	s_sschase1sSOD,
	s_sschase2SOD,
	s_sschase3SOD,
	s_sschase3sSOD,
	s_sschase4SOD,

	s_ssdie1SOD,
	s_ssdie2SOD,
	s_ssdie3SOD,
	s_ssdie4SOD,

	s_hrocket,
	s_hsmoke1,
	s_hsmoke2,
	s_hsmoke3,
	s_hsmoke4,

	s_hboom1,
	s_hboom2,
	s_hboom3,

//
// trans
//
	s_transstand,

	s_transchase1,
	s_transchase1s,
	s_transchase2,
	s_transchase3,
	s_transchase3s,
	s_transchase4,

	s_transdie0,
	s_transdie01,
	s_transdie1,
	s_transdie2,
	s_transdie3,
	s_transdie4,

	s_transshoot1,
	s_transshoot2,
	s_transshoot3,
	s_transshoot4,
	s_transshoot5,
	s_transshoot6,
	s_transshoot7,
	s_transshoot8,

	s_uberstand,

	s_uberchase1,
	s_uberchase1s,
	s_uberchase2,
	s_uberchase3,
	s_uberchase3s,
	s_uberchase4,

	s_uberdie0,
	s_uberdie01,
	s_uberdie1,
	s_uberdie2,
	s_uberdie3,
	s_uberdie4,
	s_uberdie5,

	s_ubershoot1,
	s_ubershoot2,
	s_ubershoot3,
	s_ubershoot4,
	s_ubershoot5,
	s_ubershoot6,
	s_ubershoot7,

//
// will
//
	s_willstand,

	s_willchase1,
	s_willchase1s,
	s_willchase2,
	s_willchase3,
	s_willchase3s,
	s_willchase4,

	s_willdie1,
	s_willdie2,
	s_willdie3,
	s_willdie4,
	s_willdie5,
	s_willdie6,

	s_willshoot1,
	s_willshoot2,
	s_willshoot3,
	s_willshoot4,
	s_willshoot5,
	s_willshoot6,

//
// death
//
	s_deathstand,

	s_deathchase1,
	s_deathchase1s,
	s_deathchase2,
	s_deathchase3,
	s_deathchase3s,
	s_deathchase4,

	s_deathdie1,
	s_deathdie2,
	s_deathdie3,
	s_deathdie4,
	s_deathdie5,
	s_deathdie6,
	s_deathdie7,
	s_deathdie8,
	s_deathdie9,

	s_deathshoot1,
	s_deathshoot2,
	s_deathshoot3,
	s_deathshoot4,
	s_deathshoot5,

//
// angel
//

	s_angelstand,

	s_angelchase1,
	s_angelchase1s,
	s_angelchase2,
	s_angelchase3,
	s_angelchase3s,
	s_angelchase4,

	s_angeldie1,
	s_angeldie11,
	s_angeldie2,
	s_angeldie3,
	s_angeldie4,
	s_angeldie5,
	s_angeldie6,
	s_angeldie7,
	s_angeldie8,
	s_angeldie9,

	s_angelshoot1,
	s_angelshoot2,
	s_angelshoot3,

	s_angeltired,
	s_angeltired2,
	s_angeltired3,
	s_angeltired4,
	s_angeltired5,
	s_angeltired6,
	s_angeltired7,

	s_spark1,
	s_spark2,
	s_spark3,
	s_spark4,


	s_spectrewait1,
	s_spectrewait2,
	s_spectrewait3,
	s_spectrewait4,

	s_spectrechase1,
	s_spectrechase2,
	s_spectrechase3,
	s_spectrechase4,

	s_spectredie1,
	s_spectredie2,
	s_spectredie3,
	s_spectredie4,

	s_spectrewake,

	s_playerSOD,
	s_attackSOD,

	MAXSTATESSOD
} stateenumSOD;

extern statetype gamestatesWL6[MAXSTATESWL6];
extern statetype gamestatesSOD[MAXSTATESSOD];

#endif
