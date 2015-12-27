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
import java.awt.*;
import java.awt.event.*;

/**
 * Demonstration of how to customize rendering based on whether
 * or not the table is being printed. The last column of the table,
 * the "Passed" column, is given an extended version of TablePrintDemo1's
 * PassedColumnRenderer, which uses clearer black and white versions
 * of the check mark and x mark icons when printing, as determined by
 * calling isPaintingForPrint on the table.
 * <p>
 * Requires the following other files:
 * <ul>
 *     <li>TablePrintDemo1.java
 *     <li>images/passed.png
 *     <li>images/failed.png
 *     <li>images/passed-BW.png
 *     <li>images/failed-BW.png
 * </ul>
 *
 * @author Shannon Hickey
 */
public class TablePrintDemo2 extends TablePrintDemo1 {

    /*
     * Black and white versions of the check mark and x mark items to
     * render the "Passed" column when printing.
     */
    private static final ImageIcon passedIconBW
        = new ImageIcon(TablePrintDemo1.class.getResource("images/passed-BW.png"));
    private static final ImageIcon failedIconBW
        = new ImageIcon(TablePrintDemo1.class.getResource("images/failed-BW.png"));

    /**
     * Constructs an instance of the demo.
     */
    public TablePrintDemo2() {
        setTitle("Table Print Demo 2");
    }

    /**
     * Overridden to return our BWPassedColumnRenderer.
     */
    protected TableCellRenderer createPassedColumnRenderer() {
        return new BWPassedColumnRenderer();
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
                new TablePrintDemo2().setVisible(true);
            }
        });
    }
    
    /**
     * A custom cell renderer that extends TablePrinteDemo1's renderer, to instead
     * use clearer black and white versions of the icons when printing.
     */
    protected static class BWPassedColumnRenderer extends PassedColumnRenderer {
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value,
                                                           boolean isSelected,
                                                           boolean hasFocus,
                                                           int row,
                                                           int column) {

            super.getTableCellRendererComponent(table, value, isSelected,
                                                hasFocus, row, column);

            /* if we're currently printing, use the black and white icons */
            if (table.isPaintingForPrint()) {
                boolean status = (Boolean)value;
                setIcon(status ? passedIconBW : failedIconBW);
            } /* otherwise, the superclass (colored) icons are used */

            return this;
        }
    }
}
