package com.jajahome.diyview;

import com.jajahome.model.SetModel;

import java.util.Comparator;

/**
 *  对 透视图 的层级关系 排序
 */
public class SortComparator implements Comparator<SetModel.DataBean.SetBean.ListBean>{
	@Override
	public int compare(SetModel.DataBean.SetBean.ListBean lhs, SetModel.DataBean.SetBean.ListBean rhs) {
		long rhsValue=Long.valueOf(rhs.getZorder());
		long lhsValue=Long.valueOf(lhs.getZorder());
		return (int) (lhsValue-rhsValue);
	}
}
