package bayesGame.levelcontrollers;

public class ResolutionScript extends Script {

	public ResolutionScript(LevelController controller) {
		this.controller = controller;
	}

	@Override
	public void run() {
		controller.showResolutionMenu();

	}

}
