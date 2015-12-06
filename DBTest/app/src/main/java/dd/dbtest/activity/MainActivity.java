package dd.dbtest.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dd.dbtest.R;
import dd.dbtest.dao.DBHelper;
import dd.dbtest.dao.DBImp;
import dd.dbtest.model.Book;

public class MainActivity extends AppCompatActivity {

    public static final int DBVERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("DBVERSION", DBVERSION);
                startActivity(intent);
            }
        });

        ListView lvBook = (ListView) findViewById(R.id.lv_book);
        DBImp dbImp = new DBImp(this, DBVERSION);
        List<Book> books = dbImp.getAll();

        List adpterList = new ArrayList();

        if (books != null) {
            for (Book book : books) {
                HashMap map = new HashMap();
                map.put("name", book.getName());
                map.put("author", book.getAuthor() + " 著");
                map.put("price", "￥" + book.getPrice());
                map.put("pages", book.getPages() + "页");
                map.put("pic", R.drawable.book);
                adpterList.add(map);
            }
        }

        String[] from = {"name", "author", "price", "pages", "pic"};
        int[] to = {R.id.tv_bookname, R.id.tv_author, R.id.tv_price, R.id.tv_pages, R.id.iv_cover};
        SimpleAdapter adapter = new SimpleAdapter(this, adpterList, R.layout.book_item, from, to);

        lvBook.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
