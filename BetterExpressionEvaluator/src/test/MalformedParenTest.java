package test;

import junit.framework.TestCase;
import suite.Expression;
import exception.MalformedParenthesisException;

public class MalformedParenTest extends TestCase {

	public void testUnmatchedOpenParenthesis() throws Exception {
		
		String openParenString = "(3 + 5";
		try {
			new Expression(openParenString);
			fail("Did not throw exception");
		} catch (MalformedParenthesisException mpe) {}
	}

	public void testUnmatchedClosedParenthesis() throws Exception {
		
		String openParenString = "3 + 5)";
		try {
			new Expression(openParenString);
			fail("Did not throw exception");
		} catch (MalformedParenthesisException mpe) {}
	}

	public void testUnmatchedClosedWithOtherValidParensParenthesis() throws Exception {
		
		String openParenString = "(3) + 5)";
		try {
			new Expression(openParenString);
			fail("Did not throw exception");
		} catch (MalformedParenthesisException mpe) {}
	}

	public void testIncorrectlyOrderedParenthesis() throws Exception {
		
		String openParenString = ") 3 + 5 (";
		try {
			new Expression(openParenString);
			fail("Did not throw exception");
			
		} catch (MalformedParenthesisException mpe) {}
	}
	
	public void testIncorrectlyOrderedParenthesisInsideNormalParenthesis() throws Exception {
		
		String openParenString = "( 3 + 5  + ) 8 + 9 ( )";
		try {
			new Expression(openParenString);
			fail("Did not throw exception");
			
		} catch (MalformedParenthesisException mpe) {}
	}
}


