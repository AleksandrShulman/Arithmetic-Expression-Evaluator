package suite;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import exception.InvalidOperandException;
import exception.MalformedParenthesisException;

public class Expression {

	List<Object> expressionList;
	final String expressionString;
	int expressionValue;
	boolean hasBeenEvaluated = false;
	
	final static char[] WhiteSpace = { '\n', '\r', '\f', '\t',' '};
	
	public Expression(String expressionString) throws MalformedParenthesisException, InvalidOperandException {
		
		if(expressionString==null) {
			
			throw new IllegalArgumentException();
		}
		
		this.expressionString = removeWhiteSpace(expressionString.toCharArray());
		verifyValidParenStructuring();
		
		
		if(expressionString.length()==0) {
			this.expressionValue = 0;
			hasBeenEvaluated = true;
		}
		
		this.getExpressionValue();
	}

	private void tokenize() throws MalformedParenthesisException, InvalidOperandException {
		
		if(!hasBeenEvaluated) {
			
			this.expressionList = tokenizeImpl(removeWhiteSpace(this.expressionString.toCharArray()));
		}
	}
	
	public static List<Object> tokenizeImpl(String expressionString) throws MalformedParenthesisException, InvalidOperandException {

		List<Object> tempList = new LinkedList<Object>();
		
		//go through the expressionString
		char[] asCharArray = expressionString.toCharArray();
		StringBuilder tempString = new StringBuilder();
		
		Stack<Character> parenStack = new Stack<Character>();
		
		for(char c : asCharArray) {
			//just a plan vanilla character...add it to whatever is going on
			if(c!='(' && c!=')' && Operation.memberOf(c)==null) {

				tempString.append(c);
			} else if (c == '(' || c == ')') {
				if (c == '(') {
					parenStack.push(c);
					tempString.append(c);
				}
				else if (c == ')') {
					try {
						
						if (parenStack.size() == 0) {

							// time to close this parenthetical expression
							if(tempString.toString().length()>0) {
								tempList.add(tempString.toString());
								tempString = new StringBuilder();
							}
						}

						tempString.append(c);
						parenStack.pop();
						
					} catch (EmptyStackException e) {

						throw new MalformedParenthesisException();
					}
				}
			} else if (Operation.memberOf(c) != null) {
			
				
				if (parenStack.size() == 0) {
					tempList.add(tempString.toString());

					tempList.add(Operation.memberOf(c));
					tempString = new StringBuilder();
				} else {
				
					tempString.append(c);
				}
			} else {

				throw new IllegalArgumentException("Shouldn't even get here");
			}
		}
		
		//flush what's there
		
		if(tempString.toString().length()>0)
			tempList.add(tempString.toString());
			tempString = new StringBuilder();
		
		return tempList;
	}
	
	public Integer getExpressionValue() throws MalformedParenthesisException, InvalidOperandException {
		
		if(hasBeenEvaluated) {
			return this.expressionValue;
		} else {
			
			return calculateExpressionValue();
		}
	}
	
	private Integer calculateExpressionValue() throws MalformedParenthesisException, InvalidOperandException {

		tokenize();
		performParentheticalExpressionEvaluation();
		performMultiplicationSubstitution();
		performSubtractionSubstitution();
		performAdditionSubstitution();
		
		Integer value = Integer.valueOf(this.expressionList.get(0).toString());
		this.expressionValue = value;
		this.hasBeenEvaluated = true;
		//assume that we've burned it down to one object at this point
		return value;
		
	}
	
	public void performParentheticalExpressionEvaluation() throws MalformedParenthesisException, InvalidOperandException {
		
		performParentheticalExpressionEvaluation(this.expressionList);
	}
	
	public static void performParentheticalExpressionEvaluation(List<Object> exprObjs) throws MalformedParenthesisException, InvalidOperandException {
		
		int i = 0;
		for(Object o : exprObjs) {
			
			if(o.toString().contains("(")) {
				
				//is a parenthetical expression
				Expression e = new Expression(String.valueOf(removeSurroundingParens(o.toString().toCharArray())));
				
				//get what it evaluates to in order to create a terminal
				exprObjs.set(i, e.getExpressionValue()); 
			}
			i++;
		}
		
	}
	
	public void performMultiplicationSubstitution() throws MalformedParenthesisException, InvalidOperandException {
		
		performMultiplicationSubstitution(this.expressionList);
	}
	
	public static void performMultiplicationSubstitution(List<Object> objs) throws MalformedParenthesisException, InvalidOperandException {
		
	int i = 0;
		
		while(i<objs.size()) {
			
			if(objs.get(i).equals(Operation.MULTIPLICATION)) {
				
				int first;
				int second;
				//final value goes in i-1
				if(objs.get(i-1) instanceof Integer)  {
				
					first = (Integer)objs.get(i-1);
				} else {
					
					first = Integer.valueOf((String)objs.get(i-1));	
				}
				
				if(objs.get(i+1) instanceof Integer) {
					
					second = (Integer)objs.get(i+1);
				} else {
				
					second = Integer.valueOf((String)objs.get(i+1));
				}
				
				objs.set(i-1, first*second);
				objs.remove(i+1);
				objs.remove(i);
				i--;
				
			} else {
				i++;
			}
		}
	}
	
	private void performSubtractionSubstitution() {
		
		performSubtractionSubstitution(this.expressionList);
	}
	
	public static void performSubtractionSubstitution(List<Object> objs) {
		
		int i = 0;
		
		while(i<objs.size()) {
			
			if(objs.get(i).equals(Operation.SUBTRACTION)) {
				
				int first;
				int second;
				//final value goes in i-1
				if(objs.get(i-1) instanceof Integer)  {
				
					first = (Integer)objs.get(i-1);
				} else {
					
					first = Integer.valueOf((String)objs.get(i-1));	
				}
				
				if(objs.get(i+1) instanceof Integer) {
					
					second = (Integer)objs.get(i+1);
				} else {
				
					second = Integer.valueOf((String)objs.get(i+1));
				}
				
				objs.set(i-1, first-second);
				objs.remove(i+1);
				objs.remove(i);
				i--;
				
			} else {
				i++;
			}
		}
	}
	
	private void performAdditionSubstitution() {

		performAdditionSubstitution(this.expressionList);
	}

	public static void performAdditionSubstitution(List<Object> objs) {

		int i = 0;

		while (i < objs.size()) {

			if (objs.get(i).equals(Operation.ADDITION)) {

				int first;
				int second;
				// final value goes in i-1
				if (objs.get(i - 1) instanceof Integer) {

					first = (Integer) objs.get(i - 1);
				} else {

					first = Integer.valueOf((String) objs.get(i - 1));
				}

				if (objs.get(i + 1) instanceof Integer) {

					second = (Integer) objs.get(i + 1);
				} else {

					second = Integer.valueOf((String) objs.get(i + 1));
				}

				objs.set(i - 1, first + second);
				objs.remove(i + 1);
				objs.remove(i);
				i--;

			} else {
				i++;
			}
		}
	}
	
	private void verifyValidParenStructuring() throws MalformedParenthesisException {
	
		Stack<Character> s = new Stack<Character>();

		for (Character c : this.expressionString.toCharArray()) {

			if (c == '(')
				s.push(c);
			try {
				if (c == ')')
					s.pop();
			} catch (EmptyStackException e) {

				throw new MalformedParenthesisException();
			}
		}
		if (!s.isEmpty()) {
			throw new MalformedParenthesisException();
		}
	}
	
	public static String removeWhiteSpace(char[] cArray) {

		StringBuilder s = new StringBuilder();
		for (char c : cArray) {
			
			boolean isWS = false;
			for(char ws : WhiteSpace) {

				if(c==ws) {
					isWS = true;
				}
			}

			if(!isWS) {
				s.append(c);
			}
		}

		return s.toString();
	}
	
	public List<Object> getExpressionList() {
		
		return this.expressionList;
	}
	
	private static char[] removeSurroundingParens(char[] chars) {

		if (isParenExpression(chars)) {

			char[] charArray = new char[chars.length - 2];
			String.valueOf(chars).getChars(1, chars.length - 1, charArray, 0);
			return charArray;
		}

		return chars;
	}

	private static boolean isParenExpression(char[] chars) {

		return chars[0] == '(' && chars[chars.length - 1] == ')';
	}
}
