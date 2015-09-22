package bayesGame.minigame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.levelcontrollers.LevelController;
import bayesGame.levelcontrollers.Script;
import bayesGame.ui.InterfaceView;
import bayesGame.viewcontrollers.MinigameViewController;
import bayesGame.viewcontrollers.ViewController;
import bayesGame.world.GameCharacters;
import bayesGame.world.PlayerCharacter;
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
	
	private Object successResult;
	private Object failureResult;
	
	private boolean ready = false;
	private int turnsTaken;
	
	private boolean energycost = false;
	private PlayerCharacter PC;
	
	private LevelController owner;

	private String helpReference;
	
	public MinigameController(DiscussionNet gameNet, Set<Object> targetNodes) {
		this.gameNet = gameNet;
		this.targetNodes = targetNodes;
		this.gameMode = 0;
		this.reaction = 0;
		
		this.successResult = "";
		this.failureResult = "Restart";
		
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
	
	public void enableEnergyCost(){
		this.PC = GameCharacters.PC;
		energycost = true;
	}
	
	public void hideTargetNodes(){
		hiddenNodes.addAll(targetNodes);
	}
	
	public void hideTargetNodeFamily(){
		for (Object o : targetNodes){
			List<Object> targetNodeFamily = gameNet.getFamily(o);
			hiddenNodes.addAll(targetNodeFamily);
		}
	}
	
	public void randomizeHiddenNodes(double percentage){
		if (percentage > 1){
			percentage = 1;
		} else if (percentage < 0){
			percentage = 0;
		}
		
		int nodecount = gameNet.getGraph().getVertexCount();
		int hidden_node_count = (int)(percentage * nodecount);
		if (hidden_node_count == 0){
			hidden_node_count = 1;
		}
		if (hidden_node_count == nodecount){
			hidden_node_count--;
		}
		
		int nodes_hidden_already = hiddenNodes.size();
		int nodes_to_hide = hidden_node_count - nodes_hidden_already;
		if (nodes_to_hide > 0){
			randomizeHiddenNodes(nodes_to_hide);
		}
	}
	
	public void randomizeHiddenNodes(int amount){
		Random random = new Random();
		BayesNode[] nodes = gameNet.getGraph().getVertices().toArray(new BayesNode[gameNet.getGraph().getVertexCount()]);
		
		for(int i = 0; i < amount; i++){
			// pick a random node which is not already in the set of hidden nodes
			int item;
			do{
				item = random.nextInt(nodes.length);
			} while (!hiddenNodes.contains(nodes[item].type));
			hiddenNodes.add(nodes[item].type);
		}
	}
	
	public void randomizeTargetNodes(int amount){
		Random random = new Random();
		BayesNode[] nodes = gameNet.getGraph().getVertices().toArray(new BayesNode[gameNet.getGraph().getVertexCount()]);
		
		targetNodes.clear();
		
		for(int i = 0; i < amount; i++){
			int item = random.nextInt(nodes.length);
			targetNodes.add(nodes[item].type);
		}
	}
	
	public void randomizeTargetNode(){
		List<Object> nodefamily;
		do {
			randomizeTargetNodes(1);
			Object[] nodearray = targetNodes.toArray();
			Object node = nodearray[0];
			nodefamily = gameNet.getFamily(node);
		} while (nodefamily.size() < 2);
	}
	
	public void setDiscussions(Map<Object,String[]> discussions){
		this.discussions = discussions;
	}
	
	public void setHelpReference(String string) {
		this.helpReference = string;
	}
	
	public void startGame(){
		startGame(0, new Object[0]);
	}
	
	// game mode 1 = figure out the contents of target nodes
	public void startGame(int timeLimit, Object[] knowledges){
		this.timeLimit = timeLimit;
		turnsTaken = 0;
		viewController.setHelpReference(helpReference);
		
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
		if (ready){
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
				clear(true);
			} else if (turnsTaken == timeLimit && timeLimit > 0){
				viewController.showText("Failure!");
				clear(false);
			} else if (allTargetNodesKnown && gameMode == 0){
				if (energycost){
					finishedThinking();
				} else {
					clear(true);
				}
			}
		}
		
	}
	
	private void decisionMade(Object node){
		Fraction probability = gameNet.getProbability(node);
		if (probability.doubleValue() > 0.5d){
			reaction++;
			clear(true);
			// viewController.addText("Positive reaction! Current reaction " + reaction);
		} else {
			reaction--;
			clear(false);
			// viewController.addText("Negative reaction! Current reaction " + reaction);
		}
	}
	
	private void clear(boolean success){
		ready = false;
		viewController.processEventQueue();
		
		if (success){
			processGameEnd(successResult);
		} else {
			processGameEnd(failureResult);
		}
	}

	private void processGameEnd(Object resultType) {
		if (resultType.equals("Restart")){
			// TODO: Restart
		} else if (resultType instanceof Script){
			Script script = (Script)resultType;
			script.run();
		} else {
			owner.minigameCompleted(viewController);
		}
		
	}

	public void observeNode(Object type, OptionNodeOption option) {
		if (ready){
			if (!hiddenNodes.contains(type)){
				if ((!energycost) || PC.getEnergy() > 0){
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
					if (energycost){
						PC.useEnergy(1);
						viewController.showText("You observe the truth of " + type + " by looking it up, which costs you some mental energy. You have " + PC.getEnergy() + " points of energy left.");
					}
					viewController.updateStatusText();
					this.endOfTurn(timeSpent);
				} else if (energycost && PC.getEnergy() <= 0){
					viewController.showText("You're exhausted and can't think about this kind of thing anymore.");
				}
				
				
				

			}
		}
		
	}

	public void setOwner(LevelController levelController) {
		this.owner = levelController;
	}

	public void offerViewController(ViewController viewController) {
		viewController.giveControlTo(this.viewController);
	}

	public void setFailureResult(String string) {
		this.failureResult = string;
	}

	public void setSuccessResult(Script script) {
		this.successResult = script;
	}
	
	
	public void genericMessageReceived(){
		finishedThinking();
	}
	
	public void setLectureMode(boolean b) {
		viewController.setLectureMode(b);
	}
	
	public void finishedThinking(){
		
		int correct = 0;
		int score = 0;
		
		List<BayesNode> priorNodes = new ArrayList<BayesNode>();
		List<BayesNode> otherNodes = new ArrayList<BayesNode>();
		Map<BayesNode,Fraction> probabilities = new HashMap<BayesNode,Fraction>();
		
		for (BayesNode n : gameNet){
			if (targetNodes.contains(n.type)){
				Fraction probability = n.getProbability();
				probabilities.put(n, probability);
				if (n.cptName.equals("Prior")){
					priorNodes.add(n);
				} else {
					otherNodes.add(n);
				}
			}
		}
		
		int scoreThreshold = probabilities.size() / 2;
/*		if (probabilities.size() % 2 == 1){
			scoreThreshold++;
		} */
		
		priorNodes.addAll(otherNodes);
		
		viewController.clearText();
		
		viewController.showText("With " + probabilities.size() + " variables, you need to get more than " + scoreThreshold + " predictions correct in order to gain points.");
		for (BayesNode n : priorNodes){
			n.observe();
			gameNet.updateBeliefs();
			
			Fraction oldProbability = probabilities.get(n);
			boolean prediction = oldProbability.compareTo(Fraction.ONE_HALF) > 0;
			
			Fraction newProbability = n.getProbability();
			boolean actualValue = newProbability.equals(Fraction.ONE);
			
			String adjective ="";
			if (prediction == actualValue){
				correct++;
				adjective = " YAY! ";
				if (correct > scoreThreshold){
					score++;
					adjective = adjective + "SCORE! ";
				}
			} else {
				n.addProperty("misguessed");
			}
			
			if (correct <= scoreThreshold){
				viewController.showText("Variable " + n.type + ": had " + (int)(oldProbability.doubleValue()*100) + "% probability, so you guess that it's " + prediction + ". Is " + actualValue + ". " + adjective + "Correct predictions: " + correct + " (need " + (scoreThreshold+1 - correct) + " more), score: " + score);
			} else {
				viewController.showText("Variable " + n.type + ": had " + (int)(oldProbability.doubleValue()*100) + "% probability, so you guess that it's " + prediction + ". Is " + actualValue + ". " + adjective + "Correct predictions: " + correct + " score: " + score);
			}

			viewController.updateGraph();
			
		}
		
		if (correct == probabilities.size()){
			int scorebonus  = probabilities.size() / 2;
			score = score + scorebonus;
			viewController.showText("All predictions correct! Bonus score: " + scorebonus + ", total score: " + score);
		}
		
		GameCharacters.PC.pointsToSkill("Psychology", score, this);
		viewController.updateStatusText();
		
		clear(true);
	}

	public void showText(String string) {
		viewController.showText(string);
	}
	
	
	
	

}
