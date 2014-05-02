package doctorProfile;

import doctorProfile.fragments.DoctorProfileFragment;
import doctorProfile.handlers.DoctorProfileHandler;
import android.os.Bundle;
import android.view.View;
import baseActivity.BaseActivity;
import edu.cmu.medhub.R;
import entity.Doctor;
import entity.User;

/**
 * Handle user login and registration.
 * @author Sairam Krishnan (sbkrishn)
 */
public class DoctorProfileActivity extends BaseActivity {
	private User u;
	private Doctor d;
	private DoctorProfileHandler dh;

	@Override
	public void initializeTabMap() {
		tabMap.put("Profile", new DoctorProfileFragment());
	}

	public int getLayout() {
		return R.layout.doctor_activity;
	}
	
	public int getPagerId() {
		return R.id.doctorPager;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		u = (User) getIntent().getParcelableExtra("user");		
		d = (Doctor) getIntent().getParcelableExtra("doctor");
		dh = new DoctorProfileHandler(this); //Initialize handler.
	}

	@Override
	protected void onResume() {
		super.onResume();
		
	}
}