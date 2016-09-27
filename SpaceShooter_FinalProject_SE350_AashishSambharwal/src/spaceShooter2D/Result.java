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

public class Result implements GameState {
	
	MainGame game;
	ObservableList<Node> obs;
	private Button play;
	private Button help;
	private Button exit;
	private Label title;
	private GameMachine gameMach;
	
	/**
	 * Argument Constructor: Initializes the current ending state.
	 * @param game Required for the checking of whether the boss of the game has been defeated.
	 * @param gameMachine Required for changing the game state later on.
	 */
	public Result(MainGame game, GameMachine gameMachine){
		this.game = game;
		this.obs = game.pane.getChildren();
		this.gameMach = gameMachine;
	}
	
	/**
	 * Handles the current state by setting images, buttons and labels.
	 */
	@Override
	public void handle() {
		// TODO Auto-generated method stub
		obs.clear();
		setBackground();
		setLabel();
		setButtons();
	}
	
	/**
	 * Sets the label of the Result state. Either the player loses or wins.
	 */
	public void setLabel(){
		if(!game.player.bossIsDefeated()){ //System.out.println(obs.size());
			title = new Label("Your Score was:  "+game.PlayerScore+"\n\tYou lost!");title.setTextFill(Color.RED);}
		else {title = new Label("Nice job! You won!!");title.setTextFill(Color.BLUE);}
		setScale(title, 425, 200, 4, 4);
		obs.add(title);
	}
	
	/**
	 * Sets the background of the Result state.
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
	 * Sets and defines the buttons that are used in the result state.
	 */
	public void setButtons(){
		play = new Button("RETRY");
    	setScale(play, 450, 350 , 3 ,3);
		play.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				game.pane.getChildren().clear();
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
	 * Sets the labels position and scale according to the users input.
	 * @param p The Label to be adjusted.
	 * @param x The X coordinate of where the label is to be placed.
	 * @param y The Y coordinate of where the label is to be placed.
	 * @param scalex Defines the factor by which coordinates are scaled about the center of the object along the X axis of this Label.
	 * @param scaley Defines the factor by which coordinates are scaled about the center of the object along the Y axis of this Label.
	 */
	public void setScale(Label p, double x, double y, double scalex, double scaley){
		p.setLayoutX(x);
    	p.setLayoutY(y);
    	p.setScaleX(scalex);
    	p.setScaleY(scaley);
	}
}
