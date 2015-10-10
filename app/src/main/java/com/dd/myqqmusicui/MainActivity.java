package com.dd.myqqmusicui;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager)findViewById(R.id.main_pager);
    }
}
