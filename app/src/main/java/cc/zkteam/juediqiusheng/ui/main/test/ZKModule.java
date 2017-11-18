package cc.zkteam.juediqiusheng.ui.main.test;

import cc.zkteam.juediqiusheng.di.annotations.SingletonGlobal;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * ZKManager
 * Created by WangQing on 2017/11/19.
 */
@Module
public class ZKModule {

    /**
     * 给 app 全局提供单例
     * @return OkHttpClient 的单例
     */
    @Provides
    @SingletonGlobal
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

}
