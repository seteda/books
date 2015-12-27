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

public class RulesDemo  {

   public static void sortStrings(Collator collator, String[] words) {
       String tmp;
       for (int i = 0; i < words.length; i++) {
           for (int j = i + 1; j < words.length; j++) {
               // Compare elements of the words array
               if( collator.compare(words[i], words[j] ) > 0 ) {
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

   static public void main(String[] args) {

      String englishRules = 
         ("< a,A < b,B < c,C < d,D < e,E < f,F " +
          "< g,G < h,H < i,I < j,J < k,K < l,L " +
          "< m,M < n,N < o,O < p,P < q,Q < r,R " +
          "< s,S < t,T < u,U < v,V < w,W < x,X " +
          "< y,Y < z,Z");
  
      String smallnTilde  = new String("\u00F1");
      String capitalNTilde = new String("\u00D1");
  
      String traditionalSpanishRules = 
         ("< a,A < b,B < c,C " +
          "< ch, cH, Ch, CH "  +
          "< d,D < e,E < f,F " +
          "< g,G < h,H < i,I < j,J < k,K < l,L " +
          "< ll, lL, Ll, LL "  +
          "< m,M < n,N " +
          "< " + smallnTilde + "," + capitalNTilde + " " +
          "< o,O < p,P < q,Q < r,R " +
          "< s,S < t,T < u,U < v,V < w,W < x,X " +
          "< y,Y < z,Z");

       String [] words = {
         "luz",
         "curioso",
         "llama",
         "chalina"
       };

       try {
           RuleBasedCollator enCollator = 
              new RuleBasedCollator(englishRules);
           RuleBasedCollator spCollator = 
              new RuleBasedCollator(traditionalSpanishRules);
 
           sortStrings(enCollator, words);
           printStrings(words);
      
           System.out.println();
      
           sortStrings(spCollator, words);
           printStrings(words);
       } catch (ParseException pe) {
           System.out.println("Parse exception for rules");
       }
   }
}
