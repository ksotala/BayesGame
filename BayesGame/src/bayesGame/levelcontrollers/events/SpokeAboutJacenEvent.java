package bayesGame.levelcontrollers.events;

import bayesGame.BayesGame;
import bayesGame.levelcontrollers.LevelController;
import bayesGame.levelcontrollers.Script;

public class SpokeAboutJacenEvent extends Script {



	@Override
	public void run() {
		controller.addText("Doot de doo");
		controller.addProcessEventQueue();
		controller.run();
	}

	@Override
	public void setController(LevelController controller) {
		// TODO Auto-generated method stub
		
	}

}
