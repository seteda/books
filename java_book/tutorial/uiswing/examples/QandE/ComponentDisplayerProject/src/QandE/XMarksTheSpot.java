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

package QandE;

import javax.swing.JComponent;
import javax.swing.BorderFactory;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Color;
import java.awt.geom.Line2D;

/* XMarksTheSpot is used by ComponentDisplayer. */
public class XMarksTheSpot extends JComponent {
    Dimension preferredSize = null;
    
    public XMarksTheSpot() {
        setOpaque(true);
        
        //Set the border using either a MatteBorder (standard, used in 
        //Exercise 1) or a StripeBorder (custom, used in Exercise 3).
        //You can find the exercises here:
        //  http://docs.oracle.com/javase/javatutorials/tutorial/uiswing/QandE/questions-ch6.html
        setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.BLACK));
        //setBorder(new StripeBorder());
    }
    
    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(getForeground());
        }
            
        Graphics2D g2 = (Graphics2D)g;
        Insets insets = getInsets();
        g2.setStroke(new BasicStroke(5.0f));
        g2.draw(new Line2D.Double(insets.left,
                                  insets.top,
                                  getWidth() - insets.right,
                                  getHeight() - insets.bottom));
        g2.draw(new Line2D.Double(insets.left,
                                  getHeight() - insets.bottom,
                                  getWidth() - insets.right,
                                  insets.top));
    }
    
    public Dimension getPreferredSize() {
        if (preferredSize == null) {
            return new Dimension(250,100); 
        } else {
            return super.getPreferredSize();
        }
    }
    
    public void setPreferredSize(Dimension newPrefSize) {
        preferredSize = newPrefSize;
        super.setPreferredSize(newPrefSize);
    }
}
