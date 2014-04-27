package dbLayout;

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
	 * Register a new user.
	 * @param u - User to be registered
	 */
	public void registerUser(User u) {
		userTable.create(database, u);
	}
	
	/**
	 * Used for user login.
	 * @param u - User whose credentials are being verified
	 * @return true if the entered password is equal to the actual password
	 */
	public boolean authenticate(User u) {
		User actual = userTable.getUser(database, u.getEmail());
		return actual.getPassword().equals(u.getPassword());
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
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			userTable.deleteTable(db);
			db.setVersion(newVersion); //Update version
			onCreate(db);
		}
	}
}