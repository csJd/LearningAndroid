package dd.contactapro;

import dd.dao.DBHelper;
import dd.model.Contact;
import dd.service.Service;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract.Helpers;
import android.text.style.UpdateAppearance;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class DetailActivity extends Activity {

	EditText etNo;
	EditText etName;
	EditText etTel;
	EditText etMail;
	EditText etAddr;
	EditText etRmk;
	EditText etSex;
	EditText etRls;
	ImageView ivSex;
	Button btDial;
	Button btSms;
	
	Contact contact = null;
	Service service = new Service(this);
	
	public void update(){
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确认修改为编辑后的内容? ");
		builder.setTitle("提示");
		
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				
				if(contact != null){
					contact.setNumber(etNo.getText().toString());
					contact.setName(etName.getText().toString());
					contact.setPhone(etTel.getText().toString());
					contact.setEmail(etMail.getText().toString());
					contact.setAddress(etAddr.getText().toString());
					contact.setGender(etSex.getText().toString());
					contact.setRemark(etRmk.getText().toString());
					contact.setRelation(etRls.getText().toString());
				}

				service.update(contact);
				finish();
			}
		});
		
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	public void delete(){
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确认删除吗? ");
		builder.setTitle("提示");
		
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				service.delete(contact.getId());
				finish();
			}
		});
		
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		etNo = (EditText) findViewById(R.id.dt_no);
		etName = (EditText) findViewById(R.id.dt_name);
		etTel = (EditText) findViewById(R.id.dt_tel);
		etMail = (EditText) findViewById(R.id.dt_mail);
		etAddr = (EditText) findViewById(R.id.dt_addr);
		etRmk = (EditText) findViewById(R.id.dt_rmk);
		etSex = (EditText) findViewById(R.id.dt_sex);
		etRls = (EditText) findViewById(R.id.dt_rls);
		
		btSms = (Button) findViewById(R.id.bt_sms);
		btDial = (Button) findViewById(R.id.bt_dial);
		
		ivSex = (ImageView) findViewById(R.id.imageView1);

		Intent intent = getIntent();
		int id = intent.getIntExtra("id", 0);
		if (id == 0) finish();
			
		else {
			Service service = new Service(this);
			contact = service.getById(id);

			//Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();

			if (contact != null) {
					
				etNo.setText(contact.getNumber());
				etName.setText(contact.getName());
				etTel.setText(contact.getPhone());
				etMail.setText(contact.getEmail());
				etAddr.setText(contact.getAddress());
				etSex.setText(contact.getGender());
				etRls.setText(contact.getRelation());
				etRmk.setText(contact.getRemark());
				
				if(etSex.getText().toString().equals("女"))
					ivSex.setImageResource(R.drawable.female);
				else ivSex.setImageResource(R.drawable.male);
			}
		}
		
		btDial.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + contact.getPhone()));
				//intent.addCategory("android.intent.category.DEFAULT");
				startActivity(intent);
			}
		});
		
		
		btSms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SENDTO);
				intent.setData(Uri.parse("smsto:" + contact.getPhone()));
				//intent.addCategory("android.intent.category.DEFAULT");
				startActivity(intent);
			}
		});
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.mu_del:
			delete();
			break;
		case R.id.mu_update:
			update();
			break;
		default:
			break;
		}
		return true;
	}
}
