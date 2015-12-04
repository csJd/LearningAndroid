package dd.datasolution.service;

import android.content.Context;

import dd.datasolution.dao.FileImp;
import dd.datasolution.dao.TestDao;

/**
 * Created by Dd on 12/4 , 0004.
 */
public class Service {
    private TestDao dao = null;

    public Service(Context context){
        dao = new FileImp(context);
    }

    public Boolean save(String data){
        return dao.save(data);
    }
}
