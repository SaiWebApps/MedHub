package businessLogic;

import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import entities.Patient;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

public class EditPatientProfileHandler extends EditProfileHandler {	
	//Views
	private EditText newMedicalBioView;
	
	//Views' values
	private String newMedicalBio;
	
	private DatabaseManager dbm;
	
	public EditPatientProfileHandler(Activity a) {
		super(a);
		this.dbm = new DatabaseManager(a.getApplicationContext());
		this.newMedicalBioView = (EditText) a.findViewById(R.id.newMedicalBio);
		this.newMedicalBio = newMedicalBioView.getText().toString();
	}

	public void clearFields() {
		super.clearFields();
		newMedicalBioView.setText("");
	}
	
	@Override
	public boolean allEmpty() {
		return super.allEmpty() && newMedicalBio.isEmpty();
	}
	
	private void updatePatient(Activity a) {
		Intent i = a.getIntent();
		Patient p = (Patient) a.getIntent().getParcelableExtra("patient");
		
		if (!newMedicalBio.isEmpty()) {
			Log.v("med bio", newMedicalBio);
			p.setMedicalBio(newMedicalBio);
		}
		dbm.updatePatient(p);		
		i.removeExtra("patient");
		i.putExtra("patient", p);
	}
	
	public void editProfile(Activity a) {
		super.editProfile(a);
		updatePatient(a);
		
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