package bayesGame.levelcontrollers;

import bayesGame.BayesGame;

public abstract class Script {
	
	protected LevelController controller = BayesGame.controller;
	
	public abstract void run();

	public void setController(LevelController controller){
		this.controller = controller;
	}
	

	
}