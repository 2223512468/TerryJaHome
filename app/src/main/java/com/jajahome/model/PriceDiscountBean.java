package com.jajahome.model;

import java.io.Serializable;

/**
 * 折扣价基础model
 */
public class PriceDiscountBean  implements Serializable {
	private int id;
	private int min;

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	private int max;
}
