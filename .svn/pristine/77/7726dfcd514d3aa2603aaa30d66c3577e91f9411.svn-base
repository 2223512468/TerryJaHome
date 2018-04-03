package com.jajahome.feature.house;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.house.adapter.HeadAdapter;
import com.jajahome.feature.house.adapter.SortCityAdapter;
import com.jajahome.model.AddFavoriteModel;
import com.jajahome.model.HouseModel;
import com.jajahome.model.SortCityModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.widget.AutoListView;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${Terry} on 2017/10/18.
 */
public class SelectCityAct extends BaseActivity implements MultiRecycleView.OnMutilRecyclerViewListener, HeadAdapter.OnItemLongClickListener {

    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.message)
    ImageButton message;
    @BindView(R.id.recyclerView)
    MultiRecycleView recycleView;
    @BindView(R.id.listview)
    LetterListView listView;
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;


    AutoListView headListView;


    private SortCityAdapter sortCityAdapter;
    private HashMap<String, Integer> firstChar = new HashMap<>();
    private String[] sections;// 存放存在的汉语拼音首字母
    private OverlayThread overlayThread;
    private HeadAdapter mHeadAdapter;

    @Override
    protected int getViewId() {
        return R.layout.act_select_city;
    }

    @Override
    protected void initEvent() {

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView2.setText("选择城市");
        message.setVisibility(View.GONE);
        initViewController(recycleView);
        showLoading(true, "");
        sortCityAdapter = new SortCityAdapter();
        recycleView.setAdapter(sortCityAdapter);
        recycleView.addHeaderView(getHeader());
        recycleView.setOnMutilRecyclerViewListener(this);
        listView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        handler = new Handler();
        overlayThread = new OverlayThread();
        initOverlay();
        getSortCity();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCollected();
    }

    private void getCollected() {
        BaseReq req = new BaseReq();
        req.setCmd(Constant.GETFAVOURTBUILDING);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        ApiImp.get().getCollected(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HouseModel>() {
                    @Override
                    public void onCompleted() {
                        recycleView.stopRefresh();
                        recycleView.stopLoadingMore();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HouseModel model) {
                        List<HouseModel.DataBean.HouseBeanX> houseList = model.getData().getHouse();
                        mHeadAdapter = new HeadAdapter(mContext, houseList);
                        headListView.setAdapter(mHeadAdapter);
                        mHeadAdapter.setListener(SelectCityAct.this);

                    }
                });
    }

    private void getSortCity() {
        BaseReq req = new BaseReq();
        req.setCmd(Constant.SORTCITYLIST);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        ApiImp.get().sortCity(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SortCityModel>() {
                    @Override
                    public void onCompleted() {
                        recycleView.stopRefresh();
                        recycleView.stopLoadingMore();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SortCityModel model) {

                        int size = model.getData().getSort_citys().size();
                        sections = new String[size];
                        showLoading(false, "");
                        for (int i = 0; i < size; i++) {
                            // 当前汉语拼音首字母
                            // getAlpha(list.get(i));
                            String currentStr = model.getData().getSort_citys().get(i).getFirstChar();
                            // 上一个汉语拼音首字母，如果不存在为“ ”
                            String previewStr = (i - 1) >= 0 ? model.getData().getSort_citys().get(i - 1)
                                    .getFirstChar() : " ";
                            if (!previewStr.equals(currentStr)) {
                                String name = model.getData().getSort_citys().get(i).getFirstChar();
                                firstChar.put(name, i);
                                sections[i] = name;
                            }
                        }
                        sortCityAdapter.addItems(model.getData().getSort_citys());
                    }
                });
    }

    @Override
    public void onRefresh() {
        getCollected();
    }

    @Override
    public void onLoadMore() {
        recycleView.stopLoadingMore();
    }

    @Override
    public void delete(String id) {
        delFav(id);
    }


    private class LetterListViewListener implements LetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            if (firstChar != null && firstChar.get(s) != null) {
                int position = firstChar.get(s);
                LinearLayoutManager manager = (LinearLayoutManager) recycleView.getRecyclerView().getLayoutManager();
                manager.scrollToPositionWithOffset(position + 1, 0);
                overlay.setText(sections[position]);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                handler.postDelayed(overlayThread, 1000);
            }
        }
    }

    private TextView overlay;
    private Handler handler;

    // 初始化汉语拼音首字母弹出提示框
    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.act_house_circle_item, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    // 设置overlay不可见
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    private View getHeader() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.act_house_head, null);
        headListView = (AutoListView) rootView.findViewById(R.id.headListView);
        return rootView;
    }

    private void setListHeight(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < 5; i++) {   //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    private void delFav(String id) {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.DEL_FAVORITE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(Integer.valueOf(id));
        contentBean.setType(3);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("取消收藏中");
        mSubscription = ApiImp.get().delFavorite(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AddFavoriteModel>() {
            @Override
            public void onCompleted() {
                dissmisProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                dissmisProgressDialog();
                e.printStackTrace();
            }

            @Override
            public void onNext(AddFavoriteModel model) {
                showToast(R.string.del_collect_success);
                getCollected();
            }
        });
    }

}
