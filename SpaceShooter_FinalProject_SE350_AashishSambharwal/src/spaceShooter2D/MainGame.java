package spaceShooter2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.event.EventHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainGame extends Application implements GameState{
	final double minX = 0;
    final double maxX = 900; 
    final double minY = 0;
    final double maxY = 700;
    final double playerHSpeed = 500;
    final double playerVSpeed = 500;
    public Pane pane;
    List<KeyCode> keys = new ArrayList<KeyCode>();
    public Enemies en;
    Scene scene;
    ImageView playerImg;
    public Player player;
    Bullet bullet;
    ImageView bulletImg;
	protected int ammo= Integer.MAX_VALUE;
	ImageView background;
	int count;
	Thread Enemy;

	Rectangle health;
	private ImageView base;
	int begsize;
	private Label score;
	int PlayerScore = 0, oldSize;
	private GameMachine gameMach;
	protected int ammo1 = 6;
	private boolean bossMode = false;
	private Rectangle Enemyhealth;
	private ImageView EnemyBase;
	private CollisionDetect k = new CollisionDetect();
	
	//Ammo Indication
	private ImageView missileImg;
	private Label missileSize;
	
	
	/**
	 * Overall setting of the game and enabling game machine which handles the states.
	 */
	@Override
    public void start(Stage primaryStage) {
		
    	pane = new Pane();
    	scene = new Scene(pane, 1000, 800);
    	
    	if(gameMach==null){
    		gameMach = new GameMachine(this);
    		gameMach.setGameState(gameMach.getMenuState());
    	}
        
    	primaryStage.setTitle("SE-350 Final Project - 2D Space Shooter by Aashish Sambharwal");
    	primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Loads the image by adding it to the pane and then returning the image so that it may be implemented in the bullet class.
     * @return Image of the Bullet that is shot by the player.
     */
    public ImageView loadBulletImage() {
		// TODO Auto-generated method stub
    	Image playerbull = new Image("bullet2.png");
        ImageView bulletImg = new ImageView(playerbull);
        bulletImg.toBack();
        pane.getChildren().add(bulletImg);
        return bulletImg;
    }
    
    /**
     * Loads the player image by adding it to the pane's observable list.
     */
	public void loadPlayerImage(){
    	Image player = new Image("player.png");
        playerImg = new ImageView(player);
        playerImg.toFront();
        pane.getChildren().add(playerImg);
    }
	
	/**
	 * Loads the enemy image by adding it to the pane's observable list and then returning the image for the enemy class. 
	 * @return Image of the Enemy.
	 */
	public ImageView loadEnemyImage(){
    	Image player = new Image("enemy1.png");
        ImageView eneImg = new ImageView(player);
        eneImg.toFront();
        pane.getChildren().add(eneImg);
        return eneImg;
    }
    
	/**
	 * Returns the current class.
	 * @return Returns the Main Game class.
	 */
	public MainGame getGame(){
		return this;
	}
	
	/**
	 * Handles the player movement, attacks, health, and enemies.
	 * @param playerVSpeed The desired vertical speed of the player.
	 * @param playerHSpeed The desired horizontal speed of the player.
	 * @param playerImg The Image of the the player so as to handle movment.
	 */
    private void startGame(double playerVSpeed, double playerHSpeed, ImageView playerImg) {
		// TODO Auto-generated method stub
    	scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@SuppressWarnings("deprecation")
			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				switch(event.getCode()){
				case CONTROL:
					if(ammo1 >0 && gameMach.curState == gameMach.Playing){
						player.addMissile(new Missiles(player.getX(), player.getY()+(player.getH()/2), en, pane.getChildren(), player));
						player.ammo1.get(0).homing=true;
	            		player.firemissile();
	            		ammo1--;
	            		}
					break;
				case SPACE:
					if(ammo>0){
						player.addBullet(new Bullet(loadBulletImage(), player.getX(), player.getY()+(player.getH()/2), en, pane.getChildren(), player));
	            		player.fire();
	            		ammo--;
	            		}
	            	break;
				case ESCAPE:
					Enemy.stop();
					System.exit(1);
					break;	
	            default:
	            	break;
				}
				if(!keys.contains(event.getCode()))keys.add(event.getCode());
			}
    	});
    	
    	scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				keys.remove(event.getCode());
			}
    	});
    	count = 0;
    	final LongProperty lastUpdatedTime = new SimpleLongProperty();
    	final AnimationTimer smoothAni = new AnimationTimer(){

			@SuppressWarnings("deprecation")
			@Override
			public void handle(long now) {
				
				if(player.Health<=0 || player.bossIsDefeated()){
					en.getList().clear();
		    		gameMach.setGameState(gameMach.getFinishedState());
		    		gameMach.getFinishedState().handle();
		    		stop();
		    		Enemy.stop();
		    		return;
		    	}
				tick();
	            background.setLayoutX(-count);
				// TODO Auto-generated method stub
				if (! keys.isEmpty() && lastUpdatedTime.get() > 0) {
					final double elapsedSeconds = (now - lastUpdatedTime.get()) / 1_000_000_000.0 ;
		            double deltaX = 0 ;
		            double deltaY = 0 ;
		            switch(keys.get(0)) {
		            case UP:
		                deltaY = -playerVSpeed * elapsedSeconds;
		                break ;
		            case DOWN: 
		                deltaY = playerVSpeed * elapsedSeconds ;
		                break ;
		            case LEFT:
		                deltaX = -playerHSpeed * elapsedSeconds ;
		                break ;
		            case RIGHT:
		                deltaX = playerHSpeed * elapsedSeconds ;
		                break;
		            default:
		                break ;
		            }
		            double oldX = playerImg.getTranslateX() ;
		            double oldY = playerImg.getTranslateY() ;
		            
		            player.moveRelative(oldX, deltaX, oldY, deltaY);
		         }
				
				lastUpdatedTime.set(now);
			}};
			smoothAni.start();
		}
    
    /**
     * Handles the missile ammo size, score count, health, boss mode and the spawning of enemies.
     */
	public void tick() {
		// TODO Auto-generated method stub
		k.collide(en, player, pane.getChildren());
		
    	if(PlayerScore<20)count++;
    	
    	if(count == 1800){
    		count = 0;
    		count++;
    	}
    	
    	//Player health and score.
    	pane.getChildren().removeAll(base, health, score);
    	
    	if(en.getList().size()<oldSize){
    		oldSize = en.getList().size();
    		PlayerScore += begsize-en.getList().size();
    		begsize--;
    	}
    	
    	score = new Label("Score:\t"+PlayerScore);
    	score.setLayoutX(800);
    	score.setLayoutY(10);
    	score.setTextFill(Color.WHITE);
    	score.setScaleX(4);
    	score.setScaleY(4);
    
    	
    	base = new ImageView(new Image("healthbar.png"));
        
    	pane.getChildren().remove(health);
    	
    	health = new Rectangle(80,42,player.Health*3, 10);
    	health.setFill(Color.GREEN);
    	
    	
    	pane.getChildren().addAll(base, health, score);
    	/////////////////////////////////////////////////////
    	
    	//AMMO INDICATION
    	pane.getChildren().removeAll(missileImg, missileSize);
    	
    	missileImg = new ImageView(new Image("jhr6ol.png"));
    	missileImg.setX(600);
    	missileImg.setY(10);
    	missileImg.setScaleX(2);
    	missileImg.setScaleY(2);
    	
    	missileSize = new Label(ammo1+"  x");
    	missileSize.setTextFill(Color.RED);
    	missileSize.setLayoutX(550);
    	missileSize.setLayoutY(10);
    	missileSize.setScaleX(3);
    	missileSize.setScaleY(3);
    	
    	pane.getChildren().addAll(missileImg, missileSize);
    	//////////////////////////////////////////////////////
    	
    	//Boss health
    	if(bossMode && !en.getList().isEmpty()){
    		if(en.getList().get(0) instanceof Boss){
    			pane.getChildren().removeAll(EnemyBase, Enemyhealth);
    			
    			EnemyBase = new ImageView(new Image("healthbar-empty.png"));
    			
    			EnemyBase.setY(700);
    			EnemyBase.setX(700);
    			
    			Enemyhealth = new Rectangle(702,702, ((Boss)en.getList().get(0)).Health*2.5, 20);
    			Enemyhealth.setFill(Color.GREEN);
    			
    			pane.getChildren().addAll(EnemyBase, Enemyhealth);
    			
    			if(((Boss)en.getList().get(0)).Health<=0){
    				player.setBossDefeatState(true);
    			}
    		}
    	}
    	////////////////////////////////////////////////////////
    	
    	if(en.checkFalseVisibilityofAll()){
    		en.getList().clear();
    	}
    	
    	if(!player.bossIsDefeated() && !bossMode && en.getList().size()==0){
    		Random o = new Random();
    		en.setFormation(o.nextInt(5));
    		this.begsize = en.getList().size();
    		oldSize = begsize;
    		if(PlayerScore>=20) bossMode = true;
	    }
    	
    	if(!player.bossIsDefeated() && bossMode && en.getList().size()==0){
    		en.addBoss();
    	}
	}
	
	/**
	 * Launches the GUI application of the game.
	 * @param args Required to launch the GUI.
	 */
	public static void main(String[] args) {
        launch(args);
    }

	/**
	 * Overrides the handle function for the current state, which initializes the important pieces and parts of the game.
	 */
	@Override
	public void handle() {
		// TODO Auto-generated method stub
		bossMode = false;
		
		ammo1 = 6;
		
		Image back = new Image("space1.jpg");
    	background = new ImageView(back);
    	background.setX(0);
    	background.setY(0);
    	background.toBack();
    	pane.getChildren().add(background);
    	
    	loadPlayerImage();
        player = new Player(playerImg, 10, 150);
        
        PlayerScore = 0;
        
        en = Enemies.getInstance();
        en.EnemyAdder(this, 6);
        oldSize = en.getList().size();
        begsize = en.getList().size();
        
        
        Enemy = new Thread(en);
        Enemy.start();
		
        startGame(playerVSpeed, playerHSpeed, playerImg);
	}
}