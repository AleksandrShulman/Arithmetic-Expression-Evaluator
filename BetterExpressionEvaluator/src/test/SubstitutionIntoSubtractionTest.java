package test;

import java.util.LinkedList;
import java.util.List;

import exception.InvalidOperandException;
import exception.MalformedParenthesisException;
import suite.Expression;
import suite.Operation;
import junit.framework.TestCase;

public class SubstitutionIntoSubtractionTest extends TestCase {

	
	public void testBasicSubtraction() throws MalformedParenthesisException, InvalidOperandException {
		
		String basicString = "4-3";
		final List<Object> expectedList = new LinkedList<Object>();
		expectedList.add(Integer.valueOf("1"));
		
		List<Object> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performSubtractionSubstitution(listUnderTest);
		SubstitutionIntoSubtractionTest.compareTwoLists(listUnderTest, expectedList);
	}
	
	public void testNoSubstitution() throws  MalformedParenthesisException, InvalidOperandException {
		
		String basicString = "1";
		final List<Object> expectedList = new LinkedList<Object>();
		expectedList.add(1);
		
		List<Object> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performSubtractionSubstitution(listUnderTest);
	
		SubstitutionIntoSubtractionTest.compareTwoLists(listUnderTest, expectedList);
	}
	
	public void testMultipleTermSubtraction() throws MalformedParenthesisException, InvalidOperandException {
		
		String basicString = "4-3-5";
		final List<Object> expectedList = new LinkedList<Object>();
		expectedList.add(Integer.valueOf("-4"));
		
		List<Object> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performSubtractionSubstitution(listUnderTest);
		SubstitutionIntoSubtractionTest.compareTwoLists(listUnderTest, expectedList);
	}
	
	public void testMixedAdditionAndSubtraction()  throws MalformedParenthesisException, InvalidOperandException {
		
		String basicString = "4-3+5-3+2";
		final List<Object> expectedList = new LinkedList<Object>();
		expectedList.add(Integer.valueOf("1"));
		expectedList.add(Operation.ADDITION);
		expectedList.add(Integer.valueOf("2"));
		expectedList.add(Operation.ADDITION);
		expectedList.add(Integer.valueOf("2"));
		
		List<Object> listUnderTest = Expression.tokenizeImpl(basicString);
		
		Expression.performSubtractionSubstitution(listUnderTest);
		SubstitutionIntoSubtractionTest.compareTwoLists(listUnderTest, expectedList);
	}
	
	public static void compareTwoLists(List<Object> l1, List<Object> l2) {
		
		assertEquals(l1.size(),l2.size());
		
		int i = 0;
		for(Object o : l1) {
			
			assertEquals(l2.get(i).toString(), o.toString());
			i++;
		}
	}
}
