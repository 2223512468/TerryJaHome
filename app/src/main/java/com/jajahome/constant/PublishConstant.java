package com.jajahome.constant;

/**
 * 单品状态
 * 现在有0：编辑，1：上架,2：下架,3：删除   四种状态。套装点击单品进入单品详情时，只有1：上架的可以打开这个单品详情页面，其它状态不可以
 */
public class PublishConstant {
	public static final int EDIT = 0;//编辑
	public static final int UP = 1;//上架
	public static final int DOWN = 2;//下架
	public static final int DEL = 3;//删除

	public static String getState(int published){
		if(published==EDIT){
			return "该商品未发布";
		}else if(published==DOWN){
			return "该商品已下架";
		}else if(published==DEL){
			return "该商品已删除";
		}
		return "";
	}
}
