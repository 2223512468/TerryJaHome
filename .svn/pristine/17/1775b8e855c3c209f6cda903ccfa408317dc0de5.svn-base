package com.jajahome.util.signutils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.model.SignModel;
import com.jajahome.util.IdCheckUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.GiftImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */
@SuppressLint("NewApi")
public class CalendarViewAdapter extends BaseAdapter {

    private Context mContext;
    private int mCountDay = 42;
    private int mCurrent_mouth_Countday;
    private int mCurrent_Week, size;
    private int id[] = {R.string.week7, R.string.week1, R.string.week2,
            R.string.week3, R.string.week4, R.string.week5, R.string.week6};
    private ViewHolder mViewHolder;
    private List<Boolean> booleans;
    private HuahuaLoadPop mLoadPop;
    private View mView;
    private onClickSignInCallBack mCallBack;
    private String mCurrentDay;
    private List<SignModel.DataBean.CalendarBean.CalendarInfoBean> calModel = new ArrayList<>();
    private int weekDays;

    public void addItem(List<SignModel.DataBean.CalendarBean.CalendarInfoBean> calendarModel) {
        calModel.clear();
        for (int i = 0; i < calendarModel.size(); i++) {
            calModel.add(calendarModel.get(i));
        }
        notifyDataSetChanged();
    }

    public void setWeekDay(String weekDay, int countday, String firstDay) {
        mCurrentDay = weekDay;
        this.mCurrent_Week = Integer.parseInt(firstDay);
        this.mCurrent_mouth_Countday = countday;
        int atLeastCount = mCurrent_Week + countday + 7;
        if (atLeastCount > 42) {
            mCountDay = 49;
        } else {
            mCountDay = 42;
        }

        notifyDataSetChanged();
    }

    public CalendarViewAdapter(Context context, int countday, View view, List<SignModel.DataBean.CalendarBean.CalendarInfoBean> calModel) {
        this.mContext = context;
        this.mCurrent_Week = Utils.getCurrentMonthStart();
        this.mCurrent_mouth_Countday = countday;
        int atLeastCount = mCurrent_Week + countday + 7;


        if (atLeastCount > 42) {
            mCountDay = 49;
        } else {
            mCountDay = 42;
        }
        this.calModel = calModel;
        booleans = new ArrayList<Boolean>();
        mView = view;
        mLoadPop = new HuahuaLoadPop(mContext, mView);

        for (int i = 0; i < mCountDay; i++) {
            booleans.add(i, false);
        }
    }

    public void setonClickSignInCallBack(onClickSignInCallBack callBack) {
        this.mCallBack = callBack;
    }

    @Override
    public int getCount() {
        return mCountDay;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        int pos = 0;
        int weekPos = 0;

        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_calendar, null);
            mViewHolder.mTv_calendar_day = (TextView) convertView
                    .findViewById(R.id.tv_calendar_day);
            mViewHolder.mFl_calendar = (RelativeLayout) convertView.findViewById(R.id.fl_calendar);
            mViewHolder.rl = (RelativeLayout) convertView.findViewById(R.id.rl);
            mViewHolder.giftGray = (ImageView) convertView.findViewById(R.id.gift_iv);
            mViewHolder.giftRed = (GiftImageView) convertView.findViewById(R.id.gift_red_iv);
            convertView.setTag(mViewHolder);
        } else
            mViewHolder = (ViewHolder) convertView.getTag();
        /////////////////////////////////////////////////////////////////////////////
        if (position <= 6) {
            mViewHolder.giftGray.setVisibility(View.GONE);
            mViewHolder.mTv_calendar_day.setTextColor(Color.BLACK);
            mViewHolder.rl.setBackgroundColor(mContext.getResources().getColor(R.color.sign_gray));
            mViewHolder.mTv_calendar_day.setTextSize(mContext.getResources()
                    .getDimension(R.dimen.text_size_7));
            mViewHolder.mTv_calendar_day.setText(mContext.getResources().getString(
                    id[position]));
            mViewHolder.mFl_calendar.setGravity(Gravity.CENTER);

        } else {
            pos = position - 6;
            weekPos = position - mCurrent_Week - 6;
            //日历
            if (mCurrent_Week == 7 && pos <= mCurrent_mouth_Countday && calModel.size() > 0) {
                mViewHolder.mTv_calendar_day.setText(calModel.get(position - 6).getDay() + "");
            } else if (position - 7 >= mCurrent_Week && weekPos <= mCurrent_mouth_Countday && calModel.size() > 0) {
                if (weekPos - 1 < calModel.size()) {
                    mViewHolder.mTv_calendar_day.setText(calModel.get(weekPos - 1).getDay() + "");
                }
            }
        }

/////////////////////////////////////////////////////////////////////////////
        if (position % 7 == 7) {
            mViewHolder.mFl_calendar.setBackgroundResource(R.drawable.line_right);
        } else if (position % 7 == 1) {
            mViewHolder.mFl_calendar.setBackgroundResource(R.drawable.line_left);
        }

    /*    if (booleans.get(position)) {
            mViewHolder.giftGray.setBackgroundResource(R.mipmap.ic_already_sign);
        } else {*/
        if (Build.VERSION.SDK_INT > 8) {
            mViewHolder.mTv_calendar_day.setBackground(null);
        }
        //}
/////////////////////////////////////////////////////////////////////////////


        //判断日历中的每个ITEM是否是数字
        String msg = (String) mViewHolder.mTv_calendar_day.getText();
        if (StringUtil.isEmpty(msg) || position <= 6) {
            mViewHolder.giftGray.setVisibility(View.GONE);
        } else {
            mViewHolder.giftGray.setVisibility(View.VISIBLE);
        }

        size = weekPos - 1;

        //判断当天是否已签到
        if (calModel.size() > 0 && size >= 0) {
            if (size < calModel.size() && (calModel.get(size).getDay() + "").equals(mCurrentDay)) {
                if (calModel.get(size).getSign().equals("1")) {
                    mViewHolder.giftRed.setVisibility(View.INVISIBLE);
                    mViewHolder.giftGray.setBackgroundResource(R.mipmap.ic_already_sign);
                    mViewHolder.giftGray.setVisibility(View.VISIBLE);
                    mViewHolder.mTv_calendar_day.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                } else if (calModel.get(size).getSign().equals("0")) {
                    mViewHolder.giftRed.setVisibility(View.VISIBLE);
                    mViewHolder.giftGray.setBackgroundResource(R.mipmap.ic_already_sign);
                    mViewHolder.giftGray.setVisibility(View.INVISIBLE);
                }
            }
        }

/////////////////////////////////////////////////////////////////////////////
        if (!StringUtil.isEmpty(msg) && IdCheckUtil.isNumer(msg)) {
            int monthDay = Integer.parseInt(msg);
            int mCurMonthDay = Integer.parseInt(mCurrentDay);
            //循环遍历日历中的数字是否小于本月号数
            if (monthDay < mCurMonthDay) {
                if (mCurrent_Week == 7 && pos <= mCurrent_mouth_Countday && calModel.size() > 0) {
                    String sign = calModel.get(pos).getSign();
                    if (sign.equals("1")) {
                        mViewHolder.giftGray.setBackgroundResource(R.mipmap.ic_already_sign);
                    } else if (sign.equals("0")) {
                        mViewHolder.giftGray.setBackgroundResource(R.mipmap.ic_un_sign);
                    }
                } else if (position - 7 >= mCurrent_Week && weekPos <= mCurrent_mouth_Countday && calModel.size() > 0) {
                    String sign = calModel.get(weekPos - 1).getSign();
                    if (sign.equals("1")) {
                        mViewHolder.giftGray.setBackgroundResource(R.mipmap.ic_already_sign);
                    } else if (sign.equals("0")) {
                        mViewHolder.giftGray.setBackgroundResource(R.mipmap.ic_un_sign);
                    }
                }
            }
        }


        return convertView;
    }

    public void onRefresh(int position, Boolean isClick) {
        if (position <= 6) {

        } else {
            if (mCurrent_Week == 7 && (position - 6) <= mCurrent_mouth_Countday) {
                if (!booleans.get(position)) {
                    booleans.set(position, isClick);
                    mCallBack.onSucess();
                }
            } else if (position - 7 >= mCurrent_Week
                    && position - mCurrent_Week - 6 <= mCurrent_mouth_Countday) {
                if (!booleans.get(position)) {
                    booleans.set(position, isClick);
                    mCallBack.onSucess();
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView mTv_calendar_day;
        RelativeLayout mFl_calendar, rl;
        ImageView giftGray;
        GiftImageView giftRed;
    }


    public interface onClickSignInCallBack {

        public void onSucess();

        public void onFail(String error);

        public void onTimeOut(String msg);
    }
}
