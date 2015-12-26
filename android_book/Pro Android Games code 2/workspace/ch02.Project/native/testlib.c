#include <stdio.h>

extern int lib_main(int argc, char **argv);

//void _start(int argc, char **argv)
int main(int argc, char **argv)  
{

	int i;

	printf("Argc=%d Argv=%p\n", argc, argv);

	for ( i = 0 ; i < argc ; i++ ) {
		printf("Main argv[%d]=%s\n", i, argv[i]);
	}

	printf("Starting Lib main sub\n");
	lib_main(argc, argv) ;

	return 0;
}

