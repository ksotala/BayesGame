package bayesGame.levelcontrollers;

import java.util.HashSet;
import java.util.Set;

import bayesGame.bayesbayes.OptionNode;
import bayesGame.bayesbayes.OptionNodeOption;
import bayesGame.bayesbayes.nodeCPD.DeterministicOR;
import bayesGame.minigame.DiscussionNet;
import bayesGame.minigame.MinigameController;
import bayesGame.viewcontrollers.DefaultViewController;
import bayesGame.viewcontrollers.ViewController;

public class ExamLevelScript {

	private LevelController controller;
	
	public ExamLevelScript() {
		controller = new LevelController();
		run();
	}

	private void run() {
		controller.addText("I am nervous.");
		controller.addText("I’m standing at the gates of the Academy, the school where my brother Opin was studying when he disappeared. When we asked the school to investigate, they were oddly reluctant, and told us to drop the issue.");
		controller.addText("The police were more helpful at first, until they got in contact with the school. Then they actually started threatening us, and told us that we would get thrown in prison if we didn’t forget about Opin.");
		controller.addText("That was three years ago. Ever since it happened, I’ve been studying hard to make sure that I could join the Academy once I was old enough, to find out what exactly happened to Opin. The answer lies somewhere inside the Academy gates, I’m sure of it.");
		controller.addText("Now I’m finally 16, and facing the Academy entrance exams. I have to do everything I can to pass them, and I have to keep my relation to Opin a secret, too.");
		controller.addText("???: 'Hey there.'");
		controller.addText("Eep! Someone is talking to me! Is he another applicant, or a staff member? Wait, let me think… I’m guessing that applicant would look a lot younger than staff members! So, to find that out… I should look at him!");
		controller.addProcessEventQueue();
		
		// viewController.dispose();
		
		DiscussionNet net = new DiscussionNet();
		net.addNode("Student", true);
		
		OptionNode youngLooking = new OptionNode("Young-looking");
		OptionNodeOption lookHim = new OptionNodeOption("Look at him");
		lookHim.setPositiveResponse("Short for an adult, face has boyish look, teenagerish clothes... yeah, he looks young!");
		youngLooking.addOption(lookHim);
		net.addNode(youngLooking, new DeterministicOR(), "Student");
		net.setTrueValue("Young-looking", true);
		
		Set<Object> targets = new HashSet<Object>();
		targets.add("Student");
		
		net.updateBeliefs();
		
		MinigameController minigameController = new MinigameController(net, targets);
		minigameController.setHiddenNodes(targets);
		
		controller.addMinigame(minigameController, 0, new Object[]{""});
		
		controller.addText("He's young, so he has to be a student!");
		controller.addText("...I feel like I’m overthinking things now.");
		controller.addText("...he’s looking at me.");
		controller.addText("I’m guessing he’s either waiting for me to respond, or there’s something to see behind me, and he’s actually looking past me. If there isn’t anything behind me, then I know that he must be waiting for me to respond!");
		controller.addText("Maybe there's a monster behind me, and he's paralyzed with fear! I should check that possibility before it eats me!");
		controller.addProcessEventQueue();
		
		DiscussionNet responseNet = new DiscussionNet();
		
		responseNet.addNode("Waiting for you", true);
		
		OptionNode monster = new OptionNode("Attacking monster");
		OptionNodeOption behindYou = new OptionNodeOption("Look behind you");
		behindYou.setNegativeResponse("You see nothing there. Besides trees, that is.");
		behindYou.setPositiveResponse("You see nothing there. Besides trees, that is.");
		monster.addOption(behindYou);
		responseNet.addNode(monster);
		responseNet.setTrueValue("Attacking monster", false);
		
		responseNet.addDeterministicOr("Looks at you", "Waiting for you", "Attacking monster");
		responseNet.observe("Looks at you", true);
		
		Set<Object> responseTargets = new HashSet<Object>();
		responseTargets.add("Waiting for you");
		
		minigameController = new MinigameController(responseNet, responseTargets);
		minigameController.setHiddenNodes(responseTargets);
		
		controller.addMinigame(minigameController, 0, new Object[]{""});
		
		controller.addText("Boy: 'Umm, are you okay?'");
		controller.addText("'Yeah, sorry. I just… you were looking in my direction, and I wasn’t sure of whether you were expecting me to reply, or whether there was a monster behind me.'");
		controller.addText("He blinks.");
		controller.addText("'You thought that there was a reasonable chance for a monster to be behind you?'");
		controller.addText("'Yeah...'");
		controller.addText("I’m embarrassed to admit it, but I’m not really sure of what the probability of a monster having snuck up behind me really should have been.");
		controller.addText("y studies have entirely focused on getting into this school, and Monsterology isn’t one of the subjects on the entrance exam!");
		controller.addText("I just went with a 50-50 chance since I didn’t know any better.");
		controller.addText("'Okay, look. Monsterology is my favorite subject. Monsters avoid the Academy, since it’s surrounded by a mystical protective field. There’s no chance of them getting even near! 0 percent chance.'");
		controller.addText("'Oh. Okay.'");
		controller.addText("[Your model of the world has been updated! The prior of the variable 'Monster Near The Academy' is now 0%.]");
		
		
		
		
		controller.run();
		
		// controller.addText("");
		// controller.addText("???: 'Um, are you okay?'");
		
	}
	
	

}
