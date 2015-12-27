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

import javax.swing.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;

public class SizeDisplayer extends JComponent {
    private Icon icon;
    private String text;
    private int xTextPad = 5;
    private int yTextPad = 5;

    //Reuse textSizeD and textSizeR to avoid creating
    //lots of unnecessary Dimensions and Rectangles.
    private Rectangle textSizeR = new Rectangle();
    private Dimension textSizeD = new Dimension();
    
    private Dimension userPreferredSize, //null
                      userMinimumSize,   //null
                      userMaximumSize;   //null
    
    public SizeDisplayer(String text, Icon icon) {
        this.text = text;
        this.icon = icon;
        setOpaque(true);
    }
    
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g.create(); //copy g
        Dimension minSize = getMinimumSize();
        Dimension prefSize = getPreferredSize();
        Dimension size = getSize();
        int prefX = 0, prefY = 0;

        //Set hints so text looks nice.
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                             RenderingHints.VALUE_RENDER_QUALITY);
                             
        //Draw the maximum size rectangle if we're opaque.
        if (isOpaque()) {
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, size.width, size.height);
        }
        
        //Draw the icon.
        if (icon != null) {
            Composite oldComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(
                                      AlphaComposite.SRC_OVER,
                                      0.1f));
            icon.paintIcon(this, g2d,
                           (size.width - icon.getIconWidth())/2,
                           (size.height - icon.getIconHeight())/2);
            g2d.setComposite(oldComposite);
        }
                
        //Draw the preferred size rectangle.
        prefX = (size.width - prefSize.width)/2;
        prefY = (size.height - prefSize.height)/2;
        g2d.setColor(Color.RED);
        g2d.drawRect(prefX, prefY, prefSize.width - 1, prefSize.height - 1);

        //Draw the minimum size rectangle.
        if (minSize.width != prefSize.width || minSize.height != prefSize.height) {
            int minX = (size.width - minSize.width)/2;
            int minY = (size.height - minSize.height)/2;
            g2d.setColor(Color.CYAN);
            g2d.drawRect(minX, minY, minSize.width - 1, minSize.height - 1);
        }
        
        //Draw the text.
        if (text != null) {
            Dimension textSize = getTextSize(g2d);
            g2d.setColor(getForeground());
            g2d.drawString(text,
                           (size.width - textSize.width)/2,
                           (size.height - textSize.height)/2
                            + g2d.getFontMetrics().getAscent());
        }
        g2d.dispose();
    }
        
    private Dimension getTextSize(Graphics2D g2d) {
        if (text == null) {
            textSizeD.setSize(0,0);
        } else {
            FontRenderContext frc;
            if (g2d != null) {
                frc = g2d.getFontRenderContext();
            } else {
                frc = new FontRenderContext(null, false, false);
            }
            Rectangle2D textRect = getFont().getStringBounds(
                                              text,
                                              frc);
            textSizeR.setRect(textRect);
            textSizeD.setSize(textSizeR.width, textSizeR.height);
        }
        
        return textSizeD;
    }
        
    public Dimension getMinimumSize() {
        if (userMinimumSize != null) { //user has set the min size
            return userMinimumSize;
        } else {
            return getPreferredSize();
        }
    }
    
    public Dimension getPreferredSize() {
        if (userPreferredSize != null) { //user has set the pref size
            return userPreferredSize;
        } else {
            return calculatePreferredSize();
        }
    }
    
    public Dimension getMaximumSize() {
        if (userMaximumSize != null) { //user has set the max size
            return userMaximumSize;
        } else {
            return new Dimension(Integer.MAX_VALUE,
                                 Integer.MAX_VALUE);
        }
    }
    
    public void setMinimumSize(Dimension newSize) {
        userMinimumSize = newSize;
    }
    public void setPreferredSize(Dimension newSize) {
        userPreferredSize = newSize;
    }
    public void setMaximumSize(Dimension newSize) {
        userMaximumSize = newSize;
    }
    
    private Dimension calculatePreferredSize() {
        Insets insets = getInsets();
        Dimension textSize = getTextSize(null);
        int iconWidth = 0;
        int iconHeight = 0;
        
        if (icon != null) {
            iconWidth = icon.getIconWidth();
            iconHeight = icon.getIconHeight();
        }

        return new Dimension(
                Math.max(iconWidth, textSize.width + 2*xTextPad)
                + insets.left + insets.right,
                Math.max(iconHeight, textSize.height + 2*yTextPad)
                + insets.top + insets.bottom);
    }
}
