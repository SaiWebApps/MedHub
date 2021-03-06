package businessLogic;

import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import entities.User;
import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

public abstract class EditProfileHandler extends ActivityHandler {	
	//Views
	private EditText newFirstNameView;
	private EditText newLastNameView;
	private EditText newPasswordView;
	private EditText confirmPasswordView;
	private TextView errorMessageView;

	//Views' values
	private String newFirstName;
	private String newLastName;
	private String newPassword;
	private String confirmPassword;

	private DatabaseManager dbm;

	public EditProfileHandler(Activity a) {
		this.dbm = new DatabaseManager(a.getApplicationContext());

		//Initialize views.
		this.newFirstNameView = (EditText) a.findViewById(R.id.newFirstName);
		this.newLastNameView = (EditText) a.findViewById(R.id.newLastName);
		this.newPasswordView = (EditText) a.findViewById(R.id.newPassword);
		this.confirmPasswordView = (EditText) a.findViewById(R.id.confirmNewPassword);
		this.errorMessageView = (TextView) a.findViewById(R.id.editProfileErrors);

		//Read values from views.
		this.newFirstName = newFirstNameView.getText().toString().trim();
		this.newLastName = newLastNameView.getText().toString().trim();
		this.newPassword = newPasswordView.getText().toString().trim();
		this.confirmPassword = confirmPasswordView.getText().toString().trim();		
	}

	public boolean allEmpty() {
		return newFirstName.isEmpty() && newLastName.isEmpty() && newPassword.isEmpty()
				&& confirmPassword.isEmpty();
	}
	
	@Override
	public boolean validateFields() {
		if (!newPassword.equals(confirmPassword)) {
			errorMessages.add("Password confirmation does not match new password.");
		}
		if (allEmpty()) {
			errorMessages.add("Please specify at least 1 profile attribute to modify.");
		}
		return errorMessages.size() == 0;
	}

	public void clearFields() {
		newFirstNameView.setText("");
		newLastNameView.setText("");
		newPasswordView.setText("");
		confirmPasswordView.setText("");
		errorMessageView.setText("");
	}

	private void updateUser(Activity a) {
		Intent i = a.getIntent();
		User u = i.getParcelableExtra("user");

		if (!newFirstName.isEmpty()) {
			u.setFirstName(newFirstName);
		}
		if (!newLastName.isEmpty()) {
			u.setLastName(newLastName);
		}
		if (!newPassword.isEmpty()) {
			u.setPassword(newPassword);
		}

		if (!newFirstName.isEmpty() && !newLastName.isEmpty() && !newPassword.isEmpty()) {
			dbm.updateUser(u);		
			i.removeExtra("user");
			i.putExtra("user", u);
		}
	}

	public void editProfile(Activity a) {
		if (!validateFields()) {
			displayErrorMessages(errorMessageView);
			return;
		}
		updateUser(a);
	}
}