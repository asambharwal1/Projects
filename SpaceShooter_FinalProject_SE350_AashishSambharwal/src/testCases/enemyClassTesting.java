package testCases;
import org.junit.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import spaceShooter2D.*;
import static org.junit.Assert.*;

public class enemyClassTesting{
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	MainGame mainGame;
	Enemy enemy;
	
	public enemyClassTesting(){
		mainGame = new MainGame();
	}
	
	/**
	 * Testing is the image is properly implemented and that the height and width aren't 0.
	 */
	@Test
	public void testingImage(){
		Image player = new Image("enemy1.png");
        ImageView eneImg = new ImageView(player);
		enemy = new Enemy(eneImg, 100, 100);
		boolean heightCheck = enemy.getH()!=0;
		boolean widthCheck = enemy.getW()!=0;
		boolean image = enemy.getImage()!=null;
		assertEquals(heightCheck, true);
		assertEquals(widthCheck, true);
		assertEquals(image, true);
	}
	
	/**
	 * Tests whether the intial placement of the Enemy is set.
	 */
	@Test
	public void testingInitialSettings(){
		Image player = new Image("enemy1.png");
        ImageView eneImg = new ImageView(player);
		enemy = new Enemy(eneImg, 100, 100);
		assertEquals(enemy.getX(), 100, 0);
		assertEquals(enemy.getY(), 100, 0);
	}
	
	/**
	 * Tests if the image is actually set at X-coordinate that we intended.
	 */
	@Test
	public void testingImageSettingX(){
		Image player = new Image("enemy1.png");
        ImageView eneImg = new ImageView(player);
		enemy = new Enemy(eneImg, 0, 0);
		enemy.setX(25);
		assertEquals(enemy.getImage().getX(), 25, 0);
	}
	
	/**
	 * Tests if the image is actually set at Y-coordinate that we intended.
	 */
	@Test
	public void testingImageSettingY(){
		Image player = new Image("enemy1.png");
        ImageView eneImg = new ImageView(player);
		enemy = new Enemy(eneImg, 0, 0);
		enemy.setY(24);
		assertEquals(enemy.getImage().getY(), 24, 0);
	}
	
	/**
	 * Tests if the enemy itself is actually set at X-coordinate that we intended.
	 */
	@Test
	public void testingSettingX(){
		Image player = new Image("enemy1.png");
        ImageView eneImg = new ImageView(player);
		enemy = new Enemy(eneImg, 0, 0);
		enemy.setX(25);
		assertEquals(enemy.getX(), 25, 0);
	}
	
	/**
	 * Tests if the enemy itself is actually set at Y-coordinate that we intended.
	 */
	@Test
	public void testingSettingY(){
		Image player = new Image("enemy1.png");
        ImageView eneImg = new ImageView(player);
		enemy = new Enemy(eneImg, 0, 0);
		enemy.setY(24);
		assertEquals(enemy.getY(), 24, 0);
	}
	
	/**
	 * Tests the setVisibility function which sets all the bullets and the enemy itself to the boolean given by the user.
	 */
	@Test
	public void testingSetVisibility(){
		Image player = new Image("enemy1.png");
        ImageView eneImg = new ImageView(player);
		enemy = new Enemy(eneImg, 0, 0);
		boolean value = false;
		enemy.setAllVisible(value);
		boolean test = enemy.getImage().isVisible();
		assertEquals(test, value);
	}
	
	/**
	 * Tests bullet addition to the enemies ammo.
	 */
	@Test
	public void testingBulletAddition(){
		Image player = new Image("enemy1.png");
		ImageView eneImg = new ImageView(player);
		enemy = new Enemy(eneImg, 0, 0);
		ImageView bulletimg = new ImageView(new Image("bullet2.png"));
		enemy.add(new Bullet(bulletimg, 0, 0, null, null, null));
		assertEquals(enemy.getBulletAmmo().size(), 1, 0);
	}
	
	/**
	 * Tests bullet removal from the enemies ammo.
	 */
	@Test
	public void testingBulletRemoval(){
		Image player = new Image("enemy1.png");
		ImageView eneImg = new ImageView(player);
		enemy = new Enemy(eneImg, 0, 0);
		ImageView bulletimg = new ImageView(new Image("bullet2.png"));
		enemy.add(new Bullet(bulletimg, 0, 0, null, null, null));
		enemy.remove(enemy.getBulletAmmo().get(0));
		assertEquals(enemy.getBulletAmmo().size(), 0, 0);
	}
	
	/**
	 * Tests if the movement of the enemy also moves the leaf nodes (bullets) along with it.
	 */
	@Test
	public void testingCompositePatternMovement(){
		Image player = new Image("enemy1.png");
		ImageView eneImg = new ImageView(player);
		enemy = new Enemy(eneImg, 0, 0);
		ImageView bulletimg = new ImageView(new Image("bullet2.png"));
		enemy.add(new Bullet(bulletimg, 0, 0, null, null, null));
		enemy.move(25, 25);
		
		assertEquals(enemy.getBulletAmmo().get(0).getX(), enemy.getX(), 0);
		assertEquals(enemy.getBulletAmmo().get(0).getY(), enemy.getY(), 0);
	}
	
	/**
	 * Tests if two entities collide.
	 */
	@Test
	public void testingCollisions(){
		ImageView pImg = new ImageView(new Image("player.png"));
		Player player11 = new Player(pImg, 25, 25);
		Image player = new Image("enemy1.png");
		ImageView eneImg = new ImageView(player);
		enemy = new Enemy(eneImg, 25, 25);
		assertTrue(enemy.collidesWith(player11));
	}
}
