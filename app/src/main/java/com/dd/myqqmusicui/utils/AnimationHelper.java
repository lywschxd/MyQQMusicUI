package com.dd.myqqmusicui.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;  
import android.view.animation.AccelerateInterpolator;  
import android.view.animation.Animation;  
import android.view.animation.AnimationSet;  
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import java.util.Objects;

public class AnimationHelper {

    public static void ScaleOutAnimation(View view) {
        ScaleAnimation myAnimation_Scale = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,  
                0.5f);  
        myAnimation_Scale.setInterpolator(new AccelerateInterpolator());
        myAnimation_Scale.setDuration(100);
        myAnimation_Scale.setFillAfter(true);
        view.startAnimation(myAnimation_Scale);
    }

    public static void ScaleOutAnimation(View view, float offset) {
        ScaleAnimation myAnimation_Scale = new ScaleAnimation(1.0f, offset, 1.0f, offset,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        myAnimation_Scale.setInterpolator(new AccelerateInterpolator());
        myAnimation_Scale.setDuration(100);
        myAnimation_Scale.setFillAfter(true);
        view.startAnimation(myAnimation_Scale);
    }

    public static void HorizontalAnimation(View view, float[] dir, Animation.AnimationListener listener) {
        if ((dir == null) || ((dir != null) && (dir.length != 4))) {
            throw new ArrayIndexOutOfBoundsException("dir must have = 4 values");
        }

        TranslateAnimation myAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, dir[0], Animation.RELATIVE_TO_SELF, dir[1], Animation.RELATIVE_TO_SELF, dir[2], Animation.RELATIVE_TO_SELF, dir[3]);
        myAnimation.setDuration(500);
        myAnimation.setFillAfter(true);
        myAnimation.setAnimationListener(listener);
        view.startAnimation(myAnimation);
    }
}