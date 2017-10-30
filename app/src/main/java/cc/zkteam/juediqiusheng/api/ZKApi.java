package cc.zkteam.juediqiusheng.api;

import cc.zkteam.juediqiusheng.bean.BaseBean;
import cc.zkteam.juediqiusheng.bean.CategoryBean;
import cc.zkteam.juediqiusheng.bean.PicCategoryBean;
import java.util.List;

import cc.zkteam.juediqiusheng.bean.RecommendedBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * ZKService retrofit2
 * Created by WangQing on 2017/10/27.
 */

public interface ZKApi {

  /**
   * 请求分类接口
   *
   * @return 分类的数据
   */
  @GET("JueDiQiuSheng/categoryJson")
  Call<BaseBean<CategoryBean>> categoryData();

  /**
   * 图片分类
   */
  @GET("JueDiQiuSheng/picCategoryJson")
  Call<BaseBean<List<PicCategoryBean>>> getPicCategory();

  /**
   * 精品推荐
   */
  @GET("JueDiQiuSheng/recommendedItemJson")
  Call<BaseBean<List<RecommendedBean>>> getRecommended(@Query("jid") String jId, @Query("pageCount") String pageCount);

}
