package cc.zkteam.juediqiusheng.view;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.util.AttributeSet;
import com.youth.banner.Banner;
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
        startAutoPlay();
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopPlay() {
        stopAutoPlay();
    }
}
