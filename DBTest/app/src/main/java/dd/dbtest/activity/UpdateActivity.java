package dd.dbtest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dd.dbtest.R;
import dd.dbtest.dao.DBImp;
import dd.dbtest.model.Book;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Button btUpt = (Button) findViewById(R.id.bt_smt);
        btUpt.setText("更新");

        Intent intent = getIntent();
        final int DBVERSION = intent.getIntExtra("DBVERSION", 1);
        final int id = intent.getIntExtra("id", 0);

        DBImp dbImp = new DBImp(this, DBVERSION);
        Book book = dbImp.getById(id);
        EditText etName =  (EditText) findViewById(R.id.et_bookname);
        EditText etAuthor = (EditText) findViewById(R.id.et_author);
        EditText etPages  = (EditText) findViewById(R.id.et_pages);
        EditText etPrice = (EditText) findViewById(R.id.et_price);

        etName.setText(book.getName());
        etAuthor.setText(book.getAuthor());
        etPages.setText(String.valueOf(book.getPages()));
        etPrice.setText(String.valueOf(book.getPrice()));

        btUpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName(((EditText) findViewById(R.id.et_bookname)).getText().toString());
                book.setAuthor(((EditText) findViewById(R.id.et_author)).getText().toString());
                book.setPages(Integer.valueOf(((EditText) findViewById(R.id.et_pages)).getText().toString()));
                book.setPrice(Double.valueOf(((EditText) findViewById(R.id.et_price)).getText().toString()));
                DBImp dbImp = new DBImp(UpdateActivity.this, DBVERSION);
                dbImp.update(id, book);
                Toast.makeText(UpdateActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button btEsc =  (Button) findViewById(R.id.bt_esc);
        btEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
