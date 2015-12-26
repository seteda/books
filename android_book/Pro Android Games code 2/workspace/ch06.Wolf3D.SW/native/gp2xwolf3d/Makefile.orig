CROSS_COMPILE = arm-linux-
SDL_BASE = arm-linux-
LDFLAGS = -static
COMPRESS = gpecomp


CC = $(CROSS_COMPILE)gcc
STRIP = $(CROSS_COMPILE)strip

#CFLAGS = -g -Wall
CFLAGS = `$(SDL_BASE)sdl-config --cflags` -O2 -Wall -ffast-math -funroll-loops
#CFLAGS = -g `$(SDL_BASE)sdl-config --cflags` -O2 -Wall -ffast-math
#CFLAGS = -Wall -O6 -fomit-frame-pointer -ffast-math -funroll-loops -march=pentiumpro
#CFLAGS = -g -Wall -W -pedantic -std=c99
#CFLAGS = -Os -Wall -pedantic
#CFLAGS = -Os -Wall -fomit-frame-pointer -ffast-math -march=pentiumpro
#CFLAGS=-O3 -xiMK -tpp6 -c99 -wp_ipo -g
OBJS = objs.o misc.o id_ca.o id_vh.o id_us.o \
	wl_act1.o wl_act2.o wl_act3.o wl_agent.o wl_game.o \
	wl_inter.o wl_menu.o wl_play.o wl_state.o wl_text.o wl_main.o \
	wl_debug.o vi_comm.o sd_comm.o
ROBJS = wl_draw.o
SOBJS = $(OBJS) $(ROBJS) vi_svga.o
XOBJS = $(OBJS) $(ROBJS) vi_xlib.o
DOBJS = $(OBJS) $(ROBJS) vi_sdl.o

#LDLIBS = -lm -wp_ipo
LDLIBS = -lm

# no sound
#OBJS += sd_null.o
# sound using OSS
OBJS += sd_oss.o fmopl.o
#CFLAGS += -D_REENTRANT
#LDLIBS += -lpthread

SLDLIBS = $(LDLIBS) -lvga
XLDLIBS = $(LDLIBS) -L/usr/X11R6/lib -lX11 -lXext
DLDLIBS = $(LDLIBS) `$(SDL_BASE)sdl-config --libs`

NASM = nasm

.SUFFIXES: .asm

#all:	swolf3d xwolf3d sdlwolf3d
all:	sdlwolf3d

$(SOBJS): version.h id_heads.h wl_def.h
$(XOBJS): version.h id_heads.h wl_def.h
$(DOBJS): version.h id_heads.h wl_def.h

.asm.o:
	$(NASM) -f elf -o $@ $<

swolf3d: $(SOBJS)
	$(CC) -o swolf3d $(SOBJS) $(SLDLIBS) 

xwolf3d: $(XOBJS)
	$(CC) -o xwolf3d $(XOBJS) $(XLDLIBS)

sdlwolf3d: $(DOBJS)
	$(CC) $(LDFLAGS) -o sdlwolf3d $(DOBJS) $(DLDLIBS)
	$(STRIP) sdlwolf3d
	$(COMPRESS) sdlwolf3d wolf3d.gpe

clean:
	rm -rf swolf3d xwolf3d sdlwolf3d *.o *.il

distclean: clean
	rm -rf *~ DEADJOE

forced: distclean all
	rm -rf *.o
