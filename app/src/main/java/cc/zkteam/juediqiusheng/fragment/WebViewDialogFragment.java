package cc.zkteam.juediqiusheng.fragment;
import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import com.networkbench.agent.impl.instrumentation.NBSWebChromeClient;
import cc.zkteam.juediqiusheng.Constant;
import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.utils.L;
import static android.webkit.WebView.setWebContentsDebuggingEnabled;
public class WebViewDialogFragment extends DialogFragment {
    private static volatile WebViewDialogFragment dialog = null;
    WebView webView;
    public WebViewDialogFragment() {
    }
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
    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;
        window.setAttributes(windowParams);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Window window = getDialog().getWindow();
        View view = inflater.inflate(R.layout.dialog_webview_fragment,  ((ViewGroup) window.findViewById(android.R.id.content)), false);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(-1, -2);
        webView = view.findViewById(R.id.webView);
        RelativeLayout azkRefreshLayout = view.findViewById(R.id.root_view);
        azkRefreshLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(true);
        }
        initWebSettings(webView);
        getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager
                .LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        webView.loadUrl(Constant.ZKTEAM_XIAN_LIAO_ME_URL);
        return view;
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSettings(final WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); 
        webSettings.setLoadWithOverviewMode(true); 
        webSettings.setSupportZoom(true); 
        webSettings.setBuiltInZoomControls(true); 
        webSettings.setDisplayZoomControls(false); 
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); 
        webSettings.setAllowFileAccess(true); 
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); 
        webSettings.setLoadsImagesAutomatically(true); 
        webSettings.setDefaultTextEncodingName("utf-8");
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
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                NBSWebChromeClient.initJSMonitor(view, newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
    }
}
