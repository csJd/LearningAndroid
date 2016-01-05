package dd.blackit.dao;

/**
 * Created by Dd on 1/4 , 0004.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {

    public static final String NAME = "blacklist.db";
    private Context context;

    public DbHelper(Context context, int version) {
        super(context, NAME, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_BL = "create table blacklist ("
                + "id integer primary key autoincrement,"
                + "tel text,"
                + "catcall integer,"
                + "catsms integer)";

        final String CREATE_KW = "create table keyword ("
                + "id integer primary key autoincrement, "
                + "kwd text)";

        final String CREATE_CALLS = "create table calls ("
                + "id integer primary key autoincrement, "
                + "tel text,"
                + "time text)";

        final String CREATE_SMSS = "create table smss ("
                + "id integer primary key autoincrement, "
                + "tel text,"
                + "msg text,"
                + "time text)";

        db.execSQL(CREATE_BL);
        db.execSQL(CREATE_KW);
        db.execSQL(CREATE_CALLS);
        db.execSQL(CREATE_SMSS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists blacklist");
        db.execSQL("drop table if exists keyword");
        db.execSQL("drop table if exists calls");
        db.execSQL("drop table if exists smss");
        onCreate(db);
    }
}
