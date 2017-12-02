package cc.zkteam.juediqiusheng.di;

import cc.zkteam.juediqiusheng.JDQSApplication;
import cc.zkteam.juediqiusheng.di.annotations.SingletonGlobal;
import cc.zkteam.juediqiusheng.ui.main.test.ZKModule;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * AppComponent: Dagger2 的全局注册组件
 * Created by WangQing on 2017/11/17.
 */

@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class,
        ZKModule.class
})
@SingletonGlobal
public interface AppComponent extends AndroidInjector<JDQSApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<JDQSApplication>{}
}
