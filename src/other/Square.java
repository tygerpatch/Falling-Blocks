package other;

import java.awt.Color;
import java.awt.Graphics;

// Each square has a color, size, and row, column position on board.
public class Square {
   private Color color;
   private Position position = new Position();
   public static final int SIZE = 20;

   // empty constructor
   public Square() {
   }

   public void paint(Graphics graphic) {
      int x = position.getRow() * SIZE;
      int y = position.getColumn() * SIZE;

      // color the inside of the square
      graphic.setColor(color);
      graphic.fillRect(x, y, SIZE, SIZE);

      // draw the outline of the square
      graphic.setColor(Color.BLACK);
      graphic.drawRect(x, y, SIZE, SIZE);
   }

   // set the square's position on the board
   public void setPosition(int row, int column) {
      position.setRow(row);
      position.setColumn(column);
   }

   // set the square's position on the board
   public void setPosition(Position position) {
      setPosition(position.getRow(), position.getColumn());
   }

   public Position getPosition() {
      return position;
   }

   public void setColor(Color color) {
      this.color = color;
   }
}
