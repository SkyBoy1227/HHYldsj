package com.henghao.parkland.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WebviewActivity extends ActivityFragmentSupport {

    @InjectView(R.id.webview)
    WebView webView;

    private static final int FILECHOOSER_RESULTCODE = 0x0011;

    private String url;
    private String title;
    private ValueCallback mUploadMessage;
    private String mCameraFilePath;

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
        webView.setWebChromeClient(new WebChromeClient() {
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                if (mUploadMessage != null) return;
                mUploadMessage = uploadMsg;
                startActivityForResult(createDefaultOpenableIntent(), FILECHOOSER_RESULTCODE);
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, "");
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg, acceptType);
            }
        });
        //打开本包内asset目录下的1.html文件
        if (url.contains("http://")) {
            webView.loadUrl(url);
        } else {
            webView.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
            webView.loadData(url, "text/html; charset=UTF-8", null);//这种写法可以正确解码
        }
    }

    private Intent createDefaultOpenableIntent() {
        // Create and return a chooser with the default OPENABLE
        // actions including the camera, camcorder and sound
        // recorder where available.
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");

        Intent chooser = createChooserIntent(createCameraIntent(), createCamcorderIntent(),
                createSoundRecorderIntent());
        chooser.putExtra(Intent.EXTRA_INTENT, i);
        return chooser;
    }

    private Intent createChooserIntent(Intent... intents) {
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        chooser.putExtra(Intent.EXTRA_TITLE, "File Chooser");
        return chooser;
    }

    private Intent createCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mCameraFilePath = Constant.CACHE_DIR_PATH + File.separator +
                System.currentTimeMillis() + ".jpg";
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));
        return cameraIntent;
    }

    private Intent createCamcorderIntent() {
        return new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
    }

    private Intent createSoundRecorderIntent() {
        return new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
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
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;
            Uri result = data == null || resultCode != RESULT_OK ? null
                    : data.getData();
            File cameraFile = new File(mCameraFilePath);
            if (cameraFile.exists()) {
                result = Uri.fromFile(cameraFile);
                // Broadcast to the media scanner that we have a new photo
                // so it will be added into the gallery for the user.
                sendBroadcast(
                        new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
            }
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }
}
