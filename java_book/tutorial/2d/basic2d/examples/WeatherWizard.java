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


import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

public class WeatherWizard extends JApplet implements ChangeListener {
    
    WeatherPainter painter;
    
    public void init() {
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
    }
    
    public void start() {
        initComponents();
    }
    
    public static void main(String[] args) {
        JFrame f = new JFrame("Weather Wizard");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JApplet ap = new WeatherWizard();
        ap.init();
        ap.start();
        f.add("Center", ap);
        f.pack();
        f.setVisible(true);
        
    }
    
    private BufferedImage loadImage(String name) {
        String imgFileName = "images/weather-"+name+".png";
        URL url = WeatherWizard.class.getResource(imgFileName);
        BufferedImage img = null;
        try {
            img =  ImageIO.read(url);
        } catch (Exception e) {
        }
        return img;
    }
    
    public void initComponents() {
        
        
        setLayout(new BorderLayout());
        
        
        JPanel p = new JPanel();
        p.add(new JLabel("Temperature:"));
        JSlider tempSlider = new JSlider(20, 100, 65);
        tempSlider.setMinorTickSpacing(5);
        tempSlider.setMajorTickSpacing(20);
        tempSlider.setPaintTicks(true);
        tempSlider.setPaintLabels(true);
        tempSlider.addChangeListener(this);
        p.add(tempSlider);
        add("North", p);
        
        painter = new WeatherPainter();
        painter.sun = loadImage("sun");
        painter.cloud = loadImage("cloud");
        painter.rain = loadImage("rain");
        painter.snow = loadImage("snow");
        painter.setTemperature(65);
        p.add("Center", painter);
        
    }
    
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider)e.getSource();
        painter.setTemperature(slider.getValue());
    }
}

class WeatherPainter extends Component {
    
    int temperature = 65;
    
    String[] conditions = { "Snow", "Rain", "Cloud", "Sun"};
    BufferedImage snow = null;
    BufferedImage rain = null;
    BufferedImage cloud = null;
    BufferedImage sun = null;
    Color textColor = Color.yellow;
    String condStr = "";
    String feels = "";
    
    Composite alpha0 = null, alpha1 = null;
    BufferedImage img0 = null, img1 = null;
    
    void setTemperature(int temp) {
        temperature = temp;
        repaint();
    }
    
    public Dimension getPreferredSize(){
        return new Dimension(450, 125);
    }
    
    void setupText(String s1, String s2) {
        if (temperature <= 32) {
            textColor = Color.blue;
            feels = "Freezing";
        } else if (temperature <= 50) {
            textColor = Color.green;
            feels = "Cold";
        } else if (temperature <= 65) {
            textColor = Color.yellow;
            feels = "Cool";
        } else if (temperature <= 75) {
            textColor = Color.orange;
            feels = "Warm";
        } else {
            textColor = Color.red;
            feels = "Hot";
        }
        condStr = s1;
        if (s2 != null) {
            condStr += "/" + s2;
        }
    }
    
    void setupImages(BufferedImage i0) {
        alpha0 = null;
        alpha1 = null;
        img0   = i0;
        img1   = null;
    }
    
    void setupImages(int min, int max, BufferedImage i0, BufferedImage i1) {
        float alpha = (max-temperature)/(float)(max-min);
        alpha0 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        alpha1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-alpha);
        img0 = i0;
        img1 = i1;
        
    }
    
    void setupWeatherReport() {
        if (temperature <= 32) {
            setupImages(snow);
            setupText("Snow", null);
        } else if (temperature <= 40) {
            setupImages(32, 40, snow, rain);
            setupText("Snow", "Rain");
        } else if (temperature <= 50) {
            setupImages(rain);
            setupText("Rain", null);
        } else if (temperature <= 58) {
            setupImages(50, 58, rain, cloud);
            setupText("Rain", "Cloud");
        }  else if (temperature <= 65) {
            setupImages(cloud);
            setupText("Cloud", null);
        }  else if (temperature <= 75) {
            setupImages(65, 75, cloud, sun);
            setupText("Cloud", "Sun");
        } else {
            setupImages(sun);
            setupText("Sun", null);
        }
    }
    
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension size = getSize();
        Composite origComposite;
        
        setupWeatherReport();
        
        origComposite = g2.getComposite();
        if (alpha0 != null) g2.setComposite(alpha0);
        g2.drawImage(img0,
                0, 0, size.width, size.height,
                0, 0, img0.getWidth(null), img0.getHeight(null),
                null);
        if (img1 != null) {
            if (alpha1 != null) g2.setComposite(alpha1);
            g2.drawImage(img1,
                    0, 0, size.width, size.height,
                    0, 0, img1.getWidth(null), img1.getHeight(null),
                    null);
        }
        g2.setComposite(origComposite);
        
        // Freezing, Cold, Cool, Warm, Hot,
        // Blue, Green, Yellow, Orange, Red
        Font font = new Font("Serif", Font.PLAIN, 36);
        g.setFont(font);
        
        String tempString = feels + " " + temperature+"F";
        FontRenderContext frc = ((Graphics2D)g).getFontRenderContext();
        Rectangle2D boundsTemp = font.getStringBounds(tempString, frc);
        Rectangle2D boundsCond = font.getStringBounds(condStr, frc);
        int wText = Math.max((int)boundsTemp.getWidth(), (int)boundsCond.getWidth());
        int hText = (int)boundsTemp.getHeight() + (int)boundsCond.getHeight();
        int rX = (size.width-wText)/2;
        int rY = (size.height-hText)/2;
        
        g.setColor(Color.LIGHT_GRAY);
        g2.fillRect(rX, rY, wText, hText);
        
        g.setColor(textColor);
        int xTextTemp = rX-(int)boundsTemp.getX();
        int yTextTemp = rY-(int)boundsTemp.getY();
        g.drawString(tempString, xTextTemp, yTextTemp);
        
        int xTextCond = rX-(int)boundsCond.getX();
        int yTextCond = rY-(int)boundsCond.getY() + (int)boundsTemp.getHeight();
        g.drawString(condStr, xTextCond, yTextCond);
        
    }
}
