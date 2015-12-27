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

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PersistentEcho {
    public static void main (String[] args) {
        String argString = "";
        boolean notProperty = true;

        // Are there arguments? 
        // If so retrieve them.
        if (args.length > 0) {
            for (String arg: args) {
                argString += arg + " ";
            }
            argString = argString.trim();
        }
        // No arguments, is there
        // an environment variable?
        // If so, //retrieve it.
        else if ((argString = System.getenv("PERSISTENTECHO")) != null) {}
        // No environment variable
        // either. Retrieve property value.
        else {
            notProperty = false;
            // Set argString to null.
            // If it's still null after
            // we exit the try block,
            // we've failed to retrieve
            // the property value.
            argString = null;
            FileInputStream fileInputStream = null;
            try {
                fileInputStream =
                    new FileInputStream("PersistentEcho.txt");
                Properties inProperties
                    = new Properties();
                inProperties.load(fileInputStream);
                argString = inProperties.getProperty("argString");
            } catch (IOException e) {
                System.err.println("Can't read property file.");
                System.exit(1);
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch(IOException e) {};
                }
            }
        }
        if (argString == null) {
            System.err.println("Couldn't find argString property");
            System.exit(1);
        }

        // Somehow, we got the
        // value. Echo it already!
        System.out.println(argString);

        // If we didn't retrieve the
        // value from the property,
        // save it //in the property.
        if (notProperty) {
            Properties outProperties =
                new Properties();
            outProperties.setProperty("argString",
                                      argString);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream =
                    new FileOutputStream("PersistentEcho.txt");
                outProperties.store(fileOutputStream,
                        "PersistentEcho properties");
            } catch (IOException e) {}
            finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch(IOException e) {};
                }
            }
        }
    }
}

