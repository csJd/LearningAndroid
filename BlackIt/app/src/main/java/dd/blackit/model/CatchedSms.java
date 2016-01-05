package dd.blackit.model;

/**
 * Created by Dd on 1/4 , 0004.
 */
public class CatchedSms {
    int id;
    String tel, sms, time;

    public int getId() {
        return id;
    }

    public String getTel() {
        return tel;
    }

    public String getTime() {
        return time;
    }

    public String getSms() {
        return sms;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }
}
