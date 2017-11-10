package cc.zkteam.juediqiusheng.lifecycle.strategy;

import android.arch.lifecycle.ViewModel;

/**
 * ZKStrategyViewModel
 * Created by WangQing on 2017/11/10.
 */
public class ZKStrategyViewModel extends ViewModel {

    private ZKStrategyLiveData mCategoryList;

    public ZKStrategyLiveData getCategoryList() {
        if (mCategoryList == null) {
            mCategoryList = new ZKStrategyLiveData();
        }
        return mCategoryList;
    }
}
