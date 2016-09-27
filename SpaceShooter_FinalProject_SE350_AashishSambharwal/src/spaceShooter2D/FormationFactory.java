package spaceShooter2D;

public class FormationFactory {
	Formation k;
	MainGame mainGame;
	Enemies en;
	
	/**
	 * Creates the factory of the formations.
	 * @param mainGame Required for adding the enemies to the pane and making them visible.
	 * @param en Required to add the enemies to the list in the Enemies class.
	 */
	public FormationFactory(MainGame mainGame, Enemies en){
		this.mainGame = mainGame;
		this.en = en;
	}
	
	/**
	 * Sets the formation of the factory depending upon the ID.
	 * @param i Depending upon the ID returns the respective formation.
	 */
	public Formation setFormation(int i){
		if(i==0) return new CardFiveFormation(mainGame, en);
		if(i==1) return new BoomerangFormation(mainGame, en);
		if(i==2) return new VicFormation(mainGame, en);
		if(i==3) return new TeeFormation(mainGame, en);
		return k;
	}
}
