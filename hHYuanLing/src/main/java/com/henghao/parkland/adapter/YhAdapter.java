package com.henghao.parkland.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.henghao.parkland.R;
import com.henghao.parkland.activity.maintenance.GuanhuSubmitActivity;
import com.henghao.parkland.activity.maintenance.GuanhuDataActivity;
import com.henghao.parkland.model.entity.MaintenanceInfoEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/2/13.
 */

public class YhAdapter extends BaseAdapter {
    private List<MaintenanceInfoEntity> list;
    private Context context;
    private LayoutInflater inflater;

    public YhAdapter(List<MaintenanceInfoEntity> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_maintenace, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MaintenanceInfoEntity bean = list.get(position);
        viewHolder.tvCode.setText(bean.getCode());
        viewHolder.tvState.setText(bean.getState());
        viewHolder.tvAddress.setText(bean.getAddress());
        viewHolder.tvTime.setText(bean.getTime());
        if (bean.getStatus().equals("0")) {
            viewHolder.tvWrite.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvWrite.setVisibility(View.GONE);
        }
        viewOnclick(viewHolder, convertView, position, bean);
        return convertView;
    }

    private void viewOnclick(ViewHolder viewHolder, View convertView, int position, final MaintenanceInfoEntity bean) {
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getStatus().equals("1")) {
                    Intent intent = new Intent(context, GuanhuDataActivity.class);
                    intent.putExtra("code", bean.getId());//养护编号
                    context.startActivity(intent);
                }
            }
        });
        viewHolder.tvWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GuanhuSubmitActivity.class);
                intent.putExtra("maintenanceCode", bean.getId());//养护编号
                intent.putExtra("code", bean.getCode());//植物编号
                intent.putExtra("type", bean.getState());//养护类型
                intent.putExtra("address", bean.getAddress());//养护地点
                intent.putExtra("time", bean.getTime());//养护时间
                context.startActivity(intent);
            }
        });
    }


    static class ViewHolder {
        @InjectView(R.id.tv_code_item)
        TextView tvCode;
        @InjectView(R.id.tv_state_item)
        TextView tvState;
        @InjectView(R.id.tv_address_item)
        TextView tvAddress;
        @InjectView(R.id.tv_time_item)
        TextView tvTime;
        @InjectView(R.id.tv_write_item)
        TextView tvWrite;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
