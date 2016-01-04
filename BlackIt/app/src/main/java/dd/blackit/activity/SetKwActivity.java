package dd.blackit.activity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import dd.blackit.R;
import dd.blackit.dao.DbImp;

public class SetKwActivity extends AppCompatActivity {

    private int dbVersion;

    private ListView lvKwd;
    private Button btAddKwd;
    private EditText etKwd;
    private DbImp dbImp;

    protected void getKwds() {
        String kwd = etKwd.getText().toString();
        List list = dbImp.getKw(kwd);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                SetKwActivity.this, android.R.layout.simple_list_item_1, list);
        lvKwd.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_kw);

        Intent intent = getIntent();
        dbVersion = intent.getIntExtra("dbVersion", 1);
        dbImp = new DbImp(SetKwActivity.this, dbVersion);

        etKwd = (EditText) findViewById(R.id.et_kwd);
        etKwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getKwds();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lvKwd = (ListView) findViewById(R.id.lv_kwd);
        btAddKwd = (Button) findViewById(R.id.bt_add_kwd);
        btAddKwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kw = etKwd.getText().toString();
                if (kw == null || kw.equals(""))
                    Toast.makeText(SetKwActivity.this, "拦截词不能为空!", Toast.LENGTH_SHORT).show();
                else if (dbImp.addKw(kw)) {
                    Toast.makeText(SetKwActivity.this, "成功添加拦截关键词 " + kw + " !", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(SetKwActivity.this, "添加失败^_^", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getKwds();
    }
}
