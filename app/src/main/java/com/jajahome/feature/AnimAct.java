package com.jajahome.feature;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.feature.home.MainAty;
import com.jajahome.model.LaunchModel;
import com.jajahome.util.CountDownTimer;
import com.jajahome.util.cacheutils.CacheUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/4/13.
 */
public class AnimAct extends BaseActivity {

    @BindView(R.id.btn_skip)
    Button btn;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.fl)
    FrameLayout fl;
    private CountDownTimer mc;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Bitmap bitmap;

    @Override
    protected int getViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.act_anim_lunch;
    }

    @Override
    protected void initEvent() {


       /* if (LaunchAct.bitmap != null) {
            img.setImageBitmap(LaunchAct.bitmap);
        }*/

        String jsonModel = CacheUtils.readData(GuildAct.dirName);
        LaunchModel model = new Gson().fromJson(jsonModel, LaunchModel.class);
        if (model == null) {
            return;
        }
        Picasso.with(mContext).load(model.getData().getLaunchImage()).into(img);
        /*if (!StringUtil.isEmpty(model.getData().getLaunchImage())) {
            imageLoader.loadImage(model.getData().getLaunchImage(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    fl.setVisibility(View.VISIBLE);
                    img.setImageBitmap(loadedImage);
                }
            });
        }*/

        mc = new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                btn.setText(millisUntilFinished / 1000 + "秒跳转");
                btn.setTextSize(12);
            }

            public void onFinish() {
                Intent intent = new Intent(AnimAct.this, MainAty.class);
                startActivity(intent);
                finish();
            }
        }.start();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mc.cancel();
                Intent intent = new Intent(AnimAct.this, MainAty.class);
                startActivity(intent);
                finish();
            }
        });
    }

}



