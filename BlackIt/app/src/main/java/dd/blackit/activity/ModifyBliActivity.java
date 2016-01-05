package dd.blackit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import dd.blackit.R;
import dd.blackit.dao.DbImp;
import dd.blackit.model.BlacklistItem;

public class ModifyBliActivity extends AppCompatActivity {

    int dbVersion, id;
    String tel;
    EditText etTel;
    Button btOk;
    Button btEsc;
    CheckBox cbCall;
    CheckBox cbSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bl);
        setTitle("修改");
        Intent intent = getIntent();
        dbVersion = intent.getIntExtra("dbVersion", 1);
        id = intent.getIntExtra("id", 0);
        tel = intent.getStringExtra("tel");



        etTel = (EditText) findViewById(R.id.et_tel);
        etTel.setText(tel);
        cbCall = (CheckBox) findViewById(R.id.cb_call);
        cbSms = (CheckBox) findViewById(R.id.cb_sms);
        btOk = (Button) findViewById(R.id.bt_ok);
        btOk.setText("更新");
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlacklistItem bl = new BlacklistItem();
                bl.setId(id);
                bl.setTel(etTel.getText().toString());
                bl.setCatCall(cbCall.isChecked());
                bl.setCatSms(cbSms.isChecked());

                DbImp dbImp = new DbImp(ModifyBliActivity.this, dbVersion);
                if (bl.isCatSms() || bl.isCatCall()) {
                    dbImp.modifyBi(bl);
                    Toast.makeText(ModifyBliActivity.this, "修改成功!", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(ModifyBliActivity.this, "黑名单至少得拦截一项呀!", Toast.LENGTH_SHORT).show();
            }
        });

        btEsc = (Button) findViewById(R.id.bt_esc);
        btEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
