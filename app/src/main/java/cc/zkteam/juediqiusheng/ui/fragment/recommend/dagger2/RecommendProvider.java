package cc.zkteam.juediqiusheng.ui.fragment.recommend.dagger2;

import android.support.v4.app.Fragment;

import cc.zkteam.juediqiusheng.ui.fragment.recommend.RecommendFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * RecommendProvider
 * Created by WangQing on 2017/12/3.
 */
@Module
public abstract class RecommendProvider {

    @Binds
    @IntoMap
    @FragmentKey(RecommendFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> providerRecommendFragment(RecommendComponent.Builder builder);
}