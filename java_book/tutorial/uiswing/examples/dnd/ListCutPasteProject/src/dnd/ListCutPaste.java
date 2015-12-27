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

package dnd;

/**
 * ListCutPaste.java requires the following files:
 *     ListTransferHandler.java
 *     TransferActionListener.java
 */
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import javax.swing.*;

/**
 * The ListCutPaste example illustrates cut, copy, paste
 * and drag and drop using three instances of JList.
 * The TransferActionListener class listens for one of
 * the CCP actions and, when one occurs, forwards the
 * action to the component which currently has the focus.
 */
public class ListCutPaste extends JPanel {
    ListTransferHandler lh;

    public ListCutPaste() {
        super(new BorderLayout());
        lh = new ListTransferHandler();

        JPanel panel = new JPanel(new GridLayout(1, 3));
        DefaultListModel list1Model = new DefaultListModel();
        list1Model.addElement("alpha");
        list1Model.addElement("beta");
        list1Model.addElement("gamma");
        list1Model.addElement("delta");
        list1Model.addElement("epsilon");
        list1Model.addElement("zeta");
        JList list1 = new JList(list1Model);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp1 = new JScrollPane(list1);
        sp1.setPreferredSize(new Dimension(400,100));
        list1.setDragEnabled(true);
        list1.setTransferHandler(lh);
        list1.setDropMode(DropMode.ON_OR_INSERT);
        setMappings(list1);
        JPanel pan1 = new JPanel(new BorderLayout());
        pan1.add(sp1, BorderLayout.CENTER);
        pan1.setBorder(BorderFactory.createTitledBorder("Greek Alphabet"));
        panel.add(pan1);

        DefaultListModel list2Model = new DefaultListModel();
        list2Model.addElement("uma");
        list2Model.addElement("dois");
        list2Model.addElement("tres");
        list2Model.addElement("quatro");
        list2Model.addElement("cinco");
        list2Model.addElement("seis");
        JList list2 = new JList(list2Model);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        list2.setDragEnabled(true);
        JScrollPane sp2 = new JScrollPane(list2);
        sp2.setPreferredSize(new Dimension(400,100));
        list2.setTransferHandler(lh);
        list2.setDropMode(DropMode.INSERT);
        setMappings(list2);
        JPanel pan2 = new JPanel(new BorderLayout());
        pan2.add(sp2, BorderLayout.CENTER);
        pan2.setBorder(BorderFactory.createTitledBorder("Portuguese Numbers"));
        panel.add(pan2);

        DefaultListModel list3Model = new DefaultListModel();
        list3Model.addElement("adeen");
        list3Model.addElement("dva");
        list3Model.addElement("tri");
        list3Model.addElement("chyetirye");
        list3Model.addElement("pyat");
        list3Model.addElement("shest");
        JList list3 = new JList(list3Model);
        list3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        list3.setDragEnabled(true);
        JScrollPane sp3 = new JScrollPane(list3);
        sp3.setPreferredSize(new Dimension(400,100));
        list3.setTransferHandler(lh);
        list3.setDropMode(DropMode.ON);
        setMappings(list3);
        JPanel pan3 = new JPanel(new BorderLayout());
        pan3.add(sp3, BorderLayout.CENTER);
        pan3.setBorder(BorderFactory.createTitledBorder("Russian Numbers"));
        panel.add(pan3);

        setPreferredSize(new Dimension(500, 200));
        add(panel, BorderLayout.CENTER);
    }

    /**
     * Create an Edit menu to support cut/copy/paste.
     */
    public JMenuBar createMenuBar() {
        JMenuItem menuItem = null;
        JMenuBar menuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("Edit");
        mainMenu.setMnemonic(KeyEvent.VK_E);
        TransferActionListener actionListener = new TransferActionListener();

        menuItem = new JMenuItem("Cut");
        menuItem.setActionCommand((String)TransferHandler.getCutAction().
                 getValue(Action.NAME));
        menuItem.addActionListener(actionListener);
        menuItem.setAccelerator(
          KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        menuItem.setMnemonic(KeyEvent.VK_T);
        mainMenu.add(menuItem);
        
        menuItem = new JMenuItem("Copy");
        menuItem.setActionCommand((String)TransferHandler.getCopyAction().
                 getValue(Action.NAME));
        menuItem.addActionListener(actionListener);
        menuItem.setAccelerator(
          KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        menuItem.setMnemonic(KeyEvent.VK_C);
        mainMenu.add(menuItem);
        
        menuItem = new JMenuItem("Paste");
        menuItem.setActionCommand((String)TransferHandler.getPasteAction().
                 getValue(Action.NAME));
        menuItem.addActionListener(actionListener);
        menuItem.setAccelerator(
          KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        menuItem.setMnemonic(KeyEvent.VK_P);
        mainMenu.add(menuItem);

        menuBar.add(mainMenu);
        return menuBar;
    }
    
    /**
     * Add the cut/copy/paste actions to the action map.
     */
    private void setMappings(JList list) {
        ActionMap map = list.getActionMap();
        map.put(TransferHandler.getCutAction().getValue(Action.NAME),
                TransferHandler.getCutAction());
        map.put(TransferHandler.getCopyAction().getValue(Action.NAME),
                TransferHandler.getCopyAction());
        map.put(TransferHandler.getPasteAction().getValue(Action.NAME),
                TransferHandler.getPasteAction());

    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ListCutPaste");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the menu bar and content pane.
        ListCutPaste demo = new ListCutPaste();
        frame.setJMenuBar(demo.createMenuBar());
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
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
