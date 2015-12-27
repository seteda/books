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

public class RBControl {
    public static void main(String[] args) {
	test(Locale.CHINA);
	test(new Locale("zh", "HK"));
	test(Locale.TAIWAN);
	test(Locale.CANADA);
    }

    private static void test(Locale locale) {
	ResourceBundle rb = ResourceBundle.getBundle("RBControl", locale,
	     new ResourceBundle.Control() {
		 @Override
		 public List<Locale> getCandidateLocales(String baseName, Locale locale) {
		     if (baseName == null)
			 throw new NullPointerException();
		     if (locale.equals(new Locale("zh", "HK"))) {
			 return Arrays.asList(
			     locale,
			     Locale.TAIWAN,
			     // no Locale.CHINESE here
			     Locale.ROOT);
		     } else if (locale.equals(Locale.TAIWAN)) {
			 return Arrays.asList(
			     locale,
			     // no Locale.CHINESE here
			     Locale.ROOT);
		     }
		     return super.getCandidateLocales(baseName, locale);
		 }
	     });
	System.out.println("locale: " + locale);
	System.out.println("\tregion: " + rb.getString("region"));
	System.out.println("\tlanguage: " + rb.getString("language"));
    }
}
