package bayesGame.levelcontrollers;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.bayesbayes.nodeCPD.BayesCPD;
import bayesGame.bayesbayes.nodeCPD.DeterministicOR;
import bayesGame.minigame.DiscussionNet;
import bayesGame.minigame.MinigameController;

public class IntroScript extends Script {
	
	public IntroScript(LevelController controller) {
		this.controller = controller;
	}

	@Override
	public void run() {
		controller.addText("You're about to see a square box. It represents a variable that can be either true or false, and your task is to discover which one it is.");
		controller.addText("The grid at the bottom of each represents its probability: it has a 50-50 chance of being true. It's also surrounded by a fog of ignorance (the grey border), but you can penetrate that fog by right-clicking on it to see its value. Press space to see the box, and then right-click on the box to observe it.");
		controller.addProcessEventQueue();
		
		DiscussionNet net = new DiscussionNet();
		net.addNode("A");
		net.setTrueValue("A", true);

		Set<Object> targets = new HashSet<Object>();
		targets.add("A");
		
		MinigameController minigameController = new MinigameController(net, targets);
		
		controller.addMinigame(minigameController, 0, new Object[]{""});
		
		controller.addText("(Left-click on this text field to return the focus here, and then press space to continue.)");
		controller.addText("There you go! Now that you've observed the variable, you know its value for a certainty, and it happened to be true. This is reflected by the probability of it becoming 100%.");
		controller.addText("Now let's go with something slightly harder. You're going to see two nodes instead, and while one of them is still surrounded by a fog of ignorance (grey), the other has a wall of ignorance (black), meaning that you cannot observe it directly. You can still observe the one with a grey border, however.");
		controller.addProcessEventQueue();
		
		net = new DiscussionNet();
		net.addNode("A");
		net.setTrueValue("A", false);

		net.addNodeWithParents("B", new DeterministicOR(), "A");
		net.updateBeliefs();
		
		targets = new HashSet<Object>();
		targets.add("B");
		
		minigameController = new MinigameController(net, targets);
		minigameController.setHiddenNodes(targets);
		
		controller.addMinigame(minigameController, 0, new Object[]{""});
		
		controller.addText("(Again, left-click on this text field to return the focus here, and then press space to continue.)");
		controller.addText("Wait, what happened? Not only the variable that you observed, but also the variable that you didn't observe, turned out to be false!");
		controller.addText("The reason is that the bottom variable is an 'IS-node'. It always has the same value as its parent node.");
		controller.addText("Since it turned out that the top variable was false, the IS-node had to be false too.");
		controller.addText("The same logic also works in reverse - if you observe an IS-node, that will tell you what value its parent has. Let's look at that case.");
		controller.addProcessEventQueue();
		
		net = new DiscussionNet();
		net.addNode("A");
		net.setTrueValue("A", false);

		net.addNodeWithParents("B", new DeterministicOR(), "A");
		net.updateBeliefs();
		
		targets = new HashSet<Object>();
		targets.add("A");
		
		minigameController = new MinigameController(net, targets);
		minigameController.setHiddenNodes(targets);
		
		controller.addMinigame(minigameController, 0, new Object[]{""});
				
		controller.addText("See?");
		controller.addText("Next, let's have an 'OR' node. This one is true if at least one of its two parents is true.");
		controller.addText("The probability of the 'OR' node is displayed a little differently - as four boxes. The size of the one on the top left reflects the probability that both parents are true, and the size of the one on the bottom right is the probability that both are false.");
		controller.addText("The size of the other two reflect the probability of exactly one of the parents being true. Three of the boxes are colored true, and the bottom right one is colored false, to indicate that the node is false if both of its parents are false and true otherwise.");
		controller.addProcessEventQueue();
		
		net = new DiscussionNet();
		net.addNode("A");
		net.addNode("B");
		net.addNodeWithParents("Or", new DeterministicOR(), "A", "B");
		net.setTrueValue("A", false);
		net.setTrueValue("B", false);
		net.updateBeliefs();
		
		targets = new HashSet<Object>();
		targets.add("Or");
		
		minigameController = new MinigameController(net, targets);
		minigameController.setHiddenNodes(targets);
		
		controller.addMinigame(minigameController, 0, new Object[]{""});
		
		controller.addText("You noticed that when you observed the values of one of the node's parents, its number of boxes went down to two. That was because finding out that one of the parents was false eliminated the 'true-true' possibility and one of the 'true-false' possibilities, leaving only two remaining possibilities.");
		controller.addText("And observing the second parent left only one possibility.");
		controller.addText("Now the boxes aren't necessarily all of the same size. Suppose one of the parents would have had a 25% chance of being true, and the other one a 75% chance of being true. You can see that in that case, the sizes of the boxes are proportionately different.");
		controller.addProcessEventQueue();
		
		net = new DiscussionNet();
		net.addNode("A");
		net.addNode("B");
		net.addNodeWithParents("Or", new DeterministicOR(), "A", "B");
		net.setProbabilityOfUntrue("A", Fraction.ONE_QUARTER);
		net.setProbabilityOfUntrue("A", Fraction.THREE_QUARTERS, "A");
		net.setProbabilityOfUntrue("B", Fraction.THREE_QUARTERS);
		net.setProbabilityOfUntrue("B", Fraction.ONE_QUARTER, "B");
		net.updateBeliefs();
		
		targets = new HashSet<Object>();
		targets.add("Or");
		
		minigameController = new MinigameController(net, targets);
		minigameController.setHiddenNodes(targets);
		
		controller.addMinigame(minigameController, 0, new Object[]{""});
		
		controller.addText("Let's introduce one more concept, and then you can go play the actual game.");
		controller.addText("The last concept is that of a Bayes node. Instead of its parents determining its truth value directly, the values of its parents only influence the probability of its truth value.");
		controller.addText("In this case, it has a 50% chance of being true if its parent is true, and a 90% chance of being true if its parent is false.");
		controller.addText("We'll leave it as a puzzle for you to figure out how to read the notation. Here, you need to observe both variables in order to find out their true values for certain, but observing one still shifts the probability of the other.");
		controller.addProcessEventQueue();
		
		net = new DiscussionNet();
		net.addNode("A");
		net.setProbabilityOfUntrue("A", Fraction.THREE_QUARTERS);
		net.setProbabilityOfUntrue("A", Fraction.ONE_QUARTER, "A");
		net.addNodeWithParents("B", new BayesCPD(Fraction.ONE_HALF, Fraction.getReducedFraction(4,5)), "A");
		net.updateBeliefs();
		
		targets = new HashSet<Object>();
		targets.add("A");
		targets.add("B");
		
		minigameController = new MinigameController(net, targets);
		controller.addMinigame(minigameController, 0, new Object[]{""});
		
		controller.addText("Congratulations, you're now familiar with the basic concepts of the game!");
		
		
		controller.addProcessEventQueue();
		controller.setNextScript(new LoopScript(controller));
		controller.run();
		
		
		
	}

}
