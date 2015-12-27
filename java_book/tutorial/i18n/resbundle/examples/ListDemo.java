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

public class ListDemo {

   static void displayValues(Locale currentLocale) {

      ResourceBundle stats = 
         ResourceBundle.getBundle("StatsBundle",currentLocale);

      Integer gdp = (Integer)stats.getObject("GDP");
      System.out.println("GDP = " + gdp.toString());
      Integer pop = (Integer)stats.getObject("Population");
      System.out.println("Population = " + pop.toString());
      Double lit = (Double)stats.getObject("Literacy");
      System.out.println("Literacy = " + lit.toString());
      
   } // displayValues

   static public void main(String[] args) {

      Locale[] supportedLocales = {
         new Locale("en","CA"),
         new Locale("ja","JP"),
         new Locale("fr","FR")
      };

      for (int i = 0; i < supportedLocales.length; i ++) {
         System.out.println("Locale = " + supportedLocales[i]);
         displayValues(supportedLocales[i]);
         System.out.println();
      }

   } // main

} // class
