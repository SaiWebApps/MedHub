package entry.handlers;

import patientProfile.PatientProfileActivity;
import baseActivity.handler.ActivityHandler;
import dbLayout.DatabaseManager;
import doctorProfile.DoctorProfileActivity;
import edu.cmu.medhub.R;
import entity.User;
import entity.UserType;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

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
	
	public LoginHandler(Activity activity) {
		this.dbm = new DatabaseManager(activity.getApplicationContext());
		this.loginEmailView = (EditText) activity.findViewById(R.id.loginEmail);
		this.loginPasswordView = (EditText) activity.findViewById(R.id.loginPassword);
		this.loginErrorMessageView = (TextView) activity.findViewById(R.id.loginErrors);
		this.loginEmail = loginEmailView.getText().toString().trim();
		this.loginPassword = loginPasswordView.getText().toString().trim();
	}
	
	public boolean validateFields() {
		if (loginEmail.isEmpty()) {
			Log.v("Login validation", "Blank email");
			errorMessages.add("Invalid login email");
		}
		if (loginPassword.isEmpty() || !dbm.authenticate(loginEmail, loginPassword)) {
			Log.v("Login validation", "Blank or incorrect password");
			errorMessages.add("Invalid password");
		}
		return errorMessages.isEmpty();
	}
	
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
	
	public void login(Context current) {
		if (!validateFields()) {
			Log.e("Login Validation", "Login failed");
			displayErrorMessages(loginErrorMessageView);
			return;
		}
		Log.v("Login Validation", "Login successful");
		dbm.open();
		User u = setupContext();
		dbm.close();
		if (u.getType() == UserType.DOCTOR.ordinal()) {
			nextActivity(current, DoctorProfileActivity.class);
		}
		else if (u.getType() == UserType.PATIENT.ordinal()) {
			nextActivity(current, PatientProfileActivity.class);
		}
	}
}