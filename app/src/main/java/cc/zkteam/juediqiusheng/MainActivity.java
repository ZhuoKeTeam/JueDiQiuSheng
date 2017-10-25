package cc.zkteam.juediqiusheng;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.zkteam.juediqiusheng.lifecycle.components.WQLiveData;
import cc.zkteam.juediqiusheng.lifecycle.components.WQText;
import cc.zkteam.juediqiusheng.lifecycle.components.WQViewModule;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "WQLiveData";
    @BindView(R.id.jump)
    TextView jump;

//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 0 ){
//
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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


        LiveData<String> wqLiveData = new WQLiveData();
//        //这个方法向LiveData中添加观察者，LiveData 则通过 LifecycleOwner 来判断，当前传入的观察者是否是活跃的
//        // 也就是当前 UI 是否可见
        wqLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // update
                // 当 LiveData 中通过 setValue() 修改了数据时，
                // 这里将收到修改后的数据
                Log.d(TAG, "LiveData onChanged() called with: s = [" + s + "]");
            }
        });

        WQViewModule module = ViewModelProviders.of(this).get(WQViewModule.class);
        module.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // update UI
                Log.d(TAG, "WQViewModule onChanged() called with: s = [" + s + "]");
                jump.setText(s);
            }
        });


    }
}
