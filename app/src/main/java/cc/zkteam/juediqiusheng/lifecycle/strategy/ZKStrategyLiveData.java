package cc.zkteam.juediqiusheng.lifecycle.strategy;

import android.arch.lifecycle.LiveData;

import java.util.List;

import cc.zkteam.juediqiusheng.api.ZKApi;
import cc.zkteam.juediqiusheng.bean.BaseBean;
import cc.zkteam.juediqiusheng.bean.CategoryBean;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ZKSrtategyLiveData
 * Created by WangQing on 2017/11/10.
 */

public class ZKStrategyLiveData extends LiveData<List<CategoryBean>> {

    private Call<BaseBean<List<CategoryBean>>> call;

    public ZKStrategyLiveData() {
        ZKApi zkApi = ZKConnectionManager.getInstance().getZKApi();
        call = zkApi.categoryData(20);
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (call.isExecuted())
            return;

        call.enqueue(new Callback<BaseBean<List<CategoryBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<CategoryBean>>> call, Response<BaseBean<List<CategoryBean>>> response) {
                postValue(response.body().getResult());
            }

            @Override
            public void onFailure(Call<BaseBean<List<CategoryBean>>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (call.isCanceled())
            call.cancel();
    }
}
