package entry;

import android.view.View;
import baseActivity.BaseActivity;
import entry.fragments.LoginFragment;
import entry.fragments.RegistrationFragment;
import entry.handlers.LoginHandler;
import entry.handlers.RegistrationHandler;

/**
 * Handle user login and registration.
 * @author Sairam Krishnan (sbkrishn)
 */
public class MainActivity extends BaseActivity {
	
	@Override
	public void initializeTabMap() {
		//This activity will contain only 2 tabs - login and registration.
		tabMap.put("Login", new LoginFragment());
		tabMap.put("Registration", new RegistrationFragment());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	public void register(View v) {
		new RegistrationHandler(this).register();
	}
	
	public void login(View v) {
		new LoginHandler(this).login();
	}
}