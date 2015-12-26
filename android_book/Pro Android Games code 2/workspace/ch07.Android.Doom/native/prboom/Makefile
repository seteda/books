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

#
# Compiler
#
CC 	= $(CROSS_COMPILE)gcc
MACROS 	= -DNORMALUNIX -DLINUX -DHAVE_NET -DUSE_SDL_NET -DHAVE_CONFIG_H
FLAGS 	= -O2 -ffast-math -Wall -nostdinc

# Includes
BASE=$(SYS_DEV)/frameworks/base
LIBC=$(SYS_DEV)/bionic/libc
LIBM=$(SYS_DEV)/bionic/libm
TC=$(SYS_DEV)/prebuilt/linux-x86/toolchain/arm-eabi-4.2.1/lib/gcc/arm-eabi/4.2.1/install-tools
KERNEL = $(SYS_DEV)/kernel

INCLUDES = -I. -Iinclude \
	-I$(JAVA_HOME)/include \
	-I$(JAVA_HOME)/include/linux \
	-I$(LIBC)/include \
	-I$(LIBC)/arch-arm/include \
	-I$(LIBC)/kernel/arch-arm/include \
	-I$(LIBM)/include \
	-I$(BASE)/include \
	-I$(TC)/include \
	-I$(KERNEL)/include -I$(KERNEL)/arch/arm/include \
	-I$(KERNEL)/arch/arm/mach-ebsa110/include \
	-I$(SYS_DEV)/system/core/include
 
# Libraries
LIB_PATHS = -rpath /system/lib -L. \
	-rpath $(SYS_ROOT)/lib -L$(SYS_ROOT)/lib \
	-L$(JAVA_HOME)/jre/lib/i386

#
# Linker
#
LINKER 		= $(CROSS_COMPILE)ld
LFLAGS		= --dynamic-linker=/system/bin/linker -nostdlib
LIBS 		= -lc -lm 


MAIN_OBJS 	= \
 am_map.o    m_cheat.o     p_lights.o  p_user.o    sounds.o \
 hu_lib.o       md5.o         p_map.o     r_bsp.o     s_sound.o \
 d_deh.o     hu_stuff.o     m_menu.o      p_maputl.o  r_data.o    st_lib.o \
 d_items.o   i_main.o       m_misc.o      p_mobj.o    r_demo.o    st_stuff.o \
 d_main.o    info.o             p_plats.o   r_draw.o    tables.o \
 doomdef.o   i_sound.o      m_random.o    p_pspr.o    r_filter.o  version.o \
 doomstat.o  i_system.o     p_ceilng.o    p_saveg.o   r_fps.o     v_video.o \
 p_checksum.o  p_setup.o   r_main.o    wi_stuff.o \
 dstrings.o   p_doors.o     p_sight.o   r_patch.o   w_memcache.o \
 f_finale.o  jni_doom.o     p_enemy.o     p_spec.o    r_plane.o   w_mmap.o \
 f_wipe.o    lprintf.o      p_floor.o     p_switch.o  r_segs.o    w_wad.o \
 g_game.o    m_argv.o       p_genlin.o    p_telept.o  r_sky.o     z_bmalloc.o \
 m_bbox.o       p_inter.o     p_tick.o    r_things.o  z_zone.o \
 d_client.o  i_video.o i_network.o d_server.o

                  
EXE		= ../bin/doom-static
LIB		= ../bin/libdoom_jni.so
DYN		= ../bin/doom-cli
SRV		= ../bin/doom-srv

all: lib

jni:
	@echo "Creating JNI C headers..."
	javah -jni -classpath ../../bin -d include doom.jni.Natives

lib:  $(MAIN_OBJS) # jni  
	@echo
	$(LINKER) -shared $(LFLAGS) $(LIB_PATHS) $(LIBS) -o $(LIB) $(MAIN_OBJS) $(LIBRARIES)
	@echo
	@echo Done. Out file is $(LIB)
	@echo

exe: $(MAIN_OBJS) 
	@echo
	$(CC) -c ../bin/cli.c $(INCLUDES)
	$(CC) -static -o $(EXE) test.o $(MAIN_OBJS)  $(LIBS)
	@echo
	@echo Done. Out file is $(EXE)
	@echo


dyncli:
	$(CC) -c ../bin/cli.c -o ../bin/cli.o $(MACROS) $(INCLUDES)
	$(LINKER) $(LFLAGS) $(LIB_PATHS) $(LIBS) -ldoom_jni -L../bin -o $(DYN) ../bin/cli.o $(LIBRARIES)
	@echo
	@echo Done. Out file is $(DYN)
	@echo

dynsrv:
	$(CC) -c ../bin/srv.c -o ../bin/srv.o $(MACROS) $(INCLUDES)
	$(LINKER) $(LFLAGS) $(LIB_PATHS) $(LIBS) -ldoom_jni -L../bin -o $(SRV) ../bin/srv.o $(LIBRARIES)
	@echo
	@echo Done. Out file is $(SRV)
	@echo
	
.c.o:
	@echo
	$(CC) -fpic -c $(FLAGS) $(MACROS) $(INCLUDES) $<

deploy-exe:
	adb push $(EXE) /data
	
deploy-lib: lib
	adb push $(LIB) /data/data/org.doom/files

# Deploy lib to a test folder in the emulator

deploy-test: lib dyncli dynsrv
	adb push $(LIB) /data
	adb push $(DYN) /data
	adb push $(SRV) /data

# Compress lib in assets folder

zip: lib
	gzip -c ../bin/libdoom_jni.so > ../../assets/lib.gzip
	
clean:
	rm -f *.o $(EXE)
               
