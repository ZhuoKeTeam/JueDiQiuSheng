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
import cc.zkteam.juediqiusheng.ad.ZKAD;
public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    protected TextView mTitle;
    protected Context mContext;
    public FloatLogoMenu mFloatMenu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
        initToolbar();
        initViews();
        initListener();
        initData();
    }
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
    }
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onDestroy() {
        ZKAD.destory();
        super.onDestroy();
    }
}
