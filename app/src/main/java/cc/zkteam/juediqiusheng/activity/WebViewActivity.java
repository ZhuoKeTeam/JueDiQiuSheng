package cc.zkteam.juediqiusheng.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.launcher.ARouter;

import javax.inject.Inject;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.utils.L;
import dagger.android.AndroidInjection;
import okhttp3.OkHttpClient;

@SuppressLint("SetJavaScriptEnabled")

public class WebViewActivity extends BaseActivity {
    WebView webView;
    private WebSettings mWebSettings;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initViews() {
        setTitle("详情");
        webView = findViewById(R.id.wb_sort);
    }

    @Override
    protected void initListener() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String[] split = url.split("\\?");
                ARouter.getInstance().build("/module/pic/details").withString("url", split[1]).navigation();
//                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        mWebSettings = webView.getSettings();
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        //设置自适应屏幕，两者合用
        mWebSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        mWebSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        mWebSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        mWebSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //设置字体大小
        Resources res = getResources();
        int fontSize = (int) res.getDimension(R.dimen.txtSize);
        mWebSettings.setDefaultFontSize(fontSize);
        //其他细节操作
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        mWebSettings.setAllowFileAccess(true); //设置可以访问文件
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        mWebSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        mWebSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

//        http://www.cnblogs.com/yuzhongwusan/p/4211681.html  android 中 webview 怎么用 localStorage?
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = webView.getContext().getCacheDir().getAbsolutePath();
        mWebSettings.setAppCachePath(appCachePath);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAppCacheEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                NBSWebChromeClient.initJSMonitor(view, newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });


    }

    public static final String TAG = "MainActivity";

    @Inject
    ZKConnectionManager zkConnectionManager1;
    @Inject
    ZKConnectionManager zkConnectionManager2;

    @Inject
    OkHttpClient okHttpClient1;

    @Inject
    OkHttpClient okHttpClient2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        Log.i(TAG, "WebViewActivity: " + zkConnectionManager1.toString());
        Log.i(TAG, "WebViewActivity: " + zkConnectionManager2.toString());

        Log.i(TAG, "OkHttpClient: " + okHttpClient1.toString());
        Log.i(TAG, "OkHttpClient: " + okHttpClient2.toString());

    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebSettings.setJavaScriptEnabled(true);
        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra("url");
            L.i("当前加载的页面地址是： " + url);
            webView.loadUrl(url);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWebSettings.setJavaScriptEnabled(false);

    }


}
