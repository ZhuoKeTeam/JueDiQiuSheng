package cc.zkteam.juediqiusheng.fragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cc.zkteam.juediqiusheng.Constant;
import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.utils.L;

import static android.webkit.WebView.setWebContentsDebuggingEnabled;

public class WebViewDialogFragment extends DialogFragment {

    /**
     * 用volatile修饰的变量，
     * 线程在每次使用变量的时候，都会读取变量修改后的最的值。
     * volatile很容易被误用，用来进行原子性操作。
     */
    private static volatile WebViewDialogFragment dialog = null;

    public WebViewDialogFragment() {
    }

    /**
     * 单例模式：创建  Fragment：
     *
     * @return
     */
    public static WebViewDialogFragment getInstance() {
        if (dialog == null) {
            synchronized (WebViewDialogFragment.class) {
                if (dialog == null) {
                    dialog = new WebViewDialogFragment();
                }
            }
        }
        return dialog;
    }

    /**
     * 使用   onCreateView 创建   对话框的样式  使用自定义视图
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * 先设置   无标题样式的  对话框
         */
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.dialog_webview_fragment, container, false);

        WebView webView = (WebView) view.findViewById(R.id.webView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            http://blog.csdn.net/zhulin2609/article/details/51437821  基于webkit核心的webview端调试
            setWebContentsDebuggingEnabled(true);
        }

        initWebSettings(webView);

        webView.loadUrl(Constant.ZKTEAM_XIAN_LIAO_ME_URL);
        return view;
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
