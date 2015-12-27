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

import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.Predicate;

public class StateFilter implements Predicate {

  private int lo;
  private int hi;
  private String colName = null;
  private int colNumber = -1;

  public StateFilter(int lo, int hi, int colNumber) {
    this.lo = lo;
    this.hi = hi;
    this.colNumber = colNumber;
  }

  public StateFilter(int lo, int hi, String colName) {
    this.lo = lo;
    this.hi = hi;
    this.colName = colName;
  }

  public boolean evaluate(Object value, String columnName) {

    boolean evaluation = true;
    if (columnName.equalsIgnoreCase(this.colName)) {
      int columnValue = ((Integer)value).intValue();
      if ((columnValue >= this.lo) && (columnValue <= this.hi)) {
        evaluation = true;
      } else {
        evaluation = false;
      }
    }
    return evaluation;
  }

  public boolean evaluate(Object value, int columnNumber) {

    boolean evaluation = true;
    if (this.colNumber == columnNumber) {
      int columnValue = ((Integer)value).intValue();
      if ((columnValue >= this.lo) && (columnValue <= this.hi)) {
        evaluation = true;
      } else {
        evaluation = false;
      }
    }
    return evaluation;
  }


  public boolean evaluate(RowSet rs) {

    CachedRowSet frs = (CachedRowSet)rs;
    boolean evaluation = false;
    try {
      int columnValue = -1;

      if (this.colNumber > 0) {
        columnValue = frs.getInt(this.colNumber);
      } else if (this.colName != null) {
        columnValue = frs.getInt(this.colName);
      } else {
        return false;
      }

      if ((columnValue >= this.lo) && (columnValue <= this.hi)) {
        evaluation = true;
      }
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
      return false;
    } catch (NullPointerException npe) {
      System.out.println("NullPointerException caught");
      return false;
    }
    return evaluation;
  }
}
