package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.henghao.parkland.model.IdEntity;

import java.util.ArrayList;

/**
 * Created by 晏琦云 on 2017/3/2.
 */

public class ProjectBGManageEntity extends IdEntity {
    /**
     * 表主键：int aid
     * 确认方：String confirmingParty
     * 变更时间：String times
     * 变更依据图：file file
     * 项目名称：String name
     */
    private boolean isChecked;//是否被选中
    @Expose
    private int aid;
    @Expose
    private String confirmingParty;
    @Expose
    private String name;
    @Expose
    private String times;
    @Expose
    private String files;
    @Expose
    private ArrayList<String> url;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfirmingParty() {
        return confirmingParty;
    }

    public void setConfirmingParty(String confirmingParty) {
        this.confirmingParty = confirmingParty;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }


    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}
