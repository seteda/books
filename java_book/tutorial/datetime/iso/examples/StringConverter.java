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
import java.time.*;
import java.time.chrono.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.Locale; 
import java.io.PrintStream;
/*
 * Convert LocalDate -> ChronoLocalDate -> String and back.
 */

public class StringConverter {

    /**
     * Converts a LocalDate (ISO) value to a ChronoLocalDate date
     * using the provided Chronology, and then formats the
     * ChronoLocalDate to a String using a DateTimeFormatter with a
     * SHORT pattern based on the Chronology and the current Locale.
     *
     * @param localDate - the ISO date to convert and format.
     * @param chrono - an optional Chronology. If null, then IsoChronology
     *                 is used.
     */
    public static String toString(LocalDate localDate, Chronology chrono) {
        if (localDate != null) {
            Locale locale = Locale.getDefault(Locale.Category.FORMAT);
            ChronoLocalDate cDate;
            if (chrono == null) {
                chrono = IsoChronology.INSTANCE;
            }
            try {
                cDate = chrono.date(localDate);
            } catch (DateTimeException ex) {
                System.err.println(ex);
                chrono = IsoChronology.INSTANCE;
                cDate = localDate;
            }
            String pattern = "M/d/yyyy GGGGG";
            DateTimeFormatter dateFormatter = 
                              DateTimeFormatter.ofPattern(pattern);
            return dateFormatter.format(cDate);
        } else {
            return "";
        }
    }

    /**
     * Parses a String to a ChronoLocalDate using a DateTimeFormatter
     * with a short pattern based on the current Locale and the
     * provided Chronology, then converts this to a LocalDate (ISO)
     * value.
     *
     * @param text   - the input date text in the SHORT format expected
     *                 for the Chronology and the current Locale.
     *
     * @param chrono - an optional Chronology. If null, then IsoChronology
     *                 is used.
     */
    public static LocalDate fromString(String text, Chronology chrono) {
        if (text != null && !text.isEmpty()) {
            Locale locale = Locale.getDefault(Locale.Category.FORMAT);
            if (chrono == null) {
                chrono = IsoChronology.INSTANCE;
            }
            String pattern = "M/d/yyyy GGGGG";
            DateTimeFormatter df = new DateTimeFormatterBuilder().parseLenient()
                                  .appendPattern(pattern)
                                  .toFormatter()
                                  .withChronology(chrono)
                                  .withDecimalStyle(DecimalStyle.of(locale));
            TemporalAccessor temporal = df.parse(text);
            ChronoLocalDate cDate = chrono.date(temporal);
            return LocalDate.from(cDate);
        }
        return null;
    }

    public static void main(String[] args) {
        LocalDate date = LocalDate.of(1996, Month.OCTOBER, 29);
        System.out.printf("%s%n",
            StringConverter.toString(date, JapaneseChronology.INSTANCE));
        System.out.printf("%s%n",
            StringConverter.toString(date, MinguoChronology.INSTANCE));
        System.out.printf("%s%n",
            StringConverter.toString(date, ThaiBuddhistChronology.INSTANCE));
        System.out.printf("%s%n",
            StringConverter.toString(date, HijrahChronology.INSTANCE));


        System.out.printf("%s%n", StringConverter.fromString("10/29/0008 H",
            JapaneseChronology.INSTANCE));
        System.out.printf("%s%n",
            StringConverter.fromString("10/29/0085 1",
            MinguoChronology.INSTANCE));
        System.out.printf("%s%n",
            StringConverter.fromString("10/29/2539 B.E.",
            ThaiBuddhistChronology.INSTANCE));
        System.out.printf("%s%n",
            StringConverter.fromString("6/16/1417 1",
            HijrahChronology.INSTANCE));
    }
}
