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
  * By default, this applet raises a security exception, unless
  *  you configure your policy to allow applets from its location
  *  to write to the file "writetest".
  */

import java.awt.*;
import java.io.*;
import java.lang.*;
import java.applet.*;

public class WriteFile extends Applet {
    String myFile = "writetest";
    File f = new File(myFile);
    DataOutputStream dos;

  public void init() {
    
    String osname = System.getProperty("os.name");
  }

  public void paint(Graphics g) {
	try {
  	  dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(myFile),128));
	  dos.writeChars("Cats can hypnotize you when you least expect it\n");
	  dos.flush();
	  g.drawString("Successfully wrote to the file named " + myFile + " -- go take a look at it!", 10, 10);
	}
	catch (SecurityException e) {
	  g.drawString("writeFile: caught security exception: " + e, 10, 10);
        }
	catch (IOException ioe) {
		g.drawString("writeFile: caught i/o exception", 10, 10);
        }
   }
}

