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

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;

/*
 * A custom socket factory used to override the default socket factory.
 * It prints out debugging information before using default Socket creation
 * methods. This class is required for the UseFactory example.
 */
public class CustomSocketFactory extends SocketFactory {

    public CustomSocketFactory() {
	System.out.println("[creating a custom socket factory]");
    }

    public static SocketFactory getDefault() {

	System.out.println("[acquiring the default socket factory]");
	return new CustomSocketFactory();
    }

    public Socket createSocket(String host, int port)
	throws IOException, UnknownHostException {

	System.out.println("[creating a custom socket (method 1)]");
	return new Socket(host, port);
    }

    public Socket createSocket(String host, int port, InetAddress localHost,
	int localPort) throws IOException, UnknownHostException {

	System.out.println("[creating a custom socket (method 2)]");
	return new Socket(host, port, localHost, localPort);
    }

    public Socket createSocket(InetAddress host, int port) throws IOException {

	System.out.println("[creating a custom socket (method 3)]");
	return new Socket(host, port);
    }

    public Socket createSocket(InetAddress address, int port,
	InetAddress localAddress, int localPort) throws IOException {

	System.out.println("[creating a custom socket (method 4)]");
	return new Socket(address, port, localAddress, localPort);
    }
}

