package cc.zkteam.juediqiusheng.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import cc.zkteam.juediqiusheng.lifecycle.ZKLifecycleObserver;

/**
 * The {@link ViewPager} that will host the section contents.
 * Created by WangQing on 2017/11/20.
 */

public class ZKViewPager extends ViewPager implements ZKLifecycleObserver {

    private Lifecycle lifecycle;
    private OnPageChangeListener listener;

    public ZKViewPager(@NonNull Context context) {
        super(context);
    }

    public ZKViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置 listener 和 adapter
     * @param listener  OnPageChangeListener
     * @param adapter   PagerAdapter
     */
    public void setViewPager(@NonNull OnPageChangeListener listener, @Nullable PagerAdapter adapter) {
        this.listener = listener;
        addOnPageChangeListener(listener);
        setAdapter(adapter);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void destroyViewPager() {
        removeOnPageChangeListener(listener);
    }
}
