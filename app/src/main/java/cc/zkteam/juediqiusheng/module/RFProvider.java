package cc.zkteam.juediqiusheng.module;

import android.support.v4.app.Fragment;

import cc.zkteam.juediqiusheng.fragment.RecommendFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * Created by WangQing on 2017/12/2.
 */
@Module
public abstract class RFProvider {
    @Binds
    @IntoMap
    @FragmentKey(RecommendFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> providerQuestionFragmentFactory(RFComponent.Builder builder);

}
