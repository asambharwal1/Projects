package Battlefield;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class battleField {
	public String[][] grid = new String[25][25];
	private String[] gameInstance = new String[3];
	private int currentGameInstance = 0;
	private String file = "src//input.txt";
	private String outputfile = "src//output.txt";
	private SearchStrategy search = new randomSearch();
	
	public void readInputFile() throws IOException{
		readShipsFile(file);
	}
	
	public void readShipsFile(String fileName) throws IOException{
		FileReader inputFile = new FileReader(fileName);
		BufferedReader reader = new BufferedReader(inputFile);
		String line; int count=0;
		while((line = reader.readLine())!=null){
			String[] k = line.split("[()]+");
			String nline = "";
			for(int i=1; i<k.length; i++){
				nline += k[i]+" ";
			}
			gameInstance[count] = nline;
			count++;
		}
		
	}
	public void nextGame(){
		grid = new String[25][25];
		currentGameInstance++;
	}
	
	public void insertShips(){
		String[] location = gameInstance[currentGameInstance].split("\\s+");
		for(int i=0; i<location.length;i++){
			String[] coor = location[i].split(",");
			grid[Integer.parseInt(coor[0])][Integer.parseInt(coor[1])]="x";
		}
	}
	
	public void map(){
		for(int i=0; i<25; i++){
			for(int j=0; j<25; j++){
				if(grid[i][j]==null)System.out.print("o ");
				else System.out.print(grid[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public void setStrategy(SearchStrategy strategy){
		search = strategy;
	}
	
	public String search(){
		search.search(grid);
		return "Strategy: "+search.strategyUsed()+"\nNumber of cells searched: "+search.cellCount()+"\n"+search.location()+"\n";
	}
	
	public void writeToOutput() throws IOException{
		String output="";
		int actualGameInstance = currentGameInstance;
		currentGameInstance=0;
		battleField k = new battleField();
		k.readInputFile();
		k.insertShips();
		//System.out.println(gameInstance.length);
		while((k.currentGameInstance+1)!=(k.gameInstance.length)){
			//System.out.println(k.currentGameInstance+"\t"+k.gameInstance.length);
			output+="Game "+(k.currentGameInstance+1)+":\n";
			k.setStrategy(new horizontalSweep());
		    output+=k.search();
		    k.setStrategy(new randomSearch());
		    output+=k.search();
		    k.setStrategy(new strategicSearch());
		    output+=k.search()+"\n";
		    k.nextGame();
		    k.insertShips();
		}
		output+="Game "+(k.currentGameInstance+1)+":\n";
		k.setStrategy(new horizontalSweep());
	    output+=k.search();
	    k.setStrategy(new randomSearch());
	    output+=k.search();
	    k.setStrategy(new strategicSearch());
	    output+=k.search()+"\n";
	    FileWriter outputFile = new FileWriter("src//output.txt");
	    BufferedWriter writer = new BufferedWriter(outputFile);
	    System.out.println(output);
	    writer.write(output);
	    writer.close();
	}
	
	public static void main(String[] args) throws IOException{
		battleField k = new battleField();
		
		
		///////////////////////TESTING////////////////////////////////////
		/*k.grid = new String[25][25];
		k.readInputFile();
		k.insertShips();
		/*k.nextGame();
		k.insertShips();
		//k.nextGame();
		//k.insertShips();
	   // SearchStrategy se = new strategicSearch();
		k.setStrategy(new horizontalSweep());
	    //System.out.print(k.search());
	    k.setStrategy(new randomSearch());
	    //System.out.print(k.search());
	    k.setStrategy(new strategicSearch());
	    //System.out.println(k.search());*/
		/*System.out.println("Enter input file location:");
		Scanner reader = new Scanner(System.in);
		k.file = reader.next();
		System.out.println("Enter output file location:");
		Scanner reader1 = new Scanner(System.in);
		k.outputfile = reader1.next();*/
		k.writeToOutput();

	    
	}
}
