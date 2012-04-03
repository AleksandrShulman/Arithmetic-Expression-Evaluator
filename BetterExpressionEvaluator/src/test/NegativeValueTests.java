package test;

import suite.Expression;
import exception.InvalidOperandException;
import exception.MalformedDecimalException;
import exception.MalformedParenthesisException;
import exception.MalformedTokenException;
import junit.framework.TestCase;

//negative values are tricky because they use the operator for subtraction is the same as 
//the one that indicates negation

public class NegativeValueTests extends TestCase {

	//CURRENTLY FAILING...casual negative handling not yet implemented
	public void testBasicNegativeValue() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("-4", -4.0);
		runExpressionTest("-4.0", -4.0);
		
	}

	private void runExpressionTest(String expression, Double value) throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {

		Expression e = new Expression(expression);
		assertEquals(Double.valueOf(value), e.getExpressionValue());
	}
	
}
