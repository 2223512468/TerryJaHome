package com.jajahome.feature.diy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.constant.Constant;
import com.jajahome.diyview.ImageDiyFrameLayout;
import com.jajahome.feature.diy.DiySelectItemAct;
import com.jajahome.feature.set.fragment.adapter.SetItemFabricAdapter;
import com.jajahome.feature.set.fragment.adapter.SetItemMaterialAdapter;
import com.jajahome.feature.set.fragment.adapter.SetItemPreviewAdapter;
import com.jajahome.feature.user.activity.LoginAct;
import com.jajahome.model.SetItemModel;
import com.jajahome.model.SetModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.HttpCode;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.util.LoginUtil;
import com.jajahome.widget.HorizontalRecycleView;
import com.jajahome.widget.horizontalrecycle.AutoMoveRecycleView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 套装详情 单品列表
 */
public class DiyExpandableAdapter extends BaseExpandableListAdapter implements ImageDiyFrameLayout.OnAllImageLoadedListener {
    private final int REQUEST_CODE = 0X33;
    private final String BRACKETS_RIGHT = ")";
    private final String BRACKETS_LEFT = "(";
    private List<SetModel.DataBean.SetBean.ListBean> mListGroup;
    private LayoutInflater inflater;
    private HashMap<Integer, SetItemModel> mapChild = new HashMap<>();
    /**
     * 存放套装单品面料adapter
     */
    private HashMap<Integer, SetItemFabricAdapter> mFabricAdapters = new HashMap<>();
    /**
     * 存放套装单品面料adapter
     */
    private HashMap<Integer, SetItemMaterialAdapter> mMaterialAdapters = new HashMap<>();
    /**
     * 存放透视图adapter
     */
    private HashMap<Integer, SetItemPreviewAdapter> mPreViewAdapters = new HashMap<>();

    private Context mContext;
    private Subscription mSubscription;
    private ImageDiyFrameLayout imgFrameLayout;

    /**
     * ** 0 所有模板 ** ** 1 我的列表 **
     */
    private int type;


    private OnSetItemChangeListener onSetItemChangeListener;

    public DiyExpandableAdapter(Context context, List<SetModel.DataBean.SetBean.ListBean> mListGroup, int type, ImageDiyFrameLayout imgFrameLayout) {
        this.mListGroup = new ArrayList<>();
        for (SetModel.DataBean.SetBean.ListBean bean : mListGroup) {
            this.mListGroup.add(bean);
        }
        this.mContext = context;
        this.type = type;
        this.imgFrameLayout = imgFrameLayout;
        imgFrameLayout.setmOnAllImageLoadedListener(this);
        inflater = LayoutInflater.from(mContext);

    }

    private void getPosition(String itemId, int j) {
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            if (mList.get(i).getId().equals(itemId)) {
                IndexMap.put(j, i);
            }
        }
    }

    public void setOnSetItemChangeListener(OnSetItemChangeListener onSetItemChangeListener) {
        this.onSetItemChangeListener = onSetItemChangeListener;
    }


    @Override
    public int getGroupCount() {
        return mListGroup == null ? 0 : mListGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (mListGroup == null) return null;
        return mListGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public HashMap<Integer, Boolean> map = new HashMap<>();
    public HashMap<Integer, View> viewHap = new HashMap<>();
    public HashMap<Integer, View> noViewHap = new HashMap<>();
    public HashMap<Integer, Integer> IndexMap = new HashMap<>();

    /**
     * 传递map集合，在大图套装是否显示和隐藏
     */
    public HashMap<Integer, Boolean> isBigMap = new HashMap<>();

    /**
     * expandalbelist header部分
     */
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView title;
        ImageView iv, ivMore;
        ImageView noEyes, eyes;

        SetModel.DataBean.SetBean.ListBean bean = mListGroup.get(groupPosition);
       /* int imageIndex = getPosition(bean.getId());
        IndexMap.put(groupPosition, imageIndex);*/

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_header, parent, false);
        }
        title = (TextView) convertView.findViewById(R.id.header_text);
        iv = (ImageView) convertView.findViewById(R.id.imageView);
        ivMore = (ImageView) convertView.findViewById(R.id.imageView_more);
        noEyes = (ImageView) convertView.findViewById(R.id.image_noeyes);
        eyes = (ImageView) convertView.findViewById(R.id.image_eyes);
        if (!clickMap.isEmpty()) {
            if (clickMap.containsKey(groupPosition) && clickMap.get(groupPosition) == true) {
                ivMore.setVisibility(View.VISIBLE);
                iv.setVisibility(View.GONE);
            } else {
                ivMore.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
            }
        }

        viewHap.put(groupPosition, eyes);
        noViewHap.put(groupPosition, noEyes);

        if (viewHap.containsKey(groupPosition) && noViewHap.containsKey(groupPosition)) {
            if (map.containsKey(groupPosition)) {
                if (map.get(groupPosition) == true) {
                    noViewHap.get(groupPosition).setVisibility(View.GONE);
                    viewHap.get(groupPosition).setVisibility(View.VISIBLE);
                } else {
                    noViewHap.get(groupPosition).setVisibility(View.VISIBLE);
                    viewHap.get(groupPosition).setVisibility(View.GONE);
                }
            } else {
                noViewHap.get(groupPosition).setVisibility(View.VISIBLE);
                viewHap.get(groupPosition).setVisibility(View.GONE);
            }

            viewHap.get(groupPosition).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noViewHap.get(groupPosition).setVisibility(View.VISIBLE);
                    viewHap.get(groupPosition).setVisibility(View.GONE);
                    map.put(groupPosition, false);

                    if (IndexMap.containsKey(groupPosition)) {

                        imageViews.get(IndexMap.get(groupPosition)).setVisibility(View.VISIBLE);

                        isBigMap.put(IndexMap.get(groupPosition), true);
                    }
                }
            });

            noViewHap.get(groupPosition).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noViewHap.get(groupPosition).setVisibility(View.GONE);
                    viewHap.get(groupPosition).setVisibility(View.VISIBLE);
                    map.put(groupPosition, true);
                    if (IndexMap.containsKey(groupPosition)) {
                        if (imageViews.size() > IndexMap.get(groupPosition)) {
                            imageViews.get(IndexMap.get(groupPosition)).setVisibility(View.INVISIBLE);
                            isBigMap.put(IndexMap.get(groupPosition), false);
                        }
                    }
                }
            });
        }

        title.setText(getItemName(bean));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final SetItemModel model = mapChild.get(groupPosition);
        final SetModel.DataBean.SetBean.ListBean bean = mListGroup.get(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.diy_view_content, parent, false);
        }

        if (null == model) {
            getList(groupPosition, bean.getId());
            return convertView;
        } else {
            //面料相关view
            convertView.findViewById(R.id.diy_item_add_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //去选择可更换单品
                    onAddClicked(groupPosition);
                }
            });
            final LinearLayout llFabric = (LinearLayout) convertView.findViewById(R.id.view_fabric);
            final TextView tvFabric = (TextView) convertView.findViewById(R.id.tv_fabric);
            final HorizontalRecycleView fabricRecy = (HorizontalRecycleView) convertView.findViewById(R.id.fabric_img_recycle);
            //材质相关
            final LinearLayout llMaterial = (LinearLayout) convertView.findViewById(R.id.view_material);
            final TextView tvMaterial = (TextView) convertView.findViewById(R.id.tv_material);
            final HorizontalRecycleView materialRecy = (HorizontalRecycleView) convertView.findViewById(R.id.material_img_recycle);
            AutoMoveRecycleView topRecy = (AutoMoveRecycleView) convertView.findViewById(R.id.top_img_recycle);
            if (mPreViewAdapters.get(groupPosition) == null) {
                final SetItemPreviewAdapter mSetItemPreviewAdapter = new SetItemPreviewAdapter();
                mSetItemPreviewAdapter.setmAutoMoveRecycleView(topRecy);
                topRecy.setAdapter(mSetItemPreviewAdapter);
                mSetItemPreviewAdapter.setItemImgs(mapChild.get(groupPosition), mListGroup.get(groupPosition).getItem_info().getId());
                mSetItemPreviewAdapter.setListener(new SetItemPreviewAdapter.OnSetItemChangeListener() {
                    @Override
                    public void onSetItemChangeed(int index) {
                        SetItemModel setItemModel = mapChild.get(groupPosition);
                        setItemModel.getData().setIndex(index);
                        SetItemModel.DataBean.ListBean.ItemBean bean = setItemModel.getData().getList().get(index).getItem();
                        //改单品没有面料相关信息
                        if (bean.getFabric() == null || bean.getFabric().size() == 0) {
                            llFabric.setVisibility(View.GONE);
                            fabricRecy.setVisibility(View.GONE);
                        } else {
                            llFabric.setVisibility(View.VISIBLE);
                            fabricRecy.setVisibility(View.VISIBLE);
                            //显示关于面料的名称
                            if (mFabricAdapters.get(groupPosition) == null) {
                                SetItemFabricAdapter fabricAdapter = new SetItemFabricAdapter();
                                fabricAdapter.setItemImgs(model);
                                fabricAdapter.setListener(new SetItemFabricAdapter.OnSetItemFabricListener() {
                                    @Override
                                    public void onFabricChanged(int index, String fabricId, String name) {
                                        tvFabric.setText(name);
                                        if (mPreViewAdapters.get(groupPosition) != null)
                                            mPreViewAdapters.get(groupPosition).changePreviewImageByFabric(fabricId);
                                    }
                                });
                                fabricRecy.setAdapter(fabricAdapter);
                                mFabricAdapters.put(groupPosition, fabricAdapter);
                                tvFabric.setText(fabricAdapter.getCurrentItemName());
                            } else {
                                mFabricAdapters.get(groupPosition).setItemImgs(mapChild.get(groupPosition));
                                tvFabric.setText(mFabricAdapters.get(groupPosition).getCurrentItemName());
                                fabricRecy.setAdapter(mFabricAdapters.get(groupPosition));
                            }
                        }

                        //关于材质
                        if (bean.getMaterial() == null || bean.getMaterial().size() == 0) {
                            llMaterial.setVisibility(View.GONE);
                            materialRecy.setVisibility(View.GONE);
                        } else {
                            llMaterial.setVisibility(View.VISIBLE);
                            materialRecy.setVisibility(View.VISIBLE);
                            //显示已选择材质的名称
                            if (mMaterialAdapters.get(groupPosition) == null) {
                                SetItemMaterialAdapter materialAdapter = new SetItemMaterialAdapter();
                                materialAdapter.setItemImgs(model);
                                materialAdapter.setListener(new SetItemMaterialAdapter.OnSetItemMaterialListener() {
                                    @Override
                                    public void onMaterialChanged(int index, String materialId, String name) {
                                        tvFabric.setText(name);
                                        if (mPreViewAdapters.get(groupPosition) != null)
                                            mPreViewAdapters.get(groupPosition).changePreviewImageByMaterial(materialId);
                                    }
                                });
                                materialRecy.setAdapter(materialAdapter);
                                mMaterialAdapters.put(groupPosition, materialAdapter);
                                tvMaterial.setText(materialAdapter.getCurrentItemName());
                            } else {
                                mMaterialAdapters.get(groupPosition).setItemImgs(mapChild.get(groupPosition));
                                tvMaterial.setText(mMaterialAdapters.get(groupPosition).getCurrentItemName());
                                materialRecy.setAdapter(mMaterialAdapters.get(groupPosition));
                            }
                        }
                    }

                    @Override
                    public void onSetImgageChanged(String itemId, SetItemModel.DataBean.ListBean bean, String url, String id) {
                        if (onSetItemChangeListener != null) {
                            onSetItemChangeListener.onSetItemChanged(itemId, bean, url, groupPosition, id);
                        }
                    }
                });
                mPreViewAdapters.put(groupPosition, mSetItemPreviewAdapter);
            } else {
                topRecy.setAdapter(mPreViewAdapters.get(groupPosition));
            }
            SetItemModel.DataBean data = model.getData();
            if (data.getList() != null && data.getList().size() > 0) {
                SetItemModel.DataBean.ListBean itemBean = data.getList().get(data.getIndex());
                SetItemModel.DataBean.ListBean.ItemBean item = itemBean.getItem();
                //关于面料
                if (item.getFabric() == null || item.getFabric().size() == 0) {
                    llFabric.setVisibility(View.GONE);
                    fabricRecy.setVisibility(View.GONE);
                } else {
                    llFabric.setVisibility(View.VISIBLE);
                    fabricRecy.setVisibility(View.VISIBLE);
                    //显示关于面料的名称
                    if (mFabricAdapters.get(groupPosition) == null) {
                        SetItemFabricAdapter fabricAdapter = new SetItemFabricAdapter();
                        fabricAdapter.setItemImgs(model);
                        fabricAdapter.setListener(new SetItemFabricAdapter.OnSetItemFabricListener() {
                            @Override
                            public void onFabricChanged(int index, String fabricId, String name) {
                                tvFabric.setText(name);
                                if (mPreViewAdapters.get(groupPosition) != null)
                                    mPreViewAdapters.get(groupPosition).changePreviewImageByFabric(fabricId);
                            }
                        });
                        fabricRecy.setAdapter(fabricAdapter);
                        mFabricAdapters.put(groupPosition, fabricAdapter);
                        tvFabric.setText(fabricAdapter.getCurrentItemName());
                    } else {
                        fabricRecy.setAdapter(mFabricAdapters.get(groupPosition));
                    }
                }
                //关于材质
                if (item.getMaterial() == null || item.getMaterial().size() == 0) {
                    llMaterial.setVisibility(View.GONE);
                    materialRecy.setVisibility(View.GONE);
                } else {
                    llMaterial.setVisibility(View.VISIBLE);
                    materialRecy.setVisibility(View.VISIBLE);
                    //显示已选择材质的名称
                    if (mMaterialAdapters.get(groupPosition) == null) {
                        SetItemMaterialAdapter materialAdapter = new SetItemMaterialAdapter();
                        materialAdapter.setItemImgs(model);
                        materialAdapter.setListener(new SetItemMaterialAdapter.OnSetItemMaterialListener() {
                            @Override
                            public void onMaterialChanged(int index, String materialId, String name) {
                                tvFabric.setText(name);
                                if (mPreViewAdapters.get(groupPosition) != null)
                                    mPreViewAdapters.get(groupPosition).changePreviewImageByMaterial(materialId);
                            }
                        });
                        materialRecy.setAdapter(materialAdapter);
                        mMaterialAdapters.put(groupPosition, materialAdapter);
                        tvMaterial.setText(materialAdapter.getCurrentItemName());
                    } else {
                        materialRecy.setAdapter(mMaterialAdapters.get(groupPosition));
                    }
                }
            }
        }
        return convertView;
    }

    private String getItemName(SetModel.DataBean.SetBean.ListBean bean) {
        StringBuilder sb = new StringBuilder();
        sb.append(bean.getName()).append(BRACKETS_LEFT).append(bean.getItem_size()).append(BRACKETS_RIGHT);
        return sb.toString();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private boolean isLogin = false;

    private void getList(final int position, final String id) {
        if (!LoginUtil.isLogin(mContext) && isLogin == false) {
            Intent intent = new Intent(mContext, LoginAct.class);
            mContext.startActivity(intent);
            isLogin = true;
            Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage.ContentBean contentBean = new ReqPage.ContentBean();
        contentBean.setPagination(paginationBean);
        contentBean.setId(id);
        contentBean.setId_type(String.valueOf(type));//
        contentBean.setType("0");//** 0 默认模板+收藏夹 **  ** 1 所有列表 **
        ReqPage reqPage = new ReqPage();
        reqPage.setContent(contentBean);
        reqPage.setCmd(Constant.DIY_ITEMLIST);
        final Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().getDiyItemList(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<SetItemModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(SetItemModel model) {
                      /*  if (model.getStatus() == HttpCode.INVALID_LOGIN_STATE) {
                            //isLogin=true;
                            Log.i("--DiyE", "执行网络");
                            ((BaseActivity) mContext).showLoginStatusDiyErrorDialog(0);
                        } else*/

                        if (model.getStatus() == HttpCode.SUCCESS) {
                            mapChild.put(position, model);
                            notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 添加单品按钮被点击
     *
     * @param p
     */
    private void onAddClicked(int p) {
        if (LoginUtil.isLogin(mContext)) {
            Intent intent = new Intent(mContext, DiySelectItemAct.class);
            intent.putExtra(DiySelectItemAct.ID, mListGroup.get(p).getId());
            intent.putExtra(DiySelectItemAct.TYPE, type);
            intent.putExtra(DiySelectItemAct.POS, p);
            intent.putExtra(DiySelectItemAct.DATA, (Serializable) mapChild.get(p).getData().getList());
            ((Activity) mContext).startActivityForResult(intent, REQUEST_CODE);

        } else {
            Intent intent = new Intent(mContext, LoginAct.class);
            mContext.startActivity(intent);

        }
    }

    /**
     * 选择完单品 显示在diy详情可选列表中
     *
     * @param pos
     * @param list
     */


    public void addSelectItems(int pos, ArrayList<SetItemModel.DataBean.ListBean> list) {
        list = getFiltedList(pos, list);
        mapChild.get(pos).getData().setList(list);
        List<SetItemModel.DataBean.ListBean.ItemBean.PreviewBean> listBeans = new ArrayList<>();
        for (SetItemModel.DataBean.ListBean bean : list) {
            SetItemModel.DataBean.ListBean.ItemBean itemBean = bean.getItem();
            List<SetItemModel.DataBean.ListBean.ItemBean.PreviewBean> previewBeanList = itemBean.getPreview();
            if (previewBeanList != null && previewBeanList.size() > 0) {
                listBeans.add(previewBeanList.get(0));
            }

        }
        mPreViewAdapters.get(pos).setmIndexList(0);
        mPreViewAdapters.get(pos).resetItems(listBeans);


    }

    /**
     * 过滤 已经选择的单品
     *
     * @param pos
     * @param list
     * @return
     */
    private ArrayList<SetItemModel.DataBean.ListBean> getFiltedList(int pos, ArrayList<SetItemModel.DataBean.ListBean> list) {
        List<SetItemModel.DataBean.ListBean> hadList = mapChild.get(pos).getData().getList();
        int index = mPreViewAdapters.get(pos).getIndex();
        if (hadList == null || hadList.size() == 0) return list;
        SetItemModel.DataBean.ListBean hadBean = hadList.get(index);
        ArrayList<SetItemModel.DataBean.ListBean> filtedList = new ArrayList<>();
        filtedList.add(hadBean);
        hadList.clear();
        for (SetItemModel.DataBean.ListBean bean : list) {
            if (!hadBean.getInfo().getId().equals(bean.getInfo().getId())) {
                filtedList.add(bean);
            }
        }
        return filtedList;
    }

    /**
     * 单击眼睛事件
     */


    /**
     * 判断单品是否在已选择单品已存在
     *
     * @param hadList ：已选择单品
     * @param itemId  单品id
     * @return true 存在 false不存在
     */
    private boolean isHadThisItem(List<SetItemModel.DataBean.ListBean> hadList, String itemId) {
        for (SetItemModel.DataBean.ListBean bean : hadList) {
            if (bean.getInfo().getId().equals(itemId)) return true;
        }
        return false;
    }

    private HashMap<Integer, Boolean> clickMap = new HashMap<>();

    public void pushMap(HashMap<Integer, Boolean> clickMap) {
        this.clickMap = clickMap;
    }

    @Override
    public void onAllImageLoaded() {

    }

    @Override
    public void vOrg(List<ImageView> imageViews, int selectIndex, List<SetModel.DataBean.SetBean.ListBean> mList) {
        this.imageViews = imageViews;
        this.mList = mList;
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            SetModel.DataBean.SetBean.ListBean bean = mListGroup.get(i);
            getPosition(bean.getId(), i);
        }
    }

    private List<ImageView> imageViews = new ArrayList<>();
    private List<SetModel.DataBean.SetBean.ListBean> mList = new ArrayList<>();

    /**
     * 单品选项变化监听
     */
    public interface OnSetItemChangeListener {
        /**
         * 单品样式 等变化
         *
         * @param itemId ：单品id
         * @param bean   :变化单品位置 透视图相关
         * @param url    ：透视图地址
         */
        void onSetItemChanged(String itemId, final SetItemModel.DataBean.ListBean bean, String url, int groupPosition, String id);
    }

    public void stopSubscription() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

}
