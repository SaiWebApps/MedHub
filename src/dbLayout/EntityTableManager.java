package dbLayout;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * TableManager object, manages access to a specific table in the database
 * @author Sairam Krishnan
 */
public abstract class EntityTableManager<T> implements SQLiteTableManager<T> {
	private String tableName;
	
	/**
	 * Initialize this TableManager with the specified table name.
	 * @param tableName - Name of this table
	 */
	public EntityTableManager(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the name of the table managed by this TableManager
	 */
	@Override
	public String getTableName() {
		return tableName;
	}

	/**
	 * Set the name of the table managed by this TableManager.
	 * @param tableName - New name for the managed table
	 */
	@Override
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * Delete this table from the database.
	 * @param db - Database containing this table
	 * @return true if successfully deleted, false otherwise
	 */
	@Override
	public boolean deleteTable(SQLiteDatabase db) {
		try {
			db.execSQL("DROP TABLE IF EXISTS " + tableName);
			Log.v("Table Management", "Successfully deleted table.");
			return true;
		} catch (SQLException e) {
			Log.e("Table Management", "Unable to delete table.");
			return false;
		}
	}
}