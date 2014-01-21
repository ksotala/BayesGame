package bayesGame.levelcontrollers;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.ui.AnyKeyListener;
import bayesGame.ui.DefaultInterfaceView;

public class TutorialController extends Controller {
	
	DefaultInterfaceView UI;
	int level = 0;
	int part = 0;

	public TutorialController(){
		UI = new DefaultInterfaceView();
		UI.addKeyListener(new AnyKeyListener(this));
		advanceTutorial();
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
			UI.addText("When I was little, X, my big brother, told me that there was a treasure hidden near our village.");
			UI.addText("");
			UI.addText("(press space for more)");
			break;
		case 1:
			UI.addTextMoreClear("Of course I wanted to know more, but he claimed that that was all he knew.");
			break;
		case 2:
			UI.addTextMoreClear("I asked him who had told him about the treasure, but he told me to figure it out myself.");
			break;
		case 3:
			BayesNet net = new BayesNet();
			net.addNode("Mom");
			net.addNode("Dad");
			net.addDeterministicOr("Brother", "Mom", "Dad");
			
			UI.setGraph(net);
			UI.displayGraph(DefaultInterfaceView.graphTypeBayesGraph);
			
			UI.clearText();
			UI.addText("Well, I’d show him! He hadn’t been out of the house today, and he was too impatient to keep a secret for the whole night.");
			UI.addTextMore("So he must have heard it this morning, from either mom or dad.");
			break;
		case 4:
			Map<Object,Boolean> fromMom = new HashMap<Object, Boolean>();
			fromMom.put("Mom", true);
			fromMom.put("Dad", false);
			fromMom.put("Brother", true);
			
			UI.addVisualization(fromMom);
			UI.highlightVisualization(fromMom, true);
			UI.addTextMoreClear("So. Maybe he heard it from mom, and dad didn't know anything...");
			break;
		case 5:
			Map<Object,Boolean> fromDad = new HashMap<Object, Boolean>();
			fromDad.put("Mom", false);
			fromDad.put("Dad", true);
			fromDad.put("Brother", true);
			
			UI.addVisualization(fromDad);
			UI.switchVisualizationHightlight(fromDad);
			UI.addTextMoreClear("...or maybe he heard it from dad, and mom was innocent to this crime of not telling me...");
			break;
		case 6:
			Map<Object,Boolean> fromBoth = new HashMap<Object, Boolean>();
			fromBoth.put("Mom", true);
			fromBoth.put("Dad", true);
			fromBoth.put("Brother", true);
			
			UI.addVisualization(fromBoth);
			UI.switchVisualizationHightlight(fromBoth);
			UI.addTextMoreClear("...or maybe they both knew...");
			break;
		case 7:
			Map<Object,Boolean> fromNeither = new HashMap<Object, Boolean>();
			fromNeither.put("Mom", false);
			fromNeither.put("Dad", false);
			fromNeither.put("Brother", false);
			
			UI.addVisualization(fromNeither);
			UI.switchVisualizationHightlight(fromNeither);
			UI.addTextMoreClear("...or maybe I misheard, and he was talking about something else entirely?");
			break;
		case 8:
			UI.clearText();
			UI.clearVisualizationHighlights();
			UI.addTutorialText("You may click on any of the three people in the display on the upper left to talk with them and find out what they know.");
			UI.addText("");
			UI.addTutorialText("The display on the upper right shows different possibilities for who might know what; whenever you find out something that eliminates one or more possibilities, it will be marked as eliminated.");
			UI.addText("");
			UI.addTutorialText("Try to eliminate all the incorrect possibilities in as few clicks as possible!");
			break;
		}
		part++;
	}
	
	@Override
	public void keyMessage(KeyEvent e){
		advanceTutorial();
	}
	
	@Override
	public void mouseMessage(MouseEvent e){
		
	}

}
