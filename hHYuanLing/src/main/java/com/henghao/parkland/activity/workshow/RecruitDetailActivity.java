package com.henghao.parkland.activity.workshow;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.henghao.parkland.ActivityFragmentSupport;
import com.henghao.parkland.Constant;
import com.henghao.parkland.R;
import com.henghao.parkland.model.entity.RecruitEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 晏琦云 on 2017/3/16.
 * 工作台人才招聘展示
 */
public class RecruitDetailActivity extends ActivityFragmentSupport {

    @InjectView(R.id.tv_contact_employee)
    TextView tvContactEmployee;
    @InjectView(R.id.tv_positions_employee)
    TextView tvPositionsEmployee;
    @InjectView(R.id.tv_characters_employee)
    TextView tvCharactersEmployee;
    @InjectView(R.id.tv_money_employee)
    TextView tvMoneyEmployee;
    @InjectView(R.id.tv_email_employee)
    TextView tvEmailEmployee;
    @InjectView(R.id.tv_tel_employee)
    TextView tvTelEmployee;
    @InjectView(R.id.tv_date_employee)
    TextView tvDateEmployee;
    @InjectView(R.id.tv_experience_employee)
    TextView tvExperienceEmployee;
    @InjectView(R.id.tv_evaluate_employee)
    TextView tvEvaluateEmployee;
    @InjectView(R.id.ll_employee)
    LinearLayout llEmployee;
    @InjectView(R.id.tv_companyName_employer)
    TextView tvCompanyNameEmployer;
    @InjectView(R.id.tv_companyAdress_employer)
    TextView tvCompanyAdressEmployer;
    @InjectView(R.id.tv_companyIntro_employer)
    TextView tvCompanyIntroEmployer;
    @InjectView(R.id.tv_contact_employer)
    TextView tvContactEmployer;
    @InjectView(R.id.tv_tel_employer)
    TextView tvTelEmployer;
    @InjectView(R.id.tv_email_employer)
    TextView tvEmailEmployer;
    @InjectView(R.id.tv_positions_employer)
    TextView tvPositionsEmployer;
    @InjectView(R.id.tv_characters_employer)
    TextView tvCharactersEmployer;
    @InjectView(R.id.tv_workAdress_employer)
    TextView tvWorkAdressEmployer;
    @InjectView(R.id.tv_content_employer)
    TextView tvContentEmployer;
    @InjectView(R.id.tv_money_employer)
    TextView tvMoneyEmployer;
    @InjectView(R.id.tv_date_employer)
    TextView tvDateEmployer;
    @InjectView(R.id.ll_employer)
    LinearLayout llEmployer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivityFragmentView.viewMain(R.layout.activity_recruitdetail);
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
        initWithBar();
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftTextView.setText("返回");
        initWithCenterBar();
        mCenterTextView.setText("人才招聘");
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        RecruitEntity mEntity = (RecruitEntity) bundle.getSerializable(Constant.INTNET_DATA);
        llEmployee.setVisibility(View.GONE);
        llEmployer.setVisibility(View.GONE);
        // 工作性质（1为全职/2为兼职）
        if (mEntity.getCharacters() == 1) {
            tvCharactersEmployee.setText("工作性质：全职");
            tvCharactersEmployer.setText("工作性质：全职");
        } else if (mEntity.getCharacters() == 2) {
            tvCharactersEmployee.setText("工作性质：兼职");
            tvCharactersEmployer.setText("工作性质：兼职");
        }
        //根据信息的类型（招聘信息/求职信息）显示不同的数据
        if (mEntity.getTp() == 1) {//招聘信息
            llEmployer.setVisibility(View.VISIBLE);
            tvCompanyAdressEmployer.setText("公司地址：" + mEntity.getCompanyAdress());
            tvCompanyIntroEmployer.setText("公司简介：" + mEntity.getCompanyIntro());
            tvContactEmployer.setText("联系人：" + mEntity.getContact());
            tvTelEmployer.setText("联系电话：" + mEntity.getTel());
            tvEmailEmployer.setText("邮箱：" + mEntity.getEmail());
            tvPositionsEmployer.setText("职位：" + mEntity.getPositions());
            tvWorkAdressEmployer.setText("工作地址：" + mEntity.getWorkAdress());
            tvMoneyEmployer.setText("月薪：" + mEntity.getMoney());
            tvDateEmployer.setText("发布时间：" + mEntity.getDate() + mEntity.getTime());
            tvContentEmployer.setText("工作内容：" + mEntity.getContent());
        } else if (mEntity.getTp() == 2) {//求职信息
            llEmployee.setVisibility(View.VISIBLE);
            tvContactEmployee.setText("联系人：" + mEntity.getContact());
            tvPositionsEmployee.setText("求职职位：" + mEntity.getPositions());
            tvMoneyEmployee.setText("月薪：" + mEntity.getMoney());
            tvEmailEmployee.setText("邮箱：" + mEntity.getEmail());
            tvTelEmployee.setText("联系电话：" + mEntity.getTel());
            tvDateEmployee.setText("发布时间：" + mEntity.getDate() + mEntity.getTime());
            tvExperienceEmployee.setText("工作经历：" + mEntity.getExperience());
            tvEvaluateEmployee.setText("自我评价：" + mEntity.getEvaluate());
        }
    }
}
