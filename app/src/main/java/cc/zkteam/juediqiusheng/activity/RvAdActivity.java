package cc.zkteam.juediqiusheng.activity;

import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.module.answer.QuestionFragment;

public class RvAdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_ad);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rv_ad;
    }

    @Override
    protected void initViews() {
        getSupportFragmentManager()    //
                .beginTransaction()
                .add(R.id.fragment_container, QuestionFragment.newInstance())   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    //退出时的时间
    private long mExitTime;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showShort("再按一次退出");
            mExitTime = System.currentTimeMillis();
//            ZKAD.showCPAD(this);
        } else {
            super.onBackPressed();
        }
    }
}
