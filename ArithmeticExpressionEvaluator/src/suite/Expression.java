package suite;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

public class Expression {

	private String expressionString;
	private Integer expressionValue;
	
	public static HashSet<String> specialChars;
	
	private Operation operation;
	private Expression operand1;
	private Expression operand2;
	
	public Expression(String e) {
		
		this.expressionString = e;
		calculateExpressionValue();
	}
	
	public Expression(int i) {
	
		this.expressionString = String.valueOf(i);
		this.expressionValue = i;
		
	}
	
	public Operation getParentOperation() {

		if(operation==null) {
			
			calculateExpressionValue();
		}
		return this.operation;
	}

	public Expression getOperand1() {
		
		if(operand1==null) {
			
			calculateExpressionValue();
		}
		return this.operand1;
	}
	
	public Expression getOperand2() {
	
		if(operand1==null) {
			
			calculateExpressionValue();
		}
		return this.operand2;
	}
	
	public static char[] tokenize(String exp) {
		
		if(exp==null) {
			
			throw new IllegalArgumentException();
		}
		
		List<String> tokenList = new ArrayList<String>();
		char[] expAsChars = exp.toCharArray();
		
		for(int i=0;i<expAsChars.length;i++) {
			
			
			if(specialChars.contains(String.valueOf(expAsChars[i])) ){
				
				tokenList.add(String.valueOf(expAsChars[i]));
			}
			
		}
		
		char[] charArray = new char[tokenList.size()];
		
		int i = 0;
		for(String s : tokenList) {
			
			charArray[i] = s.charAt(0);
		}
		
		return charArray;
	}
	
	
	private void calculateExpressionValue() {
		
		char[] tokens = removeParens(tokenize(this.expressionString));
		
		//now that parens are removed, do a first pass and get any plus/minus
		
		
	}
	
	private char[] removeParens(char[] tokens) {
		
		if(tokens[0]=='(' && tokens[tokens.length-1]==')') {
		
			char[] charArray = new char[tokens.length -2];
			String.valueOf(tokens).getChars(1, tokens.length-1, charArray, 0);
			
		}
		
		return tokens;
		
	}
	
	public Integer getExpressionValue() {
	
		if(expressionValue==null) {
			calculateExpressionValue();
		}
		
		return expressionValue;
	}
	
	public static boolean isToken(char c) {
	
		
		return false;
		
	}
}
