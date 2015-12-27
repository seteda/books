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


import java.lang.Integer;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.*;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.*;

/* 
 * This applet renders an ellipse overlapping a rectangle with the compositing rule and 
 * alpha value selected by the user.
*/

public class Composite extends JApplet implements ItemListener {
    CompPanel comp;
    JLabel alphaLabel, rulesLabel;
    JComboBox alphas, rules;
    String alpha = "1.0";
    int rule = 0;

    // Initializes the layout of the components.
    public void init() {
        GridBagLayout layOut = new GridBagLayout();
        getContentPane().setLayout(layOut);

        GridBagConstraints l = new GridBagConstraints();
        l.weightx = 1.0;
        l.fill = GridBagConstraints.BOTH;
        l.gridwidth = GridBagConstraints.RELATIVE;
        alphaLabel = new JLabel();
        alphaLabel.setText("Alphas");
        Font newFont = getFont().deriveFont(1);
        alphaLabel.setFont(newFont);
        alphaLabel.setHorizontalAlignment(JLabel.CENTER);
        layOut.setConstraints(alphaLabel, l);
        getContentPane().add(alphaLabel);   
        GridBagConstraints c = new GridBagConstraints();
        getContentPane().setLayout(layOut);

        l.gridwidth = GridBagConstraints.REMAINDER;
        rulesLabel = new JLabel();
        rulesLabel.setText("Rules");
        newFont = getFont().deriveFont(1);
        rulesLabel.setFont(newFont);
        rulesLabel.setHorizontalAlignment(JLabel.CENTER);
        layOut.setConstraints(rulesLabel, l);
        getContentPane().add(rulesLabel);   

        GridBagConstraints a = new GridBagConstraints();
        a.gridwidth = GridBagConstraints.RELATIVE;
        a.weightx = 1.0;
        a.fill = GridBagConstraints.BOTH;
        alphas = new JComboBox();
        layOut.setConstraints(alphas, a);
        alphas.addItem("1.0");
        alphas.addItem("0.75");
        alphas.addItem("0.50");
        alphas.addItem("0.25");
        alphas.addItem("0.0");
        alphas.addItemListener(this);
        getContentPane().add(alphas);

        a.gridwidth = GridBagConstraints.REMAINDER;
        rules = new JComboBox();
        layOut.setConstraints(rules, a);
        rules.addItem("SRC");
        rules.addItem("DST_IN");
        rules.addItem("DST_OUT");
        rules.addItem("DST_OVER");
        rules.addItem("SRC_IN");
        rules.addItem("SRC_OVER");
        rules.addItem("SRC_OUT");
        rules.addItem("CLEAR");
        rules.addItemListener(this);
        getContentPane().add(rules);

        GridBagConstraints fC = new GridBagConstraints(); 
        fC.fill = GridBagConstraints.BOTH;
        fC.weightx = 1.0;
        fC.weighty = 1.0;
        fC.gridwidth = GridBagConstraints.REMAINDER;
        comp = new CompPanel();
        layOut.setConstraints(comp, fC);
        getContentPane().add(comp); 

        validate();
    }

/*
 * Detects a change in either of the Choice components.  Resets the variable corresponding
 * to the Choice whose state is changed.  Invokes changeRule in CompPanel with the current
 * alpha and composite rules.
*/
    public void itemStateChanged(ItemEvent e){
        if ( e.getStateChange() != ItemEvent.SELECTED ) {
            return;
        }

        Object choice = e.getSource();
        if ( choice == alphas ) {
            alpha = (String)alphas.getSelectedItem();
        } else {
            rule = rules.getSelectedIndex();
        }
        comp.changeRule(alpha, rule);
    }

    public static void main(String s[]) {
        JFrame f = new JFrame("Composite");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JApplet applet = new Composite();
        f.getContentPane().add("Center", applet);
        applet.init();
        f.pack();
        f.setSize(new Dimension(300,300));
        f.setVisible(true);
    }
}


class CompPanel extends JPanel {

    AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC);  
    float alpha = 1.0f;

    public CompPanel(){}

// Resets the alpha and composite rules with selected items.	
    public void changeRule(String a, int rule) {
        alpha = Float.valueOf(a).floatValue();
        ac = AlphaComposite.getInstance(getRule(rule), alpha);
        repaint();
    }

// Gets the requested compositing rule.
    public int getRule(int rule){
        int alphaComp = 0;
        switch ( rule ) {
        case 0: alphaComp = AlphaComposite.SRC; break;
        case 1: alphaComp = AlphaComposite.DST_IN; break;
        case 2: alphaComp = AlphaComposite.DST_OUT; break;
        case 3: alphaComp = AlphaComposite.DST_OVER; break;
        case 4: alphaComp = AlphaComposite.SRC_IN; break;
        case 5: alphaComp = AlphaComposite.SRC_OVER; break;
        case 6: alphaComp = AlphaComposite.SRC_OUT; break;
        case 7: alphaComp = AlphaComposite.CLEAR; break;
        }
        return alphaComp;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent( g );
        Graphics2D g2 = (Graphics2D) g;

        Dimension d = getSize();
        int w = d.width;
        int h = d.height; 

        // Creates the buffered image.
        BufferedImage buffImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gbi = buffImg.createGraphics();

        // Clears the previously drawn image.
        g2.setColor(Color.white);
        g2.fillRect(0, 0, d.width, d.height);

        int rectx = w/4;
        int recty = h/4;

        // Draws the rectangle and ellipse into the buffered image.
        gbi.setColor(new Color(0.0f, 0.0f, 1.0f, 1.0f));
        gbi.fill(new Rectangle2D.Double(rectx, recty, 150, 100));
        gbi.setColor(new Color(1.0f, 0.0f, 0.0f, 1.0f));    
        gbi.setComposite(ac);
        gbi.fill(new Ellipse2D.Double(rectx+rectx/2,recty+recty/2,150,100));

        // Draws the buffered image.
        g2.drawImage(buffImg, null, 0, 0);
    }
}   
