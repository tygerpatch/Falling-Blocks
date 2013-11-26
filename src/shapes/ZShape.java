package shapes;

import other.Shape;

public class ZShape extends Shape {
   public ZShape(int row, int column) {
      super(row, column);

      squares[0].setPosition(-1, -1);
      squares[1].setPosition(-1, 0);
      squares[2].setPosition(0, 0);
      squares[3].setPosition(0, 1);
   }
}
