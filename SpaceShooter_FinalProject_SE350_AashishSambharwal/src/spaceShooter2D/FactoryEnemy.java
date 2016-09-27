package spaceShooter2D;

public class FactoryEnemy {
	/**
	 * Factory of the Enemies: Creates the enemies based on the ID given into the Factory.
	 * @param ID Required in order to decide what enemy type to make.
	 * @param game Required in order to load images, pass the Enemies instance and pass the observable list for removal and addition of images.
	 * @param x Required for setting the current image at this particular X coordinate.
	 * @param y Required for setting the current image at this particular Y coordinate.
	 * @return
	 */
	public GamePiece makeEnemy(int ID, MainGame game, double x, double y){
		if(ID==1) {
			return new Enemy(game.loadEnemyImage(), x, y);
		} else{
			return new Boss(game.en, game.pane.getChildren(), x, y);
		}
	}
}
