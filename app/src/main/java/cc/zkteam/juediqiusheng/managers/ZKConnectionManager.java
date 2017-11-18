package cc.zkteam.juediqiusheng.managers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import cc.zkteam.juediqiusheng.Constant;
import cc.zkteam.juediqiusheng.api.ZKApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ZKConnectionManager ZKteam 的网络管理类
 * 单例使用教程: http://blog.csdn.net/lmj121212/article/details/68922401
 * Created by WangQing on 2017/10/28.
 */
public class ZKConnectionManager {

    private static final String TAG = "ZKConnectionManager";

    private static ZKConnectionManager instance = null;

    @Inject
    public ZKConnectionManager() {
    }

//    public ZKConnectionManager provideZKConnectionManager() {
//        return new ZKConnectionManager(); ////每次注入都是new的，单例的话只会调用一次这里，下次回去缓存查找
//    }

    public static ZKConnectionManager getInstance() {
        if (instance == null) {
            synchronized (ZKConnectionManager.class) {
                ZKConnectionManager temp = instance;
                if (temp == null) {
                    temp = new ZKConnectionManager();
                    instance = temp;
                }
            }
        }
        return instance;
    }


    private Gson getGson() {
        return new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constant.ZKTEAM_DOMAIN_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
    }

    public ZKApi getZKApi() {
        return getRetrofit().create(ZKApi.class);
    }

    public void test() {
        Log.d(TAG, "test() called");
    }

}
