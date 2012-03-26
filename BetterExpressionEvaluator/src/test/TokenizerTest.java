package test;

import java.util.LinkedList;
import java.util.List;

import exception.InvalidOperandException;
import exception.MalformedParenthesisException;

import suite.Expression;
import suite.Operation;
import junit.framework.TestCase;

public class TokenizerTest extends TestCase {

	public void testBasicAddition() throws MalformedParenthesisException, InvalidOperandException {
		
		final String exprString = "4+3";
		final List<Object> expectedTokens=new LinkedList<Object>();
		
		expectedTokens.add("4");
		expectedTokens.add(Operation.ADDITION);
		expectedTokens.add("3");
	
		List<Object> tokens = Expression.tokenizeImpl(exprString);
		assertEquals(expectedTokens,tokens);
	}

	public void testBasicNumber() throws MalformedParenthesisException, InvalidOperandException {
		
		final String exprString = "4";
		final List<Object> expectedTokens=new LinkedList<Object>();
		
		expectedTokens.add("4");
		
		List<Object> tokens = Expression.tokenizeImpl(exprString);
		assertEquals(expectedTokens,tokens);
	}

	public void testMultipleTokens() throws MalformedParenthesisException, InvalidOperandException {
		
		final String exprString = "4+3*2";
		final List<Object> expectedTokens=new LinkedList<Object>();
		
		expectedTokens.add("4");
		expectedTokens.add(Operation.ADDITION);
		expectedTokens.add("3");
		expectedTokens.add(Operation.MULTIPLICATION);
		expectedTokens.add("2");
		
		List<Object> tokens = Expression.tokenizeImpl(exprString);
		assertEquals(expectedTokens,tokens);
	}
	
	public void testMultipleTokensWithOuterParens() throws MalformedParenthesisException, InvalidOperandException {
		
		final String exprString = "4+3*2";
		final String parenExprStr = "(" + exprString + ")";
		final List<Object> expectedTokens=new LinkedList<Object>();
		
		expectedTokens.add(parenExprStr);
		
		List<Object> tokens = Expression.tokenizeImpl(parenExprStr);
		assertEquals(expectedTokens,tokens);
	}
	
	public void testParensOnly() throws MalformedParenthesisException, InvalidOperandException {
		
		final String exprString = "";
		final String parenExprStr = "(" + exprString + ")";
		final List<Object> expectedTokens=new LinkedList<Object>();
		
		expectedTokens.add(parenExprStr);
		
		List<Object> tokens = Expression.tokenizeImpl(parenExprStr);
		assertEquals(expectedTokens,tokens);
	}
	
	public void testNonExtenalParenthesis() throws MalformedParenthesisException, InvalidOperandException {
		
		final String exprString = "(4+3)*2";
		final List<Object> expectedTokens=new LinkedList<Object>();
		
		expectedTokens.add("(4+3)");
		expectedTokens.add(Operation.MULTIPLICATION);
		expectedTokens.add("2");
		
		List<Object> tokens = Expression.tokenizeImpl(exprString);
		assertEquals(expectedTokens,tokens);
	}
	
	public void testTriviallyParenthesizedExpression() throws MalformedParenthesisException, InvalidOperandException {
	
		final String exprString = "3";
		final String parenExprStr = "(" + exprString + ")";
		final List<Object> expectedTokens=new LinkedList<Object>();
		
		expectedTokens.add(parenExprStr);
		
		List<Object> tokens = Expression.tokenizeImpl(parenExprStr);
		assertEquals(expectedTokens,tokens);
	}
	
	public void testNestedParenthesis() throws MalformedParenthesisException, InvalidOperandException {
		
		final String exprString = "(4+(3*2)+3)*2";
		final List<Object> expectedTokens=new LinkedList<Object>();
		
		expectedTokens.add("(4+(3*2)+3)");
		expectedTokens.add(Operation.MULTIPLICATION);
		expectedTokens.add("2");
		
		List<Object> tokens = Expression.tokenizeImpl(exprString);
		assertEquals(expectedTokens,tokens);
	}
}
