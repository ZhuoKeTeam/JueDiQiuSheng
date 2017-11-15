package cc.zkteam.dagger2.ui.main;

import cc.zkteam.dagger2.data.ApiService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by WangQing on 2017/11/15.
 */
@Module
public class MainActivityModule {

    @Provides
    MainView provideMainView(MainActivity mainActivity) {
        return mainActivity;
    }

    @Provides
    MainPresenter provideMainPresenter(MainView mainView, ApiService apiService) {
        return new MainPresenterImpl(mainView, apiService);
    }
}
