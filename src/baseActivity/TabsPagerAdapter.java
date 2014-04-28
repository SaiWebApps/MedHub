package baseActivity;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> tabList = new ArrayList<Fragment>();
	
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

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