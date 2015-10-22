package com.dd.myqqmusicui.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.myqqmusicui.R;
import com.dd.myqqmusicui.utils.AnimationHelper;

/**
 * Created by dong on 2015-10-12 0012.
 */
public class UserButton extends LinearLayout implements View.OnFocusChangeListener {
    private static final int backColor = 0xff232732;
    private static final int tipsColor = 0xff4f5258;
    private static final int nameColor = 0xff6b6d75;
    private Context mContext;
    private ImageView icon;
    private TextView name;
    private TextView tips;
    public UserButton(Context context) {
        this(context, null);
    }

    public UserButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setFocusable(true);
        setOnFocusChangeListener(this);
        init();
    }

    private void init() {
        setBackgroundColor(backColor);
        icon = new ImageView(mContext);
        icon.setImageResource(R.drawable.collect);
        name = new TextView(mContext);
        name.setText(R.string.user_name_login);
        tips = new TextView(mContext);
        name.setText(R.string.user_tips_login);

        setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams iconParams = new LayoutParams(160, 160);
        iconParams.setMargins(40, 40, 0, 0);
        addView(icon, iconParams);
        name.setText(R.string.user_name_login);
        name.setTextColor(nameColor);
        name.setTextSize(40);
        LinearLayout.LayoutParams nameParams = new LayoutParams(-2, -2);
        nameParams.leftMargin = 70;
        addView(name, nameParams);
        tips.setText(R.string.user_tips_login);
        tips.setTextColor(tipsColor);
        LinearLayout.LayoutParams tipsParams = new LayoutParams(-2, -2);
        tipsParams.leftMargin = 70;
        addView(tips, tipsParams);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            AnimationHelper.ScaleOutAnimation(v, 1.1f);
        }else {
            v.clearAnimation();
        }
    }
}
