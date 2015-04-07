package bayesGame.minigame;

import java.util.Random;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.bayesbayes.nodeCPD.BayesCPD;
import bayesGame.bayesbayes.nodeCPD.DeterministicAND;
import bayesGame.bayesbayes.nodeCPD.DeterministicOR;
import bayesGame.bayesbayes.nodeCPD.RandomCPD;
import bayesGame.fluff.RandomSubjectVariable;

public class RandomNet {

	private static String nextNode;
	private static String nodePointer;
	private static DiscussionNet net;
	private static RandomSubjectVariable randomVariable;
	
	public RandomNet() {
		// TODO Auto-generated constructor stub
	}
	
	public static DiscussionNet generateNet(int components){
		net = new DiscussionNet();
		randomVariable = new RandomSubjectVariable();
		
		nextNode = randomVariable.getNewRandomTerm();
		nodePointer = nextNode;
		addSingleNode();
		nextNode = randomVariable.getNewRandomTerm();
		
		Random rn = new Random();

		for (int x = 0; x < components; x++){
			if ((x > 0) && (x % 3 == 0)){
				nodePointer = randomVariable.getOldRandomTerm();
			}
			int structure = rn.nextInt(3);
			switch (structure){
			case 0: 
				indirectEffect();
				break;
			case 1:
				commonCause();
				break;
			case 2:
				commonEffect();
				break;
			}
		}
		
		net.updateBeliefs();
		
		return net;
	}

	private static void indirectEffect() {
		addSingleNodeWithParents();
		nodePointer = nextNode;
		nextNode = randomVariable.getNewRandomTerm();
		
		addSingleNodeWithParents();
		nodePointer = nextNode;
		nextNode = randomVariable.getNewRandomTerm();
	}
	
	private static void commonCause() {
		addSingleNodeWithParents();
		nextNode = randomVariable.getNewRandomTerm();
		
		addSingleNodeWithParents();
		nodePointer = nextNode;
		nextNode = randomVariable.getNewRandomTerm();
	}
	
	private static void commonEffect(){
		
		addSingleNode();
		String justAddedNode = nextNode;
		nextNode = randomVariable.getNewRandomTerm();
		
		DeterministicAND AndCPDOption = new DeterministicAND();
		DeterministicOR OrCPDOption = new DeterministicOR();
		RandomCPD RandomNode = new RandomCPD(AndCPDOption, OrCPDOption);
		
		net.addNodeWithParents(nextNode, RandomNode, nodePointer, justAddedNode);
		nodePointer = nextNode;
		nextNode = randomVariable.getNewRandomTerm();
	}
	
	private static void addSingleNode(){
		Random rn = new Random();
		int oneOf = rn.nextInt(3) + 2;
		net.addNode(nextNode);
		Fraction probability = Fraction.getReducedFraction(1, oneOf);
		net.setProbabilityOfUntrue(nextNode, probability);
		net.setProbabilityOfUntrue(nextNode, Fraction.ONE.subtract(probability), nextNode);
	}
	
	
	private static void addSingleNodeWithParents(){
		Random rn = new Random();
		
		BayesCPD bayesCPDOption = new BayesCPD(Fraction.getReducedFraction(1, rn.nextInt(5)+1), Fraction.getReducedFraction(1, rn.nextInt(5)+1));
		BayesCPD bayesCPDOption2 = new BayesCPD(Fraction.getReducedFraction(1, rn.nextInt(5)+1), Fraction.getReducedFraction(1, rn.nextInt(5)+1));
		DeterministicOR orCPDOption = new DeterministicOR();
		RandomCPD randomNode = new RandomCPD(bayesCPDOption, bayesCPDOption2, orCPDOption);
		
		net.addNodeWithParents(nextNode, randomNode, nodePointer);
	}

}
