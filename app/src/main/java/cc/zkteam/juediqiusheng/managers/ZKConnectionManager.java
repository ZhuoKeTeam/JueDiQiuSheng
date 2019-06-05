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
public class ZKConnectionManager {
    private static final String TAG = "ZKConnectionManager";
    private static ZKConnectionManager instance = null;
    @Inject
    public ZKConnectionManager() {
    }
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
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
    }
    private Retrofit getRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        if (ZKBase.isDebug) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
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
