package Battlefield;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class strategicSearch implements SearchStrategy{
	int cellCount=0;
	Set<String> coordinatesX = new LinkedHashSet<String>();
	Integer[][] submarine = new Integer[3][2];
	Integer[][] carrier = new Integer[5][2];
 	boolean[][] marked = new boolean[25][25];
	@Override
	public void search(String[][] grid) {
		// TODO Auto-generated method stub
		
		////////////////RANDOM POINT GENERATOR + EFFICIENT SEARCH WHEN HIT //Sometimes efficient/////////////////////////
		/*Random k = new Random();int count = 0;
		while(coordinatesX.size()!=8){
			int i = k.nextInt(25); int j = k.nextInt(25);
			if(grid[i][j]=="x"){
					cellCount++;
					int iupperbound=i; int ilowerbound=i; int jupperbound=j; int jlowerbound=j;
					//FOR NEAR-CORNER PLACEMENTS OF THE BATTLESHIPS 
					////Near-corner Placements Horizontally
					if(ilowerbound<=3){ilowerbound=0; iupperbound+=4;}
					else if(iupperbound>=20){iupperbound=24; ilowerbound-=4;}
					else {ilowerbound-=4; iupperbound+=4;}
					////Near-corner Placements Vertically
					if(jlowerbound<=3){jlowerbound=0; jupperbound+=4;}
					else if(jupperbound>=20){jupperbound=24; jlowerbound-=4;}
					else {jlowerbound-=4; jupperbound+=4;}
					//
					for(int o=ilowerbound; o<=iupperbound; o++){
						cellCount++;
						if(grid[o][j]=="x"){coordinatesX.add("("+o+","+j+")");}
						//if(((o+1>=24)||((o+1<24)&&grid[o+1][j]==null))&&xcount==3)break;
						//if(((o+1>=24)||((o+1<24)&&grid[o+1][j]==null))&&xcount==5)break;
					}
					for(int o=jlowerbound; o<=jupperbound; o++){
						cellCount++;
						//System.out.println(j+"\t"+jlowerbound+"\t"+jupperbound);
						if(grid[i][o]=="x"){coordinatesX.add("("+i+","+o+")");}
					}
			}
			else cellCount++;
			
		}*/
		
		///Breadth-First Search
		bfs1(grid, 13,13);
		//int count=0;
		//System.out.println(coordinatesX.size());
		
	}
	//////////////////////////////BREADTH FIRST SEARCH + EFFICIENT SEARCH WHEN HIT/////////////////////////////////
	private void bfs1(String[][] grid, int a, int b) {
		 Queue<Integer[]> q = new Queue<Integer[]>();
		 Integer[] k = {a,b};
		 q.enqueue(k);
		 marked[k[0]][k[1]] = true;
		 cellCount++;
		 while (!q.isEmpty()) {
			 Integer[] v = q.dequeue();
			 Integer[][] k2 = {{v[0]+1,v[1]},{v[0]-1,v[1]},{v[0],v[1]+1},{v[0],v[1]-1}};
			 for(int i=0; i<k2.length; i++){
						if(((k2[i][0]>=0)&&(k2[i][0]<25))&&((k2[i][1]>=0)&&(k2[i][1]<25))){ 	//Making sure not to go out of bounds
							if(coordinatesX.size()==8) return;									//if the list is full we can stop searching unnecesarily 
							if (!marked[k2[i][0]][k2[i][1]]) {  								//if the point isn't marked
								Integer[] k1 = {k2[i][0], k2[i][1]};
								//System.out.println(k1[0]+"\t"+k1[1]+"\t"+grid[k1[0]][k1[1]]);
								
								if(grid[k1[0]][k1[1]]=="x"){
									int count=0;
									//System.out.println(k1[0]+"\t"+k1[1]+"\t"+grid[k1[0]][k1[1]]);
									int iupperbound=k1[0]; int ilowerbound=k1[0]; int jupperbound=k1[1]; int jlowerbound=k1[1];
									//FOR NEAR-CORNER PLACEMENTS OF THE BATTLESHIPS 
									////Near-corner Placements Horizontally
									if(ilowerbound<=3){ilowerbound=0; iupperbound+=4;}
									else if(iupperbound>=20){iupperbound=24; ilowerbound-=4;}
									else {ilowerbound-=4; iupperbound+=4;}
									////Near-corner Placements Vertically
									if(jlowerbound<=3){jlowerbound=0; jupperbound+=4;}
									else if(jupperbound>=20){jupperbound=24; jlowerbound-=4;}
									else {jlowerbound-=4; jupperbound+=4;}
									//
									for(int o=ilowerbound; o<=iupperbound; o++){
										if(!marked[o][k1[1]]){marked[o][k1[1]]=true; cellCount++;}
										if(grid[o][k1[1]]=="x"){coordinatesX.add("("+o+","+k1[1]+")");count++;}
										//if(((o+1>=24)||((o+1<24)&&grid[o+1][j]==null))&&xcount==3)break;
										//if(((o+1>=24)||((o+1<24)&&grid[o+1][j]==null))&&xcount==5)break;
									}
									for(int o=jlowerbound; o<=jupperbound; o++){
										if(!marked[k1[0]][o]){marked[k1[0]][o]=true; cellCount++;}
										//marked[k1[0]][o]=true;
										//System.out.println(j+"\t"+jlowerbound+"\t"+jupperbound);
										if(grid[k1[0]][o]=="x"){coordinatesX.add("("+k1[0]+","+o+")"); count++;}
									}
									
									//System.out.println(count);
									
									//////////////////////////////////////////CHECKING IF SUBMARINE OR CARRIER
									if(count-1==3){
										//System.out.println("Gets here");
										for(int j=0; j<3; j++){
											String[] curr = ((String) coordinatesX.toArray()[coordinatesX.size()-1-j]).replace("(","").replace(")", "").split(",");
											Integer[] curint = {Integer.parseInt(curr[0]), Integer.parseInt(curr[1])};
											submarine[j] = curint;//coordinatesX.toArray()[coordinatesX.size()-1-j];
										}
									}
									if(count-1==5){
										//System.out.println("Gets here");
										for(int j=0; j<5; j++){
											//System.out.println( coordinatesX.toArray()[coordinatesX.size()-1-j]);
											String[] curr = ((String) coordinatesX.toArray()[coordinatesX.size()-1-j]).replace("(","").replace(")", "").split(",");
											Integer[] curint = {Integer.parseInt(curr[0]), Integer.parseInt(curr[1])};
											carrier[j] = curint;
										}
									}
								}
								//System.out.println(count);
								marked[k1[0]][k1[1]] = true;
								q.enqueue(k1);
								cellCount++;
							}
						}
			 		}
		 		}
			}
	
	
	@Override
	public int cellCount() {
		// TODO Auto-generated method stub
		return cellCount;
	}

	@Override
	public String location() {
		// TODO Auto-generated method stub
		
		//////////////////// CARRIER MAX AND MIN//////////////
		Integer[] max = new Integer[2];; Integer[] min = new Integer[2];
		max = carrier[0]; min = carrier[0];
		for(int i=0; i<carrier.length; i++){
			if(carrier[0][0]==carrier[1][0]){
				if(carrier[i][1]>max[1]) max = carrier[i];
				if(carrier[i][1]<min[1]) min = carrier[i]; 
			}
			else{
				
				if(carrier[i][0]>max[0]) max = carrier[i];
				if(carrier[i][0]<min[0]) min = carrier[i];
			}
		}
		
		///////////////////////SUBMARINE MAX AND MIN//////////
		Integer[] max1 = new Integer[2];; Integer[] min1 = new Integer[2];
		max1 = submarine[0]; min1 = submarine[0];
		for(int i=0; i<submarine.length; i++){
			if(submarine[0][0]==submarine[1][0]){
				if(submarine[i][1]>max1[1]) max1 = submarine[i];
				if(submarine[i][1]<min1[1]) min1 = submarine[i]; 
			}
			else{
				if(submarine[i][0]>max1[0]) max1 = submarine[i];
				if(submarine[i][0]<min1[0]) min1 = submarine[i];
			}
		}
		
		//////////////////////FINAL ANSWER////////////////
		return "Carrier found: "+"("+min[0]+","+min[1]+")"+" to "+"("+max[0]+","+max[1]+")"+"\tSubmarine found: "+"("+min1[0]+","+min1[1]+")"+" to "+"("+max1[0]+","+max1[1]+")";
	}

	@Override
	public String strategyUsed() {
		// TODO Auto-generated method stub
		return "Strategic Search";
	}

}
