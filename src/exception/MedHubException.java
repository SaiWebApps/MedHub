package exception;

import android.util.Log;

public class MedHubException extends Exception {
	private int errorNo; //error code
	private String errorMsg; //error message

	/**
	 * Default, no-args constructor.
	 */
	public MedHubException() {
		super();
		printMyProblem();
	}

	/**
	 * Initialize this Exception class with given message.
	 * @param errorMsg - Message to initialize this class with
	 */
	public MedHubException(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
		printMyProblem();
	}

	/**
	 * Initialize this Exception class with given error number.
	 * @param errorNo - Error Number to initialize this class with
	 */
	public MedHubException(int errorNo) {
		super();
		this.errorNo = errorNo;
		printMyProblem();
	}

	/**
	 * Initialize this Exception class with given message and error num.
	 * @param errorMsg - Message to initialize this class with
	 * @param errorNo - Error Number to initialize this class with
	 */
	public MedHubException(int errorNo, String errorMsg) {
		super();
		this.errorNo = errorNo;
		this.errorMsg = errorMsg;
		printMyProblem();
	}

	/**
	 * @return error number/code
	 */
	public int getErrorNo() {
		return errorNo;
	}

	/**
	 * Set this Exception's error number.
	 * @param errorNo - Identifies cause of exception
	 */
	public void setErrorNo(int errorNo) {
		this.errorNo = errorNo;
	}

	/**
	 * @return error message
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * Set error message.
	 * @param errorMsg - New error message
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * Print out details about the current error.
	 */
	public void printMyProblem() {
		StringBuilder builder = new StringBuilder("MedHubException [errorNo=");
		builder.append(errorNo);
		builder.append(", errorMsg=");
		builder.append(errorMsg);
		builder.append("]\n");
		Log.v("MedHubException", builder.toString());
	}
}