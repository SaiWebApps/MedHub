package businessLogic;

import edu.cmu.medhub.R;
import entities.Doctor;
import entities.User;
import android.app.Activity;
import android.widget.TextView;

/**
 * Display doctor's profile.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class DisplayDoctorProfileHandler extends ActivityHandler {	
	private TextView doctorProfileHeading;
	private TextView credentialsView;

	/**
	 * Initialize doctor's profile views.
	 */
	public DisplayDoctorProfileHandler(Activity a) {
		this.doctorProfileHeading = (TextView) a.findViewById(R.id.doctorProfileMessage);
		this.credentialsView = (TextView) a.findViewById(R.id.credentials);
		updateProfileInfo(a);
	}

	/**
	 * Update doctor's profile information.
	 */
	public void updateProfileInfo(Activity a) {
		User u = (User) a.getIntent().getParcelableExtra("user");
		Doctor d = (Doctor) a.getIntent().getParcelableExtra("doctor");
		String text = u.getFirstName() + " " + u.getLastName() + "'s Profile\nEmail: " + 
				u.getEmail() + "\nUser Type: Doctor";
		doctorProfileHeading.setText(text);
		if (d.getCredentials() != null) {
			credentialsView.setText(d.getCredentials());
		}
	}

	@Override
	public boolean validateFields() {
		return false;
	}
}