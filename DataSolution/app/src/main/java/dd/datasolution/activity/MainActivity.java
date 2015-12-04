package dd.datasolution.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import dd.datasolution.R;
import dd.datasolution.service.Service;

public class MainActivity extends AppCompatActivity {


    Service service = new Service(this);

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        EditText etInput = (EditText) findViewById(R.id.editText);
        String tmp = null;
        try{
            Log.d("ddbug", "in try");
            tmp = service.load();
        }catch (Exception  e) {
            e.printStackTrace();
        }
        finally {
            if (tmp != null) {
                etInput.setText(tmp);
                etInput.setSelection(tmp.length());
                Toast.makeText(this, "成功恢复上次的输入内容", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EditText etInput = (EditText) findViewById(R.id.editText);
        String inputText = etInput.getText().toString();
        String hint = "save failed";
        if (!(TextUtils.isEmpty(inputText)) && service.save(inputText)) hint = "save success";
        Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
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
