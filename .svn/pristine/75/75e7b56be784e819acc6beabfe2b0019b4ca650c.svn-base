package com.jajahome.feature.user.adapter;

import android.view.View;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.base.BaseRecyclerAdapter;
import com.jajahome.base.BaseRecyclerViewHolder;
import com.jajahome.model.AddressModel;

import java.util.List;

/**
 * Created by laotang on 2016/8/2.
 */
public class AddressAdapter extends BaseRecyclerAdapter<AddressModel.AddressBean> {

    private AddressModel.AddressBean addressBean;

    /**
     * @param viewHolder    基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param i             位置
     * @param mItemDataList 数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int i, List<AddressModel.AddressBean> mItemDataList) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tv_name.setText(mItemDataList.get(i).getName());
        holder.tv_phone.setText(mItemDataList.get(i).getMobile());
        String tv = mItemDataList.get(i).getArea() + mItemDataList.get(i).getDetail_address();

        holder.tv_area.setText(tv);
        addressBean = mItemDataList.get(i);


        String type = mItemDataList.get(i).getType();
        if ("1".equals(type)) {
            holder.tv_type.setText("默认地址");
        } else {
            holder.tv_type.setText("");
        }

    }


    @Override
    public int getListLayoutId() {
        return R.layout.item_address;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        TextView tv_name, tv_phone, tv_area, tv_detail_address, tv_type;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            // tv_area = (TextView) itemView.findViewById(R.id.tv_area);
            //  tv_detail_address = (TextView) itemView.findViewById(R.id.tv_detail_address);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_area = (TextView) itemView.findViewById(R.id.tv_detail);
        }
    }
}
