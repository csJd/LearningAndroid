package dd.dbtest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dd.dbtest.R;
import dd.dbtest.dao.DBImp;
import dd.dbtest.model.Book;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button btSmt = (Button) findViewById(R.id.bt_smt);
        Button btEsc = (Button) findViewById(R.id.bt_esc);

        btEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btSmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName(((EditText) findViewById(R.id.et_bookname)).getText().toString());
                book.setAuthor(((EditText) findViewById(R.id.et_author)).getText().toString());
                book.setPages(Integer.valueOf(((EditText) findViewById(R.id.et_pages)).getText().toString()));
                book.setPrice(Double.valueOf(((EditText) findViewById(R.id.et_price)).getText().toString()));
                DBImp dbImp = new DBImp(AddActivity.this, getIntent().getIntExtra("DBVERSION",1));
                if(dbImp.save(book)){
                    Toast.makeText(AddActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                }
                else  Toast.makeText(AddActivity.this,"add failed",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
