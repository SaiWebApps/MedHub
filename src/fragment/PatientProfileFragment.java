package fragment;

import businessLogic.DisplayPatientProfileHandler;
import edu.cmu.medhub.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Display patient profile.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class PatientProfileFragment extends Fragment {
	private DisplayPatientProfileHandler handler;
	
	public PatientProfileFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_patient_profile, container, false);
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		handler = new DisplayPatientProfileHandler(getActivity());
	}
	
	public void updateProfileInfo() {
		handler.updateProfileInfo(getActivity());
	}
}