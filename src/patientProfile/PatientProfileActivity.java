package patientProfile;

import patientProfile.fragments.CreatePostFragment;
import patientProfile.fragments.EditPatientProfileFragment;
import patientProfile.fragments.PatientProfileFragment;
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
		tabMap.put("Create Post", new CreatePostFragment());
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
	
	public void createPost(View v) {
		new CreatePostHandler(this).createPost();
	}
}