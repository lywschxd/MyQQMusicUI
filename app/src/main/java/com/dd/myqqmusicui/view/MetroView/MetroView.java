package com.dd.myqqmusicui.view.MetroView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MetroView extends ViewGroup {

    public static enum OrientationType {
        All, Vertical, Horizontal
    };

    private static final LayoutParams FILL_FILL = new LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    /** Default count of visible items */
    private static final int DEF_VISIBLE_ROWS = 5;
    private static final int DEF_VISIBLE_COLS = 2;

    private OrientationType mOrientation = OrientationType.Horizontal;

    // Count of visible items
    protected int visibleRows = DEF_VISIBLE_ROWS;
    protected int visibleCols = DEF_VISIBLE_COLS;

    private int mCurRow = 0, mCurCol = 0;
    protected int mRowsCount = 0, mColsCount = 0;

    protected ArrayList<MetroItem> mMetroItems = new ArrayList<MetroItem>();

    private int mRowHeight, mColWidth;
    private int mGap;

//    private ViewTreeObserver mViewTreeObserver;
//    private FocusBorderView mBorderView;

    public MetroView(Context context) {
        this(context, null);
        initViewGroup(context);
    }

    public MetroView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initViewGroup(context);
    }

    public MetroView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initViewGroup(context);
    }

    private void initViewGroup(Context context) {
        mGap = 10;

        setClipChildren(false);
        setClipToPadding(false);

//        mViewTreeObserver = this.getViewTreeObserver();
//        mViewTreeObserver.addOnGlobalFocusChangeListener(globalFocusListener);
    }

    /**
     * set row and column count for visible item 0 equals to not change current
     * value others equal to
     * 
     * @param rowCount
     * @param colCount
     */
    public void setVisibleItems(int rowCount, int colCount) {

        if (rowCount < 0 || colCount < 0)
            throw new IllegalArgumentException("visible count < 0");

        if (rowCount != 0)
            visibleRows = rowCount;

        if (colCount != 0)
            visibleCols = colCount;
    }

    public void setRowHeight(int height) {
        mRowHeight = height;
    }

    public void setColWidth(int width) {
        mColWidth = width;
    }

    public int getVisibleRows() {
        return visibleRows;
    }

    public int getVisibleCols() {
        return visibleCols;
    }

    public void addMetroItem(MetroItem item) {
        mMetroItems.add(item);
        addView(item.getMetroView(), FILL_FILL);

        adjustRowCol(item);
    }

    public boolean deleteMetroItem(MetroItem item) {

        boolean ret = false;

        if (mMetroItems.contains(item)) {
            mMetroItems.remove(item);
            removeView(item.getMetroView());
            ret = true;
        }

        mRowsCount = 0;
        mColsCount = 0;

        for (MetroItem mi : mMetroItems) {
            adjustRowCol(mi);
        }

        return ret;
    }

    private void adjustRowCol(MetroItem item) {
        // adjust rows count
        if (mRowsCount < item.getRow() + item.getRowSpan())
            mRowsCount = item.getRow() + item.getRowSpan();

        // adjust columns count
        if (mColsCount < item.getCol() + item.getColSpan())
            mColsCount = item.getCol() + item.getColSpan();
    }

    public void clearMetroItem() {
        mMetroItems.clear();
        removeAllViews();

        mRowsCount = 0;
        mColsCount = 0;
    }

    public void setOrientation(OrientationType orientation) {
        mOrientation = orientation;
    }

    public OrientationType getOrientation() {
        return mOrientation;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        final int itemCount = mMetroItems.size();

        if (itemCount != getChildCount())
            throw new IllegalArgumentException("contain unrecorded child");

        for (int i = 0; i < itemCount; i++) {
            final MetroItem item = mMetroItems.get(i);
            final View childView = item.getMetroView();

            if (childView.getVisibility() != View.GONE) {
                final int childLeft = (mColWidth + mGap) * item.getCol()
                        + this.getPaddingLeft();
                final int childTop = (mRowHeight + mGap) * item.getRow()
                        + this.getPaddingTop();
                final int childWidth = (mColWidth + mGap) * item.getColSpan()
                        - mGap;
                final int childHeight = (mRowHeight + mGap) * item.getRowSpan()
                        - mGap;

                childView.layout(childLeft, childTop, childLeft + childWidth,
                        childTop + childHeight);
            }
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // The children are given the same width and height as the scrollLayout
        final int itemCount = mMetroItems.size();

        for (int i = 0; i < itemCount; i++) {

            final MetroItem item = mMetroItems.get(i);
            final View childView = item.getMetroView();

            final int childWidth = MeasureSpec.makeMeasureSpec(
                    (mColWidth + mGap) * item.getColSpan() - mGap,
                    MeasureSpec.EXACTLY);
            final int childHeight = MeasureSpec.makeMeasureSpec(
                    (mRowHeight + mGap) * item.getRowSpan() - mGap,
                    MeasureSpec.EXACTLY);

            childView.measure(childWidth, childHeight);
        }

        int maxHeight = this.mRowsCount * (this.mRowHeight + this.mGap)
                - this.mGap + this.getPaddingTop() + this.getPaddingTop();
        int maxWidth = this.mColsCount * (this.mColWidth + this.mGap)
                - this.mGap + this.getPaddingLeft() + this.getPaddingRight();
        setMeasuredDimension(
                resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
            Rect previouslyFocusedRect) {
        // 重写的焦点变化方法
        Log.d("LIF", "in onFocusChanged");
    }

    public int getCurRow() {
        return mCurRow;
    }

    public int getCurCol() {
        return mCurCol;
    }

//    private OnGlobalFocusChangeListener globalFocusListener = new OnGlobalFocusChangeListener() {
//
//        @Override
//        public void onGlobalFocusChanged(View oldFocus, View newFocus) {
//            Log.d("LIF", "oldFocus = " + oldFocus);
//            Log.d("LIF", "newFocus = " + newFocus);
//        }
//
//    };
}
