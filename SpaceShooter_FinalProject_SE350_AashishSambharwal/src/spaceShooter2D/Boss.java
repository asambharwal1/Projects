package spaceShooter2D;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * 
 * @author Aashish SSG
 *
 */
public class Boss extends Foes implements GamePiece, Observer{
	double x;
	double y;
	double w;
	double h;
	ImageView ene;
	int scalingFactor;
	CopyOnWriteArrayList<Bullet> ammo = new CopyOnWriteArrayList<Bullet>();
	CopyOnWriteArrayList<Missiles> missiles = new CopyOnWriteArrayList<Missiles>();
	int Army = 1;
	int ammoe = 1000;
	final double minX = 0;
    final double maxX = 800; 
    final double minY = 0;
    final double maxY = 600;
	private double playerY;
	int chanceint;
	private Random o;
	int Health = 100;
	private Enemies en;
	
	/**
	 * Creates a Boss to be fought at the end of the game.
	 * @param en Enemies instance for getting the list and adding the Boss' personal army to it.
	 * @param obs ObservableList for adding the image of the Boss.
	 * @param x X-coordinate placement for the Boss Image.
	 * @param y Y-coordinate placement for the Boss Image.
	 */
	public Boss(Enemies en, ObservableList<Node> obs, double x, double y){
		this.x = x;
		this.y = y;
		this.ene = new ImageView(new Image("Spaceship15.png"));
		this.h = this.ene.getBoundsInParent().getHeight();
		this.w = this.ene.getBoundsInParent().getWidth();
		this.en = en;
		obs.add(ene);
		setX(x);
		setY(y);
	}
	
	/**
	 * Set the current X-coordinate to the one given by the user as well as the image position.
	 * @param x X-coordinate for the placement of the image and the replacement of the current X value.
	 */
	public void setX(double x){
		this.x = x;
		setPositionX(x);
	}
	
	/**
	 * Set the current Y-coordinate to the one given by the user as well as the image position.
	 * @param y Y-coordinate for the placement of the image and the replacement of the current Y value.
	 */
	public void setY(double y){
		this.y = y;
		setPositionY(y);
	}
	
	/**
	 * Returns the current X-coordinate of the Boss.
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * Returns the current Y-coordinate of the Boss.
	 */
	public double getY(){
		return y;
	}
	
	/**
	 * Sets the Image of the Boss at the particular X coordinate given by the user.
	 * @param x X-coordinate for the placement of the image.
	 */
	public void setPositionX(double x){
		ene.setX(x);
	}
	
	/**
	 * Sets the Image of the Boss at the particular Y coordinate given by the user.
	 * @param y Y-coordinate for the placement of the image.
	 */
	public void setPositionY(double y){
		ene.setY(y);
	}
	
	/**
	 * Fires the current bullet straight ahead from the current X and Y position of the Boss from the first Gun.
	 * @param bullet Fires this particular bullet.
	 */
	public void fireFirstGun(Bullet bullet){
		bullet.fireBossBullet(false, getX(), getY());
		remove(bullet);
	}
	
	/**
	 * Fires the current bullet straight ahead from the current X and Y position of the Boss from the second Gun.
	 * @param bullet Fires this particular bullet.
	 */
	public void fireSecondGun(Bullet bullet){
		bullet.fireBossBullet(false, getX(), getY()+getH()-5);
		remove(bullet);
	}
	
	/**
	 * Fires the current missile straight ahead from the current X and Y position of the Boss.
	 * @param missile Fires this particular missile.
	 */
	public void fireMissile(Missiles missile){
		missile.fire(false, getX(), getY()+(getH()/2));
		missiles.remove(missile);
	}
	
	/**
	 * Adds the army that the boss commands to the list of Enemies in order for them to move straight ahead.
	 */
	public void dispenseArmy(){
		Random o = new Random();
		int formation = o.nextInt(5);
		en.setFormation(formation, getX(), getY());
	}
	
	/**
	 * Moves the Boss as well as the bullets and missiles that the boss has within its arsenal.
	 * @param x Moves the Boss and it's components to the particular X coordinate.
	 * @param y Moves the Boss and it's components to the particular Y coordinate.
	 */
	public void move(double x, double y){
		this.x = x;
		this.y = y;
		ene.setX(x);
		ene.setY(y);
		for(Bullet b: ammo){
        	b.move(getX(), getY());
        }
		for(Missiles b: missiles){
        	b.move(getX(), getY());
        }
	}
	
	/**
	 * Removes the particular bullet from the enemies arsenal.
	 * @param bullet2 The bullet the user wants to remove.
	 */
	public void remove(Bullet bullet2) {
		// TODO Auto-generated method stub
		ammo.remove(bullet2);
	}
	
	/**
	 * Sets the visibility of the Boss and all it's components.
	 * @param bool Sets the visibility to the current boolean, false for invisible and true for visible.
	 */
	public void setAllVisible(boolean bool) {
		// TODO Auto-generated method stub
		ene.setVisible(false);
		for(Bullet b: ammo){
        	b.bulletimg.setVisible(false);
        	b.removeFromPane();
        }
	}
	
	/**
	 * Movement of the Boss according to the movement of the Player.
	 */
	public void towardsOrigin(){
		new AnimationTimer(){
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(x<=minX || !ene.isVisible()){setAllVisible(false); stop();}
				if(getImage().getY()-playerY<0){y+=0.1;}
				else{y-=0.1;}
				move(x, y);
			}
		}.start();
	}
	
	/**
	 * Returns the Image of the Boss.
	 */
	public ImageView getImage() {
		// TODO Auto-generated method stub
		return ene;
	}
	
	@Override
	/**
	 * Handles both the movement as well as the firing of bullets and missiles, also sending of the army. 
	 */
	public void movement() {
		// TODO Auto-generated method stub
		towardsOrigin();
		int chanceint = o.nextInt(50);
		int chanceMissile = o.nextInt(50);
		int chanceArmy = o.nextInt(50);
		if(getImage().isVisible() && ammo.size()>=2 ){
			if(chanceint>30) {
				fireFirstGun(ammo.get(0));
				fireSecondGun(ammo.get(0));
				}
		}
		
		if(getImage().isVisible() && missiles.size()>0 ){
			if(chanceMissile<10) fireMissile(missiles.get(0));
		}
		
		if(chanceArmy==39 && Army>0){dispenseArmy(); Army--;}
		
	}
	
	@Override
	/**
	 * Collision detection with another piece.
	 * @param otherPiece Checks if bounds intersect with the this piece.
	 */
	public boolean collidesWith(GamePiece otherPiece) {
		// TODO Auto-generated method stub
		return ((otherPiece.getX() + otherPiece.getW() >= x) && (otherPiece.getY() + otherPiece.getH() >= y) && (otherPiece.getX() <= x + w) && (otherPiece.getY() <= y + h));
	}
	
	/**
	 * Returns the current height of the Image.
	 */
	@Override
	public double getH() {
		// TODO Auto-generated method stub
		return h;
	}
	
	/**
	 * Returns the current Width of the Image.
	 */
	@Override
	public double getW() {
		// TODO Auto-generated method stub
		return w;
	}
	
	/**
	 * Updates the Boss class when the player moves and acts accordingly.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		o = new Random();
		int slowingMovement = o.nextInt(50);
		playerY = (double) arg1;
		if(slowingMovement<10){ movement(); }
	}
}
