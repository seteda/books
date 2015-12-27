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
import java.net.URL;
import java.net.MalformedURLException;
import java.lang.reflect.InvocationTargetException;

/**
 * Runs a jar application from any url. Usage is 'java JarRunner url [args..]'
 * where url is the url of the jar file and args is optional arguments to
 * be passed to the application's main method.
 */
public class JarRunner {
    public static void main(String[] args) {
        if (args.length < 1) {
            usage();
        }
        URL url = null;
        try {
            url = new URL(args[0]);
        } catch (MalformedURLException e) {
            fatal("Invalid URL: " + args[0]);
        }
        // Create the class loader for the application jar file
        JarClassLoader cl = new JarClassLoader(url);
        // Get the application's main class name
        String name = null;
        try {
            name = cl.getMainClassName();
        } catch (IOException e) {
            System.err.println("I/O error while loading JAR file:");
            e.printStackTrace();
            System.exit(1);
        }
        if (name == null) {
            fatal("Specified jar file does not contain a 'Main-Class'" +
                  " manifest attribute");
        }
        // Get arguments for the application
        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, newArgs.length);
        // Invoke application's main class
        try {
            cl.invokeClass(name, newArgs);
        } catch (ClassNotFoundException e) {
            fatal("Class not found: " + name);
        } catch (NoSuchMethodException e) {
            fatal("Class does not define a 'main' method: " + name);
        } catch (InvocationTargetException e) {
            e.getTargetException().printStackTrace();
            System.exit(1);
        }
    }

    private static void fatal(String s) {
        System.err.println(s);
        System.exit(1);
    }

    private static void usage() {
        fatal("Usage: java JarRunner url [args..]");
    }
}
