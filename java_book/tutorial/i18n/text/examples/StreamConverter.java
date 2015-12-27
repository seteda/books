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


import java.io.*;
import java.util.*;

public class StreamConverter {

   static void writeOutput(String str) {

       try {
           FileOutputStream fos = new FileOutputStream("test.txt");
           Writer out = new OutputStreamWriter(fos, "UTF8");
           out.write(str);
           out.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   static String readInput() {

      StringBuffer buffer = new StringBuffer();
      try {
          FileInputStream fis = new FileInputStream("test.txt");
          InputStreamReader isr = new InputStreamReader(fis, "UTF8");
          Reader in = new BufferedReader(isr);
          int ch;
          while ((ch = in.read()) > -1) {
             buffer.append((char)ch);
          }
          in.close();
          return buffer.toString();
      } catch (IOException e) {
          e.printStackTrace();
          return null;
      }
   }

   public static void main(String[] args) {

      String jaString  = 
         new String("\u65e5\u672c\u8a9e\u6587\u5b57\u5217");

      writeOutput(jaString);
      String inputString = readInput();
      String displayString = jaString + " " + inputString;
      new ShowString(displayString, "Conversion Demo");
   }

}

