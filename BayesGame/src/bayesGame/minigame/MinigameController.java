package bayesGame.minigame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.levelcontrollers.LevelController;
import bayesGame.ui.InterfaceView;
import bayesGame.viewcontrollers.MinigameViewController;
import bayesGame.viewcontrollers.ViewController;
import bayesGame.bayesbayes.*;

public class MinigameController {

	private MinigameViewController viewController;
	
	private Map<Object,String[]> discussions;
	private DiscussionNet gameNet;
	private int timeLimit;
	private Set<Object> hiddenNodes;
	private Set<Object> targetNodes;
	
	private int gameMode;
	private int reaction;
	
	private boolean ready = false;
	private int turnsTaken;
	
	private LevelController owner;
	
	public MinigameController(DiscussionNet gameNet, Set<Object> targetNodes) {
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
		
		this.viewController = new MinigameViewController(this, gameNet);
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
		
		for (Object h : hiddenNodes){
			gameNet.addProperty(h, "hidden");
		}
		
		for (Object t : targetNodes){
			gameNet.addProperty(t, "target");
		}
		
		ready = true;
		viewController.display();
	}
	
	public void chooseNode(Object node){
		if (ready){
			if (!hiddenNodes.contains(node) && !gameNet.isObserved(node)){
				viewController.displayOptions(node);
			}
		}
	}
	
	private void endOfTurn(int timeTaken){
		turnsTaken = turnsTaken + timeTaken;
		
		boolean allTargetNodesKnown = true;
		for (Object o : targetNodes){
			if (gameNet.getProbability(o).doubleValue() > 0.0d && gameNet.getProbability(o).doubleValue() < 1.0d){
				allTargetNodesKnown = false;
				break;
			}
		}
		
		if (timeLimit > 0){
			viewController.showText("Turn " + turnsTaken + "/" + timeLimit);
		}
		
		
		if (allTargetNodesKnown && gameMode == 0 && timeLimit > 0){
			viewController.showText("Success!");
			viewController.showText("Clearing this level with " + (timeLimit - turnsTaken) + " turns to spare confers " + (timeLimit - turnsTaken) + "fame.");
			clear();
		} else if (turnsTaken == timeLimit && timeLimit > 0){
			viewController.showText("Failure!");
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
		
		owner.minigameCompleted(viewController);
	}

	public void observeNode(Object type, OptionNodeOption option) {
		if (ready){
			if (!hiddenNodes.contains(type)){
				boolean nodeTrue = gameNet.observe(type);
				int timeSpent = 1;
				
				if (option != null){
					timeSpent = option.getTimeSpent();
					viewController.showText(option.getDescription());
					String response;
					if (nodeTrue){
						response = option.getPositiveResponse();
					} else {
						response = option.getNegativeResponse();
					}
					viewController.showText(response);
					// viewController.displayPopup(option.getDescription(), response);
				}
				
				gameNet.updateBeliefs();
				viewController.addRefreshDisplay();
				viewController.processEventQueue();
				if (gameMode == 1 && targetNodes.contains(type)){
					decisionMade(type);
				}
				this.endOfTurn(timeSpent);
			}
		}
		
	}

	public void setOwner(LevelController levelController) {
		this.owner = levelController;
	}

	public void offerViewController(ViewController viewController) {
		viewController.giveControlTo(this.viewController);
	}
	
	
	

}
