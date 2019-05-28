package cc.zkteam.juediqiusheng.ad;

import android.app.Activity;
import android.app.Application;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.Utils;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

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
    public static final String AD_FACEBOOK_RELEASE_GL_DTS_JL_KEY = "872993369704116_872993936370726";

    private static Application application;

    public static void init(Application appContext) {
        application = appContext;
        // google 广告
//        MobileAds.initialize(appContext, ZKAD.AD_GOOGLE_APP_ID);

        // facebook 广告
        AudienceNetworkAds.initialize(appContext);
    }

    public static View initADView() {
        return initFacebookADView();
//        return initGoogleADView();
    }

    private static View initFacebookADView() {
        try {
            com.facebook.ads.AdView adView = new com.facebook.ads.AdView(application,
                    AD_FACEBOOK_RELEASE_GL_DTS_JL_KEY, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            adView.loadAd();
            return adView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static View initGoogleADView() {
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
        View view = activity.getWindow().getDecorView().getRootView();
        initHFAD(view);
    }

    public static void initHFAD(View rootView) {
        try {
            RelativeLayout adContentView = rootView.findViewById(R.id.ad_content_view);
            if (adContentView != null)
                adContentView.addView(ZKAD.initADView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
