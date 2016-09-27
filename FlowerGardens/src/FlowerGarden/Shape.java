package FlowerGarden;

import javafx.geometry.Point2D;

public interface Shape {
	public boolean ContainsPoint(Point2D point);
	public void setPosition(Point2D point);
	public void moveRelative(double X, double Y);
	public Point2D getPlacement();
}
