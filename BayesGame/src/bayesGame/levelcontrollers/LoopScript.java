package bayesGame.levelcontrollers;

import bayesGame.BayesGame;
import bayesGame.world.Day;
import bayesGame.world.GameCharacters;
import bayesGame.world.PlayerCharacter;
import bayesGame.world.World;

public class LoopScript extends Script {
	
	private LevelController controller;
	
	private int day_number;
	
	public LoopScript(LevelController controller){
		this.controller = controller;
	}

	public LoopScript() {
		this.controller = BayesGame.controller;
	}


	@Override
	public void run() {
		PlayerCharacter PC = GameCharacters.PC;
		Day day = World.getDate();
		
		day.advanceTimeOfDay();
		
		day_number = day.date();
		
		if (day.justStarted()){
			PC.resetEnergy();
			
			switch(day_number){
			   case 1:
				   controller.showMessage("Welcome to the game!\n\n"
				   		+ "You are trying to get into the prestigious Academy where your brother mysteriously disappeared three years ago.\n"
				   		+ "Applicants are tested by having them study a new subject in the Academy, while taking exams of increasing difficulty. \nFailing any one exam means being permanently disqualified. If you manage to ace all five exams, you're successful.\n"
				   		+ "You will be facing exams on days 3, 5, 6, 7 and 8 of your studies, them requiring you to have mastered psychology \non skill levels 1, 2, 3, 4 and 5, respectively. \n\nGood luck!");
				   break;
			   case 3:
				   testSkillRequirement(1);
				   break;
			   case 5:
				   testSkillRequirement(2);
				   break;
			   case 6:
				   testSkillRequirement(3);
				   break;
			   case 7:
				   testSkillRequirement(4);
				   break;
			   case 8:
				   testSkillRequirement(5);
				   break;
			}
			
			if (day_number == 8){
				controller.showEndMessage("Congratulations! You have beat all the five exams, and are admitted into the academy! You have won the game.");
			}

		}
		
	

		
		controller.addText("It is day " + day_number + " of your studies. You have " + day.timeLeft() + " hours to spend today, and " + PC.getEnergy() + " points of energy.");
		controller.addProcessEventQueue();
	
		ChoiceMenu choices = day.getChoices();
		choices.setController(controller);
		controller.addChoiceMenu(choices);
		
		controller.run();
	}
	
	
	
	private void testSkillRequirement(int requirement) {
		PlayerCharacter PC = GameCharacters.PC;
		int psychology_skill = PC.getSkillLevel();
		
		if (psychology_skill < requirement){
			controller.showEndMessage("Day " + day_number + " starts with an exam. Unfortunately, your skill of " + psychology_skill + " is below the required skill of " + requirement + ", and you are rejected from the academy!");
		} else {
			controller.showMessage("Day " + day_number + " starts with an exam. Fortunately, your skill of " + psychology_skill + " meets the required skill of " + requirement + ", and you may continue your studies.");
		}

		
	}

	@Override
	public void setController(LevelController controller) {
		this.controller = controller;
		
	}

}
