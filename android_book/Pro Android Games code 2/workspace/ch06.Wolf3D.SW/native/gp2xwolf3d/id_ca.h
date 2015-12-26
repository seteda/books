#ifndef __ID_CA_H__
#define __ID_CA_H__

/* ======================================================================== */

#define NUMMAPS		60
#define MAPPLANES	2

typedef	struct
{
	int planestart[3];
	int planelength[3];
	int width, height;
	char name[16];
} maptype;

/* ======================================================================== */

extern	int	mapon;

extern	word	*mapsegs[MAPPLANES];
extern	maptype	*mapheaderseg[NUMMAPS];
extern	byte	*audiosegsWL6[NUMSNDCHUNKSWL6];
extern	byte	*audiosegsSOD[NUMSNDCHUNKSSOD];
extern	byte	*grsegsWL1[NUMCHUNKSWL1];
extern	byte	*grsegsWL6[NUMCHUNKSWL6];
extern	byte	*grsegsSDM[NUMCHUNKSSDM];
extern	byte	*grsegsSOD[NUMCHUNKSSOD];

extern char extension[5];
extern char extension2[5];

/* ======================================================================== */

boolean CA_LoadFile(const char *filename, memptr *ptr);
boolean CA_WriteFile(const char *filename, const void *ptr, long length);

void CA_Startup();
void CA_Shutdown();

void CA_CacheAudioChunk(int chunk);
void CA_UnCacheAudioChunk(int chunk);
void CA_LoadAllSounds();

void CA_CacheMap(int mapnum);
void CA_CacheGrChunk(int chunk);
void CA_UnCacheGrChunk(int chunk);

/* ======================================================================= */

void MM_GetPtr(memptr *baseptr, unsigned long size);
void MM_FreePtr(memptr *baseptr);

void MM_SetPurge(memptr *baseptr, int purge);
void MM_SetLock(memptr *baseptr, boolean locked);
void MM_SortMem();

#define PMPageSize	4096

typedef	struct {
	int offset;	/* Offset of chunk into file */
	int length;	/* Length of the chunk */
	memptr addr;
} PageListStruct;

extern int ChunksInFile, PMSpriteStart, PMSoundStart;

extern PageListStruct *PMPages;

#define	PM_GetSoundPage(v)	PM_GetPage(PMSoundStart + (v))
#define	PM_GetSpritePage(v)	PM_GetPage(PMSpriteStart + (v))
memptr PM_GetPage(int pagenum);
#define	PM_FreeSoundPage(v)	PM_FreePage(PMSoundStart + (v))
#define	PM_FreeSpritePage(v)	PM_FreePage(PMSpriteStart + (v))
void PM_FreePage(int pagenum);

void PM_Startup();
void PM_Shutdown();

#endif
