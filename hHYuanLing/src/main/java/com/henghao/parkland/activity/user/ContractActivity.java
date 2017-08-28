package com.henghao.parkland.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.ContractAdapter;
import com.henghao.parkland.model.entity.AppGridEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ASUS on 2017/8/23.
 * 合同管理界面
 */

public class ContractActivity extends ActivityFragmentSupport {
    @InjectView(R.id.gridview)
    GridView gridview;

    private List<AppGridEntity> mList;
    private ContractAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.common_gridview);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.clipToPadding(true);
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
        mCenterTextView.setText("合同管理");
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        //第一个
        AppGridEntity mEntity = new AppGridEntity();
        mEntity.setImageId(R.mipmap.icon_contract_one);
        mEntity.setName("商务合同");
        mList.add(mEntity);
        //第二个
        AppGridEntity mEntity2 = new AppGridEntity();
        mEntity2.setImageId(R.mipmap.icon_contract_two);
        mEntity2.setName("劳务合同");
        mList.add(mEntity2);
        //第三个
        AppGridEntity mEntity3 = new AppGridEntity();
        mEntity3.setImageId(R.mipmap.icon_contract_three);
        mEntity3.setName("授权合同");
        mList.add(mEntity3);
        mAdapter = new ContractAdapter(this, mList);
        this.gridview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
