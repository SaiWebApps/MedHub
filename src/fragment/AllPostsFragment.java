package fragment;

import businessLogic.AllPostsHandler;
import edu.cmu.medhub.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Display all posts created thus far in public forum.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class AllPostsFragment extends Fragment {
	
	public AllPostsFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_all_posts, container, false);
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		new AllPostsHandler(getActivity());
	}
}