package entry.handlers;

import java.util.ArrayList;
import java.util.List;
import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import entity.User;
import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationHandler {
	//EditText
	private EditText regEmailView;
	private EditText regPasswordView;
	private EditText confirmPasswordView;
	private EditText firstNameView;
	private EditText lastNameView;
	private TextView regErrorMessageView;
	
	//String values of Views
	private String regEmail;
	private String regPassword;
	private String confirmPassword;
	private String firstName;
	private String lastName;

	//Handle interactions with database.
	private DatabaseManager dbm;
	
	//Validation errors
	private List<String> errorMessages;

	public RegistrationHandler(Activity activity) {
		try {
			this.dbm = new DatabaseManager(activity.getApplicationContext());
			this.errorMessages = new ArrayList<String>();

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
			
			this.regErrorMessageView = (TextView) activity.findViewById(R.id.regErrorMessages);
		} catch (NullPointerException e) {
			Log.e("Reg Init Error", "We are not on the registration page.");
		}
	}

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

	private void clearFields() {
		firstNameView.setText("");
		lastNameView.setText("");
		regEmailView.setText("");
		regPasswordView.setText("");
		confirmPasswordView.setText("");
		regErrorMessageView.setText("");
	}
	
	public void displayErrorMessages() {
		String errorList = "";
		for (String error : errorMessages) {
			errorList += error + "\n";
		}
		regErrorMessageView.setText(errorList.trim());
	}
	
	public void register() {
		if (!validateFields()) {
			Log.v("Logging", "Validation error");
			displayErrorMessages();
			return;
		}
		
		Log.v("Logging", "Inside register method of RH");
		dbm.open();
		User u = new User();
		u.setEmail(regEmail);
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setPassword(regPassword);
		u.setType(0);
		u.setScore(0);
		dbm.registerUser(u);
		dbm.close();
		clearFields();
		Log.v("Registration Status", "Successful");
	}
}