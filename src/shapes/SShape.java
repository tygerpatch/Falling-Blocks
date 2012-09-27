package src.shapes;

import src.Shape;

public class SShape extends Shape 
{
	public SShape(int row, int column) 
	{
		super(row, column);
		
		squares[0].setPosition(-1, 0);
		squares[1].setPosition(-1, 1);
		squares[2].setPosition(0, -1);
		squares[3].setPosition(0, 0);
	}
}
