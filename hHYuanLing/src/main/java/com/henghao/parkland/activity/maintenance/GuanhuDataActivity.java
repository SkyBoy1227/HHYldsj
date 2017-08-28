package com.henghao.parkland.activity.maintenance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.utils.tools.ToolsJson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.ManagementInfoEntity;
import com.henghao.parkland.utils.Requester;
import com.henghao.parkland.utils.ScanImageUtils;
import com.lidroid.xutils.BitmapUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

import static com.henghao.parkland.R.id.iv_after;
import static com.henghao.parkland.R.id.iv_before;
import static com.henghao.parkland.R.id.tv_address;
import static com.henghao.parkland.R.id.tv_time;

/**
 * 管护信息详情界面
 */
public class GuanhuDataActivity extends ActivityFragmentSupport implements View.OnClickListener {
    /**
     * 植物编号
     */
    @InjectView(R.id.tv_code)
    TextView tvCode;
    /**
     * 养护地址
     */
    @InjectView(tv_address)
    TextView tvAddress;
    /**
     * 养护时间
     */
    @InjectView(tv_time)
    TextView tvTime;
    /**
     * 养护人员
     */
    @InjectView(R.id.tv_personnel)
    TextView tvPersonnel;
    /**
     * 养护内容
     */
    @InjectView(R.id.tv_content)
    TextView tvContent;
    /**
     * 发现问题
     */
    @InjectView(R.id.tv_problem)
    TextView tvProblem;
    /**
     * 植物长势
     */
    @InjectView(R.id.tv_plantGrowth)
    TextView tvPlantGrowth;
    /**
     * 保洁情况
     */
    @InjectView(R.id.tv_cleaning)
    TextView tvCleaning;
    /**
     * 备注
     */
    @InjectView(R.id.tv_remarks)
    TextView tvRemarks;
    /**
     * 养护前图片
     */
    @InjectView(iv_before)
    ImageView ivBefore;
    /**
     * 养护后图片
     */
    @InjectView(iv_after)
    ImageView ivAfter;

    private static final String TAG = "GuanhuDataActivity";

    private BitmapUtils mBitmapUtils;
    private ArrayList<String> data;

    private Call findManagementInfoCall;//植物管护信息查询请求

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_yhdata);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        mBitmapUtils = new BitmapUtils(context, Constant.CACHE_DIR_PATH);
        mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
        mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
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

    @Override
    public void initData() {
        String code = getIntent().getStringExtra("code");
        //请求网络，查询管护信息
        findManagementInfoCall = Requester.findManagementInfo(getLoginUid(), code, findManagementInfoCallBack);
    }

    private DefaultCallback findManagementInfoCallBack = new DefaultCallback() {
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
                Type baseType = new TypeToken<BaseEntity>() {
                }.getType();
                BaseEntity entity = ToolsJson.parseObjecta(response, baseType);
                int errorCode = entity.getErrorCode();//错误代码 0 正确 1 错误
                if (errorCode == 0) {
                    String jsonStr = ToolsJson.toJson(entity.getData());
                    Type infoType = new TypeToken<ManagementInfoEntity>() {
                    }.getType();
                    ManagementInfoEntity infoEntity = ToolsJson.parseObjecta(jsonStr, infoType);
                    setData(infoEntity);
                } else {
                    msg(entity.getMsg());
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    };

    private void setData(ManagementInfoEntity mdata) {
        tvCode.setText(mdata.getCode());
        tvAddress.setText(mdata.getAddress());
        tvTime.setText(mdata.getTime());
        tvPersonnel.setText(mdata.getPersonnel());
        tvContent.setText(mdata.getContent());
        tvProblem.setText(mdata.getProblem());
        tvPlantGrowth.setText(mdata.getPlantGrowth());
        tvCleaning.setText(mdata.getCleaning());
        tvRemarks.setText(mdata.getRemarks());
        mBitmapUtils.display(ivBefore, mdata.getFrontPhoto());
        mBitmapUtils.display(ivAfter, mdata.getAfterPhoto());
        data = new ArrayList<>();
        data.add(mdata.getFrontPhoto());
        data.add(mdata.getAfterPhoto());
        ivBefore.setOnClickListener(this);
        ivAfter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case iv_before:
                ScanImageUtils.ScanImage(context, data, 0);
                break;
            case iv_after:
                ScanImageUtils.ScanImage(context, data, 1);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (findManagementInfoCall != null && !findManagementInfoCall.isCanceled()) {
            findManagementInfoCall.cancel();
        }
    }
}
