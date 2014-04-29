package userProfile;

import android.os.Bundle;
import android.view.View;
import baseActivity.BaseActivity;
import edu.cmu.medhub.R;
import entity.Doctor;
import entity.Patient;
import entity.User;

/**
 * Handle user login and registration.
 * @author Sairam Krishnan (sbkrishn)
 */
public class ProfileActivity extends BaseActivity {
	private User u;
	private Patient p;
	private Doctor d;
	
	@Override
	public void initializeTabMap() {
		
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		u = (User) getIntent().getParcelableExtra("user");
		switch (u.getType()) {
		case 0:
			d = (Doctor) getIntent().getParcelableExtra("doctor");
			break;
		case 1:
			p = (Patient) getIntent().getParcelableExtra("patient");
			break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
}