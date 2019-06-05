package cc.zkteam.juediqiusheng.bean;
public class CategoryBean {
    private int id;
    private String categoryName;
    private String categoryUrl;
    private String categoryPicUrl;
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
    public String getCategoryPicUrl() {
        return categoryPicUrl;
    }
    public void setCategoryPicUrl(String categoryPicUrl) {
        this.categoryPicUrl = categoryPicUrl;
    }
    public int getArtifactCollection() {
        return artifactCollection;
    }
    public void setArtifactCollection(int artifactCollection) {
        this.artifactCollection = artifactCollection;
    }
}
