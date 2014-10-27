package bayesGame.minigame;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.ui.GameInterface;
import bayesGame.ui.GraphPanel;

public class MinigameViewController {
	
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

	
	

}
