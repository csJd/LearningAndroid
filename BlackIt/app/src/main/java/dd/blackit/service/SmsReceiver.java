package dd.blackit.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dd.blackit.dao.DbImp;
import dd.blackit.model.BlacklistItem;
import dd.blackit.model.Sms;

public class SmsReceiver extends BroadcastReceiver {

    private DbImp dbImp;
    private int dbVersion;
    private List<String> kwds;
    private List<BlacklistItem> blackList;

    public SmsReceiver() {}
    public SmsReceiver(int dbVersion) {
        this.dbVersion  =  dbVersion;
    }

    private void addSms(Sms sms){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH:mm");//取得时间
        sms.setTime(dateFormat.format(new Date(System.currentTimeMillis())));
        dbImp.addSms(sms);
    }

    @Override
    public void onReceive(Context context, Intent intent) {  //4.4以下才能拦截
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            dbImp = new DbImp(context, dbVersion);
            blackList = dbImp.getBl("");
            kwds = dbImp.getKw("");

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
                    String tel = message.getOriginatingAddress();
                    Sms sms = new Sms();
                    sms.setMsg(msg);
                    sms.setTel(tel);

                    Log.d("ddtest", "phoneNum = " + tel + " msg = " + msg);
                    boolean flag = false;
                    for (BlacklistItem bli : blackList) {
                        if (tel.contains(bli.getTel())&&bli.isCatSms()) {
                            flag = true;
                            addSms(sms);
                            Toast.makeText(context, "拦截了一条短信", Toast.LENGTH_SHORT).show();
                            abortBroadcast();
                            break;
                        }
                    }

                    if(flag) continue;
                    for (String s : kwds) {
                        if (msg.toLowerCase().contains(s)) {
                            BlacklistItem bli = new BlacklistItem();
                            bli.setTel(tel);
                            bli.setCatSms(true);
                            bli.setCatCall(false);
                            dbImp.addBl(bli);  //添加号码到短信黑名单

                            addSms(sms);
                            Toast.makeText(context, "拦截了一条短信", Toast.LENGTH_SHORT).show();
                            //String out = "Deny you!";    //自动回发一条短信
                            //smsm.sendTextMessage(phoneNum, null, out, null, null);
                            Log.d("ddtest", "send 'Deny you'");
                            abortBroadcast();
                        }
                    }
                }
            }
        }
    }
}
