package bayesGame.world;

import bayesGame.levelcontrollers.Controller;
import bayesGame.minigame.MinigameController;

public class PlayerCharacter extends GameCharacter {

	private final int STARTING_DEFAULT_ENERGY = 5;
	private final int SKILL_COST_PER_LEVEL = 10;
		
	private int energy;
	private int base_energy;
	
	private int psychology_skill = 0;
	private int psychology_level = 0;
	
	public PlayerCharacter() {
		super("You", GameCharacter.GENDER.GIRL);
		base_energy = STARTING_DEFAULT_ENERGY;
		resetEnergy();
	}
	
	public void setEnergy(int energy){
		this.energy = energy;
	}
	
	public void resetEnergy(){
		energy = base_energy;
	}
	
	public int getEnergy(){
		return energy;
	}

	public boolean useEnergy(int i) {
		if (i > energy){
			return false;
		}
		energy = energy - i;
		return true;
	}

	public void pointsToSkill(String string, int score,
			MinigameController controller) {
		psychology_skill = psychology_skill + score;
		int nextSkillLevel = getNextSkillLevel(string);
		controller.showText("Your current skill in " + string + " is " + psychology_level + ". Getting " + score + " points to your " + string + " skill puts you at " + psychology_skill + "/" + nextSkillLevel + " experience points.");
		attemptSkillIncrease(string, controller);
	}

	private void attemptSkillIncrease(String string, MinigameController controller) {
		int nextLevel = getNextSkillLevel(string);
		while (psychology_skill >= nextLevel){
			psychology_level++;
			nextLevel = getNextSkillLevel(string);
			controller.showText("Your " + string + " skill level has increased! You're now at level " + psychology_level + ". Next level at " + nextLevel + " experience points.");
		}
	}

	private int getNextSkillLevel(String string) {
		return ((psychology_level+1) * SKILL_COST_PER_LEVEL);
	}

}
