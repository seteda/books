/* Emacs style mode select   -*- C++ -*-
 *-----------------------------------------------------------------------------
 *
 *
 *  PrBoom: a Doom port merged with LxDoom and LSDLDoom
 *  based on BOOM, a modified and improved DOOM engine
 *  Copyright (C) 1999 by
 *  id Software, Chi Hoang, Lee Killough, Jim Flynn, Rand Phares, Ty Halderman
 *  Copyright (C) 1999-2000 by
 *  Jess Haas, Nicolas Kalkhof, Colin Phipps, Florian Schulze
 *  Copyright 2005, 2006 by
 *  Florian Schulze, Colin Phipps, Neil Stevens, Andrey Budko
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 *  02111-1307, USA.
 *
 * DESCRIPTION:
 *  Low level UDP network interface. This is shared between the server
 *  and client, with SERVER defined for the former to select some extra
 *  functions. Handles socket creation, and packet send and receive.
 *
 *-----------------------------------------------------------------------------*/

#ifdef HAVE_CONFIG_H
# include "config.h"
#endif
#ifdef HAVE_NETINET_IN_H
# include <netinet/in.h>
#endif
#include <stdlib.h>
#include <errno.h>
#ifdef HAVE_UNISTD_H
#include <unistd.h>
#endif
#include <stdio.h>
#include <fcntl.h>
#include <string.h>

#ifdef HAVE_NET
/*
#include "SDL.h"
#include "SDL_net.h"
*/
#include "protocol.h"
#include "i_network.h"
#include "lprintf.h"
//#include "doomstat.h"


/*************************************************************
 * NET STUFF
 *************************************************************/
#include <arpa/inet.h>
#include <sys/socket.h>
#include "d_net.h"

UDPpacket *SDLNet_AllocPacket(int size) 
{
	UDPpacket * this = (UDPpacket*) malloc(sizeof(UDPpacket));
	this->data = (uint8_t *) malloc(size * sizeof(uint8_t));
	this->len = size;
	this->status =0;
	printf("SDLNet_AllocPacket size=%d\n", size);
	return this;
}

void SDLNet_FreePacket(UDPpacket *packet)
{
	free(packet->data);
	free(packet);
}

UDP_SOCKET SDLNet_UDP_Open(uint16_t port)
{
	UDP_SOCKET sock;
	sock = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);

	if ( sock == -1 )
		perror("Socket Error");
	else
		printf("SDLNet_UDP_Open port=%d, sock desc=%d\n", port, sock);

	return sock;
}

void SDLNet_UDP_Close(UDP_SOCKET sock) 
{
	close(sock);//Close Socket descriptor
}

int SDLNet_ResolveHost(IPaddress *address, char *host, uint16_t port)
{
	struct sockaddr_in sin;
	int rc = inet_pton(AF_INET, host, &sin.sin_addr);

	address->host = sin.sin_addr.s_addr; // inet_addr(host);
	address->port = port;

	printf("SDLNet_ResolveHost Host=%s (%d) port=%d, rc=%d\n"
		, host, address->host, port, rc);

	return (rc > 0 ) ? 0 : -1;
}

int SDLNet_UDP_Bind(UDP_SOCKET sock, int channel, IPaddress *address)
{
	struct sockaddr_in sin;
	sin.sin_family = AF_INET;
	sin.sin_port = htons(address->port);
	sin.sin_addr.s_addr = address->host;

	// 0 == success, -1 == error
	int rc = bind(sock, (struct sockaddr *)&sin, sizeof(sin) );

	printf("SDLNet_UDP_Bind Sock Descriptor=%d, channel=%d, IP Host=%d, IP Port=%d, rc=%d\n"
		, sock, channel, address->host, address->port, rc);

	return rc != -1 ? sock : -1 ;
}

void SDLNet_UDP_Unbind(UDPsocket sock, int channel)
{
	close(sock);
}

static void DumpPacket(UDPpacket *packet)
{
	int i = 0;
	printf("--Packet len=%d\n", packet->len);
	for (i = 0; i < packet->len; i++) printf("%d ", packet->data[i]);
	printf("\n--End Packet\n");
}

int SDLNet_UDP_Send(UDPsocket sock, int channel, UDPpacket *packet)
{
	DumpPacket(packet);

	ssize_t sent = send(sock, packet->data, packet->len, 0);

	printf("SDLNet_UDP_Send Sock Descriptor=%d, Data=%p, Len=%d, Sent=%ld\n"
		, sock, packet->data, packet->len, sent);
	return sent;
}

int SDLNet_UDP_Recv(UDPsocket sock, UDPpacket *packet)
{
	ssize_t len = recv(sock, packet->data, packet->len, 0);
	printf("SDLNet_UDP_Recv Sock=%d, Data=%p, Len=%d, Received=%ld\n"
		, sock, packet->data, packet->len, len);
	return len; 
}
/*************************************************************
 * NET STUFF
 *************************************************************/


/* cph -
 * Each client will either use the IPv4 socket or the IPv6 socket
 * Each server will use whichever or both that are available
 */
UDP_CHANNEL sentfrom;

IPaddress sentfrom_addr;

UDP_SOCKET udp_socket;

/* Statistics */
size_t sentbytes, recvdbytes;

UDP_PACKET *udp_packet;

/* I_ShutdownNetwork
 *
 * Shutdown the network code
 */
void I_ShutdownNetwork(void)
{
        SDLNet_FreePacket(udp_packet);
/*
        SDLNet_Quit();
*/
}

/* I_InitNetwork
 *
 * Sets up the network code
 */
void I_InitNetwork(void)
{
/*
  SDLNet_Init();
*/
  atexit(I_ShutdownNetwork);
  udp_packet = SDLNet_AllocPacket(10000);
}

UDP_PACKET *I_AllocPacket(int size)
{
  return (SDLNet_AllocPacket(size));
}

void I_FreePacket(UDP_PACKET *packet)
{
  SDLNet_FreePacket(packet);
}


/* cph - I_WaitForPacket - use select(2) via SDL_net's interface
 * No more I_uSleep loop kludge */

void I_WaitForPacket(int ms)
{
	unsigned long micros = ms * 1000;
	printf("I_WaitForPacket micros=%ld\n", micros);
	usleep(micros);
	printf("I_WaitForPacket Done Waiting\n");
/*
  SDLNet_SocketSet ss = SDLNet_AllocSocketSet(1);
  SDLNet_UDP_AddSocket(ss, udp_socket);
  SDLNet_CheckSockets(ss,ms);
  SDLNet_FreeSocketSet(ss);
#if (defined _WIN32 && !defined PRBOOM_SERVER)
  I_UpdateConsole();
#endif
*/
}

/* I_ConnectToServer
 *
 * Connect to a server
 */
IPaddress serverIP;

int I_ConnectToServer(const char *serv)
{

  char server[500], *p;
  uint16_t port;

  // Split serv into address and port 
  if (strlen(serv)>500) return 0;
  strcpy(server,serv);
  p = strchr(server, ':');
  if(p)
  {
    *p++ = '\0';
    port = atoi(p);
  }
  else
    port = 5030; // Default server port 

  SDLNet_ResolveHost(&serverIP, server, port);

  if ( serverIP.host == INADDR_NONE )
    return -1;

  if (SDLNet_UDP_Bind(udp_socket, 0, &serverIP) == -1)
    return -1;

  return 0;
}

/* I_Disconnect
 *
 * Disconnect from server
 */
void I_Disconnect(void)
{
/*
  int i;
  UDP_PACKET *packet;
  packet_header_t *pdata = (packet_header_t *)packet->data;
  packet = I_AllocPacket(sizeof(packet_header_t) + 1);

  packet->data[sizeof(packet_header_t)] = consoleplayer;
  pdata->type = PKT_QUIT; pdata->tic = gametic;

  for (i=0; i<4; i++) {
    I_SendPacket(packet);
    I_uSleep(10000);
  }
  I_FreePacket(packet); */

  SDLNet_UDP_Unbind(udp_socket, 0);
}

/*
 * I_Socket
 *
 * Sets the given socket non-blocking, binds to the given port, or first
 * available if none is given
 */
UDP_SOCKET I_Socket( uint16_t port) // Uint16 port)
{
  printf("I_Socket port=%d\n", port);

  if (port)
    return (SDLNet_UDP_Open(port));

  else {
    UDP_SOCKET sock;
    port = IPPORT_RESERVED;
    while( (sock = SDLNet_UDP_Open(port)) == -1 ) // Vladimir NULL )
      port++;
    return sock;
  }
}

void I_CloseSocket(UDP_SOCKET sock)
{
  SDLNet_UDP_Close(sock);
}

UDP_CHANNEL I_RegisterPlayer(IPaddress *ipaddr)
{
  static int freechannel;
  return(SDLNet_UDP_Bind(udp_socket, freechannel++, ipaddr));
}

void I_UnRegisterPlayer(UDP_CHANNEL channel)
{
  SDLNet_UDP_Unbind(udp_socket, channel);
}

/*
 * ChecksumPacket
 *
 * Returns the checksum of a given network packet
 */
static byte ChecksumPacket(const packet_header_t* buffer, size_t len)
{
  const byte* p = (const void*)buffer;
  byte sum = 0;


  if (len==0)
    return 0;

  while (p++, --len)
    sum += *p;

  return sum;
}

size_t I_GetPacket(packet_header_t* buffer, size_t buflen)
{

  int checksum;
  size_t len;
  int status;

  status = SDLNet_UDP_Recv(udp_socket, udp_packet);
  len = udp_packet->len;

  if (buflen<len)
    len=buflen;
  if ( (status!=0) && (len>0) )
    memcpy(buffer, udp_packet->data, len);

  sentfrom=udp_packet->channel;

#ifndef SDL_NET_UDP_PACKET_SRC
  sentfrom_addr=udp_packet->address;
#else
  sentfrom_addr=udp_packet->src; // cph - allow for old SDL_net library 
#endif

  checksum=buffer->checksum;
  buffer->checksum=0;

  if ( (status!=0) && (len>0)) {
    byte psum = ChecksumPacket(buffer, udp_packet->len);

    printf("recvlen = %u, stolen = %u, csum = %u, psum = %u\n",
  	udp_packet->len, len, checksum, psum); 

    if (psum == checksum) return len;
  }

  return 0;
}

void DumpPacketHeader(packet_header_t * packet)
{
	printf("Packet Header checksum=%d, type=%d, timestamp=%d\n"
		, packet->checksum, packet->type , packet->tic ); 

}

void I_SendPacket(packet_header_t* packet, size_t len)
{
  printf("I_SendPacket Packet=%p, len=%d\n", packet, len);

  packet->checksum = ChecksumPacket(packet, len);

  DumpPacketHeader (packet);

  memcpy(udp_packet->data, packet, udp_packet->len = len);
  SDLNet_UDP_Send(udp_socket, 0, udp_packet);

}

void I_SendPacketTo(packet_header_t* packet, size_t len, UDP_CHANNEL *to)
{
  printf("I_SendPacketTo Packet=%p, len=%d, Dest=%p\n", packet, len, to);

  packet->checksum = ChecksumPacket(packet, len);
  memcpy(udp_packet->data, packet, udp_packet->len = len);
  SDLNet_UDP_Send(udp_socket, *to, udp_packet);
}

void I_PrintAddress(FILE* fp, UDP_CHANNEL *addr)
{
/*
  char *addy;
  Uint16 port;
  IPaddress *address;

  address = SDLNet_UDP_GetPeerAddress(udp_socket, player);

//FIXME: if it cant resolv it may freeze up
  addy = SDLNet_ResolveIP(address);
  port = address->port;

  if(addy != NULL)
      fprintf(fp, "%s:%d", addy, port);
  else
    fprintf(fp, "Error");
*/
}

#endif /* HAVE_NET */
