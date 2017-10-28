package cc.zkteam.juediqiusheng.api;

import cc.zkteam.juediqiusheng.bean.BaseBean;
import cc.zkteam.juediqiusheng.bean.CategoryBean;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * ZKService retrofit2
 * Created by WangQing on 2017/10/27.
 */

public interface ZKApi {

    /**
     * 请求分类接口
     * @return 分类的数据
     */
    @GET("JueDiQiuSheng/categoryJson")
    Call<BaseBean<CategoryBean>> categoryData();

}
