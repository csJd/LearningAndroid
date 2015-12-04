package dd.datasolution.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dd on 12/4 , 0004.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String  NAME  = "test.db";
    private static int VERSION = 1;

    public DBHelper(Context  context) {
        super(context,NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String  sql = "create table data ("+
                      "id integer primary key autoincrement,"+
                      "input  text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
