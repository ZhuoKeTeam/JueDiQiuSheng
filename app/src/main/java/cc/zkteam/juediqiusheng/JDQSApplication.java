package cc.zkteam.juediqiusheng;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;

import javax.inject.Inject;

import cc.zkteam.juediqiusheng.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * JDQSApplication
 * Created by WangQing on 2017/10/23.
 */

public class JDQSApplication extends Application implements HasActivityInjector {

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
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
