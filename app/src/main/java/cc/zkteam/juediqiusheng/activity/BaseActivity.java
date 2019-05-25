package cc.zkteam.juediqiusheng.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yw.game.floatmenu.FloatLogoMenu;

import cc.zkteam.juediqiusheng.R;

/**
 * BaseActivity
 * <p>
 * Created by WangQing on 2017/10/28.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    protected TextView mTitle;
    protected Context mContext;
    public FloatLogoMenu mFloatMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
        initToolbar();
        initViews();
        initListener();
        initData();
    }



    //获取资源ID
    protected abstract int getLayoutId();

    protected abstract void initViews();

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        mTitle = findViewById(R.id.tv_title);
        if (mToolbar != null) {
            if (mToolbar.getVisibility() == View.GONE)
                mToolbar.setVisibility(View.VISIBLE);
            mToolbar.setNavigationIcon(R.mipmap.arrow_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLeftClick();
                }
            });
        }

    }
    //设置标题
    protected void setTitle(String title) {
        mTitle.setText(title);
    }
    protected abstract void initListener();

    protected abstract void initData();

    protected void onLeftClick() {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

//        if (mFloatMenu == null) {
//            mFloatMenu = ZKFloatMenuManager.getInstance().getFloatLogoMenu(this, getFragmentManager());
//        }
//        try {
//            mFloatMenu.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
//        ZKFloatMenuManager.getInstance().hideFloat();
    }

    @Override
    protected void onDestroy() {
//        ZKFloatMenuManager.getInstance().destroyFloat();
        super.onDestroy();
    }



}
