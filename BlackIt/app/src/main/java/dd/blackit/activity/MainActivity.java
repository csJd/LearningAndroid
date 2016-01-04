package dd.blackit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dd.blackit.R;
import dd.blackit.dao.DbImp;
import dd.blackit.model.BlacklistItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int dbVersion = 1;
    ListView lvBl;
    EditText etTel;
    DbImp dbImp;

    protected void getBlacklist() {
        String tel = etTel.getText().toString();
        List<BlacklistItem> blacklist = dbImp.getBl(tel);

        List adpterList = new ArrayList();
        if (blacklist != null) {
            for (BlacklistItem bi : blacklist) {
                HashMap map = new HashMap();
                map.put("tel", bi.getTel());
                String op = "";
                int t = 0;
                if (bi.isCatCall()) t += 1;
                if (bi.isCatSms()) t += 2;
                if (t == 1) op = "拦截电话";
                if (t == 2) op = "拦截短信";
                if (t == 3) op = "拦截电话和短信";
                map.put("op", op);
                adpterList.add(map);
            }
        }

        String[] from = {"tel", "op"};
        int[] to = {R.id.tv_tel, R.id.tv_op};
        SimpleAdapter adapter = new SimpleAdapter(this, adpterList, R.layout.item_bl, from, to);
        lvBl.setAdapter(adapter);
    }

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbImp = new DbImp(MainActivity.this, dbVersion);
        lvBl = (ListView) findViewById(R.id.lv_bl);
        etTel = (EditText) findViewById(R.id.et_tel);

        etTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getBlacklist();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getBlacklist();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addbl) {
            Intent intent = new Intent(MainActivity.this, AddBlActivity.class);
            intent.getIntExtra("dbVersion", dbVersion);
            startActivity(intent);
        } else if (id == R.id.nav_call) {

        } else if (id == R.id.nav_sms) {

        } else if (id == R.id.nav_setkwd) {
            Intent intent = new Intent(MainActivity.this, SetKwActivity.class);
            intent.getIntExtra("dbVersion", dbVersion);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
