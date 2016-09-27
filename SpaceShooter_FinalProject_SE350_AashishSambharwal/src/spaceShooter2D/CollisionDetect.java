package spaceShooter2D;
import javafx.collections.ObservableList;
import javafx.scene.Node;
/**
 * 
 * @author Aashish SSG
 *
 */
public class CollisionDetect {

	public CollisionDetect(){
		
	}
	
	/**
	 * Collision detection for bullets, if the bullet gets within the bounds of the enemy if Type 1 else,
	 * if it is Type 2 it will check collision with the player. Only two classes utilize this which are the
	 * Boss class and the Player class.
	 * @param bul The bullet that is being tested for collision.
	 * @param enemy The Enemy instance via which we obtain the list of enemies for collision detection.
	 * @param player The player which is checked for collision with enemies or with bullet.
	 * @param list The observable list in order to remove the redundant images.
	 */
	public void collide(Bullet bul, Enemies enemy, Player player, ObservableList<Node> list){
		if(enemy!=null){
			for(Foes e: enemy.getList()){
				if(e instanceof Enemy){
					if(bul.Type == 1 && ((GamePiece) e).collidesWith(bul)){
						((Enemy)e).setAllVisible(false);
						enemy.getList().remove(e);
						bul.collisionOccurred(true);
						bul.removeFromPane();
						list.remove(e.getImage());
						
						break;
					}
				}
				if(e instanceof Boss){
					if(bul.Type ==1 && ((GamePiece) e).collidesWith(bul)){
						if(((Boss)e).Health<=0) {
							((Boss)e).setAllVisible(false);
							player.setBossDefeatState(true);
							list.remove(e.getImage());
							}
						bul.collisionOccurred(true);
						bul.removeFromPane();
						((Boss)e).Health-=5;
						break;
					}
				}
			}
			
			if(bul.Type == 2 && bul.collidesWith(player) && bul.bulletimg.isVisible()){
				player.Health-=10;
				bul.bulletimg.setVisible(false);
				bul.collisionOccurred(true);
			}
		}
	}
	
	/**
	 * Collision detection for missiles, if the missile gets within the bounds of the enemy if Type 1 else,
	 * if it is Type 2 it will check collision with the player. Only two classes utilize this which are the
	 * Boss class and the Player class.
	 * @param mis Checking the current missile.
	 * @param en Enemies for obtaining the list to check for intersection of bounds.
	 * @param player Player for checking if the player comes in contact with the missile instead.
	 * @param children Observable list for removal of images that are redundant.
	 */
	public void collide(Missiles mis, Enemies en, Player player, ObservableList<Node> children) {
		// TODO Auto-generated method stub
		for(Foes e: en.getList()){
			if(e instanceof Enemy){
				if(mis.Type==1 && ((GamePiece) e).collidesWith(mis)){
					((Enemy)e).setAllVisible(false);
					en.getList().remove(e);
					mis.collisionOccurred(true);
					mis.removeFromPane();
					children.remove(e.getImage());
					break;
				}
			}
			if(e instanceof Boss){
				if(mis.Type == 1 && ((GamePiece) e).collidesWith(mis)){
					if(((Boss)e).Health<=0) {
						((Boss)e).setAllVisible(false);
						player.setBossDefeatState(true);
						children.remove(e.getImage());
						}
					((Boss) e).Health-=mis.getDamage()/2.5;
					mis.collisionOccurred(true);
					mis.removeFromPane();
					break;
				}
			}
		}
		if(mis.Type == 2 && mis.collidesWith(player) && mis.getImage().isVisible()){
			player.Health-=mis.getDamage();
			mis.getImage().setVisible(false);
			mis.collisionOccurred(true);
		}
	}
	
	/**
	 * Checks Enemy to Player collision only.
	 * @param en Enemies for obtaining the list to check for intersection of bounds.
	 * @param player Player for checking if the player comes in contact with the missile instead.
	 * @param children Observable list for removal of images that are redundant.
	 */
	public void collide(Enemies en, Player player, ObservableList<Node> children) {
		// TODO Auto-generated method stub
		for(Foes e: en.getList()){
			if(((GamePiece) e).collidesWith(player)&&e instanceof Enemy){
				player.Health-=15;
				((Enemy)e).setAllVisible(false);
				en.getList().remove(e);
				children.remove(e.getImage());
				break;
			}
			if(((GamePiece) e).collidesWith(player) && e instanceof Boss){
				player.Health--;
				((Boss) e).Health--;
				break;
			}
		}
	}
}

	

