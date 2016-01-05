package dd.blackit.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dd.blackit.R;
import dd.blackit.dao.DbImp;
import dd.blackit.model.Call;

public class CallsActivity extends AppCompatActivity {

    private ListView lv_calls;
    private List<Call> calls;
    private DbImp dbImp;

    protected void getCalls() {
        calls = dbImp.getCalls();
        List adpterList = new ArrayList();
        if (calls != null) {
            for (Call call : calls) {
                HashMap map = new HashMap();
                map.put("tel", call.getTel());
                map.put("time", call.getTime());
                adpterList.add(map);
            }
        }

        String[] from = {"tel", "time"};
        int[] to = {R.id.tv_tel, R.id.tv_time};
        SimpleAdapter adapter = new SimpleAdapter(this, adpterList, R.layout.item_call, from, to);
        lv_calls.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTitle("电话拦截记录");

        Intent intent = getIntent();
        lv_calls = (ListView) findViewById(R.id.lv_show);
        dbImp = new DbImp(this, intent.getIntExtra("dbVersion", 1));
        getCalls();

        lv_calls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int cid = calls.get(position).getId();
                AlertDialog alertDialog = new AlertDialog.Builder(CallsActivity.this)
                        .setTitle("提示")
                        .setMessage("确定这条记录吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbImp.delCall(cid);
                                getCalls();
                                Toast.makeText(CallsActivity.this, "删除成功!", Toast.LENGTH_SHORT).show();
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
}
