package test;

import java.util.LinkedList;
import java.util.List;

import exception.InvalidOperandException;
import exception.MalformedParenthesisException;

import suite.Expression;
import junit.framework.TestCase;

public class SubstitutionIntoAdditionTest extends TestCase {

	public void testSimpleAddition() throws MalformedParenthesisException, InvalidOperandException {
		
		String basicString = "4+3";
		final List<Object> expectedList = new LinkedList<Object>();
		expectedList.add(Integer.valueOf("7"));
		
		List<Object> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performAdditionSubstitution(listUnderTest);
		SubstitutionIntoSubtractionTest.compareTwoLists(listUnderTest, expectedList);
	}
	
	public void testMultipleTermAddition() throws MalformedParenthesisException, InvalidOperandException {
		
		String basicString = "4+3+5";
		final List<Object> expectedList = new LinkedList<Object>();
		expectedList.add(Integer.valueOf("12"));
		
		List<Object> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performAdditionSubstitution(listUnderTest);
		SubstitutionIntoSubtractionTest.compareTwoLists(listUnderTest, expectedList);
	}
}
