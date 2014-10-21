package bayesGame.minigame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bayesGame.ui.InterfaceView;
import bayesGame.bayesbayes.*;

public class MinigameController {

	private InterfaceView gameInterface;
	private Map<Object,String[]> discussions;
	private BayesNet gameNet;
	private int timeLimit;
	private Set<Object> hiddenNodes;
	private Set<Object> targetNodes;
	
	private boolean ready = false;
	private int turnsTaken;
	
	public MinigameController(BayesNet gameNet, InterfaceView gameInterface, Set<Object> targetNodes) {
		this.gameNet = gameNet;
		this.gameInterface = gameInterface;
		this.targetNodes = targetNodes;
		
		this.hiddenNodes = new HashSet<Object>();
		this.discussions = new HashMap<Object,String[]>();
	}
	
	public void setHiddenNodes(Set<Object> hiddenNodes){
		this.hiddenNodes = hiddenNodes;
	}
	
	public void setDiscussions(Map<Object,String[]> discussions){
		this.discussions = discussions;
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
			if (!hiddenNodes.contains(node)){
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
			if (gameNet.getProbability(o).doubleValue() > 0.0d && gameNet.getProbability(o).doubleValue() < 1.0d){
				allTargetNodesKnown = false;
				break;
			}
		}
		
		if (allTargetNodesKnown){
			// report game ended in success
			ready = false;
		} else if (turnsTaken == timeLimit){
			// report game ended in failure
			ready = false;
		}
	}
	
	
	
	
	

}
