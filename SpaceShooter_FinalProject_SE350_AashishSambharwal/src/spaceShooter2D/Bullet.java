package spaceShooter2D;
/**
 * 
 * @author Aashish SSG
 */
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Bullet implements GamePiece{
	ImageView bulletimg;
	int Type;
	double x;
	double y;
	double w;
	double h;
	final double minX = 0;
    final double maxX = 1000; 
    final double minY = 0;
    final double maxY = 800;
	Enemies en;
	MainGame game;
	Rectangle rect;
	CollisionDetect c;
	Player player;
	boolean collision = false;
	ObservableList<Node> children;
	
	/**
	 * Initializer for the bullet's that the player uses.
	 * @param bul ImageView of the Bullet for it to be visible.
	 * @param x	The X-coordinate in order to place the bullet at that particular coordinate.
	 * @param y The Y-coordinate in order to place the bullet at that particular coordinate.
	 * @param en The enemies that are spawned, via which we can obtain the list which will be used for collision detection.
	 * @param children The observable list or the root for which we need to add the Image of the Bullet to.
	 * @param player The player class in order to check for collision detections.
	 */
	public Bullet(ImageView bul, double x, double y, Enemies en, ObservableList<Node> children, Player player){
		Type = 1;
		this.player = player;
		this.bulletimg = bul;
		this.x = x; 
		this.y = y;
		this.en = en;
		this.children = children;
		this.w = bulletimg.getBoundsInParent().getWidth();
		this.h = bulletimg.getBoundsInParent().getWidth();
		c = new CollisionDetect();
		bulletimg.setX(x);
		bulletimg.setY(y);
		bulletimg.setVisible(false);
	}
	
	/**
	 * Initializer for the bullet's that the enemy shall use.
	 * @param x The X-coordinate in order to place the bullet at that particular coordinate.
	 * @param y The Y-coordinate in order to place the bullet at that particular coordinate.
	 * @param en The enemies that are spawned, via which we can obtain the list which will be used for collision detection.
	 * @param children The observable list or the root for which we need to add the Image of the Bullet to.
	 * @param player The player class in order to check for collision detections.
	 */
	public Bullet(double x, double y, Enemies en, ObservableList<Node> children, Player player){
		Type = 2;
		this.player = player;
		Image bulletEnemy = new Image("bullet3.png");
		this.bulletimg = new ImageView(bulletEnemy);
		this.children = children;
		children.add(bulletimg);
		this.x = x; 
		this.y = y;
		this.en = en;
		c = new CollisionDetect();
		this.w = bulletimg.getBoundsInParent().getWidth();
		this.h = bulletimg.getBoundsInParent().getWidth();
		bulletimg.setX(x);
		bulletimg.setY(y);
		bulletimg.setVisible(false);
	}
	
	/**
	 * Returns the current X coordinate of the Bullet.
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * Returns the current Y coordinate of the Bullet.
	 */
	public double getY(){
		return y;
	}
	
	/**
	 * Sets the Image of the Bullet to the corresponding X-coordinate and Y-coordinate values.
	 * @param x Required to set the current Bullet Image X-coordinate to the one given.
	 * @param y Required to set the current Bullet Image Y-coordinate to the one given.
	 */
	public void setImage(double x, double y){
		bulletimg.setX(x);
		bulletimg.setY(y);
	}
	
	/**
	 * Sets the collision to either true or false.
	 * @param ouccured Boolean parameter sets the collision to true or false.
	 */
	public void collisionOccurred(boolean ouccured){
		this.collision = ouccured;
	}
	
	/**
	 * Returns the current class.
	 * @return
	 */
	public Bullet getBullet(){
		return this;
	}
	
	/**
	 * Removes the bullet image from the pane.
	 */
	public void removeFromPane(){
		this.children.remove(this.bulletimg);
	}
	
	/**
	 * Fires the bullet depending upon the direction and position.
	 * @param direction Direction of the bullet, false for heading towards the player and true for heading towards the enemy.
	 * @param X X-coordinate of the ship.
	 * @param Y Y-coordinate of the ship.
	 */
	public void fire(boolean direction, double X, double Y){
		x = X;
		setImage(x,y);
		new AnimationTimer(){

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(x>=maxX || x<=minX || collision) {
					bulletimg.setVisible(false);
					removeFromPane();
					stop();
					}
				
				else if(x<maxX && x>minX && !collision){
					bulletimg.setVisible(true);
					}
				
				c.collide(getBullet(), en, player, children);
				
				if(direction)x+=10;
				else x-=10;
				
				move(getX(), getY());
			}
		}.start();
	}
	
	/**
	 * Fires the bosses bullet which requires both the X and Y coordinate.
	 * @param direction Direction of the bullet, false for heading towards the player and true for heading towards the enemy.
	 * @param X X-coordinate of the ship.
	 * @param Y Y-coordinate of the ship.
	 */
	public void fireBossBullet(boolean direction, double X, double Y){
		x = X;
		y = Y;
		setImage(x,y);
		new AnimationTimer(){

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(x>=maxX || x<=minX || collision) {
					bulletimg.setVisible(false);
					removeFromPane();
					stop();
					}
				
				else if(x<maxX && x>minX && !collision){
					bulletimg.setVisible(true);
					}
				
				c.collide(getBullet(), en, player, children);
				
				if(direction)x+=10;
				else x-=10;
				
				move(getX(), getY());
			}
		}.start();
	}
	
	/**
	 * Checks whether the current piece is within the bound of the other piece.
	 * @param otherPiece Required for checking the if the bounds of this piece collides with the otherPiece
	 */
	public boolean collidesWith(GamePiece otherPiece) {
		return ((otherPiece.getX() + otherPiece.getW() >= x) && (otherPiece.getY() + otherPiece.getH() >= y) && (otherPiece.getX() <= x + w) && (otherPiece.getY() <= y + h));
		}
	
	/**
	 * 
	 * @param oldX The previous X coordinate.
	 * @param deltaX The change in the X-coordinate.
	 * @param oldY The previous Y coordinate.
	 * @param deltaY The change in the Y-coordinate.
	 */
	public void moveRelative(double oldX, double deltaX, double oldY, double deltaY){
		bulletimg.setTranslateX(Math.max(minX, Math.min(maxX, oldX + deltaX)));
        bulletimg.setTranslateY(Math.max(minY, Math.min(maxY, oldY + deltaY)));
        setX(Math.max(minX, Math.min(maxX, oldX + deltaX)));
        setY(Math.max(minY, Math.min(maxY, oldY + deltaY)));
	}
	
	/**
	 * Sets the X-coordinate of the Bullet to the users choice.
	 * @param X Required to set the current Bullet X-coordinate to the one given.
	 */
	public void setX(double X){
		this.x = X;
	}
	
	/**
	 * Sets the Y-coordinate of the Bullet to the users choice.
	 * @param y Required to set the current Bullet Y-coordinate to the one given.
	 */
	public void setY(double y) {
		// TODO Auto-generated method stub
		this.y = y;
	}
	
	/**
	 * Sets the image's location in the corresponding x-coordinate and y coordinate values given by the user.
	 * @param x Required in order to set the X-coordinate of the Image.
	 * @param y Required in order to set the Y-Coordinate of the Image.
	 */
	public void move(double x, double y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
		bulletimg.setX(x);
		bulletimg.setY(y);
	}
	
	/**
	 * Returns the Image of the Bullet
	 */
	public ImageView getImage() {
		// TODO Auto-generated method stub
		return bulletimg;
	}
	
	/**
	 * Returns the Height of the Image
	 */
	@Override
	public double getH() {
		// TODO Auto-generated method stub
		return h;
	}
	
	/**
	 * Returns the Width of the Image
	 */
	@Override
	public double getW() {
		// TODO Auto-generated method stub
		return w;
	}
}
