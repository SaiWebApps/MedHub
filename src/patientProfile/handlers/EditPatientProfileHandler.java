package patientProfile.handlers;

import baseActivity.handler.ActivityHandler;
import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import entity.User;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

public class EditPatientProfileHandler extends ActivityHandler {	
	//Views
	private EditText newFirstNameView;
	private EditText newLastNameView;
	private EditText newPasswordView;
	private EditText confirmPasswordView;

	//Views' values
	private String newFirstName;
	private String newLastName;
	private String newPassword;
	private String confirmPassword;
	
	private DatabaseManager dbm;
	
	public EditPatientProfileHandler(Activity a) {
		this.dbm = new DatabaseManager(a.getApplicationContext());
		
		//Initialize views.
		this.newFirstNameView = (EditText) a.findViewById(R.id.newFirstName);
		this.newLastNameView = (EditText) a.findViewById(R.id.newLastName);
		this.newPasswordView = (EditText) a.findViewById(R.id.newPassword);
		this.confirmPasswordView = (EditText) a.findViewById(R.id.confirmNewPassword);
		
		//Read values from views.
		this.newFirstName = newFirstNameView.getText().toString().trim();
		this.newLastName = newLastNameView.getText().toString().trim();
		this.newPassword = newPasswordView.getText().toString().trim();
		this.confirmPassword = confirmPasswordView.getText().toString().trim();
		
		Log.v("Edit Profile Status", "Registered Views");
	}
	
	@Override
	public boolean validateFields() {
		if (!newPassword.equals(confirmPassword)) {
			errorMessages.add("Password confirmation does not match new password.");
		}
		return errorMessages.size() == 0;
	}

	public void clearFields() {
		newFirstNameView.setText("");
		newLastNameView.setText("");
		newPasswordView.setText("");
		confirmPasswordView.setText("");
	}
	
	private User updateUser(Activity a) {
		Intent i = a.getIntent();
		User u = i.getParcelableExtra("user");
		if (!newFirstName.isEmpty()) {
			Log.v("Edit Profile Status", "Updating first name to " + newFirstName);
			u.setFirstName(newFirstName);
		}
		if (!newLastName.isEmpty()) {
			Log.v("Edit Profile Status", "Updating last name to " + newLastName );
			u.setLastName(newLastName);
		}
		if (!newPassword.isEmpty()) {
			Log.v("Edit Profile Status", "Updating password to " + newPassword);
			u.setPassword(newPassword);
		}
		dbm.updateUser(u);		
		i.removeExtra("user");
		i.putExtra("user", u);
		return u;
	}
	
	public void editProfile(Activity a) {
		if (!validateFields()) {
			return;
		}
		
		updateUser(a);
		Toast t = Toast.makeText(a.getApplicationContext(), "Profile Updated Successfully", 
				Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP, Gravity.CENTER, Gravity.CENTER);
		t.getView().setBackgroundColor(Color.BLUE);
		t.show();
		clearFields();
	}
}