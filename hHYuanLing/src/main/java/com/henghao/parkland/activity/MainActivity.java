package com.henghao.parkland.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.benefit.buy.library.utils.NSLog;
import com.benefit.buy.library.views.ToastView;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.FragmentTabAdapter;
import com.henghao.parkland.fragment.AppFragment;
import com.henghao.parkland.fragment.FragmentSupport;
import com.henghao.parkland.fragment.HomeFragment;
import com.henghao.parkland.fragment.MyLoginFragment;
import com.henghao.parkland.fragment.WorkShowFragment;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.model.entity.HCMenuEntity;
import com.henghao.parkland.utils.PermissionUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 *
 * @author zhangxianwen
 */
@SuppressLint("NewApi")
public class MainActivity extends ActivityFragmentSupport {

    @ViewInject(R.id.tabs_rg)
    private RadioGroup rgs;
    @ViewInject(R.id.tab_top)
    public View mTabLinearLayout;
    private boolean isExit = false;
    private ToastView mToastView;
    public List<FragmentSupport> fragments = new ArrayList<FragmentSupport>();
    private List<HCMenuEntity> menuLists;
    private final boolean ready = true; // 是否获取nfc信息
    private FragmentTabAdapter tabAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.tabAdapter != null) {
            this.tabAdapter.remove();
        }
        this.mActivityFragmentView.viewMain(R.layout.activity_main);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        this.mActivityFragmentView.clipToPadding(true);
        setContentView(this.mActivityFragmentView);
//        if (getLoginUid() == null) {
//            Intent intent = new Intent();
//            intent.setClass(this, LoginAndRegActivity.class);
//            startActivity(intent);
//            finish();
//        }
        com.lidroid.xutils.ViewUtils.inject(this);
        menuList();
        try {
            // 动态加载tab
            // 动态设置tab item
            for (int i = 0; i < this.menuLists.size(); i++) {
                HCMenuEntity menu = this.menuLists.get(i);
                if (menu.getStatus() == -1) {
                    @SuppressWarnings("unchecked")
                    Class<FragmentSupport> clazz = (Class<FragmentSupport>) Class.forName(menu.getClazz());
                    FragmentSupport fragmentSuper = clazz.newInstance();
                    fragmentSuper.fragmentId = menu.getmId();
                    this.fragments.add(fragmentSuper);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        // Intent intent = getIntent();
        // page = intent.getIntExtra("page", 0);
        // if (page == 3) {
        // hcShopcar.setChecked(true);
        // }
        this.tabAdapter = new FragmentTabAdapter(this, this.fragments, R.id.tab_content, this.rgs);
        this.tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {

            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                System.out.println("Extra---- " + index + " checked!!! ");
            }
        });
        initData();
        //Android 6.0 以上需动态请求权限
        if (Build.VERSION.SDK_INT >= 23) handlePermission();
    }

    private void handlePermission() {
        String[] permissions = PermissionUtil.checkPermissions(getContext());
        if (permissions.length != 0) {
            requestPermissions(permissions, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(context, "您需要同意全部权限才能正常使用本软件", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }

    @Override
    public void initData() {
        // 导航栏
        mActivityFragmentView.getNavitionBarView().setVisibility(View.GONE);
        if (BuildConfig.DEBUG) {
            SharedPreferences sp = getSharedPreferences("DebugSetting", MODE_PRIVATE);
            ProtocolUrl.ROOT_URL = sp.getString(DebugSettingActivity.KEY_HOST, ProtocolUrl.ROOT_URL);
            ProtocolUrl.HZ_ROOT_URL = sp.getString(DebugSettingActivity.KEY_HZ_HOST, ProtocolUrl.HZ_ROOT_URL);
        }

    }

    /**
     * 牵扯到的tab items
     */
    public void menuList() {
        this.menuLists = new ArrayList<HCMenuEntity>();
        HCMenuEntity mMenuHome = new HCMenuEntity(1, getResources().getString(R.string.hc_home),
                R.drawable.selector_home, HomeFragment.class.getName(), -1);// 首页
        this.menuLists.add(mMenuHome);
//        HCMenuEntity mMenuJob = new HCMenuEntity(2, getResources().getString(R.string.hc_xinxi),
//                R.drawable.selector_xiangmu, MsgFragment.class.getName(), -1);// 信息
//        this.menuLists.add(mMenuJob);
        HCMenuEntity mMenuWork = new HCMenuEntity(3, getResources().getString(R.string.hc_work),
                R.drawable.selector_work, WorkShowFragment.class.getName(), -1);// 工作台
        this.menuLists.add(mMenuWork);
        HCMenuEntity mMenuCommunication = new HCMenuEntity(4, getResources().getString(R.string.hc_xiangmu),
                R.drawable.selector_xiangmu, AppFragment.class.getName(), -1);// 应用
        this.menuLists.add(mMenuCommunication);
        HCMenuEntity mXiangmuguanl = new HCMenuEntity(5, getResources().getString(R.string.hc_xiangmuguanl),
                R.drawable.selector_xiangmuguanli, XiangmuFragment.class.getName(), -1);// 项目管理
        this.menuLists.add(mXiangmuguanl);
        HCMenuEntity mMenuMore = new HCMenuEntity(6, getResources().getString(R.string.hc_my), R.drawable.selector_my,
                MyLoginFragment.class.getName(), -1);// 我的
        this.menuLists.add(mMenuMore);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if ((event.getKeyCode() == KeyEvent.KEYCODE_BACK) && (event.getAction() != KeyEvent.ACTION_UP)) {
            if (!this.isExit) {
                this.isExit = true;
                this.mToastView = ToastView.makeText(this, getResources().getString(R.string.home_exit)).setGravity(
                        Gravity.CENTER, 0, 0);
                this.mToastView.show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        MainActivity.this.isExit = false;
                    }
                }, 3000);
                return true;
            } else {
                this.mToastView.cancel();
                this.mApplication.exit();
                return false;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    // 显示扫描到的内容
                    String content = bundle.getString("result");
                    // 显示
                    Bitmap bitmap = data.getParcelableExtra("bitmap");
                    NSLog.e(this, "content:" + content);
                }
                break;
        }
    }
}
