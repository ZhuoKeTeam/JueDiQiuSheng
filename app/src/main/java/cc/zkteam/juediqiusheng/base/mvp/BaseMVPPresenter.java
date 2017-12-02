package cc.zkteam.juediqiusheng.base.mvp;

import javax.inject.Inject;

/**
 * Presenter 基类
 *
 * Created by WangQing on 2017/12/2.
 *
 * @param <V> MVP View 类型 继承{@link BaseMVPView}
 * @param <M> MVP Module 继承 {@link BaseMVPModule}
 */
public class BaseMVPPresenter<V extends BaseMVPView, M extends BaseMVPModule> {

    protected V mView;

    @Inject
    protected M mModule;

    public BaseMVPPresenter() {
    }

    public void onAttach(V view) {
        mView = view;
    }

    public void onDetach() {
        mView = null;
    }
}
