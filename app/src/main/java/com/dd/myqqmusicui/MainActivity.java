package com.dd.myqqmusicui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dd.myqqmusicui.fragment.BaseFragment;
import com.dd.myqqmusicui.fragment.MyFragmentPagerAdapter;
import com.dd.myqqmusicui.fragment.RadioFragment;
import com.dd.myqqmusicui.fragment.RankFragment;
import com.dd.myqqmusicui.fragment.RecommendFragment;
import com.dd.myqqmusicui.fragment.SingerFragment;
import com.dd.myqqmusicui.fragment.UserFragment;
import com.dd.myqqmusicui.utils.AnimationHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnFocusChangeListener {

    private ViewPager mViewPager;
    private List<BaseFragment> mFragmentList;
    private List<Button> mNavgationButtonList;
    private LinearLayout mNavgationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.main_pager);

        initButton();
        initViewpager();
    }

    private void initViewpager() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new UserFragment());
        mFragmentList.add(new RecommendFragment());
        mFragmentList.add(new RadioFragment());
        mFragmentList.add(new RankFragment());
        mFragmentList.add(new SingerFragment());
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mNavgationButtonList.size(); i++) {
                    mNavgationButtonList.get(i).setTextColor(position == i ? Color.GREEN : Color.BLACK);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(BaseFragment.FRAGMENT_TYPE_SINGERS);
    }

    private void initButton() {
        mNavgationButtonList = new ArrayList<>();
        mNavgationButtonList.add((Button)findViewById(R.id.user));
        mNavgationButtonList.add((Button)findViewById(R.id.recommend));
        mNavgationButtonList.add((Button)findViewById(R.id.radio));
        mNavgationButtonList.add((Button)findViewById(R.id.ranking));
        mNavgationButtonList.add((Button) findViewById(R.id.singer));
        for (Button item: mNavgationButtonList) {
            item.setOnFocusChangeListener(this);
        }

        mNavgationLayout = (LinearLayout) findViewById(R.id.navgation);
        mNavgationLayout.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if(v.getId() == R.id.navgation) {
                Button btn = (Button) v.findViewWithTag(mViewPager.getCurrentItem()+"");
                btn.requestFocus();
            }else {
                mViewPager.setCurrentItem(Integer.parseInt((String) v.getTag()));
                AnimationHelper.ScaleOutAnimation(v);
            }
        }else {
            v.clearAnimation();
        }
    }

    public void gotoMusic(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MusicActivity.class);
        startActivity(intent);
    }
}
