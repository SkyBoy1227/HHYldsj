package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 晏琦云 on 2017/2/19
 * 管护信息实体
 * code :           植物编码
 * address :        养护地点
 * time :           养护时间
 * personnel :      养护人员
 * content :        养护内容
 * frontPhoto :     养护前照片
 * afterPhoto :     养护后照片
 * problem :        发现问题
 * cleaning :       保洁情况
 * plantGrowth :    植物长势
 * remarks :        备注
 * type :           养护类型
 */

public class ManagementInfoEntity implements Serializable {

    // 植物编码
    @Expose
    @SerializedName("code")
    private String code;
    // 养护地点
    @Expose
    @SerializedName("address")
    private String address;
    // 养护时间
    @Expose
    @SerializedName("time")
    private String time;
    // 养护人员
    @Expose
    @SerializedName("personnel")
    private String personnel;
    // 养护内容
    @Expose
    @SerializedName("content")
    private String content;
    // 养护前照片
    @Expose
    @SerializedName("frontPhoto")
    private String frontPhoto;
    // 养护后照片
    @Expose
    @SerializedName("afterPhoto")
    private String afterPhoto;
    // 发现问题
    @Expose
    @SerializedName("problem")
    private String problem;
    // 保洁情况
    @Expose
    @SerializedName("cleaning")
    private String cleaning;
    // 植物长势
    @Expose
    @SerializedName("plantGrowth")
    private String plantGrowth;
    // 备注
    @Expose
    @SerializedName("remarks")
    private String remarks;
    // 养护类型
    @Expose
    @SerializedName("type")
    private String type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrontPhoto() {
        return frontPhoto;
    }

    public void setFrontPhoto(String frontPhoto) {
        this.frontPhoto = frontPhoto;
    }

    public String getAfterPhoto() {
        return afterPhoto;
    }

    public void setAfterPhoto(String afterPhoto) {
        this.afterPhoto = afterPhoto;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getCleaning() {
        return cleaning;
    }

    public void setCleaning(String cleaning) {
        this.cleaning = cleaning;
    }

    public String getPlantGrowth() {
        return plantGrowth;
    }

    public void setPlantGrowth(String plantGrowth) {
        this.plantGrowth = plantGrowth;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
