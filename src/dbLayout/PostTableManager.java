package dbLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Post;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PostTableManager extends EntityTableManager<Post> {
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public PostTableManager(String tableName) {
		super(tableName);
	}

	@Override
	public void createTable(SQLiteDatabase db) {
		StringBuffer buf = new StringBuffer("CREATE TABLE " + getTableName() + " (");
		buf.append("postId INTEGER PRIMARY KEY AUTOINCREMENT,");
		buf.append("userId INTEGER NOT NULL,");
		buf.append("timestamp TEXT NOT NULL,");
		buf.append("postTitle TEXT NOT NULL,");
		buf.append("postText TEXT NOT NULL)");

		try {
			db.execSQL(buf.toString());
			Log.v("Table Creation", "Created Post table successfully.");
		} catch(SQLException e) {
			Log.e("Table Creation", "Unable to create Post table.");
			Log.e("Table Creation Query", buf.toString());
		}
	}

	public Post get(SQLiteDatabase db, long postId) {
		Cursor c = null;
		Post p = null;

		try {
			p = new Post();
			c = db.rawQuery("SELECT * FROM " + getTableName() + " WHERE postId=" + postId, null);
			c.moveToFirst();
			p.setPostId(postId);
			p.setUserId(c.getLong(c.getColumnIndexOrThrow("userId")));
			p.setPostText(c.getString(c.getColumnIndexOrThrow("postText")));
			p.setPostTitle(c.getString(c.getColumnIndexOrThrow("postTitle")));
			p.setTimestamp(c.getString(c.getColumnIndexOrThrow("timestamp")));
			return p;
		} catch (Exception e) {
			return null;
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}

	public List<Post> getPosts(SQLiteDatabase db, long userId) {
		Cursor c = null;
		List<Post> postList = null;

		try {
			postList = new ArrayList<Post>();
			c = db.rawQuery("SELECT * FROM " + getTableName() + " WHERE userId=" + userId, null);
			while (c.moveToNext()) {
				Post p = new Post();
				p.setUserId(userId);
				p.setPostId(c.getLong(c.getColumnIndexOrThrow("postId")));
				p.setPostText(c.getString(c.getColumnIndexOrThrow("postText")));
				p.setTimestamp(c.getString(c.getColumnIndexOrThrow("timestamp")));
				p.setPostTitle(c.getString(c.getColumnIndexOrThrow("postTitle")));
				postList.add(p);
			}
			return postList;
		} catch (Exception e) {
			return null;
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}
	
	public List<Post> getAllPosts(SQLiteDatabase db) {
		Cursor c = null;
		List<Post> postList = null;

		try {
			postList = new ArrayList<Post>();
			c = db.rawQuery("SELECT * FROM " + getTableName(), null);
			while (c.moveToNext()) {
				Post p = new Post();
				p.setUserId(c.getLong(c.getColumnIndexOrThrow("userId")));
				p.setPostId(c.getLong(c.getColumnIndexOrThrow("postId")));
				p.setPostText(c.getString(c.getColumnIndexOrThrow("postText")));
				p.setTimestamp(c.getString(c.getColumnIndexOrThrow("timestamp")));
				p.setPostTitle(c.getString(c.getColumnIndexOrThrow("postTitle")));
				postList.add(p);
			}
			return postList;
		} catch (Exception e) {
			return null;
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}

	@Override
	public long create(SQLiteDatabase db, Post p) {
		//Do not create user if he/she already exists.
		if (getPosts(db, p.getUserId()).contains(p)) {
			Log.v("Post Creation", "Post already exists.");
			return CreationError.ALREADY_EXISTS.getCode();
		}

		//Otherwise, create user.
		String timestamp = df.format(new Date());
		ContentValues userProperties = new ContentValues();
		userProperties.put("userId", p.getUserId());
		userProperties.put("postText", p.getPostText());
		userProperties.put("postTitle", p.getPostTitle());
		userProperties.put("timestamp", timestamp);

		long newId = db.insert(getTableName(), null, userProperties);
		if (newId == -1) {
			Log.v("Post Creation", "Unable to create post.");
			return CreationError.UNABLE_TO_CREATE.getCode();
		}
		Log.v("Post Creation", "Post has been successfully created.");
		p.setTimestamp(timestamp);
		p.setPostId(newId);
		return newId;
	}

	@Override
	public boolean delete(SQLiteDatabase db, Post p) {
		return (db.delete(getTableName(), "postId=" + p.getPostId(), null) == 1);
	}
}