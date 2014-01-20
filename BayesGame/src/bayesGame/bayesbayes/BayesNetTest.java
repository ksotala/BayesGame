package bayesGame.bayesbayes;

import static org.junit.Assert.*;

import org.apache.commons.math3.fraction.Fraction;
import org.junit.Before;
import org.junit.Test;

public class BayesNetTest {
	
	BayesNet testNet;
	
	@Before
	public final void setup() {
		testNet = new BayesNet();
	}

	@Test
	public final void testContainsNode() {
		String text1 = "Hello";
		String text2 = "How are you";
		String text3 = "Hello";
		
		testNet.addNode(text1);
		assertTrue("The net should contain a node for 'Hello'", testNet.containsNode(text1));
		assertFalse("The net should not contain a node for 'How are you'", testNet.containsNode(text2));
		assertTrue("The net should contain a node for 'Hello' in a different object", testNet.containsNode(text3));
	}
	
	@Test
	public final void testConnectNodes() {
		String text = "Hello";
		int number = 42;
		
		
		assertFalse("Implementation requires nodes to exist before they are added", testNet.connectNodes(text, number));
		assertTrue("Node should be added successfully", testNet.addNode(text));
		assertTrue("Node should be added successfully", testNet.addNode(number));
		assertFalse("Should refuse to connect incompatible scopes", testNet.connectNodes(text, number));
		assertTrue("Node should be added successfully", testNet.addNode("Boo", new Object[]{"Hello"}));
		assertTrue("Should connect nodes", testNet.connectNodes(text, "Boo"));
		assertFalse("Nodes should already be connected", testNet.connectNodes(text, "Boo"));
	}
	
	@Test
	public final void testNetworkMessagePassing(){
		// set up rain node
		testNet.addNode("Rain");
		testNet.setProbabilityOfUntrue("Rain", new Fraction(7, 10));
		testNet.setProbabilityOfUntrue("Rain", new Fraction(3, 10), "Rain");

		// set up mushroom node
		testNet.addNode("Mushroom", new Object[]{"Mushroom", "Rain"});
		
		testNet.setProbabilityOfUntrue("Mushroom", Fraction.FOUR_FIFTHS);
		testNet.setProbabilityOfUntrue("Mushroom", Fraction.ONE_FIFTH, "Mushroom");
		testNet.setProbabilityOfUntrue("Mushroom", Fraction.TWO_FIFTHS, "Rain");
		testNet.setProbabilityOfUntrue("Mushroom", Fraction.THREE_FIFTHS, "Rain", "Mushroom");
		
		// set up snake node
		testNet.addNode("Snake", new Object[]{"Snake", "Mushroom"});

		testNet.setProbabilityOfUntrue("Snake", new Fraction(3, 10));
		testNet.setProbabilityOfUntrue("Snake", new Fraction(4, 5), "Mushroom");
		testNet.setProbabilityOfUntrue("Snake", new Fraction(7, 10), "Snake");
		testNet.setProbabilityOfUntrue("Snake", new Fraction(1, 5), "Snake", "Mushroom");
		
		// set up node connections
		testNet.connectNodes("Rain", "Mushroom");
		testNet.connectNodes("Mushroom", "Snake");
		
		testNet.updateBeliefs();
		
		// get and verify basic probabilities
		
		Fraction probabilityOfRain = testNet.getProbability("Rain");
		Fraction probabilityOfMushroom = testNet.getProbability("Mushroom");
		Fraction probabilityOfSnake = testNet.getProbability("Snake");
		
		assertEquals("Probability of rain should be 70%", 0.7d, probabilityOfRain.doubleValue(), 0.01);
		assertEquals("Probability of mushroom should be 68%", 0.68d, probabilityOfMushroom.doubleValue(), 0.01);
		assertEquals("Probability of snake should be 46%", 0.46d, probabilityOfSnake.doubleValue(), 0.01);
		
		// observe Snake, then update network
		
		testNet.observe("Snake", true);
		testNet.updateBeliefs();
		
		probabilityOfRain = testNet.getProbability("Rain");
		probabilityOfMushroom = testNet.getProbability("Mushroom");
		probabilityOfSnake = testNet.getProbability("Snake");
		
		assertEquals("Probability of rain should be 60,87%", 0.6087d, probabilityOfRain.doubleValue(), 0.01);
		assertEquals("Probability of mushroom should be 44,35%", 0.4435d, probabilityOfMushroom.doubleValue(), 0.01);
		assertEquals("Probability of snake should be 100,0%", 1.00d, probabilityOfSnake.doubleValue(), 0.01);
		
	}
	
	@Test
	public final void addDeterministicOrIsParentPresentNegativeTest(){
		assertFalse("Should return false when asked to create OR-node with non-existent parents", testNet.addDeterministicOr("Boo", "Goo"));
	}
	
	@Test
	public final void addDeterministicOrIsParentPresentPositiveTest(){
		testNet.addNode("Goo");
		assertTrue("Should return true when asked to create OR-node with a existing parent", testNet.addDeterministicOr("Boo", "Goo"));
	}
	
	@Test
	public final void addDeterministicOrZeroParents(){
		assertFalse("Should return false when asked to create OR-node with no parents specified", testNet.addDeterministicOr("Boo"));
	}
	
	@Test
	public final void addDeterministicOrIsPresentNegativeTest(){
		testNet.addNode("Boo");
		testNet.addNode("Goo");
		assertFalse("Should return false when asked to create OR-node when a node of same name already exists", testNet.addDeterministicOr("Boo", "Goo"));
	}
	
	@Test
	public final void addDeterministicOrOneParentTrue(){
		testNet.addNode("Mother");
		testNet.setProbabilityOfUntrue("Mother", Fraction.ONE);
		testNet.setProbabilityOfUntrue("Mother", Fraction.ZERO, "Mother");
		
		testNet.addNode("Father");
		testNet.setProbabilityOfUntrue("Father", Fraction.ZERO);
		testNet.setProbabilityOfUntrue("Father", Fraction.ONE, "Father");
		
		testNet.addDeterministicOr("Child", new Object[]{"Mother", "Father"});
		testNet.updateBeliefs();
		
		Fraction probabilityOfChild = testNet.getProbability("Child");
		
		assertEquals("Child should have P = 1 when a parent has P = 1", 1.00d, probabilityOfChild.doubleValue(), 0.01);
	}
	
	@Test
	public final void addDeterministicOrTwoParentsTrue(){
		testNet.addNode("Mother");
		testNet.setProbabilityOfUntrue("Mother", Fraction.ONE);
		testNet.setProbabilityOfUntrue("Mother", Fraction.ZERO, "Mother");
		
		testNet.addNode("Father");
		testNet.setProbabilityOfUntrue("Father", Fraction.ONE);
		testNet.setProbabilityOfUntrue("Father", Fraction.ZERO, "Father");
		
		testNet.addDeterministicOr("Child", new Object[]{"Mother", "Father"});
		testNet.updateBeliefs();
		
		Fraction probabilityOfChild = testNet.getProbability("Child");
		
		assertEquals("Child should have P = 1 when both parents have P = 1", 1.00d, probabilityOfChild.doubleValue(), 0.01);
	}
	
	@Test
	public final void addDeterministicOrTwoParentsFalse(){
		testNet.addNode("Mother");
		testNet.setProbabilityOfUntrue("Mother", Fraction.ZERO);
		testNet.setProbabilityOfUntrue("Mother", Fraction.ONE, "Mother");
		
		testNet.addNode("Father");
		testNet.setProbabilityOfUntrue("Father", Fraction.ZERO);
		testNet.setProbabilityOfUntrue("Father", Fraction.ONE, "Father");
		
		testNet.addDeterministicOr("Child", new Object[]{"Mother", "Father"});
		testNet.updateBeliefs();
		
		Fraction probabilityOfChild = testNet.getProbability("Child");
		
		assertEquals("Child should have P = 0 when both parents have P = 0", 0.00d, probabilityOfChild.doubleValue(), 0.01);
	}
	
	
	
	

}
