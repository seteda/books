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

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;

public class SortThread extends Thread {
    private PrintWriter out = null;
    private BufferedReader in = null;

    public SortThread(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
    }

    public void run() {
        int MAXWORDS = 50;

        if (out != null && in != null) {
            try {
                String[] listOfWords = new String[MAXWORDS];
                int numwords = 0;
 
                while ((listOfWords[numwords] = in.readLine()) != null)
                    numwords++;
                quicksort(listOfWords, 0, numwords-1);
                for (int i = 0; i < numwords; i++)
                    out.println(listOfWords[i]);
                out.close();
            } catch (IOException e) {
                System.err.println("SortThread run: " + e);
            }
        }
    }

    private static void quicksort(String[] a, int lo0, int hi0) {
        int lo = lo0;
        int hi = hi0;

        if (lo >= hi)
            return;

        String mid = a[(lo + hi) / 2];
        while (lo < hi) {
            while (lo<hi && a[lo].compareTo(mid) < 0)
                lo++;
            while (lo<hi && a[hi].compareTo(mid) > 0)
                hi--;
            if (lo < hi) {
                String T = a[lo];
                a[lo] = a[hi];
                a[hi] = T;
                lo++;
                hi--;
            }
        }
        if (hi < lo) {
            int T = hi;
            hi = lo;
            lo = T;
        }
        quicksort(a, lo0, lo);
        quicksort(a, lo == lo0 ? lo+1 : lo, hi0);
    }
}
