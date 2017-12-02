package cc.zkteam.juediqiusheng.ui.fragment.question.dagger2;

import cc.zkteam.juediqiusheng.ui.fragment.question.QuestionFragment;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * NewQuestionComponent
 * Created by WangQing on 2017/12/2.
 */
@Subcomponent(modules = {QuestionModule.class})
public interface QuestionComponent extends AndroidInjector<QuestionFragment>{
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<QuestionFragment>{}
}
