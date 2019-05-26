package cc.zkteam.juediqiusheng.ad;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import cc.zkteam.juediqiusheng.BuildConfig;
import cc.zkteam.juediqiusheng.R;

public class ZKAD {

    // Appid
    public static final String AD_GOOGLE_APP_ID = "ca-app-pub-5576379109949376~6821793256";
    // google 测试广告
    public static final String AD_GOOGLE_TEST_KEY = "ca-app-pub-3940256099942544/6300978111";
    // 横幅广告
    public static final String AD_GOOGLE_RELEASE_DTS_GL_HF_KEY = "ca-app-pub-5576379109949376/8466047413";
    // 激励广告
    public static final String AD_GOOGLE_RELEASE_GL_DTS_JL_KEY = "ca-app-pub-5576379109949376/9427296362";

    public static View initADView() {
        return initGoogleADView();
    }

    private static View initGoogleADView() {
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
    }

    public static void initHFAD(Activity activity) {
        View view = activity.getWindow().getDecorView().getRootView();
        initHFAD(view);
    }

    public static void initHFAD(View rootView) {
        RelativeLayout adContentView = rootView.findViewById(R.id.ad_content_view);
        if (adContentView != null)
            adContentView.addView(ZKAD.initADView());
    }
}
