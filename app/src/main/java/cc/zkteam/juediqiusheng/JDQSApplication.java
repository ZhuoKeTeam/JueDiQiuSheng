package cc.zkteam.juediqiusheng;

import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;

import cc.zkteam.juediqiusheng.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * JDQSApplication
 * Created by WangQing on 2017/10/23.
 */

public class JDQSApplication extends DaggerApplication {

    Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Utils.init(this);
        ZKBase.init(this);

        // 2017/12/2  添加 Stetho，可以使用 chrome 方便调试
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
