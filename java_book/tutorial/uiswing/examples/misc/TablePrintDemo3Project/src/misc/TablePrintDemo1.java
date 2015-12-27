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

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.text.MessageFormat;

/**
 * Demo of the basic features of {@code JTable} printing.
 * Allows the user to configure a couple of options and print
 * a table of student grades.
 * <p>
 * Requires the following other files:
 * <ul>
 *     <li>passed.png
 *     <li>failed.png
 * </ul>
 *
 * @author Shannon Hickey
 */
public class TablePrintDemo1 extends JFrame {

    /* Check mark and x mark items to render the "Passed" column */
    private static final ImageIcon passedIcon
        = new ImageIcon(TablePrintDemo1.class.getResource("images/passed.png"));
    private static final ImageIcon failedIcon
        = new ImageIcon(TablePrintDemo1.class.getResource("images/failed.png"));

    /* UI Components */
    private JPanel contentPane;
    private JLabel gradesLabel;
    private JTable gradesTable;
    private JScrollPane scroll;
    private JCheckBox showPrintDialogBox;
    private JCheckBox interactiveBox;
    private JCheckBox fitWidthBox;
    private JButton printButton;

    /* Protected so that they can be modified/disabled by subclasses */
    protected JCheckBox headerBox;
    protected JCheckBox footerBox;
    protected JTextField headerField;
    protected JTextField footerField;

    /**
     * Constructs an instance of the demo.
     */
    public TablePrintDemo1() {
        super("Table Printing Demo 1");

        gradesLabel = new JLabel("Final Grades - CSC 101");
        gradesLabel.setFont(new Font("Dialog", Font.BOLD, 16));

        gradesTable = createTable(new GradesModel());
        gradesTable.setFillsViewportHeight(true);
        gradesTable.setRowHeight(24);

        /* Set a custom renderer on the "Passed" column */
        gradesTable.getColumn("Passed").setCellRenderer(createPassedColumnRenderer());

        scroll = new JScrollPane(gradesTable);

        String tooltipText;

        tooltipText = "Include a page header";
        headerBox = new JCheckBox("Header:", true);
        headerBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                headerField.setEnabled(headerBox.isSelected());
            }
        });
        headerBox.setToolTipText(tooltipText);
        tooltipText = "Page Header (Use {0} to include page number)";
        headerField = new JTextField("Final Grades - CSC 101");
        headerField.setToolTipText(tooltipText);

        tooltipText = "Include a page footer";
        footerBox = new JCheckBox("Footer:", true);
        footerBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                footerField.setEnabled(footerBox.isSelected());
            }
        });
        footerBox.setToolTipText(tooltipText);
        tooltipText = "Page Footer (Use {0} to Include Page Number)";
        footerField = new JTextField("Page {0}");
        footerField.setToolTipText(tooltipText);

        tooltipText = "Show the Print Dialog Before Printing";
        showPrintDialogBox = new JCheckBox("Show print dialog", true);
        showPrintDialogBox.setToolTipText(tooltipText);
        showPrintDialogBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (!showPrintDialogBox.isSelected()) {
                    JOptionPane.showMessageDialog(
                        TablePrintDemo1.this,
                        "If the Print Dialog is not shown,"
                            + " the default printer is used.",
                        "Printing Message",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        
        tooltipText = "Keep the GUI Responsive and Show a Status Dialog During Printing";
        interactiveBox = new JCheckBox("Interactive (Show status dialog)", true);
        interactiveBox.setToolTipText(tooltipText);
        interactiveBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (!interactiveBox.isSelected()) {
                    JOptionPane.showMessageDialog(
                        TablePrintDemo1.this,
                        "If non-interactive, the GUI is fully blocked"
                            + " during printing.",
                        "Printing Message",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        tooltipText = "Shrink the Table to Fit the Entire Width on a Page";
        fitWidthBox = new JCheckBox("Fit width to printed page", true);
        fitWidthBox.setToolTipText(tooltipText);

        tooltipText = "Print the Table";
        printButton = new JButton("Print");
        printButton.setToolTipText(tooltipText);
        
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                printGradesTable();
            }
        });

        contentPane = new JPanel();
        addComponentsToContentPane();
        setContentPane(contentPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
    }

    /**
     * Adds to and lays out all GUI components on the {@code contentPane} panel.
     * <p>
     * It is recommended that you <b>NOT</b> try to understand this code. It was
     * automatically generated by the NetBeans GUI builder.
     * 
     */
    private void addComponentsToContentPane() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Printing"));

        GroupLayout bottomPanelLayout = new GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(headerBox)
                    .addComponent(footerBox))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(footerField)
                    .addComponent(headerField, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(bottomPanelLayout.createSequentialGroup()
                        .addComponent(fitWidthBox)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(printButton))
                    .addGroup(bottomPanelLayout.createSequentialGroup()
                        .addComponent(showPrintDialogBox)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(interactiveBox)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addGroup(bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(headerBox)
                    .addComponent(headerField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(interactiveBox)
                    .addComponent(showPrintDialogBox))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bottomPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(footerBox)
                    .addComponent(footerField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(fitWidthBox)
                    .addComponent(printButton))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(scroll, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                    .addComponent(gradesLabel)
                    .addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gradesLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }

    /**
     * Create and return a table for the given model.
     * <p>
     * This is protected so that a subclass can return an instance
     * of a different {@code JTable} subclass. This is interesting
     * only for {@code TablePrintDemo3} where we want to return a
     * subclass that overrides {@code getPrintable} to return a
     * custom {@code Printable} implementation.
     */
    protected JTable createTable(TableModel model) {
        return new JTable(model);
    }

    /**
     * Create and return a cell renderer for rendering the pass/fail column.
     * This is protected so that a subclass can further customize it.
     */
    protected TableCellRenderer createPassedColumnRenderer() {
        return new PassedColumnRenderer();
    }

    /**
     * Print the grades table.
     */
    private void printGradesTable() {
        /* Fetch printing properties from the GUI components */

        MessageFormat header = null;
        
        /* if we should print a header */
        if (headerBox.isSelected()) {
            /* create a MessageFormat around the header text */
            header = new MessageFormat(headerField.getText());
        }

        MessageFormat footer = null;
        
        /* if we should print a footer */
        if (footerBox.isSelected()) {
            /* create a MessageFormat around the footer text */
            footer = new MessageFormat(footerField.getText());
        }

        boolean fitWidth = fitWidthBox.isSelected();
        boolean showPrintDialog = showPrintDialogBox.isSelected();
        boolean interactive = interactiveBox.isSelected();

        /* determine the print mode */
        JTable.PrintMode mode = fitWidth ? JTable.PrintMode.FIT_WIDTH
                                         : JTable.PrintMode.NORMAL;

        try {
            /* print the table */
            boolean complete = gradesTable.print(mode, header, footer,
                                                 showPrintDialog, null,
                                                 interactive, null);

            /* if printing completes */
            if (complete) {
                /* show a success message */
                JOptionPane.showMessageDialog(this,
                                              "Printing Complete",
                                              "Printing Result",
                                              JOptionPane.INFORMATION_MESSAGE);
            } else {
                /* show a message indicating that printing was cancelled */
                JOptionPane.showMessageDialog(this,
                                              "Printing Cancelled",
                                              "Printing Result",
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException pe) {
            /* Printing failed, report to the user */
            JOptionPane.showMessageDialog(this,
                                          "Printing Failed: " + pe.getMessage(),
                                          "Printing Result",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Start the application.
     */
    public static void main(final String[] args) {
        /* Schedule for the GUI to be created and shown on the EDT */
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                /* Don't want bold fonts if we end up using metal */
                UIManager.put("swing.boldMetal", false);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                }
                new TablePrintDemo1().setVisible(true);
            }
        });
    }

    /**
     * A table model containing student grades.
     */
    private static class GradesModel implements TableModel {
        private final Object[][] GRADES = {
            {"Agnew", "Kieran", 80, 74, 75, 93},
            {"Albertson", "Jack", 90, 65, 88, 79},
            {"Anderson", "Mischa", 34, 45, 48, 59},
            {"Andrews", "Danielle", 50, 56, 55, 44},
            {"Baja", "Ron", 32, 23, 55, 67},
            {"Barry", "Douglas", 46, 66, 77, 90},
            {"Davis", "Lacy", 99, 100, 98, 97},
            {"Egelstein", "Harold", 34, 58, 76, 89},
            {"Elens", "Xavier", 35, 66, 49, 47},
            {"Elmer", "Thomas", 50, 32, 51, 60},
            {"Farras", "Elena", 77, 51, 75, 55},
            {"Filison", "Paulo", 88, 87, 77, 52},
            {"Gabon", "Parvati", 9, 15, 35, 86},
            {"Hickey", "Shannon", 95, 89, 95, 100},
            {"Ingles", "Jaime", 75, 65, 55, 95},
            {"Instein", "Payton", 51, 56, 51, 84},
            {"Jackson", "Donald", 94, 78, 97, 13},
            {"Jefferson", "Lynn", 21, 51, 20, 74},
            {"Johnson", "Tanya", 11, 52, 80, 48},
            {"Kimble", "James", 18, 50, 90, 54},
            {"Kolson", "Laura", 98, 88, 97, 99},
            {"Leigh", "Tasha", 85, 78, 48, 100},
            {"Lombardi", "Leonard", 68, 54, 97, 24},
            {"Manning", "Havvy", 59, 54, 9, 18},
            {"McNichol", "Vivian", 88, 99, 58, 87},
            {"Michaels", "Daniel", 97, 95, 54, 99},
            {"Nicholson", "Alex", 24, 100, 55, 100},
            {"Nimble", "Tonya", 41, 33, 33, 81},
            {"Onning", "Wehhoh", 45, 65, 32, 24},
            {"Opals", "Diamond", 98, 59, 12, 11},
            {"Osser", "Michael", 55, 54, 31, 87},
            {"Paton", "Geoff", 68, 22, 31, 80},
            {"Plumber", "Ester", 58, 20, 28, 98},
            {"Robbins", "Noah", 99, 12, 87, 64},
            {"Robinson", "Jenny", 65, 10, 98, 66},
            {"Ronald", "Dmitri", 25, 9, 98, 61},
            {"Sabo", "Polly", 20, 68, 82, 50},
            {"Silverstone", "Dina", 31, 62, 54, 58},
            {"Singleton", "Alyssa", 50, 30, 98, 88},
            {"Stevens", "Cameron", 89, 8, 81, 56},
            {"Talbert", "Will", 34, 80, 81, 84},
            {"Trimble", "Vicky", 58, 65, 98, 54},
            {"Tussle", "Paulo", 55, 55, 88, 55},
            {"Umber", "Gus", 90, 87, 85, 55},
            {"Valhalla", "Yohan", 60, 77, 62, 89},
            {"Viola", "Michel", 31, 84, 62, 68},
            {"Wanderer", "Sean", 24, 51, 85, 95},
            {"West", "North", 88, 23, 81, 87},
            {"Xavier", "Kerry", 91, 99, 24, 84},
            {"Xu", "Richard", 99, 58, 20, 24},
            {"Ying", "Lina", 85, 99, 89, 90},
            {"Yee", "Tong", 95, 65, 74, 64},
        };

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}
        public void addTableModelListener(TableModelListener l) {}
        public void removeTableModelListener(TableModelListener l) {}

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public Class<?> getColumnClass(int col) {
            switch(col) {
                case 0:
                case 1:
                    return String.class;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    return Integer.class;
                case 7:
                    return Boolean.class;
            }

            throw new AssertionError("invalid column");
        }
        
        public int getRowCount() {
            return GRADES.length;
        }
        
        public int getColumnCount() {
            return 8;
        }
        
        public String getColumnName(int col) {
            switch(col) {
                case 0: return "Last Name";
                case 1: return "First Name";
                case 2: return "Assign. 1";
                case 3: return "Midterm";
                case 4: return "Assign. 2";
                case 5: return "Exam";
                case 6: return "Final";
                case 7: return "Passed";
            }
            
            throw new AssertionError("invalid column");
        }
        
        public Object getValueAt(int row, int col) {
            switch(col) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    return GRADES[row][col];
                case 6:
                case 7:
                    int fin = 0;
                    fin += (Integer)GRADES[row][2];
                    fin += (Integer)GRADES[row][3];
                    fin += (Integer)GRADES[row][4];
                    fin += (Integer)GRADES[row][5];
                    fin = Math.round((fin / 4.0f));
                    if (col == 6) {
                        return fin;
                    } else {
                        return fin > 50;
                    }
            }

            throw new AssertionError("invalid column");
        }
    }

    /**
     * A custom cell renderer for rendering the "Passed" column.
     */
    protected static class PassedColumnRenderer extends DefaultTableCellRenderer {
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value,
                                                           boolean isSelected,
                                                           boolean hasFocus,
                                                           int row,
                                                           int column) {

            super.getTableCellRendererComponent(table, value, isSelected,
                                                hasFocus, row, column);

            setText("");
            setHorizontalAlignment(SwingConstants.CENTER);

            /* set the icon based on the passed status */
            boolean status = (Boolean)value;
            setIcon(status ? passedIcon : failedIcon);

            return this;
        }
    }
}
