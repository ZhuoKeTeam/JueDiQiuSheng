package cc.zkteam.dagger2.ui.detail;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by WangQing on 2017/11/15.
 */
@Subcomponent(modules = {DetailActivityModule.class, DetailFragmentProvider.class})
public interface DetailActivityComponent extends AndroidInjector<DetailActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DetailActivity> {
    }
}
