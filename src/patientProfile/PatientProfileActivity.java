package patientProfile;

import patientProfile.fragments.PatientProfileFragment;
import patientProfile.handlers.PatientProfileHandler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import baseActivity.BaseActivity;
import edu.cmu.medhub.R;
import entity.Patient;
import entity.User;

/**
 * Handle user login and registration.
 * @author Sairam Krishnan (sbkrishn)
 */
public class PatientProfileActivity extends BaseActivity {
	private User u;
	private Patient p;
	private PatientProfileHandler ph;

	@Override
	public void initializeTabMap() {
		tabMap.put("Profile", new PatientProfileFragment());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Retrieve information about the user who has just logged in/registered.
		//This info will be displayed in the user's profile.
		u = (User) getIntent().getParcelableExtra("user");		
		p = (Patient) getIntent().getParcelableExtra("patient");
		ph = new PatientProfileHandler(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ph.initializeFields(u);
	}
}