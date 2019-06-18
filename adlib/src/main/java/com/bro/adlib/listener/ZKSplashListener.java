package com.bro.adlib.listener;

/**
 * Created by zhangshan on 2019-06-17 15:53.
 */
public interface ZKSplashListener {

    void onADPresent();

    void onADDismissed();

    void onNoAD();

    /**
     * 倒计时回调，返回广告还将被展示的剩余时间。
     * 通过这个接口，开发者可以自行决定是否显示倒计时提示，或者还剩几秒的时候显示倒计时
     *
     * @param millisUntilFinished 剩余毫秒数
     */
    void onADTick(long millisUntilFinished);



}
