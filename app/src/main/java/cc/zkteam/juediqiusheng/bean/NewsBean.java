package cc.zkteam.juediqiusheng.bean;

/**
 * Created by Doraemon on 2017/10/29.
 */

public class NewsBean {
//            "id":1,
//            "jid":"968786",
//            "categoryId_id":10000,
//            "picUrl":"",
//            "artifactName":"《绝地求生》主播蛇哥直播中被封号 发文求公道",
//            "artifactDate":1508544000.0,
//            "artifactSourceUrl":"http://www.gamersky.com/news/201710/968786.shtml",
//            "artifactUrl":"<p><a href=\"http://www.zkteam.cc/JueDiQiuSheng/detail.html?jid=968786\">http://www.zkteam.cc/JueDiQiuSheng/detail.html?jid=968786</a></p>",
//            "artifactCollection":1508631771.0

    private int id;
    private String jid;
    private String categoryId_id;
    private String picUrl;
    private String artifactName;
    private long artifactDate;
    private String artifactSourceUrl;
    private String artifactUrl;
    private long artifactCollection;


    public int getId() {
        return id;
    }

    public String getJid() {
        return jid;
    }

    public String getCategoryId_id() {
        return categoryId_id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public long getArtifactDate() {
        return artifactDate;
    }

    public String getArtifactSourceUrl() {
        return artifactSourceUrl;
    }

    public String getArtifactUrl() {
        return artifactUrl;
    }

    public long getArtifactCollection() {
        return artifactCollection;
    }
}
