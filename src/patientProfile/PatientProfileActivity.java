package patientProfile;

import patientProfile.fragments.EditPatientProfileFragment;
import patientProfile.fragments.PatientProfileFragment;
import patientProfile.handlers.EditPatientProfileHandler;
import android.view.View;
import baseActivity.BaseActivity;
import edu.cmu.medhub.R;

public class PatientProfileActivity extends BaseActivity {
	private PatientProfileFragment ppf;
	
	@Override
	public void initializeTabMap() {
		ppf = new PatientProfileFragment();
		tabMap.put("Profile", ppf);
		tabMap.put("Edit Profile", new EditPatientProfileFragment());
	}

	@Override
	public int getLayout() {
		return R.layout.patient_activity;
	}
	
	@Override
	public int getPagerId() {
		return R.id.patientPager;
	}
	
	public void editProfile(View v) {
		new EditPatientProfileHandler(this).editProfile(this);
		ppf.updateProfileInfo();
	}
}