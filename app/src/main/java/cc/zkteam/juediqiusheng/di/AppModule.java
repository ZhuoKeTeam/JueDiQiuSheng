package cc.zkteam.juediqiusheng.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import cc.zkteam.juediqiusheng.ui.main.MainActivityComponent;
import dagger.Module;
import dagger.Provides;

/**
 * // TODO: 2017/11/17 todo
 * Created by WangQing on 2017/11/17.
 */
@Module(subcomponents = {
        MainActivityComponent.class})
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }
}
