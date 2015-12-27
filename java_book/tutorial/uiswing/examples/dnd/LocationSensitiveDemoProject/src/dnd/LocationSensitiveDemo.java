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

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;

public class LocationSensitiveDemo extends JFrame {

    private DefaultListModel model = new DefaultListModel();
    private int count = 0;
    private JTree tree;
    private JComboBox indicateCombo;
    private DefaultTreeModel treeModel;
    private TreePath namesPath;

    private static DefaultTreeModel getDefaultTreeModel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("things");
        DefaultMutableTreeNode parent;
        DefaultMutableTreeNode nparent;

        parent = new DefaultMutableTreeNode("colors");
        root.add(parent);
        parent.add(new DefaultMutableTreeNode("red"));
        parent.add(new DefaultMutableTreeNode("yellow"));
        parent.add(new DefaultMutableTreeNode("green"));
        parent.add(new DefaultMutableTreeNode("blue"));
        parent.add(new DefaultMutableTreeNode("purple"));

        parent = new DefaultMutableTreeNode("names");
        root.add(parent);
        nparent = new DefaultMutableTreeNode("men");
        nparent.add(new DefaultMutableTreeNode("jack"));
        nparent.add(new DefaultMutableTreeNode("kieran"));
        nparent.add(new DefaultMutableTreeNode("william"));
        nparent.add(new DefaultMutableTreeNode("jose"));
        
        parent.add(nparent);
        nparent = new DefaultMutableTreeNode("women");
        nparent.add(new DefaultMutableTreeNode("jennifer"));
        nparent.add(new DefaultMutableTreeNode("holly"));
        nparent.add(new DefaultMutableTreeNode("danielle"));
        nparent.add(new DefaultMutableTreeNode("tara"));
        parent.add(nparent);

        parent = new DefaultMutableTreeNode("sports");
        root.add(parent);
        parent.add(new DefaultMutableTreeNode("basketball"));
        parent.add(new DefaultMutableTreeNode("soccer"));
        parent.add(new DefaultMutableTreeNode("football"));

        nparent = new DefaultMutableTreeNode("hockey");
        parent.add(nparent);
        nparent.add(new DefaultMutableTreeNode("ice hockey"));
        nparent.add(new DefaultMutableTreeNode("roller hockey"));
        nparent.add(new DefaultMutableTreeNode("floor hockey"));
        nparent.add(new DefaultMutableTreeNode("road hockey"));

        parent = new DefaultMutableTreeNode("food");
        root.add(parent);
        parent.add(new DefaultMutableTreeNode("pizza"));
        parent.add(new DefaultMutableTreeNode("wings"));
        parent.add(new DefaultMutableTreeNode("pasta"));
        nparent = new DefaultMutableTreeNode("fruit");
        parent.add(nparent);
        nparent.add(new DefaultMutableTreeNode("bananas"));
        nparent.add(new DefaultMutableTreeNode("apples"));
        nparent.add(new DefaultMutableTreeNode("grapes"));
        nparent.add(new DefaultMutableTreeNode("pears"));
        return new DefaultTreeModel(root);
    }

    public LocationSensitiveDemo() {
        super("Location Sensitive Drag and Drop Demo");

        treeModel = getDefaultTreeModel();
        tree = new JTree(treeModel);
        tree.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        tree.setDropMode(DropMode.ON);
        namesPath = tree.getPathForRow(2);
        tree.expandRow(2);
        tree.expandRow(1);
        tree.setRowHeight(0);

        tree.setTransferHandler(new TransferHandler() {

            public boolean canImport(TransferHandler.TransferSupport info) {
                // for the demo, we'll only support drops (not clipboard paste)
                if (!info.isDrop()) {
                    return false;
                }

                String item = (String)indicateCombo.getSelectedItem();
                
                if (item.equals("Always")) {
                    info.setShowDropLocation(true);
                } else if (item.equals("Never")) {
                    info.setShowDropLocation(false);
                }

                // we only import Strings
                if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    return false;
                }

                // fetch the drop location
                JTree.DropLocation dl = (JTree.DropLocation)info.getDropLocation();

                TreePath path = dl.getPath();

                // we don't support invalid paths or descendants of the names folder
                if (path == null || namesPath.isDescendant(path)) {
                    return false;
                }

                return true;
            }

            public boolean importData(TransferHandler.TransferSupport info) {
                // if we can't handle the import, say so
                if (!canImport(info)) {
                    return false;
                }

                // fetch the drop location
                JTree.DropLocation dl = (JTree.DropLocation)info.getDropLocation();
                
                // fetch the path and child index from the drop location
                TreePath path = dl.getPath();
                int childIndex = dl.getChildIndex();

                // fetch the data and bail if this fails
                String data;
                try {
                    data = (String)info.getTransferable().getTransferData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException e) {
                    return false;
                } catch (IOException e) {
                    return false;
                }

                // if child index is -1, the drop was on top of the path, so we'll
                // treat it as inserting at the end of that path's list of children
                if (childIndex == -1) {
                    childIndex = tree.getModel().getChildCount(path.getLastPathComponent());
                }

                // create a new node to represent the data and insert it into the model
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(data);
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)path.getLastPathComponent();
                treeModel.insertNodeInto(newNode, parentNode, childIndex);

                // make the new node visible and scroll so that it's visible
                tree.makeVisible(path.pathByAddingChild(newNode));
                tree.scrollRectToVisible(tree.getPathBounds(path.pathByAddingChild(newNode)));

                // demo stuff - remove for blog
                model.removeAllElements();
                model.insertElementAt("String " + (++count), 0);
                // end demo stuff

                return true;
            }
        });

        JList dragFrom = new JList(model);
        dragFrom.setFocusable(false);
        dragFrom.setPrototypeCellValue("String 0123456789");
        model.insertElementAt("String " + count, 0);
        dragFrom.setDragEnabled(true);
        dragFrom.setBorder(BorderFactory.createLoweredBevelBorder());

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        JPanel wrap = new JPanel();
        wrap.add(new JLabel("Drag from here:"));
        wrap.add(dragFrom);
        p.add(Box.createHorizontalStrut(4));
        p.add(Box.createGlue());
        p.add(wrap);
        p.add(Box.createGlue());
        p.add(Box.createHorizontalStrut(4));
        getContentPane().add(p, BorderLayout.NORTH);

        getContentPane().add(new JScrollPane(tree), BorderLayout.CENTER);
        indicateCombo = new JComboBox(new String[] {"Default", "Always", "Never"});
        indicateCombo.setSelectedItem("INSERT");

        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        wrap = new JPanel();
        wrap.add(new JLabel("Show drop location:"));
        wrap.add(indicateCombo);
        p.add(Box.createHorizontalStrut(4));
        p.add(Box.createGlue());
        p.add(wrap);
        p.add(Box.createGlue());
        p.add(Box.createHorizontalStrut(4));
        getContentPane().add(p, BorderLayout.SOUTH);

        getContentPane().setPreferredSize(new Dimension(400, 450));
    }

    private static void increaseFont(String type) {
        Font font = UIManager.getFont(type);
        font = font.deriveFont(font.getSize() + 4f);
        UIManager.put(type, font);
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        LocationSensitiveDemo test = new LocationSensitiveDemo();
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Display the window.
        test.pack();
        test.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {                
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    increaseFont("Tree.font");
                    increaseFont("Label.font");
                    increaseFont("ComboBox.font");
                    increaseFont("List.font");
                } catch (Exception e) {}

                //Turn off metal's use of bold fonts
	        UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
