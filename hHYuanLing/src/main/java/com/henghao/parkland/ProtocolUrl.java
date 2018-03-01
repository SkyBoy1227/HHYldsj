/*
 * 文件名：ProtocolUrl.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author zhangxianwen
 * @version HDMNV100R001, 2015-4-20
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ProtocolUrl {

    /**
     * 服务端根地址
     */
    public static String ROOT_URL = "";

    /**
     * 慧正服务器地址
     */
    public static String HZ_ROOT_URL = "";

    static {
        if (BuildConfig.DEBUG) {
            // 测试地址/172.16.13.113
            ROOT_URL = "http://172.16.13.113";
            // 测试地址/172.16.0.121
            HZ_ROOT_URL = "http://172.16.0.121";
        } else {
            // 生产地址
            ROOT_URL = "http://222.85.156.50:81";
            // 生产地址
            HZ_ROOT_URL = "http://222.85.156.50:81";
        }
    }

    /************************
     * 轮播图
     **************************/

    /**
     * 轮播图请求地址
     */
    public static String CAROUSEL = "ylcx/carousel";

    /************************ 轮播图 end **************************/

    /************************
     * 用户相关
     **************************/

    /**
     * 用户登录
     */
    public static String APP_LOGIN = "ylcx/app/login";

    public static String APP_GET_NFCBYID = "login";
    /**
     * 用户注册
     */
    public static String APP_REG = "ylcx/register";
    /**
     * 验证码
     */
    public static String AUTHCODE = "ylcx/authCode";

    /************************ 用户相关 end **************************/

    /************************
     * app系统 相关
     **************************/
    public static final String SYSTEM = "j_appSystem/";

    /**
     * app启动页面信息
     */
    public static final String APP_START = SYSTEM + "appStart";

    /**
     * app引导页面信息
     */
    public static final String APP_GUIDE = SYSTEM + "appGuide";

    /**
     * app系统版本更新
     */
    public static final String APP_SYS_UPDATE = SYSTEM + "appUserUpdate";
    /************************ app系统 end **************************/

    /************************
     * 签到相关
     **************************/

    /**
     * 签到
     */
    public static final String SIGNIN = "ylcx/signIn";

    /**
     * 查询签到次数
     */
    public static final String APP_NUMBEROFQIANDAO = "/report/message";

    /**
     * 查询签到情况
     */
    public static final String FIND_SIGNIN = "ylcx/find/signIn";

    /************************ 签到相关 end **************************/

    /************************
     * 养护管理
     **************************/

    /**
     * 查询当天植物养护信息
     */
    public static final String FIND_MAINTENANCE_INFO = "ylcx/maintenance/findMaintenanceInfo";

    /**
     * 养护前植物信息查询
     */
    public static final String FIND_PLANT_INFORMATION = "ylcx/maintenance/findPlantInformation";

    /**
     * 植物管护信息查询
     */
    public static final String FIND_MANAGEMENT_INFO = "ylcx/maintenance/findManagementInfo";

    /**
     * 植物养护信息录入
     */
    public static final String ADD_MAINTENANCE_INFORMATION = "ylcx/maintenance/addMaintenanceInformation";

    /**
     * 养护前植物信息录入
     */
    public static final String ADD_PLANT_INFORMATION = "ylcx/maintenance/addPlantInformation";

    /**
     * 植物管护信息录入
     */
    public static final String ADD_INFORMATION = "ylcx/maintenance/addInformation";
    /************************ 养护管理 end **************************/

    /************************
     * 项目管理相关
     **************************/

    /**
     * 项目信息提交
     */
    public static final String ADD_XMXX = "horizon/template/form/default.wf?formid=HZ90804e5e1719f9015e174e52b604fa&loginName=";

    /**
     * 会审结果提交
     */
    public static final String ADD_HSJG = "horizon/template/form/default.wf?formid=HZ90804e5e1765cd015e17740b410153&loginName=";

    /**
     * 供货方信息提交
     */
    public static final String ADD_GHFXX = "horizon/template/form/default.wf?formid=HZ90804e5e178180015e178418dd003c&loginName=";

    /**
     * 施工人员提交
     */
    public static final String ADD_SGRY = "horizon/template/form/default.wf?formid=HZ90804e5e17f143015e17ff0702010f&loginName=";

    /**
     * 开工报告提交
     */
    public static final String ADD_KGBG = "horizon/template/form/default.wf?formid=HZ90804e5e17f143015e180f811a028c&loginName=";

    /**
     * 设备信息提交
     */
    public static final String ADD_SBXX = "horizon/template/form/default.wf?formid=HZ90804e5e17f143015e1827a8f90422&loginName=";

    /**
     * 工序报验提交
     */
    public static final String ADD_GXBY = "horizon/template/form/default.wf?formid=HZ90804e5e17f143015e1835a39f054e&loginName=";

    /**
     * 现场勘察提交
     */
    public static final String ADD_XCKC = "horizon/template/form/default.wf?formid=HZ90804e5e17f143015e183c55f20642&loginName=";

    /**
     * 变更管理提交
     */
    public static final String ADD_BGGL = "horizon/template/form/default.wf?formid=HZ90804e5e17f143015e184c55fd082a&loginName=";

    /**
     * 竣工验收提交
     */
    public static final String ADD_JGYS = "horizon/template/form/default.wf?formid=HZ90804e5e1852f8015e18545b94005c&loginName=";

    /**
     * 进度申报提交
     */
    public static final String ADD_JDSB = "horizon/template/form/default.wf?formid=HZ90804e5e1852f8015e185ca8f9015b&loginName=";

    /**
     * 技术交底提交
     */
    public static final String ADD_JSJD = "horizon/template/form/default.wf?formid=HZ90804e5e1852f8015e186ba5a7028c&loginName=";

    /**
     * 项目结算提交
     */
    public static final String ADD_XMJS = "horizon/template/form/default.wf?formid=HZ90804e5e1852f8015e1877bdd903d1&loginName=";

    /**
     * 监理日志提交
     */
    public static final String ADD_JLRZ = "horizon/template/form/default.wf?formid=HZ90804e5e1852f8015e188171bc051d&loginName=";

    /**
     * 施工日志提交
     */
    public static final String ADD_SGRZ = "horizon/template/form/default.wf?formid=HZ90804e5e1852f8015e18ab526c099c&loginName=";

    /**
     * 施工安全日志提交
     */
    public static final String ADD_SGAQRZ = "horizon/template/form/default.wf?formid=HZ90804e5e1852f8015e18b549e20a86&loginName=";

    /**
     * 施工钱包提交
     */
    public static final String ADD_SGQG = "horizon/template/form/default.wf?formid=HZ90804e5e1852f8015e18c318380c67&loginName=";

    /**
     * 所有流程查看
     */
    public static final String XMGL = "horizon/basics/getBasics.wf?loginName=";

    /**
     * 施工钱包查看
     */
    public static final String FIND_SGQB = "resource/skins/bootstrap/view/datatables/view.datatables.jsp?viewid=HZ90804e5e1852f8015e18c8fc7a0d19&loginName=";

    /**
     * 项目信息查看
     */
    public static final String FIND_XMXX = "resource/skins/bootstrap/view/datatables/view.datatables.jsp?viewid=HZ90804e5e1719f9015e176357c3064f&loginName=";

    /************************ 项目管理相关 end **************************/

    /************************
     * 合同管理相关
     **************************/

    /**
     * 主合同提交
     */
    public static final String ADD_ZHT = "horizon/template/form/default.wf?formid=HZ8bb06261d0d4e60161da039d3864c8&loginName=";

    /**
     * 从合同提交
     */
    public static final String ADD_CHT = "horizon/template/form/default.wf?formid=HZ8bb06261d0d4e60161db2dc5ab74f1&loginName=";

    /************************ 合同管理相关 end **************************/

    /************************
     * 任务安排
     **************************/
    /**
     * 工作任务
     */
    public static final String ADD_WORK_TASK = "resource/skins/bootstrap/view/datatables/view.datatables.jsp?viewid=HZ90804e5e1852f8015e18679bce0251&loginName=";

    /**
     * 工作安排
     */
    public static final String ADD_WORK_ARRANGE = "resource/skins/bootstrap/view/datatables/view.datatables.jsp?viewid=HZ90804e5e1852f8015e18cec7430deb&loginName=";

    /**
     * 我的计划提交
     */
    public static final String ADD_MY_PLAN = "horizon/template/form/default.wf?formid=HZ9080f95e26636a015e2681e67d02ca&loginName=";

    /************************ 任务安排 end **************************/

    /**
     * 会审结果查询
     */
    public static final String FIND_HSJG = "ylcx/project/find/hsjg";

    /**
     * 项目信息删除
     */
    public static final String DELETE_PROJECT = "/projectManage/delete/project";

    /**
     * 技术交底删除
     */
    public static final String DELETE_TECHNOLOGY = "/projectManage/delete/technology";

    /**
     * 会审结果删除
     */
    public static final String DELETE_HSRESULT = "/projectManage/delete/hsResult";

    /**
     * 进度申报删除
     */
    public static final String DELETE_DECLARATION = "/projectManage/delete/declaration";

    /**
     * 供货方信息删除
     */
    public static final String DELETE_SUPPLIER = "/projectManage/delete/supplier";

    /**
     * 我的轨迹删除
     */
    public static final String DELETE_MYLOCUS = "/projectManage/delete/mylocus";

    /**
     * 变更管理删除
     */
    public static final String DELETE_ALTERATION = "/projectManage/delete/alteration";

    /**
     * 竣工验收删除
     */
    public static final String DELETE_FINALACCEPTANCE = "/projectManage/delete/finalacceptance";

    /**
     * 施工人员删除
     */
    public static final String DELETE_SGPERSONNEL = "/projectManage/delete/sgPersonnel";

    /**
     * 监理日志删除
     */
    public static final String DELETE_SUPERVISIONLOG = "/projectManage/delete/supervisionlog";

    /**
     * 施工日志删除
     */
    public static final String DELETE_CONSTURCTIONLOG = "/projectManage/delete/constructionLog";

    /**
     * 施工安全日志删除
     */
    public static final String DELETE_CONSTURCTIONSAFELOG = "/projectManage/delete/constructionSafetyLog";

    /**
     * 现场勘察删除
     */
    public static final String DELETE_PROSPECT = "/projectManage/delete/prospect";

    /**
     * 开工报告删除
     */
    public static final String DELETE_KGREPORT = "/projectManage/delete/kgReport";

    /**
     * 设备信息删除
     */
    public static final String DELETE_EQUIPMENT = "/projectManage/delete/equipment";

    /**
     * 工序报验删除
     */
    public static final String DELETE_CHECKOUT = "/projectManage/delete/checkout";

    /**
     * 项目结算删除
     */
    public static final String DELETE_SETTLEMENT = "/projectManage/delete/settlement";

    /**
     * 施工钱包查询
     */
    public static final String PROJECT_SGWALLET = "projectManage/queryWalletMsg";

    /**
     * 施工钱包Excel下载
     */
    public static final String DOWNLOAD_WALLETEXCEL = "download/walletExcel";
    /**
     * 施工钱包提交
     */
    public static final String WALLETE_SUBMIT = "/projectManage/saveWalletMsg";
    /**
     * 开工报告文件下载
     */
    public static final String DOWNLOAD_KGREPORTFILE = "download/kgReportFile";

    /**
     * 查询施工信息
     */
    public static final String PROJECT_SGMSG = "projectManage/queryProjectMsg";

    /**
     * 施工备忘提交
     */
    public static final String PROJECT_SGBEIWANG = "projectManage/saveSgmemoMsg";

    /**
     * 工作备忘提交
     */
    public static final String PROJECT_WORKBEIWANG = "projectManage/saveWorkMemoMsg";

    /**
     * 现场勘查
     */
    public static final String PROJECT_SAVEXCKC = "projectManage/saveProspectMsg";

    /**
     * 技术交底提交
     */
    public static final String PROJECT_SAVEJSJD = "projectManage/saveTechnologyMsg";

    /**
     * 提交用户会审结果信息数据访问接口
     */
    public static final String SAVEHSRESULTMSG = "/projectManage/saveHsResultMsg";//提交用户会审结果信息数据访问接口

    /**
     * 进度申报提交
     */
    public static final String PROJECT_SAVEJDSB = "projectManage/saveDeclarationMsg";

    /**
     * 工序报验提交
     */
    public static final String PROJECT_SAVECHECKOUTMSG = "projectManage/saveCheckoutMsg";
    /**
     * 开工报告
     */
    public static final String PROJECT_SAVEKGBG = "projectManage/saveKgReportMsg";
    /**
     * 现场勘查查询
     */
    public static final String PROJECT_QUERYXCKC = "projectManage/queryProspectMsg";

    /**
     * 施工资料查询
     */
    public static final String PROJECT_QUERYSGZL = "projectManage/queryBuildDateMsg";

    /**
     * 施工资料提交
     */
    public static final String PROJECT_SGINFO = "http://172.16.13.101:8080/YL_BigData/" + "projectManage/saveBiuldDataMsg";

    /**
     * 施工日志查询
     */
    public static final String PROJECT_QUERYCONSTRUCTIONLOGMSG = "projectManage/queryConstructionLogMsg";

    /**
     * 施工安全日志查询
     */
    public static final String PROJECT_QUERYSUMMARYLOGMSG = "projectManage/querySummaryLogMsg";

    /**
     * 监理日志查询
     */
    public static final String PROJECT_QUERYSUPERVISIONLOGMSG = "projectManage/querySupervisionlogMsg";

    /**
     * 竣工验收查询
     */
    public static final String PROJECT_QUERYFINALACCEPTANCEMSG = "projectManage/queryFinalacceptanceMsg";

    /**
     * 变更管理查询
     */
    public static final String PROJECT_QUERYALTERATIONMSG = "projectManage/queryAlterationMsg";

    /**
     * 项目结算查询
     */
    public static final String PROJECT_QUERYSETTLEMENTMSG = "projectManage/querySettlementMsg";

    /**
     * 供货方信息查询
     */
    public static final String PROJECT_QUERYSUPPLIERMSG = "projectManage/querySupplierMsg";

    /**
     * 设备信息查询
     */
    public static final String PROJECT_QUERYEQUIPMENTMSG = "projectManage/queryEquipmentMsg";

    /**
     * 开工报告查询
     */
    public static final String PROJECT_QUERYKGREPORTMSG = "projectManage/queryKgReportMsg";

    /**
     * 项目信息提交
     */
    public static final String PROJECT_SAVEPROJECTMSG = "projectManage/saveProjectMsg";

    /**
     * 施工日志提交
     */
    public static final String PROJECT_SAVECONSTRUCTIONLOGMSG = "projectManage/saveConstructionLogMsg";

    /**
     * 施工安全日志提交
     */
    public static final String PROJECT_SAVESUMMARYLOGMSG = "projectManage/saveSummaryLogMsg";

    /**
     * 监理日志提交
     */
    public static final String PROJECT_SAVESUPERVISIONLOGMSG = "projectManage/saveSupervisionlogMsg";

    /**
     * 施工人员提交
     */
    public static final String PROJECT_SAVESGPERSONNELMSG = "projectManage/saveSgPersonnelMsg";

    /**
     * 设备信息提交
     */
    public static final String PROJECT_SAVEEQUIPMENTMSG = "projectManage/saveEquipmentMsg";

    /**
     * 变更管理提交
     */
    public static final String PROJECT_SAVEALTERATIONMSG = "projectManage/saveAlterationMsg";

    /**
     * 供货方信息提交
     */
    public static final String PROJECT_SAVESUPPLIERMSG = "projectManage/saveSupplierMsg";

    /**
     * 竣工验收提交
     */
    public static final String PROJECT_SAVEFINALACCEPTANCEMSG = "projectManage/saveFinalacceptanceMsg";

    /**
     * 项目结算提交
     */
    public static final String PROJECT_SAVESETTLEMENT = "projectManage/saveSettlementMsg";

    /**
     * 工序报验查询
     */
    public static final String PROJECT_QUERYCHECKOUTMSG = "projectManage/queryCheckoutMsg";

    /**
     * 施工人员查询
     */
    public static final String PROJECT_QUERYSGPERSONNELMSG = "projectManage/querySgPersonnelMsg";

    /**
     * 查询技术交底
     */
    public static final String PROJECT_QUERYTECHNOLOGYMSG = "projectManage/queryTechnologyMsg";

    /**
     * 查询进度申报
     */
    public static final String PROJECT_QUERYDECLARATIONMSG = "projectManage/queryDeclarationMsg";

    /**
     * 添加我的轨迹
     */
    public static final String PROJECT_SAVE_MYLOCUSMSG = "projectManage/saveMylocusMsg";

    /**
     * 查询我的轨迹
     */
    public static final String PROJECT_QUERY_MYLOCUSMSG = "projectManage/queryMylocusMsg";

    /************************ 项目管理相关 end **************************/

    /************************
     * 工作台
     **************************/

    /**
     * 设备租赁查询
     */
    public static final String FIND_EQUIPMENT = "ylcx/xqfb/find/equipment";

    /**
     * 苗木信息查询
     */
    public static final String FIND_SEEDLING = "ylcx/xqfb/find/seedling";

    /**
     * 招标信息查询
     */
    public static final String RELEASE_QUERYBID = "release/queryBid";

    /**
     * 人才招聘查询
     */
    public static final String FIND_RECRUIT = "ylcx/xqfb/find/recruit";

    /**
     * 招标信息查询
     */
    public static final String FIND_BID = "ylcx/xqfb/find/bid";

    /************************ 工作台 end **************************/

    /************************
     * 合同管理
     **************************/
    /**
     * 商务合同查询
     */
    public static final String COMPACT_QUERYGARDENCOMPACT = "compact/queryGardenCompact";

    /**
     * 劳务合同查询
     */
    public static final String COMPACT_QUERYBUILDCOMPACT = "compact/queryBuildCompact";

    /**
     * 授权合同查询
     */
    public static final String COMPACT_QUERYENGINEERINGCOMPACT = "compact/queryEngineeringCompact";

    /**
     * 商务合同录入
     */
    public static final String COMPACT_SAVEGARDENCOMPACT = "compact/saveGardenCompact";

    /**
     * 劳务合同录入
     */
    public static final String COMPACT_SAVEBUILDCOMPACT = "compact/saveBuildCompact";

    /**
     * 授权合同录入
     */
    public static final String COMPACT_SAVEENGINEERINGCOMPACT = "compact/saveEngineeringCompact";

    /**
     * 商务合同删除
     */
    public static final String DELETECOMPACT_GARDEN = "deleteCompact/garden";

    /**
     * 劳务合同删除
     */
    public static final String DELETECOMPACT_BUILD = "deleteCompact/build";

    /**
     * 授权合同删除
     */
    public static final String DELETECOMPACT_ENGINEERING = "deleteCompact/engineering";

    /************************ 合同管理 end **************************/

    /**
     * 上传错误日志到服务器
     */
    public static final String UPLOAD_ERROR_SERVER = "appError";


}
