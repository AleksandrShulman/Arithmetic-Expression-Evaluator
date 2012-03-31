package test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import suite.Expression;
import suite.Operation;
import suite.Token;
import exception.InvalidOperandException;
import exception.MalformedDecimalException;
import exception.MalformedParenthesisException;
import exception.MalformedTokenException;
import junit.framework.TestCase;

public class DivisionTest extends TestCase {

	public void testSimpleDivision() throws MalformedParenthesisException,
			InvalidOperandException, MalformedTokenException,
			MalformedDecimalException {

		List<String> basicStrings = new ArrayList<String>();
		basicStrings.add("6.0/3.0");
		basicStrings.add("6.0/3");
		basicStrings.add("6/3.0");
		basicStrings.add("6.000000/3.00000");
		basicStrings.add("6/3");

		for (String basicString : basicStrings) {
			final List<Token> expectedList = new LinkedList<Token>();
			expectedList.add(new Token(Double.valueOf("2")));

			List<Token> listUnderTest = Expression.tokenizeImpl(basicString);

			Expression.performDivisionSubstitution(listUnderTest);
			Token.equateTokenList(listUnderTest, expectedList);
		}
	}
	
	public void testDivisionAndAddition() throws NumberFormatException, MalformedParenthesisException, MalformedDecimalException, InvalidOperandException, MalformedTokenException {
		
		List<String> basicStrings = new ArrayList<String>();
		basicStrings.add("6.0/3.0 + 1");
		basicStrings.add("6.0/3 + 1");
		basicStrings.add("6/3.0 + 1");
		basicStrings.add("6.000000/3.00000 + 1");
		basicStrings.add("6/3 + 1");

		for (String basicString : basicStrings) {
			final List<Token> expectedList = new LinkedList<Token>();
			expectedList.add(new Token(Double.valueOf("3")));

			List<Token> listUnderTest = Expression.tokenizeImpl(basicString);

			Expression.performDivisionSubstitution(listUnderTest);
			Token.equateTokenList(listUnderTest, expectedList);
		}
	}
	
	public void testInverseRelationshipHolds() throws NumberFormatException, MalformedParenthesisException, MalformedDecimalException, MalformedTokenException, InvalidOperandException {
		
		String basicString = "4/3*3/4";
		final List<Token> expectedList = new LinkedList<Token>();
		expectedList.add(new Token(Double.valueOf(4.0)));
		expectedList.add(new Token(Operation.DIVISION));
		expectedList.add(new Token(Double.valueOf(3.0)));
		expectedList.add(new Token(Operation.MULTIPLICATION));
		expectedList.add(new Token(Double.valueOf(3.0)));
		expectedList.add(new Token(Operation.DIVISION));
		expectedList.add(new Token(Double.valueOf(4.0)));
		
		List<Token> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performAdditionSubstitution(listUnderTest);
		assertTrue(Token.equateTokenList(listUnderTest, expectedList));
	}
	
	public void testPartialMixedParenExpression() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		MixedEndToEndTests.runExpressionTest("4/3*3/4",1.0);
	}
	
}
