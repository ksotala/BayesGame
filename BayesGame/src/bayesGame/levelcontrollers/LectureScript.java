package bayesGame.levelcontrollers;

import java.util.HashSet;
import java.util.Random;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.bayesbayes.nodeCPD.BayesCPD;
import bayesGame.bayesbayes.nodeCPD.DeterministicAND;
import bayesGame.bayesbayes.nodeCPD.DeterministicOR;
import bayesGame.bayesbayes.nodeCPD.RandomCPD;
import bayesGame.minigame.DiscussionNet;
import bayesGame.minigame.LearningController;
import bayesGame.minigame.RandomNet;
import bayesGame.world.TutorialMessages;

public class LectureScript extends Script {
	
	private LevelController controller;

	public LectureScript() {
		controller = new LevelController();
	}
	
	public LectureScript(LevelController controller) {
		this.controller = controller;
	}

	public void run() {
		ChoiceMenu choice = new ChoiceMenu();
		
		String s0 = "You go to the lecture of Professor Alt, the famous psychologist. Unfortunately, while he's famous at doing research, he's also famous at not being especially easy to comprehend.";
		String s2 = "'Anyway, I hope that was enlightening. If there's anything you didn't understand, I'll leave it as a homework exercise to work it out. Thinking keeps your neurotransmitter juices flowing! Heh heh.'";
		String s3 = "That, what. Sigh. You'll have to try to work out an example of this in your head if you want to understand anything.";
		String s4 = "The graph your best interpretation of what Professor Alt said. The variables represent claims about psychology that could be true or false. You can look them up by right-clicking on them, but doing so costs a point of mental energy. Press done when you don't want to look up any more claims. For any that you didn't look up, you'll assume it to be true or false depending on which seems more likely at the moment. You're then shown how things really are: each claim you looked up, or guessed correctly, counts as correct. If more than half of the claims were judged correctly, you'll get points towards your Psychology skill.";
		String s5 = "You can review these instructions later on by clicking the 'Help' button in this minigame. You can also click on the 'Hints' button if you're not sure of what you should do.";

		
		choice.setDescription("Choose psychology lecture");
		
		RandomNet randomNet = new RandomNet();
		DiscussionNet easy = randomNet.generateNet(2);

		Random rn = new Random();

		LearningController easyController = new LearningController(easy);
		easyController.setHelpReference("LectureTutorial");
		
		ChoiceMenuChoice easyChoice = new ChoiceMenuChoice();
		easyChoice.setDescription("Introductory psychology");
		easyChoice.setGameController(easyController);
		
		if (TutorialMessages.contains("LectureTutorial")){
			easyChoice.setPreamble(randomNet.getVerbalDescription());
		} else {
			easyChoice.setPreamble(s0, randomNet.getVerbalDescription(), s2, s3, s4, s5);
		}
		
		choice.addChoice(easyChoice);
		
		DiscussionNet medium = randomNet.generateNet(4);

		LearningController mediumController = new LearningController(medium);
		mediumController.setHelpReference("LectureTutorial");
	
		ChoiceMenuChoice mediumChoice = new ChoiceMenuChoice();
		mediumChoice.setDescription("Intermediate psychology");
		mediumChoice.setGameController(mediumController);
		if (TutorialMessages.contains("LectureTutorial")){
			mediumChoice.setPreamble(randomNet.getVerbalDescription());
		} else {
			mediumChoice.setPreamble(s0, randomNet.getVerbalDescription(), s2, s3, s4, s5);
		}

		
		choice.addChoice(mediumChoice);
		
		DiscussionNet hard = randomNet.generateNet(6);

		LearningController hardController = new LearningController(hard);
		hardController.setHelpReference("LectureTutorial");

		ChoiceMenuChoice hardChoice = new ChoiceMenuChoice();
		hardChoice.setDescription("Advanced psychology");
		hardChoice.setGameController(hardController);
		if (TutorialMessages.contains("LectureTutorial")){
			hardChoice.setPreamble(randomNet.getVerbalDescription());
		} else {
			hardChoice.setPreamble(s0, randomNet.getVerbalDescription(), s2, s3, s4, s5);
		}
		
		choice.addChoice(hardChoice);
		choice.setReturnScript(new MeetingCherylScript());
		
		if (!TutorialMessages.contains("LectureTutorial")){
			TutorialMessages.put("LectureTutorial", s4);
		}
		
		controller.addChoiceMenu(choice);
		controller.run();
	}




	
	@Override
	public void setController(LevelController controller) {
		this.controller = controller;
		
	}

}
