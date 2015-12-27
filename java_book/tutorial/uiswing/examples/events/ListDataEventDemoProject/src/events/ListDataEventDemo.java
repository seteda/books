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

package events;

/*
 * ListDataEventDemo.java requires the Java Look and Feel Graphics
 * Repository (jlfgr-1_0.jar).  You can download this file from
 * http://java.sun.com/developer/techDocs/hi/repository/
 * Put it in the class path using one of the following commands
 * (assuming jlfgr-1_0.jar is in a subdirectory named jars):
 *
 *   java -cp .;jars/jlfgr-1_0.jar ListDataEventDemo [Microsoft Windows]
 *   java -cp .:jars/jlfgr-1_0.jar ListDataEventDemo [UNIX]
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.URL;

public class ListDataEventDemo extends JPanel 
                               implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    private static final String addString = "Add";
    private static final String deleteString = "Delete";
    private static final String upString = "Move up";
    private static final String downString = "Move down";

    private JButton addButton;
    private JButton deleteButton;
    private JButton upButton;
    private JButton downButton;

    private JTextField nameField;
    private JTextArea log;
    static private String newline = "\n";

    public ListDataEventDemo() {
        super(new BorderLayout());

        //Create and populate the list model.
        listModel = new DefaultListModel();
        listModel.addElement("Whistler, Canada");
        listModel.addElement("Jackson Hole, Wyoming");
        listModel.addElement("Squaw Valley, California");
        listModel.addElement("Telluride, Colorado");
        listModel.addElement("Taos, New Mexico");
        listModel.addElement("Snowbird, Utah");
        listModel.addElement("Chamonix, France");
        listModel.addElement("Banff, Canada");
        listModel.addElement("Arapahoe Basin, Colorado");
        listModel.addElement("Kirkwood, California");
        listModel.addElement("Sun Valley, Idaho");
        listModel.addListDataListener(new MyListDataListener());

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(
            ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        JScrollPane listScrollPane = new JScrollPane(list);

        //Create the list-modifying buttons.
        addButton = new JButton(addString);
        addButton.setActionCommand(addString);
        addButton.addActionListener(new AddButtonListener());

        deleteButton = new JButton(deleteString);
        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(
            new DeleteButtonListener());

        ImageIcon icon = createImageIcon("Up16");
        if (icon != null) {
            upButton = new JButton(icon);
            upButton.setMargin(new Insets(0,0,0,0));
        } else {
            upButton = new JButton("Move up");
        }
        upButton.setToolTipText("Move the currently selected list item higher.");
        upButton.setActionCommand(upString);
        upButton.addActionListener(new UpDownListener());

        icon = createImageIcon("Down16");
        if (icon != null) {
            downButton = new JButton(icon);
            downButton.setMargin(new Insets(0,0,0,0));
        } else {
            downButton = new JButton("Move down");
        }
        downButton.setToolTipText("Move the currently selected list item lower.");
        downButton.setActionCommand(downString);
        downButton.addActionListener(new UpDownListener());

        JPanel upDownPanel = new JPanel(new GridLayout(2, 1));
        upDownPanel.add(upButton);
        upDownPanel.add(downButton);

        //Create the text field for entering new names.
        nameField = new JTextField(15);
        nameField.addActionListener(new AddButtonListener());
        String name = listModel.getElementAt(list.getSelectedIndex())
                               .toString();
        nameField.setText(name);

        //Create a control panel, using the default FlowLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.add(nameField);
        buttonPane.add(addButton);
        buttonPane.add(deleteButton);
        buttonPane.add(upDownPanel);

        //Create the log for reporting list data events.
        log = new JTextArea(10, 20);
        JScrollPane logScrollPane = new JScrollPane(log);

        //Create a split pane for the log and the list.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                        listScrollPane, logScrollPane);
        splitPane.setResizeWeight(0.5);
        
        //Put everything together.
        add(buttonPane, BorderLayout.PAGE_START);
        add(splitPane, BorderLayout.CENTER);
    }

    class MyListDataListener implements ListDataListener {
        public void contentsChanged(ListDataEvent e) {
            log.append("contentsChanged: " + e.getIndex0() +
                       ", " + e.getIndex1() + newline); 
            log.setCaretPosition(log.getDocument().getLength());
        }
        public void intervalAdded(ListDataEvent e) {
            log.append("intervalAdded: " + e.getIndex0() +
                       ", " + e.getIndex1() + newline); 
            log.setCaretPosition(log.getDocument().getLength());
        }
        public void intervalRemoved(ListDataEvent e) {
            log.append("intervalRemoved: " + e.getIndex0() +
                       ", " + e.getIndex1() + newline); 
            log.setCaretPosition(log.getDocument().getLength());
        }
    }

    class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            /* 
             * This method can be called only if
             * there's a valid selection,
             * so go ahead and remove whatever's selected.
             */

            ListSelectionModel lsm = list.getSelectionModel();
            int firstSelected = lsm.getMinSelectionIndex();
            int lastSelected = lsm.getMaxSelectionIndex();
            listModel.removeRange(firstSelected, lastSelected);

            int size = listModel.size();

            if (size == 0) {
            //List is empty: disable delete, up, and down buttons.
                deleteButton.setEnabled(false);
                upButton.setEnabled(false);
                downButton.setEnabled(false);

            } else {
            //Adjust the selection.
                if (firstSelected == listModel.getSize()) {
                //Removed item in last position.
                    firstSelected--;
                }
                list.setSelectedIndex(firstSelected);
            }
        }
    }

    /** A listener shared by the text field and add button. */
    class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (nameField.getText().equals("")) {
            //User didn't type in a name...
                Toolkit.getDefaultToolkit().beep();
                return;
            }

            int index = list.getSelectedIndex();
            int size = listModel.getSize();

            //If no selection or if item in last position is selected,
            //add the new one to end of list, and select new one.
            if (index == -1 || (index+1 == size)) {
                listModel.addElement(nameField.getText());
                list.setSelectedIndex(size);

            //Otherwise insert the new one after the current selection,
            //and select new one.
            } else {
                listModel.insertElementAt(nameField.getText(), index+1);
                list.setSelectedIndex(index+1);
            }
        }
    }

    //Listen for clicks on the up and down arrow buttons.
    class UpDownListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only when
            //there's a valid selection,
            //so go ahead and move the list item.
            int moveMe = list.getSelectedIndex();

            if (e.getActionCommand().equals(upString)) {
            //UP ARROW BUTTON
                if (moveMe != 0) {     
                //not already at top
                    swap(moveMe, moveMe-1);
                    list.setSelectedIndex(moveMe-1);
                    list.ensureIndexIsVisible(moveMe-1);
                }
            } else {
            //DOWN ARROW BUTTON
                if (moveMe != listModel.getSize()-1) {
                //not already at bottom
                    swap(moveMe, moveMe+1);
                    list.setSelectedIndex(moveMe+1);
                    list.ensureIndexIsVisible(moveMe+1);
                }
            }
        }
    }

    //Swap two elements in the list.
    private void swap(int a, int b) {
        Object aObject = listModel.getElementAt(a);
        Object bObject = listModel.getElementAt(b);
        listModel.set(a, bObject);
        listModel.set(b, aObject);
    }

    //Listener method for list selection changes.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
            //No selection: disable delete, up, and down buttons.
                deleteButton.setEnabled(false);
                upButton.setEnabled(false);
                downButton.setEnabled(false);
                nameField.setText("");

            } else if (list.getSelectedIndices().length > 1) {
            //Multiple selection: disable up and down buttons.
                deleteButton.setEnabled(true);
                upButton.setEnabled(false);
                downButton.setEnabled(false);

            } else {
            //Single selection: permit all operations.
                deleteButton.setEnabled(true);
                upButton.setEnabled(true);
                downButton.setEnabled(true);
                nameField.setText(list.getSelectedValue().toString());
            }
        }
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String imageName) {
        String imgLocation = "toolbarButtonGraphics/navigation/"
                             + imageName
                             + ".gif";
        java.net.URL imageURL = ListDataEventDemo.class.getResource(imgLocation);

        if (imageURL == null) {
            System.err.println("Resource not found: "
                               + imgLocation);
            return null;
        } else {
            return new ImageIcon(imageURL);
        }
    }

    /** 
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ListDataEventDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ListDataEventDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        
        //Don't let the content pane get too small.
        //(Works if the Java look and feel provides
        //the window decorations.)
        newContentPane.setMinimumSize(
                new Dimension(
                        newContentPane.getPreferredSize().width,
                        100));

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
