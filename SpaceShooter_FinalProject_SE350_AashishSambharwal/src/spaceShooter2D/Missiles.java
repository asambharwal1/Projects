package spaceShooter2D;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Missiles implements GamePiece{
	
	final double minX = 0;
    final double maxX = 900; 
    final double minY = 0;
    final double maxY = 700;
	
	boolean homing;
	ImageView misimg;
	Player player;
	ObservableList<Node> children;
	private double x;
	private double y;
	private Enemies en;
	CollisionDetect c;
	protected boolean collision;
	private double w;
	private double h;
	int Type;
	private int missiledmg = 25;
	
	/**
	 * The missile class for the Player class.
	 * @param x The X-coordinate of the the player.
	 * @param y The Y-coordinate of the the player.
	 * @param en The Enemies class instance for collision detection.
	 * @param children The list of observable nodes, required for removing redundant images.
	 * @param player The player required for collision detection.
	 */
	public Missiles(double x, double y, Enemies en, ObservableList<Node> children, Player player){
		Type = 1;
		this.player = player;
		Image bulletEnemy = new Image("jhr6ol.png");
		this.misimg = new ImageView(bulletEnemy);
		this.children = children;
		children.add(misimg);
		this.x = x; 
		this.y = y;
		this.en = en;
		this.w = misimg.getBoundsInParent().getWidth();
		this.h = misimg.getBoundsInParent().getWidth();
		c = new CollisionDetect();
		misimg.setRotate(360);
		misimg.setScaleX(2);
		misimg.setScaleY(2);
		misimg.setX(x);
		misimg.setY(y);
		misimg.setVisible(false);
	}
	
	/**
	 * The missile class for the Boss class.
	 * @param en The Enemies class instance for collision detection.
	 * @param children The list of observable nodes, required for removing redundant images.
	 * @param player The player required for collision detection.
	 * @param x The X-coordinate of the the boss.
	 * @param y The Y-coordinate of the the boss.
	 */
	public Missiles(Enemies en, ObservableList<Node> children, Player player, double x, double y){
		Type = 2;
		this.player = player;
		Image bulletEnemy = new Image("jhr6ol.png");
		this.misimg = new ImageView(bulletEnemy);
		this.children = children;
		children.add(misimg);
		this.x = x; 
		this.y = y;
		this.en = en;
		this.w = misimg.getBoundsInParent().getWidth();
		this.h = misimg.getBoundsInParent().getWidth();
		c = new CollisionDetect();
		misimg.setRotate(360);
		misimg.setScaleX(2);
		misimg.setScaleY(2);
		misimg.setX(x);
		misimg.setY(y);
		misimg.setVisible(false);
	}
	
	/**
	 * Returns the X-coordinate of the missile.
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * Returns the Y-coordinate of the missile.
	 */
	public double getY(){
		return y;
	}
	
	/**
	 * Sets the image of missile at a particular X and Y coordinate.
	 * @param x The X-coordinate at which the image of missile is to be set.
	 * @param y The Y-coordinate at which the image of missile is to be set.
	 */
	public void setImage(double x, double y){
		misimg.setX(x);
		misimg.setY(y);
	}
	
	/**
	 * Collision occurrance.
	 * @param k Sets the current boolean value to collision.
	 * @return Returns true if collision occurred, otherwise false if collision didn't occur.
	 */
	public boolean collisionOccurred(boolean k){
		this.collision = k;
		return collision;
	}
	
	/**
	 * Returns this class.
	 * @return Returns the current class.
	 */
	public Missiles getMissile(){
		return this;
	}
	
	/**
	 * Removes the missiles image from the pane.
	 */
	public void removeFromPane(){
		this.children.remove(this.misimg);
	}
	
	/**
	 * Fires the missile (Player Class).
	 * @param X The X-coordinate from which the missile is to be fired from.
	 * @param Y The Y-coordinate from which the missile is to be fired from.
	 */
	public void fire(double X, double Y){
		x = X;
		y = Y;
		setImage(x,y);
		new AnimationTimer(){

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(x>=maxX || x<=minX || collision) {
					misimg.setVisible(false);
					removeFromPane();
					stop();
					}
				
				else if(x<maxX && x>minX && !collision){
					misimg.setVisible(true);
					}
				c.collide(getMissile(), en, player, children);
				
				if(homing && en.getList().size()!=0){
					if(en.getList().get(0).getX()==getImage().getX()) x+=25;
					if(en.getList().get(0).getY() == getImage().getY()) y+=25;
					if(getImage().getX() != en.getList().get(0).getX() || getImage().getY() != en.getList().get(0).getY()){
						if(getImage().getX()-en.getList().get(0).getX()<0){x+=5;}
						else{x-=5;}
						if(getImage().getY()-en.getList().get(0).getY()<0){y+=5;}
						else{y-=5;}
					}
				} else{homing = false; x+=10;}
				
				move(getX(), getY());
			}
		}.start();
	}
	
	/**
	 * Checks for collision of the missile against some other piece.
	 * @param otherPiece The piece with which the collision is to be tested with.
	 */
	public boolean collidesWith(GamePiece otherPiece) {
		return ((otherPiece.getX() + otherPiece.getW() >= x) && (otherPiece.getY() + otherPiece.getH() >= y) && (otherPiece.getX() <= x + w) && (otherPiece.getY() <= y + h));
		}
	
	/**
	 * Moves the missile more smoothly across the screen.
	 * @param oldX The old X-coordinate of the missile.
	 * @param deltaX The change in the X-coordinate of the missile.
	 * @param oldY The old Y-coordinate of the missile.
	 * @param deltaY The change in the Y-coordinate of the missile.
	 */
	public void moveRelative(double oldX, double deltaX, double oldY, double deltaY){
		misimg.setTranslateX(Math.max(minX, Math.min(maxX, oldX + deltaX)));
        misimg.setTranslateY(Math.max(minY, Math.min(maxY, oldY + deltaY)));
        setX(Math.max(minX, Math.min(maxX, oldX + deltaX)));
        setY(Math.max(minY, Math.min(maxY, oldY + deltaY)));
	}
	
	/**
	 * Sets only the classes X-coordinate.
	 * @param X The X-coordinate which is to replace the current X-coordinate.
	 */
	public void setX(double X){
		this.x = X;
	}
	
	/**
	 * Sets only the classes Y coordinate.
	 * @param y The Y-coordinate which is to replace the current Y-coordinate.
	 */
	public void setY(double y) {
		// TODO Auto-generated method stub
		this.y = y;
	}
	
	/**
	 * Moves the missile according to the X-coordinate and the Y-coordinate.
	 * @param x The X-coordinate at which the missile is to be placed.
	 * @param y The Y-coordinate at which the missile is to be placed.
	 */
	public void move(double x, double y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
		misimg.setX(x);
		misimg.setY(y);
	}

	/**
	 * Returns the image of the missile.
	 */
	public ImageView getImage() {
		// TODO Auto-generated method stub
		return misimg;
	}
	
	/**
	 * Returns the height of the missiles image.
	 */
	@Override
	public double getH() {
		// TODO Auto-generated method stub
		return h;
	}
	
	/**
	 * Returns the width of the missiles image.
	 */
	@Override
	public double getW() {
		// TODO Auto-generated method stub
		return w;
	}
	
	
	/**
	 * Fires the missile across the screen depending upon whether it is a homing missile or a reglar missile.
	 * @param direction The direction of the missile. True for positive direction and false for negative direction.
	 * @param X The X-coordinate at which the missile to be fired from.
	 * @param Y The Y-coordinate at which the missile to be fired from.
	 */
	public void fire(boolean direction, double X, double Y) {
		// TODO Auto-generated method stub
		x = X;
		y = Y;
		setImage(x,y);
		getImage().setRotate(180);
		new AnimationTimer(){
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(x>=maxX || x<=minX || collision) {
					misimg.setVisible(false);
					removeFromPane();
					stop();
					}
				
				else if(x<maxX && x>minX && !collision){
					misimg.setVisible(true);
					}
				
				c.collide(getMissile(), en, player, children);
				
				if(direction)x+=10;
				else x-=10;
				
				move(getX(), getY());
			}
		}.start();
	}
	
	/**
	 * Returns the damage done by the missile.
	 * @return Amount of damage a missile inflicts upon the enemy.
	 */
	public int getDamage() {
		// TODO Auto-generated method stub
		return missiledmg ;
	}
}
