package bayesGame.minigame;

import java.util.Random;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.bayesbayes.nodeCPD.BayesCPD;
import bayesGame.bayesbayes.nodeCPD.DeterministicAND;
import bayesGame.bayesbayes.nodeCPD.DeterministicOR;
import bayesGame.bayesbayes.nodeCPD.NodeCPD;
import bayesGame.bayesbayes.nodeCPD.RandomCPD;
import bayesGame.fluff.RandomSubjectVariable;

public class RandomNet {

	private String nextNode;
	private String nodePointer;
	private DiscussionNet net;
	private RandomSubjectVariable randomVariable;
	private RandomSubjectVariable subjectTerm;
	private int maxIsNodes = 1;
	private Random rn = new Random();
	private String verbalDescription;


	public RandomNet() {
		// TODO Auto-generated constructor stub
	}
	
	public DiscussionNet generateNet(int components){
		net = new DiscussionNet();
		verbalDescription = "'";
		randomVariable = new RandomSubjectVariable();
		subjectTerm = new RandomSubjectVariable(RandomSubjectVariable.PSYCHOLOGY_SET_VALUES);
		subjectTerm.shuffle();
		
		nextNode = randomVariable.getNewRandomTerm();
		nodePointer = nextNode;
		addPriorNode();
		nextNode = randomVariable.getNewRandomTerm();

		for (int x = 0; x < components; x++){
			if ((x > 0) && (x % 3 == 0)){
				randomizePointerLocation();
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
			verbalDescription = verbalDescription + getFillerSentence();
		}
		
		net.updateBeliefs();
		verbalDescription = verbalDescription + "Straightforward, no?'";
		
		return net;
	}
	
	public String getVerbalDescription(){
		return verbalDescription;
	}

	private String getFillerSentence() {
		String sentence = "";
		int sentenceInt = rn.nextInt(11);
		switch(sentenceInt){
		case 0:
			sentence = "This reminds me of my work on " + subjectTerm.getNewRandomTerm() + ". Anyway... ";
			break;
		case 1:
			sentence = "It's all about " + subjectTerm.getNewRandomTerm() + ", you see. ";
			break;
		case 2:
			sentence = "Never underestimate the importance of " + subjectTerm.getNewRandomTerm() + "! ";
			break;
		case 3:
			sentence = "Remember what I said about " + subjectTerm.getOldRandomTerm() + "? ";
			break;
		case 4:
			sentence = "How far we've gotten. I remember when we all thought that this was because of " + subjectTerm.getNewRandomTerm() + ". No, it's all about " + subjectTerm.getNewRandomTerm() + ". ";
			break;
		case 5:
			sentence = "(incomprehensible mumbling) ";
			break;
		case 6:
			sentence = "We figured this out because of my good friend, Dr. Prof. Weltschmerz. ";
			break;
		case 7:
			sentence = "Now where was I going with this, again? Oh yes, " + subjectTerm.getNewRandomTerm() + ". ";
			break;
		case 8:
			sentence = "To think, we once spent a whole year figuring out how this relates to " + subjectTerm.getNewRandomTerm() + ", and now you younglings find out about it in five minutes, just by listening to me. ";
			break;
		case 9:
			sentence = "There are many things I could connect this with, but I'm going to connect it with " + subjectTerm.getNewRandomTerm() + ". I'm sure you see why. ";
			break;
		case 10:
			sentence = "(someone desperately tries to raise and wave their hand, but the professor doesn't notice) ";
			break;
		}

		return sentence;
	}

	private void indirectEffect() {
		addSingleNodeWithParents();
		nodePointer = nextNode;
		nextNode = randomVariable.getNewRandomTerm();
		
		verbalDescription = verbalDescription + getFillerSentence();
		
		addSingleNodeWithParents();
		nodePointer = nextNode;
		nextNode = randomVariable.getNewRandomTerm();
	}
	
	private void commonCause() {
		addSingleNodeWithParents();
		nextNode = randomVariable.getNewRandomTerm();
		
		verbalDescription = verbalDescription + getFillerSentence();
		
		addSingleNodeWithParents();
		nodePointer = nextNode;
		nextNode = randomVariable.getNewRandomTerm();
	}
	
	private void commonEffect(){
		addPriorNode();
		String justAddedNode = nextNode;
		nextNode = randomVariable.getNewRandomTerm();
		
		verbalDescription = verbalDescription + getFillerSentence();
		
		DeterministicAND AndCPDOption = new DeterministicAND();
		DeterministicOR OrCPDOption = new DeterministicOR();
		RandomCPD RandomNode = new RandomCPD(AndCPDOption, OrCPDOption);
		
		while (justAddedNode.equals(nodePointer)){
			randomizePointerLocation();
		}
		
		net.addNodeWithParents(nextNode, RandomNode, nodePointer, justAddedNode);
		
		NodeCPD chosenCPD = RandomNode.getChosenCPD();
		if (chosenCPD instanceof DeterministicAND){
			verbalDescription = verbalDescription + "Now, it logically follows that if " + nodePointer + " and " + justAddedNode + ", then " + nextNode + ". ";
		} else {
			verbalDescription = verbalDescription + "As I'm sure is too obvious to state at this point, if " + nodePointer + " or " + justAddedNode + ", then " + nextNode + ". ";
		}
		
		if (rn.nextBoolean()){
			nodePointer = nextNode;
		} else {
			nodePointer = justAddedNode;
		}
		
		
		nextNode = randomVariable.getNewRandomTerm();
	}
	
	private void addPriorNode(){
		int prior = rn.nextInt(3);
		net.addNode(nextNode);
		Fraction probability;
		
		if (prior == 0){
			probability = Fraction.ONE_THIRD;
			verbalDescription = verbalDescription + "As you all know, there's a chance that " + nextNode + ". ";
		} else if (prior == 1){
			probability = Fraction.ONE_HALF;
			verbalDescription = verbalDescription + "Now, it could maybe be that " + nextNode + ". ";
		} else {
			probability = Fraction.TWO_THIRDS;
			verbalDescription = verbalDescription + "Obviously, it's likely that " + nextNode + ". ";
		}
		
		net.setProbabilityOfUntrue(nextNode, probability);
		net.setProbabilityOfUntrue(nextNode, Fraction.ONE.subtract(probability), nextNode);
	}
	
	
	private void addSingleNodeWithParents(){
		BayesCPD bayesCPDNeutral = new BayesCPD(getFraction(4,5), getFraction(4,5));
		Fraction unlikelyFraction = Fraction.getReducedFraction(1, rn.nextInt(5)+1);
		int likelyNumber = rn.nextInt(4)+2;
		Fraction likelyFraction = Fraction.getReducedFraction(likelyNumber, likelyNumber+1);
		
		BayesCPD bayesCPDLikelyIfTrue = new BayesCPD(likelyFraction, unlikelyFraction);
		BayesCPD bayesCPDLikelyIfFalse = new BayesCPD(unlikelyFraction, likelyFraction);
		
		DeterministicOR orCPDOption = new DeterministicOR();
		
		int chosenCPD = rn.nextInt(4);
		NodeCPD randomNode = null;
		
		switch(chosenCPD){
		case 0:
			randomNode = bayesCPDNeutral;
			verbalDescription = verbalDescription + "Then whether or not " + nextNode + " depends on whether " + nodePointer + ". ";
			break;
		case 1:
			randomNode = bayesCPDLikelyIfTrue;
			verbalDescription = verbalDescription + "Hmm... so then it is likely that if  " + nodePointer + ", then " + nextNode + ". ";
			break;
		case 2:
			randomNode = bayesCPDLikelyIfFalse;
			verbalDescription = verbalDescription + "Might then " + nextNode + "? Maybe. I think it's likely, at least if not " + nodePointer + ". ";
			break;
		case 3:
			randomNode = orCPDOption;
			verbalDescription = verbalDescription + "In which case, " + nextNode + "! Isn't science fantastic? ";
			break;
		}
		
		net.addNodeWithParents(nextNode, randomNode, nodePointer);
	}
	
	private Fraction getFraction(int maxNumerator, int maxDenominator){
		int numerator = rn.nextInt(maxNumerator+1)+1;
		int max = maxDenominator+1;
		int min = numerator+1;
		int denominator = getRandomWithinRange(min,max);
		
		return Fraction.getReducedFraction(numerator, denominator);
	}
	
	private int getRandomWithinRange(int min, int max){
		return rn.nextInt((max-min)+1) + min;
	}
	
	private void randomizePointerLocation(){
		do{
			nodePointer = randomVariable.getOldRandomTerm();
		}
		while (nodePointer.equals(nextNode));
	}
	
}
