package com.henghao.parkland.utils.webview;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.henghao.parkland.Constant;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ASUS on 2017/8/31.
 * 兼容android5.0及以上的WebChromeClient类
 */

public class WebChromeClientAboveFive extends BaseWebChromeClient {
    private ValueCallback<Uri[]> mUploadCallbackAboveFive;
    private Activity mActivity;

    public WebChromeClientAboveFive(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 兼容5.0及以上
     *
     * @param webView
     * @param valueCallback
     * @param fileChooserParams
     * @return
     */
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, android.webkit.WebChromeClient.FileChooserParams fileChooserParams) {
        mUploadCallbackAboveFive = valueCallback;
//        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//        i.addCategory(Intent.CATEGORY_OPENABLE);
//        i.setType("*/*");
//        mActivity.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        mActivity.startActivityForResult(createDefaultOpenableIntent(), FILECHOOSER_RESULTCODE);
        return true;
    }

    //创建文件夹包装图片
    private File createImageFile() throws IOException {
        mCameraFilePath = Constant.CACHE_DIR_PATH + File.separator +
                System.currentTimeMillis() + ".jpg";
        File storageDir = new File(mCameraFilePath);
        return storageDir;
    }

    @Override
    protected Intent createCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(mActivity.getPackageManager()) != null) {
            Uri imageUri = null;
            try {
                imageUri = Uri.fromFile(createImageFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }
        return cameraIntent;
    }

    @Override
    public void onActivityResult(int resultCode, Intent data) {
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
                    File file = new File(mCameraFilePath);
                    Uri localUri = Uri.fromFile(file);
                    Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
                    mActivity.sendBroadcast(localIntent);
                    result = Uri.fromFile(file);
                    mUploadCallbackAboveFive.onReceiveValue(new Uri[]{result});
                    mUploadCallbackAboveFive = null;
                    return;
                }
            }
        }
        mUploadCallbackAboveFive.onReceiveValue(results);
        mUploadCallbackAboveFive = null;
        return;
    }
}
