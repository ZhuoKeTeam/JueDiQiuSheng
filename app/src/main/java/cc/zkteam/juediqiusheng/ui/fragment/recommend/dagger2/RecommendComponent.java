package cc.zkteam.juediqiusheng.ui.fragment.recommend.dagger2;

import cc.zkteam.juediqiusheng.ui.fragment.recommend.RecommendFragment;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by WangQing on 2017/12/3.
 */
@Subcomponent(modules = {RecommendModule.class})
public interface RecommendComponent extends AndroidInjector<RecommendFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<RecommendFragment> {}
}
