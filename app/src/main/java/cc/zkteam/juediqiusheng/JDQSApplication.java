package cc.zkteam.juediqiusheng;

import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

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

        initLog();
        initStetho();

    }

    private void initStetho() {
        // 2017/12/2  添加 Stetho，可以使用 chrome 方便调试
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

    private void initLog() {
        // 初始化默认的 logger
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .logStrategy(new LogcatLogStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("JDQS")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        // 初始化Logger
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
