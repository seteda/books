/*
* Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
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
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import javax.swing.plaf.LayerUI;

public class TapTapTap {
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new TapTapTap().createUI();
      }
    });
  }

  private JButton mOrderButton;

  public void createUI() {
    JFrame f = new JFrame ("TapTapTap");
    
    final WaitLayerUI layerUI = new WaitLayerUI();
    JPanel panel = createPanel();
    JLayer<JPanel> jlayer = new JLayer<JPanel>(panel, layerUI);
    
    final Timer stopper = new Timer(4000, new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        layerUI.stop();
      }
    });
    stopper.setRepeats(false);

    mOrderButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
          layerUI.start();
          if (!stopper.isRunning()) {
            stopper.start();
          }
        }
      }
    );

    f.add (jlayer);
    
    f.setSize(300, 200);
    f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    f.setLocationRelativeTo (null);
    f.setVisible (true);
  }

  private JPanel createPanel() {
    JPanel p = new JPanel();

    ButtonGroup entreeGroup = new ButtonGroup();
    JRadioButton radioButton;
    p.add(radioButton = new JRadioButton("Beef", true));
    entreeGroup.add(radioButton);
    p.add(radioButton = new JRadioButton("Chicken"));
    entreeGroup.add(radioButton);
    p.add(radioButton = new JRadioButton("Vegetable"));
    entreeGroup.add(radioButton);

    p.add(new JCheckBox("Ketchup"));
    p.add(new JCheckBox("Mustard"));
    p.add(new JCheckBox("Pickles"));

    p.add(new JLabel("Special requests:"));
    p.add(new JTextField(20));

    mOrderButton = new JButton("Place Order");
    p.add(mOrderButton);

    return p;
  }
}

class WaitLayerUI extends LayerUI<JPanel> implements ActionListener {
  private boolean mIsRunning;
  private boolean mIsFadingOut;
  private Timer mTimer;

  private int mAngle;
  private int mFadeCount;
  private int mFadeLimit = 15;

  @Override
  public void paint (Graphics g, JComponent c) {
    int w = c.getWidth();
    int h = c.getHeight();

    // Paint the view.
    super.paint (g, c);

    if (!mIsRunning) {
      return;
    }

    Graphics2D g2 = (Graphics2D)g.create();

    float fade = (float)mFadeCount / (float)mFadeLimit;
    // Gray it out.
    Composite urComposite = g2.getComposite();
    g2.setComposite(AlphaComposite.getInstance(
        AlphaComposite.SRC_OVER, .5f * fade));
    g2.fillRect(0, 0, w, h);
    g2.setComposite(urComposite);

    // Paint the wait indicator.
    int s = Math.min(w, h) / 5;
    int cx = w / 2;
    int cy = h / 2;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setStroke(
        new BasicStroke(s / 4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    g2.setPaint(Color.white);
    g2.rotate(Math.PI * mAngle / 180, cx, cy);
    for (int i = 0; i < 12; i++) {
      float scale = (11.0f - (float)i) / 11.0f;
      g2.drawLine(cx + s, cy, cx + s * 2, cy);
      g2.rotate(-Math.PI / 6, cx, cy);
      g2.setComposite(AlphaComposite.getInstance(
          AlphaComposite.SRC_OVER, scale * fade));
    }

    g2.dispose();
  }

  public void actionPerformed(ActionEvent e) {
    if (mIsRunning) {
      firePropertyChange("tick", 0, 1);
      mAngle += 3;
      if (mAngle >= 360) {
        mAngle = 0;
      }
      if (mIsFadingOut) {
        if (--mFadeCount == 0) {
          mIsRunning = false;
          mTimer.stop();
        }
      }
      else if (mFadeCount < mFadeLimit) {
        mFadeCount++;
      }
    }
  }

  public void start() {
    if (mIsRunning) {
      return;
    }
    
    // Run a thread for animation.
    mIsRunning = true;
    mIsFadingOut = false;
    mFadeCount = 0;
    int fps = 24;
    int tick = 1000 / fps;
    mTimer = new Timer(tick, this);
    mTimer.start();
  }

  public void stop() {
    mIsFadingOut = true;
  }

  @Override
  public void applyPropertyChange(PropertyChangeEvent pce, JLayer l) {
    if ("tick".equals(pce.getPropertyName())) {
      l.repaint();
    }
  }
}
