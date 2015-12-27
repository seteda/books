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

public class CityFilter implements Predicate {

  private String[] cities;
  private String colName = null;
  private int colNumber = -1;

  public CityFilter(String[] citiesArg, String colNameArg) {
    this.cities = citiesArg;
    this.colNumber = -1;
    this.colName = colNameArg;
  }

  public CityFilter(String[] citiesArg, int colNumberArg) {
    this.cities = citiesArg;
    this.colNumber = colNumberArg;
    this.colName = null;
  }

  public boolean evaluate(Object valueArg, String colNameArg) {

    if (colNameArg.equalsIgnoreCase(this.colName)) {
      for (int i = 0; i < this.cities.length; i++) {
        if (this.cities[i].equalsIgnoreCase((String)valueArg)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean evaluate(Object valueArg, int colNumberArg) {

    if (colNumberArg == this.colNumber) {
      for (int i = 0; i < this.cities.length; i++) {
        if (this.cities[i].equalsIgnoreCase((String)valueArg)) {
          return true;
        }
      }
    }
    return false;
  }


  public boolean evaluate(RowSet rs) {

    if (rs == null)
      return false;

    try {
      for (int i = 0; i < this.cities.length; i++) {

        String cityName = null;

        if (this.colNumber > 0) {
          cityName = (String)rs.getObject(this.colNumber);
        } else if (this.colName != null) {
          cityName = (String)rs.getObject(this.colName);
        } else {
          return false;
        }

        if (cityName.equalsIgnoreCase(cities[i])) {
          return true;
        }
      }
    } catch (SQLException e) {
      return false;
    }
    return false;
  }

}
