package cc.zkteam.juediqiusheng.ui.fragment.question.dagger2;

import android.arch.lifecycle.ViewModelProviders;

import cc.zkteam.juediqiusheng.ui.fragment.question.mvp.QuestionView;
import cc.zkteam.juediqiusheng.ui.fragment.question.QuestionFragment;
import cc.zkteam.juediqiusheng.ui.fragment.question.QuestionViewModel;
import cc.zkteam.juediqiusheng.ui.fragment.question.mvp.QuestionPresenterImpl;
import dagger.Module;
import dagger.Provides;

/**
 * NewQuestionModule
 * Created by WangQing on 2017/12/2.
 */
@Module
public class QuestionModule {

    @Provides
    QuestionView provideNewQuestionView(QuestionFragment fragment) {
        return fragment;
    }

    @Provides
    QuestionViewModel provideNewQuestionModule(QuestionFragment fragment) {
        return ViewModelProviders.of(fragment).get(QuestionViewModel.class);
    }

    @Provides
    QuestionPresenterImpl provideNewQuestionPresenterImpl(QuestionFragment fragment, QuestionViewModel questionViewModel) {
        return new QuestionPresenterImpl(fragment, questionViewModel);
    }
}
