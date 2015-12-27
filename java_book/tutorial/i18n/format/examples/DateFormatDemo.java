/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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


import java.util.*;
import java.text.*;

public class DateFormatDemo {

   static public void displayDate(Locale currentLocale) {

      Date today;
      String dateOut;
      DateFormat dateFormatter;

      dateFormatter = 
         DateFormat.getDateInstance(DateFormat.DEFAULT, currentLocale);
      today = new Date();
      dateOut = dateFormatter.format(today);

      System.out.println(dateOut + "   " + currentLocale.toString());
   }

   static public void showBothStyles(Locale currentLocale) {

      Date today;
      String result;
      DateFormat formatter;

      int[] styles = {
         DateFormat.DEFAULT,
         DateFormat.SHORT, 
         DateFormat.MEDIUM,
         DateFormat.LONG,
         DateFormat.FULL
      };

      System.out.println();
      System.out.println("Locale: " + currentLocale.toString());
      System.out.println();

      today = new Date();

      for (int k = 0; k < styles.length; k++) {
         formatter = DateFormat.getDateTimeInstance(
                     styles[k], styles[k], currentLocale);
         result = formatter.format(today);
         System.out.println(result);
      }
   }

   static public void showDateStyles(Locale currentLocale) {

      Date today = new Date();
      String result;
      DateFormat formatter;

      int[] styles = {
         DateFormat.DEFAULT,
         DateFormat.SHORT, 
         DateFormat.MEDIUM,
         DateFormat.LONG,
         DateFormat.FULL
      };

      System.out.println();
      System.out.println("Locale: " + currentLocale.toString());
      System.out.println();

      for (int k = 0; k < styles.length; k++) {
         formatter = 
            DateFormat.getDateInstance(styles[k], currentLocale);
         result = formatter.format(today);
         System.out.println(result);
      }
   }

   static public void showTimeStyles(Locale currentLocale) {

      Date today = new Date();
      String result;
      DateFormat formatter;

      int[] styles = {
         DateFormat.DEFAULT,
         DateFormat.SHORT, 
         DateFormat.MEDIUM,
         DateFormat.LONG,
         DateFormat.FULL
      };

      System.out.println();
      System.out.println("Locale: " + currentLocale.toString());
      System.out.println();

      for (int k = 0; k < styles.length; k++) {
         formatter = 
            DateFormat.getTimeInstance(styles[k], currentLocale);
         result = formatter.format(today);
         System.out.println(result);
      }
   }

   static public void main(String[] args) {

      Locale[] locales = {
          new Locale("fr","FR"),
          new Locale("de","DE"),
          new Locale("en","US")
      };
     
      for (int i = 0; i < locales.length; i++) {
         displayDate(locales[i]);
      }

      showDateStyles(new Locale("en","US"));
      showDateStyles(new Locale("fr","FR"));

      showTimeStyles(new Locale("en","US"));
      showTimeStyles(new Locale("de","DE"));
 
      showBothStyles(new Locale("en","US"));
      showBothStyles(new Locale("fr","FR"));

   }
}
