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
		choice.setDescription("Choose psychology lecture");
		
		DiscussionNet easy = new RandomNet().generateNet(2);
				
		Random rn = new Random();

		LearningController easyController = new LearningController(easy);
		
		ChoiceMenuChoice easyChoice = new ChoiceMenuChoice();
		easyChoice.setDescription("Introductory psychology");
		easyChoice.setGameController(easyController);
		
		choice.addChoice(easyChoice);
		
		DiscussionNet medium = new RandomNet().generateNet(4);

		LearningController mediumController = new LearningController(medium);
	
		ChoiceMenuChoice mediumChoice = new ChoiceMenuChoice();
		mediumChoice.setDescription("Intermediate psychology");
		mediumChoice.setGameController(mediumController);
		
		choice.addChoice(mediumChoice);
		
		DiscussionNet hard = new RandomNet().generateNet(6);

		LearningController hardController = new LearningController(hard);

		ChoiceMenuChoice hardChoice = new ChoiceMenuChoice();
		hardChoice.setDescription("Advanced psychology");
		hardChoice.setGameController(hardController);
		
		choice.addChoice(hardChoice);
		
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
