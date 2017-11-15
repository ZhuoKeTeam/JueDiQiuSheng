package cc.zkteam.dagger2.ui.detail;

import android.support.v4.app.Fragment;

import cc.zkteam.dagger2.ui.detail.fragment.DetailFragment;
import cc.zkteam.dagger2.ui.detail.fragment.DetailFragmentComponent;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * DetailFragmentProvider
 * Created by WangQing on 2017/11/15.
 */
@Module
public abstract class DetailFragmentProvider {

    @Binds
    @IntoMap
    @FragmentKey(DetailFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> providerDetailFragmentFactory(DetailFragmentComponent.Builder builder);
}
