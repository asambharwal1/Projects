package FlowerGarden;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bird implements Shape{
	Point2D origin;
	Point2D center;
	ImageView birdImg;
	int scale;

	public Bird(Point2D point, int scale){
		Image bird = new Image("File:src\\blue-bird-md.png", scale, scale, true, true);
		birdImg = new ImageView(bird);
		birdImg.setX(point.getX()*scale);
		birdImg.setY(point.getY()*scale);
		this.scale = scale;
		origin = point;
	}
	
	
	@Override
	public boolean ContainsPoint(Point2D point) { 	//See if the clicked point is within the circle
		// TODO Auto-generated method stub
		return birdImg.contains(point);
	}
	
	public void setScale(int scale){				//Set the scale of the bird image
		birdImg.setScaleX(scale);
		birdImg.setScaleY(scale);
	}

	@Override
	public void setPosition(Point2D point) {		//Set the position of the bird image
		// TODO Auto-generated method stub
		birdImg.setX(point.getX()*scale);
		birdImg.setY(point.getY()*scale);
	}
	
	public ImageView getBirdImage(){				//Return the image view so that we can make it observable
		return birdImg;
	}

	@Override
	public void moveRelative(double X, double Y) {	//Move the image according to the drag
		// TODO Auto-generated method stub
		birdImg.setX(birdImg.getX()+X);
		birdImg.setY(birdImg.getY()+Y);
	}

	@Override
	public Point2D getPlacement() {					//Get the original place in order to refrain from staying outside the drawing place
		// TODO Auto-generated method stub
		return origin;
	}
}
