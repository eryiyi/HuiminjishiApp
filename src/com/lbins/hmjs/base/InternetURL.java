package com.lbins.hmjs.base;

public class InternetURL {
//    public static final String INTERNAL =  "http://114.215.121.7:8085/";
    public static final String INTERNAL =  "http://www.shandongguiren.com/";
//    public static final String INTERNAL =  "http://157j1274e3.iask.in/";
//    public static final String INTERNAL =  "http://192.168.0.100:8080/";

    public static final String QINIU_URL =  "http://oo4c4r583.bkt.clouddn.com/";
    //更新链接
    public static final String UPDATE_URL =  "http://a.app.qq.com/o/simple.jsp?pkgname=com.lbins.hmjs";
    //mob
    public static final String DEFAULT_GROUPS_ID1 = "18165475115009";
    public static final String DEFAULT_GROUPS_ID2 = "14989470531585";

    public static final String APP_MOB_KEY = "203000eff1a00";
    public static final String APP_MOB_SCRECT = "0cfe5b50e42eea181599d3a41cd8f9f1";

    public static final String WEIXIN_APPID = "wx977ea9023fbf25c9";
    public static final String WEIXIN_SECRET = "438d9dc88d440e51053d9d4cfeac927f";
    public static final String WX_API_KEY="13473ea7a46ba9238cf1ecaa6cad26a2";

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
//    public static final String SEND_ORDER_TOSERVER = INTERNAL + "orderSave.do";
    //微信支付
//    public static final String SEND_ORDER_TOSERVER_WX = INTERNAL + "orderSaveWx.do";

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



    //平台EMP_ID
    public static final String DEFAULT_EMP_ID = "b530cca19dba4509867477a3d9fc85d1";

    //登录
//    public static final String LOGIN__URL = INTERNAL + "memberLogin.do";
    //注册第一步
//    public static final String REG_ONE__URL = INTERNAL + "memberRegister.do";
    //根据手机号查询用户是否存在
//    public static final String GET_EMP_MOBILE = INTERNAL + "getMemberByMobile.do";

    //更新密码
    public static final String UPDATE_PWR__URL = INTERNAL + "resetPass.do";
    //通过手机号找回密码
    public static final String FIND_PWR__URL = INTERNAL + "findPwrByMobile.do";
    //更新头像
    public static final String UPDATE_INFO_COVER_URL = INTERNAL + "modifyMember.do";
    //更改性别
    public static final String UPDATE_INFO_SEX_URL = INTERNAL + "modifyMemberSex.do";
    //更新生日
    public static final String UPDATE_INFO_BIRTH_URL = INTERNAL + "modifyMemberBirth.do";
    // 跟新手机号
    public static final String UPDATE_INFO_MOBILE_URL = INTERNAL + "resetMobile.do";
    //更新支付密码
    public static final String UPDATE_INFO_PAY_PASS_URL = INTERNAL + "modifyMemberPayPass.do";

    //我的收货地址列表
    public static final String MINE_ADDRSS =   INTERNAL +"listShoppingAddress.do";
    //添加收货地址
    public static final String ADD_MINE_ADDRSS =  INTERNAL + "saveShoppingAddress.do";
    //更新收货地址
    public static final String UPDATE_MINE_ADDRSS =   INTERNAL +"updateShoppingAddress.do";
    //删除收货地址
    public static final String DELETE_MINE_ADDRSS =   INTERNAL +"deleteShoppingAddress.do";
    //获得默认收货地址
    public static final String GET_MOREN_ADDRESS =   INTERNAL +"getSingleAddressByEmpId.do";
    //添加收货地址--选择省份--城市--地区
    public static final String SELECT_PROVINCE_ADDRESS =   INTERNAL +"appProvinces.do";
    public static final String SELECT_CITY_ADDRESS =   INTERNAL +"appCitys.do";
    public static final String SELECT_AREA_ADDRESS =   INTERNAL +"appGetArea.do";
    //上传经纬度
    public static final String SEND_LOCATION_BYID_URL = INTERNAL + "sendLocation.do";
    //申请入驻
    public static final String EMP_APPLY_DIANPU_URL = INTERNAL + "saveManagerInfo.do";

    //获得商品分类
    public static final String GET_GOODS_TYPE_URL = INTERNAL + "appGetGoodsType.do";

    //获得附近首页商店列表
    public static final String GET_DIANPU_LISTS = INTERNAL + "appGetNearbyDianpu.do";
    //获得首页推荐商品
    public static final String GET_INDEX_TUIJIAN_LISTS = INTERNAL + "getIndexTuijian.do";
    //查询店铺详情
    public static final String GET_DIPU_DETAIL_LISTS = INTERNAL + "appGetDianpuDetailByEmpId.do";
    //查询店铺广告轮播图
    public static final String GET_DIPU_ADS_LISTS = INTERNAL + "appGetAdEmp.do";
    //查询商品详情
    public static final String GET_GODS_DETAIL_LISTS = INTERNAL + "paopaogoods/findById.do";
    //查询商品评论
    public static final String GET_GOODS_COMMENT_LISTS = INTERNAL + "listGoodsComment.do";
    //广告轮播
    public static final String GET_AD_LIST_TYPE_LISTS = INTERNAL + "appGetAdByType.do";
    //获取商品列表
    public static final String GET_GOODS_URL = INTERNAL + "paopaogoods/listGoods.do";


    //传订单给服务端--生成订单
    public static final String SEND_ORDER_TOSERVER = INTERNAL + "orderSave.do";
    //更新订单状态
    public static final String UPDATE_ORDER_TOSERVER =  INTERNAL +"orderUpdate.do";

    //查询订单列表
    public static final String MINE_ORDERS_URL = INTERNAL + "listOrders.do";
    //更新订单
    public static final String UPDATE_ORDER = INTERNAL + "updateOrder.do";
    //去付款--单个订单付款-支付宝
    public static final String SAVE_ORDER_SIGNLE =INTERNAL +  "orderSaveSingle.do";

    //更新订单状态
    public static final String UPDATE_ORDER_TOSERVER_SINGLE = INTERNAL + "orderUpdateSingle.do";
    //根据地址id，查询收货地址、
    public static final String GET_ADDRESS_BYID =  INTERNAL + "getSingleAddressByAddressId.do";


    //收藏商品接口
    public static final String SAVE_FAVOUR = INTERNAL +  "saveGoodsFavour.do";
    //收藏商品列表
    public static final String MINE_FAVOUR = INTERNAL +  "listFavour.do";
    //删除收藏的商品
    public static final String DELETE_FAVOUR = INTERNAL +  "deleteFavour.do";

    //删除店铺收藏
    public static final String DELETE_DIANPU_FAVOUR_URL = INTERNAL +   "deleteDianpuFavour.do";
    //收藏店铺
    public static final String SAVE_FAVOUR_URL = INTERNAL +   "saveDianpuFavour.do";
    //获得我的店铺收藏列表
    public static final String APP_GET_FAVOUR_DIANPU_URL = INTERNAL +   "appGetDianpuFavour.do";

    //查询用户银行卡
    public static final String APP_GET_BANK_CARDS_URL = INTERNAL +   "appGetBankCards.do";
    //保存银行卡信息
    public static final String APP_SAVE_BANK_CARDS_URL = INTERNAL +   "appSaveBankCards.do";
    //删除银行卡 根据银行卡id
    public static final String APP_DELETE_BANK_CARDS_URL = INTERNAL +   "deleteBankCard.do";
    //查询会员钱包信息
    public static final String APP_GET_PACKAGE_URL = INTERNAL +   "appGetPackage.do";
    //版本检查
    public static final String CHECK_VERSION_CODE_URL = INTERNAL +   "getVersionCode.do";


    //百度推送 绑定 get方法
//    public static final String UPDATE_PUSH_ID = INTERNAL + "updatePushId.do";

    //查询我的浏览记录
    public static final String GET_BROWSING_ID = INTERNAL + "appGetBrowsing.do";
    //分享商品链接
    public static final String SHARE_GOODS_DETAIL_URL = INTERNAL + "paopaogoods/shareGoodsUrl.do";
    //微信支付
    public static final String SEND_ORDER_TOSERVER_WX = INTERNAL + "orderSaveWx.do";

    //添加商品评论
    public static final String PUBLISH_GOODS_COMMNENT_URL = INTERNAL+ "saveGoodsComment.do";
    //获得客户电话列表
    public static final String GET_KEFU_TEL_URL = INTERNAL+ "getKefuTel.do";

    //推广注册
    public static final String APP_SHARE_REG_URL = INTERNAL+ "appShareReg.do";

    //去付款--单个订单付款-微信
    public static final String SAVE_ORDER_SIGNLE_WX =INTERNAL +  "orderSaveSingleWx.do";

    //确认收货 生成二维码用，卖家扫一扫确认发货 这个是面对面的，所以直接订单完成
    public static final String APP_SURE_FAHUO_URL = INTERNAL+ "scanOrder.do";

    //查询我的點評
    public static final String GET_MINE_GOODS_COMMENT_LISTS = INTERNAL + "listGoodsComment.do";

    //更新订单--退货
    public static final String UPDATE_ORDER_RETURN = INTERNAL + "updateOrder.do";

    //获得会员的消费记录 ，钱包-》我的金币，点击查看消费详情
    public static final String GET_CONSUMPTION_RETURN = INTERNAL + "appGetConsumption.do";
    //我的积分记录
    public static final String GET_COUNT_RECORD_RETURN = INTERNAL + "appGetCountRecord.do";
    //查询我的积分
    public static final String GET_COUNT_RETURN = INTERNAL + "appGetCount.do";

    //提交提现申请
    public static final String SAVE_BANK_APPLY_RETURN = INTERNAL + "appSaveBankApply.do";
    //猜你喜欢的
    public static final String GET_LIKES_URN = INTERNAL + "appGetLikes.do";
    //获得定向卡会员详情
    public static final String GET_CARD_EMP_BY_ID_URN = INTERNAL + "appGetCardEmp.do";
    //商家有偿消费二维码url
    public static final String GET_GET_GOODS_URN = INTERNAL + "appPayYouchang.do";
    //商家无偿消费二维码url
    public static final String GET_GET_DXK_GOODS_URN = INTERNAL + "appPayWuchang.do";

    public static final String SAVE_DXK_ORDER_URN = INTERNAL + "appSaveDxkOrder.do";

    //获得商品详情页好评度和消费评价总数
    public static final String GET_COMMENT_ALL_URN = INTERNAL + "appGetCountComment.do";
    //获得店铺详情页好评度和消费评价总数
    public static final String GET_COMMENT_ALL_DIANPU_URN = INTERNAL + "appGetCountCommentDianpu.do";


    //零钱支付方式(购物车生成订单 付款  直接购买的时候)
    public static final String SEND_ORDER_TOSERVER_LQ = INTERNAL + "orderSaveLq.do";
    //零钱支付方式 -- 单个付款--（我的订单，没支付的订单，去付款的时候）
    public static final String SAVE_ORDER_SIGNLE_LQ =INTERNAL +  "orderSaveSingleLq.do";
    //订单评价之后 更新订单状态
    public static final String UPDATE_ORDER_COMMENT =INTERNAL +  "orderUpdateComment.do";
    //查询店铺的评论列表
//    public static final String GET_DIANPU_COMMENT_LISTS = INTERNAL + "listGoodsComment.do";
    //定向卡要充值的金额
    public static final String GET_DXK_CHONGZHI_MONEY = INTERNAL + "appToChongzhiDxk.do";
    //定向卡充值之后 更新订单和充值卡记录
    public static final String UPDATE_ORDER_DXK_TOSERVER =  INTERNAL +"appChongzhiDxk.do";

    //公告列表
    public static final String APP_GET_NOTICES_LIST =  INTERNAL +"appListNotice.do";
    //公告详情
    public static final String APP_NOTICE_DETAIL_LIST =  INTERNAL +"viewNotice.do";
    //获得店铺详情页底部的推荐商家
    public static final String APP_GET_TUIJIAN_DIANPU_LIST =  INTERNAL +"getPaihangDianpu.do";

    //充值零钱
    public static final String appLqPayWx = INTERNAL + "appLqPayWx.do";
    public static final String appLqPayZfb = INTERNAL + "appLqPayZfb.do";
    //app更新零钱充值
    public static final String appUpdateLqCz = INTERNAL + "appUpdateLqCz.do";
    //商品详情内容
    public static final String appGoodsContent = INTERNAL + "paopaogoods/toGoodsContent.do";


    //获得商品分类-- 小分类
    //获得热门分类  is_hot = 1
    public static final String GET_GOODS_SMALL_TYPE_URL = INTERNAL + "appGetGoodsTypeSmall.do";


    //入驻选择商家要入住的分类
    public static final String appGetLxClass = INTERNAL + "appGetLxClass.do";

    //我的粉丝列表
    public static final String APP_GET_MIEN_FENSI_URL = INTERNAL +   "listMineEmps.do";

    //粉丝数量统计
    public static final String MINE_FENSI_COUNT = INTERNAL +   "MineFensiCount.do";

    //通过手机号重设支付密码
    public static final String FIND_PWR_PAY__URL = INTERNAL + "findPwrPayByMobile.do";

    //获得店铺评论列表
    public static final String appGetDianpuComment = INTERNAL + "appGetDianpuComment.do";
    //保存店铺评论
    public static final String saveDianpuComment = INTERNAL + "saveDianpuComment.do";
    //删除店铺评论
    public static final String deleteDianpuComment = INTERNAL + "deleteDianpuComment.do";

    // 获得欢迎页广告列表
    public static final String appGetLoadPics = INTERNAL + "appGetLoadPics.do";
    //获得定向卡广告列表
    public static final String appGetDxkAds = INTERNAL + "appGetDxkAds.do";

    //会员微信支付的认证
    public static final String orderSaveWxEmpRz = INTERNAL + "orderSaveWxEmpRz.do";
}
