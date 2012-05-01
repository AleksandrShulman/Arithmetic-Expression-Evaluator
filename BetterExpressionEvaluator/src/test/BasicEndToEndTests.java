package test;

import exception.InvalidOperandException;
import exception.MalformedDecimalException;
import exception.MalformedParenthesisException;
import exception.MalformedTokenException;
import suite.Expression;
import junit.framework.TestCase;

public class BasicEndToEndTests extends TestCase {

	public void testNullInputGivesCorrectErrorMessage() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		String badExpressionString = null;
		
		try {
		
			Expression badExpression = new Expression(badExpressionString);
			fail("Expression not thrown!?");
		} catch (IllegalArgumentException e) {
			
			assertEquals(e.getLocalizedMessage(), Expression.illegalArgString);
		}
	}
	
	public void testEmptyExpression() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("", 0.0);
	}
	
	public void testBasicAddition() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("30+15", 45.0);
	}
	
	public void testBasicSubtraction() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
	
		runExpressionTest("30-15", 15.0);
	}
	
	public void testBasicMultiplication() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("30*15", 450.0);
	}
	
	public void testBasicEmptyParens() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("()", 0.0);
	}
	
	public void testBasicTrivialEmptyParens() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("(30)",30.0);
	}
	
	private void runExpressionTest(String expression, Double value) throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		Expression e = new Expression(expression);
		assertEquals(Double.valueOf(value),e.getExpressionValue());
	}
}
