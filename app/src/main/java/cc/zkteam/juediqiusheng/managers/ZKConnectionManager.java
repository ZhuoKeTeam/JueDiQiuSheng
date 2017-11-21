package cc.zkteam.juediqiusheng.managers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import cc.zkteam.juediqiusheng.Constant;
import cc.zkteam.juediqiusheng.ZKBase;
import cc.zkteam.juediqiusheng.api.ZKApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

        //http://blog.csdn.net/u014695188/article/details/52985514
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);

        if (ZKBase.isDebug) {
            //log信息拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置Debug Log模式
            builder.addInterceptor(httpLoggingInterceptor);
        }

        return new Retrofit.Builder()
                .baseUrl(Constant.ZKTEAM_DOMAIN_URL)
                .client(builder.build())
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
