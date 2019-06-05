package cc.zkteam.juediqiusheng.bean;
import java.util.List;
public class HotNewsBean {
    String title;
    List<RecommendedBean> recommendedBeans;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<RecommendedBean> getSortDetailBeans() {
        return recommendedBeans;
    }
    public void setSortDetailBeans(List<RecommendedBean> recommendedBeans) {
        this.recommendedBeans = recommendedBeans;
    }
}
