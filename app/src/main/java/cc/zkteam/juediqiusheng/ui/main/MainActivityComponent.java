package cc.zkteam.juediqiusheng.ui.main;

import javax.inject.Singleton;

import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * MainActivityComponent
 * Created by WangQing on 2017/11/17.
 */
@Singleton
@Subcomponent(modules = {
        MainActivityModule.class,
        ZKConnectionManager.class
})
public interface MainActivityComponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity>{}
}
