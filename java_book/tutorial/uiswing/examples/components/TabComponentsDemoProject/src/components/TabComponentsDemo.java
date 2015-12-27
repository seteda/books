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

package components;

/*
 * TabComponentDemo.java requires one additional file:
 *   ButtonTabComponent.java
 */



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/*
 * Creating and using TabComponentsDemo example  
 */ 
public class TabComponentsDemo extends JFrame {    

    private final int tabNumber = 5;
    private final JTabbedPane pane = new JTabbedPane();
    private JMenuItem tabComponentsItem;
    private JMenuItem scrollLayoutItem;
    
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                //Turn off metal's use of bold fonts
	        UIManager.put("swing.boldMetal", Boolean.FALSE);
                new TabComponentsDemo("TabComponentsDemo").runTest();
            }
        });
    }
    
    public TabComponentsDemo(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initMenu();        
        add(pane);        
    }
    
    public void runTest() {
        pane.removeAll();
        for (int i = 0; i < tabNumber; i++) {
            String title = "Tab " + i;
            pane.add(title, new JLabel(title));
            initTabComponent(i);
        }
        tabComponentsItem.setSelected(true);
        pane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        scrollLayoutItem.setSelected(false);
        setSize(new Dimension(400, 200));
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
    private void initTabComponent(int i) {
        pane.setTabComponentAt(i,
                 new ButtonTabComponent(pane));
    }    

    //Setting menu
    
    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        //create Options menu
        tabComponentsItem = new JCheckBoxMenuItem("Use TabComponents", true);
        tabComponentsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.ALT_MASK));
        tabComponentsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < pane.getTabCount(); i++) {
                    if (tabComponentsItem.isSelected()) {
                        initTabComponent(i);
                    } else {
                        pane.setTabComponentAt(i, null);
                    }
                }
            }
        });
        scrollLayoutItem = new JCheckBoxMenuItem("Set ScrollLayout");
        scrollLayoutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));
        scrollLayoutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (pane.getTabLayoutPolicy() == JTabbedPane.WRAP_TAB_LAYOUT) {
                    pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
                } else {
                    pane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
                }
            }
        });
        JMenuItem resetItem = new JMenuItem("Reset JTabbedPane");
        resetItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK));
        resetItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runTest();
            }
        });
       
        JMenu optionsMenu = new JMenu("Options");
        optionsMenu.add(tabComponentsItem);
        optionsMenu.add(scrollLayoutItem);
        optionsMenu.add(resetItem);
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);
    }
}

