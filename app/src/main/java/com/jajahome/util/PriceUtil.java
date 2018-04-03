package com.jajahome.util;

import com.jajahome.constant.Constant;
import com.jajahome.model.PriceBasicBean;
import com.jajahome.model.PriceDiscountBean;

/**
 * 关于价格的工具类
 */
public class PriceUtil {
	private final static String ASK_PRICE = "请询价";
	private final static String QI = "起";
	private final static String DIV = " - ";
	private final static String BASIC_PRICE = "";
	//在详情中显示 价格区间格式 100起
	public static String[] getItemPrice(PriceDiscountBean discountBean, PriceBasicBean basicBean) {
		String prices[] = new String[2];
		if (discountBean.getMin() == discountBean.getMax()) {//折扣价没有区间
			if (discountBean.getMin() == 0) {
				prices[0] = ASK_PRICE;
			} else {
				prices[0] = Constant.YUAN + discountBean.getMin();
				if (basicBean.getMin() != discountBean.getMin() || basicBean.getMax() != discountBean.getMax()) {
					//价格相同没有折扣
					if (basicBean.getMin() == basicBean.getMax()) {
						prices[1] = BASIC_PRICE + Constant.YUAN + basicBean.getMin();
					} else {
						prices[1] = BASIC_PRICE + Constant.YUAN + basicBean.getMin() + DIV + basicBean.getMax();
					}
				}
			}

		} else {
			prices[0] = Constant.YUAN + discountBean.getMin() + DIV + discountBean.getMax();
			if (basicBean.getMin() != discountBean.getMin() || basicBean.getMax() != discountBean.getMax()) {
				//价格相同没有折扣
				if (basicBean.getMin() == basicBean.getMax()) {
					prices[1] = BASIC_PRICE + Constant.YUAN + basicBean.getMin();
				} else {
					prices[1] = BASIC_PRICE + Constant.YUAN + basicBean.getMin() + DIV + basicBean.getMax();
				}
			}
		}
		return prices;
	}


	/**
	 * 在列表中显示 价格区间格式 100起
	 * @param discountBean
	 * @param basicBean
	 * @return
	 */
	public static String[] getItemPriceInList(PriceDiscountBean discountBean, PriceBasicBean basicBean) {
		String prices[] = new String[2];
		if (discountBean.getMin() == discountBean.getMax()) {//折扣价没有区间
			if (discountBean.getMin() == 0) {
				prices[0] = ASK_PRICE;
			} else {
				prices[0] = Constant.YUAN + discountBean.getMin();
				if (basicBean.getMin() != discountBean.getMin() || basicBean.getMax() != discountBean.getMax()) {
					//价格相同没有折扣
					if (basicBean.getMin() == basicBean.getMax()) {
						prices[1] = BASIC_PRICE + Constant.YUAN + basicBean.getMin();
					} else {
						prices[1] = BASIC_PRICE + Constant.YUAN + basicBean.getMin() + QI;
					}
				}
			}

		} else {
			prices[0] = Constant.YUAN + discountBean.getMin() +QI;
			if (basicBean.getMin() != discountBean.getMin() || basicBean.getMax() != discountBean.getMax()) {
				//价格相同没有折扣
				if (basicBean.getMin() == basicBean.getMax()) {
					prices[1] = BASIC_PRICE + Constant.YUAN + basicBean.getMin();
				} else {
					prices[1] = BASIC_PRICE + Constant.YUAN + basicBean.getMin() + QI;
				}
			}
		}
		return prices;
	}
}
