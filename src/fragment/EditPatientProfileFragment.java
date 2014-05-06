package fragment;

import edu.cmu.medhub.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Display edit profile page for patients.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class EditPatientProfileFragment extends Fragment {
	
	public EditPatientProfileFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_edit_patient_profile, container, false);
		return rootView;
	}
}