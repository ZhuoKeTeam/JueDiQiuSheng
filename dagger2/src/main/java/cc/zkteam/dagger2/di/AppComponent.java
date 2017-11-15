package cc.zkteam.dagger2.di;

import cc.zkteam.dagger2.WQApplication;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * AppComponent
 * Created by WangQing on 2017/11/15.
 */
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(WQApplication application);

        AppComponent builder();
    }

    void inject(WQApplication app);
}
