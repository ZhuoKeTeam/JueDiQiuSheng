package cc.zkteam.juediqiusheng.ad;

import android.app.Activity;
import android.app.Application;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.Utils;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import cc.zkteam.juediqiusheng.BuildConfig;
import cc.zkteam.juediqiusheng.R;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;

public class ZKAD {

    // Appid
    public static final String AD_GOOGLE_APP_ID = "ca-app-pub-5576379109949376~6821793256";
    // google 测试广告
    public static final String AD_GOOGLE_TEST_KEY = "ca-app-pub-3940256099942544/6300978111";
    // 横幅广告
    public static final String AD_GOOGLE_RELEASE_DTS_GL_HF_KEY = "ca-app-pub-5576379109949376/8466047413";
    // 激励广告
    public static final String AD_GOOGLE_RELEASE_GL_DTS_JL_KEY = "ca-app-pub-5576379109949376/9427296362";

    // facebook 的横幅广告
    public static final String AD_FACEBOOK_RELEASE_GL_DTS_JL_KEY = "2457797387617458_2458180154245848";

    private static Application application;
    private static com.facebook.ads.AdView fbAdView;

    public static void init(Application appContext) {
        application = appContext;
        // google 广告
        MobileAds.initialize(appContext, ZKAD.AD_GOOGLE_APP_ID);

        // facebook 广告
        AudienceNetworkAds.initialize(appContext);
    }

    public static View initADView() {
        return initFacebookADView();
//        return initGoogleADView();
    }

    public static View initFacebookADView() {
        try {
            fbAdView = new com.facebook.ads.AdView(application,
                    AD_FACEBOOK_RELEASE_GL_DTS_JL_KEY, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            fbAdView.loadAd();
            return fbAdView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
                    adContentView.addView(ZKAD.initFacebookADView());
                } else  {
                    adContentView.addView(ZKAD.initGoogleADView());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void destory() {
        if (fbAdView != null)
            fbAdView.destroy();
    }
}
