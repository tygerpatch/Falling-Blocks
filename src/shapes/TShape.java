package src.shapes;

import src.Shape;

public class TShape extends Shape 
{
	public TShape(int row, int column) 
	{
		super(row, column);
		
		squares[0].setPosition(-1, 0);
		squares[1].setPosition(0, -1);
		squares[2].setPosition(0, 0);
		squares[3].setPosition(0, 1);
	}
}
