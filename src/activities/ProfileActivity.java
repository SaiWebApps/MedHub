package activities;

import ws.local.LocationService;
import ws.local.SensorClient;
import ws.local.SensorService;
import businessLogic.CreatePostHandler;
import businessLogic.EditDoctorProfileHandler;
import businessLogic.EditPatientProfileHandler;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;
import edu.cmu.medhub.R;
import entities.Patient;
import entities.User;
import entities.UserType;
import fragment.AllPostsFragment;
import fragment.CreatePostFragment;
import fragment.DoctorProfileFragment;
import fragment.EditDoctorProfileFragment;
import fragment.EditPatientProfileFragment;
import fragment.PatientProfileFragment;

/**
 * After user logs in, display the user's profile information.
 * Also, keep track of the user's location, the tablet's degree of rotation,
 * and the ambient pressure. All this can be used to track the user's level
 * of physical activity.
 * Additionally, change profile screen's color based on how intensely the
 * tablet is shaken. Red = little activity, blue = medium, green = intense
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class ProfileActivity extends BaseActivity implements SensorClient {
	private PatientProfileFragment ppf;
	private DoctorProfileFragment dpf;
	private SensorService sensorService;
	private LocationService locService;
	private long lastTime;
	private float[] lastPos = new float[3];
	private final int SPEED_THRESHOLD_1 = 400;
	private final int SPEED_THRESHOLD_2 = 1000;
	private final int SPEED_THRESHOLD_3 = 2000;
	private boolean isDoctor;
	private Toast t;
	
	/**
	 * Register location and sensor services.
	 */
	@Override
	public void onResume() {
		super.onResume();
		locService = new LocationService(this);
		locService.requestUpdates();
		sensorService = new SensorService(this);
		sensorService.registerSensors(this);
	}

	public void onDestroy() {
		super.onDestroy();
		t.cancel();
		sensorService.deregister();
		sensorService = null;
		locService.destroy();
		locService = null;
	}
	
	/**
	 * Initialize the tabs seen by doctors.
	 */
	private void initializeDoctorTabMap() {
		dpf = new DoctorProfileFragment();
		tabMap.put("Profile", dpf);
		tabMap.put("Edit Profile", new EditDoctorProfileFragment());
		isDoctor = true;
	}

	/**
	 * Init tabs seen by patients.
	 */
	private void initializePatientTabMap() {
		ppf = new PatientProfileFragment();
		tabMap.put("Profile", ppf);
		tabMap.put("Create Post", new CreatePostFragment());
		tabMap.put("Edit Profile", new EditPatientProfileFragment());
		isDoctor = false;
	}

	/*
	 * There is only 1 difference in the tab setup for doctors and patients -
	 * doctors do not have the ability to create posts; they can only respond
	 * to posts. On the other hand, patients can create and respond to posts.
	 */
	@Override
	public void initializeTabMap() {
		User u = (User) getIntent().getParcelableExtra("user");
		if (u.getType() == UserType.DOCTOR.ordinal()) {
			initializeDoctorTabMap();
		}
		else {
			initializePatientTabMap();
		}
		tabMap.put("Forum", new AllPostsFragment());
	}

	@Override
	public int getLayout() {
		return R.layout.activities_profile;
	}

	@Override
	public int getPagerId() {
		return R.id.profilePager;
	}

	/**
	 * Forward edit-profile responsibilities to appropriate handler
	 * based on whether user is doctor or patient.
	 * @param v - "Edit-Profile" button
	 */
	public void editProfile(View v) {
		if (isDoctor) {
			new EditDoctorProfileHandler(this).editProfile(this);
			dpf.updateProfileInfo();
		}
		else {
			new EditPatientProfileHandler(this).editProfile(this);
			ppf.updateProfileInfo();
		}
	}

	/**
	 * Forward create-post responsibilities to CreatePostHandler.
	 * Update the appropriate user profile based on user type.
	 * @param v - "Create-Post" button
	 */
	public void createPost(View v) {
		new CreatePostHandler(this).createPost();
	}

	/**
	 * Pull up email dialog asking user how he wants to share his medical bio
	 * with his doctor.
	 * @param v - "Share Medical Bio" button on patient profile page
	 */
	public void shareMedicalBio(View v) {
		User u = (User) getIntent().getParcelableExtra("user");
		Patient p = (Patient) getIntent().getParcelableExtra("patient");

		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{u.getEmail()});
		i.putExtra(Intent.EXTRA_SUBJECT, "Share Medical Bio");
		i.putExtra(Intent.EXTRA_TEXT, p.getMedicalBio());
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(this, "There are no email clients installed.", 
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Accelerometer handler - change screen color based on intensity of shaking
	 */
	public void handleAccelerometer(float[] values) {
		long currentTime = System.currentTimeMillis();
		long diffTime = currentTime - lastTime;
		if (diffTime > 100) {
			lastTime = currentTime;
			float x = values[0];
			float y = values[1];
			float z = values[2];
			float speed = Math.abs(x + y + z - lastPos[0] - 
					lastPos[1] - lastPos[2]) / diffTime * 10000;

			View v = findViewById(R.id.pf);
			
			if (v != null && speed < SPEED_THRESHOLD_1) {
				v.setBackgroundColor(Color.WHITE);
			}
			else if (v != null && speed > SPEED_THRESHOLD_3) {
				v.setBackgroundColor(Color.GREEN);
			}
			else if (v != null && speed > SPEED_THRESHOLD_2) {
				v.setBackgroundColor(Color.BLUE);
			}
			else if (v != null && speed > SPEED_THRESHOLD_1) {
				v.setBackgroundColor(Color.RED);
			}

			lastPos[0] = x;
			lastPos[1] = y;
			lastPos[2] = z;
		}
	}

	/**
	 * Track tablet's degree of rotation.
	 */
	public void handleGyroscope(float[] values) {
		if (t != null) {
			t.cancel();
			t = null;
		}
		t = Toast.makeText(this, "Gyroscope: (" + values[0] + "," + values[1] + 
				"," + values[2] + ")", Toast.LENGTH_SHORT);
		t.show();
	}

	/**
	 * Track ambient pressure.
	 */
	public void handlePressure(float[] values) {
		if (t != null) {
			t.cancel();
			t = null;
		}
		t = Toast.makeText(this, "Current Pressure: " + values[0] + " hPa.", 
				Toast.LENGTH_SHORT);
		t.show();
	}
}