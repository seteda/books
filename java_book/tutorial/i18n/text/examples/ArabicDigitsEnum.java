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


/**
 * This example displays ASCII digits, 0 through 9, as Arabit digits.
 */
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.text.*;
import java.util.HashMap;

public class ArabicDigitsEnum extends Applet {
    ArabicDigitsEnumPanel panel;

    static final String defaultFontName = "Lucida Sans";

    public ArabicDigitsEnum() {
        panel = new ArabicDigitsEnumPanel(defaultFontName);
    }

    public ArabicDigitsEnum(String fontname) {
        panel = new ArabicDigitsEnumPanel(fontname);
    }

    public void init() {
        setLayout(new BorderLayout());
        add("Center", panel);
    }

    public void destroy() {
        remove(panel);
    }

    public static void main(String args[]) {
        String fontname = defaultFontName;
        if (args.length > 0) {
            fontname = args[0];
        }
        ArabicDigitsEnum arabicDigits = new ArabicDigitsEnum(fontname);
        arabicDigits.init();
        arabicDigits.start();

        Frame f = new Frame("ArabicDigitsEnum");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.add("Center", arabicDigits);
        f.setSize(600, 250);
        f.setVisible(true);
    }

    public String getAppletInfo() {
        return "Arabic Digits Example";
    }

    static class ArabicDigitsEnumPanel extends Panel {
        String fontname;
	TextLayout layout;

	private static final String text = "0 1 2 3 4 5 6 7 8 9";

        void dumpChars(char[] chars) {
            for (int i = 0; i < chars.length; ++i) {
                char c = chars[i];
                if (c < 0x7f) {
                    System.out.print(c);
                } else {
                    String n = Integer.toHexString(c);
                    n = "0000".substring(n.length()) + n;
                    System.out.print("0x" + n);
                }
            }
            System.out.println();
        }

        ArabicDigitsEnumPanel(String fontname) {
	    HashMap map = new HashMap();
	    Font font = new Font(fontname, Font.PLAIN, 60);
	    map.put(TextAttribute.FONT, font);
	    map.put(TextAttribute.NUMERIC_SHAPING,
	        NumericShaper.getShaper(NumericShaper.Range.ARABIC));
	    FontRenderContext frc = new FontRenderContext(null, false, false);
	    layout = new TextLayout(text, map, frc);
        }

        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;

            layout.draw(g2d, 10, 50);
        }
    }
}
