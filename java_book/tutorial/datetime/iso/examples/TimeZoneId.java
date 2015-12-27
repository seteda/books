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
import java.util.Set;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.BufferedWriter;
import java.io.PrintStream;
import java.io.IOException;
/*
 * This program performs two functions:
 *   1. It prints, to standard out, a list of time zone IDs for time zones
 *      that are offset by times other than an hour.
 *   2. It prints a file, called "timeZones", that contains a list of
 *      all time zone IDs.
 */


public class TimeZoneId {
    public static void main(String[] args) {

        // Get the set of all time zone IDs.
        Set<String> allZones = ZoneId.getAvailableZoneIds(); 

        // Create a List using the set of zones and sort it.
        List<String> zoneList = new ArrayList<String>(allZones);
        Collections.sort(zoneList);

        LocalDateTime dt = LocalDateTime.now();

        Path p = Paths.get("timeZones");
        try (BufferedWriter tzfile = Files.newBufferedWriter(p,
                                           StandardCharsets.US_ASCII)) {
            for (String s : zoneList) {
                ZoneId zone = ZoneId.of(s);
                ZonedDateTime zdt = dt.atZone(zone);
                ZoneOffset offset = zdt.getOffset();
                int secondsOfHour = offset.getTotalSeconds() % (60 * 60);
                String out = String.format("%35s %10s%n", zone, offset);

                // Write only time zones that do not have a whole hour offset
                // to standard out.
                if (secondsOfHour != 0) {
                    System.out.printf(out);
                }

                // Write all time zones to the file.
                tzfile.write(out);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
