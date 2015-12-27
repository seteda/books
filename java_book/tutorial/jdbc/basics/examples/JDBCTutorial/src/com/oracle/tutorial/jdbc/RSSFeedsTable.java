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

import java.io.IOException;

import java.io.StringReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class RSSFeedsTable {

  private String dbName;
  private Connection con;
  private String dbms;


  public RSSFeedsTable(Connection connArg, String dbNameArg, String dbmsArg) {
    super();
    this.con = connArg;
    this.dbName = dbNameArg;
    this.dbms = dbmsArg;
  }

  public void createTable() throws SQLException {

    Statement stmt = null;
    try {

      if (this.dbms.equals("derby")) {
        String createString =
          "create table RSS_FEEDS (RSS_NAME varchar(32) NOT NULL," +
          "  RSS_FEED_XML xml NOT NULL, PRIMARY KEY (RSS_NAME))";
        stmt = con.createStatement();
        stmt.executeUpdate(createString);
      } else if (this.dbms.equals("mysql")) {
        String createString =
          "create table RSS_FEEDS (RSS_NAME varchar(32) NOT NULL," +
          "  RSS_FEED_XML longtext NOT NULL, PRIMARY KEY (RSS_NAME))";
        stmt = con.createStatement();
        stmt.executeUpdate(createString);

      }

    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
    }
  }

  public void dropTable() throws SQLException {
    Statement stmt = null;
    try {
      stmt = con.createStatement();
      if (this.dbms.equals("mysql")) {
        stmt.executeUpdate("DROP TABLE IF EXISTS RSS_FEEDS");
      } else if (this.dbms.equals("derby")) {
        stmt.executeUpdate("DROP TABLE RSS_FEEDS");
      }
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } finally {
      stmt.close();
    }
  }

  public void addRSSFeed(String fileName) throws ParserConfigurationException,
                                                 SAXException, IOException,
                                                 XPathExpressionException,
                                                 TransformerConfigurationException,
                                                 TransformerException,
                                                 SQLException {
    // Parse the document and retrieve the name of the RSS feed

    String titleString = null;

    javax.xml.parsers.DocumentBuilderFactory factory =
      javax.xml.parsers.DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(fileName);

    XPathFactory xPathfactory = XPathFactory.newInstance();

    XPath xPath = xPathfactory.newXPath();

    Node titleElement =
      (Node)xPath.evaluate("/rss/channel/title[1]", doc, XPathConstants.NODE);

    if (titleElement == null) {
      System.out.println("Unable to retrieve title element");
      return;
    } else {
      titleString =
          titleElement.getTextContent().trim().toLowerCase().replaceAll("\\s+",
                                                                        "_");
      System.out.println("title element: [" + titleString + "]");
    }

    System.out.println(JDBCTutorialUtilities.convertDocumentToString(doc));

    PreparedStatement insertRow = null;
    SQLXML rssData = null;

    System.out.println("Current DBMS: " + this.dbms);

    try {
      if (this.dbms.equals("mysql")) {
        // For databases that support the SQLXML data type, this creates a
        // SQLXML object from org.w3c.dom.Document.


        System.out.println("Adding XML file " + fileName);
        String insertRowQuery =
          "insert into RSS_FEEDS (RSS_NAME, RSS_FEED_XML) values" + " (?, ?)";
        insertRow = con.prepareStatement(insertRowQuery);
        insertRow.setString(1, titleString);

        System.out.println("Creating SQLXML object with MySQL");
        rssData = con.createSQLXML();
        System.out.println("Creating DOMResult object");
        DOMResult dom = (DOMResult)rssData.setResult(DOMResult.class);
        dom.setNode(doc);

        insertRow.setSQLXML(2, rssData);
        System.out.println("Running executeUpdate()");
        insertRow.executeUpdate();

      }

      else if (this.dbms.equals("derby")) {

        System.out.println("Adding XML file " + fileName);
        String insertRowQuery =
          "insert into RSS_FEEDS (RSS_NAME, RSS_FEED_XML) values" +
          " (?, xmlparse(document cast (? as clob) preserve whitespace))";
        insertRow = con.prepareStatement(insertRowQuery);
        insertRow.setString(1, titleString);
        String convertedDoc =
          JDBCTutorialUtilities.convertDocumentToString(doc);
        insertRow.setClob(2, new StringReader(convertedDoc));

        System.out.println("Running executeUpdate()");
        insertRow.executeUpdate();

      }

    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } catch (Exception ex) {
      System.out.println("Another exception caught:");
      ex.printStackTrace();
    }



    finally {
      if (insertRow != null) { insertRow.close(); }
    }
  }

  public void viewTable(Connection con) throws SQLException,
                                               ParserConfigurationException,
                                               SAXException, IOException,
                                               TransformerConfigurationException,
                                               TransformerException {
    Statement stmt = null;
    try {
      stmt = con.createStatement();
      if (this.dbms.equals("derby")) {
        String query =
          "select RSS_NAME, xmlserialize (RSS_FEED_XML as clob) from RSS_FEEDS";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
          String rssName = rs.getString(1);
          String rssFeedXML = rs.getString(2);
          javax.xml.parsers.DocumentBuilderFactory factory =
            javax.xml.parsers.DocumentBuilderFactory.newInstance();
          factory.setNamespaceAware(true);
          DocumentBuilder builder = factory.newDocumentBuilder();
          Document doc =
            builder.parse(new InputSource(new StringReader(rssFeedXML)));
          System.out.println("RSS identifier: " + rssName);
          System.out.println(JDBCTutorialUtilities.convertDocumentToString(doc));
        }
      }
      else if (this.dbms.equals("mysql")) {
        String query = "select RSS_NAME, RSS_FEED_XML from RSS_FEEDS";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
          String rssName = rs.getString(1);
          SQLXML rssFeedXML = rs.getSQLXML(2);
          javax.xml.parsers.DocumentBuilderFactory factory =
            javax.xml.parsers.DocumentBuilderFactory.newInstance();
          factory.setNamespaceAware(true);
          DocumentBuilder builder = factory.newDocumentBuilder();
          Document doc = builder.parse(rssFeedXML.getBinaryStream());
          System.out.println("RSS identifier: " + rssName);
          System.out.println(JDBCTutorialUtilities.convertDocumentToString(doc));
        }
      }
    } catch (SQLException e) {
      JDBCTutorialUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
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

      RSSFeedsTable myRSSFeedsTable =
        new RSSFeedsTable(myConnection, myJDBCTutorialUtilities.dbName,
                          myJDBCTutorialUtilities.dbms);

      myRSSFeedsTable.addRSSFeed("xml/rss-coffee-industry-news.xml");
      myRSSFeedsTable.addRSSFeed("xml/rss-the-coffee-break-blog.xml");
      myRSSFeedsTable.viewTable(myConnection);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JDBCTutorialUtilities.closeConnection(myConnection);
    }

  }

}
