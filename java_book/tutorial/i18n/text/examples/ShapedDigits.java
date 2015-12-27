/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 *
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
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.text.*;
import java.util.HashMap;

public class ShapedDigits extends Applet {
    ShaperPanel panel;

    static final String defaultFontName = "Lucida Sans";

    public ShapedDigits() {
        panel = new ShaperPanel(defaultFontName);
    }

    public ShapedDigits(String fontname) {
        panel = new ShaperPanel(fontname);
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
        ShapedDigits shapedDigits = new ShapedDigits(fontname);
        shapedDigits.init();
        shapedDigits.start();

        Frame f = new Frame("ShapedDigits");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.add("Center", shapedDigits);
        f.setSize(600, 250);
        f.setVisible(true);
    }

    public String getAppletInfo() {
        return "Shaped Digits Sample";
    }

    static class ShaperPanel extends Panel {
        String fontname;
        TextLayout[][] layouts;
        String[] titles;

        private static final String text =
            "-123 (Latin) 456.00 (Arabic) \u0641\u0642\u0643 -789 (Thai) \u0e01\u0e33 01.23";

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

        ShaperPanel(String fontname) {
            setBackground(Color.white);
            setForeground(Color.black);

            Font textfont = new Font(fontname, Font.PLAIN, 12);
            System.out.println("asked for: " + fontname + " and got: " + textfont.getFontName());
            setFont(textfont);

            Font font = new Font(fontname, Font.PLAIN, 18);
            System.out.println("asked for: " + fontname + " and got: " + font.getFontName());

            FontRenderContext frc = new FontRenderContext(null, false, false);

            layouts = new TextLayout[5][2];

            HashMap map = new HashMap();
            map.put(TextAttribute.FONT, font);
            layouts[0][0] = new TextLayout(text, map, frc);
            AttributedCharacterIterator iter = new AttributedString(text, map).getIterator();
            layouts[0][1] = new LineBreakMeasurer(iter, frc).nextLayout(Float.MAX_VALUE);

            NumericShaper arabic = NumericShaper.getShaper(NumericShaper.ARABIC);
            map.put(TextAttribute.NUMERIC_SHAPING, arabic);
            layouts[1][0] = new TextLayout(text, map, frc);
            iter = new AttributedString(text, map).getIterator();
            layouts[1][1] = new LineBreakMeasurer(iter, frc).nextLayout(Float.MAX_VALUE);

            NumericShaper contextualArabic = NumericShaper.getContextualShaper(NumericShaper.ARABIC, NumericShaper.ARABIC);
            map.put(TextAttribute.NUMERIC_SHAPING, contextualArabic);
            layouts[2][0] = new TextLayout(text, map, frc);
            iter = new AttributedString(text, map).getIterator();
            layouts[2][1] = new LineBreakMeasurer(iter, frc).nextLayout(Float.MAX_VALUE);

            NumericShaper contextualArabicASCII = NumericShaper.getContextualShaper(NumericShaper.ARABIC);
            map.put(TextAttribute.NUMERIC_SHAPING, contextualArabicASCII);
            layouts[3][0] = new TextLayout(text, map, frc);
            iter = new AttributedString(text, map).getIterator();
            layouts[3][1] = new LineBreakMeasurer(iter, frc).nextLayout(Float.MAX_VALUE);

            NumericShaper contextualAll = NumericShaper.getContextualShaper(NumericShaper.ALL_RANGES);
            map.put(TextAttribute.NUMERIC_SHAPING, contextualAll);
            layouts[4][0] = new TextLayout(text, map, frc);
            iter = new AttributedString(text, map).getIterator();
            layouts[4][1] = new LineBreakMeasurer(iter, frc).nextLayout(Float.MAX_VALUE);

            titles = new String[] {
                "Latin - all digits are Latin (ASCII)",
                "Arabic - all digits are Arabic",
                "Contextual Arabic Default Arabic - only leading digits and digits following Arabic text are Arabic",
                "Contextual Arabic Default Latin - only digits following Arabic text are Arabic",
                "Contextual All Default Latin - leading digits are Latin (ASCII), others correspond to context"
             };
        }

        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;

            float x = 5;
            float y = 5;

            for (int i = 0; i < layouts.length; ++i) {
                y += 18;
                g2d.drawString(titles[i], x, y);
                y += 4;

                for (int j = 0; j < 2; ++j) {
                    y += layouts[i][j].getAscent();
                    layouts[i][j].draw(g2d, x, y);
                    y += layouts[i][j].getDescent() + layouts[i][j].getLeading();
                }
            }
        }
    }
}
