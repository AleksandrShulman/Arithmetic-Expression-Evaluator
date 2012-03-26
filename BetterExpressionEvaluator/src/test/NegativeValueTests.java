package test;

import suite.Expression;
import exception.InvalidOperandException;
import exception.MalformedParenthesisException;
import junit.framework.TestCase;

//negative values are tricky because they use the operator for subtraction is the same as 
//the one that indicates negation

public class NegativeValueTests extends TestCase {

	//CURRENTLY FAILING...casual negative handling not yet implemented
	public void testBasicNegativeValue() throws MalformedParenthesisException, InvalidOperandException {
		
		runExpressionTest("-4", -4);
		
	}

	private void runExpressionTest(String expression, int value) throws MalformedParenthesisException, InvalidOperandException {

		Expression e = new Expression(expression);
		assertEquals(Integer.valueOf(value), e.getExpressionValue());
	}
	
}
