package FlowerGarden;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FlowerBed implements Shape{
	List<Shape> inShape = new ArrayList<Shape>(); ///Since it is a composite node it contains a list of the leaf nodes
	int height;
	int width;
	Point2D topLeft = null;
	Rectangle rect = new Rectangle();
	private Color rectangleColor;
	Point2D origin;
	
	public FlowerBed(Point2D point, int height, int width){
		origin = point;
		this.height = height;
		this.width = width;
		topLeft = point;
		rect.setHeight(height);
		rect.setWidth(width);
		rect.setX(topLeft.getX());
		rect.setY(topLeft.getY());
		rect.setFill(Color.OLIVE);
		rect.setStrokeWidth(5);
		setLineColor(Color.BLACK);
		setPosition(topLeft);
	}
	
	public void setLineColor(Color color) {
		rectangleColor = color;	
		rect.setStroke(rectangleColor);
	}
	
	@Override
	public boolean ContainsPoint(Point2D point) { //For checking if the mouse is clicked within the point
		// TODO Auto-generated method stub
		boolean inRectangle = (point.getX() >= rect.getX() && point.getX() <= rect.getX()+rect.getWidth()
		&& point.getY()>= rect.getY() && point.getY() <= rect.getY()+rect.getHeight());
		return inRectangle;

	}

	@Override
	public void setPosition(Point2D point) { //Setting position of the rectangle to a certain point
		// TODO Auto-generated method stub
		topLeft = point;
		rect.setX(point.getX());
		rect.setY(point.getY());
	}
	
	public void addChild(Shape e){			//Adding a child to the FlowerBed
		inShape.add(e);
	}
	
	public void removeChild(Shape shape){	//Removing a child from the FlowerBed
		if(inShape.contains(shape)){
			inShape.remove(shape);
		}
	}
	
	public void resetChildren(){			//Resetting the children in the FlowerBed
		inShape = new ArrayList<Shape>();
	}

	@Override
	public void moveRelative(double X, double Y) { //Since it is a composite Node, it and its leaf nodes (if any) have to move
		// TODO Auto-generated method stub
		rect.setX(rect.getX()+X);
		rect.setY(rect.getY()+Y);
		for(Shape shape: inShape){
			shape.moveRelative(X, Y);
		}
	}

	public Rectangle getRectangle() {			//getting the rectangle itself
		// TODO Auto-generated method stub
		return rect;
	}

	@Override
	public Point2D getPlacement() {				//Get the original location for resetting the flowerbed in case it isn't placed in the right area
		// TODO Auto-generated method stub
		return origin;
	}

}
