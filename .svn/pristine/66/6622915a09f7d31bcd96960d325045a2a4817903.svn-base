package com.jajahome.network;

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
import retrofit2.http.Part;
import rx.Observable;

/**
 * 类描述：
 * 创建人：admin
 * 创建时间：2016/5/27 10:35
 * 修改人：admin
 * 修改时间：2016/5/27 10:35
 * 修改备注：
 */
public class ApiImp implements Api {
    private static ApiImp mApiImp;

    public static ApiImp get() {
        if (null == mApiImp) {
            mApiImp = new ApiImp();
        }
        return mApiImp;
    }

    private Api genApi() {
        return HttpService.instance().getApi();
    }

    @Override
    public Observable<SetModel> register(@Body RequestBody requestBody) {
        return genApi().register(requestBody);
    }

    @Override
    public Observable<HomeBannerModel> home(@Body RequestBody requestBody) {
        return genApi().home(requestBody);
    }

    @Override
    public Observable<RecommendModel> recommend(@Body RequestBody requestBody) {
        return genApi().recommend(requestBody);
    }

    @Override
    public Observable<ConfigModel> config(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().config(json, session);
    }

    @Override
    public Observable<ConfigModel> config_v2(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().config_v2(json, session);
    }

    @Override
    public Observable<AuthCodeModle> getAuthCode(RequestBody requestBody) {
        return genApi().getAuthCode(requestBody);
    }

    @Override
    public Observable<ItemListModel> itemList(@Body RequestBody requestBody) {
        return genApi().itemList(requestBody);
    }

    @Override
    public Observable<SetListModel> setList(@Body RequestBody requestBody) {
        return genApi().setList(requestBody);
    }

    @Override
    public Observable<DiyListModel> diyList(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().diyList(json, session);
    }

    @Override
    public Observable<BuildingModel> buildingList(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().buildingList(json, session);
    }


    @Override
    public Observable<HouseListModel> houseList(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().houseList(json, session);
    }

    @Override
    public Observable<DiyModel> diy(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().diy(json, session);
    }

    @Override
    public Observable<SimpleModel> saveDiy(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().saveDiy(json, session);
    }


    @Override
    public Observable<ShowListModel> showlist(@Body RequestBody requestBody) {
        return genApi().showlist(requestBody);
    }

    @Override
    public Observable<ShowDetailModel> show(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().show(json, session);
    }

    @Override
    public Observable<AvatarModel> sendAvatar(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().sendAvatar(json, session);
    }

    @Override
    public Observable<SimpleModel> getRegister(@Body RequestBody requestBody) {
        return genApi().getRegister(requestBody);
    }

    @Override
    public Observable<SimpleModel> sendLogin(@Body RequestBody requestBody) {
        return genApi().sendLogin(requestBody);
    }


    @Override
    public Observable<SimpleModel> changePwd(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().changePwd(json, session);
    }

    @Override
    public Observable<ItemModel> item(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().item(json, session);
    }


    @Override
    public Observable<AreaModel> getArea(@Body RequestBody requestBody) {
        return genApi().getArea(requestBody);
    }

    @Override
    public Observable<SetModel> set(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().set(json, session);
    }

    @Override
    public Observable<SetItemModel> setItem(@Body RequestBody requestBody) {
        return genApi().setItem(requestBody);
    }

    @Override
    public Observable<SimpleModel> userInfo(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().userInfo(json, session);
    }

    @Override
    public Observable<BaseModel> updateUser(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().updateUser(json, session);
    }

    @Override
    public Observable<SimpleModel> findPwd(@Body RequestBody requestBody) {
        return genApi().findPwd(requestBody);
    }

    @Override
    public Observable<SetListModel> getSetCollect(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().getSetCollect(json, session);
    }

    @Override
    public Observable<ItemShowModel> getItemCollect(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().getItemCollect(json, session);
    }

    @Override
    public Observable<ShowModel> getShowCollect(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().getShowCollect(json, session);
    }

    @Override
    public Observable<SetItemModel> getDiyItemList(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().getDiyItemList(json, session);
    }

    @Override
    public Observable<AddFavoriteModel> addFavorite(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().addFavorite(json, session);
    }

    @Override
    public Observable<AddFavoriteModel> delFavorite(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().delFavorite(json, session);
    }

    @Override
    public Observable<FeedbackModel> sendFeedback(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().sendFeedback(json, session);
    }

    @Override
    public Observable<DataModel> sendData(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().sendData(json, session);
    }

    @Override
    public Observable<InviteModel> getInviteList(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().getInviteList(json, session);
    }

    @Override
    public Observable<UserMessageModel> getUserMessage(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().getUserMessage(json, session);
    }

    @Override
    public Observable<BaseModel> comment(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().comment(json, session);
    }

    @Override
    public Observable<CommentModel> getComment(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().getComment(json, session);
    }

    @Override
    public Observable<UserMessageModel> designerApply(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().designerApply(json, session);
    }

    @Override
    public Observable<SaveAddressModel> saveAddress(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().saveAddress(json, session);
    }

    @Override
    public Observable<AddressModel> getAddress(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().getAddress(json, session);
    }

    @Override
    public Observable<AddressModel> deleteAddress(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().deleteAddress(json, session);
    }

    @Override
    public Observable<SimpleModel> operationMessage(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().operationMessage(json, session);
    }

    @Override
    public Observable<CityListModel> citylist(RequestBody json) {
        return genApi().citylist(json);
    }

    @Override
    public Observable<ShoppingCartNumsModel> shoppingCartNums(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().shoppingCartNums(json, session);
    }

    @Override
    public Observable<AlipayModel> alipay(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().alipay(json, session);
    }

    @Override
    public Observable<String> alipay1(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().alipay1(json, session);
    }


    @Override
    public Observable<JWeichatPayModel> weixinPay(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().weixinPay(json, session);
    }

    @Override
    public Observable<SimpleModel> closeOrder(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().closeOrder(json, session);
    }

    @Override
    public Observable<SimpleModel> reciveOrder(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().reciveOrder(json, session);
    }

    @Override
    public Observable<SellItemListModel> getSellItemList(@Part("json") RequestBody json) {
        return genApi().getSellItemList(json);
    }

    @Override
    public Observable<SellItemListModel> getDiySellItemList(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().getDiySellItemList(json, session);
    }


    public Observable<ExitMoneyModel> exitMoney(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().exitMoney(json, session);
    }

    @Override
    public Observable<OrderDetailModel.DataBean.OrderBean> confirmExitMoney(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().confirmExitMoney(json, session);
    }

    @Override
    public Observable<SimpleModel> user_third_login(@Part("json") RequestBody json) {
        return genApi().user_third_login(json);
    }

    @Override
    public Observable<ThirdLoginModel> queryOpenID(@Part("json") RequestBody json) {
        return genApi().queryOpenID(json);
    }

    @Override
    public Observable<SimpleModel> binding_MobilePhone(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().binding_MobilePhone(json, session);
    }

    @Override
    public Observable<SimpleModel> unbinding_MobilePhone(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().unbinding_MobilePhone(json, session);
    }

    @Override
    public Observable<SetVRModel> vr_get_and_create_vrXML(@Body RequestBody requestBody) {
        return genApi().vr_get_and_create_vrXML(requestBody);
    }

    @Override
    public Observable<YunOrderModel> order_Waybill_query(@Part("json") RequestBody json, @Part("session") RequestBody session) {

        return genApi().order_Waybill_query(json, session);
    }

    @Override
    public Observable<WuLiuModel> order_logistics_query(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().order_logistics_query(json, session);
    }

    @Override
    public Observable<OrderDetailModel> order(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().order(json, session);
    }

    @Override
    public Observable<OrderDetailModel> order_delete(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().order_delete(json, session);
    }

    @Override
    public Observable<OrderListModel> orderList(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().orderList(json, session);
    }

    @Override
    public Observable<LaunchModel> launch_Image(@Part("json") RequestBody json) {
        return genApi().launch_Image(json);
    }

    @Override
    public Observable<UserLogModel> user_log(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().user_log(json, session);
    }

    @Override
    public Observable<RebateInfoModel> rebate_details(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().rebate_details(json, session);
    }

    @Override
    public Observable<IntegralModel> integral_details(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().integral_details(json, session);
    }

    @Override
    public Observable<AlipayModel> designer_alipay(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().designer_alipay(json, session);
    }

    @Override
    public Observable<JWeichatPayModel> designer_wxpay(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().designer_wxpay(json, session);
    }

    @Override
    public Observable<TimeModel> get_designer_time(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().get_designer_time(json, session);
    }

    @Override
    public Observable<SignModel> get_calendar_time(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().get_calendar_time(json, session);
    }

    @Override
    public Observable<IsSignModel> get_sign_info(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().get_sign_info(json, session);
    }

    @Override
    public Observable<BaseModel> sign_time(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().sign_time(json, session);
    }

    @Override
    public Observable<CashModel> reward_cash(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().reward_cash(json, session);
    }

    @Override
    public Observable<PickAccountModel> pickCash_account(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().pickCash_account(json, session);
    }

    @Override
    public Observable<CommitModel> pay_transform_account(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().pay_transform_account(json, session);
    }

    @Override
    public Observable<ExModel> item_exchange_List(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().item_exchange_List(json, session);
    }

    @Override
    public Observable<ActionModel> integral_exchange(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().integral_exchange(json, session);
    }

    @Override
    public Observable<ValueListModel> coupon_list(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().coupon_list(json, session);
    }

    @Override
    public Observable<GetCouModel> get_coupon(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().get_coupon(json, session);
    }

    @Override
    public Observable<HouseModel> getCollected(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().getCollected(json, session);
    }

    @Override
    public Observable<SortCityModel> sortCity(@Part("json") RequestBody json) {
        return genApi().sortCity(json);
    }

    @Override
    public Observable<BuildListModel> builingList(@Part("json") RequestBody json) {
        return genApi().builingList(json);
    }

    @Override
    public Observable<SearchBuildListModel> searchBuilding(@Part("json") RequestBody json) {
        return genApi().searchBuilding(json);
    }

    @Override
    public Observable<RecommendModel> recommendSet(@Body RequestBody requestBody) {
        return genApi().recommendSet(requestBody);
    }

    @Override
    public Observable<TransModel> transfer_conpon(@Part("json") RequestBody json, @Part("session") RequestBody session) {
        return genApi().transfer_conpon(json,session);
    }

    @Override
    public Observable<SearchTipsModel> searchTips(@Part("json") RequestBody json) {
        return genApi().searchTips(json);
    }

    @Override
    public Observable<SearchResultModel> search(@Part("json") RequestBody json) {
        return genApi().search(json);
    }
}
