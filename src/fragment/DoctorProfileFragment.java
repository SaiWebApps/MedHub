package fragment;

import ws.local.MapService;
import businessLogic.DisplayDoctorProfileHandler;
import edu.cmu.medhub.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Display doctor's profile. Doctor's profile consists of doctor's details and
 * current location (displayed using Google Maps).
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class DoctorProfileFragment extends Fragment {
	private DisplayDoctorProfileHandler handler;
	private MapService mapService;
	private View rootView;

	public DoctorProfileFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView != null) {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
		}

		try {
			rootView = inflater.inflate(R.layout.fragment_doctor_profile, container, false);
		} catch (Exception e) {

		}
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		handler = new DisplayDoctorProfileHandler(getActivity());
	}

	@Override
	public void onResume() {
		super.onResume();
		mapService = new MapService(getActivity());
	}

	@Override
	public void onPause() {
		super.onPause();
		mapService.releaseResources();
		mapService = null;
	}

	public void updateProfileInfo() {
		handler.updateProfileInfo(getActivity());
	}
}