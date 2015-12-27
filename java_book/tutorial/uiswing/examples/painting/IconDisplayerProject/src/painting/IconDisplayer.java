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

package painting;

/* 
 * IconDisplayer.java requires the following files:
 *   images/rocketship.gif
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/* 
 * This component displays a single icon one or more times
 * in a row.  The images are right-aligned, with 5 pixels
 * padding in between.  All but the rightmost image are
 * mostly transparent.
 */

public class IconDisplayer extends JComponent {
    private Icon icon;
    private int preferredNumImages = 2;
    private int pad = 5; //space between images
    private Rectangle iconRect = new Rectangle();
    private Rectangle clipRect = new Rectangle();

    public IconDisplayer(Icon icon, int preferredNumImages, int pad) {
        this.icon = icon;
        if (preferredNumImages > 0) {
            this.preferredNumImages = preferredNumImages;
        }
        if (pad >= 0) {
            this.pad = pad;
        }
        
        //Set a reasonable default border.
        if (this.pad > 0) {
            setBorder(BorderFactory.createEmptyBorder(this.pad,
                                                      this.pad,
                                                      this.pad,
                                                      this.pad));
        }
    }
    
    public Dimension getPreferredSize() {
        if (icon != null) {
            Insets insets = getInsets();
            return new Dimension(icon.getIconWidth() 
                                   * preferredNumImages
                                 + pad
                                   * (preferredNumImages - 1)
                                 + insets.left
                                 + insets.right,
                                 icon.getIconHeight() 
                                 + insets.top
                                 + insets.bottom);
        } else {
            return new Dimension(100, 100);
        }
    }
    
    public Dimension getMinimumSize() {
        if (icon != null) {
            Insets insets = getInsets();
            
            //Return enough space for one icon.
            return new Dimension(icon.getIconWidth()
                                 + insets.left
                                 + insets.right,
                                 icon.getIconHeight() 
                                 + insets.top
                                 + insets.bottom);
        } else {
            return new Dimension(0,0);
        }
    }

    protected void paintComponent(Graphics g) {
        if (isOpaque()) { //paint background
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        if (icon != null) {
            //Draw the icon over and over, right aligned.
            Insets insets = getInsets();
            int iconWidth = icon.getIconWidth();
            int iconX = getWidth() - insets.right
                                   - iconWidth;
            int iconY = insets.top;
            boolean faded = false;

            //Copy the Graphics object, which is actually
            //a Graphics2D object.  Cast it so we can
            //set alpha composite.
            Graphics2D g2d = (Graphics2D)g.create();

            //Draw the icons, starting from the right.
            //After the first one, the rest are faded.
            //We won't bother painting icons that are clipped.
            g.getClipBounds(clipRect);
            while (iconX >= insets.left) {
                iconRect.setBounds(iconX, iconY, 
                                   iconWidth, 
                                   icon.getIconHeight());
                if (iconRect.intersects(clipRect)) {
                    icon.paintIcon(this, g2d, iconX, iconY);
                }
                iconX -= (iconWidth + pad);
                if (!faded) { 
                    g2d.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, 0.1f));
                    faded = true;
                }
            }
            
            g2d.dispose(); //clean up
        }
    }
   
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = IconDisplayer.class.getResource(path);
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
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("IconDisplayer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        ImageIcon ship = createImageIcon("images/rocketship.gif");
        IconDisplayer id = new IconDisplayer(ship, 5, -1);
        frame.getContentPane().add(id, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
