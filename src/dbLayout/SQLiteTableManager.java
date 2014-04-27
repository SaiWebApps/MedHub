package dbLayout;

import android.database.sqlite.SQLiteDatabase;

public interface SQLiteTableManager<T> {
	public String getTableName();
	public void setTableName(String tableName);
	public void createTable(SQLiteDatabase db);
	public boolean deleteTable(SQLiteDatabase db);
	public boolean create(SQLiteDatabase db, T obj);
	public boolean delete(SQLiteDatabase db, T obj);
	public T get(SQLiteDatabase db, long id);
}