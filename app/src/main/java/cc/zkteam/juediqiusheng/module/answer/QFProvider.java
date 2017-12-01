package cc.zkteam.juediqiusheng.module.answer;

import android.support.v4.app.Fragment;

import cc.zkteam.juediqiusheng.module.answer.dagger2.QFComponent;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * QFProvider
 * Created by WangQing on 2017/11/22.
 */
@Module
public abstract class QFProvider {

    @Binds
    @IntoMap
    @FragmentKey(QuestionFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> providerQuestionFragmentFactory(QFComponent.Builder builder);
}
