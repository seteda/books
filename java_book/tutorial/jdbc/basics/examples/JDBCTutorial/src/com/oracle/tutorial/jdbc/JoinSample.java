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

import com.sun.rowset.JoinRowSetImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JoinRowSet;

public class JoinSample {
  
  Connection con;
  JDBCTutorialUtilities settings;  
  
  public JoinSample(Connection connArg, JDBCTutorialUtilities settingsArg) {
    this.con = connArg;
    this.settings = settingsArg;
  }

  public static void getCoffeesBoughtBySupplier(String supplierName,
                                                Connection con) throws SQLException {
    Statement stmt = null;
    String query =
      "SELECT COFFEES.COF_NAME " + "FROM COFFEES, SUPPLIERS " + "WHERE SUPPLIERS.SUP_NAME LIKE '" +
      supplierName + "' " + "and SUPPLIERS.SUP_ID = COFFEES.SUP_ID";

    try {
      stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      System.out.println("Coffees bought from " + supplierName + ": ");
      while (rs.next()) {
        String coffeeName = rs.getString(1);
        System.out.println("     " + coffeeName);
      }
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
    }
  }
  
  public void testJoinRowSet(String supplierName) throws SQLException {
    
    CachedRowSet coffees = null;
    CachedRowSet suppliers = null;
    JoinRowSet jrs = null;
    
    try {
      coffees = new CachedRowSetImpl();
      coffees.setCommand("SELECT * FROM COFFEES");
      coffees.setUsername(settings.userName);
      coffees.setPassword(settings.password);
      coffees.setUrl(settings.urlString);
      coffees.execute();
      
      suppliers = new CachedRowSetImpl();
      suppliers.setCommand("SELECT * FROM SUPPLIERS");
      suppliers.setUsername(settings.userName);
      suppliers.setPassword(settings.password);
      suppliers.setUrl(settings.urlString);
      suppliers.execute();      
      
      jrs = new JoinRowSetImpl();
      jrs.addRowSet(coffees, "SUP_ID");
      jrs.addRowSet(suppliers, "SUP_ID");
      
      
      System.out.println("Coffees bought from " + supplierName + ": ");
      while (jrs.next()) {
        if (jrs.getString("SUP_NAME").equals(supplierName)) { 
          String coffeeName = jrs.getString(1);
          System.out.println("     " + coffeeName);
        }
      }
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } finally {
      if (jrs != null) { jrs.close(); }
      if (suppliers != null) { suppliers.close(); }
      if (coffees != null) { coffees.close(); }
    }
  }


  public static void main(String[] args) {
    JDBCTutorialUtilities myJDBCTutorialUtilities;
    Connection myConnection = null;
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
      myConnection = myJDBCTutorialUtilities.getConnection();

      System.out.println("\nCoffees bought by each supplier:");
      JoinSample.getCoffeesBoughtBySupplier("Acme, Inc.", myConnection);

      System.out.println("\nUsing JoinRowSet:");
      JoinSample myJoinSample = new JoinSample(myConnection, myJDBCTutorialUtilities);
      myJoinSample.testJoinRowSet("Acme, Inc.");

    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } finally {
      JDBCTutorialUtilities.closeConnection(myConnection);
    }

  }
}
