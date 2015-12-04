package dd.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.provider.Contacts;
import dd.model.Contact;

public class FileImp implements ContactDao {

	// 声明上下文对象
	private Context context = null;
	String FILEPATH = "contact.txt";

	public FileImp(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public Boolean save(Contact contact) {
		// TODO Auto-generated method stub
		BufferedWriter bw = null; // 带缓冲的字符流
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					context.openFileOutput(FILEPATH, Context.MODE_APPEND)));
			bw.write(contact.getNumber() + "##" + contact.getName() + "##"
					+ contact.getPhone() + "##" + contact.getEmail() + "##"
					+ contact.getAddress() + "##" + contact.getGender() + "##"
					+ contact.getRelation() + "##" + contact.getRemark() + "\n");
			//bw.newLine();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				bw.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}
		}
	}

	@Override
	public List getByName(String name) {
		// TODO Auto-generated method stub
		
		List contacts = null;
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader
					(context.openFileInput(FILEPATH)));
			String data = null;
			contacts = new ArrayList();
			while((data = br.readLine()) != null){
				String[] info = data.split("##"); //以##分开
				if(name == null || name.equals("") || info[1].contains(name)){
					Contact ret = new Contact();
					ret.setNumber(info[0]);
					ret.setName(info[1]);
					ret.setPhone(info[2]);
					ret.setEmail(info[3]);
					ret.setAddress(info[4]);
					ret.setGender(info[5]);
					ret.setRemark(info[6]);
					//ret.setRelation(info[7]);
					contacts.add(ret);
				}
			}
		} catch (IOException e){
			e.printStackTrace();
			return contacts;
		} finally{
			try {
				br.close();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
				return contacts;
			}
		}
		return contacts;
	}

	@Override
	public Contact getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void update(Contact contact) {
		// TODO Auto-generated method stub
		
	}
}
