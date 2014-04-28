package baseHandler;

import java.util.ArrayList;
import java.util.List;
import android.widget.TextView;

public abstract class ActivityHandler {
	protected List<String> errorMessages = new ArrayList<String>();
	
	/**
	 * @return true if user inputs are valid, false otherwise; add
	 * validation errors to errorMessages list
	 */
	public abstract boolean validateFields();
	
	/**
	 * Clear fields after successful form submission.
	 */
	public abstract void clearFields();
	
	/**
	 * Convert the list of error messages into a "\n" delimited String.
	 * Then, display the list in the specified TextView.
	 * @param v - TextView used to display error messages in UI
	 */
	public void displayErrorMessages(TextView v) {
		String errorList = "";
		for (String error : errorMessages) {
			errorList += error + "\n";
		}
		v.setText(errorList.trim());
	}	
}