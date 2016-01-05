package dd.blackit.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
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
    List<String> kwds;

    protected void getKwds() {
        String kwd = etKwd.getText().toString();
        kwds = dbImp.getKw(kwd);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                SetKwActivity.this, android.R.layout.simple_list_item_1, kwds);
        lvKwd.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_kw);

        setTitle("设置拦截关键词");
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
                    getKwds();
                    Toast.makeText(SetKwActivity.this, "成功添加拦截关键词 " + kw + " !", Toast.LENGTH_SHORT).show();
                    etKwd.setText("");
                    getKwds();
                } else Toast.makeText(SetKwActivity.this, "添加失败^_^", Toast.LENGTH_SHORT).show();
            }
        });

        lvKwd.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String item = kwds.get(position);
                AlertDialog alertDialog = new AlertDialog.Builder(SetKwActivity.this)
                        .setTitle("提示")
                        .setMessage("确定要从拦截关键词列表中删除" + item + "吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbImp.delKw(item);
                                getKwds();
                                Toast.makeText(SetKwActivity.this, "删除成功!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                alertDialog.show();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getKwds();
    }
}
