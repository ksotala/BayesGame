package bayesGame;

import java.util.HashSet;
import bayesGame.bayesbayes.BayesNet;

public class Character {

	public final String name;
	private HashSet<PersonalityTrait> personalityTraits;
	
	public Character(String name) {
		this.name = name;
		this.personalityTraits = new HashSet<PersonalityTrait>();
	}
	
	public Character(String name, BayesNet traitDistribution){
		this.name = name;
		
		// TODO: generate personality traits based on the trait distribution
		
	}
	
	
	
	
	
	public boolean equals(Object other){
		
		boolean result = false;
		
		if (other instanceof Character){
			Character theOther = (Character)other;
			result = (this.name.equals(theOther.name));
		}
		
		return result;
	}
	
	public int hashCode(){
		
		return name.hashCode();
	}

}
