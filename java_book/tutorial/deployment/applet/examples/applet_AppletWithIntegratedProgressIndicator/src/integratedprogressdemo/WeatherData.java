/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
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

package integratedprogressdemo;

import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;


/**
 *
 * @author skannan
 */
public class WeatherData extends JPanel {
    private JPanel progressPanel = null;
    private JProgressBar progressBar = null;
    private static WeatherData weatherDataPanel = null;

    // weather data panel is a singleton
    public static WeatherData getWeatherDataPanel(boolean displayProgress) {
        if (weatherDataPanel == null) {
            weatherDataPanel = new WeatherData(displayProgress);
        } else {
            if (!displayProgress) {
                weatherDataPanel.removeProgressPanel();
            }
        }
        return weatherDataPanel;
    }

    private  WeatherData(boolean displayProgress) {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("World-Wide Weather Data");
        lbl.setFont(new Font("Serif", Font.PLAIN, 18));

        add(lbl, BorderLayout.PAGE_START);
        lbl = new JLabel("Weather information from over 50 cities");
        add(lbl, BorderLayout.LINE_START);

        if (displayProgress) {
            progressPanel = new JPanel();
            progressPanel.setBackground(Color.WHITE);
            progressPanel.setLayout(new BorderLayout(20, 20));

            String lblText = "<html>Stuck in the mud? Make progress with...<br /><font color=red><em>JDK Documentation</em></font><br/></html>";
            lbl = new JLabel(lblText);
            progressPanel.add(lbl, BorderLayout.NORTH);

            progressBar = new JProgressBar(0, 100);
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            progressPanel.add(progressBar, BorderLayout.SOUTH);

            add(progressPanel, BorderLayout.LINE_END);
        }
        
        String[] columnNames = {"City", "Temperature"};
        JTable table = new JTable(getData(), columnNames);

        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(Color.WHITE);

        //Add the scroll pane to this panel.
        add(scrollPane, BorderLayout.PAGE_END);
    }

    private void removeProgressPanel() {
        if (progressPanel != null) {
            remove(progressPanel);
            progressPanel = null;
            progressBar = null;
        }
    }

    public void updateProgress(int overallPercent) {
        if (progressBar != null) {
            progressBar.setValue(overallPercent);
        }
    }

    private Object[][]  getData() {
        // Get current classloader

        Object[][] data = {
            {"London", "55"},
            {"New York", "70"},
            {"Los Angeles", "80"},
            {"New Delhi", "95"},
            {"Tokyo", "60"},
            {"Seoul", "55"},
            {"Shanghai", "60"},
            {"Milan", "64"},
            {"Paris", "66"},
            {"Buenos Aires", "70"},
            {"Washington DC", "80"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Some City", "68"},
            {"Some City", "99"},
            {"Some City", "60"},
            {"Some City", "63"},
            {"Some City", "65"},
            {"Some City", "64"},
            {"Ottowa", "44"},
            {"Sacramento", "100"},
            };
        return data;
    }
}
