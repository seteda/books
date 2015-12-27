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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.*;
import java.io.*;
import java.sql.BatchUpdateException;
import java.sql.DatabaseMetaData;
import java.sql.RowIdLifetime;
import java.sql.SQLWarning;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class JDBCTutorialUtilities {

  public String dbms;
  public String jarFile;
  public String dbName; 
  public String userName;
  public String password;
  public String urlString;
  
  private String driver;
  private String serverName;
  private int portNumber;
  private Properties prop;
  
  public static void initializeTables(Connection con, String dbNameArg, String dbmsArg) throws SQLException {
    SuppliersTable mySuppliersTable =
      new SuppliersTable(con, dbNameArg, dbmsArg);
    CoffeesTable myCoffeeTable =
      new CoffeesTable(con, dbNameArg, dbmsArg);
    RSSFeedsTable myRSSFeedsTable = 
      new RSSFeedsTable(con, dbNameArg, dbmsArg);
    ProductInformationTable myPIT =
      new ProductInformationTable(con, dbNameArg, dbmsArg);

    System.out.println("\nDropping exisiting PRODUCT_INFORMATION, COFFEES and SUPPLIERS tables");
    myPIT.dropTable();
    myRSSFeedsTable.dropTable();
    myCoffeeTable.dropTable();
    mySuppliersTable.dropTable();

    System.out.println("\nCreating and populating SUPPLIERS table...");

    System.out.println("\nCreating SUPPLIERS table");
    mySuppliersTable.createTable();
    System.out.println("\nPopulating SUPPLIERS table");
    mySuppliersTable.populateTable();

    System.out.println("\nCreating and populating COFFEES table...");

    System.out.println("\nCreating COFFEES table");
    myCoffeeTable.createTable();
    System.out.println("\nPopulating COFFEES table");
    myCoffeeTable.populateTable();
    
    System.out.println("\nCreating RSS_FEEDS table...");    
    myRSSFeedsTable.createTable();
  }
  
  public static void rowIdLifetime(Connection conn) throws SQLException {
    DatabaseMetaData dbMetaData = conn.getMetaData();
    RowIdLifetime lifetime = dbMetaData.getRowIdLifetime();
    switch (lifetime) {
    case ROWID_UNSUPPORTED:
      System.out.println("ROWID type not supported");
      break;
    case ROWID_VALID_FOREVER:
      System.out.println("ROWID has unlimited lifetime");
      break;
    case ROWID_VALID_OTHER:
      System.out.println("ROWID has indeterminate lifetime");
      break;
    case ROWID_VALID_SESSION:  
      System.out.println("ROWID type has lifetime that is valid for at least the containing session");
    break;
    case ROWID_VALID_TRANSACTION:
      System.out.println("ROWID type has lifetime that is valid for at least the containing transaction");
    }
  }
  
  

  public static void cursorHoldabilitySupport(Connection conn) throws SQLException {
    DatabaseMetaData dbMetaData = conn.getMetaData();
    System.out.println("ResultSet.HOLD_CURSORS_OVER_COMMIT = " +
                       ResultSet.HOLD_CURSORS_OVER_COMMIT);
    System.out.println("ResultSet.CLOSE_CURSORS_AT_COMMIT = " +
                       ResultSet.CLOSE_CURSORS_AT_COMMIT);
    System.out.println("Default cursor holdability: " +
                       dbMetaData.getResultSetHoldability());
    System.out.println("Supports HOLD_CURSORS_OVER_COMMIT? " +
                       dbMetaData.supportsResultSetHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT));
    System.out.println("Supports CLOSE_CURSORS_AT_COMMIT? " +
                       dbMetaData.supportsResultSetHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT));
  }

  public JDBCTutorialUtilities(String propertiesFileName) throws FileNotFoundException,
                                                                 IOException,
                                                                 InvalidPropertiesFormatException {
    super();
    this.setProperties(propertiesFileName);
  }

  public static void getWarningsFromResultSet(ResultSet rs) throws SQLException {
    JDBCTutorialUtilities.printWarnings(rs.getWarnings());
  }

  public static void getWarningsFromStatement(Statement stmt) throws SQLException {
    JDBCTutorialUtilities.printWarnings(stmt.getWarnings());
  }  

  public static void printWarnings(SQLWarning warning) throws SQLException {
    if (warning != null) {
      System.out.println("\n---Warning---\n");
      while (warning != null) {
        System.out.println("Message: " + warning.getMessage());
        System.out.println("SQLState: " + warning.getSQLState());
        System.out.print("Vendor error code: ");
        System.out.println(warning.getErrorCode());
        System.out.println("");
        warning = warning.getNextWarning();
      }
    }
  }

  public static boolean ignoreSQLException(String sqlState) {
    if (sqlState == null) {
      System.out.println("The SQL state is not defined!");
      return false;
    }
    // X0Y32: Jar file already exists in schema
    if (sqlState.equalsIgnoreCase("X0Y32"))
      return true;
    // 42Y55: Table already exists in schema
    if (sqlState.equalsIgnoreCase("42Y55"))
      return true;
    return false;
  }

  public static void printBatchUpdateException(BatchUpdateException b) {
    System.err.println("----BatchUpdateException----");
    System.err.println("SQLState:  " + b.getSQLState());
    System.err.println("Message:  " + b.getMessage());
    System.err.println("Vendor:  " + b.getErrorCode());
    System.err.print("Update counts:  ");
    int[] updateCounts = b.getUpdateCounts();
    for (int i = 0; i < updateCounts.length; i++) {
      System.err.print(updateCounts[i] + "   ");
    }
  }

  public static void printSQLException(SQLException ex) {
    for (Throwable e : ex) {
      if (e instanceof SQLException) {
        if (ignoreSQLException(((SQLException)e).getSQLState()) == false) {
          e.printStackTrace(System.err);
          System.err.println("SQLState: " + ((SQLException)e).getSQLState());
          System.err.println("Error Code: " + ((SQLException)e).getErrorCode());
          System.err.println("Message: " + e.getMessage());
          Throwable t = ex.getCause();
          while (t != null) {
            System.out.println("Cause: " + t);
            t = t.getCause();
          }
        }
      }
    }
  }

  public static void alternatePrintSQLException(SQLException ex) {
    while (ex != null) {
      System.err.println("SQLState: " + ex.getSQLState());
      System.err.println("Error Code: " + ex.getErrorCode());
      System.err.println("Message: " + ex.getMessage());
      Throwable t = ex.getCause();
      while (t != null) {
        System.out.println("Cause: " + t);
        t = t.getCause();
      }
      ex = ex.getNextException();
    }
  }

  private void setProperties(String fileName) throws FileNotFoundException,
                                                     IOException,
                                                     InvalidPropertiesFormatException {
    this.prop = new Properties();
    FileInputStream fis = new FileInputStream(fileName);
    prop.loadFromXML(fis);

    this.dbms = this.prop.getProperty("dbms");
    this.jarFile = this.prop.getProperty("jar_file");
    this.driver = this.prop.getProperty("driver");
    this.dbName = this.prop.getProperty("database_name");
    this.userName = this.prop.getProperty("user_name");
    this.password = this.prop.getProperty("password");
    this.serverName = this.prop.getProperty("server_name");
    this.portNumber = Integer.parseInt(this.prop.getProperty("port_number"));

    System.out.println("Set the following properties:");
    System.out.println("dbms: " + dbms);
    System.out.println("driver: " + driver);
    System.out.println("dbName: " + dbName);
    System.out.println("userName: " + userName);
    System.out.println("serverName: " + serverName);
    System.out.println("portNumber: " + portNumber);

  }

  public Connection getConnectionToDatabase() throws SQLException {
    {
      Connection conn = null;
      Properties connectionProps = new Properties();
      connectionProps.put("user", this.userName);
      connectionProps.put("password", this.password);

      // Using a driver manager:

      if (this.dbms.equals("mysql")) {
//        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        conn =
            DriverManager.getConnection("jdbc:" + dbms + "://" + serverName +
                                        ":" + portNumber + "/" + dbName,
                                        connectionProps);
        conn.setCatalog(this.dbName);
      } else if (this.dbms.equals("derby")) {
//        DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        conn =
            DriverManager.getConnection("jdbc:" + dbms + ":" + dbName, connectionProps);
      }
      System.out.println("Connected to database");
      return conn;
    }
  }

  public Connection getConnection() throws SQLException {
    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", this.userName);
    connectionProps.put("password", this.password);
    
    String currentUrlString = null;

    if (this.dbms.equals("mysql")) {
      currentUrlString = "jdbc:" + this.dbms + "://" + this.serverName +
                                      ":" + this.portNumber + "/";
      conn =
          DriverManager.getConnection(currentUrlString,
                                      connectionProps);
      
      this.urlString = currentUrlString + this.dbName;
      conn.setCatalog(this.dbName);
    } else if (this.dbms.equals("derby")) {
      this.urlString = "jdbc:" + this.dbms + ":" + this.dbName;
      
      conn =
          DriverManager.getConnection(this.urlString + 
                                      ";create=true", connectionProps);
      
    }
    System.out.println("Connected to database");
    return conn;
  }

  public Connection getConnection(String userName,
                                  String password) throws SQLException {
    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", userName);
    connectionProps.put("password", password);
    if (this.dbms.equals("mysql")) {
      conn =
          DriverManager.getConnection("jdbc:" + this.dbms + "://" + this.serverName +
                                      ":" + this.portNumber + "/",
                                      connectionProps);
      conn.setCatalog(this.dbName);
    } else if (this.dbms.equals("derby")) {
      conn =
          DriverManager.getConnection("jdbc:" + this.dbms + ":" + this.dbName +
                                      ";create=true", connectionProps);
    }
    return conn;
  }


  public static void createDatabase(Connection connArg, String dbNameArg,
                                    String dbmsArg) {

    if (dbmsArg.equals("mysql")) {
      try {
        Statement s = connArg.createStatement();
        String newDatabaseString =
          "CREATE DATABASE IF NOT EXISTS " + dbNameArg;
        // String newDatabaseString = "CREATE DATABASE " + dbName;
        s.executeUpdate(newDatabaseString);

        System.out.println("Created database " + dbNameArg);
      } catch (SQLException e) {
        printSQLException(e);
      }
    }
  }

  public static void closeConnection(Connection connArg) {
    System.out.println("Releasing all open resources ...");
    try {
      if (connArg != null) {
        connArg.close();
        connArg = null;
      }
    } catch (SQLException sqle) {
      printSQLException(sqle);
    }
  }
  
  public static String convertDocumentToString(Document doc) throws TransformerConfigurationException,
                                                                    TransformerException {
    Transformer t = TransformerFactory.newInstance().newTransformer();
//    t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    StringWriter sw = new StringWriter();
    t.transform(new DOMSource(doc), new StreamResult(sw));
    return sw.toString();
    
    
  }

  public static void main(String[] args) {
    JDBCTutorialUtilities myJDBCTutorialUtilities;
    Connection myConnection = null;
    if (args[0] == null) {
      System.err.println("Properties file not specified at command line");
      return;
    } else {
      try {
        System.out.println("Reading properties file " + args[0]);
        myJDBCTutorialUtilities = new JDBCTutorialUtilities(args[0]);
      } catch (Exception e) {
        System.err.println("Problem reading properties file " + args[0]);
        e.printStackTrace();
        return;
      }
    }

    try {
      myConnection = myJDBCTutorialUtilities.getConnection();
      //      JDBCTutorialUtilities.outputClientInfoProperties(myConnection);
      // myConnection = myJDBCTutorialUtilities.getConnection("root", "root", "jdbc:mysql://localhost:3306/");
      //       myConnection = myJDBCTutorialUtilities.
      //         getConnectionWithDataSource(myJDBCTutorialUtilities.dbName,"derby","", "", "localhost", 3306);

      // Java DB does not have an SQL create database command; it does require createDatabase
      JDBCTutorialUtilities.createDatabase(myConnection,
                                           myJDBCTutorialUtilities.dbName,
                                           myJDBCTutorialUtilities.dbms);

      JDBCTutorialUtilities.cursorHoldabilitySupport(myConnection);
      JDBCTutorialUtilities.rowIdLifetime(myConnection);

    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } catch (Exception e) {
      e.printStackTrace(System.err);
    } finally {
      JDBCTutorialUtilities.closeConnection(myConnection);
    }

  }
}
