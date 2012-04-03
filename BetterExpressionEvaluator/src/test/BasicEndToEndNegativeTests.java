package test;

import exception.InvalidOperandException;
import exception.MalformedDecimalException;
import exception.MalformedParenthesisException;
import exception.MalformedTokenException;
import suite.Expression;
import junit.framework.TestCase;

public class BasicEndToEndNegativeTests extends TestCase {

	public void testJustDecimalIsNotAValidExpression() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException {
		
		runMalformedDecimalExpressionTest(".");
		runMalformedDecimalExpressionTest(" .");
		runMalformedDecimalExpressionTest(" .  ");
	}

	public void testRepeatingDecimalsNotAValidExpression() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException {
		
		runMalformedDecimalExpressionTest("..");
		runMalformedDecimalExpressionTest("3+2.0+4..0");
		runMalformedDecimalExpressionTest("3+..4");
	}
	
	public void testDecimalsAfterOperatorNotAValidExpression() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException {
		
		runMalformedDecimalExpressionTest("3.+2");
		runMalformedDecimalExpressionTest("2/.3.");
	}
	
	public static void runMalformedDecimalExpressionTest(String badExp)
			throws MalformedParenthesisException, InvalidOperandException,
			MalformedTokenException {

		try {
			new Expression(badExp);
		} catch (MalformedDecimalException mde) {
		}
	}
	
}

