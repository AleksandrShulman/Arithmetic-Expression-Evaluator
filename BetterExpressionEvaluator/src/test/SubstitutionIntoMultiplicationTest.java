package test;

import java.util.LinkedList;
import java.util.List;

import suite.Expression;
import suite.Operation;
import exception.InvalidOperandException;
import exception.MalformedParenthesisException;
import junit.framework.TestCase;

public class SubstitutionIntoMultiplicationTest extends TestCase {

	public void testSimpleMultiplication() throws MalformedParenthesisException, InvalidOperandException {
		
		String basicString = "4*3";
		final List<Object> expectedList = new LinkedList<Object>();
		expectedList.add(Integer.valueOf("12"));
		
		List<Object> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performMultiplicationSubstitution(listUnderTest);
		SubstitutionIntoSubtractionTest.compareTwoLists(listUnderTest, expectedList);
	}
	
	public void testMultipleTermMultiplication() throws MalformedParenthesisException, InvalidOperandException {
		
		String basicString = "4*3*5";
		final List<Object> expectedList = new LinkedList<Object>();
		expectedList.add(Integer.valueOf("60"));
		
		List<Object> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performMultiplicationSubstitution(listUnderTest);
		SubstitutionIntoSubtractionTest.compareTwoLists(listUnderTest, expectedList);
		
	}
	
	public void testMixedMultiplicationAdditionAndSubtraction()  throws MalformedParenthesisException, InvalidOperandException {
		
		String basicString = "4-3*5-3*2";
		final List<Object> expectedList = new LinkedList<Object>();
		expectedList.add(Integer.valueOf("4"));
		expectedList.add(Operation.SUBTRACTION);
		expectedList.add(Integer.valueOf("15"));
		expectedList.add(Operation.SUBTRACTION);
		expectedList.add(Integer.valueOf("6"));
		
		List<Object> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performMultiplicationSubstitution(listUnderTest);
		SubstitutionIntoSubtractionTest.compareTwoLists(listUnderTest, expectedList);
	}
	
	public void testMixedHeavyMultiplicationAdditionAndSubtraction()  throws MalformedParenthesisException, InvalidOperandException {
		
		String basicString = "4-3*5*2-3*2";
		final List<Object> expectedList = new LinkedList<Object>();
		expectedList.add(Integer.valueOf("4"));
		expectedList.add(Operation.SUBTRACTION);
		expectedList.add(Integer.valueOf("30"));
		expectedList.add(Operation.SUBTRACTION);
		expectedList.add(Integer.valueOf("6"));
		
		List<Object> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performMultiplicationSubstitution(listUnderTest);
		SubstitutionIntoSubtractionTest.compareTwoLists(listUnderTest, expectedList);
	}
		
}
