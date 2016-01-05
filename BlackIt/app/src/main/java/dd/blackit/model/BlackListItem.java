package dd.blackit.model;

/**
 * Created by Dd on 1/4 , 0004.
 */
public class BlacklistItem {
    private int id;
    private String tel;
    private boolean catCall;
    private boolean catSms;

    public void setId(int id) {
        this.id = id;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setCatCall(boolean catcall) {
        this.catCall = catcall;
    }

    public void setCatSms(boolean catsms) {
        this.catSms = catsms;
    }

    public int getId() {
        return id;
    }

    public String getTel() {
        return tel;
    }

    public boolean isCatCall() {
        return catCall;
    }

    public boolean isCatSms() {
        return catSms;
    }
}
