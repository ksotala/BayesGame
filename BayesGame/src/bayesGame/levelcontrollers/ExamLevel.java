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

public class ExamLevel {

	private ViewController viewController;
	
	public ExamLevel() {
		viewController = new DefaultViewController();
		run();
	}

	private void run() {
		viewController.addText("I am nervous.");
		viewController.addText("I’m standing at the gates of the Academy, the school where my brother Opin was studying when he disappeared. When we asked the school to investigate, they were oddly reluctant, and told us to drop the issue.");
		viewController.addText("The police were more helpful at first, until they got in contact with the school. Then they actually started threatening us, and told us that we would get thrown in prison if we didn’t forget about Opin.");
		viewController.addText("That was three years ago. Ever since it happened, I’ve been studying hard to make sure that I could join the Academy once I was old enough, to find out what exactly happened to Opin. The answer lies somewhere inside the Academy gates, I’m sure of it.");
		viewController.addText("Now I’m finally 16, and facing the Academy entrance exams. I have to do everything I can to pass them, and I have to keep my relation to Opin a secret, too.");
		viewController.addText("???: 'Hey there.'");
		viewController.addText("Eep! Someone is talking to me! Is he another applicant, or a staff member? Wait, let me think… I’m guessing that applicant would look a lot younger than staff members! So, to find that out… I should look at him!");
		viewController.processEventQueue();
		
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
		
		MinigameController controller = new MinigameController(net, targets);
		controller.setHiddenNodes(targets);
		
		controller.startGame(0, new Object[]{""});
		
		
		
		
		/*
		viewController.addText("He's young, so he has to be a student!");
		viewController.addText("...I feel like I’m overthinking things now.");
		viewController.addText("...he’s looking at me.");
		viewController.addText("I’m guessing he’s either waiting for me to respond, or there’s something to see behind me, and he’s actually looking past me. If there isn’t anything behind me, then I know that he must be waiting for me to respond!");
		viewController.processEventQueue();
		
		viewController.addText("");
		viewController.addText("???: 'Um, are you okay?'");
		*/
	}
	
	

}
