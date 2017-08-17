package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

/**
 * Created by Administrator on 2017/3/2 0002.
 */

public class MyWorkerEntity extends IdEntity {

    /**
     * 表主键：int mid
     * 用户编号：int uid
     * 人员：String personnel
     * 工作内容：String details
     * 时间：String dates
     * 工作类型：String workType	POST	uid：用户编号
     * 时间精确到分钟
     */
    private boolean isChecked;//是否被选中
    @Expose
    private int mid;//表主键
    @Expose
    private String personnel;

    @Expose
    private String details;

    @Expose
    private String dates;

    @Expose
    private String workType;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }
}
