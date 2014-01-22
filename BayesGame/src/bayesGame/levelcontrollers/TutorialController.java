package bayesGame.levelcontrollers;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.ui.AnyKeyListener;
import bayesGame.ui.DefaultInterfaceView;

public class TutorialController extends Controller {
	
	DefaultInterfaceView UI;
	int level = 0;
	int part = 0;
	int usedobservations = 0;
	boolean awaitingkeypresses;
	boolean awaitingmousemessage;

	public TutorialController(){
		UI = new DefaultInterfaceView();
		UI.addKeyListener(new AnyKeyListener(this));
		advanceTutorial();
		awaitingkeypresses = true;
		awaitingmousemessage = false;
	}
	
	private void advanceTutorial(){
		switch(level){
		case 0:
			levelOne();
		}

		
	}
	
	private void levelOne(){
		switch(part){
		case 0:
			UI.addText("Celia: When I was little, Opin, my big brother, told me that there was a treasure hidden near our village.");
			UI.addText("");
			UI.addText("(press space for more)");
			break;
		case 1:
			UI.addTextMoreClear("Celia: Of course I wanted to know more, but he claimed that that was all he knew.");
			break;
		case 2:
			UI.addTextMoreClear("Celia: I asked Opin how he knew about the treasure, but he told me to figure it out myself.");
			break;
		case 3:
			BayesNet net = new BayesNet();
			net.addNode("Mom");
			net.addNode("Dad");
			net.addDeterministicOr("Opin", "Mom", "Dad");
			
			UI.setGraph(net);
			UI.displayGraph(DefaultInterfaceView.graphTypeBayesGraph);
			
			UI.addTextClear("Celia: Well, I’d show him! He hadn’t been out of the house today, and he was too impatient to keep a secret for the whole night.");
			UI.addText("So he must have heard it this morning, from either mom or dad.");
			UI.addText("");
			UI.addTutorialTextMore("Celia has figured out a rule: your brother knows about the treasure, if (and only if) at least one of your parents knows about it.");
			break;
		case 4:
			Map<Object,Boolean> fromMom = new HashMap<Object, Boolean>();
			fromMom.put("Mom", true);
			fromMom.put("Dad", false);
			fromMom.put("Opin", true);
			
			UI.addVisualization(fromMom);
			UI.highlightVisualization(fromMom, true);
			UI.addTextClear("Celia: So. Maybe he heard it from mom, and dad didn't know anything...");
			UI.addText("");
			UI.addTutorialTextMore("The display on the right shows a possible world consistent with the rule: your mother knows (marked by the green color and large letter), and so does your brother. Your father does not know about it (marked with the red color and small letter).");
			break;
		case 5:
			Map<Object,Boolean> fromDad = new HashMap<Object, Boolean>();
			fromDad.put("Mom", false);
			fromDad.put("Dad", true);
			fromDad.put("Opin", true);
			
			UI.addVisualization(fromDad);
			UI.switchVisualizationHighlight(fromDad);
			UI.addTextClear("Celia: ...or maybe he heard it from dad, and mom was innocent to this crime of not telling me...");
			UI.addText("");
			UI.addTutorialTextMore("Here's another possible world: your father knows and so does your brother, but your mother does not.");
			break;
		case 6:
			Map<Object,Boolean> fromBoth = new HashMap<Object, Boolean>();
			fromBoth.put("Mom", true);
			fromBoth.put("Dad", true);
			fromBoth.put("Opin", true);
			
			UI.addVisualization(fromBoth);
			UI.switchVisualizationHighlight(fromBoth);
			UI.addTextClear("Celia: ...or maybe they both knew...");
			UI.addText("");
			UI.addTutorialTextMore("In this possible world, everyone knows, so they are all in green / large letters.");
			break;
		case 7:
			Map<Object,Boolean> fromNeither = new HashMap<Object, Boolean>();
			fromNeither.put("Mom", false);
			fromNeither.put("Dad", false);
			fromNeither.put("Opin", false);
			
			UI.addVisualization(fromNeither);
			UI.switchVisualizationHighlight(fromNeither);
			UI.addTextClear("Celia: ...or maybe I misheard, and he was talking about something else entirely?");
			UI.addText("");
			UI.addTutorialTextMore("And here nobody knows, so they are all in red / small letters. These are the only four possible worlds that the rule of 'brother knows if (and only if) at least one parent knows' allows!");
			break;
		case 8:
			Map<Object,Boolean> magicalKnowledge = new HashMap<Object, Boolean>();
			magicalKnowledge.put("Mom", false);
			magicalKnowledge.put("Dad", false);
			magicalKnowledge.put("Opin", true);
			UI.addVisualization(magicalKnowledge, false);
			UI.switchVisualizationHighlight(magicalKnowledge);
			
			UI.clearText();
			UI.addTutorialTextMore("For example, if neither of your parents knew about the treasure, then there's no way your brother could have found out about it just this morning. So we know that that's an impossible world.");
			break;
		case 9:
			UI.clearText();
			UI.clearVisualizationHighlights();
			UI.addTutorialText("You may left-click on any of the people in the above graph to assume that they know about the treasure. Left-click on the same person again to assume that they don't know. A third left-click clears the assumption. Try clicking on your brother once.");
			awaitingkeypresses = false;
			UI.addGraphMouseListener(new TutorialMouseListener(this, "Opin"));
			break;
		case 10:
			UI.clearText();
			net.assumeTruthValue("Opin", true);
			ArrayList<Map> newPossibilities = net.getNonZeroProbabilities("Opin");
			UI.updateVisualizations(newPossibilities);
			UI.addTutorialText("You are now assuming that your brother does know. Since him knowing isn't compatible with the world where he doesn't know, that world gets marked as an impossible one.");
			UI.addText("");
			UI.addTutorialText("Now click on your brother again.");
			break;
		case 11:
			UI.clearText();
			net.assumeTruthValue("Opin", false);
			ArrayList<Map> moreNewPossibilities = net.getNonZeroProbabilities("Opin");
			UI.updateVisualizations(moreNewPossibilities);
			UI.addTutorialText("And now you are assuming that your brother doesn't know. This assumption is very much compatible with the world we eliminated previously, so it becomes a possible world again, but the three other worlds now become impossible.");
			UI.addText("");
			UI.addTutorialText("Click on your brother one more time.");
			break;
		case 12:
			UI.clearText();
			net.assumeTruthValue("Opin");
			ArrayList<Map> oldPossibilities = net.getNonZeroProbabilities("Opin");
			UI.updateVisualizations(oldPossibilities);
			UI.addTutorialText("Now we're back to where we started from. You can now play around with the map, left-clicking the various people involved to test what it'd look like if you assumed specific things. You can assume things about more than one person at a time.");
			UI.addText("");
			UI.addTutorialText("When you are done, you can right-click on anyone to talk to them and find out what they *actually* know. When you've eliminated all but one of the possible worlds, you've found the true one. Try to find it in as few right-clicks as possible!");
			UI.removeGraphMouseListeners;
			UI.addGraphMouseListener(new AssumingObservingMouseListener(this));
		}
		part++;
	}
	
	@Override
	public void keyMessage(KeyEvent e){
		if (awaitingkeypresses){
			advanceTutorial();
		}
	}
	
	@Override
	public void mouseMessage(MouseEvent e){
		if (awaitingmousemessage){
			advanceTutorial();
		}
	}

}
