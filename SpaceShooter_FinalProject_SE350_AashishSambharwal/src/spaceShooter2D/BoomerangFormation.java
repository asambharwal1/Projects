package spaceShooter2D;

import java.util.Random;

import com.sun.javafx.geom.Point2D;

@SuppressWarnings("restriction")
public class BoomerangFormation extends Formation{
	private Point2D[] boomerangFormation = {new Point2D(75,0),new Point2D(0,25),new Point2D(0,100),new Point2D(75,125)};
	MainGame mainGame;
	Enemies en;
	Random random = new Random();
	
	/**
	 * Boomerang formation of attack.
	 * @param game Required for setting the enemies on to the pane.
	 * @param en Required for adding the enemies to the list of enemies in the Enemies class.
	 */
	public BoomerangFormation(MainGame game, Enemies en){
		mainGame = game;
		this.en = en;
	}
	
	/**
	 * Sets the formation randomly at any position.
	 */
	@Override
	public void formationSet() {
		// TODO Auto-generated method stub
		int x = (int) (mainGame.background.getX()+1000);
		int y = random.nextInt(700);
		if(y+200>mainGame.maxY) y-=250;
		for(Point2D pos: boomerangFormation){
			Enemy temp = new Enemy(mainGame.loadEnemyImage(), x+pos.x, y+pos.y);
			for(int i=0; i<20; i++){
				temp.ammo.add(new Bullet(temp.getX()+25, temp.getY()+25, en, mainGame.pane.getChildren(), mainGame.player));
			}
			temp.ene.toFront();
			en.getList().add(temp);
		}
	}

	/**
	 * Sets the formation at a particular x and y coordinate.
	 * @param x Sets the formation to the given x-coordinate.
	 * @param y Sets the formation to the given y-coordinate.
	 */
	@Override
	public void formationSet(double x, double y) {
		// TODO Auto-generated method stub
		if(y+200>mainGame.maxY) y-=250;
		for(Point2D pos: boomerangFormation){
			Enemy temp = new Enemy(mainGame.loadEnemyImage(), x+pos.x, y+pos.y);
			for(int i=0; i<20; i++){
				temp.ammo.add(new Bullet(temp.getX()+25, temp.getY()+25, en, mainGame.pane.getChildren(), mainGame.player));
			}
			temp.ene.toFront();
			en.getList().add(temp);
		}
	}

}
