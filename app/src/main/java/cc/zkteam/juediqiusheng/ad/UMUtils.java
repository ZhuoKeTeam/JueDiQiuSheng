package cc.zkteam.juediqiusheng.ad;

import com.blankj.utilcode.util.Utils;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.Banner;

public class UMUtils {

    // Facebook 广告统计
    public static final String EVENT_FB_HF_AD_ADD = "event_fb_hf_ad_add"; // FB开始添加小横幅广告

    public static final String EVENT_FB_HF_AD_ERROR = "event_fb_hf_ad_error"; // FB广告错误
    public static final String EVENT_FB_HF_AD_LOADED = "event_fb_hf_ad_loaded"; // FB广告已加载
    public static final String EVENT_FB_HF_AD_CLICKED = "event_fb_hf_ad_clicked"; // FB广告已点击
    public static final String EVENT_FB_HF_AD_LOGGING_IMPRESSION = "event_fb_hf_ad_logging_impression"; // FB日志Impression


    // Google 广告统计
    public static final String EVENT_GG_HF_AD_ADD = "event_gg_hf_ad_add"; // GG开始添加小横幅广告

    public static final String EVENT_GG_HF_AD_CLOSED = "event_gg_hf_ad_closed"; // GG广告关闭
    public static final String EVENT_GG_HF_AD_FAILED_TO_LOAD = "event_gg_hf_ad_failed_to_load"; // GG开广告加载失败
    public static final String EVENT_GG_HF_AD_LEFT_APPLICATION = "event_gg_hf_ad_left_application"; // GG广告离开我们App
    public static final String EVENT_GG_HF_AD_OPENED = "event_gg_hf_ad_opened"; // GG广告已打开
    public static final String EVENT_GG_HF_AD_LOADED = "event_gg_hf_ad_loaded"; // GG广告已加载
    public static final String EVENT_GG_HF_AD_CLICKED = "event_gg_hf_ad_clicked"; // GG广告已点击
    public static final String EVENT_GG_HF_AD_IMPRESSION = "event_gg_hf_ad_impression"; // GG日志Impression


    public static final String EVENT_GG_JL_AD_ADD = "event_gg_jl_ad_add"; // GG奖励广告添加
    public static final String EVENT_GG_JL_AD_LOADED = "event_gg_jl_ad_loaded"; // GG奖励广告已经加载
    public static final String EVENT_GG_JL_AD_LOADED_FAILED = "event_gg_jl_ad_loaded_failed"; // GG奖励广告加载失败

    public static final String EVENT_GG_JL_AD_OPENED = "event_gg_jl_ad_opened"; // GG奖励广告已经打开
    public static final String EVENT_GG_JL_AD_CLOSED = "event_gg_jl_ad_closed"; // GG奖励广告已经关闭
    public static final String EVENT_GG_JL_AD_EARNED_JL = "event_gg_jl_ad_earned_jl"; // GG奖励广告获取奖励成功
    public static final String EVENT_GG_JL_AD_SHOW_FAILED = "event_gg_jl_ad_show_failed"; // GG奖励广告显示失败


    public static final String EVENT_GG_CP_AD_ADD = "event_gg_cp_ad_add"; // GG开始添加插屏广告
    public static final String EVENT_GG_CP_AD_CLOSED = "event_gg_cp_ad_closed"; // GG插屏广告关闭
    public static final String EVENT_GG_CP_AD_LOAD_FAILED = "event_gg_cp_ad_load_failed"; // GG插屏广告加载失败
    public static final String EVENT_GG_CP_AD_LEFT_APPLICATION = "event_gg_cp_ad_left_application"; // GG插屏广告离开 app
    public static final String EVENT_GG_CP_AD_OPENED = "event_gg_cp_ad_opened"; // GG插屏广告被打开
    public static final String EVENT_GG_CP_AD_LOADED = "event_gg_cp_ad_loaded"; // GG插屏广告被加载
    public static final String EVENT_GG_CP_AD_CLICKED = "event_gg_cp_ad_clicked"; // GG插屏广告被点击
    public static final String EVENT_GG_CP_AD_IMPRESSION = "event_gg_cp_ad_impression"; // GG插屏日志Impression


    /**
     * Tencent 广告统计
     * 广告分为:
     * 1. 开屏     |    splash
     * 2. Banner   |    banner
     * 3. 激励视频  |    reward
     * 4. 插屏     |     incert
     * 5. 原生     |     native
     */

    // 1. splash
    public static final String EVENT_TENCENT_SPLASH_ADDISMISSED = "event_tencent_splash_addismissed";
    public static final String EVENT_TENCENT_SPLASH_NOAD = "event_tencent_splash_noad";
    public static final String EVENT_TENCENT_SPLASH_ADPRESENT = "event_tencent_splash_adpresent";
    public static final String EVENT_TENCENT_SPLASH_ADCLICKED = "event_tencent_splash_adclicked";
    public static final String EVENT_TENCENT_SPLASH_ADTICK = "event_tencent_splash_adtick";
    public static final String EVENT_TENCENT_SPLASH_ADEXPOSURE = "event_tencent_splash_adexposure";

    // 2. banner
    public static final String EVENT_TENCENT_BANNER_NOAD = "event_tencent_banner_noad";
    public static final String EVENT_TENCENT_BANNER_ADRECEIVE = "event_tencent_banner_adreceive";
    public static final String EVENT_TENCENT_BANNER_ADEXPOSURE = "event_tencent_banner_adexposure";
    public static final String EVENT_TENCENT_BANNER_ADCLOSED = "event_tencent_banner_adclosed";
    public static final String EVENT_TENCENT_BANNER_ADCLICKED = "event_tencent_banner_adclicked";
    public static final String EVENT_TENCENT_BANNER_ADLEFTAPPLICATION = "event_tencent_banner_adleftapplication";
    public static final String EVENT_TENCENT_BANNER_ADOPENOVERLAY = "event_tencent_banner_adopenoverlay";
    public static final String EVENT_TENCENT_BANNER_ADCLOSEOVERLAY = "event_tencent_banner_adcloseoverlay";

    // 3. reward
    public static final String EVENT_TENCENT_REWARD_ADLOAD = "event_tencent_reward_adload";//广告加载成功，可在此回调后进行广告展示
    public static final String EVENT_TENCENT_REWARD_VIDEOCACHED = "event_tencent_reward_videocached";//视频素材缓存成功，可在此回调后进行广告展示
    public static final String EVENT_TENCENT_REWARD_ADSHOW = "event_tencent_reward_adshow";//激励视频广告页面展示
    public static final String EVENT_TENCENT_REWARD_ADEXPOSE = "event_tencent_reward_adexpose";//激励视频广告曝光
    public static final String EVENT_TENCENT_REWARD_REWARD = "event_tencent_reward_reward";//激励视频触发激励（观看视频大于一定时长或者视频播放完毕）
    public static final String EVENT_TENCENT_REWARD_ADCLICK = "event_tencent_reward_adclick";//激励视频广告被点击
    public static final String EVENT_TENCENT_REWARD_VIDEOCOMPLETE = "event_tencent_reward_videocomplete";//激励视频播放完毕
    public static final String EVENT_TENCENT_REWARD_ADCLOSE = "event_tencent_reward_adclose";//激励视频广告被关闭
    public static final String EVENT_TENCENT_REWARD_ERROR = "event_tencent_reward_error";//广告流程出错

    // 4. incert
    public static final String EVENT_TENCENT_INCERT_ADRECEIVE = "event_tencent_incert_adreceive";
    public static final String EVENT_TENCENT_INCERT_NOAD = "event_tencent_incert_noad";
    public static final String EVENT_TENCENT_INCERT_ADOPENED = "event_tencent_incert_adopened";
    public static final String EVENT_TENCENT_INCERT_ADEXPOSURE = "event_tencent_incert_adexposure";
    public static final String EVENT_TENCENT_INCERT_ADCLICKED = "event_tencent_incert_adclicked";
    public static final String EVENT_TENCENT_INCERT_ADLEFTAPPLICATION = "event_tencent_incert_adleftapplication";
    public static final String EVENT_TENCENT_INCERT_ADCLOSED = "event_tencent_incert_adclosed";

    // 5. native
    public static final String EVENT_TENCENT_NATIVE_ADLOADED = "event_tencent_native_adloaded";
    public static final String EVENT_TENCENT_NATIVE_RENDERFAIL = "event_tencent_native_renderfail";
    public static final String EVENT_TENCENT_NATIVE_RENDERSUCCESS = "event_tencent_native_rendersuccess";
    public static final String EVENT_TENCENT_NATIVE_ADEXPOSURE = "event_tencent_native_adexposure";
    public static final String EVENT_TENCENT_NATIVE_ADCLICKED = "event_tencent_native_adclicked";
    public static final String EVENT_TENCENT_NATIVE_ADCLOSED = "event_tencent_native_adclosed";
    public static final String EVENT_TENCENT_NATIVE_ADLEFTAPPLICATION = "event_tencent_native_adleftapplication";
    public static final String EVENT_TENCENT_NATIVE_ADOPENOVERLAY = "event_tencent_native_adopenoverlay";
    public static final String EVENT_TENCENT_NATIVE_ADCLOSEOVERLAY = "event_tencent_native_adcloseoverlay";
    public static final String EVENT_TENCENT_NATIVE_NOAD = "event_tencent_native_noad";


    /**
     * 友盟统计事件
     *
     * @param event event
     */
    public static void event(String event) {
        MobclickAgent.onEvent(Utils.getApp(), event);
    }
}
