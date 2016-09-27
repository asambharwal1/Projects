package Columbus;

import java.awt.Point;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class OceanExplorer extends Application{
	int dimension=25; 
	int islandCount=10;
	int scale = 25;
	ImageView shipImg;
	ImageView prtImg;
	EnemyShip p1;
	EnemyShip p2;
	OceanMap oceanMap;
	Pane root;
	Scene scene;
	Ship ship;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage oceanStage) throws Exception {
		// TODO Auto-generated method stub
		//Random number generator for number of islands
		Random o = new Random();
		islandCount = o.nextInt(10);
		
		oceanMap = new OceanMap(dimension, islandCount);
		
		//Anchor Pane favorable for current Assignment
		root = new AnchorPane();
		drawMap();
		
		//Loading and placing our Ship
		ship = new Ship(oceanMap);
		loadShipImage();
		
		//Loading Pirate Image and placing image
		loadprtImage(oceanMap.piratePosition1);
		p1 = new EnemyShip(oceanMap, oceanMap.piratePosition1, prtImg);
		
		//Loading Pirate Image and placing image
		loadprtImage(oceanMap.piratePosition2);
		p2 = new EnemyShip(oceanMap, oceanMap.piratePosition2, prtImg);
		
		//Making the pirateships able to view changes in the position of our ship
		ship.addObserver(p1);
		ship.addObserver(p2);
		
		//Button for resetting the game
		Button btn = new Button();
		btn.setText("Retry!");
		btn.setScaleX(2.5);
		btn.setScaleY(2.5);
		AnchorPane.setBottomAnchor(btn, 25.0);
		AnchorPane.setRightAnchor(btn, 290.0);
		btn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				//RESET EVERYTHING
				Random o = new Random();
				islandCount = o.nextInt(10);
				
				oceanMap = new OceanMap(dimension, islandCount);

				drawMap();
				
				ship = new Ship(oceanMap);
				loadShipImage();
				
				loadprtImage(oceanMap.piratePosition1);
				p1 = new EnemyShip(oceanMap, oceanMap.piratePosition1, prtImg);
				
				loadprtImage(oceanMap.piratePosition2);
				p2 = new EnemyShip(oceanMap, oceanMap.piratePosition2, prtImg);
				
				ship.addObserver(p1);
				ship.addObserver(p2);
			}
			
		});
		root.getChildren().add(btn);
		scene = new Scene(root, 625,700);
		oceanStage.setTitle("Christopher Columbus Sails the Ocean");
		oceanStage.setScene(scene);
		oceanStage.show();
		startSailing();
	}
	
	
	private void loadShipImage() { //Placing the Ship Image
		// TODO Auto-generated method stub
		Image shipImage = new Image("File:src\\ship.png", scale, scale, true, true);
		shipImg = new ImageView(shipImage);
		shipImg.setX(ship.getLocation().x*scale);
		shipImg.setY(ship.getLocation().y*scale);
		root.getChildren().add(shipImg);
	}
	
	private void loadislandImage(int x, int y) { //Placing the Island Image on the coordinates x and y
		// TODO Auto-generated method stub
		Image islandImage = new Image("File:src\\island.jpg", scale, scale, true, true);
		ImageView islandImg = new ImageView(islandImage);
		islandImg.setX(x*scale);
		islandImg.setY(y*scale);
		root.getChildren().add(islandImg);
	}
	
	private void loadprtImage(Point x) { //Placing the PirateShip Image
		// TODO Auto-generated method stub
		Image shipImage = new Image("File:src\\pirateShip.png", scale, scale, true, true);
		prtImg = new ImageView(shipImage);
		prtImg.setX(x.x*scale);
		prtImg.setY(x.y*scale);
		root.getChildren().add(prtImg);
	}

	private void startSailing() {
		// TODO Auto-generated method stub
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				switch(event.getCode()){
				case RIGHT:
					ship.goEast();
					break;
				case LEFT:
					ship.goWest();
					break;
				case DOWN:
					ship.goSouth();
					break;
				case UP:
					ship.goNorth();
					break;
				default:
					break;
				}
				shipImg.setX(ship.getLocation().x*scale); //Moving Ship image according to command
				shipImg.setY(ship.getLocation().y*scale); //
				movePirate(p1); movePirate(p2); // moving pirates when the x and/or y coordinates are changed.
			}
		});
	}
	
	private void movePirate(EnemyShip p){ //To make moveability of pirate ships easier rather than repeating code
		p.getImageLocation().setX(p.getShipLocation().x*scale);
		p.getImageLocation().setY(p.getShipLocation().y*scale);
	}

	private void drawMap() { //creating the map, oceans and islands.
		// TODO Auto-generated method stub
		boolean[][] grid = oceanMap.getMap();
		for(int x=0; x<dimension; x++){
			for(int y=0; y<dimension; y++){
				Rectangle rect = new Rectangle(x*scale, y*scale, scale, scale);
				rect.setStroke(Color.BLACK);
				if(grid[y][x]==true) {loadislandImage(x,y); //Island Placements
					continue;
				}
				else rect.setFill(Color.TURQUOISE); //Ocean Color
				root.getChildren().add(rect);
			}
		}
	}
	
	

}
