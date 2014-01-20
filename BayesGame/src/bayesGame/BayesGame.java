package bayesGame;

import java.io.IOException;

import bayesGame.bayesbayes.BayesNet;
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

	public static void main(String[] args) throws IOException {
		
		DefaultInterfaceView defaultInterface = new DefaultInterfaceView();
		
		BayesNet net = new BayesNet();
		net.addNode("Mother");
		net.addNode("Father");
		net.addDeterministicOr("Brother", "Mother", "Father");

		defaultInterface.setGraph(net);
		defaultInterface.displayGraph(DefaultInterfaceView.graphTypeBayesGraph);
		
		defaultInterface.addText("When I was little, X, my big brother, told me that there was a treasure hidden near our village.");
		defaultInterface.addText("Of course I wanted to know more, but he said he didn’t know more than that.");
		defaultInterface.addText("I asked him how he knew about the treasure, but he told me to figure it out myself.");
		defaultInterface.addText("Well, I’d show him! He hadn’t been out of the house today, and if he’d have heard about it yesterday he’d have told me earlier. So it had to be either mom or dad who told him.");
	}
	

}
