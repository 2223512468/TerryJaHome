package com.jajahome.wxapi;

import android.content.Intent;

import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    public static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    /**
     * 微信结果标志
     */
    public static final int WEICAT_PAY_RESULT_ACTION = 0X993;

    private IWXAPI api;

    @Override
    protected int getViewId() {
        return 0;
    }

    @Override
    protected void initEvent() {
        api = WXAPIFactory.createWXAPI(this, Constant.WEICHAT_APP_ID);
        api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq req) {
    }

    /**
     *
     Field Summary
     static int	ERR_AUTH_DENIED

     认证被否决
     static int	ERR_COMM

     一般错误
     static int	ERR_OK

     正确返回
     static int	ERR_SENT_FAILED

     发送失败
     static int	ERR_UNSUPPORT

     不支持错误
     static int	ERR_USER_CANCEL

     用户取消

     */
    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            EventBus.getDefault().post(new EventMessage(WEICAT_PAY_RESULT_ACTION,String.valueOf( resp.errCode)));
            finish();
        }
    }
}