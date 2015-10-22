package com.dd.myqqmusicui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dd.myqqmusicui.R;
import com.dd.myqqmusicui.view.MetroView.AutoFillMetroItem;
import com.dd.myqqmusicui.view.MetroView.AutoFillMetroView;
import com.dd.myqqmusicui.view.MetroView.MetroView;

/**
 * Created by dong on 2015-10-12 0012.
 */
public class RecommendFragment extends BaseFragment {

    AutoFillMetroView mAutoFillMetroView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recommend_fragment, container, false);
        LinearLayout lt = (LinearLayout) rootView.findViewById(R.id.con);

        mAutoFillMetroView = new AutoFillMetroView(getActivity().getApplicationContext());
        mAutoFillMetroView.setRowHeight(200);
        mAutoFillMetroView.setColWidth(200);
        mAutoFillMetroView.setOrientation(MetroView.OrientationType.Horizontal);
        mAutoFillMetroView.setVisibleItems(2, 3);
        mAutoFillMetroView.setPadding(90, 20, 90, 20);

        ImageView view1 = new ImageView(getActivity().getApplicationContext());
        view1.setImageResource(R.drawable.test_earth);
        view1.setFocusable(true);
        ImageView view2 = new ImageView(getActivity().getApplicationContext());
        view2.setImageResource(R.drawable.test_hd);
        view2.setFocusable(true);
        ImageView view3 = new ImageView(getActivity().getApplicationContext());
        view3.setImageResource(R.drawable.test_video);
        view3.setFocusable(true);


        mAutoFillMetroView.addAutoFillMetroItem(new AutoFillMetroItem(view1, 2, 2));
        mAutoFillMetroView.addAutoFillMetroItem(new AutoFillMetroItem(view2, 1, 2));
        mAutoFillMetroView.addAutoFillMetroItem(new AutoFillMetroItem(view3, 1, 1));


        lt.addView(mAutoFillMetroView);

        return rootView;
    }
}
