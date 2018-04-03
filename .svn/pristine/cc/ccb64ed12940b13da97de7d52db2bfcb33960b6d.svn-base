package com.jajahome.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jajahome.R;
import com.jajahome.feature.home.MainAty;
import com.jajahome.util.GuildUtil;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 被用于 ImagePagerAdapter 的图片显示
 */
public class GuildPageFragment extends Fragment {

    public static final String ID = "ID";

    /**
     * 图片id
     */
    private int id;

    public static GuildPageFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(ID, id);
        GuildPageFragment imagePageFragment = new GuildPageFragment();
        imagePageFragment.setArguments(bundle);
        return imagePageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        id = bundle.getInt(ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_guild, container, false);
        ImageView imgBanner = (ImageView) view.findViewById(R.id.item_img);
        imgBanner.setImageResource(id);
        if (id == R.mipmap.guide_image_5) {
            Log.e("time","1111111111");
            startTimer();
        }
        return view;
    }

    private void startTimer() {
        GuildUtil.setIsNotFirst(getActivity());
        if (timer == null) {
            timer = new Timer();
            tast = new TimerTask() {
                @Override
                public void run() {
                    //设置该应用已经启动过
                    Intent intent = new Intent(getActivity(), MainAty.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            };
            timer.schedule(tast, 1500);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
            tast.cancel();
            tast = null;
        }
    }

    Timer timer;
    TimerTask tast;
}
