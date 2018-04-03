package com.jajahome.wxapi;

import com.jajahome.model.JWeichatPayModel;
import com.tencent.mm.sdk.modelpay.PayReq;

public class WxPayApi {

    public static PayReq genPayReq(JWeichatPayModel model) {
        JWeichatPayModel.DataBean.PayInfoBean payInfoBean = model.getData().getPay_info();
        PayReq req = new PayReq();
        req.appId = payInfoBean.getAppid();
        req.partnerId = payInfoBean.getPartnerid();
        req.prepayId = payInfoBean.getPrepayid();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = payInfoBean.getNoncestr();
        req.timeStamp = String.valueOf(payInfoBean.getTimestamp());
        req.sign = payInfoBean.getSign();
        return req;
    }

}