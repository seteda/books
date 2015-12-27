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

public class Palindrome {

    public static boolean isPalindrome(String stringToTest) {
        String workingCopy = removeJunk(stringToTest);
        String reversedCopy = reverse(workingCopy);
        
        return reversedCopy.equalsIgnoreCase(workingCopy);
    }

    protected static String removeJunk(String string) {
        int i, len = string.length();
  	StringBuilder dest = new StringBuilder(len);
	char c;

	for (i = (len - 1); i >= 0; i--) {
	    c = string.charAt(i);
	    if (Character.isLetterOrDigit(c)) {
		dest.append(c);
	    }
	}

        return dest.toString();
    }

    protected static String reverse(String string) {
  	StringBuilder sb = new StringBuilder(string);

        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        String string = "Madam, I'm Adam.";

        System.out.println();
        System.out.println("Testing whether the following "
                         + "string is a palindrome:");
        System.out.println("    " + string);
        System.out.println();

        if (isPalindrome(string)) {
            System.out.println("It IS a palindrome!");
        } else {
            System.out.println("It is NOT a palindrome!");
        }
        System.out.println();
    }
}
