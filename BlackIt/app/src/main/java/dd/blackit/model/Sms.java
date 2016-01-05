package dd.blackit.model;

/**
 * Created by Dd on 1/4 , 0004.
 */
public class Sms {
    int id;
    String tel, msg, time;

    public int getId() {
        return id;
    }

    public String getTel() {
        return tel;
    }

    public String getTime() {
        return time;
    }

    public String getMsg() {
        return msg;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
