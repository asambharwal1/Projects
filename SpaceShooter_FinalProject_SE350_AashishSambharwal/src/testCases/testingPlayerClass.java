package testCases;
import org.junit.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import spaceShooter2D.*;
import static org.junit.Assert.*;

public class testingPlayerClass{
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	MainGame mainGame;
	Player player;
	
	public testingPlayerClass(){
		mainGame = new MainGame();
	}
	
	/**
	 * Testing is the image is properly implemented and that the height and width aren't 0.
	 */
	@Test
	public void testingImage(){
		Image player = new Image("player.png");
        ImageView eneImg = new ImageView(player);
		this.player = new Player(eneImg, 0, 0);
		boolean heightCheck = player.getHeight()!=0;
		boolean widthCheck = player.getWidth()!=0;
		boolean image = this.player.getImage()!=null;
		assertEquals(heightCheck, true);
		assertEquals(widthCheck, true);
		assertEquals(image, true);
	}
	
	/**
	 * Tests whether our given X and Y-coordinates actually place the image in the one desired.
	 */
	@Test
	public void testingImageSettings(){
		Image player = new Image("player.png");
        ImageView eneImg = new ImageView(player);
        this.player = new Player(eneImg, 0, 0);
		this.player.setImage(25, 25);
		assertEquals(this.player.getImage().getX(), 25, 0);
		assertEquals(this.player.getImage().getY(), 25, 0);
	}
	
	/**
	 * Tests if the player itself is actually set at the X-coordinate that we intended.
	 */
	@Test
	public void testingSettingX(){
		Image player = new Image("player.png");
        ImageView eneImg = new ImageView(player);
        this.player = new Player(eneImg, 0, 0);
		this.player.setX(25);
		assertEquals(this.player.getX(), 25, 0);
	}
	
	/**
	 * Tests if the player itself is actually set at the Y-coordinate that we intended.
	 */
	@Test
	public void testingSettingY(){
		Image player = new Image("player.png");
        ImageView eneImg = new ImageView(player);
        this.player = new Player(eneImg, 0, 0);
		this.player.setY(24);
		assertEquals(this.player.getY(), 24, 0);
	}
	
	/**
	 * Tests if the image is actually set at scale that we intended.
	 */
	@Test
	public void testingScale(){
		Image player = new Image("player.png");
        ImageView eneImg = new ImageView(player);
        this.player = new Player(eneImg, 0, 0);
        this.player.setScale(50);
        assertEquals(this.player.getImage().getScaleX(), 50, 0);
		assertEquals(this.player.getImage().getScaleY(), 50, 0);
	}
	
	/**
	 * Tests if the value of the bossDefeated boolean is changed when we use the function to alter it.
	 */
	@Test
	public void testingbossDefeated(){
		Image player = new Image("player.png");
        ImageView eneImg = new ImageView(player);
        this.player = new Player(eneImg, 0, 0);
        boolean value = false;
        this.player.setBossDefeatState(value);
        assertEquals(this.player.bossIsDefeated(), value);
	}
	
	/**
	 * Tests if the height of the player is equal to the height of the image that represents it.
	 */
	@Test
	public void testingHeight(){
		Image player = new Image("player.png");
		ImageView eneImg = new ImageView(player);
		this.player = new Player(eneImg, 0, 0);
		assertEquals(this.player.getH(), eneImg.getBoundsInParent().getHeight(), 0);
	}
	
	/**
	 * Tests if the width of the player is equal to the width of the image that represents it.
	 */
	@Test
	public void testingWidth(){
		Image player = new Image("player.png");
		ImageView eneImg = new ImageView(player);
		this.player = new Player(eneImg, 0, 0);
		assertEquals(this.player.getW(), eneImg.getBoundsInParent().getWidth(), 0);
	}
	
	/**
	 * Tests if the bullet is actually added to the list of bullets in the player class.
	 */
	@Test
	public void testingBulletAddition(){
		Image player = new Image("player.png");
		ImageView eneImg = new ImageView(player);
		ImageView bulletimg = new ImageView(new Image("bullet2.png"));
		this.player = new Player(eneImg, 0, 0);
		this.player.addBullet(new Bullet(bulletimg, 0, 0, null, null, null));
		assertEquals(this.player.getBulletAmmo().size(), 1, 0);
	}
	
	/**
	 * Tests if the bullet is actually removed from the list of bullets in the player class.
	 */
	@Test
	public void testingBulletRemoval(){
		Image player = new Image("player.png");
		ImageView eneImg = new ImageView(player);
		ImageView bulletimg = new ImageView(new Image("bullet2.png"));
		this.player = new Player(eneImg, 0, 0);
		this.player.addBullet(new Bullet(bulletimg, 0, 0, null, null, null));
		this.player.remove(this.player.getBulletAmmo().get(0));
		assertEquals(this.player.getBulletAmmo().size(), 0, 0);
	}
	
	/**
	 * Tests if the missile is actually added to the list of missiles in the player class.
	 */
	@Test
	public void testingMissileAddition(){
		Image player = new Image("player.png");
		ImageView eneImg = new ImageView(player);
		this.player = new Player(eneImg, 0, 0);
		Pane pane = new Pane();
		this.player.addMissile(new Missiles(0, 0, null, pane.getChildren(), null));
		assertEquals(this.player.getMissileAmmo().size(), 1, 0);
	}
	
	/**
	 * Tests if the missile is actually removed from the list of missiles in the player class.
	 */
	@Test
	public void testingMissileRemoval(){
		Image player = new Image("player.png");
		ImageView eneImg = new ImageView(player);
		this.player = new Player(eneImg, 0, 0);
		Pane pane = new Pane();
		this.player.addMissile(new Missiles(0, 0, null, pane.getChildren(), null));
		this.player.remove(this.player.getMissileAmmo().get(0));
		assertEquals(this.player.getMissileAmmo().size(), 0, 0);
	}
	
	/**
	 * Tests if the movement of the player also moves the leaf nodes (bullets and missiles) along with it.
	 */
	@Test
	public void testingCompositePatternMovement(){
		Image player = new Image("player.png");
		ImageView eneImg = new ImageView(player);
		this.player = new Player(eneImg, 0, 0);
		Pane pane = new Pane();
		ImageView bulletimg = new ImageView(new Image("bullet2.png"));
		this.player.addMissile(new Missiles(0, 0, null, pane.getChildren(), null));
		this.player.addBullet(new Bullet(bulletimg, 0, 0, null, null, null));
		this.player.moveRelative(0, 25, 0, 25);
		
		assertEquals(this.player.getMissileAmmo().get(0).getX(), this.player.getX(), 0);
		assertEquals(this.player.getMissileAmmo().get(0).getY(), this.player.getY(), 0);
		
		assertEquals(this.player.getBulletAmmo().get(0).getX(), this.player.getX(), 0);
		assertEquals(this.player.getBulletAmmo().get(0).getY(), this.player.getY(), 0);
	}
	
	/**
	 * Tests if two entities collide with one another.
	 */
	@Test
	public void testingCollisions(){
		Image player = new Image("player.png");
		ImageView eneImg = new ImageView(player);
		this.player = new Player(eneImg, 0, 0);
		ImageView enemyImage = new ImageView(new Image("enemy1.png"));
		this.player.moveRelative(0, 1, 0, 1);
		Enemy enemy = new Enemy(enemyImage, 1, 1);
		assertTrue(this.player.collidesWith(enemy));
	}
}
