package GUI;

import javax.swing.*;

public class ProgPureFrame 
{
  public static void main(String[] args) 
  {
    JFrame frame = new JFrame("Test Frame");
    
    frame.setSize(400,300);
    
    frame.setLocationRelativeTo(null);
    
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    frame.setVisible(true);
    
    frame.add( new JButton("OK") );

    
  }
}

