package dbLayout;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import entities.Doctor;

public class DoctorTableManager extends EntityTableManager<Doctor> {

	public DoctorTableManager(String tableName) {
		super(tableName);
	}

	@Override
	public void createTable(SQLiteDatabase db) {
		StringBuffer buf = new StringBuffer("CREATE TABLE " + getTableName() + " (");
		buf.append("doctorId INTEGER PRIMARY KEY AUTOINCREMENT,");
		buf.append("userId INTEGER NOT NULL,");
		buf.append("credentials TEXT)");

		try {
			db.execSQL(buf.toString());
			Log.v("Table Creation", "Doctor table has been created successfully.");
		} catch(SQLException e) {
			Log.e("Table Creation", "Unable to create Doctor table.");
			Log.e("Table Creation Query", buf.toString());
		}
	}

	public Doctor getDoctor(SQLiteDatabase db, long userId) {
		Cursor c = null;
		Doctor d = null;

		try {
			d = new Doctor();
			c = db.rawQuery("SELECT * FROM " + getTableName() + " WHERE userId=" + userId, null);
			c.moveToFirst();
			d.setUserId(userId);
			d.setDoctorId(c.getLong(c.getColumnIndexOrThrow("doctorId")));
			d.setCredentials(c.getString(c.getColumnIndexOrThrow("credentials")));
			return d;
		} catch (Exception e) {
			return null;
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}
	
	@Override
	public long create(SQLiteDatabase db, Doctor d) {
		//Do not create patient if he/she already exists.
		if (getDoctor(db, d.getUserId()) != null) {
			Log.v("Doctor Creation", "Doctor already exists.");
			return CreationError.ALREADY_EXISTS.getCode();
		}

		//Otherwise, create user.
		ContentValues userProperties = new ContentValues();
		userProperties.put("userId", d.getUserId());
		userProperties.put("credentials", d.getCredentials());

		long newId = db.insert(getTableName(), null, userProperties);
		if (newId == -1) {
			Log.v("Doctor Creation", "Unable to create doctor.");
			return CreationError.UNABLE_TO_CREATE.getCode();
		}
		d.setDoctorId(newId);
		Log.v("Doctor Creation", "Doctor has been successfully created.");
		return newId;
	}

	@Override
	public boolean delete(SQLiteDatabase db, Doctor d) {
		return (db.delete(getTableName(), "userId=" + d.getUserId(), null) == 1);
	}

	@Override
	public Doctor get(SQLiteDatabase db, long doctorId) {
		Cursor c = null;
		Doctor d = null;

		try {
			d = new Doctor();
			c = db.rawQuery("SELECT * FROM " + getTableName() + " WHERE doctorId=" + doctorId, null);
			c.moveToFirst();
			d.setDoctorId(doctorId);
			d.setUserId(c.getInt(c.getColumnIndexOrThrow("userId")));
			d.setCredentials(c.getString(c.getColumnIndexOrThrow("credentials")));
			return d;
		} catch (Exception e) {
			return null;
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}
	
	public void update(SQLiteDatabase db, Doctor d) {
		String newCredentials = d.getCredentials();
		Doctor orig = get(db, d.getUserId());
		ContentValues vals = new ContentValues();

		if (orig == null) {
			return;
		}
		if (orig.getCredentials()==null || !orig.getCredentials().equals(newCredentials)) {
			vals.put("credentials", newCredentials);
		}

		db.update(getTableName(), vals, "userId=" + d.getUserId(), null);
	}
}