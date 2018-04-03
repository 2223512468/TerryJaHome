package com.jajahome.util.signutils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.constant.Constant;
import com.jajahome.model.BaseModel;
import com.jajahome.model.SignModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.util.IdCheckUtil;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/17.
 */
@SuppressLint("NewApi")
public class HuaCalendarView extends LinearLayout implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private GridView mGv_Calendar;
    private TextView mTv_Current_Time, integerFen, signTips;
    private CalendarViewAdapter mCalendarViewAdapter;
    private CalendarViewAdapter.onClickSignInCallBack mClickSignInCallBack;
    private TextView textTitle, goOnSignTv;
    private List<SignModel.DataBean.CalendarBean.CalendarInfoBean> calModel = new ArrayList<>();
    private String mCurrentDay;
    private ImageButton iBackBtn;
    private String weekDay;

    public HuaCalendarView(Context context, CalendarViewAdapter.onClickSignInCallBack callBack) {
        super(context);
        this.mContext = context;
        this.mClickSignInCallBack = callBack;
        initView();
        initData();
    }

    public HuaCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
        initData();
    }

    public HuaCalendarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;

        initView();
        initData();
    }

    public void initView() {
        mLayoutInflater = LayoutInflater.from(mContext);
        mLayoutInflater.inflate(R.layout.view_calendar, this);
        textTitle = (TextView) findViewById(R.id.textView2);
        goOnSignTv = (TextView) findViewById(R.id.tv_get_money);
        mTv_Current_Time = (TextView) findViewById(R.id.tv_current_time);
        mGv_Calendar = (GridView) findViewById(R.id.gv_calendar);
        integerFen = (TextView) findViewById(R.id.tv_total_price_num);
        iBackBtn = (ImageButton) findViewById(R.id.ibtn_back);
        signTips = (TextView) findViewById(R.id.sign_link);
        signTips.setText("每日签到获得的金币,可以到www.jajahome.com官方网站，兑换下载产品3D模型！也可以在活动期间兑换礼品!");
        signTips.setMovementMethod(LinkMovementMethod.getInstance());
        getCalendarTime();

        iBackBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) mContext;
                activity.finish();
            }
        });
    }

    public void initData() {
        // mTv_Current_Time.setText(Utils.getTime(System.currentTimeMillis()));
        textTitle.setText(R.string.sign_exchange);
        // goOnSignTv.setText("连续签到第100天");
        mCalendarViewAdapter = new CalendarViewAdapter(mContext, Utils.getCurrentMonthDay(), mTv_Current_Time, calModel);
        mGv_Calendar.setAdapter(mCalendarViewAdapter);
        mCalendarViewAdapter.setonClickSignInCallBack(mClickSignInCallBack);
        mGv_Calendar.setOnItemClickListener(this);
        getCurrentTimePosition();
    }


    private void getCalendarTime() {
        if (LoginUtil.getInfo(mContext) == null) return;
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.GETCALENDARTIME);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        ApiImp.get().get_calendar_time(requestBody, requestBody1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SignModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(SignModel model) {
                        int count = model.getData().getCalendar().getCalendar_info().size();
                        if (model != null) {
                            goOnSignTv.setText("连续签到" + model.getData().getCalendar().getSignDays() + "天");
                            integerFen.setText(model.getData().getCalendar().getTotalIntegral() + "");
                        }
                        if (model.getData().getCalendar().getCalendar_info().size() > 0) {
                            mCalendarViewAdapter.setWeekDay(subStr(model.getData().getCalendar().getDayString()), count, model.getData().getCalendar().getWeek_day());
                            mCalendarViewAdapter.addItem(model.getData().getCalendar().getCalendar_info());
                            mCalendarViewAdapter.notifyDataSetChanged();
                            mTv_Current_Time.setText(model.getData().getCalendar().getDayString());
                            mCurrentDay = subStr(model.getData().getCalendar().getDayString());
                        }
                    }
                });
    }

    private String subStr(String mCurDay) {
        String time = mCurDay.substring(8, mCurDay.length());
        String firTime = time.substring(0, 1);

        if (firTime.equals("0")) {
            String secTime = time.substring(1, 2);
            return secTime;
        } else {
            return time;
        }
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
        TextView textView = (TextView) view
                .findViewById(R.id.tv_calendar_day);
        String clickDay = textView.getText().toString();
        Log.e("day", clickDay);

        if (!StringUtil.isEmpty(clickDay) && !StringUtil.isEmpty(mCurrentDay)) {
            if (mCurrentDay.equals(clickDay)) {
                SignDate(position);
            } else if (IdCheckUtil.isNumer(clickDay) && (Integer.parseInt(clickDay) > Integer.parseInt(mCurrentDay))) {
                Toast.makeText(mContext, "不要心急，记得连续签到哟！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setOnClickSignInCallBack(CalendarViewAdapter.onClickSignInCallBack callBack) {
        this.mClickSignInCallBack = callBack;
        mCalendarViewAdapter.setonClickSignInCallBack(mClickSignInCallBack);
    }


    public int getCurrentTime() {
        return Utils.getDayOfMonth();
    }

    public int getCurrentTimePosition() {
        int position;
        if (Utils.getCurrentMonthStart() == 7) {
            position = 0;
        } else
            position = Utils.getCurrentMonthStart();
        return getCurrentTime() + 6 + position;

    }


    public void onSignInCurrent(int day) {
        int position;
        if (day == 0) {
            position = getCurrentTimePosition();
        } else
            position = day + 6 + Utils.getCurrentMonthStart();

        mCalendarViewAdapter.onRefresh(position, true);
    }


    public void setSignInDays(List<Integer> integers) {
        for (int i = 0; i < integers.size(); i++) {
            int position = integers.get(i) + 6 + Utils.getCurrentMonthStart();
            mCalendarViewAdapter.onRefresh(position, true);
        }
    }


    public void setCurrentTime(String time) {
        mTv_Current_Time.setText(time);

    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    private void SignDate(final int position) {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.SIGNTIME);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("application/session"), gson.toJson(LoginUtil.getInfo(mContext).getData().getSession()));
        ApiImp.get().sign_time(requestBody, requestBody1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getCalendarTime();
                        e.printStackTrace();
                        getCalendarTime();
                    }

                    @Override
                    public void onNext(BaseModel model) {
                        if (model != null) {
                            getCalendarTime();
                        }
                    }
                });
    }
}

