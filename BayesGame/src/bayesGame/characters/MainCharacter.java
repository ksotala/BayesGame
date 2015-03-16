package bayesGame.characters;

import bayesGame.characters.skills.Skills;

public class MainCharacter {

	private static Skills skills = new Skills();
	
	private MainCharacter() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean hasSkills(String[] skillList){
		return skills.hasSkills(skillList);
	}

}
