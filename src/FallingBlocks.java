import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import other.Board;
import other.EnumClockAction;
import other.Shape;
import other.ShapeFactory;
import other.Square;

//author: Todd Gerspacher
//use jpanel so it can be placed in either jframe or japplet
public class FallingBlocks extends JPanel implements KeyListener,
      ActionListener {
   private EnumClockAction clockAction = EnumClockAction.DropShape;
   private Timer clock = new Timer(200, this);

   public static final int NEXT_BOARD = Square.SIZE * 6;

   // The following are for double buffered graphics.
   // The idea is to draw everything on an offscreen graphic, sort of like a flip-book.
   // Do this so that every time the paint method is called, it has little to redraw.
   private Image image;
   private Graphics graphics;

   private Shape shape;
   private Shape nextShape;
   private Board board = new Board();
   private int score = 0;

   public FallingBlocks() {
      setPreferredSize(new Dimension(
      // The width of the JPanel needs to be the width of both the board and the window that shows the next shape.
      // Also need to include the spaces before, after, and between these windows.
            Board.WIDTH + NEXT_BOARD + (Square.SIZE * 3),
            // The height of the JPanel needs to be the height of the board.
            // Plus space above and below it.
            Board.HEIGHT + (Square.SIZE * 2)));

      board.addOffSet(Square.SIZE, Square.SIZE);

      shape = ShapeFactory.getRandomShape();
      shape.setColor(ShapeFactory.getRandomColor());
      // TODO: research static variables, why above two steps have to be separate
      shape.addOffSet(Square.SIZE, Square.SIZE);

      nextShape = ShapeFactory.getRandomShape();
      nextShape.setColor(ShapeFactory.getRandomColor());
      nextShape.addOffSet(Square.SIZE * 2, Square.SIZE * 11);

      setFocusable(true);
      addKeyListener(this);
   }

   public void paint(Graphics graphics) {
      if (image == null) {
         Dimension dimension = this.getPreferredSize();

         image = createImage(dimension.width, dimension.height);
         this.graphics = image.getGraphics();

         board.paint(this.graphics);

         // determine font and line spacing dimensions
         FontMetrics fontMetric = this.graphics.getFontMetrics();
         int lineSpace = fontMetric.getAscent() + fontMetric.getDescent();

         int x = (Square.SIZE * 2) + Board.WIDTH;
         int y = scoreY = (Square.SIZE * 2) + NEXT_BOARD;

         scoreX = fontMetric.stringWidth("Score: ") + x;

         this.graphics.drawString("Score: ", x, y);
         y += (lineSpace * 2);

         this.graphics.drawString("~ Instructions ~", x, y);
         y += lineSpace;

         this.graphics.drawString("rotate ~ up arrow", x, y);
         y += lineSpace;

         this.graphics.drawString("move left ~ left arrow", x, y);
         y += lineSpace;

         this.graphics.drawString("move right ~ left arrow", x, y);
         y += lineSpace;

         this.graphics.drawString("move down ~ down arrow or space bar", x, y);
         y += lineSpace;

         this.graphics.drawString("pause/play ~ p key", x, y);
      }

      this.graphics.setColor(Color.BLACK);
      this.graphics.fillRect((Square.SIZE * 2) + Board.WIDTH, Square.SIZE, NEXT_BOARD, NEXT_BOARD);

      shape.paint(this.graphics);
      nextShape.paint(this.graphics);

      // place buffered graphics onto panel
      graphics.drawImage(image, 0, 0, this);

      // draw score outside of everything so don't have to worry about erasing old score
      graphics.drawString("" + score, scoreX, scoreY);
   }

   // position to draw the score
   private int scoreX, scoreY;

   public void keyPressed(KeyEvent e) {
      Shape temp = new Shape(shape);

      // move temporary shape
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
         case KeyEvent.VK_P:
            if (clock.isRunning()) {
               clock.stop();
            }
            else {
               clock.start();
            }
            break;
      }

      // if temporary shape is okay to place on board
      if (board.canPlace(temp) && clock.isRunning()) {
         // erase old shape
         shape.setColor(Color.BLACK);
         shape.paint(graphics);

         // move shape to new position
         shape = new Shape(temp);

         // draw shape at new position
         shape.paint(graphics);
      }

      if (shape.atBottom(board)) {
         int row;

         board.place(shape);

         for (Square square : shape.getSquares()) {
            row = square.getPosition().getRow();

            if (board.rowCompleted(row)) {
               board.highlightRow(row, graphics);
               score = score + 1;
               clockAction = EnumClockAction.DropHighlightedRows;
            }
         }

         // create new shape
         shape = new Shape(nextShape);
         shape.addOffSet(Square.SIZE, Square.SIZE);

         nextShape = ShapeFactory.getRandomShape();
         nextShape.setColor(ShapeFactory.getRandomColor());
         nextShape.addOffSet(Square.SIZE * 2, Square.SIZE * 11);
      }

      repaint();
   }

   public void keyReleased(KeyEvent e) {
   }

   public void keyTyped(KeyEvent e) {
   }

   public void actionPerformed(ActionEvent evt) {
      if (clockAction == EnumClockAction.DropShape) {
         keyPressed(new KeyEvent(this, KeyEvent.KEY_PRESSED, new Date().getTime(), KeyEvent.VK_UNDEFINED, KeyEvent.VK_DOWN));
      }
      else {
         board.dropHighlightedRows();
         board.paint(graphics);
         clockAction = EnumClockAction.DropShape;
      }
   }

   public static void main(String[] args) {
      JFrame frame = new JFrame("~ Falling Blocks ~");
      frame.setContentPane(new FallingBlocks());
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setResizable(false);
      frame.setVisible(true);
   }
}
