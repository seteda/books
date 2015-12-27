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
 * TextBatchPrintingDemo.java requires the following files:
 * index.html
 * chapter1.html
 * chapter2.html
 * chapter3.html
 * chapter4.html
 * chapter5.html
 * chapter6.html
 * chapter7.html
 * chapter8.html
 * chapter9.html
 * chapter10.html
 */

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;

/**
 * This is a simple web browser that allows to store visited pages on the 
 * "print list" and then prints all selected pages at once, in batch mode.
 */
public class TextBatchPrintingDemo
                        implements HyperlinkListener, ListSelectionListener {
    
    /**
     * A class representing pages in the page cache and in the print list.
     */
    static class PageItem extends JEditorPane {
        String title;
        
        /**
         * If the loaded document has a title, use it; otherwise use the
         * string representation of the document URL.
         */
        public String toString() {
            if (title == null) {
                String s = (String) getDocument().getProperty(
                                                        Document.TitleProperty);
                title = (s == null ? getPage().toString() : s);
            }
            return title;
        }
    }

    /** Default start page, could be changed from the command line.  */
    static String defaultPage = "index.html";
    
    /** Default message area contents.  */
    static String defaultMessage = "Select: Alt-A  Print: Alt-P  Quit: Alt-Q";
    
    /** Default print service.  */
    static PrintService printService =
                                PrintServiceLookup.lookupDefaultPrintService();
    
    /** The currently displayed page item.  */
    PageItem pageItem;
    
    /** Start page for the page browser.  */
    URL homePage;
    
    /** Cache for the visited page items indexed by page URL.  */
    Map<URL, PageItem> pageCache = new HashMap<URL, PageItem>();
    
    /** Pages currently selected for printing (aka "print list").  */
    JList selectedPages;
    
    /** An area holding the informational and error messages.  */
    JLabel messageArea;


    /**
     * The demo has three logical parts.  The first is the batch printing
     * method, the second is UI controller routines and the third is the UI
     * initialization and other (non-UI) setup code.
     */

    /* Part 1: Batch printing.  */

    /**
     * Print all selected pages in separate threads, one thread per page.
     */
    void printSelectedPages() {
        DefaultListModel pages = (DefaultListModel) selectedPages.getModel();
        int n = pages.getSize();
        if (n < 1) {
            messageArea.setText("No pages selected");
            return;
        }
        if (printService == null) {
            messageArea.setText("No print service");
            return;
        }

        for (int i = 0; i < n; i++) {
            final PageItem item = (PageItem) pages.getElementAt(i);
            // This method is called from EDT.  Printing is a time-consuming
            // task, so it should be done outside EDT, in a separate thread.
            Runnable printTask = new Runnable() {
                public void run() {
                    try {
                        item.print(
                                // Two "false" args mean "no print dialog" and
                                // "non-interactive" (ie, batch-mode printing).
                                null, null, false, printService, null, false);
                    } catch (PrinterException pe) {
                        JOptionPane.showMessageDialog(null,
                                "Error printing " + item.getPage() + "\n" + pe,
                                "Print Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            };
            new Thread(printTask).start();
        }

        pages.removeAllElements();
        messageArea.setText(n + (n > 1 ? " pages" : " page") + " printed");
    }


    /* Part 2: UI controller.  */

    /**
     * Called when something is happened on a hyperlink in the page browser.
     * This is the {@code HyperlinkListener} method.
     */
    public void hyperlinkUpdate(HyperlinkEvent e) {
        URL url = e.getURL();
        EventType type = e.getEventType();
        
        if (type == EventType.ENTERED) {
            messageArea.setText("Go to " + url);
        } else if (type == EventType.EXITED) {
            messageArea.setText(defaultMessage);
        } else if (type == EventType.ACTIVATED) {
            setPage(url);
            messageArea.setText(defaultMessage);
        }
    }

    /**
     * Called when the print list selection state is changed.  This is the 
     * {@code ListSelectionListener} method.
     */
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int index = ((JList) e.getSource()).getSelectedIndex();
            if (index >= 0) {
                // Load the currently selected URL into the page browser.
                PageItem item =
                        (PageItem) selectedPages.getModel().getElementAt(index);
                URL page = item.getPage();
                if (! page.equals(pageItem.getPage())) {
                    setPage(page);
                }
            }
        }
    }

    /**
     * Load URL into the current page item.  If the cached page item exists for
     * the given URL, use it; otherwise create new page item.
     */
    void setPage(URL url) {
        PageItem item = pageCache.get(url);
        if (item == null) {
            item = createPageItem(url);
            pageCache.put(url, item);
        }
        if (pageItem != null) {
            Container p = pageItem.getParent();
            if (p != null) {
                p.remove(pageItem);
                p.add(item);
            }
        }
        pageItem = item;
        updateSelectedPages();
    }

    /**
     * Synchronize the selection in the print list with the current page item.
     * If the current page item isn't in the print list, clear selection.
     */
    void updateSelectedPages() {
        ListModel pages = selectedPages.getModel();
        int n = pages.getSize();
        if (n > 0) {
            URL page = pageItem.getPage();
            int index = selectedPages.getSelectedIndex();
            if (index >= 0) {
                PageItem selected = (PageItem) pages.getElementAt(index);
                if (page.equals(selected.getPage())) {
                    // Currently displayed page is selected in the print list.
                    return;
                }
            }
            for (int i = 0; i < n; i++) {
                PageItem pi = (PageItem) pages.getElementAt(i);
                if (page.equals(pi.getPage())) {
                    // Currently displayed page is in the print list, select it.
                    selectedPages.setSelectedIndex(i);
                    return;
                }
            }
            // Currently displayed page is not in the print list. 
            selectedPages.clearSelection();
        }
    }


    /* Part 3: Initialization and setup.  */
    
    /**
     * Create and return a page item initialized with the given URL.
     */
    PageItem createPageItem(URL url) {
        PageItem item = new PageItem();
        item.setPreferredSize(new Dimension(800, 600));
        item.setEditable(false);
        item.addHyperlinkListener(this);
        try {
            item.setPage(url);
        } catch (IOException ioe) {
            messageArea.setText("Error loading " + url + ": " + ioe);
        }
        return item;
    }

    /**
     * Create and return a menu item with the specified action, mnemonics and
     * keyboad accelerator.
     */
    JMenuItem createMenuItem(Action action, int mnemonics, KeyStroke accel) {
        JMenuItem item = new JMenuItem(action);
        item.setMnemonic(mnemonics);
        item.setAccelerator(accel);
        return item;
    }

    
    /**
     * Create and display the main application frame.
     */
    void createAndShowGUI() {
        messageArea = new JLabel(defaultMessage);
        
        selectedPages = new JList(new DefaultListModel());
        selectedPages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectedPages.addListSelectionListener(this);
        
        setPage(homePage);
        
        JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(pageItem), new JScrollPane(selectedPages));
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        /** Menu item and keyboard shortcuts for the "add page" command.  */
        fileMenu.add(createMenuItem(
                new AbstractAction("Add Page") {
                    public void actionPerformed(ActionEvent e) {
                        DefaultListModel pages =
                                (DefaultListModel) selectedPages.getModel();
                        pages.addElement(pageItem);
                        selectedPages.setSelectedIndex(pages.getSize() - 1);
                   }
                },
                KeyEvent.VK_A, 
                KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK)));

        /** Menu item and keyboard shortcuts for the "print selected" command.*/
        fileMenu.add(createMenuItem(
                new AbstractAction("Print Selected") {
                    public void actionPerformed(ActionEvent e) {
                        printSelectedPages();
                    }
                },
                KeyEvent.VK_P, 
                KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK)));


        /** Menu item and keyboard shortcuts for the "clear selected" command.*/
        fileMenu.add(createMenuItem(
                new AbstractAction("Clear Selected") {
                    public void actionPerformed(ActionEvent e) {
                        DefaultListModel pages =
                                (DefaultListModel) selectedPages.getModel();
                        pages.removeAllElements();
                    }
                },
                KeyEvent.VK_C, 
                KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK)));

        fileMenu.addSeparator();

        /** Menu item and keyboard shortcuts for the "home page" command.  */
        fileMenu.add(createMenuItem(
                new AbstractAction("Home Page") {
                    public void actionPerformed(ActionEvent e) {
                        setPage(homePage);
                    }
                },
                KeyEvent.VK_H, 
                KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK)));

	/** Menu item and keyboard shortcuts for the "quit" command.  */
        fileMenu.add(createMenuItem(
                new AbstractAction("Quit") {
                    public void actionPerformed(ActionEvent e) {
                        for (Window w : Window.getWindows()) {
                            w.dispose();
                        }
                    }
                },
                KeyEvent.VK_A, 
                KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK)));

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(pane);
        contentPane.add(messageArea);

        JFrame frame = new JFrame("Text Batch Printing Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        if (printService == null) {
            // Actual printing is not possible, issue a warning message.
            JOptionPane.showMessageDialog(frame, "No default print service",
                            "Print Service Alert", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        final TextBatchPrintingDemo demo = new TextBatchPrintingDemo();

        demo.homePage = demo.getClass().getResource(defaultPage);
        // Custom home page can be specified on the command line.
        if (args.length > 0) {
            String pageName = args[0];
            try {
                URL url = new URL(pageName);
                demo.homePage = url;
            } catch (MalformedURLException e) {
                System.out.println("Error parsing " + pageName + ": " + e);
                // Home page is unchanged from the default value.
            }
        }


        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                demo.createAndShowGUI();
            }
        });
    }
}
