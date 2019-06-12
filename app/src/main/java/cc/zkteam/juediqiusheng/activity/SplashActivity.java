package cc.zkteam.juediqiusheng.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.networkbench.agent.impl.NBSAppAgent;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;

import cc.zkteam.juediqiusheng.Constant;
import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.ui.main.MainActivity;

import static cc.zkteam.juediqiusheng.ad.ZKAD.AD_TENCENT_APP_ID;
import static cc.zkteam.juediqiusheng.ad.ZKAD.AD_TENCENT_SPLASH_KEY;

public class SplashActivity extends BaseActivity {


    private static final int DELAY_TIME = 3000;
    private static final int FLAG_ENTER_MAIN = 0;

    @SuppressLint("HandlerLeak")
    private Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == FLAG_ENTER_MAIN) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        }
    };


    private SplashAD splashAD;
    private ImageView img1, img2, img3;
    private LinearLayout linear;
    private TextView skip_view;
    private LinearLayout ll_splash_holder;
    private FrameLayout splash_container;
    private final String SKIP_TEXT = "点击跳过 %d";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
        initView();
        initData();
        initAnimation();
        initTencentSplashAD();

    }

    private void initTencentSplashAD() {
        skip_view.setVisibility(View.VISIBLE);
        fetchSplashAD(this, splash_container, skip_view, AD_TENCENT_APP_ID, AD_TENCENT_SPLASH_KEY, new SplashADListener() {
            @Override
            public void onADDismissed() {
                Log.i("AD_DEMO", "SplashADDismissed");
                splashHandler.sendEmptyMessageDelayed(FLAG_ENTER_MAIN, 0);
            }

            @Override
            public void onNoAD(AdError error) {
                Log.i(
                        "AD_DEMO",
                        String.format("LoadSplashADFail, eCode=%d, errorMsg=%s", error.getErrorCode(),
                                error.getErrorMsg()));
                /**
                 * 为防止无广告时造成视觉上类似于"闪退"的情况，设定无广告时页面跳转根据需要延迟一定时间，demo
                 * 给出的延时逻辑是从拉取广告开始算开屏最少持续多久，仅供参考，开发者可自定义延时逻辑，如果开发者采用demo
                 * 中给出的延时逻辑，也建议开发者考虑自定义minSplashTimeWhenNoAD的值
                 **/
                long alreadyDelayMills = System.currentTimeMillis() - fetchSplashADTime;//从拉广告开始到onNoAD已经消耗了多少时间
                long shouldDelayMills = alreadyDelayMills > minSplashTimeWhenNoAD ? 0 : minSplashTimeWhenNoAD
                        - alreadyDelayMills;//为防止加载广告失败后立刻跳离开屏可能造成的视觉上类似于"闪退"的情况，根据设置的minSplashTimeWhenNoAD
                // 计算出还需要延时多久
                splashHandler.sendEmptyMessageDelayed(FLAG_ENTER_MAIN, shouldDelayMills);
            }

            @Override
            public void onADPresent() {
                Log.i("AD_DEMO", "SplashADPresent");
                ll_splash_holder.setVisibility(View.GONE);
            }

            @Override
            public void onADClicked() {
                Log.i("AD_DEMO", "SplashADClicked clickUrl: "
                        + (splashAD.getExt() != null ? splashAD.getExt().get("clickUrl") : ""));
            }

            /**
             * 倒计时回调，返回广告还将被展示的剩余时间。
             * 通过这个接口，开发者可以自行决定是否显示倒计时提示，或者还剩几秒的时候显示倒计时
             *
             * @param millisUntilFinished 剩余毫秒数
             */
            @Override
            public void onADTick(long millisUntilFinished) {
                Log.i("AD_DEMO", "SplashADTick " + millisUntilFinished + "ms");
                skip_view.setText(String.format(SKIP_TEXT, Math.round(millisUntilFinished / 1000f)));
            }

            @Override
            public void onADExposure() {
                Log.i("AD_DEMO", "SplashADExposure");
            }
        }, 0);
    }

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        // 初始化听云 SDK
        NBSAppAgent.setLicenseKey(Constant.ZKTEAM_TINGYUN_KEY)
                .withLocationServiceEnabled(true)
                .enableLogging(true)
                .start(this.getApplicationContext());
    }

    private void initView() {
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        linear = findViewById(R.id.id_linear);
        skip_view = findViewById(R.id.skip_view);
        ll_splash_holder = findViewById(R.id.ll_splash_holder);
        splash_container = findViewById(R.id.splash_container);

    }

    private void initAnimation() {
        permissionCheck();

        WindowManager wm = (WindowManager)
                getSystemService(this.WINDOW_SERVICE);

        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        //btnAlpha(img1,500);
        btnTranslateLeft(img1, 500);
        // btnAlpha(img2,1000);
        btnTranslateRight(img2, 1000);
        btnTranslateLeft(img3, 1500);
        btnAlpha(linear, 2000);
    }

    private void btnAlpha(View view, long duration) {
        //透明度动画 public AlphaAnimation(float fromAlpha, float toAlpha){}
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        //持续时间
        aa.setDuration(duration);
        view.startAnimation(aa);
    }

    public void btnTranslateLeft(View view, long duration) {
        AnimationSet as = new AnimationSet(true);
        as.setDuration(duration);
        TranslateAnimation ta = new TranslateAnimation(width, 0, 0, 0);
        ta.setDuration(duration);
        as.addAnimation(ta);
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        //持续时间
        aa.setDuration(duration);
        as.addAnimation(aa);
        view.startAnimation(as);
    }

    public void btnTranslateRight(View view, long duration) {

        AnimationSet as = new AnimationSet(true);
        as.setDuration(duration);
        TranslateAnimation ta = new TranslateAnimation(-width, 0, 0, 0);
        ta.setDuration(duration);
        as.addAnimation(ta);
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        //持续时间
        aa.setDuration(duration);
        as.addAnimation(aa);
        view.startAnimation(as);
    }


    //####################################### 权限 startActivity ############################################
    private void permissionCheck() {
//        if (PermissionUtils.shouldShowRequestPermissionRationale(this, permissions)) {
//            PermissionUtils.requestPermissions(this, FLAG_REQUEST_PERMISSION, permissions, new PermissionUtils.OnPermissionListener() {
//
//                @Override
//                public void onPermissionGranted() {
//                    splashHandler.sendEmptyMessageDelayed(FLAG_ENTER_MAIN, DELAY_TIME);
//                }
//
//                @Override
//                public void onPermissionDenied(String[] deniedPermissions) {
//                    ToastUtils.showShort(getString(R.string.question_permission_tip));
//                    splashHandler.sendEmptyMessageDelayed(FLAG_ENTER_MAIN, DELAY_TIME);
//                }
//            });
//        } else {
//            splashHandler.sendEmptyMessageDelayed(FLAG_ENTER_MAIN, DELAY_TIME);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashHandler.removeMessages(FLAG_ENTER_MAIN);
    }


    /**
     * 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
