package com.henghao.parkland.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.benefit.buy.library.utils.tools.ToolsKit;
import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.WebviewActivity;
import com.henghao.parkland.model.entity.AppGridEntity;
import com.henghao.parkland.utils.Requester;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ASUS on 2017/8/22.
 * 任务安排
 */

public class TaskAdapter extends ArrayAdapter<AppGridEntity> {
    private final LayoutInflater inflater;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    private final List<AppGridEntity> mList;

    public TaskAdapter(ActivityFragmentSupport activityFragment, List<AppGridEntity> list) {
        super(activityFragment, R.layout.item_gridview_textimage, list);
        this.mActivityFragmentSupport = activityFragment;
        this.mList = list;
        this.inflater = LayoutInflater.from(activityFragment);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.item_gridview_textimage, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageTitle.setImageResource(getItem(position).getImageId());
        viewHolder.tvTitle.setText(getItem(position).getName());
        viewOnClick(viewHolder, convertView, position);
        return convertView;
    }

    //点击
    private void viewOnClick(ViewHolder viewHolder, View convertView, final int position) {
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ToolsKit.isEmpty(mActivityFragmentSupport.getLoginUid())) {
                    mActivityFragmentSupport.msg("请先登录！");
                    return;
                }
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        // 工作任务
                        intent.putExtra("title", "工作任务");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_WORK_TASK) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 1:
                        // 工作安排
                        intent.putExtra("title", "工作安排");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_WORK_ARRANGE) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 2:
                        // 我的计划
                        intent.putExtra("title", "我的计划");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_MY_PLAN) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    static class ViewHolder {
        @InjectView(R.id.image_title)
        ImageView imageTitle;
        @InjectView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
