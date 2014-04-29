package dbLayout;

public enum CreationError {
	ALREADY_EXISTS(-2), UNABLE_TO_CREATE(-1);
	int code;
	
	private CreationError(int code) { this.code = code; }
	public int getCode() { return code; }
}