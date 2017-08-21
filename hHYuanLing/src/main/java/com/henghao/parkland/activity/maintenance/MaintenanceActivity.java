package com.henghao.parkland.activity.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.utils.tools.ToolsJson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.utils.Requester;

import java.lang.reflect.Type;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;


/**
 * Created by 晏琦云 on 2017/2/10.
 * 植物养护信息录入界面
 */

public class MaintenanceActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_code_maintenance)
    TextView tvCode;
    @InjectView(R.id.tv_state_maintenance)
    TextView tvState;
    @InjectView(R.id.tv_time_maintenance)
    TextView tvTime;
    @InjectView(R.id.tv_address_maintenance)
    TextView tvAddress;

    private static final String TAG = "MaintenanceActivity";

    private String code;//植物编号
    private String state;//养护状态
    private String time;//养护时间
    private String address;//养护地点
    private Call addMaintenanceInformationCall;//植物养护信息录入请求

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_maintenance);
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
        mCenterTextView.setText("养护信息");
    }

    public void initData() {
        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        state = intent.getStringExtra("state");
        time = intent.getStringExtra("time");
        address = intent.getStringExtra("address");
        tvCode.setText(code);
        tvState.setText(state);
        tvTime.setText(time);
        tvAddress.setText(address);
    }


    @OnClick({R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                // 访问网络
                addMaintenanceInformationCall = Requester.addMaintenanceInformation(code, state, time, address, getLoginUid(), getLoginUser().getDeptId(), addMaintenanceInformationCallback);
                break;
        }
    }

    /**
     * 植物养护信息录入回调
     */
    private DefaultCallback addMaintenanceInformationCallback = new DefaultCallback() {
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
                if (errorCode == 0) {//养护成功
                    Toast.makeText(context, "养护成功！", Toast.LENGTH_SHORT).show();
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
        if (addMaintenanceInformationCall != null && !addMaintenanceInformationCall.isCanceled()) {
            addMaintenanceInformationCall.cancel();
        }
    }
}
