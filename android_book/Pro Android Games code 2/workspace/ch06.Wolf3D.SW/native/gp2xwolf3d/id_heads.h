#ifndef __ID_HEADS_H__
#define __ID_HEADS_H__

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <time.h>
#include <inttypes.h>
//#include <glob.h>
#include <ctype.h>
#include <math.h>

#ifdef __cplusplus
typedef bool boolean;
#else
typedef enum { false, true } boolean;
#endif

#include "version.h"

/* ------------------------------------------------------------------------ */

#include "gfxv_wl6.h"
#include "gfxv_wl1.h"
#include "audiowl6.h"

#include "gfxv_sod.h"
#include "gfxv_sdm.h"
#include "audiosod.h"

/* ---------------- */

typedef uint8_t		byte;
typedef uint16_t	word;
typedef uint32_t	longword;
typedef uint32_t	dword;

typedef long fixed;

typedef void * memptr;

#include "misc.h"

#include "vi_comm.h"
#include "sd_comm.h"

#include "id_ca.h"
#include "id_vh.h"
#include "id_us.h"

extern const byte gamepalWL6[];
extern const byte gamepalSOD[];

int MS_CheckParm(const char *string);
void Quit(const char *error);

#define TickBase	70	/* 70Hz per tick */

#undef PI
#define PI		3.1415926535897932384626433832795028841971693993751058209749445920

#define	MAXTICS		10
#define DEMOTICS	4

extern int tics;

#define mapwidth	64
#define mapheight	64

#endif
