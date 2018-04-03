package com.jajahome.network;


import com.jajahome.constant.Constant;
import com.jajahome.model.ActionModel;
import com.jajahome.model.AddFavoriteModel;
import com.jajahome.model.AddressModel;
import com.jajahome.model.AlipayModel;
import com.jajahome.model.AreaModel;
import com.jajahome.model.AuthCodeModle;
import com.jajahome.model.AvatarModel;
import com.jajahome.model.BaseModel;
import com.jajahome.model.BuildListModel;
import com.jajahome.model.BuildingModel;
import com.jajahome.model.CashModel;
import com.jajahome.model.CityListModel;
import com.jajahome.model.CommentModel;
import com.jajahome.model.CommitModel;
import com.jajahome.model.ConfigModel;
import com.jajahome.model.DataModel;
import com.jajahome.model.DiyListModel;
import com.jajahome.model.DiyModel;
import com.jajahome.model.ExModel;
import com.jajahome.model.ExitMoneyModel;
import com.jajahome.model.FeedbackModel;
import com.jajahome.model.GetCouModel;
import com.jajahome.model.HomeBannerModel;
import com.jajahome.model.HouseListModel;
import com.jajahome.model.HouseModel;
import com.jajahome.model.IntegralModel;
import com.jajahome.model.InviteModel;
import com.jajahome.model.IsSignModel;
import com.jajahome.model.ItemListModel;
import com.jajahome.model.ItemModel;
import com.jajahome.model.ItemShowModel;
import com.jajahome.model.JWeichatPayModel;
import com.jajahome.model.LaunchModel;
import com.jajahome.model.OrderDetailModel;
import com.jajahome.model.OrderListModel;
import com.jajahome.model.PickAccountModel;
import com.jajahome.model.RebateInfoModel;
import com.jajahome.model.RecommendModel;
import com.jajahome.model.SaveAddressModel;
import com.jajahome.model.SearchBuildListModel;
import com.jajahome.model.SearchResultModel;
import com.jajahome.model.SearchTipsModel;
import com.jajahome.model.SellItemListModel;
import com.jajahome.model.SetItemModel;
import com.jajahome.model.SetListModel;
import com.jajahome.model.SetModel;
import com.jajahome.model.SetVRModel;
import com.jajahome.model.ShoppingCartNumsModel;
import com.jajahome.model.ShowDetailModel;
import com.jajahome.model.ShowListModel;
import com.jajahome.model.ShowModel;
import com.jajahome.model.SignModel;
import com.jajahome.model.SimpleModel;
import com.jajahome.model.SortCityModel;
import com.jajahome.model.ThirdLoginModel;
import com.jajahome.model.TimeModel;
import com.jajahome.model.TransModel;
import com.jajahome.model.UserLogModel;
import com.jajahome.model.UserMessageModel;
import com.jajahome.model.ValueListModel;
import com.jajahome.model.WuLiuModel;
import com.jajahome.model.YunOrderModel;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * 类描述： 网络接口api
 * 创建人：admin
 * 创建时间：2016/5/27 10:31
 * 修改人：admin
 * 修改时间：2016/5/27 10:31
 * 修改备注：
 */
public interface Api {


    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<SetModel> register(@Body RequestBody requestBody);

    /**
     * 请求接口
     *
     * @param requestBody ：请求体
     * @return ：
     */
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<HomeBannerModel> home(@Body RequestBody requestBody);

    /**
     * 获取推荐列表
     *
     * @param requestBody ：请求体
     * @return ：
     */
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<RecommendModel> recommend(@Body RequestBody requestBody);

    /**
     * 获取配置信息
     *
     * @param ：请求体
     * @return ：
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<ConfigModel> config(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取配置信息
     *
     * @param ：请求体
     * @return ：
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<ConfigModel> config_v2(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取验证码
     *
     * @param requestBody
     * @return
     */
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<AuthCodeModle> getAuthCode(@Body RequestBody requestBody);

    /**
     * 注册用户
     *
     * @param requestBody
     * @return
     */
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<SimpleModel> getRegister(@Body RequestBody requestBody);

    /**
     * 用户登陆
     *
     * @param requestBody
     * @return
     */
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<SimpleModel> sendLogin(@Body RequestBody requestBody);

    /**
     * 获取单品列表
     *
     * @param requestBody
     * @return
     */
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<ItemListModel> itemList(@Body RequestBody requestBody);

    /**
     * 获取套餐列表
     *
     * @param requestBody
     * @return
     */
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<SetListModel> setList(@Body RequestBody requestBody);

    /**
     * 获取DIY列表
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<DiyListModel> diyList(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取DIY列表
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<BuildingModel> buildingList(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取DIY列表
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<HouseListModel> houseList(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取diy详情
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<DiyModel> diy(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 保存DIY
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<SimpleModel> saveDiy(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取秀家列表
     *
     * @param requestBody
     * @return
     */
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<ShowListModel> showlist(@Body RequestBody requestBody);

    /**
     * 获取秀家列表
     *
     * @param
     * @return
     */
    @Multipart
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<ShowDetailModel> show(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 修改密码
     *
     * @param
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<SimpleModel> changePwd(@Part("json") RequestBody json, @Part("session") RequestBody session);


    /**
     * 通过单品id 获取单品信息
     *
     * @return
     */

    @Multipart
    @POST(Constant.CALL)
    Observable<ItemModel> item(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取套装详情
     *
     * @param
     * @return
     */
    @Multipart
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<SetModel> set(@Part("json") RequestBody json, @Part("session") RequestBody session);


    /**
     * 获取VR
     */
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<SetVRModel> vr_get_and_create_vrXML(@Body RequestBody requestBody);

    /**
     * 获取运单号信息
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<YunOrderModel> order_Waybill_query(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 物流信息查询
     *
     * @param
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<WuLiuModel> order_logistics_query(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取套装详情
     *
     * @param requestBody
     * @return
     */
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<SetItemModel> setItem(@Body RequestBody requestBody);

    /**
     * 获取用户信息
     *
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<SimpleModel> userInfo(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<BaseModel> updateUser(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取省市列表
     *
     * @param requestBody
     * @return
     */
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<AreaModel> getArea(@Body RequestBody requestBody);

    /**
     * 找回密码
     *
     * @param requestBody
     * @return
     */
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<SimpleModel> findPwd(@Body RequestBody requestBody);

    /**
     * 获取套装收藏
     *
     * @param
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<SetListModel> getSetCollect(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取套装收藏
     *
     * @param
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<ItemShowModel> getItemCollect(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取套装收藏
     *
     * @param
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<ShowModel> getShowCollect(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取设计方案和我的Diy
     *
     * @param json
     * @param session
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<SetItemModel> getDiyItemList(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 添加收藏
     *
     * @param json
     * @param session
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<AddFavoriteModel> addFavorite(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 删除收藏
     *
     * @param json
     * @param session
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<AddFavoriteModel> delFavorite(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 提交反馈
     *
     * @param
     * @param
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<FeedbackModel> sendFeedback(@Part("json") RequestBody json, @Part("session") RequestBody session);
 /*   @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<FeedbackModel> sendFeedback(@Body RequestBody requestBody);*/

    /**
     * 上传头像
     *
     * @param
     * @return
     */

    @Multipart
    @POST(Constant.CALL)
    Observable<AvatarModel> sendAvatar(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 提交资料
     *
     * @param json
     * @param session
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<DataModel> sendData(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 邀请人列表
     *
     * @param json
     * @param session
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<InviteModel> getInviteList(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取消息列表
     *
     * @param json
     * @param session
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<UserMessageModel> getUserMessage(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取消息列表
     *
     * @param json
     * @param session
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<BaseModel> comment(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取评论列表
     *
     * @param json
     * @param session
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<CommentModel> getComment(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 申请设计师
     *
     * @param json
     * @param session
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<UserMessageModel> designerApply(@Part("json") RequestBody json, @Part("session") RequestBody session);


    /**
     * 新增地址
     *
     * @param json
     * @param session
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<SaveAddressModel> saveAddress(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取地址列表
     *
     * @param json
     * @param session
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<AddressModel> getAddress(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取地址列表
     *
     * @param json
     * @param session
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<AddressModel> deleteAddress(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<SimpleModel> operationMessage(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取地址列表
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<CityListModel> citylist(@Part("json") RequestBody json);

    /**
     * 获取购物车数量
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<ShoppingCartNumsModel> shoppingCartNums(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 支付信息
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<OrderDetailModel> order(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 删除订单
     *
     * @param json
     * @param session
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<OrderDetailModel> order_delete(@Part("json") RequestBody json, @Part("session") RequestBody session);


    /**
     * 支付信息
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<OrderListModel> orderList(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 支付宝支付信息
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<AlipayModel> alipay(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 支付宝支付信息
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<String> alipay1(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 微信支付
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<JWeichatPayModel> weixinPay(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 关闭订单
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<SimpleModel> closeOrder(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 确认订单
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<SimpleModel> reciveOrder(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 获取销售清单
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<SellItemListModel> getSellItemList(@Part("json") RequestBody json);

    @Multipart
    @POST(Constant.CALL)
    Observable<SellItemListModel> getDiySellItemList(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 退款
     */

    @Multipart
    @POST(Constant.CALL)
    Observable<ExitMoneyModel> exitMoney(@Part("json") RequestBody json, @Part("session") RequestBody session);

    /**
     * 确认退款
     *
     * @param
     * @return
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<OrderDetailModel.DataBean.OrderBean> confirmExitMoney(@Part("json") RequestBody json, @Part("session") RequestBody session);


    @Multipart
    @POST(Constant.CALL)
    Observable<SimpleModel> user_third_login(@Part("json") RequestBody json);

    @Multipart
    @POST(Constant.CALL)
    Observable<ThirdLoginModel> queryOpenID(@Part("json") RequestBody json);

    /**
     * 绑定手机
     */

    @Multipart
    @POST(Constant.CALL)
    Observable<SimpleModel> binding_MobilePhone(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<SimpleModel> unbinding_MobilePhone(@Part("json") RequestBody json, @Part("session") RequestBody session);


    @Multipart
    @POST(Constant.CALL)
    Observable<LaunchModel> launch_Image(@Part("json") RequestBody json);

    @Multipart
    @POST(Constant.CALL)
    Observable<UserLogModel> user_log(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<RebateInfoModel> rebate_details(@Part("json") RequestBody json, @Part("session") RequestBody session);


    @Multipart
    @POST(Constant.CALL)
    Observable<IntegralModel> integral_details(@Part("json") RequestBody json, @Part("session") RequestBody session);


    /**
     * 支付宝超级设计师支付信息
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<AlipayModel> designer_alipay(@Part("json") RequestBody json, @Part("session") RequestBody session);


    /**
     * 微信超级设计师支付
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<JWeichatPayModel> designer_wxpay(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<TimeModel> get_designer_time(@Part("json") RequestBody json, @Part("session") RequestBody session);


    @Multipart
    @POST(Constant.CALL)
    Observable<SignModel> get_calendar_time(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<IsSignModel> get_sign_info(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<BaseModel> sign_time(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<CashModel> reward_cash(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<PickAccountModel> pickCash_account(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<SearchTipsModel> searchTips(@Part("json") RequestBody json);

    @Multipart
    @POST(Constant.CALL)
    Observable<SearchResultModel> search(@Part("json") RequestBody json);

    /**
     * 转帐支付
     */
    @Multipart
    @POST(Constant.CALL)
    Observable<CommitModel> pay_transform_account(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<ExModel> item_exchange_List(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<ActionModel> integral_exchange(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<ValueListModel> coupon_list(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<GetCouModel> get_coupon(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<HouseModel> getCollected(@Part("json") RequestBody json, @Part("session") RequestBody session);

    @Multipart
    @POST(Constant.CALL)
    Observable<SortCityModel> sortCity(@Part("json") RequestBody json);


    @Multipart
    @POST(Constant.CALL)
    Observable<BuildListModel> builingList(@Part("json") RequestBody json);

    @Multipart
    @POST(Constant.CALL)
    Observable<SearchBuildListModel> searchBuilding(@Part("json") RequestBody json);


    /**
     * 获取推荐列表
     *
     * @param requestBody ：请求体
     * @return ：
     */
    @HTTP(method = "POST", hasBody = true, path = Constant.CALL)
    Observable<RecommendModel> recommendSet(@Body RequestBody requestBody);


    @Multipart
    @POST(Constant.CALL)
    Observable<TransModel> transfer_conpon(@Part("json") RequestBody json, @Part("session") RequestBody session);

}
