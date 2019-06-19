package com.bro.adlib.ad;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mobad.video.XAdManager;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.baidu.mobads.InterstitialAd;
import com.baidu.mobads.InterstitialAdListener;
import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.baidu.mobads.rewardvideo.RewardVideoAd;
import com.blankj.utilcode.util.ToastUtils;
import com.bro.adlib.listener.ZKNativeListener;
import com.bro.adlib.listener.ZKRewardListener;
import com.bro.adlib.listener.ZKSplashListener;
import com.bro.adlib.strategy.ZKStrategy;
import com.bro.adlib.util.UMUtils;

import org.json.JSONObject;

/**
 * Created by zhangshan on 2019-06-17 15:56.
 */
public class ZKBaiduAD implements ZKStrategy {

    private final String TAG = "ZKBaiduAD";

    // Appid_baidu
    public static final String AD_BAIDU_APP_ID = "e64ccf7d";
    // 百度横幅广告ID
    public static final String AD_BAIDU_RELEASE_DTS_HF_KEY = "6288996";
    // 百度激励广告ID
    public static final String AD_BAIDU_RELEASE_DTS_JL_KEY = "6288997";
    // 开屏广告
    public static final String AD_BAIDU_RELEASE_DTS_KP_KEY = "6289086";
    // 插屏广告
    public static final String AD_BAIDU_RELEASE_DTS_CP_KEY = "6294769";
//    public  final String AD_BAIDU_RELEASE_DTS_CP_KEY = "6289360";

    /**
     * ---------------------------------------------BaiDu 激励广告-----------------------------------------------------------
     */
    private static RewardVideoAd rewardVideoAd;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler();

    private static ZKBaiduAD mSingleton = null;
    private InterstitialAd interAd;

    private ZKBaiduAD() {
    }

    public static ZKBaiduAD getInstance() {
        if (mSingleton == null) {
            synchronized (ZKBaiduAD.class) {
                if (mSingleton == null) {
                    mSingleton = new ZKBaiduAD();
                }
            }
        }
        return mSingleton;
    }


    @Override
    public void initBannerAD(ViewGroup bannerView, Activity activity) {
        try {
            if (bannerView != null) {
                bannerView.removeAllViews();
                String adPlaceID = "6294768";
                AdView adView = new AdView(activity, adPlaceID);
                adView.setListener(new AdViewListener() {
                    public void onAdReady(AdView adView) {
                        logD("BD_HF_onAdReady->");
                    }

                    public void onAdShow(JSONObject jsonObject) {
                        logD("BD_HF_onAdShow->");
                    }

                    public void onAdClick(JSONObject jsonObject) {
                        logD("BD_HF_onAdClick->");
                    }

                    public void onAdFailed(String s) {
                        logD("BD_HF_onAdFailed->" + s);
                    }

                    public void onAdSwitch() {
                        logD("BD_HF_onAdSwitch->");
                    }

                    public void onAdClose(JSONObject jsonObject) {
                        logD("BD_HF_onAdClose->");
                    }
                });
                bannerView.addView(adView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initInterstitialAD(final Activity activity , boolean isAutoShow) {

        interAd = new InterstitialAd(activity, AD_BAIDU_RELEASE_DTS_CP_KEY);
        interAd.setListener(new InterstitialAdListener() {
            @Override
            public void onAdReady() {
                logD("BD_CP_onAdReady->");
                ToastUtils.showShort("onAdReady");
//                interAd.showAd(activity);
            }

            @Override
            public void onAdPresent() {
                ToastUtils.showShort("onAdPresent");
                logD("BD_CP_onAdPresent->");
            }

            @Override
            public void onAdClick(InterstitialAd interstitialAd) {
                logD("BD_CP_onAdClick->");
            }

            @Override
            public void onAdDismissed() {
                logD("BD_CP_onAdDismissed->");
                interAd.loadAd();
            }

            @Override
            public void onAdFailed(String s) {
                logD("BD_CP_onAdFailed->" + s);
//                interAd.loadAd();
            }
        });

        interAd.loadAd();

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 5000);
    }

    @Override
    public void loadInterstitialAd(Activity activity) {
        if (interAd.isAdReady()) {
            ToastUtils.showShort("Ad is Ready");
            interAd.showAd(activity);
        } else {
            ToastUtils.showShort("Ad not Ready");
            interAd.loadAd();
        }
    }

    @Override
    public void initRewardVideoAd(Context context, ZKRewardListener rewardListener) {
        XAdManager.getInstance(context).setAppSid(AD_BAIDU_APP_ID);
        rewardVideoAd = getBadiDuRewardedAd(context, rewardListener);
    }

    public RewardVideoAd getBadiDuRewardedAd(Context context, ZKRewardListener rewardListener) {
        String adUnitId = AD_BAIDU_RELEASE_DTS_JL_KEY;

//        if (!BuildConfig.DEBUG)
//            adUnitId = AD_GOOGLE_RELEASE_GL_DTS_JL_KEY;

        return createBDAndLoadRewardedAd(adUnitId, context, rewardListener);
    }

    private RewardVideoAd createBDAndLoadRewardedAd(String adUnitId, Context context, final ZKRewardListener rewardListener) {

        rewardVideoAd = new RewardVideoAd(context, adUnitId, new RewardVideoAd.RewardVideoAdListener() {
            @Override
            public void onAdShow() {
                logD("BD—JL->onAdShow:");
            }

            @Override
            public void onAdClick() {
                logD("BD—JL->onAdClick:");
            }

            @Override
            public void onAdClose(float v) {
                logD("BD—JL->onAdClose: " + v);
                rewardVideoAd.load();
            }

            @Override
            public void onAdFailed(String s) {
                logD("BD—JL->onAdFailed:" + s);
                rewardVideoAd.load();
            }

            @Override
            public void onVideoDownloadSuccess() {
                logD("BD—JL->onVideoDownloadSuccess:");
            }

            @Override
            public void onVideoDownloadFailed() {
                logD("BD—JL->onVideoDownloadFailed:");
            }

            @Override
            public void playCompletion() {
                logD("BD—JL->playCompletion:");
                if (rewardListener != null) {
                    rewardListener.playCompletion();
                }
//                ZKSP.reset();
            }
        });

        rewardVideoAd.load();
        return rewardVideoAd;
    }

    @Override
    public void loadRewardVideoAd() {
        if (rewardVideoAd.isReady()) {
            rewardVideoAd.show();
        } else {
            rewardVideoAd.load();
            ToastUtils.showShort("奖励视频飞了，倒数5秒，再来一次");
        }
    }

    @Override
    public void initSplashAD(Activity activity, TextView skipView, ViewGroup splashContainer, final ZKSplashListener zkSplashListener) {
        // 设置视频广告最大缓存占用空间(15MB~100MB)，单位 MB
        SplashAd.setMaxVideoCacheCapacityMb(30);
//        RelativeLayout adsParent = (RelativeLayout) this.findViewById(R.id.splash_page);

        SplashAdListener listener = new SplashAdListener() {
            @Override
            public void onAdDismissed() {
                logD("BD_KP_onAdDismissed");
                if (zkSplashListener != null) {
                    zkSplashListener.onADDismissed();
                }
//                handler.sendEmptyMessageDelayed(FLAG_ENTER_MAIN, 0);
            }

            @Override
            public void onAdFailed(String arg0) {
                logD("BD_KP_onAdFailed:" + arg0);
                if (zkSplashListener != null) {
                    zkSplashListener.onNoAD();
                }
//                handler.sendEmptyMessageDelayed(FLAG_ENTER_MAIN, 0);
            }

            @Override
            public void onAdPresent() {
                logD("BD_KP_onAdPresent");
                if (zkSplashListener != null) {
                    zkSplashListener.onADPresent();
                }
            }

            @Override
            public void onAdClick() {
                logD("BD_KP_onAdClick");
                //设置开屏可接受点击时，该回调可用
            }
        };
        new SplashAd(activity, splashContainer, listener, AD_BAIDU_RELEASE_DTS_KP_KEY, true);
    }

    @Override
    public void initNativeExpressAD(Context context, int ad_count, ZKNativeListener zkNativeListener) {

    }

    //统计事件
    private void event(String event) {
        UMUtils.event(event);
    }


    public void logD(String msg) {
        Log.d(TAG, msg);
    }

}
