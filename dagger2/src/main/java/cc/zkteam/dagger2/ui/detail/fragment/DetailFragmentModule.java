package cc.zkteam.dagger2.ui.detail.fragment;

import dagger.Module;
import dagger.Provides;

/**
 * DetailFragmentModule
 * Created by WangQing on 2017/11/15.
 */
@Module
public class DetailFragmentModule {

    @Provides
    DetailFragmentView provideDetailFragmentView(DetailFragment detailFragment) {
        return detailFragment;
    }

}
