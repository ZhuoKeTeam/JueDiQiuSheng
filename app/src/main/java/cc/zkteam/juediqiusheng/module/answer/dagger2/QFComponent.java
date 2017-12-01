package cc.zkteam.juediqiusheng.module.answer.dagger2;

import cc.zkteam.juediqiusheng.module.answer.QuestionFragment;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * QFComponent
 * Created by WangQing on 2017/11/22.
 */
@Subcomponent(modules = {QFModule.class})
public interface QFComponent extends AndroidInjector<QuestionFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<QuestionFragment>{}
}
