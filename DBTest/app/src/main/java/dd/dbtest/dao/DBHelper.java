package dd.dbtest.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Dd on 12/5 , 0005.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String NAME = "book.db";
    private Context context;

    public DBHelper(Context context, int version) {
        super(context, NAME, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_BOOK = "create table book ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "author text,"
                + "pages integer,"
                + "price real)";

        final String CREATE_CATEGORY = "create table Category ("
                + "id integer primary key autoincrement, "
                + "category_name text, "
                + "category_code integer)";
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Log.d("ddtest", "Create database success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
        Log.d("ddtest", "Upgrade database from " + oldVersion + " to " + newVersion + " success");
    }
}
