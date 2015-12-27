/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.net.*;
import java.util.*;

public class CookieAccessor {
    
    public void accessCookies() {
        System.out.println("---- Access cookies using CookieHandler ---");
        getCookieUsingCookieHandler();
        setCookieUsingCookieHandler();

        System.out.println("--- Access cookies using URLConnection headers; the old way ---");
        getCookieFromURLConn();
        setCookieInURLConn();
    }
    
    /**
     * Get cookies for a url from cookie store
     */
    public void getCookieUsingCookieHandler() { 
        try {       
            // instantiate CookieManager; make sure to set CookiePolicy
            CookieManager manager = new CookieManager();
            manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(manager);

            // get content from URLConnection; cookies are set by web site
            URL url = new URL("http://host.example.com");
            URLConnection connection = url.openConnection();
            connection.getContent();

            // get cookies from underlying CookieStore
            CookieStore cookieJar = manager.getCookieStore();
            List <HttpCookie> cookies = cookieJar.getCookies();
            for (HttpCookie cookie: cookies) {
              System.out.println("CookieHandler retrieved cookie: " + cookie);
            }
        } catch(Exception e) {
            System.out.println("Unable to get cookie using CookieHandler");
            e.printStackTrace();
        }
    } 
    
    /**
     * Set cookie in cookie store
     */
    public void setCookieUsingCookieHandler() {
        try {
            // instantiate CookieManager
            CookieManager manager = new CookieManager();
            CookieHandler.setDefault(manager);
            CookieStore cookieJar = manager.getCookieStore();

            // create cookie
            HttpCookie cookie = new HttpCookie("UserName", "John Doe");

            // add cookie to CookieStore for a particular URL
            URL url = new URL("http://host.example.com");
            cookieJar.add(url.toURI(), cookie);
            System.out.println("Added cookie using cookie handler");
        } catch(Exception e) {
            System.out.println("Unable to set cookie using CookieHandler");
            e.printStackTrace();
        }
    }

 /**
   * Retrieves cookie from header fields in URL connection.
   * Another way to get cookies before recent changes to CookieManager
   */
    public void getCookieFromURLConn() {
        try {
            URL url = new URL("http://host.example.com");            
            URLConnection conn = url.openConnection();
            Map<String, List<String>> headers = conn.getHeaderFields(); 
            // the literal "Set-Cookie" may be capitalized differently in different web sites
            List<String> values = headers.get("Set-cookie");  

            if (values != null) {
                String cookieValue = null; 
                for (Iterator iter = values.iterator(); iter.hasNext(); ) {
                    cookieValue = (String)iter.next(); 
                    System.out.println("Cookie value from URL connection: " + cookieValue);     
                }
            } else {
                System.out.println("No cookies found");
            }
        } catch (Exception e) {
            System.out.println("Unable to get cookie from URL connection");
            e.printStackTrace();
        }
    }
    
    /**
     * Sets a custom cookie in request header
     * Another way to set cookies before recent changes to CookieManager
     */
    public void setCookieInURLConn() {
        try {
            URL url = new URL( "http://host.example.com/" );
            URLConnection conn = url.openConnection(); 
            conn.setRequestProperty("Cookie", "UserName=John Doe"); 
            System.out.println("Set cookie in URL connection");            
        } catch(Exception e) {
            System.out.println("Unable to set cookie in URL connection");
            e.printStackTrace();
        }        
    }  
}
