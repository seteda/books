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


import java.io.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

public class ImageLabel extends Component {

    public static void main(String args[]) {

        JFrame frame = new JFrame("Image Label");
        BufferedImage img = null;
        try {
            String imageFileName = "painting.gif";
            img = ImageIO.read(new File(imageFileName));
        } catch (IOException e) {
        }      
        frame.add("Center", new ImageLabel(img, "Java 2D Graphics!"));
        frame.pack();
        frame.setVisible(true);
    }

    BufferedImage img;
    String text;
    Font font;
    
    public ImageLabel(BufferedImage img, String text) {
        this.img = img;
        this.text = text;
        font = new Font("Serif", Font.PLAIN, 36);
    }
    
    /* We want to size the component around the text.  */
    public Dimension getPreferredSize() {
        FontMetrics metrics = img.getGraphics().getFontMetrics(font);
        int width = metrics.stringWidth(text)*2;
        int height = metrics.getHeight()*2;
        return new Dimension(width, height);
    }

    public void paint(Graphics g) {
        
        /* Draw the image stretched to exactly cover the size of the
         * drawing area.
         */
        Dimension size = getSize();
        g.drawImage(img, 
                    0, 0, size.width, size.height,
                    0, 0, img.getWidth(null), img.getHeight(null),
                    null);                    

        /* Fill a rounded rectangle centered in the drawing area.
         * Calculate the size of the rectangle from the size of the text
         */
        g.setFont(font);
        FontRenderContext frc = ((Graphics2D)g).getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(text, frc);

        int wText = (int)bounds.getWidth();
        int hText = (int)bounds.getHeight();

        int rX = (size.width-wText)/2;
        int rY = (size.height-hText)/2;
        g.setColor(Color.yellow);
        g.fillRoundRect(rX, rY, wText, hText, hText/2, hText/2);

        /* Draw text positioned in the rectangle.
         * Since the rectangle is sized based on the bounds of
         * the String we can position it using those bounds.
         */
        int xText = rX-(int)bounds.getX();
        int yText = rY-(int)bounds.getY();
        g.setColor(Color.black);
        g.setFont(font);
        g.drawString(text, xText, yText);
    }
}
