package FlowerGarden;


import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GardenLayout extends Application{
	
	private Point2D clickPoint;
	List<Shape> shapes = new ArrayList<Shape>();
	List<Shape> menu = new ArrayList<Shape>();
	Shape currentShape = null;
	private boolean inDragMode=false;
	private AnchorPane root;
	private Point2D oldPoint=null;
	FlowerBed drawing;
	Rectangle drawingPlace;
	private Shape crrntShp;
    
	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>(){

		@Override
		public void handle(MouseEvent mouseEvent) {
			// TODO Auto-generated method stub
			clickPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
			String eventName = mouseEvent.getEventType().getName();
			
			if(!inDragMode){
				currentShape = getCurrentShape(clickPoint);
				crrntShp = getCurrentMenuShape(clickPoint);
			}
			
			switch(eventName){
			case "MOUSE_PRESSED":
				
				/*For adding any shape in the shapes array so we can
				 *move and drag the shape */
				if(crrntShp!=null){
					shapes.add(crrntShp);
				}
				break;
			
			case "MOUSE_RELEASED":
				
				/*For adding the leaf node (Flowers) to the composite Node (FlowerBed)
				 *and removing the leaf node */
				if(currentShape!=null && currentShape instanceof Flowers && drawingPlace.contains(clickPoint)){
					for(Shape container: shapes){
						if(container instanceof FlowerBed && container.ContainsPoint(clickPoint)){
							((Flowers) currentShape).getCircle().toFront();
							if(!((FlowerBed) container).inShape.contains(currentShape))((FlowerBed) container).addChild(currentShape);
							break;
						}								
						if(container instanceof FlowerBed && ((FlowerBed) container).inShape.contains(currentShape)){
							((FlowerBed) container).removeChild(currentShape);
							break;
						}
					}
				}
				
				/*For adding the leaf node (Bird) to the composite Node (Tree)
				 *and removing the leaf node */
				if(currentShape!=null && currentShape instanceof Bird && drawingPlace.contains(clickPoint)){
					for(Shape container: shapes){
						if(container instanceof Tree && container.ContainsPoint(clickPoint)){
							((Bird) currentShape).getBirdImage().toFront();
							if(!((Tree) container).innerShapes.contains(currentShape))((Tree) container).addChild(currentShape);
							break;
						}
						if(container instanceof Tree && ((Tree) container).innerShapes.contains(currentShape)){
							((Tree) container).removeChild(currentShape);
							break;
						}
					}
				}
				
				/*This if statement is to handle the removal of children when
				 *  the child is dragged outside the drawing area for both cases
				 *  Tree(Composite Node) and Bird(Leaf Node) as well as 
				 *  FlowerBed(Composite Node) and Flowers (Leaf Node)*/
				if(currentShape!=null && currentShape instanceof Bird && !drawingPlace.contains(clickPoint)){
					for(Shape container: shapes){
						if(container instanceof Tree && ((Tree) container).innerShapes.contains(currentShape)){
							((Tree) container).removeChild(currentShape);
						}
					}
					shapes.remove(currentShape);
				}
				
				if(currentShape!=null && currentShape instanceof Flowers && !drawingPlace.contains(clickPoint)){
					for(Shape container: shapes){
						if(container instanceof FlowerBed && ((FlowerBed) container).inShape.contains(currentShape)){
							((FlowerBed) container).removeChild(currentShape);
						}
					}
					shapes.remove(currentShape);
				}
				
				
				/*Once a shape enters the drawing place and is placed
				 *within the drawing place all the shapes get a new instance
				 *so there can be more placements */
				if(drawingPlace.contains(clickPoint)){
					setMoveables();
				}
				
				/*Handling for when a composite node leaves the drawing place, 
				 * in which case we remove all the leaf nodes and set them back
				 *  to where they were in the menu*/
				if(!drawingPlace.contains(clickPoint)&&currentShape!=null){
					if(currentShape instanceof FlowerBed&&((FlowerBed)currentShape).inShape.size()>0){
						for(Shape shape: ((FlowerBed)currentShape).inShape){
							shapes.remove(shape);
							shape.setPosition(shape.getPlacement());
						}
						((FlowerBed)currentShape).resetChildren();
					}
					
					if(currentShape instanceof Tree &&((Tree)currentShape).innerShapes.size()!=0){
						for(Shape shape: ((Tree)currentShape).innerShapes){
							shapes.remove(shape);
							shape.setPosition(shape.getPlacement());
						}
						((Tree)currentShape).resetChildren();
					}
					
					shapes.remove(currentShape);
					currentShape.setPosition(currentShape.getPlacement());
				}
				
				inDragMode =false;
				break;
				
			case "MOUSE_DRAGGED":
				inDragMode = true;
				if(currentShape!=null && oldPoint!=null){
					currentShape.moveRelative(clickPoint.getX()-oldPoint.getX(), clickPoint.getY()-oldPoint.getY());
					if (currentShape instanceof Flowers) ((Flowers) currentShape).getCircle().toFront();
				}
				break;
			}
			oldPoint = clickPoint;
		}
		
	};
	
	@Override
	public void start(Stage display) throws Exception {
		// TODO Auto-generated method stub
		root = new AnchorPane();
		root.setStyle("-fx-background: #90EE90;");
		Scene scene = new Scene(root, 1000, 800);
		display.setTitle("Create A Garden");
		setShapes(); //Setting the overall shape of the menu and the drawing area
		setMoveables(); //Setting the moveable shapes inside the menu
		
		scene.setOnMouseClicked(mouseHandler);
	    scene.setOnMouseDragged(mouseHandler);
	    scene.setOnMouseEntered(mouseHandler);
	    scene.setOnMouseExited(mouseHandler);
	    scene.setOnMouseMoved(mouseHandler);
	    scene.setOnMousePressed(mouseHandler);
	    scene.setOnMouseReleased(mouseHandler );


	    Label Title = new Label("Create A Garden");				//Title
	    Title.setFont(new Font(40));
	    AnchorPane.setRightAnchor(Title, 350.0); 				////Centering the Title
	    Label mylabel = new Label("Instructions:\tClick on a shape on the left and then create a new instance of it in the garden");
	    
	    Button exit = new Button();
	    exit.setText("Exit Game");
	    exit.setStyle("-fx-background-color: #FF0000;");
	    exit.setTextFill(Color.WHITE);							//Setting text white for visibility
	    AnchorPane.setBottomAnchor(exit, 10.0);
	    AnchorPane.setRightAnchor(exit, 20.0);
	    exit.setOnAction(new EventHandler<ActionEvent>(){ 		//Handling exiting when button is clicked or pressed
	    	
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
	    	
	    });
	    
	    Button reset = new Button();
	    reset.setText("Reset Game");
	    reset.setStyle("-fx-background-color: #FF0000;");		//Button color is set to Red
	    reset.setTextFill(Color.WHITE);							//Setting text white for visibility
	    AnchorPane.setBottomAnchor(reset, 10.0);
	    AnchorPane.setRightAnchor(reset, 100.0);
	    reset.setOnAction(new EventHandler<ActionEvent>(){ 		//Handling exiting when button is clicked or pressed
	    	
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				for(Shape shape: shapes){
					if(shape instanceof FlowerBed){				//If a composite node (FlowerBed) resetChildren(), which removes the leaf nodes inside
						((FlowerBed) shape).resetChildren();
					}
					if(shape instanceof Tree){					//If a composite node (Tree) resetChildren(), which removes the leaf nodes inside
						((Tree) shape).resetChildren();
					}
					shape.setPosition(shape.getPlacement());	//Placing them in their original location
				}
				shapes = new ArrayList<Shape>();				//Resetting the shapes list
				drawing.resetChildren();
			}
	    	
	    });
	    
	    AnchorPane.setRightAnchor(mylabel, 80.0);
	    AnchorPane.setBottomAnchor(mylabel, 700.0);
	    root.getChildren().addAll(mylabel, reset, exit, Title);
	    display.setScene(scene);
		display.show();
	}
	
	protected Shape getCurrentShape(Point2D point) { //For finding shapes
		// TODO Auto-generated method stub
		Shape currShape = null;
		for(Shape shapes: shapes){
			if(shapes.ContainsPoint(point)){
				currShape = shapes;
				break;
			}
		}
		return currShape;
	}
	
	protected Shape getCurrentMenuShape(Point2D point) { //For the shapes in menu
		// TODO Auto-generated method stub
		Shape currShape = null;
		for(Shape shapes: menu){
			if(shapes.ContainsPoint(point)){
				currShape = shapes;
				break;
			}
		}
		return currShape;
	}
	
	public void setShapes(){
		Rectangle menu = new Rectangle(); ///MENU
	    menu.setHeight(700);
		menu.setWidth(300);
		menu.setX(25);
		menu.setY(60);
		menu.setFill(Color.WHITE);
		menu.setStroke(Color.BLACK);
		menu.setStrokeWidth(1);
		menu.toBack();
		//rect.
		FlowerBed drawing = new FlowerBed(new Point2D(355, 110), 650, 625); //Giant Composite Node Method w/Reset Button
		/* For normal rectangle method
		 * //Rectangle drawing = new Rectangle(); ///Drawing Area
	    	//drawing.setHeight(650);
			//drawing.setWidth(425);
			//drawing.setX(355);
			//drawing.setY(110);*/
		
		drawing.rect.setFill(Color.LAWNGREEN);
		drawing.rect.setStroke(Color.BLACK);
		drawing.rect.setStrokeWidth(1);
		this.drawing = drawing;
		drawingPlace = drawing.rect;					//For denoting the shape which is to be the drawing Area
		
		
		Label FlowerBed = new Label("Flower Bed");			//LABEL FOR FLOWER BEDS
		AnchorPane.setTopAnchor(FlowerBed, (double) 240);	
		AnchorPane.setLeftAnchor(FlowerBed, 40.0);
		
		Label Vegetables = new Label("Vegetables");			//LABEL FOR VEGETABLES
		AnchorPane.setTopAnchor(Vegetables, (double) 160);
		AnchorPane.setLeftAnchor(Vegetables, 40.0);
		
		Label flower = new Label("Flowers");				//LABEL FOR FLOWERS
		AnchorPane.setTopAnchor(flower, (double) 80);
		AnchorPane.setLeftAnchor(flower, 40.0);
		
		Label tree = new Label("Tree");						//LABEL FOR TREES
		AnchorPane.setTopAnchor(tree, (double) 625);
		AnchorPane.setLeftAnchor(tree, 185.0);
		
		Label bird = new Label("Bird");						//LABEL FOR BIRDS
		AnchorPane.setTopAnchor(bird, (double) 625);
		AnchorPane.setLeftAnchor(bird, 70.0);
		
		
		
		root.getChildren().addAll(drawing.getRectangle(), menu, FlowerBed, Vegetables, flower, bird, tree);
	}
	
	public void setMoveables(){
		Color[] flowers = {Color.YELLOW, Color.ORANGE, Color.RED, Color.PLUM, Color.DARKORANGE};
		for(int i=0; i<flowers.length; i++){ //Since there are 5 types of flowers, we loop 5 times
			Point2D set = new Point2D(50+(i*33), 120);	//Keeping distance between the flowers
			Flowers circle = new Flowers(set, 10);		//
			circle.setFill(flowers[i]);					//Setting the different colors based on the Color array called flowers
			shapes.add(circle);							//Adding to the shapes
			menu.add(circle);							//Adding to the menu
			root.getChildren().add(circle.getCircle());	//Making it observable
		}
		
		Color[] vegetables = {Color.MEDIUMSEAGREEN, Color.PALEGREEN, Color.ALICEBLUE,  Color.GREENYELLOW};
		for(int i=0; i<vegetables.length; i++){ //Since there are 4 types of Vegetables, we loop 5 times
			Point2D set = new Point2D(50+(i*33), 200);	//Keeping the distance between vegetables
			Flowers circle = new Flowers(set, 10);		//
			circle.setFill(vegetables[i]);				//Setting the different colors based on the Color array called vegetables
			shapes.add(circle);							//Adding to the shapes
			menu.add(circle);							//Adding to the menu
			root.getChildren().add(circle.getCircle());	//Making it observable
		}
		
		Point2D fb = new Point2D(40, 280);
		FlowerBed rect2 = new FlowerBed(fb, 300, 200);
		menu.add(rect2);								//Adding it to the menu
		shapes.add(rect2);								//Adding it to shapes
		
		Point2D treelocation = new Point2D(200, 700);
		Tree tree = new Tree(treelocation, 50);
		menu.add(tree);									//Adding it to the menu
		shapes.add(tree);								//Adding it to the shapes
		
		Point2D birdlocation = new Point2D(1, 13.5);
		Bird bird = new Bird(birdlocation, 50);
		menu.add(bird);									//Adding it to the menu
		shapes.add(bird);								//Adding it to the shapes
		
		root.getChildren().addAll(rect2.getRectangle(), tree.getCircle(), bird.getBirdImage()); //Making all the things outside the loops observable
	}
	
	public static void main(String[] args){
		launch(args);
	}
}
