package spaceShooter2D;

import java.util.concurrent.CopyOnWriteArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;


public class Enemy extends Foes implements GamePiece{
	double x;
	double y;
	double w;
	double h;
	ImageView ene;
	int scalingFactor;
	CopyOnWriteArrayList<Bullet> ammo = new CopyOnWriteArrayList<Bullet>();
	int ammoe = 20;
	final double minX = 0;
    final double maxX = 800; 
    final double minY = 0;
    final double maxY = 600;
	
    /**
     * The Enemy class is the basic enemy of which there are multiple of in the game.
     * @param ene Image of the enemy to be used in the game.
     * @param x X-coordinate, where the enemy is to be placed.
     * @param y Y-coordinate, where the enemy is to be placed.
     */
	public Enemy(ImageView ene, double x, double y){
		this.x = x;
		this.y = y;
		this.ene = ene;
		this.h = this.ene.getBoundsInParent().getHeight();
		this.w = this.ene.getBoundsInParent().getWidth();
		setX(x);
		setY(y);
	}
	
	/**
	 * Sets the enemies position at the corresponding X-coordinate as well as the enemies Image at the X-coordinate.
	 * @param x The X-coordinate at which the Image and the position of the enemy is to be set at.
	 */
	public void setX(double x){
		this.x = x;
		setPositionX(x);
	}
	
	/**
	 * Sets the enemies position at the corresponding Y-coordinate as well as the enemies Image at the Y-coordinate.
	 * @param y The Y-coordinate at which the Image and the position of the enemy is to be set at.
	 */
	public void setY(double y){
		this.y = y;
		setPositionY(y);
	}
	
	/**
	 * Returns the X-coordinate at which the Image and the position of the enemy are set.
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * Returns the Y-coordinate at which the Image and the position of the enemy are set.
	 */
	public double getY(){
		return y;
	}
	
	/**
	 * Sets only the Image of the enemy at the given X-coordinate.
	 * @param x The X-coordinate at which the image of the enemy is to be set at.
	 */
	private void setPositionX(double x){
		ene.setX(x);
	}
	
	/**
	 * Sets only the image of the enemy at the given Y-coordinate.
	 * @param y The Y-coordinate at which the image of the enemy is to be set at.
	 */
	private void setPositionY(double y){
		ene.setY(y);
	}
	
	/**
	 * Fires the bullet of the enemy towards the other end of the screen.
	 * @param bullet Fires the particular bullet.
	 */
	public void fire(Bullet bullet){
		bullet.fire(false,getX(), getY());
		remove(bullet);
	}
	
	/**
	 * Move the enemy iamge and it's bullets accordinate to the given x and y coordinates.
	 * @param x The x coordinate at which the enemy and it's bullets are to be placed at.
	 * @param y The y coordinate at which the enemy and it's bullets are to be placed at.
	 */
	public void move(double x, double y){
		this.x = x;
		this.y = y;
		ene.setX(x);
		ene.setY(y);
		for(Bullet b: ammo){
        	b.move(getX(), getY());
        }
	}
	
	/**
	 * Adds a particular bullet to the enemy's ammo.
	 * @param bullet2 The bullet to be added to the ammo.
	 */
	public void add(Bullet bullet2) {
		// TODO Auto-generated method stub
		ammo.add(bullet2);
	}
	
	/**
	 * Removes a particular bullet from the enemy's ammo.
	 * @param bullet2 The bullet to be removed from the ammo.
	 */
	public void remove(Bullet bullet2) {
		// TODO Auto-generated method stub
		ammo.remove(bullet2);
	}
	
	/**
	 * Sets the visibility of the entire enemy image and if it is false, it will delete all the bullets inside the enemies ammo if any.
	 * @param bool Sets the visibility of all to the value bool.
	 */
	public void setAllVisible(boolean bool) {
		// TODO Auto-generated method stub
		ene.setVisible(bool);
		if(!bool){
			for(Bullet b: ammo){
	        	b.bulletimg.setVisible(bool);
	        	b.removeFromPane();
			}
		}
	}
	
	/**
	 * This handles the smooth movement of the enemy and ensures that once it has reached the end of the visible area, it dissapears.
	 */
	public void towardsOrigin(){
		new AnimationTimer(){
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(x<=minX || !ene.isVisible()) {setAllVisible(false); stop();}
				x-=.5;
				move(x, y);
			}
		}.start();
	}
	
	/**
	 * Returns the image of the enemy.
	 */
	public ImageView getImage() {
		// TODO Auto-generated method stub
		return ene;
	}
	
	/**
	 * Handles the movement of the enemy.
	 */
	@Override
	public void movement() {
		// TODO Auto-generated method stub
		towardsOrigin();
	}
	
	/**
	 * Handles collision detection if the enemy comes in contact with the bullet or comes in contact with the player.
	 */
	@Override
	public boolean collidesWith(GamePiece otherPiece) {
		// TODO Auto-generated method stub
		return ((otherPiece.getX() + otherPiece.getW() >= x) && (otherPiece.getY() + otherPiece.getH() >= y) && (otherPiece.getX() <= x + w) && (otherPiece.getY() <= y + h));
	}
	
	/**
	 * Returns the height of the enemy's image.
	 */
	@Override
	public double getH() {
		// TODO Auto-generated method stub
		return h;
	}
	
	/**
	 * Returns the width of the enemy's image.
	 */
	@Override
	public double getW() {
		// TODO Auto-generated method stub
		return w;
	}

	/**
	 * Returns the list containing the bullets that the enemy has.
	 * @return Returns the list of bullets in the ammo.
	 */
	public CopyOnWriteArrayList<Bullet> getBulletAmmo() {
		// TODO Auto-generated method stub
		return ammo;
	}
}
