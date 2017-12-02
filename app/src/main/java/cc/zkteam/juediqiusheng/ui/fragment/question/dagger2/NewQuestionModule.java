package cc.zkteam.juediqiusheng.ui.fragment.question.dagger2;

import android.arch.lifecycle.ViewModelProviders;

import cc.zkteam.juediqiusheng.module.answer.QuestionViewModel;
import cc.zkteam.juediqiusheng.ui.fragment.question.NewQuestionFragment;
import cc.zkteam.juediqiusheng.ui.fragment.question.mvp.NewQuestionPresenterImpl;
import cc.zkteam.juediqiusheng.ui.fragment.question.mvp.NewQuestionView;
import dagger.Module;
import dagger.Provides;

/**
 * NewQuestionModule
 * Created by WangQing on 2017/12/2.
 */
@Module
public class NewQuestionModule {

    @Provides
    NewQuestionView provideNewQuestionView(NewQuestionFragment fragment) {
        return fragment;
    }

    @Provides
    QuestionViewModel provideNewQuestionModule(NewQuestionFragment fragment) {
        return ViewModelProviders.of(fragment).get(QuestionViewModel.class);
    }

    @Provides
    NewQuestionPresenterImpl provideNewQuestionPresenterImpl(NewQuestionFragment fragment, QuestionViewModel questionViewModel) {
        return new NewQuestionPresenterImpl(fragment, questionViewModel);
    }
}
