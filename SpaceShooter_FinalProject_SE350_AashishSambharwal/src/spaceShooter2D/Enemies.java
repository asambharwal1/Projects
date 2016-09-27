package spaceShooter2D;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.sun.javafx.geom.Point2D;

import javafx.scene.image.ImageView;

@SuppressWarnings({ "restriction", "unused" })
public class Enemies implements Runnable{
	private CopyOnWriteArrayList<Foes> en = new CopyOnWriteArrayList<Foes>();
	private int ammoe = 20;
	private final double minX = 0;
    private final double maxX = 900; 
    private final double minY = 0;
    private final double maxY = 700;
	private MainGame mainGame;
	private Random random = new Random();
	private int solNum;
	private boolean running = true;
	private FactoryEnemy factory;
	private static Enemies instance;
	
	private Enemies(){
		
	}
	
	/**
	 * Singleton Pattern Implementation.
	 * Gives a single instance of the Enemies class.
	 * @return Returns the Enemies class.
	 */
	public static Enemies getInstance(){
		if(instance == null) instance = new Enemies();
		return instance;
	}
	
	/**
	 * Adds the number of initial enemies, when the class is first called.
	 * @param mainGame Required for bullet collision and for adding enemy images to the class.
	 * @param solNum Number of intial enemies to be set.
	 */
	public void EnemyAdder(MainGame mainGame, int solNum){
		this.solNum = solNum;
		this.mainGame = mainGame;
		factory = new FactoryEnemy();
		addEnemy((int)(solNum*0.8));
	}
	
	/**
	 * Checks the visibility of the enemies within the list.
	 * @return Returns true if all enemies within the list are invisible and false if one of the images is visible.
	 */
	public boolean checkFalseVisibilityofAll(){
		for(Foes e: en){
			if(e.getImage().isVisible()) return false;
		}
		return true;
	}
	
	/**
	 * Returns the list of enemies in case we need to remove all enemies within the list.
	 * @return Returns the list of enemies.
	 */
	public synchronized CopyOnWriteArrayList<Foes> getList(){
		return en;
	}
	
	/**
	 * Add a certain number of enemies to the list.	
	 * @param solNum The number of enemies to be added.
	 */
	public void addEnemy(int solNum){
		int setPieces = 0;
		while(setPieces!=solNum){
			int x = (int) (mainGame.background.getX()+1000);
			int y = random.nextInt(700);
			Enemy temp = (Enemy) factory.makeEnemy(1, mainGame, x, y);
			for(int i=0; i<ammoe; i++){
				temp.ammo.add(new Bullet(temp.getX()+25, temp.getY()+25, this, mainGame.pane.getChildren(), mainGame.player));
			}
			temp.ene.toFront();
			if(notOverlapping(temp)){
				en.add(temp);
				setPieces++;
			} else{
				mainGame.pane.getChildren().remove(temp.getImage());
				temp = null;
			}
		}
	}
	
	/**
	 * Add the Boss for when a certain number of enemies have been defeated.
	 */
	public void addBoss(){
		int x = (int) (mainGame.background.getX()+850);
		int y = random.nextInt(700);
		Boss temp = (Boss) factory.makeEnemy(2, mainGame, x, y);
		for(int i=0; i<ammoe; i++){
			temp.ammo.add(new Bullet(temp.getX()+25, temp.getY()+25, this, mainGame.pane.getChildren(), mainGame.player));
		}
		for(int i=0; i<ammoe; i++){
			temp.missiles.add(new Missiles(this, mainGame.pane.getChildren(), mainGame.player,temp.getX()+25, temp.getY()+25));
		}
		en.add(temp);
		mainGame.player.addObserver(temp);
		return;
	}

	/**
	 * Sets the formation of the new enemies depending upon the ID, then forwards it to the factory of formations.
	 * @param i ID to be set to the factory of formations.
	 */
	public void setFormation(int i){
		FormationFactory form = new FormationFactory(mainGame, this);
		if(i==4) {addEnemy(4); return;}
		else {
			Formation k;
			k = form.setFormation(i);
			k.formationSet();
		}	
	}
	
	/**
	 * Sets the formation depending upon the ID and the position at which the formation is to be set.
	 * @param i ID number of the formation to be set.
	 * @param x X-coordinate at which the formation is to be set.
	 * @param y Y-coordinate at which the formation is to be set.
	 */
	public void setFormation(int i, double x, double y){
		FormationFactory form = new FormationFactory(mainGame, this);
		if(i==4) {addEnemy(4); return;}
		else {
			Formation k;
			k = form.setFormation(i);
			k.formationSet(x, y);
		}
	}
	
	/**
	 * Checks if the two enemies collide with one another, in such case we cannot set the second enemy there.
	 * @param another Checks if all the enemies within the list collide with the another piece.
	 * @return Returns true if it doesn't overlap with any enemy in the list and false if the "another" piece collides with a piece in the list.
	 */
	public boolean notOverlapping(GamePiece another){
		for(Foes e: en){
			if(((GamePiece) e).collidesWith((GamePiece)another)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Ensures that the Enemy class types in the list move towards the other end of the screen and shoot.
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(running){
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Random o = new Random();
			int chanceint = o.nextInt(50);
			final Iterator<Foes> iter = getList().iterator();
	    	while(iter.hasNext()){
	    		Foes e = iter.next();
	    		if(e instanceof Enemy){
	    			e.movement();
	    			if(e.getImage().isVisible() && ((Enemy)e).ammo.size()>0 ){
	    				if(chanceint>30) ((Enemy)e).fire(((Enemy)e).ammo.get(0));
	    			}
	    		}
	    	}
		}
	}
	
}
