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

public class CollatorDemo {

   public static void sortStrings(Collator collator, String[] words) {
       String tmp;
       for (int i = 0; i < words.length; i++) {
           for (int j = i + 1; j < words.length; j++) {
               // Compare elements of the array two at a time.
               if (collator.compare(words[i], words[j] ) > 0 ) {
                   // Swap words[i] and words[j] 
                   tmp = words[i];
                   words[i] = words[j];
                   words[j] = tmp;
               }
           }
       }
   }

   public static void printStrings(String [] words) {
       for (int i = 0; i < words.length; i++) {
          System.out.println(words[i]);
       }
   }

   public static void testCompare() {

      Collator myCollator = Collator.getInstance(new Locale("en", "US"));

      System.out.println(myCollator.compare("abc", "def")); 
      System.out.println(myCollator.compare("rtf", "rtf")); 
      System.out.println(myCollator.compare("xyz", "abc")); 
   }

   static public void main(String[] args) {

      testCompare();
      System.out.println();

      Collator fr_FRCollator = Collator.getInstance(new Locale("fr","FR"));
      Collator en_USCollator = Collator.getInstance(new Locale("en","US"));
 
      String eWithCircumflex = new String("\u00EA");
      String eWithAcute = new String("\u00E9");
 
      String peachfr = "p" + eWithAcute + "ch" + eWithAcute;
      String sinfr = "p" + eWithCircumflex + "che";
 
      String [] words = {
        peachfr,
        sinfr,
        "peach",
        "sin"
      };
 
      sortStrings(fr_FRCollator, words);
      System.out.println("Locale: fr_FR");
      printStrings(words);
 
      System.out.println();
 
      sortStrings(en_USCollator, words);
      System.out.println("Locale: en_US");
      printStrings(words);
   }
}
