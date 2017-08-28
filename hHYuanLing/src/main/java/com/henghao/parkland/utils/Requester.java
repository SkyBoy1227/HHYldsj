package com.henghao.parkland.utils;

import com.alibaba.fastjson.JSON;
import com.henghao.parkland.ProtocolUrl;
import com.henghao.parkland.model.entity.DeleteEntity;
import com.higdata.okhttphelper.OkHttpController;
import com.higdata.okhttphelper.callback.BaseCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 请求管理class，将所有网络请求写入此类以方便维护
 */
public class Requester {

    /**
     * 获取请求地址
     *
     * @param url 接口url
     * @return 请求url
     */
    public static String getRequestURL(String url) {
        String separator = "/";
        String host = ProtocolUrl.ROOT_URL;
        if (!url.startsWith(separator)) url = "/" + url;
        return String.format("%s%s", host, url);
    }

    /**
     * 获取慧正请求地址
     *
     * @param url 接口url
     * @return 请求url
     */
    public static String getRequestHZURL(String url) {
        String separator = "/";
        String host = ProtocolUrl.HZ_ROOT_URL;
        if (!url.startsWith(separator)) url = "/" + url;
        return String.format("%s%s", host, url);
    }

    /************************
     * 用户登录相关
     **************************/
    /**
     * 获取验证码
     *
     * @param headers  请求头
     * @param callback 回调
     * @return
     */
    public static Call authCode(Map<String, String> headers, BaseCallback callback) {
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.AUTHCODE), headers, callback);
    }

    /**
     * 注册
     *
     * @param userName    用户名
     * @param passWord    密码
     * @param name        姓名
     * @param tel         手机号
     * @param email       邮箱
     * @param sex         性别（0：男 1：女）
     * @param idCard      身份证号
     * @param companyName 企业名称
     * @param files       身份证正反照
     * @param userCode    验证码
     * @param callback    回调
     * @return {@link Call}
     */
    public static Call register(String userName, String passWord, String name, String tel, String email, int sex, String idCard, String companyName, List<File> files, String userCode, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        params.put("passWord", passWord);
        params.put("name", name);
        params.put("tel", tel);
        params.put("email", email);
        params.put("sex", sex);
        params.put("idCard", idCard);
        params.put("companyName", companyName);
        params.put("userCode", userCode);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.APP_REG), params, files, null, callback);
    }

    /**
     * 登录
     *
     * @param userName 用户名
     * @param passWord 密码
     * @param userCode 验证码
     * @param headers  请求头
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call login(String userName, String passWord, String userCode, Map<String, String> headers, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        params.put("passWord", passWord);
        params.put("userCode", userCode);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.APP_LOGIN), params, headers, callback);
    }
    /************************ 用户登录相关end **************************/

    /************************
     * 签到相关
     **************************/
    /**
     * 查询当天签到次数
     *
     * @param uid      uid
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call qiandaoQuery(String uid, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.APP_NUMBEROFQIANDAO), params, null, callback);
    }

    /**
     * 查询所有签到情况
     *
     * @param page     页数
     * @param uid      用户ID
     * @param comp     所属企业
     * @param dept     所属部门
     * @param lev      用户等级（0~10依次递减）
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call findSignIn(int page, String uid, String comp, String dept, int lev, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("uid", uid);
        params.put("comp", comp);
        params.put("dept", dept);
        params.put("lev", lev);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.FIND_SIGNIN), params, null, callback);
    }

    /**
     * 用户签到
     *
     * @param address   签到地址
     * @param comments  备注
     * @param comp      所属企业
     * @param uid       用户ID
     * @param name      姓名
     * @param latitude  纬度
     * @param longitude 经度
     * @param company   当前企业
     * @param dept      所属部门
     * @param callback  回调
     * @return
     */
    public static Call signIn(String address, String comments, String comp, String uid, String name, double latitude, double longitude, String company, String dept, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("address", address);
        params.put("comments", comments);
        params.put("comp", comp);
        params.put("uid", uid);
        params.put("name", name);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("company", company);
        params.put("dept", dept);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.SIGNIN), params, null, callback);
    }
    /************************ 签到相关end **************************/

    /************************
     * 工作台展示
     **************************/
    /**
     * 苗木信息查询
     *
     * @param page     页数
     * @param callback 回调
     * @return
     */
    public static Call findSeedling(int page, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.FIND_SEEDLING), params, null, callback);
    }

    /**
     * 设备租赁查询
     *
     * @param page     页数
     * @param callback 回调
     * @return
     */
    public static Call findEquipment(int page, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.FIND_EQUIPMENT), params, null, callback);
    }

    /**
     * 人员招聘查询
     *
     * @param page     页数
     * @param callback 回调
     * @return
     */
    public static Call findRecruit(int page, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.FIND_RECRUIT), params, null, callback);
    }

    /**
     * 招标信息查询
     *
     * @param page     页数
     * @param callback 回调
     * @return
     */
    public static Call findBid(int page, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.FIND_BID), params, null, callback);
    }

    /************************ 工作台展示end **************************/

    /************************
     * 项目管理相关
     **************************/
    /**
     * 项目信息查询
     *
     * @param page     页数
     * @param uid      用户ID
     * @param deptId   部门编号
     * @param callback 回调
     * @return
     */
    public static Call findXmxx(int page, String uid, String deptId, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("uid", uid);
        params.put("deptId", deptId);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.FIND_XMXX), params, null, callback);
    }

    /**
     * 项目信息提交
     *
     * @param uid       用户ID
     * @param deptId    部门编号
     * @param name      项目名称
     * @param principal 项目负责人
     * @param tel       联系方式
     * @param personNum 项目人数
     * @param address   项目地点
     * @param company   施工单位
     * @param startTime 开工时间
     * @param endTime   竣工时间
     * @param callback  回调
     * @return
     */
    public static Call addXmxx(String uid, String deptId, String name, String principal, String tel, int personNum, String address, String company, String startTime, String endTime, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        params.put("deptId", deptId);
        params.put("name", name);
        params.put("principal", principal);
        params.put("tel", tel);
        params.put("personNum", personNum);
        params.put("address", address);
        params.put("company", company);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.ADD_XMXX), params, null, callback);
    }

    /**
     * 会审结果查询
     *
     * @param page     页数
     * @param uid      用户ID
     * @param deptId   部门编号
     * @param callback 回调
     * @return
     */
    public static Call findHsjg(int page, String uid, String deptId, BaseCallback callback) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("uid", uid);
        params.put("deptId", deptId);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.FIND_HSJG), params, null, callback);
    }
    /************************ 项目管理相关end **************************/
    /************************
     * 养护管理
     **************************/
    /**
     * 植物管护信息录入
     *
     * @param maintenanceCode 养护编号
     * @param type            养护类型
     * @param userId          用户ID
     * @param dept            所属部门
     * @param code            植物二维码
     * @param address         养护地点
     * @param personnel       养护人员
     * @param content         养护内容
     * @param time            养护时间
     * @param problem         发现问题
     * @param cleaning        陆地保洁情况
     * @param plantGrowth     植物长势
     * @param remarks         备注信息
     * @param files           养护前后图片
     * @param callback        回调
     * @return {@link Call}
     */
    public static Call addInformation(String maintenanceCode, String type, String userId, String dept, String code, String address, String personnel, String content, String time, String problem, String cleaning, String plantGrowth, String remarks, List<File> files, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("maintenanceCode", maintenanceCode);
        params.put("type", type);
        params.put("userId", userId);
        params.put("dept", dept);
        params.put("code", code);
        params.put("address", address);
        params.put("personnel", personnel);
        params.put("content", content);
        params.put("time", time);
        params.put("problem", problem);
        params.put("cleaning", cleaning);
        params.put("plantGrowth", plantGrowth);
        params.put("remarks", remarks);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.ADD_INFORMATION), params, files, null, callback);
    }

    /**
     * 植物养护信息录入
     *
     * @param code     植物二维码
     * @param state    养护状态
     * @param time     养护时间
     * @param address  养护地点
     * @param userId   用户ID
     * @param dept     所属部门
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call addMaintenanceInformation(String code, String state, String time, String address, String userId, String dept, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("state", state);
        params.put("time", time);
        params.put("address", address);
        params.put("userId", userId);
        params.put("dept", dept);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.ADD_MAINTENANCE_INFORMATION), params, null, callback);
    }

    /**
     * 养护前植物信息录入
     *
     * @param code           植物二维码
     * @param name           植物名称
     * @param purpose        植物用途
     * @param specifications 植物规格
     * @param address        种植地点
     * @param time           录入时间
     * @param dept           所属部门
     * @param callback       回调
     * @return {@link Call}
     */
    public static Call addPlantInformation(String code, String name, String purpose, String specifications, String address, String time, String dept, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("name", name);
        params.put("purpose", purpose);
        params.put("specifications", specifications);
        params.put("address", address);
        params.put("time", time);
        params.put("dept", dept);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.ADD_PLANT_INFORMATION), params, null, callback);
    }

    /**
     * 植物养护信息查询
     *
     * @param userId   用户ID
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call findMaintenanceInfo(String userId, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.FIND_MAINTENANCE_INFO), params, null, callback);
    }

    /**
     * 养护前植物信息查询
     *
     * @param code     植物二维码
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call findPlantInformation(String code, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.FIND_PLANT_INFORMATION), params, null, callback);
    }

    /**
     * 植物管护信息查询
     *
     * @param userId   用户ID
     * @param code     养护编号
     * @param callback 回调
     * @return
     */
    public static Call findManagementInfo(String userId, String code, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("code", code);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.FIND_MANAGEMENT_INFO), params, null, callback);
    }
    /************************ 养护管理 end **************************/

    /**
     * 删除变更管理条目
     *
     * @param dataList 要删除的条目List
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call changeManageDeleteInfo(List<DeleteEntity> dataList, BaseCallback callback) {
        String json = JSON.toJSONString(dataList);
        return OkHttpController.doJsonRequest(getRequestURL(ProtocolUrl.DELETE_ALTERATION), json, null, callback);
    }

    /**
     * 提交变更管理条目
     *
     * @param uid             uid
     * @param pid             项目信息ID
     * @param confirmingParty 确认方
     * @param times           变更时间
     * @param files           变更依据
     * @param callback        回调
     * @return {@link Call}
     */
    public static Call changeManageSubmit(String uid, String pid, String confirmingParty, String times, List<File> files, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("pid", pid);
        params.put("confirmingParty", confirmingParty);
        params.put("times", times);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.PROJECT_SAVEALTERATIONMSG), params, files, null, callback);
    }

    /**
     * 删除进度申报条目
     *
     * @param dataList 要删除的条目List
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call declareDeleteInfo(List<DeleteEntity> dataList, BaseCallback callback) {
        String json = JSON.toJSONString(dataList);
        return OkHttpController.doJsonRequest(getRequestURL(ProtocolUrl.DELETE_DECLARATION), json, null, callback);
    }

    /**
     * 进度申报提交
     *
     * @param dates    时间
     * @param uid      uid
     * @param pid      项目ID
     * @param files    申报文件
     * @param callback 回调
     * @return {@link Call}
     */
    public static Call declareSubmit(String dates, String uid, String pid, List<File> files, BaseCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("dates", dates);
        params.put("uid", uid);
        params.put("pid", pid);
        return OkHttpController.doRequest(getRequestURL(ProtocolUrl.PROJECT_SAVEJDSB), params, files, null, callback);
    }
}
