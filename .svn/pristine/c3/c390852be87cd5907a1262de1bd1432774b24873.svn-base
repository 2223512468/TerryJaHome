package com.jajahome.feature.house;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.house.adapter.SearchBuildAdapter;
import com.jajahome.feature.house.adapter.SortBuildAdapter;
import com.jajahome.model.BuildListModel;
import com.jajahome.model.SearchBuildListModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BuildingReq;
import com.jajahome.network.SortCityReq;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ${Terry} on 2017/10/19.
 */
public class SelectBuildingListAct extends BaseActivity implements View.OnClickListener, View.OnLayoutChangeListener {

    public static final String CODE = "CODE";
    private String id;
    private String cityName;
    private String searchText = ""; //搜索文字


    @BindView(R.id.ibtn_back)
    ImageButton ibtnBtn;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.ic_main_search)
    ImageView searchView;
    @BindView(R.id.buildName)
    EditText buildName;
    @BindView(R.id.recyclerView)
    MultiRecycleView recycleView;
    @BindView(R.id.listview)
    LetterListView listView;
    @BindView(R.id.recyclerSearchView)
    MultiRecycleView searchListView;
    @BindView(R.id.view_root)
    LinearLayout rootLayout;

    private SortBuildAdapter buildAdapter;
    private SearchBuildAdapter searchBuildAdapter;
    private HashMap<String, Integer> firstChar = new HashMap<>();
    private String[] sections;// 存放存在的汉语拼音首字母
    private OverlayThread overlayThread;


    @Override
    protected int getViewId() {
        return R.layout.act_city_list;
    }

    @Override
    protected void initEvent() {
        initViewController(recycleView);
        recycleView.setRefreshEnable(false);
        recycleView.setLoadMoreable(false);
        showLoading(true, "");
        ibtnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keyPan == -1) {
                    finish();
                }
            }
        });
        textView2.setText("选择楼盘");
        id = getIntent().getStringExtra(CODE);
        cityName = getIntent().getStringExtra(Constant.LOCATION);
        buildAdapter = new SortBuildAdapter();
        recycleView.setAdapter(buildAdapter);
        getBuildingList();
        setListener();
        listView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        handler = new Handler();
        overlayThread = new OverlayThread();
        initOverlay();

        buildName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchText = s.toString();
                if (StringUtil.isEmpty(searchText)) {
                    listView.setVisibility(View.VISIBLE);
                    recycleView.setVisibility(View.VISIBLE);
                    searchListView.setVisibility(View.GONE);
                    getBuildingList();
                }
            }
        });

        buildName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //点击搜索按钮
                    searchText = buildName.getText().toString();
                    if (!StringUtil.isEmpty(searchText)) {
                        searchBuilding();
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    private void getBuildingList() {
        SortCityReq req = new SortCityReq();
        SortCityReq.ContentBean bean = new SortCityReq.ContentBean();
        bean.setCity(Double.parseDouble(id));
        req.setCmd(Constant.SORTBUILDINGLIST);
        req.setContent(bean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        ApiImp.get().builingList(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BuildListModel>() {
                    @Override
                    public void onCompleted() {
                        recycleView.stopRefresh();
                        recycleView.stopLoadingMore();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BuildListModel model) {

                        showLoading(false, "");

                        int size = model.getData().getSort_buildings().size();
                        if (size == 0) {
                            showEmpty(true, "当前城市未设置相应楼盘", null);
                            return;
                        }

                        sections = new String[size];
                        for (int i = 0; i < size; i++) {
                            // 当前汉语拼音首字母
                            // getAlpha(list.get(i));
                            String currentStr = model.getData().getSort_buildings().get(i).getFirstChar();
                            // 上一个汉语拼音首字母，如果不存在为“ ”
                            String previewStr = (i - 1) >= 0 ? model.getData().getSort_buildings().get(i - 1)
                                    .getFirstChar() : " ";
                            if (!previewStr.equals(currentStr)) {
                                String name = model.getData().getSort_buildings().get(i).getFirstChar();
                                firstChar.put(name, i);
                                sections[i] = name;
                            }
                        }
                        buildAdapter.addItems(model.getData().getSort_buildings());
                    }
                });
    }

    private void searchBuilding() {
        String name = buildName.getText().toString();
        if (StringUtil.isEmpty(name)) {
            Toast.makeText(mContext, "请输入要搜索的楼盘", Toast.LENGTH_SHORT).show();
            return;
        }

        BuildingReq req = new BuildingReq();
        req.setCmd(Constant.SEARCHBUILDING);
        BuildingReq.ContentBean bean = new BuildingReq.ContentBean();
        bean.setSearch_name(name);
        bean.setCity(Double.parseDouble(id));
        req.setContent(bean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        ApiImp.get().searchBuilding(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchBuildListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SearchBuildListModel model) {
                        recycleView.setVisibility(View.GONE);
                        listView.setVisibility(View.GONE);
                        searchListView.setVisibility(View.VISIBLE);

                        int size = model.getData().getBuildings().size();
                        if (size == 0) {
                            Toast.makeText(mContext, "未查询到当前城市的楼盘", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        searchBuildAdapter = new SearchBuildAdapter();
                        searchListView.setAdapter(searchBuildAdapter);
                        searchBuildAdapter.addItems(model.getData().getBuildings());

                        searchBuildAdapter.setListener(new SearchBuildAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(String name, String id) {
                                Intent intent = new Intent(mContext, HouseListAct.class);
                                intent.putExtra(HouseListAct.ID, id);
                                intent.putExtra(HouseListAct.TITLE, name);
                                startActivity(intent);
                            }
                        });
                    }
                });
    }

    private void setListener() {
        searchView.setOnClickListener(this);
        rootLayout.addOnLayoutChangeListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_main_search:
                searchBuilding();
                break;
        }
    }

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

    private TextView overlay;
    private Handler handler;
    private int keyHeight = 300;
    private int keyPan = -1;

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            keyPan = 1;
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            keyPan = 0;
            timer();
        }
    }

    private void timer() {
        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                keyPan = -1;
            }
        });
    }


    // 设置overlay不可见
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    private class LetterListViewListener implements LetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            if (firstChar != null && firstChar.get(s) != null) {
                int position = firstChar.get(s);
                LinearLayoutManager manager = (LinearLayoutManager) recycleView.getRecyclerView().getLayoutManager();
                manager.scrollToPositionWithOffset(position, 0);
                overlay.setText(sections[position]);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                handler.postDelayed(overlayThread, 1000);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

}
