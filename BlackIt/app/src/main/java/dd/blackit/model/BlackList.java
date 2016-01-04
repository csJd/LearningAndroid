package dd.blackit.model;

/**
 * Created by Dd on 1/4 , 0004.
 */
public class BlackList {
    private int id;
    private String tel;
    private boolean catcall;
    private boolean catsms;

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setCatcall(boolean catcall) {
        this.catcall = catcall;
    }

    public void setCatsms(boolean catsms) {
        this.catsms = catsms;
    }

    public int getId() {
        return id;
    }

    public String getTel() {
        return tel;
    }

    public boolean isCatcall() {
        return catcall;
    }

    public boolean isCatsms() {
        return catsms;
    }
}
