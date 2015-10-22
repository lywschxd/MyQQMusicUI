package com.dd.myqqmusicui.view.MetroView;

import android.view.View;

public class MetroItem {

	private View mMetroView = null;

	private int mRow = 0;
	private int mRowSpan = 1;

	private int mCol = 0;
	private int mColSpan = 1;

	/**
	 * @param v
	 * @param row
	 * @param col
	 */
	public MetroItem(View v, int row, int col) {
		this(v, row, 1, col, 1);
	}

	/**
	 * @param v
	 * @param row
	 * @param rowspan
	 * @param col
	 * @param colspan
	 */
	public MetroItem(View v, int row, int rowspan, int col, int colspan) {
		mMetroView = v;

		setPosition(row, col);

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

	public int getRow() {
		return mRow;
	}

	public int getRowSpan() {
		return mRowSpan;
	}

	public int getCol() {
		return mCol;
	}

	public int getColSpan() {
		return mColSpan;
	}

	public void setPosition(int row, int col) {
		if (row < 0)
			throw new IllegalArgumentException("row < 0");
		mRow = row;

		if (col < 0)
			throw new IllegalArgumentException("col < 0");
		mCol = col;
	}
}
