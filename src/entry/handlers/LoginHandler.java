package entry.handlers;

import baseHandler.ActivityHandler;
import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import android.app.Activity;
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
	
	@Override
	public void clearFields() {
		loginEmailView.setText("");
		loginPasswordView.setText("");
		loginErrorMessageView.setText("");
	}
	
	public void login() {
		if (!validateFields()) {
			Log.e("Login Validation", "Login failed");
			displayErrorMessages(loginErrorMessageView);
			return;
		}
		Log.v("Login Validation", "Login successful");
		clearFields();
	}
}