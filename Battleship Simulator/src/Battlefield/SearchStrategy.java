package Battlefield;

public interface SearchStrategy {
	public void search(String[][] grid);
	public int cellCount();
	public String location();
	public String strategyUsed();
}
