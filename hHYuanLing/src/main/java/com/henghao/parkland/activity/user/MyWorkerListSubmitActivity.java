package com.henghao.parkland.activity.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.CommonListStringAdapter;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.protocol.ProjectSecProtocol;
import com.henghao.parkland.utils.PopupWindowHelper;
import com.henghao.parkland.views.DateChooseWheelViewDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * 我的轨迹提交页面
 */
public class MyWorkerListSubmitActivity extends ActivityFragmentSupport {


    @ViewInject(R.id.tv_select)
    private TextView tv_select;

    @ViewInject(R.id.tv_people)
    private EditText tv_people;

    @ViewInject(R.id.tv_contant)
    private EditText tv_contant;

    @ViewInject(R.id.tv_time)
    private TextView tv_time;

    private View popView;

    private PopupWindowHelper popupWindowHelper;
    private ProjectSecProtocol mProtocol;

    private String mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivityFragmentView.viewMain(R.layout.activity_myworkerlist_submit);
        mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        mActivityFragmentView.viewEmptyGone();
        mActivityFragmentView.viewLoading(View.GONE);
        mActivityFragmentView.clipToPadding(true);
        setContentView(mActivityFragmentView);
        com.lidroid.xutils.ViewUtils.inject(this);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        initWithContent();
        mProtocol = new ProjectSecProtocol(this);
        mProtocol.addResponseListener(this);

    }

    private void initWithContent() {
        initWithBar();
        initWithCenterBar();
        mCenterTextView.setText("我的轨迹");

        LayoutInflater inflater = LayoutInflater.from(this);
        this.popView = inflater.inflate(R.layout.common_android_listview, null);
        ListView mListView = (ListView) this.popView.findViewById(R.id.mlistview);
        final List<String> mList = new ArrayList<String>();
        mList.add("工作任务");
        mList.add("工作安排");
        mList.add("我的计划");
        CommonListStringAdapter mListStringAdapter = new CommonListStringAdapter(MyWorkerListSubmitActivity.this, mList);
        mListView.setAdapter(mListStringAdapter);
        mListStringAdapter.notifyDataSetChanged();
        this.popupWindowHelper = new PopupWindowHelper(this.popView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String whatSelect = mList.get(arg2);
                tv_select.setText(whatSelect);
                popupWindowHelper.dismiss();
            }
        });
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.tv_select, R.id.tv_time, R.id.tv_submit})
    private void viewOnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select:
                popupWindowHelper.showFromBottom(v);
                break;
            case R.id.tv_time:
                getDialogTime("请选择时间");
                break;
            case R.id.tv_submit:
                String selectWhat = tv_select.getText().toString().trim();
                String contant = tv_contant.getText().toString().trim();
                String person = tv_people.getText().toString().trim();
                if (ToolsKit.isEmpty(selectWhat)) {
                    msg("请选择工作类型");
                    return;
                }
                if (ToolsKit.isEmpty(contant)) {
                    msg("请填写内容");
                    tv_contant.requestFocus();
                    return;
                }
                if (ToolsKit.isEmpty(mTime)) {
                    msg("请选择时间");
                    return;
                }
                if (ToolsKit.isEmpty(person)) {
                    tv_people.requestFocus();
                    msg("请填写人员名称");
                    return;
                }
                mProtocol.saveMylocusMsg(getLoginUid(), person, contant, mTime, selectWhat);
                mActivityFragmentView.viewLoading(View.VISIBLE);
                break;
        }
    }

    private DateChooseWheelViewDialog getDialogTime(String title) {
        DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(this, new DateChooseWheelViewDialog.DateChooseInterface() {
            @Override
            public void getDateTime(String time, boolean longTimeChecked) {
                tv_time.setText(time);
                mTime = time;
            }
        });
        startDateChooseDialog.setDateDialogTitle(title);
        startDateChooseDialog.showDateChooseDialog();
        startDateChooseDialog.setCanceledOnTouchOutside(true);
        return startDateChooseDialog;
    }


    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_SAVE_MYLOCUSMSG)) {
            //我的轨迹
            if (jo instanceof BaseEntity) {
                BaseEntity mEntity = (BaseEntity) jo;
                msg(mEntity.getMsg());
                onBackPressed();
                return;
            }
        }
    }
}
