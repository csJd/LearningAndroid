package dd.contactapro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dd.dao.ContactDao;
import dd.model.Contact;
import dd.service.Service;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button btAdd;
	ListView lvContact;
	EditText etSrh;
	List contacts;
	int option_dialog = 1;
	Contact contact = null;

	private void getRes() {
		List myList = new ArrayList();
		String queryName = etSrh.getText().toString();
		Service service = new Service(this);

		contacts = service.getByName(queryName);

		if (contacts != null) {
			for (int i = 0; i < contacts.size(); ++i) {
				Contact contact = (Contact) contacts.get(i);
				HashMap map = new HashMap();
				map.put("tv_name", contact.getName());
				map.put("tv_tel", contact.getPhone());
				map.put("ib_sex",
						contact.getGender().equals("ÄÐ") ? R.drawable.male
								: R.drawable.female);
				myList.add(map);
			}
		}

		/*
		 * for (int i = 0; i < 9; ++i) { HashMap map = new HashMap();
		 * map.put("tv_name", "ÒÆ¶¯"); map.put("tv_tel", "10086");
		 * myList.add(map); }
		 */

		String[] from = { "tv_name", "tv_tel", "ib_sex" };
		int[] to = { R.id.tv_showname, R.id.tv_showtel, R.id.imageView1 };

		SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, myList,
				R.layout.item_contact, from, to);
		lvContact.setAdapter(adapter);
	}

	class IBListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ib_call:
				if (contact.getPhone().equals("")) {
					Toast.makeText(getApplicationContext(), "ÎÞºÅÂë",
							Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + contact.getPhone()));
					// intent.addCategory("android.intent.category.DEFAULT");
					startActivity(intent);
				}
				dismissDialog(option_dialog);
				break;

			case R.id.ib_det:
				Intent intent = new Intent(MainActivity.this,
						DetailActivity.class);
				intent.putExtra("id", contact.getId());
				startActivity(intent);
				dismissDialog(option_dialog);
				break;

			case R.id.ib_sms:
				if (contact.getPhone().equals("")) {
					Toast.makeText(getApplicationContext(), "ÎÞºÅÂë",
							Toast.LENGTH_SHORT).show();
				} else {
					Intent intent1 = new Intent();
					intent1.setAction(Intent.ACTION_SENDTO);
					intent1.setData(Uri.parse("smsto:" + contact.getPhone()));
					// intent.addCategory("android.intent.category.DEFAULT");
					startActivity(intent1);
				}
				dismissDialog(option_dialog);
				break;

			default:
				break;
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub

		if (id == option_dialog) {
			Dialog optionDialog;
			View optinDialogView = null;
			LayoutInflater li = LayoutInflater.from(this);
			optinDialogView = li.inflate(R.layout.option_dialog, null);

			ImageButton ibCall = (ImageButton) optinDialogView.findViewById(R.id.ib_call);
			ImageButton ibShow = (ImageButton) optinDialogView.findViewById(R.id.ib_det);
			ImageButton ibSms = (ImageButton) optinDialogView.findViewById(R.id.ib_sms);

			ibCall.setOnClickListener(new IBListener());
			ibShow.setOnClickListener(new IBListener());
			ibSms.setOnClickListener(new IBListener());

			optionDialog = new AlertDialog.Builder(this).setView(
					optinDialogView).create();
			return optionDialog;
		}
		return null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btAdd = (Button) findViewById(R.id.bt_add);
		lvContact = (ListView) findViewById(R.id.lv_contact);
		etSrh = (EditText) findViewById(R.id.et_srh);
		getRes();

		btAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, AddActivity.class);
				startActivity(intent);
			}
		});

		etSrh.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				// Toast.makeText(getApplicationContext(),
				// etSrh.getText().toString(), Toast.LENGTH_SHORT).show();
				getRes();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		lvContact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				lvContact.setCacheColorHint(Color.TRANSPARENT);
				contact = (Contact) contacts.get(position);
				showDialog(option_dialog);
				/*
				 * Intent intent = new Intent
				 * (MainActivity.this,DetailActivity.class);
				 * intent.putExtra("id", contact.getId());
				 * startActivity(intent);
				 */
			}
		});

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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
