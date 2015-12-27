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
import javax.jnlp.DownloadServiceListener;
import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.*;
import java.net.URL;

/**
 * A custom progress bar can be integrated into the applet's UI. How things work:
 * - IntegratedProgressIndicator implements DownloadServiceListener
 * - WeatherData is a top level JPanel class that builds and manages the 
 *   overall UI (applet and custom progress UI).
 * - In IntegratedProgressIndicator's updateProgressUI, add the top level JPanel (WeatherData)
 *   to surfaceContainer when overallPercent is greater than 0 but less than  100
 * - In the progress(), upgradingArchive(), and validating() methods, pass the 
 *   overallPercent to the weatherDataPanel.updateProgress() method, so that the 
 *   progress bar can be updated. (see updateProgressUI() method).
 * - When overallPercent > 99, remove the top level JPanel class (WeatherData) 
 *   from the surfaceContainer.
 */
public class IntegratedProgressIndicator implements DownloadServiceListener {
    Container surfaceContainer = null;
    JFrame frame = null;
    WeatherData weatherDataPanel = null;
    IntegratedProgressIndicator integratedProgressIndicator = null;
    boolean uiCreated = false;
    boolean createUIInFrame = false;

    public IntegratedProgressIndicator(Object surface) {
        try {
            surfaceContainer = (Container) surface;
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        }
    }

    public void downloadFailed(java.net.URL url, java.lang.String version) {        
    }

    public void progress(URL url, String version, long readSoFar,
                         long total, int overallPercent) {
        // check progress of download and update display
        updateProgressUI(overallPercent);

    }

    public void upgradingArchive(java.net.URL url,
                      java.lang.String version,
                      int patchPercent,
                      int overallPercent) {
        updateProgressUI(overallPercent);
    }

    public  void validating(java.net.URL url,
                java.lang.String version,
                long entry,
                long total,
                int overallPercent) {
        updateProgressUI(overallPercent);
    }

    private void updateProgressUI(int overallPercent) {
        if (!uiCreated && overallPercent > 0 && overallPercent < 100) {
            // create custom progress indicator's UI only if 
            // there is more work to do, meaning overallPercent > 0 and < 100
            // this prevents flashing when RIA is loaded from cache
            create(); 
            uiCreated = true;
        }
        if (uiCreated) {
            weatherDataPanel.updateProgress(overallPercent);
            if (overallPercent > 99 && surfaceContainer!= null) {
                surfaceContainer.remove(weatherDataPanel);
            }
        }
    }

    private void create() {
        weatherDataPanel = WeatherData.getWeatherDataPanel(true);
        if (surfaceContainer != null) {
            // lay out custom progress UI in the given Container
            surfaceContainer.add(weatherDataPanel, BorderLayout.NORTH);
            surfaceContainer.invalidate();
            surfaceContainer.validate();
        }
    }

}


