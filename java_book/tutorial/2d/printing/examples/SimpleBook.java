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


import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.geom.*;
import java.awt.print.*;

public class SimpleBook extends JPanel implements ActionListener{

   final static Color bg = Color.white;
    final static Color fg = Color.black;
    final static Color red = Color.red;
    final static Color white = Color.white;

    final static BasicStroke stroke = new BasicStroke(2.0f);
    final static BasicStroke wideStroke = new BasicStroke(8.0f);

    final static float dash1[] = {10.0f};
    final static BasicStroke dashed = new BasicStroke(1.0f,
                                                      BasicStroke.CAP_BUTT,
							BasicStroke.JOIN_MITER,
                                                      10.0f, dash1, 0.0f);
    final static JButton button = new JButton("Print");

   public SimpleBook() {
       setBackground(bg);
        button.addActionListener(this);
   }

  public void actionPerformed(ActionEvent e) {

    // Get a PrinterJob
    PrinterJob job = PrinterJob.getPrinterJob();
    // Create a landscape page format
    PageFormat landscape = job.defaultPage();
    landscape.setOrientation(PageFormat.LANDSCAPE);
    // Set up a book
    Book bk = new Book();
    bk.append(new PaintCover(), job.defaultPage());
    bk.append(new PaintContent(), landscape);
    // Pass the book to the PrinterJob
    job.setPageable(bk);
    // Put up the dialog box
    if (job.printDialog()) {
        // Print the job if the user didn't cancel printing
        try { job.print(); }
            catch (Exception exc) { /* Handle Exception */ }
    }
}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawShapes(g2);
    }       

    static void drawShapes(Graphics2D g2){
        int gridWidth = 600 / 6;
        int gridHeight = 250 / 2;
        
        int rowspacing = 5;
        int columnspacing = 7;
        int rectWidth = gridWidth - columnspacing;
        int rectHeight = gridHeight - rowspacing;
        
        Color fg3D = Color.lightGray;
     
        g2.setPaint(fg3D);
        g2.drawRect(80, 80, 605 - 1, 265);
        g2.setPaint(fg);
             
        int x = 85;
        int y = 87;
 
    
        // draw Line2D.Double
        g2.draw(new Line2D.Double(x, y+rectHeight-1, x + rectWidth, y));
        x += gridWidth;
        
	Graphics2D temp = g2;
        // draw Rectangle2D.Double
        g2.setStroke(stroke);
        g2.draw(new Rectangle2D.Double(x, y, rectWidth, rectHeight));
        x += gridWidth;
        
        // draw  RoundRectangle2D.Double
        g2.setStroke(dashed);
        g2.draw(new RoundRectangle2D.Double(x, y, rectWidth,
                                            rectHeight, 10, 10));
        x += gridWidth;
        
        // draw Arc2D.Double
        g2.setStroke(wideStroke);
        g2.draw(new Arc2D.Double(x, y, rectWidth, rectHeight, 90,
                                 135, Arc2D.OPEN));
        x += gridWidth;
        
        // draw Ellipse2D.Double
        g2.setStroke(stroke);
        
        g2.draw(new Ellipse2D.Double(x, y, rectWidth, rectHeight));
        x += gridWidth;
        
        // draw GeneralPath (polygon)
        int x1Points[] = {x, x+rectWidth, x, x+rectWidth};
        int y1Points[] = {y, y+rectHeight, y+rectHeight, y};
       GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                                              x1Points.length);
        polygon.moveTo(x1Points[0], y1Points[0]);
        for ( int index = 1; index < x1Points.length; index++ ) {
            polygon.lineTo(x1Points[index], y1Points[index]);
        };
        polygon.closePath(); 
        
        g2.draw(polygon);

        // NEW ROW
        x = 85;
        y += gridHeight;
        
        // draw GeneralPath (polyline)
        
        int x2Points[] = {x, x+rectWidth, x, x+rectWidth};
        int y2Points[] = {y, y+rectHeight, y+rectHeight, y};
        GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                                               x2Points.length);
        polyline.moveTo (x2Points[0], y2Points[0]);
       for ( int index = 1; index < x2Points.length; index++ ) {
            polyline.lineTo(x2Points[index], y2Points[index]);
        };
        
        g2.draw(polyline);
        x += gridWidth;
                                              
        // fill Rectangle2D.Double (red)
        g2.setPaint(red);
        g2.fill(new Rectangle2D.Double(x, y, rectWidth, rectHeight));
        g2.setPaint(fg);
        x += gridWidth;
        
        // fill RoundRectangle2D.Double
        GradientPaint redtowhite = new GradientPaint(x,y,red,x+rectWidth,y,white);
        g2.setPaint(redtowhite);
        g2.fill(new RoundRectangle2D.Double(x, y, rectWidth,
                                            rectHeight, 10, 10));
        g2.setPaint(fg);
        x += gridWidth;
        
        // fill Arc2D
        g2.setPaint(red);
        g2.fill(new Arc2D.Double(x, y, rectWidth, rectHeight, 90,
                                 135, Arc2D.OPEN));
        g2.setPaint(fg);
        x += gridWidth;
            
        // fill Ellipse2D.Double
        redtowhite = new GradientPaint(x,y,red,x+rectWidth, y,white);
        g2.setPaint(redtowhite);
       g2.fill (new Ellipse2D.Double(x, y, rectWidth, rectHeight));
        g2.setPaint(fg);                      
        x += gridWidth;
        // fill and stroke GeneralPath
        int x3Points[] = {x, x+rectWidth, x, x+rectWidth};
        int y3Points[] = {y, y+rectHeight, y+rectHeight, y};
        GeneralPath filledPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                                                    x3Points.length);
        filledPolygon.moveTo(x3Points[0], y3Points[0]);
        for ( int index = 1; index < x3Points.length; index++ ) {
            filledPolygon.lineTo(x3Points[index], y3Points[index]);
        };
        filledPolygon.closePath();
        g2.setPaint(red);
        g2.fill(filledPolygon);
        g2.setPaint(fg);
        g2.draw(filledPolygon);
	g2.setStroke(temp.getStroke());
    }



  public static void main(String[] args) {
        WindowListener l = new WindowAdapter() {
                public void windowClosing(WindowEvent e) {System.exit(0);}
                public void windowClosed(WindowEvent e) {System.exit(0);}
        };
        JFrame f = new JFrame();
        f.addWindowListener(l);
        JPanel panel = new JPanel();
        panel.add(button);
        f.getContentPane().add(BorderLayout.SOUTH, panel);
        f.getContentPane().add(BorderLayout.CENTER, new SimpleBook());
        f.setSize(775, 450);
        f.show();
  }

}

class PaintCover implements Printable {
  Font fnt = new Font("Helvetica-Bold", Font.PLAIN, 48);

  public int print(Graphics g, PageFormat pf, int pageIndex)
        throws PrinterException {
    g.setFont(fnt);
    g.setColor(Color.black);
    g.drawString("Sample Shapes", 100, 200);
    return Printable.PAGE_EXISTS;
  }
}

class PaintContent implements Printable {
  public int print(Graphics g, PageFormat pf, int pageIndex)
  throws PrinterException {
      SimpleBook.drawShapes((Graphics2D) g);
      return Printable.PAGE_EXISTS;

  }


}

