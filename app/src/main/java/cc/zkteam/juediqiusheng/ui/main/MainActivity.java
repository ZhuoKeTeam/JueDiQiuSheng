package cc.zkteam.juediqiusheng.ui.main;
import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.activity.BaseActivity;
import cc.zkteam.juediqiusheng.bean.CategoryBean;
import cc.zkteam.juediqiusheng.bean.UpdateBean;
import cc.zkteam.juediqiusheng.fragment.WQFragment;
import cc.zkteam.juediqiusheng.lifecycle.components.demo.ZKLiveData;
import cc.zkteam.juediqiusheng.lifecycle.components.demo.ZKText;
import cc.zkteam.juediqiusheng.lifecycle.components.demo.ZKViewModule;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.module.answer.QuestionFragment;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;
import cc.zkteam.juediqiusheng.strategy.StrategyFragment;
import cc.zkteam.juediqiusheng.ui.main.test.User;
import cc.zkteam.juediqiusheng.utils.ZKSP;
import cc.zkteam.juediqiusheng.view.ZKViewPager;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import okhttp3.OkHttpClient;
public class MainActivity extends BaseActivity implements HasSupportFragmentInjector {
    public static final String TAG = "MainActivity";
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    public static final int NAV_TYPE_RECOMMEND = 0;
    public static final int NAV_TYPE_STRATEGY = 1;
    public static final int NAV_TYPE_GALLERY = 2;
    public static final int NAV_TYPE_QUESTION= 3;
    public static int [] NAV_TYPE = new int[]{NAV_TYPE_RECOMMEND, NAV_TYPE_STRATEGY, NAV_TYPE_GALLERY, NAV_TYPE_QUESTION};
    private static final int FLAG_REQUEST_PERMISSION =  100;
    private String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private ZKViewPager mViewPager;
    private BottomNavigationView navigation;
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            int itemId = R.id.navigation_recommend;
            switch (position) {
                case NAV_TYPE_RECOMMEND:
                    itemId = R.id.navigation_recommend;
                    break;
                case NAV_TYPE_STRATEGY:
                    itemId = R.id.navigation_game;
                    break;
                case NAV_TYPE_GALLERY:
                    itemId = R.id.navigation_picture;
                    break;
                case NAV_TYPE_QUESTION:
                    itemId = R.id.navigation_question;
                    break;
            }
            navigation.setSelectedItemId(itemId);
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_recommend:
                    mViewPager.setCurrentItem(NAV_TYPE_RECOMMEND);
                    return true;
                case R.id.navigation_game:
                    mViewPager.setCurrentItem(NAV_TYPE_STRATEGY);
                    return true;
                case R.id.navigation_picture:
                    mViewPager.setCurrentItem(NAV_TYPE_GALLERY);
                    return true;
                case R.id.navigation_question:
                    mViewPager.setCurrentItem(NAV_TYPE_QUESTION);
                    return true;
                default:
            }
            return false;
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        getAAID();
        saveLifeCount();
    }
    private void saveLifeCount() {
        ZKSP.init();
    }
    private void getAAID() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AdvertisingIdClient.Info  info = AdvertisingIdClient.getAdvertisingIdInfo(mContext);
                    if (info != null) {
                        String aaid = info.getId();
                        boolean adTrackingEnabled = info.isLimitAdTrackingEnabled();
                        Log.d("WangQing", "gms AAID==" + aaid + ", adTrackingEnabled=" +adTrackingEnabled);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void initViews() {
        navigation = findViewById(R.id.navigation);
        mViewPager = findViewById(R.id.container);
        mViewPager.setLifecycle(getLifecycle());
    }
    @Override
    protected void initListener() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mViewPager.setViewPager(onPageChangeListener, new SectionsPagerAdapter(getSupportFragmentManager()));
    }
    @Override
    protected void initData() {
        PermissionUtils.permission(permissions)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                    }
                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        ToastUtils.showShort(getString(R.string.question_permission_tip));
                    }
                })
                .request();
        Context context = this;
        ZKConnectionManager.getInstance().getZKApi().update()
                .enqueue(new ZKCallback<UpdateBean>() {
                    @Override
                    public void onResponse(UpdateBean result) {
                        if (result != null) {
                            boolean check = result.isCheck();
                            int version = result.getApp_version();
                            String info = result.getInfo();
                            if (check && version > 0 && version != AppUtils.getAppVersionCode()) {
                                final AlertDialog.Builder normalDialog =
                                        new AlertDialog.Builder(MainActivity.this);
                                normalDialog.setTitle("升级提示");
                                normalDialog.setMessage(info);
                                normalDialog.setPositiveButton("确定",
                                        (dialog, which) -> {
                                            try {
                                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                                                if (intent.resolveActivity(context.getPackageManager()) != null) {
                                                    context.startActivity(intent);
                                                } else {
                                                }
                                            } catch (ActivityNotFoundException activityNotFoundException1) {
                                            }
                                        });
                                normalDialog.setNegativeButton("关闭",
                                        (dialog, which) -> {
                                        });
                                normalDialog.show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                    }
                });
    }
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case NAV_TYPE_RECOMMEND:
                    return WQFragment.newInstance(0);
                case NAV_TYPE_STRATEGY:
                    return StrategyFragment.newInstance();
                case NAV_TYPE_GALLERY:
                    return (Fragment) ARouter.getInstance().build("/modules/pic/main").navigation();
                case NAV_TYPE_QUESTION:
                    return QuestionFragment.newInstance();
            }
            return null;
        }
        @Override
        public int getCount() {
            return NAV_TYPE.length;
        }
    }
    @Inject
    ZKConnectionManager zkConnectionManager;
    @Inject
    ZKConnectionManager zkConnectionManager1;
    @Inject
    OkHttpClient okHttpClient1;
    @Inject
    OkHttpClient okHttpClient2;
    @Inject
    User user;
    private void demoRequestApi() {
        user.setName("hello");
        Log.d(TAG, "WWWW demoRequestApi() called: " + user.getName());
        Log.i(TAG, "MainActivity: " + zkConnectionManager.toString());
        Log.i(TAG, "MainActivity: " + zkConnectionManager1.toString());
        Log.i(TAG, "OkHttpClient: " + okHttpClient1.toString());
        Log.i(TAG, "OkHttpClient: " + okHttpClient2.toString());
        zkConnectionManager.getZKApi().categoryData(20)
                .enqueue(new ZKCallback<List<CategoryBean>>() {
                    @Override
                    public void onResponse(List<CategoryBean> result) {
                        Log.d(TAG, "onResponse() called with: resultList = [" + result + "]");
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d(TAG, "onFailure() called with: throwable = [" + throwable + "]");
                    }
                });
        Log.d(TAG, "demoRequestApi() called");
    }
    private void demoLifeComponents(final TextView textView) {
        LiveData<String> zkLiveData = new ZKLiveData();
        zkLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "LiveData onChanged() called with: s = [" + s + "]");
            }
        });
        new ZKText(getLifecycle());
        ZKViewModule module = ViewModelProviders.of(this).get(ZKViewModule.class);
        module.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "WQViewModule onChanged() called with: s = [" + s + "]");
                if (textView != null) {
                    textView.setText(s);
                }
            }
        });
    }
}
