package cc.zkteam.juediqiusheng.module.category;

import java.util.List;

/**
 * Created by renxuelong on 17-10-27.
 * Description:
 */

public class BeanCategory {

    /**
     * http://www.zkteam.cc/JueDiQiuSheng/categoryJson?pageCount=20
     * code : 0
     * message : ok
     * result ---
     */

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
}
