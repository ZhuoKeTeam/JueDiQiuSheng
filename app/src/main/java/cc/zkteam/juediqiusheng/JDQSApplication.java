package cc.zkteam.juediqiusheng;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.umeng.commonsdk.UMConfigure;

import javax.inject.Inject;

import cc.zkteam.juediqiusheng.ad.ZKAD;
import cc.zkteam.juediqiusheng.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * JDQSApplication
 * Created by WangQing on 2017/10/23.
 */

public class JDQSApplication extends MultiDexApplication implements HasActivityInjector {

    // 2017/12/2 Dagger2 Activity 的注册
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Utils.init(this);
        ZKBase.init(this);
        DaggerAppComponent.builder()
                .application(this)
                .builder()
                .inject(this);

        UMConfigure.init(this, "5ce947420cafb23dee000572", "Tencent_AD", UMConfigure.DEVICE_TYPE_PHONE, null);
        ZKAD.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this) ;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
