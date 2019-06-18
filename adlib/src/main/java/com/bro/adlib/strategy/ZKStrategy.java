package com.bro.adlib.strategy;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bro.adlib.listener.ZKNativeListener;
import com.bro.adlib.listener.ZKRewardListener;
import com.bro.adlib.listener.ZKSplashListener;

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
    void initInterstitialAD(Activity activity , boolean isAutoShow);

    /**
     * 加载插屏广告
     */
    void loadInterstitialAd(Activity activity);

    /**
     * 初始化激励广告
     */
    void initRewardVideoAd(Context context , ZKRewardListener rewardListener);

    /**
     * 加载激励广告
     */
    void loadRewardVideoAd();

    /**
     * 加载激励广告
     */
    void initSplashAD(Activity activity, TextView skipView,
                      ViewGroup splashContainer, ZKSplashListener zkSplashListener);


    /**
     * 加载原生广告
     * @param ad_count  加载广告的条数，取值范围为[1, 10]
     */
    void initNativeExpressAD(Context context, int ad_count, ZKNativeListener zkNativeListener);




}
