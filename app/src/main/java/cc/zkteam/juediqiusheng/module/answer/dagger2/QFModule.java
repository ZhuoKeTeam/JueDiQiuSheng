package cc.zkteam.juediqiusheng.module.answer.dagger2;

import cc.zkteam.juediqiusheng.module.answer.QuestionFragment;
import cc.zkteam.juediqiusheng.module.answer.mvp.QFView;
import dagger.Module;
import dagger.Provides;

/**
 * QFModule
 * Created by WangQing on 2017/11/22.
 */
@Module
public class QFModule {

    @Provides
    QFView provideQFView(QuestionFragment fragment) {
        return fragment;
    }

//    @Provides
//    QFPresenterImpl providerQFPresenterImpl(QFView qfView){
//        return new QFPresenterImpl(qfView);
//    }
}
