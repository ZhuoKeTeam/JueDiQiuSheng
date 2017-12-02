package cc.zkteam.juediqiusheng.ui.fragment.question.dagger2;

import cc.zkteam.juediqiusheng.ui.fragment.question.NewQuestionFragment;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * NewQuestionComponent
 * Created by WangQing on 2017/12/2.
 */
@Subcomponent(modules = {NewQuestionModule.class})
public interface NewQuestionComponent extends AndroidInjector<NewQuestionFragment>{
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<NewQuestionFragment>{}
}
