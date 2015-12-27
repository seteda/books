/*
 * Copyright (c) 1995, 2009, Oracle and/or its affiliates. All rights reserved.
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

/*
 * Java(TM) SE 6 version.
 * SwingWorker can be downloaded at:
 * https://swingworker.dev.java.net/
 * SwingWorker is included in Java(TM) SE 6.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class QuoteClientApplet extends JApplet
                               implements ActionListener {
    private static boolean DEBUG = false;
    private InetAddress address;
    private JTextField portField;
    private JLabel display;
    private JButton sendButton;
    private JProgressBar progressBar;
    private DatagramSocket socket;
    private String host;
    private static final int MAX_NUM_CHARS = 256;
    private static final int TIMEOUT = 500; //time out after 1/2 a second
    private static String sendButtonText = "OK";
    private QuoteGetter quoteGetter;

    public void init() {
        //Initialize networking stuff.
        host = getCodeBase().getHost();

        try {
            address = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            System.err.println("Couldn't get Internet address: Unknown host");
            // What should we do?
        }

        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(TIMEOUT);
        } catch (IOException e) {
            System.err.println("Couldn't create new DatagramSocket");
            return;
        }

        //Set up the UI.
        //Execute a job on the event-dispatching thread:
        //creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't successfully complete");
        }
    }

    private void createGUI() {
        JPanel contentPane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        int numColumns = 3;

        JLabel l1 = new JLabel("Quote of the Moment:", JLabel.CENTER);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.SOUTH;
        c.gridwidth = numColumns;
        contentPane.add(l1, c);

        display = new JLabel("(no quote received yet)", JLabel.CENTER);
        display.setForeground(Color.gray);
        c.gridy = 1;
        c.gridwidth = numColumns;
        c.anchor = GridBagConstraints.CENTER;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(display, c);

        JLabel l2 = new JLabel("Enter the port (on host " + host
                               + ") to send the request to:",
                               JLabel.RIGHT);
        c.gridy = 2;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.SOUTH;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;
        contentPane.add(l2, c);

        portField = new JTextField(6);
        c.gridx = 1;
        c.weightx = 1.0;
        c.insets = new Insets(0,5,0,0);
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(portField, c);

        sendButton = new JButton(sendButtonText);
        c.gridx = 2;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        contentPane.add(sendButton, c);

        progressBar = new JProgressBar();
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = numColumns;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,0,5,0);
        contentPane.add(progressBar, c);

        portField.addActionListener(this);
        sendButton.addActionListener(this);

        //Finish setting up the content pane and its border.
        contentPane.setBorder(BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(Color.black),
                                    BorderFactory.createEmptyBorder(5,20,5,10)));
        setContentPane(contentPane);
    }

    private class QuoteGetter extends SwingWorker<String, String> {
        int port;
        InetAddress address;

        public QuoteGetter(int port, InetAddress address) {
            this.port = port;
            this.address = address;
        }

         @Override
         public String doInBackground() {
            DatagramPacket packet;
            byte[] sendBuf = new byte[MAX_NUM_CHARS];

            packet = new DatagramPacket(sendBuf, MAX_NUM_CHARS, address, port);

            try { // send request
                if (DEBUG) {
                    System.out.println("Applet about to send packet to address "
                                   + address + " at port " + port);
                }
                socket.send(packet);
                if (DEBUG) {
                    System.out.println("Applet sent packet.");
                }
            } catch (IOException e) {
                System.err.println("Applet socket.send failed:\n"
                                   + e.getStackTrace());
                return null;
            }

            packet = new DatagramPacket(sendBuf, MAX_NUM_CHARS);

            try { // get response
                if (DEBUG) {
                    System.out.println("Applet about to call socket.receive().");
                }
                socket.receive(packet);
                if (DEBUG) {
                    System.out.println("Applet returned from socket.receive().");
                }
            } catch (SocketTimeoutException e) {
                System.err.println("Applet socket.receive timed out.");
                //Should let the user know in the UI.s
                return null;
            } catch (IOException e) {
                System.err.println("Applet socket.receive failed:\n"
                                   + e.getStackTrace());
                return null;
            }

            String received = new String(packet.getData());
            StringBuffer data = new StringBuffer(received);

            //Hack alert! Assuming the last character is garbage, rid
            //this string of all garbage characters.
            int firstGarbage = data.indexOf(Character.toString(
                                            received.charAt(MAX_NUM_CHARS - 1)));
            if (firstGarbage > -1) {
                data.delete(firstGarbage, MAX_NUM_CHARS);
            }
            return data.toString();
         }

        //Once the socket has been read, display what it sent.
        protected void done() {
            if (DEBUG) {
                System.out.println("SwingWorker is done.");
            }
            progressBar.setIndeterminate(false);

            try {
                String s = get();
                if (DEBUG) {
                    System.out.println("get() returned \"" + s + "\"");
                }
                if (s != null) {
                    //Display the text.
                    display.setForeground(Color.gray);
                    display.setText(s);
                } else {
                    display.setForeground(Color.red);
                    display.setText("Read failed (see console for details).");
                }
            } catch (Exception ignore) { }
         }
    }

    //invoked on the event-dispatching thread
    private void doIt(int port) {
        //Listen to the port on a background thread to avoid
        //tying up the GUI.
        quoteGetter = new QuoteGetter(port, address);
        quoteGetter.execute();

        display.setForeground(Color.gray);
        display.setText("Reading port #" + port);
    }

    public void actionPerformed(ActionEvent event) {
        try {
            int port = Integer.parseInt(portField.getText());
            progressBar.setIndeterminate(true);
            doIt(port);
        } catch (NumberFormatException e) {
            //No integer entered.
            display.setForeground(Color.red);
            display.setText("Please enter a number in the text field below.");
        }
    }

    public void destroy() {
        //destroy the DatagramSocket?
        if (socket != null) {
            socket.close();
        }

        //Set up the UI.
        //Execute a job on the event-dispatching thread:
        //creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    getContentPane().removeAll();
                }
            });
        } catch (Exception e) { }
    }
}


