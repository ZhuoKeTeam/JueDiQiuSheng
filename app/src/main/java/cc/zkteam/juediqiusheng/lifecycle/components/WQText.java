package cc.zkteam.juediqiusheng.lifecycle.components;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * WQText 文本组件
 * 通过继承 LifecycleObserver， 保证我们可以通过注解或者接口直接查询 UI 的生命周期
 *
 * Created by WangQing on 2017/10/26.
 */

public class WQText implements LifecycleObserver {
    private static final String TAG = "WQText";
    private Lifecycle lifecycle;

    public WQText(Lifecycle lifecycle) {
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
