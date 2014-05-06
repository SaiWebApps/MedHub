package fragment;

import businessLogic.DisplayDoctorProfileHandler;
import edu.cmu.medhub.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Display doctor's profile.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class DoctorProfileFragment extends Fragment {
	private DisplayDoctorProfileHandler handler;
	
	public DoctorProfileFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_doctor_profile, container, false);
		return rootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		handler = new DisplayDoctorProfileHandler(getActivity());
	}
	
	public void updateProfileInfo() {
		handler.updateProfileInfo(getActivity());
	}
}