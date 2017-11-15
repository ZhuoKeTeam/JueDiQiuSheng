package cc.zkteam.juediqiusheng.bean;

/**
 * Created by zhangchuanqiang on 2017/11/14.
 */

public class BannerBean {
    /**
      "id": 28,
     "jid": "967259",
     "tjCategoryId_id": 10003,
     "tjPicUrl": "http://imgs.gamersky.com/pic/2017/20171018_qy_372_2.jpg",
     "tjName": "《绝地求生》全载具原型出处及历史背景科普",
     "tjDate": 1508638537,
     "tjSourceUrl": "http://www.gamersky.com/handbook/201710/967259.shtml",
     "tjUrl": "<p><a href=\"http://www.zkteam.cc/JueDiQiuSheng/detail.html?jid=967259\">http://www.zkteam.cc/JueDiQiuSheng/detail.html?jid=967259</a></p>",
     "tjCollection": 1508638541
     */
    private int id;
    private String jid;
    private int tjCategoryId_id;
    private String tjPicUrl;
    private String tjName;
    private String tjDate;
    private String tjSourceUrl;
    private String tjUrl;
    private long  tjCollection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public int getTjCategoryId_id() {
        return tjCategoryId_id;
    }

    public void setTjCategoryId_id(int tjCategoryId_id) {
        this.tjCategoryId_id = tjCategoryId_id;
    }

    public String getTjPicUrl() {
        return tjPicUrl;
    }

    public void setTjPicUrl(String tjPicUrl) {
        this.tjPicUrl = tjPicUrl;
    }

    public String getTjName() {
        return tjName;
    }

    public void setTjName(String tjName) {
        this.tjName = tjName;
    }

    public String getTjDate() {
        return tjDate;
    }

    public void setTjDate(String tjDate) {
        this.tjDate = tjDate;
    }

    public String getTjSourceUrl() {
        return tjSourceUrl;
    }

    public void setTjSourceUrl(String tjSourceUrl) {
        this.tjSourceUrl = tjSourceUrl;
    }

    public String getTjUrl() {
        return tjUrl;
    }

    public void setTjUrl(String tjUrl) {
        this.tjUrl = tjUrl;
    }

    public long getTjCollection() {
        return tjCollection;
    }

    public void setTjCollection(long tjCollection) {
        this.tjCollection = tjCollection;
    }
}
