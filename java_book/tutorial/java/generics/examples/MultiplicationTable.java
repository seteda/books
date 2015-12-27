/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
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

public class MultiplicationTable {

    public static void main(String[] args) throws Exception {

        int begin = 1, end = 9;

        if (args.length == 2) {
            begin = Integer.parseInt(args[0]);
            end = Integer.parseInt(args[1]);
        }

        SortedMap<Integer, List<Integer>> mt = multTable(begin, end);
        printMultiplicationTable(mt);
        System.out.println();
    }

    public static SortedMap<Integer, List<Integer>> multTable(int begin, int end) {

        if (begin < 0 || end < 0 || begin > end)
            return null;

        SortedMap<Integer, List<Integer>> mt = new TreeMap<>();

        for (int i = begin; i <= end; ++i) {
            List<Integer> li = new ArrayList<>();
            for (int k = begin; k <= end; ++k)
                li.add(i * k);

            mt.put(i, li);
        }

        return mt;
    }

    public static void printMultiplicationTable(SortedMap<Integer, List<Integer>> mt) {

        Set<Integer> keys = mt.keySet();
        Integer[] iKeys = keys.toArray(new Integer[0]);
        Integer last = iKeys[iKeys.length - 1];
        int width = Integer.toString(last*last).length() + 1;
        String fmt = "%" + width + "d";

        System.out.printf("%" + width + "s", "");
        for (Integer k : keys)
            System.out.printf(fmt, k);
        System.out.println();

        for (Integer k : keys) {
            System.out.printf(fmt, k);

            List<Integer> li = mt.get(k);
            for (Integer i : li)
                System.out.printf(fmt, i);
            System.out.println();
        }
    }
}
