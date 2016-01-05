package dd.blackit.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import dd.blackit.dao.DbImp;
import dd.blackit.model.BlacklistItem;

public class SmsReceiver extends BroadcastReceiver {

    private DbImp dbImp;
    private List<String> kwds;
    private List<BlacklistItem> blackList;

    public SmsReceiver() {}

    public SmsReceiver(Context context, int dbVersion) {
        dbImp = new DbImp(context, dbVersion);
        kwds = dbImp.getKw("");
        blackList = dbImp.getBl("");
    }

    @Override
    public void onReceive(Context context, Intent intent) {  //4.4以下才能拦截
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            SmsManager smsm = SmsManager.getDefault();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                for (SmsMessage message : messages) {
                    String msg = message.getMessageBody();
                    String phoneNum = message.getOriginatingAddress();
                    Log.d("ddtest", "phoneNum = " + phoneNum);
                    Log.d("ddtest", "body = " + msg);

                    boolean flag = false;
                    for (BlacklistItem bli : blackList) {
                        if (phoneNum.contains(bli.getTel())&&bli.isCatSms()) {
                            Toast.makeText(context, "拦截了一条短信", Toast.LENGTH_SHORT).show();
                            flag = true;
                            abortBroadcast();
                            break;
                        }
                    }

                    if(flag) continue;
                    for (String s : kwds) {
                        if (msg.toLowerCase().contains(s)) {
                            BlacklistItem bli = new BlacklistItem();
                            bli.setTel(phoneNum);
                            bli.setCatSms(true);
                            bli.setCatCall(false);
                            dbImp.addBl(bli);  //添加号码到黑名单

                            Toast.makeText(context, "拦截了一条短信", Toast.LENGTH_SHORT).show();
                            String out = "Deny you!";    //自动回发一条短信
                            //smsm.sendTextMessage(phoneNum, null, out, null, null);
                            Log.d("ddtest", "send 'Deny you'");
                            abortBroadcast();
                        }
                    }
                }
            }
        }
        // throw new UnsupportedOperationException("Not yet implemented");
    }
}
