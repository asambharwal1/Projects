package FlowerGarden;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Flowers implements Shape{
	Circle circle = new Circle();
	int radius;
	Point2D center;
	Point2D origin;
	public Flowers(Point2D point, int radius){
		origin = point;
		center = point;
		this.radius = radius;
		circle.setStroke(Color.BLACK);
		circle.setStrokeWidth(1);
		setPosition(point);
	}
	
	@Override
	public boolean ContainsPoint(Point2D point) {	//See if the clicked point is within the circle
		// TODO Auto-generated method stub
		return circle.contains(point);
	}
	
	public void setFill(Color color){ 				 //Fill circles color
		circle.setFill(color);
	}

	@Override
	public void setPosition(Point2D point) {		 //Set position of the circle at the Point2D point
		// TODO Auto-generated method stub
		circle.setCenterX(point.getX());
		circle.setCenterY(point.getY());
		circle.setRadius(radius);
	}
	
	public Circle getCircle(){
		return circle;
	}

	@Override
	public void moveRelative(double X, double Y) {	 //Move the leaf node according to the drag
		// TODO Auto-generated method stub
		circle.setCenterX(circle.getCenterX()+X);
		circle.setCenterY(circle.getCenterY()+Y);
	}

	@Override
	public Point2D getPlacement() {					 //Get origin in case the shape isn't placed within the drawing area
		// TODO Auto-generated method stub
		return origin;
	}


}
