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

/*
 * DropDemo.java requires the following file:
 *     ListTransferHandler.java
 */
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;

public class DropDemo extends JPanel implements ActionListener {
    private JComboBox dropCombo;
    private JList list;
    
    public DropDemo() {
        super(new GridLayout(2,1));
        add(createArea());
        add(createList());
    }
    
    private JPanel createList() {
        DefaultListModel listModel = new DefaultListModel();
            
        for (int i = 0; i < 10; i++) {
            listModel.addElement("List Item " + i);
        }
        
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(400,100));
        
        list.setDragEnabled(true);
        list.setTransferHandler(new ListTransferHandler());
        
        dropCombo = new JComboBox(new String[] {"USE_SELECTION", "ON", "INSERT", "ON_OR_INSERT"});
        dropCombo.addActionListener(this);
        JPanel dropPanel = new JPanel();
        dropPanel.add(new JLabel("List Drop Mode:"));
        dropPanel.add(dropCombo);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(dropPanel, BorderLayout.SOUTH);
        panel.setBorder(BorderFactory.createTitledBorder("List"));
        return panel;
    }
    
    private JPanel createArea() {
        String text = "Drag from or drop into this area.\nThe default action is MOVE;\nhold the Control key to COPY.";
        
        JTextArea area = new JTextArea();
        area.setText(text);
        area.setDragEnabled(true);
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(400,100));
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createTitledBorder("Text Area"));
        return panel;
    }

    public void actionPerformed(ActionEvent ae) {
        Object val = dropCombo.getSelectedItem();
        if (val == "USE_SELECTION") {
            list.setDropMode(DropMode.USE_SELECTION);
        } else if (val == "ON") {
            list.setDropMode(DropMode.ON);
        } else if (val == "INSERT") {
            list.setDropMode(DropMode.INSERT);
        } else if (val == "ON_OR_INSERT") {
            list.setDropMode(DropMode.ON_OR_INSERT);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("DropDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new DropDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

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
