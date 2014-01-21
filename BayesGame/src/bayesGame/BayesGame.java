package bayesGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.levelcontrollers.TutorialController;
import bayesGame.ui.DefaultInterfaceView;

/**
 * 
 * @author Kaj Sotala
 * 
 * 
 * Class naming conventions:
 * 
 * This game attempts to follow a Model-View-Controller pattern. To keep things clear, the following naming conventions should be followed:
 * 
 * - MODEL classes should just be named after the thing they model, e.g. "Character".
 * - CONTROLLER classes should have "Controller" at the end, e.g. "CharacterController".
 * - VIEW classes should have "View" at the end, e.g. "CharacterPanelView".
 *
 * Please note that models are allowed to have extensive program logic inside them, as long as that 
 * 
 */
public class BayesGame {

	public static void main(String[] args) {
		
		TutorialController tutorial = new TutorialController();

	}
	
	
	


}
