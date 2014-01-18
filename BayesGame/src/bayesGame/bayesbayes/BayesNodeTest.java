package bayesGame.bayesbayes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.util.Pair;
import org.junit.Test;

public class BayesNodeTest {

	@Test
	public final void testBayesNodeObject() {
		BayesNode testNode = new BayesNode("Boo");
		Object[] scope = testNode.scope;
		ArrayList<Fraction> fractions = new ArrayList<Fraction>();
		
		assertEquals("The node's scope should contain exactly 1 variable", 1, scope.length);
		assertEquals("The node's potential should contain 2 items", 2, testNode.getPotential().length);
		
		Fraction fraction = Fraction.ZERO;
		fraction = fraction.add(testNode.getPotential()[0]);
		assertEquals("The probability of 'Boo' being untrue should be .5", fraction, Fraction.ONE_HALF);
		
		fraction = fraction.add(testNode.getPotential()[1]);
		
		assertEquals("The sum of node's potentials should equal 1", 1, fraction.doubleValue(), 0.001);
		
	}
	
	
	
	
	@Test
	public final void testObserveTrueMarginalProbability(){
		String type = "Badger Badger Badger Mushroom Mushroom";
		BayesNode testNode = new BayesNode(type);
		testNode.observe(true);
		
		assertEquals("If we've observed badger, we should get P = 1 for badger", Fraction.ONE, testNode.getProbability());
		assertEquals("If we've observed badger, we should get P = 0 for !badger", Fraction.ZERO, testNode.getNormalizedMarginalPotential(type)[1]);
	}
	
	@Test
	public final void testObserveFalseMarginalProbability(){
		String type = "Badger Badger Badger Mushroom Mushroom";
		BayesNode testNode = new BayesNode(type);
		testNode.observe(false); 
				
		assertEquals("If we've observed !badger, we should get P = 0 for badger", Fraction.ZERO, testNode.getProbability());
		assertEquals("If we've observed !badger, we should get P = 1 for !badger", Fraction.ONE, testNode.getNormalizedMarginalPotential(type)[1]);
	}
	
	@Test
	public final void testObserveTrue(){
		String type = "Badger Badger Badger Mushroom Mushroom";
		BayesNode testNode = new BayesNode(type);
		testNode.observe(true);
		
		assertEquals("If we've observed badger, we should get P = .5 for unnormalized badger", Fraction.ONE_HALF, testNode.getMarginalPotential(type)[0]);
		assertEquals("If we've observed badger, we should get P = 0 for unnormalized !badger", Fraction.ZERO, testNode.getMarginalPotential(type)[1]);
	}
	
	@Test
	public final void testObserveReset(){
		String type = "Badger Badger Badger Mushroom Mushroom";
		BayesNode testNode = new BayesNode(type);
		testNode.observe(true);
		testNode.resetNode();
		
		assertEquals("After resetting the node, we should get P = .5 for unnormalized badger", Fraction.ONE_HALF, testNode.getMarginalPotential(type)[0]);
		assertEquals("After resetting the node, we should get P = .5 for unnormalized !badger", Fraction.ONE_HALF, testNode.getMarginalPotential(type)[1]);
	}
	
	@Test
	public final void testObserveResetPotential(){
		String type = "Badger Badger Badger Mushroom Mushroom";
		BayesNode testNode = new BayesNode(type);
		testNode.observe(true);
		testNode.resetPotential();
		
		assertEquals("After resetting the node, we should get P = .5 for unnormalized badger", Fraction.ONE_HALF, testNode.getMarginalPotential(type)[0]);
		assertEquals("After resetting the node, we should get P = 0 for unnormalized !badger", Fraction.ZERO, testNode.getMarginalPotential(type)[1]);
	}
	
	
	
	@Test
	public final void testSynthesizedScope(){
		Object[] scope = {"Kind"};
		BayesNode testNode = new BayesNode("Trustworthy", scope);
		scope = testNode.scope;
		
		assertEquals("The node's scope should contain exactly 2 variables", 2, scope.length);
		assertEquals("The node's potential should contain 4 items", 4, testNode.getPotential().length);
		
		scope = testNode.scope;
		assertTrue("The node's scope should contain 'Kind'", Arrays.asList(scope).contains("Kind"));
		assertTrue("The node's scope should contain 'Trustworthy'", Arrays.asList(scope).contains("Trustworthy"));
	}
	
	@Test
	public final void testPresetScope(){
		Object[] scope = {"Kind", "Trustworthy"};
		BayesNode testNode = new BayesNode("Trustworthy", scope);
		scope = testNode.scope;
		
		assertEquals("The node's scope should contain exactly 2 variables", 2, scope.length);
		assertEquals("The node's potential should contain 4 items", 4, testNode.getPotential().length);
		
		scope = testNode.scope;
		assertTrue("The node's scope should contain 'Kind'", Arrays.asList(scope).contains("Kind"));
		assertTrue("The node's scope should contain 'Trustworthy'", Arrays.asList(scope).contains("Trustworthy"));
	}
	
	
	
	@Test
	public final void testSPOUTVValidityChecks(){
		Object[] scope = {"Rain"};
		BayesNode rainNode = new BayesNode(scope[0]);
		
		assertTrue("Should be a valid input", rainNode.setProbabilityOfUntrueVariables(new Fraction(7, 10)));
		assertTrue("Should be a valid input", rainNode.setProbabilityOfUntrueVariables(new Fraction(3, 10), scope));
		
		assertFalse("Probabilities above 1 should be rejected", rainNode.setProbabilityOfUntrueVariables(new Fraction(11, 10), scope));
		assertFalse("Probabilities below 0 should be rejected", rainNode.setProbabilityOfUntrueVariables(Fraction.MINUS_ONE, scope));
		
		assertFalse("Should be invalid input", rainNode.setProbabilityOfUntrueVariables(new Fraction(3, 10), "Mushroom"));
	}
	
	
	@Test
	public final void testPriorNodeMessageGeneration(){
		Object[] scope = {"Rain"};
		BayesNode rainNode = new BayesNode(scope[0]);
		
		rainNode.setProbabilityOfUntrueVariables(new Fraction(7, 10));
		rainNode.setProbabilityOfUntrueVariables(new Fraction(3, 10), scope);
		rainNode.resetPotential();
		
		Message m = rainNode.generateMessage(true, scope);
		
		assertArrayEquals("The scope of the message should match the specified scope", scope, m.scope);
		assertArrayEquals("The message should match the node's potential", rainNode.getPotential(), m.message);
		assertEquals("The sender should refer to the creator of the message", rainNode, m.sender);		
		
	}
	
	@Test
	public final void testPriorMessageSending(){
		Object[] scope = {"Rain"};
		BayesNode rainNode = new BayesNode(scope[0]);
		
		rainNode.setProbabilityOfUntrueVariables(new Fraction(7, 10), new Object[0]);
		rainNode.setProbabilityOfUntrueVariables(new Fraction(3, 10), scope);
		
		Message message = rainNode.generateMessage(true, scope);
		
		Object[] mushroomScope = {"Mushroom", "Rain"};
		BayesNode mushroomNode = new BayesNode("Rain", mushroomScope);
		
		mushroomNode.setProbabilityOfUntrueVariables(Fraction.FOUR_FIFTHS);
		mushroomNode.setProbabilityOfUntrueVariables(Fraction.ONE_FIFTH, "Mushroom");
		mushroomNode.setProbabilityOfUntrueVariables(Fraction.TWO_FIFTHS, "Rain");
		mushroomNode.setProbabilityOfUntrueVariables(Fraction.THREE_FIFTHS, "Rain", "Mushroom");
		
		mushroomNode.resetPotential();
		
		mushroomNode.receiveDownstreamMessage(message);
		
		Object[] mushroomMushroomScope = {"Mushroom"};
		Message mushroomMessage = mushroomNode.generateMessage(false, mushroomMushroomScope);
		
		mushroomNode.multiplyPotentialWithMessages();

		Fraction mushroomProbability = new Fraction(17, 25);
		Fraction mushroomNonProbability = new Fraction(8, 25);
						
		Fraction[] receivedProbability = mushroomNode.getNormalizedMarginalPotential("Mushroom");
				
		assertEquals("Probability of Mushroom should be 17/25", mushroomProbability, receivedProbability[0]);
		assertEquals("Probability of !Mushroom should be 8/25", mushroomNonProbability, receivedProbability[1]);
		
		assertEquals("Probability of Mushroom in message should equal marginalized probability", mushroomMessage.message[0], receivedProbability[0]);
		assertEquals("Probability of !Mushroom in message should equal marginalized probability", mushroomMessage.message[1], receivedProbability[1]);
		
	}
	
	@Test
	public final void testNetworkMessagePassing(){
		// set up rain node
		
		BayesNode rainNode = new BayesNode("Rain");
		
		rainNode.setProbabilityOfUntrueVariables(new Fraction(7, 10));
		rainNode.setProbabilityOfUntrueVariables(new Fraction(3, 10), "Rain");

		// set up mushroom node
		
		Object[] mushroomScope = {"Mushroom", "Rain"};
		BayesNode mushroomNode = new BayesNode("Rain", mushroomScope);
		
		mushroomNode.setProbabilityOfUntrueVariables(Fraction.FOUR_FIFTHS);
		mushroomNode.setProbabilityOfUntrueVariables(Fraction.ONE_FIFTH, "Mushroom");
		mushroomNode.setProbabilityOfUntrueVariables(Fraction.TWO_FIFTHS, "Rain");
		mushroomNode.setProbabilityOfUntrueVariables(Fraction.THREE_FIFTHS, "Rain", "Mushroom");
		
		// set up snake node
		
		Object[] snakeScope = {"Snake", "Mushroom"};
		BayesNode snakeNode = new BayesNode("Snake", snakeScope);
		
		snakeNode.setProbabilityOfUntrueVariables(new Fraction(3, 10));
		snakeNode.setProbabilityOfUntrueVariables(new Fraction(4, 5), "Mushroom");
		snakeNode.setProbabilityOfUntrueVariables(new Fraction(7, 10), "Snake");
		snakeNode.setProbabilityOfUntrueVariables(new Fraction(1, 5), "Snake", "Mushroom");
		
		snakeNode.observe(true);
		
		// do message passing
		
		Message rainMessage = rainNode.generateDownstreamMessage("Rain");
		mushroomNode.receiveDownstreamMessage(rainMessage);
		Message mushroomMessage = mushroomNode.generateDownstreamMessage("Mushroom");
		snakeNode.receiveDownstreamMessage(mushroomMessage);
		
		Message snakeUpMessage = snakeNode.generateUpstreamMessage("Mushroom");
		mushroomNode.receiveUpstreamMessage(snakeUpMessage);
		Message mushroomUpMessage = mushroomNode.generateUpstreamMessage("Rain");
		rainNode.receiveUpstreamMessage(mushroomUpMessage);
		
		// calculate outcomes
		
		rainNode.multiplyPotentialWithMessages();
		mushroomNode.multiplyPotentialWithMessages();
		snakeNode.multiplyPotentialWithMessages();
		
		// get and verify probabilities
		
		Fraction[] probabilityOfRain = rainNode.getNormalizedMarginalPotential("Rain");
		Fraction[] probabilityOfMushroom = mushroomNode.getNormalizedMarginalPotential("Mushroom");
		Fraction[] probabilityOfSnake = snakeNode.getNormalizedMarginalPotential("Snake");
		
		assertEquals("Probability of rain should be 60,87%", 0.6087d, probabilityOfRain[0].doubleValue(), 0.01);
		assertEquals("Probability of mushroom should be 44,35%", 0.4435d, probabilityOfMushroom[0].doubleValue(), 0.01);
		assertEquals("Probability of snake should be 100,0%", 1.00d, probabilityOfSnake[0].doubleValue(), 0.01);
		
	}
	
	

}
