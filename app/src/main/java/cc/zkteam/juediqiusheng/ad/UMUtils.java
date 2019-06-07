package cc.zkteam.juediqiusheng.ad;

import com.blankj.utilcode.util.Utils;
import com.umeng.analytics.MobclickAgent;

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
    public static final String EVENT_GG_JL_AD_SHOW_FAILED= "event_gg_jl_ad_show_failed"; // GG奖励广告显示失败


    public static final String EVENT_GG_CP_AD_ADD = "event_gg_cp_ad_add"; // GG开始添加插屏广告
    public static final String EVENT_GG_CP_AD_CLOSED= "event_gg_cp_ad_closed"; // GG插屏广告关闭
    public static final String EVENT_GG_CP_AD_LOAD_FAILED= "event_gg_cp_ad_load_failed"; // GG插屏广告加载失败
    public static final String EVENT_GG_CP_AD_LEFT_APPLICATION= "event_gg_cp_ad_left_application"; // GG插屏广告离开 app
    public static final String EVENT_GG_CP_AD_OPENED= "event_gg_cp_ad_opened"; // GG插屏广告被打开
    public static final String EVENT_GG_CP_AD_LOADED= "event_gg_cp_ad_loaded"; // GG插屏广告被加载
    public static final String EVENT_GG_CP_AD_CLICKED= "event_gg_cp_ad_clicked"; // GG插屏广告被点击
    public static final String EVENT_GG_CP_AD_IMPRESSION= "event_gg_cp_ad_impression"; // GG插屏日志Impression


    /**
     * 友盟统计事件
     * @param event event
     */
    public static void event(String event) {
        MobclickAgent.onEvent(Utils.getApp(), event);
    }
}
