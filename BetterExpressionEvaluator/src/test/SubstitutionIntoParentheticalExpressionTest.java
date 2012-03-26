package test;

import java.util.LinkedList;
import java.util.List;

import exception.InvalidOperandException;
import exception.MalformedParenthesisException;

import suite.Expression;
import suite.Operation;
import junit.framework.TestCase;

public class SubstitutionIntoParentheticalExpressionTest extends TestCase {

	public void testNoParens() throws MalformedParenthesisException, InvalidOperandException {
		
		String basicString = "4+3";
		final List<Object> expectedList = new LinkedList<Object>();
		expectedList.add("4");
		expectedList.add(Operation.ADDITION);
		expectedList.add("3");
		
		
		List<Object> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performParentheticalExpressionEvaluation(listUnderTest);
		SubstitutionIntoSubtractionTest.compareTwoLists(listUnderTest, expectedList);
	}
	
	public void testBasicParens() throws MalformedParenthesisException, InvalidOperandException {
		
		String basicString = "(4+3)";
		final List<Object> expectedList = new LinkedList<Object>();
		expectedList.add("7");
		
		
		List<Object> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performParentheticalExpressionEvaluation(listUnderTest);
		SubstitutionIntoSubtractionTest.compareTwoLists(listUnderTest, expectedList);
	}
	
	public void testParensAndMixedOps() {
		
		
	}
	
	public void testNestedParens() {
		
	}
	
	public void testMultipleTriviallyNestedParens() {
		
		
	}
	
	
}
