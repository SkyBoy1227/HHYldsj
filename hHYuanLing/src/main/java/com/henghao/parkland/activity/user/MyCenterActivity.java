package com.henghao.parkland.activity.user;

import android.os.Bundle;
import android.view.View;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;

/**
 * Created by ASUS on 2017/8/22.
 * 个人中心界面
 */
public class MyCenterActivity extends ActivityFragmentSupport {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_my_center);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        setContentView(this.mActivityFragmentView);
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
        mCenterTextView.setText("个人中心");
        initWithRightBar();
    }

    @Override
    public void initData() {
        super.initData();
    }
}
