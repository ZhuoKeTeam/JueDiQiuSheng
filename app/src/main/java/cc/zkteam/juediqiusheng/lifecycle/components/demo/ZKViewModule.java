package cc.zkteam.juediqiusheng.lifecycle.components.demo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import cc.zkteam.juediqiusheng.ui.main.MainActivity;


/**
 * ZKViewModule ViewModel
 * Created by WangQing on 2017/10/27.
 */

public class ZKViewModule extends ViewModel {

    private static final String TAG = MainActivity.TAG;

    private ZKLiveData zkLiveData;
    private int count = 0;

    public LiveData<String> getText() {
        if (zkLiveData == null) {
            zkLiveData = new ZKLiveData();
            loadNewNumber();
        }
        return zkLiveData;
    }

    private void loadNewNumber() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG, "run() called: count:" + count);
//                对数据模型更新后，对应的界面也会自动刷新，可以取代 handler
                while (count < 5) {
                    zkLiveData.setPostText(String.valueOf(count));
                    count++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                zkLiveData.setPostText("测试完成！");

            }
        }).start();
    }

}
