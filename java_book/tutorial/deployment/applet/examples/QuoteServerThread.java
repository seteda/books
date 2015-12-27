/*
 * Copyright (c) 1995, 2009, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

/**
 * Java(TM) SE 6 version.
 */

import java.io.*;
import java.net.*;
import java.util.*;

class QuoteServerThread extends Thread {
    private DatagramSocket socket = null;
    BufferedReader qfs = null;
    private boolean moreQuotes = true;
    private int bufLength = 256;

    QuoteServerThread() {
        super("QuoteServer");
        try {
            socket = new DatagramSocket();
            System.out.println("QuoteServer listening on port: " + socket.getLocalPort());
        } catch (java.io.IOException e) {
            System.err.println("Could not create datagram socket.");
        }
        this.openInputFile();
    }

    public void run() {
        if (socket == null)
            return;

        while (moreQuotes) {
            try {
                byte[] buf = new byte[bufLength];
                DatagramPacket packet;
                InetAddress address;
                int port;
                String dString = null;

                    // receive request
                packet = new DatagramPacket(buf, bufLength);
                socket.receive(packet);
                address = packet.getAddress();
                port = packet.getPort();

                    // send response
                if (qfs == null)
                    dString = new Date().toString();
                else
                    dString = getNextQuote();
                buf = dString.getBytes();
                packet = new DatagramPacket(buf, buf.length, address, port);                
                socket.send(packet);
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
                moreQuotes = false;
                e.printStackTrace();
            }
        }
        socket.close();
    }

    private void openInputFile() {
        try {
            qfs = new BufferedReader(new FileReader("one-liners.txt"));
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Could not open quote file. Serving time instead.");
        }
    }
    private String getNextQuote() {
        String returnValue = null;
        try {
            if ((returnValue = qfs.readLine()) == null) {
                qfs.close();
                moreQuotes = false;
                returnValue = "No more quotes. Goodbye.";

            }           
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }

}
