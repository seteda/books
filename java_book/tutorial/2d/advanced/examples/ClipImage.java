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


import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;

import java.net.URL;


/**
 * Animated clipping of an image & shapes with alpha.
 */
public class ClipImage extends JApplet implements Runnable {

    private Image img;
    private final double OINC[] = {5.0, 3.0};
    private final double SINC[] = {5.0, 5.0};
    private double x, y;
    private double ix = OINC[0];
    private double iy = OINC[1];
    private double iw = SINC[0];
    private double ih = SINC[1];
    private double ew, eh;   // ellipse width & height
    private GeneralPath p = new GeneralPath();
    private AffineTransform at = new AffineTransform();
    private BasicStroke bs = new BasicStroke(20.0f);
    private Arc2D arc = new Arc2D.Float();
    private Ellipse2D ellipse = new Ellipse2D.Float();
    private RoundRectangle2D roundRect = new RoundRectangle2D.Float();
    private Rectangle2D rect = new Rectangle2D.Float();
    private Color redBlend = new Color(255, 0, 0, 120);
    private Color greenBlend = new Color(0, 255, 0, 120);
    private Thread thread;
    private BufferedImage offImg;
    private int w, h;
    private boolean newBufferedImage;

    public void init() {
        setBackground(Color.white);
        img = getImage(getURL("images/clouds.jpg"));
        try {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(img, 0);
            tracker.waitForID(0);
        }
        catch ( Exception e ) {}
    }

    public void drawDemo(Graphics2D g2) {

        if ( newBufferedImage ) {
            x = Math.random()*w;
            y = Math.random()*h;
            ew = (Math.random()*w)/2;
            eh = (Math.random()*h)/2;
        }
        x += ix;
        y += iy;
        ew += iw;
        eh += ih;
        if ( ew > w/2 ) {
            ew = w/2;
            iw = Math.random() * -w/16 - 1;
        }
        if ( ew < w/8 ) {
            ew = w/8;
            iw = Math.random() * w/16 + 1;
        }
        if ( eh > h/2 ) {
            eh = h/2;
            ih = Math.random() * -h/16 - 1;
        }
        if ( eh < h/8 ) {
            eh = h/8;
            ih = Math.random() * h/16 + 1;
        }
        if ( (x+ew) > w ) {
            x = (w - ew)-1;
            ix = Math.random() * -w/32 - 1;
        }
        if ( x < 0 ) {
            x = 2;
            ix = Math.random() * w/32 + 1;
        }
        if ( (y+eh) > h ) {
            y = (h - eh)-2;
            iy = Math.random() * -h/32 - 1;
        }
        if ( y < 0 ) {
            y = 2;
            iy = Math.random() * h/32 + 1;
        }

        ellipse.setFrame(x, y, ew, eh);
        g2.setClip(ellipse);

        rect.setRect(x+5, y+5, ew-10, eh-10);
        g2.clip(rect);

        g2.drawImage(img, 0, 0, w, h, this);

        p.reset();
        p.moveTo(- w / 2.0f, - h / 8.0f);
        p.lineTo(+ w / 2.0f, - h / 8.0f);
        p.lineTo(- w / 4.0f, + h / 2.0f);
        p.lineTo(+         0.0f, - h / 2.0f);
        p.lineTo(+ w / 4.0f, + h / 2.0f);
        p.closePath();

        at.setToIdentity();
        at.translate(w*.5f, h*.5f);
        g2.transform(at);
        g2.setStroke(bs);
        g2.setPaint(redBlend);
        g2.draw(p);

        at.setToIdentity();
        g2.setTransform(at);

        g2.setPaint(greenBlend);

        for ( int yy = 0; yy < h; yy += 50 ) {
            for ( int xx = 0, i=0; xx < w; i++, xx += 50 ) {
                switch ( i ) {
                case 0 : arc.setArc(xx, yy, 25, 25, 45, 270, Arc2D.PIE);
                    g2.fill(arc); break;
                case 1 : ellipse.setFrame(xx, yy, 25, 25);
                    g2.fill(ellipse); break;
                case 2 : roundRect.setRoundRect(xx, yy, 25, 25, 4, 4);
                    g2.fill(roundRect); break;
                case 3 : rect.setRect(xx, yy, 25, 25);
                    g2.fill(rect);
                    i = -1;
                }

            }
        }
    }


    public Graphics2D createDemoGraphics2D(Graphics g) {
        Graphics2D g2 = null;

        if ( offImg == null || offImg.getWidth() != w ||
             offImg.getHeight() != h ) {
            offImg = (BufferedImage) createImage(w, h);
            newBufferedImage = true;
        }

        if ( offImg != null ) {
            g2 = offImg.createGraphics();
            g2.setBackground(getBackground());
        }

        // .. set attributes ..
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                            RenderingHints.VALUE_RENDER_QUALITY);

        // .. clear canvas ..
        g2.clearRect(0, 0, w, h);

        return g2;
    }


    public void paint(Graphics g) {

        w = getWidth(); 
        h = getHeight(); 

        if ( w <= 0 || h <= 0 )
            return;

        Graphics2D g2 = createDemoGraphics2D(g);
        drawDemo(g2);
        g2.dispose();

        if ( offImg != null && isShowing() ) {
            g.drawImage(offImg, 0, 0, this);
        }

        newBufferedImage = false;
    }


    public void start() {
        thread = new Thread(this);
        thread.start();
    }


    public synchronized void stop() {
        thread = null;
    }


    public void run() {

        Thread me = Thread.currentThread();

        while ( thread == me && isShowing() ) {
            Graphics g = getGraphics();
            paint(g);
            g.dispose();
            thread.yield();
        }
        thread = null;
    }

    protected URL getURL(String filename) {
        URL codeBase = this.getCodeBase();
        URL url = null;

        try {
            url = new URL(codeBase, filename);
        } catch (java.net.MalformedURLException e) {
            System.out.println("Couldn't create image: "
                             + "badly specified URL");
            return null;
        }

        return url;
    }

    public static void main(String s[]) {
        final ClipImage demo = new ClipImage();
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
            public void windowDeiconified(WindowEvent e) { demo.start();}
            public void windowIconified(WindowEvent e) { demo.stop();}
        };
        JFrame f = new JFrame("Java 2D Demo - ClipImage");
        f.addWindowListener(l);
        f.getContentPane().add("Center", demo);
        f.setSize(new Dimension(400,300));
        f.setVisible(true);
        demo.start();
    }
}

