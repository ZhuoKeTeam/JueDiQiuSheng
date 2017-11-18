package cc.zkteam.juediqiusheng.ui.main;

import javax.inject.Singleton;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * MainActivityComponent
 * Created by WangQing on 2017/11/17.
 */
@Subcomponent(modules = {
        MainActivityModule.class,
})
@Singleton
public interface MainActivityComponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity>{}
}
