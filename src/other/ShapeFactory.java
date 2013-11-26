package other;

import java.awt.Color;
import java.util.Random;

import shapes.IShape;
import shapes.JShape;
import shapes.LShape;
import shapes.OShape;
import shapes.SShape;
import shapes.TShape;
import shapes.ZShape;

public class ShapeFactory {
   private static final Random random = new Random();

   // return a random shape with a random color
   public static Shape getRandomShape() {
      Shape shape = new Shape(0, 0);

      // shape.setColor(getRandomColor()); // Doesn't work.

      // random number between 0 (inclusive) and 7 (exclusive)
      switch (random.nextInt(7)) {
         case 0:
            shape = new IShape(2, 4);
            break;
         case 1:
            shape = new JShape(0, 4);
            break;
         case 2:
            shape = new LShape(0, 5);
            break;
         case 3:
            shape = new OShape(1, 4);
            break;
         case 4:
            shape = new SShape(1, 4);
            break;
         case 5:
            shape = new TShape(1, 4);
            break;
         case 6:
            shape = new ZShape(1, 5);
            break;
      }

      return shape;
   }

   public static Color getRandomColor() {
      Color result = Color.BLACK;

      // random number between 0 (inclusive) and 9 (exclusive)
      switch (random.nextInt(9)) {
         case 0:
            result = Color.BLUE;
            break;
         case 1:
            result = Color.CYAN;
            break;
         case 2:
            result = Color.GREEN;
            break;
         case 3:
            result = Color.LIGHT_GRAY;
            break;
         case 4:
            result = Color.MAGENTA;
            break;
         case 5:
            result = Color.ORANGE;
            break;
         case 6:
            result = Color.PINK;
            break;
         case 7:
            result = Color.RED;
            break;
         case 8:
            result = Color.YELLOW;
            break;
      }

      return result;
   }
}
