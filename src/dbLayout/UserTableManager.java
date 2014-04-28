package dbLayout;

import entity.User;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserTableManager extends EntityTableManager<User> {

	public UserTableManager(String tableName) {
		super(tableName);
	}

	@Override
	public void createTable(SQLiteDatabase db) {
		StringBuffer buf = new StringBuffer("CREATE TABLE " + getTableName() + " (");
		buf.append("userId INTEGER PRIMARY KEY AUTOINCREMENT,");
		buf.append("email TEXT NOT NULL,");
		buf.append("password TEXT NOT NULL,");
		buf.append("firstName TEXT NOT NULL,");
		buf.append("lastName TEXT NOT NULL,");
		buf.append("type INTEGER NOT NULL,");
		buf.append("score INTEGER NOT NULL)");

		try {
			db.execSQL(buf.toString());
		} catch(SQLException e) {
			Log.e("Table Creation", "Unable to create Users table.");
			Log.e("Table Creation Query", buf.toString());
		}
	}

	public User get(SQLiteDatabase db, long userId) {
		Cursor c = null;
		User u = null;

		try {
			u = new User();
			c = db.rawQuery("SELECT * FROM " + getTableName() + " WHERE userId=" + userId, null);
			c.moveToFirst();
			u.setUserId(userId);
			u.setEmail(c.getString(c.getColumnIndexOrThrow("email")));
			u.setPassword(c.getString(c.getColumnIndexOrThrow("password")));
			u.setFirstName(c.getString(c.getColumnIndexOrThrow("firstName")));
			u.setLastName(c.getString(c.getColumnIndexOrThrow("lastName")));
			u.setScore(c.getInt(c.getColumnIndexOrThrow("score")));
			u.setType(c.getInt(c.getColumnIndexOrThrow("type")));
			return u;
		} catch (Exception e) {
			return null;
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}
	
	public User getUser(SQLiteDatabase db, String email) {
		Cursor c = null;
		User u = null;

		try {
			u = new User();
			c = db.rawQuery("SELECT * FROM " + getTableName() + " WHERE email=\"" + email + "\"", null);
			c.moveToFirst();
			u.setUserId(c.getInt(c.getColumnIndexOrThrow("userId")));
			u.setEmail(email);
			u.setPassword(c.getString(c.getColumnIndexOrThrow("password")));
			u.setFirstName(c.getString(c.getColumnIndexOrThrow("firstName")));
			u.setLastName(c.getString(c.getColumnIndexOrThrow("lastName")));
			u.setScore(c.getInt(c.getColumnIndexOrThrow("score")));
			u.setType(c.getInt(c.getColumnIndexOrThrow("type")));
			return u;
		} catch (Exception e) {
			return null;
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}

	@Override
	public boolean create(SQLiteDatabase db, User u) {
		//Do not create user if she already exists.
		if (getUser(db, u.getEmail()) != null) {
			Log.v("User Creation", "User already exists.");
			return false;
		}
		
		//Otherwise, create user.
		ContentValues userProperties = new ContentValues();
		userProperties.put("email", u.getEmail());
		userProperties.put("password", u.getPassword());
		userProperties.put("firstName", u.getFirstName());
		userProperties.put("lastName", u.getLastName());
		userProperties.put("type", u.getType());
		userProperties.put("score", u.getScore());
		
		long newId = db.insert(getTableName(), null, userProperties);
		if (newId == -1) {
			Log.v("User Creation", "Unable to create user.");
			return false;
		}
		u.setUserId(newId);
		Log.v("User Creation", "User has been successfully created.");
		return true;
	}

	@Override
	public boolean delete(SQLiteDatabase db, User u) {
		return false;
	}
}