package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.henghao.parkland.model.IdEntity;

/**
 * Created by 晏琦云 on 2017/8/10.
 * 招标信息实体
 * "bid":           数据id,
 * "titleName":     标题名称,
 * "content":       内容,
 * "contact":       联系人,
 * "tel":           联系方式,
 * "date":          发布日期,
 * "time":          发布时间
 */
public class BidEntity extends IdEntity {

    // 数据id
    @Expose
    @SerializedName("bid")
    private int bid;
    // 标题名称
    @Expose
    @SerializedName("titleName")
    private String titleName;
    // 内容
    @Expose
    @SerializedName("content")
    private String content;
    // 联系人
    @Expose
    @SerializedName("contact")
    private String contact;
    // 联系方式
    @Expose
    @SerializedName("tel")
    private String tel;
    // 发布日期
    @Expose
    @SerializedName("date")
    private String date;
    //发布时间
    @Expose
    @SerializedName("time")
    private String time;

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}