package dd.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String NAME = "contact.db";
	private static final int VERSION = 1;
	
	public DBHelper(Context context) {
		super(context, NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		String sql = "create table contact (" +
					 "_id integer primary key autoincrement," + 
					 "number text," + 
					 "name text," +
					 "phone text," +
					 "email text," +
					 "address text, " +
					 "gender text, " +
					 "relationship text, " +
					 "remark text)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
