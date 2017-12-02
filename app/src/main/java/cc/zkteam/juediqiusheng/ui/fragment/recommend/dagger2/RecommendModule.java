package cc.zkteam.juediqiusheng.ui.fragment.recommend.dagger2;

import cc.zkteam.juediqiusheng.ui.fragment.recommend.RecommendFragment;
import cc.zkteam.juediqiusheng.ui.fragment.recommend.mvp.RecommendPresenterImpl;
import cc.zkteam.juediqiusheng.ui.fragment.recommend.mvp.RecommendView;
import dagger.Module;
import dagger.Provides;

/**
 * RecommendModule
 * Created by WangQing on 2017/12/3.
 */
@Module
public class RecommendModule {

    @Provides
    RecommendView provideRecommendView(RecommendFragment recommendFragment){
        return recommendFragment;
    }

    @Provides
    RecommendPresenterImpl provideRecommendPresenter(RecommendFragment recommendFragment) {
        return new RecommendPresenterImpl();
    }
}