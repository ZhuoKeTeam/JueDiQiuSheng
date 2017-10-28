package cc.zkteam.juediqiusheng.lifecycle.components.demo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import cc.zkteam.juediqiusheng.MainActivity;

/**
 * ZKText 的生命周期测试
 *
 * Created by WangQing on 2017/10/27.
 */

public class ZKText implements LifecycleObserver {
    private static final String TAG = MainActivity.TAG;
    private Lifecycle lifecycle;

    public ZKText(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
        this.lifecycle = lifecycle;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume() {
        Log.d(TAG, "resume: WQText");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Log.d(TAG, "onStop: WQText");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Log.d(TAG, "onDestroy: WQText");
    }

    public void updateText(String msg) {
        if (lifecycle.getCurrentState().equals(Lifecycle.State.RESUMED)) {
            Log.d(TAG, "updateText: ON_RESUME");
        } else {
            Log.d(TAG, "updateText: not resume!");
        }
    }
}
