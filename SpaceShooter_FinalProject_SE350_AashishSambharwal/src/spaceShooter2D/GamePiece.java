package spaceShooter2D;
import javafx.scene.image.ImageView;

public interface GamePiece {
	public ImageView getImage();
	public double getX();
	public double getY();
	public double getH();
	public double getW();
	public boolean collidesWith(GamePiece g);
}
