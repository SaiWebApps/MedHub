package businessLogic;

import dbLayout.AuthError;
import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import entities.User;
import entities.UserType;
import exception.MedHubException;
import activities.ProfileActivity;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Business logic for login.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class LoginHandler extends ActivityHandler {
	//Login views
	private EditText loginEmailView;
	private EditText loginPasswordView;
	private TextView loginErrorMessageView;

	//Views' values
	private String loginEmail;
	private String loginPassword;

	//Provides way to access database.
	private DatabaseManager dbm;

	/**
	 * Initialize database and views.
	 */
	public LoginHandler(Activity activity) {
		this.dbm = new DatabaseManager(activity.getApplicationContext());
		this.loginEmailView = (EditText) activity.findViewById(R.id.loginEmail);
		this.loginPasswordView = (EditText) activity.findViewById(R.id.loginPassword);
		this.loginErrorMessageView = (TextView) activity.findViewById(R.id.loginErrors);
		this.loginEmail = loginEmailView.getText().toString().trim();
		this.loginPassword = loginPasswordView.getText().toString().trim();
	}

	@Override
	public boolean validateFields() {
		if (loginEmail.isEmpty()) {
			Log.v("Login validation", "Blank email");
			errorMessages.add("Invalid login email");
		}

		if (loginPassword.isEmpty() || dbm.authenticate(loginEmail, loginPassword) 
				== AuthError.INCORRECT.getCode()) {
			Log.v("Login validation", "Blank password");
			errorMessages.add("Invalid password");
		}
		else if (dbm.authenticate(loginEmail, loginPassword) == AuthError.DOES_NOT_EXIST.getCode()) {
			Log.v("Login validation", "Nonexistent user");
			errorMessages.add("No such user exists.");
		}
		return errorMessages.isEmpty();
	}

	/**
	 * Set up context for next view. Add current user and doctor/patient
	 * to the context so that we can show right info in profile page.
	 */
	private User setupContext() {
		User u = dbm.getUser(loginEmail);
		contextInfo.put("user", u);
		if (u.getType() == UserType.DOCTOR.ordinal()) {
			contextInfo.put("doctor", dbm.getDoctor(u));
		}
		else if (u.getType() == UserType.PATIENT.ordinal()) {
			contextInfo.put("patient", dbm.getPatient(u));
		}
		return u;
	}

	/**
	 * Clear login form's fields.
	 */
	public void clearFields() {
		loginEmailView.setText("");
		loginPasswordView.setText("");
		loginErrorMessageView.setText("");
	}
	
	/**
	 * Handle login.
	 */
	public void login(Context current) {
		if (!validateFields()) {
			try {
				throw new MedHubException(101, "Login Failed");
			} catch (MedHubException e) {
				e.printMyProblem();
			}
			displayErrorMessages(loginErrorMessageView);
			return;
		}
		Log.v("Login Validation", "Login successful");
		dbm.open();
		setupContext();
		dbm.close();
		clearFields();
		nextActivity(current, ProfileActivity.class);
	}
}