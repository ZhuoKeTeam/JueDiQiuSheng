package cc.zkteam.dagger2.ui.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import cc.zkteam.dagger2.R;
import cc.zkteam.dagger2.ui.detail.fragment.DetailFragment;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class DetailActivity extends AppCompatActivity implements DetailView, HasSupportFragmentInjector {

    private static final String TAG = "DetailActivity";

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    DetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detailPresenter.loadDetail();

        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, DetailFragment.newInstance())
                    .commitAllowingStateLoss();
    }

    @Override
    public void onDetailLoaded() {

        Log.d(TAG, "onDetailLoaded: ");

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
