package suite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import exception.InvalidOperandException;
import exception.MalformedDecimalException;
import exception.MalformedParenthesisException;
import exception.MalformedTokenException;

public class Expression {

	public static void main(String args[]) throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		while(true) {
		System.out.print("Welcome. Please enter an expression: ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
	      String inputExpression = null;

	      //  read the username from the command-line; need to use try/catch with the
	      //  readLine() method
	      try {
	         inputExpression = br.readLine();
	      } catch (IOException ioe) {
	         System.out.println("IO error trying to read your name!");
	         System.exit(1);
	      }

	      if(inputExpression.equals("exit") || inputExpression.equals("Exit"))
	    	  System.exit(0);
	      
	      try {
	      Expression e = new Expression(inputExpression);
	      
	      	System.out.println("Evaluates to: " + Double.valueOf(e.getExpressionValue()));
	      } catch (IllegalArgumentException e) {
	    	  
	    	  System.out.println(e.getLocalizedMessage());
	      }
	      
		}
	}
	
	LinkedList<Token> expressionList;
	final String expressionString;
	Double expressionValue;
	boolean hasBeenEvaluated = false;
	final static char[] WhiteSpace = { '\n', '\r', '\f', '\t',' '};
	public final static String illegalArgString = "Baahh! Bad input. You suck.";
	
	public Expression(String expressionString) throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		if(expressionString==null) {
			
			throw new IllegalArgumentException(illegalArgString);
		}
		
		this.expressionString = removeWhiteSpace(expressionString.toCharArray());
		verifyValidParenStructuring();
		
		
		if(expressionString.length()==0) {
			this.expressionValue = 0.0;
			hasBeenEvaluated = true;
		}
		
		this.getExpressionValue();
	}

	public Expression(LinkedList<Token> tokens) {
		
		if(tokens==null) {
			
			throw new IllegalArgumentException("Token list cannot be null");
		}
		
		this.expressionList = tokens;
		this.expressionString = null; //TODO: Find a reasonable way to deal with this
	}
	
	private void tokenize() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		verifyValidParenStructuring();
		if(!hasBeenEvaluated) {
			
			this.expressionList = tokenizeImpl(removeWhiteSpace(this.expressionString.toCharArray()));
		}
	}
	
	public static LinkedList<Token> tokenizeImpl(String expressionString) throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {

		LinkedList<Token> tempList = new LinkedList<Token>();
		
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
								Token t = new Token(tempString.toString());
								if(t.getType()!=TokenType.PARENTHETICAL_EXPRESSION) {
									throw new MalformedTokenException();
								}
								tempList.add(t);
								tempString = new StringBuilder();
							}
						}

						tempString.append(c);
						parenStack.pop();
						if(parenStack.size()==0) {
							//end the parenExp
							tempList.add(new Token(tempString.toString()));
							tempString = new StringBuilder();
							
						}
						
					} catch (EmptyStackException e) {

						throw new MalformedParenthesisException();
					}
				}
			} else if (Operation.memberOf(c) != null) {

				if (c == '-') {

					if (parenStack.size() == 0) {
						// ok so what do I do if I see a negative sign?

						// well if there is nothing ahead of me, make me a
						// negator
						Token t;
						
						if(tempString.length()>0) {
							
							Token x = new Token(tempString.toString());
							tempList.add(x);
							tempString= new StringBuilder();
						}
					
						
						if (tempList.size() > 0) {
							//see what the previous value would have been if we had instantiated the list
							t = tempList.getLast();
						} else {
							t = null;
						}
						if (t == null && tempString.length() == 0) {

							tempList.add(new Token(Operation.NEGATION));
						} else if (t == null && tempString.length() > 0) {
							Token previousToken = new Token(tempString.toString());
							tempList.add(previousToken);
							tempString =  new StringBuilder();
							tempList.add(new Token(Operation.SUBTRACTION));
						} else {

							// if there is anything...it's a minus sign unless
							// there is an operator up front
							if (t.getUnderlyingObject() instanceof Operation) {

								tempList.add(new Token(Operation.NEGATION));
							} else {

								tempList.add(new Token(Operation.SUBTRACTION));
							}
						}
					} else {

						tempString.append(c);
					}
				}
				
				else if (parenStack.size() == 0) {
					
					//dump out what was there
					if (tempString.toString().length() > 0) {
						Token tOperand = new Token(tempString.toString());
						tempList.add(tOperand);
					}
	
					Token tOperation = new Token(Operation.memberOf(c));
					if(tOperation.getType()!=TokenType.OPERATION)
						throw new MalformedTokenException();

					
					tempList.add(tOperation);
					tempString = new StringBuilder();
				} else {
				
					tempString.append(c);
				}
				
			} else {

				throw new IllegalArgumentException("Shouldn't even get here");
			}
		}
		
		// flush what's there
		Token t = null;
		if (tempString.toString().length() > 0)
			t = new Token(tempString.toString());
		if (t != null) {
			tempList.add(t);
		}
		tempString = new StringBuilder();

		turnNegationsIntoNegativeMultplication(tempList);
		return tempList;
	}
	
	public Double getExpressionValue() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		if(hasBeenEvaluated) {
			return this.expressionValue;
		} else {
			
			return calculateExpressionValue();
		}
	}
	
	public static Double calcalateExpressionValue(LinkedList<Token> tokens) throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		performParentheticalExpressionEvaluation(tokens);
		performExponentiationSubstitution(tokens);
		performMultiplicationSubstitution(tokens);
		performSubtractionSubstitution(tokens);
		performAdditionSubstitution(tokens);
		
		return Double.valueOf(tokens.get(0).getUnderlyingObject().toString());
	}
	
	private Double calculateExpressionValue() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {

		if(this.expressionList==null) {
			
			tokenize();
		}
		
		//Magic happens here (i.e. PEMDAS)
		performParentheticalExpressionEvaluation();
		performExponentiationSubstitution();
		performMultiplicationAndDivisionSubstitution();
		performSubtractionSubstitution();
		performAdditionSubstitution();
		
		Double value = Double.valueOf(this.expressionList.get(0).getUnderlyingObject().toString());
		this.expressionValue = value;
		this.hasBeenEvaluated = true;
		//assume that we've burned it down to one object at this point
		return value;
		
	}
	
	public void performParentheticalExpressionEvaluation() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		performParentheticalExpressionEvaluation(this.expressionList);
	}
	
	public static void performParentheticalExpressionEvaluation(List<Token> exprObjs) throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		int i = 0;
		for(Token o : exprObjs) {
			
			if(o.getUnderlyingObject().toString().contains("(")) {
				
				//is a parenthetical expression
				Expression e = new Expression(String.valueOf(removeSurroundingParens(o.getUnderlyingObject().toString().toCharArray())));
				Token t = new Token(Double.valueOf(e.getExpressionValue()));
				if(t.getType()!=TokenType.NUMERIC)
					throw new MalformedTokenException();
				
				//get what it evaluates to in order to create a terminal
				exprObjs.set(i,t); 
			}
			i++;
		}
	}
	
	private void performMultiplicationAndDivisionSubstitution() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		performMultiplicationSubstitution(this.expressionList);
	}
	
	public static void performMultiplicationSubstitution(List<Token> objs) throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
	int i = 0;
		
		while(i<objs.size()) {
			
			if(objs.get(i).getUnderlyingObject().equals(Operation.MULTIPLICATION)) {
				
				performMultiplicationSubstitution(i,objs);
				
			} else if(objs.get(i).getUnderlyingObject().equals(Operation.DIVISION)) {
				
				performDivisionSubstitution(i, objs);
				
			} else {
				i++;
			}
		}
	}
	
	private static void performMultiplicationSubstitution(int i, List<Token> objs) throws MalformedParenthesisException, MalformedDecimalException, MalformedTokenException {
		
		Double first;
		Double second;
		// final value goes in i-1
		if (objs.get(i - 1).getType()==TokenType.NUMERIC) {

			first = Double.valueOf(objs.get(i - 1).getUnderlyingObject().toString());
		} else {

			first = Double.valueOf((String) objs.get(i - 1).getUnderlyingObject());
		}

		if (objs.get(i + 1).getType()==TokenType.NUMERIC) {

			second = Double.valueOf(objs.get(i + 1).getUnderlyingObject().toString());
		} else {

			second = Double.valueOf((String) objs.get(i + 1).getUnderlyingObject());
		}
		
		Token t = new Token(Double.valueOf(first*second));
		if(t.getType()!=TokenType.NUMERIC)
			throw new MalformedTokenException();
		
		objs.set(i - 1, t);
		objs.remove(i + 1);
		objs.remove(i);
		i--;
	}
	
	
	private static void performDivisionSubstitution(int i, List<Token> objs) throws MalformedParenthesisException, MalformedDecimalException, MalformedTokenException {
		
		Double first;
		Double second;
		// final value goes in i-1
		if (objs.get(i - 1).getType()==TokenType.NUMERIC) {

			first = (Double) objs.get(i - 1).getUnderlyingObject();
		} else {

			first = Double.valueOf((String) objs.get(i - 1).getUnderlyingObject());
		}

		if (objs.get(i + 1).getType()==TokenType.NUMERIC) {

			second = (Double) objs.get(i + 1).getUnderlyingObject();
		} else {

			second = Double.valueOf((String) objs.get(i + 1).getUnderlyingObject());
		}
		
		Token t = new Token(Double.valueOf(first / second));
		if(t.getType()!=TokenType.NUMERIC)
			throw new MalformedTokenException();
		
		objs.set(i - 1, t);
		objs.remove(i + 1);
		objs.remove(i);
		i--;
	}
	
	public void performDivisionSubstitution() throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
		performDivisionSubstitution(this.expressionList);
	}
	
	public static void performDivisionSubstitution(List<Token> objs) throws MalformedParenthesisException, InvalidOperandException, MalformedTokenException, MalformedDecimalException {
		
	int i = 0;
		
		while(i<objs.size()) {
			
			if(objs.get(i).getUnderlyingObject().equals(Operation.DIVISION)) {
				
				Double first;
				Double second;
				// final value goes in i-1
				if (objs.get(i - 1).getType()==TokenType.NUMERIC) {

					first = (Double) objs.get(i - 1).getUnderlyingObject();
				} else {

					first = Double.valueOf((String) objs.get(i - 1).getUnderlyingObject());
				}

				if (objs.get(i + 1).getType()==TokenType.NUMERIC) {

					second = (Double) objs.get(i + 1).getUnderlyingObject();
				} else {

					second = Double.valueOf((String) objs.get(i + 1).getUnderlyingObject());
				}
				
				Token t = new Token(Double.valueOf(first / second));
				if(t.getType()!=TokenType.NUMERIC)
					throw new MalformedTokenException();
				
				objs.set(i - 1, t);
				objs.remove(i + 1);
				objs.remove(i);
				i--;
				
			} else {
				i++;
			}
		}
	}

	
	private void performSubtractionSubstitution() throws MalformedParenthesisException, MalformedTokenException, MalformedDecimalException {
		
		performSubtractionSubstitution(this.expressionList);
	}
	
	public static void performSubtractionSubstitution(List<Token> objs) throws MalformedParenthesisException, MalformedTokenException, MalformedDecimalException {
		
		int i = 0;
		
		while(i<objs.size()) {
			
			if(objs.get(i).getUnderlyingObject().equals(Operation.SUBTRACTION)) {
				
				Double first;
				Double second;
				// final value goes in i-1
				if (objs.get(i - 1).getType()==TokenType.NUMERIC) {

					first = Double.valueOf(objs.get(i - 1).getUnderlyingObject().toString());
				} else {

					first = Double.valueOf((String) objs.get(i - 1).getUnderlyingObject());
				}

				if (objs.get(i + 1).getType()==TokenType.NUMERIC) {

					second = Double.valueOf(objs.get(i + 1).getUnderlyingObject().toString());
				} else {

					second = Double.valueOf((String) objs.get(i + 1).getUnderlyingObject());
				}
				
				Token t = new Token(Double.valueOf(first-second));
				if(t.getType()!=TokenType.NUMERIC)
					throw new MalformedTokenException();
				
				objs.set(i - 1, t);
				objs.remove(i + 1);
				objs.remove(i);
				i--;
				
			} else {
				i++;
			}
		}
	}
	
	private void performAdditionSubstitution() throws MalformedParenthesisException, MalformedTokenException, MalformedDecimalException {

		performAdditionSubstitution(this.expressionList);
	}
	
	public static void performAdditionSubstitution(List<Token> objs)
			throws MalformedParenthesisException, MalformedTokenException, MalformedDecimalException {

		int i = 0;

		while (i < objs.size()) {
			Object underlyingObject = objs.get(i).getUnderlyingObject();
			if (objs.get(i).getUnderlyingObject() instanceof Operation) {
				if (((Operation) underlyingObject) == Operation.ADDITION) {

					Double first;
					Double second;
					// final value goes in i-1
					if (objs.get(i - 1).getType() == TokenType.NUMERIC) {

						first = (Double) objs.get(i - 1).getUnderlyingObject();
					} else {

						first = Double.valueOf((String) objs.get(i - 1)
								.getUnderlyingObject());
					}

					if (objs.get(i + 1).getType() == TokenType.NUMERIC) {

						second = (Double) objs.get(i + 1)
								.getUnderlyingObject();
					} else {

						second = Double.valueOf((String) objs.get(i + 1)
								.getUnderlyingObject());
					}

					Token t = new Token(Double.valueOf(first + second));
					if (t.getType() != TokenType.NUMERIC)
						throw new MalformedTokenException();

					objs.set(i - 1, t);
					objs.remove(i + 1);
					objs.remove(i);
					i--;

				} 
			}
			i++;
		}
	}

	private void performExponentiationSubstitution() throws MalformedParenthesisException, MalformedTokenException, MalformedDecimalException, InvalidOperandException {

		performExponentiationSubstitution(this.expressionList);
	}
	
	public static void performExponentiationSubstitution(LinkedList<Token> objs)
			throws MalformedParenthesisException, MalformedTokenException, MalformedDecimalException, InvalidOperandException {

		int i = 0;

		while (i < objs.size()) {
			Object underlyingObject = objs.get(i).getUnderlyingObject();
			if (objs.get(i).getUnderlyingObject() instanceof Operation) {
				if (((Operation) underlyingObject) == Operation.EXPONENTIATION) {
					Double base;
					if (objs.get(i - 1).getType() == TokenType.NUMERIC) {

						base = (Double) objs.get(i - 1).getUnderlyingObject();
					} else {

						base = Double.valueOf((String) objs.get(i - 1)
								.getUnderlyingObject());
					}
					
					LinkedList<Token> expressionTokens = new LinkedList<Token>();
					
					int j = i+1;
					while(j<objs.size() && (objs.get(j).getType()!=TokenType.OPERATION || objs.get(j).getUnderlyingObject()==Operation.EXPONENTIATION)) {
						
						//keep going until you hit any operation except exponentiation
						expressionTokens.add(objs.get(j));
						j++;
					}

					Double exponentValue = Expression.calcalateExpressionValue(expressionTokens);
					Double baseValue = Math.pow(base,exponentValue);
					
					//go through and remove the evaluated expression
					for(int k = j -1; k> i-1 ; k--) {
						
						objs.remove(k);
					}

					Token t = new Token(Double.valueOf(baseValue));
					if(t.getType()!=TokenType.NUMERIC)
						throw new MalformedTokenException();
					
					objs.set(i - 1, t);
				}		
			}
			i++;
		}
	}

	
	public static void verifyValidParenStructuring(String expString) throws MalformedParenthesisException {
		

		Stack<Character> s = new Stack<Character>();

		for (Character c : expString.toCharArray()) {

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
	
	private void verifyValidParenStructuring()
			throws MalformedParenthesisException {

		Expression.verifyValidParenStructuring(this.expressionString);

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
	
	public List<Token> getExpressionList() {
		
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

	public static boolean isParenExpression(char[] chars) {

		return chars[0] == '(' && chars[chars.length - 1] == ')';
	}
	
	public static void turnNegationsIntoNegativeMultplication(LinkedList<Token> list) throws MalformedParenthesisException, MalformedDecimalException, InvalidOperandException, MalformedTokenException {
		
		//TODO: Add parenthesis around expressions that we modify
		
		//Create a parenthetical expression out of these tokens
		//This is the all the negations and the token that they
		//modify
		//<Negation><Negation>...<Negation><Token> --> (<Negation><Negation>...<Negation><Token>) 
		
		//go to the first instance of negation
		int i=0;
		int numberOfNegators;
		Integer firstInstanceOfNegation=null;
		
		while(i<list.size()) {
			numberOfNegators=0;
			firstInstanceOfNegation = getNextInstanceOfNegation(list, i);
			
			if(firstInstanceOfNegation==null)
				return;
			
			i=firstInstanceOfNegation;
			int j=firstInstanceOfNegation;
			while(list.get(j).getType()==TokenType.NEGATOR) {
				
				numberOfNegators++;
				j++;
			}
			
			//add a new expression made up of tokens
			//j++ represents the string including the token
			LinkedList<Token> newTokenList = new LinkedList<Token>();
				
			for(int p=0; p<numberOfNegators; p++) {
					newTokenList.add(2*p, new Token(-1));
					newTokenList.add(2*p + 1, new Token(Operation.MULTIPLICATION));
				}
					
			newTokenList.add(list.get(j));  //not getLast...getNEXT

			Expression e= new Expression(newTokenList);
			Double d = e.getExpressionValue();
			
			//Remove all but i
			for(int k = i+numberOfNegators; k> i ; k--) {
				
				list.remove(k);
			}

			Token t = new Token(d);
			if(t.getType()!=TokenType.NUMERIC)
				throw new MalformedTokenException();
			
			list.set(i, t);
			i++;
		}
	}
		
	private static Integer getNextInstanceOfNegation(LinkedList<Token> tokens, int startingAtIndex) {
		
		for(int i=startingAtIndex; i<tokens.size();i++) {
			
			Token t = tokens.get(i);
			if(t.getUnderlyingObject()==Operation.NEGATION) {
				return i;
			}
		}
		return null;
	}
}