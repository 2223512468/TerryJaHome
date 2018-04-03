package com.jajahome.pop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jajahome.R;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.user.activity.CashAct;
import com.jajahome.util.StringUtil;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.tencent.mm.sdk.openapi.IWXAPI;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/7/5.
 */
public class PopCash {
    private Context mContext;
    private Activity activity;
    private PopupWindow mPopupWindow;
    private View view;
    private ImageView dismiss;
    private EditText accEt;
    private Button rightBtn;


    public PopCash(Context context, IWXAPI mIwxapi, IWeiboShareAPI mIWeiboShareAPI, View view) {
        this.activity = (Activity) context;
        this.mContext = context;
        this.view = view;
        init();
    }

    public PopCash(Context context, View view) {

        this.mContext = context;
        this.view = view;
        init();
    }

    protected void init() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.act_cash_et, null);
        dismiss = (ImageView) rootView.findViewById(R.id.dismiss);
        accEt = (EditText) rootView.findViewById(R.id.acc_name);
        rightBtn = (Button) rootView.findViewById(R.id.rignov_cash);
        mPopupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_transparent));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setListener();
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

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = accEt.getText().toString();
                if (!StringUtil.isEmpty(msg)) {
                    EventBus.getDefault().post(new EventMessage(CashAct.action, msg));
                }
                dismiss();
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
