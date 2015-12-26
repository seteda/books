#####################################
# Make file
#####################################

# Compiler and loader
CC = agcc
LD = ald

# flags
CFLAGS	= -Werror
MACROS	=
INCLUDES =

# static objects
OBJ = main.o

# shared library ob
LIBOBJ = lib.o

# test shared lib
TESTLIBOBJ = testlib.o

# default make target
all: testlib $(OBJ)
	@echo
	@echo "Linking..."
	$(LD) -static -o a.out $(OBJ)

# Build lib
lib: $(LIBOBJ)
	@echo
	@echo "Linking Shared library..."
	$(LD) -shared -o libch02.so $(LIBOBJ)
	@echo
	@echo "Copying Shared library to assest folder"
	cp libch02.so ../assets

# Build test program for lib
testlib: lib $(TESTLIBOBJ)
	@echo
	@echo "Linking Test for Shared library"
	$(LD) -o testlib $(TESTLIBOBJ) -L. -lch02

# Build JNI Headers (for lib)
jni:
	@echo "Creating JNI C headers..."
	javah -jni -classpath ../bin -d include jni.Natives

# compile
.c.o:
	@echo
	@echo "Compiling $<..."
	$(CC) -c $(FLAGS) $(MACROS) $(INCLUDES)  $<

# Cleanup
clean:
	rm *.o
	
#
# Push binary into device
#
pushbin:
	adb push a.out /data

# Push lib & test program to the device	
pushlib:
	adb push testlib /data
	adb push libch02.so /data
		
	