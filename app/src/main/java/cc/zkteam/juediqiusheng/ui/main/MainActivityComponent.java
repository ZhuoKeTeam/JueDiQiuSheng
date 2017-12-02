package cc.zkteam.juediqiusheng.ui.main;

import javax.inject.Singleton;

import cc.zkteam.juediqiusheng.ui.fragment.question.dagger2.QuestionProvider;
import cc.zkteam.juediqiusheng.ui.fragment.recommend.dagger2.RecommendProvider;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * MainActivityComponent
 * Created by WangQing on 2017/11/17.
 */
@Subcomponent(modules = {MainActivityModule.class, QuestionProvider.class, RecommendProvider.class})
@Singleton
public interface MainActivityComponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity>{}
}
