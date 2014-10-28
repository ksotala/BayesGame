package bayesGame.minigame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import bayesGame.ui.InterfaceView;
import bayesGame.bayesbayes.*;

public class MinigameController {

	private MinigameViewController viewController;
	
	private Map<Object,String[]> discussions;
	private BayesNet gameNet;
	private int timeLimit;
	private Set<Object> hiddenNodes;
	private Set<Object> targetNodes;
	
	private boolean ready = false;
	private int turnsTaken;
	
	public MinigameController(BayesNet gameNet, Set<Object> targetNodes) {
		this.gameNet = gameNet;
		this.targetNodes = targetNodes;
		
		if (targetNodes.size() == 0){
			for (BayesNode n : gameNet.getGraph().getVertices().toArray(new BayesNode[gameNet.getGraph().getVertexCount()])){
				targetNodes.add(n.type);
			}
		}
		
		this.hiddenNodes = new HashSet<Object>();
		this.discussions = new HashMap<Object,String[]>();
	}
	
	public void setHiddenNodes(Set<Object> hiddenNodes){
		this.hiddenNodes = hiddenNodes;
	}
	
	public void randomizeHiddenNodes(int amount){
		Random random = new Random();
		BayesNode[] nodes = gameNet.getGraph().getVertices().toArray(new BayesNode[gameNet.getGraph().getVertexCount()]);
		
		for(int i = 0; i < amount; i++){
			int item = random.nextInt(nodes.length);
			hiddenNodes.add(nodes[item].type);
		}
	}
	
	public void setDiscussions(Map<Object,String[]> discussions){
		this.discussions = discussions;
	}
	
	// game mode 1 = figure out the contents of target nodes
	public void startGame(int timeLimit, Object[] knowledges){
		this.timeLimit = timeLimit;
		turnsTaken = 0;
		
		// mark known nodes as known
		for (Object o : knowledges){
			gameNet.observe(o);
		}
		
		for (Object h : hiddenNodes){
			gameNet.hide(h);
			targetNodes.remove(h);
		}
		
		// initialize interface
		viewController = new MinigameViewController(this, gameNet);
		viewController.addText("Target nodes: " + targetNodes.toString());
		viewController.processEventQueue();
		
		ready = true;
	}
	
	public void observeNode(Object node){
		if (ready){
			if (!hiddenNodes.contains(node)){
				String[] question = discussions.get(node);
				if (question != null){
					viewController.addText(question[0]);
				}
				gameNet.observe(node);
				gameNet.updateBeliefs();
				viewController.addRefreshDisplay();
				if (question != null){
					viewController.addText(question[1]);
				}
				viewController.processEventQueue();
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
		
		viewController.addText("Turn " + turnsTaken + "/" + timeLimit);
		
		if (allTargetNodesKnown){
			viewController.addText("Success!");
			ready = false;
		} else if (turnsTaken == timeLimit){
			viewController.addText("Failure!");
			ready = false;
		}
		
		viewController.processEventQueue();
	}
	
	
	
	
	

}
