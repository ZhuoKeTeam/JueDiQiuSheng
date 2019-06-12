package cc.zkteam.juediqiusheng.ad;

import android.app.Activity;
import android.app.Application;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;

import java.util.Date;
import java.util.Locale;

import cc.zkteam.juediqiusheng.BuildConfig;
import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.utils.ZKSP;

import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_FB_HF_AD_ADD;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_FB_HF_AD_CLICKED;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_FB_HF_AD_ERROR;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_FB_HF_AD_LOADED;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_FB_HF_AD_LOGGING_IMPRESSION;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_CP_AD_ADD;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_CP_AD_CLOSED;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_CP_AD_LEFT_APPLICATION;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_CP_AD_LOAD_FAILED;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_HF_AD_ADD;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_HF_AD_CLICKED;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_HF_AD_CLOSED;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_HF_AD_FAILED_TO_LOAD;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_HF_AD_IMPRESSION;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_HF_AD_LEFT_APPLICATION;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_HF_AD_LOADED;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_HF_AD_OPENED;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_JL_AD_ADD;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_JL_AD_CLOSED;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_JL_AD_EARNED_JL;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_JL_AD_LOADED;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_JL_AD_LOADED_FAILED;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_JL_AD_OPENED;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_GG_JL_AD_SHOW_FAILED;

public class ZKAD {

    private static final String TAG = "ZKAD";


    // 腾讯 APP ID
    public static final String AD_TENCENT_APP_ID = "1109306826";
    //腾讯 tencentBanner 2.0
    public static final String AD_TENCENT_BANNER_KEY = "4060363860868383";
    //腾讯 激励视频
    public static final String AD_TENCENT_REWARD_KEY = "9070065724358455";
    //腾讯 开屏广告
    public static final String AD_TENCENT_SPLASH_KEY = "7070864870360301";
    //腾讯 插屏 2.0
    public static final String AD_TENCENT_INTERSTITIAL_KEY = "9050361810766342";


    // Appid
    public static final String AD_GOOGLE_APP_ID = "ca-app-pub-5576379109949376~6821793256";
    // google 测试广告
    public static final String AD_GOOGLE_TEST_KEY = "ca-app-pub-3940256099942544/6300978111";
    // 横幅广告
    public static final String AD_GOOGLE_RELEASE_DTS_GL_HF_KEY = "ca-app-pub-5576379109949376/8466047413";
    // 激励广告
    public static final String AD_GOOGLE_RELEASE_GL_DTS_JL_KEY = "ca-app-pub-5576379109949376/9427296362";
    public static final String AD_GOOGLE_TEST_GL_DTS_JL_KEY = "ca-app-pub-3940256099942544/5224354917";

    // facebook 的横幅广告
    public static final String AD_FACEBOOK_RELEASE_GL_DTS_JL_KEY = "2457797387617458_2458180154245848";

    private static Application application;
    private static com.facebook.ads.AdView fbAdView;

    private static UnifiedBannerView tencentBanner;
    private static UnifiedInterstitialAD tencentInterstitialAD;

    // google 插屏广告，每个 Activity 唯一
    private static InterstitialAd mInterstitialAd;

    public static void init(Application appContext) {
        application = appContext;
        // google 广告
        MobileAds.initialize(appContext, ZKAD.AD_GOOGLE_APP_ID);
        // init google 插页广告
//        mInterstitialAd = new InterstitialAd(appContext);
//        if (BuildConfig.DEBUG) {
//            mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//        }  暂时去掉插屏广告

//        // facebook 广告
//        AudienceNetworkAds.initialize(appContext);
    }

    public static View initADView() {
//        return initFacebookADView();
//        return initGoogleADView();
        return initADView(null);
    }

    public static View initADView(Activity activity) {
        return initTencentBannerADView(activity);
//        return initFacebookADView();
//        return initGoogleADView();
    }

    public static View initFacebookADView() {
        try {
            fbAdView = new com.facebook.ads.AdView(application,
                    AD_FACEBOOK_RELEASE_GL_DTS_JL_KEY, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            fbAdView.setAdListener(new AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {
                    event(EVENT_FB_HF_AD_ERROR);
                    logD(EVENT_FB_HF_AD_ERROR + "—>adError Msg=" + adError.getErrorMessage() + ", code=" + adError.getErrorCode());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    event(EVENT_FB_HF_AD_LOADED);
                    logD(EVENT_FB_HF_AD_LOADED + "—>onAdLoaded: " + ad.getPlacementId());
                }

                @Override
                public void onAdClicked(Ad ad) {
                    event(EVENT_FB_HF_AD_CLICKED);
                    logD(EVENT_FB_HF_AD_CLICKED + "—>onAdClicked: " + ad.getPlacementId());
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    event(EVENT_FB_HF_AD_LOGGING_IMPRESSION);
                    logD(EVENT_FB_HF_AD_LOGGING_IMPRESSION + "—>onLoggingImpression: " + ad.getPlacementId());
                }
            });
            fbAdView.loadAd();
            return fbAdView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static View initTencentBannerADView(Activity activity) {
        if (activity == null)
            throw new RuntimeException("腾讯广告必须传入 Activity 的上下文，请使用：initADView(Activity activity)。");

        try {
            if (tencentBanner != null) {
                tencentBanner.destroy();
            }
            // 创建 Banner 2.0 广告 对象
            tencentBanner = new UnifiedBannerView(activity, AD_TENCENT_APP_ID, AD_TENCENT_BANNER_KEY, new UnifiedBannerADListener() {
                @Override
                public void onNoAD(com.qq.e.comm.util.AdError adError) {
                    Log.i(
                            "AD_DEMO",
                            String.format("Banner onNoAD，eCode = %d, eMsg = %s", adError.getErrorCode(),
                                    adError.getErrorMsg()));
                }

                @Override
                public void onADReceive() {
                    Log.i("AD_DEMO", "ONBannerReceive");
                }

                @Override
                public void onADExposure() {

                }

                @Override
                public void onADClosed() {

                }

                @Override
                public void onADClicked() {

                }

                @Override
                public void onADLeftApplication() {

                }

                @Override
                public void onADOpenOverlay() {

                }

                @Override
                public void onADCloseOverlay() {

                }
            });
//            tencentBanner.setRefresh(30);
            //发起广告请求，收到广告数据后会展示数据
            tencentBanner.loadAD();
            return tencentBanner;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void logD(String msg) {
        Log.d(TAG, msg);
    }

    public static View initGoogleADView() {
        try {
            AdView adView = new AdView(Utils.getApp());
            adView.setAdSize(AdSize.SMART_BANNER);
            if (BuildConfig.DEBUG) {
                adView.setAdUnitId(AD_GOOGLE_TEST_KEY);
            } else {
                adView.setAdUnitId(AD_GOOGLE_RELEASE_DTS_GL_HF_KEY);
            }

            adView.setAdListener(new com.google.android.gms.ads.AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    event(EVENT_GG_HF_AD_CLOSED);
                    logD(EVENT_GG_HF_AD_CLOSED + "—>onAdClosed");
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    event(EVENT_GG_HF_AD_FAILED_TO_LOAD);
                    logD(EVENT_GG_HF_AD_FAILED_TO_LOAD + "—>onAdFailedToLoad");
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                    event(EVENT_GG_HF_AD_LEFT_APPLICATION);
                    logD(EVENT_GG_HF_AD_LEFT_APPLICATION + "—>onAdLeftApplication");
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                    event(EVENT_GG_HF_AD_OPENED);
                    logD(EVENT_GG_HF_AD_OPENED + "—>onAdOpened");
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    event(EVENT_GG_HF_AD_LOADED);
                    logD(EVENT_GG_HF_AD_LOADED + "—>onAdLoaded");
                }

                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                    event(EVENT_GG_HF_AD_CLICKED);
                    logD(EVENT_GG_HF_AD_CLICKED + "—>onAdClicked");
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                    event(EVENT_GG_HF_AD_IMPRESSION);
                    logD(EVENT_GG_HF_AD_IMPRESSION + "—>onAdImpression");
                }
            });

            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            return adView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void initHFAD(Activity activity) {
        initHFAD(activity, false);
    }

    public static void initHFAD(Activity activity, boolean isFacebookAd) {
        View view = activity.getWindow().getDecorView().getRootView();
        initHFAD(view, isFacebookAd);
    }

    //    public static void initHFAD(View rootView, Activity activity) {
    public static void initHFAD(View rootView, boolean isFacebookAd) {
        try {
            LinearLayout adContentView = rootView.findViewById(R.id.ad_content_view);
            if (adContentView != null) {
                if (isFacebookAd) {
                    event(EVENT_FB_HF_AD_ADD);
                    adContentView.addView(ZKAD.initFacebookADView());
                } else {
                    event(EVENT_GG_HF_AD_ADD);
                    adContentView.addView(ZKAD.initGoogleADView());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initTencentBannerAD(View rootView, Activity activity) {
        try {
            LinearLayout adContentView = rootView.findViewById(R.id.ad_content_view);
            if (adContentView != null) {
                adContentView.removeAllViews();
                adContentView.addView(ZKAD.initTencentBannerADView(activity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 插屏广告 2.0
     *
     * @param activity
     */
    public static void initTencentInterstitialAD(Activity activity) {
        try {
            if (tencentInterstitialAD != null) {
                tencentInterstitialAD.close();
                tencentInterstitialAD.destroy();
                tencentInterstitialAD = null;
            }
            if (tencentInterstitialAD == null) {
                tencentInterstitialAD = new UnifiedInterstitialAD(activity, AD_TENCENT_APP_ID, AD_TENCENT_INTERSTITIAL_KEY, new UnifiedInterstitialADListener() {
                    @Override
                    public void onADReceive() {
                        Toast.makeText(activity, "广告加载成功 ！ ", Toast.LENGTH_LONG).show();
                        if (tencentInterstitialAD != null) {
                            tencentInterstitialAD.show();
                        } else {
                            Toast.makeText(activity, "请加载广告后再进行展示 ！ ", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onNoAD(com.qq.e.comm.util.AdError error) {
                        String msg = String.format(Locale.getDefault(), "onNoAD, error code: %d, error msg: %s",
                                error.getErrorCode(), error.getErrorMsg());
                        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onADOpened() {
                        Log.i(TAG, "onADOpened");
                    }

                    @Override
                    public void onADExposure() {
                        Log.i(TAG, "onADExposure");
                    }

                    @Override
                    public void onADClicked() {
                        Log.i(TAG, "onADClicked : " + (tencentInterstitialAD.getExt() != null ? tencentInterstitialAD.getExt().get("clickUrl") : ""));
                    }

                    @Override
                    public void onADLeftApplication() {
                        Log.i(TAG, "onADLeftApplication");
                    }

                    @Override
                    public void onADClosed() {
                        Log.i(TAG, "onADClosed");
                    }
                });
                tencentInterstitialAD.loadAD();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //**************************** 腾讯激励视频 *****************************
    private static RewardVideoAD rewardVideoAD;
    private static boolean adLoaded;//广告加载成功标志
    private static boolean videoCached;//视频素材文件下载完成标志

    public static void initTencentRewardVideoAd() {
        // 1. 初始化激励视频广告
        rewardVideoAD = new RewardVideoAD(application, AD_TENCENT_APP_ID, AD_TENCENT_REWARD_KEY, new RewardVideoADListener() {
            /**
             * 广告加载成功，可在此回调后进行广告展示
             **/
            @Override
            public void onADLoad() {
                adLoaded = true;
                String msg = "load ad success ! expireTime = " + new Date(System.currentTimeMillis() +
                        rewardVideoAD.getExpireTimestamp() - SystemClock.elapsedRealtime());
                Toast.makeText(application, msg, Toast.LENGTH_LONG).show();
                showTencentRewardVideoAd();
            }

            /**
             * 视频素材缓存成功，可在此回调后进行广告展示
             */
            @Override
            public void onVideoCached() {
                videoCached = true;
                Log.i(TAG, "onVideoCached");
            }

            /**
             * 激励视频广告页面展示
             */
            @Override
            public void onADShow() {
                Log.i(TAG, "onADShow");
            }

            /**
             * 激励视频广告曝光
             */
            @Override
            public void onADExpose() {
                Log.i(TAG, "onADExpose");
            }

            /**
             * 激励视频触发激励（观看视频大于一定时长或者视频播放完毕）
             */
            @Override
            public void onReward() {
                Log.i(TAG, "onReward");
            }

            /**
             * 激励视频广告被点击
             */
            @Override
            public void onADClick() {
                Log.i(TAG, "onADClick");
            }

            /**
             * 激励视频播放完毕
             */
            @Override
            public void onVideoComplete() {
                Log.i(TAG, "onVideoComplete");
            }

            /**
             * 激励视频广告被关闭
             */
            @Override
            public void onADClose() {
                Log.i(TAG, "onADClose");
            }

            /**
             * 广告流程出错
             */
            @Override
            public void onError(com.qq.e.comm.util.AdError adError) {
                String msg = String.format(Locale.getDefault(), "onError, error code: %d, error msg: %s",
                        adError.getErrorCode(), adError.getErrorMsg());
                Toast.makeText(application, msg, Toast.LENGTH_LONG).show();
            }
        });
        adLoaded = false;
        videoCached = false;
        // 2. 加载激励视频广告
//        rewardVideoAD.loadAD();
    }

    public static void loadTencentRewardVideoAd() {
        // 2. 加载激励视频广告
        if (rewardVideoAD != null) {
            rewardVideoAD.loadAD();
        }
    }

    private static void showTencentRewardVideoAd() {
        // 3. 展示激励视频广告
        //广告展示检查1：广告成功加载，此处也可以使用videoCached来实现视频预加载完成后再展示激励视频广告的逻辑
        if (adLoaded && rewardVideoAD != null) {
            //广告展示检查2：当前广告数据还没有展示过
            if (!rewardVideoAD.hasShown()) {
                //建议给广告过期时间加个buffer，单位ms，这里demo采用1000ms的buffer
                long delta = 1000;
                //广告展示检查3：展示广告前判断广告数据未过期
                if (SystemClock.elapsedRealtime() < (rewardVideoAD.getExpireTimestamp() - delta)) {
                    rewardVideoAD.showAD();
                } else {
                    Toast.makeText(application, "激励视频广告已过期，请再次请求广告后进行广告展示！", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(application, "此条广告已经展示过，请再次请求广告后进行广告展示！", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(application, "成功加载广告后再进行广告展示！", Toast.LENGTH_LONG).show();
        }
    }

    private static void event(String event) {
        UMUtils.event(event);
    }

    public static void destory() {
        if (fbAdView != null) {
            fbAdView.destroy();
        }
//        if (tencentBanner != null) {
//            tencentBanner.destroy();
//        }
//        if (tencentInterstitialAD != null) {
//            tencentInterstitialAD.destroy();
//        }
    }


    /**
     * ---------------------------------------------google 激励广告-----------------------------------------------------------
     */

    private static RewardedAd rewardedAD;

    public static void initGoogleRewardedAd() {
        rewardedAD = getGoogleRewardedAd();
    }

    public static RewardedAd getCurrentRewardedAd() {
        return rewardedAD;
    }

    /**
     * 获取 google 激励广告
     *
     * @return 返回激励广告
     */
    public static RewardedAd getGoogleRewardedAd() {
        String adUnitId;

        if (BuildConfig.DEBUG)
            adUnitId = AD_GOOGLE_TEST_GL_DTS_JL_KEY;
        else
            adUnitId = AD_GOOGLE_RELEASE_GL_DTS_JL_KEY;

        return createAndLoadRewardedAd(adUnitId);
    }

    private static RewardedAd createAndLoadRewardedAd(String adUnitId) {
        RewardedAd rewardedAd = new RewardedAd(application, adUnitId);
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                event(EVENT_GG_JL_AD_LOADED);
                logD(EVENT_GG_JL_AD_LOADED + "—>onRewardedAdLoaded");
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
                event(EVENT_GG_JL_AD_LOADED_FAILED);
                logD(EVENT_GG_JL_AD_LOADED_FAILED + "—>onRewardedAdFailedToLoad， errorCode==" + errorCode);
            }
        };
        event(EVENT_GG_JL_AD_ADD);
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }

    /**
     * 展示 google 激励广告
     *
     * @param activity   Activity
     * @param rewardedAd 激励广告
     */
    public static void showGoogleJLAD(Activity activity, RewardedAd rewardedAd) {
        if (rewardedAd.isLoaded()) {
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                public void onRewardedAdOpened() {
                    // Ad opened.
                    event(EVENT_GG_JL_AD_OPENED);
                    logD(EVENT_GG_JL_AD_OPENED + "—>onRewardedAdOpened");
                }

                public void onRewardedAdClosed() {
                    // Ad closed.
                    rewardedAD = getGoogleRewardedAd();
                    event(EVENT_GG_JL_AD_CLOSED);
                    logD(EVENT_GG_JL_AD_CLOSED + "—>onRewardedAdClosed");
                }

                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    // User earned reward.
                    ZKSP.reset();
                    rewardedAD = getGoogleRewardedAd();

                    int amount = reward.getAmount();
                    String type = reward.getType();

                    event(EVENT_GG_JL_AD_EARNED_JL);
                    logD(EVENT_GG_JL_AD_EARNED_JL + "—>onRewardedAdLoaded, amount==" + amount + ", type==" + type);
                }

                public void onRewardedAdFailedToShow(int errorCode) {
                    // Ad failed to display
                    event(EVENT_GG_JL_AD_SHOW_FAILED);
                    logD(EVENT_GG_JL_AD_SHOW_FAILED + "—>onRewardedAdFailedToShow， errorCode==" + errorCode);
                }
            };
            rewardedAd.show(activity, adCallback);
        } else {
            ToastUtils.showShort("奖励视频飞了，倒数5秒，再来一次");
        }
    }

    // load google 插屏广告
    public static void loadGoogleCPAD() {
        mInterstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                event(EVENT_GG_CP_AD_CLOSED);
                logD(EVENT_GG_CP_AD_CLOSED + "—>onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                event(EVENT_GG_CP_AD_LOAD_FAILED);
                logD(EVENT_GG_CP_AD_LOAD_FAILED + "—>onAdFailedToLoad， errorCode==" + i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                event(EVENT_GG_CP_AD_LEFT_APPLICATION);
                logD(EVENT_GG_CP_AD_LEFT_APPLICATION + "—>onAdLeftApplication");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                logD(EVENT_GG_CP_AD_LEFT_APPLICATION + "—>onAdOpened");
                event(EVENT_GG_CP_AD_LEFT_APPLICATION);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                logD(EVENT_GG_CP_AD_LEFT_APPLICATION + "—>onAdLoaded");
                event(EVENT_GG_CP_AD_LEFT_APPLICATION);

            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                logD(EVENT_GG_CP_AD_LEFT_APPLICATION + "—>onAdClicked");
                event(EVENT_GG_CP_AD_LEFT_APPLICATION);

            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                logD(EVENT_GG_CP_AD_LEFT_APPLICATION + "—>onAdImpression");
                event(EVENT_GG_CP_AD_LEFT_APPLICATION);
            }
        });
        event(EVENT_GG_CP_AD_ADD);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    // show google 插屏广告
    public static void showGoogleCPAD() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            logD("插屏广告飞了: The interstitial wasn't loaded yet.");
            ToastUtils.showShort("插屏广告飞了");
        }
    }

}
