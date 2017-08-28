package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ASUS on 2017/8/13.
 * 用户信息实体
 * uid :        用户编号
 * name :       姓名
 * userName :   用户名
 * tel :        联系方式
 * member :     是否为会员（0：是 1：否）
 * email :      邮箱
 * sex :        性别（0：男 1：女）
 * date :       注册时间
 * idCard :     身份证号
 * comp :       所属企业
 * dept :       所属部门
 * lev :        用户等级（0~10依次递减）
 */
public class UserLoginEntity implements Serializable {

    // 用户编号
    @Expose
    @SerializedName("uid")
    private String uid;
    // 姓名
    @Expose
    @SerializedName("name")
    private String name;
    // 用户名
    @Expose
    @SerializedName("userName")
    private String userName;
    // 联系方式
    @Expose
    @SerializedName("tel")
    private String tel;
    // 是否为会员（0：是 1：否）
    @Expose
    @SerializedName("member")
    private String member;
    // 邮箱
    @Expose
    @SerializedName("email")
    private String email;
    // 性别（0：男 1：女）
    @Expose
    @SerializedName("sex")
    private int sex;
    // 注册时间
    @Expose
    @SerializedName("date")
    private String date;
    // 身份证号
    @Expose
    @SerializedName("idCard")
    private String idCard;
    // 所属企业
    @Expose
    @SerializedName("comp")
    private String comp;
    // 所属部门
    @Expose
    @SerializedName("dept")
    private String dept;
    // 等级（0~10依次递减）
    @Expose
    @SerializedName("lev")
    private int lev;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getLev() {
        return lev;
    }

    public void setLev(int lev) {
        this.lev = lev;
    }
}
