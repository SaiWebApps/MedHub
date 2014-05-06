package businessLogic;

import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import entities.User;
import entities.UserType;
import activities.ProfileActivity;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Business logic for registration.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class RegistrationHandler extends ActivityHandler {
	//EditText
	private EditText regEmailView;
	private EditText regPasswordView;
	private EditText confirmPasswordView;
	private EditText firstNameView;
	private EditText lastNameView;
	private EditText telephoneView;
	private TextView regMessageView;
	private RadioGroup radioGroup;

	//String values of Views
	private String regEmail;
	private String regPassword;
	private String confirmPassword;
	private String firstName;
	private String lastName;
	private String tn;
	private int userType; //0 = doctor, 1 = patient

	//Handle interactions with database.
	private DatabaseManager dbm;

	/**
	 * Initialize form fields.
	 */
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
			this.radioGroup = (RadioGroup) activity.findViewById(R.id.userType);
			this.userType = radioGroup.getCheckedRadioButtonId();
			this.telephoneView = (EditText) activity.findViewById(R.id.telephoneNumber);
			this.tn = telephoneView.getText().toString().trim();
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
		if (tn.isEmpty()) {
			Log.v("Validating Registration Fields", "Invalid telephone number");
			errorMessages.add("Please specify a valid telephone number.");
		}
		return errorMessages.isEmpty();
	}

	/**
	 * Read user inputs into user object, and register this user object.
	 */
	private User readInputs() {
		User u = new User();
		u.setEmail(regEmail);
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setPassword(regPassword);
		switch (userType) {
		case R.id.doctorType:
			u.setType(UserType.DOCTOR.ordinal());
			break;
		case R.id.patientType:
			u.setType(UserType.PATIENT.ordinal());
			break;
		}
		u.setTelephoneNumber(tn);
		return u;
	}

	/**
	 * Pass on the newly registered User and the corresponding Doctor/Patient
	 * to the next Activity.
	 */
	private void setupContext(User u) {
		contextInfo.put("user", u);
		if (u.getType() == UserType.DOCTOR.ordinal()) {
			contextInfo.put("doctor", dbm.getDoctor(u));
		}
		else if (u.getType() == UserType.PATIENT.ordinal()) {
			contextInfo.put("patient", dbm.getPatient(u));
		}
	}

	/**
	 * Clear registration form's fields.
	 */
	public void clearFields() {
		regEmailView.setText("");
		regPasswordView.setText("");
		confirmPasswordView.setText("");
		firstNameView.setText("");
		lastNameView.setText("");
		regMessageView.setText("");
		radioGroup.clearCheck();
		telephoneView.setText("");
	}
	
	/**
	 * Handle user registration.
	 */
	public void register(Context current) {
		if (!validateFields()) {
			Log.v("Logging", "Validation error");
			displayErrorMessages(regMessageView);
			return;
		}

		Log.v("Logging", "Inside register method of Registration Handler");
		dbm.open();
		User u = readInputs();
		if (dbm.registerUser(u) < 0) {
			Log.v("Registration Status", "Unsuccessful - User already exists");
			regMessageView.setText("A user already exists for the given email.");
			dbm.close();
			return;
		}
		Log.v("Registration Status", "User successfully registered");	
		setupContext(u);
		dbm.close();
		clearFields();
		nextActivity(current, ProfileActivity.class);
	}
}