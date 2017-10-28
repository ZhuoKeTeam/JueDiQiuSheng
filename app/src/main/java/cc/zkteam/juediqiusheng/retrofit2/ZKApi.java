package cc.zkteam.juediqiusheng.retrofit2;

import cc.zkteam.juediqiusheng.retrofit2.bean.BaseBean;
import cc.zkteam.juediqiusheng.retrofit2.bean.CategoryBean;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * ZKService retrofit2
 * Created by WangQing on 2017/10/27.
 */

public interface ZKApi {
//    http://www.zkteam.cc/JueDiQiuSheng/categoryJson

    @GET("JueDiQiuSheng/categoryJson")
    Call<BaseBean<CategoryBean>> categoryData();

}
