package FlowerGarden;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Tree implements Shape{
	Point2D origin;
	Point2D center;
	int radius;
	Circle circle = new Circle();
	List<Shape>innerShapes = new ArrayList<Shape>(); ///Since it is a composite node it contains a list of the leaf nodes

	public Tree(Point2D point, int radius){
		origin = point;
		center = point;
		this.radius = radius;
		circle.setStroke(Color.BLACK);
		circle.setStrokeWidth(1);
		circle.setFill(Color.GREEN);
		setPosition(point);
	}
	
	
	@Override
	public boolean ContainsPoint(Point2D point) { //For checking if the mouse is clicked within the point
		// TODO Auto-generated method stub
		return circle.contains(point);
	}

	@Override
	public void setPosition(Point2D point) { //Allowing to set at a point
		// TODO Auto-generated method stub
		circle.setCenterX(point.getX());
		circle.setCenterY(point.getY());
		circle.setRadius(radius);
	}
	
	public Circle getCircle(){
		return circle;
	}

	@Override
	public void moveRelative(double X, double Y) { //Since it's a composite node, we need to move it and its children (if any)
		// TODO Auto-generated method stub
		circle.setCenterX(circle.getCenterX()+X);
		circle.setCenterY(circle.getCenterY()+Y);
		for(Shape child: innerShapes){
			child.moveRelative(X,Y);
		}
	}

	@Override
	public Point2D getPlacement() { //Finding the original placement, for purposes of resetting the shape to it's original place.
		// TODO Auto-generated method stub
		return origin;
	}


	public void addChild(Shape currentShape) { //Adding a child
		// TODO Auto-generated method stub
		innerShapes.add(currentShape);
	}


	public void removeChild(Shape shape){ //Removal of a specific child
		if(innerShapes.contains(shape)){
			innerShapes.remove(shape);
		}
	}
	
	public void resetChildren(){ //Removal of all Children
		innerShapes = new ArrayList<Shape>();
	}

}
