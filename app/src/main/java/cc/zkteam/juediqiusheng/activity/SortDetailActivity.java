package cc.zkteam.juediqiusheng.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.utils.L;

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

        initWebSettings(mWebView);

        Intent intent = getIntent();
        if (intent != null)
            mWebView.loadUrl(intent.getStringExtra("url"));


    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSettings(WebView webView) {
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

//        http://www.cnblogs.com/yuzhongwusan/p/4211681.html  android 中 webview 怎么用 localStorage?
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024*1024*8);
        String appCachePath = webView.getContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                L.i("当前的 shouldOverrideUrlLoading Url:" + url);
                view.loadUrl(url);
                return true;
            }
        });
    }
}
