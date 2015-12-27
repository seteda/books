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

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;

/**
 * @author jag
 * @author mem
 * @author kwalrath
 * @author ir71389
 */

/*
 * TumbleItem.java requires these files:
 *   all the images in the images/tumble directory
 *     (or, if specified in the applet tag, another directory [dir]
 *     with images named T1.gif ... Tx.gif, where x is the total
 *     number of images [nimgs])
 *   the appropriate code to specify that the applet be executed,
 *     such as the HTML code in TumbleItem.html or TumbleItem.atag,
 *     or the JNLP code in TumbleItem.jnlp
 *
 */
public class TumbleItem extends JApplet
                        implements ActionListener {
    int loopslot = -1;  //the current frame number

    String dir;         //the directory relative to the codebase
                        //from which the images are loaded

    Timer timer;
                        //the timer animating the images

    int pause;          //the length of the pause between revs

    int offset;         //how much to offset between loops
    int off;            //the current offset
    int speed;          //animation speed
    int nimgs;          //number of images to animate
    int width;          //width of the applet's content pane
    Animator animator;  //the applet's content pane

    ImageIcon imgs[];   //the images
    int maxWidth;       //width of widest image
    JLabel statusLabel;

    //Called by init.
    protected void loadAppletParameters() {
        //Get the applet parameters.
        String at = getParameter("img");
        dir = (at != null) ? at : "images/tumble";
        at = getParameter("pause");
        pause = (at != null) ? Integer.valueOf(at).intValue() : 1900;
        at = getParameter("offset");
        offset = (at != null) ? Integer.valueOf(at).intValue() : 0;
        at = getParameter("speed");
        speed = (at != null) ? (1000 / Integer.valueOf(at).intValue()) : 100;
        at = getParameter("nimgs");
        nimgs = (at != null) ? Integer.valueOf(at).intValue() : 16;
        at = getParameter("maxwidth");
        maxWidth = (at != null) ? Integer.valueOf(at).intValue() : 0;
    }

    /**
     * Create the GUI. For thread safety, this method should
     * be invoked from the event-dispatching thread.
     */
    private void createGUI() {
        //Animate from right to left if offset is negative.
        width = getSize().width;
        if (offset < 0) {
            off = width - maxWidth;
        }

        //Custom component to draw the current image
        //at a particular offset.
        animator = new Animator();
        animator.setOpaque(true);
        animator.setBackground(Color.white);
        setContentPane(animator);

        //Put a "Loading Images..." label in the middle of
        //the content pane.  To center the label's text in
        //the applet, put it in the center part of a
        //BorderLayout-controlled container, and center-align
        //the label's text.
        statusLabel = new JLabel("Loading Images...",
                                 JLabel.CENTER);
        animator.add(statusLabel, BorderLayout.CENTER);
    }

    //Background task for loading images.
    SwingWorker worker = new SwingWorker<ImageIcon[], Void>() {
        @Override
        public ImageIcon[] doInBackground() {
            final ImageIcon[] innerImgs = new ImageIcon[nimgs];
            for (int i = 0; i < nimgs; i++) {
                innerImgs[i] = loadImage(i + 1);
            }
            return innerImgs;
        }

        @Override
        public void done() {
            //Remove the "Loading images" label.
            animator.removeAll();
            loopslot = -1;
            try {
                imgs = get();
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
                String why = null;
                Throwable cause = e.getCause();
                if (cause != null) {
                    why = cause.getMessage();
                } else {
                    why = e.getMessage();
                }
                System.err.println("Error retrieving file: " + why);
            }
        }
    };

    //Called when this applet is loaded into the browser.
    public void init() {
        loadAppletParameters();

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

        //Set up timer to drive animation events.
        timer = new Timer(speed, this);
        timer.setInitialDelay(pause);
        timer.start(); 

        //Start loading the images in the background.
        worker.execute();
    }

    //The component that actually presents the GUI.
    public class Animator extends JPanel {
        public Animator() {
            super(new BorderLayout());
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (worker.isDone() &&
                (loopslot > -1) && (loopslot < nimgs)) {
                if (imgs != null && imgs[loopslot] != null) {
                    imgs[loopslot].paintIcon(this, g, off, 0);
                }
            }
        }
    }

    //Handle timer event. Update the loopslot (frame number) and the
    //offset.  If it's the last frame, restart the timer to get a long
    //pause between loops.
    public void actionPerformed(ActionEvent e) {
        //If still loading, can't animate.
        if (!worker.isDone()) {
            return;
        }

        loopslot++;

        if (loopslot >= nimgs) {
            loopslot = 0;
            off += offset;

            if (off < 0) {
                off = width - maxWidth;
            } else if (off + maxWidth > width) {
                off = 0;
            }
        }

        animator.repaint();

        if (loopslot == nimgs - 1) {
            timer.restart();
        }
    }

    public void start() {
        if (worker.isDone() && (nimgs > 1)) {
            timer.restart();
        }
    }

    public void stop() {
        timer.stop();
    }

    /**
     * Load the image for the specified frame of animation. Since
     * this runs as an applet, we use getResourceAsStream for 
     * efficiency and so it'll work in older versions of Java Plug-in.
     */
    protected ImageIcon loadImage(int imageNum) {
        String path = dir + "/T" + imageNum + ".gif";
        int MAX_IMAGE_SIZE = 2400;  //Change this to the size of
                                     //your biggest image, in bytes.
        int count = 0;
        BufferedInputStream imgStream = new BufferedInputStream(
           this.getClass().getResourceAsStream(path));
        if (imgStream != null) {
            byte buf[] = new byte[MAX_IMAGE_SIZE];
            try {
                count = imgStream.read(buf);
                imgStream.close();
            } catch (java.io.IOException ioe) {
                System.err.println("Couldn't read stream from file: " + path);
                return null;
            }
            if (count <= 0) {
                System.err.println("Empty file: " + path);
                return null;
            }
            return new ImageIcon(Toolkit.getDefaultToolkit().createImage(buf));
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public String getAppletInfo() {
        return "Title: TumbleItem v1.2, 23 Jul 1997\n"
               + "Author: James Gosling\n"
               + "A simple Item class to play an image loop.";
    }

    public String[][] getParameterInfo() {
        String[][] info = {
          {"img", "string", "the directory containing the images to loop"},
          {"pause", "int", "pause between complete loops; default is 3900"},
          {"offset", "int", "offset of each image to simulate left (-) or "
                            + "right (+) motion; default is 0 (no motion)"},
          {"speed", "int", "the speed at which the frames are looped; "
                           + "default is 100"},
          {"nimgs", "int", "the number of images to be looped; default is 16"},
          {"maxwidth", "int", "the maximum width of any image in the loop; "
                              + "default is 0"}
        };
        return info;
    }
}
