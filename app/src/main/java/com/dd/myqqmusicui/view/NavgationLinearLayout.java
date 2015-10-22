package com.dd.myqqmusicui.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by dong on 2015-10-12 0012.
 */
public class NavgationLinearLayout extends LinearLayout implements View.OnFocusChangeListener{

    private NavgationOnFocusListener mlistener = null;

    public interface NavgationOnFocusListener {
        public void onFocus();
    }

    public NavgationLinearLayout(Context context) {
        this(context, null);
    }

    public NavgationLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavgationLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            if(mlistener != null)
                mlistener.onFocus();
        }
    }

    public void setNavgtionOnFocusListener(NavgationOnFocusListener listener) {
        mlistener = listener;
    }
}
