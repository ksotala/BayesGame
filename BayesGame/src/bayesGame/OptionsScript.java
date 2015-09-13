package bayesGame;

import javax.swing.JComponent;
import javax.swing.JFrame;

import bayesGame.levelcontrollers.LevelController;
import bayesGame.levelcontrollers.Script;
import bayesGame.ui.ColorSelection;

public class OptionsScript extends Script {
	
	public OptionsScript(LevelController controller){
		this.controller = controller;
	}

	@Override
	public void run() {
		controller.showOptionsMenu();
	}

}
