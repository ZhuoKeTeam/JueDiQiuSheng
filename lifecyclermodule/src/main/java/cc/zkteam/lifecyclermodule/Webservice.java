package cc.zkteam.lifecyclermodule;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Webservice
 * Created by WangQing on 2017/11/8.
 */
public interface Webservice {

    @GET("/user/{user}")
    Call<User> getUser(@Path("user") int userId);
}
