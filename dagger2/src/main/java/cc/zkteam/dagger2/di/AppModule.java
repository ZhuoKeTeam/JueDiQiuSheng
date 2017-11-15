package cc.zkteam.dagger2.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import cc.zkteam.dagger2.ui.detail.DetailActivityComponent;
import cc.zkteam.dagger2.ui.main.MainActivityComponent;
import dagger.Module;
import dagger.Provides;

/**
 * Created by WangQing on 2017/11/15.
 */
@Module(subcomponents = {
        MainActivityComponent.class,
        DetailActivityComponent.class})
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }
}
