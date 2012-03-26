package suite;


public enum Operation {

	
	PARENTHESIS('('),
	MULTIPLICATION('*'),
	ADDITION('+'),
	SUBTRACTION('-'),
	NO_OP(' ');
	
	private char underlyingCharacter;
	
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
}
