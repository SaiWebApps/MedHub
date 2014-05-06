package businessLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import activities.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.widget.TextView;

/**
 * Base activity handler - must provide field validation and error-message
 * display. Also provides a way to pass on contextInfo to next Activity.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public abstract class ActivityHandler implements BaseHandler {
	protected List<String> errorMessages = new ArrayList<String>();
	protected Map<String, Parcelable> contextInfo = new HashMap<String, Parcelable>();
	
	public abstract boolean validateFields();
		
	@Override
	public void displayErrorMessages(TextView v) {
		String errorList = "";
		for (String error : errorMessages) {
			errorList += error + "\n";
		}
		v.setText(errorList.trim());
	}
	

	@Override
	public void nextActivity(Context current, Class<? extends BaseActivity> next) {
		Intent moveToProfile = new Intent(current, next);
		for (String key : contextInfo.keySet()) {
			moveToProfile.putExtra(key, contextInfo.get(key));
		}
		current.startActivity(moveToProfile);
	}
}