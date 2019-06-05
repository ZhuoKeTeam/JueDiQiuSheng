package cc.zkteam.juediqiusheng.lifecycle.strategy;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import java.util.List;
import cc.zkteam.juediqiusheng.bean.CategoryBean;
public class ZKStrategyViewModel extends ViewModel {
    private MutableLiveData<List<CategoryBean>> mCategoryList;
    private ZKStrategyRepository strategyRepository;
    public MutableLiveData<List<CategoryBean>> getCategoryList() {
        if (mCategoryList == null) {
            mCategoryList = new MutableLiveData<>();
            init();
        }
        return mCategoryList;
    }
    public void init() {
        if (strategyRepository != null) {
            return;
        }
        strategyRepository = ZKStrategyRepository.getInstance();
        mCategoryList = strategyRepository.getCategoryBeanList();
    }
}
