#include <sys/stat.h>
#include <sys/time.h>
#include <unistd.h>
#include <fcntl.h>
              
#include "wl_def.h"

#ifndef O_BINARY
#define O_BINARY 0
#endif

static struct timeval t0;
static unsigned long tc0;

void set_TimeCount(unsigned long t)
{
	tc0 = t;
	
	gettimeofday(&t0, NULL);
}

unsigned long get_TimeCount(void)
{
	struct timeval t1;
	long secs, usecs;
	unsigned long tc;
	
	gettimeofday(&t1, NULL);

	secs = t1.tv_sec - t0.tv_sec;
	usecs = t1.tv_usec - t0.tv_usec;

	if (usecs < 0) {
		usecs += 1000000;
		secs--;
	}

	tc = tc0 + secs * 70 + (usecs * 70) / 1000000;
		
	return tc;
}

long filelength(int handle)
{
	long current = lseek(handle, 0, SEEK_CUR);
	long length  = lseek(handle, 0, SEEK_END);
	lseek(handle, current, SEEK_SET);
//printf("filelength h=%d, len=%ld\n", handle, length);
	return length;
}

char *strlwr(char *s)
{
	char *p = s;
	
	while (*p) {
		*p = tolower(*p);
		p++;
	}
	
	return s;
}
	
char *itoa(int value, char *string, int radix)
{
	(void) radix;
	
	/* wolf3d only uses radix 10 */
	sprintf(string, "%d", value);
	return string;
}

char *ltoa(long value, char *string, int radix)
{
	(void) radix;
	
	/* wolf3d only uses radix 10 */
	sprintf(string, "%ld", value);
	return string;
}

char *ultoa(unsigned long value, char *string, int radix)
{
	(void) radix;
	
	/* wolf3d only uses radix 10 */
	sprintf(string, "%lu", value);
	return string;
}

/* from Dan Olson */
static void put_dos2ansi(byte attrib)
{
	byte fore,back,blink=0,intens=0;
	
	fore = attrib&15;	// bits 0-3
	back = attrib&112; // bits 4-6
       	blink = attrib&128; // bit 7
	
	// Fix background, blink is either on or off.
	back = back>>4;

	// Fix foreground
	if (fore > 7) {
		intens = 1;
		fore-=8;
	}

	// Convert fore/back
	switch (fore) {
		case 0: // BLACK
			fore=30;
			break;
		case 1: // BLUE
			fore=34;
			break;
		case 2: // GREEN
			fore=32;
			break;
		case 3: // CYAN
			fore=36;
			break;
		case 4: // RED
			fore=31;
			break;
		case 5: // Magenta
			fore=35;
			break;
		case 6: // BROWN(yellow)
			fore=33;
			break;
		case 7: //GRAy
			fore=37;
			break;
	}
			
	switch (back) {
		case 0: // BLACK
			back=40;
			break;
		case 1: // BLUE
			back=44;
			break;
		case 2: // GREEN
			back=42;
			break;
		case 3: // CYAN
			back=46;
			break;
		case 4: // RED
			back=41;
			break;
		case 5: // Magenta
			back=45;
			break;
		case 6: // BROWN(yellow)
			back=43;
			break;
		case 7: //GRAy
			back=47;
			break;
	}
	if (blink)
		printf ("%c[%d;5;%dm%c[%dm", 27, intens, fore, 27, back);
	else
		printf ("%c[%d;25;%dm%c[%dm", 27, intens, fore, 27, back);
}

void DisplayTextSplash(const byte *text, int l)
{
	int i, x;
	
	//printf("%02X %02X %02X %02X\n", text[0], text[1], text[2], text[3]);
	text += 4;
	//printf("%02X %02X %02X %02X\n", text[0], text[1], text[2], text[3]);
	text += 2;
	
	for (x = 0; x < l; x++) {
		for (i = 0; i < 160; i += 2) {
			put_dos2ansi(text[160*x+i+2]);
			if (text[160*x+i+1] && text[160*x+i+1] != 160)
				printf("%c", text[160*x+i+1]);
			else
				printf(" ");
		}
		printf("%c[m", 27);
		printf("\n");
	}
}

/* ** */

uint16_t SwapInt16L(uint16_t i)
{
#if BYTE_ORDER == BIG_ENDIAN
	return ((uint16_t)i >> 8) | ((uint16_t)i << 8);
#else
	return i;
#endif
}

uint32_t SwapInt32L(uint32_t i)
{
#if BYTE_ORDER == BIG_ENDIAN
	return	((uint32_t)(i & 0xFF000000) >> 24) | 
		((uint32_t)(i & 0x00FF0000) >>  8) |
		((uint32_t)(i & 0x0000FF00) <<  8) | 
		((uint32_t)(i & 0x000000FF) << 24);
#else
	return i;
#endif
}

/* ** */
// Base directory
extern char * basedir;

int OpenWrite(const char *fn)
{
	int fp;
	char buffer [64];

	sprintf (buffer, "%s%s", basedir, fn);

//jni_printf("OpenWrite %s", buffer);
	
	//fp = open(fn, O_CREAT|O_WRONLY|O_TRUNC|O_BINARY, S_IRUSR|S_IWUSR|S_IRGRP|S_IWGRP|S_IROTH|S_IWOTH);
	fp = open(buffer, O_CREAT|O_WRONLY|O_TRUNC|O_BINARY, S_IRUSR|S_IWUSR|S_IRGRP|S_IWGRP|S_IROTH|S_IWOTH);
	
	return fp;
}

int OpenWriteAppend(const char *fn)
{
	int fp;
	char buffer [64];

	sprintf (buffer, "%s%s", basedir, fn);

//jni_printf("OpenWriteAppend  %s", buffer);
	
	//fp = open(fn, O_CREAT|O_WRONLY|O_BINARY, S_IRUSR|S_IWUSR|S_IRGRP|S_IWGRP|S_IROTH|S_IWOTH);
	fp = open(buffer, O_CREAT|O_WRONLY|O_BINARY, S_IRUSR|S_IWUSR|S_IRGRP|S_IWGRP|S_IROTH|S_IWOTH);
	
	return fp;
}

void CloseWrite(int fp)
{
	close(fp);
	sync();
}

int WriteSeek(int fp, int offset, int whence)
{
	return lseek(fp, offset, whence);
}

int WritePos(int fp)
{
	return lseek(fp, 0, SEEK_CUR);
}

int WriteInt8(int fp, int8_t d)
{
	return write(fp, &d, 1);
}

int WriteInt16(int fp, int16_t d)
{
	int16_t b = SwapInt16L(d);
	
	return write(fp, &b, 2) / 2;
}

int WriteInt32(int fp, int32_t d)
{
	int32_t b = SwapInt32L(d);
	
	return write(fp, &b, 4) / 4;
}

int WriteBytes(int fp, const byte *d, int len)
{
	return write(fp, d, len);
}


int OpenRead(const char *fn)
{
	int fp;
	char buffer [64];

	sprintf (buffer, "%s%s", basedir, fn);

//jni_printf("OpenRead %s", buffer);

	// Vladimir fp = open(fn, O_RDONLY | O_BINARY);
	fp = open(buffer, O_RDONLY | O_BINARY);

	return fp;
}

void CloseRead(int fp)
{
	close(fp);
}

int ReadSeek(int fp, int offset, int whence)
{
	return lseek(fp, offset, whence);
}

int ReadLength(int fp)
{
	return filelength(fp);
}

int8_t ReadInt8(int fp)
{
	byte d[1];
	
	read(fp, d, 1);
	
	return d[0];
}

int16_t ReadInt16(int fp)
{
	byte d[2];
	
	read(fp, d, 2);
//printf("ReadInt16 fp=%d byte=%d\n", fp, (d[0]) | (d[1] << 8) );	
	return (d[0]) | (d[1] << 8);
}

int32_t ReadInt32(int fp)
{
	byte d[4];
	
	read(fp, d, 4);
//printf("ReadInt32 fp=%d byte=%d\n", fp, ((d[0]) | (d[1] << 8) | (d[2] << 16) | (d[3] << 24)) );	
	return (d[0]) | (d[1] << 8) | (d[2] << 16) | (d[3] << 24);
}

int ReadBytes(int fp, byte *d, int len)
{
	int rc = read(fp, d, len);
/*
int i;
for (i =0 ; i < len ; i++)
	printf("fp=%d byte[%d]=%d\n", fp, i, d[i]);
*/
	return rc;
}
