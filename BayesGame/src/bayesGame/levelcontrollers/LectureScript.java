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

public class LectureScript implements Script {
	
	private LevelController controller;

	public LectureScript() {
	}
	
	public void run() {
		controller = new LevelController();
		
		ChoiceMenu choice = new ChoiceMenu();
		choice.setDescription("Choose psychology lecture");
		
		DiscussionNet easy = new DiscussionNet();
		easy.addSkillNode("X", "Psychology1");
		easy.addSkillNode("Y", "Psychology1");
		easy.addSkillNode("Z", "Psychology1");
		
		easy.forceConnectNodes("X", "Y");
		easy.forceConnectNodes("Y", "Z");
		
		Random rn = new Random();
		easy.changeNodeCPD("Y", new BayesCPD(Fraction.getReducedFraction(1, rn.nextInt(5)+1), Fraction.getReducedFraction(1, rn.nextInt(5)+1)));
		easy.changeNodeCPD("Z", new BayesCPD(Fraction.getReducedFraction(1, rn.nextInt(5)+1), Fraction.getReducedFraction(1, rn.nextInt(5)+1)));
		easy.updateBeliefs();

		LearningController easyController = new LearningController(easy);
		
		ChoiceMenuChoice easyChoice = new ChoiceMenuChoice();
		easyChoice.setDescription("Introductory psychology");
		easyChoice.setGameController(easyController);
		
		choice.addChoice(easyChoice);
		
		DiscussionNet medium = new DiscussionNet();
		medium.addSkillNode("A", "Psychology2");
		medium.addSkillNode("B", "Psychology2");
		medium.addSkillNode("C", "Psychology2");
		medium.addSkillNode("D", "Psychology2");
		medium.addSkillNode("E", "Psychology2");
		medium.addSkillNode("X", "Psychology1");
		medium.addSkillNode("Y", "Psychology1");
		medium.addSkillNode("Z", "Psychology1");
		
		medium.forceConnectNodes("A", "C");
		medium.forceConnectNodes("B", "C");
		medium.forceConnectNodes("B", "D");
		medium.forceConnectNodes("C", "E");
		medium.forceConnectNodes("X", "Y");
		medium.forceConnectNodes("Y", "Z");
		medium.forceConnectNodes("Z", "D");
		
		medium.changeNodeCPD("Y", new BayesCPD(Fraction.getReducedFraction(1, rn.nextInt(5)+1), Fraction.getReducedFraction(1, rn.nextInt(5)+1)));
		medium.changeNodeCPD("Z", new BayesCPD(Fraction.getReducedFraction(1, rn.nextInt(5)+1), Fraction.getReducedFraction(1, rn.nextInt(5)+1)));
		medium.changeNodeCPD("C", new RandomCPD(new DeterministicOR(), new DeterministicAND()));
		medium.changeNodeCPD("D", new RandomCPD(new DeterministicOR(), new DeterministicAND()));
		medium.changeNodeCPD("E", new BayesCPD(Fraction.getReducedFraction(1, rn.nextInt(5)+1), Fraction.getReducedFraction(1, rn.nextInt(5)+1)));		
		
		medium.updateBeliefs();

		
		LearningController mediumController = new LearningController(medium);
		
		ChoiceMenuChoice mediumChoice = new ChoiceMenuChoice();
		mediumChoice.setDescription("Intermediate psychology");
		mediumChoice.setGameController(mediumController);
		
		choice.addChoice(mediumChoice);
		
		DiscussionNet hard = new DiscussionNet();
		hard.addSkillNode("A", "Psychology3");
		hard.addSkillNode("B", "Psychology3");
		hard.addSkillNode("C", "Psychology3");
		hard.addSkillNode("D", "Psychology3");
		hard.addSkillNode("E", "Psychology2");
		hard.addSkillNode("F", "Psychology3");
		hard.addSkillNode("G", "Psychology3");
		hard.addSkillNode("H", "Psychology2");
		hard.addSkillNode("I", "Psychology2");
		hard.addSkillNode("J", "Psychology2");
		hard.addSkillNode("X", "Psychology1");
		hard.addSkillNode("Y", "Psychology1");
		hard.addSkillNode("Z", "Psychology1");
		
		hard.forceConnectNodes("A", "C");
		hard.forceConnectNodes("B", "C");
		hard.forceConnectNodes("B", "D");
		hard.forceConnectNodes("C", "E");
		hard.forceConnectNodes("C", "F");
		hard.forceConnectNodes("D", "G");
		hard.forceConnectNodes("E", "G");
		hard.forceConnectNodes("F", "I");
		hard.forceConnectNodes("F", "J");
		hard.forceConnectNodes("G", "F");
		hard.forceConnectNodes("H", "A");
		hard.forceConnectNodes("X", "Y");
		hard.forceConnectNodes("Y", "Z");
		hard.forceConnectNodes("Z", "D");

		hard.changeNodeCPD("A", new BayesCPD(Fraction.getReducedFraction(1, rn.nextInt(5)+1), Fraction.getReducedFraction(1, rn.nextInt(5)+1)));
		hard.changeNodeCPD("C", new RandomCPD(new DeterministicOR(), new DeterministicAND()));
		hard.changeNodeCPD("D", new RandomCPD(new DeterministicOR(), new DeterministicAND()));
		hard.changeNodeCPD("E", new BayesCPD(Fraction.getReducedFraction(1, rn.nextInt(5)+1), Fraction.getReducedFraction(1, rn.nextInt(5)+1)));
		hard.changeNodeCPD("F", new RandomCPD(new DeterministicOR(), new DeterministicAND()));
		hard.changeNodeCPD("G", new RandomCPD(new DeterministicOR(), new DeterministicAND()));
		hard.changeNodeCPD("H", new BayesCPD(Fraction.getReducedFraction(1, rn.nextInt(5)+1), Fraction.getReducedFraction(1, rn.nextInt(5)+1)));
		hard.changeNodeCPD("I", new BayesCPD(Fraction.getReducedFraction(1, rn.nextInt(5)+1), Fraction.getReducedFraction(1, rn.nextInt(5)+1)));
		hard.changeNodeCPD("J", new BayesCPD(Fraction.getReducedFraction(1, rn.nextInt(5)+1), Fraction.getReducedFraction(1, rn.nextInt(5)+1)));
		hard.changeNodeCPD("Y", new BayesCPD(Fraction.getReducedFraction(1, rn.nextInt(5)+1), Fraction.getReducedFraction(1, rn.nextInt(5)+1)));
		hard.changeNodeCPD("Z", new BayesCPD(Fraction.getReducedFraction(1, rn.nextInt(5)+1), Fraction.getReducedFraction(1, rn.nextInt(5)+1)));
		
		hard.updateBeliefs();

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

}
