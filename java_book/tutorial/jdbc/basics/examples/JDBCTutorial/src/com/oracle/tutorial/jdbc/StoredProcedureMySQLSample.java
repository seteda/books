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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class StoredProcedureMySQLSample {

  private String dbName;
  private Connection con;
  private String dbms;

  public StoredProcedureMySQLSample(Connection connArg, String dbName,
                                    String dbmsArg) {
    super();
    this.con = connArg;
    this.dbName = dbName;
    this.dbms = dbmsArg;
  }
  
  public void createProcedureRaisePrice() throws SQLException {
    
    String createProcedure = null;

    String queryDrop = "DROP PROCEDURE IF EXISTS RAISE_PRICE";

    createProcedure =
        "create procedure RAISE_PRICE(IN coffeeName varchar(32), IN maximumPercentage float, INOUT newPrice numeric(10,2)) " +
          "begin " +
            "main: BEGIN " +
              "declare maximumNewPrice numeric(10,2); " +
              "declare oldPrice numeric(10,2); " +
              "select COFFEES.PRICE into oldPrice " +
                "from COFFEES " +
                "where COFFEES.COF_NAME = coffeeName; " +
              "set maximumNewPrice = oldPrice * (1 + maximumPercentage); " +
              "if (newPrice > maximumNewPrice) " +
                "then set newPrice = maximumNewPrice; " +
              "end if; " +
              "if (newPrice <= oldPrice) " +
                "then set newPrice = oldPrice;" +
                "leave main; " +
              "end if; " +
              "update COFFEES " +
                "set COFFEES.PRICE = newPrice " +
                "where COFFEES.COF_NAME = coffeeName; " +
              "select newPrice; " +
            "END main; " +
          "end";
    
    Statement stmt = null;
    Statement stmtDrop = null;

    try {
      System.out.println("Calling DROP PROCEDURE");
      stmtDrop = con.createStatement();
      stmtDrop.execute(queryDrop);
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } finally {
      if (stmtDrop != null) { stmtDrop.close(); }
    }


    try {
      stmt = con.createStatement();
      stmt.executeUpdate(createProcedure);
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
    }

    
  }
  
  
  public void createProcedureGetSupplierOfCoffee() throws SQLException {

    String createProcedure = null;

    String queryDrop = "DROP PROCEDURE IF EXISTS GET_SUPPLIER_OF_COFFEE";

    createProcedure =
        "create procedure GET_SUPPLIER_OF_COFFEE(IN coffeeName varchar(32), OUT supplierName varchar(40)) " +
          "begin " +
            "select SUPPLIERS.SUP_NAME into supplierName " +
              "from SUPPLIERS, COFFEES " +
              "where SUPPLIERS.SUP_ID = COFFEES.SUP_ID " +
              "and coffeeName = COFFEES.COF_NAME; " +
            "select supplierName; " +
          "end";
    Statement stmt = null;
    Statement stmtDrop = null;

    try {
      System.out.println("Calling DROP PROCEDURE");
      stmtDrop = con.createStatement();
      stmtDrop.execute(queryDrop);
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } finally {
      if (stmtDrop != null) { stmtDrop.close(); }
    }


    try {
      stmt = con.createStatement();
      stmt.executeUpdate(createProcedure);
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
    }
  }
  

  public void createProcedureShowSuppliers() throws SQLException {
    String createProcedure = null;

    String queryDrop = "DROP PROCEDURE IF EXISTS SHOW_SUPPLIERS";

    createProcedure =
        "create procedure SHOW_SUPPLIERS() " +
          "begin " +
            "select SUPPLIERS.SUP_NAME, COFFEES.COF_NAME " +
              "from SUPPLIERS, COFFEES " +
              "where SUPPLIERS.SUP_ID = COFFEES.SUP_ID " +
              "order by SUP_NAME; " +
          "end";
    Statement stmt = null;
    Statement stmtDrop = null;

    try {
      System.out.println("Calling DROP PROCEDURE");
      stmtDrop = con.createStatement();
      stmtDrop.execute(queryDrop);
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } finally {
      if (stmtDrop != null) { stmtDrop.close(); }
    }


    try {
      stmt = con.createStatement();
      stmt.executeUpdate(createProcedure);
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
    }
  }

  public void runStoredProcedures(String coffeeNameArg, float maximumPercentageArg, float newPriceArg) throws SQLException {
    CallableStatement cs = null;

    try {
      
      System.out.println("\nCalling the procedure GET_SUPPLIER_OF_COFFEE");
      cs = this.con.prepareCall("{call GET_SUPPLIER_OF_COFFEE(?, ?)}");
      cs.setString(1, coffeeNameArg);
      cs.registerOutParameter(2, Types.VARCHAR);
      cs.executeQuery();
            
      String supplierName = cs.getString(2);
      
      if (supplierName != null) {
        System.out.println("\nSupplier of the coffee " + coffeeNameArg + ": " + supplierName);          
      } else {
        System.out.println("\nUnable to find the coffee " + coffeeNameArg);        
      }
      
      System.out.println("\nCalling the procedure SHOW_SUPPLIERS");
      cs = this.con.prepareCall("{call SHOW_SUPPLIERS}");
      ResultSet rs = cs.executeQuery();

      while (rs.next()) {
        String supplier = rs.getString("SUP_NAME");
        String coffee = rs.getString("COF_NAME");
        System.out.println(supplier + ": " + coffee);
      }
      
      System.out.println("\nContents of COFFEES table before calling RAISE_PRICE:");
      CoffeesTable.viewTable(this.con);
      
      System.out.println("\nCalling the procedure RAISE_PRICE");
      cs = this.con.prepareCall("{call RAISE_PRICE(?,?,?)}");
      cs.setString(1, coffeeNameArg);
      cs.setFloat(2, maximumPercentageArg);
      cs.registerOutParameter(3, Types.NUMERIC);
      cs.setFloat(3, newPriceArg);
      
      cs.execute();
      
      System.out.println("\nValue of newPrice after calling RAISE_PRICE: " + cs.getFloat(3));
      
      System.out.println("\nContents of COFFEES table after calling RAISE_PRICE:");
      CoffeesTable.viewTable(this.con);
      


    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } finally {
      if (cs != null) { cs.close(); }
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
      myConnection = myJDBCTutorialUtilities.getConnectionToDatabase();

      StoredProcedureMySQLSample myStoredProcedureSample =
        new StoredProcedureMySQLSample(myConnection,
                                       myJDBCTutorialUtilities.dbName,
                                       myJDBCTutorialUtilities.dbms);

//      JDBCTutorialUtilities.initializeTables(myConnection,
//                                             myJDBCTutorialUtilities.dbName,
//                                             myJDBCTutorialUtilities.dbms);


      System.out.println("\nCreating SHOW_SUPPLIERS stored procedure");
      myStoredProcedureSample.createProcedureShowSuppliers();
      
      System.out.println("\nCreating GET_SUPPLIER_OF_COFFEE stored procedure");
      myStoredProcedureSample.createProcedureGetSupplierOfCoffee();

      System.out.println("\nCreating RAISE_PRICE stored procedure");
      myStoredProcedureSample.createProcedureRaisePrice();
      

      System.out.println("\nCalling all stored procedures:");
      myStoredProcedureSample.runStoredProcedures("Colombian", 0.10f, 19.99f);

    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } finally {
      JDBCTutorialUtilities.closeConnection(myConnection);
    }

  }
}
