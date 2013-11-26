package shapes;

import other.Shape;

public class IShape extends Shape {
   public IShape(int row, int column) {
      super(row, column);

      squares[0].setPosition(-2, 0);
      squares[1].setPosition(-1, 0);
      squares[2].setPosition(0, 0);
      squares[3].setPosition(1, 0);
   }
}
