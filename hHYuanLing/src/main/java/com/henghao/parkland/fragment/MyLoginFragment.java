package com.henghao.parkland.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.benefit.buy.library.utils.tools.ToolsKit;
import com.benefit.buy.library.views.CircularImageView;
import com.benefit.buy.library.views.dialog.BaseDialog;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.DebugSettingActivity;
import com.henghao.parkland.activity.WebviewActivity;
import com.henghao.parkland.activity.user.ContractActivity;
import com.henghao.parkland.activity.user.LoginAndRegActivity;
import com.henghao.parkland.activity.user.MyCenterActivity;
import com.henghao.parkland.activity.user.QiandaoActivity;
import com.henghao.parkland.activity.user.SettingActivity;
import com.henghao.parkland.utils.Requester;
import com.lidroid.xutils.ViewUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyLoginFragment extends FragmentSupport {
    /**
     * 用户头像
     */
    @InjectView(R.id.user_header)
    CircularImageView user_header;
    /**
     * 用户名称
     */
    @InjectView(R.id.tv_userName)
    TextView tv_userName;
    /**
     * 用户电话
     */
    @InjectView(R.id.tv_userPhone)
    TextView tv_userPhone;
    /**
     * 登录
     */
    @InjectView(R.id.tv_login)
    TextView tv_login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.fragment_my);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        mActivityFragmentView.getNavitionBarView().setVisibility(View.GONE);
        ViewUtils.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
        if (BuildConfig.DEBUG) initButton();
        ButterKnife.inject(this, this.mActivityFragmentView);
        return this.mActivityFragmentView;
    }

    /**
     * 设置调试界面
     */
    private void initButton() {
        if (BuildConfig.DEBUG) {
            Button bt = (Button) mActivityFragmentView.findViewById(R.id.bt_set_host);
            bt.setVisibility(View.VISIBLE);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), DebugSettingActivity.class));
                }
            });
        }
    }

    private void initData() {

    }


    public void initWidget() {
    }


    public void onResume() {
        super.onResume();
        String Uid = mActivity.getLoginUserName();
        if (ToolsKit.isEmpty(Uid)) {
            //未登录
            tv_userName.setText("点击登录更精彩");
            tv_userPhone.setText("暂未登录");
            tv_login.setText("登录");
        } else {
            //已登录
            tv_userName.setText(mActivity.getLoginUserName());
            tv_userPhone.setText(mActivity.getLoginUserPhone());
            tv_login.setText("注销");
        }
    }

    @OnClick({R.id.tv_mywallet, R.id.tv_login, R.id.ll_updatename, R.id.image_setting,
            R.id.tv_my_center, R.id.tv_qiandao, R.id.tv_myproject, R.id.tv_compactmanage})
    public void onViewClicked(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_mywallet://我的钱包
                if (!isLogin()) {
                    mActivity.msg("请先登录！");
                    return;
                }
                intent.putExtra("title", "施工钱包");
                intent.putExtra("url", Requester.getRequestHZURL(mActivity.getUserComp() + "/" + ProtocolUrl.FIND_SGQB) + mActivity.getLoginUserName());
                intent.setClass(mActivity, WebviewActivity.class);
                mActivity.startActivity(intent);
                break;
            case R.id.tv_login:
                if (isLogin()) {
                    Resources mResources = getResources();
                    String title = mResources.getString(R.string.account_cancel_hint);
                    String ok = mResources.getString(R.string.ok);
                    String cancel = mResources.getString(R.string.cancel);
                    String message = mResources.getString(R.string.account_cancel_affirm);
                    BaseDialog.getDialog(mActivity, title, message, cancel, cancelListener, ok, exitListener).show();
                } else {
                    intent.setClass(mActivity, LoginAndRegActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_updatename:
                if (!isLogin()) {
                    intent.setClass(mActivity, LoginAndRegActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.image_setting:
                //设置
                intent.setClass(mActivity, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_my_center:
                //个人中心
                intent.setClass(mActivity, MyCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_qiandao:
                //签到
                intent.setClass(mActivity, QiandaoActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_myproject:
                //我的项目
                if (!isLogin()) {
                    mActivity.msg("请先登录！");
                    return;
                }
                intent.putExtra("title", "我的项目");
                intent.putExtra("url", Requester.getRequestHZURL(mActivity.getUserComp() + "/" + ProtocolUrl.FIND_XMXX) + mActivity.getLoginUserName());
                intent.setClass(mActivity, WebviewActivity.class);
                mActivity.startActivity(intent);
                break;
            case R.id.tv_compactmanage:
                //合同管理
                intent.setClass(mActivity, ContractActivity.class);
                startActivity(intent);
                break;
        }

    }

    /**
     * 判断是否登录
     *
     * @return
     */
    private boolean isLogin() {
        if (!ToolsKit.isEmpty(mActivity.getLoginUserName())) {
            return true;
        }
        return false;
    }

    // 注销对话框监听器
    DialogInterface.OnClickListener exitListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            SharedPreferences.Editor editor = mActivity.getLoginUserSharedPre().edit();
            // 退出清空用户信息
            editor.remove(Constant.USER)
                    .remove(Constant.USERNAME)
                    .remove(Constant.USERID)
                    .remove(Constant.USERSESSION)
                    .remove(Constant.USERPHONE);
            editor.putString(Constant.USER, null);
            editor.putString(Constant.USERNAME, null);
            editor.putString(Constant.USERID, null);
            editor.putString(Constant.USERSESSION, null);
            editor.putString(Constant.USERPHONE, null);
            editor.commit();
            dialog.dismiss();

            Intent intent = new Intent();
            intent.setClass(mActivity, LoginAndRegActivity.class);
            startActivity(intent);
        }
    };

    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
