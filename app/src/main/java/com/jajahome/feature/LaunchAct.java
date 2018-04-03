package com.jajahome.feature;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.constant.Constant;
import com.jajahome.feature.home.MainAty;
import com.jajahome.model.LaunchModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.util.GuildUtil;
import com.jajahome.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 启动页
 */
public class LaunchAct extends Activity {


    private LaunchModel launchModel;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public static Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_launch);
        initEvent();

    }

    protected void initEvent() {

        if (!GuildUtil.getIsNotFirst(this)) {
            //第一次打开应用 跳入 引导页
            Intent intent = new Intent(LaunchAct.this, GuildAct.class);
            startActivity(intent);
            finish();
        } else {
            // getIntentImage();
            startTimer();
        }
    }


    private void startTimer() {
        timer.schedule(tast, 1000);
    }

    //定时器
    Timer timer = new Timer();
    TimerTask tast = new TimerTask() {
        @Override
        public void run() {
            //进入首页
           /* if (launchModel != null && bitmap != null) {
                Intent intent = new Intent(LaunchAct.this, AnimAct.class);
                startActivity(intent);
                finish();
            } else {
                jumpToAct();
            }*/
            Intent intent = new Intent(LaunchAct.this, AnimAct.class);
            startActivity(intent);
            finish();
        }
    };

    private void getIntentImage() {
        final BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.LAUNCHIMAGE);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        ApiImp.get().launch_Image(requestBody).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<LaunchModel>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(LaunchModel model) {
                launchModel = model;
                if (model.getStatus() == 0) {
                    if (!StringUtil.isEmpty(launchModel.getData().getLaunchImage())) {
                        imageLoader.loadImage(launchModel.getData().getLaunchImage(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                bitmap = loadedImage;

                            }
                        });
                    }

                }
            }
        });
    }

    private void jumpToAct() {
        //进入首页
        Intent intent = new Intent(LaunchAct.this, MainAty.class);
        startActivity(intent);
        finish();
    }
}