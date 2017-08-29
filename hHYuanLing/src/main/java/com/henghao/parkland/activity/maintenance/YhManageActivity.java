package com.henghao.parkland.activity.maintenance;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.YhAdapter;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.MaintenanceInfoEntity;
import com.henghao.parkland.utils.Requester;
import com.henghao.parkland.views.dialog.DialogList;
import com.henghao.parkland.views.dialog.DialogYanghu;
import com.zbar.lib.zxing.CaptureActivity;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;


/**
 * Created by 晏琦云 on 2017/2/13.
 * 养护管理界面
 */

public class YhManageActivity extends ActivityFragmentSupport {

    @InjectView(R.id.lv_yhmanage)
    ListView listView;

    private static final String TAG = "YhManageActivity";

    private static final int REQUEST_CODE_TREEMESSAGE = 0x0000;//植物信息录入request code
    private static final int REQUEST_CODE_YANGHU = 0x0001;//植物养护request code
    // 定位相关声明
    public LocationClient locationClient = null;

    private String address;//获取到的GPS定位地理位置信息
    private YhAdapter adapter;

    private String code;//植物编号

    private String state;//养护状态
    private DialogYanghu dialogYanghu;
    private Call findMaintenanceInfoCall;//查询植物养护信息请求
    private Call findPlantInformationCall;//查询植物信息请求

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.acitivty_yhmanage);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.getNavitionBarView().setVisibility(View.VISIBLE);
        setContentView(this.mActivityFragmentView);
        ButterKnife.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mActivityFragmentView.viewMainGone();
        initWithBar();
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("返回");
        initWithCenterBar();
        mCenterTextView.setText("养护管理");
        initWithRightBar();
        mRightImageView.setVisibility(View.GONE);
        mRightImageView.setImageResource(R.drawable.scan);
    }

    /**
     * 查询植物养护信息回调
     */
    private DefaultCallback findMaintenanceInfoCallback = new DefaultCallback() {
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            mActivityFragmentView.viewMainGone();
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
                    mRightImageView.setVisibility(View.VISIBLE);
                    String jsonStr = ToolsJson.toJson(entity.getData());
                    Type infoType = new TypeToken<List<MaintenanceInfoEntity>>() {
                    }.getType();
                    List<MaintenanceInfoEntity> maintenanceInfoEntities = ToolsJson.parseObjecta(jsonStr, infoType);
                    Collections.reverse(maintenanceInfoEntities);
                    mActivityFragmentView.viewEmptyGone();
                    adapter = new YhAdapter(maintenanceInfoEntities, YhManageActivity.this);
                    listView.setAdapter(adapter);
                } else {
                    //如果未登录，则不可以进行养护
                    if (ToolsKit.isEmpty(getLoginUid())) {
                        mRightImageView.setVisibility(View.GONE);
                        Toast.makeText(context, "请登录！", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        mRightImageView.setVisibility(View.VISIBLE);
                    }
                    mActivityFragmentView.viewMainGone();
                    msg(entity.getMsg());
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void initData() {
        mRightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //二维码
                dialogYanghu = getDialogYanghu();
                dialogYanghu.show();
            }
        });
        //定位
        locationClient = new LocationClient(getApplicationContext()); // 实例化LocationClient类
        locationClient.registerLocationListener(this.myListener); // 注册监听函数
        setLocationOption(); // 设置定位参数
        locationClient.start(); // 开始定位
    }

    /**
     * 弹出养护状态选择对话框
     *
     * @return
     */
    private DialogYanghu getDialogYanghu() {
        return new DialogYanghu(YhManageActivity.this, new DialogYanghu.DialogAlertListener() {
            @Override
            public void onDialogCreate(Dialog dlg) {

            }

            @Override
            public void onDialogOk(Dialog dlg) {
                //选择养护状态
                DialogList mdialogList = dialogList();
                dialogYanghu.cancel();
                mdialogList.show();
            }

            @Override
            public void onDialogCancel(Dialog dlg) {
                //植物信息录入
                Intent intent = new Intent(YhManageActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_TREEMESSAGE);
            }
        });


    }

    private DialogList dialogList() {
        return new DialogList(YhManageActivity.this, new DialogList.DialogAlertListener() {
            @Override
            public void onDialogCreate(Dialog dlg) {

            }

            @Override
            public void onDialogOk(Dialog dlg, String par) {
                state = par;
                Intent intent = new Intent(YhManageActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_YANGHU);
            }

            @Override
            public void onDialogCancel(Dialog dlg) {

            }
        });
    }

    /**
     * 检测网络是否连接
     *
     * @return
     */
    private boolean checkNetworkState() {
        boolean flag = false;
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }

    /**
     * 判断GPS是否开启
     *
     * @param context
     * @return true 表示开启
     */
    private boolean isOPenGPS(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gps;
    }

    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            address = location.getAddrStr();
            /**
             * 如果GPS未打开且无网络
             */
            if (!checkNetworkState() && !isOPenGPS(getContext())) {
                address = null;
                Toast.makeText(YhManageActivity.this,
                        "对不起，获取不到当前的地理位置！请开启GPS和网络", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 设置定位参数
     */
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000); // 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true); // 返回的定位结果包含手机机头的方向
        this.locationClient.setLocOption(option);
    }

    // 三个状态实现地图生命周期管理
    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        this.locationClient.stop();
        super.onDestroy();
        if (findMaintenanceInfoCall != null && !findMaintenanceInfoCall.isCanceled()) {
            findMaintenanceInfoCall.cancel();
        }
        if (findPlantInformationCall != null && !findPlantInformationCall.isCanceled()) {
            findPlantInformationCall.cancel();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        this.locationClient.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.locationClient.start(); // 开始定位
        if (ToolsKit.isEmpty(getLoginUid())) {
            msg("请先登录！");
            return;
        }
        //请求网络，查询当天养护信息
        findMaintenanceInfoCall = Requester.findMaintenanceInfo(getLoginUid(), findMaintenanceInfoCallback);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传------植物养护信息录入
        if (requestCode == REQUEST_CODE_YANGHU && resultCode == RESULT_OK) {
            if (data != null) {
                if (address == null) {
                    Toast.makeText(YhManageActivity.this,
                            "对不起，获取不到当前的地理位置！请开启GPS和网络", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle bundle = data.getExtras();
                // 显示扫描到的内容
                code = bundle.getString("result");
                findPlantInformationCall = Requester.findPlantInformation(code, findPlantInformationCallback);
            }
        } else if (requestCode == REQUEST_CODE_TREEMESSAGE && resultCode == RESULT_OK) {//扫描二维码/条码回传------植物信息录入
            if (data != null) {
                Bundle bundle = data.getExtras();
                // 显示扫描到的内容
                String content = bundle.getString("result");
                if (address == null) {
                    Toast.makeText(YhManageActivity.this,
                            "对不起，获取不到当前的地理位置！请开启GPS和网络", Toast.LENGTH_SHORT).show();
                    return;
                }

                //跳转到植物信息录入界面
                Intent intent = new Intent(YhManageActivity.this, TreeMessageActivity.class);
                intent.putExtra("code", content);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        }
    }

    /**
     * 查询植物信息回调
     */
    private DefaultCallback findPlantInformationCallback = new DefaultCallback() {
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
                int errorCode = entity.getErrorCode();
                if (errorCode > 0) {//未查询到数据
                    msg("当前植物信息未录入！请先录入！");
                    return;
                } else {//查询有数据
                    //获取养护时间
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String time = dateFormat.format(date);//格式化时间
                    //跳转到植物养护信息界面
                    Intent intent = new Intent(YhManageActivity.this, MaintenanceActivity.class);
                    intent.putExtra("code", code);
                    intent.putExtra("state", state);
                    intent.putExtra("time", time);
                    intent.putExtra("address", address);
                    startActivity(intent);
                }
            } catch (JsonSyntaxException e) {
                Toast.makeText(YhManageActivity.this, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    };
}
