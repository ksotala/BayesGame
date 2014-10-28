package bayesGame.minigame;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.bayesbayes.BayesNode;
import bayesGame.levelcontrollers.Controller;
import bayesGame.ui.GameInterface;
import bayesGame.ui.GraphPanel;
import bayesGame.ui.verbs.InteractingVerb;
import bayesGame.ui.verbs.Verb;

public class MinigameViewController extends Controller {
	
	private final MinigameController owner;
	private final BayesNet gameNet;
	private final GameInterface gameInterface;
	private GraphPanel graphPanel;

	
	public MinigameViewController(MinigameController minigameController,
			BayesNet gameNet) {
		this.owner = minigameController;
		this.gameNet = gameNet;
		this.gameInterface = new GameInterface();
		
		initializeView();
	}
	
	private void initializeView(){
		graphPanel = new GraphPanel(gameNet);
		Verb interactingVerb = new InteractingVerb(this, Verb.returnCall.MouseMessage);
		graphPanel.addVerb(interactingVerb);

		gameInterface.setBigPanel(graphPanel);
		gameInterface.display();
	}
	
	public void addText(String text){
		gameInterface.addText(text);
	}
	
	public void addRefreshDisplay(){
		gameInterface.addRefreshDisplay();
	}
	
	public void updateGraph(){
		graphPanel.updateGraph();
	}
	
	public void processEventQueue(){
		gameInterface.processEventQueue();
	}

	@Override
	public void keyMessage(Object o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMessage(Object o) {
		if (o != null){
			BayesNode node = (BayesNode)o;
			Object type = node.type;
			owner.observeNode(type);
			System.out.println("Observed " + node.toString());
		}
	}

	@Override
	public void genericMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void genericMessage(Object o) {
		// TODO Auto-generated method stub
		
	}

	
	

}
