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

/* CoordinatesDemo.java requires no other files. */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

/* 
 * This displays a framed area.  As the user moves the cursor
 * over the area, a label displays the cursor's location. When 
 * the user clicks, the area displays a 7x7 dot at the click
 * location.
 */
public class CoordinatesDemo {
    private JLabel label;
    private Point clickPoint, cursorPoint;

    private void buildUI(Container container) {
        container.setLayout(new BoxLayout(container,
                                          BoxLayout.PAGE_AXIS));

        CoordinateArea coordinateArea = new CoordinateArea(this);
        container.add(coordinateArea);

        label = new JLabel();
        resetLabel();
        container.add(label);

        //Align the left edges of the components.
        coordinateArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT); //redundant
    }

    public void updateCursorLocation(int x, int y) {
        if (x < 0 || y < 0) {
            cursorPoint = null;
            updateLabel();
            return;
        }
            
        if (cursorPoint == null) {
            cursorPoint = new Point();
        }
        
        cursorPoint.x = x;
        cursorPoint.y = y;
        updateLabel();
    }

    public void updateClickPoint(Point p) {
        clickPoint = p;
        updateLabel();
    }

    public void resetLabel() {
        cursorPoint = null;
        updateLabel();        
    }
    
    protected void updateLabel() {
        String text = "";

        if ((clickPoint == null) && (cursorPoint == null)) {
            text = "Click or move the cursor within the framed area.";
        } else {
            
            if (clickPoint != null) {
                text += "The last click was at (" 
                        + clickPoint.x + ", " + clickPoint.y + "). ";
            }
            
            if (cursorPoint != null) {
                text += "The cursor is at ("
                        + cursorPoint.x + ", " + cursorPoint.y + "). ";
            }
        }
        
        label.setText(text);
    }

    /**
     * Create the GUI and show it.  For thread safety, 
     * this method should be invoked from the 
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("CoordinatesDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        CoordinatesDemo controller = new CoordinatesDemo();
        controller.buildUI(frame.getContentPane());

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

    public static class CoordinateArea extends JComponent 
                                       implements MouseInputListener {
        Point point = null;
        CoordinatesDemo controller;
        Dimension preferredSize = new Dimension(400,75);
        Color gridColor;
    
        public CoordinateArea(CoordinatesDemo controller) {
            this.controller = controller;
            
            //Add a border of 5 pixels at the left and bottom,
            //and 1 pixel at the top and right.
            setBorder(BorderFactory.createMatteBorder(1,5,5,1,
                                                      Color.RED));
                                                      
            addMouseListener(this);
            addMouseMotionListener(this);
            setBackground(Color.WHITE);
            setOpaque(true);
        }
    
        public Dimension getPreferredSize() {
            return preferredSize;
        }
    
        protected void paintComponent(Graphics g) {
            //Paint background if we're opaque.
            if (isOpaque()) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            //Paint 20x20 grid.
            g.setColor(Color.GRAY);
            drawGrid(g, 20);
            
            //If user has chosen a point, paint a small dot on top.
            if (point != null) {
                g.setColor(getForeground());
                g.fillRect(point.x - 3, point.y - 3, 7, 7);
            }
        }
        
        //Draws a 20x20 grid using the current color.
        private void drawGrid(Graphics g, int gridSpace) {
            Insets insets = getInsets();
            int firstX = insets.left;
            int firstY = insets.top;
            int lastX = getWidth() - insets.right;
            int lastY = getHeight() - insets.bottom;
            
            //Draw vertical lines.
            int x = firstX;
            while (x < lastX) {
                g.drawLine(x, firstY, x, lastY);
                x += gridSpace;
            }
            
            //Draw horizontal lines.
            int y = firstY;
            while (y < lastY) {
                g.drawLine(firstX, y, lastX, y);
                y += gridSpace;
            }
        }
    
        //Methods required by the MouseInputListener interface.
        public void mouseClicked(MouseEvent e) { 
            int x = e.getX();
            int y = e.getY();
            if (point == null) {
                point = new Point(x, y);
            } else {
                point.x = x;
                point.y = y;
            }
            controller.updateClickPoint(point);
            repaint();
        }

        public void mouseMoved(MouseEvent e) {
            controller.updateCursorLocation(e.getX(), e.getY());
        }

        public void mouseExited(MouseEvent e) { 
            controller.resetLabel();
        }

        public void mouseReleased(MouseEvent e) { }
        public void mouseEntered(MouseEvent e) { }
        public void mousePressed(MouseEvent e) { }
        public void mouseDragged(MouseEvent e) { }
    }
}
