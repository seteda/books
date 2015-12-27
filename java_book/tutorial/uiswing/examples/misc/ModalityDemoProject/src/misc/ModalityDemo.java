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

package misc;

/*
 * ModalityDemo.java
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;

import javax.swing.*;

public class ModalityDemo {
    
    // first document: frame, modeless dialog, document-modal dialog
    private JFrame f1;
    private JDialog d2;
    private JDialog d3;
    
    // second document: frame, modeless dialog, document-modal dialog
    private JFrame f4;
    private JDialog d5;
    private JDialog d6;
    
    // third document: modal excluded frame
    private JFrame f7;
    
    // fourth document: frame, file dialog (application-modal)
    private JFrame f8;
    
    // radiobuttons in f1
    JRadioButton rb11, rb12, rb13;
    // text field in d2
    JTextField tf2;
    
    // label in d3
    JLabel l3;
    
    // radiobuttons in f4
    JRadioButton rb41, rb42, rb43;
    // text field in d5
    JTextField tf5;
    
    // label in d6
    JLabel l6;
    
    // radiobuttons in f7
    JRadioButton rb71, rb72, rb73;
   
    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ModalityDemo md = new ModalityDemo();
                md.createAndShowGUI();
                md.start();
            }
        });
    }
    
    //start frames
    private void start() {
        f1.setVisible(true);
        f4.setVisible(true);
        f7.setVisible(true);
        f8.setVisible(true);
    }
    
    private static WindowListener closeWindow = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            e.getWindow().dispose();
        }
    };
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method is invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        Insets ins = Toolkit.getDefaultToolkit().getScreenInsets(gc);
        int sw = gc.getBounds().width - ins.left - ins.right;
        int sh = gc.getBounds().height - ins.top - ins.bottom;
        
        // first document
        
        // frame f1
        
        f1 = new JFrame("Book 1 (parent frame)");
        f1.setBounds(32, 32, 300, 200);
        f1.addWindowListener(closeWindow);
        // create radio buttons
        rb11 = new JRadioButton("Biography", true);
        rb12 = new JRadioButton("Funny tale", false);
        rb13 = new JRadioButton("Sonnets", false);
        // place radio buttons into a single group
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(rb11);
        bg1.add(rb12);
        bg1.add(rb13);
        JButton b1 = new JButton("OK");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get label of selected radiobutton
                String title = null;
                if (rb11.isSelected()) {
                    title = rb11.getText();
                } else if (rb12.isSelected()) {
                    title = rb12.getText();
                } else {
                    title = rb13.getText();
                }
                // prepend radio button label to dialogs' titles
                d2.setTitle(title + " (modeless dialog)");
                d3.setTitle(title + " (document-modal dialog)");
                d2.setVisible(true);
            }
        });
        Container cp1 = f1.getContentPane();
        // create three containers to improve layouting
        cp1.setLayout(new GridLayout(1, 3));
        // an empty container
        Container cp11 = new Container();
        // a container to layout components
        Container cp12 = new Container();
        // an empty container
        Container cp13 = new Container();
        // add a button into a separate panel
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        p1.add(b1);
        // add radio buttons and the OK button one after another into a single column
        cp12.setLayout(new GridLayout(4, 1));
        cp12.add(rb11);
        cp12.add(rb12);
        cp12.add(rb13);
        cp12.add(p1);
        // add three containers
        cp1.add(cp11);
        cp1.add(cp12);
        cp1.add(cp13);
        
        // dialog d2
        
        d2 = new JDialog(f1);
        d2.setBounds(132, 132, 300, 200);
        d2.addWindowListener(closeWindow);
        JLabel l2 = new JLabel("Enter your name: ");
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        tf2 = new JTextField(12);
        JButton b2 = new JButton("OK");
        b2.setHorizontalAlignment(SwingConstants.CENTER);
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //pass a name into the document modal dialog
                l3.setText("by " + tf2.getText());
                d3.setVisible(true);
            }
        });
        Container cp2 = d2.getContentPane();
        // add label, text field and button one after another into a single column
        cp2.setLayout(new BorderLayout());
        cp2.add(l2, BorderLayout.NORTH);
        cp2.add(tf2, BorderLayout.CENTER);
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        p2.add(b2);
        cp2.add(p2, BorderLayout.SOUTH);
                
        // dialog d3
        
        d3 = new JDialog(d2, "", Dialog.ModalityType.DOCUMENT_MODAL);
        d3.setBounds(232, 232, 300, 200);
        d3.addWindowListener(closeWindow);
        JTextArea ta3 = new JTextArea();
        l3 = new JLabel();
        l3.setHorizontalAlignment(SwingConstants.RIGHT);
        Container cp3 = d3.getContentPane();
        cp3.setLayout(new BorderLayout());
        cp3.add(new JScrollPane(ta3), BorderLayout.CENTER);
        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout(FlowLayout.RIGHT));
        p3.add(l3);
        cp3.add(p3, BorderLayout.SOUTH);
        
        // second document
        
        // frame f4
        
        f4 = new JFrame("Book 2 (parent frame)");
        f4.setBounds(sw - 300 - 32, 32, 300, 200);
        f4.addWindowListener(closeWindow);
        // create radio buttons
        rb41 = new JRadioButton("Biography", true);
        rb42 = new JRadioButton("Funny tale", false);
        rb43 = new JRadioButton("Sonnets", false);
        // place radio buttons into a single group
        ButtonGroup bg4 = new ButtonGroup();
        bg4.add(rb41);
        bg4.add(rb42);
        bg4.add(rb43);
        JButton b4 = new JButton("OK");
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get label of selected radiobutton
                String title = null;
                if (rb41.isSelected()) {
                    title = rb41.getText();
                } else if (rb42.isSelected()) {
                    title = rb42.getText();
                } else {
                    title = rb43.getText();
                }
                // prepend radiobutton label to dialogs' titles
                d5.setTitle(title + " (modeless dialog)");
                d6.setTitle(title + " (document-modal dialog)");
                d5.setVisible(true);
            }
        });
        Container cp4 = f4.getContentPane();
        // create three containers to improve layouting
        cp4.setLayout(new GridLayout(1, 3));
        Container cp41 = new Container();
        Container cp42 = new Container();
        Container cp43 = new Container();
        // add the button into a separate panel
        JPanel p4 = new JPanel();
        p4.setLayout(new FlowLayout());
        p4.add(b4);
        // add radiobuttons and the OK button one after another into a single column
        cp42.setLayout(new GridLayout(4, 1));
        cp42.add(rb41);
        cp42.add(rb42);
        cp42.add(rb43);
        cp42.add(p4);
        //add three containers
        cp4.add(cp41);
        cp4.add(cp42);
        cp4.add(cp43);
        
        // dialog d5
        
        d5 = new JDialog(f4);
        d5.setBounds(sw - 400 - 32, 132, 300, 200);
        d5.addWindowListener(closeWindow);
        JLabel l5 = new JLabel("Enter your name: ");
        l5.setHorizontalAlignment(SwingConstants.CENTER);
        tf5 = new JTextField(12);
        tf5.setHorizontalAlignment(SwingConstants.CENTER);
        JButton b5 = new JButton("OK");
        b5.setHorizontalAlignment(SwingConstants.CENTER);
        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //pass a name into the document modal dialog
                l6.setText("by " + tf5.getText());
                d6.setVisible(true);
            }
        });
        Container cp5 = d5.getContentPane();
        // add label, text field and button one after another into a single column
        cp5.setLayout(new BorderLayout());
        cp5.add(l5, BorderLayout.NORTH);
        cp5.add(tf5, BorderLayout.CENTER);
        JPanel p5 = new JPanel();
        p5.setLayout(new FlowLayout());
        p5.add(b5);
        cp5.add(p5, BorderLayout.SOUTH);
        
        // dialog d6
        
        d6 = new JDialog(d5, "", Dialog.ModalityType.DOCUMENT_MODAL);
        d6.setBounds(sw - 500 - 32, 232, 300, 200);
        d6.addWindowListener(closeWindow);
        JTextArea ta6 = new JTextArea();
        l6 = new JLabel();
        l6.setHorizontalAlignment(SwingConstants.RIGHT);
        Container cp6 = d6.getContentPane();
        cp6.setLayout(new BorderLayout());
        cp6.add(new JScrollPane(ta6), BorderLayout.CENTER);
        JPanel p6 = new JPanel();
        p6.setLayout(new FlowLayout(FlowLayout.RIGHT));
        p6.add(l6);
        cp6.add(p6, BorderLayout.SOUTH);
                
        // third document
        
        // frame f7
        
        f7 = new JFrame("Classics (excluded frame)");
        f7.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        f7.setBounds(32, sh - 200 - 32, 300, 200);
        f7.addWindowListener(closeWindow);
        JLabel l7 = new JLabel("Famous writers: ");
        l7.setHorizontalAlignment(SwingConstants.CENTER);
        // create radio buttons
        rb71 = new JRadioButton("Burns", true);
        rb72 = new JRadioButton("Dickens", false);
        rb73 = new JRadioButton("Twain", false);
        // place radio buttons into a single group
        ButtonGroup bg7 = new ButtonGroup();
        bg7.add(rb71);
        bg7.add(rb72);
        bg7.add(rb73);
        Container cp7 = f7.getContentPane();
        // create three containers to improve layouting
        cp7.setLayout(new GridLayout(1, 3));
        Container cp71 = new Container();
        Container cp72 = new Container();
        Container cp73 = new Container();
        // add the label into a separate panel
        JPanel p7 = new JPanel();
        p7.setLayout(new FlowLayout());
        p7.add(l7);
        // add a label and radio buttons one after another into a single column
        cp72.setLayout(new GridLayout(4, 1));
        cp72.add(p7);
        cp72.add(rb71);
        cp72.add(rb72);
        cp72.add(rb73);
        // add three containers
        cp7.add(cp71);
        cp7.add(cp72);
        cp7.add(cp73);
        
        // fourth document
        
        // frame f8
        
        f8 = new JFrame("Feedback (parent frame)");
        f8.setBounds(sw - 300 - 32, sh - 200 - 32, 300, 200);
        f8.addWindowListener(closeWindow);
        JButton b8 = new JButton("Rate yourself");
        b8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(null,
                        "I really like my book",
                        "Question (application-modal dialog)",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
            }
        });
        Container cp8 = f8.getContentPane();
        cp8.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        cp8.add(b8);
    }
}
