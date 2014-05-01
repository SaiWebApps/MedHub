package patientProfile.handlers;

import baseActivity.handler.ActivityHandler;
import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import entity.User;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class PatientProfileHandler extends ActivityHandler {
	//EditText
	private TextView firstNameView;
	private TextView lastNameView;
	private TextView emailView;
	private EditText medicalBio;

	//Handle interactions with database.
	private DatabaseManager dbm;

	public PatientProfileHandler(Activity activity) {
		try {
			this.dbm = new DatabaseManager(activity.getApplicationContext());
			this.firstNameView = (TextView) activity.findViewById(R.id.patientFirstName);
			this.lastNameView = (TextView) activity.findViewById(R.id.patientLastName);
			this.emailView = (TextView) activity.findViewById(R.id.patientEmail);			
		} catch (NullPointerException e) {
			Log.e("Profile Init Error", "We are not on the patient profile page.");
		}
	}

	public void initializeFields(User u) {
		this.firstNameView.setText(u.getFirstName());
		this.lastNameView.setText(u.getLastName());
		this.emailView.setText(u.getEmail());
	}
	
	@Override
	public boolean validateFields() {
		return false;
	}
}