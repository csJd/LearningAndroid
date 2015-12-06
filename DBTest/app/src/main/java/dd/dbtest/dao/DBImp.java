package dd.dbtest.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dd.dbtest.model.Book;

/**
 * Created by Dd on 12/5 , 0005.
 */
public class DBImp {
    private  Context context;
    private  int  dbVersion;
    private  DBHelper dbHelper  =  null;
    public DBImp(Context  context,  int dbVersion){
        this.context  = context;
        this.dbVersion = dbVersion;
    }

    public Boolean save(Book book){
        dbHelper = new DBHelper(context, dbVersion);
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        String name = book.getName();
        String author = book.getAuthor();
        String pages = String.valueOf(book.getPages());
        String price = String.valueOf(book.getPrice());
        try
        {
            db.execSQL("insert into book values (null, ?, ?, ?, ?)", new String[]{name,  author, pages, price});
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List getAll(){
        dbHelper = new DBHelper(context, dbVersion);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List list = new ArrayList();
        Cursor cursor = db.rawQuery("select * from book", null);
        if(cursor != null){
            while(cursor.moveToNext()){
                Book book =  new Book();
                book.setName(cursor.getString(cursor.getColumnIndex("name")));
                book.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                book.setPages(cursor.getInt(cursor.getColumnIndex("pages")));
                book.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                list.add(book);
            }
        }
        return list;
    }
}
