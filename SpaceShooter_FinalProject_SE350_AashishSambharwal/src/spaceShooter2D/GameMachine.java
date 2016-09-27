package spaceShooter2D;

public class GameMachine {
	GameState Menu;
	GameState Playing;
	GameState Finished;
	
	GameState curState;
	
	/**
	 * The Main Game machine which sets different states upon pressing buttons or upon defeat or win.
	 * @param mainGame Required for setting up the different states and making buttons and labels visible
	 */
	public GameMachine(MainGame mainGame){
		Menu = new Menu(mainGame, this);
		Playing = mainGame;
		Finished  = new Result(mainGame, this);
	}
	
	/**
	 * Sets the Games state to the one specified.
	 * @param state The state to which the game is to be set.
	 */
	public void setGameState(GameState state){
		curState = state;
	}
	
	/**
	 * Returns the menu state.
	 * @return Returns the intial state when the game is run.
	 */
	public GameState getMenuState()	{return Menu;}
	
	/**
	 * Returns the playing state.
	 * @return Returns the playing state, when the player is trying to win the game.
	 */
	public GameState getPlayingState()	{return Playing;}
	
	/**
	 * Returns the finished state.
	 * @return Returns the finished state, when the player has either lost or won.
	 */
	public GameState getFinishedState()	{return Finished;}
}
