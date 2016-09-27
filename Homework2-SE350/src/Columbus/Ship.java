package Columbus;

import java.awt.Point;
import java.util.Observable;

import javafx.scene.image.ImageView;

public class Ship extends Observable{
	Point currentPosition;
	int dimension;
	ImageView prtImg;
	boolean[][] grid;
	
	public Ship(OceanMap ocean){
		this.currentPosition = ocean.currentPosition;
		this.dimension = ocean.dimension;
		grid = ocean.getMap();
	}
	
	public Point getLocation(){
		return currentPosition;
	}

	public void goWest() {
		// TODO Auto-generated method stub
	////Not to exceed boundary or go over islands
		if((currentPosition.x!=0)&&(grid[currentPosition.y][currentPosition.x-1]!=true)){
			currentPosition.x--;
			setChanged();
			notifyObservers(currentPosition); //Notifying the observers
		}
	}

	public void goEast() {
		// TODO Auto-generated method stub
		////Not to exceed boundary or go over islands
		if((currentPosition.x!=dimension-1)&&(grid[currentPosition.y][currentPosition.x+1]!=true)){
			currentPosition.x++;
			setChanged();
			notifyObservers(currentPosition); //Notifying the observers
		}
	}

	public void goSouth() {
		// TODO Auto-generated method stub
	////Not to exceed boundary or go over islands
		if((currentPosition.y!=dimension-1)&&(grid[currentPosition.y+1][currentPosition.x]!=true)){
			currentPosition.y++;
			setChanged();
			notifyObservers(currentPosition); //Notifying the observers
		}
	}

	public void goNorth() {
		// TODO Auto-generated method stub
	////Not to exceed boundary or go over islands
		if((currentPosition.y!=0)&&(grid[currentPosition.y-1][currentPosition.x]!=true)){
			currentPosition.y--;
			setChanged();
			notifyObservers(currentPosition); //Notifying the observers
		}
	}
	
}
