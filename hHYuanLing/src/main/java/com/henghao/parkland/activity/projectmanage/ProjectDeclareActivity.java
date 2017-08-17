package com.henghao.parkland.activity.projectmanage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.benefit.buy.library.http.query.callback.AjaxStatus;
import com.benefit.buy.library.utils.tools.ToolsJson;
import com.benefit.buy.library.views.xlistview.XListView;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.ProjectDeclareAdapter;
import com.henghao.parkland.callback.MyCallBack;
import com.henghao.parkland.fragment.XiangmuFragment;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.DeleteEntity;
import com.henghao.parkland.model.entity.ProjectDeclareEntity;
import com.henghao.parkland.model.protocol.ProjectProtocol;
import com.henghao.parkland.utils.Requester;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 项目管理 -- 进度申报
 */
public class ProjectDeclareActivity extends ActivityFragmentSupport implements MyCallBack {

    @InjectView(R.id.lv_projectdeclare)
    XListView mXlistView;
    @InjectView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    private static final String TAG = "ProjectDeclareActivity";

    private CheckBox checkBox;//全选/多选
    private TextView tvEdit;//编辑
    private List<Integer> itemID;//被选中的item ID集合

    private ArrayList<ProjectDeclareEntity> data;

    private ProjectDeclareAdapter mAdapter;
    private Call deleteCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_project_declare);
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
        mActivityFragmentView.viewMainGone();
        initWithBar();
        initWithCenterBar();
        mCenterTextView.setText("进度申报");
        initWithRightBar();
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setText("添加");
        mRightLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getLoginUserName() == null) {
                    msg("请先登录！");
                    return;
                }
                if (XiangmuFragment.mInfoEntity == null) {
                    msg("请先添加项目信息！");
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(ProjectDeclareActivity.this, ProjectDeclareSubmitActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        itemID = new ArrayList<>();
        View HeaderView = LayoutInflater.from(this).inflate(R.layout.include_projecttop, null);
        checkBox = (CheckBox) HeaderView.findViewById(R.id.checkBox);
        tvEdit = (TextView) HeaderView.findViewById(R.id.tv_edit);
        mXlistView.addHeaderView(HeaderView);
        tvEdit.setVisibility(View.VISIBLE);
        data = new ArrayList<>();
        mAdapter = new ProjectDeclareAdapter(this, data, this);
        mXlistView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edit = tvEdit.getText().toString().trim();
                if (edit.equals("编辑")) {
                    tvEdit.setText("完成");
                    checkBox.setVisibility(View.VISIBLE);
                    mAdapter.showCheckBox();
                    mAdapter.notifyDataSetChanged();
                    layoutBottom.setVisibility(View.VISIBLE);
                } else {
                    tvEdit.setText("编辑");
                    checkBox.setVisibility(View.GONE);
                    mAdapter.hideCheckBox();
                    mAdapter.notifyDataSetChanged();
                    layoutBottom.setVisibility(View.GONE);
                }
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    itemID.clear();
                    for (ProjectDeclareEntity entity : data) {
                        entity.setChecked(true);
                        addId(entity.getDid());
                    }
                } else {
                    itemID.clear();
                    for (ProjectDeclareEntity entity : data) {
                        entity.setChecked(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        itemID.clear();//清空
        checkBox.setChecked(false);//默认不选中
        tvEdit.setText("编辑");
        checkBox.setVisibility(View.GONE);
        mAdapter.hideCheckBox();
        mAdapter.notifyDataSetChanged();
        layoutBottom.setVisibility(View.GONE);
        /**
         * 访问网络
         */
        ProjectProtocol mProtocol = new ProjectProtocol(this);
        mProtocol.addResponseListener(this);
        mProtocol.queryDeclarationMsg(getLoginUid());
        mActivityFragmentView.viewLoading(View.VISIBLE);
    }

    @Override
    public void OnMessageResponse(String url, Object jo, AjaxStatus status) throws JSONException {
        super.OnMessageResponse(url, jo, status);
        if (url.endsWith(ProtocolUrl.PROJECT_QUERYDECLARATIONMSG)) {
            if (jo instanceof BaseEntity) {
                BaseEntity mData = (BaseEntity) jo;
                //msg(mData.getMsg());
                if (mData.getData() == null) {
                    mActivityFragmentView.viewMainGone();
                } else {
                    mActivityFragmentView.viewEmptyGone();
                    String jsonStr = ToolsJson.toJson(mData.getData());
                    Type type = new TypeToken<List<ProjectDeclareEntity>>() {
                    }.getType();
                    List<ProjectDeclareEntity> homeData = ToolsJson.parseObjecta(jsonStr, type);
                    String topPath = mData.getPath();//图片URL头部地址
                    data.clear();
                    for (ProjectDeclareEntity entity : homeData) {
                        entity.setPhotoId(topPath);
                        data.add(entity);
                    }
                    mAdapter.notifyDataSetChanged();
                    mXlistView.setAdapter(mAdapter);
                }
            }
        }
    }

    @OnClick({R.id.tv_delete, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_delete:
                if (!isItemChecked()) {
                    Toast.makeText(this, "您还没有选择信息哦！", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.icon_warn);
                builder.setTitle("警告！");
                builder.setMessage("您真的确认要删除这些信息吗？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<DeleteEntity> deleteArray = new ArrayList<DeleteEntity>();
                        for (Integer id : itemID) {
                            DeleteEntity entity = new DeleteEntity();
                            entity.setUid(getLoginUid());
                            entity.setId(id);
                            deleteArray.add(entity);
                        }
                        deleteInfo(deleteArray);//请求网络，删除信息
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.tv_cancel:
                tvEdit.setText("编辑");
                checkBox.setVisibility(View.GONE);
                mAdapter.hideCheckBox();
                mAdapter.notifyDataSetChanged();
                layoutBottom.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 删除信息
     *
     * @param entity
     */
    private void deleteInfo(List<DeleteEntity> entity) {
        deleteCall = Requester.declareDeleteInfo(entity, new DefaultCallback() {
            @Override
            public void onFailure(Exception e, int code) {
                if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
                e.printStackTrace();
                Toast.makeText(context, "网络访问错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String response) {
                Type type = new TypeToken<BaseEntity>() {
                }.getType();
                try {
                    final BaseEntity baseEntity = ToolsJson.parseObjecta(response, type);
                    Toast.makeText(context, baseEntity.getMsg(), Toast.LENGTH_SHORT).show();
                    // 刷新界面
                    itemID.clear();
                    // 如果全选按钮被选中，将全选按钮选中状态取消
                    checkBox.setChecked(false);
                    List<Integer> idList = getIdList();
                    for (int id : idList) {
                        removeList(id);
                    }
                    mAdapter.notifyDataSetChanged();
                    // 全部删除之后
                    if (data.size() == 0) {
                        mActivityFragmentView.viewMainGone();
                    }
                } catch (JsonSyntaxException e) {
                    Toast.makeText(context, "服务器错误，请稍后重试！", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void addId(int id) {
        itemID.add(id);
    }

    @Override
    public void removeId(int id) {
        int index = 0;
        /**
         * 在List中找到与id相同的索引
         */
        for (int i = 0; i < itemID.size(); i++) {
            if (itemID.get(i) == id) {
                index = i;
                break;
            }
        }
        itemID.remove(index);
    }

    @Override
    public void setChecked() {
        int size = itemID.size();
        if (size == data.size()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }

    /**
     * 得到选中的ID
     *
     * @return
     */
    public List<Integer> getIdList() {
        List<Integer> idList = new ArrayList<Integer>();
        for (ProjectDeclareEntity entity : data) {
            if (entity.isChecked()) {
                idList.add(entity.getDid());
            }
        }
        return idList;
    }

    /**
     * 删除List
     */
    public void removeList(int id) {
        int index = 0;
        /**
         * 在List中找到与id相同的索引
         */
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getDid() == id) {
                index = i;
            }
        }
        data.remove(index);
    }

    /**
     * 判断是否有item被选中
     *
     * @return
     */
    private boolean isItemChecked() {
        for (ProjectDeclareEntity entity : data) {
            if (entity.isChecked()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (deleteCall != null && !deleteCall.isCanceled()) {
            deleteCall.cancel();
        }
    }
}

