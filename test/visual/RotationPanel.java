package visual;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class RotationPanel extends ShapesPanel implements KeyListener {
   public RotationPanel() {
      super();

      setFocusable(true);
      addKeyListener(this);
   }

   public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_UP) {
         graphics.clearRect(0, 0, label.getWidth(), label.getHeight());

         shape.rotate();
         shape.paint(graphics);

         label.repaint();
      }
   }

   public void keyReleased(KeyEvent e) {
   }

   public void keyTyped(KeyEvent e) {
   }

   public static void main(String[] args) {
      JFrame frame = new JFrame();

      frame.setTitle("~ RotationPanel ~");
      frame.setSize(300, 300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new RotationPanel());
      frame.setVisible(true);
   }
}