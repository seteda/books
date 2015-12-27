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

/**
 * This test shows the different buffer capabilities for each
 * GraphicsConfiguration on each GraphicsDevice.
 */
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class wraps a graphics configuration so that it can be
 * displayed nicely in components.
 */
class GCWrapper {
    private GraphicsConfiguration gc;
    private int index;
    
    public GCWrapper(GraphicsConfiguration gc, int index) {
        this.gc = gc;
        this.index = index;
    }
    
    public GraphicsConfiguration getGC() {
        return gc;
    }
    
    public String toString() {
        return gc.toString();
    }
}

/**
 * Main frame class.
 */
public class CapabilitiesTest extends JFrame implements ItemListener {
    
    private JComboBox gcSelection = new JComboBox();
    private JCheckBox imageAccelerated = new JCheckBox("Accelerated", false);
    private JCheckBox imageTrueVolatile = new JCheckBox("Volatile", false);
    private JCheckBox flipping = new JCheckBox("Flipping", false);
    private JLabel flippingMethod = new JLabel("");
    private JCheckBox fullScreen = new JCheckBox("Full Screen Only", false);
    private JCheckBox multiBuffer = new JCheckBox("Multi-Buffering", false);
    private JCheckBox fbAccelerated = new JCheckBox("Accelerated", false);
    private JCheckBox fbTrueVolatile = new JCheckBox("Volatile", false);
    private JCheckBox bbAccelerated = new JCheckBox("Accelerated", false);
    private JCheckBox bbTrueVolatile = new JCheckBox("Volatile", false);
    
    public CapabilitiesTest(GraphicsDevice dev) {
        super(dev.getDefaultConfiguration());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });
        initComponents(getContentPane());
        GraphicsConfiguration[] gcs = dev.getConfigurations();
        for (int i = 0; i < gcs.length; i++) {
            gcSelection.addItem(new GCWrapper(gcs[i], i));
        }
        gcSelection.addItemListener(this);
        gcChanged();
    }
    
    /**
     * Creates and lays out components in the container.
     * See the comments below for an organizational overview by panel.
     */
    private void initComponents(Container c) {
        // +=c=====================================================+
        // ++=gcPanel==============================================+
        // ++                    [gcSelection]                     +
        // ++=capsPanel============================================+
        // +++=imageCapsPanel======================================+
        // +++ [imageAccelerated]                                  +
        // +++ [imageTrueVolatile]                                 +
        // +++=bufferCapsPanel=====================================+
        // ++++=bufferAccessCapsPanel==============================+
        // +++++=flippingPanel=====================================+
        // +++++ [flipping]                                        +
        // +++++=fsPanel===========================================+
        // +++++ [indentPanel][fullScreen]                         +
        // +++++=mbPanel===========================================+
        // +++++ [indentPanel][multiBuffer]                        +
        // ++++=buffersPanel=======================================+
        // +++++=fbPanel===============+=bbPanel===================+
        // +++++                       +                           +
        // +++++ [fbAccelerated]       + [bbAccelerated]           +
        // +++++                       +                           +
        // +++++ [fbTrueVolatile]      + [bbTrueVolatile]          +
        // +++++                       +                           +
        // +=======================================================+
        c.setLayout(new BorderLayout());
        // Graphics Config
        JPanel gcPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        c.add(gcPanel, BorderLayout.NORTH);
        gcSelection.setPreferredSize(new Dimension(400, 30));
        gcPanel.add(gcSelection);
        // Capabilities
        JPanel capsPanel = new JPanel(new BorderLayout());
        c.add(capsPanel, BorderLayout.CENTER);
        // Image Capabilities
        JPanel imageCapsPanel = new JPanel(new GridLayout(2, 1));
        capsPanel.add(imageCapsPanel, BorderLayout.NORTH);
        imageCapsPanel.setBorder(BorderFactory.createTitledBorder(
            "Image Capabilities"));
        imageAccelerated.setEnabled(false);
        imageCapsPanel.add(imageAccelerated);
        imageTrueVolatile.setEnabled(false);
        imageCapsPanel.add(imageTrueVolatile);
        // Buffer Capabilities
        JPanel bufferCapsPanel = new JPanel(new BorderLayout());
        capsPanel.add(bufferCapsPanel, BorderLayout.CENTER);
        bufferCapsPanel.setBorder(BorderFactory.createTitledBorder(
            "Buffer Capabilities"));
        // Buffer Access
        JPanel bufferAccessCapsPanel = new JPanel(new GridLayout(3, 1));
        bufferAccessCapsPanel.setPreferredSize(new Dimension(300, 88));
        bufferCapsPanel.add(bufferAccessCapsPanel, BorderLayout.NORTH);
        // Flipping
        JPanel flippingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bufferAccessCapsPanel.add(flippingPanel);
        flippingPanel.add(flipping);
        flipping.setEnabled(false);
        flippingPanel.add(flippingMethod);
        // Full-screen
        JPanel fsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bufferAccessCapsPanel.add(fsPanel);
        JPanel indentPanel = new JPanel();
        indentPanel.setPreferredSize(new Dimension(30, 30));
        fsPanel.add(indentPanel);
        fsPanel.add(fullScreen);
        fullScreen.setEnabled(false);
        // Multi-buffering
        JPanel mbPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bufferAccessCapsPanel.add(mbPanel);
        indentPanel = new JPanel();
        indentPanel.setPreferredSize(new Dimension(30, 30));
        mbPanel.add(indentPanel);
        mbPanel.add(multiBuffer);
        multiBuffer.setEnabled(false);
        // Front and Back Buffer Capabilities
        JPanel buffersPanel = new JPanel(new GridLayout(1, 2));
        bufferCapsPanel.add(buffersPanel, BorderLayout.CENTER);
        // Front Buffer
        JPanel fbPanel = new JPanel(new GridLayout(2, 1));
        fbPanel.setBorder(BorderFactory.createTitledBorder(
            "Front Buffer"));
        buffersPanel.add(fbPanel);
        fbPanel.add(fbAccelerated);
        fbAccelerated.setEnabled(false);
        fbPanel.add(fbTrueVolatile);
        fbTrueVolatile.setEnabled(false);
        // Back Buffer
        JPanel bbPanel = new JPanel(new GridLayout(2, 1));
        bbPanel.setPreferredSize(new Dimension(250, 80));
        bbPanel.setBorder(BorderFactory.createTitledBorder(
            "Back and Intermediate Buffers"));
        buffersPanel.add(bbPanel);
        bbPanel.add(bbAccelerated);
        bbAccelerated.setEnabled(false);
        bbPanel.add(bbTrueVolatile);
        bbTrueVolatile.setEnabled(false);
    }
    
    public void itemStateChanged(ItemEvent ev) {
        gcChanged();
    }
    
    private void gcChanged() {
        GCWrapper wrap = (GCWrapper)gcSelection.getSelectedItem();
        //assert wrap != null;
        GraphicsConfiguration gc = wrap.getGC();
        //assert gc != null;
        //Image Caps
        ImageCapabilities imageCaps = gc.getImageCapabilities();
        imageAccelerated.setSelected(imageCaps.isAccelerated());
        imageTrueVolatile.setSelected(imageCaps.isTrueVolatile());
        // Buffer Caps
        BufferCapabilities bufferCaps = gc.getBufferCapabilities();
        flipping.setSelected(bufferCaps.isPageFlipping());
        flippingMethod.setText(getFlipText(bufferCaps.getFlipContents()));
        fullScreen.setSelected(bufferCaps.isFullScreenRequired());
        multiBuffer.setSelected(bufferCaps.isMultiBufferAvailable());
        // Front buffer caps
        imageCaps = bufferCaps.getFrontBufferCapabilities();
        fbAccelerated.setSelected(imageCaps.isAccelerated());
        fbTrueVolatile.setSelected(imageCaps.isTrueVolatile());
        imageCaps = bufferCaps.getFrontBufferCapabilities();
        // Back buffer caps
        imageCaps = bufferCaps.getBackBufferCapabilities();
        bbAccelerated.setSelected(imageCaps.isAccelerated());
        bbTrueVolatile.setSelected(imageCaps.isTrueVolatile());
    }
    
    private static String getFlipText(BufferCapabilities.FlipContents flip) {
        if (flip == null) {
            return "";
        } else if (flip == BufferCapabilities.FlipContents.UNDEFINED) {
            return "Method Unspecified";
        } else if (flip == BufferCapabilities.FlipContents.BACKGROUND) {
            return "Cleared to Background";
        } else if (flip == BufferCapabilities.FlipContents.PRIOR) {
            return "Previous Front Buffer";
        } else { // if (flip == BufferCapabilities.FlipContents.COPIED)
            return "Copied";
        }
    }
    
    public static void main(String[] args) {
        GraphicsEnvironment ge =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();
        for (int i = 0; i < devices.length; i++) {
            CapabilitiesTest tst = new CapabilitiesTest(devices[i]);
            tst.pack();
            tst.setVisible(true);
        }
    }
}
