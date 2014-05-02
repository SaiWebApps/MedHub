package dbLayout;

public enum AuthError {
	DOES_NOT_EXIST(-2), INCORRECT(-1);
	int code;
	
	private AuthError(int code) { this.code = code; }
	public int getCode() { return code; }
}