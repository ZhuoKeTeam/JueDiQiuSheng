package cc.zkteam.juediqiusheng.retrofit2;

import java.io.IOException;

import cc.zkteam.juediqiusheng.retrofit2.bean.BaseBean;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ZKCall
 * Created by WangQing on 2017/10/28.
 */
public interface ZKCall<K> extends Call<BaseBean<K>> {

    @Override
    Response<BaseBean<K>> execute() throws IOException;

    @Override
    void enqueue(Callback<BaseBean<K>> callback);

    @Override
    boolean isExecuted();

    @Override
    void cancel();

    @Override
    boolean isCanceled();

    @Override
    Call<BaseBean<K>> clone();

    @Override
    Request request();
}
