package cc.zkteam.juediqiusheng.activity;

import android.content.Intent;
import android.webkit.WebView;

import cc.zkteam.juediqiusheng.R;

public class SortDetailActivity extends BaseActivity {
    WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sort_detail;
    }

    @Override
    protected void initViews() {
        setTitle("分类详情");
        mWebView = findViewById(R.id.wb_sort);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null)
            mWebView.loadUrl(intent.getStringExtra("url"));

    }
}
