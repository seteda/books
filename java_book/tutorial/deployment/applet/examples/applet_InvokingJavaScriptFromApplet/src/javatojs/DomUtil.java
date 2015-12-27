/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
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

package javatojs;

import  org.w3c.dom.bootstrap.DOMImplementationRegistry;
import  org.w3c.dom.*;
import  org.w3c.dom.ls.DOMImplementationLS;
import  org.w3c.dom.ls.LSParser;
import  org.w3c.dom.ls.LSSerializer;

/**
 *
 * @author sk219469
 */
public class DomUtil {

     public static Document readDocument(String uri) {
        Document document = null;
        try {
            DOMImplementationRegistry registry = 
                DOMImplementationRegistry.newInstance();
        

            DOMImplementationLS impl = 
                (DOMImplementationLS)registry.getDOMImplementation("LS");

            LSParser builder = impl.createLSParser(
                DOMImplementationLS.MODE_SYNCHRONOUS, null);

            System.out.println("In readDocument uri: " + uri);
            document = builder.parseURI(uri);
            if (document == null) {
                System.out.println("Null doc returned!!!!!!!!");
            }
            System.out.println("Read toc: \n " + document);
        } catch (Exception e) {
            System.out.println("ERROR reading toc: \n ");
            e.printStackTrace();
        }
        return document;
   }
    public static void writeDocument(Document document) {
        try {
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();

            DOMImplementationLS impl =
                    (DOMImplementationLS) registry.getDOMImplementation("LS");
            LSSerializer writer = impl.createLSSerializer();
            String documentStr = writer.writeToString(document);
            System.out.println("Serialized document: \n" + documentStr);
        } catch (Exception e) {
            System.out.println("ERROR writing document: \n ");
            e.printStackTrace();
        }
    }
     

   public static void main (String [] args) {
       Document document  = readDocument("D:\\users\\sk\\netbeansProjects\\LiveConnect_Java_To_JavaScript_Example\\src\\AppletPage.html");
       if (document != null) {
                Element summaryElem = document.getElementById("summary");
                System.out.println("=====summaryElem: " + summaryElem);
                if (summaryElem != null) {
                    Node summaryTextNode = document.createTextNode("this is a summary");
                    summaryElem.appendChild(summaryTextNode);

                }
       }
       writeDocument(document);
   }  
     
}
