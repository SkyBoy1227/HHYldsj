package com.henghao.parkland.activity.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.utils.Requester;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;


/**
 * Created by 晏琦云 on 2017/2/15.
 * 植物信息录入界面
 */

public class TreeMessageActivity extends ActivityFragmentSupport {


    @InjectView(R.id.tv_code_treemessage)
    TextView tvCode;
    @InjectView(R.id.et_name_treemessage)
    EditText etName;
    @InjectView(R.id.et_purpose_treemessage)
    EditText etPurpose;
    @InjectView(R.id.et_specifications_treemessage)
    EditText etSpecifications;
    @InjectView(R.id.tv_address_treemessage)
    TextView tvAddress;
    @InjectView(R.id.tv_time_treemessage)
    TextView tvTime;

    private static final String TAG = "TreeMessageActivity";

    private String name;//植物名称
    private String code;//植物编号
    private String purpose;//植物用途
    private String specifications;//植物规格
    private String address;//种植地点
    private String time;//录入时间
    private Call call;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_treemessage);
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
        super.initWidget();
        initWithBar();
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("返回");
        initWithCenterBar();
        mCenterTextView.setText("植物信息录入");
    }

    /**
     * 初始化数据
     */
    public void initData() {
        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        tvCode.setText(code);//获取植物编号
        address = intent.getStringExtra("address");
        tvAddress.setText(address);//获取种植地点
        //获取录入时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        time = dateFormat.format(date);//格式化时间
        tvTime.setText(time);
    }

    @OnClick({R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                name = etName.getText().toString().trim();//植物名称
                purpose = etPurpose.getText().toString().trim();//植物用途
                specifications = etSpecifications.getText().toString().trim();//植物规格
                if (checkData()) {
                    //访问网络
                    call = Requester.addPlantInformation(code, name, purpose, specifications, address, time, getLoginUser().getDeptId(), callback);
                }
                break;
        }
    }

    private boolean checkData() {
        if (ToolsKit.isEmpty(name)) {
            msg("植物名称不能为空！");
            etName.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(purpose)) {
            msg("植物用途不能为空！");
            etPurpose.requestFocus();
            return false;
        }
        if (ToolsKit.isEmpty(specifications)) {
            msg("植物规格不能为空！");
            etSpecifications.requestFocus();
            return false;
        }
        return true;
    }

    private DefaultCallback callback = new DefaultCallback() {
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            Toast.makeText(context, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(String response) {
            if (BuildConfig.DEBUG) Log.d(TAG, "onSuccess: " + response);
            try {
                // 解析返回的json字符串
                Type baseType = new TypeToken<BaseEntity>() {
                }.getType();
                BaseEntity entity = ToolsJson.parseObjecta(response, baseType);
                int errorCode = entity.getErrorCode();
                if (errorCode == 0) {//录入成功
                    Toast.makeText(context, "录入成功！", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(context, entity.getMsg(), Toast.LENGTH_SHORT).show();
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
    }
}
