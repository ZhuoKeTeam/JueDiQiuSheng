package cc.zkteam.juediqiusheng.lifecycle.components;

import android.arch.lifecycle.LiveData;
import android.util.Log;

/**
 * WQLiveData
 * Created by WangQing on 2017/10/26.
 */

public class WQLiveData extends LiveData<String> {

    private static final String TAG = "WQLiveData";

    public WQLiveData() {
    }

    @Override
    protected void onActive() {
        super.onActive();
        Log.d(TAG, "onActive() called");
        // TODO: 2017/10/26 可以在此请求数据或者加载数据等操作
    }

    @Override
    protected void onInactive() {
        super.onInactive();

        Log.d(TAG, "onInactive() called");
    }

    public void setPostText(String msg) {
        postValue(msg);
    }

    public void setText(String msg) {
        setValue(msg);
    }
}
