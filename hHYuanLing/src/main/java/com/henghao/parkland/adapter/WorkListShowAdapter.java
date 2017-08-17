package com.henghao.parkland.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 工作台信息展示〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class WorkListShowAdapter extends ArrayAdapter<String> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    public WorkListShowAdapter(ActivityFragmentSupport activityFragment, List<String> mList) {
        super(activityFragment, R.layout.item_worklist, mList);
        this.mActivityFragmentSupport = activityFragment;
        this.inflater = LayoutInflater.from(activityFragment);
        this.mBitmapUtils = new BitmapUtils(activityFragment, Constant.CACHE_DIR_PATH);
        this.mBitmapUtils.configDefaultLoadFailedImage(R.drawable.img_loading_fail_big);
        this.mBitmapUtils.configDefaultLoadingImage(R.drawable.img_loading_default_big);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HodlerView mHodlerView = null;
        if (convertView == null) {
            mHodlerView = new HodlerView();
            convertView = this.inflater.inflate(R.layout.item_worklist, null);
            mHodlerView.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mHodlerView.tv_des = (TextView) convertView.findViewById(R.id.tv_des);
            convertView.setTag(mHodlerView);
        } else {
            mHodlerView = (HodlerView) convertView.getTag();
        }
        mHodlerView.tv_des.setText("");
        switch (position) {
            case 0:
                mHodlerView.tv_title.setText("唱响绿色变奏曲——贵阳发展苗林产业纪实");
                break;
            case 1:
                mHodlerView.tv_title.setText("贵阳今年已完成造林及苗林结合培育13.8万亩");
                break;
        }
        return convertView;
    }

    private class HodlerView {

        TextView tv_title;

        TextView tv_des;
    }
}
