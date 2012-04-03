package test;

import java.util.LinkedList;
import java.util.List;

import suite.Expression;
import suite.Token;
import exception.InvalidOperandException;
import exception.MalformedDecimalException;
import exception.MalformedParenthesisException;
import exception.MalformedTokenException;
import junit.framework.TestCase;

public class SubstitutionIntoMultiplicationTest extends TestCase {

	public void testSimpleMultiplication() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, NumberFormatException, MalformedDecimalException {
		
		String basicString = "4*3";
		final List<Token> expectedList = new LinkedList<Token>();
		expectedList.add(new Token(Integer.valueOf("12")));
		
		List<Token> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performMultiplicationSubstitution(listUnderTest);
		Token.equateTokenList(listUnderTest, expectedList);
	}
	
	public void testMultipleTermMultiplication() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		String basicString = "4*3*5";
		final List<Token> expectedList = new LinkedList<Token>();
		expectedList.add(new Token(Integer.valueOf("60")));
		
		List<Token> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performMultiplicationSubstitution(listUnderTest);
		Token.equateTokenList(listUnderTest, expectedList);
	}
	
	public void testMixedMultiplicationAdditionAndSubtraction()  throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		String basicString = "4-3*5-3*2";
		final List<Token> expectedList = Expression.tokenizeImpl("4-15-6");
		
		List<Token> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performMultiplicationSubstitution(listUnderTest);
		Token.equateTokenList(listUnderTest, expectedList);
	}
	
	public void testMixedHeavyMultiplicationAdditionAndSubtraction()  throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		String basicString = "4-3*5*2-3*2";
		final List<Token> expectedList = Expression.tokenizeImpl("4-30-6");
		
		List<Token> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performMultiplicationSubstitution(listUnderTest);
		Token.equateTokenList(listUnderTest, expectedList);
	}
		
}
