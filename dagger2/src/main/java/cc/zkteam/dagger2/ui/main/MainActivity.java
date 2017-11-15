package cc.zkteam.dagger2.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import cc.zkteam.dagger2.R;
import cc.zkteam.dagger2.ui.detail.DetailActivity;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = "MainActivity";

    @Inject
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter.loadMain();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DetailActivity.class));
            }
        });
    }

    @Override
    public void onMainLoaded() {
        Log.d(TAG, "onMainLoaded() called");
    }
}
