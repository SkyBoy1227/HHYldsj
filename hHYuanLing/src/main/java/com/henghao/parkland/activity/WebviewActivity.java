package com.henghao.parkland.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.henghao.parkland.utils.webview.CommonWebChromeClient;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WebviewActivity extends ActivityFragmentSupport {

    @InjectView(R.id.webview)
    WebView webView;

    private String url;
    private String title;
    private CommonWebChromeClient webChromeClient;// 兼容android所有版本的WebChromeClient类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.common_tecentwebview);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        webChromeClient = new CommonWebChromeClient(this);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        initWidget();
        initView();
    }

    @Override
    public void initWidget() {
        initWithBar();
        this.mLeftTextView.setVisibility(View.VISIBLE);
        this.mLeftTextView.setText("返回");
        initWithCenterBar();
        this.mCenterTextView.setVisibility(View.VISIBLE);
        this.mCenterTextView.setText(title);
    }

    private void initView() {
        webView.getSettings().setJavaScriptEnabled(true);//启用JavaScript
        webView.getSettings().setSupportZoom(true);//启用页面的缩放
        webView.getSettings().setBuiltInZoomControls(true);//启用页面缩放的按钮
        webView.getSettings().setDisplayZoomControls(false);//隐藏缩放按钮
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDefaultFontSize(15);//默认字体大小
        webView.getSettings().setDefaultTextEncodingName("UTF_8");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.setWebChromeClient(webChromeClient);
        //打开本包内asset目录下的1.html文件
        if (url.contains("http://")) {
            webView.loadUrl(url);
        } else {
            webView.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
            webView.loadData(url, "text/html; charset=UTF-8", null);//这种写法可以正确解码
        }
    }

    /*
     * 用onkeyDown监听不到返回键
     * (non-Javadoc)
     * @see android.app.Activity#dispatchKeyEvent(android.view.KeyEvent)
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() != KeyEvent.ACTION_UP) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CommonWebChromeClient.FILECHOOSER_RESULTCODE) {
            if (Build.VERSION.SDK_INT >= 21) {
                webChromeClient.onActivityResultAboveFive(resultCode, data);
            } else {
                webChromeClient.onActivityResult(resultCode, data);
            }
        }
    }
}
