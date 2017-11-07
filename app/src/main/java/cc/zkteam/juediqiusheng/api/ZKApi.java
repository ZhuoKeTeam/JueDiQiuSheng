package cc.zkteam.juediqiusheng.api;

import java.util.List;

import cc.zkteam.juediqiusheng.bean.BaseBean;
import cc.zkteam.juediqiusheng.bean.CategoryBean;
import cc.zkteam.juediqiusheng.bean.PicBean;
import cc.zkteam.juediqiusheng.bean.PicCategoryBean;
import cc.zkteam.juediqiusheng.bean.RecommendedBean;
import cc.zkteam.juediqiusheng.bean.SortDetailBean;
import cc.zkteam.juediqiusheng.module.waterfall.ItemBean;
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
    Call<BaseBean<List<CategoryBean>>> categoryData();

    /**
     * 图片分类
     */
    @GET("JueDiQiuSheng/picCategoryJson")
    Call<BaseBean<List<PicCategoryBean>>> getPicCategory();

    /**
     * 图片分类结果
     * @param id
     * @param count
     * @return
     */
    @GET("JueDiQiuSheng/picUrlJson")
    Call<BaseBean<List<PicBean>>> getCategoryList(@Query("jid")String id,@Query("pageCount") int count);

    /**
     * 精品推荐
     */
    @GET("JueDiQiuSheng/recommendedItemJson")
    Call<BaseBean<List<RecommendedBean>>> getRecommended(@Query("jid") String jId, @Query("pageCount") String pageCount);

    /**
     * 分类详情
     */
    @GET("JueDiQiuSheng/itemJson")
    Call<BaseBean<List<SortDetailBean>>> getSortDetail(@Query("jid") String jId, @Query("pageCount") String pageCount);

    /**
     * 瀑布流
     * @param id
     * @param count
     * @param page
     * @return
     */
    @GET("JueDiQiuSheng/picUrlJson")
    Call<BaseBean<List<ItemBean>>> getCategoryListByJId(@Query("jid")String id, @Query("pageCount") int count, @Query("page") int page);

}
