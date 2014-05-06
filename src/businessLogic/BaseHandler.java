package businessLogic;

import activities.BaseActivity;
import android.content.Context;
import android.widget.TextView;

/**
 * Expectations for all activity handlers.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public interface BaseHandler {
	
	/**
	 * @return true if fields were successfully validated, false ow
	 */
	public boolean validateFields();
	
	/**
	 * Display error messages from validation in given text view.
	 * @param tv - TextView showing validation errors
	 */
	public void displayErrorMessages(TextView tv);

	/**
	 * Move from the current Activity to the next Activity (page transition).
	 * @param current - Context of Activity currently being displayed
	 * @param next - Class of next Activity to display
	 */
	public void nextActivity(Context current, Class<? extends BaseActivity> next);
}