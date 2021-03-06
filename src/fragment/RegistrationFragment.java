package fragment;

import edu.cmu.medhub.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Display registration page.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class RegistrationFragment extends Fragment {

	public RegistrationFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_register, container, false);
		return rootView;
	}
}