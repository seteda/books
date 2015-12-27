/*
 * Copyright (c) 1995, 2011, Oracle and/or its affiliates. All rights reserved.
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

package com.oracle.tutorial.jdbc;

import com.sun.rowset.CachedRowSetImpl;
import javax.sql.RowSetEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;

public class CoffeesFrame extends JFrame implements RowSetListener {

  JDBCTutorialUtilities settings;
  Connection connection;
  JTable table; // The table for displaying data

  JLabel label_COF_NAME;
  JLabel label_SUP_ID;
  JLabel label_PRICE;
  JLabel label_SALES;
  JLabel label_TOTAL;

  JTextField textField_COF_NAME;
  JTextField textField_SUP_ID;
  JTextField textField_PRICE;
  JTextField textField_SALES;
  JTextField textField_TOTAL;

  JButton button_ADD_ROW;
  JButton button_UPDATE_DATABASE;
  JButton button_DISCARD_CHANGES;

  CoffeesTableModel myCoffeesTableModel;

  public CoffeesFrame(JDBCTutorialUtilities settingsArg) throws SQLException {


    super("The Coffee Break: COFFEES Table"); // Set window title

    this.settings = settingsArg;
    connection = settings.getConnection();

    // Close connections exit the application when the user
    // closes the window

    addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {

          try {
            connection.close();
          } catch (SQLException sqle) {
            JDBCTutorialUtilities.printSQLException(sqle);
          }
          System.exit(0);
        }
      });

    // Initialize and lay out window controls

    CachedRowSet myCachedRowSet = getContentsOfCoffeesTable();
    myCoffeesTableModel = new CoffeesTableModel(myCachedRowSet);
    myCoffeesTableModel.addEventHandlersToRowSet(this);

    table = new JTable(); // Displays the table
    table.setModel(myCoffeesTableModel);

    label_COF_NAME = new JLabel();
    label_SUP_ID = new JLabel();
    label_PRICE = new JLabel();
    label_SALES = new JLabel();
    label_TOTAL = new JLabel();

    textField_COF_NAME = new JTextField(10);
    textField_SUP_ID = new JTextField(10);
    textField_PRICE = new JTextField(10);
    textField_SALES = new JTextField(10);
    textField_TOTAL = new JTextField(10);

    button_ADD_ROW = new JButton();
    button_UPDATE_DATABASE = new JButton();
    button_DISCARD_CHANGES = new JButton();

    label_COF_NAME.setText("Coffee Name:");
    label_SUP_ID.setText("Supplier ID:");
    label_PRICE.setText("Price:");
    label_SALES.setText("Sales:");
    label_TOTAL.setText("Total Sales:");

    textField_COF_NAME.setText("Enter new coffee name");
    textField_SUP_ID.setText("101");
    textField_PRICE.setText("0");
    textField_SALES.setText("0");
    textField_TOTAL.setText("0");

    button_ADD_ROW.setText("Add row to table");
    button_UPDATE_DATABASE.setText("Update database");
    button_DISCARD_CHANGES.setText("Discard changes");

    // Place the components within the container contentPane; use GridBagLayout
    // as the layout.

    Container contentPane = getContentPane();
    contentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    contentPane.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    c.fill = GridBagConstraints.BOTH;
    c.anchor = GridBagConstraints.CENTER;
    c.weightx = 0.5;
    c.weighty = 1.0;
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 2;
    contentPane.add(new JScrollPane(table), c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_START;
    c.weightx = 0.25;
    c.weighty = 0;
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 1;
    contentPane.add(label_COF_NAME, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_END;
    c.weightx = 0.75;
    c.weighty = 0;
    c.gridx = 1;
    c.gridy = 1;
    c.gridwidth = 1;
    contentPane.add(textField_COF_NAME, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 0.25;
    c.weighty = 0;
    c.anchor = GridBagConstraints.LINE_START;
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 1;
    contentPane.add(label_SUP_ID, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_END;
    c.weightx = 0.75;
    c.weighty = 0;
    c.gridx = 1;
    c.gridy = 2;
    c.gridwidth = 1;
    contentPane.add(textField_SUP_ID, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_START;
    c.weightx = 0.25;
    c.weighty = 0;
    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 1;
    contentPane.add(label_PRICE, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_END;
    c.weightx = 0.75;
    c.weighty = 0;
    c.gridx = 1;
    c.gridy = 3;
    c.gridwidth = 1;
    contentPane.add(textField_PRICE, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_START;
    c.weightx = 0.25;
    c.weighty = 0;
    c.gridx = 0;
    c.gridy = 4;
    c.gridwidth = 1;
    contentPane.add(label_SALES, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_END;
    c.weightx = 0.75;
    c.weighty = 0;
    c.gridx = 1;
    c.gridy = 4;
    c.gridwidth = 1;
    contentPane.add(textField_SALES, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_START;
    c.weightx = 0.25;
    c.weighty = 0;
    c.gridx = 0;
    c.gridy = 5;
    c.gridwidth = 1;
    contentPane.add(label_TOTAL, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_END;
    c.weightx = 0.75;
    c.weighty = 0;
    c.gridx = 1;
    c.gridy = 5;
    c.gridwidth = 1;
    contentPane.add(textField_TOTAL, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_START;
    c.weightx = 0.5;
    c.weighty = 0;
    c.gridx = 0;
    c.gridy = 6;
    c.gridwidth = 1;
    contentPane.add(button_ADD_ROW, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_END;
    c.weightx = 0.5;
    c.weighty = 0;
    c.gridx = 1;
    c.gridy = 6;
    c.gridwidth = 1;
    contentPane.add(button_UPDATE_DATABASE, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.LINE_START;
    c.weightx = 0.5;
    c.weighty = 0;
    c.gridx = 0;
    c.gridy = 7;
    c.gridwidth = 1;
    contentPane.add(button_DISCARD_CHANGES, c);

    // Add listeners for the buttons in the application

    button_ADD_ROW.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {

          JOptionPane.showMessageDialog(CoffeesFrame.this,
                                        new String[] {
                "Adding the following row:",
                "Coffee name: [" + textField_COF_NAME.getText() + "]",
                "Supplier ID: [" + textField_SUP_ID.getText() + "]",
                "Price: [" + textField_PRICE.getText() + "]",
                "Sales: [" + textField_SALES.getText() + "]",
                "Total: [" + textField_TOTAL.getText() + "]" });


          try {

            myCoffeesTableModel.insertRow(textField_COF_NAME.getText(),
                                          Integer.parseInt(textField_SUP_ID.getText().trim()),
                                          Float.parseFloat(textField_PRICE.getText().trim()),
                                          Integer.parseInt(textField_SALES.getText().trim()),
                                          Integer.parseInt(textField_TOTAL.getText().trim()));
          } catch (SQLException sqle) {
            displaySQLExceptionDialog(sqle);
          }
        }
      });

    button_UPDATE_DATABASE.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
          try {
            myCoffeesTableModel.coffeesRowSet.acceptChanges();
          } catch (SQLException sqle) {
            displaySQLExceptionDialog(sqle);
            // Now revert back changes
            try {
              createNewTableModel();
            } catch (SQLException sqle2) {
              displaySQLExceptionDialog(sqle2);
            }
          }
        }
      });

    button_DISCARD_CHANGES.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            createNewTableModel();
          } catch (SQLException sqle) {
            displaySQLExceptionDialog(sqle);
          }
        }
      });
  }

  private void displaySQLExceptionDialog(SQLException e) {

    // Display the SQLException in a dialog box
    JOptionPane.showMessageDialog(
      CoffeesFrame.this,
      new String[] {
        e.getClass().getName() + ": ",
        e.getMessage()
      }
    );
  }

  private void createNewTableModel() throws SQLException {
    myCoffeesTableModel = new CoffeesTableModel(getContentsOfCoffeesTable());
    myCoffeesTableModel.addEventHandlersToRowSet(this);
    table.setModel(myCoffeesTableModel);
  }

  public static void main(String[] args) throws Exception {
    JDBCTutorialUtilities myJDBCTutorialUtilities;
    if (args[0] == null) {
      System.err.println("Properties file not specified at command line");
      return;
    } else {
      try {
        myJDBCTutorialUtilities = new JDBCTutorialUtilities(args[0]);
      } catch (Exception e) {
        System.err.println("Problem reading properties file " + args[0]);
        e.printStackTrace();
        return;
      }
    }
    try {
      CoffeesFrame qf = new CoffeesFrame(myJDBCTutorialUtilities);
      qf.pack();
      qf.setVisible(true);
    } catch (SQLException sqle) {
      JDBCTutorialUtilities.printSQLException(sqle);
    }
    catch (Exception e) {
      System.out.println("Unexpected exception");
      e.printStackTrace();
    }
  }

  public CachedRowSet getContentsOfCoffeesTable() throws SQLException {
    CachedRowSet crs = null;
    try {
      connection = settings.getConnection();
      crs = new CachedRowSetImpl();
      crs.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
      crs.setConcurrency(ResultSet.CONCUR_UPDATABLE);
      crs.setUsername(settings.userName);
      crs.setPassword(settings.password);

      // In MySQL, to disable auto-commit, set the property relaxAutoCommit to
      // true in the connection URL.

      if (this.settings.dbms.equals("mysql")) {
        crs.setUrl(settings.urlString + "?relaxAutoCommit=true");
      } else {
        crs.setUrl(settings.urlString);
      }

      // Regardless of the query, fetch the contents of COFFEES

      crs.setCommand("select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES");
      crs.execute();

    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    }
    return crs;
  }


  public void actionPerformed(ActionEvent event) {  }

  public void rowSetChanged(RowSetEvent event) {  }

  public void rowChanged(RowSetEvent event) {

    CachedRowSet currentRowSet = this.myCoffeesTableModel.coffeesRowSet;

    try {
      currentRowSet.moveToCurrentRow();
      myCoffeesTableModel =
        new CoffeesTableModel(myCoffeesTableModel.getCoffeesRowSet());
      table.setModel(myCoffeesTableModel);

    } catch (SQLException ex) {

      JDBCTutorialUtilities.printSQLException(ex);

      // Display the error in a dialog box.

      JOptionPane.showMessageDialog(
        CoffeesFrame.this,
        new String[] { // Display a 2-line message
          ex.getClass().getName() + ": ",
          ex.getMessage()
        }
      );
    }
  }

  public void cursorMoved(RowSetEvent event) {  }
}
