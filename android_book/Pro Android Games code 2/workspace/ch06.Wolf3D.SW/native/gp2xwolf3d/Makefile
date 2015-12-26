#############################################
# Android Makefile
#############################################

HOME		= /home/user
JAVA_HOME	= /usr/lib/jvm/java-6-sun

# Tool chain
TOOLCHAIN_ROOT	= $(HOME)/Desktop/android/arm-2008q3

# Target OS
SYS_ROOT	= $(HOME)/tmp/android/system
SYS_DEV		= $(HOME)/mydroid

CROSS_COMPILE	= $(TOOLCHAIN_ROOT)/bin/arm-none-linux-gnueabi-
LIBDIR		=$(TOOLCHAIN_ROOT)/lib/gcc/arm-none-linux-gnueabi/4.3.2

LIBRARIES	=$(LIBDIR)/libgcc.a

CC = $(CROSS_COMPILE)gcc
#CC = gcc

CFLAGS = -g -Wall -O2  -ffast-math -nostdinc #-DWMODE=0
#CFLAGS = -g -O2 -Wall -pedantic 
#CFLAGS = -Wall -O6 -fomit-frame-pointer -ffast-math -funroll-loops -mpentiumpro -mcpu=pentiumpro -march=pentiumpro
#CFLAGS = -g -Wall -pedantic -std=gnu99
#CFLAGS = -Os -Wall -pedantic
#CFLAGS = -Os -Wall -fomit-frame-pointer -ffast-math -mpentiumpro -mcpu=pentiumpro -march=pentiumpro

BASE=$(SYS_DEV)/frameworks/base
LIBC=$(SYS_DEV)/bionic/libc
LIBM=$(SYS_DEV)/bionic/libm
TC=$(SYS_DEV)/prebuilt/linux-x86/toolchain/arm-eabi-4.2.1/lib/gcc/arm-eabi/4.2.1/install-tools
KERNEL = $(SYS_DEV)/kernel

INCLUDES = -I$(LIBC)/include -I$(LIBC)/arch-arm/include \
	-I$(LIBC)/kernel/arch-arm/include \
	-I$(LIBM)/include -I$(BASE)/include -I$(TC)/include \
	-I$(KERNEL)/include -I$(KERNEL)/arch/arm/include

#
# Linker
#
LINKER 		= $(CROSS_COMPILE)ld
LFLAGS		= --dynamic-linker=/system/bin/linker -nostdlib

# Libraries
LIB_PATHS = -rpath /system/lib -L. \
	-rpath $(SYS_ROOT)/lib -L$(SYS_ROOT)/lib \
	-L$(JAVA_HOME)/jre/lib/i386

OBJS = objs.o misc.o id_ca.o id_vh.o id_us.o \
	wl_act1.o wl_act2.o wl_act3.o wl_agent.o wl_game.o \
	wl_inter.o wl_menu.o wl_play.o wl_state.o wl_text.o wl_main.o \
	wl_debug.o vi_comm.o sd_comm.o 
#glob.o

ROBJS = wl_draw.o
SOBJS = $(OBJS) $(ROBJS) vi_svga.o
XOBJS = $(OBJS) $(ROBJS) vi_xlib.o
DOBJS = $(OBJS) $(ROBJS) vi_sdl.o
VNOBJS = $(OBJS) $(ROBJS)  # vi_null.o
JNIOBJS = jni_wolf.o vi_null.o

LFLAGS = -lc -lm
ALFLAGS=--dynamic-linker=/system/bin/linker -nostdlib $(LFLAGS)

OBJS += sd_null.o
#OBJS += sd_oss.o fmopl.o
#CFLAGS += -D_REENTRANT
#LFLAGS += -lpthread

#CFLAGS += `sdl-config --cflags`

#-lpthread

SLFLAGS = $(LFLAGS) -lvga
XLFLAGS = $(LFLAGS) -L/usr/X11R6/lib -lX11 -lXext 
DLFLAGS = $(LFLAGS) `sdl-config --libs` -L/usr/X11R6/lib -lX11 -lXext 


NASM = nasm

.SUFFIXES: .asm .c

#all:	swolf3d xwolf3d sdlwolf3d
#all:	sdlwolf3d xwolf3d
all:	lib

$(SOBJS): version.h id_heads.h wl_def.h
$(XOBJS): version.h id_heads.h wl_def.h
$(DOBJS): version.h id_heads.h wl_def.h
$(VNOBJS): version.h id_heads.h wl_def.h

.asm.o:
	$(NASM) -f elf -o $@ $<

.c.o:
	@echo
	$(CC) -fpic -c $(CFLAGS) $(MACROS) $(INCLUDES) $<


swolf3d: $(SOBJS)
	$(CC) -o swolf3d $(SOBJS) $(SLFLAGS) 


xwolf3d: $(XOBJS)
	$(CC) -o wolfdata/xwolf3d $(XOBJS) $(XLFLAGS)

sdlwolf3d: $(DOBJS)
	$(CC) -o wolfdata/sdlwolf3d $(DOBJS) $(DLFLAGS)

##################################################
# Android
##################################################
OUT_DIR = ../bin
BIN_DIR = ../bin
JNI_LIBS = -L$(OUT_DIR) -lwolf_jni

exe: $(VNOBJS)
	$(CC) -c -nostdinc $(INCLUDES) test.c
	$(LINKER) $(ALFLAGS)  $(LIB_PATHS) $(LIBS) -o $(BIN_DIR)/armwolf3d test.o  $(JNI_LIBS) $(LIBRARIES)

##################################################
# JNI stuff
##################################################

jni:
	@echo "Creating JNI C headers..."
	javah -jni -classpath ../../bin -d include wolf.jni.Natives

jni_comp:
	$(CC) -fpic -c $(CFLAGS) $(MACROS) $(INCLUDES) -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux jni_wolf.c
	$(CC) -fpic -c $(CFLAGS) $(MACROS) $(INCLUDES) -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux vi_null.c


lib: $(VNOBJS) jni_comp
	$(LINKER) $(ALFLAGS) $(LIB_PATHS) $(LIBS) -shared -o $(OUT_DIR)/libwolf_jni.so $(VNOBJS) $(JNIOBJS) $(LIBRARIES)

deploy-exe:
	adb push $(BIN_DIR)/armwolf3d /data/wolf
	adb push $(OUT_DIR)/libwolf_jni.so /data/wolf

deploy-lib:
	adb push $(OUT_DIR)/libwolf_jni.so /data/data/game.wolf/files

clean:
	rm -rf *.o

forced: distclean all
	rm -rf *.o
