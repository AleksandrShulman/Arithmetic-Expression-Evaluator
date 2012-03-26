package test;

import junit.framework.TestCase;
import suite.Expression;

public class TestWhiteSpaceRemoval extends TestCase {

	public void testBasicEndWhiteSpace() {

		runWhiteSpaceTest(" (4+3) ", "(4+3)");
	}

	private void runWhiteSpaceTest(String startString, String expectedString) {
		
		String trimmedString =Expression.removeWhiteSpace(startString.toCharArray()); 
		assertTrue(trimmedString.equals("(4+3)"));
	}
	
	public void testIntermediateWhiteSpace() {
		
		runWhiteSpaceTest(" ( 4 +3) ", "(4+3)");
	}
	
	public void testDoubleWhiteSpace() {

		runWhiteSpaceTest(" (  4 +3) ", "(4+3)");
	}
	
	public void testDoubleWhiteSpaceAndNewline() {
		
		runWhiteSpaceTest(" (  4 +3) ", "(4+3)");
	}
}
