package test;

import java.util.LinkedList;
import java.util.List;

import exception.InvalidOperandException;
import exception.MalformedDecimalException;
import exception.MalformedParenthesisException;
import exception.MalformedTokenException;

import suite.Expression;
import suite.Operation;
import suite.Token;
import junit.framework.TestCase;

public class SubstitutionIntoParentheticalExpressionTest extends TestCase {

	public void testNoParens() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		String basicString = "4+3";
		final List<Token> expectedList = new LinkedList<Token>();
		expectedList.add(new Token("4"));
		expectedList.add(new Token(Operation.ADDITION));
		expectedList.add(new Token("3"));
		
		
		List<Token> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performParentheticalExpressionEvaluation(listUnderTest);
		Token.equateTokenList(listUnderTest, expectedList);
	}
	
	public void testBasicParens() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		String basicString = "(4+3)";
		final List<Token> expectedList = new LinkedList<Token>();
		expectedList.add(new Token("7"));
		
		
		List<Token> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performParentheticalExpressionEvaluation(listUnderTest);
		Token.equateTokenList(listUnderTest, expectedList);
	}
	
	public void testParensAndMixedOps() {
		
		//TODO: Add this
	}
	
	public void testNestedParens() {
		
		//TODO: Add this
	}
	
	public void testMultipleTriviallyNestedParens() {
		
		//TODO: Add this
	}
}
