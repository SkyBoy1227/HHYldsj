package com.henghao.parkland.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ASUS on 2017/8/30.
 * id :             表主键
 * picture :        轮播图地址
 * picName :        图片名称
 * description :    图片描述
 * effect :         图片作用
 * urlAddress :     图片跳转地址
 * date :           图片上传日期
 */

public class CarouselEntity implements Serializable {

    // 表主键
    @Expose
    @SerializedName("id")
    private String id;
    // 图片名称
    @Expose
    @SerializedName("picName")
    private String picName;
    // 图片描述
    @Expose
    @SerializedName("description")
    private String description;
    // 图片作用
    @Expose
    @SerializedName("effect")
    private String effect;
    // 图片跳转地址
    @Expose
    @SerializedName("urlAddress")
    private String urlAddress;
    // 图片上传日期
    @Expose
    @SerializedName("date")
    private String date;
    // 轮播图地址
    @Expose
    @SerializedName("picture")
    private List<String> picture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }
}
