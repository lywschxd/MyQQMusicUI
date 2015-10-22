package com.dd.myqqmusicui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dd.myqqmusicui.R;

/**
 * Created by dong on 2015-10-12 0012.
 */
public class UserFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_fragment, container, false);
        return rootView;
    }
}
