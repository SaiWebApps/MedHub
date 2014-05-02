package doctorProfile;

import doctorProfile.fragments.DoctorProfileFragment;
import baseActivity.BaseActivity;
import edu.cmu.medhub.R;

public class DoctorProfileActivity extends BaseActivity {
	private DoctorProfileFragment dpf;

	@Override
	public void initializeTabMap() {
		dpf = new DoctorProfileFragment();
		tabMap.put("Profile", dpf);
	}

	public int getLayout() {
		return R.layout.doctor_activity;
	}
	
	public int getPagerId() {
		return R.id.doctorPager;
	}
}