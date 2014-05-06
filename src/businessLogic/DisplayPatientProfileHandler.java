package businessLogic;

import dbLayout.DatabaseManager;
import edu.cmu.medhub.R;
import entities.Patient;
import entities.User;
import adapters.PostAdapter;
import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Display the patient's profile page.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class DisplayPatientProfileHandler extends ActivityHandler {	
	private TextView patientProfileHeading;
	private TextView medicalBioView;
	private ListView listView;
	private DatabaseManager dbm;
	
	/**
	 * Initialize views.
	 */
	public DisplayPatientProfileHandler(Activity a) {
		this.dbm = new DatabaseManager(a.getApplicationContext());
		this.patientProfileHeading = (TextView) a.findViewById(R.id.patientProfileHeading);
		this.medicalBioView = (TextView) a.findViewById(R.id.medicalBio);
		this.listView = (ListView) a.findViewById(R.id.postHistory);
		updateProfileInfo(a);
	}
	
	/**
	 * Update the profile info (used after editing profile or creating posts).
	 * Also updates list view, which shows all of the posts created by this user.
	 */
	public void updateProfileInfo(Activity a) {
		User u = a.getIntent().getParcelableExtra("user");
		Patient p = a.getIntent().getParcelableExtra("patient");
		String text = u.getFirstName() + " " + u.getLastName() + "'s Profile\nEmail: " + 
				u.getEmail() + "\nUser Type: Patient";
		patientProfileHeading.setText(text);
		
		if (p.getMedicalBio() != null) {
			medicalBioView.setText(p.getMedicalBio());
		}
		
		PostAdapter adapter = new PostAdapter(a.getApplicationContext(), dbm.getPosts(u));
		listView.setAdapter(adapter);
	}
	
	@Override
	public boolean validateFields() {
		return false;
	}
}