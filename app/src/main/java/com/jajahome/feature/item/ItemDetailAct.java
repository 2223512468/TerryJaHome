package com.jajahome.feature.item;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.application.JaJaHomeApplication;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.constant.PublishConstant;
import com.jajahome.event.EventMessage;
import com.jajahome.feature.H5Act;
import com.jajahome.feature.comment.CommentAct;
import com.jajahome.feature.comment.CommentInnerAdapter;
import com.jajahome.feature.comment.CommentRecyclerAdapter;
import com.jajahome.feature.item.adapter.FabricAdapter;
import com.jajahome.feature.item.adapter.ItemCommentAdapter;
import com.jajahome.feature.item.adapter.MaterialAdapter;
import com.jajahome.feature.item.adapter.PreviewImgAdapter;
import com.jajahome.feature.item.adapter.PreviewViewPagerAdapter;
import com.jajahome.feature.item.adapter.RelatedImgAdapter;
import com.jajahome.feature.item.adapter.RelatedSetImgAdapter;
import com.jajahome.feature.item.fragment.ItemDetailFrag;
import com.jajahome.feature.user.activity.LoginAct;
import com.jajahome.feature.user.activity.ShoppingCartAct;
import com.jajahome.feature.user.adapter.ItemCollectAdapter;
import com.jajahome.model.AddFavoriteModel;
import com.jajahome.model.CommentModel;
import com.jajahome.model.ItemModel;
import com.jajahome.model.LoginModle;
import com.jajahome.model.SimpleModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqBuy;
import com.jajahome.network.ReqPage;
import com.jajahome.pop.PopShare;
import com.jajahome.util.Base64Helper;
import com.jajahome.util.LocationSvc;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.PriceUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.util.TimeUtil;
import com.jajahome.widget.HorizontalRecycleView;
import com.jajahome.widget.SlideDetailsLayout;
import com.jajahome.widget.TextLinearLayout;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.squareup.picasso.Picasso;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 单品详情页
 */
public class ItemDetailAct extends BaseActivity implements View.OnClickListener,
        PreviewImgAdapter.OnPreviewImageChange, FabricAdapter.OnFabricChangeListener, MaterialAdapter.OnMaterialChangeListener, CommentRecyclerAdapter.OnCommentListener {
    public static final String ITEM_ID = "ITEM_ID"; //传递单品id
    public static final String ITEM_COLLECT = "ITEM_COLLECT";
    public static final String ITEM_DELETECOLLECT = "ITEM_DELETECOLLECT";
    public static final String ITEM_IMG_URL = "ITEM_IMG_URL";
    public static final String BACK_ACTION = "BACKACTION";
    @BindView(R.id.top_img_recycle)
    HorizontalRecycleView topImgRecycle;
    @BindView(R.id.top_view_pager)
    ViewPager topViewPager;
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_material)
    TextView tvMaterial;
    @BindView(R.id.tv_material_name)
    TextView tvMaterialName;

    @BindView(R.id.view_material)
    LinearLayout viewMaterial;
    @BindView(R.id.material_img_recycle)
    HorizontalRecycleView materialImgRecycle;
    @BindView(R.id.tv_related)
    TextView tvRelated;
    @BindView(R.id.view_related)
    LinearLayout viewRelated;
    @BindView(R.id.related_img_recycle)
    HorizontalRecycleView relatedImgRecycle;
    @BindView(R.id.text_ll)
    TextLinearLayout textLl;
    @BindView(R.id.view_goto_detail)
    RelativeLayout viewGotoDetail;

    @BindView(R.id.bottom_img_loc)
    ImageView bottomImgLoc;
    @BindView(R.id.bottom_img_collect)
    ImageView bottomImgCollect;
    @BindView(R.id.bottom_img_collected)
    ImageView bottomImgCollected;
    @BindView(R.id.bottom_img_share)
    ImageView bottomImgShare;
    @BindView(R.id.tv_fabric)
    TextView tvFabric;
    @BindView(R.id.view_fabric)
    LinearLayout viewFabric;
    //面料
    @BindView(R.id.fabric_img_recycle)
    HorizontalRecycleView fabricImgRecycle;
    @BindView(R.id.btn_buy_now)
    Button btnBuyNow;
    @BindView(R.id.btn_add_to_shopping_cart)
    Button btnAddToShoppingCart;
    private FabricAdapter mFabricAdapter;
    @BindView(R.id.tv_price_basic)
    TextView tvPriceBasic;
    @BindView(R.id.header_text)
    TextView headerText;
    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.scrollView2)
    ScrollView scrollView2;
    @BindView(R.id.view_bottom_line)
    View viewBottomLine;
    @BindView(R.id.view_bottom)
    LinearLayout viewBottom;
    @BindView(R.id.item_onclick)
    TextView onClick;

    @BindView(R.id.ll_pull_up)
    LinearLayout goonLayout;

    @BindView(R.id.sv_switch)
    SlideDetailsLayout sv_switch;

    @BindView(R.id.fl_content)
    FrameLayout fl_content;

    @BindView(R.id.view_top)
    RelativeLayout rl;


    @BindView(R.id.pic_text_ll)
    RelativeLayout pic_ll;

    @BindView(R.id.can_shu_ll)
    RelativeLayout canshu_ll;

    @BindView(R.id.shop_introduce_ll)
    RelativeLayout shop_ll;

    @BindView(R.id.indicator_pic)
    View pic_lv;

    @BindView(R.id.indicator_canshu)
    View canshu_lv;

    @BindView(R.id.indicator_shop)
    View shop_lv;

    @BindView(R.id.tv_total_comment)
    TextView tvTotalComment;

    @BindView(R.id.tv_empty)
    TextView tvCommentEmpty;
    @BindView(R.id.tv_item_introduce)
    TextView intrDesc;

    @BindView(R.id.related_img_recycle_set)
    HorizontalRecycleView relatedSetRecyclerView;
    @BindView(R.id.view_related_set)
    LinearLayout relatedSet;

    @BindView(R.id.top_item)
    ImageView topItem;
    private ItemCommentAdapter mItemCommentAdapter;//评论的adapter
    private String mId;//单品id
    private String Tag;
    private String index;
    private String imgUrl;
    private ItemModel mItemModel;//单品数据
    private PreviewViewPagerAdapter mPreviewViewPagerAdapter;
    PreviewImgAdapter mPreviewImgAdapter;//顶部小的预览图adapter

    private MaterialAdapter mMaterialAdapter; //面料adater
    private RelatedImgAdapter mRelatedAdapter;//材质的adapter
    private PopShare mPopShare;  //分享弹窗
    private IWXAPI mIwxapi;      //微信api
    private IWeiboShareAPI mIWeiboShareAPI;      //微博api
    private String cityName;
    private Fragment[] fragments;
    private ItemModel.DataBean.ItemBean bean;
    private RelatedSetImgAdapter setImgAdapter;

    public static String ItemDetailTag = "ItemDetailAct";


    @Override
    protected int getViewId() {
        return R.layout.act_item_detail;
    }

    @Override
    protected void initEvent() {
        textView2.setText(R.string.goods_item);
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mId = getIntent().getStringExtra(ITEM_ID);
        Tag = getIntent().getStringExtra(ITEM_COLLECT);
        index = getIntent().getStringExtra(ITEM_DELETECOLLECT);
        imgUrl = getIntent().getStringExtra(ITEM_IMG_URL);
        mPreviewImgAdapter = new PreviewImgAdapter();
        mPreviewImgAdapter.setmListener(this);
        mPreviewViewPagerAdapter = new PreviewViewPagerAdapter(mContext, null);
        topImgRecycle.setAdapter(mPreviewImgAdapter);
        bottomImgCollect.setVisibility(View.VISIBLE);
        getDetail();
        setListener();
        mIwxapi = WXAPIFactory.createWXAPI(mContext, Constant.WEICHAT_APP_ID, true);
        //新浪微博分享
        mIWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constant.WEIBO_APP_KEY);
        mIWeiboShareAPI.registerApp();    // 将应用注册到微博客户端

        JaJaHomeApplication app = (JaJaHomeApplication) getApplication();
        app.setmId(mId);


        fragments = new Fragment[]{ItemDetailFrag.newInstance(1), ItemDetailFrag.newInstance(2), ItemDetailFrag.newInstance(3)};


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (int i = 0; i < fragments.length; i++) {
            Fragment fragment = fragments[i];
            transaction.add(R.id.fl_content, fragment);
            transaction.hide(fragment);
        }
        transaction.show(fragments[0]);
        transaction.commitAllowingStateLoss();

        pic_lv.setVisibility(View.VISIBLE);

        pic_ll.setOnClickListener(this);
        canshu_ll.setOnClickListener(this);
        shop_ll.setOnClickListener(this);
        initBottomComment();
    }

    private LinearLayout mRecyclerViewComment;
    private CommentRecyclerAdapter mCommentRecyclerAdapter;

    private void initBottomComment() {
        mRecyclerViewComment = (LinearLayout) findViewById(R.id.recyclerView_bottom);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCommentList();
    }

    /**
     * 获取评论
     */
    private void getCommentList() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(1);
        paginationBean.setLimit(2);
        ReqPage reqPage = new ReqPage();
        ReqPage.ContentBean content = new ReqPage.ContentBean();
        content.setContent_id(mId);
        content.setContent_type("item");
        reqPage.setContent(content);
        content.setPagination(paginationBean);
        reqPage.setCmd(Constant.COMMENT_LIST);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().getComment(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(CommentModel userMessageModel) {
                        tvTotalComment.setText("累计评论(" + userMessageModel.getData().getPagination().getTotal() + ")");
                        List<CommentModel.DataBean.ListsBean> list = userMessageModel.getData().getLists();
                        if (list == null || list.size() == 0) {
                            tvCommentEmpty.setVisibility(View.VISIBLE);
                            mRecyclerViewComment.setVisibility(View.GONE);
                        } else {
                            mRecyclerViewComment.setVisibility(View.VISIBLE);
                            tvCommentEmpty.setVisibility(View.GONE);
                            addCommentView(list);
                        }
                    }
                });
    }

    private void addCommentView(List<CommentModel.DataBean.ListsBean> list) {
        mRecyclerViewComment.removeAllViews();
        for (final CommentModel.DataBean.ListsBean bean : list) {
            View commentView = LayoutInflater.from(mContext).inflate(R.layout.item_comment2, null);
            CircleImageView imgView;
            TextView tvName;
            TextView tvTime;
            TextView tvContent;
            final TextView tvNums = (TextView) commentView.findViewById(R.id.tv_zan_num);
            tvName = (TextView) commentView.findViewById(R.id.item_name);
            tvTime = (TextView) commentView.findViewById(R.id.item_time);
            tvContent = (TextView) commentView.findViewById(R.id.tv_content);
            imgView = (CircleImageView) commentView.findViewById(R.id.image_person);
            LinearLayout viewLike = (LinearLayout) commentView.findViewById(R.id.view_zan);
            LinearLayout viewReplys = (LinearLayout) commentView.findViewById(R.id.view_replys);
            LinearLayout viewReplysContent = (LinearLayout) commentView.findViewById(R.id.view_replys_content);

            if (bean.getReplys() != null && bean.getReplys().size() == 0) {
                viewReplys.setVisibility(View.GONE);
            } else {
                viewReplys.setVisibility(View.VISIBLE);
                CommentInnerAdapter adapter = new CommentInnerAdapter();
                List<CommentModel.DataBean.ListsBean.ReplysBean> replys = bean.getReplys();
                if (replys != null && replys.size() > 2) {
                    replys = replys.subList(0, 2);
                }
                for (int i = 0; i < replys.size(); i++) {
                    CommentModel.DataBean.ListsBean.ReplysBean replysBean = replys.get(i);
                    View innerReply = LayoutInflater.from(mContext).inflate(R.layout.item_comment_inner, null);
                    TextView tvNameReply = (TextView) innerReply.findViewById(R.id.item_name);
                    TextView tvContentReply = (TextView) innerReply.findViewById(R.id.item_content);
                    tvNameReply.setText(replysBean.getUser().getNickname() + " 回复：");
                    tvContentReply.setText(replysBean.getComment());
                    viewReplysContent.addView(innerReply);
                }
            }
            if (bean.getUser().getAvatar() != null) {
                Picasso.with(mContext)
                        .load(bean.getUser().getAvatar().getSmall())
                        .placeholder(R.mipmap.ic_holder_header_big)
                        .error(R.mipmap.ic_holder_header_big)
                        .into(imgView);
            } else {
                imgView.setImageResource(R.mipmap.ic_holder_header_big);
            }
            tvName.setText(bean.getUser().getNickname());
            tvTime.setText(TimeUtil.getCommentTime(bean.getTime()));
            tvContent.setText(bean.getComment());
            tvNums.setText(String.valueOf(bean.getLike()));
            viewLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!LoginUtil.isLogin(mContext)) {
                        mContext.startActivity(new Intent(mContext, LoginAct.class));
                        Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    like(bean, tvNums, Integer.parseInt(bean.getId()));
                }
            });
            mRecyclerViewComment.addView(commentView);

            commentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!LoginUtil.isLogin(mContext)) {
                        mContext.startActivity(new Intent(mContext, LoginAct.class));
                        Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        gotoComment();
                    }
                }
            });

        }
    }


    /**
     * 获取套装信息
     */
    private void getDetail() {
        mSubscription = ApiImp.get().
                item(HttpUtil.getRequestBodyDetail(Constant.ITEM, mId), HttpUtil.getSession(mContext)).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ItemModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showToast("未发现该商品");
                        finish();
                    }

                    @Override
                    public void onNext(ItemModel model) {
                        mItemModel = model;
                        int published = model.getData().getItem().getPublished();
                        if (published != PublishConstant.UP) {
                            showToast(PublishConstant.getState(published));
                            finish();
                        }
                        mPreviewImgAdapter.setItemPreviewImgsNotDefault(mItemModel);
                        setData();
                        setTopViewPager();
                        bottomImgCollect.setVisibility(View.VISIBLE);
                        mItemModel.getData().getItem().setFavorite(0);
                        isMyCollect(Tag);

                        if (model.getData().getItem().getPreview().get(0).getSet_image() == null) {
                            topItem.setVisibility(View.GONE);
                        } else {
                            topItem.setVisibility(View.VISIBLE);
                        }

                    }
                });
    }

    /**
     * 进入评论
     */
    private void gotoComment() {
        //        set | 套装
        //       item | 单品
        Intent intent = new Intent(mContext, CommentAct.class);
        intent.putExtra("id", mId);
        intent.putExtra("content_type", "item");
        startActivity(intent);
    }

    /*判断是否是从我的收藏跳转过来*/
    public void isMyCollect(String Tag) {
        if (Tag != null && Tag.equals(ItemCollectAdapter.Tag)) {
            bottomImgCollect.setVisibility(View.GONE);
            bottomImgCollected.setVisibility(View.VISIBLE);
            mItemModel.getData().getItem().setFavorite(1);
        }
    }

    /**
     * 获取到数据 填充页面
     */
    private void setData() {
        bean = mItemModel.getData().getItem();
        tvName.setText(bean.getName());
        String[] prices = PriceUtil.getItemPrice(bean.getPrice_discount(), bean.getPrice_basic());
        tvPrice.setText(prices[0]);

        if (!StringUtil.isEmpty(bean.getItem_introduce())) {
            intrDesc.setText(bean.getItem_introduce());
        } else {
            intrDesc.setVisibility(View.GONE);
        }

        if (prices[1] == null) {
            tvPriceBasic.setVisibility(View.GONE);
        } else {
            tvPriceBasic.setText(prices[1]);
            tvPriceBasic.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        //材质
        if (bean.getMaterial() != null && bean.getMaterial().size() > 0) {
            mMaterialAdapter = new MaterialAdapter();
            materialImgRecycle.setAdapter(mMaterialAdapter);
            mMaterialAdapter.setListener(this);
            //去除默认选项的判断 直接显示第一个
            mMaterialAdapter.setMaterialList(bean.getMaterial(), "");
            ItemModel.DataBean.ItemBean.MaterialBean first = bean.getMaterial().get(0);
            tvMaterial.setText(first.getName());
            mPreviewImgAdapter.setDefaultMaterialId(first.getId());
        } else {
            viewMaterial.setVisibility(View.GONE);
        }
        //相关
        if (bean.getRelated() != null && bean.getRelated().size() > 0) {
            mRelatedAdapter = new RelatedImgAdapter();
            relatedImgRecycle.setAdapter(mRelatedAdapter);
            mRelatedAdapter.resetItems(bean.getRelated());
        } else {
            viewRelated.setVisibility(View.GONE);
        }

        if (bean.getRelated_set() != null && bean.getRelated_set().size() > 0) {
            setImgAdapter = new RelatedSetImgAdapter();
            relatedSetRecyclerView.setAdapter(setImgAdapter);
            setImgAdapter.resetItems(bean.getRelated_set());
        } else {
            relatedSet.setVisibility(View.GONE);
        }

        //面料
        if (bean.getFabric() != null && bean.getFabric().size() > 0) {
            mFabricAdapter = new FabricAdapter();
            fabricImgRecycle.setAdapter(mFabricAdapter);
            mFabricAdapter.setListener(this);
            //去除默认选项的判断 直接显示第一个
            mFabricAdapter.setFabricList(bean.getFabric(), "");
            ItemModel.DataBean.ItemBean.FabricBean fabricBean = bean.getFabric().get(0);
            mPreviewImgAdapter.setDefaultFabricId(fabricBean.getId());
            tvFabric.setText(fabricBean.getName());
            if (bean.getGroup_sofa() == 1) {
                tvMaterialName.setText("款式");
            }
        } else {
            viewFabric.setVisibility(View.GONE);
        }
        textLl.setItemData(bean);
        viewGotoDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                H5Act.gotoH5(mContext, Constant.ITEM_DETAIL + mId, "参数详情");
            }
        });

        textLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                H5Act.gotoH5(mContext, Constant.ITEM_DETAIL + mId, "参数详情");

            }
        });
        onClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                H5Act.gotoH5(mContext, Constant.ITEM_DETAIL + mId, "参数详情");
            }
        });
        mPreviewImgAdapter.setModel(mItemModel);
        mPreviewImgAdapter.change();
    }

    private void setListener() {
        bottomImgCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginModle loginModle = LoginUtil.getInfo(mContext);
                if (loginModle == null) {
                    gotoNewAty(LoginAct.class);
                } else {
                    if (mItemModel != null) {
                        if (mItemModel.getData().getItem().getFavorite() == 0) {
                            //还未收藏
                            addFav();
                        } else {
                            delFav();
                        }
                    }
                }
            }
        });

        bottomImgCollected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginModle loginModle = LoginUtil.getInfo(mContext);
                if (loginModle == null) {
                    gotoNewAty(LoginAct.class);
                } else {
                    if (mItemModel != null) {
                        if (mItemModel.getData().getItem().getFavorite() == 1) {
                            //还未收藏
                            delFav();
                        } else {
                            addFav();
                        }
                    }
                }
            }
        });
        //立即购买
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginModle loginModle = LoginUtil.getInfo(mContext);
                if (loginModle == null) {
                    gotoNewAty(LoginAct.class);
                } else {
                    ShoppingCartAct.gotoBuy(mContext, Constant.ITEM_PRICE + getBuyOrShopCardUrl(loginModle, 0));
                }
            }
        });
        //添加购物车
        btnAddToShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginModle loginModle = LoginUtil.getInfo(mContext);
                if (loginModle == null) {
                    gotoNewAty(LoginAct.class);
                } else {
                    ShoppingCartAct.addToShoppingCart(mContext, Constant.ITEM_PRICE + getBuyOrShopCardUrl(loginModle, 1));
                }
            }
        });
        //位置
        bottomImgLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LocationSvc.sharedPreferencesUtils != null && !StringUtil.isEmpty(LocationSvc.sharedPreferencesUtils.getUserCity())) {
                    cityName = LocationSvc.sharedPreferencesUtils.getUserCity();
                } else {
                    cityName = "";
                }
                try {
                    cityName = URLDecoder.decode(cityName, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (mItemModel != null) {
                    String locationUri = Constant.lOCATION + mItemModel.getData().getItem().getId() + "&city=" + cityName;
                    H5Act.gotoH5(mContext, locationUri);
                }

            }
        });
        bottomImgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopShare == null) {
                    mPopShare = new PopShare(ItemDetailAct.this, mIwxapi, mIWeiboShareAPI, bottomImgShare, ItemDetailTag);
                    // mPopShare.setmUrl(mItemModel.getData().getItem().getPreview().get(0).getImage().get(0).getThumb());
                    mPopShare.setmUrl(Constant.ITEMURL + mId);
                    // mPopShare.setShowURI(imgUrl);
                }

                if (bean != null) {
                    mPopShare.setText(bean.getName());
                }
                mPopShare.setShowURI(imgUrl);
                mPopShare.show();
            }
        });


        topItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginModle loginModle = LoginUtil.getInfo(mContext);
                if (loginModle == null) {
                    gotoNewAty(LoginAct.class);
                } else {
                    if (mItemModel != null) {
                        if (mItemModel.getData().getItem().getFavorite() == 1) {
                            //还未收藏
                            delFav();
                        } else {
                            addFav();
                        }
                    }
                }

            }
        });

        findViewById(R.id.view_goto_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoComment();
            }
        });
    }

    /**
     * 立即购买 加入购物车 访问连接拼接
     *
     * @param loginModle ：用户信息
     * @param type       ：//0 立即购买 1加入购物车
     * @return ：访问连接
     */
    private String getBuyOrShopCardUrl(LoginModle loginModle, int type) {
        ReqBuy req = new ReqBuy();
        LoginModle.DataBean dataBean = loginModle.getData();
        req.setSession(dataBean.getSession());
        req.setItem_id(Integer.valueOf(mId));
        req.setType(type);
        req.setUser_id(Integer.valueOf(dataBean.getUser().getId()));
        Gson gson = new Gson();
        String json = gson.toJson(req);
        return Base64Helper.encodeStringToBase64(json);
    }

    /**
     * 设置顶部 viewpager
     */
    private void setTopViewPager() {
        topViewPager.setAdapter(mPreviewViewPagerAdapter);
        topViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mPreviewImgAdapter.setmIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mPreviewViewPagerAdapter.setItemPreviewImgs(mPreviewImgAdapter.getDataList());
    }

    @Override
    public void onPreviewImageChange(int index) {
        topViewPager.setCurrentItem(index);
    }


    /**
     * 添加收藏
     */

    private void addFav() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.ADD_FAVORITE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(Integer.valueOf(mId));
        contentBean.setType(1);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("收藏中");
        mSubscription = ApiImp.get().addFavorite(requestBody, HttpUtil.getSession(mContext)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AddFavoriteModel>() {
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
                showToast(R.string.collect_success);
                mItemModel.getData().getItem().setFavorite(1);
                bottomImgCollect.setVisibility(View.GONE);
                bottomImgCollected.setVisibility(View.VISIBLE);

            }
        });
    }

    /**
     * 删除收藏
     */
    private void delFav() {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.DEL_FAVORITE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(Integer.valueOf(mId));
        contentBean.setType(1);
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
                mItemModel.getData().getItem().setFavorite(0);
                showToast(R.string.del_collect_success);
                bottomImgCollect.setVisibility(View.VISIBLE);
                bottomImgCollected.setVisibility(View.GONE);
                EventBus.getDefault().post(new EventMessage(4, index));
            }
        });
    }

    /**
     * 所选的材质变化
     *
     * @param id   ：材质id
     * @param name :材质名称
     */
    @Override
    public void onFabricChanged(String id, String name) {
        tvFabric.setText(name);
        mPreviewImgAdapter.setItemPreviewImgsByFabric(mItemModel, id);
        mPreviewViewPagerAdapter.setItemPreviewImgs(mPreviewImgAdapter.getDataList());
    }

    /**
     * 所选的面料变化
     *
     * @param id   ：面料id
     * @param name :面料名称
     */
    @Override
    public void onMaterialChanged(String id, String name) {
        tvMaterial.setText(name);
        mPreviewImgAdapter.setItemPreviewImgsByMaterial(mItemModel, id);
        mPreviewViewPagerAdapter.setItemPreviewImgs(mPreviewImgAdapter.getDataList());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private int checkIndex;


    @Override
    public void onClick(View view) {
        int index = 0;
        switch (view.getId()) {
            case R.id.pic_text_ll:

                pic_lv.setVisibility(View.VISIBLE);
                canshu_lv.setVisibility(View.GONE);
                shop_lv.setVisibility(View.GONE);
                break;
            case R.id.can_shu_ll:
                index = 1;
                pic_lv.setVisibility(View.GONE);
                canshu_lv.setVisibility(View.VISIBLE);
                shop_lv.setVisibility(View.GONE);
                break;
            case R.id.shop_introduce_ll:
                index = 2;
                pic_lv.setVisibility(View.GONE);
                canshu_lv.setVisibility(View.GONE);
                shop_lv.setVisibility(View.VISIBLE);
                break;

            default:
                index = 0;
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (checkIndex == index) {

        } else {
            transaction.show(fragments[index]);
            transaction.hide(fragments[checkIndex]);
            transaction.commitAllowingStateLoss();
        }

        checkIndex = index;

    }

    /**
     * 用户消息处理
     */
    private void like(final CommentModel.DataBean.ListsBean bean, final TextView tvNums, int i) {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.COMMENT_LIKE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(i);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("请稍等");
        ApiImp.get().operationMessage(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleModel>() {
                    @Override
                    public void onCompleted() {
                        dissmisProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmisProgressDialog();
                    }

                    @Override
                    public void onNext(SimpleModel deleteModel) {
                        if (deleteModel.getStatus() == 0) {
                            tvNums.setText(String.valueOf(bean.getLike() + 1));
                        } else {
                            showToast(deleteModel.getMessage());
                        }
                    }
                });

    }

    @Override
    public void onLike(int position, String id) {
    }

    @Override
    public void onReply(int position, String id, String name) {

    }

    @Override
    public void onDel(int position, String id) {

    }
}
