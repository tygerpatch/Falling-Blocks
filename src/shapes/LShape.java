package shapes;

import other.Shape;

public class LShape extends Shape {
   public LShape(int row, int column) {
      super(row, column);

      squares[0].setPosition(0, -1);
      squares[1].setPosition(0, 0);
      squares[2].setPosition(1, 0);
      squares[3].setPosition(2, 0);
   }
}
