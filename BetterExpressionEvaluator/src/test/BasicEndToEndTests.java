package test;

import exception.InvalidOperandException;
import exception.MalformedParenthesisException;
import suite.Expression;
import junit.framework.TestCase;

public class BasicEndToEndTests extends TestCase {

	public void testEmptyExpression() throws MalformedParenthesisException, InvalidOperandException {
		
		runExpressionTest("", 0);
	}
	
	public void testBasicAddition() throws MalformedParenthesisException, InvalidOperandException {
		
		runExpressionTest("30+15", 45);
	}
	
	public void testBasicSubtraction() throws MalformedParenthesisException, InvalidOperandException {
	
		runExpressionTest("30-15", 15);
	}
	
	public void testBasicMultiplication() throws MalformedParenthesisException, InvalidOperandException {
		
		runExpressionTest("30*15", 450);
	}
	
	public void testBasicEmptyParens() throws MalformedParenthesisException, InvalidOperandException {
		
		runExpressionTest("()", 0);
	}
	
	public void testBasicTrivialEmptyParens() throws MalformedParenthesisException, InvalidOperandException {
		
		runExpressionTest("(30)",30);
	}
	
	private void runExpressionTest(String expression, int value) throws MalformedParenthesisException, InvalidOperandException {
		
		Expression e = new Expression(expression);
		assertEquals(Integer.valueOf(value),e.getExpressionValue());
	}
}
