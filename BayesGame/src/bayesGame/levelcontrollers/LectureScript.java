package bayesGame.levelcontrollers;

import java.util.Random;

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
		
		String s0 = "You go to the lecture of Professor Alt, the famous psychologist. Unfortunately, while he's famous at doing research, he's also famous at not being especially easy to comprehend. (Press space to proceed, or click on this text area and then press space.)";
		String s2 = "'Anyway, I hope that was enlightening. If there's anything you didn't understand, I'll leave it as a homework exercise to work it out. Thinking keeps your neurotransmitter juices flowing! Heh heh.'";
		String s3 = "That, what. Sigh. You'll have to try to work out an example of this in your head if you want to understand anything.";
		String s4 = "The graph your best interpretation of what Professor Alt said. The variables represent claims about psychology that could be true or false. You can look them up ('observe' them) by clicking on them, but doing so costs a point of mental energy. Press done when you don't want to look up any more claims. For any that you didn't look up, you'll assume it to be true if it has more than a 50% probability, and assume it to be false otherwise. You're then shown how things really are: each claim you looked up, or guessed correctly, counts as correct. If more than one half of the claims were judged correctly, you'll get points towards your Psychology skill."
				+ "\n\nHints:\n * The more guesses you get right, the more points you'll get. The closer a variable is to 50% at the time when you press 'Done', the less likely your guess is to be right. The closer it is to either 0% or 100%, the more likely your guess is to be right"
				+ "\n* Sometimes a network is such that you don't need to observe any variables in order to get all guesses correct; sometimes you could spend all of your energy and not get them all correct. Part of the challenge is figuring out when it's useful to observe nodes and when it's not, since you only have a limited amount of energy per day. (Your energy replenishes at the end of the day.)"
				+ "\n* Useful questions to ask when trying to pick a node to observe: how many other nodes would be influenced if I observed this one? How much could I reduce uncertainty (drive the probabilities of nodes away from 50%) by this click?";
		String s5 = "You can review these instructions later on by clicking the 'Help' button in this minigame.";

		choice.setDescription("Choose psychology lecture");
		
		RandomNet randomNet = new RandomNet();
		DiscussionNet easy = randomNet.generateNet(2, 0);

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
		
		DiscussionNet medium = randomNet.generateNet(3, 1);
		
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
		
		DiscussionNet hard = randomNet.generateNet(5, 1);

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
		
		// choice.addChoice(hardChoice);
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
