package com.bro.adlib.ad;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bro.adlib.strategy.ZKNativeListener;
import com.bro.adlib.strategy.ZKSplashListener;
import com.bro.adlib.strategy.ZKStrategy;
import com.bro.adlib.util.UMUtils;
import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.pi.AdData;
import com.qq.e.comm.util.AdError;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by zhangshan on 2019-06-17 15:56.
 */
public class ZKTencentAD implements ZKStrategy {

    private UnifiedBannerView tencentBanner;
    private UnifiedInterstitialAD tencentInterstitialAD;

    //**************************** 腾讯激励视频 *****************************
    private static RewardVideoAD rewardVideoAD;
    private static boolean adLoaded;//广告加载成功标志
    private static boolean videoCached;//视频素材文件下载完成标志


    // 腾讯 APP ID
    public static final String AD_TENCENT_APP_ID = "1109306826";
    //腾讯 Banner 2.0
    public static final String AD_TENCENT_BANNER_KEY = "4060363860868383";
    //腾讯 激励视频
    public static final String AD_TENCENT_REWARD_KEY = "9070065724358455";
    //腾讯 开屏广告
    public static final String AD_TENCENT_SPLASH_KEY = "7070864870360301";
    //腾讯 插屏 2.0
    public static final String AD_TENCENT_INTERSTITIAL_KEY = "9050361810766342";
    //腾讯 原生-左图右文
    public static final String AD_TENCENT_ORIGINAL_KEY = "3050764842146730";


    private static ZKTencentAD mSingleton = null;

    private ZKTencentAD() {
    }

    public static ZKTencentAD getInstance() {
        if (mSingleton == null) {
            synchronized (ZKTencentAD.class) {
                if (mSingleton == null) {
                    mSingleton = new ZKTencentAD();
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
                bannerView.addView(initTencentBannerADView(activity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View initTencentBannerADView(Activity activity) {
        if (activity == null)
            throw new RuntimeException("腾讯广告必须传入 Activity 的上下文，请使用：initADView(Activity activity)。");

        try {
            if (tencentBanner != null) {
                tencentBanner.destroy();
            }
            // 创建 Banner 2.0 广告 对象
            tencentBanner = new UnifiedBannerView(activity, AD_TENCENT_APP_ID, AD_TENCENT_BANNER_KEY, new UnifiedBannerADListener() {
                @Override
                public void onNoAD(AdError adError) {
                    Log.i("ad_tencent_banner",
                            String.format("Banner onNoAD，eCode = %d, eMsg = %s", adError.getErrorCode(),
                                    adError.getErrorMsg()));
                    UMUtils.event(UMUtils.EVENT_TENCENT_BANNER_NOAD);
                }

                @Override
                public void onADReceive() {
                    Log.i("ad_tencent_banner", "ONBannerReceive");
                    event(UMUtils.EVENT_TENCENT_BANNER_ADRECEIVE);
                }

                @Override
                public void onADExposure() {
                    Log.i("_ad_tencent_banner", "ONBannerReceive");
                    event(UMUtils.EVENT_TENCENT_BANNER_ADEXPOSURE);
                }

                @Override
                public void onADClosed() {
                    Log.i("_ad_tencent_banner", "ONBannerReceive");
                    event(UMUtils.EVENT_TENCENT_BANNER_ADCLOSED);
                }

                @Override
                public void onADClicked() {
                    Log.i("_ad_tencent_banner", "ONBannerReceive");
                    event(UMUtils.EVENT_TENCENT_BANNER_ADCLICKED);
                }

                @Override
                public void onADLeftApplication() {
                    Log.i("_ad_tencent_banner", "ONBannerReceive");
                    event(UMUtils.EVENT_TENCENT_BANNER_ADLEFTAPPLICATION);
                }

                @Override
                public void onADOpenOverlay() {
                    Log.i("_ad_tencent_banner", "ONBannerReceive");
                    event(UMUtils.EVENT_TENCENT_BANNER_ADOPENOVERLAY);
                }

                @Override
                public void onADCloseOverlay() {
                    Log.i("_ad_tencent_banner", "ONBannerReceive");
                    event(UMUtils.EVENT_TENCENT_BANNER_ADCLOSEOVERLAY);
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

    @Override
    public void initInterstitialAD(final Activity activity) {
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
                        Log.i("ad_tencent_incert", "onADReceive");
                        UMUtils.event(UMUtils.EVENT_TENCENT_INCERT_ADRECEIVE);
                        Toast.makeText(activity, "广告加载成功 ！ ", Toast.LENGTH_LONG).show();
                        if (tencentInterstitialAD != null) {
                            tencentInterstitialAD.show();
                        } else {
                            Toast.makeText(activity, "请加载广告后再进行展示 ！ ", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onNoAD(AdError error) {
                        Log.i("ad_tencent_incert", "onNoAD");
                        UMUtils.event(UMUtils.EVENT_TENCENT_INCERT_NOAD);
                        String msg = String.format(Locale.getDefault(), "onNoAD, error code: %d, error msg: %s",
                                error.getErrorCode(), error.getErrorMsg());
                        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onADOpened() {
                        Log.i("ad_tencent_incert", "onADOpened");
                        UMUtils.event(UMUtils.EVENT_TENCENT_INCERT_ADOPENED);
                    }

                    @Override
                    public void onADExposure() {
                        Log.i("ad_tencent_incert", "onADExposure");
                        UMUtils.event(UMUtils.EVENT_TENCENT_INCERT_ADEXPOSURE);
                    }

                    @Override
                    public void onADClicked() {
                        UMUtils.event(UMUtils.EVENT_TENCENT_INCERT_ADCLICKED);
                        Log.i("ad_tencent_incert", "onADClicked : " + (tencentInterstitialAD.getExt() != null ? tencentInterstitialAD.getExt().get("clickUrl") : ""));
                    }

                    @Override
                    public void onADLeftApplication() {
                        UMUtils.event(UMUtils.EVENT_TENCENT_INCERT_ADLEFTAPPLICATION);
                        Log.i("ad_tencent_incert", "onADLeftApplication");
                    }

                    @Override
                    public void onADClosed() {
                        UMUtils.event(UMUtils.EVENT_TENCENT_INCERT_ADCLOSED);
                        Log.i("ad_tencent_incert", "onADClosed");
                    }
                });
                tencentInterstitialAD.loadAD();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initRewardVideoAd(final Context context) {
        // 1. 初始化激励视频广告
        rewardVideoAD = new RewardVideoAD(context, AD_TENCENT_APP_ID, AD_TENCENT_REWARD_KEY, new RewardVideoADListener() {
            /**
             * 广告加载成功，可在此回调后进行广告展示
             **/
            @Override
            public void onADLoad() {
                adLoaded = true;
                String msg = "load ad success ! expireTime = " + new Date(System.currentTimeMillis() +
                        rewardVideoAD.getExpireTimestamp() - SystemClock.elapsedRealtime());
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                showTencentRewardVideoAd(context);
                Log.i("ad_tencent_reward", "onADLoad");
                UMUtils.event(UMUtils.EVENT_TENCENT_REWARD_ADLOAD);
            }

            /**
             * 视频素材缓存成功，可在此回调后进行广告展示
             */
            @Override
            public void onVideoCached() {
                videoCached = true;
                Log.i("ad_tencent_reward", "onVideoCached");
                UMUtils.event(UMUtils.EVENT_TENCENT_REWARD_VIDEOCACHED);
            }

            /**
             * 激励视频广告页面展示
             */
            @Override
            public void onADShow() {
                Log.i("ad_tencent_reward", "onADShow");
                UMUtils.event(UMUtils.EVENT_TENCENT_REWARD_ADSHOW);
            }

            /**
             * 激励视频广告曝光
             */
            @Override
            public void onADExpose() {
                Log.i("ad_tencent_reward", "onADExpose");
                UMUtils.event(UMUtils.EVENT_TENCENT_REWARD_ADEXPOSE);
            }

            /**
             * 激励视频触发激励（观看视频大于一定时长或者视频播放完毕）
             */
            @Override
            public void onReward() {
                Log.i("ad_tencent_reward", "onReward");
                UMUtils.event(UMUtils.EVENT_TENCENT_REWARD_REWARD);
            }

            /**
             * 激励视频广告被点击
             */
            @Override
            public void onADClick() {
                Log.i("ad_tencent_reward", "onADClick");
                UMUtils.event(UMUtils.EVENT_TENCENT_REWARD_ADCLICK);
            }

            /**
             * 激励视频播放完毕
             */
            @Override
            public void onVideoComplete() {
                Log.i("ad_tencent_reward", "onVideoComplete");
                UMUtils.event(UMUtils.EVENT_TENCENT_REWARD_VIDEOCOMPLETE);
            }

            /**
             * 激励视频广告被关闭
             */
            @Override
            public void onADClose() {
                Log.i("ad_tencent_reward", "onADClose");
                UMUtils.event(UMUtils.EVENT_TENCENT_REWARD_ADCLOSE);
            }

            /**
             * 广告流程出错
             */
            @Override
            public void onError(AdError adError) {
                String msg = String.format(Locale.getDefault(), "onError, error code: %d, error msg: %s",
                        adError.getErrorCode(), adError.getErrorMsg());
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                Log.i("ad_tencent_reward", "onError");
                UMUtils.event(UMUtils.EVENT_TENCENT_REWARD_ERROR);
            }
        });
        adLoaded = false;
        videoCached = false;
        // 2. 加载激励视频广告
//        rewardVideoAD.loadAD();
    }

    private void showTencentRewardVideoAd(Context context) {
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
                    Toast.makeText(context, "激励视频广告已过期，请再次请求广告后进行广告展示！", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, "此条广告已经展示过，请再次请求广告后进行广告展示！", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "成功加载广告后再进行广告展示！", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void loadRewardVideoAd() {
        // 2. 加载激励视频广告
        if (rewardVideoAD != null) {
            rewardVideoAD.loadAD();
        }
    }

    @Override
    public void initSplashAD(Activity activity, TextView skipView, FrameLayout splashContainer, ZKSplashListener zkSplashListener) {
        permissionCheck(activity, skipView, splashContainer, zkSplashListener);
    }


    private NativeExpressAD mADManager;
    public int AD_COUNT = 1;    // 加载广告的条数，取值范围为[1, 10]
    private final String TAG = ZKTencentAD.class.getSimpleName();

    /**
     * @param ad_count 加载原生广告
     */
    @Override
    public void initNativeExpressAD(final Context context, int ad_count, final ZKNativeListener zkNativeListener) {
        if (ad_count != -1) {
            AD_COUNT = ad_count;
        }
        ADSize adSize = new ADSize(ADSize.FULL_WIDTH, ADSize.AUTO_HEIGHT); // 消息流中用AUTO_HEIGHT
        mADManager = new NativeExpressAD(context, adSize, AD_TENCENT_APP_ID, AD_TENCENT_ORIGINAL_KEY, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onADLoaded(List<NativeExpressADView> adList) {
                Log.i("ad_tencent_native", "onADLoaded: " + adList.size());
                UMUtils.event(UMUtils.EVENT_TENCENT_NATIVE_ADLOADED);
                if (zkNativeListener != null) {
                    zkNativeListener.onNativeCallBack(adList);
                }
//                mAdViewList = adList;
//                for (int i = 0; i < mAdViewList.size(); i++) {
//                    int position = FIRST_AD_POSITION + ITEMS_PER_AD * i;
//                    if (position < mNormalDataList.size()) {
//                        NativeExpressADView view = mAdViewList.get(i);
//                        GDTLogger.i("ad load[" + i + "]: " + getAdInfo(view));
////                        if (view.getBoundData().getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
////                            view.setMediaListener(mediaListener);
////                        }
//                        mAdViewPositionMap.put(view, position); // 把每个广告在列表中位置记录下来
////                        hotNewsItemAdapter.addADViewToPosition(position, mAdViewList.get(i));
//                    }
//                }
//                hotNewsItemAdapter.notifyDataSetChanged();
            }


            @Override
            public void onRenderFail(NativeExpressADView adView) {
                UMUtils.event(UMUtils.EVENT_TENCENT_NATIVE_RENDERFAIL);
                Log.i("ad_tencent_native", "onRenderFail: " + adView.toString());
            }

            @Override
            public void onRenderSuccess(NativeExpressADView adView) {
                UMUtils.event(UMUtils.EVENT_TENCENT_NATIVE_RENDERSUCCESS);
                Log.i("ad_tencent_native", "onRenderSuccess: " + adView.toString() + ", adInfo: " + getAdInfo(adView));
            }

            @Override
            public void onADExposure(NativeExpressADView adView) {
                UMUtils.event(UMUtils.EVENT_TENCENT_NATIVE_ADEXPOSURE);
                Log.i("ad_tencent_native", "onADExposure: " + adView.toString());
            }

            @Override
            public void onADClicked(NativeExpressADView adView) {
                UMUtils.event(UMUtils.EVENT_TENCENT_NATIVE_ADCLICKED);
                Log.i("ad_tencent_native", "onADClicked: " + adView.toString());
            }

            @Override
            public void onADClosed(NativeExpressADView adView) {
                UMUtils.event(UMUtils.EVENT_TENCENT_NATIVE_ADCLOSED);
                Log.i("ad_tencent_native", "onADClosed: " + adView.toString());
//                if (hotNewsItemAdapter != null) {
//                    int removedPosition = mAdViewPositionMap.get(adView);
//                    hotNewsItemAdapter.removeADView(removedPosition, adView);
//                }
            }

            @Override
            public void onADLeftApplication(NativeExpressADView adView) {
                UMUtils.event(UMUtils.EVENT_TENCENT_NATIVE_ADLEFTAPPLICATION);
                Log.i("ad_tencent_native", "onADLeftApplication: " + adView.toString());
            }

            @Override
            public void onADOpenOverlay(NativeExpressADView adView) {
                UMUtils.event(UMUtils.EVENT_TENCENT_NATIVE_ADOPENOVERLAY);
                Log.i("ad_tencent_native", "onADOpenOverlay: " + adView.toString());
            }

            @Override
            public void onADCloseOverlay(NativeExpressADView adView) {
                UMUtils.event(UMUtils.EVENT_TENCENT_NATIVE_ADCLOSEOVERLAY);
                Log.i("ad_tencent_native", "onADCloseOverlay");
            }

            @Override
            public void onNoAD(AdError adError) {
                UMUtils.event(UMUtils.EVENT_TENCENT_NATIVE_NOAD);
                Log.i("ad_tencent_native", String.format("onNoAD, error code: %d, error msg: %s", adError.getErrorCode(),
                        adError.getErrorMsg()));
            }
        });
        mADManager.setMaxVideoDuration(30000);
        mADManager.loadAD(AD_COUNT);
    }

    private String getAdInfo(NativeExpressADView nativeExpressADView) {
        AdData adData = nativeExpressADView.getBoundData();
        if (adData != null) {
            StringBuilder infoBuilder = new StringBuilder();
            infoBuilder.append("title:").append(adData.getTitle()).append(",")
                    .append("desc:").append(adData.getDesc()).append(",")
                    .append("patternType:").append(adData.getAdPatternType());
            if (adData.getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                infoBuilder.append(", video info: ")
                        .append(getVideoInfo(adData.getProperty(AdData.VideoPlayer.class)));
            }
            return infoBuilder.toString();
        }
        return null;
    }

    private String getVideoInfo(AdData.VideoPlayer videoPlayer) {
        if (videoPlayer != null) {
            StringBuilder videoBuilder = new StringBuilder();
            videoBuilder.append("state:").append(videoPlayer.getVideoState()).append(",")
                    .append("duration:").append(videoPlayer.getDuration()).append(",")
                    .append("position:").append(videoPlayer.getCurrentPosition());
            return videoBuilder.toString();
        }
        return null;
    }

    private NativeExpressMediaListener mediaListener = new NativeExpressMediaListener() {
        @Override
        public void onVideoInit(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoInit: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoLoading(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoLoading: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoReady(NativeExpressADView nativeExpressADView, long l) {
            Log.i(TAG, "onVideoReady: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoStart(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoStart: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoPause(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoPause: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoComplete(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoComplete: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoError(NativeExpressADView nativeExpressADView, AdError adError) {
            Log.i(TAG, "onVideoError");
        }

        @Override
        public void onVideoPageOpen(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoPageOpen");
        }

        @Override
        public void onVideoPageClose(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoPageClose");
        }
    };

    private String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    //####################################### 权限 startActivity ############################################
    private void permissionCheck(final Activity activity, final TextView skipView, final FrameLayout splashContainer,final  ZKSplashListener zkSplashListener) {
        if (!PermissionUtils.isGranted(permissions)) {
            PermissionUtils.permission(permissions)
                    .callback(new PermissionUtils.FullCallback() {
                        @Override
                        public void onGranted(List<String> permissionsGranted) {
                            initTencentSplashAD(activity, skipView, splashContainer, zkSplashListener);
                        }

                        @Override
                        public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                            ToastUtils.showShort("获取权限失败");

                        }
                    })
                    .request();
        } else {
            initTencentSplashAD(activity, skipView, splashContainer, zkSplashListener);
        }
    }

    /**
     * 为防止无广告时造成视觉上类似于"闪退"的情况，设定无广告时页面跳转根据需要延迟一定时间，demo
     * 给出的延时逻辑是从拉取广告开始算开屏最少持续多久，仅供参考，开发者可自定义延时逻辑，如果开发者采用demo
     * 中给出的延时逻辑，也建议开发者考虑自定义minSplashTimeWhenNoAD的值（单位ms）
     **/
    private int minSplashTimeWhenNoAD = 2000;
    /**
     * 记录拉取广告的时间
     */
    private long fetchSplashADTime = 0;
    private int width, height;
    private final String SKIP_TEXT = "点击跳过 %d";

    private void initTencentSplashAD(final Activity activity,final  TextView skipView,final  FrameLayout splashContainer,final  ZKSplashListener zkSplashListener) {
        skipView.setVisibility(View.VISIBLE);
        fetchSplashAD(activity, splashContainer, skipView, AD_TENCENT_APP_ID, AD_TENCENT_SPLASH_KEY, new SplashADListener() {
            @Override
            public void onADDismissed() {
                Log.i("ad_tencent_splash", "SplashADDismissed");
//                splashHandler.sendEmptyMessageDelayed(FLAG_SPLASH_ADDISMISSED, 0);
                if (zkSplashListener != null) {
                    zkSplashListener.onADDismissed();
                }
                UMUtils.event(UMUtils.EVENT_TENCENT_SPLASH_ADDISMISSED);
            }

            @Override
            public void onNoAD(AdError error) {
                Log.i("ad_tencent_splash",
                        String.format("LoadSplashADFail, eCode=%d, errorMsg=%s", error.getErrorCode(),
                                error.getErrorMsg()));
                UMUtils.event(UMUtils.EVENT_TENCENT_SPLASH_NOAD);
                /**
                 * 为防止无广告时造成视觉上类似于"闪退"的情况，设定无广告时页面跳转根据需要延迟一定时间，demo
                 * 给出的延时逻辑是从拉取广告开始算开屏最少持续多久，仅供参考，开发者可自定义延时逻辑，如果开发者采用demo
                 * 中给出的延时逻辑，也建议开发者考虑自定义minSplashTimeWhenNoAD的值
                 **/
                long alreadyDelayMills = System.currentTimeMillis() - fetchSplashADTime;//从拉广告开始到onNoAD已经消耗了多少时间
                long shouldDelayMills = alreadyDelayMills > minSplashTimeWhenNoAD ? 0 : minSplashTimeWhenNoAD
                        - alreadyDelayMills;//为防止加载广告失败后立刻跳离开屏可能造成的视觉上类似于"闪退"的情况，根据设置的minSplashTimeWhenNoAD
                // 计算出还需要延时多久
                splashHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (zkSplashListener != null) {
                            zkSplashListener.onNoAD();
                        }
                    }
                }, shouldDelayMills);
//                splashHandler.sendEmptyMessageDelayed(FLAG_SPLASH_NOAD, shouldDelayMills);
            }

            @Override
            public void onADPresent() {
                Log.i("ad_tencent_splash", "SplashADPresent");
                UMUtils.event(UMUtils.EVENT_TENCENT_SPLASH_ADPRESENT);

                if (zkSplashListener != null) {
                    zkSplashListener.onADPresent();
                }
//                ll_splash_holder.setVisibility(View.GONE);
            }

            @Override
            public void onADClicked() {
                Log.i("ad_tencent_splash", "SplashADClicked clickUrl: "
                        + (splashAD.getExt() != null ? splashAD.getExt().get("clickUrl") : ""));
                UMUtils.event(UMUtils.EVENT_TENCENT_SPLASH_ADCLICKED);
            }

            /**
             * 倒计时回调，返回广告还将被展示的剩余时间。
             * 通过这个接口，开发者可以自行决定是否显示倒计时提示，或者还剩几秒的时候显示倒计时
             *
             * @param millisUntilFinished 剩余毫秒数
             */
            @Override
            public void onADTick(long millisUntilFinished) {
                Log.i("ad_tencent_splash", "SplashADTick " + millisUntilFinished + "ms");
                UMUtils.event(UMUtils.EVENT_TENCENT_SPLASH_ADTICK);
                skipView.setText(String.format(SKIP_TEXT, Math.round(millisUntilFinished / 1000f)));
                if (zkSplashListener != null) {
                    zkSplashListener.onADTick(millisUntilFinished);
                }
            }

            @Override
            public void onADExposure() {
                Log.i("ad_tencent_splash", "SplashADExposure");
                UMUtils.event(UMUtils.EVENT_TENCENT_SPLASH_ADEXPOSURE);
            }
        }, 0);
    }

    private SplashAD splashAD;

    /**
     * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
     *
     * @param activity      展示广告的 activity
     * @param adContainer   展示广告的大容器
     * @param skipContainer 自定义的跳过按钮：传入该 view 给 SDK 后，SDK 会自动给它绑定点击跳过事件。SkipView 的样式可以由开发者自由定制，其尺寸限制请参考 activity_splash.xml 或下面的注意事项。
     * @param appId         应用 ID
     * @param posId         广告位 ID
     * @param adListener    广告状态监听器
     * @param fetchDelay    拉取广告的超时时长：即开屏广告从请求到展示所花的最大时长（并不是指广告曝光时长）取值范围[3000, 5000]，设为0表示使用广点通 SDK 默认的超时时长。
     */
    private void fetchSplashAD(Activity activity, ViewGroup adContainer, View skipContainer,
                               String appId, String posId, SplashADListener adListener, int fetchDelay) {
        fetchSplashADTime = System.currentTimeMillis();
        splashAD = new SplashAD(activity, adContainer, skipContainer, appId, posId, adListener, fetchDelay);
    }

    private static final int FLAG_SPLASH_ADDISMISSED = 200;
    private static final int FLAG_SPLASH_NOAD = 201;
    private static final int FLAG_SPLASH_ADPRESENT = 202;
    @SuppressLint("HandlerLeak")
    private Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == FLAG_SPLASH_ADPRESENT) {

            } else if (msg.what == FLAG_SPLASH_ADDISMISSED) {

            } else if (msg.what == FLAG_SPLASH_NOAD) {

            }
        }
    };

    //统计事件
    private static void event(String event) {
        UMUtils.event(event);
    }

}
