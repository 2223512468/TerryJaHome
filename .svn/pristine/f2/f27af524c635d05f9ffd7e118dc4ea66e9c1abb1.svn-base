package com.jajahome.feature.user.activity;

import android.app.Activity;
import android.os.Bundle;

import com.jajahome.util.signutils.CalendarViewAdapter;
import com.jajahome.util.signutils.HuaCalendarView;

/**
 * Created by Administrator on 2017/6/17.
 */
public class SignAct extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new HuaCalendarView(this, mClickSignInCallBack));


    }

    private CalendarViewAdapter.onClickSignInCallBack mClickSignInCallBack = new CalendarViewAdapter.onClickSignInCallBack() {

        @Override
        public void onTimeOut(String msg) {
        }

        @Override
        public void onSucess() {
        }

        @Override
        public void onFail(String error) {
        }
    };
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
