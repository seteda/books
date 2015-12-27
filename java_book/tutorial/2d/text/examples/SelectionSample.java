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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextHitInfo;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * This class demonstrates how to select text with TextLayout.
 * 
 * When the mouse is dragged inside this panel, the selection range is updated
 * to reflect the mouse position. TextLayout's hitTestChar() method is used to
 * get the selection endpoint corresponding to the mouse position.
 * getLogicalHighlightShape() returns the Shape which is filled with the
 * highlight color to show the selection range.
 */
public class SelectionSample extends JPanel {

  private static String mixed = "\u05D0\u05E0\u05D9 Hello \u05DC\u05D0 \u05DE\u05D1\u05D9\u05DF "
      + "\u05E2\u05D1\u05E8\u05D9\u05EA Arabic \u0644\u0645\u062C\u0645\u0648\u0639\u0629";

  private static final FontRenderContext DEFAULT_FRC = new FontRenderContext(
      null, false, false);

  private static JFrame frame;

  private static final Hashtable<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();

  static {
    map.put(TextAttribute.FAMILY, "Serif");
    map.put(TextAttribute.SIZE, new Float(24.0));
  }

  // Colors to use for the strong and weak carets.
  private static final Color STRONG_CARET_COLOR = Color.red;
  private static final Color WEAK_CARET_COLOR = Color.black;
  // Color to use for highlighting.
  private static final Color HIGHLIGHT_COLOR = Color.pink;
  // Color to use for text.
  private static final Color TEXT_COLOR = Color.black;
  // The TextLayout to hit-test and select.
  private TextLayout textLayout;
  // The insertion index of the initial mouse click; one
  // end of the selection range. During a mouse drag this end
  // of the selection is constant.
  private int anchorEnd;

  // The insertion index of the current mouse location; the
  // other end of the selection range. This changes during a mouse
  // drag.
  private int activeEnd;

  public SelectionSample() {

    AttributedString attributedMixed = new AttributedString(mixed, map);
    AttributedCharacterIterator text = attributedMixed.getIterator();

    // Create a new TextLayout from the given text.
    textLayout = new TextLayout(text, DEFAULT_FRC);

    // Initialize activeEnd and anchorEnd.
    anchorEnd = 0;
    activeEnd = 0;

    addMouseListener(new SelectionMouseListener());
    addMouseMotionListener(new SelectionMouseMotionListener());
  }

  private static void createAndShowGUI() {

    // Create and set up the window.

    SelectionSample demo = new SelectionSample();
    frame = new JFrame("Selection Sample");

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Add contents to the window.

    frame.add(demo);
    frame.setBackground(Color.white);

    // Display the window.
    frame.pack();
    frame.setSize(new Dimension(400, 250));
    frame.setVisible(true);
  }

  /**
   * Compute a location within this Panel for textLayout's origin, such that
   * textLayout is centered horizontally and vertically.
   * 
   * Note that this location is unknown to textLayout; it is used only by this
   * Panel for positioning.
   */
  private Point2D computeLayoutOrigin() {
    Dimension size = getSize();
    Point2D.Float origin = new Point2D.Float();
    origin.x = (size.width - textLayout.getAdvance()) / 2;
    origin.y = (size.height - textLayout.getDescent() + textLayout.getAscent()) / 2;
    return origin;
  }

  /**
   * Draw textLayout and either: the selection range (if a range of characters
   * is selected), or the carets (if the selection range is 0-length).
   */

  public void paint(Graphics g) {
    Graphics2D graphics2D = (Graphics2D) g;
    Point2D origin = computeLayoutOrigin();

    // Since the selection and caret Shapes are relative to the
    // origin of textLayout, we'll translate the graphics so that
    // the origin of the graphics is where we want textLayout to
    // appear.

    graphics2D.translate(origin.getX(), origin.getY());

    // If the insertion indices of the two selection endpoints
    // are equal, the method we will draw caret(s) at the insertion index.
    // Otherwise we will draw a highlight region between the
    // insertion indices.

    boolean haveCaret = anchorEnd == activeEnd;

    if (!haveCaret) {
      // Retrieve highlight region for selection range.
      Shape highlight = textLayout.getLogicalHighlightShape(anchorEnd,
          activeEnd);
      // Fill the highlight region with the highlight color.
      graphics2D.setColor(HIGHLIGHT_COLOR);
      graphics2D.fill(highlight);
    }

    // Draw textLayout.
    graphics2D.setColor(TEXT_COLOR);
    textLayout.draw(graphics2D, 0, 0);

    if (haveCaret) {
      // Retrieve caret Shapes for the insertion index.
      Shape[] carets = textLayout.getCaretShapes(anchorEnd);

      // Draw the carets. carets[0] is the strong caret, and
      // is never null. carets[1], if it is not null, is the
      // weak caret.
      graphics2D.setColor(STRONG_CARET_COLOR);
      graphics2D.draw(carets[0]);

      if (carets[1] != null) {
        graphics2D.setColor(WEAK_CARET_COLOR);
        graphics2D.draw(carets[1]);
      }
    }
  }

  /**
   * Set the active selection endpoint to the character position of the mouse.
   */

  private class SelectionMouseMotionListener extends MouseMotionAdapter {
    public void mouseDragged(MouseEvent e) {
      Point2D origin = computeLayoutOrigin();
      // Compute the mouse location relative to
      // textLayout's origin.
      float clickX = (float) (e.getX() - origin.getX());
      float clickY = (float) (e.getY() - origin.getY());
      // Get the character position of the mouse location.
      TextHitInfo position = textLayout.hitTestChar(clickX, clickY);
      int newActiveEnd = position.getInsertionIndex();
      // If newActiveEnd is different from activeEnd, update activeEnd
      // and repaint the Panel so the new selection will be displayed.
      if (activeEnd != newActiveEnd) {
        activeEnd = newActiveEnd;
        frame.repaint();
      }
    }
  }

  /**
   * Set the active and anchor selection endpoints to the character position of
   * the mouse click.
   */

  private class SelectionMouseListener extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
      Point2D origin = computeLayoutOrigin();
      // Compute the mouse location relative to
      // TextLayout's origin.
      float clickX = (float) (e.getX() - origin.getX());
      float clickY = (float) (e.getY() - origin.getY());
      // Set the anchor and active ends of the selection
      // to the character position of the mouse location.
      TextHitInfo position = textLayout.hitTestChar(clickX, clickY);
      anchorEnd = position.getInsertionIndex();
      activeEnd = anchorEnd;
      // Repaint the Panel so the new selection will be displayed.
      frame.repaint();
    }
  }

  public static void main(String[] args) {
    // Schedule a job for the event dispatch thread:
    // creating and showing this application's GUI.
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        // Turn off metal's use of bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        createAndShowGUI();
      }
    });
  }
}
