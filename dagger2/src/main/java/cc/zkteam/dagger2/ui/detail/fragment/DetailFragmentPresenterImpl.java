package cc.zkteam.dagger2.ui.detail.fragment;

import javax.inject.Inject;

/**
 * DetailFragmentPresenterImpl
 * Created by WangQing on 2017/11/15.
 */

public class DetailFragmentPresenterImpl implements DetailFragmentPresenter {
    DetailFragmentView detailFragmentView;

    @Inject
    public DetailFragmentPresenterImpl(DetailFragmentView detailFragmentView) {
        this.detailFragmentView = detailFragmentView;
        detailFragmentView.onDetailFragmentLoaded();
    }
}
