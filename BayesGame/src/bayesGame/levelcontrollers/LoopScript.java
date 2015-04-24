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
		
		if (day.justStarted()){
			PC.resetEnergy();
		}
		
		day.advanceTimeOfDay();
		
		controller.addText("It is day " + day.date() + " of your studies.");
		controller.addText("You have time to do " + day.timeLeft() + " things today, and there are classes on the first " + day.classesLeft() + " of them.");
		controller.addText("You have " + PC.getEnergy() + " points of energy to use.");
		controller.addText("You have " + GameCharacters.listFriends());
		controller.addText("Your available activities are:");
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
