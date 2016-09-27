package spaceShooter2D;

import java.util.Random;

import com.sun.javafx.geom.Point2D;

@SuppressWarnings("restriction")
public class CardFiveFormation extends Formation{
	private Point2D[] card5Formation = {new Point2D(0,0),new Point2D(0,100),new Point2D(100,0),new Point2D(100,100),new Point2D(50,50)};
	MainGame mainGame;
	Enemies en;
	Random random = new Random();
	
	/**
	 * Card Five formation of attack.
	 * @param game Required for setting the enemies on to the pane.
	 * @param en Required for adding the enemies to the list of enemies in the Enemies class.
	 */
	public CardFiveFormation(MainGame game, Enemies en){
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
		for(Point2D pos: card5Formation){
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
		for(Point2D pos: card5Formation){
			Enemy temp = new Enemy(mainGame.loadEnemyImage(), x+pos.x, y+pos.y);
			for(int i=0; i<20; i++){
				temp.ammo.add(new Bullet(temp.getX()+25, temp.getY()+25, en, mainGame.pane.getChildren(), mainGame.player));
			}
			temp.ene.toFront();
			en.getList().add(temp);
		}
	}

}
