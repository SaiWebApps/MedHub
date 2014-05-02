package patientProfile.handlers;

import baseActivity.handler.ActivityHandler;
import edu.cmu.medhub.R;
import entity.User;
import android.app.Activity;
import android.widget.TextView;

public class DisplayPatientProfileHandler extends ActivityHandler {	
	private TextView patientProfileHeading;
	private TextView patientEmailView;
	
	public DisplayPatientProfileHandler(Activity a) {
		User u = a.getIntent().getParcelableExtra("user");
		this.patientProfileHeading = (TextView) a.findViewById(R.id.patientProfileHeading);
		this.patientEmailView = (TextView) a.findViewById(R.id.patientEmail);
		patientProfileHeading.setText(u.getFirstName() + " " + u.getLastName() + "'s Profile");
		patientEmailView.setText("Email: " + u.getEmail());
	}
	
	public void updateProfileInfo(Activity a) {
		User u = a.getIntent().getParcelableExtra("user");
		patientProfileHeading.setText(u.getFirstName() + " " + u.getLastName() + "'s Profile");
		patientEmailView.setText("Email: " + u.getEmail());
	}
	
	@Override
	public boolean validateFields() {
		return false;
	}
}