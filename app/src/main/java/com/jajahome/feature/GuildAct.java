package com.jajahome.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.GuildPagerAdapter;
import com.jajahome.constant.Constant;
import com.jajahome.feature.home.MainAty;
import com.jajahome.model.LaunchModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.util.GuildUtil;
import com.jajahome.util.cacheutils.CacheUtils;
import com.jajahome.widget.banner.SyCirclePageIndicator;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 引导页
 */
public class GuildAct extends AppCompatActivity {
    private ViewPager viewPager;  //viewpager
    private SyCirclePageIndicator indicator; //指示器
    public static String dirName = "GuildAct.txt";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_guild);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (SyCirclePageIndicator) findViewById(R.id.indicator);
        GuildPagerAdapter adapter = new GuildPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        findViewById(R.id.btn_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置该应用已经启动过
                GuildUtil.setIsNotFirst(GuildAct.this);
                Intent intent = new Intent(GuildAct.this, MainAty.class);
                startActivity(intent);
                finish();
            }
        });
        getIntentImage();
    }

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
                if (model.getStatus() == 0) {
                    String jsonModel = gson.toJson(model);
                    CacheUtils.writeData(jsonModel, dirName);
                   /* if (!StringUtil.isEmpty(model.getData().getLaunchImage())) {
                        imageLoader.loadImage(model.getData().getLaunchImage(), new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                bitmap = loadedImage;
                            }
                        });
                    }*/

                }
            }
        });
    }

}
