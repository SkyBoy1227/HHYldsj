package com.henghao.parkland.activity.maintenance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.phoneview.MultiImageSelectorActivity;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.utils.FileUtils;
import com.henghao.parkland.utils.Requester;
import com.henghao.parkland.views.FlowRadioGroup;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;


/**
 * Created by 晏琦云 on 2017/2/13.
 * 植物管护信息录入界面
 */

public class GuanhuSubmitActivity extends ActivityFragmentSupport {
    @InjectView(R.id.tv_code)
    TextView tvCode;
    @InjectView(R.id.tv_type)
    TextView tvType;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.et_personnel)
    EditText etPersonnel;
    @InjectView(R.id.et_content)
    EditText etContent;
    @InjectView(R.id.et_remarks)
    EditText etRemarks;
    @InjectView(R.id.rg_cleaning)
    RadioGroup rgCleaning;
    @InjectView(R.id.rg_plantGrowth)
    RadioGroup rgPlantGrowth;
    @InjectView(R.id.rg_problem)
    FlowRadioGroup rgProblem;
    @InjectView(R.id.tv_before)
    TextView tvBefore;
    @InjectView(R.id.tv_after)
    TextView tvAfter;

    private static final String TAG = "GuanhuSubmitActivity";

    private String maintenanceCode;//养护编号
    private String code;//植物二维码
    private String type;//养护类型
    private String address;//养护地点
    private String time;//养护时间
    private String personnel;//养护人员
    private String content;//养护内容
    private String problem = "无";//问题发现
    private String cleaning = "好";//陆地保洁情况
    private String plantGrowth = "好";//植物长势
    private String remarks;//备注信息

    private Call addInformationCall;//植物管护信息录入请求

    private static final int REQUEST_BEFORE = 0x00;//养护前照片请求
    private static final int REQUEST_AFTER = 0x01;//养护后照片请求
    private ArrayList<String> mSelectPathBefore;//养护前图片地址
    private ArrayList<String> mSelectPathAfter;//养护后图片地址
    private List<File> mFileList;//图片文件集合
    private File mFileBefore;//养护前图片文件
    private File mFileAfter;//养护后图片文件

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FileUtils.COMPRESS_FINISH:
                    mActivityFragmentView.viewLoading(View.GONE);
                    request();
                    break;
                case FileUtils.COMPRESS_PROGRESS://压缩进度
                    mActivityFragmentView.setLoadingText(getString(R.string.compressing) + " " + msg.arg1 + "/" + msg.arg2);
                    break;
            }
        }
    };

    private void request() {
        //访问网络，提交数据
        addInformationCall = Requester.addInformation(maintenanceCode, type, getLoginUid(), getLoginUser().getDeptId(),
                code, address, personnel, content, time, problem,
                cleaning, plantGrowth, remarks, mFileList, addInformationCallback);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_guanhu);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        initWithBar();
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("返回");
        initWithCenterBar();
        mCenterTextView.setText("管护信息");
    }

    /**
     * 初始化数据
     */
    public void initData() {
        Intent intent = getIntent();
        maintenanceCode = intent.getStringExtra("maintenanceCode");
        code = intent.getStringExtra("code");
        type = intent.getStringExtra("type");
        address = intent.getStringExtra("address");
        time = intent.getStringExtra("time");
        tvCode.setText(code);
        tvType.setText(type);
        tvAddress.setText(address);
        tvTime.setText(time);
        ((RadioButton) rgProblem.getChildAt(0)).setChecked(true);
        ((RadioButton) rgPlantGrowth.getChildAt(0)).setChecked(true);
        ((RadioButton) rgCleaning.getChildAt(0)).setChecked(true);
        rgProblem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_problem_one://无
                        problem = "无";
                        break;
                    case R.id.rb_problem_two://有病虫害
                        problem = "有病虫害";
                        break;
                    case R.id.rb_problem_three://有病虫害
                        problem = "施肥";
                        break;
                    case R.id.rb_problem_four://有病虫害
                        problem = "破坏";
                        break;
                    case R.id.rb_problem_five://有病虫害
                        problem = "须冲洗";
                        break;
                }
            }
        });
        rgCleaning.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_cleaning_one:
                        cleaning = "好";
                        break;
                    case R.id.rb_cleaning_two:
                        cleaning = "良好";
                        break;
                    case R.id.rb_cleaning_three:
                        cleaning = "差";
                        break;
                }
            }
        });
        rgPlantGrowth.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_plantGrowth_one:
                        plantGrowth = "好";
                        break;
                    case R.id.rb_plantGrowth_two:
                        plantGrowth = "良好";
                        break;
                    case R.id.rb_plantGrowth_three:
                        plantGrowth = "差";
                        break;
                }
            }
        });
    }

    @OnClick({R.id.tv_submit, R.id.tv_before, R.id.tv_after})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_before:
                addPicBefore();
                break;
            case R.id.tv_after:
                addPicAfter();
                break;
            case R.id.tv_submit:
                if (CheckData()) {
                    mActivityFragmentView.viewLoading(View.VISIBLE, getString(R.string.compressing));
                    mFileList = new ArrayList<>();
                    mFileList.add(mFileBefore);
                    mFileList.add(mFileAfter);
                    FileUtils.compressImagesFromList(context, handler, mFileList);
                }
                break;
        }
    }

    private boolean CheckData() {
        personnel = etPersonnel.getText().toString().trim();
        content = etContent.getText().toString().trim();
        remarks = etRemarks.getText().toString().trim();
        if (ToolsKit.isEmpty(personnel)) {
            msg("请输入养护人员！");
            etPersonnel.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(content)) {
            msg("请输入养护内容！");
            etContent.requestFocus();
            return false;
        }
        if (mFileBefore == null) {
            msg("请添加养护前图片！");
            return false;
        }
        if (mFileAfter == null) {
            msg("请添加养护后图片！");
            return false;
        }
        if (remarks.equals("") || remarks == null) {
            remarks = "无";
        }
        return true;
    }

    /**
     * 添加养护前图片
     */
    private void addPicBefore() {
        // 查看session是否过期
        int selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
//        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        int maxNum = 1;
        Intent picIntent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
        if ((this.mSelectPathBefore != null) && (this.mSelectPathBefore.size() > 0)) {
            picIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, this.mSelectPathBefore);
        }
        startActivityForResult(picIntent, REQUEST_BEFORE);
    }

    /**
     * 添加养护后图片
     */
    private void addPicAfter() {
        // 查看session是否过期
        int selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
//        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        int maxNum = 1;
        Intent picIntent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        picIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
        if ((this.mSelectPathAfter != null) && (this.mSelectPathAfter.size() > 0)) {
            picIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, this.mSelectPathAfter);
        }
        startActivityForResult(picIntent, REQUEST_AFTER);
    }

    /**
     * 植物管护信息录入回调
     */
    private DefaultCallback addInformationCallback = new DefaultCallback() {
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            Toast.makeText(context, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(String response) {
            if (BuildConfig.DEBUG) Log.i(TAG, "onResponse: " + response);
            try {
                Type baseType = new TypeToken<BaseEntity>() {
                }.getType();
                BaseEntity entity = ToolsJson.parseObjecta(response, baseType);
                int errorCode = entity.getErrorCode();
                if (errorCode == 0) {
                    Toast.makeText(context, "管护信息提交成功！", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    msg("添加失败，请重试！");
                }
            } catch (JsonSyntaxException e) {
                Toast.makeText(GuanhuSubmitActivity.this, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (addInformationCall != null && !addInformationCall.isCanceled()) {
            addInformationCall.cancel();
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_BEFORE) {//养护前图片
                if ((resultCode == Activity.RESULT_OK) || (resultCode == Activity.RESULT_CANCELED)) {
                    this.mSelectPathBefore = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (!ToolsKit.isEmpty(this.mSelectPathBefore)) {
                        String filePath = mSelectPathBefore.get(0);
                        String imageName = getImageName(filePath);
                        mFileBefore = new File(filePath);
                        List<String> imgNames = new ArrayList<>();
                        imgNames.add(imageName);
                        tvBefore.setText("图片名：" + imgNames.toString());
                    }
                }
            }
            if (requestCode == REQUEST_AFTER) {//养护后图片
                if ((resultCode == Activity.RESULT_OK) || (resultCode == Activity.RESULT_CANCELED)) {
                    this.mSelectPathAfter = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (!ToolsKit.isEmpty(this.mSelectPathAfter)) {
                        String filePath = mSelectPathAfter.get(0);
                        String imageName = getImageName(filePath);
                        mFileAfter = new File(filePath);
                        List<String> imgNames = new ArrayList<>();
                        imgNames.add(imageName);
                        tvAfter.setText("图片名：" + imgNames.toString());
                    }
                }
            }
        }
    }

    private String getImageName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
