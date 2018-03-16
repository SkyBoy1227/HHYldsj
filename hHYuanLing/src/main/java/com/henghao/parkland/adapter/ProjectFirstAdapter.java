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
import com.henghao.parkland.Constant;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.R;
import com.henghao.parkland.activity.WebviewActivity;
import com.henghao.parkland.model.entity.AppGridEntity;
import com.henghao.parkland.utils.Requester;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 项目管理-项目信息〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015年12月21日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProjectFirstAdapter extends ArrayAdapter<AppGridEntity> {

    private final LayoutInflater inflater;

    private final BitmapUtils mBitmapUtils;

    private final ActivityFragmentSupport mActivityFragmentSupport;

    private final List<AppGridEntity> mList;

    public ProjectFirstAdapter(ActivityFragmentSupport activityFragment, List<AppGridEntity> list) {
        super(activityFragment, R.layout.item_gridview_textimage, list);
        this.mActivityFragmentSupport = activityFragment;
        this.mList = list;
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
            convertView = this.inflater.inflate(R.layout.item_gridview_textimage, null);
            mHodlerView.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            mHodlerView.image_title = (ImageView) convertView.findViewById(R.id.image_title);
            convertView.setTag(mHodlerView);
        } else {
            mHodlerView = (HodlerView) convertView.getTag();
        }
        mHodlerView.image_title.setImageResource(getItem(position).getImageId());
        mHodlerView.tv_title.setVisibility(View.VISIBLE);
        mHodlerView.tv_title.setText(getItem(position).getName());
        viewOnClick(mHodlerView, convertView, position);
        return convertView;
    }


    /**
     * 点击
     *
     * @param mHodlerView
     * @param convertView
     * @param position
     */
    private void viewOnClick(HodlerView mHodlerView, View convertView, final int position) {
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
                        //会审结果
                        intent.putExtra("title", "会审结果");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_HSJG) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 1:
                        //供货方信息
                        intent.putExtra("title", "供货方信息");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_GHFXX) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 2:
                        //施工人员
                        intent.putExtra("title", "施工人员");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_SGRY) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 3:
                        //工程质量监督报告
                        intent.putExtra("title", "工程质量监督报告");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_GCZLJDBG) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 4:
                        //设备信息
                        intent.putExtra("title", "设备信息");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_SBXX) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 5:
                        //工序报验
                        intent.putExtra("title", "工序报验");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_GXBY) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 6:
                        //现场勘察
                        intent.putExtra("title", "现场勘察");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_XCKC) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 7:
                        //变更管理
                        intent.putExtra("title", "变更管理");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_BGGL) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 8:
                        //竣工验收
                        intent.putExtra("title", "竣工验收");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_JGYS) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 9:
                        //进度申报
                        intent.putExtra("title", "进度申报");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_JDSB) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 10:
                        //技术交底
                        intent.putExtra("title", "技术交底");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_JSJD) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                    case 11:
                        //项目结算
                        intent.putExtra("title", "项目结算");
                        intent.putExtra("url", Requester.getRequestHZURL(mActivityFragmentSupport.getUserComp() + "/" + ProtocolUrl.ADD_XMJS) + mActivityFragmentSupport.getLoginUserName());
                        intent.setClass(mActivityFragmentSupport, WebviewActivity.class);
                        mActivityFragmentSupport.startActivity(intent);
                        break;
                }
            }
        });
    }

    private class HodlerView {

        TextView tv_title;

        ImageView image_title;
    }

    @Override
    public int getCount() {
        return (this.mList.size());
    }
}
