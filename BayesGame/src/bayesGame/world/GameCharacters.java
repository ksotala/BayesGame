package bayesGame.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GameCharacters {
	
	public static PlayerCharacter PC;
	
	private static Set<GameCharacter> characters;
	
	public GameCharacters(){
		PC = new PlayerCharacter();
		characters = new HashSet<GameCharacter>();
	}
	
	public static void add(GameCharacter character){
		characters.add(character);
	}

	public static String listFriends() {
		ArrayList<GameCharacter> friendlist = new ArrayList<GameCharacter>();
		for (GameCharacter character : characters){
			if (character.isFriend()){
				friendlist.add(character);
			}	
		}
		String output = friendlist.size() + " friends: " + friendlist.toString() + ".";
		
		return output;
	}
	

}
