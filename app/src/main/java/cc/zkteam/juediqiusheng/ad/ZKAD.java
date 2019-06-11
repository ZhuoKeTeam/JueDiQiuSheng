package cc.zkteam.juediqiusheng.ad;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
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

import cc.zkteam.juediqiusheng.BuildConfig;
import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.utils.ZKSP;

import static cc.zkteam.juediqiusheng.ad.UMUtils.*;

public class ZKAD {

    private static final String TAG = "ZKAD";

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

    //
    public static final String AD_TOUTIAO_TEST_APP_ID = "5018599";

    private static Application application;
    private static com.facebook.ads.AdView fbAdView;

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

        toutiaoADInit();
    }

    private static void toutiaoADInit() {
        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        TTAdSdk.init(application,
                new TTAdConfig.Builder()
                        .appId(AD_TOUTIAO_TEST_APP_ID)
                        .useTextureView(false) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                        .appName("大逃杀游戏攻略")
                        .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                        .allowShowNotify(true) //是否允许sdk展示通知栏提示
                        .allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                        .debug(true) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                        .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_3G) //允许直接下载的网络状态集合
                        .supportMultiProcess(false) //是否支持多进程，true支持
                        .build());

    }

    public static View initADView() {
//        return initFacebookADView();
        return initGoogleADView();
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

    private static void event(String event) {
        UMUtils.event(event);
    }

    public static void destory() {
        if (fbAdView != null)
            fbAdView.destroy();
    }


    /**
     * ---------------------------------------------google 激励广告-----------------------------------------------------------
     */
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

    private static RewardedAd rewardedAD;

    /**
     * ---------------------------------------------头条激励广告-----------------------------------------------------------
     */

    private static TTAdNative mTTAdNative;
    private static TTRewardVideoAd mttRewardVideoAd;
    private static boolean mHasShowDownloadActive = false;

    public static void showTouTiaoJLAD(String codeId, Activity activity) {
        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdSdk.getAdManager();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        ttAdManager.requestPermissionIfNecessary(activity);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(activity);
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .setRewardName("Test") //奖励的名称
                .setRewardAmount(3)  //奖励的数量
                .setUserID("user123")//用户id,必传参数
                .setMediaExtra("media_extra") //附加参数，可选
                .setOrientation(TTAdConstant.VERTICAL) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();
        //step5:请求广告
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                logD("showTouTiaoJLAD onError -> " + message);
                logD("showTouTiaoJLAD onError -> " + code);
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                logD("showTouTiaoJLAD rewardVideoAd video cached");
                mttRewardVideoAd.showRewardVideoAd(activity);
                mttRewardVideoAd = null;
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                logD("showTouTiaoJLAD rewardVideoAd loadedd");
                mttRewardVideoAd = ad;
//                mttRewardVideoAd.setShowDownLoadBar(false);
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        logD("showTouTiaoJLAD rewardVideoAd show");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        logD("showTouTiaoJLAD rewardVideoAd bar click");
                    }

                    @Override
                    public void onAdClose() {
                        logD("showTouTiaoJLAD rewardVideoAd close");
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        logD("showTouTiaoJLAD rewardVideoAd complete");
                    }

                    @Override
                    public void onVideoError() {
                        logD("showTouTiaoJLAD rewardVideoAd error");
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        logD("showTouTiaoJLAD onRewardVerify -> " + "verify:" + rewardVerify + " amount:" + rewardAmount +
                                " name:" + rewardName);
                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        mHasShowDownloadActive = false;
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        if (!mHasShowDownloadActive) {
                            mHasShowDownloadActive = true;
                            logD("showTouTiaoJLAD 下载中，点击下载区域暂停");
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        logD("showTouTiaoJLAD 下载暂停，点击下载区域继续");
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        logD("showTouTiaoJLAD 下载失败，点击下载区域重新下载");
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        logD("showTouTiaoJLAD 下载完成，点击下载区域重新下载");
                        mttRewardVideoAd.showRewardVideoAd(activity);
                        mttRewardVideoAd = null;
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        logD("安装完成，点击下载区域打开");
                    }
                });
            }
        });
    }
}
