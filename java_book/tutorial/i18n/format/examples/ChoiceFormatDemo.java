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

public class ChoiceFormatDemo {

   static void displayMessages(Locale currentLocale) {

      System.out.println("currentLocale = " + currentLocale.toString());
      System.out.println();

      ResourceBundle bundle = 
         ResourceBundle.getBundle("ChoiceBundle",currentLocale);

      MessageFormat messageForm = new MessageFormat("");
      messageForm.setLocale(currentLocale);

      double[] fileLimits = {0,1,2};

      String [] fileStrings = {
         bundle.getString("noFiles"),
         bundle.getString("oneFile"),
         bundle.getString("multipleFiles")
      };

      ChoiceFormat choiceForm = new ChoiceFormat(fileLimits, fileStrings);

      String pattern = bundle.getString("pattern");
      Format[] formats = {choiceForm, null, NumberFormat.getInstance()};

      messageForm.applyPattern(pattern);
      messageForm.setFormats(formats);

      Object[] messageArguments = {null, "XDisk", null};

      for (int numFiles = 0; numFiles < 4; numFiles++) {
         messageArguments[0] = new Integer(numFiles);
         messageArguments[2] = new Integer(numFiles);
         String result = messageForm.format(messageArguments);
         System.out.println(result);
      }
   }

   static public void main(String[] args) {
      displayMessages(new Locale("en", "US"));
      System.out.println();
      displayMessages(new Locale("fr", "FR"));
   }
} 
