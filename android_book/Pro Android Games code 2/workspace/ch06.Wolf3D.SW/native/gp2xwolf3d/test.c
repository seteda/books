
#include <stdio.h>

void _start(int argc, char **argv) {
	int i;
	int myargc = 4;

	// wolf, wolfsw, sodemo, sod, sodm2, sodm3
	char * myargv[] = {"wolf3d", "wolfsw", "basedir" , "/data/wolf/"};

	for ( i = 0 ; i < myargc; i++ ) printf("argv[%d]=%s\n", i, myargv[i]);

	wolf_main(myargc, myargv);
	exit(0);
}

