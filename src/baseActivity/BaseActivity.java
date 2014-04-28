package baseActivity;

import java.util.LinkedHashMap;
import java.util.Map;
import edu.cmu.medhub.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Base activity - handles tab-related functionality (associating tags with
 * fragments, updating tabs, updating the view displayed when a tab is selected, etc.)
 * Let subclasses/children define what the tab names are without having to worry about setup.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public abstract class BaseActivity extends FragmentActivity implements TabListener {
	private TabsPagerAdapter tabManager;
	private ViewPager viewPager;
	private ActionBar actionBar;
	public Map<String, Fragment> tabMap = new LinkedHashMap<String, Fragment>();
	
	/**
	 * Initialize the map with the list of tab names and corresponding fragments.
	 */
	public abstract void initializeTabMap();
	
	/**
	 * Creates ActionBar, and populates it with tabs. A TabListener (this class)
	 * is attached to each tab.
	 */
	private void initializeActionBar() {
		actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);		
		for (String tab : tabMap.keySet()) {
			actionBar.addTab(actionBar.newTab().setText(tab).setTabListener(this));
		}	
	}
	
	/**
	 * Register each fragment for this Activity with the TabPageAdapter. That way,
	 * when we select a tab, the TabAdapter will fetch the corresponding tab and
	 * pass it onto the ViewPager.
	 */
	private void initializeTabAdapter() {
		tabManager = new TabsPagerAdapter(getSupportFragmentManager());
		for (String tab : tabMap.keySet()) {
			tabManager.addFragment(tabMap.get(tab));
		}	
	}
	
	/**
	 * The ViewPager will display the fragment returned by the TabAdapter and
	 * will update the action bar after a tab selection.
	 */
	private void initializeViewPager() {
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(tabManager);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				//When we change the page, tell action bar to update display
				//(change tab with emphasis emphasized).
				actionBar.setSelectedNavigationItem(pos);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entry_main);
		initializeTabMap(); //Map tab names to corresponding fragments.
		initializeTabAdapter(); //Register fragments with adapter.
		initializeViewPager(); //Master, coordinates interactions between ActionBar and Adapter
		initializeActionBar(); //Displays tabs, tells view pager which tab was selected
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	@Override
	public void onTabSelected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
		//Tab listener conveys index of selected tab to viewPager.
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(android.app.ActionBar.Tab tab,
			android.app.FragmentTransaction ft) {		
	}
}