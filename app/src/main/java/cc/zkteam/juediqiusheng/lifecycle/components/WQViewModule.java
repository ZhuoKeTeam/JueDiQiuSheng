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

                if (count < 5) {
                    wqLiveData.setPostText(String.valueOf(count));
                    count++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }


}
