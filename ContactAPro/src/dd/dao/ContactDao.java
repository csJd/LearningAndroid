package dd.dao;

import java.util.List;

import android.support.v4.app.ActionBarDrawerToggle.Delegate;
import dd.model.Contact;

public interface ContactDao {
	 
	public Boolean save(Contact contact);
	public List getByName(String name);
	public Contact getById(int id);
	public void delete(int id);
	public void update(Contact contact);
}
