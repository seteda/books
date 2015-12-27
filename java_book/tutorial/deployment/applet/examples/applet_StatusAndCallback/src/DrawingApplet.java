/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.applet.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

/**
 * This applet exports an API for the use of client-side JavaScript code.
 */
public class DrawingApplet extends Applet {
    BufferedImage image;  // We draw into this offscreen image
    Graphics2D g;         // using this graphics context

    // The browser calls this method to initialize the applet
    public void init() {
        // Find out how big the applet is and create an offscreen image
        // that size.
        int w = getWidth();
        int h = getHeight();
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // Get a graphics context for drawing into the image
        g = image.createGraphics();
        // Start with a pure white background
        g.setPaint(Color.WHITE);
        g.fillRect(0, 0, w, h);
        // Turn on antialiasing
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
        
        try {
            // sleep here to simulate a long init method
            Thread.sleep(4000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    // The browser automatically calls this method when the applet needs
    // to be redrawn.  We copy the offscreen image onscreen.
    // JavaScript code drawing to this applet must call the inherited
    // repaint() method to request a redraw.
    public void paint(Graphics g) { g.drawImage(image, 0, 0, this); }

    // These methods set basic drawing parameters
    // This is just a subset: the Java2D API supports many others
    public void setLineWidth(float w) { g.setStroke(new BasicStroke(w)); }
    public void setColor(int color) { g.setPaint(new Color(color)); }
    public void setFont(String fontfamily, int pointsize) {
        g.setFont(new Font(fontfamily, Font.PLAIN, pointsize));
    }

    // These are simple drawing primitives
    public void fillRect(int x, int y, int w, int h) { g.fillRect(x,y,w,h); }
    public void drawRect(int x, int y, int w, int h) { g.drawRect(x,y,w,h); }
    public void drawString(String s, int x, int y) { g.drawString(s, x, y); }

    // These methods fill and draw arbitrary shapes
    public void fill(Shape shape) { g.fill(shape); }
    public void draw(Shape shape) { g.draw(shape); }

    // These methods return simple Shape objects
    // This is just a sampler.  The Java2D API supports many others
    public Shape createRectangle(double x, double y, double w, double h) {
        return new Rectangle2D.Double(x, y, w, h);
    }
    public Shape createEllipse(double x, double y, double w, double h) {
        return new Ellipse2D.Double(x, y, w, h);
    }
    public Shape createWedge(double x, double y, double w, double h,
                             double start, double extent) {
        return new Arc2D.Double(x, y, w, h, start, extent, Arc2D.PIE);
    }
}