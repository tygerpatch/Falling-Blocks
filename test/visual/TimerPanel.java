package test.visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import src.Board;
import src.EnumClockAction;
import src.Shape;
import src.Square;
import src.shapes.OShape;

// it'd be like board panel, except have shapes drop on timer
// check if any rows are filled, and clear them
public class TimerPanel extends JPanel implements ActionListener
{		
	protected Image image;
	protected Graphics graphics;
	protected JLabel label = new JLabel();
	
	private Board board = new Board();
	private Shape shape;

	private Timer clock = new Timer(200, this);
	private EnumClockAction clockAction = EnumClockAction.DropShape;
	
	public TimerPanel()
	{
		super();
		
		int width = (Square.SIZE * 2) + (Square.SIZE * Board.NUMBER_COLUMNS);
		int height = (Square.SIZE * 2) + (Square.SIZE * Board.NUMBER_ROWS);
		
		setPreferredSize(new Dimension(width, height));
										
		board.addOffSet(Square.SIZE, Square.SIZE);		
		
		add(label);
	}
	
	private void newShape()
	{
		shape = new OShape(Board.NUMBER_ROWS - 5, 4);
		
		shape.addOffSet(Square.SIZE, Square.SIZE);
		shape.setColor(Color.RED);
	}
	
	private void setupBoard()
	{
		Shape shape = new OShape(Board.NUMBER_ROWS - 1, 0);			
		shape.setColor(Color.GREEN);
		
		board.place(shape);
		
		shape = new OShape(Board.NUMBER_ROWS - 1, 2);						
		shape.setColor(Color.BLUE);
		
		board.place(shape);

		shape = new OShape(Board.NUMBER_ROWS - 1, 6);						
		shape.setColor(Color.MAGENTA);
		
		board.place(shape);

		shape = new OShape(Board.NUMBER_ROWS - 1, 8);						
		shape.setColor(Color.ORANGE);
		
		board.place(shape);		
	}
	
	public void paint(Graphics g) 
	{
		super.paint(g);

		if (image == null) 
		{
			image = label.createImage(getWidth(), getHeight());
			label.setIcon(new ImageIcon(image));
			graphics = image.getGraphics();
			
			newShape();
			setupBoard();
			
			board.paint(graphics);			
			shape.paint(graphics);
			
			label.repaint();
			
			clock.start();
		}
	}

	private void doDropShape()
	{
		Shape temp = new Shape(shape);
		temp.down();
				
		if(board.canPlace(temp))
		{
			// erase old shape
			shape.setColor(Color.BLACK);
			shape.paint(graphics);
		
			// move shape to new position
			shape = new Shape(temp);
			
			// draw shape at new position
			shape.paint(graphics);			
			label.repaint();
		}
		
		if(shape.atBottom(board))
		{
			int row;
			
			//  place shape on board
			board.place(shape);
						
			// for each square in shape
			for(Square square : shape.getSquares())
			{
				row = square.getPosition().getRow();
				
				if(board.rowCompleted(row))
				{
					board.highlightRow(row, graphics);
					clockAction = EnumClockAction.DropHighlightedRows;
				}
				
			    // TODO: create custom arraylist that holds ints (move away from boxing)
				// This will be used to prevent checking the same row twice.
			}
		}
	}
	
	private void doDropHighlightedRows()
	{
		board.dropHighlightedRows();
		board.paint(graphics);
		
		clockAction = EnumClockAction.BoardSetupForTimerPanel;
	}
	
	private void doBoardSetupForTimerPanel()
	{
		setupBoard();
		board.paint(graphics);
		
		newShape();
		shape.paint(graphics);
		
		clockAction = EnumClockAction.DropShape;
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		switch(clockAction)
		{
			case DropShape:
				doDropShape();
				break;
			case DropHighlightedRows:
				doDropHighlightedRows();
				break;
			case BoardSetupForTimerPanel:
				doBoardSetupForTimerPanel();
				break;				
		}
		
		label.repaint();
	}
			
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("~ ShapesPanel ~");
		frame.setContentPane(new TimerPanel());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}	
}
