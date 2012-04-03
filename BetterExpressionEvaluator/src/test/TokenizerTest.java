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
import suite.TokenType;
import junit.framework.TestCase;

public class TokenizerTest extends TestCase {

	public void testBasicAddition() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		final String exprString = "4+3";
		final List<Token> expectedTokens=new LinkedList<Token>();
		
		expectedTokens.add(new Token(4));
		expectedTokens.add(new Token(Operation.ADDITION));
		expectedTokens.add(new Token(3));
	
		List<Token> tokens = Expression.tokenizeImpl(exprString);
		Token.equateTokenList(expectedTokens,tokens);
	}

	public void testBasicNumber() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		final String exprString = "4";
		final List<Token> expectedTokens=new LinkedList<Token>();
		
		expectedTokens.add(new Token(4));
		
		List<Token> tokens = Expression.tokenizeImpl(exprString);
		Token.equateTokenList(expectedTokens,tokens);
	}

	public void testMultipleTokens() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		final String exprString = "4+3*2";
		final List<Token> expectedTokens=new LinkedList<Token>();
		
		expectedTokens.add(new Token(4));
		expectedTokens.add(new Token(Operation.ADDITION));
		expectedTokens.add(new Token(3));
		expectedTokens.add(new Token(Operation.MULTIPLICATION));
		expectedTokens.add(new Token(2));
		
		List<Token> tokens = Expression.tokenizeImpl(exprString);
		Token.equateTokenList(expectedTokens,tokens);
	}
	
	public void testMultipleTokensWithOuterParens() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		final String exprString = "4+3*2";
		final String parenExprStr = "(" + exprString + ")";
		final List<Token> expectedTokens=new LinkedList<Token>();
		expectedTokens.add(new Token(parenExprStr));
		
		List<Token> tokens = Expression.tokenizeImpl(parenExprStr);
		Token.equateTokenList(expectedTokens,tokens);
	}
	
	
	
	
	public void testParensOnly() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		final String exprString = "";
		final String parenExprStr = "(" + exprString + ")";
		final List<Token> expectedTokens=new LinkedList<Token>();
		
		expectedTokens.add(new Token(parenExprStr));
		
		List<Token> tokens = Expression.tokenizeImpl(parenExprStr);
		Token.equateTokenList(expectedTokens,tokens);
	}
	
	public void testNonExternalParenthesis() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		final String exprString = "(4+3)*2";
		final List<Token> expectedTokens=new LinkedList<Token>();
		
		expectedTokens.add(new Token("(4+3)"));
		expectedTokens.add(new Token(Operation.MULTIPLICATION));
		expectedTokens.add(new Token("2"));
		
		List<Token> tokens = Expression.tokenizeImpl(exprString);
		Token.equateTokenList(expectedTokens,tokens);
	}
	
	public void testTriviallyParenthesizedExpression() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
	
		final String exprString = "3";
		final String parenExprStr = "(" + exprString + ")";
		final List<Token> expectedTokens=new LinkedList<Token>();
		
		expectedTokens.add(new Token(parenExprStr));
		
		List<Token> tokens = Expression.tokenizeImpl(parenExprStr);
		Token.equateTokenList(expectedTokens,tokens);
	}
	
	public void testNestedParenthesis() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		final String exprString = "(4+(3*2)+3)*2";
		final List<Token> expectedTokens=new LinkedList<Token>();
		
		expectedTokens.add(new Token("(4+(3*2)+3)"));
		expectedTokens.add(new Token(Operation.MULTIPLICATION));
		expectedTokens.add(new Token(2));
		
		List<Token> tokens = Expression.tokenizeImpl(exprString);
		Token.equateTokenList(expectedTokens,tokens);
	}
	
	public void testTokenInterpretsStringOfDigitsAsInteger() throws MalformedParenthesisException, MalformedDecimalException {
		
		final String digits = "123456";
		final Double digitValue = Double.valueOf(123456);
		
		Token testToken = new Token(digits);
		assertEquals(testToken.getType(), TokenType.NUMERIC);
		assertEquals(testToken.getUnderlyingObject(), digitValue);
	}
	
	public void testTokenInterpretsParentheticalExpressionAsSuch() throws MalformedParenthesisException, MalformedDecimalException {
		
		final String parenDigits = "(123456)";
		final String parenDigitValue = "(123456)";
		
		Token testToken = new Token(parenDigits);
		assertEquals(testToken.getType(), TokenType.PARENTHETICAL_EXPRESSION);
		assertEquals(testToken.getUnderlyingObject(), parenDigitValue);
	}
	
	public void testNegationToken() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		final String negValueStr = "-5";
		LinkedList<Token> expectedTokenList = new LinkedList<Token>();

		Token tVal = new Token(-5.0);
		expectedTokenList.add(tVal);
	
		assertTrue(Token.equateTokenList(expectedTokenList, Expression.tokenizeImpl(negValueStr)));
	}
	
	public void testMixedDecimalInComplexExpression() throws MalformedParenthesisException, MalformedDecimalException, InvalidOperandException, MalformedTokenException {
		
		final String expressionString = "(30*2.0+(4-((2.0)))*3-5*93)";
		LinkedList<Token> expectedTokenList = new LinkedList<Token>();
		expectedTokenList.add(new Token("(30*2.0+(4-((2.0)))*3-5*93)"));

		assertTrue(Token.equateTokenList(expectedTokenList, Expression.tokenizeImpl(expressionString)));
	}
	
	public void testMixedExpressionTokenization() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {

		final String negValueStr = "30*2+3*3-5*93";
		LinkedList<Token> expectedTokenList = new LinkedList<Token>();

		expectedTokenList.add(new Token(30.0));
		expectedTokenList.add(new Token(Operation.MULTIPLICATION));
		expectedTokenList.add(new Token(2.0));
		expectedTokenList.add(new Token(Operation.ADDITION));
		expectedTokenList.add(new Token(3.0));
		expectedTokenList.add(new Token(Operation.MULTIPLICATION));
		expectedTokenList.add(new Token(3.0));
		expectedTokenList.add(new Token(Operation.SUBTRACTION));
		expectedTokenList.add(new Token(5.0));
		expectedTokenList.add(new Token(Operation.MULTIPLICATION));
		expectedTokenList.add(new Token(93.0));
		
		assertTrue(Token.equateTokenList(expectedTokenList, Expression.tokenizeImpl(negValueStr)));
	}

	public void testDoubleNegationToken() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {

		final String negValueStr = "--5";
		LinkedList<Token> expectedTokenList = buildTokens(5.0);

		assertTrue(Token.equateTokenList(expectedTokenList, Expression.tokenizeImpl(negValueStr)));
	}
	
	public static LinkedList<Token> buildTokens(Object... objects) throws MalformedParenthesisException, MalformedDecimalException {
		LinkedList<Token> l = new LinkedList<Token>();
		
		for(Object o : objects) {
			
			l.add(new Token(o));
		}
		return l;
	}
	
	public void testTokenTypeNegatorWhenUnderlyingTypeIsNegator() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		final String s = "--4";
		LinkedList<Token> expectedTokenList = new LinkedList<Token>();
		expectedTokenList.add(new Token(4.0));

		assertTrue(Token.equateTokenList(expectedTokenList, Expression.tokenizeImpl(s)));
	}
	
	public void testSingleNegatorToken()  throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		final String s = "-4";
		LinkedList<Token> expectedTokenList = new LinkedList<Token>();
		expectedTokenList.add(new Token(-4.0));

		assertTrue(Token.equateTokenList(expectedTokenList, Expression.tokenizeImpl(s)));
	}
	
	public void testNegatorTokenUnderlyingValueTokenTypeIsNegator() throws MalformedParenthesisException, MalformedDecimalException {
		
		Token t= new Token(Operation.NEGATION);
		assertEquals(TokenType.NEGATOR, t.getType());
	}
}
