package com.dd.myqqmusicui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.myqqmusicui.R;
import com.dd.myqqmusicui.view.EnlargeGridView;

/**
 * Created by dong on 2015-10-12 0012.
 */
public class SingerFragment extends BaseFragment{
    private String[] mSingerString;

    private EnlargeGridView mGridView;
    private SingerGridViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.singer_fragment, container, false);
        mSingerString = getActivity().getApplicationContext().getResources().getStringArray(R.array.siners_array);

        mGridView = (EnlargeGridView) rootView.findViewById(R.id.singer_view_pager);
        mAdapter = new SingerGridViewAdapter();
        mGridView.setAdapter(mAdapter);
        refreshImageGridview();

        return rootView;
    }

    public class SingerGridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mSingerString.length;
        }

        @Override
        public Object getItem(int position) {
            return mSingerString[position];
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
            holder.textView.setText(mSingerString[position]);
            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
            TextView textView;
        }
    }

    private void refreshImageGridview() {
        int rowNum = 2; // set how many row by yourself
        int columNum = 7;
        int gridItemWidth = dip2px(getActivity().getApplicationContext(), 200);
        int gridItemHeight = dip2px(getActivity().getApplicationContext(), 200);
        int gap = dip2px(getActivity().getApplicationContext(), 10);;

        // get GridView's height and width
        int gridViewWidth = (gridItemWidth + gap) * columNum;
        int gridViewHeight = (gridItemHeight + gap) * rowNum;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridViewWidth, gridViewHeight);
        mGridView.setLayoutParams(params);
        mGridView.setColumnWidth(gridItemWidth);
        mGridView.setHorizontalSpacing(gap);
        mGridView.setVerticalSpacing(gap);
        mGridView.setStretchMode(GridView.NO_STRETCH);
        mGridView.setNumColumns(columNum);
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
