package bayesGame.levelcontrollers;

import bayesGame.world.GameCharacter;
import bayesGame.world.GameCharacters;

public class WelcomeToSchoolScript implements Script {

	private LevelController controller;
	
	public WelcomeToSchoolScript(LevelController controller) {
		this.controller = controller;
	}

	@Override
	public void MinigameCompleted(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void QueueEmpty() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {

		
		controller.addText("'Phew! Defeated it!'");
		controller.addText("Boy: 'Wow! That was some impressive work!'");
		controller.addText("'Thanks. But hey! You told me there was a 0% chance of a monster near the academy!'");
		controller.addText("Boy: 'Ehh… yeah. I guess I misremembered. I only read like half of our course book anyway, it was really boring.'");
		controller.addText("'Didn’t you say that Monsterology was your favorite subject?'");
		controller.addText("Boy: 'Hey, that only means that all the other subjects were even more boring!'");
		controller.addText("'. . .'");
		controller.addText("I guess I shouldn’t put too much faith on what he says.");
		
		controller.addText("[Your model of the world has been updated! The value of the variable 'Monster Near The Academy' is now 50%.]");
		controller.addText("[Your model of the world has been updated! The value of the variable 'Boy Has Monsterology 1' is 0%.]");
		
		//TODO: scene transition
		//controller.addNewSceneChange();
		
		controller.addText("You successfully passed the exam, and got invited to the Academy.");
		controller.addText("It is now your first day of school, and you've just arrived at the premises.");
		controller.addText("Suddenly you hear a familiar voice behind you.");
		controller.addText("Boy: 'Hey!'");
		controller.addText("'Wait, you made it to the school as well?'");
		controller.addText("Err, that could have been worded better.");
		controller.addText("To your defense, the exam was really hard, and you totally didn't expect him to pass.");
		controller.addText("He doesn't seem to mind, though.");
		controller.addText("Boy: 'Yeah! I didn't do well on Monsterology, but I did well on everything else.'");
		controller.addText("'...so you did well on every subject except your favorite one?'");
		controller.addText("Boy: 'Yeah. Isn't that how it goes for everyone?'");
		controller.addText("'...sure. Hey, I never caught your name?'");
		controller.addText("Jace: 'It's Jace.'");
		controller.addText("You have made friends with Jace!");
		
		controller.setNextScript(new LoopScript(controller));
		controller.addProcessEventQueue();
		controller.run();

	}

	@Override
	public void setController(LevelController controller) {
		this.controller = controller;
		
	}

}
