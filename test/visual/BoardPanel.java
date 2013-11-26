package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import other.Board;
import other.Shape;
import other.Square;
import shapes.TShape;

// Test moving a TShape around in the board.
public class BoardPanel extends JPanel implements ActionListener, KeyListener {
   private Board board = new Board();
   private JLabel label = new JLabel();
   private Shape shape;
   private Image image;
   private Graphics graphics;

   public BoardPanel() {
      super(new BorderLayout());

      createShape();

      JButton button = new JButton("CLEAR");
      button.addActionListener(this);

      int width = (Square.SIZE * 2) + (Square.SIZE * Board.NUMBER_COLUMNS);
      int height = (Square.SIZE * 2) + (Square.SIZE * Board.NUMBER_ROWS);

      setPreferredSize(new Dimension(width, height + button.getPreferredSize().height));
      setFocusable(true);
      addKeyListener(this);

      add(label);
      add(button, BorderLayout.SOUTH);
   }

   private void createShape() {
      shape = new TShape(1, 4);

      shape.setColor(Color.RED);
      shape.addOffSet(Square.SIZE, Square.SIZE);
   }

   public void keyPressed(KeyEvent e) {
      Shape temp = new Shape(shape);

//      JOptionPane.showMessageDialog(null, "id: " + e.getID());
//      JOptionPane.showMessageDialog(null, "when: " + e.getWhen());
//      JOptionPane.showMessageDialog(null, "modifiers: " + e.getModifiers());
//      JOptionPane.showMessageDialog(null, "keyCode: " + e.getKeyCode());

      // public KeyEvent(Component source, int id, long when, int modifiers, int keyCode)

      switch (e.getKeyCode()) {
         case KeyEvent.VK_UP:
            temp.rotate();
            break;
         case KeyEvent.VK_RIGHT:
            temp.right();
            break;
         case KeyEvent.VK_DOWN:
            temp.down();
            break;
         case KeyEvent.VK_LEFT:
            temp.left();
            break;
      }

      if (board.canPlace(temp)) {
         // erase old shape
         shape.setColor(Color.BLACK);
         shape.paint(graphics);

         // move shape to new position
         shape = new Shape(temp);

         // draw shape at new position
         shape.paint(graphics);
         label.repaint();
      }

      if (shape.atBottom(board)) {
         // place shape on board
         board.place(shape);

         // create new shape
         createShape();
         shape.paint(graphics);

         label.repaint();
      }
   }

   public void keyReleased(KeyEvent e) {
   }

   public void keyTyped(KeyEvent e) {
   }

   public void actionPerformed(ActionEvent actionEvent) {
      board.clear();

      // graphics.clearRect(0, 0, label.getWidth(), label.getHeight());
      graphics.setColor(Color.BLACK);
      graphics.fillRect(Square.SIZE, Square.SIZE,
            (Square.SIZE * Board.NUMBER_COLUMNS),
            (Square.SIZE * Board.NUMBER_ROWS));

      label.repaint();

      requestFocusInWindow();
   }

   public void paint(Graphics g) {
      super.paint(g);

      if (image == null) {
         image = label.createImage(label.getWidth(), label.getHeight());

         graphics = image.getGraphics();
         graphics.setColor(Color.BLACK);
         graphics.fillRect(Square.SIZE, Square.SIZE,
               (Square.SIZE * Board.NUMBER_COLUMNS),
               (Square.SIZE * Board.NUMBER_ROWS));

         shape.paint(graphics);

         label.setIcon(new ImageIcon(image));
      }
   }

   public static void main(String[] args) {
      JFrame frame = new JFrame();
      frame.setTitle("~ RotationPanel ~");
      frame.setContentPane(new BoardPanel());
      frame.pack();
      frame.setResizable(false);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}
