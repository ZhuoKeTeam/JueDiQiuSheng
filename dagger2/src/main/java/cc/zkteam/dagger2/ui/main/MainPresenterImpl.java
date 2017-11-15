package cc.zkteam.dagger2.ui.main;

import javax.inject.Inject;

import cc.zkteam.dagger2.data.ApiService;

/**
 * Created by WangQing on 2017/11/15.
 */

public class MainPresenterImpl implements MainPresenter {

    MainView mainView;
    ApiService apiService;

    @Inject
    public MainPresenterImpl(MainView mainView, ApiService apiService) {
        this.mainView = mainView;
        this.apiService = apiService;
    }

    @Override
    public void loadMain() {
        apiService.loadData();
        mainView.onMainLoaded();
    }
}
