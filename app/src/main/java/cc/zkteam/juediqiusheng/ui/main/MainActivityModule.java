package cc.zkteam.juediqiusheng.ui.main;

import cc.zkteam.juediqiusheng.ui.fragment.question.dagger2.QuestionComponent;
import cc.zkteam.juediqiusheng.ui.fragment.recommend.dagger2.RecommendComponent;
import dagger.Module;

/**
 * MainActivityModule
 * Created by WangQing on 2017/11/17.
 */
@Module(subcomponents = {QuestionComponent.class, RecommendComponent.class})
public class MainActivityModule {

}
