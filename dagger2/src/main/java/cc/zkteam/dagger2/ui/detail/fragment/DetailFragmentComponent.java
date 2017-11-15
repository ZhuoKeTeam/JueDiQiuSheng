package cc.zkteam.dagger2.ui.detail.fragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * DetailFragmentComponent
 * Created by WangQing on 2017/11/15.
 */
@Subcomponent(modules = {DetailFragmentModule.class})
public interface DetailFragmentComponent extends AndroidInjector<DetailFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DetailFragment>{}
}
