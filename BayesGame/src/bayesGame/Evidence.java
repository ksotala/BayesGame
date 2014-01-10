package bayesGame;

import java.util.ArrayList;

public class Evidence {
	
	public final String observation;
	private Character character;
	private ArrayList<Character> trail;

	public Evidence(String observation, Character character) {
		this(observation, character, null);
	}
	
	public Evidence(String observation, Character character, ArrayList<Character> trail) {
		this.observation = observation;
		this.setCharacter(character);
		this.trail = trail;
	}
	
	
	
	public ArrayList<Character> getTrail() {
		return trail;
	}

	public void setTrail(ArrayList<Character> trail) {
		this.trail = trail;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	
	public boolean equals(Object other){
		
		boolean result = false;
		
		if (other instanceof Evidence){
			Evidence theOther = (Evidence)other;
			result = (this.observation.equals(theOther.observation) &&
					this.character.equals(theOther.getCharacter()) &&
					this.trail.equals(theOther.getTrail()));
		}
		
		return result;
	}
	
	public int hashCode(){
		
		int result = 42;
		
		int a = observation.hashCode();
		result = 37 * result + a;
		
		int b = character.hashCode();
		result = 37 * result + b;
		
		int c = 0;
		if (trail != null){
			c = trail.hashCode();
		}
		result = 37 * result + c;
		
		return result;
	}
	
	
	
	

}
