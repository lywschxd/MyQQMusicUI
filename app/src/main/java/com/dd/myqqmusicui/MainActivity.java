package com.dd.myqqmusicui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

public class MainActivity extends Activity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager)findViewById(R.id.main_pager);
    }

    public void OnIndicatorButtonClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.recommend:
            case R.id.radio:
            case R.id.ranking:
            case R.id.singer:
        }
    }
}
