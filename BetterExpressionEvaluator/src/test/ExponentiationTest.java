package test;

import exception.InvalidOperandException;
import exception.MalformedDecimalException;
import exception.MalformedParenthesisException;
import exception.MalformedTokenException;
import junit.framework.TestCase;

public class ExponentiationTest extends TestCase {

	public void testMixedParenExpression() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		MixedEndToEndTests.runExpressionTest("3^2",9.0);
		MixedEndToEndTests.runExpressionTest("3.0^2",9.0);
		MixedEndToEndTests.runExpressionTest("3.0^2.0",9.0);
		MixedEndToEndTests.runExpressionTest("0^2",0.0);
	}
	
	public void testBasicOperationOrdering() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		MixedEndToEndTests.runExpressionTest("27+3^2",36.0);
		MixedEndToEndTests.runExpressionTest("27*3^2",243.0);
		MixedEndToEndTests.runExpressionTest("27/3^2",3.0);
		MixedEndToEndTests.runExpressionTest("27-3^2",18.0);

		//Mess with decimals
		MixedEndToEndTests.runExpressionTest("27.0+3^2",36.0);
		MixedEndToEndTests.runExpressionTest("27*3.0^2",243.0);
		MixedEndToEndTests.runExpressionTest("27/3^2.0",3.0);
		MixedEndToEndTests.runExpressionTest("27.0-3.0^2",18.0);
	}

	public void testBasicOperationOrderingWithParenthesis() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
	
		MixedEndToEndTests.runExpressionTest("(27+3)^2",900.0);
		MixedEndToEndTests.runExpressionTest("(27*3)^2",6561.0);
		MixedEndToEndTests.runExpressionTest("(27/3)^2",81.0);
		MixedEndToEndTests.runExpressionTest("(27-3)^2",576.0);
	}
	
	//Note, it's top-down, not bottom-up
	public void testRepeatedExponentiation() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		MixedEndToEndTests.runExpressionTest("3^2^3",6561.0);
	}
	
	public void testMultipleExponentialExpressions() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		MixedEndToEndTests.runExpressionTest("2.0+3^2^3-5*2+2^3^2",7065.0);
		MixedEndToEndTests.runExpressionTest("2.0+3.0^2.0^3.0-5*2+2.0^3^2",7065.0);
		MixedEndToEndTests.runExpressionTest("2.0000+3.0^2.0^3.000-5*2+2.0^3^2",7065.0);
	}
	
	public void testMultipleExponentialExpressionsWithParenthesis() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		MixedEndToEndTests.runExpressionTest("(2.0000+((3.0^2.0^3.000))-5*2+(2.0^3^2))",7065.0);
	}
	
	public void testNegationWithExponentiation() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		//having preprocessing of negation complicates things because
		//it changes the nature of the expression when it comes time
		//for exponentiation.
		
		MixedEndToEndTests.runExpressionTest("4^-1",.25);
		MixedEndToEndTests.runExpressionTest("4^--1",4.0);
		MixedEndToEndTests.runExpressionTest("4^--1*--1",4.0);
	}
}
