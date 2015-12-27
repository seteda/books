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

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClobSample {

  private String dbName;
  private Connection con;
  private String dbms;
  private JDBCTutorialUtilities settings;

  public ClobSample(Connection connArg, JDBCTutorialUtilities settingsArg) {
    super();
    this.con = connArg;
    this.dbName = settingsArg.dbName;
    this.dbms = settingsArg.dbms;
    this.settings = settingsArg;
  }



  public String retrieveExcerpt(String coffeeName,
                                int numChar) throws SQLException {

    String description = null;
    Clob myClob = null;
    PreparedStatement pstmt = null;

    try {
      String sql = "select COF_DESC from COFFEE_DESCRIPTIONS " + "where COF_NAME = ?";
      pstmt = this.con.prepareStatement(sql);
      pstmt.setString(1, coffeeName);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        myClob = rs.getClob(1);
        System.out.println("Length of retrieved Clob: " + myClob.length());
      }
      description = myClob.getSubString(1, numChar);
    } catch (SQLException sqlex) {
      JDBCTutorialUtilities.printSQLException(sqlex);
    } catch (Exception ex) {
      System.out.println("Unexpected exception: " + ex.toString());
    } finally {
      if (pstmt != null) pstmt.close();
    }
    return description;
  }

  public void addRowToCoffeeDescriptions(String coffeeName,
                                         String fileName) throws SQLException {
    PreparedStatement pstmt = null;
    try {
      Clob myClob = this.con.createClob();

      Writer clobWriter = myClob.setCharacterStream(1);
      String str = this.readFile(fileName, clobWriter);
      System.out.println("Wrote the following: " + clobWriter.toString());
      if (this.settings.dbms.equals("mysql")) {
        System.out.println("MySQL, setting String in Clob object with setString method");
        myClob.setString(1, str);
      }
      System.out.println("Length of Clob: " + myClob.length());
      String sql = "INSERT INTO COFFEE_DESCRIPTIONS VALUES(?,?)";
      pstmt = this.con.prepareStatement(sql);
      pstmt.setString(1, coffeeName);
      pstmt.setClob(2, myClob);
      pstmt.executeUpdate();
    } catch (SQLException sqlex) {
      JDBCTutorialUtilities.printSQLException(sqlex);
    } catch (Exception ex) {
      System.out.println("Unexpected exception: " + ex.toString());
    } finally {
      if (pstmt != null) { pstmt.close(); }
    }
  }

  private String readFile(String fileName,
                          Writer writerArg) throws FileNotFoundException,
                                                   IOException {

    BufferedReader br = new BufferedReader(new FileReader(fileName));
    String nextLine = "";
    StringBuffer sb = new StringBuffer();
    while ((nextLine = br.readLine()) != null) {
      System.out.println("Writing: " + nextLine);
      writerArg.write(nextLine);
      sb.append(nextLine);
    }
    // Convert the content into to a string
    String clobData = sb.toString();

    // Return the data.
    return clobData;
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

      ClobSample myClobSample =
        new ClobSample(myConnection, myJDBCTutorialUtilities);
      myClobSample.addRowToCoffeeDescriptions("Colombian",
                                              "txt/colombian-description.txt");
      String description = myClobSample.retrieveExcerpt("Colombian", 10);

      System.out.println(description);


    } catch (Exception e) {
      e.printStackTrace();
    } finally {
        JDBCTutorialUtilities.closeConnection(myConnection);
    }

  }


}
