package activities;

import ws.local.SMSService;
import businessLogic.LoginHandler;
import businessLogic.RegistrationHandler;
import dbLayout.DatabaseManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import edu.cmu.medhub.R;
import entities.User;
import fragment.LoginFragment;
import fragment.RegistrationFragment;

/**
 * Handle user login and registration. Play clicking sound on login,
 * successful or otherwise.
 * @author Sairam Krishnan (sbkrishn)
 */
public class MainActivity extends BaseActivity {
	private MediaPlayer mp;
	private final DatabaseManager dbm = new DatabaseManager(MainActivity.this);

	@Override
	public void initializeTabMap() {
		//This activity will contain only 2 tabs - login and registration.
		tabMap.put("Login", new LoginFragment());
		tabMap.put("Registration", new RegistrationFragment());
	}

	@Override
	public int getLayout() {
		return R.layout.activities_main;
	}

	@Override
	public int getPagerId() {
		return R.id.pager;
	}

	@Override
	protected void onResume() {
		super.onResume();
		//Initialize the MediaPlayer.
		mp = MediaPlayer.create(MainActivity.this, R.raw.click_sound);
	}

	public void register(View v) {
		//Delegate actual registration work to the RegistrationHandler.
		new RegistrationHandler(this).register(MainActivity.this);
	}

	/**
	 * Play clicking sound whenever login button is clicked. Then, validate
	 * credentials, and allow user to login if all's clear.
	 * @param v - Login button
	 */
	public void login(View v) {
		mp.start(); //Play clicking sound on login.
		//Delegate actual login work to the LoginHandler.
		new LoginHandler(this).login(MainActivity.this);
	}

	/**
	 * Display an AlertDialog that queries the user for his phone number.
	 * Once the user enters his phone number, reset password to a random
	 * integer and send that integer to the user via SMS.
	 * @param v - "Forgot password" button on main page
	 */
	public void forgotPassword(View v) {
		LayoutInflater inflater = getLayoutInflater();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//inside -> the forgot password template, used to show a text field
		//querying for phone number inside the following AlertDialog
		final View inside = inflater.inflate(R.layout.template_forgot_password, null);

		builder.setView(inside)
		.setPositiveButton("Submit Request", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				//Get user input for phone number.
				EditText pn = (EditText) inside.findViewById(R.id.phoneNo);
				
				if (pn.getText().toString().isEmpty()) {
					Toast.makeText(MainActivity.this, "Phone number cannot be empty.", 
							Toast.LENGTH_SHORT).show();
					return;
				}
				
				//Update user's password to random integer. Then, SMS this integer
				//to the user.
				dbm.open();
				User u = dbm.getUserByPhone(pn.getText().toString().trim());
				u.setPassword("" +  ((int) Math.random() * 1000));
				sendSMS(u);
				dbm.updateUser(u);
				dbm.close();
				
				//Dismiss dialog.
				dialog.dismiss();
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}

	/**
	 * Send SMS to the specified User.
	 * @param u - User who is trying to recover his password
	 */
	public void sendSMS(User u) {
		SMSService sms = new SMSService();
		sms.sendSMS(this, u.getTelephoneNumber(), u.getPassword());
	}
}