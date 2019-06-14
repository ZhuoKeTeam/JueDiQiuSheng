package cc.zkteam.juediqiusheng.ad;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.MainThread;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTBannerAd;
import com.bytedance.sdk.openadsdk.TTDrawFeedAd;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.TTInteractionAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.bytedance.sdk.openadsdk.TTSplashAd;

import java.util.List;

import cc.zkteam.juediqiusheng.R;

import static cc.zkteam.juediqiusheng.ad.ZKAD.AD_TOUTIAO_TEST_APP_ID;

public class ToutiaoAd {
    private static final String TAG = "ZKAD_TTAD";

    private static volatile ToutiaoAd sInstance;

    private TTAdNative mTTAdNative;
    private TTRewardVideoAd mttRewardVideoAd;
    private TTFullScreenVideoAd mttFullVideoAd;
    private boolean mHasShowDownloadActive = false;

    private ToutiaoAd() {

    }

    public ToutiaoAd(Context context) {
        //  这样不行 会抛出异常 IllegalArgumentException: Dislike 初始化必须使用activity,请在TTAdManager.createAdNative(activity)中传入
        TTAdManager ttAdManager = TTAdSdk.getAdManager();
        mTTAdNative = ttAdManager.createAdNative(context.getApplicationContext());
    }

    public static ToutiaoAd getsInstance() {
        if (sInstance == null) {
            synchronized (ToutiaoAd.class) {
                if (sInstance == null) {
                    sInstance = new ToutiaoAd();
                }
            }
        }
        return sInstance;
    }

    public static void initAd(Context application) {
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

    /**
     * 激励广告
     *
     * @param codeId
     * @param activity
     */
    public void showJLAD(String codeId, Activity activity) {
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
                logD("showJLAD onError -> " + message);
                logD("showJLAD onError -> " + code);
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                logD("showJLAD rewardVideoAd video cached");
                mttRewardVideoAd.showRewardVideoAd(activity);
                mttRewardVideoAd = null;
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                logD("showJLAD rewardVideoAd loadedd");
                mttRewardVideoAd = ad;
//                mttRewardVideoAd.setShowDownLoadBar(false);
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        logD("showJLAD rewardVideoAd show");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        logD("showJLAD rewardVideoAd bar click");
                    }

                    @Override
                    public void onAdClose() {
                        logD("showJLAD rewardVideoAd close");
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        logD("showJLAD rewardVideoAd complete");
                    }

                    @Override
                    public void onVideoError() {
                        logD("showJLAD rewardVideoAd error");
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        logD("showJLAD onRewardVerify -> " + "verify:" + rewardVerify + " amount:" + rewardAmount +
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
                            logD("showJLAD 下载中，点击下载区域暂停");
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        logD("showJLAD 下载暂停，点击下载区域继续");
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        logD("showJLAD 下载失败，点击下载区域重新下载");
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        logD("showJLAD 下载完成，点击下载区域重新下载");
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


    /**
     * Banner 广告
     *
     * @param codeId
     * @param activity
     */
    public void showBannerAd(String codeId, Activity activity) {
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(activity);
        TTAdSdk.getAdManager().requestPermissionIfNecessary(activity);
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setImageAcceptedSize(600, 257)
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadBannerAd(adSlot, new TTAdNative.BannerAdListener() {

            @Override
            public void onError(int code, String message) {
//                mBannerContainer.removeAllViews();
                logD("showBannerAd onError -> " + message);
                logD("showBannerAd onError -> " + code);
            }

            @Override
            public void onBannerAdLoad(final TTBannerAd ad) {
                if (ad == null) {
                    return;
                }
                View bannerView = ad.getBannerView();
                if (bannerView == null) {
                    return;
                }
                //设置轮播的时间间隔  间隔在30s到120秒之间的值，不设置默认不轮播
                ad.setSlideIntervalTime(30 * 1000);
//                mBannerContainer.removeAllViews();
                //设置广告互动监听回调
                ad.setBannerInteractionListener(new TTBannerAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        logD("showJLAD 广告被点击");
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        logD("showJLAD 广告展示");
                    }
                });
//                //（可选）设置下载类广告的下载监听
//                bindDownloadListener(ad);
                //在banner中显示网盟提供的dislike icon，有助于广告投放精准度提升
                ad.setShowDislikeIcon(new TTAdDislike.DislikeInteractionCallback() {
                    @Override
                    public void onSelected(int position, String value) {
                        logD("showJLAD 点击 -> " + value);
                        //用户选择不喜欢原因后，移除广告展示
//                        mBannerContainer.removeAllViews();
                    }

                    @Override
                    public void onCancel() {
                        logD("showJLAD 点击取消");
                    }
                });
                View view = activity.getWindow().getDecorView().getRootView();
                LinearLayout adContentView = view.findViewById(R.id.ad_content_view);
                adContentView.addView(bannerView);
                //获取网盟dislike dialog，您可以在您应用中本身自定义的dislike icon 按钮中设置 mTTAdDislike.showDislikeDialog();
                /*mTTAdDislike = ad.getDislikeDialog(new TTAdDislike.DislikeInteractionCallback() {
                        @Override
                        public void onSelected(int position, String value) {
                            TToast.show(mContext, "点击 " + value);
                        }

                        @Override
                        public void onCancel() {
                            TToast.show(mContext, "点击取消 ");
                        }
                    });
                if (mTTAdDislike != null) {
                    XXX.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTTAdDislike.showDislikeDialog();
                        }
                    });
                } */

            }
        });
    }

    /**
     * 数据流广告（用于 RecycleView）
     *
     * @param codeId
     * @param callback
     * @param activity
     */
    public void showFeedAD(String codeId, IADLoadCallback<List<TTFeedAd>> callback, Activity activity) {
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(activity);
        TTAdSdk.getAdManager().requestPermissionIfNecessary(activity);
        //feed广告请求类型参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(640, 320)
                .setAdCount(3)
                .build();
        //调用feed广告异步请求接口
        mTTAdNative.loadFeedAd(adSlot, new TTAdNative.FeedAdListener() {
            @Override
            public void onError(int code, String message) {
                logD("showFeedAD onError -> " + message);
                logD("showFeedAD onError -> " + code);
            }

            @Override
            public void onFeedAdLoad(List<TTFeedAd> ads) {
                if (ads == null || ads.isEmpty()) {
                    logD("showFeedAD on FeedAdLoaded: ad is null!");
                    return;
                }

                logD("showFeedAD on FeedAdLoaded -> " + ads);

                //  TODO 数据设置到 RV 中
                if (callback != null) {
                    callback.onAdLoad(ads);
                }
            }
        });
    }

    /**
     * 插屏广告
     *
     * @param codeId
     * @param activity
     */
    public void showInteractionAD(String codeId, Activity activity) {
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(activity);
        TTAdSdk.getAdManager().requestPermissionIfNecessary(activity);
        //step4:创建插屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(600, 600) //根据广告平台选择的尺寸，传入同比例尺寸
                .build();
        //step5:请求广告，调用插屏广告异步请求接口
        mTTAdNative.loadInteractionAd(adSlot, new TTAdNative.InteractionAdListener() {
            @Override
            public void onError(int code, String message) {
                logD("showInteractionAD onError -> " + message);
                logD("showInteractionAD onError -> " + code);
            }

            @Override
            public void onInteractionAdLoad(TTInteractionAd ttInteractionAd) {
                ttInteractionAd.setAdInteractionListener(new TTInteractionAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked() {
                        logD("showInteractionAD 广告被点击");
                    }

                    @Override
                    public void onAdShow() {
                        logD("showInteractionAD 广告被展示");
                    }

                    @Override
                    public void onAdDismiss() {
                        logD("showInteractionAD 插屏广告消失");
                    }
                });
                //如果是下载类型的广告，可以注册下载状态回调监听
                if (ttInteractionAd.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    ttInteractionAd.setDownloadListener(new TTAppDownloadListener() {
                        @Override
                        public void onIdle() {
                            logD("showInteractionAD 点击开始下载");
                        }

                        @Override
                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                            logD("showInteractionAD 下载中");
                        }

                        @Override
                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                            logD("showInteractionAD 下载暂停");
                        }

                        @Override
                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                            logD("showInteractionAD 下载失败");
                        }

                        @Override
                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                            logD("showInteractionAD 下载完成");
                        }

                        @Override
                        public void onInstalled(String fileName, String appName) {
                            logD("showInteractionAD 安装完成");
                        }
                    });
                }
                //弹出插屏广告
                ttInteractionAd.showInteractionAd(activity);
            }
        });
    }

    /**
     * 全屏广告
     *
     * @param codeId
     * @param activity
     */
    public void showFullVideoAD(String codeId, Activity activity, int orientation) {
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(activity);
        TTAdSdk.getAdManager().requestPermissionIfNecessary(activity);
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .setOrientation(orientation)//必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();
        //step5:请求广告
        mTTAdNative.loadFullScreenVideoAd(adSlot, new TTAdNative.FullScreenVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                logD("showFullVideoAD onError -> " + message);
                logD("showFullVideoAD onError -> " + code);
            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ad) {
                mttFullVideoAd = ad;
                mttFullVideoAd.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        logD("showFullVideoAD FullVideoAd show");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        logD("showFullVideoAD FullVideoAd bar click");
                    }

                    @Override
                    public void onAdClose() {
                        logD("showFullVideoAD FullVideoAd close");
                    }

                    @Override
                    public void onVideoComplete() {
                        logD("showFullVideoAD FullVideoAd complete");
                        mttFullVideoAd.showFullScreenVideoAd(activity);
                        mttFullVideoAd = null;
                    }

                    @Override
                    public void onSkippedVideo() {
                        logD("showFullVideoAD FullVideoAd skipped");

                    }

                });
            }

            @Override
            public void onFullScreenVideoCached() {
                logD("showFullVideoAD FullVideoAd video cache");
                mttFullVideoAd.showFullScreenVideoAd(activity);
                mttFullVideoAd = null;
            }
        });
    }

    private boolean mHasLoaded;

    /**
     * 启动页广告
     *
     * @param codeId
     * @param callback
     * @param activity
     */
    public void showSplashAD(String codeId, IADLoadCallback<String> callback, Activity activity) {
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(activity);
        TTAdSdk.getAdManager().requestPermissionIfNecessary(activity);
        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .build();
        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {
                Log.d(TAG, message);
                mHasLoaded = true;
                logD("showSplashAD onError -> " + message);
                logD("showSplashAD onError -> " + code);

                if (callback != null) {
                    callback.onAdLoad("onError");
                }
            }

            @Override
            @MainThread
            public void onTimeout() {
                mHasLoaded = true;
                logD("showSplashAD 开屏广告加载超时");
                if (callback != null) {
                    callback.onAdLoad("onTimeout");
                }
            }

            @Override
            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                Log.d(TAG, "开屏广告请求成功");
                mHasLoaded = true;
                if (ad == null) {
                    return;
                }
                View root = activity.getWindow().getDecorView().getRootView();
                RelativeLayout adContentView = root.findViewById(R.id.rl_ad_root);
                //获取SplashView
                View view = ad.getSplashView();
                adContentView.removeAllViews();
                //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕宽
                adContentView.addView(view);
                //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                //ad.setNotAllowSdkCountdown();

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        logD("showSplashAD 开屏广告点击");
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        logD("showSplashAD 开屏广告展示");
                    }

                    @Override
                    public void onAdSkip() {
                        logD("showSplashAD 开屏广告跳过");
                        if (callback != null) {
                            callback.onAdLoad("onAdSkip");
                        }

                    }

                    @Override
                    public void onAdTimeOver() {
                        logD("showSplashAD 开屏广告倒计时结束");
                        if (callback != null) {
                            callback.onAdLoad("onAdTimeOver");
                        }

                    }
                });
            }
        }, 2000);
    }

    /**
     * Draw 信息流广告，也就是抖音那样的视频流
     *
     * @param codeId
     * @param activity
     */
    public void showDrawNativeVideoAD(String codeId, IADLoadCallback<List<TTDrawFeedAd>> callback, Activity activity) {
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(activity);
        TTAdSdk.getAdManager().requestPermissionIfNecessary(activity);
        //step3:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .setAdCount(2) //请求广告数量为1到3条
                .build();
        //step4:请求广告,对请求回调的广告作渲染处理
        mTTAdNative.loadDrawFeedAd(adSlot, new TTAdNative.DrawFeedAdListener() {
            @Override
            public void onError(int code, String message) {
                logD("showDrawNativeVideoAD onError -> " + message);
                logD("showDrawNativeVideoAD onError -> " + code);
            }

            @Override
            public void onDrawFeedAdLoad(List<TTDrawFeedAd> ads) {
                if (ads == null || ads.isEmpty()) {
                    logD("showDrawNativeVideoAD ad is null!");
                    return;
                }
                logD("showDrawNativeVideoAD on FeedAdLoaded -> " + ads);

                //  TODO 数据设置到列表中
                if (callback != null) {
                    callback.onAdLoad(ads);
                }
            }
        });
    }


    public interface IADLoadCallback<T> {
        void onAdLoad(T ad);
    }

    private void logD(String msg) {
        Log.d(TAG, msg);
    }
}
