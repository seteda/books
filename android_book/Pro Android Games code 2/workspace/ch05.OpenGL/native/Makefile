#############################################
# Android Makefile for CH05
#############################################

# Android source: Contains GL C headers
SYS_DEV		= /home/user/mydroid

# compiler script
CC 		= agcc

# Linker script
LINKER 		= ald

# C files
MAIN_OBJS 	= cuberenderer.o cube.o  # triangle.o 

# Library name, dynamic executable
LIB		= libgltest_jni.so
DYN		= gl-dyn

# Compilation flags & libs
INCLUDES 	= -I$(SYS_DEV)/frameworks/base/opengl/include
CFLAGS		= -DNORMALUNIX -DLINUX -DANDROID
LIBS 		= -lGLES_CM -lui

# Compile the main library (ibgltest_jni.so)
lib:  $(MAIN_OBJS) # jni  
	@echo
	$(LINKER) -shared -o $(LIB) $(MAIN_OBJS) $(LIBS)
	@echo
#	@echo Copying $(LIB) to assets folder.
#	cp $(LIB) ../assets
	@echo
	@echo Done. Out file is $(LIB)
	@echo

# Create JNI headers
jni:
	@echo "Creating JNI C headers..."
	javah -jni -classpath ../bin -d include opengl.jni.Natives


# Test dynamic exe
dyn:
	$(CC) -c test.c $(INCLUDES)
	$(LINKER) -o $(DYN) test.o  -lgltest_jni -L.
	@echo
	@echo Done. Out file is $(DYN)
	@echo
	
.c.o:
	@echo
	$(CC) -Wall -O2 -fpic -c $(CFLAGS) $(INCLUDES) $<


# Deploy lib to device /data folder	
deploy-test:
	@echo "Deploying $(LIB) to /data"
	adb push $(LIB) /data
	
clean:
	rm -f *.o 
               
