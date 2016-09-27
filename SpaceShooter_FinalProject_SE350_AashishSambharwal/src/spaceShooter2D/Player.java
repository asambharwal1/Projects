package spaceShooter2D;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.scene.image.ImageView;

public class Player extends Observable implements GamePiece {
	List<Bullet> ammo = new CopyOnWriteArrayList<Bullet>();
	List<Missiles> ammo1 = new CopyOnWriteArrayList<Missiles>();
	ImageView pImage;
	double x;
	double y;
	double h;
	double w;
	int Health = 100;
	final double minX = 0;
    final double maxX = 900; 
    final double minY = 0;
    final double maxY = 700;
    boolean defeatedBoss = false;
	
    /**
     * The main player of the game.
     * @param img The Image to be used as the player.
     * @param Xcoor The X-coordinate at which the place is placed.
     * @param Ycoor The Y coordinate at which the place is placed.
     */
	public Player(ImageView img, double Xcoor, double Ycoor){
		this.pImage = img;
		this.x = Xcoor;
		this.y = Ycoor;
		this.h = pImage.getBoundsInParent().getHeight();
		this.w = pImage.getBoundsInParent().getWidth();
		moveRelative(x, x, y, y);
	}
	
	/**
	 * Sets the defeat state of the boss to the particular boolean value given by the user.
	 * @param defeated Boolean to be set as defeated.
	 */
	public void setBossDefeatState(boolean defeated){
		defeatedBoss = defeated;
	}
	
	/**
	 * Returns true if the boss has been defeated and false if the boss hasn't been defeated.
	 * @return Returns the current state of the Boss.
	 */
	public boolean bossIsDefeated(){
		return defeatedBoss;
	}
	
	/**
	 * Sets the scale of the player image.
	 * @param scale The scale by which the player image is magnified.
	 */
	public void setScale(int scale){
		pImage.setScaleX(scale);
		pImage.setScaleY(scale);
	}
	
	/**
	 * Returns the iamge of the player.
	 */
	@Override
	public ImageView getImage() {
		// TODO Auto-generated method stub
		return pImage;
	}

	/**
	 * Returns the Y-coordinate of the player.
	 */
	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return x;
	}

	/**
	 * Returns the Y-coordinate of the player.
	 */
	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}
	
	/**
	 * Adds a bullet to the players ammo.
	 * @param b The particular bullet to be added to the ammo.
	 */
	public void addBullet(Bullet b){
		ammo.add(b);
	}
	
	/**
	 * Sets the Y-coordinate of the player to this. (Not image) Only used when moved.
	 * @param x The X-coordinate that is to be set as the current X-coordinate.
	 */
	public void setX(double x){
		this.x = x;
	}
	
	/**
	 * Sets the Y-coordinate of the player to this. (Not image) Only used when moved.
	 * @param y The Y-coordinate that is to be set as the current X-coordinate.
	 */
	public void setY(double y){
		this.y = y;
	}
	
	/**
	 * Sets the image of the player a certain location.
	 * @param x The X-coordinate at which the players image is to be placed at.
	 * @param y The Y-coordinate at which the players image is to be placed at.
	 */
	public void setImage(double x, double y){
		pImage.setX(x);
		pImage.setY(y);
	}
	
	/**
	 * Moves the player in a more smoother way. Also alerts the player upon movement.
	 * @param oldX The old X-coordinate of the player image.
	 * @param deltaX The change in X-coordinate of the player image.
	 * @param oldY The old Y-coordinate of the player image.
	 * @param deltaY The change in Y-coordinate of the player image.
	 */
	public void moveRelative(double oldX, double deltaX, double oldY, double deltaY){
		pImage.setTranslateX(Math.max(minX, Math.min(maxX, oldX + deltaX)));
        pImage.setTranslateY(Math.max(minY, Math.min(maxY, oldY + deltaY)));
        setX(Math.max(minX, Math.min(maxX, oldX + deltaX)));
        setY(Math.max(minY, Math.min(maxY, oldY + deltaY)));
        setChanged();
        notifyObservers(getY());
        for(Bullet b: ammo){
        	b.move(getX(), getY());
        }
        for(Missiles b: ammo1){
        	b.move(getX(), getY());
        }
	}
	
	/**
	 * Fires the bullets of the player.
	 */
	public void fire(){
		for(int i=0; i<ammo.size(); i++){
			ammo.get(i).fire(true, getX()+30, getY());
			remove(ammo.get(i));
		}
	}
	
	/**
	 * Fires the missile of the player.
	 */
	public void firemissile(){
		for(int i=0; i<ammo1.size(); i++){
			ammo1.get(i).fire(getX()+30, getY()+(getH()/2));
			remove(ammo1.get(i));
		}
	}
	
	/**
	 * Removes a partiulcar missile.
	 * @param missiles The particular missile to be removed.
	 */
	public void remove(Missiles missiles) {
		// TODO Auto-generated method stub
		ammo1.remove(missiles);
	}
	
	/**
	 * Removes a particular bullet.
	 * @param bullet The partiular bullet to be removed.
	 */
	public void remove(Bullet bullet) {
		// TODO Auto-generated method stub
		ammo.remove(bullet);
	}

	/**
	 * Checks if the otherPiece intersects with the Player.
	 * @param otherPiece The piece that is to be checked with for collision. 
	 */
	@Override
	public boolean collidesWith(GamePiece otherPiece) {
		// TODO Auto-generated method stub
		return (otherPiece.getX() + otherPiece.getW() >= x && otherPiece.getY() + otherPiece.getH() >= y && otherPiece.getX() <= x + w && otherPiece.getY() <= y + h);
	}

	/**
	 * Returns the height of the players image.
	 */
	@Override
	public double getH() {
		// TODO Auto-generated method stub
		return h;
	}

	/**
	 * Returns the width of the players image.
	 */
	@Override
	public double getW() {
		// TODO Auto-generated method stub
		return w;
	}

	/**
	 * Adds a particular missile to the ammo containing the missiles for the Player.
	 * @param mis Adds the current missile to the ammo.
	 */
	public void addMissile(Missiles mis) {
		// TODO Auto-generated method stub
		ammo1.add(mis);
	}
	
	/**
	 * Returns the list containing the bullets that the player has.
	 * @return Returns the ammo list of bullets.
	 */
	public List<Bullet> getBulletAmmo() {
		// TODO Auto-generated method stub
		return ammo;
	}
	
	/**
	 * Returns the list containing the missiles that the player has.
	 * @return Returns the ammo list of missiles.
	 */
	public List<Missiles> getMissileAmmo() {
		// TODO Auto-generated method stub
		return ammo1;
	}
	
}
