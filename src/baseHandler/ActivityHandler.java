package baseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.widget.TextView;

public abstract class ActivityHandler {
	protected List<String> errorMessages = new ArrayList<String>();
	protected Map<String, Parcelable> contextInfo = new HashMap<String, Parcelable>();
	
	/**
	 * @return true if user inputs are valid, false otherwise; add
	 * validation errors to errorMessages list
	 */
	public abstract boolean validateFields();
		
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
	
	/**
	 * Move from the current Activity to the next Activity (page transition).
	 * @param current - Context of Activity currently being displayed
	 * @param next - Class of next Activity to display
	 */
	public void nextActivity(Context current, Class next) {
		Intent moveToProfile = new Intent(current, next);
		for (String key : contextInfo.keySet()) {
			moveToProfile.putExtra(key, contextInfo.get(key));
		}
		current.startActivity(moveToProfile);
	}
}