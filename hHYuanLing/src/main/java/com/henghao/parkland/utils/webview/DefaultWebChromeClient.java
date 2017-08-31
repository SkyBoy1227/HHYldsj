package com.henghao.parkland.utils.webview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.ValueCallback;

import java.io.File;

/**
 * Created by ASUS on 2017/8/31.
 * 兼容android5.0以下版本的WebChromeClient类
 */

public class DefaultWebChromeClient extends BaseWebChromeClient {
    private ValueCallback<Uri> mUploadMessage;
    private Activity mActivity;

    public DefaultWebChromeClient(Activity activity) {
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
//        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//        i.addCategory(Intent.CATEGORY_OPENABLE);
//        i.setType("image/*");
//        mActivity.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        mActivity.startActivityForResult(createDefaultOpenableIntent(), FILECHOOSER_RESULTCODE);
    }

    @Override
    public void onActivityResult(int resultCode, Intent data) {
        if (null == mUploadMessage) {
            return;
        }
        Uri result = data == null || resultCode != Activity.RESULT_OK ? null
                : data.getData();
        File cameraFile = new File(mCameraFilePath);
        if (cameraFile.exists()) {
            result = Uri.fromFile(cameraFile);
            // Broadcast to the media scanner that we have a new photo
            // so it will be added into the gallery for the user.
            mActivity.sendBroadcast(
                    new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
        }
        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }
}
