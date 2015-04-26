package bayesGame.world;

public class GameCharacter {
	
	public final String name;
	
	public enum RELATIONSHIP_TYPE{
		FUN_GAMES, SERIOUS_STUDY, TESTED_METTLE
	};
	
	public enum GENDER{
		BOY, GIRL
	};
	
	private boolean friends;
	
	private int fun_games = 0;
	private int serious_study = 0;
	private int tested_mettle = 0;
	
	private int overall_relationship = 0;
	private int bff_relationship = 0;
	
	private GENDER gender;
	
	private int unpaid_wary = 0;
	
	public GameCharacter(String name, GENDER gender){
		this.name = name;
		this.gender = gender;
	}
	
	public void increaseRelationship(int amount, RELATIONSHIP_TYPE type){
		int old_min_value = Math.max(fun_games, serious_study);
		int relationship_increase = 0;
		
		switch(type){
		case FUN_GAMES:
			fun_games = fun_games + amount;
			break;
		case SERIOUS_STUDY:
			serious_study = serious_study + amount;
			break;
		case TESTED_METTLE:
			tested_mettle = tested_mettle + amount;
			break;
		}
		
		int new_min_value = Math.max(fun_games, serious_study);

		if (new_min_value > old_min_value){
			relationship_increase = new_min_value - old_min_value;
			// any unpaid wary needs to be paid off before it increases the relationship, though
			// it will go to tested mettle afterwards
			if (unpaid_wary > 0){
				// TODO: update wary sources
				if (relationship_increase >= unpaid_wary){
					relationship_increase = relationship_increase - unpaid_wary;
					tested_mettle = tested_mettle + unpaid_wary;
					unpaid_wary = 0;
				} else {
					unpaid_wary = unpaid_wary - relationship_increase;
					tested_mettle = tested_mettle + relationship_increase;
					relationship_increase = 0;
				}
			}
		}
		
		overall_relationship = overall_relationship + relationship_increase;
		bff_relationship = Math.min(overall_relationship, tested_mettle);
	}
	
	public int getRelationship(RELATIONSHIP_TYPE type){
		int relationship = 0;
		
		switch(type){
		case FUN_GAMES:
			relationship = fun_games;
			break;
		case SERIOUS_STUDY:
			relationship = serious_study;
			break;
		case TESTED_METTLE:
			relationship = tested_mettle;
			break;
		}
		
		return relationshipXPtoRelationshipLevel(relationship);
	}
	
	private int relationshipXPtoRelationshipLevel(int xp){
		// if the current level of the relationship is r, then the amount of experience
		// needed for the next level is r+1
		int i = 0;
		int j = 0;
		while(xp > i){
			j++;
			i = i + j;
		}
		return j;
	}
	
	
	public void befriend(){
		friends = true;
	}
	
	public void unfriend(){
		friends = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameCharacter other = (GameCharacter) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public boolean isFriend() {
		return friends;
	}
	
	@Override
	public String toString(){
		return name;
	}


	
}
