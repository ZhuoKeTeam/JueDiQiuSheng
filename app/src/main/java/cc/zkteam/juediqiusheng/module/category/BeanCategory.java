package cc.zkteam.juediqiusheng.module.category;
import java.util.List;
public class BeanCategory {
    private int code;
    private String message;
    private List<ResultBean> result;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<ResultBean> getResult() {
        return result;
    }
    public void setResult(List<ResultBean> result) {
        this.result = result;
    }
    public static class ResultBean {
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
}
