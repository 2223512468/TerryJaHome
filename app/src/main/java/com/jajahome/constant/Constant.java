package com.jajahome.constant;

/**
 * 类描述：存放常量  打算的
 * 创建人：lhz
 * 创建时间：2016/6/29 9:02
 * 修改时间：2016/6/29 9:02
 * 修改备注：
 */
public class Constant {
    /**
     * 接口基地址 生产
     */

   // public static final String BASE_URL = "https://app.jajahome.com/";

    /**
     * 接口基地址 测试
     */
    public static final String BASE_URL = "http://www.jajahome.com/";

    public static final String CALL = "furniture/call.php";


    public static class Config {
        public static final boolean DEVELOPER_MODE = false;
    }

    /**
     * 单品详情（H5地址链接）167480
     */
    public static final String ITEM_DETAIL = BASE_URL + "furniture/item_ex_info.php?item_id=";

    public static final String ITEM_DETAIL_SEC = BASE_URL + "furniture/item_detail_info.php?item_id=";
    /**
     * 购买须知
     */

    public static final String ITEM_NOTICE = BASE_URL + "furniture/notice.html";

    /**
     * ----定位
     */
    public static final String lOCATION = BASE_URL + "furniture/sellers_location.php?id=";
    /**
     * ----关于我们
     */
    public static final String ABOUT_US = BASE_URL + "furniture/address.php?id=1";

    /**
     * －－－许可认证
     */
    public static final String PERMISSION = BASE_URL + "furniture/License.html";

    /**
     * －－－版权信息
     */
    public static final String COPYRIGHT = BASE_URL + "furniture/Copyright.html";

    /**
     * ----设计师协议
     */
    public static final String DESIGNER_REGISTER = BASE_URL + "furniture/designer_register.html";

    /**
     * 定位（H5地址链接）
     */
    public static final String SELLERS_LOC = BASE_URL + "sellers_location.php?id=";
    /**
     * 秀家详情（H5地址链接）
     */
    public static final String SHOW_HOME = BASE_URL + "furniture/show-home.php?id=";

    /**
     * 购物车      需添加user_id=%@&user_token=%@",
     */
    public static final String SHOPPING_CART = BASE_URL + "furniture/shopping-cart.php?";

    /**
     * 需添加user_id=%@&user_token=%@",
     */
    public static final String ITEM_PRICE = BASE_URL + "furniture/item_price.php?str=";
    /**
     * 套装或DIY 加入购物车 购买
     */
    public static final String TZ_PRICE = BASE_URL + "furniture/tz_price.php?str=";

    public static final String DIY_PRICE = BASE_URL + "furniture/diy_price.php?str=";

    /*VR*/
    public static final String vr_get_and_create_vrXML = "vr_get_and_create_vrXML";

    public static final String HOME = "home"; //首页信息
    public static final String RECOMMEND = "recommend"; // 首页
    public static final String SMS_REQUEST = "sms_request";//验证码
    public static final String CITYLIST = "citylist";//城市信息
    public static final String BUILDING_LIST = "buliding_list";//楼盘列表
    public static final String HOUSE_LIST = "house_list";//户型列表
    public static final String HOUSE_SETLIST = "house_setlist";//套装列表
    public static final String AREALIST = "arealist";//地区城市列表
    public static final String AREALIST_V2 = "arealist_v2";//地区城市列表
    public static final String USER_RECOVER = "user_recover";//找回密码
    public static final String ADD_FAVORITE = "add_favorite";//收藏
    public static final String FAVORITE_SET = "favorite_set";//套装的收藏列表
    public static final String FAVORITE_ITEM = "favorite_item";//单品的收藏列表
    public static final String FAVORITE_SHOW = "favorite_show";//单品的收藏列表
    public static final String DIY_ITEMLIST = "diy_itemlist_v2";//diy收藏
    public static final String FEEDBACK = "feedback";//反馈
    public static final String USER_INFO = "user_info";//用户信息更改
    public static final String DEL_FAVORITE = "del_favorite";//删除收藏
    public static final String INVITE_LIST = "invite_list";//邀请人列表
    public static final String USER_MESSAGE = "user_message";//用户消息
    public static final String USER_MESSAGE_OPERATION = "user_message_operation";
    public static final String SAVE_ADDRESS = "save_address";//地址添加
    public static final String ADDRESSLIST = "addresslist";//地址列表
    public static final String DEL_ADDRESS = "del_address";//删除地址
    public static final String SETLIST = "setlist_v2"; //套餐列表
    public static final String CONFIG_V2 = "config_v2"; // 获取配置信息
    public static final String SET_ITEM = "set_item"; // 获套装位置相关信息
    public static final String SET = "set"; // 获取配置信息
    public static final String USER_AVATAR = "user_avatar";//上传头像
    public static final String USER_CHANGEPASSWORD = "user_changepassword";//修改密码
    public static final String ITEM_LIST = "itemlist_v2"; //单品列表
    public static final String USER_SIGNUP = "user_signup";//用户注册
    public static final String USER_SIGNIN = "user_signin";//用户登陆
    public static final String ITEM = "item";//单品详情
    public static final String USERINFO = "userInfo";//用户信息
    public static final String DESIGNER_REG = "designer_reg";//用户信息
    public static final String SHOWLIST = "showlist";//秀家列表
    public static final String SHOWLIST2 = "showlist_v2";
    public static final String SHOW = "show";//秀家信息
    public static final String DIYLIST = "diylist";//diy套装列表
    public static final String DIY = "diy";//diy套装
    public static final String SAVE_DIY = "mydiy_save";//保存diy套装
    public static final String MYDIY_DEL = "mydiy_del";//删除diy设计
    public static final String SHOPCART_NUMBER = "shopCart_number";//购物车商品总数
    public static final String ORDER = "order"; //订单详情
    public static final String ORDER_DELETE = "order_delete";
    public static final String ORDER_LIST = "order_list"; //订单列表
    public static final String ORDER_CLOSE = "order_close"; //关闭订单
    public static final String ORDER_RECIVE = "order_recive"; //确认收货（交易完成）
    public static final String PAY_ALIPAY = "pay_alipay_v2"; //支付宝支付
    public static final String PAY_WEIXIN = "pay_weixin_v2"; //微信支付
    public static final String SET_ITEM_lIST = "set_item_list";
    public static final int PAGE_LIMIT = 12;
    public static final String TOTAL_PRICE_SUM = "TOTAL_PRICE_SUM";
    public static final String TOTAL_SUM = "TOTAL_SUM";
    public static final String LOCATION = "location";
    public static final String LOCATION_NAME = "LOCATION_NAME ";
    public static final String LOCATION_ACTION = "locationAction";
    public static final String SERVERVERSION = "SERVERVERSION";    //识别当前APP版本
    public static final String SERVERVERSIONDATA = "SERVERVERSIONDATA";     //识别当前APP版本
    public static final String CITYNAME = "CITYNAME";
    public static final String ISADD = "ISADD";
    public static final String ISCOLLECTED = "ISCOLLECTED";
    public static final String ISUNSHOW = "ISUNSHOW";
    public static final int ISADDED = 1;
    public static final int ISDELETE = 0;
    public static final int SELECTEDDIYTAB = 1;
    public static final String SENDROOM = "SENDROOM";
    public static final String ROOMNAME = "ROOMNAME";
    public static final String DIDSELECTED = "DIDSELECTED";
    public static final String tzid = "tzid";
    public static final String LOCATION_ACTION_CITY = "LOCATION_ACTION_CITY";
    public static final String LOCATIONAME = "LOCATIONAME";
    public static final int INTTABLE = 0x01;
    public static final String HOMETABLE = "HOMETABLE";
    public static final String DIYPOS = "DIYPOS";
    public static final String DIYPOSITION = "0x11";
    public static final int homeTag = 0000;
    public static final int setTag = 0001;
    public static final int itemTag = 0002;
    public static final int showTag = 0003;
    public static final int diyTag = 0004;

    public static final String order_Waybill_query = "order_Waybill_query";
    public static final String order_logistics_query = "order_logistics_query";
    public static final String DIY_ITEM_LIST = "diy_item_list";
    public static final String HTML5URL = "http://app.jajahome.com/furniture/share-show.php?id=";
    public static final String USER_THIRD_LOGIN = "user_third_login";
    public static final String QUERYOPENID = "queryOpenID";
    public static final String BINDINGMOBILEPHONE = "binding_MobilePhone";
    public static final String UNBINDINGMOBILEPHONE = "unbinding_MobilePhone";
    public static final String USER_LOG = "user_log";
    public static final String USER_LOG_DELETE = "user_log_delete";//删除足迹
    public static final String REBATE_DETAILS = "rebate_details";
    public static final String INTEGRAL_DETAILS = "integral_details";
    public static final String TZ = "http://www.jajahome.com/gjj/frontend/web/tz/tz?sid=";
    public static final String ITEMURL = "http://www.jajahome.com/gjj/frontend/web/item/item-info?item_id=";
    //评论相关
    public static final String COMMENT_LIST = "comment_list";
    public static final String COMMENT_ADD = "comment_add";
    public static final String COMMENT_DEL = "comment_del";
    public static final String COMMENT_LIKE = "comment_like";
    public static final String SIGNED = "SIGNED";
    public static final String SIGNEDING = "SIGNEDING";
    public static final String SIGNSUCCESS = "SUCCESS";
    //搜索相关
    public static final String SEARCH_TIPS = "search_tips";//搜索提示
    public static final String SEARCH_HOTLIST = "search_hotlist";// 搜索热榜
    public static final String SEARCH = "search";// 搜索
    public static final int LOGIN = 0X1100;
    public static final String COUPON_LIST = "coupon_list";
    public static final String BRAND = "brand";
    public static final String RECOMMEND_SET = "recommend_set";

    //人民币
    public static final String YUAN = "¥";

    //FULL_SET
    public static final String FULL_SET = "完整家居"; //完整家居筛选 保存和网络端返回 名称一样


    public static final int APP_PASSWORD_LIMIT_LENGTH = 6;//密码长度至少为六位数
    public static final String AES_KEY = "JAJAHOMEAESKEY";//
    public static final int MODE_SINGLE_PICK = 1;
    public static float SET_SCALE = 1.33333333f;
    public static float DEFAULT_WEIGHT = 960f;
    public static float DEFAULT_HEIGHT = 720f;
    public static final int ACCOUNT_TYPE = 368;
    public static final int mHLimit = 50;
    public static final int mWLimit = 1080;
    public static final int mItemH = 240;
    //sdk appid 由腾讯分配
    public static final int SDK_APPID = 1400021397;
    public static final String LAUNCHIMAGE = "launch_Image";
    public static final String DESIGNERMPAY = "designer_wxpay";
    public static final String DESIGNERALIPAY = "designer_alipay";
    public static final String GETDESIGNERTIME = "get_designer_time";
    public static final String GETCALENDARTIME = "get_calendar_time";
    public static final String GETSIGNINFO = "get_sign_info";
    public static final String SIGNTIME = "sign_time";
    public static final String REWARD_CASH = "reward_cash";
    public static final String PICKCAH_ACCOUNT = "pickCash_account";
    public static final String PAY_TRANSFORM_ACCOUNT = "pay_transform_account";
    public static final String ITEM_EXCHANGE_LIST = "item_exchange_List";
    public static final String INTEGRAL_EXCHANGE = "integral_exchange";
    public static final String GETCOUPON = "get_coupon";
    public static final String GETFAVOURTBUILDING = "get_favourt_building";
    public static final String SORTCITYLIST = "sort_city_list";
    public static final String SORTBUILDINGLIST = "sort_building_list";
    public static final String SEARCHBUILDING = "search_building";
    public static final int ERROR = 0x99;
    public static final String transfer_conpon = "transfer_conpon";

    /**
     * 微信分享app id
     */
    public static String WEICHAT_APP_ID = "wx865a0f13acf4bc81";
    public static String SECRET = "5519a44725587f9667bc4600cf5fe2c5";
    /**
     * 微博分享APP_KEY
     * app Key：3688280714
     * App Secret：dfaf9b1204a8c49342a74c4aa1ad109e
     */
    public static String WEIBO_APP_KEY = "3688280714";
    public static String WEIBO_APP_SERCET = "dfaf9b1204a8c49342a74c4aa1ad109e";
    public static final String SINA_REDIRECT_URL = "http://www.jajahome.com";
    public static final String SINA_SCOPE = "all";


    public static String APP_ID = "1105485989";


    /**
     * SIGNTIME广链接
     */
    public static final String APP_DOWNLOAD_URL = "http://a.app.qq.com/o/simple.jsp?pkgname=com.jajahome";

    /*版本升级链接正式环境*/
    public static final String UPGRADE = "https://app.jajahome.com/furniture/Android/download/Android_Version_upgrade.xml";

    public static String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";


}
