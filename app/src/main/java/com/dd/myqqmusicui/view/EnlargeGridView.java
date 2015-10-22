package com.dd.myqqmusicui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;


/**
 * Created by dong on 2015-10-21 0021.
 * 摘抄至王小二的blog的enlarge gridview
 */
public class EnlargeGridView extends GridView implements AdapterView.OnItemSelectedListener, View.OnFocusChangeListener, AbsListView.OnScrollListener {
    private Context mContext;
    private OnItemSelectedListener onItemSelectedListener = null;
    private OnFocusChangeListener onFocusChangeListener = null;
    private OnScrollListener onScrollListener = null;

    private int position = 0;
    private View mPreView;
    private ScaleAnimation enlargeAnimation = null;

    public interface OnItemSelectedListener {
        void onItemSelected(AdapterView<?> parent, View view, int position, long id);

        void onNothingSelected(AdapterView<?> parent);
    }

    public interface OnFocusChangeListener {
        void onFocusChange(View v, boolean hasFocus);
    }

    public interface OnScrollListener {
        public static int SCROLL_STATE_IDLE = 0;
        public static int SCROLL_STATE_TOUCH_SCROLL = 1;
        public static int SCROLL_STATE_FLING = 2;

        public void onScrollStateChanged(AbsListView view, int scrollState);

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }


    public EnlargeGridView(Context context) {
        this(context, null);
    }

    public EnlargeGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        enlargeAnimation = new ScaleAnimation(1.0f, 1.23f, 1.0f, 1.23f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        enlargeAnimation.setInterpolator(new AccelerateInterpolator());
        enlargeAnimation.setDuration(100);
        enlargeAnimation.setFillAfter(true);

        setOnItemSelectedListener(this);
        setOnFocusChangeListener(this);
        setChildrenDrawingOrderEnabled(true);

        setOnScrollListener(this);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        onItemSelectedListener = listener;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener listener) {
        onFocusChangeListener = listener;
    }

    public void setOnScrollListener(OnScrollListener listener) {
        onScrollListener = listener;
    }

    @Override
    protected void setChildrenDrawingOrderEnabled(boolean enabled) {
        super.setChildrenDrawingOrderEnabled(enabled);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        position = getSelectedItemPosition() - getFirstVisiblePosition();
        if (position < 0) {
            return i;
        } else {
            if (i == childCount - 1) {//这是最后一个需要刷新的item
                if (position > i) {
                    position = i;
                }
                return position;
            }
            if (i == position) {//这是原本要在最后一个刷新的item
                return childCount - 1;
            }
        }
        return i;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.hasFocus()) {

            if (mPreView != null) {
                mPreView.clearAnimation();
            }
            view.startAnimation(enlargeAnimation);
            mPreView = view;
        } else {
            if (mPreView != null) {
                mPreView.clearAnimation();
                mPreView = null;
            }
        }
        if (onItemSelectedListener != null)
            onItemSelectedListener.onItemSelected(parent, view, position, id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (mPreView != null) {
            mPreView.clearAnimation();
            mPreView = null;
        }
        if (onItemSelectedListener != null) {
            onItemSelectedListener.onNothingSelected(parent);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            View view = getSelectedView();
            if (view != null) {
                view.startAnimation(enlargeAnimation);
                mPreView = view;
            } else {
                if (mPreView != null) {
                    mPreView.clearAnimation();
                    mPreView = null;
                }
            }
        } else {
            if (mPreView != null) {
                mPreView.clearAnimation();
                mPreView = null;
            }
        }
        if (onFocusChangeListener != null) {
            onFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //解决翻页出现的动画跳动问题
        int count = view.getChildCount();
        for (int i = 0; i < count; i++) {
            view.getChildAt(i).clearAnimation();
        }
    }
}