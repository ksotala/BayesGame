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
		net.addNode("Brother", new Object[]{"Brother", "Mother", "Father"});
		System.out.println(net.connectNodes("Mother", "Brother"));
		System.out.println(net.connectNodes("Father", "Brother"));
		
		defaultInterface.setGraph(net.getGraph());
		defaultInterface.displayGraph(DefaultInterfaceView.graphTypeBayesGraph);
	}
	

}
