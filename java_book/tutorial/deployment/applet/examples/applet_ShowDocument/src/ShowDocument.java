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

/* 
 * Java(TM) SE 6 version.
 */

import java.applet.AppletContext;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.*;
import java.net.URL;
import java.net.MalformedURLException;

public class ShowDocument extends JApplet 
                          implements ActionListener {
    URLWindow urlWindow;

    public void init() {
        //Execute a job on the event-dispatching thread:
        //creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't successfully complete");
        }
    }

    private void createGUI() {
        JButton button = new JButton("Bring up URL window");
        button.addActionListener(this);
        add(button);

        JFrame.setDefaultLookAndFeelDecorated(true);
        urlWindow = new URLWindow(getAppletContext());
        urlWindow.pack();
    }

    public void destroy() {
        //Execute a job on the event-dispatching thread:
        //creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    destroyGUI();
                }
            });
        } catch (Exception e) { }
    }
     
    private void destroyGUI() {
        urlWindow.setVisible(false);
        urlWindow = null;
    }

    public void actionPerformed(ActionEvent event) {
        urlWindow.setVisible(true);
    }
}

class URLWindow extends JFrame 
                        implements ActionListener {
    JTextField urlField;
    JComboBox choice;
    AppletContext appletContext;

    public URLWindow(AppletContext appletContext) {
        super("Show a Document!");
        this.appletContext = appletContext;

        JPanel contentPane = new JPanel(new GridBagLayout());
        setContentPane(contentPane);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel label1 = new JLabel("URL of document to show: ",
				   JLabel.TRAILING);
        add(label1, c);

        urlField = new JTextField("http://java.sun.com/", 20);
        label1.setLabelFor(urlField);
        urlField.addActionListener(this);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        add(urlField, c);

        JLabel label2 = new JLabel("Window/frame to show it in: ",
				   JLabel.TRAILING);
        c.gridwidth = 1;
        c.weightx = 0.0;
        add(label2, c);

        String[] strings = {
            "(browser's choice)", //don't specify
            "My Personal Window", //a window named "My Personal Window"
            "_blank",             //a new, unnamed window
            "_self",
            "_parent",
            "_top"                //the Frame that contained this applet
        };
        choice = new JComboBox(strings);
        label2.setLabelFor(choice);
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5,0,0,0);
        c.anchor = GridBagConstraints.LINE_START;
        add(choice, c);

        JButton button = new JButton("Show document");
        button.addActionListener(this);
        c.weighty = 1.0;
        c.ipadx = 10;
        c.ipady = 10;
        c.insets = new Insets(10,0,0,0);
        c.anchor = GridBagConstraints.PAGE_END;
        add(button, c);
    } 
    
    public void actionPerformed(ActionEvent event) {
        String urlString = urlField.getText();
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + urlString);
        }

        if (url != null) {
            if (choice.getSelectedIndex() == 0) {
                appletContext.showDocument(url);
            } else {
                appletContext.showDocument(url, 
				  (String)choice.getSelectedItem());
            }
        }
    }
}

