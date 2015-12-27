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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class CoffeesTableModel implements TableModel {

  CachedRowSet coffeesRowSet; // The ResultSet to interpret
  ResultSetMetaData metadata; // Additional information about the results
  int numcols, numrows; // How many rows and columns in the table

  public CachedRowSet getCoffeesRowSet() {
    return coffeesRowSet;
  }


  public CoffeesTableModel(CachedRowSet rowSetArg) throws SQLException {

    this.coffeesRowSet = rowSetArg;
    this.metadata = this.coffeesRowSet.getMetaData();
    numcols = metadata.getColumnCount();

    // Retrieve the number of rows.
    this.coffeesRowSet.beforeFirst();
    this.numrows = 0;
    while (this.coffeesRowSet.next()) {
      this.numrows++;
    }
    this.coffeesRowSet.beforeFirst();
  }

  public void addEventHandlersToRowSet(RowSetListener listener) {
    this.coffeesRowSet.addRowSetListener(listener);
  }


  public void insertRow(String coffeeName, int supplierID, float price,
                        int sales, int total) throws SQLException {

    try {
      this.coffeesRowSet.moveToInsertRow();
      this.coffeesRowSet.updateString("COF_NAME", coffeeName);
      this.coffeesRowSet.updateInt("SUP_ID", supplierID);
      this.coffeesRowSet.updateFloat("PRICE", price);
      this.coffeesRowSet.updateInt("SALES", sales);
      this.coffeesRowSet.updateInt("TOTAL", total);
      this.coffeesRowSet.insertRow();
      this.coffeesRowSet.moveToCurrentRow();
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    }
  }

  public void close() {
    try {
      coffeesRowSet.getStatement().close();
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    }
  }

  /** Automatically close when we're garbage collected */
  protected void finalize() {
    close();
  }

  /** Method from interface TableModel; returns the number of columns */

  public int getColumnCount() {
    return numcols;
  }

    /** Method from interface TableModel; returns the number of rows */

  public int getRowCount() {
    return numrows;
  }

  /** Method from interface TableModel; returns the column name at columnIndex
   *  based on information from ResultSetMetaData
   */

  public String getColumnName(int column) {
    try {
      return this.metadata.getColumnLabel(column + 1);
    } catch (SQLException e) {
      return e.toString();
    }
  }

  /** Method from interface TableModel; returns the most specific superclass for
   *  all cell values in the specified column. To keep things simple, all data
   *  in the table are converted to String objects; hence, this method returns
   *  the String class.
   */

  public Class getColumnClass(int column) {
    return String.class;
  }

  /** Method from interface TableModel; returns the value for the cell specified
   *  by columnIndex and rowIndex. TableModel uses this method to populate
   *  itself with data from the row set. SQL starts numbering its rows and
   *  columns at 1, but TableModel starts at 0.
   */

  public Object getValueAt(int rowIndex, int columnIndex) {

    try {
      this.coffeesRowSet.absolute(rowIndex + 1);
      Object o = this.coffeesRowSet.getObject(columnIndex + 1);
      if (o == null)
        return null;
      else
        return o.toString();
    } catch (SQLException e) {
      return e.toString();
    }
  }

    /** Method from interface TableModel; returns true if the specified cell
     *  is editable. This sample does not allow users to edit any cells from
     *  the TableModel (rows are added by another window control). Thus,
     *  this method returns false.
     */

  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  // Because the sample does not allow users to edit any cells from the
  // TableModel, the following methods, setValueAt, addTableModelListener,
  // and removeTableModelListener, do not need to be implemented.

  public void setValueAt(Object value, int row, int column) {
    System.out.println("Calling setValueAt row " + row + ", column " + column);
  }

  public void addTableModelListener(TableModelListener l) {
  }

  public void removeTableModelListener(TableModelListener l) {
  }

  }
