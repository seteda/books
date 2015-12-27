/*
 * Copyright (c) 1995, 2009, Oracle and/or its affiliates. All rights reserved.
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

import javax.swing.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

public class GetApplets extends JApplet 
                        implements ActionListener {
    private JTextArea textArea;

    public void init() {
    //Execute a job on the event-dispatching thread:
    //creating this applet's GUI.
    try {
        javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    } catch (Exception e) {
        System.err.println("createGUI didn't successfully complete");
    }
}

    private void createGUI() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(Color.BLACK),
                                    BorderFactory.createEmptyBorder(10,10,10,10)));
        setContentPane(contentPane);
        
        JButton b = new JButton("Click to call getApplets()");
        b.addActionListener(this);
        add(b, BorderLayout.PAGE_START);

        textArea = new JTextArea(5, 40);
        textArea.setEditable(false);
        JScrollPane scroller = new JScrollPane(textArea);
        add(scroller, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent event) {
        printApplets();
    }

    public String getAppletInfo() {
        return "GetApplets";
    }

    public void printApplets() {
        //Enumeration will contain all applets on this page
        //(including this one) that we can send messages to.
        Enumeration e = getAppletContext().getApplets();

        textArea.append("Results of getApplets():\n");

        while (e.hasMoreElements()) {
            Applet applet = (Applet)e.nextElement();
            String info = ((Applet)applet).getAppletInfo();
            if (info != null) {
                textArea.append("- " + info + "\n");
            } else {
                textArea.append("- " 
				+ applet.getClass().getName() 
                                + "\n");
            } 
        }
        textArea.append("________________________\n\n");
    }
}
