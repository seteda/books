#include <stdio.h>

//void _start(int argc, char **argv)
int main(int argc, char **argv)  
{

	int i;

	printf("Argc=%d Argv=%p\n", argc, argv);

	for ( i = 0 ; i < argc ; i++ ) {
		printf("Main argv[%d]=%s\n", i, argv[i]);
	}

	printf("Hello World\n");
	exit( 0);
}

