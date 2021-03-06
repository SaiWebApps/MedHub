package fragment;

import edu.cmu.medhub.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Display fragment to create posts.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class CreatePostFragment extends Fragment {
	
	public CreatePostFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_create_post, container, false);
		return rootView;
	}
}