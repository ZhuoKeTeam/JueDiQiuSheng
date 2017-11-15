package cc.zkteam.dagger2.ui.detail;

import cc.zkteam.dagger2.data.ApiService;
import cc.zkteam.dagger2.ui.detail.fragment.DetailFragmentComponent;
import dagger.Module;
import dagger.Provides;

/**
 * Created by WangQing on 2017/11/15.
 */
@Module(subcomponents = {DetailFragmentComponent.class})
public class DetailActivityModule {

    @Provides
    DetailView provideDetailView(DetailActivity detailActivity) {
        return detailActivity;
    }

    @Provides
    DetailPresenter provideDetailPresenter(DetailView detailView, ApiService apiService) {
        return new DetailPresenterImpl(detailView, apiService);
    }

}
