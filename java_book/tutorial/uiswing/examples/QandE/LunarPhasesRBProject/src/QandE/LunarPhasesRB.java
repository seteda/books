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

package QandE;

/*
 * LunarPhasesRB.java requires the following files:
 *    images/image0.jpg
 *    images/image1.jpg
 *    images/image2.jpg
 *    images/image3.jpg
 *    images/image4.jpg
 *    images/image5.jpg
 *    images/image6.jpg
 *    images/image7.jpg
 * It is a modified version of LunarPhases.java that
 * uses radio buttons instead of a combo box.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;

public class LunarPhasesRB implements ActionListener {
    final static int NUM_IMAGES = 8;
    final static int START_INDEX = 3;

    ImageIcon[] images = new ImageIcon[NUM_IMAGES];
    JPanel mainPanel, selectPanel, displayPanel;

    JLabel phaseIconLabel = null;

    public LunarPhasesRB() {
        //Create the phase selection and display panels.
        selectPanel = new JPanel();
        displayPanel = new JPanel();

        //Add various widgets to the sub panels.
        addWidgets();

        //Create the main panel to contain the two sub panels.
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        //Add the select and display panels to the main panel.
        mainPanel.add(selectPanel);
        mainPanel.add(displayPanel);
    }

    /*
     * Get the images and set up the widgets.
     */
    private void addWidgets() {
        /*
         * Create a label for displaying the moon phase images and
         * put a border around it.
         */
        phaseIconLabel = new JLabel();
        phaseIconLabel.setHorizontalAlignment(JLabel.CENTER);
        phaseIconLabel.setVerticalAlignment(JLabel.CENTER);
        phaseIconLabel.setVerticalTextPosition(JLabel.CENTER);
        phaseIconLabel.setHorizontalTextPosition(JLabel.CENTER);
        phaseIconLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(5,5,5,5)));

        phaseIconLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(0,0,10,0),
            phaseIconLabel.getBorder()));

	//Create radio buttons with lunar phase choices.
	JRadioButton newButton = new JRadioButton("New");
	newButton.setActionCommand("0");
	newButton.setSelected(true);

	JRadioButton waxingCrescentButton =  new JRadioButton("Waxing Crescent");
	waxingCrescentButton.setActionCommand("1");

	JRadioButton firstQuarterButton = new JRadioButton("First Quarter");
	firstQuarterButton.setActionCommand("2");

	JRadioButton waxingGibbousButton = new JRadioButton("Waxing Gibbous");
	waxingGibbousButton.setActionCommand("3");

	JRadioButton fullButton = new JRadioButton("Full");
	fullButton.setActionCommand("4");

	JRadioButton waningGibbousButton = new JRadioButton("Waning Gibbous");
	waningGibbousButton.setActionCommand("5");

	JRadioButton thirdQuarterButton = new JRadioButton("Third Quarter");
	thirdQuarterButton.setActionCommand("6");

	JRadioButton waningCrescentButton = new JRadioButton("Waning Crescent");
	waningCrescentButton.setActionCommand("7");

	// Create a button group and add the radio buttons.
	ButtonGroup group = new ButtonGroup();
	group.add(newButton);
	group.add(waxingCrescentButton);
	group.add(firstQuarterButton);
	group.add(waxingGibbousButton);
	group.add(fullButton);
	group.add(waningGibbousButton);
	group.add(thirdQuarterButton);
	group.add(waningCrescentButton);

        // Display the first image.
        phaseIconLabel.setIcon(new ImageIcon("images/image0.jpg"));
        phaseIconLabel.setText("");

	  //Make the radio buttons appear in a center-aligned column.
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.PAGE_AXIS));
	  selectPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Add a border around the select panel.
        selectPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Select Phase"), 
            BorderFactory.createEmptyBorder(5,5,5,5)));

        //Add a border around the display panel.
        displayPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Display Phase"), 
            BorderFactory.createEmptyBorder(5,5,5,5)));

        //Add image and moon phases radio buttons to select panel.
        displayPanel.add(phaseIconLabel);
 	  selectPanel.add(newButton);
	  selectPanel.add(waxingCrescentButton);
	  selectPanel.add(firstQuarterButton);
	  selectPanel.add(waxingGibbousButton);
	  selectPanel.add(fullButton);
	  selectPanel.add(waningGibbousButton);
	  selectPanel.add(thirdQuarterButton);
	  selectPanel.add(waningCrescentButton);

        //Listen to events from the radio buttons.
	  newButton.addActionListener(this);
	  waxingCrescentButton.addActionListener(this);
	  firstQuarterButton.addActionListener(this);
	  waxingGibbousButton.addActionListener(this);
	  fullButton.addActionListener(this);
	  waningGibbousButton.addActionListener(this);
	  thirdQuarterButton.addActionListener(this);
	  waningCrescentButton.addActionListener(this);

    }

    // Load the selected image (lazy image loading).
    public void actionPerformed(ActionEvent event) {
	  phaseIconLabel.setIcon(new ImageIcon("images/image" 
					     + event.getActionCommand() 
					     + ".jpg"));
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create a new instance of LunarPhasesRB.
        LunarPhasesRB phases = new LunarPhasesRB();

        //Create and set up the window.
        JFrame lunarPhasesFrame = new JFrame("Lunar Phases");
        lunarPhasesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        lunarPhasesFrame.setContentPane(phases.mainPanel);

        //Display the window.
        lunarPhasesFrame.pack();
        lunarPhasesFrame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
