package bayesGame.minigame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.ui.InterfaceView;
import bayesGame.bayesbayes.*;

public class MinigameController {

	private MinigameViewController viewController;
	
	private Map<Object,String[]> discussions;
	private BayesNet gameNet;
	private int timeLimit;
	private Set<Object> hiddenNodes;
	private Set<Object> targetNodes;
	
	private int gameMode;
	private int reaction;
	
	private boolean ready = false;
	private int turnsTaken;
	
	public MinigameController(BayesNet gameNet, Set<Object> targetNodes) {
		this.gameNet = gameNet;
		this.targetNodes = targetNodes;
		this.gameMode = 0;
		this.reaction = 0;
		
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
	
	public void setGameMode(int gameMode){
		this.gameMode = gameMode;
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
		
		// need to refactor these into their own class...
		for (Object h : hiddenNodes){
			gameNet.addProperty(h, "hidden");
		}
		
		for (Object t : targetNodes){
			gameNet.addProperty(t, "target");
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
				viewController.processEventQueue();
				if (gameMode == 1 && targetNodes.contains(node)){
					decisionMade(node);
				} else {
					if (question != null){
						viewController.addText(question[1]);
					}
				}
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
		viewController.processEventQueue();
		
		if (allTargetNodesKnown && gameMode == 0){
			viewController.addText("Success!");
			viewController.addText("Clearing this level with " + (timeLimit - turnsTaken) + " turns to spare confers " + (timeLimit - turnsTaken) + "fame.");
			clear();
		} else if (turnsTaken == timeLimit){
			viewController.addText("Failure!");
			clear();
		}
	}
	
	private void decisionMade(Object node){
		Fraction probability = gameNet.getProbability(node);
		if (probability.doubleValue() > 0.5d){
			reaction++;
			viewController.addText("Positive reaction! Current reaction " + reaction);
		} else {
			reaction--;
			viewController.addText("Negative reaction! Current reaction " + reaction);
		}
		clear();
	}
	
	private void clear(){
		ready = false;
		viewController.processEventQueue();
		
		for (Object h : hiddenNodes){
			gameNet.removeProperty(h, "hidden");
		}
		
		for (Object t : targetNodes){
			gameNet.removeProperty(t, "target");
		}
		
	}
	
	
	

}
