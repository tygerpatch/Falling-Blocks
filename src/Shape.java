package src;

import java.awt.Color;
import java.awt.Graphics;

public class Shape 
{
	// Each shape is made up of four squares.
	// Use protected access modifer for squares to allow subclasses to define custom relative positioning for squares.
	protected Square squares[] = { new Square(), new Square(), new Square(), new Square() };
	private Position center = new Position();	
	private Color color; // = Color.BLACK;
	
	private int top = 0;
	private int left = 0;

	// row, column arguments represent center of shape,
	// that way Shape can be placed anywhere on board	
	public Shape(int row, int column) 
	{
		center.setRow(row);
		center.setColumn(column);
	}	

	// copy constructor
	public Shape(Shape shape)
	{
		this.center.setRow(shape.center.getRow());
		this.center.setColumn(shape.center.getColumn());	
		
		for(int i = 0; i < shape.squares.length; i++)
		{		
			this.squares[i].setPosition(shape.squares[i].getPosition());
		}
		
		this.color = shape.color;		
		this.left = shape.left;
		this.top = shape.top;			
	}
	
	public void setColor(Color color) 
	{		
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}

	public void paint(Graphics graphics) 
	{
		Position position;
		int x, y;

		for (Square square : squares) 
		{
			position = square.getPosition();
			
			x = (position.getColumn() + center.getColumn()) * Square.SIZE + left;
			y = (position.getRow() + center.getRow()) * Square.SIZE + top;

			// color the inside of the square
			graphics.setColor(color);
			graphics.fillRect(x, y, Square.SIZE, Square.SIZE);

			// draw the outline of the square
			graphics.setColor(Color.BLACK);
			graphics.drawRect(x, y, Square.SIZE, Square.SIZE);
		}
	}
	
	// Shapes are rotated around a center.
	public void rotate() 
	{
		Position position;

		for (Square square : squares) 
		{
			position = square.getPosition();
			square.setPosition(position.getColumn(), -position.getRow());
		}
	}
	
	// offset for when drawing Shape
	public void addOffSet(int top, int left)
	{
		this.top = top;
		this.left = left;
	}
	
	// move shape to the left
	public void left() 
	{
		center.setColumn(center.getColumn() - 1);
	}

	// move shape to the right
	public void right() 
	{
		center.setColumn(center.getColumn() + 1);
	}

	// move shape down
	public void down() 
	{
		center.setRow(center.getRow() + 1);
	}

	// get the squares that make this shape
	public Square[] getSquares()
	{
		Square squares[] = {new Square(), new Square(), new Square(), new Square()};
		Position position;

		for(int i = 0; i < squares.length; i++)
		{
			position = this.squares[i].getPosition();
			squares[i].setPosition(position.getRow() + center.getRow(), position.getColumn() + center.getColumn());
		}
		
		return squares;
	}

    public boolean atBottom(Board board)
    {
    	Position position;
    	int row, column;
    	
    	for(Square square : squares)
    	{
    		position = square.getPosition();    		
    		row = position.getRow() + center.getRow() + 1;
    		
    		if(row >= Board.NUMBER_ROWS)
    		{
    			return true;
    		}
    		
    		column = position.getColumn() + center.getColumn();
    		
    		if(board.isOccupied(row, column))
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }	
}
