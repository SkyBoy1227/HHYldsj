package com.henghao.parkland.utils.webview;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.henghao.parkland.Constant;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ASUS on 2017/9/12.
 * 兼容android所有版本的WebChromeClient类
 */

public class CommonWebChromeClient extends WebChromeClient {
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveFive;
    private Activity mActivity;
    public static final int FILECHOOSER_RESULTCODE = 10000;
    public static String mCameraFilePath;

    public CommonWebChromeClient(Activity activity) {
        this.mActivity = activity;
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(mUploadMessage, "", "");
    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
        openFileChooser(mUploadMessage, acceptType, "");
    }

    //For Android 4.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        mUploadMessage = uploadMsg;
        mActivity.startActivityForResult(createDefaultOpenableIntent(), FILECHOOSER_RESULTCODE);
    }

    //For Android 5.0+
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
        mUploadCallbackAboveFive = valueCallback;
        mActivity.startActivityForResult(createDefaultOpenableIntent(), FILECHOOSER_RESULTCODE);
        return true;
    }

    public Intent createDefaultOpenableIntent() {
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

    protected Intent createCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mCameraFilePath = Constant.CACHE_DIR_PATH + File.separator +
                System.currentTimeMillis() + ".jpg";
        Uri imageUri = Uri.fromFile(new File(mCameraFilePath));
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        return cameraIntent;
    }

    private Intent createCamcorderIntent() {
        return new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
    }

    private Intent createSoundRecorderIntent() {
        return new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
    }

    public void onActivityResult(int resultCode, Intent data) {
        if (null == mUploadMessage) return;
        Uri result = data == null || resultCode != RESULT_OK ? null
                : data.getData();
        if (result == null && data == null && resultCode == RESULT_OK) {
            File cameraFile = new File(mCameraFilePath);
            if (cameraFile.exists()) {
                result = Uri.fromFile(cameraFile);
                // Broadcast to the media scanner that we have a new photo
                // so it will be added into the gallery for the user.
                mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
            }
        }
        mUploadMessage.onReceiveValue(result);
        mUploadMessage = null;
    }

    public void onActivityResultAboveFive(int resultCode, Intent data) {
        if (null == mUploadCallbackAboveFive) {
            return;
        }
        Uri[] results = null;
        if (resultCode == RESULT_OK) {
            if (data != null) {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    int itemCount = clipData.getItemCount();
                    results = new Uri[itemCount];
                    for (int i = 0; i < itemCount; i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            } else {
                // 调用照相机
                Uri result = null;
                File cameraFile = new File(mCameraFilePath);
                if (cameraFile.exists()) {
                    result = Uri.fromFile(cameraFile);
                    results = new Uri[]{result};
                    mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
                }
            }
        }
        mUploadCallbackAboveFive.onReceiveValue(results);
        mUploadCallbackAboveFive = null;
    }
}
