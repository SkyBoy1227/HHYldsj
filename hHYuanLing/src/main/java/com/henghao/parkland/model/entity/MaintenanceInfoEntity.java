package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 晏琦云 on 2017/2/13.
 * 养护信息实体
 * id :         养护编号
 * code :       植物编号
 * state :      养护状态
 * time :       养护时间
 * address :    养护地点
 * userId :     用户ID
 * deptId :     部门ID
 * status :     管护状态（0：未管护 1：已管护）
 */
public class MaintenanceInfoEntity {

    // 养护编号
    @Expose
    @SerializedName("id")
    private String id;
    // 植物编号
    @Expose
    @SerializedName("code")
    private String code;
    // 养护状态
    @Expose
    @SerializedName("state")
    private String state;
    // 养护时间
    @Expose
    @SerializedName("time")
    private String time;
    // 养护地点
    @Expose
    @SerializedName("address")
    private String address;
    // 用户ID
    @Expose
    @SerializedName("userId")
    private String userId;
    // 部门ID
    @Expose
    @SerializedName("deptId")
    private String deptId;
    // 管护状态（0：未管护 1：已管护）
    @Expose
    @SerializedName("status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
