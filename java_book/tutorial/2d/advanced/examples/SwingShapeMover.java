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


import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

/*
 * This applet allows the user to move a texture painted rectangle around the applet
 * window.  The rectangle flickers and draws slowly because this applet does not use 
 * double buffering.
*/

public class SwingShapeMover extends JApplet {
    static protected JLabel label;
    DPanel d;

    public void init(){
        getContentPane().setLayout(new BorderLayout());

        d = new DPanel();
        d.setBackground(Color.white);
        getContentPane().add(d);

        label = new JLabel("Drag rectangle around within the area");
        getContentPane().add("South", label);
    }

    public static void main(String s[]) {
        JFrame f = new JFrame("SwingShapeMover");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JApplet applet = new SwingShapeMover();
        f.getContentPane().add("Center", applet);
        applet.init();
        f.pack();
        f.setSize(new Dimension(550,250));
        f.setVisible(true);
    }

}


class DPanel extends JPanel implements MouseListener, MouseMotionListener{
        Rectangle rect = new Rectangle(0, 0, 100, 50);
        BufferedImage bi;
        Graphics2D big;
        
        // Holds the coordinates of the user's last mousePressed event.
        int last_x, last_y;
        boolean firstTime = true;
        TexturePaint fillPolka, strokePolka;
	Rectangle area;                
        // True if the user pressed, dragged or released the mouse outside of
        // the rectangle; false otherwise.
        boolean pressOut = false;   


    public DPanel(){
               setBackground(Color.white);
                addMouseMotionListener(this);
                addMouseListener(this);

                // Creates the fill texture paint pattern.
                bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
                big = bi.createGraphics();
                big.setColor(Color.pink);
                big.fillRect(0, 0, 7, 7);
                big.setColor(Color.cyan);
                big.fillOval(0, 0, 3, 3);
                Rectangle r = new Rectangle(0,0,5,5);
                fillPolka = new TexturePaint(bi, r);
                big.dispose();
        
                //Creates the stroke texture paint pattern.
                bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
                big = bi.createGraphics();
                big.setColor(Color.cyan);
                big.fillRect(0, 0, 7, 7);
                big.setColor(Color.pink);
                big.fillOval(0, 0, 3, 3);
                r = new Rectangle(0,0,5,5);
                strokePolka = new TexturePaint(bi, r);
                big.dispose();
    }

    // Handles the event of the user pressing down the mouse button.
    public void mousePressed(MouseEvent e){

            last_x = rect.x - e.getX();
            last_y = rect.y - e.getY();

            // Checks whether or not the cursor is inside of the rectangle
            // while the user is pressing the mouse.
            if ( rect.contains(e.getX(), e.getY()) ) {
		 updateLocation(e);
            } else {
                 SwingShapeMover.label.setText("First position the cursor on the rectangle and then drag.");
		 pressOut = true;
            }
    }

    // Handles the event of a user dragging the mouse while holding
    // down the mouse button.
    public void mouseDragged(MouseEvent e){

            if ( !pressOut ) {
		updateLocation(e);
            } else {
                SwingShapeMover.label.setText("First position the cursor on the rectangle and then drag.");
	    }
    }

    // Handles the event of a user releasing the mouse button.
    public void mouseReleased(MouseEvent e){

            // Checks whether or not the cursor is inside of the rectangle
            // when the user releases the mouse button.
            if ( rect.contains(e.getX(), e.getY()) ) {
		 updateLocation(e);
            } else {
                 SwingShapeMover.label.setText("First position the cursor on the rectangle and then drag.");
		 pressOut = false;
            }
    }
       
     // This method is required by MouseListener.
     public void mouseMoved(MouseEvent e){}

     // These methods are required by MouseMotionListener.
     public void mouseClicked(MouseEvent e){}
     public void mouseExited(MouseEvent e){}
     public void mouseEntered(MouseEvent e){}
                         
     // Updates the coordinates representing the location of the current rectangle.
     public void updateLocation(MouseEvent e){
                 rect.setLocation(last_x + e.getX(), last_y + e.getY());
                /*
                 * Updates the label to reflect the location of the
                 * current rectangle
                 * if checkRect returns true; otherwise, returns error message.
                 */
                if (checkRect()) {
                    SwingShapeMover.label.setText("Rectangle located at " +
                                                     rect.getX() + ", " +
                                                     rect.getY());
                } else {
                    SwingShapeMover.label.setText("Please don't try to "+
                                                     " drag outside the area.");
                }
 
		repaint();
     }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
	g2.setStroke(new BasicStroke(8.0f));
        Dimension dim = getSize();
        int w = (int)dim.getWidth();
        int h = (int)dim.getHeight();

        if ( firstTime ) {
            area = new Rectangle(dim);
	    rect.setLocation(w/2-50, h/2-25);
            firstTime = false;
	}
        // Clears the rectangle that was previously drawn.
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, w, h);


        // Draws and fills the newly positioned rectangle.
	g2.setPaint(fillPolka);
        g2.fill(rect);	
        g2.setPaint(strokePolka);
        g2.draw(rect);

    }

    /*
     Checks if the rectangle is contained within the applet window.  If the rectangle
     is not contained withing the applet window, it is redrawn so that it is adjacent
     to the edge of the window and just inside the window.
    */

    boolean checkRect(){

	       if (area == null) {
			return false;
	       }

               if(area.contains(rect.x, rect.y, 100, 50)){
                        return true;
                }
                int new_x = rect.x;
                int new_y = rect.y;
        
                if((rect.x+100)>area.getWidth()){
                        new_x = (int)area.getWidth()-99;
                }
                if(rect.x < 0){
                        new_x = -1;  
                }
                if((rect.y+50)>area.getHeight()){
                        new_y = (int)area.getHeight()-49;
                }
                if(rect.y < 0){
                        new_y = -1;
                }
                rect.setLocation(new_x, new_y);
                return false;
    }
}
