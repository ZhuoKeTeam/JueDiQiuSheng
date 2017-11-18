package cc.zkteam.juediqiusheng.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import cc.zkteam.juediqiusheng.ui.main.MainActivityComponent;
import cc.zkteam.juediqiusheng.ui.main.test.WebViewActivityComponent;
import dagger.Module;
import dagger.Provides;

/**
 * 每添加一个 四大组件 都需要在此写上 对应的 Component
 * Created by WangQing on 2017/11/17.
 */
@Module(subcomponents = {
        MainActivityComponent.class,
        WebViewActivityComponent.class})
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }
}
