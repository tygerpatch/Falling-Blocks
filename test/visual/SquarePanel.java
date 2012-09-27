package test.visual;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import src.Square;

public class SquarePanel extends JPanel
{
  private Square square;
  
  public SquarePanel()
  {
    square = new Square();
    
    square.setPosition(0, 0);
    square.setColor(Color.GREEN);
  }
  
  public void paint(Graphics graphic)
  {
    square.paint(graphic);
  }
  
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("~ SquarePanel ~");    
    frame.setSize(100, 100);
    frame.setContentPane( new SquarePanel() );        
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);    
  }
}
