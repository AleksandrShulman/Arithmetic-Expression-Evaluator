package exception;

public class InvalidOperandException extends Exception {

	String s;
	
	public InvalidOperandException() {
		
	}
	
	public InvalidOperandException(String message) {
		
		s = message;
	}
	
	@Override
	public String getMessage() {
		
		return s;
	}
	
}
