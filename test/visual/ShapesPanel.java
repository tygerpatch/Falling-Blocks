package test.visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import src.Shape;
import src.shapes.IShape;
import src.shapes.JShape;
import src.shapes.LShape;
import src.shapes.OShape;
import src.shapes.SShape;
import src.shapes.TShape;
import src.shapes.ZShape;

public class ShapesPanel extends JPanel implements ActionListener 
{
	protected Shape shape;
	protected JLabel label = new JLabel();
	protected Image image;
	protected Graphics graphics;
	
	private JPanel panel = new JPanel(new GridLayout(0, 1));
	private ButtonGroup buttonGroup = new ButtonGroup();
	private HashMap<String, Shape> hashMap = new HashMap<String, Shape>(7);
	
	public ShapesPanel() 
	{
		super(new BorderLayout());
		
		addShape("IShape", new IShape(5, 5)); // (2, 4));		
		addShape("JShape", new JShape(5, 5)); // (0, 4));		
		addShape("LShape", new LShape(5, 5)); // (0, 5));		
		addShape("OShape", new OShape(5, 5)); // (1, 4));
		addShape("SShape", new SShape(5, 5)); // (1, 4));
		addShape("TShape", new TShape(5, 5)); // (1, 4));			
		addShape("ZShape", new ZShape(5, 5)); // (1, 5));
		
		// Note: The center for both JShape and LShape needs to be down to show rotation.

		add(panel, BorderLayout.WEST);
		add(label);
		
		// Let label take up rest of space.
		// Throws exception otherwise about obtaining label's height and width.
	}
	
	private void addShape(String text, Shape shape) 
	{
		JRadioButton radioButton = new JRadioButton(text);
		radioButton.setActionCommand(text);
		radioButton.addActionListener(this);

		buttonGroup.add(radioButton);		
		panel.add(radioButton);
				
		hashMap.put(text, shape);
	}
	
	
	public void paint(Graphics g) 
	{
		super.paint(g);

		if (image == null) 
		{
			image = label.createImage(label.getWidth(), label.getHeight());
			label.setIcon(new ImageIcon(image));
			graphics = image.getGraphics();
		}
	}

	public void actionPerformed(ActionEvent actionEvent) 
	{		
		graphics.clearRect(0, 0, label.getWidth(), label.getHeight());
		
		shape = hashMap.get(actionEvent.getActionCommand());
		shape.setColor(Color.RED);
		shape.paint(graphics);

		label.repaint();
		requestFocusInWindow();
	}

	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("~ ShapesPanel ~");
		frame.setSize(300, 300);
		frame.setContentPane( new ShapesPanel() );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}