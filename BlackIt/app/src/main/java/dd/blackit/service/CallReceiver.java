package dd.blackit.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;
import java.util.List;

import dd.blackit.dao.DbImp;
import dd.blackit.model.BlacklistItem;

public class CallReceiver extends BroadcastReceiver {
    private DbImp dbImp;
    private List<BlacklistItem> blackList;

    public CallReceiver() {}
    public CallReceiver(Context context, int dbVersion) {
        dbImp = new DbImp(context, dbVersion);
        blackList = dbImp.getBl("");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent.getAction() == "android.intent.action.PHONE_STATE") {
            Log.d("ddtest", "recieved call");
            TelephonyManager telm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            if (telm.getCallState() == TelephonyManager.CALL_STATE_RINGING) {
                String tel = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                for (BlacklistItem bli : blackList) {
                    if (tel.contains(bli.getTel()) && bli.isCatCall()) { //拦截电话
                        Class<TelephonyManager> c = TelephonyManager.class;
                        Method getITelephonyMethod = null;
                        try {//use aidl to end call
                            getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[]) null);
                            getITelephonyMethod.setAccessible(true);
                            ITelephony iTelephony;
                            iTelephony = (ITelephony) getITelephonyMethod.invoke(telm, (Object[]) null);
                            iTelephony.endCall();
                            Log.d("ddtest", "endcall ing");
                            Toast.makeText(context, "拦截了一个电话 来自" + tel, Toast.LENGTH_SHORT).show();
                            break;
                        } catch (Exception e) {
                            Log.d("ddtest", "end call failed");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
