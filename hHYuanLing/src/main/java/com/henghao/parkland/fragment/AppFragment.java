package com.henghao.parkland.fragment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.benefit.buy.library.utils.tools.ToolsJson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.henghao.parkland.BuildConfig;
import com.henghao.parkland.R;
import com.henghao.parkland.adapter.AppGridAdapter;
import com.henghao.parkland.model.entity.AppGridEntity;
import com.henghao.parkland.model.entity.BaseEntity;
import com.henghao.parkland.model.entity.CarouselEntity;
import com.henghao.parkland.utils.GlideImageLoader;
import com.henghao.parkland.utils.Requester;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

import static com.benefit.buy.library.views.RotateBitmap.TAG;

/**
 * 应用〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2016年8月15日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AppFragment extends FragmentSupport {

    @InjectView(R.id.banner)
    Banner banner;
    @InjectView(R.id.gridview_app)
    GridView gridview;

    private int height;//屏幕高度
    private List<String> resources;
    private Call carouselCall;//轮播图请求

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.fragment_app);
        this.mActivityFragmentView.viewEmpty(R.layout.activity_empty);
        this.mActivityFragmentView.viewEmptyGone();
        this.mActivityFragmentView.viewLoading(View.GONE);
        ButterKnife.inject(this, this.mActivityFragmentView);
        initWidget();
        initData();
        return this.mActivityFragmentView;
    }

    private void initData() {
        List<AppGridEntity> mList = new ArrayList<AppGridEntity>();
        //第一个
        AppGridEntity mEntity2 = new AppGridEntity();
        mEntity2.setImageId(R.drawable.app_two);
        mEntity2.setName("养护管理");
        mList.add(mEntity2);
        //第二个
        AppGridEntity mEntity4 = new AppGridEntity();
        mEntity4.setImageId(R.drawable.app_one);
        mEntity4.setName("行业协会");
        mList.add(mEntity4);
        AppGridAdapter maAdapter = new AppGridAdapter(this.mActivity, mList);
        this.gridview.setAdapter(maAdapter);
        maAdapter.notifyDataSetChanged();
        carouselCall = Requester.carousel(carouselCallBack);
    }

    private DefaultCallback carouselCallBack = new DefaultCallback() {
        @Override
        public void onFailure(Exception e, int code) {
            if (BuildConfig.DEBUG) Log.e(TAG, "onFailure: code = " + code, e);
            e.printStackTrace();
            Toast.makeText(mActivity, "网络访问错误！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(String response) {
            if (BuildConfig.DEBUG) Log.d(TAG, "onSuccess: " + response);
            try {
                Type baseType = new TypeToken<BaseEntity>() {
                }.getType();
                BaseEntity baseEntity = ToolsJson.parseObjecta(response, baseType);
                int errorCode = baseEntity.getErrorCode();
                if (errorCode == 0) {
                    String jsonStr = ToolsJson.toJson(baseEntity.getData());
                    Type carouselType = new TypeToken<CarouselEntity>() {
                    }.getType();
                    CarouselEntity carouselEntity = ToolsJson.parseObjecta(jsonStr, carouselType);
                    resources = carouselEntity.getPicture();
                    if (resources != null) {
                        //轮播图
                        banner.setImages(resources)
                                .setImageLoader(new GlideImageLoader())
                                .setIndicatorGravity(BannerConfig.RIGHT)
                                .setBannerAnimation(Transformer.ForegroundToBackground)
                                .start();
                    }
                } else {
                    mActivity.msg("图片加载错误！");
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 标题操作 〈一句话功能简述〉 〈功能详细描述〉
     *
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private void initwithContent() {
        initWithCenterBar();
        this.mCenterTextView.setVisibility(View.VISIBLE);
        this.mCenterTextView.setText(R.string.app);
        initWithBar();
        this.mLeftImageView.setVisibility(View.VISIBLE);
        this.mLeftImageView.setImageResource(R.drawable.home_liebiao);
        //获取屏幕高度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        height = dm.heightPixels;
        banner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / 4));
    }

    public void initWidget() {
        initwithContent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
