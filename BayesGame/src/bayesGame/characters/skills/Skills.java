package bayesGame.characters.skills;

import java.util.HashMap;
import java.util.Map;

public class Skills {
	
	private Map<String,Integer> skills;

	public Skills() {
		skills = new HashMap<String,Integer>();
	}
	
	public int skillLevel(String skill){
		Integer skillInteger = skills.get(skill);
		if (skillInteger == null){
			return 0;
		} else {
			return skillInteger.intValue();
		}
	}
	
	public boolean hasSkill(String skill){
		return (skillLevel(skill) == 100);
	}
	
	/**
	 * Returns true if at least one of the skills is possessed at the 100% level.
	 * 
	 * @param skills
	 * @return
	 */
	public boolean hasSkill(String... skills){
		for (String skill : skills){
			if (hasSkill(skill)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if all the skills are possessed at the 100% level.
	 * 
	 * @param skills
	 * @return
	 */
	public boolean hasSkills(String... skills){
		for (String skill : skills){
			if (!hasSkill(skill)){
				return false;
			}
		}
		return true;
	}

}
