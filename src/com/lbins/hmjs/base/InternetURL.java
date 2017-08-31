package com.lbins.hmjs.base;

public class InternetURL {
//    public static final String INTERNAL =  "http://114.215.121.7:8085/";
//    public static final String INTERNAL =  "http://www.shandongguiren.com/";
//    public static final String INTERNAL =  "http://157j1274e3.iask.in/";
    public static final String INTERNAL =  "http://192.168.0.102:8080/";

    public static final String QINIU_URL =  "http://oo4c4r583.bkt.clouddn.com/";
    //更新链接
    public static final String UPDATE_URL =  "http://a.app.qq.com/o/simple.jsp?pkgname=com.lbins.hmjs";
    //mob
    public static final String DEFAULT_GROUPS_ID1 = "18165475115009";
    public static final String DEFAULT_GROUPS_ID2 = "14989470531585";

    public static final String APP_MOB_KEY = "203000eff1a00";
    public static final String APP_MOB_SCRECT = "0cfe5b50e42eea181599d3a41cd8f9f1";

    public static final String WEIXIN_APPID = "wx2993024b83997eba";
    public static final String WEIXIN_SECRET = "ed8a4eeb61fc631ea0c786e175b5e1fe";
    public static final String WX_API_KEY="611fcae2cb0a43381be9ee527de1c406";

    public static final String QINIU_SPACE = "meetlove-pic";
    //七牛文件上传接口获得token
    public static final String UPLOAD_TOKEN = INTERNAL + "token.json";

    //1登陆
    public static final String appLogin = INTERNAL + "appLogin.do";
    //注册
    public static final String appReg = INTERNAL + "appReg.do";
    //更新头像
    public static final String appUpdateCover = INTERNAL + "appUpdateCover.do";
    //获得省份
    public static final String appProvinces = INTERNAL + "appProvinces.do";
    //获得城市
    public static final String appCitys = INTERNAL + "appCitys.do";
    //获得兴趣爱好列表
    public static final String appLikes = INTERNAL + "appLikes.do";
    //完善资料
    public static final String appUpdateProfile = INTERNAL + "appUpdateProfile.do";
    //根据兴趣爱好IDs查询爱好兴趣集合
    public static final String appLikesBylikeIds = INTERNAL + "appLikesBylikeIds.do";
    //获取公司介绍
    public static final String appAboutUs = INTERNAL + "appAboutUs.do";
    //版本检查
    public static final String getVersionCode = INTERNAL +   "getVersionCode.do";
    //反馈保存
    public static final String appSaveSuggest = INTERNAL +   "appSaveSuggest.do";
    //投诉保存
    public static final String appSaveReport = INTERNAL +   "appSaveReport.do";
    //修改手机号码
    public static final String appUpdateMoible = INTERNAL +   "appUpdateMoible.do";
    //修改密码根据用户ID
    public static final String appUpdatePwrById = INTERNAL +   "appUpdatePwrById.do";
    //修改密码根据用户手机号
    public static final String appUpdatePwrByMobile = INTERNAL +   "appUpdatePwrByMobile.do";
    //身份认证---身份证上传
    public static final String appUpdateCard = INTERNAL +   "appUpdateCard.do";
    //相册上传
    public static final String appSaveOrUpdatePhotos = INTERNAL +   "appSaveOrUpdatePhotos.do";
    //根据用户ID查询用户相册
    public static final String appPhotos = INTERNAL +   "appPhotos.do";
    //删除图片
    public static final String appDeletePhotos = INTERNAL +   "appDeletePhotos.do";
    //根据empid查询会员信息
    public static final String appEmpByEmpId = INTERNAL +   "appEmpByEmpId.do";
    //推荐群组
    public static final String appTuijianGroups = INTERNAL +   "appTuijianGroups.do";
    //根据群组ID查询群详情
    public static final String appGroupsById = INTERNAL +   "appGroupsById.do";
    //申请加好友
    public static final String appSaveFriends = INTERNAL +   "appSaveFriends.do";
    //好友列表
    public static final String appFriends = INTERNAL +   "appFriends.do";
    //接受好友申请
    public static final String appAcceptFriends = INTERNAL +   "appAcceptFriends.do";
    //提交交往申请
//    public static final String appSaveJiaowang = INTERNAL +   "appSaveJiaowang.do";
    //查询交往对象
//    public static final String appJiaowangs = INTERNAL +   "appJiaowangs.do";
    //处理交往请求
//    public static final String appAcceptJiaowang = INTERNAL +   "appAcceptJiaowang.do";
    //取消邀请
//    public static final String appDeleteJiaowang = INTERNAL +   "appDeleteJiaowang.do";
    //活动公告
    public static final String appNotices = INTERNAL +   "appNotices.do";
    //公告详情
    public static final String appNoticeById = INTERNAL +   "appNoticeById.do";
    //资讯列表
    public static final String appNews = INTERNAL +   "appNews.do";
    //资讯详情
    public static final String appNewsById = INTERNAL +   "appNewsById.do";
    //系统消息
    public static final String appMessages = INTERNAL +   "appMessages.do";
    //百度推送
    public static final String UPDATE_PUSH_ID = INTERNAL + "updatePushId.do";
    //系统通知列表
    public static final String appMsgAllList = INTERNAL + "appMsgAllList.do";
    //获得用户的群
    public static final String appEmpGroupsByEmpId = INTERNAL + "appEmpGroupsByEmpId.do";
    //加群
    public static final String appEmpGroupsSave = INTERNAL + "appEmpGroupsSave.do";
    //检查用户是否已经加入群聊
    public static final String appEmpIsExist = INTERNAL + "appEmpIsExist.do";
    //查询公开库
    public static final String appPublicGroups = INTERNAL + "appPublicGroups.do";
    //按条件查询会员
    public static final String appSearchPeoplesByKeyWords = INTERNAL + "appSearchPeoplesByKeyWords.do";
    //首页推荐会员或技师
    public static final String appTuijianEmpsOrYy = INTERNAL + "appTuijianEmpsOrYy.do";
    //按条件查询群
    public static final String appSearchGroupsByKeywords = INTERNAL + "appSearchGroupsByKeywords.do";
    //客服电话
    public static final String appTel = INTERNAL + "appTel.do";
    //申请退还保证金
    public static final String appSaveApplyBack = INTERNAL + "appSaveApplyBack.do";
    //删除好友关系
    public static final String appDeleteFriends = INTERNAL + "appDeleteFriends.do";
    //解除交往对象
    public static final String appDeleteJiaowangDx = INTERNAL + "appDeleteJiaowangDx.do";
    //退出群组
    public static final String appDeleteGroupsById = INTERNAL + "appDeleteGroupsById.do";
    //获得群组成员
    public static final String appEmpByGroupId = INTERNAL + "appEmpByGroupId.do";
    //查询群组管理员
    public static final String appGroupsManager = INTERNAL + "appGroupsManager.do";
    //删除环信群组的会员
    public static final String appDeleteMembers = INTERNAL + "appDeleteMembers.do";
    //退出
    public static final String appLogout = INTERNAL + "appLogout.do";

    //传订单给服务端--生成订单
    public static final String SEND_ORDER_TOSERVER = INTERNAL + "orderSave.do";
    //微信支付
    public static final String SEND_ORDER_TOSERVER_WX = INTERNAL + "orderSaveWx.do";

    //更新定位信息
    public static final String appUpdateLocation = INTERNAL + "appUpdateLocation.do";

    //查询附近的会员
    public static final String appSearchNearby = INTERNAL + "appSearchNearby.do";

    //朋友圈动态操作 删除
    public static final String appRecordDelete = INTERNAL + "appRecordDelete.do";
    //保存
    public static final String appRecordSave = INTERNAL + "appRecordSave.do";
    //查询一个
    public static final String appRecordById = INTERNAL + "appRecordById.do";
    //列表
    public static final String appRecords = INTERNAL + "appRecords.do";

    //评论
    public static final String appRecordCommentSave = INTERNAL + "appRecordCommentSave.do";
    //点赞
    public static final String appRecordFavourSave = INTERNAL + "appRecordFavourSave.do";
    //评论列表
    public static final String appRecordsComment = INTERNAL + "appRecordsComment.do";
    //点赞列表
    public static final String appRecordsFavour = INTERNAL + "appRecordsFavour.do";
}
