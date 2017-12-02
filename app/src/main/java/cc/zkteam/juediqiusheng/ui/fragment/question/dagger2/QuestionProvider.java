package cc.zkteam.juediqiusheng.ui.fragment.question.dagger2;

import android.support.v4.app.Fragment;

import cc.zkteam.juediqiusheng.ui.fragment.question.QuestionFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * NewQuestionProvider
 * Created by WangQing on 2017/12/2.
 */
@Module
public abstract class QuestionProvider {

    @Binds
    @IntoMap
    @FragmentKey(QuestionFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> providerNewQuestionFragment(QuestionComponent.Builder builder);
}
