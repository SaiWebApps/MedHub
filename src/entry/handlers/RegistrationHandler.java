package entry.handlers;

import baseHandler.ActivityHandler;
import dbLayout.DatabaseManager;
import dbLayout.UserTableManager.UserCreationError;
import edu.cmu.medhub.R;
import entity.User;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationHandler extends ActivityHandler {
	//EditText
	private EditText regEmailView;
	private EditText regPasswordView;
	private EditText confirmPasswordView;
	private EditText firstNameView;
	private EditText lastNameView;
	private TextView regMessageView;
	
	//String values of Views
	private String regEmail;
	private String regPassword;
	private String confirmPassword;
	private String firstName;
	private String lastName;

	//Handle interactions with database.
	private DatabaseManager dbm;

	public RegistrationHandler(Activity activity) {
		try {
			this.dbm = new DatabaseManager(activity.getApplicationContext());

			this.regEmailView = (EditText) activity.findViewById(R.id.regEmail);
			this.regEmail = regEmailView.getText().toString().trim();

			this.regPasswordView = (EditText) activity.findViewById(R.id.regPassword);
			this.regPassword = regPasswordView.getText().toString().trim();

			this.confirmPasswordView = (EditText) activity.findViewById(R.id.confirmPassword);
			this.confirmPassword = confirmPasswordView.getText().toString().trim();
			
			this.firstNameView = (EditText) activity.findViewById(R.id.firstName);
			this.firstName = firstNameView.getText().toString().trim();
					
			this.lastNameView = (EditText) activity.findViewById(R.id.lastName);
			this.lastName = lastNameView.getText().toString().trim();
			
			this.regMessageView = (TextView) activity.findViewById(R.id.regErrorMessages);
		} catch (NullPointerException e) {
			Log.e("Reg Init Error", "We are not on the registration page.");
		}
	}

	@Override
	public boolean validateFields() {
		if (regEmail.isEmpty()) {
			Log.v("Validating Registration Fields", "Invalid email");
			errorMessages.add("Please specify a valid email.");
		}
		if (regPassword.isEmpty()) {
			Log.v("Validating Registration Fields", "Invalid password");
			errorMessages.add("Please specify a valid password.");
		}
		if (confirmPassword.isEmpty() || !confirmPassword.equals(regPassword)) {
			Log.v("Validating Registration Fields", "Invalid password confirmation");
			errorMessages.add("Password confirmation must match entered password.");
		}
		if (firstName.isEmpty()) {
			Log.v("Validating Registration Fields", "Invalid first name");
			errorMessages.add("Please specify a valid first name.");
		}
		if (lastName.isEmpty()) {
			Log.v("Validating Registration Fields", "Invalid last name");
			errorMessages.add("Please specify a valid last name.");
		}
		return errorMessages.isEmpty();
	}

	@Override
	public void clearFields() {
		firstNameView.setText("");
		lastNameView.setText("");
		regEmailView.setText("");
		regPasswordView.setText("");
		confirmPasswordView.setText("");
		regMessageView.setText("");
	}
	
	public void register() {
		if (!validateFields()) {
			Log.v("Logging", "Validation error");
			displayErrorMessages(regMessageView);
			return;
		}
		
		Log.v("Logging", "Inside register method of Registration Handler");
		dbm.open();
		User u = new User();
		u.setEmail(regEmail);
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setPassword(regPassword);
		u.setType(0);
		u.setScore(0);
		if (dbm.registerUser(u) == UserCreationError.ALREADY_EXISTS.getCode()) {
			Log.v("Registration Status", "Unsuccessful - User already exists");
			regMessageView.setText("A user already exists for the given email.");
		}
		Log.v("Registration Status", "User successfully registered");
		dbm.close();
		clearFields();		
	}
}