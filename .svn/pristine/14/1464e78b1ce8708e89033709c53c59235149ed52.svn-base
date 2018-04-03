package com.jajahome.feature.search.adapter;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.feature.search.SearchShowAct;
import com.jajahome.feature.view.FlowTagLayout;
import com.jajahome.feature.view.OnTagClickListener;
import com.jajahome.model.ConfigModel;
import com.jajahome.util.RandomUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liuhaizhu on 2017/8/2.
 */

public class SearchTipsAdapter extends BaseRecyclerAdapter<String> {
    private String type;//类型
    private String key;
    private ConfigModel mConfigModel;//配置
    public HashMap<Integer,List<String>> tipsFlows=new HashMap<>();
    public void setType(String type) {
        this.type = type;
    }
    private final int COLOR_TEXT= Color.parseColor("#969696");
    private final int COLOR_KEY= Color.parseColor("#fa4242");

    public void setOnTipClickListener(OnTipClickListener onTipClickListener) {
        mOnTipClickListener = onTipClickListener;
    }

    private OnTipClickListener mOnTipClickListener;
    public void setConfigModel(ConfigModel configModel) {
        mConfigModel = configModel;
    }


    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int i, List<String> mItemDataList) {
        ViewHolder holder= (ViewHolder) viewHolder;
       final String text=mItemDataList.get(i);
        holder.tvKey.setText(mItemDataList.get(i));
        if(text.contains(key)){
            int index=text.indexOf(key);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.setSpan(new ForegroundColorSpan(Color.RED), index, index+key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvKey.setText(style);
        }
        final List<String> list=tipsFlows.get(i);
        SearchTipsFlowAdapter flowAdapter=new SearchTipsFlowAdapter(mContext,list);
        holder.mFlowLayout.setAdapter(flowAdapter);
        flowAdapter.notifyDataSetChanged();
        holder.mFlowLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                String clickText=list.get(position);
                mOnTipClickListener.onTipClick(text+" "+clickText);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnTipClickListener.onTipClick(text);
            }
        });
    }

    @Override
    public int getListLayoutId() {
        return R.layout.item_search_tips;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        TextView tvKey;
        FlowTagLayout mFlowLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            tvKey = (TextView) findView(R.id.tv_key);
            mFlowLayout = (FlowTagLayout) findView(R.id.flow_tag_layout);
        }
    }

    /**
     * 填充数据 然后获取配置
     */
    public void setAllData(List<String> list,String key){
        this.key=key;
        tipsFlows.clear();
        if(list==null||list.size()==0){
            mList.clear();
        }
        int size=list.size();
        for(int i=0;i<size;i++){
            tipsFlows.put(i,getRandomTips());
        }
        resetItems(list);
    }


    private List<String> getRandomTips(){
        List<String> list=new ArrayList<>();
        switch (type){
            case SearchShowAct.TYPE_ITEM:
                List<ConfigModel.DataBean.ConfigBean.ItemColorBean> colorBeans=mConfigModel.getData().getConfig().getItem_color();
                int[] random01= RandomUtil.randomCommon(0,colorBeans.size()-1,3);
                for(int i:random01){
                    list.add(colorBeans.get(i).getText());
                }
                List<ConfigModel.DataBean.ConfigBean.ItemStyleBean> brandBeans=mConfigModel.getData().getConfig().getItem_style();
                int[] random02= RandomUtil.randomCommon(0,brandBeans.size()-1,3);
                for(int i:random02){
                    list.add(brandBeans.get(i).getText());
                }
                break;
            case SearchShowAct.TYPE_SET:
                List<ConfigModel.DataBean.ConfigBean.SetColorBean> setColorBeans=mConfigModel.getData().getConfig().getSet_color();
                int[] random03= RandomUtil.randomCommon(0,setColorBeans.size()-1,3);
                for(int i:random03){
                    list.add(setColorBeans.get(i).getText());
                }
                List<ConfigModel.DataBean.ConfigBean.SetStyleBean> setStyleBeans=mConfigModel.getData().getConfig().getSet_style();
                int[] random04= RandomUtil.randomCommon(0,setStyleBeans.size()-1,3);
                for(int i:random04){
                    list.add(setStyleBeans .get(i).getText());
                }
                break;
        }
        return list;
    }

    public interface OnTipClickListener{
        void onTipClick(String value);
    }
}
