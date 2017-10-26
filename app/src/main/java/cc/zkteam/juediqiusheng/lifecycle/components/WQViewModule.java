package cc.zkteam.juediqiusheng.lifecycle.components;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by WangQing on 2017/10/26.
 */

public class WQViewModule extends ViewModel {

    private WQLiveData wqLiveData;
    private int count = 0;

    public LiveData<String> getText() {
        if (wqLiveData == null) {
            wqLiveData = new WQLiveData();
            loadNewNumber();
        }
        return wqLiveData;
    }

    private void loadNewNumber() {
        new Thread(new Runnable() {
            @Override
            public void run() {

//                对数据模型更新后，对应的界面也会自动刷新，可以取代 handler
                while (count < 5) {
                    wqLiveData.setPostText(String.valueOf(count));
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
                wqLiveData.setPostText("测试完成！");

            }
        }).start();
    }


}
