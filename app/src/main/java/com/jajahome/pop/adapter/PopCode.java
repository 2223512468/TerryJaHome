package com.jajahome.pop.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.constant.Constant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.user.fragment.CouFrag;
import com.jajahome.model.GetCouModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.CouReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.util.DensityUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.util.T;

import org.greenrobot.eventbus.EventBus;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${Terry} on 2017/9/18.
 */
public class PopCode {

    private PopupWindow mPopupWindow;
    private Context mContext;
    private View view;
    private int id;
    private String code;

    public PopCode(View view, Context mContext, int id, String code) {
        this.mContext = mContext;
        this.view = view;
        this.id = id;
        this.code = code;
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.act_pop_code, null);
        Button btn = (Button) rootView.findViewById(R.id.btn);
        TextView disIMG = (TextView) rootView.findViewById(R.id.dimiss);
        final EditText et = (EditText) rootView.findViewById(R.id.et_code);
        int height = (int) DensityUtil.getDisplayHeightDp(mContext);
        int width = (int) DensityUtil.getDisplayWidthDp(mContext);

        mPopupWindow = new PopupWindow(rootView, width - 16, height / 2);
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_transparent));
        mPopupWindow.setFocusable(true);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = et.getText().toString();
                if (StringUtil.isEmpty(msg)) {
                    T.showShort(mContext, "请输入兑换码");
                    return;
                }
                if (!code.equals(msg)) {
                    T.showShort(mContext, "兑换码有误，请重新输入");
                    return;
                } else {
                    okBuy();
                }
            }
        });
        disIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void okBuy() {
        CouReq.ContentBean bean = new CouReq.ContentBean();
        bean.setCode(code);
        bean.setId(id);
        CouReq req = new CouReq();
        req.setContent(bean);
        req.setCmd(Constant.GETCOUPON);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        ApiImp.get().get_coupon(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetCouModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        T.showShort(mContext, "领取异常");
                    }

                    @Override
                    public void onNext(GetCouModel model) {
                        dismiss();
                        T.showShort(mContext, "领取成功");
                        EventBus.getDefault().post(new EventMessage(CouFrag.REFRESH, ""));
                    }
                });
    }


    public void show() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            mPopupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    public void dismiss() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

}
