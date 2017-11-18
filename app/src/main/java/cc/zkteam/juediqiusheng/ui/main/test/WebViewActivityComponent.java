package cc.zkteam.juediqiusheng.ui.main.test;

import javax.inject.Singleton;

import cc.zkteam.juediqiusheng.activity.WebViewActivity;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * WebViewActivityComponent
 * Created by WangQing on 2017/11/19.
 */
//@Subcomponent(modules = {ZKModule.class})
@Subcomponent()
@Singleton
public interface WebViewActivityComponent extends AndroidInjector<WebViewActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder {}
}
