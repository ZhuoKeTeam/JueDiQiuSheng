package cc.zkteam.juediqiusheng.activity;

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

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.bean.CategoryBean;
import cc.zkteam.juediqiusheng.lifecycle.components.demo.ZKLiveData;
import cc.zkteam.juediqiusheng.lifecycle.components.demo.ZKText;
import cc.zkteam.juediqiusheng.lifecycle.components.demo.ZKViewModule;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.retrofit2.ZKCallback;

public class MainActivity extends AppCompatActivity {

  public static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView textView = (TextView) findViewById(R.id.jump);

    textView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ARouter.getInstance()
            .build("/modules/recommend/recommendation")
            .navigation();
      }
    });

    // 演示如何快速使用网络请求
    testRequestApi();

    // 演示 如何使用 LifeComponents
    testLifeComponents(textView);
  }

  /**
   * 演示快速使用测试 Api
   */
  private void testRequestApi() {
    ZKConnectionManager.getInstance().getZKApi().categoryData()
        .enqueue(new ZKCallback<CategoryBean>() {

          @Override
          public void onResponse(CategoryBean result) {

          }

          @Override
          public void onFailure(Throwable throwable) {

          }
        });
    Log.d(TAG, "testRequestApi() called");
  }

  /**
   * ------------------------------------------
   * -----可以通过 Log 查看相关状态------------
   * ------------------------------------------
   */
  private void testLifeComponents(final TextView textView) {
    // 对 LiveData 进行测试
    LiveData<String> zkLiveData = new ZKLiveData();
//        //这个方法向LiveData中添加观察者，LiveData 则通过 LifecycleOwner 来判断，当前传入的观察者是否是活跃的
//        // 也就是当前 UI 是否可见
    zkLiveData.observe(this, new Observer<String>() {
      @Override
      public void onChanged(@Nullable String s) {
        // update
        // 当 LiveData 中通过 setValue() 修改了数据时，
        // 这里将收到修改后的数据
        Log.d(TAG, "LiveData onChanged() called with: s = [" + s + "]");
      }
    });

    // 对 组件 的生命周期进行测试
    new ZKText(getLifecycle());

    // 对 ZKViewModule 进行测试
    ZKViewModule module = ViewModelProviders.of(this).get(ZKViewModule.class);
    module.getText().observe(this, new Observer<String>() {
      @Override
      public void onChanged(@Nullable String s) {
//                 update UI
        Log.d(TAG, "WQViewModule onChanged() called with: s = [" + s + "]");
        if (textView != null) {
          textView.setText(s);
        }
      }
    });
  }
}
