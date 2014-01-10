package bayesGame;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class CharacterTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public final void testEqualsObject() {
		Character x = new Character("x");
		Character y = new Character("y");
		Character z = new Character("x");
		
		assertTrue("A character should be considered equal to itself", x.equals(x));
		assertTrue("Two characters with the same name should be considered equal", x.equals(z));
		assertFalse("Two characters with different names should be consider non-equal", x.equals(y));
		
	}

}
