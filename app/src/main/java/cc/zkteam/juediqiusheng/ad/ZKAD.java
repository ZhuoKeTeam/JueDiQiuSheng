package cc.zkteam.juediqiusheng.ad;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.mobad.video.XAdManager;
import com.baidu.mobads.AdViewListener;
import com.baidu.mobads.InterstitialAd;
import com.baidu.mobads.InterstitialAdListener;
import com.baidu.mobads.rewardvideo.RewardVideoAd;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import org.json.JSONObject;

import cc.zkteam.juediqiusheng.BuildConfig;
import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.utils.ZKSP;

import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_FB_HF_AD_CLICKED;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_FB_HF_AD_ERROR;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_FB_HF_AD_LOADED;
import static cc.zkteam.juediqiusheng.ad.UMUtils.EVENT_FB_HF_AD_LOGGING_IMPRESSION;
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

    // Appid_baidu
    public static final String AD_BAIDU_APP_ID = "e64ccf7d";
    // 百度横幅广告ID
    public static final String AD_BAIDU_RELEASE_DTS_HF_KEY = "6288996";
    // 百度激励广告ID
    public static final String AD_BAIDU_RELEASE_DTS_JL_KEY = "6288997";
    // 开屏广告
    public static final String AD_BAIDU_RELEASE_DTS_KP_KEY = "6289086";
    // 插屏广告
    public static final String AD_BAIDU_RELEASE_DTS_CP_KEY = "6289360";

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

    public static void init(Application appContext) {
        application = appContext;

        com.baidu.mobads.AdView.setAppSid(appContext, AD_BAIDU_APP_ID);
//        AppActivity.setActionBarColorTheme(AppActivity.ActionBarColorTheme.ACTION_BAR_GREEN_THEME);
        // 设置广告请求是否使用 https 协议
        // AdSettings.setSupportHttps(true);

//        // google 广告
//        MobileAds.initialize(appContext, ZKAD.AD_GOOGLE_APP_ID);
//        // facebook 广告
//        AudienceNetworkAds.initialize(appContext);
    }

    public static View initADView() {
        return initBaiduADView();
//        return initFacebookADView();
//        return initGoogleADView();
    }

    public static View initBaiduADView() {
        com.baidu.mobads.AdView adView = new com.baidu.mobads.AdView(application, AD_BAIDU_RELEASE_DTS_HF_KEY);
        adView.setListener(new AdViewListener() {
            @Override
            public void onAdReady(com.baidu.mobads.AdView adView) {
                logD("BD->onAdReady");
            }

            @Override
            public void onAdShow(JSONObject jsonObject) {
                logD("BD->onAdShow");
            }

            @Override
            public void onAdClick(JSONObject jsonObject) {
                logD("BD->onAdClick");
            }

            @Override
            public void onAdFailed(String s) {
                logD("BD->onAdFailed:" + s);
            }

            @Override
            public void onAdSwitch() {
                logD("BD->onAdSwitch");
            }

            @Override
            public void onAdClose(JSONObject jsonObject) {
                logD("BD->onAdClose");
            }
        });
        return adView;

//把横幅 view 添加到自己的 viewGroup 组件中，必须写，才可以展示横幅广告
// RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(width, height);
//        rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM) your_original_layout.addView(addView,rllp);
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
                adContentView.addView(ZKAD.initBaiduADView());
//                if (isFacebookAd) {
//                    event(EVENT_FB_HF_AD_ADD);
//                    adContentView.addView(ZKAD.initFacebookADView());
//                } else {
//                    event(EVENT_GG_HF_AD_ADD);
//                    adContentView.addView(ZKAD.initGoogleADView());
//                }
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




    /**---------------------------------------------google 激励广告-----------------------------------------------------------*/

    private static RewardedAd rewardedAD;

    public static void initGoogleRewardedAd() {
        rewardedAD = getGoogleRewardedAd();
    }

    public static RewardedAd getCurrentRewardedAd() {
        return rewardedAD;
    }

    /**
     * 获取 google 激励广告
     * @return 返回激励广告
     */
    public static RewardedAd getGoogleRewardedAd() {
        String adUnitId = AD_GOOGLE_TEST_GL_DTS_JL_KEY;

        if (!BuildConfig.DEBUG)
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
     * @param activity      Activity
     * @param rewardedAd    激励广告
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

    /**---------------------------------------------BaiDu 激励广告-----------------------------------------------------------*/
    private static RewardVideoAd rewardVideoAd;

    public static void initBadiDuRewardVideoAd() {
        XAdManager.getInstance(application).setAppSid(ZKAD.AD_BAIDU_APP_ID);
        rewardVideoAd = getBadiDuRewardedAd();
    }

    public static RewardVideoAd getCurrentBDRewardedAd() {
        return rewardVideoAd;
    }

    public static RewardVideoAd getBadiDuRewardedAd() {
        String adUnitId = AD_BAIDU_RELEASE_DTS_JL_KEY;

//        if (!BuildConfig.DEBUG)
//            adUnitId = AD_GOOGLE_RELEASE_GL_DTS_JL_KEY;

        return createBDAndLoadRewardedAd(adUnitId);
    }

    private static RewardVideoAd createBDAndLoadRewardedAd(String adUnitId) {

        rewardVideoAd = new RewardVideoAd(application, adUnitId, new RewardVideoAd.RewardVideoAdListener() {
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
                ZKSP.reset();
            }
        });

        rewardVideoAd.load();
        return rewardVideoAd;
    }

    public static void showBaiDuJLAD(RewardVideoAd rewardVideoAd) {
        rewardVideoAd.show();
//        if (rewardVideoAd.isReady()) {
//            rewardVideoAd.show();
//        } else {
//            ToastUtils.showShort("奖励视频飞了，倒数5秒，再来一次");
//        }
    }


    // 插屏
    private static InterstitialAd cpAd;
    public static void showCPAD(Activity activity) {
        cpAd.showAd(activity);
    }

    public static void loadCPAD() {
        cpAd = new InterstitialAd(application, AD_BAIDU_RELEASE_DTS_CP_KEY);
        cpAd.setListener(new InterstitialAdListener() {

            @Override
            public void onAdReady() {
                logD("BD—CP->onAdReady:");
            }

            @Override
            public void onAdPresent() {
                logD("BD—CP->onAdPresent:");
            }

            @Override
            public void onAdClick(InterstitialAd interstitialAd) {
                logD("BD—CP->onAdClick:" + interstitialAd.isAdReady());
            }

            @Override
            public void onAdDismissed() {
                logD("BD—CP->onAdReady:");
                cpAd.loadAd();
            }

            @Override
            public void onAdFailed(String s) {
                logD("BD—CP->onAdReady:");
            }
        });
        cpAd.loadAd();
    }

}
