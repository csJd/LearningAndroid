package dd.datasolution.dao;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;

/**
 * Created by Dd on 12/4 , 0004.
 * use /data/data/package_name/files/data
 */
public class FileImp implements TestDao {

    Context context = null;

    public FileImp(Context context) {
        this.context = context;
    }

    @Override
    public Boolean save(String data) {
        //String data = "Data to save";
        FileOutputStream out = null;
        BufferedWriter writer = null;

        try {
            out = context.openFileOutput("data.txt", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder ret = new StringBuilder();
        try {

            in = context.openFileInput("data.txt");
            reader = new BufferedReader(new InputStreamReader(in));
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                ret.append(tmp);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return ret.toString();
        }
    }
}
