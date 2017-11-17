package cc.zkteam.juediqiusheng.di;

import cc.zkteam.juediqiusheng.JDQSApplication;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * AppComponent: Dagger2 的全局注册组件
 * Created by WangQing on 2017/11/17.
 */
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class,})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(JDQSApplication application);

        AppComponent builder();
    }

    void inject(JDQSApplication app);
}
