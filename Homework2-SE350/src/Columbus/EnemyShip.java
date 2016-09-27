package Columbus;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.scene.image.ImageView;

public class EnemyShip implements Observer{
	Point shipPosition;
	Point piratePosition;
	boolean[][] grid;
	int dimension;
	ImageView prtImg;
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		shipPosition = (Point) arg;
		movePirate();
	}
	
	private void movePirate() {
		// TODO Auto-generated method stub
		Random o = new Random();
		if(o.nextInt(2)==1){ /////Slow down process...
			////Not to exceed boundary or go over islands
			if(piratePosition.x-shipPosition.x<0){
				if(piratePosition.x!=dimension-1&&grid[piratePosition.y][piratePosition.x+1]==false)piratePosition.x++;}
			
			else{
				if(piratePosition.x!=0&&grid[piratePosition.y][piratePosition.x-1]==false)piratePosition.x--;}
			
			if(piratePosition.y-shipPosition.y<0){
				if(piratePosition.y!=dimension-1&&grid[piratePosition.y+1][piratePosition.x]==false)piratePosition.y++;}
			
			else{ 
				if(piratePosition.y!=0&&grid[piratePosition.y-1][piratePosition.x]==false)piratePosition.y--;}
			
			
		}
		/////System.out.println("PirateShip moved "+piratePosition.x+"\t"+piratePosition.y+"\n"); //DEBUGGING PURPOSES
	}

	public EnemyShip(OceanMap oceanMap, Point piratePosition, ImageView prtImg){
		this.dimension = oceanMap.dimension;
		this.grid=oceanMap.getMap();
		this.piratePosition = piratePosition;
		this.prtImg = prtImg;
	}
	

	public Point getShipLocation() {
		// TODO Auto-generated method stub
		return piratePosition;
	}
	
	public ImageView getImageLocation() {
		// TODO Auto-generated method stub
		return prtImg;
	}

	
}
