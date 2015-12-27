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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * This class demonstrates how to use TextLayout to respond to left and right
 * arrow keys.
 * 
 * This class maintains an insertion offset, which is initially 0. It displays a
 * TextLayout and the caret(s) for the insertion offset. The insertion offset
 * can be moved by pressing the left and right arrow keys. TextLayout's
 * getNextLeftHit() and getNextRightHit() methods are used to get the new
 * insertion offset.
 */

public class ArrowKeySample extends JPanel implements KeyListener {

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

  // The TextLayout to draw and caret through.
  private static TextLayout textLayout;

  // The current insertion index.
  private static int insertionIndex = 0;

  public ArrowKeySample() {

    AttributedString attributedMixed = new AttributedString(mixed, map);
    AttributedCharacterIterator text = attributedMixed.getIterator();

    // Create a new TextLayout from the given text.
    textLayout = new TextLayout(text, DEFAULT_FRC);
  }

  private static void createAndShowGUI() {
    // Create and set up the window.
    ArrowKeySample demo = new ArrowKeySample();
    frame = new JFrame("Arrow Key Sample");
    frame.addKeyListener(demo);
    frame.setFocusable(true);
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
   * Compute a location within this Component for textLayout's origin, such that
   * textLayout is centered horizontally and vertically.
   * 
   * Note that this location is unknown to textLayout; it is used only by this
   * Component for positioning.
   */

  private Point2D computeLayoutOrigin() {
    Dimension size = getSize();
    Point2D.Float origin = new Point2D.Float();
    origin.x = (size.width - textLayout.getAdvance()) / 2;
    origin.y = (size.height - textLayout.getDescent() + textLayout.getAscent()) / 2;
    return origin;
  }

  /**
   * Draw textLayout and the carets corresponding to the current insertion
   * index.
   */

  public void paint(Graphics g) {
    Graphics2D graphics2D = (Graphics2D) g;
    Point2D origin = computeLayoutOrigin();

    // Since the caret Shapes are relative to the origin of
    // textLayout, we'll translate the graphics so that the
    // origin of the graphics is where we want textLayout to
    // appear.
    graphics2D.translate(origin.getX(), origin.getY());

    // Draw textLayout.
    textLayout.draw(graphics2D, 0, 0);

    // Retrieve caret Shapes for the current insertion index.
    Shape[] carets = textLayout.getCaretShapes(insertionIndex);

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

  @Override
  public void keyTyped(KeyEvent e) {
    // Not implemented

  }

  @Override
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
      handleArrowKey(keyCode == KeyEvent.VK_RIGHT);
    }
  }

  private void handleArrowKey(boolean rightArrow) {
    TextHitInfo newPosition;
    if (rightArrow) {
      newPosition = textLayout.getNextRightHit(insertionIndex);
    } else {
      newPosition = textLayout.getNextLeftHit(insertionIndex);
    }

    // getNextRightHit() / getNextLeftHit() will return null if
    // there is not a caret position to the right (left) of the
    // current position.
    if (newPosition != null) {
      // Update insertionIndex.
      insertionIndex = newPosition.getInsertionIndex();
      // Repaint the Component so the new caret(s) will be displayed.
      frame.repaint();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // Not implemented

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
