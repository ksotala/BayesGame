package bayesGame.minigame;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.bayesbayes.BayesNode;
import bayesGame.levelcontrollers.Controller;
import bayesGame.ui.DialogMenu;
import bayesGame.ui.GameInterface;
import bayesGame.ui.GraphPanel;
import bayesGame.ui.verbs.InteractingVerb;
import bayesGame.ui.verbs.Verb;

public class MinigameViewController extends Controller {
	
	private final MinigameController owner;
	private final DiscussionNet gameNet;
	private final GameInterface gameInterface;
	private GraphPanel graphPanel;

	
	public MinigameViewController(MinigameController minigameController,
			DiscussionNet gameNet) {
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
			owner.chooseNode(type);
			System.out.println("Observed " + node.toString());
		}
	}

	@Override
	public void genericMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void genericMessage(Object o) {
		Object[] message = (Object[])o;
		Object type = message[0];
		int timeTaken = (int)message[1];
		owner.observeNode(type, timeTaken);
	}

	public void displayOptions(Object node) {
		DialogMenu menu = new DialogMenu(this, gameInterface.getFrame(), node, gameNet.getOptions(node));
		menu.setVisible(true);
	}
	

	
	

}
