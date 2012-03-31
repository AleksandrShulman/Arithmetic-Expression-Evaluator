package suite;

import java.util.List;

import exception.MalformedDecimalException;
import exception.MalformedParenthesisException;

public class Token {

	private TokenType type;
	
	private Object underlyingObject;
	
	public void setValue(Object o) throws MalformedParenthesisException, MalformedDecimalException {
		
		if (o instanceof Operation) {

			this.setType(TokenType.OPERATION);
			setUnderlyingObject(o);

		} else if (o instanceof Integer) {

			this.setType(TokenType.NUMERIC);
			setUnderlyingObject(o);
			
		} else if (o instanceof Double) {

			this.setType(TokenType.NUMERIC);
			setUnderlyingObject(o);

		} else if (o instanceof String) {

			// check if all the items are digits...if so, then it's a double
			boolean isDigit = true;
			boolean haveJustSeenDecimal = false; //next item better be a digit
			for (Character c : o.toString().toCharArray()) {

				if(haveJustSeenDecimal && !Character.isDigit(c)) {
					
					throw new MalformedDecimalException();
				}
				
				if (!Character.isDigit(c) && c!='.') {
					
					isDigit = false;
				}
				
				if(c=='.') {
					haveJustSeenDecimal = true;
				} else {
					haveJustSeenDecimal=false;
				}
			}

			if (!isDigit) {

				this.setType(TokenType.PARENTHETICAL_EXPRESSION);

				String expString = (String) o;
				if (!expString.contains("(")
						|| !expString.contains(")")
						|| !Expression.isParenExpression(expString
								.toCharArray())) {
					throw new IllegalArgumentException(
							"Parenthetical expression without one or more parenthesis");
				}
				Expression.verifyValidParenStructuring(expString);
				setUnderlyingObject(o);

			} else {

				this.setType(TokenType.NUMERIC);
				this.setUnderlyingObject(Double.valueOf((String) o));
			}
		} else {

			throw new IllegalArgumentException("invalid type");
		}

	}
	
	public Token(Object o) throws MalformedParenthesisException, MalformedDecimalException {
		setValue(o);
	}

	private void setType(TokenType type) {
		this.type = type;
	}

	public TokenType getType() {
		return type;
	}

	private void setUnderlyingObject(Object underlyingObject) {
		this.underlyingObject = underlyingObject;
	}

	public Object getUnderlyingObject() {
		return underlyingObject;
	}
	
	public boolean equals(Token t) {
		
		if(!(t.getType()==type)) {
			return false;
		}
		
		if(!(t.getUnderlyingObject().equals(this.underlyingObject))) {
			return false;
		}
		return true;
		
	}
	
	public static boolean equateTokenList(List<Token> a, List<Token> b) {
		
		if(a.size()!=b.size())
			return false;
		
		int i=0;
		for(Token t : b) {
			
			if(!t.equals(a.get(i))) {
				return false;
			}
			i++;
		}

		return true;
	}
	
	@Override
	public String toString() {
		
		String s = this.getType() + ": " + this.underlyingObject.toString();
		return s;
	}
	
	public boolean isOperation() {
		
		return this.type==TokenType.OPERATION;
	}
}
