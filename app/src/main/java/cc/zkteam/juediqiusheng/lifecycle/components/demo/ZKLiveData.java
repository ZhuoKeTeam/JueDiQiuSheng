package cc.zkteam.juediqiusheng.lifecycle.components.demo;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import cc.zkteam.juediqiusheng.ui.main.MainActivity;


/**
 * ZKLiveData，泛型传入 String 字符串
 *
 * Created by WangQing on 2017/10/27.
 */

public class ZKLiveData extends LiveData<String> {

    private static final String TAG = MainActivity.TAG;

    public ZKLiveData() {
    }

    @Override
    protected void onActive() {
        super.onActive();
        Log.d(TAG, "onActive() called");
        //2017/10/26 可以在此请求数据或者加载数据等操作
//        setValue(String.valueOf(countLiveData += 2));
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Log.d(TAG, "onInactive() called");
//        setValue(String.valueOf(countLiveData--));
    }

    public void setPostText(String msg) {
        postValue(msg);
    }

    public void setText(String msg) {
        setValue(msg);
    }
}
