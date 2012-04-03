package test;

import java.util.LinkedList;
import java.util.List;

import exception.InvalidOperandException;
import exception.MalformedDecimalException;
import exception.MalformedParenthesisException;
import exception.MalformedTokenException;

import suite.Expression;
import suite.Token;
import junit.framework.TestCase;

public class SubstitutionIntoAdditionTest extends TestCase {

	public void testSimpleAddition() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		String basicString = "4+3";
		final List<Token> expectedList = new LinkedList<Token>();
		expectedList.add(new Token(Integer.valueOf("7")));
		
		List<Token> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performAdditionSubstitution(listUnderTest);
		Token.equateTokenList(listUnderTest, expectedList);
	}
	
	public void testMultipleTermAddition() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, NumberFormatException, MalformedDecimalException {
		
		String basicString = "4+3+5";
		final List<Token> expectedList = new LinkedList<Token>();
		expectedList.add(new Token(Integer.valueOf("12")));
		
		List<Token> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performAdditionSubstitution(listUnderTest);
		Token.equateTokenList(listUnderTest, expectedList);
	}
}
