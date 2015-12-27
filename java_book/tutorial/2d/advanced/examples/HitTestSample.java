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
import java.awt.font.FontRenderContext;
import java.awt.font.TextHitInfo;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.awt.font.TextAttribute;         
import java.util.Hashtable;
import java.util.Enumeration;


/**
 * This class demonstrates how to hit-test a TextLayout.  Hit-testing
 * is the mapping of a graphical location to a character position within
 * text.
 *
 * This class constructs a TextLayout from an AttributedCharacterIterator
 * and displays the TextLayout.  When the mouse is clicked inside this
 * Component, the mouse position is mapped to a character position, and the
 * carets for this character position are displayed.
 */
public class HitTestSample extends JApplet {

    // Colors to use for the strong and weak carets.
    private static final Color STRONG_CARET_COLOR = Color.red;
    private static final Color WEAK_CARET_COLOR = Color.black;

    // The TextLayout to draw and hit-test.
    private TextLayout textLayout;

    // The insertion index of the most recent mouse click.
    private int insertionIndex;

    private static final FontRenderContext DEFAULT_FRC =
                                new FontRenderContext(null, false, false);

    private static final Hashtable map = new Hashtable();
    static {
        map.put(TextAttribute.SIZE, new Float(18.0));
    }             

    // text to use in samples:
    private static AttributedString helloWorld = 
		new AttributedString("Hello World", map);

    Dimension preferredSize = new Dimension(400, 250);

    HitPane hitPane;

    public void init() {
	
	AttributedCharacterIterator text = helloWorld.getIterator();
	buildUI(getContentPane(), text);
    }

    public void buildUI(Container container, AttributedCharacterIterator text){


        // Create a new TextLayout from the given text.
        textLayout = new TextLayout(text, DEFAULT_FRC);

        // Initilize insertionIndex.
        insertionIndex = 0;

	hitPane = new HitPane();
        container.add(hitPane, BorderLayout.CENTER);
        hitPane.addMouseListener(new HitTestMouseListener());
    }

    public Dimension getPreferredSize() {
        return preferredSize;
    }

    /**
     * Compute a location within this Component for textLayout's origin,
     * such that textLayout is centered horizontally and vertically.
     *
     * Note that this location is unknown to textLayout;  it is used only
     * by this Component for positioning.
     */
    private Point2D computeLayoutOrigin() {

        Dimension size = getPreferredSize();
        Point2D.Float origin = new Point2D.Float();

        origin.x = (float) (size.width - textLayout.getAdvance()) / 2;
        origin.y = (float) (size.height - textLayout.getDescent() + textLayout.getAscent()) / 2;
        return origin;
    }

    class HitPane extends JPanel {
	
	public void HitPane() {}

    	/**
     	* Draw textLayout and the carets corresponding to the most recent
     	* mouse click (if any).
     	*/
    	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.white);
        	Graphics2D graphics2D = (Graphics2D) g;

        	Point2D origin = computeLayoutOrigin();

        	// Since the caret Shapes are relative to the origin 
        	// of textLayout, we'll translate the graphics so that 
        	// the origin of the graphics is where we want textLayout
        	// to appear.

        	graphics2D.translate(origin.getX(), origin.getY());

        	// Draw textLayout.
        	textLayout.draw(graphics2D, 0, 0);

        	// Retrieve caret Shapes for insertionIndex.
        	Shape[] carets = textLayout.getCaretShapes(insertionIndex);

        	// Draw the carets.  carets[0] is the strong caret, and
        	// is never null.  carets[1], if it is not null, is the
        	// weak caret.
        	graphics2D.setColor(STRONG_CARET_COLOR);
        	graphics2D.draw(carets[0]);

        	if (carets[1] != null) {
            	graphics2D.setColor(WEAK_CARET_COLOR);
            	graphics2D.draw(carets[1]);
        }
    }

  }

    private class HitTestMouseListener extends MouseAdapter {
	

        /**
         * Compute the character position of the mouse click.
         */
        public void mouseClicked(MouseEvent e) {

            Point2D origin = computeLayoutOrigin();

            // Compute the mouse click location relative to
            // textLayout's origin.
            float clickX = (float) (e.getX() - origin.getX());
            float clickY = (float) (e.getY() - origin.getY());

            // Get the character position of the mouse click.
            TextHitInfo currentHit = textLayout.hitTestChar(clickX, clickY);
            insertionIndex = currentHit.getInsertionIndex();

            // Repaint the Component so the new caret(s) will be displayed.
            hitPane.repaint();
        }
    }

    public static void main(String[] args) {

        JFrame sampleFrame = new JFrame("HitTestSample");
	AttributedCharacterIterator text = helloWorld.getIterator();
	final HitTestSample controller = new HitTestSample();
        controller.buildUI(sampleFrame.getContentPane(), text);

        sampleFrame.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

        sampleFrame.setSize(new Dimension(400, 250));                                            
        sampleFrame.setVisible(true);
    }
}
