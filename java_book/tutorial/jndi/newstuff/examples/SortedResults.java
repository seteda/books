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

import java.util.Hashtable;
import java.io.*;

import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.*;

/**
 * Shows how sorted search results can be obtained
 * using SortControl API
 */
class SortedResults {

    public static void main(String[] args) throws IOException {

	// Set up environment for creating initial context
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
	env.put(Context.INITIAL_CONTEXT_FACTORY, 
	    "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL,
	    "ldap://localhost:389/ou=People,o=JNDITutorial");

	try {
	    // Create initial context with no connection request controls
	    LdapContext ctx = new InitialLdapContext(env, null);

	    // Create a sort control that sorts based on CN
	    String sortKey = "cn";
     	    ctx.setRequestControls(new Control[]{
		 new SortControl(sortKey, Control.CRITICAL) });

	    // Perform a search
     	    NamingEnumeration results =
         		ctx.search("", "(objectclass=*)", new SearchControls());

	    // Iterate over search results
	    System.out.println("---->sort by cn");
	    while (results != null && results.hasMore()) {
         	// Display an entry
         	SearchResult entry = (SearchResult) results.next();
         	System.out.println(entry.getName());

         	// Handle the entry's response controls (if any)
         	if (entry instanceof HasControls) {
             	    // ((HasControls)entry).getControls();
         	}
     	    }
	    // Examine the sort control response 
	    Control[] controls = ctx.getResponseControls();
     	    if (controls != null) {
         	for (int i = 0; i < controls.length; i++) {
             	    if (controls[i] instanceof SortResponseControl) {
                 	SortResponseControl src = (SortResponseControl)controls[i];
                 	if (! src.isSorted()) {
                     	    throw src.getException();
                        }
             	    } else {
                 	// Handle other response controls (if any)
             	    }
         	}
     	    }

	    // Close when no longer needed
	    ctx.close();
	} catch (NamingException e) {
	    e.printStackTrace();
	}
    }
}
