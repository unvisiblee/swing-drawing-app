import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PaintPanel extends JPanel implements MouseListener,MouseMotionListener
	{
		private final int PENCIL_TOOL = 0;
		private final int LINE_TOOL = 1;
		private final int RECTANGLE_TOOL = 2;
		private final int CIRCLE_TOOL = 3;
		private final int SELECT_TOOL = 4;
		private final int TEXT_TOOL = 5;
		private final int ERASER_TOOL = 6;
		
		private final int LINE = 1;
		private final int RECTANGLE = 2;
		private final int CIRCLE = 3;
		
		private BasicStroke stroke = new BasicStroke((float) 0.1);
		BufferedImage canvas;
		Graphics2D graphics2D;
		private int activeTool = 0;
		//private JLabel label;
	//	private boolean eraser;
		private DrawFrame frame;
		
		private Stack<Shape> shapes;
		
		
		int x1, y1, x2, y2;
		

		private Color currentColor;

		public PaintPanel()
		{
			// construct components
			// contentPane > outerPanel > inkPanel > (Components: clearButton, stroke)
			// configure components
			// add listeners
			// set components into the contentPane
			this.setBackground(Color.white);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			this.setPreferredSize(new Dimension(250, 250));
			//this.eraser = false;
			this.shapes = new Stack<Shape>();
		}

		//Now for the constructors
		public PaintPanel(int f, DrawFrame frame){
			//this.setPreferredSize(new Dimension(300,300));
			this.setSize(700, 500); //important for displaying on the draw frame.
			this.setLayout(null);
			setDoubleBuffered(true);
			setLocation(10, 10);
			setBackground(Color.WHITE);
			currentColor = Color.BLACK;
			setFocusable(true);
			requestFocus();
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			this.frame = frame;
			this.printPaintPanelSize(700, 500);
			this.shapes = new Stack<Shape>();
			//if the mouse is pressed it sets the oldX & oldY
			//coordinates as the mouses x & y coordinates

			//while the mouse is dragged it sets currentX & currentY as the mouses x and y
			//then it draws a line at the coordinates
			//it repaints it and sets oldX and oldY as currentX and currentY
		}
		

		public void paintComponent(Graphics g){
			if(canvas == null){
				canvas = new BufferedImage(800, 600,BufferedImage.TYPE_INT_ARGB);
				graphics2D = canvas.createGraphics();
				graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				clear();
			}
			g.drawImage(canvas, 0, 0, null);
			for(Shape s : shapes){
				
				if (s.getShape() == LINE){
					
					g.setColor(s.getColor());
					
					g.drawLine(s.getx1(), s.gety1(), s.getx2(), s.gety2());
				}
				else if (s.getShape() == RECTANGLE){
					
				
					g.setColor(s.getColor());
					
					g.drawRect(s.getx1(), s.gety1(), s.getx2(), s.gety2());
				}
				else if (s.getShape() == CIRCLE){
					
					
					g.setColor(s.getColor());
					
					g.drawOval(s.getx1(), s.gety1(), s.getx2(), s.gety2());
				}
			}
		}
		
		public void setTool(int tool) {
			this.activeTool = tool;
		}
		
		public void setImage(BufferedImage image) {
			graphics2D.dispose();
			canvas = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			graphics2D = canvas.createGraphics();
			graphics2D.drawImage(image, 0, 0, null);
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		public void clear(){
			graphics2D.setPaint(Color.white);
			graphics2D.fillRect(0, 0, getSize().width, getSize().height);
			repaint();
			graphics2D.setColor(currentColor);
		}
	
		public void undo(){
			shapes.pop();
			repaint();
		}
		
		public void setColor(Color c){
			currentColor = c;
			graphics2D.setColor(c);
		}
		public void setThickness(float f){
			stroke = new BasicStroke(f);
			graphics2D.setStroke(stroke);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			printCoords(e);
			x2 = e.getX();
			y2 = e.getY();
			if (activeTool == ERASER_TOOL){
				graphics2D.setColor(Color.white);
				graphics2D.drawLine(x1, y1, x2, y2);
				repaint();
				x1 = x2;
				y1 = y2;
			}
			if (activeTool == PENCIL_TOOL) {
				graphics2D.setColor(currentColor);
				graphics2D.drawLine(x1, y1, x2, y2);
				repaint();
				x1 = x2;
				y1 = y2;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			printCoords(e);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			printCoords(e);
			x1 = e.getX();
			y1 = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
			
			
				if (activeTool == LINE_TOOL) {
					shapes.push(new Shape(x1, y1, x2, y2,currentColor,stroke,1));
					repaint();
					//graphics2D.drawLine(x1, y1, x2, y2);
					//System.out.println("0");
				}
				else if (activeTool == RECTANGLE_TOOL) {
				
					if (x1 < x2 && y1 < y2) {
						shapes.push(new Shape(x1, y1, x2 - x1, y2 - y1,currentColor,stroke,2));
						//graphics2D.draw(new Rectangle2D.Double(x1, y1, x2 - x1, y2 - y1));
					}
					else if (x2 < x1 && y1 < y2) {
						shapes.push(new Shape(x2, y1, x1 - x2, y2 - y1,currentColor,stroke,2));
						//graphics2D.draw(new Rectangle2D.Double(x2, y1, x1 - x2, y2 - y1));
					}
					else if (x1 < x2 && y2 < y1) {
						shapes.push(new Shape(x1, y2, x2 - x1, y1 - y2,currentColor,stroke,2));
						//graphics2D.draw(new Rectangle2D.Double(x1, y2, x2 - x1, y1 - y2));
					}
					else if (x2 < x1 && y2 < y1) {
						shapes.push(new Shape(x2, y2, x1 - x2, y1 - y2,currentColor,stroke,2));
						//graphics2D.draw(new Rectangle2D.Double(x2, y2, x1 - x2, y1 - y2));
					}
					
				}
				else if (activeTool == CIRCLE_TOOL) {
					if (x1 < x2 && y1 < y2) {
						shapes.push(new Shape(x1, y1, x2 - x1, y2 - y1,currentColor,stroke,3));
						//graphics2D.draw(new Ellipse2D.Double(x1, y1, x2 - x1, y2 - y1));
					}
					else if (x2 < x1 && y1 < y2) {
						shapes.push(new Shape(x2, y1, x1 - x2, y2 - y1,currentColor,stroke,3));
						//graphics2D.draw(new Ellipse2D.Double(x2, y1, x1 - x2, y2 - y1));
					}
					else if (x1 < x2 && y2 < y1) {
						shapes.push(new Shape(x1, y2, x2 - x1, y1 - y2,currentColor,stroke,3));
						//graphics2D.draw(new Ellipse2D.Double(x1, y2, x2 - x1, y1 - y2));
					}
					else if (x2 < x1 && y2 < y1) {
						shapes.push(new Shape(x2, y2, x1 - x2, y1 - y2,currentColor,stroke,3));
						//graphics2D.draw(new Ellipse2D.Double(x2, y2, x1 - x2, y1 - y2));
					}
				}
				else if (activeTool == SELECT_TOOL){
					
				}
				else if (activeTool == TEXT_TOOL){
					
				}
			
			repaint();
		}
		
	    public void printCoords(MouseEvent e) 
	    {
	    	String posX = String.valueOf((int) e.getPoint().getX());
	    	String posY = String.valueOf((int) e.getPoint().getY());
	    	frame.getCoordinateBar().getCoordinates().setText(posX + ",  " + posY + " px");
	    }

	        // print drawer panel size at status tool bar
	    public void printPaintPanelSize(int width, int height) 
	    {
	    	frame.getCoordinateBar().getFrameSize().setText(width + ",  " + height + " px");
	    }
		
	}