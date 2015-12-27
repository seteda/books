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
import java.awt.Dimension;
import java.awt.event.*;

public class TextEditor extends JPanel implements ActionListener {
    private JTextArea textArea = null;
    private JButton openButton = null;
    private JButton saveButton = null;
    private JButton saveAsButton = null;

    public TextEditor() {
         createTextArea();
         createButtons();
    }

    private void createTextArea() {
        textArea = new JTextArea("some text");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(450, 250));
        add(areaScrollPane);
    }

    private void createButtons() {
        openButton = new JButton("Open");
        openButton.addActionListener(this);
        add(openButton);

        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        add(saveButton);

        saveAsButton = new JButton("SaveAs");
        saveAsButton.addActionListener(this);
        add(saveAsButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            textArea.setText(FileHandler.open());
        } else if (e.getSource() == saveButton) {
            FileHandler.save(textArea.getText());
        } else if (e.getSource() == saveAsButton) {
            FileHandler.saveAs(textArea.getText());
        }

    }

}
