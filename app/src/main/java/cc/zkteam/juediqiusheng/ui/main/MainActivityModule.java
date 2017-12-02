package cc.zkteam.juediqiusheng.ui.main;

import cc.zkteam.juediqiusheng.module.RFComponent;
import cc.zkteam.juediqiusheng.module.answer.dagger2.QFComponent;
import cc.zkteam.juediqiusheng.ui.fragment.question.dagger2.NewQuestionComponent;
import dagger.Module;

/**
 * MainActivityModule
 * Created by WangQing on 2017/11/17.
 */
@Module(subcomponents = {QFComponent.class, RFComponent.class, NewQuestionComponent.class})
public class MainActivityModule {

}
