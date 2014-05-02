package dbLayout;

import entity.Doctor;
import entity.Patient;
import entity.User;
import android.content.Context;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Manage access to SQLite database and tables.
 * @author Sairam Krishnan (sbkrishn) and Meghna Thakur (mthakur)
 */
public class DatabaseManager {
	private SQLiteDatabase database;
	private Context context;
	private final String DB_NAME = "MedHub";
	private final int VERSION = 1;

	//Table Managers
	private UserTableManager userTable = new UserTableManager("User");
	private PatientTableManager patientTable = new PatientTableManager("Patient");
	private DoctorTableManager doctorTable = new DoctorTableManager("Doctor");
	//private PostTableManager postTable = new PostTableManager("Post");
	
	/**
	 * Initializes this database manager with the given context.
	 * @param context - Interface to global info about an application environment
	 */
	public DatabaseManager(Context context) {
		this.context = context;
	}

	/**
	 * Open connection to database.
	 */
	public void open() {
		DatabaseOpenHelper h = new DatabaseOpenHelper(context, DB_NAME, null, VERSION);
		database = h.getWritableDatabase();
	}

	/**
	 * Close connection to database.
	 */
	public void close() {
		if (database != null) {
			database.close();
		}
	}
	
	/**
	 * Register a new user. Also, register the user as a Patient or a Doctor,
	 * depending on user type selected.
	 * @param u - User to be registered
	 */
	public long registerUser(User u) {
		long id = userTable.create(database, u);
		if (id < 0) {
			return id;
		}
		
		switch(u.getType()) {
		case 0:
			Doctor d = new Doctor();
			d.setUserId(u.getUserId());
			doctorTable.create(database, d);
			break;
		case 1:
			Patient p = new Patient();
			p.setUserId(u.getUserId());
			patientTable.create(database, p);
			break;
		}
		return id;
	}
	
	/**
	 * Used for user login.
	 * @param email - Email address of target User
	 * @param password - Password entered by user in login interface
	 * @return -2 if there is no such user, -1 if the password is incorrect, 0 if correct
	 */
	public int authenticate(String email, String enteredPassword) {
		open();
		User actual = userTable.getUser(database, email);
		close();
		if (actual == null) {
			return AuthError.DOES_NOT_EXIST.getCode();
		}
		if (!actual.getPassword().equals(enteredPassword)) {
			return AuthError.INCORRECT.getCode();
		}
		return 0;
	}

	public void updateUser(User u) {
		open();
		userTable.update(database, u);
		close();
	}
	
	public User getUser(String email) {
		return userTable.getUser(database, email);
	}
	
	public Patient getPatient(User user) {
		return patientTable.getPatient(database, user.getUserId());
	}
	
	public Doctor getDoctor(User user) {
		return doctorTable.getDoctor(database, user.getUserId());
	}
	
	/**
	 * Inner class that creates/upgrades and opens a connection to a database.
	 * @author Sairam Krishnan
	 */
	private class DatabaseOpenHelper extends SQLiteOpenHelper {

		public DatabaseOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			userTable.createTable(db);
			patientTable.createTable(db);
			doctorTable.createTable(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			userTable.deleteTable(db);
			patientTable.deleteTable(db);
			doctorTable.deleteTable(db);
			db.setVersion(newVersion); //Update version
			onCreate(db);
		}
	}
}