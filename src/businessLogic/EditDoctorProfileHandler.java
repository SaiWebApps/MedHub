package businessLogic;

import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import entities.Doctor;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

public class EditDoctorProfileHandler extends EditProfileHandler {	
	//Views
	private EditText newCredView;
	
	//Views' values
	private String newCredentials;
	
	private DatabaseManager dbm;
	
	public EditDoctorProfileHandler(Activity a) {
		super(a);
		this.dbm = new DatabaseManager(a.getApplicationContext());
		this.newCredView = (EditText) a.findViewById(R.id.newCredentials);
		this.newCredentials = newCredView.getText().toString();
	}

	public void clearFields() {
		super.clearFields();
		newCredView.setText("");
	}
	
	private void updateDoctor(Activity a) {
		Intent i = a.getIntent();
		Doctor d = (Doctor) a.getIntent().getParcelableExtra("doctor");
		
		if (!newCredentials.isEmpty()) {
			d.setCredentials(newCredentials);
			dbm.updateDoctor(d);		
			i.removeExtra("doctor");
			i.putExtra("doctor", d);
		}
	}
	
	@Override
	public boolean allEmpty() {
		return super.allEmpty() && newCredentials.isEmpty();
	}
	
	public void editProfile(Activity a) {
		super.editProfile(a);
		updateDoctor(a);
		
		if (!validateFields()) {
			return;
		}
		
		Toast t = Toast.makeText(a.getApplicationContext(), "Profile Updated Successfully", 
				Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP, Gravity.CENTER, Gravity.CENTER);
		t.getView().setBackgroundColor(Color.BLUE);
		t.show();
		clearFields();
	}
}