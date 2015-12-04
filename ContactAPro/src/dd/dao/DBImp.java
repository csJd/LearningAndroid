package dd.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import dd.model.Contact;

public class DBImp implements ContactDao {

	private DBHelper helper = null;
	
	public DBImp(Context context) {
		// TODO Auto-generated constructor stub
		helper = new DBHelper(context);
	}
	
	@Override
	public Boolean save(Contact contact) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = helper.getWritableDatabase();
		if(contact != null){
			String sql = "insert into contact values (null,?,?,?,?,?,?,?,?)";
			Object[] params = new Object[]{
					contact.getNumber(),
					contact.getName(),
					contact.getPhone(),
					contact.getEmail(), 
					contact.getAddress(),
					contact.getGender(),
					contact.getRelation(), 
					contact.getRemark()};
			db.execSQL(sql,params);
			return true;
		}
		return false;
	}

	@Override
	public List getByName(String name) {
		// TODO Auto-generated method stub
		
		List list = null;
		String sql;
		String[] params = null;
		SQLiteDatabase db = helper.getReadableDatabase();
		if(name == null){
			sql = "select * from contact";
		}
		else {
			sql = "select * from contact where name like ? ";
			params = new String[]{"%" + name + "%" };
		}
		Cursor cursor = db.rawQuery(sql, params);
		list = new ArrayList();
		
		while(cursor.moveToNext()){
			Contact ret = new Contact();
			ret.setId(cursor.getInt(0));
			ret.setNumber(cursor.getString(1));
			ret.setName(cursor.getString(2));
			ret.setPhone(cursor.getString(3));
			ret.setEmail(cursor.getString(4));
			ret.setAddress(cursor.getString(5));
			ret.setGender(cursor.getString(6));
			ret.setRelation(cursor.getString(7));
			ret.setRemark(cursor.getString(8));
			list.add(ret);
		}
		
		cursor.close();
		db.close();
		return list;
	}
	
	public Contact getById(int id){
		Contact ret = null;
		
		Log.d("TEST", String.valueOf(id));
		
		if(id > 0){
			
			SQLiteDatabase db = helper.getReadableDatabase();
			String sql = "select * from contact where _id = ?";
			String[] params = new String[] {String.valueOf(id)};
			Cursor cursor = db.rawQuery(sql, params);
			
			if(cursor.moveToNext()){
				ret = new Contact();
				ret.setId(cursor.getInt(0));
				ret.setNumber(cursor.getString(1));
				ret.setName(cursor.getString(2));
				ret.setPhone(cursor.getString(3));
				ret.setEmail(cursor.getString(4));
				ret.setAddress(cursor.getString(5));
				ret.setGender(cursor.getString(6));
				ret.setRelation(cursor.getString(7));
				ret.setRemark(cursor.getString(8));
			}
			cursor.close();
			db.close();
		}
		return ret;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		if(id > 0){
			SQLiteDatabase db = helper.getReadableDatabase();
			String sql = "delete from contact where _id = ?";
			Object[] params = new Object[]{String.valueOf(id)};
			db.execSQL(sql,params);
			db.close();
		}
		
	}

	@Override
	public void update(Contact contact) {
		// TODO Auto-generated method stub
		
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "update contact set "
				+ "number = ?,"
				+ "name = ?,"
				+ "phone = ?,"
				+ "email = ?,"
				+ "address = ?,"
				+ "gender = ?,"
				+ "relationship = ?,"
				+ "remark = ? "
				+ "where _id = ?";
	 Object[] params = new Object[]{
			 contact.getNumber(),
			 contact.getName(),
			 contact.getPhone(),
			 contact.getEmail(),
			 contact.getAddress(),
			 contact.getGender(),
			 contact.getRelation(),
			 contact.getRemark(),
			 contact.getId()};
	 
	 db.execSQL(sql,params);
	 db.close();
		
	}
}
