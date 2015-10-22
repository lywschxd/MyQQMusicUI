package com.dd.myqqmusicui.view.MetroView;

import android.view.View;

public class AutoFillMetroItem {

	private View mMetroView = null;

	private int mRowSpan = 1;
	private int mColSpan = 1;

	/**
	 * @param v
	 * @param rowspan
	 * @param colspan
	 */
	public AutoFillMetroItem(View v, int rowspan, int colspan) {
		mMetroView = v;

		if (rowspan < 1)
			throw new IllegalArgumentException("rowspan < 1");
		mRowSpan = rowspan;

		if (colspan < 1)
			throw new IllegalArgumentException("colspan < 1");
		mColSpan = colspan;
	}

	public View getMetroView() {
		return mMetroView;
	}

	public int getRowSpan() {
		return mRowSpan;
	}

	public int getColSpan() {
		return mColSpan;
	}
}
