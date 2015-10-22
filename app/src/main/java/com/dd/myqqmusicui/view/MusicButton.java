package com.dd.myqqmusicui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.myqqmusicui.R;
import com.dd.myqqmusicui.utils.AnimationHelper;

/**
 * Created by  on 2015-10-13 0013.
 */
public class MusicButton extends LinearLayout implements View.OnFocusChangeListener{
    private final static int MUSICBUTTON_HANDLER_ICON_ANIMATION_START = 0;
    private final static int muiscNameColor = 0xff02FF6D;
    private Context mContext;
    private ImageView musicIcon;
    private TextView musicName;
    private int[] icons = {
      R.drawable.music_play01,
      R.drawable.music_play02,
      R.drawable.music_play03,
      R.drawable.music_play04,
    };

    private Handler musicIconHandler = new Handler() {
        int index = 1;
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MUSICBUTTON_HANDLER_ICON_ANIMATION_START:
                    musicIcon.setImageResource(icons[index++]);
                    if(index >= 4) index = 0;
                    musicIconHandler.sendEmptyMessageDelayed(MUSICBUTTON_HANDLER_ICON_ANIMATION_START, 200);
                    break;
            }
        }
    };

    public MusicButton(Context context) {
        this(context, null);
    }

    public MusicButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setFocusable(true);
        setOrientation(LinearLayout.HORIZONTAL);
        setOnFocusChangeListener(this);
        init();
    }

    private void init() {
        musicIcon = new ImageView(mContext);
        musicIconHandler.sendEmptyMessage(MUSICBUTTON_HANDLER_ICON_ANIMATION_START);

        musicName = new TextView(mContext);
        musicName.setText(R.string.music_name);
        musicName.setTextSize(18);
        musicName.setTextColor(muiscNameColor);
        LinearLayout.LayoutParams nameParams = new LayoutParams(-2,-2);
        nameParams.leftMargin = 10;
        addView(musicName, nameParams);
        LinearLayout.LayoutParams iconParams = new LayoutParams(-2,-2);
        iconParams.leftMargin = 20;
        iconParams.rightMargin = 10;
        addView(musicIcon, iconParams);
        musicName.setVisibility(INVISIBLE);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
             //使用普通动画
            musicName.setVisibility(VISIBLE);
            AnimationHelper.HorizontalAnimation(musicName, new float[]{1, 0, 0, 0}, null);

            /*//使用属性动画
           ObjectAnimator objectAnimator  =ObjectAnimator.ofFloat(musicName,"translationX",musicName.getWidth(), 0);
            objectAnimator.start();
              */
        }else {

              //使用普通动画
            AnimationHelper.HorizontalAnimation(musicName, new float[]{0, 1, 0, 0}, new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    musicName.clearAnimation();         //必须clearAnimation，否则 invisible 无效
                    musicName.setVisibility(INVISIBLE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            /*//使用属性动画
            ObjectAnimator objectAnimator  =ObjectAnimator.ofFloat(musicName,"translationX", 0, musicName.getWidth());
            objectAnimator.start();
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    musicName.setVisibility(INVISIBLE);
                }
                @Override
                public void onAnimationCancel(Animator animation) {
                }
                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            */
        }
    }
}
