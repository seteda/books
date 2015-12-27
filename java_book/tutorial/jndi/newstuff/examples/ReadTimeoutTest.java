/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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
 */

import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
import javax.naming.*;
import javax.naming.directory.*;
import java.util.Hashtable;

public class ReadTimeoutTest {

    public static void main(String[] args) throws Exception {

	boolean passed = false;

	// Set up the environment for creating the initial context
	Hashtable env = new Hashtable(11);
	env.put(Context.INITIAL_CONTEXT_FACTORY,
	    "com.sun.jndi.ldap.LdapCtxFactory");
	env.put("com.sun.jndi.ldap.read.timeout", "1000");
        env.put(Context.PROVIDER_URL, "ldap://localhost:2001");

	Server s = new Server();

	try {

	    // start the server
	    s.start();

	    // Create initial context
	    DirContext ctx = new InitialDirContext(env);
	    System.out.println("LDAP Client: Connected to the Server");

	    // Close the context when we're done
	    ctx.close();
	} catch (NamingException e) {
	    e.printStackTrace();
	}
	s.interrupt();
    }

    static class Server extends Thread {

        static int serverPort = 2001;

	Server() {
	}

	public void run() {
	    try {
		ServerSocket serverSock = new ServerSocket(serverPort);
            	Socket socket = serverSock.accept();
            	System.out.println("Server: Connection accepted");

            	BufferedInputStream bin = new BufferedInputStream(socket.
                                getInputStream());
            	while (true) {
                    bin.read();
            	}
	    } catch (IOException e) {
		// ignore
	    }
       }
    }
}
