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

public class SubstitutionIntoSubtractionTest extends TestCase {

	
	public void testBasicSubtraction() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		String basicString = "4-3";
		final List<Token> expectedList = new LinkedList<Token>();
		expectedList.add(new Token(1));
		
		List<Token> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performSubtractionSubstitution(listUnderTest);
		Token.equateTokenList(listUnderTest, expectedList);
	}
	
	public void testNoSubstitution() throws  MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		String basicString = "1";
		final List<Token> expectedList = new LinkedList<Token>();
		expectedList.add(new Token(1));
		
		List<Token> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performSubtractionSubstitution(listUnderTest);
	
		Token.equateTokenList(listUnderTest, expectedList);
	}
	
	public void testMultipleTermSubtraction() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		String basicString = "4-3-5";
		final List<Token> expectedList = new LinkedList<Token>();
		expectedList.add(new Token(-4));
		
		List<Token> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performSubtractionSubstitution(listUnderTest);
		Token.equateTokenList(listUnderTest, expectedList);
	}
	
	public void testMixedAdditionAndSubtraction()  throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		String basicString = "4-3+5-3+2";
		final List<Token> expectedList = Expression.tokenizeImpl("1+2+2");
		
		List<Token> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performSubtractionSubstitution(listUnderTest);
		Token.equateTokenList(listUnderTest, expectedList);
	}
}
