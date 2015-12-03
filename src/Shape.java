import java.awt.BasicStroke;
import java.awt.Color;

public class Shape {

	private int x1;
	private int x2;
	private int y1;
	private int y2;
	
	private Color color;
	private BasicStroke stroke;
	
	private int shape;
	
	private final int LINE = 1;
	private final int RECTANGLE = 2;
	private final int CIRCLE = 3;
	
	public Shape(int x1, int y1,int x2, int y2, Color color,BasicStroke stroke, int shape){
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.color = color;
		this.stroke = stroke;
		this.shape = shape;
	}
	public int getShape(){
		return this.shape;
	}
	public int getx1(){
		return this.x1;
	}
	public int getx2(){
		return this.x2;
	}
	public int gety1(){
		return this.y1;
	}
	public int gety2(){
		return this.y2;
	}
}
