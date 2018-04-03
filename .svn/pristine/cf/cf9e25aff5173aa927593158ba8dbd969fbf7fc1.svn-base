package com.jajahome.feature.comment;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jajahome.R;
import com.jajahome.base.BaseActivity;
import com.jajahome.constant.Constant;
import com.jajahome.feature.user.activity.LoginAct;
import com.jajahome.feature.user.adapter.MessageAdapter;
import com.jajahome.feature.view.LoginDialog;
import com.jajahome.model.BaseModel;
import com.jajahome.model.CommentModel;
import com.jajahome.model.SimpleModel;
import com.jajahome.network.ApiImp;
import com.jajahome.network.BaseReq;
import com.jajahome.network.HttpUtil;
import com.jajahome.network.ReqPage;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.SoftPanUtil;
import com.jajahome.util.StringUtil;
import com.jajahome.widget.recyclerview.MultiRecycleView;

import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 评论
 * Created by laotang on 2016/7/22.
 */
public class CommentAct extends BaseActivity implements View.OnClickListener, MultiRecycleView.OnMutilRecyclerViewListener
        , MessageAdapter.OnMessageItemOperation , View.OnLayoutChangeListener ,CommentRecyclerAdapter.OnCommentListener{
    @BindView(R.id.ibtn_back)
    ImageButton ibtnBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.recyclerView)
    MultiRecycleView recyclerView;
    @BindView(R.id.edit_content)
    EditText mEditContent;
    @BindView(R.id.img_send)
    TextView mImgSend;
    @BindView(R.id.root_rel)
    RelativeLayout mRelativeLayout;
    private CommentRecyclerAdapter mAdapter;
    int offset = 1;
    private boolean isFirst = true;
    private String id; //评论id
    private String content_type;//评论类型
    private int position;//当前操作的位置
    private String replyId;//回复的评论id

    @Override
    protected int getViewId() {
        return R.layout.act_comment;
    }

    @Override
    protected void initEvent() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        textView2.setText(R.string.user_comment);
        id = getIntent().getStringExtra("id");
        content_type = getIntent().getStringExtra("content_type");
        initViewController(recyclerView);
        ibtnBack.setOnClickListener(this);
        mAdapter = new CommentRecyclerAdapter();
        mAdapter.setOnCommentListener(this);
        recyclerView.setAdapter(mAdapter);
        getCommentList();
        recyclerView.setOnMutilRecyclerViewListener(this);
        mImgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment();
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        mRelativeLayout.addOnLayoutChangeListener(this);
    }
    /**
     * 获取评论
     */
    private void getCommentList() {
        ReqPage.ContentBean.PaginationBean paginationBean = new ReqPage.ContentBean.PaginationBean();
        paginationBean.setOffset(offset);
        paginationBean.setLimit(Constant.PAGE_LIMIT);
        ReqPage reqPage = new ReqPage();
        ReqPage.ContentBean content = new ReqPage.ContentBean();
        content.setContent_id(id);
        content.setContent_type(content_type);
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
                        recyclerView.stopRefresh();
                        recyclerView.stopLoadingMore();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(CommentModel userMessageModel) {
                        if (offset == 1) {
                            if (isFirst) {
                                showLoading(false, "");
                                isFirst = false;
                            }
                            List<CommentModel.DataBean.ListsBean> list = userMessageModel.getData().getLists();
                            if (list == null || list.size() == 0) {
                                showEmpty(true, "暂无评论", null);
                                mAdapter.clear();
                            } else {
                                showEmpty(false, "暂无评论", null);
                                mAdapter.resetItems(list);
                            }
                        } else {
                            mAdapter.addItems(userMessageModel.getData().getLists());
                        }
                    }
                });
    }


    /**
     * 用户消息处理
     *
     * @param operation :1已读 2删除
     * @param index
     *
     *
     * @param id
     */
    private void operation(final int operation, final int index, int id) {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.USER_MESSAGE_OPERATION);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(id);
        contentBean.setOperation(operation);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        if (operation == 2) {
            showProgressDialog("正在删除");
        }
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
                            if (operation == 2) {
                                mAdapter.remove(index);
                                mAdapter.notifyDataSetChanged();
                            } else if (operation == 1) {

                            }
                        } else {
                            showToast(deleteModel.getMessage());
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_back:
                finish();
                break;
        }
    }

    /**
     * 下啦刷新
     */
    @Override
    public void onRefresh() {
        offset = 1;
        getCommentList();
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore() {
        offset++;
        getCommentList();
    }

    /**
     * 对消息进行操作
     *
     * @param operation ：1标识消息已读 2删除
     * @param index     ：index
     * @param id        :消息id
     */
    @Override
    public void onMessageOperation(int operation, int index, int id) {
        operation(operation, index, id);
    }

    /**
     * 获取用户消息 9612
     * content_type | 字符串 | 评论类型
     * content_id | 数值 | 评论内容id
     * comment_id | 数值 | 评论id(存在的话，即回复某条评论)
     * comment | 字符串 | 评论内容
     */

    private void comment() {
        if (!LoginUtil.isLogin(mContext)) {
            mContext.startActivity(new Intent(mContext,LoginAct.class));
            Toast.makeText(mContext,"请先登录",Toast.LENGTH_SHORT).show();
            return;
        }
        String commentContent = mEditContent.getText().toString();
        if (StringUtil.isEmpty(commentContent)) {
            if(StringUtil.isEmpty(replyId)){
                showToast(R.string.pls_input_user_comment);
            }else {
                showToast(R.string.pls_input_reply);
            }
            return;
        }
        ReqPage reqPage = new ReqPage();
        ReqPage.ContentBean content = new ReqPage.ContentBean();
        if(!StringUtil.isEmpty(replyId)){
            content.setComment_id(replyId);
            showProgressDialog(getResources().getString(R.string.is_replying));
        }else {
            showProgressDialog(getResources().getString(R.string.is_commentting));
        }
        content.setContent_id(id);
        content.setComment(commentContent);
        content.setContent_type(content_type);

        reqPage.setContent(content);
        reqPage.setCmd(Constant.COMMENT_ADD);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(reqPage));
        mSubscription = ApiImp.get().comment(requestBody, HttpUtil.getSession(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmisProgressDialog();
                        e.printStackTrace();
                        offset = 1;
                        getCommentList();
                        mEditContent.setText("");
                    }

                    @Override
                    public void onNext(BaseModel userMessageModel) {
                        dissmisProgressDialog();
                        offset = 1;
                        getCommentList();
                    }
                });
    }

    /**
     * 用户消息处理
     *
     * @param id
     */
    private void like(final int position , int id) {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.COMMENT_LIKE);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(id);
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
                            mAdapter.like(position);
                        } else {
                            showToast(deleteModel.getMessage());
                        }
                    }
                });

    }

    /**
     *  删除
     *
     * @param id
     */
    private void del(final int position , int id) {
        BaseReq baseReq = new BaseReq();
        baseReq.setCmd(Constant.COMMENT_DEL);
        BaseReq.ContentBean contentBean = new BaseReq.ContentBean();
        contentBean.setId(id);
        baseReq.setContent(contentBean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(baseReq));
        showProgressDialog("正在删除");
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
                            mAdapter.remove(position);
                        } else {
                            showToast(deleteModel.getMessage());
                        }
                    }
                });

    }


    int keyHeight = 300;
    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            replyId = null;
            mEditContent.setHint(R.string.pls_input_user_comment);
            mEditContent.setText("");
        }
    }

    @Override
    public void onLike(int position, String id) {
        like(position,Integer.parseInt(id));
    }

    @Override
    public void onReply(int position, String id, String name) {
        replyId = id;
        mEditContent.setHint("回复@" + name);
        mEditContent.requestFocus();
        mEditContent.setText("");
        SoftPanUtil.openKeybord(mEditContent, this);
    }

    @Override
    public void onDel(final  int position,final String id) {
        LoginDialog.Builder builder = new LoginDialog.Builder(mContext);
        builder.setMessage("是否删除该评论?");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                del(position,Integer.parseInt(id));

            }
        });
        builder.create().show();

    }
}
