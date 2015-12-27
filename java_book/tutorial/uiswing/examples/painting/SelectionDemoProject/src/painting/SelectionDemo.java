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
 * SelectionDemo.java requires one other file:
 *   images/starfield.gif
 */

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/* 
 * This displays an image.  When the user drags within
 * the image, this program displays a rectangle and a string
 * indicating the bounds of the rectangle.
 */
public class SelectionDemo {
    JLabel label;
    static String starFile = "images/starfield.gif";
    
    private void buildUI(Container container, ImageIcon image) {
        container.setLayout(new BoxLayout(container,
                                          BoxLayout.PAGE_AXIS));
                                          
        SelectionArea area = new SelectionArea(image, this);
        container.add(area);

        label = new JLabel("Drag within the image.");
        label.setLabelFor(area);
        container.add(label);

        //Align the left edges of the components.
        area.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT); //redundant
    }

    public void updateLabel(Rectangle rect) {
        int width = rect.width;
        int height = rect.height;

        //Make the coordinates look OK if a dimension is 0.
        if (width == 0) {
            width = 1;
        }
        if (height == 0) {
            height = 1;
        }

        label.setText("Rectangle goes from ("
                      + rect.x + ", " + rect.y + ") to ("
                      + (rect.x + width - 1) + ", "
                      + (rect.y + height - 1) + ").");
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = SelectionDemo.class.getResource(path);
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
        JFrame frame = new JFrame("SelectionDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        SelectionDemo controller = new SelectionDemo();
        controller.buildUI(frame.getContentPane(),
                           createImageIcon(starFile));

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
    
    private class SelectionArea extends JLabel {
        Rectangle currentRect = null;
        Rectangle rectToDraw = null;
        Rectangle previousRectDrawn = new Rectangle();
        SelectionDemo controller;
    
        public SelectionArea(ImageIcon image, SelectionDemo controller) {
            super(image); //This component displays an image.
            this.controller = controller;
            setOpaque(true);
            setMinimumSize(new Dimension(10,10)); //don't hog space
    
            MyListener myListener = new MyListener();
            addMouseListener(myListener);
            addMouseMotionListener(myListener);
        }
    
        private class MyListener extends MouseInputAdapter {
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                currentRect = new Rectangle(x, y, 0, 0);
                updateDrawableRect(getWidth(), getHeight());
                repaint();
            }
    
            public void mouseDragged(MouseEvent e) {
                updateSize(e);
            }
    
            public void mouseReleased(MouseEvent e) {
                updateSize(e);
            }
    
            /* 
             * Update the size of the current rectangle
             * and call repaint.  Because currentRect
             * always has the same origin, translate it
             * if the width or height is negative.
             * 
             * For efficiency (though
             * that isn't an issue for this program),
             * specify the painting region using arguments
             * to the repaint() call.
             * 
             */
            void updateSize(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                currentRect.setSize(x - currentRect.x,
                                    y - currentRect.y);
                updateDrawableRect(getWidth(), getHeight());
                Rectangle totalRepaint = rectToDraw.union(previousRectDrawn);
                repaint(totalRepaint.x, totalRepaint.y,
                        totalRepaint.width, totalRepaint.height);
            }
        }
    
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); //paints the background and image
    
            //If currentRect exists, paint a box on top.
            if (currentRect != null) {
                //Draw a rectangle on top of the image.
                g.setXORMode(Color.white); //Color of line varies
                                           //depending on image colors
                g.drawRect(rectToDraw.x, rectToDraw.y, 
                           rectToDraw.width - 1, rectToDraw.height - 1);
    
                controller.updateLabel(rectToDraw);
            }
        }
    
        private void updateDrawableRect(int compWidth, int compHeight) {
            int x = currentRect.x;
            int y = currentRect.y;
            int width = currentRect.width;
            int height = currentRect.height;
    
            //Make the width and height positive, if necessary.
            if (width < 0) {
                width = 0 - width;
                x = x - width + 1; 
                if (x < 0) {
                    width += x; 
                    x = 0;
                }
            }
            if (height < 0) {
                height = 0 - height;
                y = y - height + 1; 
                if (y < 0) {
                    height += y; 
                    y = 0;
                }
            }
    
            //The rectangle shouldn't extend past the drawing area.
            if ((x + width) > compWidth) {
                width = compWidth - x;
            }
            if ((y + height) > compHeight) {
                height = compHeight - y;
            }
          
            //Update rectToDraw after saving old value.
            if (rectToDraw != null) {
                previousRectDrawn.setBounds(
                            rectToDraw.x, rectToDraw.y, 
                            rectToDraw.width, rectToDraw.height);
                rectToDraw.setBounds(x, y, width, height);
            } else {
                rectToDraw = new Rectangle(x, y, width, height);
            }
        }
    }
}
