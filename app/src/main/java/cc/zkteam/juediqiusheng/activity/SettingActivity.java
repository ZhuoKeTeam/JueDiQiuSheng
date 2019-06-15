package cc.zkteam.juediqiusheng.activity;

import com.blankj.utilcode.util.ActivityUtils;

import butterknife.OnClick;
import cc.zkteam.juediqiusheng.R;

public class SettingActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews() {
        setTitle("设置");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.cons_about)
    public void onViewClicked() {
        ActivityUtils.startActivity(UsActivity.class);
    }
}
