package cc.zkteam.juediqiusheng.di;

import android.app.Activity;

import cc.zkteam.juediqiusheng.ui.main.MainActivity;
import cc.zkteam.juediqiusheng.ui.main.MainActivityComponent;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * ActivityBuilder
 * Created by WangQing on 2017/11/17.
 */
@Module
public abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> buildMainActivity(MainActivityComponent.Builder builder);
}
