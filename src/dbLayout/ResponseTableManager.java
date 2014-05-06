package dbLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Response;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ResponseTableManager extends EntityTableManager<Response> {
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ResponseTableManager(String tableName) {
		super(tableName);
	}

	@Override
	public void createTable(SQLiteDatabase db) {
		StringBuffer buf = new StringBuffer("CREATE TABLE " + getTableName() + " (");
		buf.append("responseId INTEGER PRIMARY KEY AUTOINCREMENT,");
		buf.append("postId INTEGER NOT NULL,");
		buf.append("userId INTEGER NOT NULL,");
		buf.append("timestamp TEXT NOT NULL,");
		buf.append("responseText TEXT NOT NULL,");
		buf.append("votes INTEGER NOT NULL)");

		try {
			db.execSQL(buf.toString());
			Log.v("Table Creation", "Created Response table successfully.");
		} catch(SQLException e) {
			Log.e("Table Creation", "Unable to create Response table.");
			Log.e("Table Creation Query", buf.toString());
		}
	}

	public Response get(SQLiteDatabase db, long responseId) {
		Cursor c = null;
		Response r = null;

		try {
			r = new Response();
			c = db.rawQuery("SELECT * FROM " + getTableName() + " WHERE responseId=" + responseId, null);
			c.moveToFirst();
			r.setResponseId(responseId);
			r.setPostId(c.getLong(c.getColumnIndexOrThrow("postId")));
			r.setUserId(c.getLong(c.getColumnIndexOrThrow("userId")));
			r.setTimestamp(c.getString(c.getColumnIndexOrThrow("timestamp")));
			r.setResponseText(c.getString(c.getColumnIndexOrThrow("responseText")));
			r.setVotes(c.getInt(c.getColumnIndexOrThrow("votes")));
			return r;
		} catch (Exception e) {
			return null;
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}

	public List<Response> getResponses(SQLiteDatabase db, long userId, long postId) {
		Cursor c = null;
		List<Response> responseList = null;

		try {
			responseList = new ArrayList<Response>();
			c = db.rawQuery("SELECT * FROM " + getTableName() + " WHERE userId=" 
					+ userId + " AND postId=" + postId, null);
			while (c.moveToNext()) {
				Response r = new Response();
				r.setUserId(userId);
				r.setPostId(postId);
				r.setResponseId(c.getLong(c.getColumnIndexOrThrow("responseId")));
				r.setTimestamp(c.getString(c.getColumnIndexOrThrow("timestamp")));
				r.setResponseText(c.getString(c.getColumnIndexOrThrow("responseText")));
				r.setVotes(c.getInt(c.getColumnIndexOrThrow("votes")));
				responseList.add(r);
			}
			return responseList;
		} catch (Exception e) {
			return null;
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}

	public List<Response> getAllResponses(SQLiteDatabase db, long postId) {
		Cursor c = null;
		List<Response> rList = null;

		try {
			rList = new ArrayList<Response>();
			c = db.rawQuery("SELECT * FROM " + getTableName() + " WHERE postId=" + postId, null);
			while (c.moveToNext()) {
				Response r = new Response();
				r.setPostId(postId);
				r.setUserId(c.getLong(c.getColumnIndexOrThrow("userId")));
				r.setResponseId(c.getLong(c.getColumnIndexOrThrow("responseId")));
				r.setResponseText(c.getString(c.getColumnIndexOrThrow("responseText")));
				r.setTimestamp(c.getString(c.getColumnIndexOrThrow("timestamp")));
				r.setVotes(c.getInt(c.getColumnIndexOrThrow("votes")));
				rList.add(r);
			}
			return rList;
		} catch (Exception e) {
			return null;
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}

	@Override
	public long create(SQLiteDatabase db, Response r) {
		String timestamp = df.format(new Date());
		ContentValues userProperties = new ContentValues();
		userProperties.put("userId", r.getUserId());
		userProperties.put("postId", r.getPostId());
		userProperties.put("responseText", r.getResponseText());
		userProperties.put("timestamp", timestamp);
		userProperties.put("votes", 0);

		long newId = db.insert(getTableName(), null, userProperties);
		if (newId == -1) {
			Log.v("Response Creation", "Unable to create response.");
			return CreationError.UNABLE_TO_CREATE.getCode();
		}
		Log.v("Response Creation", "Response has been successfully created.");
		r.setTimestamp(timestamp);
		r.setResponseId(newId);
		return newId;
	}

	@Override
	public boolean delete(SQLiteDatabase db, Response r) {
		return (db.delete(getTableName(), "responseId=" + r.getResponseId(), null) == 1);
	}
	
	public int incrementNumVotes(SQLiteDatabase db, long responseId) {
		Response r = get(db, responseId);
		ContentValues vals = new ContentValues();
		vals.put("votes", r.getVotes() + 1);
		db.update(getTableName(), vals, " responseId=" + responseId, null);
		return r.getVotes() + 1;
	}
	
	public int decrementNumVotes(SQLiteDatabase db, long responseId) {
		Response r = get(db, responseId);
		ContentValues vals = new ContentValues();
		vals.put("votes", r.getVotes() - 1);
		db.update(getTableName(), vals, " responseId=" + responseId, null);
		return r.getVotes() - 1;
	}
}