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

import java.lang.reflect.*;
import org.w3c.dom.*;
import org.w3c.dom.html.*;

/** Simple applet which dumps the DOM of the web page it's on,
    including node names and their attributes, to System.out. */

public class DOMDump extends java.applet.Applet {
    public void start() {
        try {
            // use reflection to get document
            Class c = Class.forName("com.sun.java.browser.plugin2.DOM");
            Method m = c.getMethod("getDocument",
                                   new Class[] { java.applet.Applet.class });
            
            // cast object returned as HTMLDocument; then traverse or modify DOM
            HTMLDocument doc = (HTMLDocument) m.invoke(null, new Object[] { this });
            HTMLBodyElement body = (HTMLBodyElement) doc.getBody();
            dump(body, INDENT);
        } catch (Exception e) {
            System.out.println("New Java Plug-In not available");
            // In this case, you could fallback to the old bootstrapping mechanism 
            // available in the com.sun.java.browser.plugin.dom package
        }
    }

    private static final String INDENT = "    ";
    private static final String HALF_INDENT = "  ";

    private void dump(Node root, String prefix) {
        if (root instanceof Element) {
            System.out.println(prefix + ((Element) root).getTagName() + " / " + root.getClass().getName());
        } else if (root instanceof CharacterData) {
            String data = ((CharacterData) root).getData().trim();
            if (!data.equals("")) {
                System.out.println(prefix + "CharacterData: " + data);
            }
        } else {
            System.out.println(prefix + root.getClass().getName());
        }
        NamedNodeMap attrs = root.getAttributes();
        if (attrs != null) {
            int len = attrs.getLength();
            for (int i = 0; i < len; i++) {
                Node attr = attrs.item(i);
                System.out.print(prefix + HALF_INDENT + "attribute " + i + ": " +
                                 attr.getNodeName());
                if (attr instanceof Attr) {
                    System.out.print(" = " + ((Attr) attr).getValue());
                }
                System.out.println();
            }
        }

        if (root.hasChildNodes()) {
            NodeList children = root.getChildNodes();
            if (children != null) {
                int len = children.getLength();
                for (int i = 0; i < len; i++) {
                    dump(children.item(i), prefix + INDENT);
                }
            }
        }
    }
}
