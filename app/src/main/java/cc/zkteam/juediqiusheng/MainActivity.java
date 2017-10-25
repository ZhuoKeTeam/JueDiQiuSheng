package cc.zkteam.juediqiusheng;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.android.arouter.launcher.ARouter;

import cc.zkteam.juediqiusheng.lifecycle.components.WQText;

public class MainActivity extends AppCompatActivity {

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

        WQText wqText = new WQText(this.getLifecycle());
    }
}
