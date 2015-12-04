package dd.service;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract.Helpers;
import android.text.style.UpdateAppearance;
import dd.dao.ContactDao;
import dd.dao.DBImp;
import dd.dao.FileImp;
import dd.model.Contact;

public class Service{
	private ContactDao dao = null;
	//Contact contact = null;
	public Service(Context context){
		dao = new DBImp(context);
	}
	
	public boolean save(Contact contact){
		return dao.save(contact);
	}
	
	public Contact getById(int id) {
		return dao.getById(id);
	}
	
	public void delete(int id) {
		dao.delete(id);
	}
	
	public void update(Contact contact) {
		dao.update(contact);
	}
	
	public List getByName(String name){
		return dao.getByName(name);
	}
}