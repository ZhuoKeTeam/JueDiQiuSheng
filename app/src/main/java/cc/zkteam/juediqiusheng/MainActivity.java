package cc.zkteam.juediqiusheng;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;
import cc.zkteam.juediqiusheng.retrofit2.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.bean.CategoryBean;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.jump).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/module/pic/details")
                        .withString("url", "https://modao.cc/uploads3/images/1361/13616427/raw_1508656162.png")
                        .navigation();
            }
        });

        ZKConnectionManager.getInstance().getZKApi().categoryData()
                .enqueue(new ZKCallback<CategoryBean>() {
                    @Override
                    public void onResponse(List<CategoryBean> resultList) {
                        Log.d(TAG, "onResponse() called with: resultList = [" + resultList + "]");
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d(TAG, "onFailure() called with: throwable = [" + throwable + "]");
                    }
                });

        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");


    }
}
