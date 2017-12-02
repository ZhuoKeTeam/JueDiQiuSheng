package cc.zkteam.juediqiusheng.ui.main;

import javax.inject.Singleton;

import cc.zkteam.juediqiusheng.module.RFProvider;
import cc.zkteam.juediqiusheng.module.answer.QFProvider;
import cc.zkteam.juediqiusheng.ui.fragment.question.dagger2.NewQuestionProvider;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * MainActivityComponent
 * Created by WangQing on 2017/11/17.
 */
@Subcomponent(modules = {MainActivityModule.class, QFProvider.class, RFProvider.class, NewQuestionProvider.class})
@Singleton
public interface MainActivityComponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity>{}
}
