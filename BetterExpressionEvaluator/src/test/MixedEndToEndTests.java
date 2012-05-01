package test;

import suite.Expression;
import exception.InvalidOperandException;
import exception.MalformedDecimalException;
import exception.MalformedParenthesisException;
import exception.MalformedTokenException;
import junit.framework.TestCase;

public class MixedEndToEndTests extends TestCase {

	public void testMixedParenExpression() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("(30)*2 + 5*(2-1)",65.0);
	}
	
	public void testMixednestedParenExpression() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("(30*2+(4-((2)))*3-5*93)*2 + 5*(2-1)",-793.0);
	}
	
	public void testPartialMixedParenExpression() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("(30*2+(4-((2)))*3-5*93)",-399.0);
	}
	
	public void testNeatNegativeExponentation() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("-1^(-1)",-1.0);
		runExpressionTest("-1.1^(-1)",-0.9090909090909091);	
	}
	
	public void testMessyNegativeExponentiation() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("1.1^(-1.1)",0.900467507467747);
		runExpressionTest("-1.1^(-1.1)",-0.9090909090909091);
		
	}
	
	public void testVladaExample() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("-6.3^(10.149001366)",44.0);
		runExpressionTest("-6.3^(5.710961815)^(1.33)",44.0);
		runExpressionTest("-6.3^(3/5^(-.4))^(1.33)",44.0);
		runExpressionTest("-6.3^(3/5^-.4)^(1.33)",44.0);
		runExpressionTest("-6.3^(3/5^---.4)^(1.33)",44.0);
		runExpressionTest("-6.3^(3/5^---.4)^(4/3)",44.0);
		runExpressionTest("-6.3^(3/5^---.4*(5))^(4/3)",44.0);
		runExpressionTest("(-6.3^(3/5^---.4*(5))^(4/3))",44.0);
		runExpressionTest("(-6.3^(3/5^---.4*(5))^(----4/3))",44.0);
		runExpressionTest("(-6.3^(3/5^---.4*(5))^(----4/3))*-9",44.0);
		runExpressionTest("(-6.3^(3/5^---.4*(5))^(----4/3))*-9*(-2)",44.0);
		runExpressionTest("(-6.3^(3/5^---.4*(5))^(----4/3))*-9*(2-4)",44.0);
		runExpressionTest("(-6.3^(3/5^---.4*(5))^(----4/3))*-9*(2-4)+(-7)",44.0);
		runExpressionTest("(-6.3^(3/5^---.4*(5))^(----4/3))*-9*(2-4)+(-7)+.01",44.0);
		runExpressionTest("-((-6.3^(3/5^---.4*(5))^(----4/3))*-9*(2-4)+(-7)+.01)",44.0);
	}
	
	public void testVladaExampleEasy() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		runExpressionTest("5^---.4", 0.5253055608807534);
		runExpressionTest("4",4.0);
		runExpressionTest("-4",-4.0);
		runExpressionTest("--4",4.0);
		runExpressionTest("---4",-4.0);
		runExpressionTest("----4",4.0);
	}
	
	
	public void testPartialMixedParenExpressionMixedDecimal() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("(30*2.0+(4-((2.0)))*3-5*93)",-399.0);
	}
	
	public void testPartialMixedParenExpressionMixedDecimal2() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("(30*2.0+(4-((2)))*3-5*93)",-399.0);
	}
	
	public void testPartialMixedParenExpression2() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("30*2+(4-((2)))*3-5*93",-399.0);
	}
	
	public void testPartialMixedParenExpression3() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("30*2+(4-1)*3-5*93",-396.0);
	}
	
	public void testPartialMixedParenExpression4() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
			
			runExpressionTest("30*2+(3)*3-5*93",-396.0);
	}
	
	public void testPartialMixedParenExpression5() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("30*2+3*3-5*93",-396.0);
	}
	
	public void testPartialMixedParenExpression5Plus() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("30*2+3*3+5*93",534.0);
	}
	
	public void testPartialMixedParenExpression6() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("30*2+3*3",69.0);
	}
	
	
	public void testPartialMixedDivisionAndMultiplication() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("30*2/3/2*3",30.0);
		runExpressionTest("30*2*3/2*3",270.0);
	}

	public void testPartialMixedDivisionAndMultiplicationWithDecimals() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("30.0*2.0/3.0/2.0*3.0",30.0);
		runExpressionTest("30.0*2.0*3.0/2.0*3.0",270.0);
	}

	public void testPartialMixedDivisionAndMultiplicationWithMixedDecimals() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("30.0*2.0/3.0/2*3.0",30.0);
		runExpressionTest("30*2*3.0/2.0*3.0",270.0);
	}
	
	//CURRENTLY FAILING...casual negative handling not yet implemented
	public void testMixedNestedParenExpressionWithNegatives() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		runExpressionTest("(30*2+( (-4)-((2)) )*3-5*93*((-1)-2))*2",2874.0);
		runExpressionTest("(30*2+( (-4)-((2)) )*3-5*93*(-1-2))*2",2874.0);
		runExpressionTest("(30*2+( (-4)-(2) )*3-5*93*(-1-2))*2",2874.0);
		runExpressionTest("(30*2+( -4-(2) )*3-5*93*(-1-2))*2",2874.0);
		runExpressionTest("(30*2+( -4-2 )*3-5*93*(-1-2))*2",2874.0);
		runExpressionTest("(30*2+( -6 )*3-5*93*(-1-2))*2",2874.0);
		runExpressionTest("(30*2+( -6 )*3-5*93*(-3))*2",2874.0);
		runExpressionTest("(30*2+( -6 )*3-5*93*(-3))",1437.0);
		runExpressionTest("30*2+( -6 )*3-5*93*(-3)",1437.0);
		runExpressionTest("30*2+-6*3-5*93*(-3)",1437.0);
		runExpressionTest("30*2+-18-5*93*(-3)",1437.0);
		runExpressionTest("30*2+-18-5*93*-3",1437.0);
		runExpressionTest("42-5*93*-3",1437.0);
		runExpressionTest("42-465*-3",1437.0);
		runExpressionTest("42--1395",1437.0);
		runExpressionTest("42--1395",1437.0);
		runExpressionTest("42+1395",1437.0);
		runExpressionTest("1437.0",1437.0);
	}	
	
	public static void runExpressionTest(String expression, Double value) throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		Expression e = new Expression(expression);
		assertEquals(Double.valueOf(value),e.getExpressionValue());
	}
	
	public void testMixedNestedExpression() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		

		runExpressionTest("( (-4)-((2)) )*3-5*93*(-3)",1377.0); //ok 
		runExpressionTest("( (-4)-((2)) )*3-5*93*(-1-2)",1377.0); //not ok
		runExpressionTest("30*2+( (-4)-((2)) )*3-5*93*(-1-2)",1437.0);
		runExpressionTest("(30*2+( (-4)-((2)) )*3-5*93*(-1-2))",1437.0);
		runExpressionTest("(30*2+( (-4)-((2)) )*3-5*93*(-1-2))*2",2874.0);
	}
	
	
	public void testNegationWithSubtraction() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		
		runExpressionTest("-1+-2",-3.0); //not ok
		runExpressionTest("-1-2",-3.0); //not ok

		runExpressionTest("(-1+-2)",-3.0); //not ok
		runExpressionTest("(-1-2)",-3.0); //not ok
		
	}
}
