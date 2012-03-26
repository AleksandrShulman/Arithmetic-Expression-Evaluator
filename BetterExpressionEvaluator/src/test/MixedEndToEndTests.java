package test;

import suite.Expression;
import exception.InvalidOperandException;
import exception.MalformedParenthesisException;
import junit.framework.TestCase;

public class MixedEndToEndTests extends TestCase {

	public void testMixedParenExpression() throws MalformedParenthesisException, InvalidOperandException {
		
		runExpressionTest("(30)*2 + 5*(2-1)",65);
	}
	
	public void testMixednestedParenExpression() throws MalformedParenthesisException, InvalidOperandException {
		
		runExpressionTest("(30*2+(4-((2)))*3-5*93)*2 + 5*(2-1)",-793);
	}
	
	//CURRENTLY FAILING...casual negative handling not yet implemented
	public void testMixednestedParenExpressionWithNegatives() throws MalformedParenthesisException, InvalidOperandException {
		
		runExpressionTest("(30*2+( (-4)-((2)) )*3-5*93*((-1)-2))*2",2879);
	}	
	
	private void runExpressionTest(String expression, int value) throws MalformedParenthesisException, InvalidOperandException {
		
		Expression e = new Expression(expression);
		assertEquals(Integer.valueOf(value),e.getExpressionValue());
	}
	
}
