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
 * This relies on having the Java Look and Feel Graphics Repository
 * (jlfgr-1_0.jar) in the class path.  You can download this file
 * from http://java.sun.com/developer/techDocs/hi/repository/.
 * Put it in the class path using one of the following commands
 * (assuming jlfgr-1_0.jar is in a subdirectory named jars):
 *
 *   java -cp .;jars/jlfgr-1_0.jar ActionDemo [Microsoft Windows]
 *   java -cp .:jars/jlfgr-1_0.jar ActionDemo [UNIX]
 *
 */

import javax.swing.AbstractAction;
import javax.swing.Action;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;

public class ActionDemo extends JPanel
                        implements ItemListener {
    protected JTextArea textArea;
    protected String newline = "\n";
    protected Action leftAction, middleAction, rightAction;
    protected JCheckBoxMenuItem[] cbmi;

    public ActionDemo() {
        super(new BorderLayout());

        //Create a scrolled text area.
        textArea = new JTextArea(5, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Lay out the content pane.
        setPreferredSize(new Dimension(450, 150));
        add(scrollPane, BorderLayout.CENTER);

        //Create the actions shared by the toolbar and menu.
        leftAction =   new LeftAction(  "Go left",
                                        createNavigationIcon("Back24"),
                                        "This is the left button.", 
                                        new Integer(KeyEvent.VK_L));
        middleAction = new MiddleAction("Do something",
                                        createNavigationIcon("Up24"),
                                        "This is the middle button.", 
                                        new Integer(KeyEvent.VK_M));
        rightAction =  new RightAction( "Go right",
                                        createNavigationIcon("Forward24"),
                                        "This is the right button.", 
                                        new Integer(KeyEvent.VK_R));
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createNavigationIcon(String imageName) {
        String imgLocation = "toolbarButtonGraphics/navigation/"
                             + imageName
                             + ".gif";
        java.net.URL imageURL = ActionDemo.class.getResource(imgLocation);

        if (imageURL == null) {
            System.err.println("Resource not found: "
                               + imgLocation);
            return null;
        } else {
            return new ImageIcon(imageURL);
        }
    }

    public JMenuBar createMenuBar() {
        JMenuItem menuItem = null;
        JMenuBar menuBar;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Create the first menu.
        JMenu mainMenu = new JMenu("Menu");

        Action[] actions = {leftAction, middleAction, rightAction};
        for (int i = 0; i < actions.length; i++) {
            menuItem = new JMenuItem(actions[i]);
            menuItem.setIcon(null); //arbitrarily chose not to use icon
            mainMenu.add(menuItem);
        }

        //Set up the menu bar.
        menuBar.add(mainMenu);
        menuBar.add(createAbleMenu());
        return menuBar;
    }

    public void createToolBar() {
        JButton button = null;

        //Create the toolbar.
        JToolBar toolBar = new JToolBar();
        add(toolBar, BorderLayout.PAGE_START);

        //first button
        button = new JButton(leftAction);
        if (button.getIcon() != null) {
            button.setText(""); //an icon-only button
        }
        toolBar.add(button);

        //second button
        button = new JButton(middleAction);
        if (button.getIcon() != null) {
            button.setText(""); //an icon-only button
        }
        toolBar.add(button);

        //third button
        button = new JButton(rightAction);
        if (button.getIcon() != null) {
            button.setText(""); //an icon-only button
        }
        toolBar.add(button);
    }

    protected JMenu createAbleMenu() {
        JMenu ableMenu = new JMenu("Action State");
        cbmi = new JCheckBoxMenuItem[3];

        cbmi[0] = new JCheckBoxMenuItem("First action enabled");
        cbmi[1] = new JCheckBoxMenuItem("Second action enabled");
        cbmi[2] = new JCheckBoxMenuItem("Third action enabled");

        for (int i = 0; i < cbmi.length; i++) {
            cbmi[i].setSelected(true);
            cbmi[i].addItemListener(this);
            ableMenu.add(cbmi[i]);
        }

        return ableMenu;
    }

    public void itemStateChanged(ItemEvent e) {
        JCheckBoxMenuItem mi = (JCheckBoxMenuItem)(e.getSource());
        boolean selected =
            (e.getStateChange() == ItemEvent.SELECTED);

        //Set the enabled state of the appropriate Action.
        if (mi == cbmi[0]) {
            leftAction.setEnabled(selected);
        } else if (mi == cbmi[1]) {
            middleAction.setEnabled(selected);
        } else if (mi == cbmi[2]) {
            rightAction.setEnabled(selected);
        }
    }

    public void displayResult(String actionDescription,
                                 ActionEvent e) {
        String s = ("Action event detected: "
                  + actionDescription
                  + newline
                  + "    Event source: " + e.getSource()
                  + newline);
        textArea.append(s);
    }

    public class LeftAction extends AbstractAction {
        public LeftAction(String text, ImageIcon icon,
                          String desc, Integer mnemonic) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }
        public void actionPerformed(ActionEvent e) {
            displayResult("Action for first button/menu item", e);
        }
    }

    public class MiddleAction extends AbstractAction {
        public MiddleAction(String text, ImageIcon icon,
                            String desc, Integer mnemonic) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }
        public void actionPerformed(ActionEvent e) {
            displayResult("Action for second button/menu item", e);
        }
    }

    public class RightAction extends AbstractAction {
        public RightAction(String text, ImageIcon icon,
                           String desc, Integer mnemonic) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }
        public void actionPerformed(ActionEvent e) {
            displayResult("Action for third button/menu item", e);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the 
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ActionDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create/set menu bar and content pane.
        ActionDemo demo = new ActionDemo();
        frame.setJMenuBar(demo.createMenuBar());
        demo.createToolBar();
        demo.setOpaque(true); //content panes must be opaque
        frame.setContentPane(demo);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
