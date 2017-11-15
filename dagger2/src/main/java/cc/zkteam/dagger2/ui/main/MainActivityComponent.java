package cc.zkteam.dagger2.ui.main;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * MainActivityComponent
 * Created by WangQing on 2017/11/15.
 */
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity>{}
}
