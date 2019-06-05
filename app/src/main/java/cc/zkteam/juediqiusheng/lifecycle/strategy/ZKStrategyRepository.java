package cc.zkteam.juediqiusheng.lifecycle.strategy;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import cc.zkteam.juediqiusheng.api.ZKApi;
import cc.zkteam.juediqiusheng.bean.BannerBean;
import cc.zkteam.juediqiusheng.bean.CategoryBean;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;
public class ZKStrategyRepository {
    private ZKApi zkApi;
    private static ZKStrategyRepository instance = null;
    private ZKStrategyRepository() {
        zkApi = ZKConnectionManager.getInstance().getZKApi();
    }
    public static ZKStrategyRepository getInstance() {
        if (instance == null) {
            synchronized (ZKStrategyRepository.class) {
                ZKStrategyRepository temp = instance;
                if (temp == null) {
                    temp = new ZKStrategyRepository();
                    instance = temp;
                }
            }
        }
        return instance;
    }
    public MutableLiveData<List<CategoryBean>> getCategoryBeanList() {
        MutableLiveData<List<CategoryBean>> mutableLiveData = new MutableLiveData<>();
        zkApi.categoryData(20).enqueue(new ZKCallback<List<CategoryBean>>() {
            @Override
            public void onResponse(List<CategoryBean> result) {
                List<CategoryBean> newCategoryBean = new ArrayList<>();
                for (CategoryBean bean : result) {
                    int id = bean.getId();
                    String myId = String.valueOf(id);
                    if (!myId.contains("1000")) {
                        newCategoryBean.add(bean);
                    }
                }
                mutableLiveData.setValue(newCategoryBean);
            }
            @Override
            public void onFailure(Throwable throwable) {
            }
        });
        return mutableLiveData;
    }
    public MutableLiveData<List<BannerBean>> getBanerBeanList(int number, String jid){
        MutableLiveData<List<BannerBean>>  mutableLiveData=new MutableLiveData<>();
        zkApi.getStrategy(number, jid).enqueue(new ZKCallback<List<BannerBean>>() {
            @Override
            public void onResponse(List<BannerBean> result) {
                mutableLiveData.setValue(result);
            }
            @Override
            public void onFailure(Throwable throwable) {
                Log.d("banner",throwable.toString());
            }
        });
        return mutableLiveData;
    }
}
