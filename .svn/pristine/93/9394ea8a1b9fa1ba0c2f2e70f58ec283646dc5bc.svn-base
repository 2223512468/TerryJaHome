package com.jajahome.widget.datepick;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jajahome.R;
import com.jajahome.util.DateUtil;
import com.jajahome.util.StringUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * 类描述：
 * 创建人：lhz
 * 创建时间：2016/6/7 15:07
 * 修改时间：2016/6/7 15:07
 * 修改备注：
 */
public class TimeSelector {
    public interface ResultHandler {
        void handle(String time);
    }

    private ResultHandler handler;
    private Context context;
    private final String FORMAT_STR = "yyyy-MM-dd";
    private PopupWindow seletorDialog;
    private PickerView year_pv;
    private PickerView month_pv;
    private PickerView day_pv;
    private final int MAXMONTH = 12;

    private ArrayList<String> year, month, day;
    private int startYear, startMonth, startDay, endYear, endMonth, endDay;
    private boolean spanYear, spanMon, spanDay;
    private Calendar selectedCalender = Calendar.getInstance();
    private final long ANIMATORDELAY = 200L;
    private final long CHANGEDELAY = 90L;
    private String workStart_str;
    private String workEnd_str;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private TextView tv_select;


    public TimeSelector(Context context, ResultHandler resultHandler, String startDate, String endDate) {
        this.context = context;
        this.handler = resultHandler;
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        startCalendar.setTime(DateUtil.parse(startDate, FORMAT_STR));
        endCalendar.setTime(DateUtil.parse(endDate, FORMAT_STR));
        initDialog(context);
        initView();
    }


    public TimeSelector(Context context, ResultHandler resultHandler, String startDate, String endDate, String workStartTime, String workEndTime) {
        this(context, resultHandler, startDate, endDate);
        this.workStart_str = workStartTime;
        this.workEnd_str = workEndTime;
    }

    public void show(View view) {
        if (startCalendar.getTime().getTime() >= endCalendar.getTime().getTime()) {
            Toast.makeText(context, "start>end", Toast.LENGTH_LONG).show();
            return;
        }

        if (!excuteWorkTime()) return;
        initParameter();
        initTimer();
        addListener();
        seletorDialog.showAtLocation(view, 0, 0, 0);

    }

    private void initDialog(Context context) {
        if (seletorDialog == null) {
            View rootView = LayoutInflater.from(context).inflate(R.layout.pop_selector, null);
            rootView.findViewById(R.id.select_time_empty_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(seletorDialog!=null&&seletorDialog.isShowing())
                        seletorDialog.dismiss();
                }
            });
            year_pv = (PickerView) rootView.findViewById(R.id.year_pv);
            month_pv = (PickerView) rootView.findViewById(R.id.month_pv);
            day_pv = (PickerView) rootView.findViewById(R.id.day_pv);
            tv_select = (TextView) rootView.findViewById(R.id.tv_select);
            seletorDialog = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            seletorDialog.setFocusable(true);
        }
    }

    private void initView() {

        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.handle(DateUtil.format(selectedCalender.getTime(), FORMAT_STR));
                seletorDialog.dismiss();
            }
        });

    }

    private void initParameter() {
        startYear = startCalendar.get(Calendar.YEAR);
        startMonth = startCalendar.get(Calendar.MONTH) + 1;
        startDay = startCalendar.get(Calendar.DAY_OF_MONTH);

        endYear = endCalendar.get(Calendar.YEAR);
        endMonth = endCalendar.get(Calendar.MONTH) + 1;
        endDay = endCalendar.get(Calendar.DAY_OF_MONTH);

        spanYear = startYear != endYear;
        spanMon = (!spanYear) && (startMonth != endMonth);
        spanDay = (!spanMon) && (startDay != endDay);
        Date date = new Date(System.currentTimeMillis());
        selectedCalender.setTime(date);
    }

    private void initTimer() {
        initArrayList();

        if (spanYear) {
            for (int i = startYear; i <= endYear; i++) {
                year.add(String.valueOf(i));
            }
            for (int i = startMonth; i <= MAXMONTH; i++) {
                month.add(fomatTimeUnit(i));
            }
            for (int i = startDay; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i));
            }

        } else if (spanMon) {
            year.add(String.valueOf(startYear));
            for (int i = startMonth; i <= endMonth; i++) {
                month.add(fomatTimeUnit(i));
            }
            for (int i = startDay; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i));
            }
        } else if (spanDay) {
            year.add(String.valueOf(startYear));
            month.add(fomatTimeUnit(startMonth));
            for (int i = startDay; i <= endDay; i++) {
                day.add(fomatTimeUnit(i));
            }

        }
        loadComponent();

    }

    private boolean excuteWorkTime() {
        boolean res = true;
        if (!StringUtil.isEmpty(workStart_str) && !StringUtil.isEmpty(workEnd_str)) {
            String[] start = workStart_str.split(":");
            String[] end = workEnd_str.split(":");
            Calendar workStartCalendar = Calendar.getInstance();
            Calendar workEndCalendar = Calendar.getInstance();
            workStartCalendar.setTime(startCalendar.getTime());
            workEndCalendar.setTime(endCalendar.getTime());

            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();
            Calendar startWorkTime = Calendar.getInstance();
            Calendar endWorkTime = Calendar.getInstance();

            startTime.set(Calendar.HOUR_OF_DAY, startCalendar.get(Calendar.HOUR_OF_DAY));
            startTime.set(Calendar.MINUTE, startCalendar.get(Calendar.MINUTE));
            endTime.set(Calendar.HOUR_OF_DAY, endCalendar.get(Calendar.HOUR_OF_DAY));
            endTime.set(Calendar.MINUTE, endCalendar.get(Calendar.MINUTE));

            startWorkTime.set(Calendar.HOUR_OF_DAY, workStartCalendar.get(Calendar.HOUR_OF_DAY));
            startWorkTime.set(Calendar.MINUTE, workStartCalendar.get(Calendar.MINUTE));
            endWorkTime.set(Calendar.HOUR_OF_DAY, workEndCalendar.get(Calendar.HOUR_OF_DAY));
            endWorkTime.set(Calendar.MINUTE, workEndCalendar.get(Calendar.MINUTE));


            if (startTime.getTime().getTime() == endTime.getTime().getTime() || (startWorkTime.getTime().getTime() < startTime.getTime().getTime() && endWorkTime.getTime().getTime() < startTime.getTime().getTime())) {
                Toast.makeText(context, "Exception", Toast.LENGTH_LONG).show();
                return false;
            }
            startCalendar.setTime(startCalendar.getTime().getTime() < workStartCalendar.getTime().getTime() ? workStartCalendar.getTime() : startCalendar.getTime());
            endCalendar.setTime(endCalendar.getTime().getTime() > workEndCalendar.getTime().getTime() ? workEndCalendar.getTime() : endCalendar.getTime());
        }
        return res;


    }

    private String fomatTimeUnit(int unit) {
        return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
    }

    private void initArrayList() {
        if (year == null) year = new ArrayList<>();
        if (month == null) month = new ArrayList<>();
        if (day == null) day = new ArrayList<>();
        year.clear();
        month.clear();
        day.clear();
    }


    private void addListener() {
        year_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.YEAR, Integer.parseInt(text));
                monthChange();


            }
        });
        month_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.DAY_OF_MONTH, 1);
                selectedCalender.set(Calendar.MONTH, Integer.parseInt(text) - 1);
                dayChange();


            }
        });
        day_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(text));
            }
        });
    }

    public void setDefaultData(String date) {
        Date d = DateUtil.parse(date, FORMAT_STR);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(d.getTime());
        int year = mCalendar.get(Calendar.YEAR);
        selectedCalender.set(Calendar.YEAR, year);
        year_pv.setSelected(String.valueOf(year));
        monthChange();
    }
    public void setDefaultToday(){
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        int year = mCalendar.get(Calendar.YEAR);
        selectedCalender.set(Calendar.YEAR, year);
        year_pv.setSelected(String.valueOf(year));
        monthChange();
    }


    private void loadComponent() {
        year_pv.setData(year);
        month_pv.setData(month);
        day_pv.setData(day);
        year_pv.setSelected(0);
        month_pv.setSelected(0);
        day_pv.setSelected(0);
        excuteScroll();
    }

    private void excuteScroll() {
        year_pv.setCanScroll(year.size() > 1);
        month_pv.setCanScroll(month.size() > 1);
        day_pv.setCanScroll(day.size() > 1);
    }

    private void monthChange() {
        month.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        if (selectedYear == startYear) {
            for (int i = startMonth; i <= MAXMONTH; i++) {
                month.add(fomatTimeUnit(i));
            }
        } else if (selectedYear == endYear) {
            for (int i = 1; i <= endMonth; i++) {
                month.add(fomatTimeUnit(i));
            }
        } else {
            for (int i = 1; i <= MAXMONTH; i++) {
                month.add(fomatTimeUnit(i));
            }
        }
        selectedCalender.set(Calendar.MONTH, Integer.parseInt(month.get(0)) - 1);
        month_pv.setData(month);
        month_pv.setSelected(0);
        excuteAnimator(ANIMATORDELAY, month_pv);
        month_pv.postDelayed(new Runnable() {
            @Override
            public void run() {
                dayChange();
            }
        }, CHANGEDELAY);

    }

    private void dayChange() {
        day.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
        if (selectedYear == startYear && selectedMonth == startMonth) {
            for (int i = startDay; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth) {
            for (int i = 1; i <= endDay; i++) {
                day.add(fomatTimeUnit(i));
            }
        } else {
            for (int i = 1; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i));
            }
        }
        selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day.get(0)));
        day_pv.setData(day);
        day_pv.setSelected(0);
        excuteAnimator(ANIMATORDELAY, day_pv);

    }


    private void excuteAnimator(long ANIMATORDELAY, View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(ANIMATORDELAY).start();
    }

}
