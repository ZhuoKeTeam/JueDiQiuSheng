package cc.zkteam.juediqiusheng.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.ad.ZKAD;
import cc.zkteam.juediqiusheng.managers.ZKConnectionManager;
import cc.zkteam.juediqiusheng.utils.L;
import dagger.android.AndroidInjection;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
        ZKAD.initHFAD(this, true);
    }

    @Override
    protected void initListener() {
        webView.setWebViewClient(new WebViewClient() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

                try {
                    String url = request.getUrl().toString();
                    if (url.contains("jpg") || url.contains("png") || url.contains("gif")) {
                        WebResourceResponse webResourceResponse = null;

                        OkHttpClient client = new OkHttpClient();
                        Request requestReq = new Request.Builder()
                                    .url(url)
                                    .build();

                        webResourceResponse = getOkHttpWebResourceResponse(url, client, requestReq);
                        setWebResourceResponseHeader(webResourceResponse);
                        return webResourceResponse;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println(url);
                String[] split = null;
                if (url.contains("jpg") || url.contains("png") || url.contains("gif")) {
                    //图片链接
                    try {
                        split = url.split("\\?");
                        String jumpUrl = split[1];
                        ARouter.getInstance().build("/module/pic/details").withString("url", jumpUrl).navigation();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                } else if (url.contains("video")) {
                    //视频链接
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    startActivity(intent);
                }
//                view.loadUrl(url);
                return true;
            }
        });
    }


    private static void logD(String msg) {
        ZKAD.logD(msg);
    }

    @Override
    protected void initData() {

        RelativeLayout adViewContent = findViewById(R.id.ad_content_view_new);

        String adPlaceID = "6294768";//广告位 ID
        AdView adView = new AdView(this, adPlaceID);
        adView.setListener(new AdViewListener() {
            @Override
            public void onAdReady(AdView adView) {
                logD("onAdReady->");
            }

            @Override
            public void onAdShow(JSONObject jsonObject) {
                logD("onAdShow->");
            }

            @Override
            public void onAdClick(JSONObject jsonObject) {
                logD("onAdClick->");
            }

            @Override
            public void onAdFailed(String s) {
                logD("onAdFailed->" + s);
            }

            @Override
            public void onAdSwitch() {
                logD("onAdSwitch->");
            }

            @Override
            public void onAdClose(JSONObject jsonObject) {
                logD("onAdClose->");
            }
        });

//        DisplayMetrics dm = new DisplayMetrics();
//        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
//        int winW = dm.widthPixels;
//        int winH = dm.heightPixels;
//        int width = Math.min(winW, winH);
//        int height = width * 3 / 20;
//        //把横幅 view 添加到自己的 viewGroup 组件中，必须写，才可以展示横幅广告
//        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(width, height);
//        rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adViewContent.addView(adView);










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


    /**
     * 兼容在 5.0 手机上无法加载字体库和其他资源的问题。
     */
    private void setWebResourceResponseHeader(WebResourceResponse webResourceResponse) {
        if (webResourceResponse != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Map<String, String> map = webResourceResponse.getResponseHeaders();

            if (map == null) {
                map = new HashMap<>();
            }

            map.put("Access-Control-Allow-Origin", "*");
            webResourceResponse.setResponseHeaders(map);
        }
    }

    private WebResourceResponse getOkHttpWebResourceResponse(String url, OkHttpClient client, Request request) {
        WebResourceResponse webResourceResponse = null;

        if (request != null) {
            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    InputStream inputStream = responseBody.byteStream();

                    if (inputStream != null) {
                        L.i("contentType : " + responseBody.contentType().toString());

                        String type = null;
                        if (responseBody.contentType() != null) {
                            type = responseBody.contentType().toString();

                            if (type.contains(";"))
                                type = type.substring(0, type.indexOf(";"));
                        }

                        webResourceResponse = new WebResourceResponse(type, "utf-8", inputStream);
                        L.d("webResourceResponse1: " + response.toString());
                    } else {
                        L.e("关于 responseBody:" + responseBody + "url:" + url + ",  type: " +
                                responseBody.contentType());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return webResourceResponse;
    }

}
