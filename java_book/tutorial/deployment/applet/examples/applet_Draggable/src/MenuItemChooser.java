/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
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
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

// The main class that creates the overall GUI
public class MenuItemChooser extends JPanel implements ActionListener, FocusListener {
    MenuItem [] items = null;
    int currMenuItem = 0;

    JLabel itemName = null;
    JLabel itemType = null;
    JLabel price = null;
    JTextField qty = null;
    JLabel image = null;
    JLabel desc = null;
    JLabel totalLbl = null;

    JButton next = null;
    JButton prev = null;
    JButton order = null;
    JButton cancel = null;
    JButton close = null;

    int total = 0;

    ActionListener closeListener = null;

    public MenuItemChooser() {
        items = MenuItem.initMenu();
        initGUI();
    }

    
    private void initGUI() {
        JPanel itemDetailsPanel = new JPanel();
        itemDetailsPanel.setLayout(new GridLayout(4, 2));
        itemDetailsPanel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel("Item: ");
        itemDetailsPanel.add(lbl);

        itemName = new JLabel("");
        itemDetailsPanel.add(itemName);

        lbl = new JLabel("Type: ");
        itemDetailsPanel.add(lbl);

        itemType = new JLabel("");
        itemDetailsPanel.add(itemType);

        lbl = new JLabel("Price: $ ");
        itemDetailsPanel.add(lbl);

        price = new JLabel("");
        itemDetailsPanel.add(price);

        lbl = new JLabel("Quantity: ");
        itemDetailsPanel.add(lbl);

        qty = new JTextField("0", 1);
        qty.addActionListener(this);
        qty.addFocusListener(this);
        itemDetailsPanel.add(qty);

        JPanel imgDescPanel = new JPanel(new BorderLayout());
        imgDescPanel.setBackground(Color.WHITE);
        image = new JLabel("");
        image.setHorizontalAlignment(SwingConstants.CENTER);
        image.setVerticalAlignment(SwingConstants.CENTER);

        imgDescPanel.add(image, BorderLayout.NORTH);

        desc = new JLabel("desc");
        imgDescPanel.add(desc, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        next = new JButton("Next");
        next.addActionListener(this);
        buttonPanel.add(next);
        
        prev = new JButton("Previous");
        prev.addActionListener(this);
        buttonPanel.add(prev);

        order = new JButton("Order");
        order.addActionListener(this);
        buttonPanel.add(order);

        cancel = new JButton("Cancel / New");
        cancel.addActionListener(this);
        buttonPanel.add(cancel);

        close = new JButton("Close");
        close.addActionListener(this);
        close.setEnabled(false);        
        buttonPanel.add(close);

        totalLbl = new JLabel("");
        totalLbl.setFont(new Font("Serif", Font.BOLD, 14));
        totalLbl.setForeground((Color.GREEN).darker());
        buttonPanel.add(totalLbl);
        
        BorderLayout bl = new BorderLayout();
        bl.setHgap(30);
        bl.setVgap(20);
        setLayout(bl);
        setBackground(Color.WHITE);
        add(itemDetailsPanel, BorderLayout.WEST);
        add(imgDescPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
        TitledBorder title = BorderFactory.createTitledBorder("Choose Menu Items And Place Order");
        setBorder(title);

        loadMenuItem();
    }

    private void loadMenuItem() {
        itemName.setText(items[currMenuItem].name);
        itemType.setText(items[currMenuItem].type);
        price.setText(new Integer(items[currMenuItem].price).toString());
        qty.setText(new Integer(items[currMenuItem].qtyOrdered).toString());
        ClassLoader cl = this.getClass().getClassLoader();
        Icon menuItemIcon  = new ImageIcon(cl.getResource(items[currMenuItem].imagePath));
        image.setIcon(menuItemIcon);
        desc.setText(items[currMenuItem].desc);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == prev) {
            showPrevMenuItem();
        } else if (e.getSource() == next) {
            showNextMenuItem();
        } else if (e.getSource() == order) {
            placeOrder();
        } else if (e.getSource() == cancel) {
            cancelOrder();
        } else if (e.getSource() == close) {
            close();
        } else if (e.getSource() == qty) {
            updateQtyOrdered();
        }
    }

    public void focusLost(FocusEvent e) {
        if (e.getSource() == qty) {
            updateQtyOrdered();
        }
    }

    public void focusGained(FocusEvent e) {

    }

    private void showPrevMenuItem() {
        if (currMenuItem > 0) {
            currMenuItem--;
        }        
        loadMenuItem();
    }

    private void showNextMenuItem() {
        if (currMenuItem < 3) {
            currMenuItem++;
        }
        loadMenuItem();
    }

    private void placeOrder() {
         for (int i = 0; i < 4; i++) {
            total = total + items[i].price * items[i].qtyOrdered;
        }
        totalLbl.setText("Order Placed! Total: $ " + total);
        total = 0;
    }

    private void cancelOrder() {
        for (int i = 0; i < 4; i++) {
            items[i].qtyOrdered = 0;
        }
        total = 0;
        totalLbl.setText("");
        currMenuItem = 0;
        loadMenuItem();
    }

    private void close() {
        // invoke actionPerformed of closeListener received from 
        // the Java Plug-in software.
        if (closeListener != null) {
            closeListener.actionPerformed(null);
        }
    }

    private void updateQtyOrdered() {
        try {
            String val = qty.getText();
            items[currMenuItem].qtyOrdered = (new Integer(val)).intValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // this is a special ActionListener passed in by the
    // Java Plug-in software to control applet's close behavior
    public void setCloseListener(ActionListener cl) {
        closeListener = cl;
        if (cl == null) {
            // applet is in web page
            close.setEnabled(false);
        } else {
            // applet has been dragged out of web page
            close.setEnabled(true);
        }
    }
}
