package src;

import java.awt.Color;
import java.awt.Graphics;

public class Board 
{
    // dimensions of board
    public static final int NUMBER_COLUMNS = 10;
    public static final int NUMBER_ROWS = 15;

    private Color board[][] = new Color[NUMBER_ROWS][NUMBER_COLUMNS];
    private boolean isEmpty;
	
    public Board()
    {    	
    	clear();
    }
    
    public void clear()
    {
        for (int row = 0; row < NUMBER_ROWS; row++)
        {
            for (int column = 0; column < NUMBER_COLUMNS; column++)
            {
                board[row][column] = Color.BLACK;
            }
        }
        
        isEmpty = true;
        
        // "The for-each loop is essentially read-only"
        // http://www.java2s.com/Tutorial/Java/0080__Statement-Control/Theforeachloopisessentiallyreadonly.htm
    }
    
    // check if shape can be placed on the board
    public boolean canPlace(Shape shape)
    {
    	Square squares[] = shape.getSquares();
    	
    	for(Square square:squares)
    	{    		
    		if(!onBoard(square) || isOccupied(square))
    		{
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    // check that Square is on the board
    public boolean onBoard(Square square)
    {
    	Position position = square.getPosition();
    	int row = position.getRow();
    	int column = position.getColumn();
    	
		if((row < 0) || (row >= NUMBER_ROWS))
		{
			return false;
		}
		
		if((column < 0) || (column >= NUMBER_COLUMNS))
		{
			return false;
		}
		
		return true;    	
    }

    // check if position on board is already occupied by another square
    public boolean isOccupied(Square square)
    {
    	Position position = square.getPosition();
    	return isOccupied(position.getRow(), position.getColumn());
    }

    public boolean isOccupied(int row, int column)
    {
    	return (Color.BLACK != board[row][column]);
    }
    
    // place shape on the board
    public void place(Shape shape)
    {
    	Square squares[] = shape.getSquares();
    	Position position;
    	
    	for(Square square : squares)
    	{
    		position = square.getPosition();
    		board[position.getRow()][position.getColumn()] = shape.getColor();
    	}
    	
    	isEmpty = false;
    }
        
    private int top = 0;
    private int left = 0;
    
	// offset for when drawing Shape
	public void addOffSet(int top, int left)
	{
		this.top = top;
		this.left = left;
	}
    
    public void highlightRow(int row, Graphics graphics)
    {
    	int x, y;
    	
		for(int column = 0; column < NUMBER_COLUMNS; column++)
		{
			board[row][column] = Color.WHITE;

			x = column * Square.SIZE + left;
			y = row * Square.SIZE + top;
			
			// color the inside of the square
			graphics.setColor(Color.WHITE);
			graphics.fillRect(x, y, Square.SIZE, Square.SIZE);

			// draw the outline of the square
			graphics.setColor(Color.BLACK);
			graphics.drawRect(x, y, Square.SIZE, Square.SIZE);
		}    	
    }
    
    public boolean rowCompleted(int row)
    {
    	boolean result = true;
    	
		for(int column = 0; column < NUMBER_COLUMNS; column++)
		{
			if(!isOccupied(row, column))
			{
				result = false;
				column = NUMBER_COLUMNS;
			}			
		}
		
		return result;
    }
    
    public void dropHighlightedRows()
    {
    	int fromRow, toRow, column;
    	Color newBoard[][] = new Color[NUMBER_ROWS][NUMBER_COLUMNS];
    	
    	// starting at the bottom of the board
    	fromRow = toRow = NUMBER_ROWS - 1;
    	    	
    	while(fromRow >= 0)
    	{
    		// if row is not highlighted
    		if(Color.WHITE != board[fromRow][0])
    		{
    			// copy cells to new board
    			for(column = 0; column < NUMBER_COLUMNS; column++)
    			{
    				newBoard[toRow][column] = board[fromRow][column];
    			}
    			
    			// increment 'pointer' to next row to be filled
    			toRow = toRow - 1;
    		}
    		
    		// increment 'pointer' to next row to be checked
			fromRow = fromRow - 1;
    	}
    	
    	// make the rest of the rows (until top  is reached) empty
    	while(toRow >= 0)
    	{
			for(column = 0; column < NUMBER_COLUMNS; column++)
			{
				newBoard[toRow][column] = Color.BLACK;
			}
			
			toRow = toRow - 1;
    	}
    	
    	board = newBoard;
    }
    
    public static final int WIDTH = Square.SIZE * NUMBER_COLUMNS;
    public static final int HEIGHT = Square.SIZE * NUMBER_ROWS;
    
    public void paint(Graphics graphics)
    {
    	if(isEmpty)
    	{
    		graphics.setColor(Color.BLACK);
    		graphics.fillRect(left, top, WIDTH + 1, HEIGHT + 1);
			// Add extra pixel to width and height so it doesn't look like shape moves sightly out of bounds
    	}
    	else
    	{
	    	int x, y;
	    	
	    	for(int row = 0; row < NUMBER_ROWS; row++)
	    	{
	    		for(int column = 0; column < NUMBER_COLUMNS; column++)
	    		{    			    			
	    			x = (column * Square.SIZE) + left;
	    			y = (row * Square.SIZE) + top;
	
	    			// color the inside of the square
	    			graphics.setColor(board[row][column]);
	    			graphics.fillRect(x, y, Square.SIZE, Square.SIZE);
	
	    			// draw the outline of the square
	    			graphics.setColor(Color.BLACK);
	    			graphics.drawRect(x, y, Square.SIZE, Square.SIZE);    			
	    		}
	    	}
    	}
    }
}