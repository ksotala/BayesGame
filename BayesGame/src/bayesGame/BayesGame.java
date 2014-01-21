package bayesGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;

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
		
		Map<Object,Boolean> fromMom = new HashMap<Object, Boolean>();
		fromMom.put("Mother", true);
		fromMom.put("Father", false);
		fromMom.put("Brother", true);
		
		Map<Object,Boolean> fromDad = new HashMap<Object, Boolean>();
		fromDad.put("Mother", false);
		fromDad.put("Father", true);
		fromDad.put("Brother", true);
		
		Map<Object,Boolean> fromBoth = new HashMap<Object, Boolean>();
		fromBoth.put("Mother", true);
		fromBoth.put("Father", true);
		fromBoth.put("Brother", true);
		
		Map<Object,Boolean> fromNeither = new HashMap<Object, Boolean>();
		fromNeither.put("Mother", false);
		fromNeither.put("Father", false);
		fromNeither.put("Brother", false);
		
		defaultInterface.addVisualization(fromMom);
		defaultInterface.addText("Maybe he heard it from mom...");
		
		defaultInterface.addVisualization(fromDad);
		defaultInterface.addText("...or maybe from dad...");
		
		defaultInterface.addVisualization(fromBoth);
		defaultInterface.addText("...or maybe from both...");
		
		defaultInterface.addVisualization(fromNeither);
		defaultInterface.addText("...or maybe I misheard, and he was talking about something else entirely?");
		
		defaultInterface.addTutorialText("You may click on any of the three people in the display on the upper left to talk with them and find out what they know.");
		defaultInterface.addTutorialText("The display on the upper right shows different possibilities for who might know what; whenever you find out something that eliminates one or more possibilities, it will be marked as eliminated.");
		defaultInterface.addTutorialText("Try to eliminate all the incorrect possibilities in as few clicks as possible!");

		defaultInterface.setVisualizationTruth(fromBoth, false);
		defaultInterface.setVisualizationTruth(fromNeither, false);
	}
	

}
