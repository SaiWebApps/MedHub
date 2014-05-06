package adapters;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Map tabs to fragments. Will receive command from ViewPager stating
 * which tab was selected and will serve the corresponding fragment back
 * to the ViewPager.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> tabList = new ArrayList<Fragment>();
	
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	/**
	 * Add the given fragment to the list of tab fragments.
	 * @return true if added successfully, false otherwise.
	 */
	public boolean addFragment(Fragment fragment) {
		if (fragment == null) {
			return false;
		}
		tabList.add(fragment);
		return true;
	}
	
	/**
	 * @return the fragment corresponding to the selected tab, null
	 * if there is no such tab
	 */
	@Override
	public Fragment getItem(int index) {
		try {
			return tabList.get(index);
		} catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * @return the number of tabs in the current Activity
	 */
	@Override
	public int getCount() {
		return tabList.size();
	}
}