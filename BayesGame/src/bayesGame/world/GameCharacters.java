package bayesGame.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import bayesGame.fluff.RandomSubjectVariable;
import bayesGame.world.GameCharacter.GENDER;

public class GameCharacters {
	
	public static PlayerCharacter PC;
	
	private static Set<GameCharacter> characters;
	
	private static RandomSubjectVariable names;
	
	public GameCharacters(){
		PC = new PlayerCharacter();
		characters = new HashSet<GameCharacter>();
		names = new RandomSubjectVariable(RandomSubjectVariable.NAMES);
		
		GameCharacter Jace = new GameCharacter("Jace", GameCharacter.GENDER.BOY);
		Jace.befriend();
		add(Jace);
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

	public static GameCharacter createCharacter(GENDER gender) {
		String name = names.getNewRandomTerm();
		GameCharacter character = new GameCharacter(name, gender);
		add(character);
		
		return character;
	}
	
	
	

}
