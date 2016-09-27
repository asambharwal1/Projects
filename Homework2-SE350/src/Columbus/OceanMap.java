package Columbus;

import java.awt.Point;
import java.util.Random;

public class OceanMap {
	int islandCount;
	int dimension;
	boolean[][] grid = new boolean[25][25];
	Point currentPosition;
	Point piratePosition1;
	Point piratePosition2;

	public OceanMap(int dimension, int islandCount){
		this.dimension = dimension;
		this.islandCount = islandCount;
		placeIslands(); //Placing islands before anything else. So as to keep from placing ships on islands.
		currentPosition = placeShip(); //Placing our ship
		piratePosition1 = placeShip(); //Placing Pirate Ship 1
		piratePosition2 = placeShip(); //Placing Pirate Ship 2
	}
	
	public void placeIslands(){
		Random o = new Random();
		int x = 0; int y=0;
		int icount=0;
		while(icount!=islandCount){
			x = o.nextInt(dimension); //Random x coordinate
			y = o.nextInt(dimension); //Random y coordinate
			if(grid[y][x]==false){
				grid[y][x] = true; // making sure there are exactly the number of islands and not to overlap an island
				icount++;			//adding to count when a place on the grid has been set to true
			}
		}
	}
	
	public Point placeShip(){
		Random o = new Random();
		int x = 0; int y=0;
		boolean isPlaced = false;
		while(!isPlaced){
			x = o.nextInt(dimension); //Random x coordinate
			y = o.nextInt(dimension); //Random y coordinate
			if(grid[y][x] != true) isPlaced = true; // making sure we or the pirate ship doesn't start off on an island
		}
		return new Point(x,y);
	}

	public boolean[][] getMap() {
		// TODO Auto-generated method stub
		return grid;
	}
	
	/*public void printMap(){
		//Only for checking 
	for(int x=0; x<dimension; x++){
		for(int y=0; y<dimension; y++){
			if(grid[x][y]==true) System.out.print(" x ");
			else System.out.print(" o ");
		}
		System.out.println();
		}
	}*/

}
