package entry;

import dbLayout.DatabaseManager;
import baseActivity.BaseActivity;
import edu.cmu.medhub.R;
import edu.cmu.medhub.R.id;
import edu.cmu.medhub.R.layout;
import edu.cmu.medhub.R.menu;
import entity.User;
import entry.fragments.LoginFragment;
import entry.fragments.RegistrationFragment;
import entry.handlers.LoginHandler;
import entry.handlers.RegistrationHandler;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.os.Build;

/**
 * Handle user login and registration.
 * @author Sairam Krishnan (sbkrishn)
 */
public class MainActivity extends BaseActivity {
	private LoginHandler loginHandler;
	private RegistrationHandler regHandler;
	private DatabaseManager dbm;
	
	@Override
	public void initializeTabMap() {
		//This activity will contain only 2 tabs - login and registration.
		tabMap.put("Login", new LoginFragment());
		tabMap.put("Registration", new RegistrationFragment());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//dbm = new DatabaseManager(MainActivity.this);
	}
	
	public void register(View v) {
		new RegistrationHandler(this).register();
	}
	
	public void login(View v) {
		new LoginHandler(this, dbm).login();
	}
}