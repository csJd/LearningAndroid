package dd.blackit.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import dd.blackit.model.BlacklistItem;
import dd.blackit.model.Call;
import dd.blackit.model.Sms;

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
        Cursor cursor = db.rawQuery("select * from blacklist where tel like ?"
                , new String[]{"%" + s + "%"});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                BlacklistItem bl = new BlacklistItem();
                bl.setId(cursor.getInt(cursor.getColumnIndex("id")));
                bl.setTel(cursor.getString(cursor.getColumnIndex("tel")));
                bl.setCatCall(cursor.getInt(cursor.getColumnIndex("catcall")) == 1);
                bl.setCatSms(cursor.getInt(cursor.getColumnIndex("catsms")) == 1);
                list.add(bl);
            }
        }
        db.close();
        return list;
    }

    public void  modifyBi(BlacklistItem bi){
        dbHelper = new DbHelper(context, dbVersion);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("update blacklist set tel = ?, catcall = ?, catsms = ? where id = ?",
                new String[]{bi.getTel(), bi.isCatCall()?"1":"0", bi.isCatSms()?"1":"0", String.valueOf(bi.getId())});
        db.close();
    }

    public boolean delBi(int id){
        dbHelper  = new DbHelper(context,  dbVersion);
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        db.execSQL("delete from blacklist where id = ?", new String[]{String.valueOf(id)});
        db.close();
        return true;
    }

    public boolean addKw(String kw) {
        dbHelper = new DbHelper(context, dbVersion);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "insert into keyword values(null, ?)";
        try {
            db.execSQL(sql, new String[]{kw});
        } catch (Exception e) {
            db.close();
            e.printStackTrace();
            return false;
        }
        db.close();
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

    public boolean delKw(String kw){
        dbHelper  = new DbHelper(context,  dbVersion);
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        db.execSQL("delete from keyword where kwd = ?", new String[]{kw});
        db.close();
        return true;
    }

    public  List getCalls(){
        dbHelper = new DbHelper(context, dbVersion);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List list = new ArrayList();
        Cursor cursor = db.rawQuery("select * from calls", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Call call = new Call();
                call.setId(cursor.getInt(cursor.getColumnIndex("id")));
                call.setTel(cursor.getString(cursor.getColumnIndex("tel")));
                call.setTime(cursor.getString(cursor.getColumnIndex("time")));
                list.add(call);
            }
        }
        db.close();
        return list;
    }

    public  boolean addCall(Call call){
        dbHelper  = new DbHelper(context,  dbVersion);
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        db.execSQL("insert into calls values (null, ?, ?)", new String[]{
                call.getTel(), call.getTime()});
        db.close();
        return true;
    }

    public boolean delCall(int id){
        dbHelper  = new DbHelper(context,  dbVersion);
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        db.execSQL("delete from calls where id = ?", new String[]{String.valueOf(id)});
        db.close();
        return true;
    }

    public  List getSmss(){
        dbHelper = new DbHelper(context, dbVersion);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List list = new ArrayList();
        Cursor cursor = db.rawQuery("select * from smss", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Sms sms = new Sms();
                sms.setId(cursor.getInt(cursor.getColumnIndex("id")));
                sms.setTel(cursor.getString(cursor.getColumnIndex("tel")));
                sms.setMsg(cursor.getString(cursor.getColumnIndex("msg")));
                sms.setTime(cursor.getString(cursor.getColumnIndex("time")));
                list.add(sms);
            }
        }
        db.close();
        return list;
    }

    public  boolean addSms(Sms sms){
        dbHelper  = new DbHelper(context,  dbVersion);
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        db.execSQL("insert into smss values (null, ?, ?, ?)", new String[]{
                sms.getTel(), sms.getMsg(), sms.getTime()});
        db.close();
        return true;
    }

    public boolean delSms(int id){
        dbHelper  = new DbHelper(context,  dbVersion);
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        db.execSQL("delete from smss where id = ?", new String[]{String.valueOf(id)});
        db.close();
        return true;
    }
}
