package com.henghao.parkland.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.WebviewActivity;
import com.henghao.parkland.adapter.ProjectFirstAdapter;
import com.henghao.parkland.adapter.ProjectSecondAdapter;
import com.henghao.parkland.model.entity.AppGridEntity;
import com.henghao.parkland.model.entity.ProjectInfoEntity;
import com.henghao.parkland.utils.Requester;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 项目管理〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class XiangmuFragment extends FragmentSupport {

    @InjectView(R.id.gridview)
    GridView gridview;

    private static final String TAG = "XiangmuFragment";

    private ProjectSecondAdapter mMyAdapter;
    private ProjectFirstAdapter mProAdapter;

    public static ProjectInfoEntity mInfoEntity;//通用的项目信息
    public static int index;//项目名称索引

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.fragment_xiangmuguanli);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        ButterKnife.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
        initClick();
        return this.mActivityFragmentView;
    }

    /**
     * 标题点击事件
     */
    private void initClick() {
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ToolsKit.isEmpty(mActivity.getLoginUid())) {
                    mActivity.msg("请先登录！");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("title", "项目管理");
                intent.putExtra("url", Requester.getRequestHZURL(mActivity.getUserComp() + "/" + ProtocolUrl.XMGL) + mActivity.getLoginUserName());
                intent.setClass(mActivity, WebviewActivity.class);
                mActivity.startActivity(intent);
            }
        });
    }

    private void initData() {
        projectTop();
        projectMy();
    }

    /**
     * 项目管理
     */
    private void projectMy() {
        List<AppGridEntity> mList2 = new ArrayList<AppGridEntity>();
        //第一个
        AppGridEntity mEntity = new AppGridEntity();
        mEntity.setImageId(R.drawable.icon_projectone);
        mEntity.setName("会审结果");
        mList2.add(mEntity);
        //第二个
        AppGridEntity mEntity2 = new AppGridEntity();
        mEntity2.setImageId(R.drawable.icon_projecttwo);
        mEntity2.setName("供货方信息");
        mList2.add(mEntity2);
        //第三个
        AppGridEntity mEntity3 = new AppGridEntity();
        mEntity3.setImageId(R.drawable.icon_projectthree);
        mEntity3.setName("施工人员");
        mList2.add(mEntity3);
        //第四个
        AppGridEntity mEntity4 = new AppGridEntity();
        mEntity4.setImageId(R.drawable.icon_projectfore);
        mEntity4.setName("工程质量监督报告");
        mList2.add(mEntity4);
        //第五个
        AppGridEntity mEntity5 = new AppGridEntity();
        mEntity5.setImageId(R.drawable.icon_projectfive);
        mEntity5.setName("设备信息");
        mList2.add(mEntity5);
        //第六个
        AppGridEntity mEntity6 = new AppGridEntity();
        mEntity6.setImageId(R.drawable.icon_projectsix);
        mEntity6.setName("工序报验");
        mList2.add(mEntity6);
        //第七个
        AppGridEntity mEntity7 = new AppGridEntity();
        mEntity7.setImageId(R.drawable.icon_projectseven);
        mEntity7.setName("现场勘察");
        mList2.add(mEntity7);
        //第八个
        AppGridEntity mEntity8 = new AppGridEntity();
        mEntity8.setImageId(R.drawable.icon_eight);
        mEntity8.setName("变更管理");
        mList2.add(mEntity8);
        //第九个
        AppGridEntity mEntity9 = new AppGridEntity();
        mEntity9.setImageId(R.drawable.icon_biangeng);
        mEntity9.setName("竣工验收");
        mList2.add(mEntity9);
        //第十个
        AppGridEntity mEntity10 = new AppGridEntity();
        mEntity10.setImageId(R.drawable.icon_jungong);
        mEntity10.setName("进度申报");
        mList2.add(mEntity10);
        //第十一个
        AppGridEntity mEntity11 = new AppGridEntity();
        mEntity11.setImageId(R.drawable.icon_schedule);
        mEntity11.setName("技术交底");
        mList2.add(mEntity11);
        //第十二个
        AppGridEntity mEntity12 = new AppGridEntity();
        mEntity12.setImageId(R.drawable.icon_technology);
        mEntity12.setName("项目结算");
        mList2.add(mEntity12);
        mProAdapter = new ProjectFirstAdapter(this.mActivity, mList2);
        this.gridview.setAdapter(mProAdapter);
        mProAdapter.notifyDataSetChanged();
    }


    /**
     * 我的
     */
    private void projectTop() {
        List<AppGridEntity> mList = new ArrayList<AppGridEntity>();
        //第一个
        AppGridEntity mEntity = new AppGridEntity();
        mEntity.setImageId(R.drawable.icon_projectjlrz);
        mEntity.setName("监理日志");
        mList.add(mEntity);
        //第二个
        AppGridEntity mEntity2 = new AppGridEntity();
        mEntity2.setImageId(R.drawable.icon_projectsgrz);
        mEntity2.setName("施工日志");
        mList.add(mEntity2);
        //第三个
        AppGridEntity mEntity3 = new AppGridEntity();
        mEntity3.setImageId(R.drawable.icon_projectsgaqrz);
        mEntity3.setName("施工安全日志");
        mList.add(mEntity3);
        //第四个
        AppGridEntity mEntity4 = new AppGridEntity();
        mEntity4.setImageId(R.drawable.icon_projectsgzj);
        mEntity4.setName("施工钱包");
        mList.add(mEntity4);
        //第五个
        AppGridEntity mEntity5 = new AppGridEntity();
        mEntity5.setImageId(R.mipmap.icon_task_one);
        mEntity5.setName("工作任务");
        mList.add(mEntity5);
        //第六个
        AppGridEntity mEntity6 = new AppGridEntity();
        mEntity6.setImageId(R.mipmap.icon_task_two);
        mEntity6.setName("工作安排");
        mList.add(mEntity6);
        //第七个
        AppGridEntity mEntity7 = new AppGridEntity();
        mEntity7.setImageId(R.mipmap.icon_task_three);
        mEntity7.setName("我的计划");
        mList.add(mEntity7);
        mMyAdapter = new ProjectSecondAdapter(this.mActivity, mList);
    }

    /**
     * 标题操作 〈一句话功能简述〉 〈功能详细描述〉
     *
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void initwithContent() {
        initWithCenterBar();
        this.mCenterTextView.setVisibility(View.VISIBLE);
        this.mCenterTextView.setText("项目管理");
        initWithBar();
        this.mLeftImageView.setVisibility(View.VISIBLE);
        this.mLeftImageView.setImageResource(R.drawable.home_liebiao);
        initWithRightBar();
        this.mRightTextView.setVisibility(View.VISIBLE);
        this.mRightTextView.setText("管理");
    }

    public void initWidget() {
        initwithContent();
    }

    @OnClick({R.id.xm_my, R.id.xm_project})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.xm_project:
                //项目管理
                this.gridview.setAdapter(mProAdapter);
                mProAdapter.notifyDataSetChanged();
                break;
            case R.id.xm_my:
                //我的
                this.gridview.setAdapter(mMyAdapter);
                mMyAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
