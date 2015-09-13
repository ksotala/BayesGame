package bayesGame.levelcontrollers;

import bayesGame.BayesGame;
import bayesGame.world.Day;
import bayesGame.world.GameCharacters;
import bayesGame.world.PlayerCharacter;
import bayesGame.world.World;

public class LoopScript extends Script {
	
	private LevelController controller;
	
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
	
		if (day.justStarted()){
			PC.resetEnergy();
		}
		
		controller.addText("It is day " + day.date() + " of your studies. You have " + day.timeLeft() + " hours to spend today, and " + PC.getEnergy() + " points of energy.");
		controller.addProcessEventQueue();
	
		ChoiceMenu choices = day.getChoices();
		choices.setController(controller);
		controller.addChoiceMenu(choices);
		
		controller.run();
	}
	
	
	
	@Override
	public void setController(LevelController controller) {
		this.controller = controller;
		
	}

}
