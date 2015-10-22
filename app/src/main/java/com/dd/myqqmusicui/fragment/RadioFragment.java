package com.dd.myqqmusicui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dd.myqqmusicui.R;
import com.dd.myqqmusicui.view.EnlargeGridView;

/**
 * Created by dong on 2015-10-12 0012.
 */
public class RadioFragment extends BaseFragment {
    private EnlargeGridView mGridView;
    private MagnifyGridViewAdapter mAdapter;

    private String[] mRadioString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.radio_fragment, container, false);
        mRadioString = getActivity().getApplicationContext().getResources().getStringArray(R.array.radio_catroy);
        mGridView = (EnlargeGridView) rootView.findViewById(R.id.radio_view_pager);
        mAdapter = new MagnifyGridViewAdapter();
        mGridView.setAdapter(mAdapter);
        return rootView;
    }

    public class MagnifyGridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mRadioString.length;
        }

        @Override
        public Object getItem(int position) {
            return mRadioString[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.viewpager_item, null);
                holder.imageView = (ImageView) convertView.findViewById(R.id.image);
                holder.textView = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(mRadioString[position]);
            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
            TextView textView;
        }
    }
}
