package spaceShooter2D;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Menu implements GameState{
	ObservableList<Node> obs;
	MainGame game;
	Button play;
	Button help;
	Button exit;
	Label title;
	GameMachine gameMach;
	
	/**
	 * The Menu State handles the initial beginning of the game such as the play button, about button and the exit button.
	 * @param mainGame Obtians the mainGame in order to obtain the pane's observable list.
	 * @param gameMachine Obtains the gameMachine in order to change the states when required.
	 */
	public Menu(MainGame mainGame, GameMachine gameMachine){
		this.obs = mainGame.pane.getChildren();
		this.game = mainGame;
		this.gameMach = gameMachine;
		handle();
	}
	
	/**
	 * Sets the main title of the game.
	 */
	public void setLabel(){
		title = new Label("     2-D Space Shooter\nBy: Aashish Sambharwal");
		setScale(title, 425, 200, 4, 4);
		title.setTextFill(Color.WHITE);
		obs.add(title);
	}
	
	/**
	 * Sets the buttons that are available to the user such as Play, About and Exit.
	 *
	 */
	public void setButtons(){
		play = new Button("PLAY");
    	setScale(play, 450, 350 , 3 ,3);
		play.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				obs.clear();
				gameMach.setGameState(gameMach.getPlayingState());
				gameMach.getPlayingState().handle();
			}
			
		});
		
		help = new Button("HELP");
		setScale(help, 450, 450 , 3 ,3);
		help.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Stage stage = new Stage();
				Pane pane1 = new Pane();
				Scene scene = new Scene(pane1, 250, 200);
				stage.setTitle("ABOUT THE GAME");
				Label About = new Label("ABOUT");
				setScale(About, 100, 20 , 4 ,4);
				
				Label Description = new Label("   Arrow keys for navigation of the ship\n\t     (SPACEBAR) Fire bullets! \n\t(CNTRL) Fire Homing Missiles! \n\t     (ESC) To Quit the Game.");
				setScale(Description, 10, 70 , 1 ,1);
				
				scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
					@Override
					public void handle(KeyEvent event) {
						// TODO Auto-generated method stub
						switch(event.getCode()){
						case ESCAPE:
							stage.close();
							break;
						default:
							break;
						}
					}
					
				});
				pane1.getChildren().addAll(About, Description);
				stage.setScene(scene);
				stage.show();
			}
			
		});
		
		exit = new Button("EXIT");
		setScale(exit, 450, 550, 3.25, 3);
		exit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		obs.addAll(play, help, exit);
	}
	
	/**
	 * Sets the buttons scale and position according to the users input.
	 * @param p The Button to be adjusted into position and scale.
	 * @param x The X coordinate of where the button is to be placed.
	 * @param y The Y coordinate of where the button is to be placed.
	 * @param scalex Defines the factor by which coordinates are scaled about the center of the object along the X axis of this Button.
	 * @param scaley Defines the factor by which coordinates are scaled about the center of the object along the Y axis of this Button.
	 */
	public void setScale(Button p, double x, double y, double scalex, double scaley){
		p.setLayoutX(x);
    	p.setLayoutY(y);
    	p.setScaleX(scalex);
    	p.setScaleY(scaley);
	}
	
	/**
	 * Sets the label scale and position according to the users input.
	 * @param p The label to be adjusted into position and scale.
	 * @param x The X coordinate of where the button is to be placed.
	 * @param y The Y coordinate of where the button is to be placed.
	 * @param scalex Defines the factor by which coordinates are scaled about the center of the object along the X axis of this label.
	 * @param scaley Defines the factor by which coordinates are scaled about the center of the object along the Y axis of this label.
	 */
	public void setScale(Label p, double x, double y, double scalex, double scaley){
		p.setLayoutX(x);
    	p.setLayoutY(y);
    	p.setScaleX(scalex);
    	p.setScaleY(scaley);
	}
	
	/**
	 * Sets the background of the current state.
	 */
	public void setBackground(){
		Image back = new Image("space.jpg");
    	ImageView background = new ImageView(back);
    	background.setX(0);
    	background.setY(0);
    	background.toBack();
    	obs.add(background);
	}
	
	/**
	 * Handles the state which sets up the buttons, title and background.
	 */
	@Override
	public void handle() {
		// TODO Auto-generated method stub
		obs.clear();
		setBackground();
		setLabel();
    	setButtons();
	}
	
}
