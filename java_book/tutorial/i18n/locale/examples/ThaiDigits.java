/*
 * Copyright (c) 1995, 2011, Oracle and/or its affiliates. All rights reserved.
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


/* ThaiDigits.java requires no other files. */
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ThaiDigits extends JPanel {

  AbstractDocument doc;
  protected JTextPane textPane;

  public ThaiDigits() {
    super(new GridBagLayout());

    textPane = new JTextPane();
    textPane.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textPane);

    //Add Components to this panel.
    GridBagConstraints c = new GridBagConstraints();
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1.0;
    c.weighty = 1.0;
    add(scrollPane, c);

    String outputString = new String();
    Locale[] thaiLocale = {
      new Locale("th"),
      new Locale("th", "TH"),
      new Locale("th", "TH", "TH")};
    for (Locale locale : thaiLocale) {
      NumberFormat nf = NumberFormat.getNumberInstance(locale);
      outputString = outputString + locale.toString() + ": ";
      outputString = outputString + nf.format(573.34) + "\n";
    }

    StyledDocument styledDoc = textPane.getStyledDocument();
    if (styledDoc instanceof AbstractDocument) {
      doc = (AbstractDocument) styledDoc;
    } else {
      System.err.println("Text pane's document isn't an AbstractDocument!");
      System.exit(-1);
    }

    SimpleAttributeSet attrs = new SimpleAttributeSet();
    StyleConstants.setFontFamily(attrs, "SansSerif");
    StyleConstants.setFontSize(attrs, 24);

    try {
      doc.insertString(doc.getLength(), outputString, attrs);
    } catch (BadLocationException ble) {
      System.err.println("Couldn't insert initial text.");
    }
  }

  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from the
   * event dispatch thread.
   */
  private static void createAndShowGUI() {
    //Create and set up the window.
    JFrame frame = new JFrame("ThaiDigits");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Add contents to the window.
    frame.add(new ThaiDigits());

    //Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    //Schedule a job for the event dispatch thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {

      public void run() {
        createAndShowGUI();
      }
    });
  }
}
