package bayesGame.minigame;

import java.util.Map;
import java.util.Set;

import bayesGame.ui.InterfaceView;
import bayesGame.bayesbayes.*;

public class MinigameController {

	private InterfaceView gameInterface = null;
	private Map<Object,String[]> discussions;
	private BayesNet gameNet;
	private int timeLimit;
	private Set<Object> observableNodes;
	private Set<Object> targetNodes;
	
	private boolean ready = false;
	private int turnsTaken;
	
	public MinigameController() {
	}
	
	public void setNet(BayesNet gameNet){
		this.gameNet = gameNet;
	}
		
	public void setInterface(InterfaceView gameInterface){
		this.gameInterface = gameInterface;
	}
	
	// game mode 1 = figure out the contents of target nodes
	public void startGame(int timeLimit, Object[] knowledges){
		this.timeLimit = timeLimit;
		turnsTaken = 0;
		
		// initialize interface
		
		// mark known nodes as known
		for (Object o : knowledges){
			gameNet.observe(o);
		}
		
		ready = true;
	}
	
	public void observeNode(Object node){
		if (ready){
			if (observableNodes.contains(node)){
				String[] question = discussions.get(node);
				if (question != null){
					gameInterface.addText(question[0]);
				}
				gameNet.observe(node);
				gameNet.updateBeliefs();
				gameInterface.addRefreshDisplay();
				gameInterface.addText(question[1]);
				
				this.endOfTurn();
			}
		}
		
	}
	
	private void endOfTurn(){
		turnsTaken++;
		
		boolean allTargetNodesKnown = true;
		for (Object o : targetNodes){
			if (gameNet.getProbability(o).intValue() < 1 && gameNet.getProbability(o).intValue() > 0){
				allTargetNodesKnown = false;
				break;
			}
		}
		
		if (allTargetNodesKnown){
			// report game ended in success
		} else if (turnsTaken == timeLimit){
			// report game ended in failure
		}
	}
	
	
	
	
	

}
