package cc.zkteam.juediqiusheng.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.util.AttributeSet;

import com.youth.banner.Banner;

/**
 * ZKBanner
 * 参考：https://github.com/youth5201314/banner
 * Created by WangQing on 2017/11/7.
 */

public class ZKBanner extends Banner implements LifecycleObserver {
    private Lifecycle lifecycle;

    public ZKBanner(Context context) {
        super(context);
    }

    public ZKBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZKBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLifecycle(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void startPlay() {
        //开始轮播
        startAutoPlay();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopPlay() {
        //结束轮播
        stopAutoPlay();
    }

//    // TODO: 2017/11/10  范例
//    public void setLifecycleEanable(boolean b) {
//        enable = b;
//
//        if (lifecycle.getCurrentState().isAtLeast(STARTED)) {
//            //在这里做一些逻辑判断
//        }
//    }
}
