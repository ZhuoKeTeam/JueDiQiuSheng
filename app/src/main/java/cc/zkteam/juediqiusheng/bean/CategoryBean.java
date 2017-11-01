package cc.zkteam.juediqiusheng.bean;

/**
 * CategoryBean
 * Created by WangQing on 2017/10/27.
 */

public class CategoryBean {

    /**
     * id : 6792
     * categoryName : 武器&装备
     * categoryUrl : http://www.gamersky.com/z/playbattlegrounds/862094_6792/
     * artifactCollection : 1508628957
     */

    private int id;
    private String categoryName;
    private String categoryUrl;
    private int artifactCollection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    public int getArtifactCollection() {
        return artifactCollection;
    }

    public void setArtifactCollection(int artifactCollection) {
        this.artifactCollection = artifactCollection;
    }
}
