package tests;

import junit.framework.TestCase;
import suite.Expression;

public class TestBasicForms extends TestCase {

	public void testSimpleAddition() {
		
		String inputString = "5 + 6";
		Expression sampleExp = new Expression(inputString);
		assertEquals(Integer.valueOf(11),sampleExp.getExpressionValue());
	}
	
	public void testSimpleSubtraction() {
		
		String inputString = "5 - 6";
		Expression sampleExp = new Expression(inputString);
		assertEquals(Integer.valueOf(-1),sampleExp.getExpressionValue());
	}
	
	public void testSimpleMultiplication() {
		
		String inputString = "5 * 6";
		Expression sampleExp = new Expression(inputString);
		assertEquals(Integer.valueOf(30),sampleExp.getExpressionValue());
	}
	
	public void testNegatives() {
		
		String inputString = "-5 * 6";
		Expression sampleExp = new Expression(inputString);
		assertEquals(Integer.valueOf(-30),sampleExp.getExpressionValue());
		
		sampleExp = new Expression("-5 * -6");
		assertEquals(Integer.valueOf(30),sampleExp.getExpressionValue());
	}
	
	public void testNegativesWithParens() {
	
		String inputString = "-(5 * 6)";
		Expression sampleExp = new Expression(inputString);
		assertEquals(Integer.valueOf(-30),sampleExp.getExpressionValue());
		
		sampleExp = new Expression("(-5) * -6");
		assertEquals(Integer.valueOf(30),sampleExp.getExpressionValue());
		
	}
	
}
