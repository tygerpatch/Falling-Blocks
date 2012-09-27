package src.shapes;

import src.Shape;

public class JShape extends Shape 
{
	public JShape(int row, int column) 
	{
		super(row, column);

		squares[0].setPosition(0, 0);		
		squares[1].setPosition(0, 1);
		squares[2].setPosition(1, 0);
		squares[3].setPosition(2, 0);
	}
}
