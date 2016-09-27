package Battlefield;

import java.util.Arrays;
import java.util.LinkedList;

public class horizontalSweep implements SearchStrategy{
	int cellCount=0;
	public String[] coordinatesX = new String[8];
	LinkedList<String> carrier = new LinkedList<String>();
	LinkedList<String> submarine = new LinkedList<String>();
	@Override
	public void search(String[][] grid) {
		// TODO Auto-generated method stub
		int count=0;
		for(int i=0; i<25; i++){
			for(int j=0; j<25; j++){
				cellCount++;
				if(grid[i][j]=="x"){coordinatesX[count]="("+i+","+j+")"; count++;}
				if(coordinatesX[7]!=null) break;
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
		/////////////SORTING INTO SUBMARINE AND CARRIER///////////////////
		Arrays.sort(coordinatesX);
		carrier.add(coordinatesX[0]); int count=0;
		for(int i=1; i<coordinatesX.length; i++){ 
			String[] currpos = coordinatesX[i].replace("(","").replace(")", "").split(",");
			String[] carr1 = carrier.get(count).replace("(","").replace(")", "").split(",");
			int carnum1= Integer.parseInt(carr1[0]); int carnum2 = Integer.parseInt(carr1[1]); int curnum1 = Integer.parseInt(currpos[0]); int curnum2 = Integer.parseInt(currpos[1]);
			if(((curnum1==carnum1)&&((carnum2+1)==curnum2))||(((carnum1+1)==curnum1)&&(curnum2==carnum2))){
				carrier.add("("+curnum1+","+curnum2+")"); count++;
				
			}
			else submarine.add("("+currpos[0]+","+currpos[1]+")");
			
		}
		if(submarine.size()!=3){
			LinkedList<String> temp =  submarine;
			submarine = carrier;
			carrier = temp;
		}
		
		//////CONVERSION//////
		Integer[][] carrier1 = convert(carrier);
		Integer[][] submarine1 = convert(submarine);
		
		////////////////////////////////CARRIER MIN AND MAX/////////////////////
		Integer[] max = new Integer[2];; Integer[] min = new Integer[2];
		max = carrier1[0]; min = carrier1[0];
		for(int i=0; i<carrier1.length; i++){
			if(carrier1[0][0]==carrier1[1][0]){
				if(carrier1[i][1]>max[1]) max = carrier1[i];
				if(carrier1[i][1]<min[1]) min = carrier1[i]; 
			}
			else{
				if(carrier1[i][0]>max[0]) max = carrier1[i];
				if(carrier1[i][0]<min[0]) min = carrier1[i];
			}
		}
		
		///////////////////////SUBMARINE MIN AND MAX/////////////////////////
		Integer[] max1 = new Integer[2];; Integer[] min1 = new Integer[2];
		max1 = submarine1[0]; min1 = submarine1[0];
		for(int i=0; i<submarine1.length; i++){
			if(submarine1[0][0]==submarine1[1][0]){
				if(submarine1[i][1]>max1[1]) max1 = submarine1[i];
				if(submarine1[i][1]<min1[1]) min1 = submarine1[i]; 
			}
			else{
				if(submarine1[i][0]>max1[0]) max1 = submarine1[i];
				if(submarine1[i][0]<min1[0]) min1 = submarine1[i];
			}
		}
		
		////////////////////// FINAL ANSWER////////////////////////////
		return "Carrier found: "+"("+min[0]+","+min[1]+")"+" to "+"("+max[0]+","+max[1]+")"+"\tSubmarine found: "+"("+min1[0]+","+min1[1]+")"+" to "+"("+max1[0]+","+max1[1]+")";

	}

	private Integer[][] convert(LinkedList<String> a) {
		// TODO Auto-generated method stub
		int count=0; Integer[][] output = new Integer[a.size()][2]; 
		for(int j=0; j<a.size(); j++){
			//System.out.println( coordinatesX.toArray()[coordinatesX.size()-1-j]);
			String[] curr = a.get(j).replace("(","").replace(")", "").split(",");
			Integer[] curint = {Integer.parseInt(curr[0]), Integer.parseInt(curr[1])};
			output[count] = curint;
			count++;
		}
		return output;
	}

	@Override
	public String strategyUsed() {
		// TODO Auto-generated method stub
		return "Horizontal Sweep";
	}

}
