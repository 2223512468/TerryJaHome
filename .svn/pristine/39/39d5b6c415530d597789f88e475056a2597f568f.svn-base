package com.jajahome.pop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jajahome.R;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.user.activity.UserAct;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.tencent.mm.sdk.openapi.IWXAPI;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/6/6.
 */
public class PopPay {
    private Context mContext;
    private Activity activity;
    private PopupWindow mPopupWindow;
    private View view;
    private int mPayWay = -1;//支付方式 0微信 1支付宝,-1未选择
    private IWXAPI mIwxapi;      //微信api
    private CheckBox cbAlipay, cbWeichat;


    public PopPay(Context context, IWXAPI mIwxapi, IWeiboShareAPI mIWeiboShareAPI, View view) {
        this.activity = (Activity) context;
        this.mContext = context;
        this.view = view;
        init();
    }

    public PopPay(Context context, View view) {

        this.mContext = context;
        this.view = view;
        init();
    }

    protected void init() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.act_pop_pay, null);
        TextView deleteTv = (TextView) rootView.findViewById(R.id.delete);
        Button superPay = (Button) rootView.findViewById(R.id.user_btn_pay);
        cbAlipay = (CheckBox) rootView.findViewById(R.id.cb_alipay);
        cbWeichat = (CheckBox) rootView.findViewById(R.id.cb_weichat);

        mPopupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_transparent));
        mPopupWindow.setFocusable(true);
        setListener();
        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        superPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                EventBus.getDefault().post(new EventMessage(UserAct.MPAY_METHOD, String.valueOf(mPayWay)));
            }
        });
    }


    /**
     * 显示popupwindow
     */
    public void show() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            mPopupWindow.showAtLocation(view, 0, 0, 0);
        }
    }

    /**
     * 显示popupwindow
     *
     * @param view
     */
    public void show(View view, String text) {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            mPopupWindow.showAtLocation(view, 0, 0, 0);
        }
    }

    private void setListener() {
        cbWeichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPayWay != 0) {
                    mPayWay = 0;
                    cbWeichat.setChecked(true);
                    cbAlipay.setChecked(false);
                } else {
                    mPayWay = -1;
                }
            }
        });
        cbAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPayWay != 1) {
                    mPayWay = 1;
                    cbWeichat.setChecked(false);
                    cbAlipay.setChecked(true);
                } else {
                    mPayWay = -1;
                }
            }
        });
    }


    /**
     * 关闭popupwindow
     */
    public void dismiss() {
        if (mPopupWindow.isShowing()) mPopupWindow.dismiss();
    }

    protected void showToast(String str) {
        //去换行
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
