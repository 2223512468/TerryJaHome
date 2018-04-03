package com.jajahome.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.home.MainAty;
import com.jajahome.feature.user.activity.LoginAct;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * 类描述：
 * 创建人：lhz
 * 创建时间：2016/6/24 9:10
 * 修改时间：2016/6/24 9:10
 * 修改备注：
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(getApplicationContext(), Constant.WEICHAT_APP_ID, true);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        finish();
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                SendAuth.Resp sendResp = (SendAuth.Resp) baseResp;
                String code = sendResp.code;
                EventBus.getDefault().post(new EventMessage(MainAty.WX_LOGIN, code));
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
            default:
                break;

        }
    }
}
