/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
import java.time.Month;
import java.time.Year;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.DateTimeException;

import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalAccessor;

import java.io.PrintStream;

public class Superstitious {
    
    public static void main(String[] args) {
        Month month = null;
        LocalDate date = null;

        if (args.length < 2) {
            System.out.printf("Usage: Superstitious <month> <day>%n");
            throw new IllegalArgumentException();
        }

        try {
            month = Month.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException exc) {
            System.out.printf("%s is not a valid month.%n", args[0]);
            throw exc;     // Rethrow the exception.
        }

        int day = Integer.parseInt(args[1]);

        try {
            date = Year.now().atMonth(month).atDay(day);
        } catch (DateTimeException exc) {
            System.out.printf("%s %s is not a valid date.%n", month, day);
            throw exc;     // Rethrow the exception.
        }

        System.out.println(date.query(new FridayThirteenQuery()));
    } 
}
