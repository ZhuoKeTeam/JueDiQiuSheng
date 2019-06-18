package com.bro.adlib.strategy;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by zhangshan on 2019-06-17 14:56.
 */
public interface ZKStrategy {

    /**
     * Banner广告
     *
     * @param bannerView
     * @param activity
     */
    void initBannerAD(ViewGroup bannerView, Activity activity);

    /**
     * 插屏广告
     *
     * @param activity
     */
    void initInterstitialAD(Activity activity);

    /**
     * 初始化激励广告
     */
    void initRewardVideoAd(Context context);

    /**
     * 加载激励广告
     */
    void loadRewardVideoAd();

    /**
     * 加载激励广告
     */
    void initSplashAD(Activity activity, TextView skipView,
                      FrameLayout splashContainer, ZKSplashListener zkSplashListener);


    /**
     * 加载原生广告
     * @param ad_count  加载广告的条数，取值范围为[1, 10]
     */
    void initNativeExpressAD(Context context, int ad_count, ZKNativeListener zkNativeListener);




}
