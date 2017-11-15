package cc.zkteam.dagger2.di;

import android.app.Activity;

import cc.zkteam.dagger2.ui.detail.DetailActivity;
import cc.zkteam.dagger2.ui.detail.DetailActivityComponent;
import cc.zkteam.dagger2.ui.main.MainActivity;
import cc.zkteam.dagger2.ui.main.MainActivityComponent;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by WangQing on 2017/11/15.
 */
@Module
public abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMainActivity(MainActivityComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(DetailActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindDetailActivity(DetailActivityComponent.Builder builder);
}
