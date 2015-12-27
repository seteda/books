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

import javax.naming.ldap.*;
import javax.naming.*;

/*
 *  Shows ways of comparing LDAP names
 */
public class CompareLdapNames {
    public static void main(String args[]) {
        try {
            LdapName one = new LdapName("cn=Vincent Ryan, ou=People, o=JNDITutorial");
            LdapName two = new LdapName("cn=Vincent Ryan");
            LdapName three = new LdapName("o=JNDITutorial");
            LdapName four = new LdapName("");
       
            System.out.println(one.equals(two)); 	    // false
            System.out.println(one.startsWith(three));  // true
            System.out.println(one.endsWith(two));      // true
            System.out.println(one.startsWith(four));   // true
            System.out.println(one.endsWith(four));     // true
            System.out.println(one.endsWith(three));    // false
            System.out.println(one.isEmpty());	    // false
            System.out.println(four.isEmpty());	    // true
            System.out.println(four.size() == 0);	    // true
         }  catch (InvalidNameException e) {
            e.printStackTrace();
         }
    }
}
