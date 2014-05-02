package patientProfile;

import dbLayout.DatabaseManager;
import baseActivity.handler.ActivityHandler;
import edu.cmu.medhub.R;
import entity.User;
import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayPatientProfileHandler extends ActivityHandler {	
	private TextView patientProfileHeading;
	private ListView listView;
	private DatabaseManager dbm;
	
	public DisplayPatientProfileHandler(Activity a) {
		this.dbm = new DatabaseManager(a.getApplicationContext());
		this.patientProfileHeading = (TextView) a.findViewById(R.id.patientProfileHeading);
		this.listView = (ListView) a.findViewById(R.id.postHistory);
		updateProfileInfo(a);
	}
	
	public void updateProfileInfo(Activity a) {
		User u = a.getIntent().getParcelableExtra("user");
		String text = u.getFirstName() + " " + u.getLastName() + "'s Profile\nEmail: " + 
				u.getEmail();
		patientProfileHeading.setText(text);
		PostAdapter adapter = new PostAdapter(a.getApplicationContext(), dbm.getPosts(u));
		listView.setAdapter(adapter);
	}
	
	@Override
	public boolean validateFields() {
		return false;
	}
}