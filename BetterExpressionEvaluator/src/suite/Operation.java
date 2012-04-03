package suite;


public enum Operation {

	
	PARENTHESIS('('),
	MULTIPLICATION('*'),
	ADDITION('+'),
	SUBTRACTION('-'),
	NEGATION('-'),
	DIVISION('/'),
	EXPONENTIATION('^'),
	NO_OP(' ');
	
	private char underlyingCharacter;
	TokenType type;
	
	Operation(char c) {
		
		this.underlyingCharacter = c;
	}
	
	public static Operation memberOf(char c) {
		
		for(Operation o : Operation.values()) {
			
			if(c==o.getUnderlyingCharacter()) {
				return o;
			}
		}
		
		return null;
	}
	
	private char getUnderlyingCharacter() {
		
		return underlyingCharacter;
	}
	
	public TokenType getTokenType() {
		
		if(this==Operation.NEGATION) {
			
			return TokenType.NEGATOR;
		} else {
			return TokenType.OPERATION;
		}
	}
}
