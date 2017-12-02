package cc.zkteam.juediqiusheng.module;

import cc.zkteam.juediqiusheng.fragment.RecommendFragment;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by WangQing on 2017/12/2.
 */

@Subcomponent()
public interface RFComponent extends AndroidInjector<RecommendFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<RecommendFragment>{}
}
