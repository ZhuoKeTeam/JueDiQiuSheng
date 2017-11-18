package cc.zkteam.juediqiusheng.di;

import android.app.Activity;

import cc.zkteam.juediqiusheng.activity.WebViewActivity;
import cc.zkteam.juediqiusheng.ui.main.MainActivity;
import cc.zkteam.juediqiusheng.ui.main.MainActivityComponent;
import cc.zkteam.juediqiusheng.ui.main.test.WebViewActivityComponent;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * ActivityBuilder
 * 每次添加一个 Activity 都需要在此注册上
 *
 * Created by WangQing on 2017/11/17.
 */
@Module
public abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> buildMainActivity(MainActivityComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(WebViewActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> buildWebViewActivity(WebViewActivityComponent.Builder builder);
}
