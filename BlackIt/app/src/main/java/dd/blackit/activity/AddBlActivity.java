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

public class AddBlActivity extends AppCompatActivity {

    private int dbVersion;

    EditText etTel;
    Button btOk;
    Button btEsc;
    CheckBox cbCall;
    CheckBox cbSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bl);

        setTitle("添加号码");
        Intent intent = getIntent();
        dbVersion = intent.getIntExtra("dbVersion", 1);

        etTel = (EditText) findViewById(R.id.et_tel);
        cbCall = (CheckBox) findViewById(R.id.cb_call);
        cbSms = (CheckBox) findViewById(R.id.cb_sms);
        btOk = (Button) findViewById(R.id.bt_ok);
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlacklistItem bl = new BlacklistItem();
                bl.setTel(etTel.getText().toString());
                bl.setCatCall(cbCall.isChecked());
                bl.setCatSms(cbSms.isChecked());

                DbImp dbImp = new DbImp(AddBlActivity.this, dbVersion);
                if (bl.isCatSms() || bl.isCatCall()) {
                    if (dbImp.addBl(bl)) {
                        Toast.makeText(AddBlActivity.this, "成功添加到黑名单!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddBlActivity.this, "添加失败^_^", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else
                    Toast.makeText(AddBlActivity.this, "黑名单至少得拦截一项呀!", Toast.LENGTH_SHORT).show();
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
