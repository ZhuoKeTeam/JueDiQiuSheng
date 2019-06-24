package cc.zkteam.juediqiusheng.activity;

import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;

import butterknife.OnClick;
import cc.zkteam.juediqiusheng.R;

/**
 * Created by zhangshan on 2019-06-15 15:34.
 */
public class MineActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    protected void initViews() {
        setTitle("我的");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.cons_secret, R.id.cons_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cons_secret:
                String url = "file:///android_asset/about_rules.html";
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", url);
                mContext.startActivity(intent);
                break;
            case R.id.cons_setting:
                ActivityUtils.startActivity(SettingActivity.class);
                break;
        }
    }
}
