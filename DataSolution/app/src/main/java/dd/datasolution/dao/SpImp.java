package dd.datasolution.dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Dd on 12/4 , 0004.
 */
public class SpImp implements TestDao {

    Context context = null;
    SharedPreferences.Editor editor = null;

    public SpImp(Context context) {
        this.context = context;
    }

    @Override
    public Boolean save(String data) {
        editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putString("saved", data);
        editor.commit();
        return true;
    }

    @Override
    public String load() {
        String string;
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return sp.getString("saved", null);
    }
}
