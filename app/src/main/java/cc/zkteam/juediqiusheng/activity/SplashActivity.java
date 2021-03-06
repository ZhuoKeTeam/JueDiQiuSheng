package cc.zkteam.juediqiusheng.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.networkbench.agent.impl.NBSAppAgent;

import java.util.List;

import cc.zkteam.juediqiusheng.Constant;
import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.exception.ZKSharePreferencesException;
import cc.zkteam.juediqiusheng.sharedpreferences.ZKSharedPreferences;
import cc.zkteam.juediqiusheng.ui.main.MainActivity;
import cc.zkteam.juediqiusheng.ui.main.WelcomeActivity;

public class SplashActivity extends BaseActivity {

    private static final String FIRST_START = "first_start";
    private static final int DELAY_TIME =  3000;
    private static final int FLAG_ENTER_MAIN =  0;

    private String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @SuppressLint("HandlerLeak")
    private Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == FLAG_ENTER_MAIN) {

                try {
                    boolean fistStart = (boolean) sharedPreferences.get(FIRST_START, true);
                    if (fistStart) {
                        sharedPreferences.put(FIRST_START, false);
                        Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        finish();
                    }
                    return;
                } catch (ZKSharePreferencesException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        }
    };

    private ImageView img1,img2,img3;
    private LinearLayout linear;
    private  int width,height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
        initView();
        initData();
        initAnimation();
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

    private void initPermission() {
        PermissionUtils.permission(permissions)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        splashHandler.sendEmptyMessageDelayed(FLAG_ENTER_MAIN, DELAY_TIME);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        ToastUtils.showShort(getString(R.string.question_permission_tip));
                        splashHandler.sendEmptyMessageDelayed(FLAG_ENTER_MAIN, DELAY_TIME);
                    }
                }).request();
    }

    private void initView() {
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);
        linear=findViewById(R.id.id_linear);
    }

    private void initAnimation() {

        WindowManager wm = (WindowManager)
                getSystemService(this.WINDOW_SERVICE);

         width = wm.getDefaultDisplay().getWidth();
         height = wm.getDefaultDisplay().getHeight();
        //btnAlpha(img1,500);
        btnTranslateLeft(img1,500);
       // btnAlpha(img2,1000);
        btnTranslateRight(img2,1000);
        btnTranslateLeft(img3,1500);
        btnAlpha(linear,2000);
    }
    private void btnAlpha(View view,long duration) {
        //透明度动画 public AlphaAnimation(float fromAlpha, float toAlpha){}
        AlphaAnimation aa = new AlphaAnimation(0, 1);

        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                initPermission();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //持续时间
        aa.setDuration(duration);
        view.startAnimation(aa);
    }
    public void btnTranslateLeft(View view,long duration) {
        AnimationSet as = new AnimationSet(true);
        as.setDuration(duration);
        TranslateAnimation ta = new TranslateAnimation(width,0, 0, 0);
        ta.setDuration(duration);
        as.addAnimation(ta);
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        //持续时间
        aa.setDuration(duration);
        as.addAnimation(aa);
        view.startAnimation(as);
    }
    public void btnTranslateRight(View view,long duration) {

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

    ZKSharedPreferences sharedPreferences = new ZKSharedPreferences() {

        @Override
        public String sharedPreferencesFileName() {
            return "init_setting";
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashHandler.removeMessages(FLAG_ENTER_MAIN);
    }
}
