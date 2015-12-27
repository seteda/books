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

package com.scoredev.scores;

import java.io.*;
import java.security.*;
import java.util.Hashtable;

public class HighScore
{
    private String gameName;
    private File highScoreFile;

    public HighScore(String gameName) 
    {
	this.gameName = gameName;

	AccessController.doPrivileged(new PrivilegedAction() {
	    public Object run() {
		String path = 
		    System.getProperty("user.home") +
		    File.separator + 
		    ".highscore";

		highScoreFile = new File(path);
		return null;
	    }
	});
    }

    public void setHighScore(final int score)
	throws IOException
    {
	//check permission first
	SecurityManager sm = System.getSecurityManager();
	if (sm != null) {
	    sm.checkPermission(new HighScorePermission(gameName));
	}

	// need a doPrivileged block to manipulate the file
	try {
	    AccessController.doPrivileged(new PrivilegedExceptionAction() {
		public Object run() throws IOException {
		    Hashtable scores = null;
		    // try to open the existing file. Should have a locking
		    // protocol (could use File.createNewFile).
		    try {
			FileInputStream fis = 
			    new FileInputStream(highScoreFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			scores = (Hashtable) ois.readObject();
		    } catch (Exception e) {
			// ignore, try and create new file
		    }

		    // if scores is null, create a new hashtable
		    if (scores == null)
			scores = new Hashtable(13);

		    // update the score and save out the new high score
		    scores.put(gameName, new Integer(score));
		    FileOutputStream fos = new FileOutputStream(highScoreFile);
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
		    oos.writeObject(scores);
		    oos.close();
		    return null;
		}
	    });
	} catch (PrivilegedActionException pae) {
	    throw (IOException) pae.getException();
	}
    }

    /**
     * get the high score. return -1 if it hasn't been set.
     *
     */
    public int getHighScore()
	throws IOException, ClassNotFoundException
    {
	//check permission first
	SecurityManager sm = System.getSecurityManager();
	if (sm != null) {
	    sm.checkPermission(new HighScorePermission(gameName));
	}

	Integer score = null;

	// need a doPrivileged block to manipulate the file
	try {
	     score = (Integer) AccessController.doPrivileged(
	                                new PrivilegedExceptionAction() {
		public Object run() 
		    throws IOException, ClassNotFoundException 
		{
		    Hashtable scores = null;
		    // try to open the existing file. Should have a locking
		    // protocol (could use File.createNewFile).
		    FileInputStream fis = 
			new FileInputStream(highScoreFile);
		    ObjectInputStream ois = new ObjectInputStream(fis);
		    scores = (Hashtable) ois.readObject();

		    // get the high score out
		    return scores.get(gameName);
		}
	    });
	} catch (PrivilegedActionException pae) {
	    Exception e = pae.getException();
	    if (e instanceof IOException)
		throw (IOException) e;
	    else
		throw (ClassNotFoundException) e;
	}
	if (score == null)
		return -1;
	else
		return score.intValue();
    }



    public static void main(String args[])
	throws Exception 
    {
	HighScore hs = new HighScore(args[1]);
	if (args[0].equals("set")) {
	    hs.setHighScore(Integer.parseInt(args[2]));
	} else {
	    System.out.println("score = "+ hs.getHighScore());
	}
    }
}
