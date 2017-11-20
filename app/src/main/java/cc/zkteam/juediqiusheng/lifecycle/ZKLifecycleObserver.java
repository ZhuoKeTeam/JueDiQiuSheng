package cc.zkteam.juediqiusheng.lifecycle;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;

/**
 * ZKLifecycleObserver, 需要手动添加 Lifecycle 的实例
 * Created by WangQing on 2017/11/20.
 */

public interface ZKLifecycleObserver extends LifecycleObserver {

    /**
     * 如果复写该方法，请务必添加 lifecycle.addObserver(this); 否则不能生效
     * @param lifecycle
     */
    default void setLifecycle(Lifecycle lifecycle){
        lifecycle.addObserver(this);
    }
}
