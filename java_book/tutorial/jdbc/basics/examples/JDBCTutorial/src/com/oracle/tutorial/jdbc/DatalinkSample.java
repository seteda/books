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

// Java io imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Java net imports
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

// SQL imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatalinkSample {
   
  private String dbName;
  private Connection con;
  private String dbms;
  private JDBCTutorialUtilities settings;
  private static String proxy = "http://www-proxy.us.oracle.com:80";
  
  public DatalinkSample(Connection connArg, JDBCTutorialUtilities settingsArg) {
    super();
    this.con = connArg;
    this.dbName = settingsArg.dbName;
    this.dbms = settingsArg.dbms;
    this.settings = settingsArg;
  }
  
  public static void viewTable(Connection con, Proxy proxy) throws SQLException,
                                                      IOException {
    Statement stmt = null;
    String query = "SELECT document_name, url FROM data_repository";

    try {
      stmt = con.createStatement();      
      ResultSet rs = stmt.executeQuery(query);
      
      if ( rs.next() )  {
        String documentName = null;
        java.net.URL url = null;
        
        documentName = rs.getString(1);

        // Retrieve the value as a URL object.
        url = rs.getURL(2);    
            
        if (url != null) {

          // Retrieve the contents from the URL.
          URLConnection myURLConnection = url.openConnection(proxy);
          BufferedReader bReader =
            new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
          
          System.out.println("Document name: " + documentName);
          
          String pageContent = null;
          
          while ((pageContent = bReader.readLine()) != null ) {
            // Print the URL contents
            System.out.println(pageContent);
          }
        } else { 
          System.out.println("URL is null");
        } 
      }
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } catch(IOException ioEx) {
      System.out.println("IOException caught: " + ioEx.toString());
    } catch (Exception ex) {
      System.out.println("Unexpected exception");
      ex.printStackTrace();
    } finally {
      if (stmt != null) { stmt.close(); }
    }
  }
  
  
  public void addURLRow(String description, String url) throws SQLException {
    
    PreparedStatement pstmt = null;
    
    try {
      pstmt = this.con.prepareStatement(
        "INSERT INTO data_repository(document_name,url) VALUES (?,?)");
      pstmt.setString(1, description);
      pstmt.setURL(2,new URL(url));
      pstmt.execute();    
    } catch (SQLException sqlex) {
      JDBCTutorialUtilities.printSQLException(sqlex);
    } catch (Exception ex) {
      System.out.println("Unexpected exception");
      ex.printStackTrace();
    } finally {
      if (pstmt != null) { pstmt.close(); }
    }
  }

  public static void main(String[] args)  {
    
    JDBCTutorialUtilities myJDBCTutorialUtilities;
    Connection myConnection = null;
    Proxy myProxy;
    InetSocketAddress myProxyServer;

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
      DatalinkSample myDatalinkSample =
        new DatalinkSample(myConnection, myJDBCTutorialUtilities);
       myDatalinkSample.addURLRow("Oracle", "http://www.oracle.com");

      // myProxyServer = new InetSocketAddress("www-proxy.example.com", 80);
      // myProxy = new Proxy(Proxy.Type.HTTP, myProxyServer);
      
      DatalinkSample.viewTable(myConnection, Proxy.NO_PROXY);
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } catch (Exception ex) {
      System.out.println("Unexpected exception");
      ex.printStackTrace();
    }
    finally {
      JDBCTutorialUtilities.closeConnection(myConnection);
    }
  }
}