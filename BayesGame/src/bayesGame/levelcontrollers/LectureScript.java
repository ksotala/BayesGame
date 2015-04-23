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

public class LectureScript implements Script {
	
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
		String s4 = "You're about to see your best interpretation of what Professor Alt said. Click on any variable to inspect its value, but note that thinking about it costs you a point of mental energy. When you're done thinking about this, click 'done'.";
		
		choice.setDescription("Choose psychology lecture");
		
		RandomNet randomNet = new RandomNet();
		DiscussionNet easy = randomNet.generateNet(2);

		Random rn = new Random();

		LearningController easyController = new LearningController(easy);
		
		ChoiceMenuChoice easyChoice = new ChoiceMenuChoice();
		easyChoice.setDescription("Introductory psychology");
		easyChoice.setGameController(easyController);
		easyChoice.setPreamble(s0, randomNet.getVerbalDescription(), s2, s3, s4);
		
		choice.addChoice(easyChoice);
		
		DiscussionNet medium = randomNet.generateNet(4);

		LearningController mediumController = new LearningController(medium);
	
		ChoiceMenuChoice mediumChoice = new ChoiceMenuChoice();
		mediumChoice.setDescription("Intermediate psychology");
		mediumChoice.setGameController(mediumController);
		mediumChoice.setPreamble(s0, randomNet.getVerbalDescription(), s2, s3, s4);
		
		choice.addChoice(mediumChoice);
		
		DiscussionNet hard = randomNet.generateNet(6);

		LearningController hardController = new LearningController(hard);

		ChoiceMenuChoice hardChoice = new ChoiceMenuChoice();
		hardChoice.setDescription("Advanced psychology");
		hardChoice.setGameController(hardController);
		hardChoice.setPreamble(s0, randomNet.getVerbalDescription(), s2, s3, s4);
		
		choice.addChoice(hardChoice);
		choice.setReturnScript(new LoopScript(controller));
		
		controller.addChoiceMenu(choice);
		controller.run();
	}



	@Override
	public void MinigameCompleted(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void QueueEmpty() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setController(LevelController controller) {
		this.controller = controller;
		
	}

}
