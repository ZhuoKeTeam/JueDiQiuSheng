package com.bro.adlib.strategy;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bro.adlib.ad.ZKBaiduAD;
import com.bro.adlib.ad.ZKTencentAD;
import com.bro.adlib.listener.ZKNativeListener;
import com.bro.adlib.listener.ZKRewardListener;
import com.bro.adlib.listener.ZKSplashListener;

/**
 * Created by zhangshan on 2019-06-17 16:36.
 */
public class ZKContext {

    public static int TencentType = 1;
    public static int BaiduType = 2;


    private ZKStrategy strategy;

    public ZKContext(int type) {
        if (type == 1) {
            this.strategy = ZKTencentAD.getInstance();
        } else if (type == 2) {
            this.strategy = ZKBaiduAD.getInstance();
        }
//        this.strategy = strategy;
    }

    public ZKContext(ZKStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Banner广告
     *
     * @param bannerView
     * @param activity
     */
    public void initBannerAD(ViewGroup bannerView, Activity activity) {
        strategy.initBannerAD(bannerView, activity);
    }


    /**
     * 初始化插屏广告
     *
     * @param activity
     */
    public void initInterstitialAD(Activity activity) {
        strategy.initInterstitialAD(activity, false);
    }

    /**
     * 显示插屏广告
     */
    public void loadInterstitialAD(Activity activity) {
        strategy.loadInterstitialAd(activity);
    }

    /**
     * 初始化激励广告
     */
    public void initRewardVideoAd(Context context, ZKRewardListener rewardListener) {
        strategy.initRewardVideoAd(context, rewardListener);
    }

    /**
     * 初始化激励广告
     */
    public void loadRewardVideoAd() {
        strategy.loadRewardVideoAd();
    }

    /**
     * 开屏广告
     */
    public void initSplashAD(Activity activity, TextView skipView, FrameLayout splashContainer, ZKSplashListener zkSplashListener) {
        strategy.initSplashAD(activity, skipView, splashContainer, zkSplashListener);
    }

    /**
     * 加载原生广告
     *
     * @param ad_count 加载广告的条数，取值范围为[1, 10]
     */
    public void initNativeExpressAD(Context context, int ad_count, ZKNativeListener zkNativeListener) {
        strategy.initNativeExpressAD(context, ad_count, zkNativeListener);
    }


}
