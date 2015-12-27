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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

/*
 * This applet renders a shape, selected by the user, with a paint,stroke, and rendering method,
 * also selected by the user.
*/

public class Transform extends JApplet implements ItemListener,
ActionListener {
    JLabel primLabel, lineLabel, paintLabel, transLabel, strokeLabel;
    TransPanel display;
    static JComboBox primitive, line, paint, trans, stroke;
    JButton redraw;
    public static boolean no2D = false;

    public void init() {
        GridBagLayout layOut = new GridBagLayout();
        getContentPane().setLayout(layOut);
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        primLabel = new JLabel();
        primLabel.setText("Primitive");
        Font newFont = getFont().deriveFont(1);
        primLabel.setFont(newFont);
        primLabel.setHorizontalAlignment(JLabel.CENTER);
        layOut.setConstraints(primLabel, c);
        getContentPane().add(primLabel);

        lineLabel = new JLabel();
        lineLabel.setText("Lines");
        lineLabel.setFont(newFont);
        lineLabel.setHorizontalAlignment(JLabel.CENTER);
        layOut.setConstraints(lineLabel, c);
        getContentPane().add(lineLabel);

        paintLabel = new JLabel();
        paintLabel.setText("Paints");
        paintLabel.setFont(newFont);
        paintLabel.setHorizontalAlignment(JLabel.CENTER);
        layOut.setConstraints(paintLabel, c);
        getContentPane().add(paintLabel);

        c.gridwidth = GridBagConstraints.RELATIVE;
        transLabel = new JLabel();  
        transLabel.setText("Transforms");
        transLabel.setFont(newFont);
        transLabel.setHorizontalAlignment(JLabel.CENTER);
        layOut.setConstraints(transLabel, c);
        getContentPane().add(transLabel);

        c.gridwidth = GridBagConstraints.REMAINDER;
        strokeLabel = new JLabel();
        strokeLabel.setText("Rendering");
        strokeLabel.setFont(newFont);
        strokeLabel.setHorizontalAlignment(JLabel.CENTER); 
        layOut.setConstraints(strokeLabel, c);
        getContentPane().add(strokeLabel);

        GridBagConstraints ls = new GridBagConstraints();
        ls.weightx = 1.0;
        ls.fill = GridBagConstraints.BOTH;
        primitive = new JComboBox( new Object []{
                                   "rectangle",
                                   "ellipse",
                                   "text"});
        primitive.addItemListener(this);
        newFont = newFont.deriveFont(0, 14.0f);
        primitive.setFont(newFont);
        layOut.setConstraints(primitive, ls);
        getContentPane().add(primitive);

        line = new JComboBox( new Object []{  
                              "thin",
                              "thick",
                              "dashed"});
        line.addItemListener(this);
        line.setFont(newFont);
        layOut.setConstraints(line, ls);
        getContentPane().add(line);

        paint = new JComboBox( new Object[]{
                               "solid",
                               "gradient",
                               "polka"});
        paint.addItemListener(this);
        paint.setFont(newFont);
        layOut.setConstraints(paint, ls);
        getContentPane().add(paint);

        ls.gridwidth = GridBagConstraints.RELATIVE;

        trans = new JComboBox( new Object[]{
				"Identity",
				"rotate",
				"scale",
				"shear"});
	trans.addItemListener(this);
	trans.setFont(newFont);
	layOut.setConstraints(trans, ls);
	getContentPane().add(trans);

        ls.gridwidth = GridBagConstraints.REMAINDER;
        stroke = new JComboBox( new Object[]{
                                "Stroke",
                                "Fill",
                                "Stroke & Fill"}); 
        stroke.addItemListener(this);
        stroke.setFont(newFont);
        layOut.setConstraints(stroke, ls);
        getContentPane().add(stroke);

	GridBagConstraints button = new GridBagConstraints();
	button.gridwidth = GridBagConstraints.REMAINDER;
	redraw = new JButton("Redraw");
	redraw.addActionListener(this);
        redraw.setFont(newFont);
        layOut.setConstraints(redraw, button);
        getContentPane().add(redraw);

        GridBagConstraints tP = new GridBagConstraints();
        tP.fill = GridBagConstraints.BOTH;
        tP.weightx = 1.0;
        tP.weighty = 1.0;
        tP.gridwidth = GridBagConstraints.REMAINDER;
        display = new TransPanel();
        layOut.setConstraints(display, tP);
        display.setBackground(Color.white);
        getContentPane().add(display);

        validate();

    }

    public void itemStateChanged(ItemEvent e){}

   public void actionPerformed(ActionEvent e) {
        display.setTrans(trans.getSelectedIndex());
        display.renderShape();
   }
	

    public static void main( String[] argv ) {
        if ( argv.length > 0 && argv[0].equals( "-no2d" ) ) {
            Transform.no2D = true;
        }
        
        JFrame frame = new JFrame( "Transform" );
        frame.addWindowListener( new WindowAdapter(){
            public void windowClosing( WindowEvent e ){
                System.exit( 0 );
            }
        });                     

        JApplet applet = new Transform();
        frame.getContentPane().add( BorderLayout.CENTER, applet );
        
        applet.init();
        
        frame.setSize( 550, 400 );
        frame.setVisible(true);
   }

}

class TransPanel extends JPanel {
    AffineTransform at = new AffineTransform();
    int w, h;
    Shape shapes[] = new Shape[3];
    BufferedImage bi;
    boolean firstTime = true;

    public TransPanel(){
        setBackground(Color.white);
        shapes[0] = new Rectangle(0, 0, 100, 100);
        shapes[1] = new Ellipse2D.Double(0.0, 0.0, 100.0, 100.0);
        TextLayout textTl = new TextLayout("Text", new Font("Helvetica", 1, 96), new FontRenderContext(null, false, false));
	AffineTransform textAt = new AffineTransform();
	textAt.translate(0, (float)textTl.getBounds().getHeight());
        shapes[2] = textTl.getOutline(textAt);
    }

    public void setTrans(int transIndex) {
        // Sets the AffineTransform.
        switch ( transIndex ) {
        case 0 : at.setToIdentity();
            at.translate(w/2, h/2); break;
        case 1 : at.rotate(Math.toRadians(45)); break;
        case 2 : at.scale(0.5, 0.5); break;
        case 3 : at.shear(0.5, 0.0); break;
      }
    }
	
    public void renderShape() {
        repaint();
    }

    public void paintComponent(Graphics g) {
	super.paintComponent(g);
		
	if ( !Transform.no2D ) {
	        Graphics2D g2 = (Graphics2D) g;
        	Dimension d = getSize();
        	w = d.width;
        	h = d.height;


        // Prints out the intructions.
        String instruct = "Pick a primitive, line style, paint, transform,";
        TextLayout thisTl = new TextLayout(instruct, new Font("Helvetica", 0, 10), g2.getFontRenderContext());
        float width = (float)thisTl.getBounds().getWidth();
	float height = (float)thisTl.getBounds().getHeight();
        thisTl.draw(g2, w/2-width/2, 15);

	instruct = "and rendering method and click the Redraw button.";
        thisTl = new TextLayout(instruct, new Font("Helvetica", 0, 10), g2.getFontRenderContext());
        width = (float)thisTl.getBounds().getWidth();
        thisTl.draw(g2, w/2-width/2, height + 17);

	// Initialize the transform.
	if (firstTime) {
	    at.setToIdentity();
            at.translate(w/2, h/2);
            firstTime = false;
	}

        // Sets the Stroke.
	Stroke oldStroke = g2.getStroke();

        switch ( Transform.line.getSelectedIndex() ) {
        case 0 : g2.setStroke(new BasicStroke(3.0f)); break;
        case 1 : g2.setStroke(new BasicStroke(8.0f)); break;
        case 2 : float dash[] = {10.0f};
            g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
            break;
        }

        // Sets the Paint.
	Paint oldPaint = g2.getPaint();

        switch ( Transform.paint.getSelectedIndex() ) {
        case 0 : g2.setPaint(Color.blue);break;
        case 1 : g2.setPaint(new GradientPaint(0, 0, Color.lightGray, w-250, h, Color.blue, false));
            break;
        case 2 : BufferedImage buffi = new BufferedImage(15, 15, BufferedImage.TYPE_INT_RGB);
            Graphics2D buffig = buffi.createGraphics();
            buffig.setColor(Color.blue);
            buffig.fillRect(0, 0, 15, 15);
            buffig.setColor(Color.lightGray);
	    buffig.translate((15/2)-(5/2), (15/2)-(5/2));
            buffig.fillOval(0, 0, 7, 7); 
            Rectangle r = new Rectangle(0,0,25,25);
            g2.setPaint(new TexturePaint(buffi, r));
            break;
        }



        // Sets the Shape.
        Shape shape = shapes[Transform.primitive.getSelectedIndex()];
        Rectangle r = shape.getBounds();

        // Sets the selected Shape to the center of the Canvas.
        AffineTransform saveXform = g2.getTransform();
 	AffineTransform toCenterAt = new AffineTransform();
        toCenterAt.concatenate(at);
        toCenterAt.translate(-(r.width/2), -(r.height/2));

        g2.transform(toCenterAt);

        // Sets the rendering method.
        switch ( Transform.stroke.getSelectedIndex() ) {
        case 0 : g2.draw(shape); break;
        case 1 : g2.fill(shape); break;
        case 2 : Graphics2D tempg2 = g2;
            g2.fill(shape);
            g2.setColor(Color.darkGray);
            g2.draw(shape);
            g2.setPaint(tempg2.getPaint()); break;   
        }

	g2.setStroke(oldStroke);
	g2.setPaint(oldPaint);
        g2.setTransform(saveXform);
	
	}
}
}

