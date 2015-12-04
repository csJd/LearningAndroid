package dd.datasolution.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Objects;

/**
 * Created by Dd on 12/4 , 0004.
 */
public class DBImp implements TestDao {

    private DBHelper dbHelper = null;

    public DBImp(Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public Boolean save(String data) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "insert into data (input) values (?)";
        db.execSQL(sql, new String[]{data});
        db.close();
        return true;
    }

    @Override
    public String load() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String ret = null;
        Log.d("ddbug", "in load");
        Cursor cursor = db.rawQuery("select input from data", null);
        Log.d("ddbug", "query success");
        cursor.moveToLast();
        ret  =  cursor.getString(cursor.getColumnIndex("input"));
/*        if(cursor.moveToFirst())
        {
            do{
                Log.d("ddbug", ret  =  cursor.getString(cursor.getColumnIndex("input")));
            }while(cursor.moveToNext());
        }*/
/*        cursor.moveToLast();
        ret = cursor.getString(cursor.getColumnIndex("input"));*/
        Log.d("ddbug", String.valueOf(cursor.getCount()));
        cursor.close();
        db.close();
        return ret;
    }
}
