
#include <unistd.h>
#include <stdlib.h>
#include <unistd.h>
//#include <fcntl.h>
#include <stdio.h>

#include <GLES/gl.h>

#define FIXED_ONE 0x10000
#define one 1.0f

typedef unsigned char byte;

extern void jni_printf(char *format, ...);


#define VERTS 3

static float vbb [VERTS * 3];
static float tbb [VERTS * 2];
static short ibb [VERTS];

void Tri_init() 
{
	int i;
	// A unit-sided equalateral triangle centered on the origin.
	float coords[] = {
		// X, Y, Z
		-0.5f, -0.25f, 0,
		 0.5f, -0.25f, 0,
		 0.0f,  0.559016994f, 0
	};
	float textCoords[] = {
		// S, T
		-0.5f, -0.25f,
		 0.5f, -0.25f,
		 0.0f,  0.559016994f
	};

        for (i = 0; i < VERTS * 3; i++) {
        	vbb[i] = coords[i] * 4.0f;
        }

        for (i = 0; i < VERTS *2 ; i++) {
        	tbb[i]= textCoords[i] * 4.0f + 0.5f;
        }

        for(i = 0; i < VERTS; i++) {
            ibb[i] = (short) i;
        }
}

void Tri_draw() {
	glFrontFace(GL_CCW);
	glVertexPointer(3, GL_FLOAT, 0, vbb);
	//glEnable(GL_TEXTURE_2D);
	//glTexCoordPointer(2, GL_FLOAT, 0, tbb);
	glDrawElements(GL_TRIANGLE_STRIP, VERTS, GL_UNSIGNED_SHORT, ibb);
}

