package com.jajahome.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jajahome.R;
import com.jajahome.model.ItemModel;
import com.jajahome.util.StringUtil;


/**
 * 用于显示单品详情里 参数
 * <color name="secondary_text_color">#727272</color>
 * <!-- 系统主题颜色_三级字体颜色  -->
 * <color name="third_text_color">#40000000</color>
 */
public class TextLinearLayout extends LinearLayout {

	public TextLinearLayout(Context context) {
		super(context);
		setOrientation(VERTICAL);
	}

	public TextLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(VERTICAL);
	}

	public TextLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setOrientation(VERTICAL);
	}


	public void addText(TextEntity entity) {
		TextView textView = new TextView(getContext());
		textView.setText(getSS(entity));
		addView(textView);
	}

	public void addText(String desc, String value) {
		String string = desc + ": " + value;
		int index = string.indexOf(": ");
		SpannableString spannableString = new SpannableString(string);
		spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), index + 1, string.length(),
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		TextView textView = new TextView(getContext());
		textView.setTextSize(12);
		textView.setTextColor(Color.BLACK);
		textView.setText(spannableString);
		addView(textView);
	}

	/**
	 * 设置单品详情参数
	 * @param bean
	 */
	public void setItemData(ItemModel.DataBean.ItemBean bean) {
		if (bean != null) {
			if (!StringUtil.isEmpty(bean.getBrand())) {
				addText(getString(R.string.brand), bean.getBrand());
			}
			String gjjCode=bean.getGjj_code();
			if (!StringUtil.isEmpty(gjjCode)) {
				if(gjjCode.length()>6){
					gjjCode=gjjCode.substring(gjjCode.length()-6,gjjCode.length());
				}
				addText(getString(R.string.code), gjjCode);
			}
			if (!StringUtil.isEmpty(bean.getStyle())) {
				addText(getString(R.string.style), bean.getStyle());
			}
			if (!StringUtil.isEmpty(bean.getSize())) {
				addText(getString(R.string.guige), bean.getSize());
			}
			if (!StringUtil.isEmpty(bean.getPlace())) {
				addText(getString(R.string.place), bean.getPlace());
			}
		}
	}

	public String getString(int id) {
		return getResources().getString(id);
	}

	private SpannableString getSS(TextEntity entity) {
		String string = entity.text + ": " + entity.desc;
		int index = string.indexOf(": ");
		SpannableString spannableString = new SpannableString(string);
		spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), index + 1, string.length(),
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

}
