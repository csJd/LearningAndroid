package dd.contactapro;

import dd.dao.ContactDao;
import dd.dao.FileImp;
import dd.model.Contact;
import dd.service.Service;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

public class AddActivity extends Activity {

	Button btnSave;
	Button btnEsc;

	EditText etNo;
	EditText etName;
	EditText etTel;
	EditText etMail;
	EditText etAddr;
	EditText etMrk;
	RadioGroup rgSex;

	Spinner spRel;
	
	private Contact getContact() {
		RadioButton rbSex = (RadioButton) findViewById(rgSex
				.getCheckedRadioButtonId());
		Contact ret = new Contact();
		ret.setNumber(etNo.getText().toString());
		ret.setName(etName.getText().toString());
		ret.setPhone(etTel.getText().toString());
		ret.setEmail(etMail.getText().toString());
		ret.setAddress(etAddr.getText().toString());
		ret.setGender(rbSex.getText().toString());
		ret.setRemark(etMrk.getText().toString());
		ret.setRelation(spRel.getSelectedItem().toString());
		return ret;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);

		btnSave = (Button) findViewById(R.id.bt_save);
		btnEsc = (Button) findViewById(R.id.bt_esc);

		etNo = (EditText) findViewById(R.id.et_num);
		etName = (EditText) findViewById(R.id.et_name);
		etTel = (EditText) findViewById(R.id.et_tel);
		etMail = (EditText) findViewById(R.id.et_mail);
		etAddr = (EditText) findViewById(R.id.et_addr);
		etMrk = (EditText) findViewById(R.id.et_mrk);
		rgSex = (RadioGroup) findViewById(R.id.radioGroup1);

		spRel = (Spinner) findViewById(R.id.spinner1);

		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			/*public void onClick(View v) {
				// TODO Auto-generated method stub
				etMrk.setText(etNo.getText().toString() + "##"
						+ etName.getText().toString() + "##"
						+ etTel.getText().toString() + "##"
						+ etMail.getText().toString() + "##"
						+ etAddr.getText().toString() + "##");
			}*/
			public void onClick(View v) {
				Service service = new Service(getApplicationContext());
				boolean flag =service.save(getContact());
				if(flag)
					Toast.makeText(AddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
				else Toast.makeText(AddActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
					
			}
		});

		btnEsc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		String[] relationship = { "同学", "亲人", "室友", "朋友" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, relationship);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spRel.setAdapter(adapter);

		rgSex.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				ImageButton imgSex = (ImageButton) findViewById(R.id.ib_call);
				if (checkedId == R.id.radio0)
					imgSex.setImageResource(R.drawable.male);
				else
					imgSex.setImageResource(R.drawable.female);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
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
