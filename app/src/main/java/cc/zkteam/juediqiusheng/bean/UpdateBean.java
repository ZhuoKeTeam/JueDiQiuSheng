package cc.zkteam.juediqiusheng.bean;

/**
 * CategoryBean
 * Created by WangQing on 2017/10/27.
 */

public class UpdateBean {


    /**
     * app_version : 10006
     * info : 这次的版本 XXX /n BBB /n CCC
     * check : true
     */

    private int app_version;
    private String info;
    private boolean check;

    public int getApp_version() {
        return app_version;
    }

    public void setApp_version(int app_version) {
        this.app_version = app_version;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
