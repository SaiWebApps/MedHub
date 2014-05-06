package fragment;

import businessLogic.ResponseHandler;
import edu.cmu.medhub.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * When the user selects a particular question from the forum, display
 * the question, and all of the responses that it has received.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class ResponseFragment extends Fragment {
	ResponseHandler rh;
	
	public ResponseFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_response, container, false);
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		rh = new ResponseHandler(getActivity());
	}
	
	public void updateResponseList() {
		rh.updateResponseList(getActivity());
	}
}