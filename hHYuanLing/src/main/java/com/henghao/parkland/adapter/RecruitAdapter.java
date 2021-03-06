package com.henghao.parkland.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.workshow.RecruitDetailActivity;
import com.henghao.parkland.model.entity.RecruitEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 工作台人才招聘展示〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RecruitAdapter extends ArrayAdapter<RecruitEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    public RecruitAdapter(ActivityFragmentSupport activityFragment, List<RecruitEntity> mList) {
        super(activityFragment, R.layout.item_recruit, mList);
        this.mActivityFragmentSupport = activityFragment;
        this.inflater = LayoutInflater.from(activityFragment);
        this.mBitmapUtils = new BitmapUtils(activityFragment, Constant.CACHE_DIR_PATH);
        this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
        this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.item_recruit, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RecruitEntity entity = getItem(position);
        if (entity.getTp() == 1) {
            holder.tvTp.setText("招聘");
            holder.tvCompanyName.setVisibility(View.VISIBLE);
        } else if (entity.getTp() == 2) {
            holder.tvTp.setText("求职");
            holder.tvCompanyName.setVisibility(View.GONE);
        }
        if (entity.getCharacters() == 1) {
            holder.tvCharacters.setText("全职");
        } else if (entity.getCharacters() == 2) {
            holder.tvCharacters.setText("兼职");
        }
        holder.tvPositions.setText(entity.getPositions());
        holder.tvDate.setText(entity.getDate());
        holder.tvCompanyName.setText(entity.getCompanyName());
        holder.tvMoney.setText(entity.getMoney());
        viewClick(holder, convertView, position);
        return convertView;
    }

    private void viewClick(ViewHolder mHodlerView, View convertView, final int position) {
        final RecruitEntity mentity = getItem(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivityFragmentSupport, RecruitDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.INTNET_DATA, mentity);
                intent.putExtra("bundle", bundle);
                mActivityFragmentSupport.startActivity(intent);
            }
        });
    }


    static class ViewHolder {
        @InjectView(R.id.tv_tp)
        TextView tvTp;
        @InjectView(R.id.tv_characters)
        TextView tvCharacters;
        @InjectView(R.id.tv_companyName)
        TextView tvCompanyName;
        @InjectView(R.id.tv_positions)
        TextView tvPositions;
        @InjectView(R.id.tv_money)
        TextView tvMoney;
        @InjectView(R.id.tv_date)
        TextView tvDate;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
