package applets;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class BallControl extends JPanel {
	// objects of the main Panel
  private Ball ball = new Ball();
  private JButton jbtSuspend = new JButton("Suspend");
  private JButton jbtResume = new JButton("Resume");
  private JScrollBar jsbDelay = new JScrollBar();

  public BallControl() {
	  
    // Group buttons in a panel
    JPanel panel = new JPanel();
    panel.add(jbtSuspend);
    panel.add(jbtResume);

    // Add ball and buttons to the panel
    ball.setBorder(new javax.swing.border.LineBorder(Color.red));
    jsbDelay.setOrientation(JScrollBar.HORIZONTAL);
    // Link the ball speed to the scroll bar value 
    ball.setDelay(jsbDelay.getMaximum());
    setLayout(new BorderLayout());
    // Add components to the main panel
    this.add(jsbDelay, BorderLayout.NORTH);
    this.add(ball, BorderLayout.CENTER);
    this.add(panel, BorderLayout.SOUTH);

    // Register listeners
    jbtSuspend.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ball.suspend();
      }
    });
    jbtResume.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ball.resume();
      }
    });
    jsbDelay.addAdjustmentListener(new AdjustmentListener() {
      public void adjustmentValueChanged(AdjustmentEvent e) {
        ball.setDelay(jsbDelay.getMaximum() - e.getValue());
      }
    });
  }
}
