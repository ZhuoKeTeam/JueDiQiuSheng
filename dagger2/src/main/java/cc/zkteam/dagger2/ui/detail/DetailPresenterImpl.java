package cc.zkteam.dagger2.ui.detail;

import javax.inject.Inject;

import cc.zkteam.dagger2.data.ApiService;

/**
 * Created by WangQing on 2017/11/15.
 */

public class DetailPresenterImpl implements DetailPresenter {

    DetailView detailView;
    ApiService apiService;

    @Inject
    public DetailPresenterImpl(DetailView detailView, ApiService apiService) {
        this.detailView = detailView;
        this.apiService = apiService;
    }

    @Override
    public void loadDetail() {
        apiService.loadData();
        detailView.onDetailLoaded();
    }
}
