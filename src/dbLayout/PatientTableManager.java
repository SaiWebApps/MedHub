package dbLayout;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import entity.Patient;

public class PatientTableManager extends EntityTableManager<Patient> {

	public PatientTableManager(String tableName) {
		super(tableName);
	}

	@Override
	public void createTable(SQLiteDatabase db) {
		StringBuffer buf = new StringBuffer("CREATE TABLE " + getTableName() + " (");
		buf.append("patientId INTEGER PRIMARY KEY AUTOINCREMENT,");
		buf.append("userId INTEGER NOT NULL,");
		buf.append("medicalBio TEXT)");

		try {
			db.execSQL(buf.toString());
			Log.v("Table Creation", "Created Patient table successfully.");
		} catch(SQLException e) {
			Log.e("Table Creation", "Unable to create Patient table.");
			Log.e("Table Creation Query", buf.toString());
		}
	}

	public Patient getPatient(SQLiteDatabase db, long userId) {
		Cursor c = null;
		Patient p = null;

		try {
			p = new Patient();
			c = db.rawQuery("SELECT * FROM " + getTableName() + " WHERE userId=" + userId, null);
			c.moveToFirst();
			p.setUserId(userId);
			p.setPatientId(c.getLong(c.getColumnIndexOrThrow("patientId")));
			p.setMedicalBio(c.getString(c.getColumnIndexOrThrow("medicalBio")));
			return p;
		} catch (Exception e) {
			return null;
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}
	
	@Override
	public long create(SQLiteDatabase db, Patient p) {
		//Do not create patient if he/she already exists.
		if (getPatient(db, p.getUserId()) != null) {
			Log.v("Patient Creation", "Patient already exists.");
			return CreationError.ALREADY_EXISTS.getCode();
		}

		//Otherwise, create user.
		ContentValues userProperties = new ContentValues();
		userProperties.put("userId", p.getUserId());
		userProperties.put("medicalBio", p.getMedicalBio());

		long newId = db.insert(getTableName(), null, userProperties);
		if (newId == -1) {
			Log.v("Patient Creation", "Unable to create patient.");
			return CreationError.UNABLE_TO_CREATE.getCode();
		}
		p.setPatientId(newId);
		Log.v("Patient Creation", "Patient has been successfully created.");
		return newId;
	}

	@Override
	public boolean delete(SQLiteDatabase db, Patient p) {
		return (db.delete(getTableName(), "userId=" + p.getUserId(), null) == 1);
	}

	@Override
	public Patient get(SQLiteDatabase db, long patientId) {
		Cursor c = null;
		Patient p = null;

		try {
			p = new Patient();
			c = db.rawQuery("SELECT * FROM " + getTableName() + " WHERE patientId=" + patientId, null);
			c.moveToFirst();
			p.setPatientId(patientId);
			p.setUserId(c.getInt(c.getColumnIndexOrThrow("userId")));
			p.setMedicalBio(c.getString(c.getColumnIndexOrThrow("medicalBio")));
			return p;
		} catch (Exception e) {
			return null;
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}
}