package bayesGame.viewcontrollers;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.minigame.DiscussionNet;
import bayesGame.ui.GameInterface;
import bayesGame.ui.GraphPanel;

public class DefaultViewController implements ViewController {
	
	private BayesNet gameNet;
	private GameInterface gameInterface;
	private GraphPanel graphPanel;

	public DefaultViewController() {
		this.gameInterface = new GameInterface();	
		gameInterface.display();
	}
	
	public void addGraph(BayesNet gameNet){
		this.gameNet = gameNet;
	}

	public void addText(String text){
		gameInterface.addText(text);
	}
	
	public void showText(String text){
		this.addText(text);
		this.processEventQueue();
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

	public void dispose() {
		gameInterface.dispose();
	}

}
