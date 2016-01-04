package dd.blackit.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dd.blackit.model.BlacklistItem;

/**
 * Created by Dd on 1/4 , 0004.
 */
public class DbImp {
    private Context context;
    private int dbVersion;
    private DbHelper dbHelper = null;

    public DbImp(Context context, int dbVersion) {
        this.context = context;
        this.dbVersion = dbVersion;
    }

    public boolean addBl(BlacklistItem bl) {
        dbHelper = new DbHelper(context, dbVersion);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "insert into blacklist values(null, ?, ?, ?)";
        try {
            db.execSQL(sql, new String[]{bl.getTel(), bl.isCatCall() ? "1" : "0", bl.isCatSms() ? "1" : "0"});
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List getBl(String s) {
        dbHelper = new DbHelper(context, dbVersion);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List list = new ArrayList();
        Cursor cursor = db.rawQuery("select * from blacklist where tel like ? order by tel"
                , new String[]{"%" + s + "%"});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                BlacklistItem bl = new BlacklistItem();
                bl.setTel(cursor.getString(cursor.getColumnIndex("tel")));
                bl.setCatCall(cursor.getInt(cursor.getColumnIndex("catcall")) == 1);
                bl.setCatSms(cursor.getInt(cursor.getColumnIndex("catsms")) == 1);
                list.add(bl);
            }
        }
        db.close();
        return list;
    }

    public boolean addKw(String kw) {
        dbHelper = new DbHelper(context, dbVersion);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "insert into keyword values(null, ?)";
        try {
            db.execSQL(sql, new String[]{kw});
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List getKw(String s) {
        dbHelper = new DbHelper(context, dbVersion);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List list = new ArrayList();
        Cursor cursor = db.rawQuery("select * from keyword where kwd like ? order by kwd"
                , new String[]{"%" + s + "%"});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String kwd;
                kwd = cursor.getString(cursor.getColumnIndex("kwd"));
                list.add(kwd);
            }
        }
        db.close();
        return list;
    }
}
