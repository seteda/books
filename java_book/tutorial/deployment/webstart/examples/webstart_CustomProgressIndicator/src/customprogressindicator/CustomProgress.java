/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
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

package customprogressindicator;

import javax.jnlp.DownloadServiceListener;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.*;
import java.net.URL;

public class CustomProgress implements DownloadServiceListener {   
    JFrame frame = null;
    JProgressBar progressBar = null;
    boolean uiCreated = false;

    public CustomProgress() {       
    }

    public void downloadFailed(java.net.URL url, java.lang.String version) {
    }
    
    public void progress(URL url, String version, long readSoFar,
                         long total, int overallPercent) {        
        updateProgressUI(overallPercent);
    }    

    public void upgradingArchive(java.net.URL url,
                      java.lang.String version,
                      int patchPercent,
                      int overallPercent) {
        updateProgressUI(overallPercent);
    }

    public  void validating(java.net.URL url,
                java.lang.String version,
                long entry,
                long total,
                int overallPercent) {
        updateProgressUI(overallPercent);
    }

    private void updateProgressUI(int overallPercent) {
        if (overallPercent > 0 && overallPercent < 99) {
            if (!uiCreated) {
                uiCreated = true;
                // create custom progress indicator's UI only if 
                // there is more work to do, meaning overallPercent > 0 and < 100
                // this prevents flashing when RIA is loaded from cache
                create();
            }
            progressBar.setValue(overallPercent);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    frame.setVisible(true);
                }
            });
        } else {
            // hide frame when overallPercent is above 99
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    frame.setVisible(false);
                    frame.dispose();
                }
            });
        }
    }

    private void create() {
        JPanel top = createComponents();
        frame = new JFrame(); // top level custom progress indicator UI
        frame.getContentPane().add(top, BorderLayout.CENTER);
        frame.setBounds(300,300,400,300);
        frame.pack();
        updateProgressUI(0);
    }

    private JPanel createComponents() {
        JPanel top = new JPanel();
        top.setBackground(Color.WHITE);
        top.setLayout(new BorderLayout(20, 20));

        String lblText = "<html><font color=red size=+2>JDK Documentation</font><br/> The one-stop shop for Java enlightenment! <br/></html>";
        JLabel lbl = new JLabel(lblText);
        top.add(lbl, BorderLayout.NORTH);

        lbl = new JLabel();
        ImageIcon logo = createImageIcon("images/DukeWithHelmet.png", "logo");
        lbl.setIcon(logo);
        top.add(lbl, BorderLayout.EAST);

        lbl = new JLabel("<html><font color=green size=-2>Loading application...</font></html>");
        top.add(lbl, BorderLayout.CENTER);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        top.add(progressBar, BorderLayout.SOUTH);

        return top;
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path,
                                               String description) {
        java.net.URL imgURL = CustomProgress.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}


