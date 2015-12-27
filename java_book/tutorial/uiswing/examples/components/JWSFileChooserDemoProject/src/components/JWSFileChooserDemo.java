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

package components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.SwingUtilities;
import javax.jnlp.*;

/* 
 * JWSFileChooserDemo.java must be compiled with jnlp.jar.  For
 * example, if jnlp.jar is in a subdirectory named jars:
 * 
 *   javac -classpath .:jars/jnlp.jar JWSFileChooserDemo.java [UNIX]
 *   javac -classpath .;jars/jnlp.jar JWSFileChooserDemo.java [Microsoft Windows]
 *
 * JWSFileChooserDemo.java requires the following files when executing:
 *   images/Open16.gif
 *   images/Save16.gif
 */
public class JWSFileChooserDemo extends JPanel
                                implements ActionListener {
    static private final String newline = "\n";
    JButton openButton, saveButton;
    JTextArea log;

    public JWSFileChooserDemo() {
        super(new BorderLayout());

        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        //Create the open button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        openButton = new JButton("Open a File...", 
                                 createImageIcon("images/Open16.gif"));
        openButton.addActionListener(this);

        //Create the save button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        saveButton = new JButton("Save a File...",
                                 createImageIcon("images/Save16.gif"));
        saveButton.addActionListener(this); 

        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {

        //Handle open button action.
        if (e.getSource() == openButton) {
            FileOpenService fos = null;
            FileContents fileContents = null;

            try {
                fos = (FileOpenService)ServiceManager.
                          lookup("javax.jnlp.FileOpenService"); 
            } catch (UnavailableServiceException exc) { }

            if (fos != null) {
                try {
                    fileContents = fos.openFileDialog(null, null); 
                } catch (Exception exc) {
                    log.append("Open command failed: "
                               + exc.getLocalizedMessage()
                               + newline);
                    log.setCaretPosition(log.getDocument().getLength());
                }
            }

            if (fileContents != null) {
                try {
                    //This is where a real application would do something
                    //with the file.
                    log.append("Opened file: " + fileContents.getName()
                               + "." + newline);
                } catch (IOException exc) {
                    log.append("Problem opening file: "
                               + exc.getLocalizedMessage()
                               + newline);
                }
            } else {
                log.append("User canceled open request." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }

        //Handle save button action.
        if (e.getSource() == saveButton) {
            FileSaveService fss = null;
            FileContents fileContents = null;
            ByteArrayInputStream is = new ByteArrayInputStream(
                    (new String("Saved by JWSFileChooserDemo").getBytes()));
                                             //XXX YIKES! If they select an
                                             //XXX existing file, this will
                                             //XXX overwrite that file.

            try {
                fss = (FileSaveService)ServiceManager.
                          lookup("javax.jnlp.FileSaveService"); 
            } catch (UnavailableServiceException exc) { }

            if (fss != null) {
                try {
                    fileContents = fss.saveFileDialog(null,
                                                      null,
                                                      is,
                                                      "JWSFileChooserDemo.txt"); 
                } catch (Exception exc) {
                    log.append("Save command failed: "
                               + exc.getLocalizedMessage()
                               + newline);
                    log.setCaretPosition(log.getDocument().getLength());
                }
            }

            if (fileContents != null) {
                try {
                    log.append("Saved file: " + fileContents.getName()
                               + "." + newline);
                } catch (IOException exc) {
                    log.append("Problem saving file: "
                               + exc.getLocalizedMessage()
                               + newline);
                }
            } else {
                log.append("User canceled save request." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = JWSFileChooserDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("JWSFileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new JWSFileChooserDemo());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
