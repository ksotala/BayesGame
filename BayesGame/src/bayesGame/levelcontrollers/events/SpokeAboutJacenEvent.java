package bayesGame.levelcontrollers.events;

import bayesGame.BayesGame;
import bayesGame.levelcontrollers.LevelController;
import bayesGame.levelcontrollers.Script;

public class SpokeAboutJacenEvent implements Script {

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
		BayesGame.controller.addText("Doot de doo");
		BayesGame.controller.addProcessEventQueue();
		BayesGame.controller.run();
	}

	@Override
	public void setController(LevelController controller) {
		// TODO Auto-generated method stub
		
	}

}
